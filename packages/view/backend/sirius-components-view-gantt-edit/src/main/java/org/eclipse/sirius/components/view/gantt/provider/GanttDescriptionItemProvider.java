/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.view.gantt.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.gantt.GanttDescription;
import org.eclipse.sirius.components.view.gantt.GanttFactory;
import org.eclipse.sirius.components.view.gantt.GanttPackage;
import org.eclipse.sirius.components.view.provider.RepresentationDescriptionItemProvider;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.view.gantt.GanttDescription} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class GanttDescriptionItemProvider extends RepresentationDescriptionItemProvider {
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public GanttDescriptionItemProvider(AdapterFactory adapterFactory) {
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
            this.childrenFeatures.add(GanttPackage.Literals.GANTT_DESCRIPTION__TASK_ELEMENT_DESCRIPTIONS);
            this.childrenFeatures.add(GanttPackage.Literals.GANTT_DESCRIPTION__BACKGROUND_COLOR);
            this.childrenFeatures.add(GanttPackage.Literals.GANTT_DESCRIPTION__CREATE_TOOL);
            this.childrenFeatures.add(GanttPackage.Literals.GANTT_DESCRIPTION__EDIT_TOOL);
            this.childrenFeatures.add(GanttPackage.Literals.GANTT_DESCRIPTION__DELETE_TOOL);
            this.childrenFeatures.add(GanttPackage.Literals.GANTT_DESCRIPTION__DROP_TOOL);
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
     * This returns GanttDescription.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/GanttDescription.svg"));
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
        String label = ((GanttDescription) object).getName();
        return label == null || label.length() == 0 ? this.getString("_UI_GanttDescription_type") : this.getString("_UI_GanttDescription_type") + " " + label;
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

        switch (notification.getFeatureID(GanttDescription.class)) {
            case GanttPackage.GANTT_DESCRIPTION__TASK_ELEMENT_DESCRIPTIONS:
            case GanttPackage.GANTT_DESCRIPTION__BACKGROUND_COLOR:
            case GanttPackage.GANTT_DESCRIPTION__CREATE_TOOL:
            case GanttPackage.GANTT_DESCRIPTION__EDIT_TOOL:
            case GanttPackage.GANTT_DESCRIPTION__DELETE_TOOL:
            case GanttPackage.GANTT_DESCRIPTION__DROP_TOOL:
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

        newChildDescriptors.add(this.createChildParameter(GanttPackage.Literals.GANTT_DESCRIPTION__TASK_ELEMENT_DESCRIPTIONS, GanttFactory.eINSTANCE.createTaskDescription()));

        newChildDescriptors.add(this.createChildParameter(GanttPackage.Literals.GANTT_DESCRIPTION__BACKGROUND_COLOR, ViewFactory.eINSTANCE.createFixedColor()));

        newChildDescriptors.add(this.createChildParameter(GanttPackage.Literals.GANTT_DESCRIPTION__CREATE_TOOL, GanttFactory.eINSTANCE.createCreateTaskTool()));

        newChildDescriptors.add(this.createChildParameter(GanttPackage.Literals.GANTT_DESCRIPTION__EDIT_TOOL, GanttFactory.eINSTANCE.createEditTaskTool()));

        newChildDescriptors.add(this.createChildParameter(GanttPackage.Literals.GANTT_DESCRIPTION__DELETE_TOOL, GanttFactory.eINSTANCE.createDeleteTaskTool()));

        newChildDescriptors.add(this.createChildParameter(GanttPackage.Literals.GANTT_DESCRIPTION__DROP_TOOL, GanttFactory.eINSTANCE.createDropTaskTool()));
    }

}
