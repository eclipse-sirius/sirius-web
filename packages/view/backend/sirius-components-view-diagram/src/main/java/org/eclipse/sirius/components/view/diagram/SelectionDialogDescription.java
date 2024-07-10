/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Selection Dialog Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getSelectionCandidatesExpression
 * <em>Selection Candidates Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getSelectionMessage <em>Selection
 * Message</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#isDisplayedAsTree <em>Displayed As
 * Tree</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#isExpandedAtOpening <em>Expanded At
 * Opening</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogDescription()
 * @model
 * @generated
 */
public interface SelectionDialogDescription extends DialogDescription {
    /**
     * Returns the value of the '<em><b>Selection Candidates Expression</b></em>' attribute. The default value is
     * <code>"aql:self"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Selection Candidates Expression</em>' attribute.
     * @see #setSelectionCandidatesExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogDescription_SelectionCandidatesExpression()
     * @model default="aql:self" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getSelectionCandidatesExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getSelectionCandidatesExpression
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
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogDescription_SelectionMessage()
     * @model
     * @generated
     */
    String getSelectionMessage();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getSelectionMessage <em>Selection
     * Message</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Selection Message</em>' attribute.
     * @see #getSelectionMessage()
     * @generated
     */
    void setSelectionMessage(String value);

    /**
     * Returns the value of the '<em><b>Displayed As Tree</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Displayed As Tree</em>' attribute.
     * @see #setDisplayedAsTree(boolean)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogDescription_DisplayedAsTree()
     * @model
     * @generated
     */
    boolean isDisplayedAsTree();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#isDisplayedAsTree <em>Displayed As
     * Tree</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Displayed As Tree</em>' attribute.
     * @see #isDisplayedAsTree()
     * @generated
     */
    void setDisplayedAsTree(boolean value);

    /**
     * Returns the value of the '<em><b>Expanded At Opening</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Expanded At Opening</em>' attribute.
     * @see #setExpandedAtOpening(boolean)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogDescription_ExpandedAtOpening()
     * @model
     * @generated
     */
    boolean isExpandedAtOpening();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#isExpandedAtOpening <em>Expanded At
     * Opening</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Expanded At Opening</em>' attribute.
     * @see #isExpandedAtOpening()
     * @generated
     */
    void setExpandedAtOpening(boolean value);

} // SelectionDialogDescription
