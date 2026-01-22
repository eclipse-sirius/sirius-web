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
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.components.view.provider.RepresentationDescriptionItemProvider;
import org.eclipse.sirius.components.view.table.CellDescription;
import org.eclipse.sirius.components.view.table.TableDescription;
import org.eclipse.sirius.components.view.table.TableFactory;
import org.eclipse.sirius.components.view.table.TablePackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.view.table.TableDescription} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 * @generated
 */
public class TableDescriptionItemProvider extends RepresentationDescriptionItemProvider {

    /**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public TableDescriptionItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

    /**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null)
		{
			super.getPropertyDescriptors(object);

			addUseStripedRowsExpressionPropertyDescriptor(object);
			addRowDescriptionPropertyDescriptor(object);
			addEnableSubRowsPropertyDescriptor(object);
			addPageSizeOptionsExpressionPropertyDescriptor(object);
			addDefaultPageSizeIndexExpressionPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

    /**
	 * This adds a property descriptor for the Use Striped Rows Expression feature.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 */
    protected void addUseStripedRowsExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TableDescription_useStripedRowsExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TableDescription_useStripedRowsExpression_feature", "_UI_TableDescription_type"),
				 TablePackage.Literals.TABLE_DESCRIPTION__USE_STRIPED_ROWS_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the Row Description feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected void addRowDescriptionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TableDescription_rowDescription_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TableDescription_rowDescription_feature", "_UI_TableDescription_type"),
				 TablePackage.Literals.TABLE_DESCRIPTION__ROW_DESCRIPTION,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the Enable Sub Rows feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected void addEnableSubRowsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TableDescription_enableSubRows_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TableDescription_enableSubRows_feature", "_UI_TableDescription_type"),
				 TablePackage.Literals.TABLE_DESCRIPTION__ENABLE_SUB_ROWS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the Page Size Options Expression feature.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 */
    protected void addPageSizeOptionsExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TableDescription_pageSizeOptionsExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TableDescription_pageSizeOptionsExpression_feature", "_UI_TableDescription_type"),
				 TablePackage.Literals.TABLE_DESCRIPTION__PAGE_SIZE_OPTIONS_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the Default Page Size Index Expression feature.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 */
    protected void addDefaultPageSizeIndexExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TableDescription_defaultPageSizeIndexExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TableDescription_defaultPageSizeIndexExpression_feature", "_UI_TableDescription_type"),
				 TablePackage.Literals.TABLE_DESCRIPTION__DEFAULT_PAGE_SIZE_INDEX_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 */
    @Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null)
		{
			super.getChildrenFeatures(object);
			childrenFeatures.add(TablePackage.Literals.TABLE_DESCRIPTION__COLUMN_DESCRIPTIONS);
			childrenFeatures.add(TablePackage.Literals.TABLE_DESCRIPTION__ROW_DESCRIPTION);
			childrenFeatures.add(TablePackage.Literals.TABLE_DESCRIPTION__CELL_DESCRIPTIONS);
			childrenFeatures.add(TablePackage.Literals.TABLE_DESCRIPTION__ROW_FILTERS);
		}
		return childrenFeatures;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

    /**
     * This returns TableDescription.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/TableDescription.svg"));
    }

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected boolean shouldComposeCreationImage() {
		return true;
	}

    /**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getText(Object object) {
		String label = ((TableDescription)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_TableDescription_type") :
			getString("_UI_TableDescription_type") + " " + label;
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
		updateChildren(notification);

		switch (notification.getFeatureID(TableDescription.class))
		{
			case TablePackage.TABLE_DESCRIPTION__USE_STRIPED_ROWS_EXPRESSION:
			case TablePackage.TABLE_DESCRIPTION__ENABLE_SUB_ROWS:
			case TablePackage.TABLE_DESCRIPTION__PAGE_SIZE_OPTIONS_EXPRESSION:
			case TablePackage.TABLE_DESCRIPTION__DEFAULT_PAGE_SIZE_INDEX_EXPRESSION:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case TablePackage.TABLE_DESCRIPTION__COLUMN_DESCRIPTIONS:
			case TablePackage.TABLE_DESCRIPTION__ROW_DESCRIPTION:
			case TablePackage.TABLE_DESCRIPTION__CELL_DESCRIPTIONS:
			case TablePackage.TABLE_DESCRIPTION__ROW_FILTERS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
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

        newChildDescriptors.add(this.createChildParameter(TablePackage.Literals.TABLE_DESCRIPTION__COLUMN_DESCRIPTIONS, TableFactory.eINSTANCE.createColumnDescription()));

        newChildDescriptors.add(this.createChildParameter(TablePackage.Literals.TABLE_DESCRIPTION__ROW_DESCRIPTION, TableFactory.eINSTANCE.createRowDescription()));

        CellDescription cellDescription = TableFactory.eINSTANCE.createCellDescription();
        cellDescription.setCellWidgetDescription(TableFactory.eINSTANCE.createCellTextfieldWidgetDescription());
        newChildDescriptors.add(this.createChildParameter(TablePackage.Literals.TABLE_DESCRIPTION__CELL_DESCRIPTIONS, cellDescription));

        newChildDescriptors.add(this.createChildParameter(TablePackage.Literals.TABLE_DESCRIPTION__ROW_FILTERS, TableFactory.eINSTANCE.createRowFilterDescription()));
    }

}
