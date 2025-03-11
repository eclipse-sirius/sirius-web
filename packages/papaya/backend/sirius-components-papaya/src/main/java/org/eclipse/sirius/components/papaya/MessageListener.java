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
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Message Listener</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.MessageListener#getListenedMessages <em>Listened Messages</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getMessageListener()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface MessageListener extends EObject {
    /**
     * Returns the value of the '<em><b>Listened Messages</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Message}. It is bidirectional and its opposite is
     * '{@link org.eclipse.sirius.components.papaya.Message#getListenedBy <em>Listened By</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Listened Messages</em>' reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getMessageListener_ListenedMessages()
     * @see org.eclipse.sirius.components.papaya.Message#getListenedBy
     * @model opposite="listenedBy"
     * @generated
     */
    EList<Message> getListenedMessages();

} // MessageListener
