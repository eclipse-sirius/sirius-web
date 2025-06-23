/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.TreePackage;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry} object. <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class SingleClickTreeItemContextMenuEntryItemProvider extends TreeItemContextMenuEntryItemProvider {

    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public SingleClickTreeItemContextMenuEntryItemProvider(AdapterFactory adapterFactory) {
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

            this.addLabelExpressionPropertyDescriptor(object);
            this.addIconURLExpressionPropertyDescriptor(object);
            this.addWithImpactAnalysisPropertyDescriptor(object);
        }
        return this.itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Label Expression feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addLabelExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_SingleClickTreeItemContextMenuEntry_labelExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_SingleClickTreeItemContextMenuEntry_labelExpression_feature", "_UI_SingleClickTreeItemContextMenuEntry_type"),
                TreePackage.Literals.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__LABEL_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Icon URL Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addIconURLExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_SingleClickTreeItemContextMenuEntry_iconURLExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_SingleClickTreeItemContextMenuEntry_iconURLExpression_feature", "_UI_SingleClickTreeItemContextMenuEntry_type"),
                TreePackage.Literals.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__ICON_URL_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the With Impact Analysis feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addWithImpactAnalysisPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_SingleClickTreeItemContextMenuEntry_withImpactAnalysis_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_SingleClickTreeItemContextMenuEntry_withImpactAnalysis_feature", "_UI_SingleClickTreeItemContextMenuEntry_type"),
                TreePackage.Literals.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__WITH_IMPACT_ANALYSIS, true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
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
            this.childrenFeatures.add(TreePackage.Literals.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__BODY);
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
     * This returns SingleClickTreeItemContextMenuEntry.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/SingleClickTreeItemContextMenuEntry.svg"));
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
        String label = ((SingleClickTreeItemContextMenuEntry) object).getName();
        return label == null || label.length() == 0 ? this.getString("_UI_SingleClickTreeItemContextMenuEntry_type") : this.getString("_UI_SingleClickTreeItemContextMenuEntry_type") + " " + label;
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

        switch (notification.getFeatureID(SingleClickTreeItemContextMenuEntry.class)) {
            case TreePackage.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__LABEL_EXPRESSION:
            case TreePackage.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__ICON_URL_EXPRESSION:
            case TreePackage.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__WITH_IMPACT_ANALYSIS:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
            case TreePackage.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__BODY:
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

        newChildDescriptors.add(this.createChildParameter(TreePackage.Literals.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__BODY, ViewFactory.eINSTANCE.createChangeContext()));

        newChildDescriptors.add(this.createChildParameter(TreePackage.Literals.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__BODY, ViewFactory.eINSTANCE.createCreateInstance()));

        newChildDescriptors.add(this.createChildParameter(TreePackage.Literals.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__BODY, ViewFactory.eINSTANCE.createSetValue()));

        newChildDescriptors.add(this.createChildParameter(TreePackage.Literals.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__BODY, ViewFactory.eINSTANCE.createUnsetValue()));

        newChildDescriptors.add(this.createChildParameter(TreePackage.Literals.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__BODY, ViewFactory.eINSTANCE.createDeleteElement()));

        newChildDescriptors.add(this.createChildParameter(TreePackage.Literals.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__BODY, ViewFactory.eINSTANCE.createLet()));

        newChildDescriptors.add(this.createChildParameter(TreePackage.Literals.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__BODY, ViewFactory.eINSTANCE.createIf()));

        newChildDescriptors.add(this.createChildParameter(TreePackage.Literals.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__BODY, ViewFactory.eINSTANCE.createFor()));
    }

}
