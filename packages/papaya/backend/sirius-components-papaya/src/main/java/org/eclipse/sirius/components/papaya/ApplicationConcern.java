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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Application Concern</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.ApplicationConcern#getControllers <em>Controllers</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.ApplicationConcern#getServices <em>Services</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.ApplicationConcern#getEvents <em>Events</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.ApplicationConcern#getCommands <em>Commands</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.ApplicationConcern#getQueries <em>Queries</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getApplicationConcern()
 * @model
 * @generated
 */
public interface ApplicationConcern extends NamedElement {
    /**
     * Returns the value of the '<em><b>Controllers</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Controller}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Controllers</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getApplicationConcern_Controllers()
     * @model containment="true"
     * @generated
     */
    EList<Controller> getControllers();

    /**
     * Returns the value of the '<em><b>Services</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Service}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Services</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getApplicationConcern_Services()
     * @model containment="true"
     * @generated
     */
    EList<Service> getServices();

    /**
     * Returns the value of the '<em><b>Events</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Event}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Events</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getApplicationConcern_Events()
     * @model containment="true"
     * @generated
     */
    EList<Event> getEvents();

    /**
     * Returns the value of the '<em><b>Commands</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Command}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Commands</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getApplicationConcern_Commands()
     * @model containment="true"
     * @generated
     */
    EList<Command> getCommands();

    /**
     * Returns the value of the '<em><b>Queries</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Query}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Queries</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getApplicationConcern_Queries()
     * @model containment="true"
     * @generated
     */
    EList<Query> getQueries();

    /**
     * Returns the value of the '<em><b>Domains</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Domain}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Domains</em>' reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getApplicationConcern_Domains()
     * @model
     * @generated
     */
    EList<Domain> getDomains();

} // ApplicationConcern
