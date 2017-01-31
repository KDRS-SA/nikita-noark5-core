package no.arkivlab.hioa.nikita.webapp.service.interfaces;


import nikita.model.noark5.v4.DocumentObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;

public interface IDocumentObjectService {


	// -- All CREATE operations

	DocumentObject save(DocumentObject documentObject);

	// -- All READ operations

    Iterable<DocumentObject> findDocumentObjectByOwnerPaginated(Integer top, Integer skip);

	Iterable<DocumentObject> findAll();
	List<DocumentObject> findAll(Sort sort);
	Page<DocumentObject> findAll(Pageable pageable);

	// id
	DocumentObject findById(Long id);

	// systemId
	DocumentObject findBySystemId(String systemId);

	// createdDate
	List<DocumentObject> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy);
	List<DocumentObject> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Sort sort);
	List<DocumentObject> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy);

	Page<DocumentObject> findByCreatedDateAndOwnedBy(Date createdDate, String ownedBy, Pageable pageable);
	Page<DocumentObject> findByCreatedDateBetweenAndOwnedBy(Date start, Date end, String ownedBy, Pageable pageable);

	// createdBy
	List<DocumentObject> findByCreatedByAndOwnedBy(String createdBy, String ownedBy);
	List<DocumentObject> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy);
	List<DocumentObject> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy);
	List<DocumentObject> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Sort sort);
	List<DocumentObject> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort);
	List<DocumentObject> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Sort sort);

	Page<DocumentObject> findByCreatedByAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);
	Page<DocumentObject> findByCreatedByContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);
	Page<DocumentObject> findByCreatedByIgnoreCaseContainingAndOwnedBy(String createdBy, String ownedBy, Pageable pageable);


	// deleted
	List<DocumentObject> findByDeletedAndOwnedBy(String deleted, String ownedBy);
	List<DocumentObject> findByDeletedAndOwnedBy(String deleted, String ownedBy, Sort sort);
	Page<DocumentObject> findByDeletedAndOwnedBy(String deleted, String ownedBy, Pageable pageable);

	// ownedBy
	List<DocumentObject> findByOwnedBy(String ownedBy);
	List<DocumentObject> findByOwnedBy(String ownedBy, Sort sort);
	Page<DocumentObject> findByOwnedBy(String ownedBy, Pageable pageable);

}