/*******************************************************************************
 * Copyright (c) 2023, 2023 Obeo.
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
package org.eclipse.sirius.web.customwidgets.impl;

import java.util.Collection;
import java.util.Objects;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.form.impl.WidgetDescriptionImpl;
import org.eclipse.sirius.web.customwidgets.CustomwidgetsPackage;
import org.eclipse.sirius.web.customwidgets.SliderDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Slider Description</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.web.customwidgets.impl.SliderDescriptionImpl#getMinValueExpression <em>Min Value
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.web.customwidgets.impl.SliderDescriptionImpl#getMaxValueExpression <em>Max Value
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.web.customwidgets.impl.SliderDescriptionImpl#getCurrentValueExpression <em>Current
 * Value Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.web.customwidgets.impl.SliderDescriptionImpl#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SliderDescriptionImpl extends WidgetDescriptionImpl implements SliderDescription {
    /**
     * The default value of the '{@link #getMinValueExpression() <em>Min Value Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getMinValueExpression()
     * @generated
     * @ordered
     */
    protected static final String MIN_VALUE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getMinValueExpression() <em>Min Value Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getMinValueExpression()
     * @generated
     * @ordered
     */
    protected String minValueExpression = MIN_VALUE_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getMaxValueExpression() <em>Max Value Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getMaxValueExpression()
     * @generated
     * @ordered
     */
    protected static final String MAX_VALUE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getMaxValueExpression() <em>Max Value Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getMaxValueExpression()
     * @generated
     * @ordered
     */
    protected String maxValueExpression = MAX_VALUE_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getCurrentValueExpression() <em>Current Value Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getCurrentValueExpression()
     * @generated
     * @ordered
     */
    protected static final String CURRENT_VALUE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getCurrentValueExpression() <em>Current Value Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getCurrentValueExpression()
     * @generated
     * @ordered
     */
    protected String currentValueExpression = CURRENT_VALUE_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getIsEnabledExpression() <em>Is Enabled Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getIsEnabledExpression()
     */
    protected static final String IS_ENABLED_EXPRESSION_EDEFAULT = null;
    /**
     * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getBody()
     */
    protected EList<Operation> body;
    /**
     * The cached value of the '{@link #getIsEnabledExpression() <em>Is Enabled Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getIsEnabledExpression()
     */
    protected String isEnabledExpression = IS_ENABLED_EXPRESSION_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected SliderDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CustomwidgetsPackage.Literals.SLIDER_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getMinValueExpression() {
        return this.minValueExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setMinValueExpression(String newMinValueExpression) {
        String oldMinValueExpression = this.minValueExpression;
        this.minValueExpression = newMinValueExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, CustomwidgetsPackage.SLIDER_DESCRIPTION__MIN_VALUE_EXPRESSION, oldMinValueExpression, this.minValueExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getMaxValueExpression() {
        return this.maxValueExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setMaxValueExpression(String newMaxValueExpression) {
        String oldMaxValueExpression = this.maxValueExpression;
        this.maxValueExpression = newMaxValueExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, CustomwidgetsPackage.SLIDER_DESCRIPTION__MAX_VALUE_EXPRESSION, oldMaxValueExpression, this.maxValueExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getCurrentValueExpression() {
        return this.currentValueExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setCurrentValueExpression(String newCurrentValueExpression) {
        String oldCurrentValueExpression = this.currentValueExpression;
        this.currentValueExpression = newCurrentValueExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, CustomwidgetsPackage.SLIDER_DESCRIPTION__CURRENT_VALUE_EXPRESSION, oldCurrentValueExpression, this.currentValueExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Operation> getBody() {
        if (this.body == null) {
            this.body = new EObjectContainmentEList<>(Operation.class, this, CustomwidgetsPackage.SLIDER_DESCRIPTION__BODY);
        }
        return this.body;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getIsEnabledExpression() {
        return this.isEnabledExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setIsEnabledExpression(String newIsEnabledExpression) {
        String oldIsEnabledExpression = this.isEnabledExpression;
        this.isEnabledExpression = newIsEnabledExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, CustomwidgetsPackage.SLIDER_DESCRIPTION__IS_ENABLED_EXPRESSION, oldIsEnabledExpression, this.isEnabledExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case CustomwidgetsPackage.SLIDER_DESCRIPTION__BODY:
                return ((InternalEList<?>) this.getBody()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case CustomwidgetsPackage.SLIDER_DESCRIPTION__MIN_VALUE_EXPRESSION:
                return this.getMinValueExpression();
            case CustomwidgetsPackage.SLIDER_DESCRIPTION__MAX_VALUE_EXPRESSION:
                return this.getMaxValueExpression();
            case CustomwidgetsPackage.SLIDER_DESCRIPTION__CURRENT_VALUE_EXPRESSION:
                return this.getCurrentValueExpression();
            case CustomwidgetsPackage.SLIDER_DESCRIPTION__BODY:
                return this.getBody();
            case CustomwidgetsPackage.SLIDER_DESCRIPTION__IS_ENABLED_EXPRESSION:
                return this.getIsEnabledExpression();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case CustomwidgetsPackage.SLIDER_DESCRIPTION__MIN_VALUE_EXPRESSION:
                this.setMinValueExpression((String) newValue);
                return;
            case CustomwidgetsPackage.SLIDER_DESCRIPTION__MAX_VALUE_EXPRESSION:
                this.setMaxValueExpression((String) newValue);
                return;
            case CustomwidgetsPackage.SLIDER_DESCRIPTION__CURRENT_VALUE_EXPRESSION:
                this.setCurrentValueExpression((String) newValue);
                return;
            case CustomwidgetsPackage.SLIDER_DESCRIPTION__BODY:
                this.getBody().clear();
                this.getBody().addAll((Collection<? extends Operation>) newValue);
                return;
            case CustomwidgetsPackage.SLIDER_DESCRIPTION__IS_ENABLED_EXPRESSION:
                this.setIsEnabledExpression((String) newValue);
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
            case CustomwidgetsPackage.SLIDER_DESCRIPTION__MIN_VALUE_EXPRESSION:
                this.setMinValueExpression(MIN_VALUE_EXPRESSION_EDEFAULT);
                return;
            case CustomwidgetsPackage.SLIDER_DESCRIPTION__MAX_VALUE_EXPRESSION:
                this.setMaxValueExpression(MAX_VALUE_EXPRESSION_EDEFAULT);
                return;
            case CustomwidgetsPackage.SLIDER_DESCRIPTION__CURRENT_VALUE_EXPRESSION:
                this.setCurrentValueExpression(CURRENT_VALUE_EXPRESSION_EDEFAULT);
                return;
            case CustomwidgetsPackage.SLIDER_DESCRIPTION__BODY:
                this.getBody().clear();
                return;
            case CustomwidgetsPackage.SLIDER_DESCRIPTION__IS_ENABLED_EXPRESSION:
                this.setIsEnabledExpression(IS_ENABLED_EXPRESSION_EDEFAULT);
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
            case CustomwidgetsPackage.SLIDER_DESCRIPTION__MIN_VALUE_EXPRESSION:
                return MIN_VALUE_EXPRESSION_EDEFAULT == null ? this.minValueExpression != null : !MIN_VALUE_EXPRESSION_EDEFAULT.equals(this.minValueExpression);
            case CustomwidgetsPackage.SLIDER_DESCRIPTION__MAX_VALUE_EXPRESSION:
                return MAX_VALUE_EXPRESSION_EDEFAULT == null ? this.maxValueExpression != null : !MAX_VALUE_EXPRESSION_EDEFAULT.equals(this.maxValueExpression);
            case CustomwidgetsPackage.SLIDER_DESCRIPTION__CURRENT_VALUE_EXPRESSION:
                return CURRENT_VALUE_EXPRESSION_EDEFAULT == null ? this.currentValueExpression != null : !CURRENT_VALUE_EXPRESSION_EDEFAULT.equals(this.currentValueExpression);
            case CustomwidgetsPackage.SLIDER_DESCRIPTION__BODY:
                return this.body != null && !this.body.isEmpty();
            case CustomwidgetsPackage.SLIDER_DESCRIPTION__IS_ENABLED_EXPRESSION:
                return !Objects.equals(IS_ENABLED_EXPRESSION_EDEFAULT, this.isEnabledExpression);
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
        result.append(" (minValueExpression: ");
        result.append(this.minValueExpression);
        result.append(", maxValueExpression: ");
        result.append(this.maxValueExpression);
        result.append(", currentValueExpression: ");
        result.append(this.currentValueExpression);
        result.append(", isEnabledExpression: ");
        result.append(this.isEnabledExpression);
        result.append(')');
        return result.toString();
    }

} // SliderDescriptionImpl
