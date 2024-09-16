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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Item Label Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.tree.TreeItemLabelDescription#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.TreeItemLabelDescription#getPreconditionExpression
 * <em>Precondition Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.TreeItemLabelDescription#getChildren <em>Children</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.tree.TreePackage#getTreeItemLabelDescription()
 * @model
 * @generated
 */
public interface TreeItemLabelDescription extends EObject {

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.eclipse.sirius.components.view.tree.TreePackage#getTreeItemLabelDescription_Name()
     * @model dataType="org.eclipse.sirius.components.view.Identifier"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.tree.TreeItemLabelDescription#getName
     * <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Precondition Expression</em>' attribute.
     * @see #setPreconditionExpression(String)
     * @see org.eclipse.sirius.components.view.tree.TreePackage#getTreeItemLabelDescription_PreconditionExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getPreconditionExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.tree.TreeItemLabelDescription#getPreconditionExpression
     * <em>Precondition Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Precondition Expression</em>' attribute.
     * @see #getPreconditionExpression()
     * @generated
     */
    void setPreconditionExpression(String value);

    /**
     * Returns the value of the '<em><b>Children</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.tree.TreeItemLabelElementDescription}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Children</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.tree.TreePackage#getTreeItemLabelDescription_Children()
     * @model containment="true"
     * @generated
     */
    EList<TreeItemLabelElementDescription> getChildren();

} // TreeItemLabelDescription
