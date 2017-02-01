package nikita.util.serializers.noark5v4.hateoas;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.common.collect.Iterables;
import nikita.model.noark5.v4.ClassificationSystem;
import nikita.model.noark5.v4.hateoas.ClassificationSystemHateoas;
import nikita.util.CommonUtils;

import java.io.IOException;
import java.lang.reflect.Type;

import static nikita.config.N5ResourceMappings.CLASSIFICATION_SYSTEM;

/**
 *
 * Serialise an outgoing ClassificationSystem object as JSON.
 *
 * Having an own serializer is done to have more fine grained control over the output. We need to be able to especially
 * control the HATEOAS links and the actual format of the HATEOAS links might change over time with the standard. This
 * allows us to be able to easily adapt to any changes
 *
 * Only Norwegian property names are used on the outgoing JSON property names and are in accordance with the Noark
 * standard.
 *
 * Note. Only values that are part of the standard are included in the JSON. Properties like 'id' or 'deleted' are not
 * exported
 *
 */
public class ClassificationSystemHateoasSerializer extends StdSerializer<ClassificationSystemHateoas> {

    public ClassificationSystemHateoasSerializer() {
        super(ClassificationSystemHateoas.class);
    }

    @Override
    public void serialize(ClassificationSystemHateoas classificationSystemHateoas, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {

        Iterable<ClassificationSystem> classificationSystemIterable = classificationSystemHateoas.getClassificationSystemIterable();
        if (classificationSystemIterable != null && Iterables.size(classificationSystemIterable) > 0) {
            jgen.writeStartObject();
            jgen.writeFieldName(CLASSIFICATION_SYSTEM);
            jgen.writeStartArray();
            for (ClassificationSystem classificationSystem : classificationSystemIterable) {
                serializeClassificationSystem(classificationSystem, classificationSystemHateoas, jgen, provider);
            }
            jgen.writeEndArray();
            jgen.writeEndObject();
        } else if (classificationSystemHateoas.getClassificationSystem() != null) {
            serializeClassificationSystem(classificationSystemHateoas.getClassificationSystem(), classificationSystemHateoas, jgen, provider);
        }
        // It's an empty object, so returning empty Hateoas links _links : []
        else {
            CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, null);
        }
    }

    private void serializeClassificationSystem(ClassificationSystem classificationSystem, ClassificationSystemHateoas classificationSystemHateoas,
                                               JsonGenerator jgen, SerializerProvider provider) throws IOException {


        jgen.writeStartObject();
        CommonUtils.Hateoas.Serialize.printSystemIdEntity(jgen, classificationSystem);
        CommonUtils.Hateoas.Serialize.printTitleAndDescription(jgen, classificationSystem);
        CommonUtils.Hateoas.Serialize.printCreateEntity(jgen, classificationSystem);
        CommonUtils.Hateoas.Serialize.printFinaliseEntity(jgen, classificationSystem);
        CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, classificationSystemHateoas.getLinks());
        jgen.writeEndObject();
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        throw new UnsupportedOperationException();
    }
}
