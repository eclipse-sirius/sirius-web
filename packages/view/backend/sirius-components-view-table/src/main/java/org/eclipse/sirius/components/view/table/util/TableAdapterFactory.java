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
package org.eclipse.sirius.components.view.table.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.table.*;
import org.eclipse.sirius.components.view.table.CellDescription;
import org.eclipse.sirius.components.view.table.CellLabelWidgetDescription;
import org.eclipse.sirius.components.view.table.CellTextareaWidgetDescription;
import org.eclipse.sirius.components.view.table.CellTextfieldWidgetDescription;
import org.eclipse.sirius.components.view.table.CellWidgetDescription;
import org.eclipse.sirius.components.view.table.ColumnDescription;
import org.eclipse.sirius.components.view.table.RowContextMenuEntry;
import org.eclipse.sirius.components.view.table.RowDescription;
import org.eclipse.sirius.components.view.table.RowFilterDescription;
import org.eclipse.sirius.components.view.table.TableDescription;
import org.eclipse.sirius.components.view.table.TablePackage;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter <code>createXXX</code>
 * method for each class of the model. <!-- end-user-doc -->
 * @see org.eclipse.sirius.components.view.table.TablePackage
 * @generated
 */
public class TableAdapterFactory extends AdapterFactoryImpl {

    /**
	 * The cached model package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected static TablePackage modelPackage;

    /**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected TableSwitch<Adapter> modelSwitch = new TableSwitch<Adapter>()
		{
			@Override
			public Adapter caseTableDescription(TableDescription object)
			{
				return createTableDescriptionAdapter();
			}
			@Override
			public Adapter caseColumnDescription(ColumnDescription object)
			{
				return createColumnDescriptionAdapter();
			}
			@Override
			public Adapter caseRowDescription(RowDescription object)
			{
				return createRowDescriptionAdapter();
			}
			@Override
			public Adapter caseCellDescription(CellDescription object)
			{
				return createCellDescriptionAdapter();
			}
			@Override
			public Adapter caseCellWidgetDescription(CellWidgetDescription object)
			{
				return createCellWidgetDescriptionAdapter();
			}
			@Override
			public Adapter caseCellTextfieldWidgetDescription(CellTextfieldWidgetDescription object)
			{
				return createCellTextfieldWidgetDescriptionAdapter();
			}
			@Override
			public Adapter caseCellLabelWidgetDescription(CellLabelWidgetDescription object)
			{
				return createCellLabelWidgetDescriptionAdapter();
			}
			@Override
			public Adapter caseRowContextMenuEntry(RowContextMenuEntry object)
			{
				return createRowContextMenuEntryAdapter();
			}
			@Override
			public Adapter caseCellTextareaWidgetDescription(CellTextareaWidgetDescription object)
			{
				return createCellTextareaWidgetDescriptionAdapter();
			}
			@Override
			public Adapter caseRowFilterDescription(RowFilterDescription object)
			{
				return createRowFilterDescriptionAdapter();
			}
			@Override
			public Adapter caseRepresentationDescription(RepresentationDescription object)
			{
				return createRepresentationDescriptionAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object)
			{
				return createEObjectAdapter();
			}
		};

    /**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public TableAdapterFactory() {
		if (modelPackage == null)
		{
			modelPackage = TablePackage.eINSTANCE;
		}
	}

    /**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc --> This
     * implementation returns <code>true</code> if the object is either the model's package or is an instance object of
     * the model. <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
    @Override
    public boolean isFactoryForType(Object object) {
		if (object == modelPackage)
		{
			return true;
		}
		if (object instanceof EObject)
		{
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

    /**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
    @Override
    public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.table.TableDescription
     * <em>Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.table.TableDescription
     */
    public Adapter createTableDescriptionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.table.ColumnDescription <em>Column Description</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.table.ColumnDescription
	 * @generated
	 */
    public Adapter createColumnDescriptionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.table.RowDescription <em>Row Description</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.table.RowDescription
	 * @generated
	 */
    public Adapter createRowDescriptionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.table.CellDescription <em>Cell Description</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.table.CellDescription
	 * @generated
	 */
    public Adapter createCellDescriptionAdapter() {
		return null;
	}

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.table.CellWidgetDescription <em>Cell Widget Description</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
     * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.table.CellWidgetDescription
     */
    public Adapter createCellWidgetDescriptionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.table.CellTextfieldWidgetDescription <em>Cell Textfield Widget Description</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.table.CellTextfieldWidgetDescription
	 * @generated
	 */
    public Adapter createCellTextfieldWidgetDescriptionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.table.CellLabelWidgetDescription <em>Cell Label Widget Description</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.table.CellLabelWidgetDescription
	 * @generated
	 */
    public Adapter createCellLabelWidgetDescriptionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.table.CellTextareaWidgetDescription <em>Cell Textarea Widget Description</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.table.CellTextareaWidgetDescription
	 * @generated
	 */
    public Adapter createCellTextareaWidgetDescriptionAdapter() {
		return null;
	}

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.table.RowFilterDescription <em>Row Filter Description</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
     * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.table.RowFilterDescription
     */
    public Adapter createRowFilterDescriptionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.table.RowContextMenuEntry <em>Row Context Menu Entry</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we
     * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.table.RowContextMenuEntry
	 * @generated
	 */
    public Adapter createRowContextMenuEntryAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.RepresentationDescription <em>Representation Description</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that
     * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.sirius.components.view.RepresentationDescription
	 * @generated
	 */
    public Adapter createRepresentationDescriptionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc --> This default implementation returns null.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
    public Adapter createEObjectAdapter() {
		return null;
	}

} // TableAdapterFactory
