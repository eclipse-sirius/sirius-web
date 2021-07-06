/**
 * Copyright (c) 2021 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.sirius.web.view.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.sirius.web.view.Conditional;
import org.eclipse.sirius.web.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Conditional</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.web.view.impl.ConditionalImpl#getCondition <em>Condition</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ConditionalImpl extends MinimalEObjectImpl.Container implements Conditional {
    /**
     * The default value of the '{@link #getCondition() <em>Condition</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getCondition()
     * @generated
     * @ordered
     */
    protected static final String CONDITION_EDEFAULT = "aql:false"; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getCondition() <em>Condition</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getCondition()
     * @generated
     * @ordered
     */
    protected String condition = CONDITION_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ConditionalImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.CONDITIONAL;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getCondition() {
        return this.condition;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setCondition(String newCondition) {
        String oldCondition = this.condition;
        this.condition = newCondition;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.CONDITIONAL__CONDITION, oldCondition, this.condition));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case ViewPackage.CONDITIONAL__CONDITION:
            return this.getCondition();
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
        case ViewPackage.CONDITIONAL__CONDITION:
            this.setCondition((String) newValue);
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
        case ViewPackage.CONDITIONAL__CONDITION:
            this.setCondition(CONDITION_EDEFAULT);
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
        case ViewPackage.CONDITIONAL__CONDITION:
            return CONDITION_EDEFAULT == null ? this.condition != null : !CONDITION_EDEFAULT.equals(this.condition);
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
        result.append(" (condition: "); //$NON-NLS-1$
        result.append(this.condition);
        result.append(')');
        return result.toString();
    }

} // ConditionalImpl
