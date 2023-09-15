/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.view.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.Let;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Let</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.impl.LetImpl#getVariableName <em>Variable Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.LetImpl#getValueExpression <em>Value Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LetImpl extends OperationImpl implements Let {
    /**
     * The default value of the '{@link #getVariableName() <em>Variable Name</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getVariableName()
     * @generated
     * @ordered
     */
    protected static final String VARIABLE_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getVariableName() <em>Variable Name</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getVariableName()
     * @generated
     * @ordered
     */
    protected String variableName = VARIABLE_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getValueExpression() <em>Value Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getValueExpression()
     * @generated
     * @ordered
     */
    protected static final String VALUE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getValueExpression() <em>Value Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getValueExpression()
     * @generated
     * @ordered
     */
    protected String valueExpression = VALUE_EXPRESSION_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected LetImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.LET;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getVariableName() {
        return this.variableName;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setVariableName(String newVariableName) {
        String oldVariableName = this.variableName;
        this.variableName = newVariableName;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.LET__VARIABLE_NAME, oldVariableName, this.variableName));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getValueExpression() {
        return this.valueExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setValueExpression(String newValueExpression) {
        String oldValueExpression = this.valueExpression;
        this.valueExpression = newValueExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.LET__VALUE_EXPRESSION, oldValueExpression, this.valueExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ViewPackage.LET__VARIABLE_NAME:
                return this.getVariableName();
            case ViewPackage.LET__VALUE_EXPRESSION:
                return this.getValueExpression();
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
            case ViewPackage.LET__VARIABLE_NAME:
                this.setVariableName((String) newValue);
                return;
            case ViewPackage.LET__VALUE_EXPRESSION:
                this.setValueExpression((String) newValue);
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
            case ViewPackage.LET__VARIABLE_NAME:
                this.setVariableName(VARIABLE_NAME_EDEFAULT);
                return;
            case ViewPackage.LET__VALUE_EXPRESSION:
                this.setValueExpression(VALUE_EXPRESSION_EDEFAULT);
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
            case ViewPackage.LET__VARIABLE_NAME:
                return VARIABLE_NAME_EDEFAULT == null ? this.variableName != null : !VARIABLE_NAME_EDEFAULT.equals(this.variableName);
            case ViewPackage.LET__VALUE_EXPRESSION:
                return VALUE_EXPRESSION_EDEFAULT == null ? this.valueExpression != null : !VALUE_EXPRESSION_EDEFAULT.equals(this.valueExpression);
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
        result.append(" (variableName: ");
        result.append(this.variableName);
        result.append(", valueExpression: ");
        result.append(this.valueExpression);
        result.append(')');
        return result.toString();
    }

} // LetImpl
