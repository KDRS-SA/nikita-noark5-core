package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas.metadata;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.metadata.FondsStatus;
import nikita.util.exceptions.NikitaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.*;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH + SLASH + FONDS_STATUS)
public class FondsStatusController {


    // API - All POST Requests (CRUD - CREATE)

    @ApiOperation(value = "Persists a new FondsStatus object", notes = "Returns the newly" +
            " created FondsStatus object after it is persisted to the database", response = FondsStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "FondsStatus " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FondsStatus.class),
            @ApiResponse(code = 201, message = "FondsStatus " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = FondsStatus.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 501, message = API_MESSAGE_NOT_IMPLEMTNED)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + NEW_DOCUMENT_MEDIUM)
    public ResponseEntity<FondsStatus> createFondsStatus(@RequestBody FondsStatus FondsStatus) throws NikitaException {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    // API - All GET Requests (CRUD - READ)
    @ApiOperation(value = "Gets a particular FondsStatus object given a code", notes = "Returns the requested " +
            " FondsStatus object", response = FondsStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "FondsStatus " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FondsStatus.class),
            @ApiResponse(code = 201, message = "FondsStatus " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = FondsStatus.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_MALFORMED_PAYLOAD),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR),
            @ApiResponse(code = 501, message = API_MESSAGE_NOT_IMPLEMTNED)})
    @Counted
    @Timed
    @RequestMapping(value = CODE + SLASH + "{kode}", method = RequestMethod.GET)
    public ResponseEntity<FondsStatus> findOne(@PathVariable("kode") final String code) {
        //FondsStatus FondsStatus = FondsStatusService.findbyCode(code);
        //return new ResponseEntity<> (FondsStatus, HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @ApiOperation(value = "Retrieves all FondsStatus codes", response = FondsStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "FondsStatus codes found",
                    response = FondsStatus.class),
            @ApiResponse(code = 404, message = "No FondsStatus found"),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET, value = FONDS + SLASH)
    public ResponseEntity<FondsStatus> findAllFondsStatus(
            final UriComponentsBuilder uriBuilder, HttpServletRequest request,
            final HttpServletResponse response) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
