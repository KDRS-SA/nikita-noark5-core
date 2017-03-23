package nikita.model.noark5.v4.metadata;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// Noark 5v4
@Entity
@Table(name = "electronic_signature_security_level")
// Enable soft delete
@SQLDelete(sql = "UPDATE electronic_signature_security_level SET delete" +
        "d = true WHERE id = ?")
@Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_electronic_signature_security_level_id"))
public class ElectronicSignatureSecurityLevel extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;
}
