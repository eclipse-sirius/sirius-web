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
package org.eclipse.sirius.components.view.widget.tablewidget;

import org.eclipse.emf.common.util.EList;
import org.eclipse.sirius.components.view.form.WidgetDescription;
import org.eclipse.sirius.components.view.table.CellDescription;
import org.eclipse.sirius.components.view.table.ColumnDescription;
import org.eclipse.sirius.components.view.table.RowDescription;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Description</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription#getColumnDescriptions
 * <em>Column Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription#getRowDescription <em>Row
 * Description</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription#getCellDescriptions <em>Cell
 * Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription#getUseStripedRowsExpression
 * <em>Use Striped Rows Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription#getIsEnabledExpression <em>Is
 * Enabled Expression</em>}</li>
 * </ul>
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetPackage#getTableWidgetDescription()
 */
public interface TableWidgetDescription extends WidgetDescription {

    /**
     * Returns the value of the '<em><b>Column Descriptions</b></em>' containment reference list. The list contents are
     * of type {@link org.eclipse.sirius.components.view.table.ColumnDescription}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Column Descriptions</em>' containment reference list.
     * @model containment="true"
     * @generated
     * @see org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetPackage#getTableWidgetDescription_ColumnDescriptions()
     */
    EList<ColumnDescription> getColumnDescriptions();

    /**
     * Returns the value of the '<em><b>Row Description</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Row Description</em>' containment reference.
     * @model containment="true"
     * @generated
     * @see #setRowDescription(RowDescription)
     * @see org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetPackage#getTableWidgetDescription_RowDescription()
     */
    RowDescription getRowDescription();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription#getRowDescription <em>Row
     * Description</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Row Description</em>' containment reference.
     * @generated
     * @see #getRowDescription()
     */
    void setRowDescription(RowDescription value);

    /**
     * Returns the value of the '<em><b>Cell Descriptions</b></em>' containment reference list. The list contents are of
     * type {@link org.eclipse.sirius.components.view.table.CellDescription}. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Cell Descriptions</em>' containment reference list.
     * @model containment="true"
     * @generated
     * @see org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetPackage#getTableWidgetDescription_CellDescriptions()
     */
    EList<CellDescription> getCellDescriptions();

    /**
     * Returns the value of the '<em><b>Use Striped Rows Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Use Striped Rows Expression</em>' attribute.
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setUseStripedRowsExpression(String)
     * @see org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetPackage#getTableWidgetDescription_UseStripedRowsExpression()
     */
    String getUseStripedRowsExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription#getUseStripedRowsExpression
     * <em>Use Striped Rows Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Use Striped Rows Expression</em>' attribute.
     * @generated
     * @see #getUseStripedRowsExpression()
     */
    void setUseStripedRowsExpression(String value);

    /**
     * Returns the value of the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Is Enabled Expression</em>' attribute.
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setIsEnabledExpression(String)
     * @see org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetPackage#getTableWidgetDescription_IsEnabledExpression()
     */
    String getIsEnabledExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription#getIsEnabledExpression
     * <em>Is Enabled Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Is Enabled Expression</em>' attribute.
     * @generated
     * @see #getIsEnabledExpression()
     */
    void setIsEnabledExpression(String value);

} // TableWidgetDescription
