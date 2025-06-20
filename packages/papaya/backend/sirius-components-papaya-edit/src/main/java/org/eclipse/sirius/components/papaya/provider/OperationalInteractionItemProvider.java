/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.components.papaya.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.StyledString;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.components.papaya.OperationalInteraction;
import org.eclipse.sirius.components.papaya.PapayaPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.papaya.OperationalInteraction} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class OperationalInteractionItemProvider extends NamedElementItemProvider {
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public OperationalInteractionItemProvider(AdapterFactory adapterFactory) {
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

            this.addTypePropertyDescriptor(object);
            this.addFrequencyPropertyDescriptor(object);
            this.addProtocolPropertyDescriptor(object);
            this.addLatencyPropertyDescriptor(object);
        }
        return this.itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Type feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addTypePropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_OperationalInteraction_type_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_OperationalInteraction_type_feature", "_UI_OperationalInteraction_type"),
                PapayaPackage.Literals.OPERATIONAL_INTERACTION__TYPE, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Frequency feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addFrequencyPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_OperationalInteraction_frequency_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_OperationalInteraction_frequency_feature", "_UI_OperationalInteraction_type"),
                PapayaPackage.Literals.OPERATIONAL_INTERACTION__FREQUENCY, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Protocol feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addProtocolPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_OperationalInteraction_protocol_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_OperationalInteraction_protocol_feature", "_UI_OperationalInteraction_type"),
                PapayaPackage.Literals.OPERATIONAL_INTERACTION__PROTOCOL, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Latency feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addLatencyPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_OperationalInteraction_latency_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_OperationalInteraction_latency_feature", "_UI_OperationalInteraction_type"),
                PapayaPackage.Literals.OPERATIONAL_INTERACTION__LATENCY, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This returns OperationalInteraction.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/OperationalInteraction"));
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
        return ((StyledString) this.getStyledText(object)).getString();
    }

    /**
     * This returns the label styled text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object getStyledText(Object object) {
        String label = ((OperationalInteraction) object).getName();
        StyledString styledLabel = new StyledString();
        if (label == null || label.length() == 0) {
            styledLabel.append(this.getString("_UI_OperationalInteraction_type"), StyledString.Style.QUALIFIER_STYLER);
        } else {
            styledLabel.append(this.getString("_UI_OperationalInteraction_type"), StyledString.Style.QUALIFIER_STYLER).append(" " + label);
        }
        return styledLabel;
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

        switch (notification.getFeatureID(OperationalInteraction.class)) {
            case PapayaPackage.OPERATIONAL_INTERACTION__TYPE:
            case PapayaPackage.OPERATIONAL_INTERACTION__FREQUENCY:
            case PapayaPackage.OPERATIONAL_INTERACTION__PROTOCOL:
            case PapayaPackage.OPERATIONAL_INTERACTION__LATENCY:
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
