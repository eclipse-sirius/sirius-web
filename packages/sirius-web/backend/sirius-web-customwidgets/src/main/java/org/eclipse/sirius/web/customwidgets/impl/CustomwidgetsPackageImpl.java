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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.web.customwidgets.CustomwidgetsFactory;
import org.eclipse.sirius.web.customwidgets.CustomwidgetsPackage;
import org.eclipse.sirius.web.customwidgets.SliderDescription;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CustomwidgetsPackageImpl extends EPackageImpl implements CustomwidgetsPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass sliderDescriptionEClass = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
     * package URI value.
     * <p>Note: the correct way to create the package is via the static
     * factory method {@link #init init()}, which also performs
     * initialization of the package, or returns the registered package,
     * if one already exists.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see org.eclipse.sirius.web.customwidgets.CustomwidgetsPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private CustomwidgetsPackageImpl() {
        super(eNS_URI, CustomwidgetsFactory.eINSTANCE);
    }
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     *
     * <p>This method is used to initialize {@link CustomwidgetsPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static CustomwidgetsPackage init() {
        if (isInited) return (CustomwidgetsPackage)EPackage.Registry.INSTANCE.getEPackage(CustomwidgetsPackage.eNS_URI);

        // Obtain or create and register package
        Object registeredCustomwidgetsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
        CustomwidgetsPackageImpl theCustomwidgetsPackage = registeredCustomwidgetsPackage instanceof CustomwidgetsPackageImpl ? (CustomwidgetsPackageImpl)registeredCustomwidgetsPackage : new CustomwidgetsPackageImpl();

        isInited = true;

        // Initialize simple dependencies
        ViewPackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theCustomwidgetsPackage.createPackageContents();

        // Initialize created meta-data
        theCustomwidgetsPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theCustomwidgetsPackage.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(CustomwidgetsPackage.eNS_URI, theCustomwidgetsPackage);
        return theCustomwidgetsPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EClass getSliderDescription() {
        return this.sliderDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getSliderDescription_MinValueExpression() {
        return (EAttribute)this.sliderDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getSliderDescription_MaxValueExpression() {
        return (EAttribute)this.sliderDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EAttribute getSliderDescription_CurrentValueExpression() {
        return (EAttribute)this.sliderDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EReference getSliderDescription_Body() {
        return (EReference)this.sliderDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public CustomwidgetsFactory getCustomwidgetsFactory() {
        return (CustomwidgetsFactory)this.getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package.  This method is
     * guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void createPackageContents() {
        if (this.isCreated) return;
        this.isCreated = true;

        // Create classes and their features
        this.sliderDescriptionEClass = this.createEClass(SLIDER_DESCRIPTION);
        this.createEAttribute(this.sliderDescriptionEClass, SLIDER_DESCRIPTION__MIN_VALUE_EXPRESSION);
        this.createEAttribute(this.sliderDescriptionEClass, SLIDER_DESCRIPTION__MAX_VALUE_EXPRESSION);
        this.createEAttribute(this.sliderDescriptionEClass, SLIDER_DESCRIPTION__CURRENT_VALUE_EXPRESSION);
        this.createEReference(this.sliderDescriptionEClass, SLIDER_DESCRIPTION__BODY);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model.  This
     * method is guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void initializePackageContents() {
        if (this.isInitialized) return;
        this.isInitialized = true;

        // Initialize package
        this.setName(eNAME);
        this.setNsPrefix(eNS_PREFIX);
        this.setNsURI(eNS_URI);

        // Obtain other dependent packages
        ViewPackage theViewPackage = (ViewPackage)EPackage.Registry.INSTANCE.getEPackage(ViewPackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        this.sliderDescriptionEClass.getESuperTypes().add(theViewPackage.getWidgetDescription());

        // Initialize classes, features, and operations; add parameters
        this.initEClass(this.sliderDescriptionEClass, SliderDescription.class, "SliderDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getSliderDescription_MinValueExpression(), theViewPackage.getInterpretedExpression(), "minValueExpression", null, 1, 1, SliderDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getSliderDescription_MaxValueExpression(), theViewPackage.getInterpretedExpression(), "maxValueExpression", null, 1, 1, SliderDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getSliderDescription_CurrentValueExpression(), theViewPackage.getInterpretedExpression(), "currentValueExpression", null, 1, 1, SliderDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getSliderDescription_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, SliderDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Create resource
        this.createResource(eNS_URI);
    }

} //CustomwidgetsPackageImpl
