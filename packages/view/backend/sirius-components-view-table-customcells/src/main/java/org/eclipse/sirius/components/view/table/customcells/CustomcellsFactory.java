/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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
package org.eclipse.sirius.components.view.table.customcells;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract class of
 * the model. <!-- end-user-doc -->
 *
 * @generated
 * @see org.eclipse.sirius.components.view.table.customcells.CustomcellsPackage
 */
public interface CustomcellsFactory extends EFactory {

    /**
     * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    CustomcellsFactory eINSTANCE = org.eclipse.sirius.components.view.table.customcells.impl.CustomcellsFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Cell Checkbox Widget Description</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Cell Checkbox Widget Description</em>'.
     * @generated
     */
    CellCheckboxWidgetDescription createCellCheckboxWidgetDescription();

    /**
     * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the package supported by this factory.
     * @generated
     */
    CustomcellsPackage getCustomcellsPackage();

} // CustomcellsFactory
