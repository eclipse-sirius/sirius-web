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
package org.eclipse.sirius.components.view.tree.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.components.view.provider.RepresentationDescriptionItemProvider;
import org.eclipse.sirius.components.view.tree.TreeDescription;
import org.eclipse.sirius.components.view.tree.TreeFactory;
import org.eclipse.sirius.components.view.tree.TreePackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.view.tree.TreeDescription} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class TreeDescriptionItemProvider extends RepresentationDescriptionItemProvider {

    /**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public TreeDescriptionItemProvider(AdapterFactory adapterFactory) {
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

			addKindExpressionPropertyDescriptor(object);
			addTreeItemIconExpressionPropertyDescriptor(object);
			addTreeItemIdExpressionPropertyDescriptor(object);
			addTreeItemObjectExpressionPropertyDescriptor(object);
			addElementsExpressionPropertyDescriptor(object);
			addHasChildrenExpressionPropertyDescriptor(object);
			addChildrenExpressionPropertyDescriptor(object);
			addParentExpressionPropertyDescriptor(object);
			addEditableExpressionPropertyDescriptor(object);
			addSelectableExpressionPropertyDescriptor(object);
			addDeletableExpressionPropertyDescriptor(object);
			addTreeItemLabelDescriptionsPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

    /**
	 * This adds a property descriptor for the Kind Expression feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected void addKindExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TreeDescription_kindExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TreeDescription_kindExpression_feature", "_UI_TreeDescription_type"),
				 TreePackage.Literals.TREE_DESCRIPTION__KIND_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the Tree Item Icon Expression feature.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 */
    protected void addTreeItemIconExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TreeDescription_treeItemIconExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TreeDescription_treeItemIconExpression_feature", "_UI_TreeDescription_type"),
				 TreePackage.Literals.TREE_DESCRIPTION__TREE_ITEM_ICON_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the Tree Item Id Expression feature.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 */
    protected void addTreeItemIdExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TreeDescription_treeItemIdExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TreeDescription_treeItemIdExpression_feature", "_UI_TreeDescription_type"),
				 TreePackage.Literals.TREE_DESCRIPTION__TREE_ITEM_ID_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the Tree Item Object Expression feature.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 */
    protected void addTreeItemObjectExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TreeDescription_treeItemObjectExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TreeDescription_treeItemObjectExpression_feature", "_UI_TreeDescription_type"),
				 TreePackage.Literals.TREE_DESCRIPTION__TREE_ITEM_OBJECT_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
     * This adds a property descriptor for the Elements Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addElementsExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TreeDescription_elementsExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TreeDescription_elementsExpression_feature", "_UI_TreeDescription_type"),
				 TreePackage.Literals.TREE_DESCRIPTION__ELEMENTS_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the Has Children Expression feature.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 */
    protected void addHasChildrenExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TreeDescription_hasChildrenExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TreeDescription_hasChildrenExpression_feature", "_UI_TreeDescription_type"),
				 TreePackage.Literals.TREE_DESCRIPTION__HAS_CHILDREN_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
     * This adds a property descriptor for the Children Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addChildrenExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TreeDescription_childrenExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TreeDescription_childrenExpression_feature", "_UI_TreeDescription_type"),
				 TreePackage.Literals.TREE_DESCRIPTION__CHILDREN_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the Parent Expression feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected void addParentExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TreeDescription_parentExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TreeDescription_parentExpression_feature", "_UI_TreeDescription_type"),
				 TreePackage.Literals.TREE_DESCRIPTION__PARENT_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
     * This adds a property descriptor for the Editable Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addEditableExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TreeDescription_editableExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TreeDescription_editableExpression_feature", "_UI_TreeDescription_type"),
				 TreePackage.Literals.TREE_DESCRIPTION__EDITABLE_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
     * This adds a property descriptor for the Selectable Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addSelectableExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TreeDescription_selectableExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TreeDescription_selectableExpression_feature", "_UI_TreeDescription_type"),
				 TreePackage.Literals.TREE_DESCRIPTION__SELECTABLE_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
     * This adds a property descriptor for the Deletable Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addDeletableExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TreeDescription_deletableExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TreeDescription_deletableExpression_feature", "_UI_TreeDescription_type"),
				 TreePackage.Literals.TREE_DESCRIPTION__DELETABLE_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the Tree Item Label Descriptions feature.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 */
    protected void addTreeItemLabelDescriptionsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TreeDescription_treeItemLabelDescriptions_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TreeDescription_treeItemLabelDescriptions_feature", "_UI_TreeDescription_type"),
				 TreePackage.Literals.TREE_DESCRIPTION__TREE_ITEM_LABEL_DESCRIPTIONS,
				 true,
				 false,
				 true,
				 null,
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
			childrenFeatures.add(TreePackage.Literals.TREE_DESCRIPTION__TREE_ITEM_LABEL_DESCRIPTIONS);
			childrenFeatures.add(TreePackage.Literals.TREE_DESCRIPTION__CONTEXT_MENU_ENTRIES);
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
     * This returns TreeDescription.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/TreeDescription.svg"));
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
		String label = ((TreeDescription)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_TreeDescription_type") :
			getString("_UI_TreeDescription_type") + " " + label;
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

		switch (notification.getFeatureID(TreeDescription.class))
		{
			case TreePackage.TREE_DESCRIPTION__KIND_EXPRESSION:
			case TreePackage.TREE_DESCRIPTION__TREE_ITEM_ICON_EXPRESSION:
			case TreePackage.TREE_DESCRIPTION__TREE_ITEM_ID_EXPRESSION:
			case TreePackage.TREE_DESCRIPTION__TREE_ITEM_OBJECT_EXPRESSION:
			case TreePackage.TREE_DESCRIPTION__ELEMENTS_EXPRESSION:
			case TreePackage.TREE_DESCRIPTION__HAS_CHILDREN_EXPRESSION:
			case TreePackage.TREE_DESCRIPTION__CHILDREN_EXPRESSION:
			case TreePackage.TREE_DESCRIPTION__PARENT_EXPRESSION:
			case TreePackage.TREE_DESCRIPTION__EDITABLE_EXPRESSION:
			case TreePackage.TREE_DESCRIPTION__SELECTABLE_EXPRESSION:
			case TreePackage.TREE_DESCRIPTION__DELETABLE_EXPRESSION:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case TreePackage.TREE_DESCRIPTION__TREE_ITEM_LABEL_DESCRIPTIONS:
			case TreePackage.TREE_DESCRIPTION__CONTEXT_MENU_ENTRIES:
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
				(TreePackage.Literals.TREE_DESCRIPTION__TREE_ITEM_LABEL_DESCRIPTIONS,
				 TreeFactory.eINSTANCE.createTreeItemLabelDescription()));

		newChildDescriptors.add
			(createChildParameter
				(TreePackage.Literals.TREE_DESCRIPTION__CONTEXT_MENU_ENTRIES,
				 TreeFactory.eINSTANCE.createSingleClickTreeItemContextMenuEntry()));

		newChildDescriptors.add
			(createChildParameter
				(TreePackage.Literals.TREE_DESCRIPTION__CONTEXT_MENU_ENTRIES,
				 TreeFactory.eINSTANCE.createFetchTreeItemContextMenuEntry()));

		newChildDescriptors.add
			(createChildParameter
				(TreePackage.Literals.TREE_DESCRIPTION__CONTEXT_MENU_ENTRIES,
				 TreeFactory.eINSTANCE.createCustomTreeItemContextMenuEntry()));
	}

}
