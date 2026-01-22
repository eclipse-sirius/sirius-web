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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Custom Tree Item Context Menu Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.tree.CustomTreeItemContextMenuEntry#getContributionId <em>Contribution Id</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.tree.CustomTreeItemContextMenuEntry#isWithImpactAnalysis <em>With Impact Analysis</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.tree.TreePackage#getCustomTreeItemContextMenuEntry()
 * @model
 * @generated
 */
public interface CustomTreeItemContextMenuEntry extends TreeItemContextMenuEntry {
    /**
     * Returns the value of the '<em><b>Contribution Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Contribution Id</em>' attribute.
     * @see #setContributionId(String)
     * @see org.eclipse.sirius.components.view.tree.TreePackage#getCustomTreeItemContextMenuEntry_ContributionId()
     * @model dataType="org.eclipse.sirius.components.view.Identifier"
     * @generated
     */
    String getContributionId();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.tree.CustomTreeItemContextMenuEntry#getContributionId <em>Contribution Id</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Contribution Id</em>' attribute.
	 * @see #getContributionId()
	 * @generated
	 */
    void setContributionId(String value);

    /**
	 * Returns the value of the '<em><b>With Impact Analysis</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @return the value of the '<em>With Impact Analysis</em>' attribute.
	 * @see #setWithImpactAnalysis(boolean)
	 * @see org.eclipse.sirius.components.view.tree.TreePackage#getCustomTreeItemContextMenuEntry_WithImpactAnalysis()
	 * @model
	 * @generated
	 */
    boolean isWithImpactAnalysis();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.tree.CustomTreeItemContextMenuEntry#isWithImpactAnalysis <em>With Impact Analysis</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>With Impact Analysis</em>' attribute.
	 * @see #isWithImpactAnalysis()
	 * @generated
	 */
    void setWithImpactAnalysis(boolean value);

} // CustomTreeItemContextMenuEntry
