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
import org.eclipse.sirius.web.view.UnsetValue;
import org.eclipse.sirius.web.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Unset Value</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.web.view.impl.UnsetValueImpl#getFeatureName <em>Feature Name</em>}</li>
 * <li>{@link org.eclipse.sirius.web.view.impl.UnsetValueImpl#getElementExpression <em>Element Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UnsetValueImpl extends OperationImpl implements UnsetValue {
    /**
     * The default value of the '{@link #getFeatureName() <em>Feature Name</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getFeatureName()
     * @generated
     * @ordered
     */
    protected static final String FEATURE_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFeatureName() <em>Feature Name</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getFeatureName()
     * @generated
     * @ordered
     */
    protected String featureName = FEATURE_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getElementExpression() <em>Element Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getElementExpression()
     * @generated
     * @ordered
     */
    protected static final String ELEMENT_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getElementExpression() <em>Element Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getElementExpression()
     * @generated
     * @ordered
     */
    protected String elementExpression = ELEMENT_EXPRESSION_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected UnsetValueImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.UNSET_VALUE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getFeatureName() {
        return this.featureName;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setFeatureName(String newFeatureName) {
        String oldFeatureName = this.featureName;
        this.featureName = newFeatureName;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.UNSET_VALUE__FEATURE_NAME, oldFeatureName, this.featureName));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getElementExpression() {
        return this.elementExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setElementExpression(String newElementExpression) {
        String oldElementExpression = this.elementExpression;
        this.elementExpression = newElementExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.UNSET_VALUE__ELEMENT_EXPRESSION, oldElementExpression, this.elementExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case ViewPackage.UNSET_VALUE__FEATURE_NAME:
            return this.getFeatureName();
        case ViewPackage.UNSET_VALUE__ELEMENT_EXPRESSION:
            return this.getElementExpression();
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
        case ViewPackage.UNSET_VALUE__FEATURE_NAME:
            this.setFeatureName((String) newValue);
            return;
        case ViewPackage.UNSET_VALUE__ELEMENT_EXPRESSION:
            this.setElementExpression((String) newValue);
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
        case ViewPackage.UNSET_VALUE__FEATURE_NAME:
            this.setFeatureName(FEATURE_NAME_EDEFAULT);
            return;
        case ViewPackage.UNSET_VALUE__ELEMENT_EXPRESSION:
            this.setElementExpression(ELEMENT_EXPRESSION_EDEFAULT);
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
        case ViewPackage.UNSET_VALUE__FEATURE_NAME:
            return FEATURE_NAME_EDEFAULT == null ? this.featureName != null : !FEATURE_NAME_EDEFAULT.equals(this.featureName);
        case ViewPackage.UNSET_VALUE__ELEMENT_EXPRESSION:
            return ELEMENT_EXPRESSION_EDEFAULT == null ? this.elementExpression != null : !ELEMENT_EXPRESSION_EDEFAULT.equals(this.elementExpression);
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
        result.append(" (featureName: "); //$NON-NLS-1$
        result.append(this.featureName);
        result.append(", elementExpression: "); //$NON-NLS-1$
        result.append(this.elementExpression);
        result.append(')');
        return result.toString();
    }

} // UnsetValueImpl
