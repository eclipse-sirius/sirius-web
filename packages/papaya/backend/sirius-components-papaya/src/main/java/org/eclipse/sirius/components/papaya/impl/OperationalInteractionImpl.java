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
package org.eclipse.sirius.components.papaya.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.papaya.OperationalInteraction;
import org.eclipse.sirius.components.papaya.PapayaPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Operational Interaction</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.papaya.impl.OperationalInteractionImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.papaya.impl.OperationalInteractionImpl#getFrequency <em>Frequency</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.papaya.impl.OperationalInteractionImpl#getProtocol <em>Protocol</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.papaya.impl.OperationalInteractionImpl#getLatency <em>Latency</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OperationalInteractionImpl extends NamedElementImpl implements OperationalInteraction {
    /**
     * The default value of the '{@link #getType() <em>Type</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getType()
     * @generated
     * @ordered
     */
    protected static final String TYPE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getType() <em>Type</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getType()
     * @generated
     * @ordered
     */
    protected String type = TYPE_EDEFAULT;

    /**
	 * The default value of the '{@link #getFrequency() <em>Frequency</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getFrequency()
	 * @generated
	 * @ordered
	 */
    protected static final String FREQUENCY_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getFrequency() <em>Frequency</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getFrequency()
	 * @generated
	 * @ordered
	 */
    protected String frequency = FREQUENCY_EDEFAULT;

    /**
	 * The default value of the '{@link #getProtocol() <em>Protocol</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getProtocol()
	 * @generated
	 * @ordered
	 */
    protected static final String PROTOCOL_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getProtocol() <em>Protocol</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getProtocol()
	 * @generated
	 * @ordered
	 */
    protected String protocol = PROTOCOL_EDEFAULT;

    /**
	 * The default value of the '{@link #getLatency() <em>Latency</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getLatency()
	 * @generated
	 * @ordered
	 */
    protected static final String LATENCY_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getLatency() <em>Latency</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getLatency()
	 * @generated
	 * @ordered
	 */
    protected String latency = LATENCY_EDEFAULT;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected OperationalInteractionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return PapayaPackage.Literals.OPERATIONAL_INTERACTION;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getType() {
		return type;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setType(String newType) {
		String oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.OPERATIONAL_INTERACTION__TYPE, oldType, type));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getFrequency() {
		return frequency;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setFrequency(String newFrequency) {
		String oldFrequency = frequency;
		frequency = newFrequency;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.OPERATIONAL_INTERACTION__FREQUENCY, oldFrequency, frequency));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getProtocol() {
		return protocol;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setProtocol(String newProtocol) {
		String oldProtocol = protocol;
		protocol = newProtocol;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.OPERATIONAL_INTERACTION__PROTOCOL, oldProtocol, protocol));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getLatency() {
		return latency;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setLatency(String newLatency) {
		String oldLatency = latency;
		latency = newLatency;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.OPERATIONAL_INTERACTION__LATENCY, oldLatency, latency));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case PapayaPackage.OPERATIONAL_INTERACTION__TYPE:
				return getType();
			case PapayaPackage.OPERATIONAL_INTERACTION__FREQUENCY:
				return getFrequency();
			case PapayaPackage.OPERATIONAL_INTERACTION__PROTOCOL:
				return getProtocol();
			case PapayaPackage.OPERATIONAL_INTERACTION__LATENCY:
				return getLatency();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eSet(int featureID, Object newValue) {
		switch (featureID)
		{
			case PapayaPackage.OPERATIONAL_INTERACTION__TYPE:
				setType((String)newValue);
				return;
			case PapayaPackage.OPERATIONAL_INTERACTION__FREQUENCY:
				setFrequency((String)newValue);
				return;
			case PapayaPackage.OPERATIONAL_INTERACTION__PROTOCOL:
				setProtocol((String)newValue);
				return;
			case PapayaPackage.OPERATIONAL_INTERACTION__LATENCY:
				setLatency((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eUnset(int featureID) {
		switch (featureID)
		{
			case PapayaPackage.OPERATIONAL_INTERACTION__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case PapayaPackage.OPERATIONAL_INTERACTION__FREQUENCY:
				setFrequency(FREQUENCY_EDEFAULT);
				return;
			case PapayaPackage.OPERATIONAL_INTERACTION__PROTOCOL:
				setProtocol(PROTOCOL_EDEFAULT);
				return;
			case PapayaPackage.OPERATIONAL_INTERACTION__LATENCY:
				setLatency(LATENCY_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean eIsSet(int featureID) {
		switch (featureID)
		{
			case PapayaPackage.OPERATIONAL_INTERACTION__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case PapayaPackage.OPERATIONAL_INTERACTION__FREQUENCY:
				return FREQUENCY_EDEFAULT == null ? frequency != null : !FREQUENCY_EDEFAULT.equals(frequency);
			case PapayaPackage.OPERATIONAL_INTERACTION__PROTOCOL:
				return PROTOCOL_EDEFAULT == null ? protocol != null : !PROTOCOL_EDEFAULT.equals(protocol);
			case PapayaPackage.OPERATIONAL_INTERACTION__LATENCY:
				return LATENCY_EDEFAULT == null ? latency != null : !LATENCY_EDEFAULT.equals(latency);
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (type: ");
		result.append(type);
		result.append(", frequency: ");
		result.append(frequency);
		result.append(", protocol: ");
		result.append(protocol);
		result.append(", latency: ");
		result.append(latency);
		result.append(')');
		return result.toString();
	}

} // OperationalInteractionImpl
