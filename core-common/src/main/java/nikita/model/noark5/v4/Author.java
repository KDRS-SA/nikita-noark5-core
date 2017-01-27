package nikita.model.noark5.v4;

import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "author")
// Enable soft delete of Author
@SQLDelete(sql="UPDATE author SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class Author implements INikitaEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_author_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id", unique=true)
    @Audited
    protected String systemId;

    /**
     * M024 - forfatter (xs:string)
     */
    @Column(name = "author")
    @Audited
    protected String author;

    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;

    // Links to BasicRecords
    @ManyToMany(mappedBy = "referenceAuthor")
    protected Set<BasicRecord> referenceBasicRecord = new HashSet<BasicRecord>();

    // Links to DocumentDescriptions
    @ManyToMany(mappedBy = "referenceAuthor")
    protected Set<DocumentDescription> referenceDocumentDescription = new HashSet<DocumentDescription>();

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(String ownedBy) {
        this.ownedBy = ownedBy;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<BasicRecord> getReferenceBasicRecord() {
        return referenceBasicRecord;
    }

    public void setReferenceBasicRecord(Set<BasicRecord> referenceBasicRecord) {
        this.referenceBasicRecord = referenceBasicRecord;
    }

    public Set<DocumentDescription> getReferenceDocumentDescription() {
        return referenceDocumentDescription;
    }

    public void setReferenceDocumentDescription(Set<DocumentDescription> referenceDocumentDescription) {
        this.referenceDocumentDescription = referenceDocumentDescription;
    }

    @Override
    public String toString() {
        return "Author{" +
                "author='" + author + '\'' +
                ", systemId='" + systemId + '\'' +
                ", id=" + id +
                '}';
    }
}
