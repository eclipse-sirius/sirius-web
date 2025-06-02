/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
 * <li>{@link org.eclipse.sirius.components.papaya.impl.OperationalInteractionImpl#getType <em>Type</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.OperationalInteractionImpl#getFrequency <em>Frequency</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.OperationalInteractionImpl#getProtocol <em>Protocol</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.OperationalInteractionImpl#getLatency <em>Latency</em>}</li>
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
     * The default value of the '{@link #getFrequency() <em>Frequency</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getFrequency()
     * @generated
     * @ordered
     */
    protected static final String FREQUENCY_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFrequency() <em>Frequency</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getFrequency()
     * @generated
     * @ordered
     */
    protected String frequency = FREQUENCY_EDEFAULT;

    /**
     * The default value of the '{@link #getProtocol() <em>Protocol</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getProtocol()
     * @generated
     * @ordered
     */
    protected static final String PROTOCOL_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getProtocol() <em>Protocol</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getProtocol()
     * @generated
     * @ordered
     */
    protected String protocol = PROTOCOL_EDEFAULT;

    /**
     * The default value of the '{@link #getLatency() <em>Latency</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getLatency()
     * @generated
     * @ordered
     */
    protected static final String LATENCY_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getLatency() <em>Latency</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getLatency()
     * @generated
     * @ordered
     */
    protected String latency = LATENCY_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected OperationalInteractionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapayaPackage.Literals.OPERATIONAL_INTERACTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getType() {
        return this.type;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setType(String newType) {
        String oldType = this.type;
        this.type = newType;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.OPERATIONAL_INTERACTION__TYPE, oldType, this.type));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getFrequency() {
        return this.frequency;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setFrequency(String newFrequency) {
        String oldFrequency = this.frequency;
        this.frequency = newFrequency;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.OPERATIONAL_INTERACTION__FREQUENCY, oldFrequency, this.frequency));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getProtocol() {
        return this.protocol;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setProtocol(String newProtocol) {
        String oldProtocol = this.protocol;
        this.protocol = newProtocol;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.OPERATIONAL_INTERACTION__PROTOCOL, oldProtocol, this.protocol));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getLatency() {
        return this.latency;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setLatency(String newLatency) {
        String oldLatency = this.latency;
        this.latency = newLatency;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.OPERATIONAL_INTERACTION__LATENCY, oldLatency, this.latency));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case PapayaPackage.OPERATIONAL_INTERACTION__TYPE:
                return this.getType();
            case PapayaPackage.OPERATIONAL_INTERACTION__FREQUENCY:
                return this.getFrequency();
            case PapayaPackage.OPERATIONAL_INTERACTION__PROTOCOL:
                return this.getProtocol();
            case PapayaPackage.OPERATIONAL_INTERACTION__LATENCY:
                return this.getLatency();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case PapayaPackage.OPERATIONAL_INTERACTION__TYPE:
                this.setType((String) newValue);
                return;
            case PapayaPackage.OPERATIONAL_INTERACTION__FREQUENCY:
                this.setFrequency((String) newValue);
                return;
            case PapayaPackage.OPERATIONAL_INTERACTION__PROTOCOL:
                this.setProtocol((String) newValue);
                return;
            case PapayaPackage.OPERATIONAL_INTERACTION__LATENCY:
                this.setLatency((String) newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case PapayaPackage.OPERATIONAL_INTERACTION__TYPE:
                this.setType(TYPE_EDEFAULT);
                return;
            case PapayaPackage.OPERATIONAL_INTERACTION__FREQUENCY:
                this.setFrequency(FREQUENCY_EDEFAULT);
                return;
            case PapayaPackage.OPERATIONAL_INTERACTION__PROTOCOL:
                this.setProtocol(PROTOCOL_EDEFAULT);
                return;
            case PapayaPackage.OPERATIONAL_INTERACTION__LATENCY:
                this.setLatency(LATENCY_EDEFAULT);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case PapayaPackage.OPERATIONAL_INTERACTION__TYPE:
                return TYPE_EDEFAULT == null ? this.type != null : !TYPE_EDEFAULT.equals(this.type);
            case PapayaPackage.OPERATIONAL_INTERACTION__FREQUENCY:
                return FREQUENCY_EDEFAULT == null ? this.frequency != null : !FREQUENCY_EDEFAULT.equals(this.frequency);
            case PapayaPackage.OPERATIONAL_INTERACTION__PROTOCOL:
                return PROTOCOL_EDEFAULT == null ? this.protocol != null : !PROTOCOL_EDEFAULT.equals(this.protocol);
            case PapayaPackage.OPERATIONAL_INTERACTION__LATENCY:
                return LATENCY_EDEFAULT == null ? this.latency != null : !LATENCY_EDEFAULT.equals(this.latency);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String toString() {
        if (this.eIsProxy())
            return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (type: ");
        result.append(this.type);
        result.append(", frequency: ");
        result.append(this.frequency);
        result.append(", protocol: ");
        result.append(this.protocol);
        result.append(", latency: ");
        result.append(this.latency);
        result.append(')');
        return result.toString();
    }

} // OperationalInteractionImpl
