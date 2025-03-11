/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import org.eclipse.emf.edit.provider.StyledString;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.components.papaya.Domain;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.PapayaPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.papaya.Domain} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class DomainItemProvider extends NamedElementItemProvider {
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public DomainItemProvider(AdapterFactory adapterFactory) {
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
                this.getString("_UI_Domain_dependencies_feature"), this.getString("_UI_PropertyDescriptor_description", "_UI_Domain_dependencies_feature", "_UI_Domain_type"),
                PapayaPackage.Literals.DOMAIN__DEPENDENCIES, true, false, true, null, null, null));
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
            this.childrenFeatures.add(PapayaPackage.Literals.DOMAIN__SERVICES);
            this.childrenFeatures.add(PapayaPackage.Literals.DOMAIN__REPOSITORIES);
            this.childrenFeatures.add(PapayaPackage.Literals.DOMAIN__EVENTS);
            this.childrenFeatures.add(PapayaPackage.Literals.DOMAIN__COMMANDS);
            this.childrenFeatures.add(PapayaPackage.Literals.DOMAIN__QUERIES);
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
     * This returns Domain.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/Domain"));
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
        String label = ((Domain) object).getName();
        StyledString styledLabel = new StyledString();
        if (label == null || label.length() == 0) {
            styledLabel.append(this.getString("_UI_Domain_type"), StyledString.Style.QUALIFIER_STYLER);
        } else {
            styledLabel.append(this.getString("_UI_Domain_type"), StyledString.Style.QUALIFIER_STYLER).append(" " + label);
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

        switch (notification.getFeatureID(Domain.class)) {
            case PapayaPackage.DOMAIN__SERVICES:
            case PapayaPackage.DOMAIN__REPOSITORIES:
            case PapayaPackage.DOMAIN__EVENTS:
            case PapayaPackage.DOMAIN__COMMANDS:
            case PapayaPackage.DOMAIN__QUERIES:
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

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.DOMAIN__SERVICES, PapayaFactory.eINSTANCE.createService()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.DOMAIN__REPOSITORIES, PapayaFactory.eINSTANCE.createRepository()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.DOMAIN__EVENTS, PapayaFactory.eINSTANCE.createEvent()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.DOMAIN__COMMANDS, PapayaFactory.eINSTANCE.createCommand()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.DOMAIN__QUERIES, PapayaFactory.eINSTANCE.createQuery()));
    }

}
