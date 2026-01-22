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
package org.eclipse.sirius.components.view.form;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Widget Grid Layout</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.WidgetGridLayout#getGridTemplateColumns <em>Grid Template
 * Colums</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.WidgetGridLayout#getGridTemplateRows <em>Grid Template
 * Rows</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.WidgetGridLayout#getLabelGridRow <em>Label Grid Row</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.WidgetGridLayout#getLabelGridColumn <em>Label Grid
 * Column</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.WidgetGridLayout#getWidgetGridRow <em>Widget Grid Row</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.WidgetGridLayout#getWidgetGridColumn <em>Widget Grid
 * Column</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.form.FormPackage#getWidgetGridLayout()
 * @model abstract="true"
 * @generated
 */
public interface WidgetGridLayout extends EObject {
    /**
	 * Returns the value of the '<em><b>Grid Template Columns</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Grid Template Columns</em>' attribute.
	 * @see #setGridTemplateColumns(String)
	 * @see org.eclipse.sirius.components.view.form.FormPackage#getWidgetGridLayout_GridTemplateColumns()
	 * @model default="" required="true"
	 * @generated
	 */
    String getGridTemplateColumns();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.form.WidgetGridLayout#getGridTemplateColumns <em>Grid Template Columns</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Grid Template Columns</em>' attribute.
	 * @see #getGridTemplateColumns()
	 * @generated
	 */
    void setGridTemplateColumns(String value);

    /**
	 * Returns the value of the '<em><b>Grid Template Rows</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Grid Template Rows</em>' attribute.
	 * @see #setGridTemplateRows(String)
	 * @see org.eclipse.sirius.components.view.form.FormPackage#getWidgetGridLayout_GridTemplateRows()
	 * @model default="" required="true"
	 * @generated
	 */
    String getGridTemplateRows();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.form.WidgetGridLayout#getGridTemplateRows <em>Grid Template Rows</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Grid Template Rows</em>' attribute.
	 * @see #getGridTemplateRows()
	 * @generated
	 */
    void setGridTemplateRows(String value);

    /**
     * Returns the value of the '<em><b>Label Grid Row</b></em>' attribute. The default value is <code>""</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Label Grid Row</em>' attribute.
     * @see #setLabelGridRow(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getWidgetGridLayout_LabelGridRow()
     * @model default="" required="true"
     * @generated
     */
    String getLabelGridRow();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.form.WidgetGridLayout#getLabelGridRow <em>Label Grid Row</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label Grid Row</em>' attribute.
	 * @see #getLabelGridRow()
	 * @generated
	 */
    void setLabelGridRow(String value);

    /**
	 * Returns the value of the '<em><b>Label Grid Column</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Label Grid Column</em>' attribute.
	 * @see #setLabelGridColumn(String)
	 * @see org.eclipse.sirius.components.view.form.FormPackage#getWidgetGridLayout_LabelGridColumn()
	 * @model default="" required="true"
	 * @generated
	 */
    String getLabelGridColumn();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.form.WidgetGridLayout#getLabelGridColumn <em>Label Grid Column</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label Grid Column</em>' attribute.
	 * @see #getLabelGridColumn()
	 * @generated
	 */
    void setLabelGridColumn(String value);

    /**
     * Returns the value of the '<em><b>Widget Grid Row</b></em>' attribute. The default value is <code>""</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Widget Grid Row</em>' attribute.
     * @see #setWidgetGridRow(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getWidgetGridLayout_WidgetGridRow()
     * @model default="" required="true"
     * @generated
     */
    String getWidgetGridRow();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.form.WidgetGridLayout#getWidgetGridRow <em>Widget Grid Row</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Widget Grid Row</em>' attribute.
	 * @see #getWidgetGridRow()
	 * @generated
	 */
    void setWidgetGridRow(String value);

    /**
	 * Returns the value of the '<em><b>Widget Grid Column</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Widget Grid Column</em>' attribute.
	 * @see #setWidgetGridColumn(String)
	 * @see org.eclipse.sirius.components.view.form.FormPackage#getWidgetGridLayout_WidgetGridColumn()
	 * @model default="" required="true"
	 * @generated
	 */
    String getWidgetGridColumn();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.form.WidgetGridLayout#getWidgetGridColumn <em>Widget Grid Column</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Widget Grid Column</em>' attribute.
	 * @see #getWidgetGridColumn()
	 * @generated
	 */
    void setWidgetGridColumn(String value);

    /**
     * Returns the value of the '<em><b>Gap</b></em>' attribute. The default value is <code>""</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Gap</em>' attribute.
     * @see #setGap(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getWidgetGridLayout_Gap()
     * @model default="" required="true"
     * @generated
     */
    String getGap();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.view.form.WidgetGridLayout#getGap <em>Gap</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Gap</em>' attribute.
	 * @see #getGap()
	 * @generated
	 */
    void setGap(String value);

} // WidgetGridLayout
