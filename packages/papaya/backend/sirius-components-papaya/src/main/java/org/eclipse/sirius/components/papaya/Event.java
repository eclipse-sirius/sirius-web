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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Event</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.Event#getCausedBy <em>Caused By</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getEvent()
 * @model
 * @generated
 */
public interface Event extends Message {
    /**
     * Returns the value of the '<em><b>Caused By</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Message}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Caused By</em>' reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getEvent_CausedBy()
     * @model
     * @generated
     */
    EList<Message> getCausedBy();

} // Event
