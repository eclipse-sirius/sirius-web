/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.web.customwidgets.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.sirius.web.customwidgets.CustomwidgetsFactory;
import org.eclipse.sirius.web.customwidgets.CustomwidgetsPackage;
import org.eclipse.sirius.web.customwidgets.SliderDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class CustomwidgetsFactoryImpl extends EFactoryImpl implements CustomwidgetsFactory {
    /**
     * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static CustomwidgetsFactory init() {
        try {
            CustomwidgetsFactory theCustomwidgetsFactory = (CustomwidgetsFactory) EPackage.Registry.INSTANCE.getEFactory(CustomwidgetsPackage.eNS_URI);
            if (theCustomwidgetsFactory != null) {
                return theCustomwidgetsFactory;
            }
        } catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new CustomwidgetsFactoryImpl();
    }

    /**
     * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public CustomwidgetsFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case CustomwidgetsPackage.SLIDER_DESCRIPTION:
                return this.createSliderDescription();
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
    public SliderDescription createSliderDescription() {
        SliderDescriptionImpl sliderDescription = new SliderDescriptionImpl();
        return sliderDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CustomwidgetsPackage getCustomwidgetsPackage() {
        return (CustomwidgetsPackage) this.getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @deprecated
     * @generated
     */
    @Deprecated
    public static CustomwidgetsPackage getPackage() {
        return CustomwidgetsPackage.eINSTANCE;
    }

} // CustomwidgetsFactoryImpl
