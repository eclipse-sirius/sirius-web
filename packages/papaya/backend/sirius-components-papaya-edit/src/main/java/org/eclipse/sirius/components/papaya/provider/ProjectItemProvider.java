/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import org.eclipse.emf.edit.provider.StyledString;
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
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public ProjectItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

    /**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null)
		{
			super.getPropertyDescriptors(object);

			addHomepagePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

    /**
	 * This adds a property descriptor for the Homepage feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected void addHomepagePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Project_homepage_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Project_homepage_feature", "_UI_Project_type"),
				 PapayaPackage.Literals.PROJECT__HOMEPAGE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 */
    @Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null)
		{
			super.getChildrenFeatures(object);
			childrenFeatures.add(PapayaPackage.Literals.CONTAINER__FOLDERS);
			childrenFeatures.add(PapayaPackage.Literals.CONTAINER__ELEMENTS);
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
	 * This returns Project.gif.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Project"));
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getText(Object object) {
		return ((StyledString)getStyledText(object)).getString();
	}

    /**
	 * This returns the label styled text for the adapted class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object getStyledText(Object object) {
		String label = ((Project)object).getName();
    	StyledString styledLabel = new StyledString();
		if (label == null || label.length() == 0)
		{
			styledLabel.append(getString("_UI_Project_type"), StyledString.Style.QUALIFIER_STYLER); 
		} else {
			styledLabel.append(getString("_UI_Project_type"), StyledString.Style.QUALIFIER_STYLER).append(" " + label);
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
		updateChildren(notification);

		switch (notification.getFeatureID(Project.class))
		{
			case PapayaPackage.PROJECT__HOMEPAGE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case PapayaPackage.PROJECT__FOLDERS:
			case PapayaPackage.PROJECT__ELEMENTS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

    /**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(PapayaPackage.Literals.CONTAINER__FOLDERS,
				 PapayaFactory.eINSTANCE.createFolder()));

		newChildDescriptors.add
			(createChildParameter
				(PapayaPackage.Literals.CONTAINER__ELEMENTS,
				 PapayaFactory.eINSTANCE.createOperationalCapability()));

		newChildDescriptors.add
			(createChildParameter
				(PapayaPackage.Literals.CONTAINER__ELEMENTS,
				 PapayaFactory.eINSTANCE.createOperationalEntity()));

		newChildDescriptors.add
			(createChildParameter
				(PapayaPackage.Literals.CONTAINER__ELEMENTS,
				 PapayaFactory.eINSTANCE.createOperationalActor()));

		newChildDescriptors.add
			(createChildParameter
				(PapayaPackage.Literals.CONTAINER__ELEMENTS,
				 PapayaFactory.eINSTANCE.createOperationalProcess()));

		newChildDescriptors.add
			(createChildParameter
				(PapayaPackage.Literals.CONTAINER__ELEMENTS,
				 PapayaFactory.eINSTANCE.createOperationalActivity()));

		newChildDescriptors.add
			(createChildParameter
				(PapayaPackage.Literals.CONTAINER__ELEMENTS,
				 PapayaFactory.eINSTANCE.createIteration()));

		newChildDescriptors.add
			(createChildParameter
				(PapayaPackage.Literals.CONTAINER__ELEMENTS,
				 PapayaFactory.eINSTANCE.createTask()));

		newChildDescriptors.add
			(createChildParameter
				(PapayaPackage.Literals.CONTAINER__ELEMENTS,
				 PapayaFactory.eINSTANCE.createContribution()));

		newChildDescriptors.add
			(createChildParameter
				(PapayaPackage.Literals.CONTAINER__ELEMENTS,
				 PapayaFactory.eINSTANCE.createComponent()));

		newChildDescriptors.add
			(createChildParameter
				(PapayaPackage.Literals.CONTAINER__ELEMENTS,
				 PapayaFactory.eINSTANCE.createPackage()));

		newChildDescriptors.add
			(createChildParameter
				(PapayaPackage.Literals.CONTAINER__ELEMENTS,
				 PapayaFactory.eINSTANCE.createApplicationConcern()));

		newChildDescriptors.add
			(createChildParameter
				(PapayaPackage.Literals.CONTAINER__ELEMENTS,
				 PapayaFactory.eINSTANCE.createDomain()));

		newChildDescriptors.add
			(createChildParameter
				(PapayaPackage.Literals.CONTAINER__ELEMENTS,
				 PapayaFactory.eINSTANCE.createChannel()));
	}

}
