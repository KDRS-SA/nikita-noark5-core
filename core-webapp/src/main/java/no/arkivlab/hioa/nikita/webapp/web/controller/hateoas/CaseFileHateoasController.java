package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.CaseFile;
import nikita.model.noark5.v4.RegistryEntry;
import nikita.model.noark5.v4.hateoas.CaseFileHateoas;
import nikita.model.noark5.v4.hateoas.RegistryEntryHateoas;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.ICaseFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.CASE_FILE;
import static nikita.config.N5ResourceMappings.SYSTEM_ID;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + CASE_FILE)
public class CaseFileHateoasController {

    @Autowired
    ICaseFileService caseFileService;

    // API - All POST Requests (CRUD - CREATE)

    @ApiOperation(value = "Persists a RegistryEntry object associated with the given Series systemId",
            notes = "Returns the newly created record object after it was associated with a File object and " +
                    "persisted to the database", response = RegistryEntryHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "RegistryEntry " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = RegistryEntryHateoas.class),
            @ApiResponse(code = 201, message = "RegistryEntry " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = RegistryEntryHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type RegistryEntry"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = LEFT_PARENTHESIS + "fileSystemId" + RIGHT_PARENTHESIS + SLASH
            + NEW_REGISTRY_ENTRY)
    public ResponseEntity<RegistryEntryHateoas> createRegistryEntryAssociatedWithFile(
            @ApiParam(name = "fileSystemId",
                    value = "systemId of file to associate the record with",
                    required = true)
            @PathVariable String fileSystemId,
            @ApiParam(name = "RegistryEntry",
                    value = "Incoming registryEntry object",
                    required = true)
            @RequestBody RegistryEntry registryEntry)  throws NikitaException {
        RegistryEntryHateoas registryEntryHateoas =
                new RegistryEntryHateoas(caseFileService.createRegistryEntryAssociatedWithCaseFile(
                        fileSystemId, registryEntry));
        return new ResponseEntity<> (registryEntryHateoas, HttpStatus.CREATED);
    }

    // API - All GET Requests (CRUD - READ)

    @ApiOperation(value = "Retrieves a single CaseFile entity given a systemId", response = CaseFile.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CaseFile returned", response = CaseFile.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS, method = RequestMethod.GET)
    public ResponseEntity<CaseFileHateoas> findOneCaseFilebySystemId(
            @ApiParam(name = "systemID",
                    value = "systemID of the caseFile to retrieve",
                    required = true)
            @PathVariable("systemID") final String caseFileSystemId) {
        CaseFileHateoas caseFileHateoas = new
                CaseFileHateoas((CaseFile)caseFileService.findBySystemId(caseFileSystemId));
        return new ResponseEntity<>(caseFileHateoas, HttpStatus.CREATED);
    }
}
