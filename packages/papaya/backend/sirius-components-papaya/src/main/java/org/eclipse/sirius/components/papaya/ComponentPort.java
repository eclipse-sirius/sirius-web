/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Component Port</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.ComponentPort#getProtocol <em>Protocol</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getComponentPort()
 * @model
 * @generated
 */
public interface ComponentPort extends NamedElement {
    /**
     * Returns the value of the '<em><b>Protocol</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Protocol</em>' attribute.
     * @see #setProtocol(String)
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getComponentPort_Protocol()
     * @model
     * @generated
     */
    String getProtocol();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.papaya.ComponentPort#getProtocol <em>Protocol</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Protocol</em>' attribute.
     * @see #getProtocol()
     * @generated
     */
    void setProtocol(String value);

} // ComponentPort
