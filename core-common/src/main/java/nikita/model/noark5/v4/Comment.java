package nikita.model.noark5.v4;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "comment")
// Enable soft delete of Comment
@SQLDelete(sql="UPDATE comment SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class Comment {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_comment_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    /**
     * M310 - merknadstekst (xs:string)
     */
    @Column(name = "comment_text")
    @Audited
    protected String commentText;

    /**
     * M084 - merknadstype (xs:string)
     */
    @Column(name = "comment_type")
    @Audited
    protected String commentType;

    /**
     * M611 - merknadsdato (xs:dateTime)
     */
    @Column(name = "comment_time")
    @Audited
    protected Date commentDate;

    /**
     * M612 - merknadRegistrertAv (xs:string)
     */
    @Column(name = "comment_registered_by")
    @Audited
    protected String commentRegisteredBy;

    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;

    // Link to File
    @ManyToMany(mappedBy = "referenceComment")
    protected Set<File> referenceFile = new HashSet<File>();

    // Links to BasicRecord
    @ManyToMany(mappedBy = "referenceComment")
    protected Set<BasicRecord> referenceRecord = new HashSet<BasicRecord>();

    // Link to DocumentDescription
    @ManyToMany(mappedBy = "referenceComment")
    protected Set<DocumentDescription> referenceDocumentDescription = new HashSet<DocumentDescription>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getCommentType() {
        return commentType;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public String getCommentRegisteredBy() {
        return commentRegisteredBy;
    }

    public void setCommentRegisteredBy(String commentRegisteredBy) {
        this.commentRegisteredBy = commentRegisteredBy;
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

    public Set<File> getReferenceFile() {
        return referenceFile;
    }

    public void setReferenceFile(Set<File> referenceFile) {
        this.referenceFile = referenceFile;
    }

    public Set<BasicRecord> getReferenceRecord() {
        return referenceRecord;
    }

    public void setReferenceRecord(Set<BasicRecord> referenceRecord) {
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
        return "Comment{" +
                "id=" + id +
                ", commentText='" + commentText + '\'' +
                ", commentType='" + commentType + '\'' +
                ", commentDate=" + commentDate +
                ", commentRegisteredBy='" + commentRegisteredBy + '\'' +
                '}';
    }
}