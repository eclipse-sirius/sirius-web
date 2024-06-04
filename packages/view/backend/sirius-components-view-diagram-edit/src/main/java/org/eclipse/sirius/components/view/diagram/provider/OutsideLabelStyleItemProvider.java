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
package org.eclipse.sirius.components.view.diagram.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.OutsideLabelStyle;
import org.eclipse.sirius.components.view.provider.LabelStyleItemProvider;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.view.diagram.OutsideLabelStyle} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class OutsideLabelStyleItemProvider extends LabelStyleItemProvider {

    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public OutsideLabelStyleItemProvider(AdapterFactory adapterFactory) {
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

            this.addBorderColorPropertyDescriptor(object);
            this.addBorderRadiusPropertyDescriptor(object);
            this.addBorderSizePropertyDescriptor(object);
            this.addBorderLineStylePropertyDescriptor(object);
            this.addLabelColorPropertyDescriptor(object);
            this.addBackgroundPropertyDescriptor(object);
            this.addShowIconPropertyDescriptor(object);
            this.addLabelIconPropertyDescriptor(object);
        }
        return this.itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Border Color feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addBorderColorPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_BorderStyle_borderColor_feature"), this.getString("_UI_PropertyDescriptor_description", "_UI_BorderStyle_borderColor_feature", "_UI_BorderStyle_type"),
                DiagramPackage.Literals.BORDER_STYLE__BORDER_COLOR, true, false, false, null, null, null));
    }

    /**
     * This adds a property descriptor for the Border Radius feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addBorderRadiusPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_BorderStyle_borderRadius_feature"), this.getString("_UI_PropertyDescriptor_description", "_UI_BorderStyle_borderRadius_feature", "_UI_BorderStyle_type"),
                DiagramPackage.Literals.BORDER_STYLE__BORDER_RADIUS, true, false, false, ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Border Size feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addBorderSizePropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_BorderStyle_borderSize_feature"), this.getString("_UI_PropertyDescriptor_description", "_UI_BorderStyle_borderSize_feature", "_UI_BorderStyle_type"),
                DiagramPackage.Literals.BORDER_STYLE__BORDER_SIZE, true, false, false, ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Border Line Style feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addBorderLineStylePropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_BorderStyle_borderLineStyle_feature"), this.getString("_UI_PropertyDescriptor_description", "_UI_BorderStyle_borderLineStyle_feature", "_UI_BorderStyle_type"),
                DiagramPackage.Literals.BORDER_STYLE__BORDER_LINE_STYLE, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Label Color feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addLabelColorPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_NodeLabelStyle_labelColor_feature"), this.getString("_UI_PropertyDescriptor_description", "_UI_NodeLabelStyle_labelColor_feature", "_UI_NodeLabelStyle_type"),
                DiagramPackage.Literals.NODE_LABEL_STYLE__LABEL_COLOR, true, false, true, null, null, null));
    }

    /**
     * This adds a property descriptor for the Show Icon feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addShowIconPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_NodeLabelStyle_showIcon_feature"), this.getString("_UI_PropertyDescriptor_description", "_UI_NodeLabelStyle_showIcon_feature", "_UI_NodeLabelStyle_type"),
                DiagramPackage.Literals.NODE_LABEL_STYLE__SHOW_ICON, true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Label Icon feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addLabelIconPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_NodeLabelStyle_labelIcon_feature"), this.getString("_UI_PropertyDescriptor_description", "_UI_NodeLabelStyle_labelIcon_feature", "_UI_NodeLabelStyle_type"),
                DiagramPackage.Literals.NODE_LABEL_STYLE__LABEL_ICON, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Background feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addBackgroundPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_NodeLabelStyle_background_feature"), this.getString("_UI_PropertyDescriptor_description", "_UI_NodeLabelStyle_background_feature", "_UI_NodeLabelStyle_type"),
                DiagramPackage.Literals.NODE_LABEL_STYLE__BACKGROUND, true, false, true, null, null, null));
    }

    /**
     * This returns OutsideLabelStyle.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/Style.svg"));
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
        OutsideLabelStyle outsideLabelStyle = (OutsideLabelStyle) object;
        return this.getString("_UI_OutsideLabelStyle_type") + " " + outsideLabelStyle.getFontSize();
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

        switch (notification.getFeatureID(OutsideLabelStyle.class)) {
            case DiagramPackage.OUTSIDE_LABEL_STYLE__BORDER_COLOR:
            case DiagramPackage.OUTSIDE_LABEL_STYLE__BORDER_RADIUS:
            case DiagramPackage.OUTSIDE_LABEL_STYLE__BORDER_SIZE:
            case DiagramPackage.OUTSIDE_LABEL_STYLE__BORDER_LINE_STYLE:
            case DiagramPackage.OUTSIDE_LABEL_STYLE__SHOW_ICON:
            case DiagramPackage.OUTSIDE_LABEL_STYLE__LABEL_ICON:
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
