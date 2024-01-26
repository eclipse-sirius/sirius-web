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
package org.eclipse.sirius.components.view.diagram.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
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
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription} object. <!-- begin-user-doc --> <!--
 * end-user-doc -->
 *
 * @generated
 */
public class ListLayoutStrategyDescriptionItemProvider extends ItemProviderAdapter
        implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {

    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ListLayoutStrategyDescriptionItemProvider(AdapterFactory adapterFactory) {
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

            this.addAreChildNodesDraggableExpressionPropertyDescriptor(object);
            this.addTopGapExpressionPropertyDescriptor(object);
            this.addBottomGapExpressionPropertyDescriptor(object);
        }
        return this.itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Are Child Nodes Draggable Expression feature. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addAreChildNodesDraggableExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_ListLayoutStrategyDescription_areChildNodesDraggableExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_ListLayoutStrategyDescription_areChildNodesDraggableExpression_feature", "_UI_ListLayoutStrategyDescription_type"),
                DiagramPackage.Literals.LIST_LAYOUT_STRATEGY_DESCRIPTION__ARE_CHILD_NODES_DRAGGABLE_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Top Gap Expression feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addTopGapExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_ListLayoutStrategyDescription_topGapExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_ListLayoutStrategyDescription_topGapExpression_feature", "_UI_ListLayoutStrategyDescription_type"),
                DiagramPackage.Literals.LIST_LAYOUT_STRATEGY_DESCRIPTION__TOP_GAP_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Bottom Gap Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addBottomGapExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_ListLayoutStrategyDescription_bottomGapExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_ListLayoutStrategyDescription_bottomGapExpression_feature", "_UI_ListLayoutStrategyDescription_type"),
                DiagramPackage.Literals.LIST_LAYOUT_STRATEGY_DESCRIPTION__BOTTOM_GAP_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This returns ListLayoutStrategyDescription.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/ListLayoutStrategyDescription.svg"));
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
        String label = ((ListLayoutStrategyDescription) object).getAreChildNodesDraggableExpression();
        return label == null || label.length() == 0 ? this.getString("_UI_ListLayoutStrategyDescription_type") : this.getString("_UI_ListLayoutStrategyDescription_type") + " " + label;
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

        switch (notification.getFeatureID(ListLayoutStrategyDescription.class)) {
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__ARE_CHILD_NODES_DRAGGABLE_EXPRESSION:
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__TOP_GAP_EXPRESSION:
            case DiagramPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION__BOTTOM_GAP_EXPRESSION:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
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
