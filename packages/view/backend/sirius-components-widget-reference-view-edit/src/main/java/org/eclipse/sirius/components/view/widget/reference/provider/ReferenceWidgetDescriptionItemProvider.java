/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.view.widget.reference.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.sirius.components.view.ViewFactory;

import org.eclipse.sirius.components.view.form.provider.WidgetDescriptionItemProvider;

import org.eclipse.sirius.components.view.widget.reference.ReferenceFactory;
import org.eclipse.sirius.components.view.widget.reference.ReferencePackage;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription;

import org.eclipse.sirius.components.widgets.reference.provider.ReferenceEditPlugin;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ReferenceWidgetDescriptionItemProvider extends WidgetDescriptionItemProvider
{
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReferenceWidgetDescriptionItemProvider(AdapterFactory adapterFactory)
	{
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object)
	{
		if (itemPropertyDescriptors == null)
		{
			super.getPropertyDescriptors(object);

			addIsEnabledExpressionPropertyDescriptor(object);
			addReferenceOwnerExpressionPropertyDescriptor(object);
			addReferenceNameExpressionPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Is Enabled Expression feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addIsEnabledExpressionPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ReferenceWidgetDescription_isEnabledExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ReferenceWidgetDescription_isEnabledExpression_feature", "_UI_ReferenceWidgetDescription_type"),
				 ReferencePackage.Literals.REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Reference Owner Expression feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addReferenceOwnerExpressionPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ReferenceWidgetDescription_referenceOwnerExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ReferenceWidgetDescription_referenceOwnerExpression_feature", "_UI_ReferenceWidgetDescription_type"),
				 ReferencePackage.Literals.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Reference Name Expression feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addReferenceNameExpressionPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ReferenceWidgetDescription_referenceNameExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ReferenceWidgetDescription_referenceNameExpression_feature", "_UI_ReferenceWidgetDescription_type"),
				 ReferencePackage.Literals.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION,
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object)
	{
		if (childrenFeatures == null)
		{
			super.getChildrenFeatures(object);
			childrenFeatures.add(ReferencePackage.Literals.REFERENCE_WIDGET_DESCRIPTION__BODY);
			childrenFeatures.add(ReferencePackage.Literals.REFERENCE_WIDGET_DESCRIPTION__STYLE);
			childrenFeatures.add(ReferencePackage.Literals.REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child)
	{
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns ReferenceWidgetDescription.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object)
	{
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ReferenceWidgetDescription"));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean shouldComposeCreationImage()
	{
		return true;
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object)
	{
		String label = ((ReferenceWidgetDescription)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_ReferenceWidgetDescription_type") :
			getString("_UI_ReferenceWidgetDescription_type") + " " + label;
	}


	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification)
	{
		updateChildren(notification);

		switch (notification.getFeatureID(ReferenceWidgetDescription.class))
		{
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION:
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__BODY:
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__STYLE:
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
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
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object)
	{
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(ReferencePackage.Literals.REFERENCE_WIDGET_DESCRIPTION__BODY,
				 ViewFactory.eINSTANCE.createChangeContext()));

		newChildDescriptors.add
			(createChildParameter
				(ReferencePackage.Literals.REFERENCE_WIDGET_DESCRIPTION__BODY,
				 ViewFactory.eINSTANCE.createCreateInstance()));

		newChildDescriptors.add
			(createChildParameter
				(ReferencePackage.Literals.REFERENCE_WIDGET_DESCRIPTION__BODY,
				 ViewFactory.eINSTANCE.createSetValue()));

		newChildDescriptors.add
			(createChildParameter
				(ReferencePackage.Literals.REFERENCE_WIDGET_DESCRIPTION__BODY,
				 ViewFactory.eINSTANCE.createUnsetValue()));

		newChildDescriptors.add
			(createChildParameter
				(ReferencePackage.Literals.REFERENCE_WIDGET_DESCRIPTION__BODY,
				 ViewFactory.eINSTANCE.createDeleteElement()));

		newChildDescriptors.add
			(createChildParameter
				(ReferencePackage.Literals.REFERENCE_WIDGET_DESCRIPTION__BODY,
				 ViewFactory.eINSTANCE.createLet()));

		newChildDescriptors.add
			(createChildParameter
				(ReferencePackage.Literals.REFERENCE_WIDGET_DESCRIPTION__BODY,
				 ViewFactory.eINSTANCE.createIf()));

		newChildDescriptors.add
			(createChildParameter
				(ReferencePackage.Literals.REFERENCE_WIDGET_DESCRIPTION__BODY,
				 ViewFactory.eINSTANCE.createFor()));

		newChildDescriptors.add
			(createChildParameter
				(ReferencePackage.Literals.REFERENCE_WIDGET_DESCRIPTION__STYLE,
				 ReferenceFactory.eINSTANCE.createReferenceWidgetDescriptionStyle()));

		newChildDescriptors.add
			(createChildParameter
				(ReferencePackage.Literals.REFERENCE_WIDGET_DESCRIPTION__STYLE,
				 ReferenceFactory.eINSTANCE.createConditionalReferenceWidgetDescriptionStyle()));

		newChildDescriptors.add
			(createChildParameter
				(ReferencePackage.Literals.REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES,
				 ReferenceFactory.eINSTANCE.createConditionalReferenceWidgetDescriptionStyle()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection)
	{
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify =
			childFeature == ReferencePackage.Literals.REFERENCE_WIDGET_DESCRIPTION__STYLE ||
			childFeature == ReferencePackage.Literals.REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES;

		if (qualify)
		{
			return getString
				("_UI_CreateChild_text2",
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator()
	{
		return ReferenceEditPlugin.INSTANCE;
	}

}
