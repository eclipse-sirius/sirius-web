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
package org.eclipse.sirius.components.view.form.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.form.BarChartDescription;
import org.eclipse.sirius.components.view.form.BarChartDescriptionStyle;
import org.eclipse.sirius.components.view.form.ButtonDescription;
import org.eclipse.sirius.components.view.form.ButtonDescriptionStyle;
import org.eclipse.sirius.components.view.form.CheckboxDescription;
import org.eclipse.sirius.components.view.form.CheckboxDescriptionStyle;
import org.eclipse.sirius.components.view.form.ConditionalBarChartDescriptionStyle;
import org.eclipse.sirius.components.view.form.ConditionalButtonDescriptionStyle;
import org.eclipse.sirius.components.view.form.ConditionalCheckboxDescriptionStyle;
import org.eclipse.sirius.components.view.form.ConditionalContainerBorderStyle;
import org.eclipse.sirius.components.view.form.ConditionalLabelDescriptionStyle;
import org.eclipse.sirius.components.view.form.ConditionalLinkDescriptionStyle;
import org.eclipse.sirius.components.view.form.ConditionalListDescriptionStyle;
import org.eclipse.sirius.components.view.form.ConditionalMultiSelectDescriptionStyle;
import org.eclipse.sirius.components.view.form.ConditionalPieChartDescriptionStyle;
import org.eclipse.sirius.components.view.form.ConditionalRadioDescriptionStyle;
import org.eclipse.sirius.components.view.form.ConditionalSelectDescriptionStyle;
import org.eclipse.sirius.components.view.form.ConditionalTextareaDescriptionStyle;
import org.eclipse.sirius.components.view.form.ConditionalTextfieldDescriptionStyle;
import org.eclipse.sirius.components.view.form.ContainerBorderLineStyle;
import org.eclipse.sirius.components.view.form.ContainerBorderStyle;
import org.eclipse.sirius.components.view.form.FlexDirection;
import org.eclipse.sirius.components.view.form.FlexboxContainerDescription;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.form.FormElementDescription;
import org.eclipse.sirius.components.view.form.FormElementFor;
import org.eclipse.sirius.components.view.form.FormElementIf;
import org.eclipse.sirius.components.view.form.FormFactory;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.FormVariable;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.ImageDescription;
import org.eclipse.sirius.components.view.form.LabelDescription;
import org.eclipse.sirius.components.view.form.LabelDescriptionStyle;
import org.eclipse.sirius.components.view.form.LabelPlacement;
import org.eclipse.sirius.components.view.form.LinkDescription;
import org.eclipse.sirius.components.view.form.LinkDescriptionStyle;
import org.eclipse.sirius.components.view.form.ListDescription;
import org.eclipse.sirius.components.view.form.ListDescriptionStyle;
import org.eclipse.sirius.components.view.form.MultiSelectDescription;
import org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.PieChartDescription;
import org.eclipse.sirius.components.view.form.PieChartDescriptionStyle;
import org.eclipse.sirius.components.view.form.RadioDescription;
import org.eclipse.sirius.components.view.form.RadioDescriptionStyle;
import org.eclipse.sirius.components.view.form.RichTextDescription;
import org.eclipse.sirius.components.view.form.SelectDescription;
import org.eclipse.sirius.components.view.form.SelectDescriptionStyle;
import org.eclipse.sirius.components.view.form.SplitButtonDescription;
import org.eclipse.sirius.components.view.form.TextAreaDescription;
import org.eclipse.sirius.components.view.form.TextareaDescriptionStyle;
import org.eclipse.sirius.components.view.form.TextfieldDescription;
import org.eclipse.sirius.components.view.form.TextfieldDescriptionStyle;
import org.eclipse.sirius.components.view.form.TreeDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;
import org.eclipse.sirius.components.view.form.WidgetDescriptionStyle;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class FormPackageImpl extends EPackageImpl implements FormPackage {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass formDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass formVariableEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass pageDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass groupDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass formElementDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass widgetDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass barChartDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass splitButtonDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass buttonDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass checkboxDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass flexboxContainerDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass imageDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass labelDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass linkDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass listDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass multiSelectDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass treeDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass pieChartDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass radioDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass richTextDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass selectDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass textAreaDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass textfieldDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass widgetDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass barChartDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalBarChartDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass buttonDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalButtonDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass checkboxDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalCheckboxDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass labelDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalLabelDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass linkDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalLinkDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass listDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalListDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass multiSelectDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalMultiSelectDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass pieChartDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalPieChartDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass radioDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalRadioDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass selectDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalSelectDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass textareaDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalTextareaDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass textfieldDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalTextfieldDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass containerBorderStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalContainerBorderStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass formElementForEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass formElementIfEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EEnum flexDirectionEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EEnum groupDisplayModeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EEnum labelPlacementEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EEnum containerBorderLineStyleEEnum = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
     * EPackage.Registry} by the package package URI value.
     * <p>
     * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
     * performs initialization of the package, or returns the registered package, if one already exists. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see org.eclipse.sirius.components.view.form.FormPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private FormPackageImpl() {
        super(eNS_URI, FormFactory.eINSTANCE);
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
     * This method is used to initialize {@link FormPackage#eINSTANCE} when that field is accessed. Clients should not
     * invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static FormPackage init() {
        if (isInited)
            return (FormPackage) EPackage.Registry.INSTANCE.getEPackage(FormPackage.eNS_URI);

        // Obtain or create and register package
        Object registeredFormPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
        FormPackageImpl theFormPackage = registeredFormPackage instanceof FormPackageImpl ? (FormPackageImpl) registeredFormPackage : new FormPackageImpl();

        isInited = true;

        // Initialize simple dependencies
        ViewPackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theFormPackage.createPackageContents();

        // Initialize created meta-data
        theFormPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theFormPackage.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(FormPackage.eNS_URI, theFormPackage);
        return theFormPackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getFormDescription() {
        return this.formDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getFormDescription_Pages() {
        return (EReference) this.formDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getFormDescription_FormVariables() {
        return (EReference) this.formDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getFormVariable() {
        return this.formVariableEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getFormVariable_Name() {
        return (EAttribute) this.formVariableEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getFormVariable_DefaultValueExpression() {
        return (EAttribute) this.formVariableEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getPageDescription() {
        return this.pageDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPageDescription_Name() {
        return (EAttribute) this.pageDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPageDescription_LabelExpression() {
        return (EAttribute) this.pageDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPageDescription_DomainType() {
        return (EAttribute) this.pageDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPageDescription_SemanticCandidatesExpression() {
        return (EAttribute) this.pageDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPageDescription_PreconditionExpression() {
        return (EAttribute) this.pageDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPageDescription_Groups() {
        return (EReference) this.pageDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPageDescription_ToolbarActions() {
        return (EReference) this.pageDescriptionEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getGroupDescription() {
        return this.groupDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getGroupDescription_Name() {
        return (EAttribute) this.groupDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getGroupDescription_LabelExpression() {
        return (EAttribute) this.groupDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getGroupDescription_DisplayMode() {
        return (EAttribute) this.groupDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getGroupDescription_SemanticCandidatesExpression() {
        return (EAttribute) this.groupDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getGroupDescription_ToolbarActions() {
        return (EReference) this.groupDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getGroupDescription_Children() {
        return (EReference) this.groupDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getGroupDescription_BorderStyle() {
        return (EReference) this.groupDescriptionEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getGroupDescription_ConditionalBorderStyles() {
        return (EReference) this.groupDescriptionEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getFormElementDescription() {
        return this.formElementDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getFormElementDescription_Name() {
        return (EAttribute) this.formElementDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getWidgetDescription() {
        return this.widgetDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getWidgetDescription_LabelExpression() {
        return (EAttribute) this.widgetDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getWidgetDescription_HelpExpression() {
        return (EAttribute) this.widgetDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getBarChartDescription() {
        return this.barChartDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getBarChartDescription_ValuesExpression() {
        return (EAttribute) this.barChartDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getBarChartDescription_KeysExpression() {
        return (EAttribute) this.barChartDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getBarChartDescription_YAxisLabelExpression() {
        return (EAttribute) this.barChartDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getBarChartDescription_Style() {
        return (EReference) this.barChartDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getBarChartDescription_ConditionalStyles() {
        return (EReference) this.barChartDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getBarChartDescription_Width() {
        return (EAttribute) this.barChartDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getBarChartDescription_Height() {
        return (EAttribute) this.barChartDescriptionEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getSplitButtonDescription() {
        return this.splitButtonDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getSplitButtonDescription_Actions() {
        return (EReference) this.splitButtonDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getSplitButtonDescription_IsEnabledExpression() {
        return (EAttribute) this.splitButtonDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getButtonDescription() {
        return this.buttonDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getButtonDescription_ButtonLabelExpression() {
        return (EAttribute) this.buttonDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getButtonDescription_Body() {
        return (EReference) this.buttonDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getButtonDescription_ImageExpression() {
        return (EAttribute) this.buttonDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getButtonDescription_Style() {
        return (EReference) this.buttonDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getButtonDescription_ConditionalStyles() {
        return (EReference) this.buttonDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getButtonDescription_IsEnabledExpression() {
        return (EAttribute) this.buttonDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getCheckboxDescription() {
        return this.checkboxDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getCheckboxDescription_ValueExpression() {
        return (EAttribute) this.checkboxDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getCheckboxDescription_Body() {
        return (EReference) this.checkboxDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getCheckboxDescription_Style() {
        return (EReference) this.checkboxDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getCheckboxDescription_ConditionalStyles() {
        return (EReference) this.checkboxDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getCheckboxDescription_IsEnabledExpression() {
        return (EAttribute) this.checkboxDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getFlexboxContainerDescription() {
        return this.flexboxContainerDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getFlexboxContainerDescription_Children() {
        return (EReference) this.flexboxContainerDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getFlexboxContainerDescription_FlexDirection() {
        return (EAttribute) this.flexboxContainerDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getFlexboxContainerDescription_IsEnabledExpression() {
        return (EAttribute) this.flexboxContainerDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getFlexboxContainerDescription_BorderStyle() {
        return (EReference) this.flexboxContainerDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getFlexboxContainerDescription_ConditionalBorderStyles() {
        return (EReference) this.flexboxContainerDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getImageDescription() {
        return this.imageDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getImageDescription_UrlExpression() {
        return (EAttribute) this.imageDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getImageDescription_MaxWidthExpression() {
        return (EAttribute) this.imageDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getLabelDescription() {
        return this.labelDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getLabelDescription_ValueExpression() {
        return (EAttribute) this.labelDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getLabelDescription_Style() {
        return (EReference) this.labelDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getLabelDescription_ConditionalStyles() {
        return (EReference) this.labelDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getLinkDescription() {
        return this.linkDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getLinkDescription_ValueExpression() {
        return (EAttribute) this.linkDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getLinkDescription_Style() {
        return (EReference) this.linkDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getLinkDescription_ConditionalStyles() {
        return (EReference) this.linkDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getListDescription() {
        return this.listDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getListDescription_ValueExpression() {
        return (EAttribute) this.listDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getListDescription_DisplayExpression() {
        return (EAttribute) this.listDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getListDescription_IsDeletableExpression() {
        return (EAttribute) this.listDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getListDescription_Body() {
        return (EReference) this.listDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getListDescription_Style() {
        return (EReference) this.listDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getListDescription_ConditionalStyles() {
        return (EReference) this.listDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getListDescription_IsEnabledExpression() {
        return (EAttribute) this.listDescriptionEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getMultiSelectDescription() {
        return this.multiSelectDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getMultiSelectDescription_ValueExpression() {
        return (EAttribute) this.multiSelectDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getMultiSelectDescription_CandidatesExpression() {
        return (EAttribute) this.multiSelectDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getMultiSelectDescription_CandidateLabelExpression() {
        return (EAttribute) this.multiSelectDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMultiSelectDescription_Body() {
        return (EReference) this.multiSelectDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMultiSelectDescription_Style() {
        return (EReference) this.multiSelectDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMultiSelectDescription_ConditionalStyles() {
        return (EReference) this.multiSelectDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getMultiSelectDescription_IsEnabledExpression() {
        return (EAttribute) this.multiSelectDescriptionEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getTreeDescription() {
        return this.treeDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTreeDescription_ChildrenExpression() {
        return (EAttribute) this.treeDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTreeDescription_TreeItemLabelExpression() {
        return (EAttribute) this.treeDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTreeDescription_IsTreeItemSelectableExpression() {
        return (EAttribute) this.treeDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTreeDescription_TreeItemBeginIconExpression() {
        return (EAttribute) this.treeDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTreeDescription_TreeItemEndIconsExpression() {
        return (EAttribute) this.treeDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTreeDescription_IsCheckableExpression() {
        return (EAttribute) this.treeDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTreeDescription_CheckedValueExpression() {
        return (EAttribute) this.treeDescriptionEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTreeDescription_IsEnabledExpression() {
        return (EAttribute) this.treeDescriptionEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTreeDescription_Body() {
        return (EReference) this.treeDescriptionEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getPieChartDescription() {
        return this.pieChartDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPieChartDescription_ValuesExpression() {
        return (EAttribute) this.pieChartDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPieChartDescription_KeysExpression() {
        return (EAttribute) this.pieChartDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPieChartDescription_Style() {
        return (EReference) this.pieChartDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPieChartDescription_ConditionalStyles() {
        return (EReference) this.pieChartDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getRadioDescription() {
        return this.radioDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getRadioDescription_ValueExpression() {
        return (EAttribute) this.radioDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getRadioDescription_CandidatesExpression() {
        return (EAttribute) this.radioDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getRadioDescription_CandidateLabelExpression() {
        return (EAttribute) this.radioDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRadioDescription_Body() {
        return (EReference) this.radioDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRadioDescription_Style() {
        return (EReference) this.radioDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRadioDescription_ConditionalStyles() {
        return (EReference) this.radioDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getRadioDescription_IsEnabledExpression() {
        return (EAttribute) this.radioDescriptionEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getRichTextDescription() {
        return this.richTextDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getRichTextDescription_ValueExpression() {
        return (EAttribute) this.richTextDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRichTextDescription_Body() {
        return (EReference) this.richTextDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getRichTextDescription_IsEnabledExpression() {
        return (EAttribute) this.richTextDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getSelectDescription() {
        return this.selectDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getSelectDescription_ValueExpression() {
        return (EAttribute) this.selectDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getSelectDescription_CandidatesExpression() {
        return (EAttribute) this.selectDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getSelectDescription_CandidateLabelExpression() {
        return (EAttribute) this.selectDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getSelectDescription_Body() {
        return (EReference) this.selectDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getSelectDescription_Style() {
        return (EReference) this.selectDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getSelectDescription_ConditionalStyles() {
        return (EReference) this.selectDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getSelectDescription_IsEnabledExpression() {
        return (EAttribute) this.selectDescriptionEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getTextAreaDescription() {
        return this.textAreaDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTextAreaDescription_ValueExpression() {
        return (EAttribute) this.textAreaDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTextAreaDescription_Body() {
        return (EReference) this.textAreaDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTextAreaDescription_Style() {
        return (EReference) this.textAreaDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTextAreaDescription_ConditionalStyles() {
        return (EReference) this.textAreaDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTextAreaDescription_IsEnabledExpression() {
        return (EAttribute) this.textAreaDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getTextfieldDescription() {
        return this.textfieldDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTextfieldDescription_ValueExpression() {
        return (EAttribute) this.textfieldDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTextfieldDescription_Body() {
        return (EReference) this.textfieldDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTextfieldDescription_Style() {
        return (EReference) this.textfieldDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTextfieldDescription_ConditionalStyles() {
        return (EReference) this.textfieldDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTextfieldDescription_IsEnabledExpression() {
        return (EAttribute) this.textfieldDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getWidgetDescriptionStyle() {
        return this.widgetDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getBarChartDescriptionStyle() {
        return this.barChartDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getBarChartDescriptionStyle_BarsColor() {
        return (EAttribute) this.barChartDescriptionStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalBarChartDescriptionStyle() {
        return this.conditionalBarChartDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getButtonDescriptionStyle() {
        return this.buttonDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getButtonDescriptionStyle_BackgroundColor() {
        return (EReference) this.buttonDescriptionStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getButtonDescriptionStyle_ForegroundColor() {
        return (EReference) this.buttonDescriptionStyleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalButtonDescriptionStyle() {
        return this.conditionalButtonDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getCheckboxDescriptionStyle() {
        return this.checkboxDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getCheckboxDescriptionStyle_Color() {
        return (EReference) this.checkboxDescriptionStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getCheckboxDescriptionStyle_LabelPlacement() {
        return (EAttribute) this.checkboxDescriptionStyleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalCheckboxDescriptionStyle() {
        return this.conditionalCheckboxDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getLabelDescriptionStyle() {
        return this.labelDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getLabelDescriptionStyle_Color() {
        return (EReference) this.labelDescriptionStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalLabelDescriptionStyle() {
        return this.conditionalLabelDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getLinkDescriptionStyle() {
        return this.linkDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getLinkDescriptionStyle_Color() {
        return (EReference) this.linkDescriptionStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalLinkDescriptionStyle() {
        return this.conditionalLinkDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getListDescriptionStyle() {
        return this.listDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getListDescriptionStyle_Color() {
        return (EReference) this.listDescriptionStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalListDescriptionStyle() {
        return this.conditionalListDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getMultiSelectDescriptionStyle() {
        return this.multiSelectDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMultiSelectDescriptionStyle_BackgroundColor() {
        return (EReference) this.multiSelectDescriptionStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMultiSelectDescriptionStyle_ForegroundColor() {
        return (EReference) this.multiSelectDescriptionStyleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getMultiSelectDescriptionStyle_ShowIcon() {
        return (EAttribute) this.multiSelectDescriptionStyleEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalMultiSelectDescriptionStyle() {
        return this.conditionalMultiSelectDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getPieChartDescriptionStyle() {
        return this.pieChartDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPieChartDescriptionStyle_Colors() {
        return (EAttribute) this.pieChartDescriptionStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPieChartDescriptionStyle_StrokeWidth() {
        return (EAttribute) this.pieChartDescriptionStyleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPieChartDescriptionStyle_StrokeColor() {
        return (EReference) this.pieChartDescriptionStyleEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalPieChartDescriptionStyle() {
        return this.conditionalPieChartDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getRadioDescriptionStyle() {
        return this.radioDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRadioDescriptionStyle_Color() {
        return (EReference) this.radioDescriptionStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalRadioDescriptionStyle() {
        return this.conditionalRadioDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getSelectDescriptionStyle() {
        return this.selectDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getSelectDescriptionStyle_BackgroundColor() {
        return (EReference) this.selectDescriptionStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getSelectDescriptionStyle_ForegroundColor() {
        return (EReference) this.selectDescriptionStyleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getSelectDescriptionStyle_ShowIcon() {
        return (EAttribute) this.selectDescriptionStyleEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalSelectDescriptionStyle() {
        return this.conditionalSelectDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getTextareaDescriptionStyle() {
        return this.textareaDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTextareaDescriptionStyle_BackgroundColor() {
        return (EReference) this.textareaDescriptionStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTextareaDescriptionStyle_ForegroundColor() {
        return (EReference) this.textareaDescriptionStyleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalTextareaDescriptionStyle() {
        return this.conditionalTextareaDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getTextfieldDescriptionStyle() {
        return this.textfieldDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTextfieldDescriptionStyle_BackgroundColor() {
        return (EReference) this.textfieldDescriptionStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTextfieldDescriptionStyle_ForegroundColor() {
        return (EReference) this.textfieldDescriptionStyleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalTextfieldDescriptionStyle() {
        return this.conditionalTextfieldDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getContainerBorderStyle() {
        return this.containerBorderStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getContainerBorderStyle_BorderColor() {
        return (EReference) this.containerBorderStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getContainerBorderStyle_BorderRadius() {
        return (EAttribute) this.containerBorderStyleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getContainerBorderStyle_BorderSize() {
        return (EAttribute) this.containerBorderStyleEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getContainerBorderStyle_BorderLineStyle() {
        return (EAttribute) this.containerBorderStyleEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalContainerBorderStyle() {
        return this.conditionalContainerBorderStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getFormElementFor() {
        return this.formElementForEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getFormElementFor_Iterator() {
        return (EAttribute) this.formElementForEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getFormElementFor_IterableExpression() {
        return (EAttribute) this.formElementForEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getFormElementFor_Children() {
        return (EReference) this.formElementForEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getFormElementIf() {
        return this.formElementIfEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getFormElementIf_PredicateExpression() {
        return (EAttribute) this.formElementIfEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getFormElementIf_Children() {
        return (EReference) this.formElementIfEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EEnum getFlexDirection() {
        return this.flexDirectionEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EEnum getGroupDisplayMode() {
        return this.groupDisplayModeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EEnum getLabelPlacement() {
        return this.labelPlacementEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EEnum getContainerBorderLineStyle() {
        return this.containerBorderLineStyleEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public FormFactory getFormFactory() {
        return (FormFactory) this.getEFactoryInstance();
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
        this.formDescriptionEClass = this.createEClass(FORM_DESCRIPTION);
        this.createEReference(this.formDescriptionEClass, FORM_DESCRIPTION__PAGES);
        this.createEReference(this.formDescriptionEClass, FORM_DESCRIPTION__FORM_VARIABLES);

        this.formVariableEClass = this.createEClass(FORM_VARIABLE);
        this.createEAttribute(this.formVariableEClass, FORM_VARIABLE__NAME);
        this.createEAttribute(this.formVariableEClass, FORM_VARIABLE__DEFAULT_VALUE_EXPRESSION);

        this.pageDescriptionEClass = this.createEClass(PAGE_DESCRIPTION);
        this.createEAttribute(this.pageDescriptionEClass, PAGE_DESCRIPTION__NAME);
        this.createEAttribute(this.pageDescriptionEClass, PAGE_DESCRIPTION__LABEL_EXPRESSION);
        this.createEAttribute(this.pageDescriptionEClass, PAGE_DESCRIPTION__DOMAIN_TYPE);
        this.createEAttribute(this.pageDescriptionEClass, PAGE_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION);
        this.createEAttribute(this.pageDescriptionEClass, PAGE_DESCRIPTION__PRECONDITION_EXPRESSION);
        this.createEReference(this.pageDescriptionEClass, PAGE_DESCRIPTION__GROUPS);
        this.createEReference(this.pageDescriptionEClass, PAGE_DESCRIPTION__TOOLBAR_ACTIONS);

        this.groupDescriptionEClass = this.createEClass(GROUP_DESCRIPTION);
        this.createEAttribute(this.groupDescriptionEClass, GROUP_DESCRIPTION__NAME);
        this.createEAttribute(this.groupDescriptionEClass, GROUP_DESCRIPTION__LABEL_EXPRESSION);
        this.createEAttribute(this.groupDescriptionEClass, GROUP_DESCRIPTION__DISPLAY_MODE);
        this.createEAttribute(this.groupDescriptionEClass, GROUP_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION);
        this.createEReference(this.groupDescriptionEClass, GROUP_DESCRIPTION__TOOLBAR_ACTIONS);
        this.createEReference(this.groupDescriptionEClass, GROUP_DESCRIPTION__CHILDREN);
        this.createEReference(this.groupDescriptionEClass, GROUP_DESCRIPTION__BORDER_STYLE);
        this.createEReference(this.groupDescriptionEClass, GROUP_DESCRIPTION__CONDITIONAL_BORDER_STYLES);

        this.formElementDescriptionEClass = this.createEClass(FORM_ELEMENT_DESCRIPTION);
        this.createEAttribute(this.formElementDescriptionEClass, FORM_ELEMENT_DESCRIPTION__NAME);

        this.widgetDescriptionEClass = this.createEClass(WIDGET_DESCRIPTION);
        this.createEAttribute(this.widgetDescriptionEClass, WIDGET_DESCRIPTION__LABEL_EXPRESSION);
        this.createEAttribute(this.widgetDescriptionEClass, WIDGET_DESCRIPTION__HELP_EXPRESSION);

        this.barChartDescriptionEClass = this.createEClass(BAR_CHART_DESCRIPTION);
        this.createEAttribute(this.barChartDescriptionEClass, BAR_CHART_DESCRIPTION__VALUES_EXPRESSION);
        this.createEAttribute(this.barChartDescriptionEClass, BAR_CHART_DESCRIPTION__KEYS_EXPRESSION);
        this.createEAttribute(this.barChartDescriptionEClass, BAR_CHART_DESCRIPTION__YAXIS_LABEL_EXPRESSION);
        this.createEReference(this.barChartDescriptionEClass, BAR_CHART_DESCRIPTION__STYLE);
        this.createEReference(this.barChartDescriptionEClass, BAR_CHART_DESCRIPTION__CONDITIONAL_STYLES);
        this.createEAttribute(this.barChartDescriptionEClass, BAR_CHART_DESCRIPTION__WIDTH);
        this.createEAttribute(this.barChartDescriptionEClass, BAR_CHART_DESCRIPTION__HEIGHT);

        this.buttonDescriptionEClass = this.createEClass(BUTTON_DESCRIPTION);
        this.createEAttribute(this.buttonDescriptionEClass, BUTTON_DESCRIPTION__BUTTON_LABEL_EXPRESSION);
        this.createEReference(this.buttonDescriptionEClass, BUTTON_DESCRIPTION__BODY);
        this.createEAttribute(this.buttonDescriptionEClass, BUTTON_DESCRIPTION__IMAGE_EXPRESSION);
        this.createEReference(this.buttonDescriptionEClass, BUTTON_DESCRIPTION__STYLE);
        this.createEReference(this.buttonDescriptionEClass, BUTTON_DESCRIPTION__CONDITIONAL_STYLES);
        this.createEAttribute(this.buttonDescriptionEClass, BUTTON_DESCRIPTION__IS_ENABLED_EXPRESSION);

        this.checkboxDescriptionEClass = this.createEClass(CHECKBOX_DESCRIPTION);
        this.createEAttribute(this.checkboxDescriptionEClass, CHECKBOX_DESCRIPTION__VALUE_EXPRESSION);
        this.createEReference(this.checkboxDescriptionEClass, CHECKBOX_DESCRIPTION__BODY);
        this.createEReference(this.checkboxDescriptionEClass, CHECKBOX_DESCRIPTION__STYLE);
        this.createEReference(this.checkboxDescriptionEClass, CHECKBOX_DESCRIPTION__CONDITIONAL_STYLES);
        this.createEAttribute(this.checkboxDescriptionEClass, CHECKBOX_DESCRIPTION__IS_ENABLED_EXPRESSION);

        this.flexboxContainerDescriptionEClass = this.createEClass(FLEXBOX_CONTAINER_DESCRIPTION);
        this.createEReference(this.flexboxContainerDescriptionEClass, FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN);
        this.createEAttribute(this.flexboxContainerDescriptionEClass, FLEXBOX_CONTAINER_DESCRIPTION__FLEX_DIRECTION);
        this.createEAttribute(this.flexboxContainerDescriptionEClass, FLEXBOX_CONTAINER_DESCRIPTION__IS_ENABLED_EXPRESSION);
        this.createEReference(this.flexboxContainerDescriptionEClass, FLEXBOX_CONTAINER_DESCRIPTION__BORDER_STYLE);
        this.createEReference(this.flexboxContainerDescriptionEClass, FLEXBOX_CONTAINER_DESCRIPTION__CONDITIONAL_BORDER_STYLES);

        this.imageDescriptionEClass = this.createEClass(IMAGE_DESCRIPTION);
        this.createEAttribute(this.imageDescriptionEClass, IMAGE_DESCRIPTION__URL_EXPRESSION);
        this.createEAttribute(this.imageDescriptionEClass, IMAGE_DESCRIPTION__MAX_WIDTH_EXPRESSION);

        this.labelDescriptionEClass = this.createEClass(LABEL_DESCRIPTION);
        this.createEAttribute(this.labelDescriptionEClass, LABEL_DESCRIPTION__VALUE_EXPRESSION);
        this.createEReference(this.labelDescriptionEClass, LABEL_DESCRIPTION__STYLE);
        this.createEReference(this.labelDescriptionEClass, LABEL_DESCRIPTION__CONDITIONAL_STYLES);

        this.linkDescriptionEClass = this.createEClass(LINK_DESCRIPTION);
        this.createEAttribute(this.linkDescriptionEClass, LINK_DESCRIPTION__VALUE_EXPRESSION);
        this.createEReference(this.linkDescriptionEClass, LINK_DESCRIPTION__STYLE);
        this.createEReference(this.linkDescriptionEClass, LINK_DESCRIPTION__CONDITIONAL_STYLES);

        this.listDescriptionEClass = this.createEClass(LIST_DESCRIPTION);
        this.createEAttribute(this.listDescriptionEClass, LIST_DESCRIPTION__VALUE_EXPRESSION);
        this.createEAttribute(this.listDescriptionEClass, LIST_DESCRIPTION__DISPLAY_EXPRESSION);
        this.createEAttribute(this.listDescriptionEClass, LIST_DESCRIPTION__IS_DELETABLE_EXPRESSION);
        this.createEReference(this.listDescriptionEClass, LIST_DESCRIPTION__BODY);
        this.createEReference(this.listDescriptionEClass, LIST_DESCRIPTION__STYLE);
        this.createEReference(this.listDescriptionEClass, LIST_DESCRIPTION__CONDITIONAL_STYLES);
        this.createEAttribute(this.listDescriptionEClass, LIST_DESCRIPTION__IS_ENABLED_EXPRESSION);

        this.multiSelectDescriptionEClass = this.createEClass(MULTI_SELECT_DESCRIPTION);
        this.createEAttribute(this.multiSelectDescriptionEClass, MULTI_SELECT_DESCRIPTION__VALUE_EXPRESSION);
        this.createEAttribute(this.multiSelectDescriptionEClass, MULTI_SELECT_DESCRIPTION__CANDIDATES_EXPRESSION);
        this.createEAttribute(this.multiSelectDescriptionEClass, MULTI_SELECT_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION);
        this.createEReference(this.multiSelectDescriptionEClass, MULTI_SELECT_DESCRIPTION__BODY);
        this.createEReference(this.multiSelectDescriptionEClass, MULTI_SELECT_DESCRIPTION__STYLE);
        this.createEReference(this.multiSelectDescriptionEClass, MULTI_SELECT_DESCRIPTION__CONDITIONAL_STYLES);
        this.createEAttribute(this.multiSelectDescriptionEClass, MULTI_SELECT_DESCRIPTION__IS_ENABLED_EXPRESSION);

        this.pieChartDescriptionEClass = this.createEClass(PIE_CHART_DESCRIPTION);
        this.createEAttribute(this.pieChartDescriptionEClass, PIE_CHART_DESCRIPTION__VALUES_EXPRESSION);
        this.createEAttribute(this.pieChartDescriptionEClass, PIE_CHART_DESCRIPTION__KEYS_EXPRESSION);
        this.createEReference(this.pieChartDescriptionEClass, PIE_CHART_DESCRIPTION__STYLE);
        this.createEReference(this.pieChartDescriptionEClass, PIE_CHART_DESCRIPTION__CONDITIONAL_STYLES);

        this.radioDescriptionEClass = this.createEClass(RADIO_DESCRIPTION);
        this.createEAttribute(this.radioDescriptionEClass, RADIO_DESCRIPTION__VALUE_EXPRESSION);
        this.createEAttribute(this.radioDescriptionEClass, RADIO_DESCRIPTION__CANDIDATES_EXPRESSION);
        this.createEAttribute(this.radioDescriptionEClass, RADIO_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION);
        this.createEReference(this.radioDescriptionEClass, RADIO_DESCRIPTION__BODY);
        this.createEReference(this.radioDescriptionEClass, RADIO_DESCRIPTION__STYLE);
        this.createEReference(this.radioDescriptionEClass, RADIO_DESCRIPTION__CONDITIONAL_STYLES);
        this.createEAttribute(this.radioDescriptionEClass, RADIO_DESCRIPTION__IS_ENABLED_EXPRESSION);

        this.richTextDescriptionEClass = this.createEClass(RICH_TEXT_DESCRIPTION);
        this.createEAttribute(this.richTextDescriptionEClass, RICH_TEXT_DESCRIPTION__VALUE_EXPRESSION);
        this.createEReference(this.richTextDescriptionEClass, RICH_TEXT_DESCRIPTION__BODY);
        this.createEAttribute(this.richTextDescriptionEClass, RICH_TEXT_DESCRIPTION__IS_ENABLED_EXPRESSION);

        this.selectDescriptionEClass = this.createEClass(SELECT_DESCRIPTION);
        this.createEAttribute(this.selectDescriptionEClass, SELECT_DESCRIPTION__VALUE_EXPRESSION);
        this.createEAttribute(this.selectDescriptionEClass, SELECT_DESCRIPTION__CANDIDATES_EXPRESSION);
        this.createEAttribute(this.selectDescriptionEClass, SELECT_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION);
        this.createEReference(this.selectDescriptionEClass, SELECT_DESCRIPTION__BODY);
        this.createEReference(this.selectDescriptionEClass, SELECT_DESCRIPTION__STYLE);
        this.createEReference(this.selectDescriptionEClass, SELECT_DESCRIPTION__CONDITIONAL_STYLES);
        this.createEAttribute(this.selectDescriptionEClass, SELECT_DESCRIPTION__IS_ENABLED_EXPRESSION);

        this.splitButtonDescriptionEClass = this.createEClass(SPLIT_BUTTON_DESCRIPTION);
        this.createEReference(this.splitButtonDescriptionEClass, SPLIT_BUTTON_DESCRIPTION__ACTIONS);
        this.createEAttribute(this.splitButtonDescriptionEClass, SPLIT_BUTTON_DESCRIPTION__IS_ENABLED_EXPRESSION);

        this.textAreaDescriptionEClass = this.createEClass(TEXT_AREA_DESCRIPTION);
        this.createEAttribute(this.textAreaDescriptionEClass, TEXT_AREA_DESCRIPTION__VALUE_EXPRESSION);
        this.createEReference(this.textAreaDescriptionEClass, TEXT_AREA_DESCRIPTION__BODY);
        this.createEReference(this.textAreaDescriptionEClass, TEXT_AREA_DESCRIPTION__STYLE);
        this.createEReference(this.textAreaDescriptionEClass, TEXT_AREA_DESCRIPTION__CONDITIONAL_STYLES);
        this.createEAttribute(this.textAreaDescriptionEClass, TEXT_AREA_DESCRIPTION__IS_ENABLED_EXPRESSION);

        this.textfieldDescriptionEClass = this.createEClass(TEXTFIELD_DESCRIPTION);
        this.createEAttribute(this.textfieldDescriptionEClass, TEXTFIELD_DESCRIPTION__VALUE_EXPRESSION);
        this.createEReference(this.textfieldDescriptionEClass, TEXTFIELD_DESCRIPTION__BODY);
        this.createEReference(this.textfieldDescriptionEClass, TEXTFIELD_DESCRIPTION__STYLE);
        this.createEReference(this.textfieldDescriptionEClass, TEXTFIELD_DESCRIPTION__CONDITIONAL_STYLES);
        this.createEAttribute(this.textfieldDescriptionEClass, TEXTFIELD_DESCRIPTION__IS_ENABLED_EXPRESSION);

        this.treeDescriptionEClass = this.createEClass(TREE_DESCRIPTION);
        this.createEAttribute(this.treeDescriptionEClass, TREE_DESCRIPTION__CHILDREN_EXPRESSION);
        this.createEAttribute(this.treeDescriptionEClass, TREE_DESCRIPTION__TREE_ITEM_LABEL_EXPRESSION);
        this.createEAttribute(this.treeDescriptionEClass, TREE_DESCRIPTION__IS_TREE_ITEM_SELECTABLE_EXPRESSION);
        this.createEAttribute(this.treeDescriptionEClass, TREE_DESCRIPTION__TREE_ITEM_BEGIN_ICON_EXPRESSION);
        this.createEAttribute(this.treeDescriptionEClass, TREE_DESCRIPTION__TREE_ITEM_END_ICONS_EXPRESSION);
        this.createEAttribute(this.treeDescriptionEClass, TREE_DESCRIPTION__IS_CHECKABLE_EXPRESSION);
        this.createEAttribute(this.treeDescriptionEClass, TREE_DESCRIPTION__CHECKED_VALUE_EXPRESSION);
        this.createEAttribute(this.treeDescriptionEClass, TREE_DESCRIPTION__IS_ENABLED_EXPRESSION);
        this.createEReference(this.treeDescriptionEClass, TREE_DESCRIPTION__BODY);

        this.widgetDescriptionStyleEClass = this.createEClass(WIDGET_DESCRIPTION_STYLE);

        this.barChartDescriptionStyleEClass = this.createEClass(BAR_CHART_DESCRIPTION_STYLE);
        this.createEAttribute(this.barChartDescriptionStyleEClass, BAR_CHART_DESCRIPTION_STYLE__BARS_COLOR);

        this.conditionalBarChartDescriptionStyleEClass = this.createEClass(CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE);

        this.buttonDescriptionStyleEClass = this.createEClass(BUTTON_DESCRIPTION_STYLE);
        this.createEReference(this.buttonDescriptionStyleEClass, BUTTON_DESCRIPTION_STYLE__BACKGROUND_COLOR);
        this.createEReference(this.buttonDescriptionStyleEClass, BUTTON_DESCRIPTION_STYLE__FOREGROUND_COLOR);

        this.conditionalButtonDescriptionStyleEClass = this.createEClass(CONDITIONAL_BUTTON_DESCRIPTION_STYLE);

        this.checkboxDescriptionStyleEClass = this.createEClass(CHECKBOX_DESCRIPTION_STYLE);
        this.createEReference(this.checkboxDescriptionStyleEClass, CHECKBOX_DESCRIPTION_STYLE__COLOR);
        this.createEAttribute(this.checkboxDescriptionStyleEClass, CHECKBOX_DESCRIPTION_STYLE__LABEL_PLACEMENT);

        this.conditionalCheckboxDescriptionStyleEClass = this.createEClass(CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE);

        this.labelDescriptionStyleEClass = this.createEClass(LABEL_DESCRIPTION_STYLE);
        this.createEReference(this.labelDescriptionStyleEClass, LABEL_DESCRIPTION_STYLE__COLOR);

        this.conditionalLabelDescriptionStyleEClass = this.createEClass(CONDITIONAL_LABEL_DESCRIPTION_STYLE);

        this.linkDescriptionStyleEClass = this.createEClass(LINK_DESCRIPTION_STYLE);
        this.createEReference(this.linkDescriptionStyleEClass, LINK_DESCRIPTION_STYLE__COLOR);

        this.conditionalLinkDescriptionStyleEClass = this.createEClass(CONDITIONAL_LINK_DESCRIPTION_STYLE);

        this.listDescriptionStyleEClass = this.createEClass(LIST_DESCRIPTION_STYLE);
        this.createEReference(this.listDescriptionStyleEClass, LIST_DESCRIPTION_STYLE__COLOR);

        this.conditionalListDescriptionStyleEClass = this.createEClass(CONDITIONAL_LIST_DESCRIPTION_STYLE);

        this.multiSelectDescriptionStyleEClass = this.createEClass(MULTI_SELECT_DESCRIPTION_STYLE);
        this.createEReference(this.multiSelectDescriptionStyleEClass, MULTI_SELECT_DESCRIPTION_STYLE__BACKGROUND_COLOR);
        this.createEReference(this.multiSelectDescriptionStyleEClass, MULTI_SELECT_DESCRIPTION_STYLE__FOREGROUND_COLOR);
        this.createEAttribute(this.multiSelectDescriptionStyleEClass, MULTI_SELECT_DESCRIPTION_STYLE__SHOW_ICON);

        this.conditionalMultiSelectDescriptionStyleEClass = this.createEClass(CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE);

        this.pieChartDescriptionStyleEClass = this.createEClass(PIE_CHART_DESCRIPTION_STYLE);
        this.createEAttribute(this.pieChartDescriptionStyleEClass, PIE_CHART_DESCRIPTION_STYLE__COLORS);
        this.createEAttribute(this.pieChartDescriptionStyleEClass, PIE_CHART_DESCRIPTION_STYLE__STROKE_WIDTH);
        this.createEReference(this.pieChartDescriptionStyleEClass, PIE_CHART_DESCRIPTION_STYLE__STROKE_COLOR);

        this.conditionalPieChartDescriptionStyleEClass = this.createEClass(CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE);

        this.radioDescriptionStyleEClass = this.createEClass(RADIO_DESCRIPTION_STYLE);
        this.createEReference(this.radioDescriptionStyleEClass, RADIO_DESCRIPTION_STYLE__COLOR);

        this.conditionalRadioDescriptionStyleEClass = this.createEClass(CONDITIONAL_RADIO_DESCRIPTION_STYLE);

        this.selectDescriptionStyleEClass = this.createEClass(SELECT_DESCRIPTION_STYLE);
        this.createEReference(this.selectDescriptionStyleEClass, SELECT_DESCRIPTION_STYLE__BACKGROUND_COLOR);
        this.createEReference(this.selectDescriptionStyleEClass, SELECT_DESCRIPTION_STYLE__FOREGROUND_COLOR);
        this.createEAttribute(this.selectDescriptionStyleEClass, SELECT_DESCRIPTION_STYLE__SHOW_ICON);

        this.conditionalSelectDescriptionStyleEClass = this.createEClass(CONDITIONAL_SELECT_DESCRIPTION_STYLE);

        this.textareaDescriptionStyleEClass = this.createEClass(TEXTAREA_DESCRIPTION_STYLE);
        this.createEReference(this.textareaDescriptionStyleEClass, TEXTAREA_DESCRIPTION_STYLE__BACKGROUND_COLOR);
        this.createEReference(this.textareaDescriptionStyleEClass, TEXTAREA_DESCRIPTION_STYLE__FOREGROUND_COLOR);

        this.conditionalTextareaDescriptionStyleEClass = this.createEClass(CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE);

        this.textfieldDescriptionStyleEClass = this.createEClass(TEXTFIELD_DESCRIPTION_STYLE);
        this.createEReference(this.textfieldDescriptionStyleEClass, TEXTFIELD_DESCRIPTION_STYLE__BACKGROUND_COLOR);
        this.createEReference(this.textfieldDescriptionStyleEClass, TEXTFIELD_DESCRIPTION_STYLE__FOREGROUND_COLOR);

        this.conditionalTextfieldDescriptionStyleEClass = this.createEClass(CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE);

        this.containerBorderStyleEClass = this.createEClass(CONTAINER_BORDER_STYLE);
        this.createEReference(this.containerBorderStyleEClass, CONTAINER_BORDER_STYLE__BORDER_COLOR);
        this.createEAttribute(this.containerBorderStyleEClass, CONTAINER_BORDER_STYLE__BORDER_RADIUS);
        this.createEAttribute(this.containerBorderStyleEClass, CONTAINER_BORDER_STYLE__BORDER_SIZE);
        this.createEAttribute(this.containerBorderStyleEClass, CONTAINER_BORDER_STYLE__BORDER_LINE_STYLE);

        this.conditionalContainerBorderStyleEClass = this.createEClass(CONDITIONAL_CONTAINER_BORDER_STYLE);

        this.formElementForEClass = this.createEClass(FORM_ELEMENT_FOR);
        this.createEAttribute(this.formElementForEClass, FORM_ELEMENT_FOR__ITERATOR);
        this.createEAttribute(this.formElementForEClass, FORM_ELEMENT_FOR__ITERABLE_EXPRESSION);
        this.createEReference(this.formElementForEClass, FORM_ELEMENT_FOR__CHILDREN);

        this.formElementIfEClass = this.createEClass(FORM_ELEMENT_IF);
        this.createEAttribute(this.formElementIfEClass, FORM_ELEMENT_IF__PREDICATE_EXPRESSION);
        this.createEReference(this.formElementIfEClass, FORM_ELEMENT_IF__CHILDREN);

        // Create enums
        this.flexDirectionEEnum = this.createEEnum(FLEX_DIRECTION);
        this.groupDisplayModeEEnum = this.createEEnum(GROUP_DISPLAY_MODE);
        this.labelPlacementEEnum = this.createEEnum(LABEL_PLACEMENT);
        this.containerBorderLineStyleEEnum = this.createEEnum(CONTAINER_BORDER_LINE_STYLE);
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

        // Obtain other dependent packages
        ViewPackage theViewPackage = (ViewPackage) EPackage.Registry.INSTANCE.getEPackage(ViewPackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        this.formDescriptionEClass.getESuperTypes().add(theViewPackage.getRepresentationDescription());
        this.widgetDescriptionEClass.getESuperTypes().add(this.getFormElementDescription());
        this.barChartDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.buttonDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.checkboxDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.flexboxContainerDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.imageDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.labelDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.linkDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.listDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.multiSelectDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.pieChartDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.radioDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.richTextDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.selectDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.splitButtonDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.textAreaDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.textfieldDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.treeDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.barChartDescriptionStyleEClass.getESuperTypes().add(this.getWidgetDescriptionStyle());
        this.barChartDescriptionStyleEClass.getESuperTypes().add(theViewPackage.getLabelStyle());
        this.conditionalBarChartDescriptionStyleEClass.getESuperTypes().add(theViewPackage.getConditional());
        this.conditionalBarChartDescriptionStyleEClass.getESuperTypes().add(this.getBarChartDescriptionStyle());
        this.buttonDescriptionStyleEClass.getESuperTypes().add(this.getWidgetDescriptionStyle());
        this.buttonDescriptionStyleEClass.getESuperTypes().add(theViewPackage.getLabelStyle());
        this.conditionalButtonDescriptionStyleEClass.getESuperTypes().add(theViewPackage.getConditional());
        this.conditionalButtonDescriptionStyleEClass.getESuperTypes().add(this.getButtonDescriptionStyle());
        this.checkboxDescriptionStyleEClass.getESuperTypes().add(this.getWidgetDescriptionStyle());
        this.conditionalCheckboxDescriptionStyleEClass.getESuperTypes().add(theViewPackage.getConditional());
        this.conditionalCheckboxDescriptionStyleEClass.getESuperTypes().add(this.getCheckboxDescriptionStyle());
        this.labelDescriptionStyleEClass.getESuperTypes().add(this.getWidgetDescriptionStyle());
        this.labelDescriptionStyleEClass.getESuperTypes().add(theViewPackage.getLabelStyle());
        this.conditionalLabelDescriptionStyleEClass.getESuperTypes().add(theViewPackage.getConditional());
        this.conditionalLabelDescriptionStyleEClass.getESuperTypes().add(this.getLabelDescriptionStyle());
        this.linkDescriptionStyleEClass.getESuperTypes().add(this.getWidgetDescriptionStyle());
        this.linkDescriptionStyleEClass.getESuperTypes().add(theViewPackage.getLabelStyle());
        this.conditionalLinkDescriptionStyleEClass.getESuperTypes().add(theViewPackage.getConditional());
        this.conditionalLinkDescriptionStyleEClass.getESuperTypes().add(this.getLinkDescriptionStyle());
        this.listDescriptionStyleEClass.getESuperTypes().add(this.getWidgetDescriptionStyle());
        this.listDescriptionStyleEClass.getESuperTypes().add(theViewPackage.getLabelStyle());
        this.conditionalListDescriptionStyleEClass.getESuperTypes().add(theViewPackage.getConditional());
        this.conditionalListDescriptionStyleEClass.getESuperTypes().add(this.getListDescriptionStyle());
        this.multiSelectDescriptionStyleEClass.getESuperTypes().add(this.getWidgetDescriptionStyle());
        this.multiSelectDescriptionStyleEClass.getESuperTypes().add(theViewPackage.getLabelStyle());
        this.conditionalMultiSelectDescriptionStyleEClass.getESuperTypes().add(theViewPackage.getConditional());
        this.conditionalMultiSelectDescriptionStyleEClass.getESuperTypes().add(this.getMultiSelectDescriptionStyle());
        this.pieChartDescriptionStyleEClass.getESuperTypes().add(this.getWidgetDescriptionStyle());
        this.pieChartDescriptionStyleEClass.getESuperTypes().add(theViewPackage.getLabelStyle());
        this.conditionalPieChartDescriptionStyleEClass.getESuperTypes().add(theViewPackage.getConditional());
        this.conditionalPieChartDescriptionStyleEClass.getESuperTypes().add(this.getPieChartDescriptionStyle());
        this.radioDescriptionStyleEClass.getESuperTypes().add(this.getWidgetDescriptionStyle());
        this.radioDescriptionStyleEClass.getESuperTypes().add(theViewPackage.getLabelStyle());
        this.conditionalRadioDescriptionStyleEClass.getESuperTypes().add(theViewPackage.getConditional());
        this.conditionalRadioDescriptionStyleEClass.getESuperTypes().add(this.getRadioDescriptionStyle());
        this.selectDescriptionStyleEClass.getESuperTypes().add(this.getWidgetDescriptionStyle());
        this.selectDescriptionStyleEClass.getESuperTypes().add(theViewPackage.getLabelStyle());
        this.conditionalSelectDescriptionStyleEClass.getESuperTypes().add(theViewPackage.getConditional());
        this.conditionalSelectDescriptionStyleEClass.getESuperTypes().add(this.getSelectDescriptionStyle());
        this.textareaDescriptionStyleEClass.getESuperTypes().add(this.getWidgetDescriptionStyle());
        this.textareaDescriptionStyleEClass.getESuperTypes().add(theViewPackage.getLabelStyle());
        this.conditionalTextareaDescriptionStyleEClass.getESuperTypes().add(theViewPackage.getConditional());
        this.conditionalTextareaDescriptionStyleEClass.getESuperTypes().add(this.getTextareaDescriptionStyle());
        this.textfieldDescriptionStyleEClass.getESuperTypes().add(this.getWidgetDescriptionStyle());
        this.textfieldDescriptionStyleEClass.getESuperTypes().add(theViewPackage.getLabelStyle());
        this.conditionalTextfieldDescriptionStyleEClass.getESuperTypes().add(theViewPackage.getConditional());
        this.conditionalTextfieldDescriptionStyleEClass.getESuperTypes().add(this.getTextfieldDescriptionStyle());
        this.conditionalContainerBorderStyleEClass.getESuperTypes().add(theViewPackage.getConditional());
        this.conditionalContainerBorderStyleEClass.getESuperTypes().add(this.getContainerBorderStyle());
        this.formElementForEClass.getESuperTypes().add(this.getFormElementDescription());
        this.formElementIfEClass.getESuperTypes().add(this.getFormElementDescription());

        // Initialize classes, features, and operations; add parameters
        this.initEClass(this.formDescriptionEClass, FormDescription.class, "FormDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getFormDescription_Pages(), this.getPageDescription(), null, "pages", null, 0, -1, FormDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getFormDescription_FormVariables(), this.getFormVariable(), null, "formVariables", null, 0, -1, FormDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.formVariableEClass, FormVariable.class, "FormVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getFormVariable_Name(), theViewPackage.getIdentifier(), "name", null, 0, 1, FormVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getFormVariable_DefaultValueExpression(), theViewPackage.getInterpretedExpression(), "defaultValueExpression", null, 0, 1, FormVariable.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.pageDescriptionEClass, PageDescription.class, "PageDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getPageDescription_Name(), theViewPackage.getIdentifier(), "name", null, 0, 1, PageDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getPageDescription_LabelExpression(), theViewPackage.getInterpretedExpression(), "labelExpression", null, 0, 1, PageDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getPageDescription_DomainType(), theViewPackage.getDomainType(), "domainType", "", 0, 1, PageDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getPageDescription_SemanticCandidatesExpression(), theViewPackage.getInterpretedExpression(), "semanticCandidatesExpression", "aql:self", 0, 1, PageDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getPageDescription_PreconditionExpression(), theViewPackage.getInterpretedExpression(), "preconditionExpression", "", 0, 1, PageDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getPageDescription_Groups(), this.getGroupDescription(), null, "groups", null, 0, -1, PageDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getPageDescription_ToolbarActions(), this.getButtonDescription(), null, "toolbarActions", null, 0, -1, PageDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.groupDescriptionEClass, GroupDescription.class, "GroupDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getGroupDescription_Name(), theViewPackage.getIdentifier(), "name", null, 0, 1, GroupDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getGroupDescription_LabelExpression(), theViewPackage.getInterpretedExpression(), "labelExpression", null, 0, 1, GroupDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getGroupDescription_DisplayMode(), this.getGroupDisplayMode(), "displayMode", "LIST", 1, 1, GroupDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getGroupDescription_SemanticCandidatesExpression(), theViewPackage.getInterpretedExpression(), "semanticCandidatesExpression", "aql:self", 0, 1,
                GroupDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getGroupDescription_ToolbarActions(), this.getButtonDescription(), null, "toolbarActions", null, 0, -1, GroupDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getGroupDescription_Children(), this.getFormElementDescription(), null, "children", null, 0, -1, GroupDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getGroupDescription_BorderStyle(), this.getContainerBorderStyle(), null, "borderStyle", null, 0, 1, GroupDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getGroupDescription_ConditionalBorderStyles(), this.getConditionalContainerBorderStyle(), null, "conditionalBorderStyles", null, 0, -1, GroupDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.formElementDescriptionEClass, FormElementDescription.class, "FormElementDescription", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getFormElementDescription_Name(), theViewPackage.getIdentifier(), "name", null, 0, 1, FormElementDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.widgetDescriptionEClass, WidgetDescription.class, "WidgetDescription", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getWidgetDescription_LabelExpression(), theViewPackage.getInterpretedExpression(), "labelExpression", null, 0, 1, WidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getWidgetDescription_HelpExpression(), theViewPackage.getInterpretedExpression(), "helpExpression", null, 0, 1, WidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.barChartDescriptionEClass, BarChartDescription.class, "BarChartDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getBarChartDescription_ValuesExpression(), theViewPackage.getInterpretedExpression(), "valuesExpression", null, 0, 1, BarChartDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getBarChartDescription_KeysExpression(), theViewPackage.getInterpretedExpression(), "keysExpression", null, 0, 1, BarChartDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getBarChartDescription_YAxisLabelExpression(), theViewPackage.getInterpretedExpression(), "yAxisLabelExpression", null, 0, 1, BarChartDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getBarChartDescription_Style(), this.getBarChartDescriptionStyle(), null, "style", null, 0, 1, BarChartDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getBarChartDescription_ConditionalStyles(), this.getConditionalBarChartDescriptionStyle(), null, "conditionalStyles", null, 0, -1, BarChartDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getBarChartDescription_Width(), theViewPackage.getLength(), "width", "500", 1, 1, BarChartDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getBarChartDescription_Height(), theViewPackage.getLength(), "height", "250", 1, 1, BarChartDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.buttonDescriptionEClass, ButtonDescription.class, "ButtonDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getButtonDescription_ButtonLabelExpression(), theViewPackage.getInterpretedExpression(), "buttonLabelExpression", null, 0, 1, ButtonDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getButtonDescription_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, ButtonDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getButtonDescription_ImageExpression(), theViewPackage.getInterpretedExpression(), "imageExpression", null, 0, 1, ButtonDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getButtonDescription_Style(), this.getButtonDescriptionStyle(), null, "style", null, 0, 1, ButtonDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getButtonDescription_ConditionalStyles(), this.getConditionalButtonDescriptionStyle(), null, "conditionalStyles", null, 0, -1, ButtonDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getButtonDescription_IsEnabledExpression(), theViewPackage.getInterpretedExpression(), "IsEnabledExpression", null, 0, 1, ButtonDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.checkboxDescriptionEClass, CheckboxDescription.class, "CheckboxDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getCheckboxDescription_ValueExpression(), theViewPackage.getInterpretedExpression(), "valueExpression", null, 0, 1, CheckboxDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getCheckboxDescription_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, CheckboxDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getCheckboxDescription_Style(), this.getCheckboxDescriptionStyle(), null, "style", null, 0, 1, CheckboxDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getCheckboxDescription_ConditionalStyles(), this.getConditionalCheckboxDescriptionStyle(), null, "conditionalStyles", null, 0, -1, CheckboxDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getCheckboxDescription_IsEnabledExpression(), theViewPackage.getInterpretedExpression(), "IsEnabledExpression", null, 0, 1, CheckboxDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.flexboxContainerDescriptionEClass, FlexboxContainerDescription.class, "FlexboxContainerDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getFlexboxContainerDescription_Children(), this.getFormElementDescription(), null, "children", null, 0, -1, FlexboxContainerDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getFlexboxContainerDescription_FlexDirection(), this.getFlexDirection(), "flexDirection", "row", 1, 1, FlexboxContainerDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getFlexboxContainerDescription_IsEnabledExpression(), theViewPackage.getInterpretedExpression(), "IsEnabledExpression", null, 0, 1, FlexboxContainerDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getFlexboxContainerDescription_BorderStyle(), this.getContainerBorderStyle(), null, "borderStyle", null, 0, 1, FlexboxContainerDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getFlexboxContainerDescription_ConditionalBorderStyles(), this.getConditionalContainerBorderStyle(), null, "conditionalBorderStyles", null, 0, -1,
                FlexboxContainerDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.imageDescriptionEClass, ImageDescription.class, "ImageDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getImageDescription_UrlExpression(), theViewPackage.getInterpretedExpression(), "urlExpression", null, 0, 1, ImageDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getImageDescription_MaxWidthExpression(), theViewPackage.getInterpretedExpression(), "maxWidthExpression", null, 0, 1, ImageDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.labelDescriptionEClass, LabelDescription.class, "LabelDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getLabelDescription_ValueExpression(), theViewPackage.getInterpretedExpression(), "valueExpression", null, 0, 1, LabelDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getLabelDescription_Style(), this.getLabelDescriptionStyle(), null, "style", null, 0, 1, LabelDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getLabelDescription_ConditionalStyles(), this.getConditionalLabelDescriptionStyle(), null, "conditionalStyles", null, 0, -1, LabelDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.linkDescriptionEClass, LinkDescription.class, "LinkDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getLinkDescription_ValueExpression(), theViewPackage.getInterpretedExpression(), "valueExpression", null, 0, 1, LinkDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getLinkDescription_Style(), this.getLinkDescriptionStyle(), null, "style", null, 0, 1, LinkDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getLinkDescription_ConditionalStyles(), this.getConditionalLinkDescriptionStyle(), null, "conditionalStyles", null, 0, -1, LinkDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.listDescriptionEClass, ListDescription.class, "ListDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getListDescription_ValueExpression(), theViewPackage.getInterpretedExpression(), "valueExpression", null, 0, 1, ListDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getListDescription_DisplayExpression(), theViewPackage.getInterpretedExpression(), "displayExpression", null, 0, 1, ListDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getListDescription_IsDeletableExpression(), theViewPackage.getInterpretedExpression(), "isDeletableExpression", null, 0, 1, ListDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getListDescription_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, ListDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getListDescription_Style(), this.getListDescriptionStyle(), null, "style", null, 0, 1, ListDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getListDescription_ConditionalStyles(), this.getConditionalListDescriptionStyle(), null, "conditionalStyles", null, 0, -1, ListDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getListDescription_IsEnabledExpression(), theViewPackage.getInterpretedExpression(), "IsEnabledExpression", null, 0, 1, ListDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.multiSelectDescriptionEClass, MultiSelectDescription.class, "MultiSelectDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getMultiSelectDescription_ValueExpression(), theViewPackage.getInterpretedExpression(), "valueExpression", null, 0, 1, MultiSelectDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getMultiSelectDescription_CandidatesExpression(), theViewPackage.getInterpretedExpression(), "candidatesExpression", null, 0, 1, MultiSelectDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getMultiSelectDescription_CandidateLabelExpression(), theViewPackage.getInterpretedExpression(), "candidateLabelExpression", null, 0, 1, MultiSelectDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getMultiSelectDescription_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, MultiSelectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getMultiSelectDescription_Style(), this.getMultiSelectDescriptionStyle(), null, "style", null, 0, 1, MultiSelectDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getMultiSelectDescription_ConditionalStyles(), this.getConditionalMultiSelectDescriptionStyle(), null, "conditionalStyles", null, 0, -1, MultiSelectDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getMultiSelectDescription_IsEnabledExpression(), theViewPackage.getInterpretedExpression(), "IsEnabledExpression", null, 0, 1, MultiSelectDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.pieChartDescriptionEClass, PieChartDescription.class, "PieChartDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getPieChartDescription_ValuesExpression(), theViewPackage.getInterpretedExpression(), "valuesExpression", null, 0, 1, PieChartDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getPieChartDescription_KeysExpression(), theViewPackage.getInterpretedExpression(), "keysExpression", null, 0, 1, PieChartDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getPieChartDescription_Style(), this.getPieChartDescriptionStyle(), null, "style", null, 0, 1, PieChartDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getPieChartDescription_ConditionalStyles(), this.getConditionalPieChartDescriptionStyle(), null, "conditionalStyles", null, 0, -1, PieChartDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.radioDescriptionEClass, RadioDescription.class, "RadioDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getRadioDescription_ValueExpression(), theViewPackage.getInterpretedExpression(), "valueExpression", null, 0, 1, RadioDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getRadioDescription_CandidatesExpression(), theViewPackage.getInterpretedExpression(), "candidatesExpression", null, 0, 1, RadioDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getRadioDescription_CandidateLabelExpression(), theViewPackage.getInterpretedExpression(), "candidateLabelExpression", null, 0, 1, RadioDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getRadioDescription_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, RadioDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getRadioDescription_Style(), this.getRadioDescriptionStyle(), null, "style", null, 0, 1, RadioDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getRadioDescription_ConditionalStyles(), this.getConditionalRadioDescriptionStyle(), null, "conditionalStyles", null, 0, -1, RadioDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getRadioDescription_IsEnabledExpression(), theViewPackage.getInterpretedExpression(), "IsEnabledExpression", null, 0, 1, RadioDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.richTextDescriptionEClass, RichTextDescription.class, "RichTextDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getRichTextDescription_ValueExpression(), theViewPackage.getInterpretedExpression(), "valueExpression", null, 0, 1, RichTextDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getRichTextDescription_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, RichTextDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getRichTextDescription_IsEnabledExpression(), theViewPackage.getInterpretedExpression(), "IsEnabledExpression", null, 0, 1, RichTextDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.selectDescriptionEClass, SelectDescription.class, "SelectDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getSelectDescription_ValueExpression(), theViewPackage.getInterpretedExpression(), "valueExpression", null, 0, 1, SelectDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getSelectDescription_CandidatesExpression(), theViewPackage.getInterpretedExpression(), "candidatesExpression", null, 0, 1, SelectDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getSelectDescription_CandidateLabelExpression(), theViewPackage.getInterpretedExpression(), "candidateLabelExpression", null, 0, 1, SelectDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getSelectDescription_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, SelectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getSelectDescription_Style(), this.getSelectDescriptionStyle(), null, "style", null, 0, 1, SelectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getSelectDescription_ConditionalStyles(), this.getConditionalSelectDescriptionStyle(), null, "conditionalStyles", null, 0, -1, SelectDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getSelectDescription_IsEnabledExpression(), theViewPackage.getInterpretedExpression(), "IsEnabledExpression", null, 0, 1, SelectDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.splitButtonDescriptionEClass, SplitButtonDescription.class, "SplitButtonDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getSplitButtonDescription_Actions(), this.getButtonDescription(), null, "actions", null, 0, -1, SplitButtonDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getSplitButtonDescription_IsEnabledExpression(), theViewPackage.getInterpretedExpression(), "IsEnabledExpression", null, 0, 1, SplitButtonDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.textAreaDescriptionEClass, TextAreaDescription.class, "TextAreaDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getTextAreaDescription_ValueExpression(), theViewPackage.getInterpretedExpression(), "valueExpression", null, 0, 1, TextAreaDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTextAreaDescription_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, TextAreaDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTextAreaDescription_Style(), this.getTextareaDescriptionStyle(), null, "style", null, 0, 1, TextAreaDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTextAreaDescription_ConditionalStyles(), this.getConditionalTextareaDescriptionStyle(), null, "conditionalStyles", null, 0, -1, TextAreaDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTextAreaDescription_IsEnabledExpression(), theViewPackage.getInterpretedExpression(), "IsEnabledExpression", null, 0, 1, TextAreaDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.textfieldDescriptionEClass, TextfieldDescription.class, "TextfieldDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getTextfieldDescription_ValueExpression(), theViewPackage.getInterpretedExpression(), "valueExpression", null, 0, 1, TextfieldDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTextfieldDescription_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, TextfieldDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTextfieldDescription_Style(), this.getTextfieldDescriptionStyle(), null, "style", null, 0, 1, TextfieldDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTextfieldDescription_ConditionalStyles(), this.getConditionalTextfieldDescriptionStyle(), null, "conditionalStyles", null, 0, -1, TextfieldDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTextfieldDescription_IsEnabledExpression(), theViewPackage.getInterpretedExpression(), "IsEnabledExpression", null, 0, 1, TextfieldDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.treeDescriptionEClass, TreeDescription.class, "TreeDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getTreeDescription_ChildrenExpression(), theViewPackage.getInterpretedExpression(), "childrenExpression", null, 0, 1, TreeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTreeDescription_TreeItemLabelExpression(), theViewPackage.getInterpretedExpression(), "treeItemLabelExpression", null, 0, 1, TreeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTreeDescription_IsTreeItemSelectableExpression(), theViewPackage.getInterpretedExpression(), "isTreeItemSelectableExpression", null, 0, 1, TreeDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTreeDescription_TreeItemBeginIconExpression(), theViewPackage.getInterpretedExpression(), "treeItemBeginIconExpression", null, 0, 1, TreeDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTreeDescription_TreeItemEndIconsExpression(), theViewPackage.getInterpretedExpression(), "treeItemEndIconsExpression", null, 0, 1, TreeDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTreeDescription_IsCheckableExpression(), theViewPackage.getInterpretedExpression(), "isCheckableExpression", null, 0, 1, TreeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTreeDescription_CheckedValueExpression(), theViewPackage.getInterpretedExpression(), "checkedValueExpression", null, 0, 1, TreeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTreeDescription_IsEnabledExpression(), theViewPackage.getInterpretedExpression(), "IsEnabledExpression", null, 0, 1, TreeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTreeDescription_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, TreeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.widgetDescriptionStyleEClass, WidgetDescriptionStyle.class, "WidgetDescriptionStyle", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.barChartDescriptionStyleEClass, BarChartDescriptionStyle.class, "BarChartDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getBarChartDescriptionStyle_BarsColor(), this.ecorePackage.getEString(), "barsColor", null, 0, 1, BarChartDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalBarChartDescriptionStyleEClass, ConditionalBarChartDescriptionStyle.class, "ConditionalBarChartDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.buttonDescriptionStyleEClass, ButtonDescriptionStyle.class, "ButtonDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getButtonDescriptionStyle_BackgroundColor(), theViewPackage.getUserColor(), null, "backgroundColor", null, 0, 1, ButtonDescriptionStyle.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getButtonDescriptionStyle_ForegroundColor(), theViewPackage.getUserColor(), null, "foregroundColor", null, 0, 1, ButtonDescriptionStyle.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalButtonDescriptionStyleEClass, ConditionalButtonDescriptionStyle.class, "ConditionalButtonDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.checkboxDescriptionStyleEClass, CheckboxDescriptionStyle.class, "CheckboxDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getCheckboxDescriptionStyle_Color(), theViewPackage.getUserColor(), null, "color", null, 0, 1, CheckboxDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getCheckboxDescriptionStyle_LabelPlacement(), this.getLabelPlacement(), "labelPlacement", "end", 1, 1, CheckboxDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalCheckboxDescriptionStyleEClass, ConditionalCheckboxDescriptionStyle.class, "ConditionalCheckboxDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.labelDescriptionStyleEClass, LabelDescriptionStyle.class, "LabelDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getLabelDescriptionStyle_Color(), theViewPackage.getUserColor(), null, "color", null, 0, 1, LabelDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalLabelDescriptionStyleEClass, ConditionalLabelDescriptionStyle.class, "ConditionalLabelDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.linkDescriptionStyleEClass, LinkDescriptionStyle.class, "LinkDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getLinkDescriptionStyle_Color(), theViewPackage.getUserColor(), null, "color", null, 0, 1, LinkDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalLinkDescriptionStyleEClass, ConditionalLinkDescriptionStyle.class, "ConditionalLinkDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.listDescriptionStyleEClass, ListDescriptionStyle.class, "ListDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getListDescriptionStyle_Color(), theViewPackage.getUserColor(), null, "color", null, 0, 1, ListDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalListDescriptionStyleEClass, ConditionalListDescriptionStyle.class, "ConditionalListDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.multiSelectDescriptionStyleEClass, MultiSelectDescriptionStyle.class, "MultiSelectDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getMultiSelectDescriptionStyle_BackgroundColor(), theViewPackage.getUserColor(), null, "backgroundColor", null, 0, 1, MultiSelectDescriptionStyle.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getMultiSelectDescriptionStyle_ForegroundColor(), theViewPackage.getUserColor(), null, "foregroundColor", null, 0, 1, MultiSelectDescriptionStyle.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getMultiSelectDescriptionStyle_ShowIcon(), this.ecorePackage.getEBoolean(), "showIcon", null, 0, 1, MultiSelectDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalMultiSelectDescriptionStyleEClass, ConditionalMultiSelectDescriptionStyle.class, "ConditionalMultiSelectDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.pieChartDescriptionStyleEClass, PieChartDescriptionStyle.class, "PieChartDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getPieChartDescriptionStyle_Colors(), this.ecorePackage.getEString(), "colors", null, 0, 1, PieChartDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getPieChartDescriptionStyle_StrokeWidth(), theViewPackage.getLength(), "strokeWidth", null, 0, 1, PieChartDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getPieChartDescriptionStyle_StrokeColor(), theViewPackage.getUserColor(), null, "strokeColor", null, 0, 1, PieChartDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalPieChartDescriptionStyleEClass, ConditionalPieChartDescriptionStyle.class, "ConditionalPieChartDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.radioDescriptionStyleEClass, RadioDescriptionStyle.class, "RadioDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getRadioDescriptionStyle_Color(), theViewPackage.getUserColor(), null, "color", null, 0, 1, RadioDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalRadioDescriptionStyleEClass, ConditionalRadioDescriptionStyle.class, "ConditionalRadioDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.selectDescriptionStyleEClass, SelectDescriptionStyle.class, "SelectDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getSelectDescriptionStyle_BackgroundColor(), theViewPackage.getUserColor(), null, "backgroundColor", null, 0, 1, SelectDescriptionStyle.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getSelectDescriptionStyle_ForegroundColor(), theViewPackage.getUserColor(), null, "foregroundColor", null, 0, 1, SelectDescriptionStyle.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getSelectDescriptionStyle_ShowIcon(), this.ecorePackage.getEBoolean(), "showIcon", null, 0, 1, SelectDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalSelectDescriptionStyleEClass, ConditionalSelectDescriptionStyle.class, "ConditionalSelectDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.textareaDescriptionStyleEClass, TextareaDescriptionStyle.class, "TextareaDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getTextareaDescriptionStyle_BackgroundColor(), theViewPackage.getUserColor(), null, "backgroundColor", null, 0, 1, TextareaDescriptionStyle.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTextareaDescriptionStyle_ForegroundColor(), theViewPackage.getUserColor(), null, "foregroundColor", null, 0, 1, TextareaDescriptionStyle.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalTextareaDescriptionStyleEClass, ConditionalTextareaDescriptionStyle.class, "ConditionalTextareaDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.textfieldDescriptionStyleEClass, TextfieldDescriptionStyle.class, "TextfieldDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getTextfieldDescriptionStyle_BackgroundColor(), theViewPackage.getUserColor(), null, "backgroundColor", null, 0, 1, TextfieldDescriptionStyle.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTextfieldDescriptionStyle_ForegroundColor(), theViewPackage.getUserColor(), null, "foregroundColor", null, 0, 1, TextfieldDescriptionStyle.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalTextfieldDescriptionStyleEClass, ConditionalTextfieldDescriptionStyle.class, "ConditionalTextfieldDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.containerBorderStyleEClass, ContainerBorderStyle.class, "ContainerBorderStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getContainerBorderStyle_BorderColor(), theViewPackage.getUserColor(), null, "borderColor", null, 1, 1, ContainerBorderStyle.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getContainerBorderStyle_BorderRadius(), theViewPackage.getLength(), "borderRadius", "3", 1, 1, ContainerBorderStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getContainerBorderStyle_BorderSize(), theViewPackage.getLength(), "borderSize", "1", 1, 1, ContainerBorderStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getContainerBorderStyle_BorderLineStyle(), this.getContainerBorderLineStyle(), "borderLineStyle", null, 0, 1, ContainerBorderStyle.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalContainerBorderStyleEClass, ConditionalContainerBorderStyle.class, "ConditionalContainerBorderStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.formElementForEClass, FormElementFor.class, "FormElementFor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getFormElementFor_Iterator(), this.ecorePackage.getEString(), "iterator", "it", 1, 1, FormElementFor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getFormElementFor_IterableExpression(), theViewPackage.getInterpretedExpression(), "iterableExpression", null, 1, 1, FormElementFor.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getFormElementFor_Children(), this.getFormElementDescription(), null, "children", null, 0, -1, FormElementFor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.formElementIfEClass, FormElementIf.class, "FormElementIf", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getFormElementIf_PredicateExpression(), theViewPackage.getInterpretedExpression(), "predicateExpression", null, 1, 1, FormElementIf.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getFormElementIf_Children(), this.getFormElementDescription(), null, "children", null, 0, -1, FormElementIf.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Initialize enums and add enum literals
        this.initEEnum(this.flexDirectionEEnum, FlexDirection.class, "FlexDirection");
        this.addEEnumLiteral(this.flexDirectionEEnum, FlexDirection.ROW);
        this.addEEnumLiteral(this.flexDirectionEEnum, FlexDirection.ROW_REVERSE);
        this.addEEnumLiteral(this.flexDirectionEEnum, FlexDirection.COLUMN);
        this.addEEnumLiteral(this.flexDirectionEEnum, FlexDirection.COLUMN_REVERSE);

        this.initEEnum(this.groupDisplayModeEEnum, GroupDisplayMode.class, "GroupDisplayMode");
        this.addEEnumLiteral(this.groupDisplayModeEEnum, GroupDisplayMode.LIST);
        this.addEEnumLiteral(this.groupDisplayModeEEnum, GroupDisplayMode.TOGGLEABLE_AREAS);

        this.initEEnum(this.labelPlacementEEnum, LabelPlacement.class, "LabelPlacement");
        this.addEEnumLiteral(this.labelPlacementEEnum, LabelPlacement.END);
        this.addEEnumLiteral(this.labelPlacementEEnum, LabelPlacement.TOP);
        this.addEEnumLiteral(this.labelPlacementEEnum, LabelPlacement.START);
        this.addEEnumLiteral(this.labelPlacementEEnum, LabelPlacement.BOTTOM);

        this.initEEnum(this.containerBorderLineStyleEEnum, ContainerBorderLineStyle.class, "ContainerBorderLineStyle");
        this.addEEnumLiteral(this.containerBorderLineStyleEEnum, ContainerBorderLineStyle.SOLID);
        this.addEEnumLiteral(this.containerBorderLineStyleEEnum, ContainerBorderLineStyle.DASHED);
        this.addEEnumLiteral(this.containerBorderLineStyleEEnum, ContainerBorderLineStyle.DOTTED);

        // Create resource
        this.createResource(eNS_URI);
    }

} // FormPackageImpl
