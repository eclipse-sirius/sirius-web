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
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.papaya.Project;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.papaya.Project} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class ProjectItemProvider extends NamedElementItemProvider {
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ProjectItemProvider(AdapterFactory adapterFactory) {
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

        }
        return this.itemPropertyDescriptors;
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
            this.childrenFeatures.add(PapayaPackage.Literals.PROJECT__PROJECTS);
            this.childrenFeatures.add(PapayaPackage.Literals.PROJECT__COMPONENTS);
            this.childrenFeatures.add(PapayaPackage.Literals.PROJECT__COMPONENT_EXCHANGES);
            this.childrenFeatures.add(PapayaPackage.Literals.PROJECT__ITERATIONS);
            this.childrenFeatures.add(PapayaPackage.Literals.PROJECT__TASKS);
            this.childrenFeatures.add(PapayaPackage.Literals.PROJECT__CONTRIBUTIONS);
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
     * This returns Project.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/Project"));
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
        String label = ((Project) object).getName();
        return label == null || label.length() == 0 ? this.getString("_UI_Project_type") : this.getString("_UI_Project_type") + " " + label;
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

        switch (notification.getFeatureID(Project.class)) {
            case PapayaPackage.PROJECT__PROJECTS:
            case PapayaPackage.PROJECT__COMPONENTS:
            case PapayaPackage.PROJECT__COMPONENT_EXCHANGES:
            case PapayaPackage.PROJECT__ITERATIONS:
            case PapayaPackage.PROJECT__TASKS:
            case PapayaPackage.PROJECT__CONTRIBUTIONS:
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

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.PROJECT__PROJECTS, PapayaFactory.eINSTANCE.createProject()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.PROJECT__COMPONENTS, PapayaFactory.eINSTANCE.createComponent()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.PROJECT__COMPONENT_EXCHANGES, PapayaFactory.eINSTANCE.createComponentExchange()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.PROJECT__ITERATIONS, PapayaFactory.eINSTANCE.createIteration()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.PROJECT__TASKS, PapayaFactory.eINSTANCE.createTask()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.PROJECT__CONTRIBUTIONS, PapayaFactory.eINSTANCE.createContribution()));
    }

}
