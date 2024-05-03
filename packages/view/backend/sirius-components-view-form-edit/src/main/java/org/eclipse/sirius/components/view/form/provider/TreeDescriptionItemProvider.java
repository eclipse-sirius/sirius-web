/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.components.view.form.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.TreeDescription;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.view.form.TreeDescription} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class TreeDescriptionItemProvider extends WidgetDescriptionItemProvider {
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public TreeDescriptionItemProvider(AdapterFactory adapterFactory) {
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

            this.addChildrenExpressionPropertyDescriptor(object);
            this.addTreeItemLabelExpressionPropertyDescriptor(object);
            this.addIsTreeItemSelectableExpressionPropertyDescriptor(object);
            this.addTreeItemBeginIconExpressionPropertyDescriptor(object);
            this.addTreeItemEndIconsExpressionPropertyDescriptor(object);
            this.addIsCheckableExpressionPropertyDescriptor(object);
            this.addCheckedValueExpressionPropertyDescriptor(object);
            this.addIsEnabledExpressionPropertyDescriptor(object);
        }
        return this.itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Children Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addChildrenExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_TreeDescription_childrenExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_TreeDescription_childrenExpression_feature", "_UI_TreeDescription_type"),
                FormPackage.Literals.TREE_DESCRIPTION__CHILDREN_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Tree Item Label Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addTreeItemLabelExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_TreeDescription_treeItemLabelExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_TreeDescription_treeItemLabelExpression_feature", "_UI_TreeDescription_type"),
                FormPackage.Literals.TREE_DESCRIPTION__TREE_ITEM_LABEL_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Is Tree Item Selectable Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addIsTreeItemSelectableExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_TreeDescription_isTreeItemSelectableExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_TreeDescription_isTreeItemSelectableExpression_feature", "_UI_TreeDescription_type"),
                FormPackage.Literals.TREE_DESCRIPTION__IS_TREE_ITEM_SELECTABLE_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Tree Item Begin Icon Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addTreeItemBeginIconExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_TreeDescription_treeItemBeginIconExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_TreeDescription_treeItemBeginIconExpression_feature", "_UI_TreeDescription_type"),
                FormPackage.Literals.TREE_DESCRIPTION__TREE_ITEM_BEGIN_ICON_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Tree Item End Icons Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addTreeItemEndIconsExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_TreeDescription_treeItemEndIconsExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_TreeDescription_treeItemEndIconsExpression_feature", "_UI_TreeDescription_type"),
                FormPackage.Literals.TREE_DESCRIPTION__TREE_ITEM_END_ICONS_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Is Checkable Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addIsCheckableExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_TreeDescription_isCheckableExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_TreeDescription_isCheckableExpression_feature", "_UI_TreeDescription_type"),
                FormPackage.Literals.TREE_DESCRIPTION__IS_CHECKABLE_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Checked Value Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addCheckedValueExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_TreeDescription_checkedValueExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_TreeDescription_checkedValueExpression_feature", "_UI_TreeDescription_type"),
                FormPackage.Literals.TREE_DESCRIPTION__CHECKED_VALUE_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Is Enabled Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addIsEnabledExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_TreeDescription_IsEnabledExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_TreeDescription_IsEnabledExpression_feature", "_UI_TreeDescription_type"),
                FormPackage.Literals.TREE_DESCRIPTION__IS_ENABLED_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
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
            this.childrenFeatures.add(FormPackage.Literals.TREE_DESCRIPTION__BODY);
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
        String label = ((TreeDescription) object).getName();
        return label == null || label.length() == 0 ? this.getString("_UI_TreeDescription_type") : this.getString("_UI_TreeDescription_type") + " " + label;
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

        switch (notification.getFeatureID(TreeDescription.class)) {
            case FormPackage.TREE_DESCRIPTION__CHILDREN_EXPRESSION:
            case FormPackage.TREE_DESCRIPTION__TREE_ITEM_LABEL_EXPRESSION:
            case FormPackage.TREE_DESCRIPTION__IS_TREE_ITEM_SELECTABLE_EXPRESSION:
            case FormPackage.TREE_DESCRIPTION__TREE_ITEM_BEGIN_ICON_EXPRESSION:
            case FormPackage.TREE_DESCRIPTION__TREE_ITEM_END_ICONS_EXPRESSION:
            case FormPackage.TREE_DESCRIPTION__IS_CHECKABLE_EXPRESSION:
            case FormPackage.TREE_DESCRIPTION__CHECKED_VALUE_EXPRESSION:
            case FormPackage.TREE_DESCRIPTION__IS_ENABLED_EXPRESSION:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
            case FormPackage.TREE_DESCRIPTION__BODY:
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

        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.TREE_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createChangeContext()));

        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.TREE_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createCreateInstance()));

        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.TREE_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createSetValue()));

        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.TREE_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createUnsetValue()));

        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.TREE_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createDeleteElement()));

        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.TREE_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createLet()));

        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.TREE_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createIf()));

        newChildDescriptors.add(this.createChildParameter(FormPackage.Literals.TREE_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createFor()));
    }

}
