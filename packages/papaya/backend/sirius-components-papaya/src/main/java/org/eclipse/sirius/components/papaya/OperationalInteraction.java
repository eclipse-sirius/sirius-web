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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Operational Interaction</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.papaya.OperationalInteraction#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.papaya.OperationalInteraction#getFrequency <em>Frequency</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.papaya.OperationalInteraction#getProtocol <em>Protocol</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.papaya.OperationalInteraction#getLatency <em>Latency</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getOperationalInteraction()
 * @model
 * @generated
 */
public interface OperationalInteraction extends NamedElement {
    /**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getOperationalInteraction_Type()
	 * @model
	 * @generated
	 */
    String getType();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.papaya.OperationalInteraction#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
    void setType(String value);

    /**
	 * Returns the value of the '<em><b>Frequency</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Frequency</em>' attribute.
	 * @see #setFrequency(String)
	 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getOperationalInteraction_Frequency()
	 * @model
	 * @generated
	 */
    String getFrequency();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.papaya.OperationalInteraction#getFrequency <em>Frequency</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Frequency</em>' attribute.
	 * @see #getFrequency()
	 * @generated
	 */
    void setFrequency(String value);

    /**
	 * Returns the value of the '<em><b>Protocol</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Protocol</em>' attribute.
	 * @see #setProtocol(String)
	 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getOperationalInteraction_Protocol()
	 * @model
	 * @generated
	 */
    String getProtocol();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.papaya.OperationalInteraction#getProtocol <em>Protocol</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Protocol</em>' attribute.
	 * @see #getProtocol()
	 * @generated
	 */
    void setProtocol(String value);

    /**
	 * Returns the value of the '<em><b>Latency</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Latency</em>' attribute.
	 * @see #setLatency(String)
	 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getOperationalInteraction_Latency()
	 * @model
	 * @generated
	 */
    String getLatency();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.papaya.OperationalInteraction#getLatency <em>Latency</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Latency</em>' attribute.
	 * @see #getLatency()
	 * @generated
	 */
    void setLatency(String value);

} // OperationalInteraction
