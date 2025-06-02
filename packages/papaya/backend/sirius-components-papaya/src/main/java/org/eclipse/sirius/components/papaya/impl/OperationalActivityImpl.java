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
import org.eclipse.sirius.components.papaya.OperationalActivity;
import org.eclipse.sirius.components.papaya.PapayaPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Operational Activity</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.OperationalActivityImpl#getPrecondition
 * <em>Precondition</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.impl.OperationalActivityImpl#getPostcondition
 * <em>Postcondition</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OperationalActivityImpl extends NamedElementImpl implements OperationalActivity {
    /**
     * The default value of the '{@link #getPrecondition() <em>Precondition</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getPrecondition()
     * @generated
     * @ordered
     */
    protected static final String PRECONDITION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPrecondition() <em>Precondition</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getPrecondition()
     * @generated
     * @ordered
     */
    protected String precondition = PRECONDITION_EDEFAULT;

    /**
     * The default value of the '{@link #getPostcondition() <em>Postcondition</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getPostcondition()
     * @generated
     * @ordered
     */
    protected static final String POSTCONDITION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPostcondition() <em>Postcondition</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getPostcondition()
     * @generated
     * @ordered
     */
    protected String postcondition = POSTCONDITION_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected OperationalActivityImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PapayaPackage.Literals.OPERATIONAL_ACTIVITY;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getPrecondition() {
        return this.precondition;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setPrecondition(String newPrecondition) {
        String oldPrecondition = this.precondition;
        this.precondition = newPrecondition;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.OPERATIONAL_ACTIVITY__PRECONDITION, oldPrecondition, this.precondition));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getPostcondition() {
        return this.postcondition;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setPostcondition(String newPostcondition) {
        String oldPostcondition = this.postcondition;
        this.postcondition = newPostcondition;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, PapayaPackage.OPERATIONAL_ACTIVITY__POSTCONDITION, oldPostcondition, this.postcondition));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case PapayaPackage.OPERATIONAL_ACTIVITY__PRECONDITION:
                return this.getPrecondition();
            case PapayaPackage.OPERATIONAL_ACTIVITY__POSTCONDITION:
                return this.getPostcondition();
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
            case PapayaPackage.OPERATIONAL_ACTIVITY__PRECONDITION:
                this.setPrecondition((String) newValue);
                return;
            case PapayaPackage.OPERATIONAL_ACTIVITY__POSTCONDITION:
                this.setPostcondition((String) newValue);
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
            case PapayaPackage.OPERATIONAL_ACTIVITY__PRECONDITION:
                this.setPrecondition(PRECONDITION_EDEFAULT);
                return;
            case PapayaPackage.OPERATIONAL_ACTIVITY__POSTCONDITION:
                this.setPostcondition(POSTCONDITION_EDEFAULT);
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
            case PapayaPackage.OPERATIONAL_ACTIVITY__PRECONDITION:
                return PRECONDITION_EDEFAULT == null ? this.precondition != null : !PRECONDITION_EDEFAULT.equals(this.precondition);
            case PapayaPackage.OPERATIONAL_ACTIVITY__POSTCONDITION:
                return POSTCONDITION_EDEFAULT == null ? this.postcondition != null : !POSTCONDITION_EDEFAULT.equals(this.postcondition);
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
        result.append(" (precondition: ");
        result.append(this.precondition);
        result.append(", postcondition: ");
        result.append(this.postcondition);
        result.append(')');
        return result.toString();
    }

} // OperationalActivityImpl
