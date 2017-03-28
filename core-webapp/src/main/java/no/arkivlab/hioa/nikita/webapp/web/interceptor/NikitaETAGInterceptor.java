package no.arkivlab.hioa.nikita.webapp.web.interceptor;

import nikita.util.exceptions.NikitaMalformedHeaderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpHeaders.ETAG;

/**
 * Created by tsodring on 3/25/17.
 * <p>
 * NikitaETAGInterceptor adds a
 */
public class NikitaETAGInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(NikitaETAGInterceptor.class);

    /**
     * @param request
     * @param response
     * @param handler  Make sure an incoming PUT request contains a ETAG. Any PUT request without an ETAG is rejected with
     *                 a MalformedHeaderException
     * @return
     * @throws RuntimeException
     */
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
            throws RuntimeException {

        // All PUT requests must have an eTag. Otherwise reject it
        if (RequestMethod.PUT.name().equals(request.getMethod())) {
            if (request.getHeader(ETAG) == null) {
                throw new NikitaMalformedHeaderException("eTag is missing on following request: " +
                        "[" + request.getRequestURI() + "], Request method [" + request.getMethod() + "]" +
                        ". You cannot update an entity in the core unless you first ensure you have retrieved an" +
                        " entity with an eTag set. Using an entity from a list does not provide an eTag.");
            }
        }
        // Important! You must return true if all is OK or further filer processing stops
        return true;
    }

    /**
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     *                     <p>
     *                     After a request has completed, make sure to add an eTag to requests that contain a single Noark entity.
     *                     This is so that clients can update the object. We make no difference with respect to the request method.
     *                     <p>
     *                     If for some reason the NoarkEntity has a null value, then this code assumes that this is intentional
     *                     and no eTag is generated. Without an eTag, the client cannot update the entity.
     * @throws Exception
     */
    /*public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod)handler;
        MethodParameter parameter = handlerMethod.getReturnType();
        parameter.get
        // TODO : How are we handling empty results with this?
        // If it's empty it won't be a  HateoasNoarkObject ... it's {} so the 2nd if should ignore it
        if (handler instanceof HateoasNoarkObject) {
            String superClassName = handler.getClass().getSuperclass().getName();
            if (superClassName.equals(HateoasNoarkObject.class.getName())) {
                HateoasNoarkObject outgoingObject = ((HateoasNoarkObject) handler);
                if (outgoingObject.isSingleEntity()) {
                    // There should only be a single entity in the list, so we will find the version for eTag
                    // in the first entity. If there is more than one then something has gone wrong elsewhere
                    // Don't see the need to check for everything.
                    List entityList = outgoingObject.getList();
                    INoarkSystemIdEntity noarkSystemIdEntity = (INoarkSystemIdEntity) entityList.get(0);
                    if (noarkSystemIdEntity != null && noarkSystemIdEntity.getVersion() != null) {
                        String version = noarkSystemIdEntity;
                        response.addHeader(ETAG, version);
                    } else {
                        logger.warn("Unable to add etag as entity is null for request [" +
                                request.getRequestURI() + "]");
                    }
                }
            }
        }
    }*/
}
