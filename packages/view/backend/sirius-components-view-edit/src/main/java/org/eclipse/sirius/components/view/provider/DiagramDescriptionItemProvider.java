/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.EdgeDescription;
import org.eclipse.sirius.components.view.EdgeStyle;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.view.DiagramDescription} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class DiagramDescriptionItemProvider extends RepresentationDescriptionItemProvider {
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public DiagramDescriptionItemProvider(AdapterFactory adapterFactory) {
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

            this.addAutoLayoutPropertyDescriptor(object);
        }
        return this.itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Auto Layout feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addAutoLayoutPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_DiagramDescription_autoLayout_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_DiagramDescription_autoLayout_feature", "_UI_DiagramDescription_type"), ViewPackage.Literals.DIAGRAM_DESCRIPTION__AUTO_LAYOUT,
                true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
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
            this.childrenFeatures.add(ViewPackage.Literals.DIAGRAM_DESCRIPTION__PALETTE);
            this.childrenFeatures.add(ViewPackage.Literals.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS);
            this.childrenFeatures.add(ViewPackage.Literals.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS);
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
     * This returns DiagramDescription.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/DiagramDescription.svg"));
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
        String label = ((DiagramDescription) object).getName();
        return label == null || label.length() == 0 ? this.getString("_UI_DiagramDescription_type") : this.getString("_UI_DiagramDescription_type") + " " + label;
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

        switch (notification.getFeatureID(DiagramDescription.class)) {
            case ViewPackage.DIAGRAM_DESCRIPTION__AUTO_LAYOUT:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
            case ViewPackage.DIAGRAM_DESCRIPTION__PALETTE:
            case ViewPackage.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS:
            case ViewPackage.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS:
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

        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.DIAGRAM_DESCRIPTION__PALETTE, defaultToolsFactory.createDefaultDiagramPalette()));

        NodeDescription nodeChild = ViewFactory.eINSTANCE.createNodeDescription();
        nodeChild.setName("Node");
        nodeChild.setStyle(ViewFactory.eINSTANCE.createRectangularNodeStyleDescription());
        nodeChild.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());
        nodeChild.setPalette(defaultToolsFactory.createDefaultNodePalette());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS, nodeChild));

        EdgeDescription edgeChild = ViewFactory.eINSTANCE.createEdgeDescription();
        edgeChild.setName("Edge");
        EdgeStyle newEdgeStyle = ViewFactory.eINSTANCE.createEdgeStyle();
        // newEdgeStyle.setColor("#002639");
        edgeChild.setStyle(newEdgeStyle);
        edgeChild.setPalette(defaultToolsFactory.createDefaultEdgePalette());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS, edgeChild));
    }

}
