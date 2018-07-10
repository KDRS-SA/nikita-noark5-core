package nikita.model.noark5.v4.metadata;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static nikita.config.N5ResourceMappings.EVENT_TYPE;

// Noark 5v4
@Entity
@Table(name = "event_type")
// Enable soft delete
@SQLDelete(sql = "UPDATE event_type SET delete" +
        "d = true WHERE id = ?")
@Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_event_type_id"))
public class EventType extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return EVENT_TYPE;
    }
}
