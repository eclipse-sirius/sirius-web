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
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.form.ConditionalDateTimeDescriptionStyle;
import org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.WidgetDescriptionStyle;
import org.eclipse.sirius.components.view.form.WidgetGridLayout;
import org.eclipse.sirius.components.view.impl.ConditionalImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Conditional Date Time Description
 * Style</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalDateTimeDescriptionStyleImpl#getGridTemplateColumns
 * <em>Grid Template Columns</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalDateTimeDescriptionStyleImpl#getGridTemplateRows
 * <em>Grid Template Rows</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalDateTimeDescriptionStyleImpl#getLabelGridRow
 * <em>Label Grid Row</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalDateTimeDescriptionStyleImpl#getLabelGridColumn
 * <em>Label Grid Column</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalDateTimeDescriptionStyleImpl#getWidgetGridRow
 * <em>Widget Grid Row</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalDateTimeDescriptionStyleImpl#getWidgetGridColumn
 * <em>Widget Grid Column</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalDateTimeDescriptionStyleImpl#getGap
 * <em>Gap</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalDateTimeDescriptionStyleImpl#getBackgroundColor
 * <em>Background Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalDateTimeDescriptionStyleImpl#getForegroundColor
 * <em>Foreground Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalDateTimeDescriptionStyleImpl#isItalic
 * <em>Italic</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalDateTimeDescriptionStyleImpl#isBold
 * <em>Bold</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConditionalDateTimeDescriptionStyleImpl extends ConditionalImpl implements ConditionalDateTimeDescriptionStyle {
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
     * The cached value of the '{@link #getBackgroundColor() <em>Background Color</em>}' reference. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getBackgroundColor()
     * @generated
     * @ordered
     */
    protected UserColor backgroundColor;

    /**
     * The cached value of the '{@link #getForegroundColor() <em>Foreground Color</em>}' reference. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getForegroundColor()
     * @generated
     * @ordered
     */
    protected UserColor foregroundColor;

    /**
     * The default value of the '{@link #isItalic() <em>Italic</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isItalic()
     * @generated
     * @ordered
     */
    protected static final boolean ITALIC_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isItalic() <em>Italic</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isItalic()
     * @generated
     * @ordered
     */
    protected boolean italic = ITALIC_EDEFAULT;

    /**
     * The default value of the '{@link #isBold() <em>Bold</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #isBold()
     * @generated
     * @ordered
     */
    protected static final boolean BOLD_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isBold() <em>Bold</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #isBold()
     * @generated
     * @ordered
     */
    protected boolean bold = BOLD_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ConditionalDateTimeDescriptionStyleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return FormPackage.Literals.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE;
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS, oldGridTemplateColumns, this.gridTemplateColumns));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS, oldGridTemplateRows, this.gridTemplateRows));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__LABEL_GRID_ROW, oldLabelGridRow, this.labelGridRow));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__LABEL_GRID_COLUMN, oldLabelGridColumn, this.labelGridColumn));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__WIDGET_GRID_ROW, oldWidgetGridRow, this.widgetGridRow));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN, oldWidgetGridColumn, this.widgetGridColumn));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__GAP, oldGap, this.gap));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public UserColor getBackgroundColor() {
        if (this.backgroundColor != null && this.backgroundColor.eIsProxy()) {
            InternalEObject oldBackgroundColor = (InternalEObject) this.backgroundColor;
            this.backgroundColor = (UserColor) this.eResolveProxy(oldBackgroundColor);
            if (this.backgroundColor != oldBackgroundColor) {
                if (this.eNotificationRequired())
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BACKGROUND_COLOR, oldBackgroundColor, this.backgroundColor));
            }
        }
        return this.backgroundColor;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public UserColor basicGetBackgroundColor() {
        return this.backgroundColor;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBackgroundColor(UserColor newBackgroundColor) {
        UserColor oldBackgroundColor = this.backgroundColor;
        this.backgroundColor = newBackgroundColor;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BACKGROUND_COLOR, oldBackgroundColor, this.backgroundColor));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public UserColor getForegroundColor() {
        if (this.foregroundColor != null && this.foregroundColor.eIsProxy()) {
            InternalEObject oldForegroundColor = (InternalEObject) this.foregroundColor;
            this.foregroundColor = (UserColor) this.eResolveProxy(oldForegroundColor);
            if (this.foregroundColor != oldForegroundColor) {
                if (this.eNotificationRequired())
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__FOREGROUND_COLOR, oldForegroundColor, this.foregroundColor));
            }
        }
        return this.foregroundColor;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public UserColor basicGetForegroundColor() {
        return this.foregroundColor;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setForegroundColor(UserColor newForegroundColor) {
        UserColor oldForegroundColor = this.foregroundColor;
        this.foregroundColor = newForegroundColor;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__FOREGROUND_COLOR, oldForegroundColor, this.foregroundColor));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isItalic() {
        return this.italic;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setItalic(boolean newItalic) {
        boolean oldItalic = this.italic;
        this.italic = newItalic;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__ITALIC, oldItalic, this.italic));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isBold() {
        return this.bold;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBold(boolean newBold) {
        boolean oldBold = this.bold;
        this.bold = newBold;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BOLD, oldBold, this.bold));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS:
                return this.getGridTemplateColumns();
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS:
                return this.getGridTemplateRows();
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__LABEL_GRID_ROW:
                return this.getLabelGridRow();
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__LABEL_GRID_COLUMN:
                return this.getLabelGridColumn();
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__WIDGET_GRID_ROW:
                return this.getWidgetGridRow();
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN:
                return this.getWidgetGridColumn();
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__GAP:
                return this.getGap();
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BACKGROUND_COLOR:
                if (resolve)
                    return this.getBackgroundColor();
                return this.basicGetBackgroundColor();
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__FOREGROUND_COLOR:
                if (resolve)
                    return this.getForegroundColor();
                return this.basicGetForegroundColor();
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__ITALIC:
                return this.isItalic();
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BOLD:
                return this.isBold();
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
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS:
                this.setGridTemplateColumns((String) newValue);
                return;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS:
                this.setGridTemplateRows((String) newValue);
                return;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__LABEL_GRID_ROW:
                this.setLabelGridRow((String) newValue);
                return;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__LABEL_GRID_COLUMN:
                this.setLabelGridColumn((String) newValue);
                return;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__WIDGET_GRID_ROW:
                this.setWidgetGridRow((String) newValue);
                return;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN:
                this.setWidgetGridColumn((String) newValue);
                return;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__GAP:
                this.setGap((String) newValue);
                return;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BACKGROUND_COLOR:
                this.setBackgroundColor((UserColor) newValue);
                return;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__FOREGROUND_COLOR:
                this.setForegroundColor((UserColor) newValue);
                return;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__ITALIC:
                this.setItalic((Boolean) newValue);
                return;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BOLD:
                this.setBold((Boolean) newValue);
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
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS:
                this.setGridTemplateColumns(GRID_TEMPLATE_COLUMNS_EDEFAULT);
                return;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS:
                this.setGridTemplateRows(GRID_TEMPLATE_ROWS_EDEFAULT);
                return;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__LABEL_GRID_ROW:
                this.setLabelGridRow(LABEL_GRID_ROW_EDEFAULT);
                return;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__LABEL_GRID_COLUMN:
                this.setLabelGridColumn(LABEL_GRID_COLUMN_EDEFAULT);
                return;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__WIDGET_GRID_ROW:
                this.setWidgetGridRow(WIDGET_GRID_ROW_EDEFAULT);
                return;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN:
                this.setWidgetGridColumn(WIDGET_GRID_COLUMN_EDEFAULT);
                return;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__GAP:
                this.setGap(GAP_EDEFAULT);
                return;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BACKGROUND_COLOR:
                this.setBackgroundColor((UserColor) null);
                return;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__FOREGROUND_COLOR:
                this.setForegroundColor((UserColor) null);
                return;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__ITALIC:
                this.setItalic(ITALIC_EDEFAULT);
                return;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BOLD:
                this.setBold(BOLD_EDEFAULT);
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
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS:
                return GRID_TEMPLATE_COLUMNS_EDEFAULT == null ? this.gridTemplateColumns != null : !GRID_TEMPLATE_COLUMNS_EDEFAULT.equals(this.gridTemplateColumns);
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS:
                return GRID_TEMPLATE_ROWS_EDEFAULT == null ? this.gridTemplateRows != null : !GRID_TEMPLATE_ROWS_EDEFAULT.equals(this.gridTemplateRows);
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__LABEL_GRID_ROW:
                return LABEL_GRID_ROW_EDEFAULT == null ? this.labelGridRow != null : !LABEL_GRID_ROW_EDEFAULT.equals(this.labelGridRow);
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__LABEL_GRID_COLUMN:
                return LABEL_GRID_COLUMN_EDEFAULT == null ? this.labelGridColumn != null : !LABEL_GRID_COLUMN_EDEFAULT.equals(this.labelGridColumn);
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__WIDGET_GRID_ROW:
                return WIDGET_GRID_ROW_EDEFAULT == null ? this.widgetGridRow != null : !WIDGET_GRID_ROW_EDEFAULT.equals(this.widgetGridRow);
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN:
                return WIDGET_GRID_COLUMN_EDEFAULT == null ? this.widgetGridColumn != null : !WIDGET_GRID_COLUMN_EDEFAULT.equals(this.widgetGridColumn);
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__GAP:
                return GAP_EDEFAULT == null ? this.gap != null : !GAP_EDEFAULT.equals(this.gap);
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BACKGROUND_COLOR:
                return this.backgroundColor != null;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__FOREGROUND_COLOR:
                return this.foregroundColor != null;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__ITALIC:
                return this.italic != ITALIC_EDEFAULT;
            case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BOLD:
                return this.bold != BOLD_EDEFAULT;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == WidgetDescriptionStyle.class) {
            switch (derivedFeatureID) {
                default:
                    return -1;
            }
        }
        if (baseClass == WidgetGridLayout.class) {
            switch (derivedFeatureID) {
                case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS:
                    return FormPackage.WIDGET_GRID_LAYOUT__GRID_TEMPLATE_COLUMNS;
                case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS:
                    return FormPackage.WIDGET_GRID_LAYOUT__GRID_TEMPLATE_ROWS;
                case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__LABEL_GRID_ROW:
                    return FormPackage.WIDGET_GRID_LAYOUT__LABEL_GRID_ROW;
                case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__LABEL_GRID_COLUMN:
                    return FormPackage.WIDGET_GRID_LAYOUT__LABEL_GRID_COLUMN;
                case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__WIDGET_GRID_ROW:
                    return FormPackage.WIDGET_GRID_LAYOUT__WIDGET_GRID_ROW;
                case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN:
                    return FormPackage.WIDGET_GRID_LAYOUT__WIDGET_GRID_COLUMN;
                case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__GAP:
                    return FormPackage.WIDGET_GRID_LAYOUT__GAP;
                default:
                    return -1;
            }
        }
        if (baseClass == DateTimeDescriptionStyle.class) {
            switch (derivedFeatureID) {
                case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BACKGROUND_COLOR:
                    return FormPackage.DATE_TIME_DESCRIPTION_STYLE__BACKGROUND_COLOR;
                case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__FOREGROUND_COLOR:
                    return FormPackage.DATE_TIME_DESCRIPTION_STYLE__FOREGROUND_COLOR;
                case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__ITALIC:
                    return FormPackage.DATE_TIME_DESCRIPTION_STYLE__ITALIC;
                case FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BOLD:
                    return FormPackage.DATE_TIME_DESCRIPTION_STYLE__BOLD;
                default:
                    return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
        if (baseClass == WidgetDescriptionStyle.class) {
            switch (baseFeatureID) {
                default:
                    return -1;
            }
        }
        if (baseClass == WidgetGridLayout.class) {
            switch (baseFeatureID) {
                case FormPackage.WIDGET_GRID_LAYOUT__GRID_TEMPLATE_COLUMNS:
                    return FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS;
                case FormPackage.WIDGET_GRID_LAYOUT__GRID_TEMPLATE_ROWS:
                    return FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS;
                case FormPackage.WIDGET_GRID_LAYOUT__LABEL_GRID_ROW:
                    return FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__LABEL_GRID_ROW;
                case FormPackage.WIDGET_GRID_LAYOUT__LABEL_GRID_COLUMN:
                    return FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__LABEL_GRID_COLUMN;
                case FormPackage.WIDGET_GRID_LAYOUT__WIDGET_GRID_ROW:
                    return FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__WIDGET_GRID_ROW;
                case FormPackage.WIDGET_GRID_LAYOUT__WIDGET_GRID_COLUMN:
                    return FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN;
                case FormPackage.WIDGET_GRID_LAYOUT__GAP:
                    return FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__GAP;
                default:
                    return -1;
            }
        }
        if (baseClass == DateTimeDescriptionStyle.class) {
            switch (baseFeatureID) {
                case FormPackage.DATE_TIME_DESCRIPTION_STYLE__BACKGROUND_COLOR:
                    return FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BACKGROUND_COLOR;
                case FormPackage.DATE_TIME_DESCRIPTION_STYLE__FOREGROUND_COLOR:
                    return FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__FOREGROUND_COLOR;
                case FormPackage.DATE_TIME_DESCRIPTION_STYLE__ITALIC:
                    return FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__ITALIC;
                case FormPackage.DATE_TIME_DESCRIPTION_STYLE__BOLD:
                    return FormPackage.CONDITIONAL_DATE_TIME_DESCRIPTION_STYLE__BOLD;
                default:
                    return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
        result.append(", italic: ");
        result.append(this.italic);
        result.append(", bold: ");
        result.append(this.bold);
        result.append(')');
        return result.toString();
    }

} // ConditionalDateTimeDescriptionStyleImpl
