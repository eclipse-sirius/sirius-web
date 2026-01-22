/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>If Tree Item Label Element
 * Description</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.tree.IfTreeItemLabelElementDescription#getPredicateExpression <em>Predicate Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.tree.IfTreeItemLabelElementDescription#getChildren <em>Children</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.tree.TreePackage#getIfTreeItemLabelElementDescription()
 * @model
 * @generated
 */
public interface IfTreeItemLabelElementDescription extends TreeItemLabelElementDescription {

    /**
	 * Returns the value of the '<em><b>Predicate Expression</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @return the value of the '<em>Predicate Expression</em>' attribute.
	 * @see #setPredicateExpression(String)
	 * @see org.eclipse.sirius.components.view.tree.TreePackage#getIfTreeItemLabelElementDescription_PredicateExpression()
	 * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
	 * @generated
	 */
    String getPredicateExpression();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.tree.IfTreeItemLabelElementDescription#getPredicateExpression <em>Predicate Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Predicate Expression</em>' attribute.
	 * @see #getPredicateExpression()
	 * @generated
	 */
    void setPredicateExpression(String value);

    /**
	 * Returns the value of the '<em><b>Children</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.sirius.components.view.tree.TreeItemLabelElementDescription}.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @return the value of the '<em>Children</em>' containment reference list.
	 * @see org.eclipse.sirius.components.view.tree.TreePackage#getIfTreeItemLabelElementDescription_Children()
	 * @model containment="true" required="true"
	 * @generated
	 */
    EList<TreeItemLabelElementDescription> getChildren();

} // IfTreeItemLabelElementDescription
