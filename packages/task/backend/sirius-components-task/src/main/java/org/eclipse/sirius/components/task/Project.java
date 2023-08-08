/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Project</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.task.Project#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.Project#getOwnedTasks <em>Owned Tasks</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.Project#getOwnedObjectives <em>Owned Objectives</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.Project#getOwnedTags <em>Owned Tags</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.task.TaskPackage#getProject()
 * @model
 * @generated
 */
public interface Project extends EObject {
    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.eclipse.sirius.components.task.TaskPackage#getProject_Name()
     * @model
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.task.Project#getName <em>Name</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Owned Tasks</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.task.Task}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Owned Tasks</em>' containment reference list.
     * @see org.eclipse.sirius.components.task.TaskPackage#getProject_OwnedTasks()
     * @model containment="true"
     * @generated
     */
    EList<Task> getOwnedTasks();

    /**
     * Returns the value of the '<em><b>Owned Objectives</b></em>' containment reference list. The list contents are of
     * type {@link org.eclipse.sirius.components.task.Objective}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Owned Objectives</em>' containment reference list.
     * @see org.eclipse.sirius.components.task.TaskPackage#getProject_OwnedObjectives()
     * @model containment="true"
     * @generated
     */
    EList<Objective> getOwnedObjectives();

    /**
     * Returns the value of the '<em><b>Owned Tags</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.task.TaskTag}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Owned Tags</em>' containment reference list.
     * @see org.eclipse.sirius.components.task.TaskPackage#getProject_OwnedTags()
     * @model containment="true"
     * @generated
     */
    EList<TaskTag> getOwnedTags();

} // Project
