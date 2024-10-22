/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle;
import org.eclipse.sirius.components.view.form.WidgetGridLayout;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Multi Select Description Style</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionStyleImpl#getFontSize <em>Font
 * Size</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionStyleImpl#isItalic
 * <em>Italic</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionStyleImpl#isBold <em>Bold</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionStyleImpl#isUnderline
 * <em>Underline</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionStyleImpl#isStrikeThrough <em>Strike
 * Through</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionStyleImpl#getGridTemplateColumns
 * <em>Grid Template Columns</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionStyleImpl#getGridTemplateRows <em>Grid
 * Template Rows</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionStyleImpl#getLabelGridRow <em>Label
 * Grid Row</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionStyleImpl#getLabelGridColumn <em>Label
 * Grid Column</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionStyleImpl#getWidgetGridRow <em>Widget
 * Grid Row</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionStyleImpl#getWidgetGridColumn
 * <em>Widget Grid Column</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionStyleImpl#getGap <em>Gap</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionStyleImpl#getBackgroundColor
 * <em>Background Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionStyleImpl#getForegroundColor
 * <em>Foreground Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionStyleImpl#isShowIcon <em>Show
 * Icon</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MultiSelectDescriptionStyleImpl extends WidgetDescriptionStyleImpl implements MultiSelectDescriptionStyle {
    /**
     * The default value of the '{@link #getFontSize() <em>Font Size</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getFontSize()
     * @generated
     * @ordered
     */
    protected static final int FONT_SIZE_EDEFAULT = 14;

    /**
     * The cached value of the '{@link #getFontSize() <em>Font Size</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getFontSize()
     * @generated
     * @ordered
     */
    protected int fontSize = FONT_SIZE_EDEFAULT;

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
     * The default value of the '{@link #isUnderline() <em>Underline</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isUnderline()
     * @generated
     * @ordered
     */
    protected static final boolean UNDERLINE_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isUnderline() <em>Underline</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isUnderline()
     * @generated
     * @ordered
     */
    protected boolean underline = UNDERLINE_EDEFAULT;

    /**
     * The default value of the '{@link #isStrikeThrough() <em>Strike Through</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #isStrikeThrough()
     * @generated
     * @ordered
     */
    protected static final boolean STRIKE_THROUGH_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isStrikeThrough() <em>Strike Through</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #isStrikeThrough()
     * @generated
     * @ordered
     */
    protected boolean strikeThrough = STRIKE_THROUGH_EDEFAULT;

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
     * The default value of the '{@link #isShowIcon() <em>Show Icon</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isShowIcon()
     * @generated
     * @ordered
     */
    protected static final boolean SHOW_ICON_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isShowIcon() <em>Show Icon</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isShowIcon()
     * @generated
     * @ordered
     */
    protected boolean showIcon = SHOW_ICON_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected MultiSelectDescriptionStyleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return FormPackage.Literals.MULTI_SELECT_DESCRIPTION_STYLE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int getFontSize() {
        return this.fontSize;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setFontSize(int newFontSize) {
        int oldFontSize = this.fontSize;
        this.fontSize = newFontSize;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__FONT_SIZE, oldFontSize, this.fontSize));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__ITALIC, oldItalic, this.italic));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__BOLD, oldBold, this.bold));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isUnderline() {
        return this.underline;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setUnderline(boolean newUnderline) {
        boolean oldUnderline = this.underline;
        this.underline = newUnderline;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__UNDERLINE, oldUnderline, this.underline));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isStrikeThrough() {
        return this.strikeThrough;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setStrikeThrough(boolean newStrikeThrough) {
        boolean oldStrikeThrough = this.strikeThrough;
        this.strikeThrough = newStrikeThrough;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__STRIKE_THROUGH, oldStrikeThrough, this.strikeThrough));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS, oldGridTemplateColumns, this.gridTemplateColumns));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS, oldGridTemplateRows, this.gridTemplateRows));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__LABEL_GRID_ROW, oldLabelGridRow, this.labelGridRow));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__LABEL_GRID_COLUMN, oldLabelGridColumn, this.labelGridColumn));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__WIDGET_GRID_ROW, oldWidgetGridRow, this.widgetGridRow));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN, oldWidgetGridColumn, this.widgetGridColumn));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__GAP, oldGap, this.gap));
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
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__BACKGROUND_COLOR, oldBackgroundColor, this.backgroundColor));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__BACKGROUND_COLOR, oldBackgroundColor, this.backgroundColor));
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
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__FOREGROUND_COLOR, oldForegroundColor, this.foregroundColor));
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__FOREGROUND_COLOR, oldForegroundColor, this.foregroundColor));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isShowIcon() {
        return this.showIcon;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setShowIcon(boolean newShowIcon) {
        boolean oldShowIcon = this.showIcon;
        this.showIcon = newShowIcon;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__SHOW_ICON, oldShowIcon, this.showIcon));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__FONT_SIZE:
                return this.getFontSize();
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__ITALIC:
                return this.isItalic();
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__BOLD:
                return this.isBold();
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__UNDERLINE:
                return this.isUnderline();
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__STRIKE_THROUGH:
                return this.isStrikeThrough();
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS:
                return this.getGridTemplateColumns();
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS:
                return this.getGridTemplateRows();
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__LABEL_GRID_ROW:
                return this.getLabelGridRow();
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__LABEL_GRID_COLUMN:
                return this.getLabelGridColumn();
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__WIDGET_GRID_ROW:
                return this.getWidgetGridRow();
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN:
                return this.getWidgetGridColumn();
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__GAP:
                return this.getGap();
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__BACKGROUND_COLOR:
                if (resolve)
                    return this.getBackgroundColor();
                return this.basicGetBackgroundColor();
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__FOREGROUND_COLOR:
                if (resolve)
                    return this.getForegroundColor();
                return this.basicGetForegroundColor();
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__SHOW_ICON:
                return this.isShowIcon();
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
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__FONT_SIZE:
                this.setFontSize((Integer) newValue);
                return;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__ITALIC:
                this.setItalic((Boolean) newValue);
                return;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__BOLD:
                this.setBold((Boolean) newValue);
                return;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__UNDERLINE:
                this.setUnderline((Boolean) newValue);
                return;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__STRIKE_THROUGH:
                this.setStrikeThrough((Boolean) newValue);
                return;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS:
                this.setGridTemplateColumns((String) newValue);
                return;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS:
                this.setGridTemplateRows((String) newValue);
                return;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__LABEL_GRID_ROW:
                this.setLabelGridRow((String) newValue);
                return;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__LABEL_GRID_COLUMN:
                this.setLabelGridColumn((String) newValue);
                return;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__WIDGET_GRID_ROW:
                this.setWidgetGridRow((String) newValue);
                return;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN:
                this.setWidgetGridColumn((String) newValue);
                return;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__GAP:
                this.setGap((String) newValue);
                return;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__BACKGROUND_COLOR:
                this.setBackgroundColor((UserColor) newValue);
                return;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__FOREGROUND_COLOR:
                this.setForegroundColor((UserColor) newValue);
                return;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__SHOW_ICON:
                this.setShowIcon((Boolean) newValue);
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
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__FONT_SIZE:
                this.setFontSize(FONT_SIZE_EDEFAULT);
                return;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__ITALIC:
                this.setItalic(ITALIC_EDEFAULT);
                return;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__BOLD:
                this.setBold(BOLD_EDEFAULT);
                return;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__UNDERLINE:
                this.setUnderline(UNDERLINE_EDEFAULT);
                return;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__STRIKE_THROUGH:
                this.setStrikeThrough(STRIKE_THROUGH_EDEFAULT);
                return;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS:
                this.setGridTemplateColumns(GRID_TEMPLATE_COLUMNS_EDEFAULT);
                return;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS:
                this.setGridTemplateRows(GRID_TEMPLATE_ROWS_EDEFAULT);
                return;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__LABEL_GRID_ROW:
                this.setLabelGridRow(LABEL_GRID_ROW_EDEFAULT);
                return;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__LABEL_GRID_COLUMN:
                this.setLabelGridColumn(LABEL_GRID_COLUMN_EDEFAULT);
                return;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__WIDGET_GRID_ROW:
                this.setWidgetGridRow(WIDGET_GRID_ROW_EDEFAULT);
                return;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN:
                this.setWidgetGridColumn(WIDGET_GRID_COLUMN_EDEFAULT);
                return;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__GAP:
                this.setGap(GAP_EDEFAULT);
                return;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__BACKGROUND_COLOR:
                this.setBackgroundColor((UserColor) null);
                return;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__FOREGROUND_COLOR:
                this.setForegroundColor((UserColor) null);
                return;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__SHOW_ICON:
                this.setShowIcon(SHOW_ICON_EDEFAULT);
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
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__FONT_SIZE:
                return this.fontSize != FONT_SIZE_EDEFAULT;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__ITALIC:
                return this.italic != ITALIC_EDEFAULT;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__BOLD:
                return this.bold != BOLD_EDEFAULT;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__UNDERLINE:
                return this.underline != UNDERLINE_EDEFAULT;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__STRIKE_THROUGH:
                return this.strikeThrough != STRIKE_THROUGH_EDEFAULT;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS:
                return GRID_TEMPLATE_COLUMNS_EDEFAULT == null ? this.gridTemplateColumns != null : !GRID_TEMPLATE_COLUMNS_EDEFAULT.equals(this.gridTemplateColumns);
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS:
                return GRID_TEMPLATE_ROWS_EDEFAULT == null ? this.gridTemplateRows != null : !GRID_TEMPLATE_ROWS_EDEFAULT.equals(this.gridTemplateRows);
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__LABEL_GRID_ROW:
                return LABEL_GRID_ROW_EDEFAULT == null ? this.labelGridRow != null : !LABEL_GRID_ROW_EDEFAULT.equals(this.labelGridRow);
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__LABEL_GRID_COLUMN:
                return LABEL_GRID_COLUMN_EDEFAULT == null ? this.labelGridColumn != null : !LABEL_GRID_COLUMN_EDEFAULT.equals(this.labelGridColumn);
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__WIDGET_GRID_ROW:
                return WIDGET_GRID_ROW_EDEFAULT == null ? this.widgetGridRow != null : !WIDGET_GRID_ROW_EDEFAULT.equals(this.widgetGridRow);
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN:
                return WIDGET_GRID_COLUMN_EDEFAULT == null ? this.widgetGridColumn != null : !WIDGET_GRID_COLUMN_EDEFAULT.equals(this.widgetGridColumn);
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__GAP:
                return GAP_EDEFAULT == null ? this.gap != null : !GAP_EDEFAULT.equals(this.gap);
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__BACKGROUND_COLOR:
                return this.backgroundColor != null;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__FOREGROUND_COLOR:
                return this.foregroundColor != null;
            case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__SHOW_ICON:
                return this.showIcon != SHOW_ICON_EDEFAULT;
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
        if (baseClass == LabelStyle.class) {
            switch (derivedFeatureID) {
                case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__FONT_SIZE:
                    return ViewPackage.LABEL_STYLE__FONT_SIZE;
                case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__ITALIC:
                    return ViewPackage.LABEL_STYLE__ITALIC;
                case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__BOLD:
                    return ViewPackage.LABEL_STYLE__BOLD;
                case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__UNDERLINE:
                    return ViewPackage.LABEL_STYLE__UNDERLINE;
                case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__STRIKE_THROUGH:
                    return ViewPackage.LABEL_STYLE__STRIKE_THROUGH;
                default:
                    return -1;
            }
        }
        if (baseClass == WidgetGridLayout.class) {
            switch (derivedFeatureID) {
                case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS:
                    return FormPackage.WIDGET_GRID_LAYOUT__GRID_TEMPLATE_COLUMNS;
                case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS:
                    return FormPackage.WIDGET_GRID_LAYOUT__GRID_TEMPLATE_ROWS;
                case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__LABEL_GRID_ROW:
                    return FormPackage.WIDGET_GRID_LAYOUT__LABEL_GRID_ROW;
                case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__LABEL_GRID_COLUMN:
                    return FormPackage.WIDGET_GRID_LAYOUT__LABEL_GRID_COLUMN;
                case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__WIDGET_GRID_ROW:
                    return FormPackage.WIDGET_GRID_LAYOUT__WIDGET_GRID_ROW;
                case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN:
                    return FormPackage.WIDGET_GRID_LAYOUT__WIDGET_GRID_COLUMN;
                case FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__GAP:
                    return FormPackage.WIDGET_GRID_LAYOUT__GAP;
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
        if (baseClass == LabelStyle.class) {
            switch (baseFeatureID) {
                case ViewPackage.LABEL_STYLE__FONT_SIZE:
                    return FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__FONT_SIZE;
                case ViewPackage.LABEL_STYLE__ITALIC:
                    return FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__ITALIC;
                case ViewPackage.LABEL_STYLE__BOLD:
                    return FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__BOLD;
                case ViewPackage.LABEL_STYLE__UNDERLINE:
                    return FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__UNDERLINE;
                case ViewPackage.LABEL_STYLE__STRIKE_THROUGH:
                    return FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__STRIKE_THROUGH;
                default:
                    return -1;
            }
        }
        if (baseClass == WidgetGridLayout.class) {
            switch (baseFeatureID) {
                case FormPackage.WIDGET_GRID_LAYOUT__GRID_TEMPLATE_COLUMNS:
                    return FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS;
                case FormPackage.WIDGET_GRID_LAYOUT__GRID_TEMPLATE_ROWS:
                    return FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS;
                case FormPackage.WIDGET_GRID_LAYOUT__LABEL_GRID_ROW:
                    return FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__LABEL_GRID_ROW;
                case FormPackage.WIDGET_GRID_LAYOUT__LABEL_GRID_COLUMN:
                    return FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__LABEL_GRID_COLUMN;
                case FormPackage.WIDGET_GRID_LAYOUT__WIDGET_GRID_ROW:
                    return FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__WIDGET_GRID_ROW;
                case FormPackage.WIDGET_GRID_LAYOUT__WIDGET_GRID_COLUMN:
                    return FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN;
                case FormPackage.WIDGET_GRID_LAYOUT__GAP:
                    return FormPackage.MULTI_SELECT_DESCRIPTION_STYLE__GAP;
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
        result.append(" (fontSize: ");
        result.append(this.fontSize);
        result.append(", italic: ");
        result.append(this.italic);
        result.append(", bold: ");
        result.append(this.bold);
        result.append(", underline: ");
        result.append(this.underline);
        result.append(", strikeThrough: ");
        result.append(this.strikeThrough);
        result.append(", gridTemplateColumns: ");
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
        result.append(", showIcon: ");
        result.append(this.showIcon);
        result.append(')');
        return result.toString();
    }

} // MultiSelectDescriptionStyleImpl
