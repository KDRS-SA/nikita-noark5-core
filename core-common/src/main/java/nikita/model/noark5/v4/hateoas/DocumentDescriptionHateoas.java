package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.DocumentDescription;
import nikita.util.serializers.noark5v4.hateoas.DocumentDescriptionHateoasSerializer;

import java.util.List;

/**
 * Created by tsodring on 12/9/16.
 *
 * Using composition rather than inheritance. Although this class is really only a placeholder for the DocumentDescription object
 * along with the hateoas links. It's not intended that you will manipulate the DocumentDescription object from here.
 *
 */
@JsonSerialize(using = DocumentDescriptionHateoasSerializer.class)
public class DocumentDescriptionHateoas implements IHateoasLinks {

    protected List<Link> links;
    DocumentDescription documentDescription;
    private Iterable<DocumentDescription> documentDescriptionIterable;

    public DocumentDescriptionHateoas(DocumentDescription documentDescription){
        this.documentDescription = documentDescription;
    }

    public DocumentDescriptionHateoas(Iterable<DocumentDescription> documentDescriptionIterable) {
        this.documentDescriptionIterable = documentDescriptionIterable;
    }

    public List<Link> getLinks() {
        return links;
    }
    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public DocumentDescription getDocumentDescription() {
        return documentDescription;
    }
    public void setDocumentDescription(DocumentDescription documentDescription) {
        this.documentDescription = documentDescription;
    }

    public Iterable<DocumentDescription> getDocumentDescriptionIterable() {
        return documentDescriptionIterable;
    }

    public void setDocumentDescriptionIterable(Iterable<DocumentDescription> documentDescriptionIterable) {
        this.documentDescriptionIterable = documentDescriptionIterable;
    }
}