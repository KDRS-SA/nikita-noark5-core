package nikita.util.deserialisers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nikita.util.exceptions.NikitaMalformedInputDataException;
import nikita.model.noark5.v4.Series;
import nikita.model.noark5.v4.interfaces.entities.INoarkGeneralEntity;
import nikita.util.deserialisers.interfaces.ObligatoryPropertiesCheck;
import nikita.util.CommonUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import static nikita.config.Constants.NOARK_DATE_FORMAT_PATTERN;
import static nikita.config.N5ResourceMappings.*;
import static nikita.util.CommonUtils.Hateoas.Deserialize;

/**
 * Created by tsodring on 1/6/17.
 *
 * Deserialise an incoming Series JSON object.
 *
 * Having a own deserialiser is done to have more fine grained control over the input. This allows us to be less strict
 * with property names, allowing for both English and Norwegian property names
 *
 * Both English and Norwegian property names can be used in the incoming JSON as well as there being no requirement with
 * regards to small and large letters in property names.
 *
 * Note this implementation expects that the Series object to deserialise is in compliance with the Noark standard where
 * certain properties i.e. createdBy and createdDate are set by the core, not the caller. This deserializer will not 
 * enforce this and will deserialize a series object correctly. This is because e.g the import interface will require
 * such functionality.
 *
 *  - Testing of compliance of properties is handled by the core, either in SeriesController or SeriesService
 * 
 * Note. Currently we do not include 'id' or 'deleted' properties. 'id' is a primary key and it is assumed this is 
 * taken care of by the DBMS and 'deleted' is a field internal to the core to handle soft delete. Importing soft deleted
 * objects is something we do not consider necessary.
 *
 * Note:
 *  - Unknown property values in the JSON will trigger an exception
 *  - Missing obligatory property values in the JSON will trigger an exception
 */
public class SeriesDeserializer extends JsonDeserializer implements ObligatoryPropertiesCheck {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Series deserialize(JsonParser jsonParser, DeserializationContext dc)
            throws IOException {

        Series series = new Series();
        ObjectNode objectNode = mapper.readTree(jsonParser);

        // Deserialise general properties
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkEntity(series, objectNode);
        CommonUtils.Hateoas.Deserialize.deserialiseDocumentMedium(series, objectNode);
        CommonUtils.Hateoas.Deserialize.deserialiseStorageLocation(series, objectNode);

        // Deserialize seriesStatus
        JsonNode currentNode = objectNode.get(SERIES_STATUS);
        String key = SERIES_STATUS;
        if (currentNode == null) {
            currentNode = objectNode.get(SERIES_STATUS_EN);
            key = SERIES_STATUS_EN;
        }
        if (currentNode != null) {
            series.setSeriesStatus(currentNode.textValue());
            objectNode.remove(key);
        }

        // Deserialize seriesStartDate
        currentNode = objectNode.get(SERIES_START_DATE);
        key = SERIES_START_DATE;
        if (currentNode == null) {
            currentNode = objectNode.get(SERIES_START_DATE_EN);
            key = SERIES_START_DATE_EN;
        }
        if (currentNode != null) {
            try {
                Date parsedDate = Deserialize.dateFormat.parse(currentNode.textValue());
                series.setSeriesStartDate(parsedDate);
            } catch (ParseException e) {
                throw new NikitaMalformedInputDataException("The series object you tried to create " +
                        "has a malformed arkivperiodeStartDato/seriesStartDate. Make sure format is " +
                        NOARK_DATE_FORMAT_PATTERN);
            }
            objectNode.remove(key);
        }

        // Deserialize seriesEndDate
        currentNode = objectNode.get(SERIES_END_DATE);
        key = SERIES_END_DATE;
        if (currentNode == null) {
            currentNode = objectNode.get(SERIES_END_DATE_EN);
            key = SERIES_END_DATE_EN;
        }
        if (currentNode != null) {
            try {
                Date parsedDate = Deserialize.dateFormat.parse(currentNode.textValue());
                series.setSeriesEndDate(parsedDate);
            } catch (ParseException e) {
                throw new NikitaMalformedInputDataException("The series object you tried to create " +
                        "has a malformed arkivperiodeSluttDato/seriesEndDate. Make sure format is " +
                        NOARK_DATE_FORMAT_PATTERN);
            }
            objectNode.remove(key);
        }

        // Deserialize referencePrecursor
        currentNode = objectNode.get(SERIES_PRECURSOR);
        key = SERIES_PRECURSOR;
        if (currentNode == null) {
            currentNode = objectNode.get(SERIES_PRECURSOR_EN);
            key = SERIES_PRECURSOR_EN;
        }
        if (currentNode != null) {
            Series seriesPrecursor = new Series();
            seriesPrecursor.setSystemId(currentNode.textValue());
            series.setReferencePrecursor(seriesPrecursor);
            // TODO: Does this imply that the current series object is the successor?
            // I would not set it here, as the service class has to check that
            // the seriesPrecursor object actually exists
            objectNode.remove(key);
        }

        // Deserialize
        currentNode = objectNode.get(SERIES_SUCCESSOR);
        key = SERIES_SUCCESSOR;
        if (currentNode == null) {
            currentNode = objectNode.get(SERIES_SUCCESSOR_EN);
            key = SERIES_SUCCESSOR_EN;
        }
        if (currentNode != null) {
            Series seriesSuccessor = new Series();
            seriesSuccessor.setSystemId(currentNode.textValue());
            series.setReferenceSuccessor(seriesSuccessor);
            // TODO: Does this imply that the current series object is the precursor?
            // I would not set it here, as the service class should do this
            objectNode.remove(key);
        }
        series.setReferenceDisposal(CommonUtils.Hateoas.Deserialize.deserialiseDisposal(objectNode));
        series.setReferenceDisposalUndertaken(CommonUtils.Hateoas.Deserialize.deserialiseDisposalUndertaken(objectNode));
        series.setReferenceDeletion(CommonUtils.Hateoas.Deserialize.deserialiseDeletion(objectNode));
        series.setReferenceScreening(CommonUtils.Hateoas.Deserialize.deserialiseScreening(objectNode));
        series.setReferenceClassified(CommonUtils.Hateoas.Deserialize.deserialiseClassified(objectNode));

        // Check that all obligatory values are present
        checkForObligatoryNoarkValues(series);

        // Check that there are no additional values left after processing the tree
        // If there are additional throw a malformed input exception
        if (objectNode.size() != 0) {
            throw new NikitaMalformedInputDataException("The Series object you tried to create is malformed. The "
                    + "following objects are not recognised as Series properties [" +
                    CommonUtils.Hateoas.Deserialize.checkNodeObjectEmpty(objectNode) + "]");
        }
        return series;
    }

    @Override
    /**
     *
     * The only field that is mandatory, according to arkivstruktur.xsd, when creating the object is 'title'
     */
    public void checkForObligatoryNoarkValues(INoarkGeneralEntity noarkEntity) {

        if (noarkEntity.getTitle() == null) {
            throw new NikitaMalformedInputDataException("The Series object you tried to create is malformed. The "
                    + "title field is mandatory, and you have submitted an empty value.");
        }
    }
}
