/*******************************************************************************
 * Copyright (c) 2021, 2026 Obeo.
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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.view.Conditional;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.form.*;
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
import org.eclipse.sirius.components.view.form.ConditionalDateTimeDescriptionStyle;
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
import org.eclipse.sirius.components.view.form.DateTimeDescription;
import org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle;
import org.eclipse.sirius.components.view.form.FlexboxContainerDescription;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.form.FormElementDescription;
import org.eclipse.sirius.components.view.form.FormElementFor;
import org.eclipse.sirius.components.view.form.FormElementIf;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.FormVariable;
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
import org.eclipse.sirius.components.view.form.SliderDescription;
import org.eclipse.sirius.components.view.form.SplitButtonDescription;
import org.eclipse.sirius.components.view.form.TextAreaDescription;
import org.eclipse.sirius.components.view.form.TextareaDescriptionStyle;
import org.eclipse.sirius.components.view.form.TextfieldDescription;
import org.eclipse.sirius.components.view.form.TextfieldDescriptionStyle;
import org.eclipse.sirius.components.view.form.TreeDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;
import org.eclipse.sirius.components.view.form.WidgetDescriptionStyle;
import org.eclipse.sirius.components.view.form.WidgetGridLayout;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter <code>createXXX</code>
 * method for each class of the model. <!-- end-user-doc -->
 * @see org.eclipse.sirius.components.view.form.FormPackage
 * @generated
 */
public class FormAdapterFactory extends AdapterFactoryImpl {
    /**
	 * The cached model package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected static FormPackage modelPackage;

    /**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public FormAdapterFactory() {
		if (modelPackage == null)
		{
			modelPackage = FormPackage.eINSTANCE;
		}
	}

    /**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc --> This
     * implementation returns <code>true</code> if the object is either the model's package or is an instance object of
     * the model. <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
    @Override
    public boolean isFactoryForType(Object object) {
		if (object == modelPackage)
		{
			return true;
		}
		if (object instanceof EObject)
		{
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

    /**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected FormSwitch<Adapter> modelSwitch = new FormSwitch<Adapter>()
		{
			@Override
			public Adapter caseFormDescription(FormDescription object)
			{
				return createFormDescriptionAdapter();
			}
			@Override
			public Adapter caseFormVariable(FormVariable object)
			{
				return createFormVariableAdapter();
			}
			@Override
			public Adapter casePageDescription(PageDescription object)
			{
				return createPageDescriptionAdapter();
			}
			@Override
			public Adapter caseGroupDescription(GroupDescription object)
			{
				return createGroupDescriptionAdapter();
			}
			@Override
			public Adapter caseFormElementDescription(FormElementDescription object)
			{
				return createFormElementDescriptionAdapter();
			}
			@Override
			public Adapter caseWidgetDescription(WidgetDescription object)
			{
				return createWidgetDescriptionAdapter();
			}
			@Override
			public Adapter caseBarChartDescription(BarChartDescription object)
			{
				return createBarChartDescriptionAdapter();
			}
			@Override
			public Adapter caseButtonDescription(ButtonDescription object)
			{
				return createButtonDescriptionAdapter();
			}
			@Override
			public Adapter caseCheckboxDescription(CheckboxDescription object)
			{
				return createCheckboxDescriptionAdapter();
			}
			@Override
			public Adapter caseDateTimeDescription(DateTimeDescription object)
			{
				return createDateTimeDescriptionAdapter();
			}
			@Override
			public Adapter caseFlexboxContainerDescription(FlexboxContainerDescription object)
			{
				return createFlexboxContainerDescriptionAdapter();
			}
			@Override
			public Adapter caseImageDescription(ImageDescription object)
			{
				return createImageDescriptionAdapter();
			}
			@Override
			public Adapter caseLabelDescription(LabelDescription object)
			{
				return createLabelDescriptionAdapter();
			}
			@Override
			public Adapter caseLinkDescription(LinkDescription object)
			{
				return createLinkDescriptionAdapter();
			}
			@Override
			public Adapter caseListDescription(ListDescription object)
			{
				return createListDescriptionAdapter();
			}
			@Override
			public Adapter caseMultiSelectDescription(MultiSelectDescription object)
			{
				return createMultiSelectDescriptionAdapter();
			}
			@Override
			public Adapter casePieChartDescription(PieChartDescription object)
			{
				return createPieChartDescriptionAdapter();
			}
			@Override
			public Adapter caseRadioDescription(RadioDescription object)
			{
				return createRadioDescriptionAdapter();
			}
			@Override
			public Adapter caseRichTextDescription(RichTextDescription object)
			{
				return createRichTextDescriptionAdapter();
			}
			@Override
			public Adapter caseSelectDescription(SelectDescription object)
			{
				return createSelectDescriptionAdapter();
			}
			@Override
			public Adapter caseSplitButtonDescription(SplitButtonDescription object)
			{
				return createSplitButtonDescriptionAdapter();
			}
			@Override
			public Adapter caseTextAreaDescription(TextAreaDescription object)
			{
				return createTextAreaDescriptionAdapter();
			}
			@Override
			public Adapter caseTextfieldDescription(TextfieldDescription object)
			{
				return createTextfieldDescriptionAdapter();
			}
			@Override
			public Adapter caseTreeDescription(TreeDescription object)
			{
				return createTreeDescriptionAdapter();
			}
			@Override
			public Adapter caseSliderDescription(SliderDescription object)
			{
				return createSliderDescriptionAdapter();
			}
			@Override
			public Adapter caseWidgetDescriptionStyle(WidgetDescriptionStyle object)
			{
				return createWidgetDescriptionStyleAdapter();
			}
			@Override
			public Adapter caseWidgetGridLayout(WidgetGridLayout object)
			{
				return createWidgetGridLayoutAdapter();
			}
			@Override
			public Adapter caseBarChartDescriptionStyle(BarChartDescriptionStyle object)
			{
				return createBarChartDescriptionStyleAdapter();
			}
			@Override
			public Adapter caseConditionalBarChartDescriptionStyle(ConditionalBarChartDescriptionStyle object)
			{
				return createConditionalBarChartDescriptionStyleAdapter();
			}
			@Override
			public Adapter caseButtonDescriptionStyle(ButtonDescriptionStyle object)
			{
				return createButtonDescriptionStyleAdapter();
			}
			@Override
			public Adapter caseConditionalButtonDescriptionStyle(ConditionalButtonDescriptionStyle object)
			{
				return createConditionalButtonDescriptionStyleAdapter();
			}
			@Override
			public Adapter caseCheckboxDescriptionStyle(CheckboxDescriptionStyle object)
			{
				return createCheckboxDescriptionStyleAdapter();
			}
			@Override
			public Adapter caseConditionalCheckboxDescriptionStyle(ConditionalCheckboxDescriptionStyle object)
			{
				return createConditionalCheckboxDescriptionStyleAdapter();
			}
			@Override
			public Adapter caseDateTimeDescriptionStyle(DateTimeDescriptionStyle object)
			{
				return createDateTimeDescriptionStyleAdapter();
			}
			@Override
			public Adapter caseConditionalDateTimeDescriptionStyle(ConditionalDateTimeDescriptionStyle object)
			{
				return createConditionalDateTimeDescriptionStyleAdapter();
			}
			@Override
			public Adapter caseLabelDescriptionStyle(LabelDescriptionStyle object)
			{
				return createLabelDescriptionStyleAdapter();
			}
			@Override
			public Adapter caseConditionalLabelDescriptionStyle(ConditionalLabelDescriptionStyle object)
			{
				return createConditionalLabelDescriptionStyleAdapter();
			}
			@Override
			public Adapter caseLinkDescriptionStyle(LinkDescriptionStyle object)
			{
				return createLinkDescriptionStyleAdapter();
			}
			@Override
			public Adapter caseConditionalLinkDescriptionStyle(ConditionalLinkDescriptionStyle object)
			{
				return createConditionalLinkDescriptionStyleAdapter();
			}
			@Override
			public Adapter caseListDescriptionStyle(ListDescriptionStyle object)
			{
				return createListDescriptionStyleAdapter();
			}
			@Override
			public Adapter caseConditionalListDescriptionStyle(ConditionalListDescriptionStyle object)
			{
				return createConditionalListDescriptionStyleAdapter();
			}
			@Override
			public Adapter caseMultiSelectDescriptionStyle(MultiSelectDescriptionStyle object)
			{
				return createMultiSelectDescriptionStyleAdapter();
			}
			@Override
			public Adapter caseConditionalMultiSelectDescriptionStyle(ConditionalMultiSelectDescriptionStyle object)
			{
				return createConditionalMultiSelectDescriptionStyleAdapter();
			}
			@Override
			public Adapter casePieChartDescriptionStyle(PieChartDescriptionStyle object)
			{
				return createPieChartDescriptionStyleAdapter();
			}
			@Override
			public Adapter caseConditionalPieChartDescriptionStyle(ConditionalPieChartDescriptionStyle object)
			{
				return createConditionalPieChartDescriptionStyleAdapter();
			}
			@Override
			public Adapter caseRadioDescriptionStyle(RadioDescriptionStyle object)
			{
				return createRadioDescriptionStyleAdapter();
			}
			@Override
			public Adapter caseConditionalRadioDescriptionStyle(ConditionalRadioDescriptionStyle object)
			{
				return createConditionalRadioDescriptionStyleAdapter();
			}
			@Override
			public Adapter caseSelectDescriptionStyle(SelectDescriptionStyle object)
			{
				return createSelectDescriptionStyleAdapter();
			}
			@Override
			public Adapter caseConditionalSelectDescriptionStyle(ConditionalSelectDescriptionStyle object)
			{
				return createConditionalSelectDescriptionStyleAdapter();
			}
			@Override
			public Adapter caseTextareaDescriptionStyle(TextareaDescriptionStyle object)
			{
				return createTextareaDescriptionStyleAdapter();
			}
			@Override
			public Adapter caseConditionalTextareaDescriptionStyle(ConditionalTextareaDescriptionStyle object)
			{
				return createConditionalTextareaDescriptionStyleAdapter();
			}
			@Override
			public Adapter caseTextfieldDescriptionStyle(TextfieldDescriptionStyle object)
			{
				return createTextfieldDescriptionStyleAdapter();
			}
			@Override
			public Adapter caseConditionalTextfieldDescriptionStyle(ConditionalTextfieldDescriptionStyle object)
			{
				return createConditionalTextfieldDescriptionStyleAdapter();
			}
			@Override
			public Adapter caseContainerBorderStyle(ContainerBorderStyle object)
			{
				return createContainerBorderStyleAdapter();
			}
			@Override
			public Adapter caseConditionalContainerBorderStyle(ConditionalContainerBorderStyle object)
			{
				return createConditionalContainerBorderStyleAdapter();
			}
			@Override
			public Adapter caseFormElementFor(FormElementFor object)
			{
				return createFormElementForAdapter();
			}
			@Override
			public Adapter caseFormElementIf(FormElementIf object)
			{
				return createFormElementIfAdapter();
			}
			@Override
			public Adapter caseRepresentationDescription(RepresentationDescription object)
			{
				return createRepresentationDescriptionAdapter();
			}
			@Override
			public Adapter caseLabelStyle(LabelStyle object)
			{
				return createLabelStyleAdapter();
			}
			@Override
			public Adapter caseConditional(Conditional object)
			{
				return createConditionalAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object)
			{
				return createEObjectAdapter();
			}
		};

    /**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
    @Override
    public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.FormDescription
     * <em>Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.FormDescription
     * @generated
     */
    public Adapter createFormDescriptionAdapter() {
		return null;
	}

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.FormVariable
     * <em>Variable</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.FormVariable
     * @generated
     */
    public Adapter createFormVariableAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.PageDescription <em>Page Description</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.PageDescription
	 * @generated
	 */
    public Adapter createPageDescriptionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.GroupDescription <em>Group Description</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.GroupDescription
	 * @generated
	 */
    public Adapter createGroupDescriptionAdapter() {
		return null;
	}

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.FormElementDescription <em>Element Description</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
     * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.FormElementDescription
     * @generated
     */
    public Adapter createFormElementDescriptionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.WidgetDescription <em>Widget Description</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.WidgetDescription
	 * @generated
	 */
    public Adapter createWidgetDescriptionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.BarChartDescription <em>Bar Chart Description</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.BarChartDescription
	 * @generated
	 */
    public Adapter createBarChartDescriptionAdapter() {
		return null;
	}

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.SplitButtonDescription <em>Split Button Description</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
     * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.SplitButtonDescription
     * @generated
     */
    public Adapter createSplitButtonDescriptionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.ButtonDescription <em>Button Description</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.ButtonDescription
	 * @generated
	 */
    public Adapter createButtonDescriptionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.CheckboxDescription <em>Checkbox Description</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.CheckboxDescription
	 * @generated
	 */
    public Adapter createCheckboxDescriptionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.FlexboxContainerDescription <em>Flexbox Container Description</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.FlexboxContainerDescription
	 * @generated
	 */
    public Adapter createFlexboxContainerDescriptionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.ImageDescription <em>Image Description</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.ImageDescription
	 * @generated
	 */
    public Adapter createImageDescriptionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.LabelDescription <em>Label Description</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.LabelDescription
	 * @generated
	 */
    public Adapter createLabelDescriptionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.LinkDescription <em>Link Description</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.LinkDescription
	 * @generated
	 */
    public Adapter createLinkDescriptionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.ListDescription <em>List Description</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.ListDescription
	 * @generated
	 */
    public Adapter createListDescriptionAdapter() {
		return null;
	}

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.MultiSelectDescription <em>Multi Select Description</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
     * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.MultiSelectDescription
     * @generated
     */
    public Adapter createMultiSelectDescriptionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.TreeDescription <em>Tree Description</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.TreeDescription
	 * @generated
	 */
    public Adapter createTreeDescriptionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.DateTimeDescription <em>Date Time Description</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.DateTimeDescription
	 * @generated
	 */
    public Adapter createDateTimeDescriptionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.SliderDescription <em>Slider Description</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.SliderDescription
	 * @generated
	 */
    public Adapter createSliderDescriptionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.PieChartDescription <em>Pie Chart Description</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.PieChartDescription
	 * @generated
	 */
    public Adapter createPieChartDescriptionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.RadioDescription <em>Radio Description</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.RadioDescription
	 * @generated
	 */
    public Adapter createRadioDescriptionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.RichTextDescription <em>Rich Text Description</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.RichTextDescription
	 * @generated
	 */
    public Adapter createRichTextDescriptionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.SelectDescription <em>Select Description</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.SelectDescription
	 * @generated
	 */
    public Adapter createSelectDescriptionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.TextAreaDescription <em>Text Area Description</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.TextAreaDescription
	 * @generated
	 */
    public Adapter createTextAreaDescriptionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.TextfieldDescription <em>Textfield Description</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.TextfieldDescription
	 * @generated
	 */
    public Adapter createTextfieldDescriptionAdapter() {
		return null;
	}

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.WidgetDescriptionStyle <em>Widget Description Style</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
     * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.WidgetDescriptionStyle
     * @generated
     */
    public Adapter createWidgetDescriptionStyleAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.WidgetGridLayout <em>Widget Grid Layout</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.WidgetGridLayout
	 * @generated
	 */
    public Adapter createWidgetGridLayoutAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.BarChartDescriptionStyle <em>Bar Chart Description Style</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.BarChartDescriptionStyle
	 * @generated
	 */
    public Adapter createBarChartDescriptionStyleAdapter() {
		return null;
	}

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.ConditionalBarChartDescriptionStyle <em>Conditional Bar Chart
     * Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.ConditionalBarChartDescriptionStyle
     * @generated
     */
    public Adapter createConditionalBarChartDescriptionStyleAdapter() {
		return null;
	}

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.ButtonDescriptionStyle <em>Button Description Style</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
     * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.ButtonDescriptionStyle
     * @generated
     */
    public Adapter createButtonDescriptionStyleAdapter() {
		return null;
	}

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.ConditionalButtonDescriptionStyle <em>Conditional Button
     * Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.ConditionalButtonDescriptionStyle
     * @generated
     */
    public Adapter createConditionalButtonDescriptionStyleAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.CheckboxDescriptionStyle <em>Checkbox Description Style</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.CheckboxDescriptionStyle
	 * @generated
	 */
    public Adapter createCheckboxDescriptionStyleAdapter() {
		return null;
	}

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.ConditionalCheckboxDescriptionStyle <em>Conditional Checkbox
     * Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.ConditionalCheckboxDescriptionStyle
     * @generated
     */
    public Adapter createConditionalCheckboxDescriptionStyleAdapter() {
		return null;
	}

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.LabelDescriptionStyle <em>Label Description Style</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
     * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.LabelDescriptionStyle
     * @generated
     */
    public Adapter createLabelDescriptionStyleAdapter() {
		return null;
	}

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.ConditionalLabelDescriptionStyle <em>Conditional Label
     * Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.ConditionalLabelDescriptionStyle
     * @generated
     */
    public Adapter createConditionalLabelDescriptionStyleAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.LinkDescriptionStyle <em>Link Description Style</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we
     * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.LinkDescriptionStyle
	 * @generated
	 */
    public Adapter createLinkDescriptionStyleAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.ConditionalLinkDescriptionStyle <em>Conditional Link Description Style</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.ConditionalLinkDescriptionStyle
	 * @generated
	 */
    public Adapter createConditionalLinkDescriptionStyleAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.ListDescriptionStyle <em>List Description Style</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we
     * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.ListDescriptionStyle
	 * @generated
	 */
    public Adapter createListDescriptionStyleAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.ConditionalListDescriptionStyle <em>Conditional List Description Style</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.ConditionalListDescriptionStyle
	 * @generated
	 */
    public Adapter createConditionalListDescriptionStyleAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle <em>Multi Select Description Style</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle
	 * @generated
	 */
    public Adapter createMultiSelectDescriptionStyleAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.ConditionalMultiSelectDescriptionStyle <em>Conditional Multi Select Description Style</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.ConditionalMultiSelectDescriptionStyle
	 * @generated
	 */
    public Adapter createConditionalMultiSelectDescriptionStyleAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.PieChartDescriptionStyle <em>Pie Chart Description Style</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.PieChartDescriptionStyle
	 * @generated
	 */
    public Adapter createPieChartDescriptionStyleAdapter() {
		return null;
	}

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.ConditionalPieChartDescriptionStyle <em>Conditional Pie Chart
     * Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.ConditionalPieChartDescriptionStyle
     * @generated
     */
    public Adapter createConditionalPieChartDescriptionStyleAdapter() {
		return null;
	}

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.RadioDescriptionStyle <em>Radio Description Style</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
     * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.RadioDescriptionStyle
     * @generated
     */
    public Adapter createRadioDescriptionStyleAdapter() {
		return null;
	}

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.ConditionalRadioDescriptionStyle <em>Conditional Radio
     * Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.ConditionalRadioDescriptionStyle
     * @generated
     */
    public Adapter createConditionalRadioDescriptionStyleAdapter() {
		return null;
	}

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.SelectDescriptionStyle <em>Select Description Style</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
     * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.SelectDescriptionStyle
     * @generated
     */
    public Adapter createSelectDescriptionStyleAdapter() {
		return null;
	}

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.ConditionalSelectDescriptionStyle <em>Conditional Select
     * Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.ConditionalSelectDescriptionStyle
     * @generated
     */
    public Adapter createConditionalSelectDescriptionStyleAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.TextareaDescriptionStyle <em>Textarea Description Style</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.TextareaDescriptionStyle
	 * @generated
	 */
    public Adapter createTextareaDescriptionStyleAdapter() {
		return null;
	}

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.ConditionalTextareaDescriptionStyle <em>Conditional Textarea
     * Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.ConditionalTextareaDescriptionStyle
     * @generated
     */
    public Adapter createConditionalTextareaDescriptionStyleAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.TextfieldDescriptionStyle <em>Textfield Description Style</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.TextfieldDescriptionStyle
	 * @generated
	 */
    public Adapter createTextfieldDescriptionStyleAdapter() {
		return null;
	}

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.ConditionalTextfieldDescriptionStyle <em>Conditional Textfield
     * Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.ConditionalTextfieldDescriptionStyle
     * @generated
     */
    public Adapter createConditionalTextfieldDescriptionStyleAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle <em>Date Time Description Style</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle
	 * @generated
	 */
    public Adapter createDateTimeDescriptionStyleAdapter() {
		return null;
	}

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.ConditionalDateTimeDescriptionStyle <em>Conditional Date Time
     * Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.ConditionalDateTimeDescriptionStyle
     * @generated
     */
    public Adapter createConditionalDateTimeDescriptionStyleAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.ContainerBorderStyle <em>Container Border Style</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we
     * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.ContainerBorderStyle
	 * @generated
	 */
    public Adapter createContainerBorderStyleAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.ConditionalContainerBorderStyle <em>Conditional Container Border Style</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.form.ConditionalContainerBorderStyle
	 * @generated
	 */
    public Adapter createConditionalContainerBorderStyleAdapter() {
		return null;
	}

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.FormElementFor
     * <em>Element For</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.FormElementFor
     * @generated
     */
    public Adapter createFormElementForAdapter() {
		return null;
	}

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.FormElementIf
     * <em>Element If</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.FormElementIf
     * @generated
     */
    public Adapter createFormElementIfAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.RepresentationDescription <em>Representation Description</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that
     * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.RepresentationDescription
	 * @generated
	 */
    public Adapter createRepresentationDescriptionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.LabelStyle <em>Label Style</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.LabelStyle
	 * @generated
	 */
    public Adapter createLabelStyleAdapter() {
		return null;
	}

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.Conditional
     * <em>Conditional</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.Conditional
     * @generated
     */
    public Adapter createConditionalAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc --> This default implementation returns null.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
    public Adapter createEObjectAdapter() {
		return null;
	}

} // FormAdapterFactory
