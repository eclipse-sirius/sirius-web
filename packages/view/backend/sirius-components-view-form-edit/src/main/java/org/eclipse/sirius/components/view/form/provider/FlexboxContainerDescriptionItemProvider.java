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
package org.eclipse.sirius.components.view.form.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.components.view.form.BarChartDescription;
import org.eclipse.sirius.components.view.form.ButtonDescription;
import org.eclipse.sirius.components.view.form.CheckboxDescription;
import org.eclipse.sirius.components.view.form.FlexDirection;
import org.eclipse.sirius.components.view.form.FlexboxContainerDescription;
import org.eclipse.sirius.components.view.form.FormFactory;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.ImageDescription;
import org.eclipse.sirius.components.view.form.LabelDescription;
import org.eclipse.sirius.components.view.form.LinkDescription;
import org.eclipse.sirius.components.view.form.ListDescription;
import org.eclipse.sirius.components.view.form.MultiSelectDescription;
import org.eclipse.sirius.components.view.form.PieChartDescription;
import org.eclipse.sirius.components.view.form.RadioDescription;
import org.eclipse.sirius.components.view.form.RichTextDescription;
import org.eclipse.sirius.components.view.form.SelectDescription;
import org.eclipse.sirius.components.view.form.TextAreaDescription;
import org.eclipse.sirius.components.view.form.TextfieldDescription;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.view.form.FlexboxContainerDescription}
 * object. <!-- begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class FlexboxContainerDescriptionItemProvider extends WidgetDescriptionItemProvider {
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public FlexboxContainerDescriptionItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        if (this.itemPropertyDescriptors == null) {
            super.getPropertyDescriptors(object);

            this.addFlexDirectionPropertyDescriptor(object);
            this.addIsEnabledExpressionPropertyDescriptor(object);
            this.addFlexboxJustifyContentPropertyDescriptor(object);
            this.addFlexboxAlignItemsPropertyDescriptor(object);
            this.addMarginPropertyDescriptor(object);
            this.addPaddingPropertyDescriptor(object);
            this.addGapPropertyDescriptor(object);
        }
        return this.itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Flex Direction feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addFlexDirectionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_FlexboxContainerDescription_flexDirection_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_FlexboxContainerDescription_flexDirection_feature", "_UI_FlexboxContainerDescription_type"),
                FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__FLEX_DIRECTION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Is Enabled Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addIsEnabledExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_FlexboxContainerDescription_IsEnabledExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_FlexboxContainerDescription_IsEnabledExpression_feature", "_UI_FlexboxContainerDescription_type"),
                FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__IS_ENABLED_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Flexbox Justify Content feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addFlexboxJustifyContentPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_FlexboxContainerDescription_flexboxJustifyContent_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_FlexboxContainerDescription_flexboxJustifyContent_feature", "_UI_FlexboxContainerDescription_type"),
                FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__FLEXBOX_JUSTIFY_CONTENT, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Flexbox Align Items feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addFlexboxAlignItemsPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_FlexboxContainerDescription_flexboxAlignItems_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_FlexboxContainerDescription_flexboxAlignItems_feature", "_UI_FlexboxContainerDescription_type"),
                FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__FLEXBOX_ALIGN_ITEMS, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Margin feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addMarginPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_FlexboxContainerDescription_margin_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_FlexboxContainerDescription_margin_feature", "_UI_FlexboxContainerDescription_type"),
                FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__MARGIN, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Padding feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addPaddingPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_FlexboxContainerDescription_padding_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_FlexboxContainerDescription_padding_feature", "_UI_FlexboxContainerDescription_type"),
                FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__PADDING, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Gap feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addGapPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_FlexboxContainerDescription_gap_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_FlexboxContainerDescription_gap_feature", "_UI_FlexboxContainerDescription_type"),
                FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__GAP, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
     * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
     * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    @Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
        if (this.childrenFeatures == null) {
            super.getChildrenFeatures(object);
            this.childrenFeatures.add(FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN);
            this.childrenFeatures.add(FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__BORDER_STYLE);
            this.childrenFeatures.add(FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__CONDITIONAL_BORDER_STYLES);
        }
        return this.childrenFeatures;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EStructuralFeature getChildFeature(Object object, Object child) {
        // Check the type of the specified child object and return the proper feature to use for
        // adding (see {@link AddCommand}) it as a child.

        return super.getChildFeature(object, child);
    }

    /**
     * This returns FlexboxContainerDescription.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        if (object instanceof FlexboxContainerDescription && FlexDirection.ROW.equals(((FlexboxContainerDescription) object).getFlexDirection())) {
            return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/FlexboxContainerDescription_Row.svg"));
        }
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/FlexboxContainerDescription_Column.svg"));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected boolean shouldComposeCreationImage() {
        return true;
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getText(Object object) {
        String label = ((FlexboxContainerDescription) object).getName();
        return label == null || label.length() == 0 ? this.getString("_UI_FlexboxContainerDescription_type") : this.getString("_UI_FlexboxContainerDescription_type") + " " + label;
    }

    /**
     * This handles model notifications by calling {@link #updateChildren} to update any cached children and by creating
     * a viewer notification, which it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    @Override
    public void notifyChanged(Notification notification) {
        this.updateChildren(notification);

        switch (notification.getFeatureID(FlexboxContainerDescription.class)) {
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__FLEX_DIRECTION:
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__IS_ENABLED_EXPRESSION:
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__FLEXBOX_JUSTIFY_CONTENT:
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__FLEXBOX_ALIGN_ITEMS:
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__MARGIN:
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__PADDING:
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__GAP:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN:
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__BORDER_STYLE:
            case FormPackage.FLEXBOX_CONTAINER_DESCRIPTION__CONDITIONAL_BORDER_STYLES:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
                return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children that can be created
     * under this object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN, FormFactory.eINSTANCE.createFormElementFor()));
        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN, FormFactory.eINSTANCE.createFormElementIf()));

        BarChartDescription barChartDescription = FormFactory.eINSTANCE.createBarChartDescription();
        barChartDescription.setStyle(FormFactory.eINSTANCE.createBarChartDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN, barChartDescription));

        ButtonDescription buttonDescription = FormFactory.eINSTANCE.createButtonDescription();
        buttonDescription.setStyle(FormFactory.eINSTANCE.createButtonDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN, buttonDescription));

        CheckboxDescription checkboxDescription = FormFactory.eINSTANCE.createCheckboxDescription();
        checkboxDescription.setStyle(FormFactory.eINSTANCE.createCheckboxDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN, checkboxDescription));

        FlexboxContainerDescription flexboxContainerDescription = FormFactory.eINSTANCE.createFlexboxContainerDescription();
        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN, flexboxContainerDescription));

        ImageDescription imageDescription = FormFactory.eINSTANCE.createImageDescription();
        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN, imageDescription));

        LabelDescription labelDescription = FormFactory.eINSTANCE.createLabelDescription();
        labelDescription.setStyle(FormFactory.eINSTANCE.createLabelDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN, labelDescription));

        LinkDescription linkDescription = FormFactory.eINSTANCE.createLinkDescription();
        linkDescription.setStyle(FormFactory.eINSTANCE.createLinkDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN, linkDescription));

        ListDescription listDescription = FormFactory.eINSTANCE.createListDescription();
        listDescription.setStyle(FormFactory.eINSTANCE.createListDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN, listDescription));

        MultiSelectDescription multiSelectDescription = FormFactory.eINSTANCE.createMultiSelectDescription();
        multiSelectDescription.setStyle(FormFactory.eINSTANCE.createMultiSelectDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN, multiSelectDescription));

        PieChartDescription pieChartDescription = FormFactory.eINSTANCE.createPieChartDescription();
        pieChartDescription.setStyle(FormFactory.eINSTANCE.createPieChartDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN, pieChartDescription));

        RadioDescription radioDescription = FormFactory.eINSTANCE.createRadioDescription();
        radioDescription.setStyle(FormFactory.eINSTANCE.createRadioDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN, radioDescription));

        RichTextDescription richTextDescription = FormFactory.eINSTANCE.createRichTextDescription();
        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN, richTextDescription));

        SelectDescription selectDescription = FormFactory.eINSTANCE.createSelectDescription();
        selectDescription.setStyle(FormFactory.eINSTANCE.createSelectDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN, selectDescription));

        TextAreaDescription textareaDescription = FormFactory.eINSTANCE.createTextAreaDescription();
        textareaDescription.setStyle(FormFactory.eINSTANCE.createTextareaDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN, textareaDescription));

        TextfieldDescription textfieldDescription = FormFactory.eINSTANCE.createTextfieldDescription();
        textfieldDescription.setStyle(FormFactory.eINSTANCE.createTextfieldDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN, textfieldDescription));

        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__BORDER_STYLE, FormFactory.eINSTANCE.createContainerBorderStyle()));

        newChildDescriptors
                .add(this.createChildParameter(FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__CONDITIONAL_BORDER_STYLES, FormFactory.eINSTANCE.createConditionalContainerBorderStyle()));
    }

    /**
     * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
        Object childFeature = feature;
        Object childObject = child;

        boolean qualify = childFeature == FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__BORDER_STYLE
                || childFeature == FormPackage.Literals.FLEXBOX_CONTAINER_DESCRIPTION__CONDITIONAL_BORDER_STYLES;

        if (qualify) {
            return this.getString("_UI_CreateChild_text2", new Object[] { this.getTypeText(childObject), this.getFeatureText(childFeature), this.getTypeText(owner) });
        }
        return super.getCreateChildText(owner, feature, child, selection);
    }

}
