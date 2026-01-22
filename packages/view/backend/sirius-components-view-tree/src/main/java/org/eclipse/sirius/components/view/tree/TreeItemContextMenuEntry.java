/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Item Context Menu Entry</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.tree.TreeItemContextMenuEntry#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.tree.TreeItemContextMenuEntry#getPreconditionExpression <em>Precondition Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.tree.TreePackage#getTreeItemContextMenuEntry()
 * @model abstract="true"
 * @generated
 */
public interface TreeItemContextMenuEntry extends EObject {
    /**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.sirius.components.view.tree.TreePackage#getTreeItemContextMenuEntry_Name()
	 * @model dataType="org.eclipse.sirius.components.view.Identifier" required="true"
	 * @generated
	 */
    String getName();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.tree.TreeItemContextMenuEntry#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
    void setName(String value);

    /**
	 * Returns the value of the '<em><b>Precondition Expression</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @return the value of the '<em>Precondition Expression</em>' attribute.
	 * @see #setPreconditionExpression(String)
	 * @see org.eclipse.sirius.components.view.tree.TreePackage#getTreeItemContextMenuEntry_PreconditionExpression()
	 * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
	 * @generated
	 */
    String getPreconditionExpression();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.tree.TreeItemContextMenuEntry#getPreconditionExpression <em>Precondition Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Precondition Expression</em>' attribute.
	 * @see #getPreconditionExpression()
	 * @generated
	 */
    void setPreconditionExpression(String value);

} // TreeItemContextMenuEntry
