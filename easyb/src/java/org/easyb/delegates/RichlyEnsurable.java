package org.easyb.delegates;

/**
 * this is essentially a marker interface
 *
 * @author aglover
 */
public interface RichlyEnsurable {
    /**
     * ideally, flexible delegates can do interesting things to
     * the object passed in....
     */
    void setVerified(Object verified);
}
