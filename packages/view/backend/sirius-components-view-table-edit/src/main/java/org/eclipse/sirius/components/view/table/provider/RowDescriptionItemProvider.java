/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST.
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
package org.eclipse.sirius.components.view.table.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
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
import org.eclipse.sirius.components.view.table.RowDescription;
import org.eclipse.sirius.components.view.table.TableFactory;
import org.eclipse.sirius.components.view.table.TablePackage;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.sirius.components.view.table.RowDescription} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class RowDescriptionItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider,
        IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {

    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public RowDescriptionItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        if (this.itemPropertyDescriptors == null) {
            super.getPropertyDescriptors(object);

            this.addNamePropertyDescriptor(object);
            this.addSemanticCandidatesExpressionPropertyDescriptor(object);
            this.addHeaderLabelExpressionPropertyDescriptor(object);
            this.addHeaderIconExpressionPropertyDescriptor(object);
            this.addHeaderIndexLabelExpressionPropertyDescriptor(object);
            this.addInitialHeightExpressionPropertyDescriptor(object);
            this.addIsResizableExpressionPropertyDescriptor(object);
        }
        return this.itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Name feature. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addNamePropertyDescriptor(Object object) {
        this.itemPropertyDescriptors
                .add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
                        this.getResourceLocator(), this.getString("_UI_RowDescription_name_feature"),
                        this.getString("_UI_PropertyDescriptor_description", "_UI_RowDescription_name_feature",
                                "_UI_RowDescription_type"),
                        TablePackage.Literals.ROW_DESCRIPTION__NAME, true, false, false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Semantic Candidates Expression
     * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addSemanticCandidatesExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors
                .add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
                        this.getResourceLocator(), this.getString("_UI_RowDescription_semanticCandidatesExpression_feature"),
                        this.getString("_UI_PropertyDescriptor_description",
                                "_UI_RowDescription_semanticCandidatesExpression_feature", "_UI_RowDescription_type"),
                        TablePackage.Literals.ROW_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION, true, false, false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Header Label Expression feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addHeaderLabelExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_RowDescription_headerLabelExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_RowDescription_headerLabelExpression_feature",
                        "_UI_RowDescription_type"),
                TablePackage.Literals.ROW_DESCRIPTION__HEADER_LABEL_EXPRESSION, true, false, false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Header Icon Expression feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addHeaderIconExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_RowDescription_headerIconExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_RowDescription_headerIconExpression_feature",
                        "_UI_RowDescription_type"),
                TablePackage.Literals.ROW_DESCRIPTION__HEADER_ICON_EXPRESSION, true, false, false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Header Index Label Expression
     * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addHeaderIndexLabelExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors
                .add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(),
                        this.getResourceLocator(), this.getString("_UI_RowDescription_headerIndexLabelExpression_feature"),
                        this.getString("_UI_PropertyDescriptor_description",
                                "_UI_RowDescription_headerIndexLabelExpression_feature", "_UI_RowDescription_type"),
                        TablePackage.Literals.ROW_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION, true, false, false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Initial Height Expression feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addInitialHeightExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_RowDescription_initialHeightExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_RowDescription_initialHeightExpression_feature",
                        "_UI_RowDescription_type"),
                TablePackage.Literals.ROW_DESCRIPTION__INITIAL_HEIGHT_EXPRESSION, true, false, false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Is Resizable Expression feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addIsResizableExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_RowDescription_isResizableExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_RowDescription_isResizableExpression_feature",
                        "_UI_RowDescription_type"),
                TablePackage.Literals.ROW_DESCRIPTION__IS_RESIZABLE_EXPRESSION, true, false, false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This specifies how to implement {@link #getChildren} and is used to deduce an
     * appropriate feature for an {@link org.eclipse.emf.edit.command.AddCommand},
     * {@link org.eclipse.emf.edit.command.RemoveCommand} or
     * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
        if (this.childrenFeatures == null) {
            super.getChildrenFeatures(object);
            this.childrenFeatures.add(TablePackage.Literals.ROW_DESCRIPTION__CONTEXT_MENU_ENTRIES);
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
        // Check the type of the specified child object and return the proper feature to
        // use for
        // adding (see {@link AddCommand}) it as a child.

        return super.getChildFeature(object, child);
    }

    /**
     * This returns RowDescription.gif. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/RowDescription.svg"));
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
     * This returns the label text for the adapted class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getText(Object object) {
        String label = ((RowDescription) object).getName();
        return label == null || label.length() == 0 ? this.getString("_UI_RowDescription_type")
                : this.getString("_UI_RowDescription_type") + " " + label;
    }

    /**
     * This handles model notifications by calling {@link #updateChildren} to update
     * any cached children and by creating a viewer notification, which it passes to
     * {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void notifyChanged(Notification notification) {
        this.updateChildren(notification);

        switch (notification.getFeatureID(RowDescription.class)) {
            case TablePackage.ROW_DESCRIPTION__NAME:
            case TablePackage.ROW_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
            case TablePackage.ROW_DESCRIPTION__HEADER_LABEL_EXPRESSION:
            case TablePackage.ROW_DESCRIPTION__HEADER_ICON_EXPRESSION:
            case TablePackage.ROW_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION:
            case TablePackage.ROW_DESCRIPTION__INITIAL_HEIGHT_EXPRESSION:
            case TablePackage.ROW_DESCRIPTION__IS_RESIZABLE_EXPRESSION:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
            case TablePackage.ROW_DESCRIPTION__CONTEXT_MENU_ENTRIES:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
                return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing
     * the children that can be created under this object. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        newChildDescriptors.add(this.createChildParameter(TablePackage.Literals.ROW_DESCRIPTION__CONTEXT_MENU_ENTRIES,
                TableFactory.eINSTANCE.createRowContextMenuEntry()));
    }

    /**
     * Return the resource locator for this item provider's resources. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return ((IChildCreationExtender) this.adapterFactory).getResourceLocator();
    }

}
