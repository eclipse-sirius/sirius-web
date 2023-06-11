/**
 * Copyright (c) 2021, 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.sirius.components.view.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IChildCreationExtender;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.components.view.DynamicDialogDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.view.DynamicDialogDescription} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class DynamicDialogDescriptionItemProvider extends ItemProviderAdapter
        implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public DynamicDialogDescriptionItemProvider(AdapterFactory adapterFactory) {
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

            this.addIdPropertyDescriptor(object);
            this.addTitleExpressionPropertyDescriptor(object);
            this.addDescriptionExpressionPropertyDescriptor(object);
            this.addIsValidExpressionPropertyDescriptor(object);
        }
        return this.itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Id feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addIdPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_DynamicDialogDescription_id_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_DynamicDialogDescription_id_feature", "_UI_DynamicDialogDescription_type"),
                ViewPackage.Literals.DYNAMIC_DIALOG_DESCRIPTION__ID, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Title Expression feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addTitleExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_DynamicDialogDescription_titleExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_DynamicDialogDescription_titleExpression_feature", "_UI_DynamicDialogDescription_type"),
                ViewPackage.Literals.DYNAMIC_DIALOG_DESCRIPTION__TITLE_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Description Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addDescriptionExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_DynamicDialogDescription_descriptionExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_DynamicDialogDescription_descriptionExpression_feature", "_UI_DynamicDialogDescription_type"),
                ViewPackage.Literals.DYNAMIC_DIALOG_DESCRIPTION__DESCRIPTION_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Is Valid Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addIsValidExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_DynamicDialogDescription_isValidExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_DynamicDialogDescription_isValidExpression_feature", "_UI_DynamicDialogDescription_type"),
                ViewPackage.Literals.DYNAMIC_DIALOG_DESCRIPTION__IS_VALID_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
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
            this.childrenFeatures.add(ViewPackage.Literals.DYNAMIC_DIALOG_DESCRIPTION__WIDGET_DESCRIPTIONS);
            this.childrenFeatures.add(ViewPackage.Literals.DYNAMIC_DIALOG_DESCRIPTION__BODY);
            this.childrenFeatures.add(ViewPackage.Literals.DYNAMIC_DIALOG_DESCRIPTION__VALIDATION_MESSAGES);
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
     * This returns DynamicDialogDescription.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/DynamicDialogDescription"));
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
        String label = ((DynamicDialogDescription) object).getId();
        return label == null || label.length() == 0 ? this.getString("_UI_DynamicDialogDescription_type") : this.getString("_UI_DynamicDialogDescription_type") + " " + label;
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

        switch (notification.getFeatureID(DynamicDialogDescription.class)) {
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__ID:
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__TITLE_EXPRESSION:
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__DESCRIPTION_EXPRESSION:
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__IS_VALID_EXPRESSION:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__WIDGET_DESCRIPTIONS:
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__BODY:
            case ViewPackage.DYNAMIC_DIALOG_DESCRIPTION__VALIDATION_MESSAGES:
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

        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.DYNAMIC_DIALOG_DESCRIPTION__WIDGET_DESCRIPTIONS, ViewFactory.eINSTANCE.createDSelectWidgetDescription()));

        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.DYNAMIC_DIALOG_DESCRIPTION__WIDGET_DESCRIPTIONS, ViewFactory.eINSTANCE.createDTextFieldWidgetDescription()));

        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.DYNAMIC_DIALOG_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createChangeContext()));

        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.DYNAMIC_DIALOG_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createCreateInstance()));

        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.DYNAMIC_DIALOG_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createSetValue()));

        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.DYNAMIC_DIALOG_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createUnsetValue()));

        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.DYNAMIC_DIALOG_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createDeleteElement()));

        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.DYNAMIC_DIALOG_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createCreateView()));

        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.DYNAMIC_DIALOG_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createDeleteView()));

        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.DYNAMIC_DIALOG_DESCRIPTION__VALIDATION_MESSAGES, ViewFactory.eINSTANCE.createDValidationMessageDescription()));
    }

    /**
     * Return the resource locator for this item provider's resources. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return ((IChildCreationExtender) this.adapterFactory).getResourceLocator();
    }

}
