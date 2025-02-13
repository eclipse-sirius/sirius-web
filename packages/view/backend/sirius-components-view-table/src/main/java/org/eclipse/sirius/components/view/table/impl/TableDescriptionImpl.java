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
import org.eclipse.sirius.components.view.table.TableDescription;
import org.eclipse.sirius.components.view.table.TablePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Description</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.table.impl.TableDescriptionImpl#getUseStripedRowsExpression <em>Use
 * Striped Rows Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.impl.TableDescriptionImpl#getColumnDescriptions <em>Column
 * Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.impl.TableDescriptionImpl#getRowDescription <em>Row
 * Description</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.impl.TableDescriptionImpl#getCellDescriptions <em>Cell
 * Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.impl.TableDescriptionImpl#isEnableSubRows <em>Enable Sub
 * Rows</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TableDescriptionImpl extends RepresentationDescriptionImpl implements TableDescription {

    /**
     * The default value of the '{@link #getUseStripedRowsExpression() <em>Use Striped Rows Expression</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getUseStripedRowsExpression()
     */
    protected static final String USE_STRIPED_ROWS_EXPRESSION_EDEFAULT = null;
    /**
     * The default value of the '{@link #isEnableSubRows() <em>Enable Sub Rows</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #isEnableSubRows()
     */
    protected static final boolean ENABLE_SUB_ROWS_EDEFAULT = false;
    /**
     * The cached value of the '{@link #getUseStripedRowsExpression() <em>Use Striped Rows Expression</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getUseStripedRowsExpression()
     */
    protected String useStripedRowsExpression = USE_STRIPED_ROWS_EXPRESSION_EDEFAULT;

    /**
     * The cached value of the '{@link #getColumnDescriptions() <em>Column Descriptions</em>}' containment reference
     * list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getColumnDescriptions()
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
     *
     * @generated
     * @ordered
     * @see #getCellDescriptions()
     */
    protected EList<CellDescription> cellDescriptions;

    /**
     * The cached value of the '{@link #isEnableSubRows() <em>Enable Sub Rows</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #isEnableSubRows()
     */
    protected boolean enableSubRows = ENABLE_SUB_ROWS_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected TableDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return TablePackage.Literals.TABLE_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getUseStripedRowsExpression() {
        return this.useStripedRowsExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setUseStripedRowsExpression(String newUseStripedRowsExpression) {
        String oldUseStripedRowsExpression = this.useStripedRowsExpression;
        this.useStripedRowsExpression = newUseStripedRowsExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.TABLE_DESCRIPTION__USE_STRIPED_ROWS_EXPRESSION, oldUseStripedRowsExpression, this.useStripedRowsExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<ColumnDescription> getColumnDescriptions() {
        if (this.columnDescriptions == null) {
            this.columnDescriptions = new EObjectContainmentEList<>(ColumnDescription.class, this, TablePackage.TABLE_DESCRIPTION__COLUMN_DESCRIPTIONS);
        }
        return this.columnDescriptions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public RowDescription getRowDescription() {
        return this.rowDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setRowDescription(RowDescription newRowDescription) {
        if (newRowDescription != this.rowDescription) {
            NotificationChain msgs = null;
            if (this.rowDescription != null)
                msgs = ((InternalEObject) this.rowDescription).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TablePackage.TABLE_DESCRIPTION__ROW_DESCRIPTION, null, msgs);
            if (newRowDescription != null)
                msgs = ((InternalEObject) newRowDescription).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TablePackage.TABLE_DESCRIPTION__ROW_DESCRIPTION, null, msgs);
            msgs = this.basicSetRowDescription(newRowDescription, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.TABLE_DESCRIPTION__ROW_DESCRIPTION, newRowDescription, newRowDescription));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetRowDescription(RowDescription newRowDescription, NotificationChain msgs) {
        RowDescription oldRowDescription = this.rowDescription;
        this.rowDescription = newRowDescription;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TablePackage.TABLE_DESCRIPTION__ROW_DESCRIPTION, oldRowDescription, newRowDescription);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<CellDescription> getCellDescriptions() {
        if (this.cellDescriptions == null) {
            this.cellDescriptions = new EObjectContainmentEList<>(CellDescription.class, this, TablePackage.TABLE_DESCRIPTION__CELL_DESCRIPTIONS);
        }
        return this.cellDescriptions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isEnableSubRows() {
        return this.enableSubRows;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setEnableSubRows(boolean newEnableSubRows) {
        boolean oldEnableSubRows = this.enableSubRows;
        this.enableSubRows = newEnableSubRows;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.TABLE_DESCRIPTION__ENABLE_SUB_ROWS, oldEnableSubRows, this.enableSubRows));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case TablePackage.TABLE_DESCRIPTION__COLUMN_DESCRIPTIONS:
                return ((InternalEList<?>) this.getColumnDescriptions()).basicRemove(otherEnd, msgs);
            case TablePackage.TABLE_DESCRIPTION__ROW_DESCRIPTION:
                return this.basicSetRowDescription(null, msgs);
            case TablePackage.TABLE_DESCRIPTION__CELL_DESCRIPTIONS:
                return ((InternalEList<?>) this.getCellDescriptions()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case TablePackage.TABLE_DESCRIPTION__USE_STRIPED_ROWS_EXPRESSION:
                return this.getUseStripedRowsExpression();
            case TablePackage.TABLE_DESCRIPTION__COLUMN_DESCRIPTIONS:
                return this.getColumnDescriptions();
            case TablePackage.TABLE_DESCRIPTION__ROW_DESCRIPTION:
                return this.getRowDescription();
            case TablePackage.TABLE_DESCRIPTION__CELL_DESCRIPTIONS:
                return this.getCellDescriptions();
            case TablePackage.TABLE_DESCRIPTION__ENABLE_SUB_ROWS:
                return this.isEnableSubRows();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case TablePackage.TABLE_DESCRIPTION__USE_STRIPED_ROWS_EXPRESSION:
                this.setUseStripedRowsExpression((String) newValue);
                return;
            case TablePackage.TABLE_DESCRIPTION__COLUMN_DESCRIPTIONS:
                this.getColumnDescriptions().clear();
                this.getColumnDescriptions().addAll((Collection<? extends ColumnDescription>) newValue);
                return;
            case TablePackage.TABLE_DESCRIPTION__ROW_DESCRIPTION:
                this.setRowDescription((RowDescription) newValue);
                return;
            case TablePackage.TABLE_DESCRIPTION__CELL_DESCRIPTIONS:
                this.getCellDescriptions().clear();
                this.getCellDescriptions().addAll((Collection<? extends CellDescription>) newValue);
                return;
            case TablePackage.TABLE_DESCRIPTION__ENABLE_SUB_ROWS:
                this.setEnableSubRows((Boolean) newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case TablePackage.TABLE_DESCRIPTION__USE_STRIPED_ROWS_EXPRESSION:
                this.setUseStripedRowsExpression(USE_STRIPED_ROWS_EXPRESSION_EDEFAULT);
                return;
            case TablePackage.TABLE_DESCRIPTION__COLUMN_DESCRIPTIONS:
                this.getColumnDescriptions().clear();
                return;
            case TablePackage.TABLE_DESCRIPTION__ROW_DESCRIPTION:
                this.setRowDescription(null);
                return;
            case TablePackage.TABLE_DESCRIPTION__CELL_DESCRIPTIONS:
                this.getCellDescriptions().clear();
                return;
            case TablePackage.TABLE_DESCRIPTION__ENABLE_SUB_ROWS:
                this.setEnableSubRows(ENABLE_SUB_ROWS_EDEFAULT);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case TablePackage.TABLE_DESCRIPTION__USE_STRIPED_ROWS_EXPRESSION:
                return !Objects.equals(USE_STRIPED_ROWS_EXPRESSION_EDEFAULT, this.useStripedRowsExpression);
            case TablePackage.TABLE_DESCRIPTION__COLUMN_DESCRIPTIONS:
                return this.columnDescriptions != null && !this.columnDescriptions.isEmpty();
            case TablePackage.TABLE_DESCRIPTION__ROW_DESCRIPTION:
                return this.rowDescription != null;
            case TablePackage.TABLE_DESCRIPTION__CELL_DESCRIPTIONS:
                return this.cellDescriptions != null && !this.cellDescriptions.isEmpty();
            case TablePackage.TABLE_DESCRIPTION__ENABLE_SUB_ROWS:
                return this.enableSubRows != ENABLE_SUB_ROWS_EDEFAULT;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String toString() {
        if (this.eIsProxy())
            return super.toString();

        String result = super.toString() + " (useStripedRowsExpression: " +
                this.useStripedRowsExpression +
                ", enableSubRows: " +
                this.enableSubRows +
                ')';
        return result;
    }

} // TableDescriptionImpl
