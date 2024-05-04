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
package org.eclipse.sirius.components.papaya;

import java.time.Instant;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Iteration</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.Iteration#getStartDate <em>Start Date</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Iteration#getEndDate <em>End Date</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Iteration#getTasks <em>Tasks</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Iteration#getContributions <em>Contributions</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getIteration()
 * @model
 * @generated
 */
public interface Iteration extends NamedElement {
    /**
     * Returns the value of the '<em><b>Start Date</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Start Date</em>' attribute.
     * @see #setStartDate(Instant)
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getIteration_StartDate()
     * @model dataType="org.eclipse.sirius.components.papaya.Instant"
     * @generated
     */
    Instant getStartDate();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.papaya.Iteration#getStartDate <em>Start Date</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Start Date</em>' attribute.
     * @see #getStartDate()
     * @generated
     */
    void setStartDate(Instant value);

    /**
     * Returns the value of the '<em><b>End Date</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>End Date</em>' attribute.
     * @see #setEndDate(Instant)
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getIteration_EndDate()
     * @model dataType="org.eclipse.sirius.components.papaya.Instant"
     * @generated
     */
    Instant getEndDate();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.papaya.Iteration#getEndDate <em>End Date</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>End Date</em>' attribute.
     * @see #getEndDate()
     * @generated
     */
    void setEndDate(Instant value);

    /**
     * Returns the value of the '<em><b>Tasks</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Task}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Tasks</em>' reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getIteration_Tasks()
     * @model
     * @generated
     */
    EList<Task> getTasks();

    /**
     * Returns the value of the '<em><b>Contributions</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Contribution}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Contributions</em>' reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getIteration_Contributions()
     * @model
     * @generated
     */
    EList<Contribution> getContributions();

} // Iteration
