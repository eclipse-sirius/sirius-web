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
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
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
import org.eclipse.sirius.components.view.DiagramElementDescription;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.view.DiagramElementDescription} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
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

            this.addNamePropertyDescriptor(object);
            this.addDomainTypePropertyDescriptor(object);
            this.addSemanticCandidatesExpressionPropertyDescriptor(object);
            this.addPreconditionExpressionPropertyDescriptor(object);
            this.addLabelExpressionPropertyDescriptor(object);
            this.addSynchronizationPolicyPropertyDescriptor(object);
        }
        return this.itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Name feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addNamePropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_DiagramElementDescription_name_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_DiagramElementDescription_name_feature", "_UI_DiagramElementDescription_type"),
                ViewPackage.Literals.DIAGRAM_ELEMENT_DESCRIPTION__NAME, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Domain Type feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addDomainTypePropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_DiagramElementDescription_domainType_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_DiagramElementDescription_domainType_feature", "_UI_DiagramElementDescription_type"),
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
                this.getString("_UI_DiagramElementDescription_semanticCandidatesExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_DiagramElementDescription_semanticCandidatesExpression_feature", "_UI_DiagramElementDescription_type"),
                ViewPackage.Literals.DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Label Expression feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addLabelExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_DiagramElementDescription_labelExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_DiagramElementDescription_labelExpression_feature", "_UI_DiagramElementDescription_type"),
                ViewPackage.Literals.DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Synchronization Policy feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addSynchronizationPolicyPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_DiagramElementDescription_synchronizationPolicy_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_DiagramElementDescription_synchronizationPolicy_feature", "_UI_DiagramElementDescription_type"),
                ViewPackage.Literals.DIAGRAM_ELEMENT_DESCRIPTION__SYNCHRONIZATION_POLICY, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Precondition Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addPreconditionExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_DiagramElementDescription_preconditionExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_DiagramElementDescription_preconditionExpression_feature", "_UI_DiagramElementDescription_type"),
                ViewPackage.Literals.DIAGRAM_ELEMENT_DESCRIPTION__PRECONDITION_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
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
            this.childrenFeatures.add(ViewPackage.Literals.DIAGRAM_ELEMENT_DESCRIPTION__DELETE_TOOL);
            this.childrenFeatures.add(ViewPackage.Literals.DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EDIT_TOOL);
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
     * This returns DiagramElementDescription.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/DiagramElementDescription"));
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
        String label = ((DiagramElementDescription) object).getName();
        return label == null || label.length() == 0 ? this.getString("_UI_DiagramElementDescription_type") : this.getString("_UI_DiagramElementDescription_type") + " " + label;
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
            case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__NAME:
            case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE:
            case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
            case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__PRECONDITION_EXPRESSION:
            case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION:
            case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__SYNCHRONIZATION_POLICY:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
            case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__DELETE_TOOL:
            case ViewPackage.DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EDIT_TOOL:
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
        DefaultToolsFactory defaultToolsFactory = new DefaultToolsFactory();
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EDIT_TOOL, defaultToolsFactory.createDefaultLabelEditTool()));
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.DIAGRAM_ELEMENT_DESCRIPTION__DELETE_TOOL, defaultToolsFactory.createDefaultDeleteTool()));
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
