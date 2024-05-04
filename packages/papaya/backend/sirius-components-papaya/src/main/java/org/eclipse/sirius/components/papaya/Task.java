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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Task</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.Task#getPriority <em>Priority</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Task#getCost <em>Cost</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Task#getTargets <em>Targets</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Task#getTasks <em>Tasks</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Task#getStartDate <em>Start Date</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Task#getEndDate <em>End Date</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Task#isDone <em>Done</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getTask()
 * @model
 * @generated
 */
public interface Task extends NamedElement {
    /**
     * Returns the value of the '<em><b>Priority</b></em>' attribute. The literals are from the enumeration
     * {@link org.eclipse.sirius.components.papaya.Priority}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Priority</em>' attribute.
     * @see org.eclipse.sirius.components.papaya.Priority
     * @see #setPriority(Priority)
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getTask_Priority()
     * @model
     * @generated
     */
    Priority getPriority();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.papaya.Task#getPriority <em>Priority</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Priority</em>' attribute.
     * @see org.eclipse.sirius.components.papaya.Priority
     * @see #getPriority()
     * @generated
     */
    void setPriority(Priority value);

    /**
     * Returns the value of the '<em><b>Cost</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Cost</em>' attribute.
     * @see #setCost(int)
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getTask_Cost()
     * @model
     * @generated
     */
    int getCost();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.papaya.Task#getCost <em>Cost</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Cost</em>' attribute.
     * @see #getCost()
     * @generated
     */
    void setCost(int value);

    /**
     * Returns the value of the '<em><b>Targets</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.ModelElement}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Targets</em>' reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getTask_Targets()
     * @model
     * @generated
     */
    EList<ModelElement> getTargets();

    /**
     * Returns the value of the '<em><b>Tasks</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Task}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Tasks</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getTask_Tasks()
     * @model containment="true"
     * @generated
     */
    EList<Task> getTasks();

    /**
     * Returns the value of the '<em><b>Start Date</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Start Date</em>' attribute.
     * @see #setStartDate(Instant)
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getTask_StartDate()
     * @model dataType="org.eclipse.sirius.components.papaya.Instant"
     * @generated
     */
    Instant getStartDate();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.papaya.Task#getStartDate <em>Start Date</em>}'
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
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getTask_EndDate()
     * @model dataType="org.eclipse.sirius.components.papaya.Instant"
     * @generated
     */
    Instant getEndDate();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.papaya.Task#getEndDate <em>End Date</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>End Date</em>' attribute.
     * @see #getEndDate()
     * @generated
     */
    void setEndDate(Instant value);

    /**
     * Returns the value of the '<em><b>Done</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Done</em>' attribute.
     * @see #setDone(boolean)
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getTask_Done()
     * @model
     * @generated
     */
    boolean isDone();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.papaya.Task#isDone <em>Done</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Done</em>' attribute.
     * @see #isDone()
     * @generated
     */
    void setDone(boolean value);

} // Task
