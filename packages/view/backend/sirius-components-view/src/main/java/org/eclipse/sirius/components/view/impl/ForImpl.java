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
package org.eclipse.sirius.components.view.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.For;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>For</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.impl.ForImpl#getExpression <em>Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.ForImpl#getIteratorName <em>Iterator Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ForImpl extends OperationImpl implements For {
    /**
     * The default value of the '{@link #getExpression() <em>Expression</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getExpression()
     * @generated
     * @ordered
     */
    protected static final String EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getExpression() <em>Expression</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getExpression()
     * @generated
     * @ordered
     */
    protected String expression = EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getIteratorName() <em>Iterator Name</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getIteratorName()
     * @generated
     * @ordered
     */
    protected static final String ITERATOR_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIteratorName() <em>Iterator Name</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getIteratorName()
     * @generated
     * @ordered
     */
    protected String iteratorName = ITERATOR_NAME_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ForImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.FOR;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getExpression() {
        return this.expression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setExpression(String newExpression) {
        String oldExpression = this.expression;
        this.expression = newExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.FOR__EXPRESSION, oldExpression, this.expression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getIteratorName() {
        return this.iteratorName;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setIteratorName(String newIteratorName) {
        String oldIteratorName = this.iteratorName;
        this.iteratorName = newIteratorName;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.FOR__ITERATOR_NAME, oldIteratorName, this.iteratorName));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ViewPackage.FOR__EXPRESSION:
                return this.getExpression();
            case ViewPackage.FOR__ITERATOR_NAME:
                return this.getIteratorName();
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
            case ViewPackage.FOR__EXPRESSION:
                this.setExpression((String) newValue);
                return;
            case ViewPackage.FOR__ITERATOR_NAME:
                this.setIteratorName((String) newValue);
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
            case ViewPackage.FOR__EXPRESSION:
                this.setExpression(EXPRESSION_EDEFAULT);
                return;
            case ViewPackage.FOR__ITERATOR_NAME:
                this.setIteratorName(ITERATOR_NAME_EDEFAULT);
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
            case ViewPackage.FOR__EXPRESSION:
                return EXPRESSION_EDEFAULT == null ? this.expression != null : !EXPRESSION_EDEFAULT.equals(this.expression);
            case ViewPackage.FOR__ITERATOR_NAME:
                return ITERATOR_NAME_EDEFAULT == null ? this.iteratorName != null : !ITERATOR_NAME_EDEFAULT.equals(this.iteratorName);
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
        result.append(" (expression: ");
        result.append(this.expression);
        result.append(", iteratorName: ");
        result.append(this.iteratorName);
        result.append(')');
        return result.toString();
    }

} // ForImpl
