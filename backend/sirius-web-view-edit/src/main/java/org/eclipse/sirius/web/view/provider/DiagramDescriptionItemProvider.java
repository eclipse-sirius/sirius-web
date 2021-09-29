/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.view.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.web.view.DiagramDescription;
import org.eclipse.sirius.web.view.EdgeDescription;
import org.eclipse.sirius.web.view.EdgeStyle;
import org.eclipse.sirius.web.view.NodeDescription;
import org.eclipse.sirius.web.view.ViewFactory;
import org.eclipse.sirius.web.view.ViewPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.web.view.DiagramDescription} object. <!--
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
                this.getString("_UI_DiagramDescription_autoLayout_feature"), //$NON-NLS-1$
                this.getString("_UI_PropertyDescriptor_description", "_UI_DiagramDescription_autoLayout_feature", "_UI_DiagramDescription_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                ViewPackage.Literals.DIAGRAM_DESCRIPTION__AUTO_LAYOUT, true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
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
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/DiagramDescription.svg")); //$NON-NLS-1$
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
        return label == null || label.length() == 0 ? this.getString("_UI_DiagramDescription_type") : //$NON-NLS-1$
                this.getString("_UI_DiagramDescription_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

        NodeDescription nodeChild = ViewFactory.eINSTANCE.createNodeDescription();
        nodeChild.setName("Node"); //$NON-NLS-1$
        nodeChild.setStyle(ViewFactory.eINSTANCE.createNodeStyle());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS, nodeChild));

        EdgeDescription edgeChild = ViewFactory.eINSTANCE.createEdgeDescription();
        edgeChild.setName("Edge"); //$NON-NLS-1$
        EdgeStyle newEdgeStyle = ViewFactory.eINSTANCE.createEdgeStyle();
        newEdgeStyle.setColor("#002639"); //$NON-NLS-1$
        edgeChild.setStyle(newEdgeStyle);
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS, edgeChild));
    }

}
