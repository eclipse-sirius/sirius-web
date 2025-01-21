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
package org.eclipse.sirius.components.view.table;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract class of
 * the model. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.table.TablePackage
 * @generated
 */
public interface TableFactory extends EFactory {

    /**
     * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    TableFactory eINSTANCE = org.eclipse.sirius.components.view.table.impl.TableFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Description</em>'.
     * @generated
     */
    TableDescription createTableDescription();

    /**
     * Returns a new object of class '<em>Column Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Column Description</em>'.
     * @generated
     */
    ColumnDescription createColumnDescription();

    /**
     * Returns a new object of class '<em>Row Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Row Description</em>'.
     * @generated
     */
    RowDescription createRowDescription();

    /**
     * Returns a new object of class '<em>Cell Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Cell Description</em>'.
     * @generated
     */
    CellDescription createCellDescription();

    /**
     * Returns a new object of class '<em>Cell Textfield Widget Description</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Cell Textfield Widget Description</em>'.
     * @generated
     */
    CellTextfieldWidgetDescription createCellTextfieldWidgetDescription();

    /**
     * Returns a new object of class '<em>Cell Label Widget Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return a new object of class '<em>Cell Label Widget Description</em>'.
     * @generated
     */
    CellLabelWidgetDescription createCellLabelWidgetDescription();

    /**
     * Returns a new object of class '<em>Cell Textarea Widget Description</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Cell Textarea Widget Description</em>'.
     * @generated
     */
    CellTextareaWidgetDescription createCellTextareaWidgetDescription();

    /**
     * Returns a new object of class '<em>Row Context Menu Entry</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Row Context Menu Entry</em>'.
     * @generated
     */
    RowContextMenuEntry createRowContextMenuEntry();

    /**
     * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the package supported by this factory.
     * @generated
     */
    TablePackage getTablePackage();

} // TableFactory
