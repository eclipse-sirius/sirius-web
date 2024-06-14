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
import org.eclipse.sirius.components.view.gantt.GanttFactory;
import org.eclipse.sirius.components.view.gantt.GanttPackage;
import org.eclipse.sirius.components.view.gantt.TaskDescription;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.view.gantt.TaskDescription} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class TaskDescriptionItemProvider extends ItemProviderAdapter
        implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public TaskDescriptionItemProvider(AdapterFactory adapterFactory) {
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
            this.addDomainTypePropertyDescriptor(object);
            this.addSemanticCandidatesExpressionPropertyDescriptor(object);
            this.addNameExpressionPropertyDescriptor(object);
            this.addDescriptionExpressionPropertyDescriptor(object);
            this.addStartTimeExpressionPropertyDescriptor(object);
            this.addEndTimeExpressionPropertyDescriptor(object);
            this.addProgressExpressionPropertyDescriptor(object);
            this.addComputeStartEndDynamicallyExpressionPropertyDescriptor(object);
            this.addTaskDependenciesExpressionPropertyDescriptor(object);
            this.addReusedTaskElementDescriptionsPropertyDescriptor(object);
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
                this.getString("_UI_TaskDescription_name_feature"), this.getString("_UI_PropertyDescriptor_description", "_UI_TaskDescription_name_feature", "_UI_TaskDescription_type"),
                GanttPackage.Literals.TASK_DESCRIPTION__NAME, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Domain Type feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addDomainTypePropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_TaskDescription_domainType_feature"), this.getString("_UI_PropertyDescriptor_description", "_UI_TaskDescription_domainType_feature", "_UI_TaskDescription_type"),
                GanttPackage.Literals.TASK_DESCRIPTION__DOMAIN_TYPE, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Semantic Candidates Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addSemanticCandidatesExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_TaskDescription_semanticCandidatesExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_TaskDescription_semanticCandidatesExpression_feature", "_UI_TaskDescription_type"),
                GanttPackage.Literals.TASK_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Name Expression feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addNameExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_TaskDescription_nameExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_TaskDescription_nameExpression_feature", "_UI_TaskDescription_type"), GanttPackage.Literals.TASK_DESCRIPTION__NAME_EXPRESSION,
                true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Description Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addDescriptionExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_TaskDescription_descriptionExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_TaskDescription_descriptionExpression_feature", "_UI_TaskDescription_type"),
                GanttPackage.Literals.TASK_DESCRIPTION__DESCRIPTION_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Start Time Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addStartTimeExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_TaskDescription_startTimeExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_TaskDescription_startTimeExpression_feature", "_UI_TaskDescription_type"),
                GanttPackage.Literals.TASK_DESCRIPTION__START_TIME_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the End Time Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addEndTimeExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_TaskDescription_endTimeExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_TaskDescription_endTimeExpression_feature", "_UI_TaskDescription_type"),
                GanttPackage.Literals.TASK_DESCRIPTION__END_TIME_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Progress Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addProgressExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_TaskDescription_progressExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_TaskDescription_progressExpression_feature", "_UI_TaskDescription_type"),
                GanttPackage.Literals.TASK_DESCRIPTION__PROGRESS_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Compute Start End Dynamically Expression feature. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addComputeStartEndDynamicallyExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_TaskDescription_computeStartEndDynamicallyExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_TaskDescription_computeStartEndDynamicallyExpression_feature", "_UI_TaskDescription_type"),
                GanttPackage.Literals.TASK_DESCRIPTION__COMPUTE_START_END_DYNAMICALLY_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Task Dependencies Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addTaskDependenciesExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_TaskDescription_taskDependenciesExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_TaskDescription_taskDependenciesExpression_feature", "_UI_TaskDescription_type"),
                GanttPackage.Literals.TASK_DESCRIPTION__TASK_DEPENDENCIES_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Reused Task Element Descriptions feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addReusedTaskElementDescriptionsPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_TaskDescription_reusedTaskElementDescriptions_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_TaskDescription_reusedTaskElementDescriptions_feature", "_UI_TaskDescription_type"),
                GanttPackage.Literals.TASK_DESCRIPTION__REUSED_TASK_ELEMENT_DESCRIPTIONS, true, false, true, null, null, null));
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
            this.childrenFeatures.add(GanttPackage.Literals.TASK_DESCRIPTION__SUB_TASK_ELEMENT_DESCRIPTIONS);
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
     * This returns TaskDescription.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/TaskDescription.svg"));
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
        String label = ((TaskDescription) object).getName();
        return label == null || label.length() == 0 ? this.getString("_UI_TaskDescription_type") : this.getString("_UI_TaskDescription_type") + " " + label;
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

        switch (notification.getFeatureID(TaskDescription.class)) {
            case GanttPackage.TASK_DESCRIPTION__NAME:
            case GanttPackage.TASK_DESCRIPTION__DOMAIN_TYPE:
            case GanttPackage.TASK_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
            case GanttPackage.TASK_DESCRIPTION__NAME_EXPRESSION:
            case GanttPackage.TASK_DESCRIPTION__DESCRIPTION_EXPRESSION:
            case GanttPackage.TASK_DESCRIPTION__START_TIME_EXPRESSION:
            case GanttPackage.TASK_DESCRIPTION__END_TIME_EXPRESSION:
            case GanttPackage.TASK_DESCRIPTION__PROGRESS_EXPRESSION:
            case GanttPackage.TASK_DESCRIPTION__COMPUTE_START_END_DYNAMICALLY_EXPRESSION:
            case GanttPackage.TASK_DESCRIPTION__TASK_DEPENDENCIES_EXPRESSION:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
            case GanttPackage.TASK_DESCRIPTION__SUB_TASK_ELEMENT_DESCRIPTIONS:
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

        newChildDescriptors.add(this.createChildParameter(GanttPackage.Literals.TASK_DESCRIPTION__SUB_TASK_ELEMENT_DESCRIPTIONS, GanttFactory.eINSTANCE.createTaskDescription()));
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
