/*******************************************************************************
 * Copyright (c) 2024, 2026 CEA LIST.
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
import org.eclipse.sirius.components.view.table.ColumnDescription;
import org.eclipse.sirius.components.view.table.TablePackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.view.table.ColumnDescription} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class ColumnDescriptionItemProvider extends ItemProviderAdapter
        implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {

    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ColumnDescriptionItemProvider(AdapterFactory adapterFactory) {
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
            this.addHeaderIndexLabelExpressionPropertyDescriptor(object);
            this.addHeaderLabelExpressionPropertyDescriptor(object);
            this.addHeaderIconExpressionPropertyDescriptor(object);
            this.addInitialWidthExpressionPropertyDescriptor(object);
            this.addIsResizableExpressionPropertyDescriptor(object);
            this.addFilterWidgetExpressionPropertyDescriptor(object);
            this.addIsSortableExpressionPropertyDescriptor(object);
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
                this.getString("_UI_ColumnDescription_name_feature"), this.getString("_UI_PropertyDescriptor_description", "_UI_ColumnDescription_name_feature", "_UI_ColumnDescription_type"),
                TablePackage.Literals.COLUMN_DESCRIPTION__NAME, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Domain Type feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addDomainTypePropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_ColumnDescription_domainType_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_ColumnDescription_domainType_feature", "_UI_ColumnDescription_type"), TablePackage.Literals.COLUMN_DESCRIPTION__DOMAIN_TYPE,
                true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Semantic Candidates Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addSemanticCandidatesExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_ColumnDescription_semanticCandidatesExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_ColumnDescription_semanticCandidatesExpression_feature", "_UI_ColumnDescription_type"),
                TablePackage.Literals.COLUMN_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Precondition Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addPreconditionExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_ColumnDescription_preconditionExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_ColumnDescription_preconditionExpression_feature", "_UI_ColumnDescription_type"),
                TablePackage.Literals.COLUMN_DESCRIPTION__PRECONDITION_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Header Index Label Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addHeaderIndexLabelExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_ColumnDescription_headerIndexLabelExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_ColumnDescription_headerIndexLabelExpression_feature", "_UI_ColumnDescription_type"),
                TablePackage.Literals.COLUMN_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Header Label Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addHeaderLabelExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_ColumnDescription_headerLabelExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_ColumnDescription_headerLabelExpression_feature", "_UI_ColumnDescription_type"),
                TablePackage.Literals.COLUMN_DESCRIPTION__HEADER_LABEL_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Header Icon Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addHeaderIconExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_ColumnDescription_headerIconExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_ColumnDescription_headerIconExpression_feature", "_UI_ColumnDescription_type"),
                TablePackage.Literals.COLUMN_DESCRIPTION__HEADER_ICON_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Initial Width Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addInitialWidthExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_ColumnDescription_initialWidthExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_ColumnDescription_initialWidthExpression_feature", "_UI_ColumnDescription_type"),
                TablePackage.Literals.COLUMN_DESCRIPTION__INITIAL_WIDTH_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Is Resizable Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addIsResizableExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_ColumnDescription_isResizableExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_ColumnDescription_isResizableExpression_feature", "_UI_ColumnDescription_type"),
                TablePackage.Literals.COLUMN_DESCRIPTION__IS_RESIZABLE_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Filter Widget Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addFilterWidgetExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_ColumnDescription_filterWidgetExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_ColumnDescription_filterWidgetExpression_feature", "_UI_ColumnDescription_type"),
                TablePackage.Literals.COLUMN_DESCRIPTION__FILTER_WIDGET_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Is Sortable Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addIsSortableExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_ColumnDescription_isSortableExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_ColumnDescription_isSortableExpression_feature", "_UI_ColumnDescription_type"),
                TablePackage.Literals.COLUMN_DESCRIPTION__IS_SORTABLE_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This returns ColumnDescription.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/ColumnDescription.svg"));
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
        String label = ((ColumnDescription) object).getName();
        return label == null || label.length() == 0 ? this.getString("_UI_ColumnDescription_type") : this.getString("_UI_ColumnDescription_type") + " " + label;
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

        switch (notification.getFeatureID(ColumnDescription.class)) {
            case TablePackage.COLUMN_DESCRIPTION__NAME:
            case TablePackage.COLUMN_DESCRIPTION__DOMAIN_TYPE:
            case TablePackage.COLUMN_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
            case TablePackage.COLUMN_DESCRIPTION__PRECONDITION_EXPRESSION:
            case TablePackage.COLUMN_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION:
            case TablePackage.COLUMN_DESCRIPTION__HEADER_LABEL_EXPRESSION:
            case TablePackage.COLUMN_DESCRIPTION__HEADER_ICON_EXPRESSION:
            case TablePackage.COLUMN_DESCRIPTION__INITIAL_WIDTH_EXPRESSION:
            case TablePackage.COLUMN_DESCRIPTION__IS_RESIZABLE_EXPRESSION:
            case TablePackage.COLUMN_DESCRIPTION__FILTER_WIDGET_EXPRESSION:
            case TablePackage.COLUMN_DESCRIPTION__IS_SORTABLE_EXPRESSION:
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
