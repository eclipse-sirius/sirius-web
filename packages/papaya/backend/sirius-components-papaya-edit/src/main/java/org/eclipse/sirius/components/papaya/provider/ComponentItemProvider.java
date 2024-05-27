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
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.components.papaya.Component;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.PapayaPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.papaya.Component} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class ComponentItemProvider extends NamedElementItemProvider {
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ComponentItemProvider(AdapterFactory adapterFactory) {
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

            this.addDependenciesPropertyDescriptor(object);
            this.addAllDependenciesPropertyDescriptor(object);
            this.addUsedAsDependencyByPropertyDescriptor(object);
            this.addAllComponentsPropertyDescriptor(object);
        }
        return this.itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Dependencies feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addDependenciesPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_Component_dependencies_feature"), this.getString("_UI_PropertyDescriptor_description", "_UI_Component_dependencies_feature", "_UI_Component_type"),
                PapayaPackage.Literals.COMPONENT__DEPENDENCIES, true, false, true, null, null, null));
    }

    /**
     * This adds a property descriptor for the All Dependencies feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addAllDependenciesPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_Component_allDependencies_feature"), this.getString("_UI_PropertyDescriptor_description", "_UI_Component_allDependencies_feature", "_UI_Component_type"),
                PapayaPackage.Literals.COMPONENT__ALL_DEPENDENCIES, false, false, false, null, null, null));
    }

    /**
     * This adds a property descriptor for the Used As Dependency By feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addUsedAsDependencyByPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_Component_usedAsDependencyBy_feature"), this.getString("_UI_PropertyDescriptor_description", "_UI_Component_usedAsDependencyBy_feature", "_UI_Component_type"),
                PapayaPackage.Literals.COMPONENT__USED_AS_DEPENDENCY_BY, true, false, true, null, null, null));
    }

    /**
     * This adds a property descriptor for the All Components feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addAllComponentsPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_Component_allComponents_feature"), this.getString("_UI_PropertyDescriptor_description", "_UI_Component_allComponents_feature", "_UI_Component_type"),
                PapayaPackage.Literals.COMPONENT__ALL_COMPONENTS, false, false, false, null, null, null));
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
            this.childrenFeatures.add(PapayaPackage.Literals.COMPONENT__COMPONENTS);
            this.childrenFeatures.add(PapayaPackage.Literals.COMPONENT__PACKAGES);
            this.childrenFeatures.add(PapayaPackage.Literals.COMPONENT__PORTS);
            this.childrenFeatures.add(PapayaPackage.Literals.COMPONENT__PROVIDED_SERVICES);
            this.childrenFeatures.add(PapayaPackage.Literals.COMPONENT__REQUIRED_SERVICES);
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
     * This returns Component.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/Component"));
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
        String label = ((Component) object).getName();
        return label == null || label.length() == 0 ? this.getString("_UI_Component_type") : this.getString("_UI_Component_type") + " " + label;
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

        switch (notification.getFeatureID(Component.class)) {
            case PapayaPackage.COMPONENT__COMPONENTS:
            case PapayaPackage.COMPONENT__PACKAGES:
            case PapayaPackage.COMPONENT__PORTS:
            case PapayaPackage.COMPONENT__PROVIDED_SERVICES:
            case PapayaPackage.COMPONENT__REQUIRED_SERVICES:
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

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.COMPONENT__COMPONENTS, PapayaFactory.eINSTANCE.createComponent()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.COMPONENT__PACKAGES, PapayaFactory.eINSTANCE.createPackage()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.COMPONENT__PORTS, PapayaFactory.eINSTANCE.createComponentPort()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.COMPONENT__PROVIDED_SERVICES, PapayaFactory.eINSTANCE.createProvidedService()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.COMPONENT__REQUIRED_SERVICES, PapayaFactory.eINSTANCE.createRequiredService()));
    }

}
