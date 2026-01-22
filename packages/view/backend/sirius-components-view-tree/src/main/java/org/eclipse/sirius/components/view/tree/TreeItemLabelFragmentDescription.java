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
package org.eclipse.sirius.components.view.tree;

import org.eclipse.sirius.components.view.TextStyleDescription;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Item Label Fragment Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.tree.TreeItemLabelFragmentDescription#getLabelExpression <em>Label Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.tree.TreeItemLabelFragmentDescription#getStyle <em>Style</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.tree.TreePackage#getTreeItemLabelFragmentDescription()
 * @model
 * @generated
 */
public interface TreeItemLabelFragmentDescription extends TreeItemLabelElementDescription {
    /**
     * Returns the value of the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Label Expression</em>' attribute.
     * @see #setLabelExpression(String)
     * @see org.eclipse.sirius.components.view.tree.TreePackage#getTreeItemLabelFragmentDescription_LabelExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getLabelExpression();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.tree.TreeItemLabelFragmentDescription#getLabelExpression <em>Label Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label Expression</em>' attribute.
	 * @see #getLabelExpression()
	 * @generated
	 */
    void setLabelExpression(String value);

    /**
	 * Returns the value of the '<em><b>Style</b></em>' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Style</em>' reference.
	 * @see #setStyle(TextStyleDescription)
	 * @see org.eclipse.sirius.components.view.tree.TreePackage#getTreeItemLabelFragmentDescription_Style()
	 * @model
	 * @generated
	 */
    TextStyleDescription getStyle();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.tree.TreeItemLabelFragmentDescription#getStyle <em>Style</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Style</em>' reference.
	 * @see #getStyle()
	 * @generated
	 */
    void setStyle(TextStyleDescription value);

} // TreeItemLabelFragmentDescription
