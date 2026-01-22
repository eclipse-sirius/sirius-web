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
package org.eclipse.sirius.components.view.table.impl;

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
import org.eclipse.sirius.components.view.impl.RepresentationDescriptionImpl;
import org.eclipse.sirius.components.view.table.CellDescription;
import org.eclipse.sirius.components.view.table.ColumnDescription;
import org.eclipse.sirius.components.view.table.RowDescription;
import org.eclipse.sirius.components.view.table.RowFilterDescription;
import org.eclipse.sirius.components.view.table.TableDescription;
import org.eclipse.sirius.components.view.table.TablePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Description</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.table.impl.TableDescriptionImpl#getUseStripedRowsExpression <em>Use Striped Rows Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.table.impl.TableDescriptionImpl#getColumnDescriptions <em>Column Descriptions</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.table.impl.TableDescriptionImpl#getRowDescription <em>Row Description</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.table.impl.TableDescriptionImpl#getCellDescriptions <em>Cell Descriptions</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.table.impl.TableDescriptionImpl#isEnableSubRows <em>Enable Sub Rows</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.table.impl.TableDescriptionImpl#getRowFilters <em>Row Filters</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.table.impl.TableDescriptionImpl#getPageSizeOptionsExpression <em>Page Size Options Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.table.impl.TableDescriptionImpl#getDefaultPageSizeIndexExpression <em>Default Page Size Index Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TableDescriptionImpl extends RepresentationDescriptionImpl implements TableDescription {

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
	 * The default value of the '{@link #isEnableSubRows() <em>Enable Sub Rows</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isEnableSubRows()
	 * @generated
	 * @ordered
	 */
    protected static final boolean ENABLE_SUB_ROWS_EDEFAULT = false;
    /**
	 * The cached value of the '{@link #isEnableSubRows() <em>Enable Sub Rows</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isEnableSubRows()
	 * @generated
	 * @ordered
	 */
    protected boolean enableSubRows = ENABLE_SUB_ROWS_EDEFAULT;
    /**
     * The cached value of the '{@link #getRowFilters() <em>Row Filters</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getRowFilters()
     */
    protected EList<RowFilterDescription> rowFilters;
	/**
	 * The default value of the '{@link #getPageSizeOptionsExpression() <em>Page Size Options Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPageSizeOptionsExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String PAGE_SIZE_OPTIONS_EXPRESSION_EDEFAULT = null;
    /**
	 * The cached value of the '{@link #getPageSizeOptionsExpression() <em>Page Size Options Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPageSizeOptionsExpression()
	 * @generated
	 * @ordered
	 */
    protected String pageSizeOptionsExpression = PAGE_SIZE_OPTIONS_EXPRESSION_EDEFAULT;
	/**
	 * The default value of the '{@link #getDefaultPageSizeIndexExpression() <em>Default Page Size Index Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getDefaultPageSizeIndexExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String DEFAULT_PAGE_SIZE_INDEX_EXPRESSION_EDEFAULT = null;
    /**
	 * The cached value of the '{@link #getDefaultPageSizeIndexExpression() <em>Default Page Size Index Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getDefaultPageSizeIndexExpression()
	 * @generated
	 * @ordered
	 */
    protected String defaultPageSizeIndexExpression = DEFAULT_PAGE_SIZE_INDEX_EXPRESSION_EDEFAULT;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected TableDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return TablePackage.Literals.TABLE_DESCRIPTION;
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
			eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.TABLE_DESCRIPTION__USE_STRIPED_ROWS_EXPRESSION, oldUseStripedRowsExpression, useStripedRowsExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<ColumnDescription> getColumnDescriptions() {
		if (columnDescriptions == null)
		{
			columnDescriptions = new EObjectContainmentEList<ColumnDescription>(ColumnDescription.class, this, TablePackage.TABLE_DESCRIPTION__COLUMN_DESCRIPTIONS);
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
				msgs = ((InternalEObject)rowDescription).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TablePackage.TABLE_DESCRIPTION__ROW_DESCRIPTION, null, msgs);
			if (newRowDescription != null)
				msgs = ((InternalEObject)newRowDescription).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TablePackage.TABLE_DESCRIPTION__ROW_DESCRIPTION, null, msgs);
			msgs = basicSetRowDescription(newRowDescription, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.TABLE_DESCRIPTION__ROW_DESCRIPTION, newRowDescription, newRowDescription));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TablePackage.TABLE_DESCRIPTION__ROW_DESCRIPTION, oldRowDescription, newRowDescription);
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
			cellDescriptions = new EObjectContainmentEList<CellDescription>(CellDescription.class, this, TablePackage.TABLE_DESCRIPTION__CELL_DESCRIPTIONS);
		}
		return cellDescriptions;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean isEnableSubRows() {
		return enableSubRows;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setEnableSubRows(boolean newEnableSubRows) {
		boolean oldEnableSubRows = enableSubRows;
		enableSubRows = newEnableSubRows;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.TABLE_DESCRIPTION__ENABLE_SUB_ROWS, oldEnableSubRows, enableSubRows));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<RowFilterDescription> getRowFilters() {
		if (rowFilters == null)
		{
			rowFilters = new EObjectContainmentEList<RowFilterDescription>(RowFilterDescription.class, this, TablePackage.TABLE_DESCRIPTION__ROW_FILTERS);
		}
		return rowFilters;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getPageSizeOptionsExpression() {
		return pageSizeOptionsExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setPageSizeOptionsExpression(String newPageSizeOptionsExpression) {
		String oldPageSizeOptionsExpression = pageSizeOptionsExpression;
		pageSizeOptionsExpression = newPageSizeOptionsExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.TABLE_DESCRIPTION__PAGE_SIZE_OPTIONS_EXPRESSION, oldPageSizeOptionsExpression, pageSizeOptionsExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getDefaultPageSizeIndexExpression() {
		return defaultPageSizeIndexExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setDefaultPageSizeIndexExpression(String newDefaultPageSizeIndexExpression) {
		String oldDefaultPageSizeIndexExpression = defaultPageSizeIndexExpression;
		defaultPageSizeIndexExpression = newDefaultPageSizeIndexExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.TABLE_DESCRIPTION__DEFAULT_PAGE_SIZE_INDEX_EXPRESSION, oldDefaultPageSizeIndexExpression, defaultPageSizeIndexExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case TablePackage.TABLE_DESCRIPTION__COLUMN_DESCRIPTIONS:
				return ((InternalEList<?>)getColumnDescriptions()).basicRemove(otherEnd, msgs);
			case TablePackage.TABLE_DESCRIPTION__ROW_DESCRIPTION:
				return basicSetRowDescription(null, msgs);
			case TablePackage.TABLE_DESCRIPTION__CELL_DESCRIPTIONS:
				return ((InternalEList<?>)getCellDescriptions()).basicRemove(otherEnd, msgs);
			case TablePackage.TABLE_DESCRIPTION__ROW_FILTERS:
				return ((InternalEList<?>)getRowFilters()).basicRemove(otherEnd, msgs);
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
			case TablePackage.TABLE_DESCRIPTION__USE_STRIPED_ROWS_EXPRESSION:
				return getUseStripedRowsExpression();
			case TablePackage.TABLE_DESCRIPTION__COLUMN_DESCRIPTIONS:
				return getColumnDescriptions();
			case TablePackage.TABLE_DESCRIPTION__ROW_DESCRIPTION:
				return getRowDescription();
			case TablePackage.TABLE_DESCRIPTION__CELL_DESCRIPTIONS:
				return getCellDescriptions();
			case TablePackage.TABLE_DESCRIPTION__ENABLE_SUB_ROWS:
				return isEnableSubRows();
			case TablePackage.TABLE_DESCRIPTION__ROW_FILTERS:
				return getRowFilters();
			case TablePackage.TABLE_DESCRIPTION__PAGE_SIZE_OPTIONS_EXPRESSION:
				return getPageSizeOptionsExpression();
			case TablePackage.TABLE_DESCRIPTION__DEFAULT_PAGE_SIZE_INDEX_EXPRESSION:
				return getDefaultPageSizeIndexExpression();
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
			case TablePackage.TABLE_DESCRIPTION__USE_STRIPED_ROWS_EXPRESSION:
				setUseStripedRowsExpression((String)newValue);
				return;
			case TablePackage.TABLE_DESCRIPTION__COLUMN_DESCRIPTIONS:
				getColumnDescriptions().clear();
				getColumnDescriptions().addAll((Collection<? extends ColumnDescription>)newValue);
				return;
			case TablePackage.TABLE_DESCRIPTION__ROW_DESCRIPTION:
				setRowDescription((RowDescription)newValue);
				return;
			case TablePackage.TABLE_DESCRIPTION__CELL_DESCRIPTIONS:
				getCellDescriptions().clear();
				getCellDescriptions().addAll((Collection<? extends CellDescription>)newValue);
				return;
			case TablePackage.TABLE_DESCRIPTION__ENABLE_SUB_ROWS:
				setEnableSubRows((Boolean)newValue);
				return;
			case TablePackage.TABLE_DESCRIPTION__ROW_FILTERS:
				getRowFilters().clear();
				getRowFilters().addAll((Collection<? extends RowFilterDescription>)newValue);
				return;
			case TablePackage.TABLE_DESCRIPTION__PAGE_SIZE_OPTIONS_EXPRESSION:
				setPageSizeOptionsExpression((String)newValue);
				return;
			case TablePackage.TABLE_DESCRIPTION__DEFAULT_PAGE_SIZE_INDEX_EXPRESSION:
				setDefaultPageSizeIndexExpression((String)newValue);
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
			case TablePackage.TABLE_DESCRIPTION__USE_STRIPED_ROWS_EXPRESSION:
				setUseStripedRowsExpression(USE_STRIPED_ROWS_EXPRESSION_EDEFAULT);
				return;
			case TablePackage.TABLE_DESCRIPTION__COLUMN_DESCRIPTIONS:
				getColumnDescriptions().clear();
				return;
			case TablePackage.TABLE_DESCRIPTION__ROW_DESCRIPTION:
				setRowDescription((RowDescription)null);
				return;
			case TablePackage.TABLE_DESCRIPTION__CELL_DESCRIPTIONS:
				getCellDescriptions().clear();
				return;
			case TablePackage.TABLE_DESCRIPTION__ENABLE_SUB_ROWS:
				setEnableSubRows(ENABLE_SUB_ROWS_EDEFAULT);
				return;
			case TablePackage.TABLE_DESCRIPTION__ROW_FILTERS:
				getRowFilters().clear();
				return;
			case TablePackage.TABLE_DESCRIPTION__PAGE_SIZE_OPTIONS_EXPRESSION:
				setPageSizeOptionsExpression(PAGE_SIZE_OPTIONS_EXPRESSION_EDEFAULT);
				return;
			case TablePackage.TABLE_DESCRIPTION__DEFAULT_PAGE_SIZE_INDEX_EXPRESSION:
				setDefaultPageSizeIndexExpression(DEFAULT_PAGE_SIZE_INDEX_EXPRESSION_EDEFAULT);
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
			case TablePackage.TABLE_DESCRIPTION__USE_STRIPED_ROWS_EXPRESSION:
				return USE_STRIPED_ROWS_EXPRESSION_EDEFAULT == null ? useStripedRowsExpression != null : !USE_STRIPED_ROWS_EXPRESSION_EDEFAULT.equals(useStripedRowsExpression);
			case TablePackage.TABLE_DESCRIPTION__COLUMN_DESCRIPTIONS:
				return columnDescriptions != null && !columnDescriptions.isEmpty();
			case TablePackage.TABLE_DESCRIPTION__ROW_DESCRIPTION:
				return rowDescription != null;
			case TablePackage.TABLE_DESCRIPTION__CELL_DESCRIPTIONS:
				return cellDescriptions != null && !cellDescriptions.isEmpty();
			case TablePackage.TABLE_DESCRIPTION__ENABLE_SUB_ROWS:
				return enableSubRows != ENABLE_SUB_ROWS_EDEFAULT;
			case TablePackage.TABLE_DESCRIPTION__ROW_FILTERS:
				return rowFilters != null && !rowFilters.isEmpty();
			case TablePackage.TABLE_DESCRIPTION__PAGE_SIZE_OPTIONS_EXPRESSION:
				return PAGE_SIZE_OPTIONS_EXPRESSION_EDEFAULT == null ? pageSizeOptionsExpression != null : !PAGE_SIZE_OPTIONS_EXPRESSION_EDEFAULT.equals(pageSizeOptionsExpression);
			case TablePackage.TABLE_DESCRIPTION__DEFAULT_PAGE_SIZE_INDEX_EXPRESSION:
				return DEFAULT_PAGE_SIZE_INDEX_EXPRESSION_EDEFAULT == null ? defaultPageSizeIndexExpression != null : !DEFAULT_PAGE_SIZE_INDEX_EXPRESSION_EDEFAULT.equals(defaultPageSizeIndexExpression);
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
		result.append(", enableSubRows: ");
		result.append(enableSubRows);
		result.append(", pageSizeOptionsExpression: ");
		result.append(pageSizeOptionsExpression);
		result.append(", defaultPageSizeIndexExpression: ");
		result.append(defaultPageSizeIndexExpression);
		result.append(')');
		return result.toString();
	}

} // TableDescriptionImpl
