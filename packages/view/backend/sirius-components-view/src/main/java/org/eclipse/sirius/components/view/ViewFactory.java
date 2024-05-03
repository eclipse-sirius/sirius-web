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
package org.eclipse.sirius.components.view;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract class of
 * the model. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.ViewPackage
 * @generated
 */
public interface ViewFactory extends EFactory {
    /**
     * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    ViewFactory eINSTANCE = org.eclipse.sirius.components.view.impl.ViewFactoryImpl.init();

    /**
     * Returns a new object of class '<em>View</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>View</em>'.
     * @generated
     */
    View createView();

    /**
     * Returns a new object of class '<em>Color Palette</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Color Palette</em>'.
     * @generated
     */
    ColorPalette createColorPalette();

    /**
     * Returns a new object of class '<em>Fixed Color</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Fixed Color</em>'.
     * @generated
     */
    FixedColor createFixedColor();

    /**
     * Returns a new object of class '<em>Change Context</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Change Context</em>'.
     * @generated
     */
    ChangeContext createChangeContext();

    /**
     * Returns a new object of class '<em>Create Instance</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Create Instance</em>'.
     * @generated
     */
    CreateInstance createCreateInstance();

    /**
     * Returns a new object of class '<em>Set Value</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Set Value</em>'.
     * @generated
     */
    SetValue createSetValue();

    /**
     * Returns a new object of class '<em>Unset Value</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Unset Value</em>'.
     * @generated
     */
    UnsetValue createUnsetValue();

    /**
     * Returns a new object of class '<em>Delete Element</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Delete Element</em>'.
     * @generated
     */
    DeleteElement createDeleteElement();

    /**
     * Returns a new object of class '<em>Let</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Let</em>'.
     * @generated
     */
    Let createLet();

    /**
     * Returns a new object of class '<em>If</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>If</em>'.
     * @generated
     */
    If createIf();

    /**
     * Returns a new object of class '<em>For</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>For</em>'.
     * @generated
     */
    For createFor();

    /**
     * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the package supported by this factory.
     * @generated
     */
    ViewPackage getViewPackage();

} // ViewFactory
