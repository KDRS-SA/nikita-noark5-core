package nikita.model.noark5.v4;

import nikita.model.noark5.v4.interfaces.entities.IDisposalEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.lang.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tsodring on 4/10/16.
 */
@Entity
@Table(name = "disposal")
// Enable soft delete of Disposal
@SQLDelete(sql="UPDATE disposal SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class Disposal implements IDisposalEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_disposal_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    /** M450 - kassasjonsvedtak (xs:string) */
    @Column(name = "disposal_decision")
    @Audited
    protected String disposalDecision;

    /** M453 - kassasjonshjemmel (xs:string) */
    @Column(name = "disposal_authority")
    @Audited
    protected String disposalAuthority;

    /** M451 - bevaringstid (xs:integer) */
    @Column(name = "preservation_time")
    @Audited
    protected Integer preservationTime;

    /** M452 - kassasjonsdato (xs:date) */
    @Column(name = "disposal_date")
    @Audited
    protected Date disposalDate;

    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;

    @Column(name = "etag")
    protected String eTag;

    // Links to Series
    @OneToMany(mappedBy = "referenceDisposal")
    protected Set<Series> referenceSeries = new HashSet<Series>();

    // Links to Class
    @OneToMany(mappedBy = "referenceDisposal")
    protected Set<Class> referenceClass = new HashSet<Class>();

    // Links to File
    @OneToMany(mappedBy = "referenceDisposal")
    protected Set<File> referenceFile= new HashSet<File>();

    // Links to Record
    @OneToMany(mappedBy = "referenceDisposal")
    protected Set<Record> referenceRecord = new HashSet<Record>();

    // Links to DocumentDescription
    @OneToMany(mappedBy = "referenceDisposal")
    protected Set<DocumentDescription> referenceDocumentDescription = new HashSet<DocumentDescription>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisposalDecision() {
        return disposalDecision;
    }

    public void setDisposalDecision(String disposalDecision) {
        this.disposalDecision = disposalDecision;
    }

    public String getDisposalAuthority() {
        return disposalAuthority;
    }

    public void setDisposalAuthority(String disposalAuthority) {
        this.disposalAuthority = disposalAuthority;
    }

    public Integer getPreservationTime() {
        return preservationTime;
    }

    public void setPreservationTime(Integer preservationTime) {
        this.preservationTime = preservationTime;
    }

    public Date getDisposalDate() {
        return disposalDate;
    }

    public void setDisposalDate(Date disposalDate) {
        this.disposalDate = disposalDate;
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

    public String geteTag() { return eTag;}

    public void seteTag(String eTag) { this.eTag = eTag; }

    public Set<Series> getReferenceSeries() {
        return referenceSeries;
    }

    public void setReferenceSeries(Set<Series> referenceSeries) {
        this.referenceSeries = referenceSeries;
    }

    public Set<Class> getReferenceClass() {
        return referenceClass;
    }

    public void setReferenceClass(Set<Class> referenceClass) {
        this.referenceClass = referenceClass;
    }

    public Set<File> getReferenceFile() {
        return referenceFile;
    }

    public void setReferenceFile(Set<File> referenceFile) {
        this.referenceFile = referenceFile;
    }

    public Set<Record> getReferenceRecord() {
        return referenceRecord;
    }

    public void setReferenceRecord(Set<Record> referenceRecord) {
        this.referenceRecord = referenceRecord;
    }

    public Set<DocumentDescription> getReferenceDocumentDescription() {
        return referenceDocumentDescription;
    }

    public void setReferenceDocumentDescription(Set<DocumentDescription> referenceDocumentDescription) {
        this.referenceDocumentDescription = referenceDocumentDescription;
    }

    @Override
    public String toString() {
        return "Disposal{" +
                "disposalDate=" + disposalDate +
                ", preservationTime=" + preservationTime +
                ", disposalAuthority='" + disposalAuthority + '\'' +
                ", disposalDecision='" + disposalDecision + '\'' +
                ", eTag='" + eTag + '\'' +
                ", id=" + id +
                '}';
    }
}