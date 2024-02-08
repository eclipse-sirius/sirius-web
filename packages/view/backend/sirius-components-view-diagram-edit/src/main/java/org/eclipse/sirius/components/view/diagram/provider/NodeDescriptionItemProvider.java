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
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.FreeFormLayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.IconLabelNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.InsideLabelDescription;
import org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.OutsideLabelDescription;
import org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.view.diagram.NodeDescription} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class NodeDescriptionItemProvider extends DiagramElementDescriptionItemProvider {

    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NodeDescriptionItemProvider(AdapterFactory adapterFactory) {
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

            this.addCollapsiblePropertyDescriptor(object);
            this.addReusedChildNodeDescriptionsPropertyDescriptor(object);
            this.addReusedBorderNodeDescriptionsPropertyDescriptor(object);
            this.addUserResizablePropertyDescriptor(object);
            this.addDefaultWidthExpressionPropertyDescriptor(object);
            this.addDefaultHeightExpressionPropertyDescriptor(object);
            this.addKeepAspectRatioPropertyDescriptor(object);
        }
        return this.itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Collapsible feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addCollapsiblePropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_NodeDescription_collapsible_feature"), this.getString("_UI_PropertyDescriptor_description", "_UI_NodeDescription_collapsible_feature", "_UI_NodeDescription_type"),
                DiagramPackage.Literals.NODE_DESCRIPTION__COLLAPSIBLE, true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Reused Child Node Descriptions feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addReusedChildNodeDescriptionsPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_NodeDescription_reusedChildNodeDescriptions_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_NodeDescription_reusedChildNodeDescriptions_feature", "_UI_NodeDescription_type"),
                DiagramPackage.Literals.NODE_DESCRIPTION__REUSED_CHILD_NODE_DESCRIPTIONS, true, false, true, null, null, null));
    }

    /**
     * This adds a property descriptor for the Reused Border Node Descriptions feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addReusedBorderNodeDescriptionsPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_NodeDescription_reusedBorderNodeDescriptions_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_NodeDescription_reusedBorderNodeDescriptions_feature", "_UI_NodeDescription_type"),
                DiagramPackage.Literals.NODE_DESCRIPTION__REUSED_BORDER_NODE_DESCRIPTIONS, true, false, true, null, null, null));
    }

    /**
     * This adds a property descriptor for the User Resizable feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addUserResizablePropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_NodeDescription_userResizable_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_NodeDescription_userResizable_feature", "_UI_NodeDescription_type"), DiagramPackage.Literals.NODE_DESCRIPTION__USER_RESIZABLE,
                true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Default Width Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addDefaultWidthExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_NodeDescription_defaultWidthExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_NodeDescription_defaultWidthExpression_feature", "_UI_NodeDescription_type"),
                DiagramPackage.Literals.NODE_DESCRIPTION__DEFAULT_WIDTH_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Default Height Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addDefaultHeightExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_NodeDescription_defaultHeightExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_NodeDescription_defaultHeightExpression_feature", "_UI_NodeDescription_type"),
                DiagramPackage.Literals.NODE_DESCRIPTION__DEFAULT_HEIGHT_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Keep Aspect Ratio feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addKeepAspectRatioPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_NodeDescription_keepAspectRatio_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_NodeDescription_keepAspectRatio_feature", "_UI_NodeDescription_type"),
                DiagramPackage.Literals.NODE_DESCRIPTION__KEEP_ASPECT_RATIO, true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
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
            this.childrenFeatures.add(DiagramPackage.Literals.NODE_DESCRIPTION__PALETTE);
            this.childrenFeatures.add(DiagramPackage.Literals.NODE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY);
            this.childrenFeatures.add(DiagramPackage.Literals.NODE_DESCRIPTION__STYLE);
            this.childrenFeatures.add(DiagramPackage.Literals.NODE_DESCRIPTION__CONDITIONAL_STYLES);
            this.childrenFeatures.add(DiagramPackage.Literals.NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS);
            this.childrenFeatures.add(DiagramPackage.Literals.NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS);
            this.childrenFeatures.add(DiagramPackage.Literals.NODE_DESCRIPTION__INSIDE_LABEL);
            this.childrenFeatures.add(DiagramPackage.Literals.NODE_DESCRIPTION__OUTSIDE_LABELS);
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
     * This returns NodeDescription.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/NodeDescription.svg"));
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
        String label = ((NodeDescription) object).getName();
        return label == null || label.length() == 0 ? this.getString("_UI_NodeDescription_type") : this.getString("_UI_NodeDescription_type") + " " + label;
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

        switch (notification.getFeatureID(NodeDescription.class)) {
            case DiagramPackage.NODE_DESCRIPTION__COLLAPSIBLE:
            case DiagramPackage.NODE_DESCRIPTION__USER_RESIZABLE:
            case DiagramPackage.NODE_DESCRIPTION__DEFAULT_WIDTH_EXPRESSION:
            case DiagramPackage.NODE_DESCRIPTION__DEFAULT_HEIGHT_EXPRESSION:
            case DiagramPackage.NODE_DESCRIPTION__KEEP_ASPECT_RATIO:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
            case DiagramPackage.NODE_DESCRIPTION__PALETTE:
            case DiagramPackage.NODE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY:
            case DiagramPackage.NODE_DESCRIPTION__STYLE:
            case DiagramPackage.NODE_DESCRIPTION__CONDITIONAL_STYLES:
            case DiagramPackage.NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS:
            case DiagramPackage.NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS:
            case DiagramPackage.NODE_DESCRIPTION__INSIDE_LABEL:
            case DiagramPackage.NODE_DESCRIPTION__OUTSIDE_LABELS:
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

        newChildDescriptors.add(this.createChildParameter(DiagramPackage.Literals.NODE_DESCRIPTION__PALETTE, defaultToolsFactory.createDefaultNodePalette()));

        NodeDescription nodeChild = DiagramFactory.eINSTANCE.createNodeDescription();
        nodeChild.setName("Sub-node");
        nodeChild.setStyle(DiagramFactory.eINSTANCE.createRectangularNodeStyleDescription());
        nodeChild.setChildrenLayoutStrategy(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());
        nodeChild.setPalette(defaultToolsFactory.createDefaultNodePalette());
        newChildDescriptors.add(this.createChildParameter(DiagramPackage.Literals.NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS, nodeChild));

        NodeDescription borderNodeChild = DiagramFactory.eINSTANCE.createNodeDescription();
        borderNodeChild.setName("Border node");
        borderNodeChild.setStyle(DiagramFactory.eINSTANCE.createRectangularNodeStyleDescription());
        borderNodeChild.setChildrenLayoutStrategy(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());
        borderNodeChild.setPalette(defaultToolsFactory.createDefaultNodePalette());
        newChildDescriptors.add(this.createChildParameter(DiagramPackage.Literals.NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS, borderNodeChild));

        newChildDescriptors.add(this.createChildParameter(DiagramPackage.Literals.NODE_DESCRIPTION__STYLE, DiagramFactory.eINSTANCE.createRectangularNodeStyleDescription()));
        newChildDescriptors.add(this.createChildParameter(DiagramPackage.Literals.NODE_DESCRIPTION__STYLE, DiagramFactory.eINSTANCE.createImageNodeStyleDescription()));
        newChildDescriptors.add(this.createChildParameter(DiagramPackage.Literals.NODE_DESCRIPTION__STYLE, DiagramFactory.eINSTANCE.createIconLabelNodeStyleDescription()));

        newChildDescriptors.add(this.createChildParameter(DiagramPackage.Literals.NODE_DESCRIPTION__CONDITIONAL_STYLES, DiagramFactory.eINSTANCE.createConditionalNodeStyle()));

        newChildDescriptors.add(this.createChildParameter(DiagramPackage.Literals.NODE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY, DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription()));
        newChildDescriptors.add(this.createChildParameter(DiagramPackage.Literals.NODE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY, DiagramFactory.eINSTANCE.createListLayoutStrategyDescription()));
        InsideLabelDescription insideLabelDescription = DiagramFactory.eINSTANCE.createInsideLabelDescription();
        insideLabelDescription.setStyle(DiagramFactory.eINSTANCE.createInsideLabelStyle());
        newChildDescriptors.add(this.createChildParameter(DiagramPackage.Literals.NODE_DESCRIPTION__INSIDE_LABEL, insideLabelDescription));
        OutsideLabelDescription outsideLabelDescription = DiagramFactory.eINSTANCE.createOutsideLabelDescription();
        outsideLabelDescription.setStyle(DiagramFactory.eINSTANCE.createOutsideLabelStyle());
        newChildDescriptors.add(this.createChildParameter(DiagramPackage.Literals.NODE_DESCRIPTION__OUTSIDE_LABELS, outsideLabelDescription));
    }

    /**
     * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
        String text = "";
        if (feature == DiagramPackage.Literals.NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS) {
            text = "Sub-node";
        } else if (feature == DiagramPackage.Literals.NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS) {
            text = "Border node";
        } else if (child instanceof RectangularNodeStyleDescription) {
            text = "Style Rectangular";
        } else if (child instanceof ImageNodeStyleDescription) {
            text = "Style Image";
        } else if (child instanceof IconLabelNodeStyleDescription) {
            text = "Style Icon-Label";
        } else if (child instanceof FreeFormLayoutStrategyDescription) {
            text = "Layout Free Form";
        } else if (child instanceof ListLayoutStrategyDescription) {
            text = "Layout List";
        } else {
            text = super.getCreateChildText(owner, feature, child, selection);
        }

        return text;
    }

}
