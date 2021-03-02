/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.view.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.sirius.web.view.EdgeDescription;
import org.eclipse.sirius.web.view.ViewPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.web.view.EdgeDescription} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class EdgeDescriptionItemProvider extends DiagramElementDescriptionItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EdgeDescriptionItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addIsDomainBasedEdgePropertyDescriptor(object);
			addSourceNodeDescriptionsPropertyDescriptor(object);
			addTargetNodeDescriptionsPropertyDescriptor(object);
			addSourceNodesExpressionPropertyDescriptor(object);
			addTargetNodesExpressionPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Is Domain Based Edge feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addIsDomainBasedEdgePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_EdgeDescription_isDomainBasedEdge_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_EdgeDescription_isDomainBasedEdge_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_EdgeDescription_type"), //$NON-NLS-1$
				ViewPackage.Literals.EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE, true, false, false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Source Node Descriptions feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSourceNodeDescriptionsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_EdgeDescription_sourceNodeDescriptions_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_EdgeDescription_sourceNodeDescriptions_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_EdgeDescription_type"), //$NON-NLS-1$
				ViewPackage.Literals.EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTIONS, true, false, true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Target Node Descriptions feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addTargetNodeDescriptionsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_EdgeDescription_targetNodeDescriptions_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_EdgeDescription_targetNodeDescriptions_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_EdgeDescription_type"), //$NON-NLS-1$
				ViewPackage.Literals.EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTIONS, true, false, true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Source Nodes Expression feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSourceNodesExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_EdgeDescription_sourceNodesExpression_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_EdgeDescription_sourceNodesExpression_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_EdgeDescription_type"), //$NON-NLS-1$
				ViewPackage.Literals.EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION, true, false, false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Target Nodes Expression feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addTargetNodesExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_EdgeDescription_targetNodesExpression_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_EdgeDescription_targetNodesExpression_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_EdgeDescription_type"), //$NON-NLS-1$
				ViewPackage.Literals.EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION, true, false, false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This returns EdgeDescription.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/EdgeDescription")); //$NON-NLS-1$
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
		String label = ((EdgeDescription) object).getDomainType();
		return label == null || label.length() == 0 ? getString("_UI_EdgeDescription_type") //$NON-NLS-1$
				: getString("_UI_EdgeDescription_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(EdgeDescription.class)) {
		case ViewPackage.EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE:
		case ViewPackage.EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION:
		case ViewPackage.EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION:
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
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

}
