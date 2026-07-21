/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
package org.eclipse.sirius.components.task.provider;

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
import org.eclipse.sirius.components.task.AbstractTask;
import org.eclipse.sirius.components.task.TaskFactory;
import org.eclipse.sirius.components.task.TaskPackage;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.sirius.components.task.AbstractTask} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class AbstractTaskItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider,
        IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public AbstractTaskItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

    /**
     * This returns the property descriptors for the adapted class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null)
		{
			super.getPropertyDescriptors(object);

			addNamePropertyDescriptor(object);
			addDescriptionPropertyDescriptor(object);
			addStartTimePropertyDescriptor(object);
			addEndTimePropertyDescriptor(object);
			addProgressPropertyDescriptor(object);
			addComputeStartEndDynamicallyPropertyDescriptor(object);
			addTagsPropertyDescriptor(object);
			addDependenciesPropertyDescriptor(object);
			addAssignedPersonsPropertyDescriptor(object);
			addAssignedTeamsPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

    /**
	 * This adds a property descriptor for the Name feature.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected void addNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AbstractTask_name_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AbstractTask_name_feature", "_UI_AbstractTask_type"),
				 TaskPackage.Literals.ABSTRACT_TASK__NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
     * This adds a property descriptor for the Description feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addDescriptionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AbstractTask_description_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AbstractTask_description_feature", "_UI_AbstractTask_type"),
				 TaskPackage.Literals.ABSTRACT_TASK__DESCRIPTION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
     * This adds a property descriptor for the Start Time feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addStartTimePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AbstractTask_startTime_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AbstractTask_startTime_feature", "_UI_AbstractTask_type"),
				 TaskPackage.Literals.ABSTRACT_TASK__START_TIME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the End Time feature.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @generated
	 */
    protected void addEndTimePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AbstractTask_endTime_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AbstractTask_endTime_feature", "_UI_AbstractTask_type"),
				 TaskPackage.Literals.ABSTRACT_TASK__END_TIME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the Progress feature.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @generated
	 */
    protected void addProgressPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AbstractTask_progress_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AbstractTask_progress_feature", "_UI_AbstractTask_type"),
				 TaskPackage.Literals.ABSTRACT_TASK__PROGRESS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the Compute Start End Dynamically feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected void addComputeStartEndDynamicallyPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AbstractTask_computeStartEndDynamically_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AbstractTask_computeStartEndDynamically_feature", "_UI_AbstractTask_type"),
				 TaskPackage.Literals.ABSTRACT_TASK__COMPUTE_START_END_DYNAMICALLY,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the Tags feature.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected void addTagsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AbstractTask_tags_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AbstractTask_tags_feature", "_UI_AbstractTask_type"),
				 TaskPackage.Literals.ABSTRACT_TASK__TAGS,
				 true,
				 false,
				 false,
				 null,
				 null,
				 null));
	}

    /**
     * This adds a property descriptor for the Dependencies feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addDependenciesPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AbstractTask_dependencies_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AbstractTask_dependencies_feature", "_UI_AbstractTask_type"),
				 TaskPackage.Literals.ABSTRACT_TASK__DEPENDENCIES,
				 true,
				 false,
				 false,
				 null,
				 null,
				 null));
	}

    /**
     * This adds a property descriptor for the Assigned Persons feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addAssignedPersonsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AbstractTask_assignedPersons_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AbstractTask_assignedPersons_feature", "_UI_AbstractTask_type"),
				 TaskPackage.Literals.ABSTRACT_TASK__ASSIGNED_PERSONS,
				 true,
				 false,
				 false,
				 null,
				 null,
				 null));
	}

    /**
     * This adds a property descriptor for the Assigned Teams feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addAssignedTeamsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AbstractTask_assignedTeams_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AbstractTask_assignedTeams_feature", "_UI_AbstractTask_type"),
				 TaskPackage.Literals.ABSTRACT_TASK__ASSIGNED_TEAMS,
				 true,
				 false,
				 false,
				 null,
				 null,
				 null));
	}

    /**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null)
		{
			super.getChildrenFeatures(object);
			childrenFeatures.add(TaskPackage.Literals.ABSTRACT_TASK__SUB_TASKS);
		}
		return childrenFeatures;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected boolean shouldComposeCreationImage() {
		return true;
	}

    /**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getText(Object object) {
		String label = ((AbstractTask)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_AbstractTask_type") :
			getString("_UI_AbstractTask_type") + " " + label;
	}

    /**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(AbstractTask.class))
		{
			case TaskPackage.ABSTRACT_TASK__NAME:
			case TaskPackage.ABSTRACT_TASK__DESCRIPTION:
			case TaskPackage.ABSTRACT_TASK__START_TIME:
			case TaskPackage.ABSTRACT_TASK__END_TIME:
			case TaskPackage.ABSTRACT_TASK__PROGRESS:
			case TaskPackage.ABSTRACT_TASK__COMPUTE_START_END_DYNAMICALLY:
			case TaskPackage.ABSTRACT_TASK__TAGS:
			case TaskPackage.ABSTRACT_TASK__DEPENDENCIES:
			case TaskPackage.ABSTRACT_TASK__ASSIGNED_PERSONS:
			case TaskPackage.ABSTRACT_TASK__ASSIGNED_TEAMS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case TaskPackage.ABSTRACT_TASK__SUB_TASKS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

    /**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(TaskPackage.Literals.ABSTRACT_TASK__SUB_TASKS,
				 TaskFactory.eINSTANCE.createTask()));
	}

    /**
     * Return the resource locator for this item provider's resources. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
		return ((IChildCreationExtender)adapterFactory).getResourceLocator();
	}

}
