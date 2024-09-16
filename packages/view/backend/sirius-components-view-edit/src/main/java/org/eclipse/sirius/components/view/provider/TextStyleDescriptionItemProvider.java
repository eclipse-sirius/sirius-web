/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.view.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
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
import org.eclipse.sirius.components.view.TextStyleDescription;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.view.TextStyleDescription} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class TextStyleDescriptionItemProvider extends ItemProviderAdapter
        implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public TextStyleDescriptionItemProvider(AdapterFactory adapterFactory) {
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

            this.addNamePropertyDescriptor(object);
            this.addForegroundColorExpressionPropertyDescriptor(object);
            this.addBackgroundColorExpressionPropertyDescriptor(object);
            this.addIsBoldExpressionPropertyDescriptor(object);
            this.addIsItalicExpressionPropertyDescriptor(object);
            this.addIsUnderlineExpressionPropertyDescriptor(object);
        }
        return this.itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Name feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addNamePropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_TextStyleDescription_name_feature"), this.getString("_UI_PropertyDescriptor_description", "_UI_TextStyleDescription_name_feature", "_UI_TextStyleDescription_type"),
                ViewPackage.Literals.TEXT_STYLE_DESCRIPTION__NAME, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Foreground Color Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addForegroundColorExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_TextStyleDescription_foregroundColorExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_TextStyleDescription_foregroundColorExpression_feature", "_UI_TextStyleDescription_type"),
                ViewPackage.Literals.TEXT_STYLE_DESCRIPTION__FOREGROUND_COLOR_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Background Color Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addBackgroundColorExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_TextStyleDescription_backgroundColorExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_TextStyleDescription_backgroundColorExpression_feature", "_UI_TextStyleDescription_type"),
                ViewPackage.Literals.TEXT_STYLE_DESCRIPTION__BACKGROUND_COLOR_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Is Bold Expression feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addIsBoldExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_TextStyleDescription_isBoldExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_TextStyleDescription_isBoldExpression_feature", "_UI_TextStyleDescription_type"),
                ViewPackage.Literals.TEXT_STYLE_DESCRIPTION__IS_BOLD_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Is Italic Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addIsItalicExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_TextStyleDescription_isItalicExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_TextStyleDescription_isItalicExpression_feature", "_UI_TextStyleDescription_type"),
                ViewPackage.Literals.TEXT_STYLE_DESCRIPTION__IS_ITALIC_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Is Underline Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addIsUnderlineExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_TextStyleDescription_isUnderlineExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_TextStyleDescription_isUnderlineExpression_feature", "_UI_TextStyleDescription_type"),
                ViewPackage.Literals.TEXT_STYLE_DESCRIPTION__IS_UNDERLINE_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This returns TextStyleDescription.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/TextStyleDescription.svg"));
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
        String label = ((TextStyleDescription) object).getName();
        return label == null || label.length() == 0 ? this.getString("_UI_TextStyleDescription_type") : this.getString("_UI_TextStyleDescription_type") + " " + label;
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

        switch (notification.getFeatureID(TextStyleDescription.class)) {
            case ViewPackage.TEXT_STYLE_DESCRIPTION__NAME:
            case ViewPackage.TEXT_STYLE_DESCRIPTION__FOREGROUND_COLOR_EXPRESSION:
            case ViewPackage.TEXT_STYLE_DESCRIPTION__BACKGROUND_COLOR_EXPRESSION:
            case ViewPackage.TEXT_STYLE_DESCRIPTION__IS_BOLD_EXPRESSION:
            case ViewPackage.TEXT_STYLE_DESCRIPTION__IS_ITALIC_EXPRESSION:
            case ViewPackage.TEXT_STYLE_DESCRIPTION__IS_UNDERLINE_EXPRESSION:
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
