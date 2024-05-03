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
package org.eclipse.sirius.components.view.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.Conditional;
import org.eclipse.sirius.components.view.CreateInstance;
import org.eclipse.sirius.components.view.DeleteElement;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.view.For;
import org.eclipse.sirius.components.view.If;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.Let;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.SetValue;
import org.eclipse.sirius.components.view.UnsetValue;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class ViewPackageImpl extends EPackageImpl implements ViewPackage {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass viewEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass colorPaletteEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass fixedColorEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass userColorEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass representationDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass labelStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass operationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass changeContextEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass createInstanceEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass setValueEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass unsetValueEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass deleteElementEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass letEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass ifEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass forEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EDataType identifierEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EDataType interpretedExpressionEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EDataType domainTypeEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EDataType colorEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EDataType lengthEDataType = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
     * EPackage.Registry} by the package package URI value.
     * <p>
     * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
     * performs initialization of the package, or returns the registered package, if one already exists. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see org.eclipse.sirius.components.view.ViewPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private ViewPackageImpl() {
        super(eNS_URI, ViewFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     *
     * <p>
     * This method is used to initialize {@link ViewPackage#eINSTANCE} when that field is accessed. Clients should not
     * invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static ViewPackage init() {
        if (isInited)
            return (ViewPackage) EPackage.Registry.INSTANCE.getEPackage(ViewPackage.eNS_URI);

        // Obtain or create and register package
        Object registeredViewPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
        ViewPackageImpl theViewPackage = registeredViewPackage instanceof ViewPackageImpl ? (ViewPackageImpl) registeredViewPackage : new ViewPackageImpl();

        isInited = true;

        // Create package meta-data objects
        theViewPackage.createPackageContents();

        // Initialize created meta-data
        theViewPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theViewPackage.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(ViewPackage.eNS_URI, theViewPackage);
        return theViewPackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getView() {
        return this.viewEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getView_Descriptions() {
        return (EReference) this.viewEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getView_ColorPalettes() {
        return (EReference) this.viewEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getColorPalette() {
        return this.colorPaletteEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getColorPalette_Name() {
        return (EAttribute) this.colorPaletteEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getColorPalette_Colors() {
        return (EReference) this.colorPaletteEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getFixedColor() {
        return this.fixedColorEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getFixedColor_Value() {
        return (EAttribute) this.fixedColorEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getUserColor() {
        return this.userColorEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getUserColor_Name() {
        return (EAttribute) this.userColorEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getRepresentationDescription() {
        return this.representationDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getRepresentationDescription_Name() {
        return (EAttribute) this.representationDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getRepresentationDescription_DomainType() {
        return (EAttribute) this.representationDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getRepresentationDescription_PreconditionExpression() {
        return (EAttribute) this.representationDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getRepresentationDescription_TitleExpression() {
        return (EAttribute) this.representationDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getLabelStyle() {
        return this.labelStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getLabelStyle_FontSize() {
        return (EAttribute) this.labelStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getLabelStyle_Italic() {
        return (EAttribute) this.labelStyleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getLabelStyle_Bold() {
        return (EAttribute) this.labelStyleEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getLabelStyle_Underline() {
        return (EAttribute) this.labelStyleEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getLabelStyle_StrikeThrough() {
        return (EAttribute) this.labelStyleEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getOperation() {
        return this.operationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getOperation_Children() {
        return (EReference) this.operationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getChangeContext() {
        return this.changeContextEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getChangeContext_Expression() {
        return (EAttribute) this.changeContextEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getCreateInstance() {
        return this.createInstanceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getCreateInstance_TypeName() {
        return (EAttribute) this.createInstanceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getCreateInstance_ReferenceName() {
        return (EAttribute) this.createInstanceEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getCreateInstance_VariableName() {
        return (EAttribute) this.createInstanceEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getSetValue() {
        return this.setValueEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getSetValue_FeatureName() {
        return (EAttribute) this.setValueEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getSetValue_ValueExpression() {
        return (EAttribute) this.setValueEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getUnsetValue() {
        return this.unsetValueEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getUnsetValue_FeatureName() {
        return (EAttribute) this.unsetValueEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getUnsetValue_ElementExpression() {
        return (EAttribute) this.unsetValueEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDeleteElement() {
        return this.deleteElementEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getLet() {
        return this.letEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getLet_VariableName() {
        return (EAttribute) this.letEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getLet_ValueExpression() {
        return (EAttribute) this.letEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getIf() {
        return this.ifEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getIf_ConditionExpression() {
        return (EAttribute) this.ifEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditional() {
        return this.conditionalEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getConditional_Condition() {
        return (EAttribute) this.conditionalEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getFor() {
        return this.forEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getFor_Expression() {
        return (EAttribute) this.forEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getFor_IteratorName() {
        return (EAttribute) this.forEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EDataType getIdentifier() {
        return this.identifierEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EDataType getInterpretedExpression() {
        return this.interpretedExpressionEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EDataType getDomainType() {
        return this.domainTypeEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EDataType getColor() {
        return this.colorEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EDataType getLength() {
        return this.lengthEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ViewFactory getViewFactory() {
        return (ViewFactory) this.getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isCreated = false;

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
        this.viewEClass = this.createEClass(VIEW);
        this.createEReference(this.viewEClass, VIEW__DESCRIPTIONS);
        this.createEReference(this.viewEClass, VIEW__COLOR_PALETTES);

        this.colorPaletteEClass = this.createEClass(COLOR_PALETTE);
        this.createEAttribute(this.colorPaletteEClass, COLOR_PALETTE__NAME);
        this.createEReference(this.colorPaletteEClass, COLOR_PALETTE__COLORS);

        this.fixedColorEClass = this.createEClass(FIXED_COLOR);
        this.createEAttribute(this.fixedColorEClass, FIXED_COLOR__VALUE);

        this.userColorEClass = this.createEClass(USER_COLOR);
        this.createEAttribute(this.userColorEClass, USER_COLOR__NAME);

        this.representationDescriptionEClass = this.createEClass(REPRESENTATION_DESCRIPTION);
        this.createEAttribute(this.representationDescriptionEClass, REPRESENTATION_DESCRIPTION__NAME);
        this.createEAttribute(this.representationDescriptionEClass, REPRESENTATION_DESCRIPTION__DOMAIN_TYPE);
        this.createEAttribute(this.representationDescriptionEClass, REPRESENTATION_DESCRIPTION__PRECONDITION_EXPRESSION);
        this.createEAttribute(this.representationDescriptionEClass, REPRESENTATION_DESCRIPTION__TITLE_EXPRESSION);

        this.labelStyleEClass = this.createEClass(LABEL_STYLE);
        this.createEAttribute(this.labelStyleEClass, LABEL_STYLE__FONT_SIZE);
        this.createEAttribute(this.labelStyleEClass, LABEL_STYLE__ITALIC);
        this.createEAttribute(this.labelStyleEClass, LABEL_STYLE__BOLD);
        this.createEAttribute(this.labelStyleEClass, LABEL_STYLE__UNDERLINE);
        this.createEAttribute(this.labelStyleEClass, LABEL_STYLE__STRIKE_THROUGH);

        this.operationEClass = this.createEClass(OPERATION);
        this.createEReference(this.operationEClass, OPERATION__CHILDREN);

        this.changeContextEClass = this.createEClass(CHANGE_CONTEXT);
        this.createEAttribute(this.changeContextEClass, CHANGE_CONTEXT__EXPRESSION);

        this.createInstanceEClass = this.createEClass(CREATE_INSTANCE);
        this.createEAttribute(this.createInstanceEClass, CREATE_INSTANCE__TYPE_NAME);
        this.createEAttribute(this.createInstanceEClass, CREATE_INSTANCE__REFERENCE_NAME);
        this.createEAttribute(this.createInstanceEClass, CREATE_INSTANCE__VARIABLE_NAME);

        this.setValueEClass = this.createEClass(SET_VALUE);
        this.createEAttribute(this.setValueEClass, SET_VALUE__FEATURE_NAME);
        this.createEAttribute(this.setValueEClass, SET_VALUE__VALUE_EXPRESSION);

        this.unsetValueEClass = this.createEClass(UNSET_VALUE);
        this.createEAttribute(this.unsetValueEClass, UNSET_VALUE__FEATURE_NAME);
        this.createEAttribute(this.unsetValueEClass, UNSET_VALUE__ELEMENT_EXPRESSION);

        this.deleteElementEClass = this.createEClass(DELETE_ELEMENT);

        this.letEClass = this.createEClass(LET);
        this.createEAttribute(this.letEClass, LET__VARIABLE_NAME);
        this.createEAttribute(this.letEClass, LET__VALUE_EXPRESSION);

        this.ifEClass = this.createEClass(IF);
        this.createEAttribute(this.ifEClass, IF__CONDITION_EXPRESSION);

        this.conditionalEClass = this.createEClass(CONDITIONAL);
        this.createEAttribute(this.conditionalEClass, CONDITIONAL__CONDITION);

        this.forEClass = this.createEClass(FOR);
        this.createEAttribute(this.forEClass, FOR__EXPRESSION);
        this.createEAttribute(this.forEClass, FOR__ITERATOR_NAME);

        // Create data types
        this.identifierEDataType = this.createEDataType(IDENTIFIER);
        this.interpretedExpressionEDataType = this.createEDataType(INTERPRETED_EXPRESSION);
        this.domainTypeEDataType = this.createEDataType(DOMAIN_TYPE);
        this.colorEDataType = this.createEDataType(COLOR);
        this.lengthEDataType = this.createEDataType(LENGTH);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isInitialized = false;

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

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        this.fixedColorEClass.getESuperTypes().add(this.getUserColor());
        this.changeContextEClass.getESuperTypes().add(this.getOperation());
        this.createInstanceEClass.getESuperTypes().add(this.getOperation());
        this.setValueEClass.getESuperTypes().add(this.getOperation());
        this.unsetValueEClass.getESuperTypes().add(this.getOperation());
        this.deleteElementEClass.getESuperTypes().add(this.getOperation());
        this.letEClass.getESuperTypes().add(this.getOperation());
        this.ifEClass.getESuperTypes().add(this.getOperation());
        this.forEClass.getESuperTypes().add(this.getOperation());

        // Initialize classes, features, and operations; add parameters
        this.initEClass(this.viewEClass, View.class, "View", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getView_Descriptions(), this.getRepresentationDescription(), null, "descriptions", null, 0, -1, View.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getView_ColorPalettes(), this.getColorPalette(), null, "colorPalettes", null, 0, -1, View.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.colorPaletteEClass, ColorPalette.class, "ColorPalette", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getColorPalette_Name(), this.getIdentifier(), "name", null, 1, 1, ColorPalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getColorPalette_Colors(), this.getUserColor(), null, "colors", null, 0, -1, ColorPalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.fixedColorEClass, FixedColor.class, "FixedColor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getFixedColor_Value(), this.getColor(), "value", null, 1, 1, FixedColor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.userColorEClass, UserColor.class, "UserColor", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getUserColor_Name(), this.getIdentifier(), "name", null, 0, 1, UserColor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.representationDescriptionEClass, RepresentationDescription.class, "RepresentationDescription", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getRepresentationDescription_Name(), this.getIdentifier(), "name", "NewRepresentationDescription", 0, 1, RepresentationDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getRepresentationDescription_DomainType(), this.getDomainType(), "domainType", "", 0, 1, RepresentationDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getRepresentationDescription_PreconditionExpression(), this.getInterpretedExpression(), "preconditionExpression", "", 0, 1, RepresentationDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getRepresentationDescription_TitleExpression(), this.getInterpretedExpression(), "titleExpression", "aql:\'New Representation\'", 0, 1,
                RepresentationDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.labelStyleEClass, LabelStyle.class, "LabelStyle", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getLabelStyle_FontSize(), this.getLength(), "fontSize", "14", 1, 1, LabelStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getLabelStyle_Italic(), this.ecorePackage.getEBoolean(), "italic", "false", 1, 1, LabelStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getLabelStyle_Bold(), this.ecorePackage.getEBoolean(), "bold", "false", 1, 1, LabelStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getLabelStyle_Underline(), this.ecorePackage.getEBoolean(), "underline", "false", 1, 1, LabelStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getLabelStyle_StrikeThrough(), this.ecorePackage.getEBoolean(), "strikeThrough", "false", 1, 1, LabelStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.operationEClass, Operation.class, "Operation", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getOperation_Children(), this.getOperation(), null, "children", null, 0, -1, Operation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.changeContextEClass, ChangeContext.class, "ChangeContext", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getChangeContext_Expression(), this.getInterpretedExpression(), "expression", "aql:self", 1, 1, ChangeContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.createInstanceEClass, CreateInstance.class, "CreateInstance", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getCreateInstance_TypeName(), this.getDomainType(), "typeName", null, 1, 1, CreateInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getCreateInstance_ReferenceName(), this.ecorePackage.getEString(), "referenceName", null, 1, 1, CreateInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getCreateInstance_VariableName(), this.ecorePackage.getEString(), "variableName", "newInstance", 1, 1, CreateInstance.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.setValueEClass, SetValue.class, "SetValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getSetValue_FeatureName(), this.ecorePackage.getEString(), "featureName", null, 1, 1, SetValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getSetValue_ValueExpression(), this.getInterpretedExpression(), "valueExpression", null, 1, 1, SetValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.unsetValueEClass, UnsetValue.class, "UnsetValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getUnsetValue_FeatureName(), this.ecorePackage.getEString(), "featureName", null, 1, 1, UnsetValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getUnsetValue_ElementExpression(), this.getInterpretedExpression(), "elementExpression", null, 1, 1, UnsetValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.deleteElementEClass, DeleteElement.class, "DeleteElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.letEClass, Let.class, "Let", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getLet_VariableName(), this.ecorePackage.getEString(), "variableName", null, 1, 1, Let.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getLet_ValueExpression(), this.getInterpretedExpression(), "valueExpression", null, 1, 1, Let.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.ifEClass, If.class, "If", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getIf_ConditionExpression(), this.getInterpretedExpression(), "conditionExpression", null, 1, 1, If.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalEClass, Conditional.class, "Conditional", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getConditional_Condition(), this.getInterpretedExpression(), "condition", "aql:false", 1, 1, Conditional.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.forEClass, For.class, "For", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getFor_Expression(), this.getInterpretedExpression(), "expression", null, 1, 1, For.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getFor_IteratorName(), this.ecorePackage.getEString(), "iteratorName", null, 1, 1, For.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Initialize data types
        this.initEDataType(this.identifierEDataType, String.class, "Identifier", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        this.initEDataType(this.interpretedExpressionEDataType, String.class, "InterpretedExpression", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        this.initEDataType(this.domainTypeEDataType, String.class, "DomainType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        this.initEDataType(this.colorEDataType, String.class, "Color", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        this.initEDataType(this.lengthEDataType, int.class, "Length", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        this.createResource(eNS_URI);
    }

} // ViewPackageImpl
