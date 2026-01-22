/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.view.widget.tablewidget.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.components.view.form.provider.WidgetDescriptionItemProvider;
import org.eclipse.sirius.components.view.table.TableFactory;
import org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription;
import org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 * @generated
 */
public class TableWidgetDescriptionItemProvider extends WidgetDescriptionItemProvider {

    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public TableWidgetDescriptionItemProvider(AdapterFactory adapterFactory) {
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
		if (itemPropertyDescriptors == null)
		{
			super.getPropertyDescriptors(object);

			addUseStripedRowsExpressionPropertyDescriptor(object);
			addIsEnabledExpressionPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

    /**
	 * This adds a property descriptor for the Use Striped Rows Expression feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected void addUseStripedRowsExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TableWidgetDescription_useStripedRowsExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TableWidgetDescription_useStripedRowsExpression_feature", "_UI_TableWidgetDescription_type"),
				 TableWidgetPackage.Literals.TABLE_WIDGET_DESCRIPTION__USE_STRIPED_ROWS_EXPRESSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
     * This adds a property descriptor for the Is Enabled Expression feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addIsEnabledExpressionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_TableWidgetDescription_IsEnabledExpression_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_TableWidgetDescription_IsEnabledExpression_feature", "_UI_TableWidgetDescription_type"),
				 TableWidgetPackage.Literals.TABLE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION,
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null)
		{
			super.getChildrenFeatures(object);
			childrenFeatures.add(TableWidgetPackage.Literals.TABLE_WIDGET_DESCRIPTION__COLUMN_DESCRIPTIONS);
			childrenFeatures.add(TableWidgetPackage.Literals.TABLE_WIDGET_DESCRIPTION__ROW_DESCRIPTION);
			childrenFeatures.add(TableWidgetPackage.Literals.TABLE_WIDGET_DESCRIPTION__CELL_DESCRIPTIONS);
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
     * This returns TableWidgetDescription.gif. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/TableWidgetDescription.svg"));
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
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getText(Object object) {
		String label = ((TableWidgetDescription)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_TableWidgetDescription_type") :
			getString("_UI_TableWidgetDescription_type") + " " + label;
	}

    /**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(TableWidgetDescription.class))
		{
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__USE_STRIPED_ROWS_EXPRESSION:
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__COLUMN_DESCRIPTIONS:
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__ROW_DESCRIPTION:
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__CELL_DESCRIPTIONS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

    /**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(TableWidgetPackage.Literals.TABLE_WIDGET_DESCRIPTION__COLUMN_DESCRIPTIONS,
				 TableFactory.eINSTANCE.createColumnDescription()));

		newChildDescriptors.add
			(createChildParameter
				(TableWidgetPackage.Literals.TABLE_WIDGET_DESCRIPTION__ROW_DESCRIPTION,
				 TableFactory.eINSTANCE.createRowDescription()));

		newChildDescriptors.add
			(createChildParameter
				(TableWidgetPackage.Literals.TABLE_WIDGET_DESCRIPTION__CELL_DESCRIPTIONS,
				 TableFactory.eINSTANCE.createCellDescription()));
	}

    /**
     * Return the resource locator for this item provider's resources. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
		return TableWidgetEditPlugin.INSTANCE;
	}

}
