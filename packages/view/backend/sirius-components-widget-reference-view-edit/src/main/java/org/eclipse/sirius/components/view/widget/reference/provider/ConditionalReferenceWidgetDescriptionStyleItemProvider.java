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

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.sirius.components.view.ViewPackage;

import org.eclipse.sirius.components.view.provider.ConditionalItemProvider;

import org.eclipse.sirius.components.view.widget.reference.ConditionalReferenceWidgetDescriptionStyle;
import org.eclipse.sirius.components.view.widget.reference.ReferencePackage;

import org.eclipse.sirius.components.widgets.reference.provider.ReferenceEditPlugin;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.view.widget.reference.ConditionalReferenceWidgetDescriptionStyle} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ConditionalReferenceWidgetDescriptionStyleItemProvider extends ConditionalItemProvider
{
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConditionalReferenceWidgetDescriptionStyleItemProvider(AdapterFactory adapterFactory)
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

			addFontSizePropertyDescriptor(object);
			addItalicPropertyDescriptor(object);
			addBoldPropertyDescriptor(object);
			addUnderlinePropertyDescriptor(object);
			addStrikeThroughPropertyDescriptor(object);
			addColorPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Font Size feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addFontSizePropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_LabelStyle_fontSize_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_LabelStyle_fontSize_feature", "_UI_LabelStyle_type"),
				 ViewPackage.Literals.LABEL_STYLE__FONT_SIZE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Italic feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addItalicPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_LabelStyle_italic_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_LabelStyle_italic_feature", "_UI_LabelStyle_type"),
				 ViewPackage.Literals.LABEL_STYLE__ITALIC,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Bold feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addBoldPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_LabelStyle_bold_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_LabelStyle_bold_feature", "_UI_LabelStyle_type"),
				 ViewPackage.Literals.LABEL_STYLE__BOLD,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Underline feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addUnderlinePropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_LabelStyle_underline_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_LabelStyle_underline_feature", "_UI_LabelStyle_type"),
				 ViewPackage.Literals.LABEL_STYLE__UNDERLINE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Strike Through feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addStrikeThroughPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_LabelStyle_strikeThrough_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_LabelStyle_strikeThrough_feature", "_UI_LabelStyle_type"),
				 ViewPackage.Literals.LABEL_STYLE__STRIKE_THROUGH,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Color feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addColorPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ReferenceWidgetDescriptionStyle_color_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ReferenceWidgetDescriptionStyle_color_feature", "_UI_ReferenceWidgetDescriptionStyle_type"),
				 ReferencePackage.Literals.REFERENCE_WIDGET_DESCRIPTION_STYLE__COLOR,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This returns ConditionalReferenceWidgetDescriptionStyle.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object)
	{
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ConditionalReferenceWidgetDescriptionStyle"));
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
		String label = ((ConditionalReferenceWidgetDescriptionStyle)object).getCondition();
		return label == null || label.length() == 0 ?
			getString("_UI_ConditionalReferenceWidgetDescriptionStyle_type") :
			getString("_UI_ConditionalReferenceWidgetDescriptionStyle_type") + " " + label;
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

		switch (notification.getFeatureID(ConditionalReferenceWidgetDescriptionStyle.class))
		{
			case ReferencePackage.CONDITIONAL_REFERENCE_WIDGET_DESCRIPTION_STYLE__FONT_SIZE:
			case ReferencePackage.CONDITIONAL_REFERENCE_WIDGET_DESCRIPTION_STYLE__ITALIC:
			case ReferencePackage.CONDITIONAL_REFERENCE_WIDGET_DESCRIPTION_STYLE__BOLD:
			case ReferencePackage.CONDITIONAL_REFERENCE_WIDGET_DESCRIPTION_STYLE__UNDERLINE:
			case ReferencePackage.CONDITIONAL_REFERENCE_WIDGET_DESCRIPTION_STYLE__STRIKE_THROUGH:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
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
