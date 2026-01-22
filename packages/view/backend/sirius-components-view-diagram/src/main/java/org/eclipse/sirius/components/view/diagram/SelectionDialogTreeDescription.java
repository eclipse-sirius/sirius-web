/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Selection Dialog Tree Description</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDialogTreeDescription#getElementsExpression <em>Elements Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDialogTreeDescription#getChildrenExpression <em>Children Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDialogTreeDescription#getIsSelectableExpression <em>Is Selectable Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogTreeDescription()
 * @model
 * @generated
 */
public interface SelectionDialogTreeDescription extends EObject {
    /**
	 * Returns the value of the '<em><b>Elements Expression</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Elements Expression</em>' attribute.
	 * @see #setElementsExpression(String)
	 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogTreeDescription_ElementsExpression()
	 * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression" required="true"
	 * @generated
	 */
    String getElementsExpression();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.SelectionDialogTreeDescription#getElementsExpression <em>Elements Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Elements Expression</em>' attribute.
	 * @see #getElementsExpression()
	 * @generated
	 */
    void setElementsExpression(String value);

    /**
	 * Returns the value of the '<em><b>Children Expression</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @return the value of the '<em>Children Expression</em>' attribute.
	 * @see #setChildrenExpression(String)
	 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogTreeDescription_ChildrenExpression()
	 * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
	 * @generated
	 */
    String getChildrenExpression();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.SelectionDialogTreeDescription#getChildrenExpression <em>Children Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Children Expression</em>' attribute.
	 * @see #getChildrenExpression()
	 * @generated
	 */
    void setChildrenExpression(String value);

    /**
	 * Returns the value of the '<em><b>Is Selectable Expression</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @return the value of the '<em>Is Selectable Expression</em>' attribute.
	 * @see #setIsSelectableExpression(String)
	 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogTreeDescription_IsSelectableExpression()
	 * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
	 * @generated
	 */
    String getIsSelectableExpression();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.SelectionDialogTreeDescription#getIsSelectableExpression <em>Is Selectable Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Selectable Expression</em>' attribute.
	 * @see #getIsSelectableExpression()
	 * @generated
	 */
    void setIsSelectableExpression(String value);

} // SelectionDialogTreeDescription
