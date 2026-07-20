/*******************************************************************************
 * Copyright (c) 2021, 2026 Obeo.
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
import org.eclipse.sirius.components.view.form.ConditionalTextareaDescriptionStyle;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.TextareaDescriptionStyle;
import org.eclipse.sirius.components.view.form.WidgetDescriptionStyle;
import org.eclipse.sirius.components.view.form.WidgetGridLayout;
import org.eclipse.sirius.components.view.impl.ConditionalImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Conditional Textarea Description
 * Style</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalTextareaDescriptionStyleImpl#getFontSize <em>Font Size</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalTextareaDescriptionStyleImpl#isItalic <em>Italic</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalTextareaDescriptionStyleImpl#isBold <em>Bold</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalTextareaDescriptionStyleImpl#isUnderline <em>Underline</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalTextareaDescriptionStyleImpl#isStrikeThrough <em>Strike Through</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalTextareaDescriptionStyleImpl#getGridTemplateColumns <em>Grid Template Columns</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalTextareaDescriptionStyleImpl#getGridTemplateRows <em>Grid Template Rows</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalTextareaDescriptionStyleImpl#getLabelGridRow <em>Label Grid Row</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalTextareaDescriptionStyleImpl#getLabelGridColumn <em>Label Grid Column</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalTextareaDescriptionStyleImpl#getWidgetGridRow <em>Widget Grid Row</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalTextareaDescriptionStyleImpl#getWidgetGridColumn <em>Widget Grid Column</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalTextareaDescriptionStyleImpl#getGap <em>Gap</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalTextareaDescriptionStyleImpl#getBackgroundColor <em>Background Color</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalTextareaDescriptionStyleImpl#getForegroundColor <em>Foreground Color</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConditionalTextareaDescriptionStyleImpl extends ConditionalImpl implements ConditionalTextareaDescriptionStyle {
    /**
	 * The default value of the '{@link #getFontSize() <em>Font Size</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getFontSize()
	 * @generated
	 * @ordered
	 */
    protected static final int FONT_SIZE_EDEFAULT = 14;

    /**
	 * The cached value of the '{@link #getFontSize() <em>Font Size</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getFontSize()
	 * @generated
	 * @ordered
	 */
    protected int fontSize = FONT_SIZE_EDEFAULT;

    /**
	 * The default value of the '{@link #isItalic() <em>Italic</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #isItalic()
	 * @generated
	 * @ordered
	 */
    protected static final boolean ITALIC_EDEFAULT = false;

    /**
	 * The cached value of the '{@link #isItalic() <em>Italic</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
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
	 * The default value of the '{@link #isUnderline() <em>Underline</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #isUnderline()
	 * @generated
	 * @ordered
	 */
    protected static final boolean UNDERLINE_EDEFAULT = false;

    /**
	 * The cached value of the '{@link #isUnderline() <em>Underline</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #isUnderline()
	 * @generated
	 * @ordered
	 */
    protected boolean underline = UNDERLINE_EDEFAULT;

    /**
	 * The default value of the '{@link #isStrikeThrough() <em>Strike Through</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isStrikeThrough()
	 * @generated
	 * @ordered
	 */
    protected static final boolean STRIKE_THROUGH_EDEFAULT = false;

    /**
	 * The cached value of the '{@link #isStrikeThrough() <em>Strike Through</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
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
	 * The default value of the '{@link #getLabelGridRow() <em>Label Grid Row</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getLabelGridRow()
	 * @generated
	 * @ordered
	 */
    protected static final String LABEL_GRID_ROW_EDEFAULT = "";

    /**
	 * The cached value of the '{@link #getLabelGridRow() <em>Label Grid Row</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
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
	 * The cached value of the '{@link #getLabelGridColumn() <em>Label Grid Column</em>}' attribute.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getLabelGridColumn()
	 * @generated
	 * @ordered
	 */
    protected String labelGridColumn = LABEL_GRID_COLUMN_EDEFAULT;

    /**
	 * The default value of the '{@link #getWidgetGridRow() <em>Widget Grid Row</em>}' attribute.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getWidgetGridRow()
	 * @generated
	 * @ordered
	 */
    protected static final String WIDGET_GRID_ROW_EDEFAULT = "";

    /**
	 * The cached value of the '{@link #getWidgetGridRow() <em>Widget Grid Row</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
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
	 * The cached value of the '{@link #getGap() <em>Gap</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getGap()
	 * @generated
	 * @ordered
	 */
    protected String gap = GAP_EDEFAULT;

    /**
	 * The cached value of the '{@link #getBackgroundColor() <em>Background Color</em>}' reference.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getBackgroundColor()
	 * @generated
	 * @ordered
	 */
    protected UserColor backgroundColor;

    /**
	 * The cached value of the '{@link #getForegroundColor() <em>Foreground Color</em>}' reference.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getForegroundColor()
	 * @generated
	 * @ordered
	 */
    protected UserColor foregroundColor;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected ConditionalTextareaDescriptionStyleImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return FormPackage.Literals.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public int getFontSize() {
		return fontSize;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setFontSize(int newFontSize) {
		int oldFontSize = fontSize;
		fontSize = newFontSize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__FONT_SIZE, oldFontSize, fontSize));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean isItalic() {
		return italic;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setItalic(boolean newItalic) {
		boolean oldItalic = italic;
		italic = newItalic;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__ITALIC, oldItalic, italic));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean isBold() {
		return bold;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setBold(boolean newBold) {
		boolean oldBold = bold;
		bold = newBold;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__BOLD, oldBold, bold));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean isUnderline() {
		return underline;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setUnderline(boolean newUnderline) {
		boolean oldUnderline = underline;
		underline = newUnderline;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__UNDERLINE, oldUnderline, underline));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean isStrikeThrough() {
		return strikeThrough;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setStrikeThrough(boolean newStrikeThrough) {
		boolean oldStrikeThrough = strikeThrough;
		strikeThrough = newStrikeThrough;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__STRIKE_THROUGH, oldStrikeThrough, strikeThrough));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getGridTemplateColumns() {
		return gridTemplateColumns;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setGridTemplateColumns(String newGridTemplateColumns) {
		String oldGridTemplateColumns = gridTemplateColumns;
		gridTemplateColumns = newGridTemplateColumns;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS, oldGridTemplateColumns, gridTemplateColumns));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getGridTemplateRows() {
		return gridTemplateRows;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setGridTemplateRows(String newGridTemplateRows) {
		String oldGridTemplateRows = gridTemplateRows;
		gridTemplateRows = newGridTemplateRows;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS, oldGridTemplateRows, gridTemplateRows));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getLabelGridRow() {
		return labelGridRow;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setLabelGridRow(String newLabelGridRow) {
		String oldLabelGridRow = labelGridRow;
		labelGridRow = newLabelGridRow;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__LABEL_GRID_ROW, oldLabelGridRow, labelGridRow));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getLabelGridColumn() {
		return labelGridColumn;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setLabelGridColumn(String newLabelGridColumn) {
		String oldLabelGridColumn = labelGridColumn;
		labelGridColumn = newLabelGridColumn;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__LABEL_GRID_COLUMN, oldLabelGridColumn, labelGridColumn));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getWidgetGridRow() {
		return widgetGridRow;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setWidgetGridRow(String newWidgetGridRow) {
		String oldWidgetGridRow = widgetGridRow;
		widgetGridRow = newWidgetGridRow;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__WIDGET_GRID_ROW, oldWidgetGridRow, widgetGridRow));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getWidgetGridColumn() {
		return widgetGridColumn;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setWidgetGridColumn(String newWidgetGridColumn) {
		String oldWidgetGridColumn = widgetGridColumn;
		widgetGridColumn = newWidgetGridColumn;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN, oldWidgetGridColumn, widgetGridColumn));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getGap() {
		return gap;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setGap(String newGap) {
		String oldGap = gap;
		gap = newGap;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__GAP, oldGap, gap));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public UserColor getBackgroundColor() {
		if (backgroundColor != null && backgroundColor.eIsProxy())
		{
			InternalEObject oldBackgroundColor = (InternalEObject)backgroundColor;
			backgroundColor = (UserColor)eResolveProxy(oldBackgroundColor);
			if (backgroundColor != oldBackgroundColor)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__BACKGROUND_COLOR, oldBackgroundColor, backgroundColor));
			}
		}
		return backgroundColor;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public UserColor basicGetBackgroundColor() {
		return backgroundColor;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setBackgroundColor(UserColor newBackgroundColor) {
		UserColor oldBackgroundColor = backgroundColor;
		backgroundColor = newBackgroundColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__BACKGROUND_COLOR, oldBackgroundColor, backgroundColor));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public UserColor getForegroundColor() {
		if (foregroundColor != null && foregroundColor.eIsProxy())
		{
			InternalEObject oldForegroundColor = (InternalEObject)foregroundColor;
			foregroundColor = (UserColor)eResolveProxy(oldForegroundColor);
			if (foregroundColor != oldForegroundColor)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__FOREGROUND_COLOR, oldForegroundColor, foregroundColor));
			}
		}
		return foregroundColor;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public UserColor basicGetForegroundColor() {
		return foregroundColor;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setForegroundColor(UserColor newForegroundColor) {
		UserColor oldForegroundColor = foregroundColor;
		foregroundColor = newForegroundColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__FOREGROUND_COLOR, oldForegroundColor, foregroundColor));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__FONT_SIZE:
				return getFontSize();
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__ITALIC:
				return isItalic();
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__BOLD:
				return isBold();
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__UNDERLINE:
				return isUnderline();
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__STRIKE_THROUGH:
				return isStrikeThrough();
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS:
				return getGridTemplateColumns();
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS:
				return getGridTemplateRows();
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__LABEL_GRID_ROW:
				return getLabelGridRow();
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__LABEL_GRID_COLUMN:
				return getLabelGridColumn();
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__WIDGET_GRID_ROW:
				return getWidgetGridRow();
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN:
				return getWidgetGridColumn();
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__GAP:
				return getGap();
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__BACKGROUND_COLOR:
				if (resolve) return getBackgroundColor();
				return basicGetBackgroundColor();
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__FOREGROUND_COLOR:
				if (resolve) return getForegroundColor();
				return basicGetForegroundColor();
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
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__FONT_SIZE:
				setFontSize((Integer)newValue);
				return;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__ITALIC:
				setItalic((Boolean)newValue);
				return;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__BOLD:
				setBold((Boolean)newValue);
				return;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__UNDERLINE:
				setUnderline((Boolean)newValue);
				return;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__STRIKE_THROUGH:
				setStrikeThrough((Boolean)newValue);
				return;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS:
				setGridTemplateColumns((String)newValue);
				return;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS:
				setGridTemplateRows((String)newValue);
				return;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__LABEL_GRID_ROW:
				setLabelGridRow((String)newValue);
				return;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__LABEL_GRID_COLUMN:
				setLabelGridColumn((String)newValue);
				return;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__WIDGET_GRID_ROW:
				setWidgetGridRow((String)newValue);
				return;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN:
				setWidgetGridColumn((String)newValue);
				return;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__GAP:
				setGap((String)newValue);
				return;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__BACKGROUND_COLOR:
				setBackgroundColor((UserColor)newValue);
				return;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__FOREGROUND_COLOR:
				setForegroundColor((UserColor)newValue);
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
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__FONT_SIZE:
				setFontSize(FONT_SIZE_EDEFAULT);
				return;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__ITALIC:
				setItalic(ITALIC_EDEFAULT);
				return;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__BOLD:
				setBold(BOLD_EDEFAULT);
				return;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__UNDERLINE:
				setUnderline(UNDERLINE_EDEFAULT);
				return;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__STRIKE_THROUGH:
				setStrikeThrough(STRIKE_THROUGH_EDEFAULT);
				return;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS:
				setGridTemplateColumns(GRID_TEMPLATE_COLUMNS_EDEFAULT);
				return;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS:
				setGridTemplateRows(GRID_TEMPLATE_ROWS_EDEFAULT);
				return;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__LABEL_GRID_ROW:
				setLabelGridRow(LABEL_GRID_ROW_EDEFAULT);
				return;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__LABEL_GRID_COLUMN:
				setLabelGridColumn(LABEL_GRID_COLUMN_EDEFAULT);
				return;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__WIDGET_GRID_ROW:
				setWidgetGridRow(WIDGET_GRID_ROW_EDEFAULT);
				return;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN:
				setWidgetGridColumn(WIDGET_GRID_COLUMN_EDEFAULT);
				return;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__GAP:
				setGap(GAP_EDEFAULT);
				return;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__BACKGROUND_COLOR:
				setBackgroundColor((UserColor)null);
				return;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__FOREGROUND_COLOR:
				setForegroundColor((UserColor)null);
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
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__FONT_SIZE:
				return fontSize != FONT_SIZE_EDEFAULT;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__ITALIC:
				return italic != ITALIC_EDEFAULT;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__BOLD:
				return bold != BOLD_EDEFAULT;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__UNDERLINE:
				return underline != UNDERLINE_EDEFAULT;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__STRIKE_THROUGH:
				return strikeThrough != STRIKE_THROUGH_EDEFAULT;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS:
				return GRID_TEMPLATE_COLUMNS_EDEFAULT == null ? gridTemplateColumns != null : !GRID_TEMPLATE_COLUMNS_EDEFAULT.equals(gridTemplateColumns);
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS:
				return GRID_TEMPLATE_ROWS_EDEFAULT == null ? gridTemplateRows != null : !GRID_TEMPLATE_ROWS_EDEFAULT.equals(gridTemplateRows);
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__LABEL_GRID_ROW:
				return LABEL_GRID_ROW_EDEFAULT == null ? labelGridRow != null : !LABEL_GRID_ROW_EDEFAULT.equals(labelGridRow);
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__LABEL_GRID_COLUMN:
				return LABEL_GRID_COLUMN_EDEFAULT == null ? labelGridColumn != null : !LABEL_GRID_COLUMN_EDEFAULT.equals(labelGridColumn);
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__WIDGET_GRID_ROW:
				return WIDGET_GRID_ROW_EDEFAULT == null ? widgetGridRow != null : !WIDGET_GRID_ROW_EDEFAULT.equals(widgetGridRow);
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN:
				return WIDGET_GRID_COLUMN_EDEFAULT == null ? widgetGridColumn != null : !WIDGET_GRID_COLUMN_EDEFAULT.equals(widgetGridColumn);
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__GAP:
				return GAP_EDEFAULT == null ? gap != null : !GAP_EDEFAULT.equals(gap);
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__BACKGROUND_COLOR:
				return backgroundColor != null;
			case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__FOREGROUND_COLOR:
				return foregroundColor != null;
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == WidgetDescriptionStyle.class)
		{
			switch (derivedFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == LabelStyle.class)
		{
			switch (derivedFeatureID)
			{
				case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__FONT_SIZE: return ViewPackage.LABEL_STYLE__FONT_SIZE;
				case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__ITALIC: return ViewPackage.LABEL_STYLE__ITALIC;
				case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__BOLD: return ViewPackage.LABEL_STYLE__BOLD;
				case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__UNDERLINE: return ViewPackage.LABEL_STYLE__UNDERLINE;
				case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__STRIKE_THROUGH: return ViewPackage.LABEL_STYLE__STRIKE_THROUGH;
				default: return -1;
			}
		}
		if (baseClass == WidgetGridLayout.class)
		{
			switch (derivedFeatureID)
			{
				case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS: return FormPackage.WIDGET_GRID_LAYOUT__GRID_TEMPLATE_COLUMNS;
				case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS: return FormPackage.WIDGET_GRID_LAYOUT__GRID_TEMPLATE_ROWS;
				case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__LABEL_GRID_ROW: return FormPackage.WIDGET_GRID_LAYOUT__LABEL_GRID_ROW;
				case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__LABEL_GRID_COLUMN: return FormPackage.WIDGET_GRID_LAYOUT__LABEL_GRID_COLUMN;
				case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__WIDGET_GRID_ROW: return FormPackage.WIDGET_GRID_LAYOUT__WIDGET_GRID_ROW;
				case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN: return FormPackage.WIDGET_GRID_LAYOUT__WIDGET_GRID_COLUMN;
				case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__GAP: return FormPackage.WIDGET_GRID_LAYOUT__GAP;
				default: return -1;
			}
		}
		if (baseClass == TextareaDescriptionStyle.class)
		{
			switch (derivedFeatureID)
			{
				case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__BACKGROUND_COLOR: return FormPackage.TEXTAREA_DESCRIPTION_STYLE__BACKGROUND_COLOR;
				case FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__FOREGROUND_COLOR: return FormPackage.TEXTAREA_DESCRIPTION_STYLE__FOREGROUND_COLOR;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == WidgetDescriptionStyle.class)
		{
			switch (baseFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == LabelStyle.class)
		{
			switch (baseFeatureID)
			{
				case ViewPackage.LABEL_STYLE__FONT_SIZE: return FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__FONT_SIZE;
				case ViewPackage.LABEL_STYLE__ITALIC: return FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__ITALIC;
				case ViewPackage.LABEL_STYLE__BOLD: return FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__BOLD;
				case ViewPackage.LABEL_STYLE__UNDERLINE: return FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__UNDERLINE;
				case ViewPackage.LABEL_STYLE__STRIKE_THROUGH: return FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__STRIKE_THROUGH;
				default: return -1;
			}
		}
		if (baseClass == WidgetGridLayout.class)
		{
			switch (baseFeatureID)
			{
				case FormPackage.WIDGET_GRID_LAYOUT__GRID_TEMPLATE_COLUMNS: return FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS;
				case FormPackage.WIDGET_GRID_LAYOUT__GRID_TEMPLATE_ROWS: return FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS;
				case FormPackage.WIDGET_GRID_LAYOUT__LABEL_GRID_ROW: return FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__LABEL_GRID_ROW;
				case FormPackage.WIDGET_GRID_LAYOUT__LABEL_GRID_COLUMN: return FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__LABEL_GRID_COLUMN;
				case FormPackage.WIDGET_GRID_LAYOUT__WIDGET_GRID_ROW: return FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__WIDGET_GRID_ROW;
				case FormPackage.WIDGET_GRID_LAYOUT__WIDGET_GRID_COLUMN: return FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN;
				case FormPackage.WIDGET_GRID_LAYOUT__GAP: return FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__GAP;
				default: return -1;
			}
		}
		if (baseClass == TextareaDescriptionStyle.class)
		{
			switch (baseFeatureID)
			{
				case FormPackage.TEXTAREA_DESCRIPTION_STYLE__BACKGROUND_COLOR: return FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__BACKGROUND_COLOR;
				case FormPackage.TEXTAREA_DESCRIPTION_STYLE__FOREGROUND_COLOR: return FormPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE__FOREGROUND_COLOR;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (fontSize: ");
		result.append(fontSize);
		result.append(", italic: ");
		result.append(italic);
		result.append(", bold: ");
		result.append(bold);
		result.append(", underline: ");
		result.append(underline);
		result.append(", strikeThrough: ");
		result.append(strikeThrough);
		result.append(", gridTemplateColumns: ");
		result.append(gridTemplateColumns);
		result.append(", gridTemplateRows: ");
		result.append(gridTemplateRows);
		result.append(", labelGridRow: ");
		result.append(labelGridRow);
		result.append(", labelGridColumn: ");
		result.append(labelGridColumn);
		result.append(", widgetGridRow: ");
		result.append(widgetGridRow);
		result.append(", widgetGridColumn: ");
		result.append(widgetGridColumn);
		result.append(", gap: ");
		result.append(gap);
		result.append(')');
		return result.toString();
	}

} // ConditionalTextareaDescriptionStyleImpl
