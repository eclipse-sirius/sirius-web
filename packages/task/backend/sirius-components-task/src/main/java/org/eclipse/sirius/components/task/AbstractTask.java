/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.task;

import java.time.Instant;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Abstract Task</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.task.AbstractTask#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.AbstractTask#getDescription <em>Description</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.AbstractTask#getStartTime <em>Start Time</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.AbstractTask#getEndTime <em>End Time</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.AbstractTask#getProgress <em>Progress</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.AbstractTask#isComputeStartEndDynamically <em>Compute Start End
 * Dynamically</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.AbstractTask#getTags <em>Tags</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.AbstractTask#getDependencies <em>Dependencies</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.AbstractTask#getAssignedPersons <em>Assigned Persons</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.AbstractTask#getAssignedTeams <em>Assigned Teams</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.AbstractTask#getSubTasks <em>Sub Tasks</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.task.TaskPackage#getAbstractTask()
 * @model abstract="true"
 * @generated
 */
public interface AbstractTask extends EObject {
    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.eclipse.sirius.components.task.TaskPackage#getAbstractTask_Name()
     * @model
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.task.AbstractTask#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Description</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Description</em>' attribute.
     * @see #setDescription(String)
     * @see org.eclipse.sirius.components.task.TaskPackage#getAbstractTask_Description()
     * @model
     * @generated
     */
    String getDescription();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.task.AbstractTask#getDescription
     * <em>Description</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Description</em>' attribute.
     * @see #getDescription()
     * @generated
     */
    void setDescription(String value);

    /**
     * Returns the value of the '<em><b>Start Time</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Start Time</em>' attribute.
     * @see #setStartTime(Instant)
     * @see org.eclipse.sirius.components.task.TaskPackage#getAbstractTask_StartTime()
     * @model dataType="org.eclipse.sirius.components.task.Instant"
     * @generated
     */
    Instant getStartTime();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.task.AbstractTask#getStartTime <em>Start Time</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Start Time</em>' attribute.
     * @see #getStartTime()
     * @generated
     */
    void setStartTime(Instant value);

    /**
     * Returns the value of the '<em><b>End Time</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>End Time</em>' attribute.
     * @see #setEndTime(Instant)
     * @see org.eclipse.sirius.components.task.TaskPackage#getAbstractTask_EndTime()
     * @model dataType="org.eclipse.sirius.components.task.Instant"
     * @generated
     */
    Instant getEndTime();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.task.AbstractTask#getEndTime <em>End Time</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>End Time</em>' attribute.
     * @see #getEndTime()
     * @generated
     */
    void setEndTime(Instant value);

    /**
     * Returns the value of the '<em><b>Progress</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Progress</em>' attribute.
     * @see #setProgress(int)
     * @see org.eclipse.sirius.components.task.TaskPackage#getAbstractTask_Progress()
     * @model
     * @generated
     */
    int getProgress();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.task.AbstractTask#getProgress <em>Progress</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Progress</em>' attribute.
     * @see #getProgress()
     * @generated
     */
    void setProgress(int value);

    /**
     * Returns the value of the '<em><b>Compute Start End Dynamically</b></em>' attribute. The default value is
     * <code>"true"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Compute Start End Dynamically</em>' attribute.
     * @see #setComputeStartEndDynamically(boolean)
     * @see org.eclipse.sirius.components.task.TaskPackage#getAbstractTask_ComputeStartEndDynamically()
     * @model default="true"
     * @generated
     */
    boolean isComputeStartEndDynamically();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.task.AbstractTask#isComputeStartEndDynamically
     * <em>Compute Start End Dynamically</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Compute Start End Dynamically</em>' attribute.
     * @see #isComputeStartEndDynamically()
     * @generated
     */
    void setComputeStartEndDynamically(boolean value);

    /**
     * Returns the value of the '<em><b>Tags</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.task.TaskTag}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Tags</em>' reference list.
     * @see org.eclipse.sirius.components.task.TaskPackage#getAbstractTask_Tags()
     * @model
     * @generated
     */
    EList<TaskTag> getTags();

    /**
     * Returns the value of the '<em><b>Dependencies</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.task.Task}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Dependencies</em>' reference list.
     * @see org.eclipse.sirius.components.task.TaskPackage#getAbstractTask_Dependencies()
     * @model
     * @generated
     */
    EList<Task> getDependencies();

    /**
     * Returns the value of the '<em><b>Assigned Persons</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.task.Person}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Assigned Persons</em>' reference list.
     * @see org.eclipse.sirius.components.task.TaskPackage#getAbstractTask_AssignedPersons()
     * @model
     * @generated
     */
    EList<Person> getAssignedPersons();

    /**
     * Returns the value of the '<em><b>Assigned Teams</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.task.Team}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Assigned Teams</em>' reference list.
     * @see org.eclipse.sirius.components.task.TaskPackage#getAbstractTask_AssignedTeams()
     * @model
     * @generated
     */
    EList<Team> getAssignedTeams();

    /**
     * Returns the value of the '<em><b>Sub Tasks</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.task.Task}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Sub Tasks</em>' containment reference list.
     * @see org.eclipse.sirius.components.task.TaskPackage#getAbstractTask_SubTasks()
     * @model containment="true"
     * @generated
     */
    EList<Task> getSubTasks();

} // AbstractTask
