/**
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.sirius.components.view.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.BarChartDescription;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Bar Chart Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.impl.BarChartDescriptionImpl#getValuesExpression <em>Values
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.BarChartDescriptionImpl#getKeysExpression <em>Keys
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.BarChartDescriptionImpl#getYAxisLabelExpression <em>YAxis Label
 * Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BarChartDescriptionImpl extends WidgetDescriptionImpl implements BarChartDescription {
    /**
     * The default value of the '{@link #getValuesExpression() <em>Values Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getValuesExpression()
     * @generated
     * @ordered
     */
    protected static final String VALUES_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getValuesExpression() <em>Values Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getValuesExpression()
     * @generated
     * @ordered
     */
    protected String valuesExpression = VALUES_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getKeysExpression() <em>Keys Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getKeysExpression()
     * @generated
     * @ordered
     */
    protected static final String KEYS_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getKeysExpression() <em>Keys Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getKeysExpression()
     * @generated
     * @ordered
     */
    protected String keysExpression = KEYS_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getYAxisLabelExpression() <em>YAxis Label Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getYAxisLabelExpression()
     * @generated
     * @ordered
     */
    protected static final String YAXIS_LABEL_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getYAxisLabelExpression() <em>YAxis Label Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getYAxisLabelExpression()
     * @generated
     * @ordered
     */
    protected String yAxisLabelExpression = YAXIS_LABEL_EXPRESSION_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected BarChartDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.BAR_CHART_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getValuesExpression() {
        return this.valuesExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setValuesExpression(String newValuesExpression) {
        String oldValuesExpression = this.valuesExpression;
        this.valuesExpression = newValuesExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.BAR_CHART_DESCRIPTION__VALUES_EXPRESSION, oldValuesExpression, this.valuesExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getKeysExpression() {
        return this.keysExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setKeysExpression(String newKeysExpression) {
        String oldKeysExpression = this.keysExpression;
        this.keysExpression = newKeysExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.BAR_CHART_DESCRIPTION__KEYS_EXPRESSION, oldKeysExpression, this.keysExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getYAxisLabelExpression() {
        return this.yAxisLabelExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setYAxisLabelExpression(String newYAxisLabelExpression) {
        String oldYAxisLabelExpression = this.yAxisLabelExpression;
        this.yAxisLabelExpression = newYAxisLabelExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.BAR_CHART_DESCRIPTION__YAXIS_LABEL_EXPRESSION, oldYAxisLabelExpression, this.yAxisLabelExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case ViewPackage.BAR_CHART_DESCRIPTION__VALUES_EXPRESSION:
            return this.getValuesExpression();
        case ViewPackage.BAR_CHART_DESCRIPTION__KEYS_EXPRESSION:
            return this.getKeysExpression();
        case ViewPackage.BAR_CHART_DESCRIPTION__YAXIS_LABEL_EXPRESSION:
            return this.getYAxisLabelExpression();
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
        case ViewPackage.BAR_CHART_DESCRIPTION__VALUES_EXPRESSION:
            this.setValuesExpression((String) newValue);
            return;
        case ViewPackage.BAR_CHART_DESCRIPTION__KEYS_EXPRESSION:
            this.setKeysExpression((String) newValue);
            return;
        case ViewPackage.BAR_CHART_DESCRIPTION__YAXIS_LABEL_EXPRESSION:
            this.setYAxisLabelExpression((String) newValue);
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
        case ViewPackage.BAR_CHART_DESCRIPTION__VALUES_EXPRESSION:
            this.setValuesExpression(VALUES_EXPRESSION_EDEFAULT);
            return;
        case ViewPackage.BAR_CHART_DESCRIPTION__KEYS_EXPRESSION:
            this.setKeysExpression(KEYS_EXPRESSION_EDEFAULT);
            return;
        case ViewPackage.BAR_CHART_DESCRIPTION__YAXIS_LABEL_EXPRESSION:
            this.setYAxisLabelExpression(YAXIS_LABEL_EXPRESSION_EDEFAULT);
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
        case ViewPackage.BAR_CHART_DESCRIPTION__VALUES_EXPRESSION:
            return VALUES_EXPRESSION_EDEFAULT == null ? this.valuesExpression != null : !VALUES_EXPRESSION_EDEFAULT.equals(this.valuesExpression);
        case ViewPackage.BAR_CHART_DESCRIPTION__KEYS_EXPRESSION:
            return KEYS_EXPRESSION_EDEFAULT == null ? this.keysExpression != null : !KEYS_EXPRESSION_EDEFAULT.equals(this.keysExpression);
        case ViewPackage.BAR_CHART_DESCRIPTION__YAXIS_LABEL_EXPRESSION:
            return YAXIS_LABEL_EXPRESSION_EDEFAULT == null ? this.yAxisLabelExpression != null : !YAXIS_LABEL_EXPRESSION_EDEFAULT.equals(this.yAxisLabelExpression);
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
        result.append(" (valuesExpression: "); //$NON-NLS-1$
        result.append(this.valuesExpression);
        result.append(", keysExpression: "); //$NON-NLS-1$
        result.append(this.keysExpression);
        result.append(", yAxisLabelExpression: "); //$NON-NLS-1$
        result.append(this.yAxisLabelExpression);
        result.append(')');
        return result.toString();
    }

} // BarChartDescriptionImpl
