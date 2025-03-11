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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Controller</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.Controller#getCalls <em>Calls</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getController()
 * @model
 * @generated
 */
public interface Controller extends NamedElement, MessageEmitter, MessageListener {
    /**
     * Returns the value of the '<em><b>Calls</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Service}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Calls</em>' reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getController_Calls()
     * @model
     * @generated
     */
    EList<Service> getCalls();

} // Controller
