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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Cell Description</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.table.CellDescription#getValueExpression <em>Value
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.CellDescription#getTooltipExpression <em>Tooltip
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.CellDescription#getCellWidgetDescription <em>Cell Widget
 * Description</em>}</li>
 * </ul>
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.table.TablePackage#getCellDescription()
 */
public interface CellDescription extends EObject {

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Name</em>' attribute.
     * @model dataType="org.eclipse.sirius.components.view.Identifier"
     * @generated
     * @see #setName(String)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getCellDescription_Name()
     */
    String getName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.table.CellDescription#getName <em>Name</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Name</em>' attribute.
     * @generated
     * @see #getName()
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Precondition Expression</b></em>' attribute. The default value is
     * <code>""</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Precondition Expression</em>' attribute.
     * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setPreconditionExpression(String)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getCellDescription_PreconditionExpression()
     */
    String getPreconditionExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.table.CellDescription#getPreconditionExpression
     * <em>Precondition Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Precondition Expression</em>' attribute.
     * @generated
     * @see #getPreconditionExpression()
     */
    void setPreconditionExpression(String value);

    /**
     * Returns the value of the '<em><b>Selected Target Object Expression</b></em>' attribute. The default value is
     * <code>""</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Selected Target Object Expression</em>' attribute.
     * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setSelectedTargetObjectExpression(String)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getCellDescription_SelectedTargetObjectExpression()
     */
    String getSelectedTargetObjectExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.table.CellDescription#getSelectedTargetObjectExpression <em>Selected
     * Target Object Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Selected Target Object Expression</em>' attribute.
     * @generated
     * @see #getSelectedTargetObjectExpression()
     */
    void setSelectedTargetObjectExpression(String value);

    /**
     * Returns the value of the '<em><b>Value Expression</b></em>' attribute. The default value is <code>""</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Value Expression</em>' attribute.
     * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setValueExpression(String)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getCellDescription_ValueExpression()
     */
    String getValueExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.table.CellDescription#getValueExpression
     * <em>Value Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Value Expression</em>' attribute.
     * @generated
     * @see #getValueExpression()
     */
    void setValueExpression(String value);

    /**
     * Returns the value of the '<em><b>Tooltip Expression</b></em>' attribute. The default value is <code>""</code>.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Tooltip Expression</em>' attribute.
     * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setTooltipExpression(String)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getCellDescription_TooltipExpression()
     */
    String getTooltipExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.table.CellDescription#getTooltipExpression
     * <em>Tooltip Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Tooltip Expression</em>' attribute.
     * @generated
     * @see #getTooltipExpression()
     */
    void setTooltipExpression(String value);

    /**
     * Returns the value of the '<em><b>Cell Widget Description</b></em>' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Cell Widget Description</em>' containment reference.
     * @model containment="true"
     * @generated
     * @see #setCellWidgetDescription(CellWidgetDescription)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getCellDescription_CellWidgetDescription()
     */
    CellWidgetDescription getCellWidgetDescription();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.table.CellDescription#getCellWidgetDescription
     * <em>Cell Widget Description</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Cell Widget Description</em>' containment reference.
     * @generated
     * @see #getCellWidgetDescription()
     */
    void setCellWidgetDescription(CellWidgetDescription value);

} // CellDescription
