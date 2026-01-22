/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.view.table.impl;

import java.util.Objects;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.sirius.components.view.table.RowFilterDescription;
import org.eclipse.sirius.components.view.table.TablePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Row Filter Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.table.impl.RowFilterDescriptionImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.table.impl.RowFilterDescriptionImpl#getLabelExpression <em>Label Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.table.impl.RowFilterDescriptionImpl#getInitialStateExpression <em>Initial State Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RowFilterDescriptionImpl extends MinimalEObjectImpl.Container implements RowFilterDescription {

    /**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
    protected static final String ID_EDEFAULT = null;
    /**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
    protected String id = ID_EDEFAULT;
	/**
	 * The default value of the '{@link #getLabelExpression() <em>Label Expression</em>}' attribute.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getLabelExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String LABEL_EXPRESSION_EDEFAULT = null;
    /**
	 * The cached value of the '{@link #getLabelExpression() <em>Label Expression</em>}' attribute.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getLabelExpression()
	 * @generated
	 * @ordered
	 */
    protected String labelExpression = LABEL_EXPRESSION_EDEFAULT;
	/**
     * The default value of the '{@link #getInitialStateExpression() <em>Initial State Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getInitialStateExpression()
     */
    protected static final String INITIAL_STATE_EXPRESSION_EDEFAULT = "";
    /**
     * The cached value of the '{@link #getInitialStateExpression() <em>Initial State Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getInitialStateExpression()
     */
    protected String initialStateExpression = INITIAL_STATE_EXPRESSION_EDEFAULT;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected RowFilterDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return TablePackage.Literals.ROW_FILTER_DESCRIPTION;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getId() {
		return id;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.ROW_FILTER_DESCRIPTION__ID, oldId, id));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getLabelExpression() {
		return labelExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setLabelExpression(String newLabelExpression) {
		String oldLabelExpression = labelExpression;
		labelExpression = newLabelExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.ROW_FILTER_DESCRIPTION__LABEL_EXPRESSION, oldLabelExpression, labelExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getInitialStateExpression() {
		return initialStateExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setInitialStateExpression(String newInitialStateExpression) {
		String oldInitialStateExpression = initialStateExpression;
		initialStateExpression = newInitialStateExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TablePackage.ROW_FILTER_DESCRIPTION__INITIAL_STATE_EXPRESSION, oldInitialStateExpression, initialStateExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case TablePackage.ROW_FILTER_DESCRIPTION__ID:
				return getId();
			case TablePackage.ROW_FILTER_DESCRIPTION__LABEL_EXPRESSION:
				return getLabelExpression();
			case TablePackage.ROW_FILTER_DESCRIPTION__INITIAL_STATE_EXPRESSION:
				return getInitialStateExpression();
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
			case TablePackage.ROW_FILTER_DESCRIPTION__ID:
				setId((String)newValue);
				return;
			case TablePackage.ROW_FILTER_DESCRIPTION__LABEL_EXPRESSION:
				setLabelExpression((String)newValue);
				return;
			case TablePackage.ROW_FILTER_DESCRIPTION__INITIAL_STATE_EXPRESSION:
				setInitialStateExpression((String)newValue);
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
			case TablePackage.ROW_FILTER_DESCRIPTION__ID:
				setId(ID_EDEFAULT);
				return;
			case TablePackage.ROW_FILTER_DESCRIPTION__LABEL_EXPRESSION:
				setLabelExpression(LABEL_EXPRESSION_EDEFAULT);
				return;
			case TablePackage.ROW_FILTER_DESCRIPTION__INITIAL_STATE_EXPRESSION:
				setInitialStateExpression(INITIAL_STATE_EXPRESSION_EDEFAULT);
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
			case TablePackage.ROW_FILTER_DESCRIPTION__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case TablePackage.ROW_FILTER_DESCRIPTION__LABEL_EXPRESSION:
				return LABEL_EXPRESSION_EDEFAULT == null ? labelExpression != null : !LABEL_EXPRESSION_EDEFAULT.equals(labelExpression);
			case TablePackage.ROW_FILTER_DESCRIPTION__INITIAL_STATE_EXPRESSION:
				return INITIAL_STATE_EXPRESSION_EDEFAULT == null ? initialStateExpression != null : !INITIAL_STATE_EXPRESSION_EDEFAULT.equals(initialStateExpression);
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
		result.append(" (id: ");
		result.append(id);
		result.append(", labelExpression: ");
		result.append(labelExpression);
		result.append(", initialStateExpression: ");
		result.append(initialStateExpression);
		result.append(')');
		return result.toString();
	}

} // RowFilterDescriptionImpl
