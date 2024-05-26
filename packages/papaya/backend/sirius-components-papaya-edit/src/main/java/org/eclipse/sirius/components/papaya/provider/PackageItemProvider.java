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
package org.eclipse.sirius.components.papaya.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.PapayaPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.papaya.Package} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class PackageItemProvider extends NamedElementItemProvider {
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public PackageItemProvider(AdapterFactory adapterFactory) {
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

            this.addAnnotationsPropertyDescriptor(object);
            this.addQualifiedNamePropertyDescriptor(object);
        }
        return this.itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Annotations feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addAnnotationsPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_AnnotableElement_annotations_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_AnnotableElement_annotations_feature", "_UI_AnnotableElement_type"), PapayaPackage.Literals.ANNOTABLE_ELEMENT__ANNOTATIONS,
                true, false, true, null, null, null));
    }

    /**
     * This adds a property descriptor for the Qualified Name feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addQualifiedNamePropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_Package_qualifiedName_feature"), this.getString("_UI_PropertyDescriptor_description", "_UI_Package_qualifiedName_feature", "_UI_Package_type"),
                PapayaPackage.Literals.PACKAGE__QUALIFIED_NAME, false, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
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
            this.childrenFeatures.add(PapayaPackage.Literals.PACKAGE__TYPES);
            this.childrenFeatures.add(PapayaPackage.Literals.PACKAGE__PACKAGES);
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
     * This returns Package.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/Package"));
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
        String label = ((org.eclipse.sirius.components.papaya.Package) object).getName();
        return label == null || label.length() == 0 ? this.getString("_UI_Package_type") : this.getString("_UI_Package_type") + " " + label;
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

        switch (notification.getFeatureID(org.eclipse.sirius.components.papaya.Package.class)) {
            case PapayaPackage.PACKAGE__QUALIFIED_NAME:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
            case PapayaPackage.PACKAGE__TYPES:
            case PapayaPackage.PACKAGE__PACKAGES:
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

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.PACKAGE__TYPES, PapayaFactory.eINSTANCE.createAnnotation()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.PACKAGE__TYPES, PapayaFactory.eINSTANCE.createInterface()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.PACKAGE__TYPES, PapayaFactory.eINSTANCE.createClass()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.PACKAGE__TYPES, PapayaFactory.eINSTANCE.createRecord()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.PACKAGE__TYPES, PapayaFactory.eINSTANCE.createDataType()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.PACKAGE__TYPES, PapayaFactory.eINSTANCE.createEnum()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.PACKAGE__PACKAGES, PapayaFactory.eINSTANCE.createPackage()));
    }

}
