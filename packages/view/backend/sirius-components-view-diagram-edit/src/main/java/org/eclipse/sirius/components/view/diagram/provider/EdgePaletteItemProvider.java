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
package org.eclipse.sirius.components.view.diagram.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IChildCreationExtender;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.EdgePalette;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.view.diagram.EdgePalette} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class EdgePaletteItemProvider extends ItemProviderAdapter
        implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {

    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public EdgePaletteItemProvider(AdapterFactory adapterFactory) {
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

        }
        return this.itemPropertyDescriptors;
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
            this.childrenFeatures.add(DiagramPackage.Literals.EDGE_PALETTE__DELETE_TOOL);
            this.childrenFeatures.add(DiagramPackage.Literals.EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL);
            this.childrenFeatures.add(DiagramPackage.Literals.EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL);
            this.childrenFeatures.add(DiagramPackage.Literals.EDGE_PALETTE__END_LABEL_EDIT_TOOL);
            this.childrenFeatures.add(DiagramPackage.Literals.EDGE_PALETTE__NODE_TOOLS);
            this.childrenFeatures.add(DiagramPackage.Literals.EDGE_PALETTE__EDGE_RECONNECTION_TOOLS);
            this.childrenFeatures.add(DiagramPackage.Literals.EDGE_PALETTE__TOOL_SECTIONS);
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
     * This returns EdgePalette.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/EdgePalette.svg"));
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
        return this.getString("_UI_EdgePalette_type");
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

        switch (notification.getFeatureID(EdgePalette.class)) {
            case DiagramPackage.EDGE_PALETTE__DELETE_TOOL:
            case DiagramPackage.EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL:
            case DiagramPackage.EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL:
            case DiagramPackage.EDGE_PALETTE__END_LABEL_EDIT_TOOL:
            case DiagramPackage.EDGE_PALETTE__NODE_TOOLS:
            case DiagramPackage.EDGE_PALETTE__EDGE_RECONNECTION_TOOLS:
            case DiagramPackage.EDGE_PALETTE__TOOL_SECTIONS:
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

        newChildDescriptors.add(this.createChildParameter(DiagramPackage.Literals.EDGE_PALETTE__DELETE_TOOL, DiagramFactory.eINSTANCE.createDeleteTool()));

        newChildDescriptors.add(this.createChildParameter(DiagramPackage.Literals.EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL, DiagramFactory.eINSTANCE.createLabelEditTool()));

        newChildDescriptors.add(this.createChildParameter(DiagramPackage.Literals.EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL, DiagramFactory.eINSTANCE.createLabelEditTool()));

        newChildDescriptors.add(this.createChildParameter(DiagramPackage.Literals.EDGE_PALETTE__END_LABEL_EDIT_TOOL, DiagramFactory.eINSTANCE.createLabelEditTool()));

        newChildDescriptors.add(this.createChildParameter(DiagramPackage.Literals.EDGE_PALETTE__NODE_TOOLS, DiagramFactory.eINSTANCE.createNodeTool()));

        newChildDescriptors.add(this.createChildParameter(DiagramPackage.Literals.EDGE_PALETTE__EDGE_RECONNECTION_TOOLS, DiagramFactory.eINSTANCE.createSourceEdgeEndReconnectionTool()));

        newChildDescriptors.add(this.createChildParameter(DiagramPackage.Literals.EDGE_PALETTE__EDGE_RECONNECTION_TOOLS, DiagramFactory.eINSTANCE.createTargetEdgeEndReconnectionTool()));

        newChildDescriptors.add(this.createChildParameter(DiagramPackage.Literals.EDGE_PALETTE__TOOL_SECTIONS, DiagramFactory.eINSTANCE.createEdgeToolSection()));
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

        boolean qualify = childFeature == DiagramPackage.Literals.EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL || childFeature == DiagramPackage.Literals.EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL
                || childFeature == DiagramPackage.Literals.EDGE_PALETTE__END_LABEL_EDIT_TOOL;

        if (qualify) {
            return this.getString("_UI_CreateChild_text2", new Object[] { this.getTypeText(childObject), this.getFeatureText(childFeature), this.getTypeText(owner) });
        }
        return super.getCreateChildText(owner, feature, child, selection);
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
