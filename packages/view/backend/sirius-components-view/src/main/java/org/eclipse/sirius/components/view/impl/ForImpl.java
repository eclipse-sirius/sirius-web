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
 *   <li>{@link org.eclipse.sirius.components.view.impl.ForImpl#getExpression <em>Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.impl.ForImpl#getIteratorName <em>Iterator Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ForImpl extends OperationImpl implements For {
    /**
	 * The default value of the '{@link #getExpression() <em>Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getExpression() <em>Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
    protected String expression = EXPRESSION_EDEFAULT;

    /**
	 * The default value of the '{@link #getIteratorName() <em>Iterator Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getIteratorName()
	 * @generated
	 * @ordered
	 */
    protected static final String ITERATOR_NAME_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getIteratorName() <em>Iterator Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getIteratorName()
	 * @generated
	 * @ordered
	 */
    protected String iteratorName = ITERATOR_NAME_EDEFAULT;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected ForImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return ViewPackage.Literals.FOR;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getExpression() {
		return expression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setExpression(String newExpression) {
		String oldExpression = expression;
		expression = newExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.FOR__EXPRESSION, oldExpression, expression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getIteratorName() {
		return iteratorName;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setIteratorName(String newIteratorName) {
		String oldIteratorName = iteratorName;
		iteratorName = newIteratorName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.FOR__ITERATOR_NAME, oldIteratorName, iteratorName));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case ViewPackage.FOR__EXPRESSION:
				return getExpression();
			case ViewPackage.FOR__ITERATOR_NAME:
				return getIteratorName();
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
			case ViewPackage.FOR__EXPRESSION:
				setExpression((String)newValue);
				return;
			case ViewPackage.FOR__ITERATOR_NAME:
				setIteratorName((String)newValue);
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
			case ViewPackage.FOR__EXPRESSION:
				setExpression(EXPRESSION_EDEFAULT);
				return;
			case ViewPackage.FOR__ITERATOR_NAME:
				setIteratorName(ITERATOR_NAME_EDEFAULT);
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
			case ViewPackage.FOR__EXPRESSION:
				return EXPRESSION_EDEFAULT == null ? expression != null : !EXPRESSION_EDEFAULT.equals(expression);
			case ViewPackage.FOR__ITERATOR_NAME:
				return ITERATOR_NAME_EDEFAULT == null ? iteratorName != null : !ITERATOR_NAME_EDEFAULT.equals(iteratorName);
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
		result.append(" (expression: ");
		result.append(expression);
		result.append(", iteratorName: ");
		result.append(iteratorName);
		result.append(')');
		return result.toString();
	}

} // ForImpl
