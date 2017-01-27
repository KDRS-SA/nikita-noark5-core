package no.arkivlab.hioa.nikita.webapp.service.impl.imprt;

import nikita.model.noark5.v4.CaseFile;
import nikita.model.noark5.v4.RegistryEntry;
import nikita.repository.n5v4.ICaseFileRepository;
import no.arkivlab.hioa.nikita.webapp.service.impl.CaseFileService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IRegistryEntryService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.imprt.ICaseFileImportService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.imprt.IRegistryEntryImportService;
import no.arkivlab.hioa.nikita.webapp.util.NoarkUtils;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.NoarkEntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static nikita.config.Constants.INFO_CANNOT_FIND_OBJECT;

@Service
@Transactional
public class CaseFileImportService extends FileImportService implements ICaseFileImportService {

    private static final Logger logger = LoggerFactory.getLogger(CaseFileImportService.class);

    @Autowired
    IRegistryEntryImportService registryEntryImportService;

    @Autowired
    ICaseFileRepository caseFileRepository;

    @Override
    public CaseFile save(CaseFile caseFile) {
        return caseFileRepository.save(caseFile);
    }

    @Override
    public RegistryEntry createRegistryEntryAssociatedWithCaseFile(String fileSystemId, RegistryEntry registryEntry) {
        RegistryEntry persistedRecord = null;
        CaseFile file = caseFileRepository.findBySystemId(fileSystemId);
        if (file == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " CaseFile, using fileSystemId " + fileSystemId;
            logger.info(info) ;
            throw new NoarkEntityNotFoundException(info);
        }
        else {
            registryEntry.setReferenceFile(file);
            persistedRecord = registryEntryImportService.save(registryEntry);
        }
        return persistedRecord;
    }
}
