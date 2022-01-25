/**
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.components.domain.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.components.domain.Attribute;
import org.eclipse.sirius.components.domain.DomainPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.domain.Attribute} object. <!-- begin-user-doc
 * --> <!-- end-user-doc -->
 *
 * @generated
 */
public class AttributeItemProvider extends FeatureItemProvider {
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public AttributeItemProvider(AdapterFactory adapterFactory) {
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
        }
        return this.itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Type feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addTypePropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(
                this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(), this.getString("_UI_Attribute_type_feature"), //$NON-NLS-1$
                        this.getString("_UI_PropertyDescriptor_description", "_UI_Attribute_type_feature", "_UI_Attribute_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        DomainPackage.Literals.ATTRIBUTE__TYPE, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This returns Attribute.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        if (object instanceof Attribute) {
            String imageName = null;
            Attribute attr = (Attribute) object;
            switch (attr.getType()) {
            case BOOLEAN:
                imageName = "Boolean"; //$NON-NLS-1$
                break;
            case STRING:
                imageName = "String"; //$NON-NLS-1$
                break;
            case NUMBER:
                imageName = "Number"; //$NON-NLS-1$
                break;
            default:
                imageName = "Invalid"; //$NON-NLS-1$
                break;
            }
            if (attr.isMany()) {
                imageName = "Many" + imageName; //$NON-NLS-1$
            }
            return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/" + imageName + "Attribute.svg")); //$NON-NLS-1$//$NON-NLS-2$
        } else {
            return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/Attribute")); //$NON-NLS-1$
        }
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
        String label = ((Attribute) object).getName();
        return label == null || label.length() == 0 ? this.getString("_UI_Attribute_type") : //$NON-NLS-1$
                this.getString("_UI_Attribute_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

        switch (notification.getFeatureID(Attribute.class)) {
        case DomainPackage.ATTRIBUTE__TYPE:
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
