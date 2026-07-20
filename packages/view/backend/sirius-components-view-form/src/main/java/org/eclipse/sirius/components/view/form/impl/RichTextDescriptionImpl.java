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
import org.eclipse.sirius.components.view.form.RichTextDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Rich Text Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.RichTextDescriptionImpl#getValueExpression <em>Value Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.RichTextDescriptionImpl#getBody <em>Body</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.RichTextDescriptionImpl#getIsEnabledExpression <em>Is Enabled Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RichTextDescriptionImpl extends WidgetDescriptionImpl implements RichTextDescription {
    /**
	 * The default value of the '{@link #getValueExpression() <em>Value Expression</em>}' attribute.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getValueExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String VALUE_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getValueExpression() <em>Value Expression</em>}' attribute.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getValueExpression()
	 * @generated
	 * @ordered
	 */
    protected String valueExpression = VALUE_EXPRESSION_EDEFAULT;

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
     * @generated
     * @ordered
     * @see #getIsEnabledExpression()
     */
    protected static final String IS_ENABLED_EXPRESSION_EDEFAULT = null;

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
	 * @generated
	 */
    protected RichTextDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return FormPackage.Literals.RICH_TEXT_DESCRIPTION;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getValueExpression() {
		return valueExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setValueExpression(String newValueExpression) {
		String oldValueExpression = valueExpression;
		valueExpression = newValueExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.RICH_TEXT_DESCRIPTION__VALUE_EXPRESSION, oldValueExpression, valueExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Operation> getBody() {
		if (body == null)
		{
			body = new EObjectContainmentEList<Operation>(Operation.class, this, FormPackage.RICH_TEXT_DESCRIPTION__BODY);
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
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.RICH_TEXT_DESCRIPTION__IS_ENABLED_EXPRESSION, oldIsEnabledExpression, isEnabledExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case FormPackage.RICH_TEXT_DESCRIPTION__BODY:
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
			case FormPackage.RICH_TEXT_DESCRIPTION__VALUE_EXPRESSION:
				return getValueExpression();
			case FormPackage.RICH_TEXT_DESCRIPTION__BODY:
				return getBody();
			case FormPackage.RICH_TEXT_DESCRIPTION__IS_ENABLED_EXPRESSION:
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
			case FormPackage.RICH_TEXT_DESCRIPTION__VALUE_EXPRESSION:
				setValueExpression((String)newValue);
				return;
			case FormPackage.RICH_TEXT_DESCRIPTION__BODY:
				getBody().clear();
				getBody().addAll((Collection<? extends Operation>)newValue);
				return;
			case FormPackage.RICH_TEXT_DESCRIPTION__IS_ENABLED_EXPRESSION:
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
			case FormPackage.RICH_TEXT_DESCRIPTION__VALUE_EXPRESSION:
				setValueExpression(VALUE_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.RICH_TEXT_DESCRIPTION__BODY:
				getBody().clear();
				return;
			case FormPackage.RICH_TEXT_DESCRIPTION__IS_ENABLED_EXPRESSION:
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
			case FormPackage.RICH_TEXT_DESCRIPTION__VALUE_EXPRESSION:
				return VALUE_EXPRESSION_EDEFAULT == null ? valueExpression != null : !VALUE_EXPRESSION_EDEFAULT.equals(valueExpression);
			case FormPackage.RICH_TEXT_DESCRIPTION__BODY:
				return body != null && !body.isEmpty();
			case FormPackage.RICH_TEXT_DESCRIPTION__IS_ENABLED_EXPRESSION:
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
		result.append(" (valueExpression: ");
		result.append(valueExpression);
		result.append(", IsEnabledExpression: ");
		result.append(isEnabledExpression);
		result.append(')');
		return result.toString();
	}

} // RichTextDescriptionImpl
