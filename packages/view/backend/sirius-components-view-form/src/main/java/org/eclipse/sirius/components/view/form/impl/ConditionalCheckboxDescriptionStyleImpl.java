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
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.form.CheckboxDescriptionStyle;
import org.eclipse.sirius.components.view.form.ConditionalCheckboxDescriptionStyle;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.WidgetDescriptionStyle;
import org.eclipse.sirius.components.view.form.WidgetGridLayout;
import org.eclipse.sirius.components.view.impl.ConditionalImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Conditional Checkbox Description
 * Style</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalCheckboxDescriptionStyleImpl#getGridTemplateColumns <em>Grid Template Columns</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalCheckboxDescriptionStyleImpl#getGridTemplateRows <em>Grid Template Rows</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalCheckboxDescriptionStyleImpl#getLabelGridRow <em>Label Grid Row</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalCheckboxDescriptionStyleImpl#getLabelGridColumn <em>Label Grid Column</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalCheckboxDescriptionStyleImpl#getWidgetGridRow <em>Widget Grid Row</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalCheckboxDescriptionStyleImpl#getWidgetGridColumn <em>Widget Grid Column</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalCheckboxDescriptionStyleImpl#getGap <em>Gap</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.ConditionalCheckboxDescriptionStyleImpl#getColor <em>Color</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConditionalCheckboxDescriptionStyleImpl extends ConditionalImpl implements ConditionalCheckboxDescriptionStyle {

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
     * The default value of the '{@link #getFlexDirection() <em>Flex Direction</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getFlexDirection()
     * @generated NOT
     * @ordered
     */
    protected static final String FLEX_DIRECTION_EDEFAULT = "row-reverse";

    /**
     * The default value of the '{@link #getLabelFlex() <em>Label Flex</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getLabelFlex()
     * @generated NOT
     * @ordered
     */
    protected static final String LABEL_FLEX_EDEFAULT = "1 1 auto";

    /**
     * The cached value of the '{@link #getColor() <em>Color</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     * @see #getColor()
     */
    protected UserColor color;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected ConditionalCheckboxDescriptionStyleImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return FormPackage.Literals.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS, oldGridTemplateColumns, gridTemplateColumns));
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
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS, oldGridTemplateRows, gridTemplateRows));
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
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__LABEL_GRID_ROW, oldLabelGridRow, labelGridRow));
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
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__LABEL_GRID_COLUMN, oldLabelGridColumn, labelGridColumn));
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
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__WIDGET_GRID_ROW, oldWidgetGridRow, widgetGridRow));
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
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN, oldWidgetGridColumn, widgetGridColumn));
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
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__GAP, oldGap, gap));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public UserColor getColor() {
		if (color != null && color.eIsProxy())
		{
			InternalEObject oldColor = (InternalEObject)color;
			color = (UserColor)eResolveProxy(oldColor);
			if (color != oldColor)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__COLOR, oldColor, color));
			}
		}
		return color;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public UserColor basicGetColor() {
		return color;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setColor(UserColor newColor) {
		UserColor oldColor = color;
		color = newColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__COLOR, oldColor, color));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS:
				return getGridTemplateColumns();
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS:
				return getGridTemplateRows();
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__LABEL_GRID_ROW:
				return getLabelGridRow();
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__LABEL_GRID_COLUMN:
				return getLabelGridColumn();
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__WIDGET_GRID_ROW:
				return getWidgetGridRow();
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN:
				return getWidgetGridColumn();
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__GAP:
				return getGap();
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__COLOR:
				if (resolve) return getColor();
				return basicGetColor();
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
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS:
				setGridTemplateColumns((String)newValue);
				return;
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS:
				setGridTemplateRows((String)newValue);
				return;
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__LABEL_GRID_ROW:
				setLabelGridRow((String)newValue);
				return;
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__LABEL_GRID_COLUMN:
				setLabelGridColumn((String)newValue);
				return;
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__WIDGET_GRID_ROW:
				setWidgetGridRow((String)newValue);
				return;
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN:
				setWidgetGridColumn((String)newValue);
				return;
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__GAP:
				setGap((String)newValue);
				return;
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__COLOR:
				setColor((UserColor)newValue);
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
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS:
				setGridTemplateColumns(GRID_TEMPLATE_COLUMNS_EDEFAULT);
				return;
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS:
				setGridTemplateRows(GRID_TEMPLATE_ROWS_EDEFAULT);
				return;
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__LABEL_GRID_ROW:
				setLabelGridRow(LABEL_GRID_ROW_EDEFAULT);
				return;
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__LABEL_GRID_COLUMN:
				setLabelGridColumn(LABEL_GRID_COLUMN_EDEFAULT);
				return;
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__WIDGET_GRID_ROW:
				setWidgetGridRow(WIDGET_GRID_ROW_EDEFAULT);
				return;
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN:
				setWidgetGridColumn(WIDGET_GRID_COLUMN_EDEFAULT);
				return;
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__GAP:
				setGap(GAP_EDEFAULT);
				return;
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__COLOR:
				setColor((UserColor)null);
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
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS:
				return GRID_TEMPLATE_COLUMNS_EDEFAULT == null ? gridTemplateColumns != null : !GRID_TEMPLATE_COLUMNS_EDEFAULT.equals(gridTemplateColumns);
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS:
				return GRID_TEMPLATE_ROWS_EDEFAULT == null ? gridTemplateRows != null : !GRID_TEMPLATE_ROWS_EDEFAULT.equals(gridTemplateRows);
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__LABEL_GRID_ROW:
				return LABEL_GRID_ROW_EDEFAULT == null ? labelGridRow != null : !LABEL_GRID_ROW_EDEFAULT.equals(labelGridRow);
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__LABEL_GRID_COLUMN:
				return LABEL_GRID_COLUMN_EDEFAULT == null ? labelGridColumn != null : !LABEL_GRID_COLUMN_EDEFAULT.equals(labelGridColumn);
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__WIDGET_GRID_ROW:
				return WIDGET_GRID_ROW_EDEFAULT == null ? widgetGridRow != null : !WIDGET_GRID_ROW_EDEFAULT.equals(widgetGridRow);
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN:
				return WIDGET_GRID_COLUMN_EDEFAULT == null ? widgetGridColumn != null : !WIDGET_GRID_COLUMN_EDEFAULT.equals(widgetGridColumn);
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__GAP:
				return GAP_EDEFAULT == null ? gap != null : !GAP_EDEFAULT.equals(gap);
			case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__COLOR:
				return color != null;
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
		if (baseClass == WidgetGridLayout.class)
		{
			switch (derivedFeatureID)
			{
				case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS: return FormPackage.WIDGET_GRID_LAYOUT__GRID_TEMPLATE_COLUMNS;
				case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS: return FormPackage.WIDGET_GRID_LAYOUT__GRID_TEMPLATE_ROWS;
				case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__LABEL_GRID_ROW: return FormPackage.WIDGET_GRID_LAYOUT__LABEL_GRID_ROW;
				case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__LABEL_GRID_COLUMN: return FormPackage.WIDGET_GRID_LAYOUT__LABEL_GRID_COLUMN;
				case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__WIDGET_GRID_ROW: return FormPackage.WIDGET_GRID_LAYOUT__WIDGET_GRID_ROW;
				case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN: return FormPackage.WIDGET_GRID_LAYOUT__WIDGET_GRID_COLUMN;
				case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__GAP: return FormPackage.WIDGET_GRID_LAYOUT__GAP;
				default: return -1;
			}
		}
		if (baseClass == CheckboxDescriptionStyle.class)
		{
			switch (derivedFeatureID)
			{
				case FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__COLOR: return FormPackage.CHECKBOX_DESCRIPTION_STYLE__COLOR;
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
		if (baseClass == WidgetGridLayout.class)
		{
			switch (baseFeatureID)
			{
				case FormPackage.WIDGET_GRID_LAYOUT__GRID_TEMPLATE_COLUMNS: return FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS;
				case FormPackage.WIDGET_GRID_LAYOUT__GRID_TEMPLATE_ROWS: return FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS;
				case FormPackage.WIDGET_GRID_LAYOUT__LABEL_GRID_ROW: return FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__LABEL_GRID_ROW;
				case FormPackage.WIDGET_GRID_LAYOUT__LABEL_GRID_COLUMN: return FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__LABEL_GRID_COLUMN;
				case FormPackage.WIDGET_GRID_LAYOUT__WIDGET_GRID_ROW: return FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__WIDGET_GRID_ROW;
				case FormPackage.WIDGET_GRID_LAYOUT__WIDGET_GRID_COLUMN: return FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN;
				case FormPackage.WIDGET_GRID_LAYOUT__GAP: return FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__GAP;
				default: return -1;
			}
		}
		if (baseClass == CheckboxDescriptionStyle.class)
		{
			switch (baseFeatureID)
			{
				case FormPackage.CHECKBOX_DESCRIPTION_STYLE__COLOR: return FormPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE__COLOR;
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
		result.append(" (gridTemplateColumns: ");
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

} // ConditionalCheckboxDescriptionStyleImpl
