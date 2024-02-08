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
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.components.view.diagram.ConditionalEdgeStyle;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgeStyle;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.view.diagram.EdgeDescription} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class EdgeDescriptionItemProvider extends DiagramElementDescriptionItemProvider {

    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public EdgeDescriptionItemProvider(AdapterFactory adapterFactory) {
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

            this.addBeginLabelExpressionPropertyDescriptor(object);
            this.addCenterLabelExpressionPropertyDescriptor(object);
            this.addEndLabelExpressionPropertyDescriptor(object);
            this.addIsDomainBasedEdgePropertyDescriptor(object);
            this.addSourceNodeDescriptionsPropertyDescriptor(object);
            this.addTargetNodeDescriptionsPropertyDescriptor(object);
            this.addSourceNodesExpressionPropertyDescriptor(object);
            this.addTargetNodesExpressionPropertyDescriptor(object);
        }
        return this.itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Begin Label Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addBeginLabelExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_EdgeDescription_beginLabelExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_EdgeDescription_beginLabelExpression_feature", "_UI_EdgeDescription_type"),
                DiagramPackage.Literals.EDGE_DESCRIPTION__BEGIN_LABEL_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Center Label Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addCenterLabelExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_EdgeDescription_centerLabelExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_EdgeDescription_centerLabelExpression_feature", "_UI_EdgeDescription_type"),
                DiagramPackage.Literals.EDGE_DESCRIPTION__CENTER_LABEL_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the End Label Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addEndLabelExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_EdgeDescription_endLabelExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_EdgeDescription_endLabelExpression_feature", "_UI_EdgeDescription_type"),
                DiagramPackage.Literals.EDGE_DESCRIPTION__END_LABEL_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Is Domain Based Edge feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addIsDomainBasedEdgePropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_EdgeDescription_isDomainBasedEdge_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_EdgeDescription_isDomainBasedEdge_feature", "_UI_EdgeDescription_type"),
                DiagramPackage.Literals.EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE, true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Source Node Descriptions feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addSourceNodeDescriptionsPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_EdgeDescription_sourceNodeDescriptions_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_EdgeDescription_sourceNodeDescriptions_feature", "_UI_EdgeDescription_type"),
                DiagramPackage.Literals.EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTIONS, true, false, true, null, null, null));
    }

    /**
     * This adds a property descriptor for the Target Node Descriptions feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addTargetNodeDescriptionsPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_EdgeDescription_targetNodeDescriptions_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_EdgeDescription_targetNodeDescriptions_feature", "_UI_EdgeDescription_type"),
                DiagramPackage.Literals.EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTIONS, true, false, true, null, null, null));
    }

    /**
     * This adds a property descriptor for the Source Nodes Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addSourceNodesExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_EdgeDescription_sourceNodesExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_EdgeDescription_sourceNodesExpression_feature", "_UI_EdgeDescription_type"),
                DiagramPackage.Literals.EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Target Nodes Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addTargetNodesExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_EdgeDescription_targetNodesExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_EdgeDescription_targetNodesExpression_feature", "_UI_EdgeDescription_type"),
                DiagramPackage.Literals.EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
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
            this.childrenFeatures.add(DiagramPackage.Literals.EDGE_DESCRIPTION__PALETTE);
            this.childrenFeatures.add(DiagramPackage.Literals.EDGE_DESCRIPTION__STYLE);
            this.childrenFeatures.add(DiagramPackage.Literals.EDGE_DESCRIPTION__CONDITIONAL_STYLES);
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
     * This returns EdgeDescription.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/EdgeDescription.svg"));
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
        String label = ((EdgeDescription) object).getName();
        return label == null || label.length() == 0 ? this.getString("_UI_EdgeDescription_type") : this.getString("_UI_EdgeDescription_type") + " " + label;
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

        switch (notification.getFeatureID(EdgeDescription.class)) {
            case DiagramPackage.EDGE_DESCRIPTION__BEGIN_LABEL_EXPRESSION:
            case DiagramPackage.EDGE_DESCRIPTION__CENTER_LABEL_EXPRESSION:
            case DiagramPackage.EDGE_DESCRIPTION__END_LABEL_EXPRESSION:
            case DiagramPackage.EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE:
            case DiagramPackage.EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION:
            case DiagramPackage.EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
            case DiagramPackage.EDGE_DESCRIPTION__PALETTE:
            case DiagramPackage.EDGE_DESCRIPTION__STYLE:
            case DiagramPackage.EDGE_DESCRIPTION__CONDITIONAL_STYLES:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
                return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children that can be created
     * under this object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);
        DefaultToolsFactory defaultToolsFactory = new DefaultToolsFactory();

        newChildDescriptors.add(this.createChildParameter(DiagramPackage.Literals.EDGE_DESCRIPTION__PALETTE, defaultToolsFactory.createDefaultEdgePalette()));

        EdgeStyle newEdgeStyle = DiagramFactory.eINSTANCE.createEdgeStyle();
        newChildDescriptors.add(this.createChildParameter(DiagramPackage.Literals.EDGE_DESCRIPTION__STYLE, newEdgeStyle));

        ConditionalEdgeStyle conditionalEdgeStyle = DiagramFactory.eINSTANCE.createConditionalEdgeStyle();
        newChildDescriptors.add(this.createChildParameter(DiagramPackage.Literals.EDGE_DESCRIPTION__CONDITIONAL_STYLES, conditionalEdgeStyle));
    }

    /**
     * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
        Object childFeature = feature;
        Object childObject = child;

        boolean qualify = childFeature == DiagramPackage.Literals.EDGE_DESCRIPTION__STYLE || childFeature == DiagramPackage.Literals.EDGE_DESCRIPTION__CONDITIONAL_STYLES;

        if (qualify) {
            return this.getString("_UI_CreateChild_text2", new Object[] { this.getTypeText(childObject), this.getFeatureText(childFeature), this.getTypeText(owner) });
        }
        return super.getCreateChildText(owner, feature, child, selection);
    }

}
