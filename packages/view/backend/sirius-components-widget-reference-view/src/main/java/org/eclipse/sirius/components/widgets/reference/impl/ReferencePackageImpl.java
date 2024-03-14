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
package org.eclipse.sirius.components.widgets.reference.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.widgets.reference.ConditionalReferenceWidgetDescriptionStyle;
import org.eclipse.sirius.components.widgets.reference.ReferenceFactory;
import org.eclipse.sirius.components.widgets.reference.ReferencePackage;
import org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription;
import org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescriptionStyle;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class ReferencePackageImpl extends EPackageImpl implements ReferencePackage {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static boolean isInited = false;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass referenceWidgetDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass referenceWidgetDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalReferenceWidgetDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isCreated = false;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
     * EPackage.Registry} by the package package URI value.
     * <p>
     * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
     * performs initialization of the package, or returns the registered package, if one already exists. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see org.eclipse.sirius.components.widgets.reference.ReferencePackage#eNS_URI
     * @see #init()
     */
    private ReferencePackageImpl() {
        super(eNS_URI, ReferenceFactory.eINSTANCE);
    }

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     *
     * <p>
     * This method is used to initialize {@link ReferencePackage#eINSTANCE} when that field is accessed. Clients should
     * not invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static ReferencePackage init() {
        if (isInited)
            return (ReferencePackage) EPackage.Registry.INSTANCE.getEPackage(ReferencePackage.eNS_URI);

        // Obtain or create and register package
        Object registeredReferencePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
        ReferencePackageImpl theReferencePackage = registeredReferencePackage instanceof ReferencePackageImpl ? (ReferencePackageImpl) registeredReferencePackage : new ReferencePackageImpl();

        isInited = true;

        // Initialize simple dependencies
        FormPackage.eINSTANCE.eClass();
        ViewPackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theReferencePackage.createPackageContents();

        // Initialize created meta-data
        theReferencePackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theReferencePackage.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(ReferencePackage.eNS_URI, theReferencePackage);
        return theReferencePackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getReferenceWidgetDescription() {
        return this.referenceWidgetDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getReferenceWidgetDescription_ReferenceOwnerExpression() {
        return (EAttribute) this.referenceWidgetDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getReferenceWidgetDescription_ReferenceNameExpression() {
        return (EAttribute) this.referenceWidgetDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getReferenceWidgetDescription_Body() {
        return (EReference) this.referenceWidgetDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getReferenceWidgetDescription_Style() {
        return (EReference) this.referenceWidgetDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getReferenceWidgetDescription_ConditionalStyles() {
        return (EReference) this.referenceWidgetDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getReferenceWidgetDescriptionStyle() {
        return this.referenceWidgetDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getReferenceWidgetDescriptionStyle_Color() {
        return (EReference) this.referenceWidgetDescriptionStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalReferenceWidgetDescriptionStyle() {
        return this.conditionalReferenceWidgetDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getReferenceWidgetDescription_IsEnabledExpression() {
        return (EAttribute) this.referenceWidgetDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ReferenceFactory getReferenceFactory() {
        return (ReferenceFactory) this.getEFactoryInstance();
    }

    /**
     * Creates the meta-model objects for the package. This method is guarded to have no affect on any invocation but
     * its first. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public void createPackageContents() {
        if (this.isCreated)
            return;
        this.isCreated = true;

        // Create classes and their features
        this.referenceWidgetDescriptionEClass = this.createEClass(REFERENCE_WIDGET_DESCRIPTION);
        this.createEAttribute(this.referenceWidgetDescriptionEClass, REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION);
        this.createEAttribute(this.referenceWidgetDescriptionEClass, REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION);
        this.createEAttribute(this.referenceWidgetDescriptionEClass, REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION);
        this.createEReference(this.referenceWidgetDescriptionEClass, REFERENCE_WIDGET_DESCRIPTION__BODY);
        this.createEReference(this.referenceWidgetDescriptionEClass, REFERENCE_WIDGET_DESCRIPTION__STYLE);
        this.createEReference(this.referenceWidgetDescriptionEClass, REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES);

        this.referenceWidgetDescriptionStyleEClass = this.createEClass(REFERENCE_WIDGET_DESCRIPTION_STYLE);
        this.createEReference(this.referenceWidgetDescriptionStyleEClass, REFERENCE_WIDGET_DESCRIPTION_STYLE__COLOR);

        this.conditionalReferenceWidgetDescriptionStyleEClass = this.createEClass(CONDITIONAL_REFERENCE_WIDGET_DESCRIPTION_STYLE);
    }

    /**
     * Complete the initialization of the package and its meta-model. This method is guarded to have no affect on any
     * invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public void initializePackageContents() {
        if (this.isInitialized)
            return;
        this.isInitialized = true;

        // Initialize package
        this.setName(eNAME);
        this.setNsPrefix(eNS_PREFIX);
        this.setNsURI(eNS_URI);

        // Obtain other dependent packages
        FormPackage theFormPackage = (FormPackage) EPackage.Registry.INSTANCE.getEPackage(FormPackage.eNS_URI);
        ViewPackage theViewPackage = (ViewPackage) EPackage.Registry.INSTANCE.getEPackage(ViewPackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        this.referenceWidgetDescriptionEClass.getESuperTypes().add(theFormPackage.getWidgetDescription());
        this.referenceWidgetDescriptionStyleEClass.getESuperTypes().add(theFormPackage.getWidgetDescriptionStyle());
        this.referenceWidgetDescriptionStyleEClass.getESuperTypes().add(theViewPackage.getLabelStyle());
        this.conditionalReferenceWidgetDescriptionStyleEClass.getESuperTypes().add(theViewPackage.getConditional());
        this.conditionalReferenceWidgetDescriptionStyleEClass.getESuperTypes().add(this.getReferenceWidgetDescriptionStyle());

        // Initialize classes, features, and operations; add parameters
        this.initEClass(this.referenceWidgetDescriptionEClass, ReferenceWidgetDescription.class, "ReferenceWidgetDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getReferenceWidgetDescription_IsEnabledExpression(), theViewPackage.getInterpretedExpression(), "isEnabledExpression", null, 0, 1, ReferenceWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getReferenceWidgetDescription_ReferenceOwnerExpression(), theViewPackage.getInterpretedExpression(), "referenceOwnerExpression", null, 0, 1,
                ReferenceWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getReferenceWidgetDescription_ReferenceNameExpression(), theViewPackage.getInterpretedExpression(), "referenceNameExpression", null, 1, 1,
                ReferenceWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getReferenceWidgetDescription_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, ReferenceWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getReferenceWidgetDescription_Style(), this.getReferenceWidgetDescriptionStyle(), null, "style", null, 0, 1, ReferenceWidgetDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getReferenceWidgetDescription_ConditionalStyles(), this.getConditionalReferenceWidgetDescriptionStyle(), null, "conditionalStyles", null, 0, -1,
                ReferenceWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.referenceWidgetDescriptionStyleEClass, ReferenceWidgetDescriptionStyle.class, "ReferenceWidgetDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getReferenceWidgetDescriptionStyle_Color(), theViewPackage.getUserColor(), null, "color", null, 0, 1, ReferenceWidgetDescriptionStyle.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalReferenceWidgetDescriptionStyleEClass, ConditionalReferenceWidgetDescriptionStyle.class, "ConditionalReferenceWidgetDescriptionStyle", !IS_ABSTRACT,
                !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        this.createResource(eNS_URI);
    }

} // ReferencePackageImpl
