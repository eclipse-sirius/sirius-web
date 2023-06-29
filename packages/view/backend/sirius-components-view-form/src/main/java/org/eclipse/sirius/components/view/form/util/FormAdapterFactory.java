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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
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
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter <code>createXXX</code>
 * method for each class of the model. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.form.FormPackage
 * @generated
 */
public class FormAdapterFactory extends AdapterFactoryImpl {
    /**
     * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected static FormPackage modelPackage;

    /**
     * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public FormAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = FormPackage.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object. <!-- begin-user-doc --> This
     * implementation returns <code>true</code> if the object is either the model's package or is an instance object of
     * the model. <!-- end-user-doc -->
     *
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object object) {
        if (object == modelPackage) {
            return true;
        }
        if (object instanceof EObject) {
            return ((EObject) object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * The switch that delegates to the <code>createXXX</code> methods. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected FormSwitch<Adapter> modelSwitch = new FormSwitch<>() {
        @Override
        public Adapter caseFormDescription(FormDescription object) {
            return FormAdapterFactory.this.createFormDescriptionAdapter();
        }

        @Override
        public Adapter casePageDescription(PageDescription object) {
            return FormAdapterFactory.this.createPageDescriptionAdapter();
        }

        @Override
        public Adapter caseGroupDescription(GroupDescription object) {
            return FormAdapterFactory.this.createGroupDescriptionAdapter();
        }

        @Override
        public Adapter caseWidgetDescription(WidgetDescription object) {
            return FormAdapterFactory.this.createWidgetDescriptionAdapter();
        }

        @Override
        public Adapter caseBarChartDescription(BarChartDescription object) {
            return FormAdapterFactory.this.createBarChartDescriptionAdapter();
        }

        @Override
        public Adapter caseButtonDescription(ButtonDescription object) {
            return FormAdapterFactory.this.createButtonDescriptionAdapter();
        }

        @Override
        public Adapter caseCheckboxDescription(CheckboxDescription object) {
            return FormAdapterFactory.this.createCheckboxDescriptionAdapter();
        }

        @Override
        public Adapter caseFlexboxContainerDescription(FlexboxContainerDescription object) {
            return FormAdapterFactory.this.createFlexboxContainerDescriptionAdapter();
        }

        @Override
        public Adapter caseImageDescription(ImageDescription object) {
            return FormAdapterFactory.this.createImageDescriptionAdapter();
        }

        @Override
        public Adapter caseLabelDescription(LabelDescription object) {
            return FormAdapterFactory.this.createLabelDescriptionAdapter();
        }

        @Override
        public Adapter caseLinkDescription(LinkDescription object) {
            return FormAdapterFactory.this.createLinkDescriptionAdapter();
        }

        @Override
        public Adapter caseListDescription(ListDescription object) {
            return FormAdapterFactory.this.createListDescriptionAdapter();
        }

        @Override
        public Adapter caseMultiSelectDescription(MultiSelectDescription object) {
            return FormAdapterFactory.this.createMultiSelectDescriptionAdapter();
        }

        @Override
        public Adapter casePieChartDescription(PieChartDescription object) {
            return FormAdapterFactory.this.createPieChartDescriptionAdapter();
        }

        @Override
        public Adapter caseRadioDescription(RadioDescription object) {
            return FormAdapterFactory.this.createRadioDescriptionAdapter();
        }

        @Override
        public Adapter caseRichTextDescription(RichTextDescription object) {
            return FormAdapterFactory.this.createRichTextDescriptionAdapter();
        }

        @Override
        public Adapter caseSelectDescription(SelectDescription object) {
            return FormAdapterFactory.this.createSelectDescriptionAdapter();
        }

        @Override
        public Adapter caseTextAreaDescription(TextAreaDescription object) {
            return FormAdapterFactory.this.createTextAreaDescriptionAdapter();
        }

        @Override
        public Adapter caseTextfieldDescription(TextfieldDescription object) {
            return FormAdapterFactory.this.createTextfieldDescriptionAdapter();
        }

        @Override
        public Adapter caseWidgetDescriptionStyle(WidgetDescriptionStyle object) {
            return FormAdapterFactory.this.createWidgetDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseBarChartDescriptionStyle(BarChartDescriptionStyle object) {
            return FormAdapterFactory.this.createBarChartDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseConditionalBarChartDescriptionStyle(ConditionalBarChartDescriptionStyle object) {
            return FormAdapterFactory.this.createConditionalBarChartDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseButtonDescriptionStyle(ButtonDescriptionStyle object) {
            return FormAdapterFactory.this.createButtonDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseConditionalButtonDescriptionStyle(ConditionalButtonDescriptionStyle object) {
            return FormAdapterFactory.this.createConditionalButtonDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseCheckboxDescriptionStyle(CheckboxDescriptionStyle object) {
            return FormAdapterFactory.this.createCheckboxDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseConditionalCheckboxDescriptionStyle(ConditionalCheckboxDescriptionStyle object) {
            return FormAdapterFactory.this.createConditionalCheckboxDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseLabelDescriptionStyle(LabelDescriptionStyle object) {
            return FormAdapterFactory.this.createLabelDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseConditionalLabelDescriptionStyle(ConditionalLabelDescriptionStyle object) {
            return FormAdapterFactory.this.createConditionalLabelDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseLinkDescriptionStyle(LinkDescriptionStyle object) {
            return FormAdapterFactory.this.createLinkDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseConditionalLinkDescriptionStyle(ConditionalLinkDescriptionStyle object) {
            return FormAdapterFactory.this.createConditionalLinkDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseListDescriptionStyle(ListDescriptionStyle object) {
            return FormAdapterFactory.this.createListDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseConditionalListDescriptionStyle(ConditionalListDescriptionStyle object) {
            return FormAdapterFactory.this.createConditionalListDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseMultiSelectDescriptionStyle(MultiSelectDescriptionStyle object) {
            return FormAdapterFactory.this.createMultiSelectDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseConditionalMultiSelectDescriptionStyle(ConditionalMultiSelectDescriptionStyle object) {
            return FormAdapterFactory.this.createConditionalMultiSelectDescriptionStyleAdapter();
        }

        @Override
        public Adapter casePieChartDescriptionStyle(PieChartDescriptionStyle object) {
            return FormAdapterFactory.this.createPieChartDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseConditionalPieChartDescriptionStyle(ConditionalPieChartDescriptionStyle object) {
            return FormAdapterFactory.this.createConditionalPieChartDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseRadioDescriptionStyle(RadioDescriptionStyle object) {
            return FormAdapterFactory.this.createRadioDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseConditionalRadioDescriptionStyle(ConditionalRadioDescriptionStyle object) {
            return FormAdapterFactory.this.createConditionalRadioDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseSelectDescriptionStyle(SelectDescriptionStyle object) {
            return FormAdapterFactory.this.createSelectDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseConditionalSelectDescriptionStyle(ConditionalSelectDescriptionStyle object) {
            return FormAdapterFactory.this.createConditionalSelectDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseTextareaDescriptionStyle(TextareaDescriptionStyle object) {
            return FormAdapterFactory.this.createTextareaDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseConditionalTextareaDescriptionStyle(ConditionalTextareaDescriptionStyle object) {
            return FormAdapterFactory.this.createConditionalTextareaDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseTextfieldDescriptionStyle(TextfieldDescriptionStyle object) {
            return FormAdapterFactory.this.createTextfieldDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseConditionalTextfieldDescriptionStyle(ConditionalTextfieldDescriptionStyle object) {
            return FormAdapterFactory.this.createConditionalTextfieldDescriptionStyleAdapter();
        }

        @Override
        public Adapter caseContainerBorderStyle(ContainerBorderStyle object) {
            return FormAdapterFactory.this.createContainerBorderStyleAdapter();
        }

        @Override
        public Adapter caseConditionalContainerBorderStyle(ConditionalContainerBorderStyle object) {
            return FormAdapterFactory.this.createConditionalContainerBorderStyleAdapter();
        }

        @Override
        public Adapter caseRepresentationDescription(RepresentationDescription object) {
            return FormAdapterFactory.this.createRepresentationDescriptionAdapter();
        }

        @Override
        public Adapter caseLabelStyle(LabelStyle object) {
            return FormAdapterFactory.this.createLabelStyleAdapter();
        }

        @Override
        public Adapter caseConditional(Conditional object) {
            return FormAdapterFactory.this.createConditionalAdapter();
        }

        @Override
        public Adapter defaultCase(EObject object) {
            return FormAdapterFactory.this.createEObjectAdapter();
        }
    };

    /**
     * Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param target
     *            the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(Notifier target) {
        return this.modelSwitch.doSwitch((EObject) target);
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
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.PageDescription
     * <em>Page Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.PageDescription
     * @generated
     */
    public Adapter createPageDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.GroupDescription
     * <em>Group Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.GroupDescription
     * @generated
     */
    public Adapter createGroupDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.WidgetDescription
     * <em>Widget Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.WidgetDescription
     * @generated
     */
    public Adapter createWidgetDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.BarChartDescription
     * <em>Bar Chart Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.BarChartDescription
     * @generated
     */
    public Adapter createBarChartDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.ButtonDescription
     * <em>Button Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.ButtonDescription
     * @generated
     */
    public Adapter createButtonDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.CheckboxDescription
     * <em>Checkbox Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.CheckboxDescription
     * @generated
     */
    public Adapter createCheckboxDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.FlexboxContainerDescription <em>Flexbox Container
     * Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.FlexboxContainerDescription
     * @generated
     */
    public Adapter createFlexboxContainerDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.ImageDescription
     * <em>Image Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.ImageDescription
     * @generated
     */
    public Adapter createImageDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.LabelDescription
     * <em>Label Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.LabelDescription
     * @generated
     */
    public Adapter createLabelDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.LinkDescription
     * <em>Link Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.LinkDescription
     * @generated
     */
    public Adapter createLinkDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.ListDescription
     * <em>List Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
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
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.PieChartDescription
     * <em>Pie Chart Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.PieChartDescription
     * @generated
     */
    public Adapter createPieChartDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.RadioDescription
     * <em>Radio Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.RadioDescription
     * @generated
     */
    public Adapter createRadioDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.RichTextDescription
     * <em>Rich Text Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.RichTextDescription
     * @generated
     */
    public Adapter createRichTextDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.SelectDescription
     * <em>Select Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.SelectDescription
     * @generated
     */
    public Adapter createSelectDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.TextAreaDescription
     * <em>Text Area Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.TextAreaDescription
     * @generated
     */
    public Adapter createTextAreaDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.TextfieldDescription
     * <em>Textfield Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
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
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.BarChartDescriptionStyle <em>Bar Chart Description Style</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
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
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.CheckboxDescriptionStyle <em>Checkbox Description Style</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
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
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.LinkDescriptionStyle
     * <em>Link Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
     * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.LinkDescriptionStyle
     * @generated
     */
    public Adapter createLinkDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.ConditionalLinkDescriptionStyle <em>Conditional Link Description
     * Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.ConditionalLinkDescriptionStyle
     * @generated
     */
    public Adapter createConditionalLinkDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.ListDescriptionStyle
     * <em>List Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
     * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.ListDescriptionStyle
     * @generated
     */
    public Adapter createListDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.ConditionalListDescriptionStyle <em>Conditional List Description
     * Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.ConditionalListDescriptionStyle
     * @generated
     */
    public Adapter createConditionalListDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle <em>Multi Select Description
     * Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle
     * @generated
     */
    public Adapter createMultiSelectDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.ConditionalMultiSelectDescriptionStyle <em>Conditional Multi
     * Select Description Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.form.ConditionalMultiSelectDescriptionStyle
     * @generated
     */
    public Adapter createConditionalMultiSelectDescriptionStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.PieChartDescriptionStyle <em>Pie Chart Description Style</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
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
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.TextareaDescriptionStyle <em>Textarea Description Style</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
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
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.TextfieldDescriptionStyle <em>Textfield Description Style</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
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
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.form.ContainerBorderStyle
     * <em>Container Border Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
     * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.form.ContainerBorderStyle
     */
    public Adapter createContainerBorderStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.form.ConditionalContainerBorderStyle <em>Conditional Container Border
     * Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.form.ConditionalContainerBorderStyle
     */
    public Adapter createConditionalContainerBorderStyleAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.RepresentationDescription
     * <em>Representation Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
     * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.RepresentationDescription
     * @generated
     */
    public Adapter createRepresentationDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.LabelStyle <em>Label
     * Style</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
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
     * Creates a new adapter for the default case. <!-- begin-user-doc --> This default implementation returns null.
     * <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

} // FormAdapterFactory
