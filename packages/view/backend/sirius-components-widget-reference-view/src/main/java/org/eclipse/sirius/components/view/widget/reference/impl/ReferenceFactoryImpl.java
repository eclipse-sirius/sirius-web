/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.view.widget.reference.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.sirius.components.view.widget.reference.ConditionalReferenceWidgetDescriptionStyle;
import org.eclipse.sirius.components.view.widget.reference.ReferenceFactory;
import org.eclipse.sirius.components.view.widget.reference.ReferencePackage;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescriptionStyle;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class ReferenceFactoryImpl extends EFactoryImpl implements ReferenceFactory {
    /**
     * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static ReferenceFactory init() {
        try {
            ReferenceFactory theReferenceFactory = (ReferenceFactory) EPackage.Registry.INSTANCE.getEFactory(ReferencePackage.eNS_URI);
            if (theReferenceFactory != null) {
                return theReferenceFactory;
            }
        } catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new ReferenceFactoryImpl();
    }

    /**
     * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ReferenceFactoryImpl() {
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
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION:
                return this.createReferenceWidgetDescription();
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION_STYLE:
                return this.createReferenceWidgetDescriptionStyle();
            case ReferencePackage.CONDITIONAL_REFERENCE_WIDGET_DESCRIPTION_STYLE:
                return this.createConditionalReferenceWidgetDescriptionStyle();
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
    public ReferenceWidgetDescription createReferenceWidgetDescription() {
        ReferenceWidgetDescriptionImpl referenceWidgetDescription = new ReferenceWidgetDescriptionImpl();
        return referenceWidgetDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ReferenceWidgetDescriptionStyle createReferenceWidgetDescriptionStyle() {
        ReferenceWidgetDescriptionStyleImpl referenceWidgetDescriptionStyle = new ReferenceWidgetDescriptionStyleImpl();
        return referenceWidgetDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ConditionalReferenceWidgetDescriptionStyle createConditionalReferenceWidgetDescriptionStyle() {
        ConditionalReferenceWidgetDescriptionStyleImpl conditionalReferenceWidgetDescriptionStyle = new ConditionalReferenceWidgetDescriptionStyleImpl();
        return conditionalReferenceWidgetDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ReferencePackage getReferencePackage() {
        return (ReferencePackage) this.getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @deprecated
     * @generated
     */
    @Deprecated
    public static ReferencePackage getPackage() {
        return ReferencePackage.eINSTANCE;
    }

} // ReferenceFactoryImpl
