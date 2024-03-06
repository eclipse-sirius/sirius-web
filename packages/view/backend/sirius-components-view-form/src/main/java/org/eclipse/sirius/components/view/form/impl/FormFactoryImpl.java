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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
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

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class FormFactoryImpl extends EFactoryImpl implements FormFactory {
    /**
     * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static FormFactory init() {
        try {
            FormFactory theFormFactory = (FormFactory) EPackage.Registry.INSTANCE.getEFactory(FormPackage.eNS_URI);
            if (theFormFactory != null) {
                return theFormFactory;
            }
        } catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new FormFactoryImpl();
    }

    /**
     * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public FormFactoryImpl() {
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
            case FormPackage.FORM_DESCRIPTION:
                return this.createFormDescription();
            case FormPackage.FORM_VARIABLE:
                return this.createFormVariable();
            case FormPackage.PAGE_DESCRIPTION:
                return this.createPageDescription();
            case FormPackage.GROUP_DESCRIPTION:
                return this.createGroupDescription();
            case FormPackage.BAR_CHART_DESCRIPTION:
                return this.createBarChartDescription();
            case FormPackage.BUTTON_DESCRIPTION:
                return this.createButtonDescription();
            case FormPackage.CHECKBOX_DESCRIPTION:
                return this.createCheckboxDescription();
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION:
                return this.createFlexboxContainerDescription();
            case FormPackage.IMAGE_DESCRIPTION:
                return this.createImageDescription();
            case FormPackage.LABEL_DESCRIPTION:
                return this.createLabelDescription();
            case FormPackage.LINK_DESCRIPTION:
                return this.createLinkDescription();
            case FormPackage.LIST_DESCRIPTION:
                return this.createListDescription();
            case FormPackage.MULTI_SELECT_DESCRIPTION:
                return this.createMultiSelectDescription();
            case FormPackage.PIE_CHART_DESCRIPTION:
                return this.createPieChartDescription();
            case FormPackage.RADIO_DESCRIPTION:
                return this.createRadioDescription();
            case FormPackage.RICH_TEXT_DESCRIPTION:
                return this.createRichTextDescription();
            case FormPackage.SELECT_DESCRIPTION:
                return this.createSelectDescription();
            case FormPackage.SPLIT_BUTTON_DESCRIPTION:
                return this.createSplitButtonDescription();
            case FormPackage.TEXT_AREA_DESCRIPTION:
                return this.createTextAreaDescription();
            case FormPackage.TEXTFIELD_DESCRIPTION:
                return this.createTextfieldDescription();
            case FormPackage.TREE_DESCRIPTION:
                return this.createTreeDescription();
            case FormPackage.BAR_CHART_DESCRIPTION_STYLE:
                return this.createBarChartDescriptionStyle();
            case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE:
                return this.createConditionalBarChartDescriptionStyle();
            case FormPackage.BUTTON_DESCRIPTION_STYLE:
                return this.createButtonDescriptionStyle();
            case FormPackage.CONDITIONAL_BUTTON_DESCRIPTION_STYLE:
                return this.createConditionalButtonDescriptionStyle();
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE:
                return this.createCheckboxDescriptionStyle();
            case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE:
                return this.createConditionalCheckboxDescriptionStyle();
            case FormPackage.LABEL_DESCRIPTION_STYLE:
                return this.createLabelDescriptionStyle();
            case FormPackage.CONDITIONAL_LABEL_DESCRIPTION_STYLE:
                return this.createConditionalLabelDescriptionStyle();
            case FormPackage.LINK_DESCRIPTION_STYLE:
                return this.createLinkDescriptionStyle();
            case FormPackage.CONDITIONAL_LINK_DESCRIPTION_STYLE:
                return this.createConditionalLinkDescriptionStyle();
            case FormPackage.LIST_DESCRIPTION_STYLE:
                return this.createListDescriptionStyle();
            case FormPackage.CONDITIONAL_LIST_DESCRIPTION_STYLE:
                return this.createConditionalListDescriptionStyle();
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE:
                return this.createMultiSelectDescriptionStyle();
            case FormPackage.CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE:
                return this.createConditionalMultiSelectDescriptionStyle();
            case FormPackage.PIE_CHART_DESCRIPTION_STYLE:
                return this.createPieChartDescriptionStyle();
            case FormPackage.CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE:
                return this.createConditionalPieChartDescriptionStyle();
            case FormPackage.RADIO_DESCRIPTION_STYLE:
                return this.createRadioDescriptionStyle();
            case FormPackage.CONDITIONAL_RADIO_DESCRIPTION_STYLE:
                return this.createConditionalRadioDescriptionStyle();
            case FormPackage.SELECT_DESCRIPTION_STYLE:
                return this.createSelectDescriptionStyle();
            case FormPackage.CONDITIONAL_SELECT_DESCRIPTION_STYLE:
                return this.createConditionalSelectDescriptionStyle();
            case FormPackage.TEXTAREA_DESCRIPTION_STYLE:
                return this.createTextareaDescriptionStyle();
            case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE:
                return this.createConditionalTextareaDescriptionStyle();
            case FormPackage.TEXTFIELD_DESCRIPTION_STYLE:
                return this.createTextfieldDescriptionStyle();
            case FormPackage.CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE:
                return this.createConditionalTextfieldDescriptionStyle();
            case FormPackage.CONTAINER_BORDER_STYLE:
                return this.createContainerBorderStyle();
            case FormPackage.CONDITIONAL_CONTAINER_BORDER_STYLE:
                return this.createConditionalContainerBorderStyle();
            case FormPackage.FORM_ELEMENT_FOR:
                return this.createFormElementFor();
            case FormPackage.FORM_ELEMENT_IF:
                return this.createFormElementIf();
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
    public Object createFromString(EDataType eDataType, String initialValue) {
        switch (eDataType.getClassifierID()) {
            case FormPackage.FLEX_DIRECTION:
                return this.createFlexDirectionFromString(eDataType, initialValue);
            case FormPackage.GROUP_DISPLAY_MODE:
                return this.createGroupDisplayModeFromString(eDataType, initialValue);
            case FormPackage.LABEL_PLACEMENT:
                return this.createLabelPlacementFromString(eDataType, initialValue);
            case FormPackage.CONTAINER_BORDER_LINE_STYLE:
                return this.createContainerBorderLineStyleFromString(eDataType, initialValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue) {
        switch (eDataType.getClassifierID()) {
            case FormPackage.FLEX_DIRECTION:
                return this.convertFlexDirectionToString(eDataType, instanceValue);
            case FormPackage.GROUP_DISPLAY_MODE:
                return this.convertGroupDisplayModeToString(eDataType, instanceValue);
            case FormPackage.LABEL_PLACEMENT:
                return this.convertLabelPlacementToString(eDataType, instanceValue);
            case FormPackage.CONTAINER_BORDER_LINE_STYLE:
                return this.convertContainerBorderLineStyleToString(eDataType, instanceValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public FormDescription createFormDescription() {
        FormDescriptionImpl formDescription = new FormDescriptionImpl();
        return formDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public FormVariable createFormVariable() {
        FormVariableImpl formVariable = new FormVariableImpl();
        return formVariable;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public PageDescription createPageDescription() {
        PageDescriptionImpl pageDescription = new PageDescriptionImpl();
        return pageDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public GroupDescription createGroupDescription() {
        GroupDescriptionImpl groupDescription = new GroupDescriptionImpl();
        return groupDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public BarChartDescription createBarChartDescription() {
        BarChartDescriptionImpl barChartDescription = new BarChartDescriptionImpl();
        return barChartDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public SplitButtonDescription createSplitButtonDescription() {
        SplitButtonDescriptionImpl splitButtonDescription = new SplitButtonDescriptionImpl();
        return splitButtonDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ButtonDescription createButtonDescription() {
        ButtonDescriptionImpl buttonDescription = new ButtonDescriptionImpl();
        return buttonDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CheckboxDescription createCheckboxDescription() {
        CheckboxDescriptionImpl checkboxDescription = new CheckboxDescriptionImpl();
        return checkboxDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public FlexboxContainerDescription createFlexboxContainerDescription() {
        FlexboxContainerDescriptionImpl flexboxContainerDescription = new FlexboxContainerDescriptionImpl();
        return flexboxContainerDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ImageDescription createImageDescription() {
        ImageDescriptionImpl imageDescription = new ImageDescriptionImpl();
        return imageDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LabelDescription createLabelDescription() {
        LabelDescriptionImpl labelDescription = new LabelDescriptionImpl();
        return labelDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LinkDescription createLinkDescription() {
        LinkDescriptionImpl linkDescription = new LinkDescriptionImpl();
        return linkDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ListDescription createListDescription() {
        ListDescriptionImpl listDescription = new ListDescriptionImpl();
        return listDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public MultiSelectDescription createMultiSelectDescription() {
        MultiSelectDescriptionImpl multiSelectDescription = new MultiSelectDescriptionImpl();
        return multiSelectDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public TreeDescription createTreeDescription() {
        TreeDescriptionImpl treeDescription = new TreeDescriptionImpl();
        return treeDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public PieChartDescription createPieChartDescription() {
        PieChartDescriptionImpl pieChartDescription = new PieChartDescriptionImpl();
        return pieChartDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public RadioDescription createRadioDescription() {
        RadioDescriptionImpl radioDescription = new RadioDescriptionImpl();
        return radioDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public RichTextDescription createRichTextDescription() {
        RichTextDescriptionImpl richTextDescription = new RichTextDescriptionImpl();
        return richTextDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public SelectDescription createSelectDescription() {
        SelectDescriptionImpl selectDescription = new SelectDescriptionImpl();
        return selectDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public TextAreaDescription createTextAreaDescription() {
        TextAreaDescriptionImpl textAreaDescription = new TextAreaDescriptionImpl();
        return textAreaDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public TextfieldDescription createTextfieldDescription() {
        TextfieldDescriptionImpl textfieldDescription = new TextfieldDescriptionImpl();
        return textfieldDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public BarChartDescriptionStyle createBarChartDescriptionStyle() {
        BarChartDescriptionStyleImpl barChartDescriptionStyle = new BarChartDescriptionStyleImpl();
        return barChartDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ConditionalBarChartDescriptionStyle createConditionalBarChartDescriptionStyle() {
        ConditionalBarChartDescriptionStyleImpl conditionalBarChartDescriptionStyle = new ConditionalBarChartDescriptionStyleImpl();
        return conditionalBarChartDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ButtonDescriptionStyle createButtonDescriptionStyle() {
        ButtonDescriptionStyleImpl buttonDescriptionStyle = new ButtonDescriptionStyleImpl();
        return buttonDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ConditionalButtonDescriptionStyle createConditionalButtonDescriptionStyle() {
        ConditionalButtonDescriptionStyleImpl conditionalButtonDescriptionStyle = new ConditionalButtonDescriptionStyleImpl();
        return conditionalButtonDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CheckboxDescriptionStyle createCheckboxDescriptionStyle() {
        CheckboxDescriptionStyleImpl checkboxDescriptionStyle = new CheckboxDescriptionStyleImpl();
        return checkboxDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ConditionalCheckboxDescriptionStyle createConditionalCheckboxDescriptionStyle() {
        ConditionalCheckboxDescriptionStyleImpl conditionalCheckboxDescriptionStyle = new ConditionalCheckboxDescriptionStyleImpl();
        return conditionalCheckboxDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LabelDescriptionStyle createLabelDescriptionStyle() {
        LabelDescriptionStyleImpl labelDescriptionStyle = new LabelDescriptionStyleImpl();
        return labelDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ConditionalLabelDescriptionStyle createConditionalLabelDescriptionStyle() {
        ConditionalLabelDescriptionStyleImpl conditionalLabelDescriptionStyle = new ConditionalLabelDescriptionStyleImpl();
        return conditionalLabelDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LinkDescriptionStyle createLinkDescriptionStyle() {
        LinkDescriptionStyleImpl linkDescriptionStyle = new LinkDescriptionStyleImpl();
        return linkDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ConditionalLinkDescriptionStyle createConditionalLinkDescriptionStyle() {
        ConditionalLinkDescriptionStyleImpl conditionalLinkDescriptionStyle = new ConditionalLinkDescriptionStyleImpl();
        return conditionalLinkDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ListDescriptionStyle createListDescriptionStyle() {
        ListDescriptionStyleImpl listDescriptionStyle = new ListDescriptionStyleImpl();
        return listDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ConditionalListDescriptionStyle createConditionalListDescriptionStyle() {
        ConditionalListDescriptionStyleImpl conditionalListDescriptionStyle = new ConditionalListDescriptionStyleImpl();
        return conditionalListDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public MultiSelectDescriptionStyle createMultiSelectDescriptionStyle() {
        MultiSelectDescriptionStyleImpl multiSelectDescriptionStyle = new MultiSelectDescriptionStyleImpl();
        return multiSelectDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ConditionalMultiSelectDescriptionStyle createConditionalMultiSelectDescriptionStyle() {
        ConditionalMultiSelectDescriptionStyleImpl conditionalMultiSelectDescriptionStyle = new ConditionalMultiSelectDescriptionStyleImpl();
        return conditionalMultiSelectDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public PieChartDescriptionStyle createPieChartDescriptionStyle() {
        PieChartDescriptionStyleImpl pieChartDescriptionStyle = new PieChartDescriptionStyleImpl();
        return pieChartDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ConditionalPieChartDescriptionStyle createConditionalPieChartDescriptionStyle() {
        ConditionalPieChartDescriptionStyleImpl conditionalPieChartDescriptionStyle = new ConditionalPieChartDescriptionStyleImpl();
        return conditionalPieChartDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public RadioDescriptionStyle createRadioDescriptionStyle() {
        RadioDescriptionStyleImpl radioDescriptionStyle = new RadioDescriptionStyleImpl();
        return radioDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ConditionalRadioDescriptionStyle createConditionalRadioDescriptionStyle() {
        ConditionalRadioDescriptionStyleImpl conditionalRadioDescriptionStyle = new ConditionalRadioDescriptionStyleImpl();
        return conditionalRadioDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public SelectDescriptionStyle createSelectDescriptionStyle() {
        SelectDescriptionStyleImpl selectDescriptionStyle = new SelectDescriptionStyleImpl();
        return selectDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ConditionalSelectDescriptionStyle createConditionalSelectDescriptionStyle() {
        ConditionalSelectDescriptionStyleImpl conditionalSelectDescriptionStyle = new ConditionalSelectDescriptionStyleImpl();
        return conditionalSelectDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public TextareaDescriptionStyle createTextareaDescriptionStyle() {
        TextareaDescriptionStyleImpl textareaDescriptionStyle = new TextareaDescriptionStyleImpl();
        return textareaDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ConditionalTextareaDescriptionStyle createConditionalTextareaDescriptionStyle() {
        ConditionalTextareaDescriptionStyleImpl conditionalTextareaDescriptionStyle = new ConditionalTextareaDescriptionStyleImpl();
        return conditionalTextareaDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public TextfieldDescriptionStyle createTextfieldDescriptionStyle() {
        TextfieldDescriptionStyleImpl textfieldDescriptionStyle = new TextfieldDescriptionStyleImpl();
        return textfieldDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ConditionalTextfieldDescriptionStyle createConditionalTextfieldDescriptionStyle() {
        ConditionalTextfieldDescriptionStyleImpl conditionalTextfieldDescriptionStyle = new ConditionalTextfieldDescriptionStyleImpl();
        return conditionalTextfieldDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ContainerBorderStyle createContainerBorderStyle() {
        ContainerBorderStyleImpl containerBorderStyle = new ContainerBorderStyleImpl();
        return containerBorderStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ConditionalContainerBorderStyle createConditionalContainerBorderStyle() {
        ConditionalContainerBorderStyleImpl conditionalContainerBorderStyle = new ConditionalContainerBorderStyleImpl();
        return conditionalContainerBorderStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public FormElementFor createFormElementFor() {
        FormElementForImpl formElementFor = new FormElementForImpl();
        return formElementFor;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public FormElementIf createFormElementIf() {
        FormElementIfImpl formElementIf = new FormElementIfImpl();
        return formElementIf;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public FlexDirection createFlexDirectionFromString(EDataType eDataType, String initialValue) {
        FlexDirection result = FlexDirection.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String convertFlexDirectionToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public GroupDisplayMode createGroupDisplayModeFromString(EDataType eDataType, String initialValue) {
        GroupDisplayMode result = GroupDisplayMode.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String convertGroupDisplayModeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public LabelPlacement createLabelPlacementFromString(EDataType eDataType, String initialValue) {
        LabelPlacement result = LabelPlacement.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String convertLabelPlacementToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ContainerBorderLineStyle createContainerBorderLineStyleFromString(EDataType eDataType, String initialValue) {
        ContainerBorderLineStyle result = ContainerBorderLineStyle.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String convertContainerBorderLineStyleToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public FormPackage getFormPackage() {
        return (FormPackage) this.getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @deprecated
     * @generated
     */
    @Deprecated
    public static FormPackage getPackage() {
        return FormPackage.eINSTANCE;
    }

} // FormFactoryImpl
