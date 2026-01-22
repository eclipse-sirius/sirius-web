/*******************************************************************************
 * Copyright (c) 2021, 2026 Obeo.
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
 *   <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getSelectionMessage <em>Selection Message</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getSelectionDialogTreeDescription <em>Selection Dialog Tree Description</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#isMultiple <em>Multiple</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogDescription()
 * @model
 * @generated
 */
public interface SelectionDialogDescription extends DialogDescription {
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
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getSelectionMessage <em>Selection Message</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Selection Message</em>' attribute.
	 * @see #getSelectionMessage()
	 * @generated
	 */
    void setSelectionMessage(String value);

    /**
     * Returns the value of the '<em><b>Selection Dialog Tree Description</b></em>' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Selection Dialog Tree Description</em>' containment reference.
     * @see #setSelectionDialogTreeDescription(SelectionDialogTreeDescription)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogDescription_SelectionDialogTreeDescription()
     * @model containment="true"
     * @generated
     */
    SelectionDialogTreeDescription getSelectionDialogTreeDescription();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getSelectionDialogTreeDescription <em>Selection Dialog Tree Description</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Selection Dialog Tree Description</em>' containment reference.
	 * @see #getSelectionDialogTreeDescription()
	 * @generated
	 */
    void setSelectionDialogTreeDescription(SelectionDialogTreeDescription value);

    /**
	 * Returns the value of the '<em><b>Multiple</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Multiple</em>' attribute.
	 * @see #setMultiple(boolean)
	 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogDescription_Multiple()
	 * @model
	 * @generated
	 */
    boolean isMultiple();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#isMultiple <em>Multiple</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Multiple</em>' attribute.
	 * @see #isMultiple()
	 * @generated
	 */
    void setMultiple(boolean value);

} // SelectionDialogDescription
