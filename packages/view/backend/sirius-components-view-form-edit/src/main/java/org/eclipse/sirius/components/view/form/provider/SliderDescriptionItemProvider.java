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
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.SliderDescription;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.view.form.SliderDescription} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class SliderDescriptionItemProvider extends WidgetDescriptionItemProvider {
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public SliderDescriptionItemProvider(AdapterFactory adapterFactory) {
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

            this.addMinValueExpressionPropertyDescriptor(object);
            this.addMaxValueExpressionPropertyDescriptor(object);
            this.addCurrentValueExpressionPropertyDescriptor(object);
            this.addIsEnabledExpressionPropertyDescriptor(object);
        }
        return this.itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Min Value Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addMinValueExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_SliderDescription_minValueExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_SliderDescription_minValueExpression_feature", "_UI_SliderDescription_type"),
                FormPackage.Literals.SLIDER_DESCRIPTION__MIN_VALUE_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Max Value Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addMaxValueExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_SliderDescription_maxValueExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_SliderDescription_maxValueExpression_feature", "_UI_SliderDescription_type"),
                FormPackage.Literals.SLIDER_DESCRIPTION__MAX_VALUE_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Current Value Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addCurrentValueExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_SliderDescription_currentValueExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_SliderDescription_currentValueExpression_feature", "_UI_SliderDescription_type"),
                FormPackage.Literals.SLIDER_DESCRIPTION__CURRENT_VALUE_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Is Enabled Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addIsEnabledExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_SliderDescription_isEnabledExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_SliderDescription_isEnabledExpression_feature", "_UI_SliderDescription_type"),
                FormPackage.Literals.SLIDER_DESCRIPTION__IS_ENABLED_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
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
            this.childrenFeatures.add(FormPackage.Literals.SLIDER_DESCRIPTION__BODY);
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
     * This returns SliderDescription.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/SliderDescription.svg"));
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
        String label = ((SliderDescription) object).getName();
        return label == null || label.length() == 0 ? this.getString("_UI_SliderDescription_type") : this.getString("_UI_SliderDescription_type") + " " + label;
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

        switch (notification.getFeatureID(SliderDescription.class)) {
            case FormPackage.SLIDER_DESCRIPTION__MIN_VALUE_EXPRESSION:
            case FormPackage.SLIDER_DESCRIPTION__MAX_VALUE_EXPRESSION:
            case FormPackage.SLIDER_DESCRIPTION__CURRENT_VALUE_EXPRESSION:
            case FormPackage.SLIDER_DESCRIPTION__IS_ENABLED_EXPRESSION:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
            case FormPackage.SLIDER_DESCRIPTION__BODY:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
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

        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.SLIDER_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createChangeContext()));

        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.SLIDER_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createCreateInstance()));

        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.SLIDER_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createSetValue()));

        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.SLIDER_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createUnsetValue()));

        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.SLIDER_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createDeleteElement()));

        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.SLIDER_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createLet()));

        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.SLIDER_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createIf()));

        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.SLIDER_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createFor()));
    }

}
