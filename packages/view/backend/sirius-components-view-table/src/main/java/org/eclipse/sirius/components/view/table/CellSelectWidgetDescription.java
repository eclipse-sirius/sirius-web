/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import org.eclipse.sirius.components.view.Operation;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Cell Select Widget Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.table.CellSelectWidgetDescription#getCandidatesExpression
 * <em>Candidates Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.CellSelectWidgetDescription#getCandidateLabelExpression
 * <em>Candidate Label Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.CellSelectWidgetDescription#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.table.TablePackage#getCellSelectWidgetDescription()
 * @model
 * @generated
 */
public interface CellSelectWidgetDescription extends CellWidgetDescription {
    /**
     * Returns the value of the '<em><b>Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Candidates Expression</em>' attribute.
     * @see #setCandidatesExpression(String)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getCellSelectWidgetDescription_CandidatesExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getCandidatesExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.table.CellSelectWidgetDescription#getCandidatesExpression
     * <em>Candidates Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Candidates Expression</em>' attribute.
     * @see #getCandidatesExpression()
     * @generated
     */
    void setCandidatesExpression(String value);

    /**
     * Returns the value of the '<em><b>Candidate Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Candidate Label Expression</em>' attribute.
     * @see #setCandidateLabelExpression(String)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getCellSelectWidgetDescription_CandidateLabelExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getCandidateLabelExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.table.CellSelectWidgetDescription#getCandidateLabelExpression
     * <em>Candidate Label Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Candidate Label Expression</em>' attribute.
     * @see #getCandidateLabelExpression()
     * @generated
     */
    void setCandidateLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>Body</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.Operation}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Body</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.table.TablePackage#getCellSelectWidgetDescription_Body()
     * @model containment="true"
     * @generated
     */
    EList<Operation> getBody();

} // CellSelectWidgetDescription
