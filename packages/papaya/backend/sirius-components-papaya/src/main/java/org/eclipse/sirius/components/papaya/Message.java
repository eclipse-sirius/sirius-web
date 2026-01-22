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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Message</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.papaya.Message#getEmittedBy <em>Emitted By</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.papaya.Message#getListenedBy <em>Listened By</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getMessage()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Message extends NamedElement {
    /**
     * Returns the value of the '<em><b>Emitted By</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Service}. It is bidirectional and its opposite is
     * '{@link org.eclipse.sirius.components.papaya.Service#getEmittedMessages <em>Emitted Messages</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Emitted By</em>' reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getMessage_EmittedBy()
     * @see org.eclipse.sirius.components.papaya.Service#getEmittedMessages
     * @model opposite="emittedMessages"
     * @generated
     */
    EList<Publication> getEmittedBy();

    /**
     * Returns the value of the '<em><b>Listened By</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Service}. It is bidirectional and its opposite is
     * '{@link org.eclipse.sirius.components.papaya.Service#getListenedMessages <em>Listened Messages</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Listened By</em>' reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getMessage_ListenedBy()
     * @see org.eclipse.sirius.components.papaya.Service#getListenedMessages
     * @model opposite="listenedMessages"
     * @generated
     */
    EList<Subscription> getListenedBy();

} // Message
