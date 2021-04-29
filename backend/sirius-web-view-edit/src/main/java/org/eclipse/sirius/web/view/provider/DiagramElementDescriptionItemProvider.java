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
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.web.view.DiagramElementDescription;
import org.eclipse.sirius.web.view.ViewPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.web.view.DiagramElementDescription} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class DiagramElementDescriptionItemProvider extends ItemProviderAdapter
        implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public DiagramElementDescriptionItemProvider(AdapterFactory adapterFactory) {
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

            this.addDomainTypePropertyDescriptor(object);
            this.addSemanticCandidatesExpressionPropertyDescriptor(object);
            this.addCreationModePropertyDescriptor(object);
            this.addLabelExpressionPropertyDescriptor(object);
        }
        return this.itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Domain Type feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addDomainTypePropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_DiagramElementDescription_domainType_feature"), //$NON-NLS-1$
                this.getString("_UI_PropertyDescriptor_description", "_UI_DiagramElementDescription_domainType_feature", "_UI_DiagramElementDescription_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                ViewPackage.Literals.DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Semantic Candidates Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addSemanticCandidatesExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_DiagramElementDescription_semanticCandidatesExpression_feature"), //$NON-NLS-1$
                this.getString("_UI_PropertyDescriptor_description", "_UI_DiagramElementDescription_semanticCandidatesExpression_feature", "_UI_DiagramElementDescription_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                ViewPackage.Literals.DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Creation Mode feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addCreationModePropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_DiagramElementDescription_creationMode_feature"), //$NON-NLS-1$
                this.getString("_UI_PropertyDescriptor_description", "_UI_DiagramElementDescription_creationMode_feature", "_UI_DiagramElementDescription_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                ViewPackage.Literals.DIAGRAM_ELEMENT_DESCRIPTION__CREATION_MODE, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Label Expression feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addLabelExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_DiagramElementDescription_labelExpression_feature"), //$NON-NLS-1$
                this.getString("_UI_PropertyDescriptor_description", "_UI_DiagramElementDescription_labelExpression_feature", "_UI_DiagramElementDescription_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                ViewPackage.Literals.DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This returns DiagramElementDescription.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/DiagramElementDescription")); //$NON-NLS-1$
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
        String label = ((DiagramElementDescription) object).getDomainType();
        return label == null || label.length() == 0 ? this.getString("_UI_DiagramElementDescription_type") : //$NON-NLS-1$
                this.getString("_UI_DiagramElementDescription_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

        switch (notification.getFeatureID(DiagramElementDescription.class)) {
        case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE:
        case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
        case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__CREATION_MODE:
        case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION:
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
        return ViewEditPlugin.INSTANCE;
    }

}
