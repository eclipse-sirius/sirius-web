/*******************************************************************************
 * Copyright (c) 2023, 2023 Obeo.
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
package org.eclipse.sirius.web.customnodes.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.sirius.web.customnodes.CustomnodesFactory;
import org.eclipse.sirius.web.customnodes.CustomnodesPackage;
import org.eclipse.sirius.web.customnodes.EllipseNodeStyleDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class CustomnodesFactoryImpl extends EFactoryImpl implements CustomnodesFactory {

    /**
     * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public CustomnodesFactoryImpl() {
        super();
    }

    /**
     * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static CustomnodesFactory init() {
        try {
            CustomnodesFactory theCustomnodesFactory = (CustomnodesFactory) EPackage.Registry.INSTANCE.getEFactory(CustomnodesPackage.eNS_URI);
            if (theCustomnodesFactory != null) {
                return theCustomnodesFactory;
            }
        } catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new CustomnodesFactoryImpl();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @deprecated
     * @generated
     */
    @Deprecated
    public static CustomnodesPackage getPackage() {
        return CustomnodesPackage.eINSTANCE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case CustomnodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION:
                return this.createEllipseNodeStyleDescription();
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
    public EllipseNodeStyleDescription createEllipseNodeStyleDescription() {
        EllipseNodeStyleDescriptionImpl ellipseNodeStyleDescription = new EllipseNodeStyleDescriptionImpl();
        return ellipseNodeStyleDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CustomnodesPackage getCustomnodesPackage() {
        return (CustomnodesPackage) this.getEPackage();
    }

} // CustomnodesFactoryImpl
