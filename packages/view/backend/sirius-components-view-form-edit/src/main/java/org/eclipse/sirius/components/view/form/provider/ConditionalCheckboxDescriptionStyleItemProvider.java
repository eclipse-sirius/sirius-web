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
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.components.view.form.ConditionalCheckboxDescriptionStyle;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.provider.ConditionalItemProvider;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.sirius.components.view.form.ConditionalCheckboxDescriptionStyle} object. <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class ConditionalCheckboxDescriptionStyleItemProvider extends ConditionalItemProvider {
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ConditionalCheckboxDescriptionStyleItemProvider(AdapterFactory adapterFactory) {
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
            this.addGapPropertyDescriptor(object);
            this.addLabelFlexPropertyDescriptor(object);
            this.addValueFlexPropertyDescriptor(object);
            this.addColorPropertyDescriptor(object);
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
                this.getString("_UI_WidgetFlexboxLayout_flexDirection_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_WidgetFlexboxLayout_flexDirection_feature", "_UI_WidgetFlexboxLayout_type"),
                FormPackage.Literals.WIDGET_FLEXBOX_LAYOUT__FLEX_DIRECTION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Gap feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addGapPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_WidgetFlexboxLayout_gap_feature"), this.getString("_UI_PropertyDescriptor_description", "_UI_WidgetFlexboxLayout_gap_feature", "_UI_WidgetFlexboxLayout_type"),
                FormPackage.Literals.WIDGET_FLEXBOX_LAYOUT__GAP, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Label Flex feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addLabelFlexPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_WidgetFlexboxLayout_labelFlex_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_WidgetFlexboxLayout_labelFlex_feature", "_UI_WidgetFlexboxLayout_type"),
                FormPackage.Literals.WIDGET_FLEXBOX_LAYOUT__LABEL_FLEX, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Value Flex feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addValueFlexPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_WidgetFlexboxLayout_valueFlex_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_WidgetFlexboxLayout_valueFlex_feature", "_UI_WidgetFlexboxLayout_type"),
                FormPackage.Literals.WIDGET_FLEXBOX_LAYOUT__VALUE_FLEX, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Color feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addColorPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_CheckboxDescriptionStyle_color_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_CheckboxDescriptionStyle_color_feature", "_UI_CheckboxDescriptionStyle_type"),
                FormPackage.Literals.CHECKBOX_DESCRIPTION_STYLE__COLOR, true, false, false, null, null, null));
    }

    /**
     * This returns ConditionalCheckboxDescriptionStyle.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/ConditionalStyle.svg"));
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
        String label = ((ConditionalCheckboxDescriptionStyle) object).getCondition();
        return label == null || label.length() == 0 ? this.getString("_UI_ConditionalCheckboxDescriptionStyle_type") : this.getString("_UI_ConditionalCheckboxDescriptionStyle_type") + " " + label;
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

        switch (notification.getFeatureID(ConditionalCheckboxDescriptionStyle.class)) {
            case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__FLEX_DIRECTION:
            case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__GAP:
            case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__LABEL_FLEX:
            case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__VALUE_FLEX:
            case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__COLOR:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children that can be created
     * under this object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);
    }

}
