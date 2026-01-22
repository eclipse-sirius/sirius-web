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
package org.eclipse.sirius.components.view.widget.tablewidget.impl;

import java.util.Collection;
import java.util.Objects;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.form.impl.WidgetDescriptionImpl;
import org.eclipse.sirius.components.view.table.CellDescription;
import org.eclipse.sirius.components.view.table.ColumnDescription;
import org.eclipse.sirius.components.view.table.RowDescription;
import org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription;
import org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Description</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.widget.tablewidget.impl.TableWidgetDescriptionImpl#getColumnDescriptions <em>Column Descriptions</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.widget.tablewidget.impl.TableWidgetDescriptionImpl#getRowDescription <em>Row Description</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.widget.tablewidget.impl.TableWidgetDescriptionImpl#getCellDescriptions <em>Cell Descriptions</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.widget.tablewidget.impl.TableWidgetDescriptionImpl#getUseStripedRowsExpression <em>Use Striped Rows Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.widget.tablewidget.impl.TableWidgetDescriptionImpl#getIsEnabledExpression <em>Is Enabled Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TableWidgetDescriptionImpl extends WidgetDescriptionImpl implements TableWidgetDescription {

    /**
	 * The cached value of the '{@link #getColumnDescriptions() <em>Column Descriptions</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getColumnDescriptions()
	 * @generated
	 * @ordered
	 */
    protected EList<ColumnDescription> columnDescriptions;
    /**
     * The cached value of the '{@link #getRowDescription() <em>Row Description</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getRowDescription()
     */
    protected RowDescription rowDescription;
    /**
	 * The cached value of the '{@link #getCellDescriptions() <em>Cell Descriptions</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getCellDescriptions()
	 * @generated
	 * @ordered
	 */
    protected EList<CellDescription> cellDescriptions;
	/**
	 * The default value of the '{@link #getUseStripedRowsExpression() <em>Use Striped Rows Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getUseStripedRowsExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String USE_STRIPED_ROWS_EXPRESSION_EDEFAULT = null;
    /**
	 * The cached value of the '{@link #getUseStripedRowsExpression() <em>Use Striped Rows Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getUseStripedRowsExpression()
	 * @generated
	 * @ordered
	 */
    protected String useStripedRowsExpression = USE_STRIPED_ROWS_EXPRESSION_EDEFAULT;
	/**
     * The default value of the '{@link #getIsEnabledExpression() <em>Is Enabled Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getIsEnabledExpression()
     */
    protected static final String IS_ENABLED_EXPRESSION_EDEFAULT = null;
    /**
     * The cached value of the '{@link #getIsEnabledExpression() <em>Is Enabled Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getIsEnabledExpression()
     */
    protected String isEnabledExpression = IS_ENABLED_EXPRESSION_EDEFAULT;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected TableWidgetDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return TableWidgetPackage.Literals.TABLE_WIDGET_DESCRIPTION;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<ColumnDescription> getColumnDescriptions() {
		if (columnDescriptions == null)
		{
			columnDescriptions = new EObjectContainmentEList<ColumnDescription>(ColumnDescription.class, this, TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__COLUMN_DESCRIPTIONS);
		}
		return columnDescriptions;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public RowDescription getRowDescription() {
		return rowDescription;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setRowDescription(RowDescription newRowDescription) {
		if (newRowDescription != rowDescription)
		{
			NotificationChain msgs = null;
			if (rowDescription != null)
				msgs = ((InternalEObject)rowDescription).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__ROW_DESCRIPTION, null, msgs);
			if (newRowDescription != null)
				msgs = ((InternalEObject)newRowDescription).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__ROW_DESCRIPTION, null, msgs);
			msgs = basicSetRowDescription(newRowDescription, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__ROW_DESCRIPTION, newRowDescription, newRowDescription));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetRowDescription(RowDescription newRowDescription, NotificationChain msgs) {
		RowDescription oldRowDescription = rowDescription;
		rowDescription = newRowDescription;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__ROW_DESCRIPTION, oldRowDescription, newRowDescription);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<CellDescription> getCellDescriptions() {
		if (cellDescriptions == null)
		{
			cellDescriptions = new EObjectContainmentEList<CellDescription>(CellDescription.class, this, TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__CELL_DESCRIPTIONS);
		}
		return cellDescriptions;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getUseStripedRowsExpression() {
		return useStripedRowsExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setUseStripedRowsExpression(String newUseStripedRowsExpression) {
		String oldUseStripedRowsExpression = useStripedRowsExpression;
		useStripedRowsExpression = newUseStripedRowsExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__USE_STRIPED_ROWS_EXPRESSION, oldUseStripedRowsExpression, useStripedRowsExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getIsEnabledExpression() {
		return isEnabledExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setIsEnabledExpression(String newIsEnabledExpression) {
		String oldIsEnabledExpression = isEnabledExpression;
		isEnabledExpression = newIsEnabledExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION, oldIsEnabledExpression, isEnabledExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__COLUMN_DESCRIPTIONS:
				return ((InternalEList<?>)getColumnDescriptions()).basicRemove(otherEnd, msgs);
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__ROW_DESCRIPTION:
				return basicSetRowDescription(null, msgs);
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__CELL_DESCRIPTIONS:
				return ((InternalEList<?>)getCellDescriptions()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__COLUMN_DESCRIPTIONS:
				return getColumnDescriptions();
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__ROW_DESCRIPTION:
				return getRowDescription();
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__CELL_DESCRIPTIONS:
				return getCellDescriptions();
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__USE_STRIPED_ROWS_EXPRESSION:
				return getUseStripedRowsExpression();
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
				return getIsEnabledExpression();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
		switch (featureID)
		{
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__COLUMN_DESCRIPTIONS:
				getColumnDescriptions().clear();
				getColumnDescriptions().addAll((Collection<? extends ColumnDescription>)newValue);
				return;
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__ROW_DESCRIPTION:
				setRowDescription((RowDescription)newValue);
				return;
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__CELL_DESCRIPTIONS:
				getCellDescriptions().clear();
				getCellDescriptions().addAll((Collection<? extends CellDescription>)newValue);
				return;
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__USE_STRIPED_ROWS_EXPRESSION:
				setUseStripedRowsExpression((String)newValue);
				return;
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
				setIsEnabledExpression((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eUnset(int featureID) {
		switch (featureID)
		{
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__COLUMN_DESCRIPTIONS:
				getColumnDescriptions().clear();
				return;
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__ROW_DESCRIPTION:
				setRowDescription((RowDescription)null);
				return;
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__CELL_DESCRIPTIONS:
				getCellDescriptions().clear();
				return;
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__USE_STRIPED_ROWS_EXPRESSION:
				setUseStripedRowsExpression(USE_STRIPED_ROWS_EXPRESSION_EDEFAULT);
				return;
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
				setIsEnabledExpression(IS_ENABLED_EXPRESSION_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean eIsSet(int featureID) {
		switch (featureID)
		{
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__COLUMN_DESCRIPTIONS:
				return columnDescriptions != null && !columnDescriptions.isEmpty();
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__ROW_DESCRIPTION:
				return rowDescription != null;
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__CELL_DESCRIPTIONS:
				return cellDescriptions != null && !cellDescriptions.isEmpty();
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__USE_STRIPED_ROWS_EXPRESSION:
				return USE_STRIPED_ROWS_EXPRESSION_EDEFAULT == null ? useStripedRowsExpression != null : !USE_STRIPED_ROWS_EXPRESSION_EDEFAULT.equals(useStripedRowsExpression);
			case TableWidgetPackage.TABLE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
				return IS_ENABLED_EXPRESSION_EDEFAULT == null ? isEnabledExpression != null : !IS_ENABLED_EXPRESSION_EDEFAULT.equals(isEnabledExpression);
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (useStripedRowsExpression: ");
		result.append(useStripedRowsExpression);
		result.append(", IsEnabledExpression: ");
		result.append(isEnabledExpression);
		result.append(')');
		return result.toString();
	}

} // TableWidgetDescriptionImpl
