/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.view.form.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.WidgetGridLayout;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Widget Grid Layout</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.WidgetGridLayoutImpl#getGridTemplateColumns <em>Grid Template
 * Colums</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.WidgetGridLayoutImpl#getGridTemplateRows <em>Grid Template
 * Rows</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.WidgetGridLayoutImpl#getLabelGridRow <em>Label Grid
 * Row</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.WidgetGridLayoutImpl#getLabelGridColumn <em>Label Grid
 * Column</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.WidgetGridLayoutImpl#getWidgetGridRow <em>Widget Grid
 * Row</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.WidgetGridLayoutImpl#getWidgetGridColumn <em>Widget Grid
 * Column</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class WidgetGridLayoutImpl extends MinimalEObjectImpl.Container implements WidgetGridLayout {
    /**
     * The default value of the '{@link #getGridTemplateColumns() <em>Grid Template Columns</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getGridTemplateColumns()
     * @generated
     * @ordered
     */
    protected static final String GRID_TEMPLATE_COLUMNS_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getGridTemplateColumns() <em>Grid Template Columns</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getGridTemplateColumns()
     * @generated
     * @ordered
     */
    protected String gridTemplateColumns = GRID_TEMPLATE_COLUMNS_EDEFAULT;

    /**
     * The default value of the '{@link #getGridTemplateRows() <em>Grid Template Rows</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getGridTemplateRows()
     * @generated
     * @ordered
     */
    protected static final String GRID_TEMPLATE_ROWS_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getGridTemplateRows() <em>Grid Template Rows</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getGridTemplateRows()
     * @generated
     * @ordered
     */
    protected String gridTemplateRows = GRID_TEMPLATE_ROWS_EDEFAULT;

    /**
     * The default value of the '{@link #getLabelGridRow() <em>Label Grid Row</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getLabelGridRow()
     * @generated
     * @ordered
     */
    protected static final String LABEL_GRID_ROW_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getLabelGridRow() <em>Label Grid Row</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getLabelGridRow()
     * @generated
     * @ordered
     */
    protected String labelGridRow = LABEL_GRID_ROW_EDEFAULT;

    /**
     * The default value of the '{@link #getLabelGridColumn() <em>Label Grid Column</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getLabelGridColumn()
     * @generated
     * @ordered
     */
    protected static final String LABEL_GRID_COLUMN_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getLabelGridColumn() <em>Label Grid Column</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getLabelGridColumn()
     * @generated
     * @ordered
     */
    protected String labelGridColumn = LABEL_GRID_COLUMN_EDEFAULT;

    /**
     * The default value of the '{@link #getWidgetGridRow() <em>Widget Grid Row</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getWidgetGridRow()
     * @generated
     * @ordered
     */
    protected static final String WIDGET_GRID_ROW_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getWidgetGridRow() <em>Widget Grid Row</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getWidgetGridRow()
     * @generated
     * @ordered
     */
    protected String widgetGridRow = WIDGET_GRID_ROW_EDEFAULT;

    /**
     * The default value of the '{@link #getWidgetGridColumn() <em>Widget Grid Column</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getWidgetGridColumn()
     * @generated
     * @ordered
     */
    protected static final String WIDGET_GRID_COLUMN_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getWidgetGridColumn() <em>Widget Grid Column</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getWidgetGridColumn()
     * @generated
     * @ordered
     */
    protected String widgetGridColumn = WIDGET_GRID_COLUMN_EDEFAULT;

    /**
     * The default value of the '{@link #getGap() <em>Gap</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getGap()
     * @generated
     * @ordered
     */
    protected static final String GAP_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getGap() <em>Gap</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getGap()
     * @generated
     * @ordered
     */
    protected String gap = GAP_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected WidgetGridLayoutImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return FormPackage.Literals.WIDGET_GRID_LAYOUT;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getGridTemplateColumns() {
        return this.gridTemplateColumns;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setGridTemplateColumns(String newGridTemplateColumns) {
        String oldGridTemplateColumns = this.gridTemplateColumns;
        this.gridTemplateColumns = newGridTemplateColumns;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.WIDGET_GRID_LAYOUT__GRID_TEMPLATE_COLUMNS, oldGridTemplateColumns, this.gridTemplateColumns));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getGridTemplateRows() {
        return this.gridTemplateRows;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setGridTemplateRows(String newGridTemplateRows) {
        String oldGridTemplateRows = this.gridTemplateRows;
        this.gridTemplateRows = newGridTemplateRows;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.WIDGET_GRID_LAYOUT__GRID_TEMPLATE_ROWS, oldGridTemplateRows, this.gridTemplateRows));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getLabelGridRow() {
        return this.labelGridRow;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setLabelGridRow(String newLabelGridRow) {
        String oldLabelGridRow = this.labelGridRow;
        this.labelGridRow = newLabelGridRow;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.WIDGET_GRID_LAYOUT__LABEL_GRID_ROW, oldLabelGridRow, this.labelGridRow));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getLabelGridColumn() {
        return this.labelGridColumn;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setLabelGridColumn(String newLabelGridColumn) {
        String oldLabelGridColumn = this.labelGridColumn;
        this.labelGridColumn = newLabelGridColumn;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.WIDGET_GRID_LAYOUT__LABEL_GRID_COLUMN, oldLabelGridColumn, this.labelGridColumn));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getWidgetGridRow() {
        return this.widgetGridRow;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setWidgetGridRow(String newWidgetGridRow) {
        String oldWidgetGridRow = this.widgetGridRow;
        this.widgetGridRow = newWidgetGridRow;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.WIDGET_GRID_LAYOUT__WIDGET_GRID_ROW, oldWidgetGridRow, this.widgetGridRow));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getWidgetGridColumn() {
        return this.widgetGridColumn;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setWidgetGridColumn(String newWidgetGridColumn) {
        String oldWidgetGridColumn = this.widgetGridColumn;
        this.widgetGridColumn = newWidgetGridColumn;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.WIDGET_GRID_LAYOUT__WIDGET_GRID_COLUMN, oldWidgetGridColumn, this.widgetGridColumn));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getGap() {
        return this.gap;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setGap(String newGap) {
        String oldGap = this.gap;
        this.gap = newGap;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.WIDGET_GRID_LAYOUT__GAP, oldGap, this.gap));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case FormPackage.WIDGET_GRID_LAYOUT__GRID_TEMPLATE_COLUMNS:
                return this.getGridTemplateColumns();
            case FormPackage.WIDGET_GRID_LAYOUT__GRID_TEMPLATE_ROWS:
                return this.getGridTemplateRows();
            case FormPackage.WIDGET_GRID_LAYOUT__LABEL_GRID_ROW:
                return this.getLabelGridRow();
            case FormPackage.WIDGET_GRID_LAYOUT__LABEL_GRID_COLUMN:
                return this.getLabelGridColumn();
            case FormPackage.WIDGET_GRID_LAYOUT__WIDGET_GRID_ROW:
                return this.getWidgetGridRow();
            case FormPackage.WIDGET_GRID_LAYOUT__WIDGET_GRID_COLUMN:
                return this.getWidgetGridColumn();
            case FormPackage.WIDGET_GRID_LAYOUT__GAP:
                return this.getGap();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case FormPackage.WIDGET_GRID_LAYOUT__GRID_TEMPLATE_COLUMNS:
                this.setGridTemplateColumns((String) newValue);
                return;
            case FormPackage.WIDGET_GRID_LAYOUT__GRID_TEMPLATE_ROWS:
                this.setGridTemplateRows((String) newValue);
                return;
            case FormPackage.WIDGET_GRID_LAYOUT__LABEL_GRID_ROW:
                this.setLabelGridRow((String) newValue);
                return;
            case FormPackage.WIDGET_GRID_LAYOUT__LABEL_GRID_COLUMN:
                this.setLabelGridColumn((String) newValue);
                return;
            case FormPackage.WIDGET_GRID_LAYOUT__WIDGET_GRID_ROW:
                this.setWidgetGridRow((String) newValue);
                return;
            case FormPackage.WIDGET_GRID_LAYOUT__WIDGET_GRID_COLUMN:
                this.setWidgetGridColumn((String) newValue);
                return;
            case FormPackage.WIDGET_GRID_LAYOUT__GAP:
                this.setGap((String) newValue);
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
            case FormPackage.WIDGET_GRID_LAYOUT__GRID_TEMPLATE_COLUMNS:
                this.setGridTemplateColumns(GRID_TEMPLATE_COLUMNS_EDEFAULT);
                return;
            case FormPackage.WIDGET_GRID_LAYOUT__GRID_TEMPLATE_ROWS:
                this.setGridTemplateRows(GRID_TEMPLATE_ROWS_EDEFAULT);
                return;
            case FormPackage.WIDGET_GRID_LAYOUT__LABEL_GRID_ROW:
                this.setLabelGridRow(LABEL_GRID_ROW_EDEFAULT);
                return;
            case FormPackage.WIDGET_GRID_LAYOUT__LABEL_GRID_COLUMN:
                this.setLabelGridColumn(LABEL_GRID_COLUMN_EDEFAULT);
                return;
            case FormPackage.WIDGET_GRID_LAYOUT__WIDGET_GRID_ROW:
                this.setWidgetGridRow(WIDGET_GRID_ROW_EDEFAULT);
                return;
            case FormPackage.WIDGET_GRID_LAYOUT__WIDGET_GRID_COLUMN:
                this.setWidgetGridColumn(WIDGET_GRID_COLUMN_EDEFAULT);
                return;
            case FormPackage.WIDGET_GRID_LAYOUT__GAP:
                this.setGap(GAP_EDEFAULT);
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
            case FormPackage.WIDGET_GRID_LAYOUT__GRID_TEMPLATE_COLUMNS:
                return GRID_TEMPLATE_COLUMNS_EDEFAULT == null ? this.gridTemplateColumns != null : !GRID_TEMPLATE_COLUMNS_EDEFAULT.equals(this.gridTemplateColumns);
            case FormPackage.WIDGET_GRID_LAYOUT__GRID_TEMPLATE_ROWS:
                return GRID_TEMPLATE_ROWS_EDEFAULT == null ? this.gridTemplateRows != null : !GRID_TEMPLATE_ROWS_EDEFAULT.equals(this.gridTemplateRows);
            case FormPackage.WIDGET_GRID_LAYOUT__LABEL_GRID_ROW:
                return LABEL_GRID_ROW_EDEFAULT == null ? this.labelGridRow != null : !LABEL_GRID_ROW_EDEFAULT.equals(this.labelGridRow);
            case FormPackage.WIDGET_GRID_LAYOUT__LABEL_GRID_COLUMN:
                return LABEL_GRID_COLUMN_EDEFAULT == null ? this.labelGridColumn != null : !LABEL_GRID_COLUMN_EDEFAULT.equals(this.labelGridColumn);
            case FormPackage.WIDGET_GRID_LAYOUT__WIDGET_GRID_ROW:
                return WIDGET_GRID_ROW_EDEFAULT == null ? this.widgetGridRow != null : !WIDGET_GRID_ROW_EDEFAULT.equals(this.widgetGridRow);
            case FormPackage.WIDGET_GRID_LAYOUT__WIDGET_GRID_COLUMN:
                return WIDGET_GRID_COLUMN_EDEFAULT == null ? this.widgetGridColumn != null : !WIDGET_GRID_COLUMN_EDEFAULT.equals(this.widgetGridColumn);
            case FormPackage.WIDGET_GRID_LAYOUT__GAP:
                return GAP_EDEFAULT == null ? this.gap != null : !GAP_EDEFAULT.equals(this.gap);
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

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (gridTemplateColumns: ");
        result.append(this.gridTemplateColumns);
        result.append(", gridTemplateRows: ");
        result.append(this.gridTemplateRows);
        result.append(", labelGridRow: ");
        result.append(this.labelGridRow);
        result.append(", labelGridColumn: ");
        result.append(this.labelGridColumn);
        result.append(", widgetGridRow: ");
        result.append(this.widgetGridRow);
        result.append(", widgetGridColumn: ");
        result.append(this.widgetGridColumn);
        result.append(", gap: ");
        result.append(this.gap);
        result.append(')');
        return result.toString();
    }

} // WidgetGridLayoutImpl
