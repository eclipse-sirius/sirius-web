/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Publication</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.papaya.Publication#getChannel <em>Channel</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.papaya.Publication#getMessage <em>Message</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getPublication()
 * @model
 * @generated
 */
public interface Publication extends EObject {
    /**
	 * Returns the value of the '<em><b>Channel</b></em>' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Channel</em>' reference.
	 * @see #setChannel(Channel)
	 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getPublication_Channel()
	 * @model
	 * @generated
	 */
    Channel getChannel();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.papaya.Publication#getChannel <em>Channel</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Channel</em>' reference.
	 * @see #getChannel()
	 * @generated
	 */
    void setChannel(Channel value);

    /**
	 * Returns the value of the '<em><b>Message</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.sirius.components.papaya.Message#getEmittedBy <em>Emitted By</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Message</em>' reference.
	 * @see #setMessage(Message)
	 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getPublication_Message()
	 * @see org.eclipse.sirius.components.papaya.Message#getEmittedBy
	 * @model opposite="emittedBy"
	 * @generated
	 */
    Message getMessage();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.papaya.Publication#getMessage <em>Message</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Message</em>' reference.
	 * @see #getMessage()
	 * @generated
	 */
    void setMessage(Message value);

} // Publication
