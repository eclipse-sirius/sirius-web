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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Company</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.task.Company#getOwnedTeams <em>Owned Teams</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.Company#getOwnedPersons <em>Owned Persons</em>}</li>
 * <li>{@link org.eclipse.sirius.components.task.Company#getOwnedProjects <em>Owned Projects</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.task.TaskPackage#getCompany()
 * @model
 * @generated
 */
public interface Company extends Resource {
    /**
     * Returns the value of the '<em><b>Owned Teams</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.task.Team}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Owned Teams</em>' containment reference list.
     * @see org.eclipse.sirius.components.task.TaskPackage#getCompany_OwnedTeams()
     * @model containment="true"
     * @generated
     */
    EList<Team> getOwnedTeams();

    /**
     * Returns the value of the '<em><b>Owned Persons</b></em>' containment reference list. The list contents are of
     * type {@link org.eclipse.sirius.components.task.Person}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Owned Persons</em>' containment reference list.
     * @see org.eclipse.sirius.components.task.TaskPackage#getCompany_OwnedPersons()
     * @model containment="true"
     * @generated
     */
    EList<Person> getOwnedPersons();

    /**
     * Returns the value of the '<em><b>Owned Projects</b></em>' containment reference list. The list contents are of
     * type {@link org.eclipse.sirius.components.task.Project}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Owned Projects</em>' containment reference list.
     * @see org.eclipse.sirius.components.task.TaskPackage#getCompany_OwnedProjects()
     * @model containment="true"
     * @generated
     */
    EList<Project> getOwnedProjects();

} // Company
