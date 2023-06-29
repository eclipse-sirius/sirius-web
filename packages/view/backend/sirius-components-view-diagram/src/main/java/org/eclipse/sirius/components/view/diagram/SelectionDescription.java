/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.components.view.diagram;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Selection Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDescription#getSelectionCandidatesExpression
 * <em>Selection Candidates Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDescription#getSelectionMessage <em>Selection
 * Message</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDescription()
 * @model
 * @generated
 */
public interface SelectionDescription extends EObject {
    /**
     * Returns the value of the '<em><b>Selection Candidates Expression</b></em>' attribute. The default value is
     * <code>"aql:self"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Selection Candidates Expression</em>' attribute.
     * @see #setSelectionCandidatesExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDescription_SelectionCandidatesExpression()
     * @model default="aql:self" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getSelectionCandidatesExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.SelectionDescription#getSelectionCandidatesExpression
     * <em>Selection Candidates Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Selection Candidates Expression</em>' attribute.
     * @see #getSelectionCandidatesExpression()
     * @generated
     */
    void setSelectionCandidatesExpression(String value);

    /**
     * Returns the value of the '<em><b>Selection Message</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Selection Message</em>' attribute.
     * @see #setSelectionMessage(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDescription_SelectionMessage()
     * @model
     * @generated
     */
    String getSelectionMessage();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.SelectionDescription#getSelectionMessage
     * <em>Selection Message</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Selection Message</em>' attribute.
     * @see #getSelectionMessage()
     * @generated
     */
    void setSelectionMessage(String value);

} // SelectionDescription
