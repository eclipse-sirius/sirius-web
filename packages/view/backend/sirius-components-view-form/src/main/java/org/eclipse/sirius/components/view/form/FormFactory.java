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
package org.eclipse.sirius.components.view.form;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract class of
 * the model. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.form.FormPackage
 * @generated
 */
public interface FormFactory extends EFactory {
    /**
     * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    FormFactory eINSTANCE = org.eclipse.sirius.components.view.form.impl.FormFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Description</em>'.
     * @generated
     */
    FormDescription createFormDescription();

    /**
     * Returns a new object of class '<em>Variable</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Variable</em>'.
     * @generated
     */
    FormVariable createFormVariable();

    /**
     * Returns a new object of class '<em>Page Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Page Description</em>'.
     * @generated
     */
    PageDescription createPageDescription();

    /**
     * Returns a new object of class '<em>Group Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Group Description</em>'.
     * @generated
     */
    GroupDescription createGroupDescription();

    /**
     * Returns a new object of class '<em>Bar Chart Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Bar Chart Description</em>'.
     * @generated
     */
    BarChartDescription createBarChartDescription();

    /**
     * Returns a new object of class '<em>Split Button Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Split Button Description</em>'.
     * @generated
     */
    SplitButtonDescription createSplitButtonDescription();

    /**
     * Returns a new object of class '<em>Button Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Button Description</em>'.
     * @generated
     */
    ButtonDescription createButtonDescription();

    /**
     * Returns a new object of class '<em>Checkbox Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Checkbox Description</em>'.
     * @generated
     */
    CheckboxDescription createCheckboxDescription();

    /**
     * Returns a new object of class '<em>Flexbox Container Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return a new object of class '<em>Flexbox Container Description</em>'.
     * @generated
     */
    FlexboxContainerDescription createFlexboxContainerDescription();

    /**
     * Returns a new object of class '<em>Image Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Image Description</em>'.
     * @generated
     */
    ImageDescription createImageDescription();

    /**
     * Returns a new object of class '<em>Label Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Label Description</em>'.
     * @generated
     */
    LabelDescription createLabelDescription();

    /**
     * Returns a new object of class '<em>Link Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Link Description</em>'.
     * @generated
     */
    LinkDescription createLinkDescription();

    /**
     * Returns a new object of class '<em>List Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>List Description</em>'.
     * @generated
     */
    ListDescription createListDescription();

    /**
     * Returns a new object of class '<em>Multi Select Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Multi Select Description</em>'.
     * @generated
     */
    MultiSelectDescription createMultiSelectDescription();

    /**
     * Returns a new object of class '<em>Tree Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Tree Description</em>'.
     * @generated
     */
    TreeDescription createTreeDescription();

    /**
     * Returns a new object of class '<em>Slider Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Slider Description</em>'.
     * @generated
     */
    SliderDescription createSliderDescription();

    /**
     * Returns a new object of class '<em>Pie Chart Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Pie Chart Description</em>'.
     * @generated
     */
    PieChartDescription createPieChartDescription();

    /**
     * Returns a new object of class '<em>Radio Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Radio Description</em>'.
     * @generated
     */
    RadioDescription createRadioDescription();

    /**
     * Returns a new object of class '<em>Rich Text Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Rich Text Description</em>'.
     * @generated
     */
    RichTextDescription createRichTextDescription();

    /**
     * Returns a new object of class '<em>Select Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Select Description</em>'.
     * @generated
     */
    SelectDescription createSelectDescription();

    /**
     * Returns a new object of class '<em>Text Area Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Text Area Description</em>'.
     * @generated
     */
    TextAreaDescription createTextAreaDescription();

    /**
     * Returns a new object of class '<em>Textfield Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Textfield Description</em>'.
     * @generated
     */
    TextfieldDescription createTextfieldDescription();

    /**
     * Returns a new object of class '<em>Bar Chart Description Style</em>'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return a new object of class '<em>Bar Chart Description Style</em>'.
     * @generated
     */
    BarChartDescriptionStyle createBarChartDescriptionStyle();

    /**
     * Returns a new object of class '<em>Conditional Bar Chart Description Style</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Conditional Bar Chart Description Style</em>'.
     * @generated
     */
    ConditionalBarChartDescriptionStyle createConditionalBarChartDescriptionStyle();

    /**
     * Returns a new object of class '<em>Button Description Style</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Button Description Style</em>'.
     * @generated
     */
    ButtonDescriptionStyle createButtonDescriptionStyle();

    /**
     * Returns a new object of class '<em>Conditional Button Description Style</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Conditional Button Description Style</em>'.
     * @generated
     */
    ConditionalButtonDescriptionStyle createConditionalButtonDescriptionStyle();

    /**
     * Returns a new object of class '<em>Checkbox Description Style</em>'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return a new object of class '<em>Checkbox Description Style</em>'.
     * @generated
     */
    CheckboxDescriptionStyle createCheckboxDescriptionStyle();

    /**
     * Returns a new object of class '<em>Conditional Checkbox Description Style</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Conditional Checkbox Description Style</em>'.
     * @generated
     */
    ConditionalCheckboxDescriptionStyle createConditionalCheckboxDescriptionStyle();

    /**
     * Returns a new object of class '<em>Label Description Style</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Label Description Style</em>'.
     * @generated
     */
    LabelDescriptionStyle createLabelDescriptionStyle();

    /**
     * Returns a new object of class '<em>Conditional Label Description Style</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Conditional Label Description Style</em>'.
     * @generated
     */
    ConditionalLabelDescriptionStyle createConditionalLabelDescriptionStyle();

    /**
     * Returns a new object of class '<em>Link Description Style</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Link Description Style</em>'.
     * @generated
     */
    LinkDescriptionStyle createLinkDescriptionStyle();

    /**
     * Returns a new object of class '<em>Conditional Link Description Style</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Conditional Link Description Style</em>'.
     * @generated
     */
    ConditionalLinkDescriptionStyle createConditionalLinkDescriptionStyle();

    /**
     * Returns a new object of class '<em>List Description Style</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>List Description Style</em>'.
     * @generated
     */
    ListDescriptionStyle createListDescriptionStyle();

    /**
     * Returns a new object of class '<em>Conditional List Description Style</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Conditional List Description Style</em>'.
     * @generated
     */
    ConditionalListDescriptionStyle createConditionalListDescriptionStyle();

    /**
     * Returns a new object of class '<em>Multi Select Description Style</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Multi Select Description Style</em>'.
     * @generated
     */
    MultiSelectDescriptionStyle createMultiSelectDescriptionStyle();

    /**
     * Returns a new object of class '<em>Conditional Multi Select Description Style</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Conditional Multi Select Description Style</em>'.
     * @generated
     */
    ConditionalMultiSelectDescriptionStyle createConditionalMultiSelectDescriptionStyle();

    /**
     * Returns a new object of class '<em>Pie Chart Description Style</em>'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return a new object of class '<em>Pie Chart Description Style</em>'.
     * @generated
     */
    PieChartDescriptionStyle createPieChartDescriptionStyle();

    /**
     * Returns a new object of class '<em>Conditional Pie Chart Description Style</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Conditional Pie Chart Description Style</em>'.
     * @generated
     */
    ConditionalPieChartDescriptionStyle createConditionalPieChartDescriptionStyle();

    /**
     * Returns a new object of class '<em>Radio Description Style</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Radio Description Style</em>'.
     * @generated
     */
    RadioDescriptionStyle createRadioDescriptionStyle();

    /**
     * Returns a new object of class '<em>Conditional Radio Description Style</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Conditional Radio Description Style</em>'.
     * @generated
     */
    ConditionalRadioDescriptionStyle createConditionalRadioDescriptionStyle();

    /**
     * Returns a new object of class '<em>Select Description Style</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Select Description Style</em>'.
     * @generated
     */
    SelectDescriptionStyle createSelectDescriptionStyle();

    /**
     * Returns a new object of class '<em>Conditional Select Description Style</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Conditional Select Description Style</em>'.
     * @generated
     */
    ConditionalSelectDescriptionStyle createConditionalSelectDescriptionStyle();

    /**
     * Returns a new object of class '<em>Textarea Description Style</em>'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return a new object of class '<em>Textarea Description Style</em>'.
     * @generated
     */
    TextareaDescriptionStyle createTextareaDescriptionStyle();

    /**
     * Returns a new object of class '<em>Conditional Textarea Description Style</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Conditional Textarea Description Style</em>'.
     * @generated
     */
    ConditionalTextareaDescriptionStyle createConditionalTextareaDescriptionStyle();

    /**
     * Returns a new object of class '<em>Textfield Description Style</em>'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return a new object of class '<em>Textfield Description Style</em>'.
     * @generated
     */
    TextfieldDescriptionStyle createTextfieldDescriptionStyle();

    /**
     * Returns a new object of class '<em>Conditional Textfield Description Style</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Conditional Textfield Description Style</em>'.
     * @generated
     */
    ConditionalTextfieldDescriptionStyle createConditionalTextfieldDescriptionStyle();

    /**
     * Returns a new object of class '<em>Container Border Style</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Container Border Style</em>'.
     * @generated
     */
    ContainerBorderStyle createContainerBorderStyle();

    /**
     * Returns a new object of class '<em>Conditional Container Border Style</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Conditional Container Border Style</em>'.
     * @generated
     */
    ConditionalContainerBorderStyle createConditionalContainerBorderStyle();

    /**
     * Returns a new object of class '<em>Element For</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Element For</em>'.
     * @generated
     */
    FormElementFor createFormElementFor();

    /**
     * Returns a new object of class '<em>Element If</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Element If</em>'.
     * @generated
     */
    FormElementIf createFormElementIf();

    /**
     * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the package supported by this factory.
     * @generated
     */
    FormPackage getFormPackage();

} // FormFactory
