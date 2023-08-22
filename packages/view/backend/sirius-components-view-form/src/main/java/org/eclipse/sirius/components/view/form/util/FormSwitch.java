/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
package org.eclipse.sirius.components.view.form.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.sirius.components.view.Conditional;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.RepresentationDescription;
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
import org.eclipse.sirius.components.view.form.ContainerBorderStyle;
import org.eclipse.sirius.components.view.form.FlexboxContainerDescription;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.form.FormElementDescription;
import org.eclipse.sirius.components.view.form.FormElementFor;
import org.eclipse.sirius.components.view.form.FormElementIf;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.ImageDescription;
import org.eclipse.sirius.components.view.form.LabelDescription;
import org.eclipse.sirius.components.view.form.LabelDescriptionStyle;
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
import org.eclipse.sirius.components.view.form.TextAreaDescription;
import org.eclipse.sirius.components.view.form.TextareaDescriptionStyle;
import org.eclipse.sirius.components.view.form.TextfieldDescription;
import org.eclipse.sirius.components.view.form.TextfieldDescriptionStyle;
import org.eclipse.sirius.components.view.form.WidgetDescription;
import org.eclipse.sirius.components.view.form.WidgetDescriptionStyle;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the inheritance hierarchy until a non-null result is
 * returned, which is the result of the switch. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.form.FormPackage
 * @generated
 */
public class FormSwitch<T> extends Switch<T> {
    /**
     * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected static FormPackage modelPackage;

    /**
     * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public FormSwitch() {
        if (modelPackage == null) {
            modelPackage = FormPackage.eINSTANCE;
        }
    }

    /**
     * Checks whether this is a switch for the given package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param ePackage
     *            the package in question.
     * @return whether this is a switch for the given package.
     * @generated
     */
    @Override
    protected boolean isSwitchFor(EPackage ePackage) {
        return ePackage == modelPackage;
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that
     * result. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    @Override
    protected T doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
            case FormPackage.FORM_DESCRIPTION: {
                FormDescription formDescription = (FormDescription) theEObject;
                T result = this.caseFormDescription(formDescription);
                if (result == null)
                    result = this.caseRepresentationDescription(formDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.PAGE_DESCRIPTION: {
                PageDescription pageDescription = (PageDescription) theEObject;
                T result = this.casePageDescription(pageDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.GROUP_DESCRIPTION: {
                GroupDescription groupDescription = (GroupDescription) theEObject;
                T result = this.caseGroupDescription(groupDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.FORM_ELEMENT_DESCRIPTION: {
                FormElementDescription formElementDescription = (FormElementDescription) theEObject;
                T result = this.caseFormElementDescription(formElementDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.WIDGET_DESCRIPTION: {
                WidgetDescription widgetDescription = (WidgetDescription) theEObject;
                T result = this.caseWidgetDescription(widgetDescription);
                if (result == null)
                    result = this.caseFormElementDescription(widgetDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.BAR_CHART_DESCRIPTION: {
                BarChartDescription barChartDescription = (BarChartDescription) theEObject;
                T result = this.caseBarChartDescription(barChartDescription);
                if (result == null)
                    result = this.caseWidgetDescription(barChartDescription);
                if (result == null)
                    result = this.caseFormElementDescription(barChartDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.BUTTON_DESCRIPTION: {
                ButtonDescription buttonDescription = (ButtonDescription) theEObject;
                T result = this.caseButtonDescription(buttonDescription);
                if (result == null)
                    result = this.caseWidgetDescription(buttonDescription);
                if (result == null)
                    result = this.caseFormElementDescription(buttonDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.CHECKBOX_DESCRIPTION: {
                CheckboxDescription checkboxDescription = (CheckboxDescription) theEObject;
                T result = this.caseCheckboxDescription(checkboxDescription);
                if (result == null)
                    result = this.caseWidgetDescription(checkboxDescription);
                if (result == null)
                    result = this.caseFormElementDescription(checkboxDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION: {
                FlexboxContainerDescription flexboxContainerDescription = (FlexboxContainerDescription) theEObject;
                T result = this.caseFlexboxContainerDescription(flexboxContainerDescription);
                if (result == null)
                    result = this.caseWidgetDescription(flexboxContainerDescription);
                if (result == null)
                    result = this.caseFormElementDescription(flexboxContainerDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.IMAGE_DESCRIPTION: {
                ImageDescription imageDescription = (ImageDescription) theEObject;
                T result = this.caseImageDescription(imageDescription);
                if (result == null)
                    result = this.caseWidgetDescription(imageDescription);
                if (result == null)
                    result = this.caseFormElementDescription(imageDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.LABEL_DESCRIPTION: {
                LabelDescription labelDescription = (LabelDescription) theEObject;
                T result = this.caseLabelDescription(labelDescription);
                if (result == null)
                    result = this.caseWidgetDescription(labelDescription);
                if (result == null)
                    result = this.caseFormElementDescription(labelDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.LINK_DESCRIPTION: {
                LinkDescription linkDescription = (LinkDescription) theEObject;
                T result = this.caseLinkDescription(linkDescription);
                if (result == null)
                    result = this.caseWidgetDescription(linkDescription);
                if (result == null)
                    result = this.caseFormElementDescription(linkDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.LIST_DESCRIPTION: {
                ListDescription listDescription = (ListDescription) theEObject;
                T result = this.caseListDescription(listDescription);
                if (result == null)
                    result = this.caseWidgetDescription(listDescription);
                if (result == null)
                    result = this.caseFormElementDescription(listDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.MULTI_SELECT_DESCRIPTION: {
                MultiSelectDescription multiSelectDescription = (MultiSelectDescription) theEObject;
                T result = this.caseMultiSelectDescription(multiSelectDescription);
                if (result == null)
                    result = this.caseWidgetDescription(multiSelectDescription);
                if (result == null)
                    result = this.caseFormElementDescription(multiSelectDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.PIE_CHART_DESCRIPTION: {
                PieChartDescription pieChartDescription = (PieChartDescription) theEObject;
                T result = this.casePieChartDescription(pieChartDescription);
                if (result == null)
                    result = this.caseWidgetDescription(pieChartDescription);
                if (result == null)
                    result = this.caseFormElementDescription(pieChartDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.RADIO_DESCRIPTION: {
                RadioDescription radioDescription = (RadioDescription) theEObject;
                T result = this.caseRadioDescription(radioDescription);
                if (result == null)
                    result = this.caseWidgetDescription(radioDescription);
                if (result == null)
                    result = this.caseFormElementDescription(radioDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.RICH_TEXT_DESCRIPTION: {
                RichTextDescription richTextDescription = (RichTextDescription) theEObject;
                T result = this.caseRichTextDescription(richTextDescription);
                if (result == null)
                    result = this.caseWidgetDescription(richTextDescription);
                if (result == null)
                    result = this.caseFormElementDescription(richTextDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.SELECT_DESCRIPTION: {
                SelectDescription selectDescription = (SelectDescription) theEObject;
                T result = this.caseSelectDescription(selectDescription);
                if (result == null)
                    result = this.caseWidgetDescription(selectDescription);
                if (result == null)
                    result = this.caseFormElementDescription(selectDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.TEXT_AREA_DESCRIPTION: {
                TextAreaDescription textAreaDescription = (TextAreaDescription) theEObject;
                T result = this.caseTextAreaDescription(textAreaDescription);
                if (result == null)
                    result = this.caseWidgetDescription(textAreaDescription);
                if (result == null)
                    result = this.caseFormElementDescription(textAreaDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.TEXTFIELD_DESCRIPTION: {
                TextfieldDescription textfieldDescription = (TextfieldDescription) theEObject;
                T result = this.caseTextfieldDescription(textfieldDescription);
                if (result == null)
                    result = this.caseWidgetDescription(textfieldDescription);
                if (result == null)
                    result = this.caseFormElementDescription(textfieldDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.WIDGET_DESCRIPTION_STYLE: {
                WidgetDescriptionStyle widgetDescriptionStyle = (WidgetDescriptionStyle) theEObject;
                T result = this.caseWidgetDescriptionStyle(widgetDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.BAR_CHART_DESCRIPTION_STYLE: {
                BarChartDescriptionStyle barChartDescriptionStyle = (BarChartDescriptionStyle) theEObject;
                T result = this.caseBarChartDescriptionStyle(barChartDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(barChartDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(barChartDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE: {
                ConditionalBarChartDescriptionStyle conditionalBarChartDescriptionStyle = (ConditionalBarChartDescriptionStyle) theEObject;
                T result = this.caseConditionalBarChartDescriptionStyle(conditionalBarChartDescriptionStyle);
                if (result == null)
                    result = this.caseConditional(conditionalBarChartDescriptionStyle);
                if (result == null)
                    result = this.caseBarChartDescriptionStyle(conditionalBarChartDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(conditionalBarChartDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(conditionalBarChartDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.BUTTON_DESCRIPTION_STYLE: {
                ButtonDescriptionStyle buttonDescriptionStyle = (ButtonDescriptionStyle) theEObject;
                T result = this.caseButtonDescriptionStyle(buttonDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(buttonDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(buttonDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.CONDITIONAL_BUTTON_DESCRIPTION_STYLE: {
                ConditionalButtonDescriptionStyle conditionalButtonDescriptionStyle = (ConditionalButtonDescriptionStyle) theEObject;
                T result = this.caseConditionalButtonDescriptionStyle(conditionalButtonDescriptionStyle);
                if (result == null)
                    result = this.caseConditional(conditionalButtonDescriptionStyle);
                if (result == null)
                    result = this.caseButtonDescriptionStyle(conditionalButtonDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(conditionalButtonDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(conditionalButtonDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE: {
                CheckboxDescriptionStyle checkboxDescriptionStyle = (CheckboxDescriptionStyle) theEObject;
                T result = this.caseCheckboxDescriptionStyle(checkboxDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(checkboxDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE: {
                ConditionalCheckboxDescriptionStyle conditionalCheckboxDescriptionStyle = (ConditionalCheckboxDescriptionStyle) theEObject;
                T result = this.caseConditionalCheckboxDescriptionStyle(conditionalCheckboxDescriptionStyle);
                if (result == null)
                    result = this.caseConditional(conditionalCheckboxDescriptionStyle);
                if (result == null)
                    result = this.caseCheckboxDescriptionStyle(conditionalCheckboxDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(conditionalCheckboxDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.LABEL_DESCRIPTION_STYLE: {
                LabelDescriptionStyle labelDescriptionStyle = (LabelDescriptionStyle) theEObject;
                T result = this.caseLabelDescriptionStyle(labelDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(labelDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(labelDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.CONDITIONAL_LABEL_DESCRIPTION_STYLE: {
                ConditionalLabelDescriptionStyle conditionalLabelDescriptionStyle = (ConditionalLabelDescriptionStyle) theEObject;
                T result = this.caseConditionalLabelDescriptionStyle(conditionalLabelDescriptionStyle);
                if (result == null)
                    result = this.caseConditional(conditionalLabelDescriptionStyle);
                if (result == null)
                    result = this.caseLabelDescriptionStyle(conditionalLabelDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(conditionalLabelDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(conditionalLabelDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.LINK_DESCRIPTION_STYLE: {
                LinkDescriptionStyle linkDescriptionStyle = (LinkDescriptionStyle) theEObject;
                T result = this.caseLinkDescriptionStyle(linkDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(linkDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(linkDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.CONDITIONAL_LINK_DESCRIPTION_STYLE: {
                ConditionalLinkDescriptionStyle conditionalLinkDescriptionStyle = (ConditionalLinkDescriptionStyle) theEObject;
                T result = this.caseConditionalLinkDescriptionStyle(conditionalLinkDescriptionStyle);
                if (result == null)
                    result = this.caseConditional(conditionalLinkDescriptionStyle);
                if (result == null)
                    result = this.caseLinkDescriptionStyle(conditionalLinkDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(conditionalLinkDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(conditionalLinkDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.LIST_DESCRIPTION_STYLE: {
                ListDescriptionStyle listDescriptionStyle = (ListDescriptionStyle) theEObject;
                T result = this.caseListDescriptionStyle(listDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(listDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(listDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.CONDITIONAL_LIST_DESCRIPTION_STYLE: {
                ConditionalListDescriptionStyle conditionalListDescriptionStyle = (ConditionalListDescriptionStyle) theEObject;
                T result = this.caseConditionalListDescriptionStyle(conditionalListDescriptionStyle);
                if (result == null)
                    result = this.caseConditional(conditionalListDescriptionStyle);
                if (result == null)
                    result = this.caseListDescriptionStyle(conditionalListDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(conditionalListDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(conditionalListDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE: {
                MultiSelectDescriptionStyle multiSelectDescriptionStyle = (MultiSelectDescriptionStyle) theEObject;
                T result = this.caseMultiSelectDescriptionStyle(multiSelectDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(multiSelectDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(multiSelectDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE: {
                ConditionalMultiSelectDescriptionStyle conditionalMultiSelectDescriptionStyle = (ConditionalMultiSelectDescriptionStyle) theEObject;
                T result = this.caseConditionalMultiSelectDescriptionStyle(conditionalMultiSelectDescriptionStyle);
                if (result == null)
                    result = this.caseConditional(conditionalMultiSelectDescriptionStyle);
                if (result == null)
                    result = this.caseMultiSelectDescriptionStyle(conditionalMultiSelectDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(conditionalMultiSelectDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(conditionalMultiSelectDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.PIE_CHART_DESCRIPTION_STYLE: {
                PieChartDescriptionStyle pieChartDescriptionStyle = (PieChartDescriptionStyle) theEObject;
                T result = this.casePieChartDescriptionStyle(pieChartDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(pieChartDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(pieChartDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE: {
                ConditionalPieChartDescriptionStyle conditionalPieChartDescriptionStyle = (ConditionalPieChartDescriptionStyle) theEObject;
                T result = this.caseConditionalPieChartDescriptionStyle(conditionalPieChartDescriptionStyle);
                if (result == null)
                    result = this.caseConditional(conditionalPieChartDescriptionStyle);
                if (result == null)
                    result = this.casePieChartDescriptionStyle(conditionalPieChartDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(conditionalPieChartDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(conditionalPieChartDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.RADIO_DESCRIPTION_STYLE: {
                RadioDescriptionStyle radioDescriptionStyle = (RadioDescriptionStyle) theEObject;
                T result = this.caseRadioDescriptionStyle(radioDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(radioDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(radioDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.CONDITIONAL_RADIO_DESCRIPTION_STYLE: {
                ConditionalRadioDescriptionStyle conditionalRadioDescriptionStyle = (ConditionalRadioDescriptionStyle) theEObject;
                T result = this.caseConditionalRadioDescriptionStyle(conditionalRadioDescriptionStyle);
                if (result == null)
                    result = this.caseConditional(conditionalRadioDescriptionStyle);
                if (result == null)
                    result = this.caseRadioDescriptionStyle(conditionalRadioDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(conditionalRadioDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(conditionalRadioDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.SELECT_DESCRIPTION_STYLE: {
                SelectDescriptionStyle selectDescriptionStyle = (SelectDescriptionStyle) theEObject;
                T result = this.caseSelectDescriptionStyle(selectDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(selectDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(selectDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.CONDITIONAL_SELECT_DESCRIPTION_STYLE: {
                ConditionalSelectDescriptionStyle conditionalSelectDescriptionStyle = (ConditionalSelectDescriptionStyle) theEObject;
                T result = this.caseConditionalSelectDescriptionStyle(conditionalSelectDescriptionStyle);
                if (result == null)
                    result = this.caseConditional(conditionalSelectDescriptionStyle);
                if (result == null)
                    result = this.caseSelectDescriptionStyle(conditionalSelectDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(conditionalSelectDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(conditionalSelectDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.TEXTAREA_DESCRIPTION_STYLE: {
                TextareaDescriptionStyle textareaDescriptionStyle = (TextareaDescriptionStyle) theEObject;
                T result = this.caseTextareaDescriptionStyle(textareaDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(textareaDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(textareaDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE: {
                ConditionalTextareaDescriptionStyle conditionalTextareaDescriptionStyle = (ConditionalTextareaDescriptionStyle) theEObject;
                T result = this.caseConditionalTextareaDescriptionStyle(conditionalTextareaDescriptionStyle);
                if (result == null)
                    result = this.caseConditional(conditionalTextareaDescriptionStyle);
                if (result == null)
                    result = this.caseTextareaDescriptionStyle(conditionalTextareaDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(conditionalTextareaDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(conditionalTextareaDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.TEXTFIELD_DESCRIPTION_STYLE: {
                TextfieldDescriptionStyle textfieldDescriptionStyle = (TextfieldDescriptionStyle) theEObject;
                T result = this.caseTextfieldDescriptionStyle(textfieldDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(textfieldDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(textfieldDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE: {
                ConditionalTextfieldDescriptionStyle conditionalTextfieldDescriptionStyle = (ConditionalTextfieldDescriptionStyle) theEObject;
                T result = this.caseConditionalTextfieldDescriptionStyle(conditionalTextfieldDescriptionStyle);
                if (result == null)
                    result = this.caseConditional(conditionalTextfieldDescriptionStyle);
                if (result == null)
                    result = this.caseTextfieldDescriptionStyle(conditionalTextfieldDescriptionStyle);
                if (result == null)
                    result = this.caseWidgetDescriptionStyle(conditionalTextfieldDescriptionStyle);
                if (result == null)
                    result = this.caseLabelStyle(conditionalTextfieldDescriptionStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.CONTAINER_BORDER_STYLE: {
                ContainerBorderStyle containerBorderStyle = (ContainerBorderStyle) theEObject;
                T result = this.caseContainerBorderStyle(containerBorderStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.CONDITIONAL_CONTAINER_BORDER_STYLE: {
                ConditionalContainerBorderStyle conditionalContainerBorderStyle = (ConditionalContainerBorderStyle) theEObject;
                T result = this.caseConditionalContainerBorderStyle(conditionalContainerBorderStyle);
                if (result == null)
                    result = this.caseConditional(conditionalContainerBorderStyle);
                if (result == null)
                    result = this.caseContainerBorderStyle(conditionalContainerBorderStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.FORM_ELEMENT_FOR: {
                FormElementFor formElementFor = (FormElementFor) theEObject;
                T result = this.caseFormElementFor(formElementFor);
                if (result == null)
                    result = this.caseFormElementDescription(formElementFor);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case FormPackage.FORM_ELEMENT_IF: {
                FormElementIf formElementIf = (FormElementIf) theEObject;
                T result = this.caseFormElementIf(formElementIf);
                if (result == null)
                    result = this.caseFormElementDescription(formElementIf);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            default:
                return this.defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Description</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFormDescription(FormDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Page Description</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Page Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePageDescription(PageDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Group Description</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Group Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseGroupDescription(GroupDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Element Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Element Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFormElementDescription(FormElementDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Widget Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Widget Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWidgetDescription(WidgetDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Bar Chart Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Bar Chart Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseBarChartDescription(BarChartDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Button Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Button Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseButtonDescription(ButtonDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Checkbox Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Checkbox Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCheckboxDescription(CheckboxDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Flexbox Container Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Flexbox Container Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFlexboxContainerDescription(FlexboxContainerDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Image Description</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Image Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseImageDescription(ImageDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Label Description</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Label Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLabelDescription(LabelDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Link Description</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Link Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLinkDescription(LinkDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>List Description</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>List Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseListDescription(ListDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Multi Select Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Multi Select Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMultiSelectDescription(MultiSelectDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Pie Chart Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Pie Chart Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePieChartDescription(PieChartDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Radio Description</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Radio Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRadioDescription(RadioDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Rich Text Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Rich Text Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRichTextDescription(RichTextDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Select Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Select Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSelectDescription(SelectDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Text Area Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Text Area Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTextAreaDescription(TextAreaDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Textfield Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Textfield Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTextfieldDescription(TextfieldDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Widget Description Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Widget Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseWidgetDescriptionStyle(WidgetDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Bar Chart Description Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Bar Chart Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseBarChartDescriptionStyle(BarChartDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Bar Chart Description
     * Style</em>'. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate
     * the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Bar Chart Description
     *         Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalBarChartDescriptionStyle(ConditionalBarChartDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Button Description Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Button Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseButtonDescriptionStyle(ButtonDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Button Description Style</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Button Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalButtonDescriptionStyle(ConditionalButtonDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Checkbox Description Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Checkbox Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCheckboxDescriptionStyle(CheckboxDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Checkbox Description
     * Style</em>'. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate
     * the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Checkbox Description
     *         Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalCheckboxDescriptionStyle(ConditionalCheckboxDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Label Description Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Label Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLabelDescriptionStyle(LabelDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Label Description Style</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Label Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalLabelDescriptionStyle(ConditionalLabelDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Link Description Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Link Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLinkDescriptionStyle(LinkDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Link Description Style</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Link Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalLinkDescriptionStyle(ConditionalLinkDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>List Description Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>List Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseListDescriptionStyle(ListDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional List Description Style</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional List Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalListDescriptionStyle(ConditionalListDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Multi Select Description Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Multi Select Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMultiSelectDescriptionStyle(MultiSelectDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Multi Select Description
     * Style</em>'. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate
     * the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Multi Select Description
     *         Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalMultiSelectDescriptionStyle(ConditionalMultiSelectDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Pie Chart Description Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Pie Chart Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePieChartDescriptionStyle(PieChartDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Pie Chart Description
     * Style</em>'. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate
     * the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Pie Chart Description
     *         Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalPieChartDescriptionStyle(ConditionalPieChartDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Radio Description Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Radio Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRadioDescriptionStyle(RadioDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Radio Description Style</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Radio Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalRadioDescriptionStyle(ConditionalRadioDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Select Description Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Select Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSelectDescriptionStyle(SelectDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Select Description Style</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Select Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalSelectDescriptionStyle(ConditionalSelectDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Textarea Description Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Textarea Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTextareaDescriptionStyle(TextareaDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Textarea Description
     * Style</em>'. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate
     * the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Textarea Description
     *         Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalTextareaDescriptionStyle(ConditionalTextareaDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Textfield Description Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Textfield Description Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTextfieldDescriptionStyle(TextfieldDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Textfield Description
     * Style</em>'. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate
     * the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Textfield Description
     *         Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalTextfieldDescriptionStyle(ConditionalTextfieldDescriptionStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Container Border Style</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Container Border Style</em>'.
     * @generated
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     */
    public T caseContainerBorderStyle(ContainerBorderStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional Container Border Style</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional Container Border Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditionalContainerBorderStyle(ConditionalContainerBorderStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Element For</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Element For</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFormElementFor(FormElementFor object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Element If</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Element If</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFormElementIf(FormElementIf object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Representation Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Representation Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRepresentationDescription(RepresentationDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Label Style</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Label Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLabelStyle(LabelStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditional(Conditional object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch, but this is the last case
     * anyway. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    @Override
    public T defaultCase(EObject object) {
        return null;
    }

} // FormSwitch
