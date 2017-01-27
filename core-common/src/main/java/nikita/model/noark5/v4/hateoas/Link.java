package nikita.model.noark5.v4.hateoas;

/**
 * Created by tsodring on 12/22/16.
 */
public class Link {
    protected String href;
    protected String rel;
    protected Boolean templated;

    public Link(String href, String rel, Boolean templated) {
        this.href = href;
        this.rel = rel;
        this.templated = templated;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public Boolean getTemplated() {
        return templated;
    }

    public void setTemplated(Boolean templated) {
        this.templated = templated;
    }

    @Override
    public String toString() {
        return "Link{" +
                "href='" + href + '\'' +
                ", rel='" + rel + '\'' +
                ", templated=" + templated +
                '}';
    }
}
