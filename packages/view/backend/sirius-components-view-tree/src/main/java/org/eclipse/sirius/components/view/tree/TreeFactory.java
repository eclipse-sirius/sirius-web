/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract class of
 * the model. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.tree.TreePackage
 * @generated
 */
public interface TreeFactory extends EFactory {

    /**
     * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    TreeFactory eINSTANCE = org.eclipse.sirius.components.view.tree.impl.TreeFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Description</em>'.
     * @generated
     */
    TreeDescription createTreeDescription();

    /**
     * Returns a new object of class '<em>Item Label Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Item Label Description</em>'.
     * @generated
     */
    TreeItemLabelDescription createTreeItemLabelDescription();

    /**
     * Returns a new object of class '<em>If Tree Item Label Element Description</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>If Tree Item Label Element Description</em>'.
     * @generated
     */
    IfTreeItemLabelElementDescription createIfTreeItemLabelElementDescription();

    /**
     * Returns a new object of class '<em>For Tree Item Label Element Description</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>For Tree Item Label Element Description</em>'.
     * @generated
     */
    ForTreeItemLabelElementDescription createForTreeItemLabelElementDescription();

    /**
     * Returns a new object of class '<em>Item Label Fragment Description</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Item Label Fragment Description</em>'.
     * @generated
     */
    TreeItemLabelFragmentDescription createTreeItemLabelFragmentDescription();

    /**
     * Returns a new object of class '<em>Single Click Tree Item Context Menu Entry</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Single Click Tree Item Context Menu Entry</em>'.
     * @generated
     */
    SingleClickTreeItemContextMenuEntry createSingleClickTreeItemContextMenuEntry();

    /**
     * Returns a new object of class '<em>Fetch Tree Item Context Menu Entry</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Fetch Tree Item Context Menu Entry</em>'.
     * @generated
     */
    FetchTreeItemContextMenuEntry createFetchTreeItemContextMenuEntry();

    /**
     * Returns a new object of class '<em>Custom Tree Item Context Menu Entry</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Custom Tree Item Context Menu Entry</em>'.
     * @generated
     */
    CustomTreeItemContextMenuEntry createCustomTreeItemContextMenuEntry();

    /**
     * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the package supported by this factory.
     * @generated
     */
    TreePackage getTreePackage();

} // TreeFactory
