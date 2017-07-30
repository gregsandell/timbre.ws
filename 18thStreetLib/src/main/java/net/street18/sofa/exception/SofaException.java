package net.street18.sofa.exception;
/**
 * Custom exception class for SSDar.  Exceptions allow multiple kinds of descriptive
 * text (e.g. suitable for programmer, suitable for end user).   This is the general
 * parent class exception; subclasses should be used to specify certain types of
 * exceptions.
 * <p>These exceptions can be thrown anywhere, but the main expectation is that
 * they will be thrown during a call to a child of class SSdarAction class, caught in
 * SSdarAction, then routed to an exception-reporting URI such as /ssdarException.jsf.
 * @author Greg Sandell, Roundarch, Inc 2007-01-02
 * @see net.street18.sofa.struts.SofaAction
 */
public class SofaException extends Exception {
  /** Description suitable for programmer to read */
  public String userDesc;
  /** Description suitable for end user to read */
  public String codeDesc;
  /**
   * Create exception with multiple descriptions.
   * @param codeDesc
   * @param userDesc
   */
  public SofaException(String codeDesc, String userDesc) {
    super(codeDesc);
    this.userDesc = userDesc;
    this.codeDesc = codeDesc;
  }
  /**
   * Create exception with one description.  Useful for when an exception is never
   * expected to occur in production, or for quick programming when a good sounding
   * description is not handy.
   * @param oneDesc
   */
  public SofaException(String oneDesc) {
    super(oneDesc);
    this.userDesc = oneDesc;
    this.codeDesc = oneDesc;
  }
}
