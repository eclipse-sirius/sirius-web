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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract class of
 * the model. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.task.TaskPackage
 * @generated
 */
public interface TaskFactory extends EFactory {
    /**
     * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    TaskFactory eINSTANCE = org.eclipse.sirius.components.task.impl.TaskFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Company</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Company</em>'.
     * @generated
     */
    Company createCompany();

    /**
     * Returns a new object of class '<em>Team</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Team</em>'.
     * @generated
     */
    Team createTeam();

    /**
     * Returns a new object of class '<em>Person</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Person</em>'.
     * @generated
     */
    Person createPerson();

    /**
     * Returns a new object of class '<em>Task</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Task</em>'.
     * @generated
     */
    Task createTask();

    /**
     * Returns a new object of class '<em>Objective</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Objective</em>'.
     * @generated
     */
    Objective createObjective();

    /**
     * Returns a new object of class '<em>Key Result</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Key Result</em>'.
     * @generated
     */
    KeyResult createKeyResult();

    /**
     * Returns a new object of class '<em>Project</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Project</em>'.
     * @generated
     */
    Project createProject();

    /**
     * Returns a new object of class '<em>Tag</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Tag</em>'.
     * @generated
     */
    TaskTag createTaskTag();

    /**
     * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the package supported by this factory.
     * @generated
     */
    TaskPackage getTaskPackage();

} // TaskFactory
