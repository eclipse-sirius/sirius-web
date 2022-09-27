/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.sirius.components.view.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.components.view.FreeFormLayoutStrategyDescription;
import org.eclipse.sirius.components.view.IconLabelNodeStyleDescription;
import org.eclipse.sirius.components.view.ImageNodeStyleDescription;
import org.eclipse.sirius.components.view.ListLayoutStrategyDescription;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.NodeTool;
import org.eclipse.sirius.components.view.RectangularNodeStyleDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.view.NodeDescription} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
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

            this.addChildrenLayoutStrategyPropertyDescriptor(object);
        }
        return this.itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Children Layout Strategy feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addChildrenLayoutStrategyPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_NodeDescription_childrenLayoutStrategy_feature"), //$NON-NLS-1$
                this.getString("_UI_PropertyDescriptor_description", "_UI_NodeDescription_childrenLayoutStrategy_feature", "_UI_NodeDescription_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                ViewPackage.Literals.NODE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY, true, false, true, null, null, null));
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
            this.childrenFeatures.add(ViewPackage.Literals.NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS);
            this.childrenFeatures.add(ViewPackage.Literals.NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS);
            this.childrenFeatures.add(ViewPackage.Literals.NODE_DESCRIPTION__STYLE);
            this.childrenFeatures.add(ViewPackage.Literals.NODE_DESCRIPTION__NODE_TOOLS);
            this.childrenFeatures.add(ViewPackage.Literals.NODE_DESCRIPTION__CONDITIONAL_STYLES);
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
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/NodeDescription.svg")); //$NON-NLS-1$
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
        return label == null || label.length() == 0 ? this.getString("_UI_NodeDescription_type") : //$NON-NLS-1$
                this.getString("_UI_NodeDescription_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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
        case ViewPackage.NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS:
        case ViewPackage.NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS:
        case ViewPackage.NODE_DESCRIPTION__STYLE:
        case ViewPackage.NODE_DESCRIPTION__NODE_TOOLS:
        case ViewPackage.NODE_DESCRIPTION__CONDITIONAL_STYLES:
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

        NodeTool newNodeTool = ViewFactory.eINSTANCE.createNodeTool();
        newNodeTool.setName("Create Node"); //$NON-NLS-1$
        newNodeTool.getBody().add(ViewFactory.eINSTANCE.createChangeContext());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.NODE_DESCRIPTION__NODE_TOOLS, newNodeTool));

        NodeDescription nodeChild = ViewFactory.eINSTANCE.createNodeDescription();
        nodeChild.setName("Sub-node"); //$NON-NLS-1$
        nodeChild.setStyle(ViewFactory.eINSTANCE.createRectangularNodeStyleDescription());
        nodeChild.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS, nodeChild));

        NodeDescription borderNodeChild = ViewFactory.eINSTANCE.createNodeDescription();
        borderNodeChild.setName("Border node"); //$NON-NLS-1$
        borderNodeChild.setStyle(ViewFactory.eINSTANCE.createRectangularNodeStyleDescription());
        borderNodeChild.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS, borderNodeChild));

        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.NODE_DESCRIPTION__STYLE, ViewFactory.eINSTANCE.createRectangularNodeStyleDescription()));
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.NODE_DESCRIPTION__STYLE, ViewFactory.eINSTANCE.createImageNodeStyleDescription()));
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.NODE_DESCRIPTION__STYLE, ViewFactory.eINSTANCE.createIconLabelNodeStyleDescription()));

        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.NODE_DESCRIPTION__CONDITIONAL_STYLES, ViewFactory.eINSTANCE.createConditionalNodeStyle()));

        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.NODE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY, ViewFactory.eINSTANCE.createFreeFormLayoutStrategyDescription()));
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.NODE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY, ViewFactory.eINSTANCE.createListLayoutStrategyDescription()));
    }

    /**
     * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
        String text = ""; //$NON-NLS-1$
        if (feature == ViewPackage.Literals.NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS) {
            text = "Sub-node"; //$NON-NLS-1$
        } else if (feature == ViewPackage.Literals.NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS) {
            text = "Border node"; //$NON-NLS-1$
        } else if (child instanceof RectangularNodeStyleDescription) {
            text = "Style Rectangular"; //$NON-NLS-1$
        } else if (child instanceof ImageNodeStyleDescription) {
            text = "Style Image"; //$NON-NLS-1$
        } else if (child instanceof IconLabelNodeStyleDescription) {
            text = "Style Icon-Label"; //$NON-NLS-1$
        } else if (child instanceof FreeFormLayoutStrategyDescription) {
            text = "Layout Free Form"; //$NON-NLS-1$
        } else if (child instanceof ListLayoutStrategyDescription) {
            text = "Layout List"; //$NON-NLS-1$
        } else {
            text = super.getCreateChildText(owner, feature, child, selection);
        }

        return text;
    }

}
