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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.sirius.components.view.table.*;
import org.eclipse.sirius.components.view.table.CellDescription;
import org.eclipse.sirius.components.view.table.CellLabelWidgetDescription;
import org.eclipse.sirius.components.view.table.CellTextareaWidgetDescription;
import org.eclipse.sirius.components.view.table.CellTextfieldWidgetDescription;
import org.eclipse.sirius.components.view.table.ColumnDescription;
import org.eclipse.sirius.components.view.table.RowContextMenuEntry;
import org.eclipse.sirius.components.view.table.RowDescription;
import org.eclipse.sirius.components.view.table.RowFilterDescription;
import org.eclipse.sirius.components.view.table.TableDescription;
import org.eclipse.sirius.components.view.table.TableFactory;
import org.eclipse.sirius.components.view.table.TablePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * @generated
 */
public class TableFactoryImpl extends EFactoryImpl implements TableFactory {

    /**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public TableFactoryImpl() {
		super();
	}

    /**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public static TableFactory init() {
		try
		{
			TableFactory theTableFactory = (TableFactory)EPackage.Registry.INSTANCE.getEFactory(TablePackage.eNS_URI);
			if (theTableFactory != null)
			{
				return theTableFactory;
			}
		}
		catch (Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TableFactoryImpl();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EObject create(EClass eClass) {
		switch (eClass.getClassifierID())
		{
			case TablePackage.TABLE_DESCRIPTION: return createTableDescription();
			case TablePackage.COLUMN_DESCRIPTION: return createColumnDescription();
			case TablePackage.ROW_DESCRIPTION: return createRowDescription();
			case TablePackage.CELL_DESCRIPTION: return createCellDescription();
			case TablePackage.CELL_TEXTFIELD_WIDGET_DESCRIPTION: return createCellTextfieldWidgetDescription();
			case TablePackage.CELL_LABEL_WIDGET_DESCRIPTION: return createCellLabelWidgetDescription();
			case TablePackage.ROW_CONTEXT_MENU_ENTRY: return createRowContextMenuEntry();
			case TablePackage.CELL_TEXTAREA_WIDGET_DESCRIPTION: return createCellTextareaWidgetDescription();
			case TablePackage.ROW_FILTER_DESCRIPTION: return createRowFilterDescription();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public TableDescription createTableDescription() {
		TableDescriptionImpl tableDescription = new TableDescriptionImpl();
		return tableDescription;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public ColumnDescription createColumnDescription() {
		ColumnDescriptionImpl columnDescription = new ColumnDescriptionImpl();
		return columnDescription;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public RowDescription createRowDescription() {
		RowDescriptionImpl rowDescription = new RowDescriptionImpl();
		return rowDescription;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public CellDescription createCellDescription() {
		CellDescriptionImpl cellDescription = new CellDescriptionImpl();
		return cellDescription;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public CellTextfieldWidgetDescription createCellTextfieldWidgetDescription() {
		CellTextfieldWidgetDescriptionImpl cellTextfieldWidgetDescription = new CellTextfieldWidgetDescriptionImpl();
		return cellTextfieldWidgetDescription;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public CellLabelWidgetDescription createCellLabelWidgetDescription() {
		CellLabelWidgetDescriptionImpl cellLabelWidgetDescription = new CellLabelWidgetDescriptionImpl();
		return cellLabelWidgetDescription;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public CellTextareaWidgetDescription createCellTextareaWidgetDescription() {
		CellTextareaWidgetDescriptionImpl cellTextareaWidgetDescription = new CellTextareaWidgetDescriptionImpl();
		return cellTextareaWidgetDescription;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public RowFilterDescription createRowFilterDescription() {
		RowFilterDescriptionImpl rowFilterDescription = new RowFilterDescriptionImpl();
		return rowFilterDescription;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public RowContextMenuEntry createRowContextMenuEntry() {
		RowContextMenuEntryImpl rowContextMenuEntry = new RowContextMenuEntryImpl();
		return rowContextMenuEntry;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public TablePackage getTablePackage() {
		return (TablePackage)getEPackage();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
    @Deprecated
    public static TablePackage getPackage() {
		return TablePackage.eINSTANCE;
	}

} // TableFactoryImpl
