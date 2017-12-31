/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the "License").  You may not use this file except
 * in compliance with the License.
 *
 * You can obtain a copy of the license at
 * https://jaxp.dev.java.net/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * HEADER in each file and include the License file at
 * https://jaxp.dev.java.net/CDDLv1.0.html
 * If applicable add the following below this CDDL HEADER
 * with the fields enclosed by brackets "[]" replaced with
 * your own identifying information: Portions Copyright
 * [year] [name of copyright owner]
 */

/*
 * $Id: XMLEntityReader.java,v 1.3 2005/11/03 17:02:21 jeffsuttor Exp $
 * @(#)ErrorListener.java	1.21 05/11/17
 *
 * Copyright 2006 Sun Microsystems, Inc. All Rights Reserved.
 */

package com.weedong.javax.xml.transform;

/**
 * <p>To provide customized error handling, implement this interface and
 * use the <code>setErrorListener</code> method to register an instance of the
 * implmentation with the {@link Transformer}.  The
 * <code>Transformer</code> then reports all errors and warnings through this
 * interface.</p>
 * <p>
 * <p>If an application does <em>not</em> register its own custom
 * <code>ErrorListener</code>, the default <code>ErrorListener</code>
 * is used which reports all warnings and errors to <code>System.err</code>
 * and does not throw any <code>Exception</code>s.
 * Applications are <em>strongly</em> encouraged to register and use
 * <code>ErrorListener</code>s that insure proper behavior for warnings and
 * errors.</p>
 * <p>
 * <p>For transformation errors, a <code>Transformer</code> must use this
 * interface instead of throwing an <code>Exception</code>: it is up to the
 * application to decide whether to throw an <code>Exception</code> for
 * different types of errors and warnings.  Note however that the
 * <code>Transformer</code> is not required to continue with the transformation
 * after a call to {@link #fatalError(TransformerException exception)}.</p>
 * <p>
 * <p><code>Transformer</code>s may use this mechanism to report XML parsing
 * errors as well as transformation errors.</p>
 */
public interface ErrorListener {

    /**
     * Receive notification of a warning.
     * <p>
     * <p>{@link Transformer} can use this method to report
     * conditions that are not errors or fatal errors.  The default behaviour
     * is to take no action.</p>
     * <p>
     * <p>After invoking this method, the Transformer must continue with
     * the transformation. It should still be possible for the
     * application to process the document through to the end.</p>
     *
     * @param exception The warning information encapsulated in a
     *                  transformer exception.
     * @throws com.weedong.javax.xml.transform.TransformerException if the application
     *                                                              chooses to discontinue the transformation.
     * @see com.weedong.javax.xml.transform.TransformerException
     */
    public abstract void warning(TransformerException exception)
            throws TransformerException;

    /**
     * Receive notification of a recoverable error.
     * <p>
     * <p>The transformer must continue to try and provide normal transformation
     * after invoking this method.  It should still be possible for the
     * application to process the document through to the end if no other errors
     * are encountered.</p>
     *
     * @param exception The error information encapsulated in a
     *                  transformer exception.
     * @throws com.weedong.javax.xml.transform.TransformerException if the application
     *                                                              chooses to discontinue the transformation.
     * @see com.weedong.javax.xml.transform.TransformerException
     */
    public abstract void error(TransformerException exception)
            throws TransformerException;

    /**
     * <p>Receive notification of a non-recoverable error.</p>
     * <p>
     * <p>The processor may choose to continue, but will not normally
     * proceed to a successful completion.</p>
     * <p>
     * <p>The method should throw an exception if it is unable to
     * process the error, or if it wishes execution to terminate
     * immediately. The processor will not necessarily honor this
     * request.</p>
     *
     * @param exception The error information encapsulated in a
     *                  <code>TransformerException</code>.
     * @throws com.weedong.javax.xml.transform.TransformerException if the application
     *                                                              chooses to discontinue the transformation.
     * @see com.weedong.javax.xml.transform.TransformerException
     */
    public abstract void fatalError(TransformerException exception)
            throws TransformerException;
}
