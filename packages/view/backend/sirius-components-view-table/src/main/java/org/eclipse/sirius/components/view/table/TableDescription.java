/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
package org.eclipse.sirius.components.view.table;

import org.eclipse.emf.common.util.EList;
import org.eclipse.sirius.components.view.RepresentationDescription;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Description</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.table.TableDescription#getUseStripedRowsExpression <em>Use Striped Rows
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.TableDescription#getColumnDescriptions <em>Column
 * Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.TableDescription#getRowDescription <em>Row Description</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.TableDescription#getCellDescriptions <em>Cell
 * Descriptions</em>}</li>
 * </ul>
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.table.TablePackage#getTableDescription()
 */
public interface TableDescription extends RepresentationDescription {

    /**
     * Returns the value of the '<em><b>Use Striped Rows Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Use Striped Rows Expression</em>' attribute.
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setUseStripedRowsExpression(String)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getTableDescription_UseStripedRowsExpression()
     */
    String getUseStripedRowsExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.table.TableDescription#getUseStripedRowsExpression <em>Use Striped
     * Rows Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Use Striped Rows Expression</em>' attribute.
     * @generated
     * @see #getUseStripedRowsExpression()
     */
    void setUseStripedRowsExpression(String value);

    /**
     * Returns the value of the '<em><b>Column Descriptions</b></em>' containment reference list. The list contents are
     * of type {@link org.eclipse.sirius.components.view.table.ColumnDescription}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Column Descriptions</em>' containment reference list.
     * @model containment="true" keys="name"
     * @generated
     * @see org.eclipse.sirius.components.view.table.TablePackage#getTableDescription_ColumnDescriptions()
     */
    EList<ColumnDescription> getColumnDescriptions();

    /**
     * Returns the value of the '<em><b>Row Description</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Row Description</em>' reference.
     * @model keys="name"
     * @generated
     * @see #setRowDescription(RowDescription)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getTableDescription_RowDescription()
     */
    RowDescription getRowDescription();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.table.TableDescription#getRowDescription <em>Row
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
     * @model containment="true" keys="name"
     * @generated
     * @see org.eclipse.sirius.components.view.table.TablePackage#getTableDescription_CellDescriptions()
     */
    EList<CellDescription> getCellDescriptions();

} // TableDescription
