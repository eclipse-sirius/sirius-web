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

import org.eclipse.emf.common.util.EList;
import org.eclipse.sirius.components.view.Operation;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Single Click Tree Item Context Menu
 * Entry</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry#getBody <em>Body</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry#getLabelExpression <em>Label Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry#getIconURLExpression <em>Icon URL Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry#isWithImpactAnalysis <em>With Impact Analysis</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.tree.TreePackage#getSingleClickTreeItemContextMenuEntry()
 * @model
 * @generated
 */
public interface SingleClickTreeItemContextMenuEntry extends TreeItemContextMenuEntry {

    /**
	 * Returns the value of the '<em><b>Body</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.sirius.components.view.Operation}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Body</em>' containment reference list.
	 * @see org.eclipse.sirius.components.view.tree.TreePackage#getSingleClickTreeItemContextMenuEntry_Body()
	 * @model containment="true"
	 * @generated
	 */
    EList<Operation> getBody();

    /**
     * Returns the value of the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Label Expression</em>' attribute.
     * @see #setLabelExpression(String)
     * @see org.eclipse.sirius.components.view.tree.TreePackage#getSingleClickTreeItemContextMenuEntry_LabelExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getLabelExpression();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry#getLabelExpression <em>Label Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label Expression</em>' attribute.
	 * @see #getLabelExpression()
	 * @generated
	 */
    void setLabelExpression(String value);

    /**
	 * Returns the value of the '<em><b>Icon URL Expression</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @return the value of the '<em>Icon URL Expression</em>' attribute.
	 * @see #setIconURLExpression(String)
	 * @see org.eclipse.sirius.components.view.tree.TreePackage#getSingleClickTreeItemContextMenuEntry_IconURLExpression()
	 * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
	 * @generated
	 */
    String getIconURLExpression();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry#getIconURLExpression <em>Icon URL Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Icon URL Expression</em>' attribute.
	 * @see #getIconURLExpression()
	 * @generated
	 */
    void setIconURLExpression(String value);

    /**
	 * Returns the value of the '<em><b>With Impact Analysis</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @return the value of the '<em>With Impact Analysis</em>' attribute.
	 * @see #setWithImpactAnalysis(boolean)
	 * @see org.eclipse.sirius.components.view.tree.TreePackage#getSingleClickTreeItemContextMenuEntry_WithImpactAnalysis()
	 * @model
	 * @generated
	 */
    boolean isWithImpactAnalysis();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry#isWithImpactAnalysis <em>With Impact Analysis</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>With Impact Analysis</em>' attribute.
	 * @see #isWithImpactAnalysis()
	 * @generated
	 */
    void setWithImpactAnalysis(boolean value);

} // SingleClickTreeItemContextMenuEntry
