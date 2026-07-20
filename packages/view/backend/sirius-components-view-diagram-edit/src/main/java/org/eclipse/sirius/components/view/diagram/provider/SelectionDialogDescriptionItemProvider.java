/*******************************************************************************
 * Copyright (c) 2021, 2026 Obeo.
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
package org.eclipse.sirius.components.view.diagram.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.SelectionDialogDescription;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 * @generated
 */
public class SelectionDialogDescriptionItemProvider extends DialogDescriptionItemProvider {
    /**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public SelectionDialogDescriptionItemProvider(AdapterFactory adapterFactory) {
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

			addMultiplePropertyDescriptor(object);
			addOptionalPropertyDescriptor(object);
			addDefaultTitleExpressionPropertyDescriptor(object);
			addNoSelectionTitleExpressionPropertyDescriptor(object);
			addWithSelectionTitleExpressionPropertyDescriptor(object);
			addDescriptionExpressionPropertyDescriptor(object);
			addNoSelectionActionLabelExpressionPropertyDescriptor(object);
			addNoSelectionActionDescriptionExpressionPropertyDescriptor(object);
			addWithSelectionActionLabelExpressionPropertyDescriptor(object);
			addWithSelectionActionDescriptionExpressionPropertyDescriptor(object);
			addNoSelectionActionStatusMessageExpressionPropertyDescriptor(object);
			addSelectionRequiredWithoutSelectionStatusMessageExpressionPropertyDescriptor(object);
			addSelectionRequiredWithSelectionStatusMessageExpressionPropertyDescriptor(object);
			addNoSelectionConfirmButtonLabelExpressionPropertyDescriptor(object);
			addSelectionRequiredWithoutSelectionConfirmButtonLabelExpressionPropertyDescriptor(object);
			addSelectionRequiredWithSelectionConfirmButtonLabelExpressionPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

    /**
	 * This adds a property descriptor for the Multiple feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected void addMultiplePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_SelectionDialogDescription_multiple_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_SelectionDialogDescription_multiple_feature", "_UI_SelectionDialogDescription_type"),
				 DiagramPackage.Literals.SELECTION_DIALOG_DESCRIPTION__MULTIPLE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the Optional feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected void addOptionalPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_SelectionDialogDescription_optional_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_SelectionDialogDescription_optional_feature", "_UI_SelectionDialogDescription_type"),
				 DiagramPackage.Literals.SELECTION_DIALOG_DESCRIPTION__OPTIONAL,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the Default Title Expression feature.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 */
    protected void addDefaultTitleExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_SelectionDialogDescription_defaultTitleExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_SelectionDialogDescription_defaultTitleExpression_feature", "_UI_SelectionDialogDescription_type"),
				 DiagramPackage.Literals.SELECTION_DIALOG_DESCRIPTION__DEFAULT_TITLE_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the No Selection Title Expression feature.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 */
    protected void addNoSelectionTitleExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_SelectionDialogDescription_noSelectionTitleExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_SelectionDialogDescription_noSelectionTitleExpression_feature", "_UI_SelectionDialogDescription_type"),
				 DiagramPackage.Literals.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_TITLE_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the With Selection Title Expression feature.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 */
    protected void addWithSelectionTitleExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_SelectionDialogDescription_withSelectionTitleExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_SelectionDialogDescription_withSelectionTitleExpression_feature", "_UI_SelectionDialogDescription_type"),
				 DiagramPackage.Literals.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_TITLE_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
     * This adds a property descriptor for the Description Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addDescriptionExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_SelectionDialogDescription_descriptionExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_SelectionDialogDescription_descriptionExpression_feature", "_UI_SelectionDialogDescription_type"),
				 DiagramPackage.Literals.SELECTION_DIALOG_DESCRIPTION__DESCRIPTION_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the No Selection Action Label Expression feature.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected void addNoSelectionActionLabelExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_SelectionDialogDescription_noSelectionActionLabelExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_SelectionDialogDescription_noSelectionActionLabelExpression_feature", "_UI_SelectionDialogDescription_type"),
				 DiagramPackage.Literals.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_LABEL_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the No Selection Action Description Expression feature.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @generated
	 */
    protected void addNoSelectionActionDescriptionExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_SelectionDialogDescription_noSelectionActionDescriptionExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_SelectionDialogDescription_noSelectionActionDescriptionExpression_feature", "_UI_SelectionDialogDescription_type"),
				 DiagramPackage.Literals.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_DESCRIPTION_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the With Selection Action Label Expression feature.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected void addWithSelectionActionLabelExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_SelectionDialogDescription_withSelectionActionLabelExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_SelectionDialogDescription_withSelectionActionLabelExpression_feature", "_UI_SelectionDialogDescription_type"),
				 DiagramPackage.Literals.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_ACTION_LABEL_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the With Selection Action Description Expression feature.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @generated
	 */
    protected void addWithSelectionActionDescriptionExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_SelectionDialogDescription_withSelectionActionDescriptionExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_SelectionDialogDescription_withSelectionActionDescriptionExpression_feature", "_UI_SelectionDialogDescription_type"),
				 DiagramPackage.Literals.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_ACTION_DESCRIPTION_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
     * This adds a property descriptor for the No Selection Action Status Message Expression feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addNoSelectionActionStatusMessageExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_SelectionDialogDescription_noSelectionActionStatusMessageExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_SelectionDialogDescription_noSelectionActionStatusMessageExpression_feature", "_UI_SelectionDialogDescription_type"),
				 DiagramPackage.Literals.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_STATUS_MESSAGE_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the Selection Required Without Selection Status Message Expression feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected void addSelectionRequiredWithoutSelectionStatusMessageExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_SelectionDialogDescription_selectionRequiredWithoutSelectionStatusMessageExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_SelectionDialogDescription_selectionRequiredWithoutSelectionStatusMessageExpression_feature", "_UI_SelectionDialogDescription_type"),
				 DiagramPackage.Literals.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITHOUT_SELECTION_STATUS_MESSAGE_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
     * This adds a property descriptor for the Selection Required With Selection Status Message Expression feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addSelectionRequiredWithSelectionStatusMessageExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_SelectionDialogDescription_selectionRequiredWithSelectionStatusMessageExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_SelectionDialogDescription_selectionRequiredWithSelectionStatusMessageExpression_feature", "_UI_SelectionDialogDescription_type"),
				 DiagramPackage.Literals.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITH_SELECTION_STATUS_MESSAGE_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the No Selection Confirm Button Label Expression feature.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @generated
	 */
    protected void addNoSelectionConfirmButtonLabelExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_SelectionDialogDescription_noSelectionConfirmButtonLabelExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_SelectionDialogDescription_noSelectionConfirmButtonLabelExpression_feature", "_UI_SelectionDialogDescription_type"),
				 DiagramPackage.Literals.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the Selection Required Without Selection Confirm Button Label Expression feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected void addSelectionRequiredWithoutSelectionConfirmButtonLabelExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_SelectionDialogDescription_selectionRequiredWithoutSelectionConfirmButtonLabelExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_SelectionDialogDescription_selectionRequiredWithoutSelectionConfirmButtonLabelExpression_feature", "_UI_SelectionDialogDescription_type"),
				 DiagramPackage.Literals.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITHOUT_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the Selection Required With Selection Confirm Button Label Expression feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected void addSelectionRequiredWithSelectionConfirmButtonLabelExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_SelectionDialogDescription_selectionRequiredWithSelectionConfirmButtonLabelExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_SelectionDialogDescription_selectionRequiredWithSelectionConfirmButtonLabelExpression_feature", "_UI_SelectionDialogDescription_type"),
				 DiagramPackage.Literals.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITH_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION,
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
			childrenFeatures.add(DiagramPackage.Literals.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION);
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
	 * This returns SelectionDialogDescription.gif.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/SelectionDialogDescription"));
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
		SelectionDialogDescription selectionDialogDescription = (SelectionDialogDescription)object;
		return getString("_UI_SelectionDialogDescription_type") + " " + selectionDialogDescription.isMultiple();
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

		switch (notification.getFeatureID(SelectionDialogDescription.class))
		{
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__MULTIPLE:
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__OPTIONAL:
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__DEFAULT_TITLE_EXPRESSION:
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_TITLE_EXPRESSION:
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_TITLE_EXPRESSION:
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__DESCRIPTION_EXPRESSION:
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_LABEL_EXPRESSION:
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_DESCRIPTION_EXPRESSION:
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_ACTION_LABEL_EXPRESSION:
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_ACTION_DESCRIPTION_EXPRESSION:
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_STATUS_MESSAGE_EXPRESSION:
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITHOUT_SELECTION_STATUS_MESSAGE_EXPRESSION:
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITH_SELECTION_STATUS_MESSAGE_EXPRESSION:
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION:
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITHOUT_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION:
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITH_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION:
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
				(DiagramPackage.Literals.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION,
				 DiagramFactory.eINSTANCE.createSelectionDialogTreeDescription()));
	}

}
