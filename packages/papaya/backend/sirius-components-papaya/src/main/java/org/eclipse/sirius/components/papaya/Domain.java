/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Domain</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.Domain#getServices <em>Services</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Domain#getRepositories <em>Repositories</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Domain#getEvents <em>Events</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Domain#getCommands <em>Commands</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Domain#getQueries <em>Queries</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Domain#getDependencies <em>Dependencies</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getDomain()
 * @model
 * @generated
 */
public interface Domain extends NamedElement {
    /**
     * Returns the value of the '<em><b>Services</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Service}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Services</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getDomain_Services()
     * @model containment="true"
     * @generated
     */
    EList<Service> getServices();

    /**
     * Returns the value of the '<em><b>Repositories</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Repository}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Repositories</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getDomain_Repositories()
     * @model containment="true"
     * @generated
     */
    EList<Repository> getRepositories();

    /**
     * Returns the value of the '<em><b>Events</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Event}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Events</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getDomain_Events()
     * @model containment="true"
     * @generated
     */
    EList<Event> getEvents();

    /**
     * Returns the value of the '<em><b>Commands</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Command}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Commands</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getDomain_Commands()
     * @model containment="true"
     * @generated
     */
    EList<Command> getCommands();

    /**
     * Returns the value of the '<em><b>Queries</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Query}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Queries</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getDomain_Queries()
     * @model containment="true"
     * @generated
     */
    EList<Query> getQueries();

    /**
     * Returns the value of the '<em><b>Dependencies</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Domain}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Dependencies</em>' reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getDomain_Dependencies()
     * @model
     * @generated
     */
    EList<Domain> getDependencies();

} // Domain
