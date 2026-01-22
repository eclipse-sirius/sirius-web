/*******************************************************************************
 * Copyright (c) 2021, 2026 Obeo.
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
package org.eclipse.sirius.components.view.form.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.SliderDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Slider Description</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.SliderDescriptionImpl#getMinValueExpression <em>Min Value
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.SliderDescriptionImpl#getMaxValueExpression <em>Max Value
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.SliderDescriptionImpl#getCurrentValueExpression <em>Current
 * Value Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.SliderDescriptionImpl#getBody <em>Body</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.SliderDescriptionImpl#getIsEnabledExpression <em>Is Enabled
 * Expression</em>}</li>
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
	 * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
    protected EList<Operation> body;

    /**
     * The default value of the '{@link #getIsEnabledExpression() <em>Is Enabled Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIsEnabledExpression()
     * @generated
     * @ordered
     */
    protected static final String IS_ENABLED_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIsEnabledExpression() <em>Is Enabled Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIsEnabledExpression()
     * @generated
     * @ordered
     */
    protected String isEnabledExpression = IS_ENABLED_EXPRESSION_EDEFAULT;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected SliderDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return FormPackage.Literals.SLIDER_DESCRIPTION;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getMinValueExpression() {
		return minValueExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setMinValueExpression(String newMinValueExpression) {
		String oldMinValueExpression = minValueExpression;
		minValueExpression = newMinValueExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.SLIDER_DESCRIPTION__MIN_VALUE_EXPRESSION, oldMinValueExpression, minValueExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getMaxValueExpression() {
		return maxValueExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setMaxValueExpression(String newMaxValueExpression) {
		String oldMaxValueExpression = maxValueExpression;
		maxValueExpression = newMaxValueExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.SLIDER_DESCRIPTION__MAX_VALUE_EXPRESSION, oldMaxValueExpression, maxValueExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getCurrentValueExpression() {
		return currentValueExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setCurrentValueExpression(String newCurrentValueExpression) {
		String oldCurrentValueExpression = currentValueExpression;
		currentValueExpression = newCurrentValueExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.SLIDER_DESCRIPTION__CURRENT_VALUE_EXPRESSION, oldCurrentValueExpression, currentValueExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Operation> getBody() {
		if (body == null)
		{
			body = new EObjectContainmentEList<Operation>(Operation.class, this, FormPackage.SLIDER_DESCRIPTION__BODY);
		}
		return body;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getIsEnabledExpression() {
		return isEnabledExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setIsEnabledExpression(String newIsEnabledExpression) {
		String oldIsEnabledExpression = isEnabledExpression;
		isEnabledExpression = newIsEnabledExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.SLIDER_DESCRIPTION__IS_ENABLED_EXPRESSION, oldIsEnabledExpression, isEnabledExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case FormPackage.SLIDER_DESCRIPTION__BODY:
				return ((InternalEList<?>)getBody()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case FormPackage.SLIDER_DESCRIPTION__MIN_VALUE_EXPRESSION:
				return getMinValueExpression();
			case FormPackage.SLIDER_DESCRIPTION__MAX_VALUE_EXPRESSION:
				return getMaxValueExpression();
			case FormPackage.SLIDER_DESCRIPTION__CURRENT_VALUE_EXPRESSION:
				return getCurrentValueExpression();
			case FormPackage.SLIDER_DESCRIPTION__BODY:
				return getBody();
			case FormPackage.SLIDER_DESCRIPTION__IS_ENABLED_EXPRESSION:
				return getIsEnabledExpression();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
		switch (featureID)
		{
			case FormPackage.SLIDER_DESCRIPTION__MIN_VALUE_EXPRESSION:
				setMinValueExpression((String)newValue);
				return;
			case FormPackage.SLIDER_DESCRIPTION__MAX_VALUE_EXPRESSION:
				setMaxValueExpression((String)newValue);
				return;
			case FormPackage.SLIDER_DESCRIPTION__CURRENT_VALUE_EXPRESSION:
				setCurrentValueExpression((String)newValue);
				return;
			case FormPackage.SLIDER_DESCRIPTION__BODY:
				getBody().clear();
				getBody().addAll((Collection<? extends Operation>)newValue);
				return;
			case FormPackage.SLIDER_DESCRIPTION__IS_ENABLED_EXPRESSION:
				setIsEnabledExpression((String)newValue);
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
			case FormPackage.SLIDER_DESCRIPTION__MIN_VALUE_EXPRESSION:
				setMinValueExpression(MIN_VALUE_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.SLIDER_DESCRIPTION__MAX_VALUE_EXPRESSION:
				setMaxValueExpression(MAX_VALUE_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.SLIDER_DESCRIPTION__CURRENT_VALUE_EXPRESSION:
				setCurrentValueExpression(CURRENT_VALUE_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.SLIDER_DESCRIPTION__BODY:
				getBody().clear();
				return;
			case FormPackage.SLIDER_DESCRIPTION__IS_ENABLED_EXPRESSION:
				setIsEnabledExpression(IS_ENABLED_EXPRESSION_EDEFAULT);
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
			case FormPackage.SLIDER_DESCRIPTION__MIN_VALUE_EXPRESSION:
				return MIN_VALUE_EXPRESSION_EDEFAULT == null ? minValueExpression != null : !MIN_VALUE_EXPRESSION_EDEFAULT.equals(minValueExpression);
			case FormPackage.SLIDER_DESCRIPTION__MAX_VALUE_EXPRESSION:
				return MAX_VALUE_EXPRESSION_EDEFAULT == null ? maxValueExpression != null : !MAX_VALUE_EXPRESSION_EDEFAULT.equals(maxValueExpression);
			case FormPackage.SLIDER_DESCRIPTION__CURRENT_VALUE_EXPRESSION:
				return CURRENT_VALUE_EXPRESSION_EDEFAULT == null ? currentValueExpression != null : !CURRENT_VALUE_EXPRESSION_EDEFAULT.equals(currentValueExpression);
			case FormPackage.SLIDER_DESCRIPTION__BODY:
				return body != null && !body.isEmpty();
			case FormPackage.SLIDER_DESCRIPTION__IS_ENABLED_EXPRESSION:
				return IS_ENABLED_EXPRESSION_EDEFAULT == null ? isEnabledExpression != null : !IS_ENABLED_EXPRESSION_EDEFAULT.equals(isEnabledExpression);
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
		result.append(" (minValueExpression: ");
		result.append(minValueExpression);
		result.append(", maxValueExpression: ");
		result.append(maxValueExpression);
		result.append(", currentValueExpression: ");
		result.append(currentValueExpression);
		result.append(", isEnabledExpression: ");
		result.append(isEnabledExpression);
		result.append(')');
		return result.toString();
	}

} // SliderDescriptionImpl
