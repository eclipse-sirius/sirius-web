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
package org.eclipse.sirius.components.view.table.customcells.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.sirius.components.view.table.customcells.CellCheckboxWidgetDescription;
import org.eclipse.sirius.components.view.table.customcells.CustomcellsFactory;
import org.eclipse.sirius.components.view.table.customcells.CustomcellsPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class CustomcellsFactoryImpl extends EFactoryImpl implements CustomcellsFactory {

    /**
     * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public CustomcellsFactoryImpl() {
        super();
    }

    /**
     * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static CustomcellsFactory init() {
        try {
            CustomcellsFactory theCustomcellsFactory = (CustomcellsFactory) EPackage.Registry.INSTANCE.getEFactory(CustomcellsPackage.eNS_URI);
            if (theCustomcellsFactory != null) {
                return theCustomcellsFactory;
            }
        } catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new CustomcellsFactoryImpl();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case CustomcellsPackage.CELL_CHECKBOX_WIDGET_DESCRIPTION:
                return this.createCellCheckboxWidgetDescription();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CellCheckboxWidgetDescription createCellCheckboxWidgetDescription() {
        CellCheckboxWidgetDescriptionImpl cellCheckboxWidgetDescription = new CellCheckboxWidgetDescriptionImpl();
        return cellCheckboxWidgetDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CustomcellsPackage getCustomcellsPackage() {
        return (CustomcellsPackage) this.getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @deprecated
     */
    @Deprecated
    public static CustomcellsPackage getPackage() {
        return CustomcellsPackage.eINSTANCE;
    }

} // CustomcellsFactoryImpl
