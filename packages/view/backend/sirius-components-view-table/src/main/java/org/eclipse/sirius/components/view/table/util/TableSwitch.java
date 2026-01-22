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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
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
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the inheritance hierarchy until a non-null result is
 * returned, which is the result of the switch. <!-- end-user-doc -->
 * @see org.eclipse.sirius.components.view.table.TablePackage
 * @generated
 */
public class TableSwitch<T> extends Switch<T> {

    /**
	 * The cached model package
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected static TablePackage modelPackage;

    /**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public TableSwitch() {
		if (modelPackage == null)
		{
			modelPackage = TablePackage.eINSTANCE;
		}
	}

    /**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
    @Override
    protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

    /**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
    @Override
    protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID)
		{
			case TablePackage.TABLE_DESCRIPTION:
			{
				TableDescription tableDescription = (TableDescription)theEObject;
				T result = caseTableDescription(tableDescription);
				if (result == null) result = caseRepresentationDescription(tableDescription);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TablePackage.COLUMN_DESCRIPTION:
			{
				ColumnDescription columnDescription = (ColumnDescription)theEObject;
				T result = caseColumnDescription(columnDescription);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TablePackage.ROW_DESCRIPTION:
			{
				RowDescription rowDescription = (RowDescription)theEObject;
				T result = caseRowDescription(rowDescription);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TablePackage.CELL_DESCRIPTION:
			{
				CellDescription cellDescription = (CellDescription)theEObject;
				T result = caseCellDescription(cellDescription);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TablePackage.CELL_WIDGET_DESCRIPTION:
			{
				CellWidgetDescription cellWidgetDescription = (CellWidgetDescription)theEObject;
				T result = caseCellWidgetDescription(cellWidgetDescription);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TablePackage.CELL_TEXTFIELD_WIDGET_DESCRIPTION:
			{
				CellTextfieldWidgetDescription cellTextfieldWidgetDescription = (CellTextfieldWidgetDescription)theEObject;
				T result = caseCellTextfieldWidgetDescription(cellTextfieldWidgetDescription);
				if (result == null) result = caseCellWidgetDescription(cellTextfieldWidgetDescription);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TablePackage.CELL_LABEL_WIDGET_DESCRIPTION:
			{
				CellLabelWidgetDescription cellLabelWidgetDescription = (CellLabelWidgetDescription)theEObject;
				T result = caseCellLabelWidgetDescription(cellLabelWidgetDescription);
				if (result == null) result = caseCellWidgetDescription(cellLabelWidgetDescription);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TablePackage.ROW_CONTEXT_MENU_ENTRY:
			{
				RowContextMenuEntry rowContextMenuEntry = (RowContextMenuEntry)theEObject;
				T result = caseRowContextMenuEntry(rowContextMenuEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TablePackage.CELL_TEXTAREA_WIDGET_DESCRIPTION:
			{
				CellTextareaWidgetDescription cellTextareaWidgetDescription = (CellTextareaWidgetDescription)theEObject;
				T result = caseCellTextareaWidgetDescription(cellTextareaWidgetDescription);
				if (result == null) result = caseCellWidgetDescription(cellTextareaWidgetDescription);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TablePackage.ROW_FILTER_DESCRIPTION:
			{
				RowFilterDescription rowFilterDescription = (RowFilterDescription)theEObject;
				T result = caseRowFilterDescription(rowFilterDescription);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Description</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseTableDescription(TableDescription object) {
		return null;
	}

    /**
     * Returns the result of interpreting the object as an instance of '<em>Column Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Column Description</em>'.
     * @generated
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     */
    public T caseColumnDescription(ColumnDescription object) {
		return null;
	}

    /**
     * Returns the result of interpreting the object as an instance of '<em>Row Description</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Row Description</em>'.
     * @generated
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     */
    public T caseRowDescription(RowDescription object) {
		return null;
	}

    /**
     * Returns the result of interpreting the object as an instance of '<em>Cell Description</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Cell Description</em>'.
     * @generated
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     */
    public T caseCellDescription(CellDescription object) {
		return null;
	}

    /**
     * Returns the result of interpreting the object as an instance of '<em>Cell Widget Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Cell Widget Description</em>'.
     * @generated
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     */
    public T caseCellWidgetDescription(CellWidgetDescription object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Cell Textfield Widget Description</em>'.
	 * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Cell Textfield Widget Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseCellTextfieldWidgetDescription(CellTextfieldWidgetDescription object) {
		return null;
	}

    /**
     * Returns the result of interpreting the object as an instance of '<em>Cell Label Widget Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Cell Label Widget Description</em>'.
     * @generated
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     */
    public T caseCellLabelWidgetDescription(CellLabelWidgetDescription object) {
		return null;
	}

    /**
     * Returns the result of interpreting the object as an instance of '<em>Cell Textarea Widget Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Cell Textarea Widget Description</em>'.
     * @generated
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     */
    public T caseCellTextareaWidgetDescription(CellTextareaWidgetDescription object) {
		return null;
	}

    /**
     * Returns the result of interpreting the object as an instance of '<em>Row Filter Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Row Filter Description</em>'.
     * @generated
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     */
    public T caseRowFilterDescription(RowFilterDescription object) {
		return null;
	}

    /**
     * Returns the result of interpreting the object as an instance of '<em>Row Context Menu Entry</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Row Context Menu Entry</em>'.
     * @generated
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     */
    public T caseRowContextMenuEntry(RowContextMenuEntry object) {
		return null;
	}

    /**
     * Returns the result of interpreting the object as an instance of '<em>Representation Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Representation Description</em>'.
     * @generated
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     */
    public T caseRepresentationDescription(RepresentationDescription object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch, but this is the last case
     * anyway. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
    @Override
    public T defaultCase(EObject object) {
		return null;
	}

} // TableSwitch
