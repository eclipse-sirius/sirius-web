/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.view.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.sirius.web.view.RepresentationDescription;
import org.eclipse.sirius.web.view.ViewPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Representation Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.web.view.impl.RepresentationDescriptionImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.sirius.web.view.impl.RepresentationDescriptionImpl#getDomainType <em>Domain Type</em>}</li>
 *   <li>{@link org.eclipse.sirius.web.view.impl.RepresentationDescriptionImpl#getTitleExpression <em>Title Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class RepresentationDescriptionImpl extends MinimalEObjectImpl.Container
		implements RepresentationDescription {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDomainType() <em>Domain Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomainType()
	 * @generated
	 * @ordered
	 */
	protected static final String DOMAIN_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDomainType() <em>Domain Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomainType()
	 * @generated
	 * @ordered
	 */
	protected String domainType = DOMAIN_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getTitleExpression() <em>Title Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitleExpression()
	 * @generated
	 * @ordered
	 */
	protected static final String TITLE_EXPRESSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTitleExpression() <em>Title Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitleExpression()
	 * @generated
	 * @ordered
	 */
	protected String titleExpression = TITLE_EXPRESSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RepresentationDescriptionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ViewPackage.Literals.REPRESENTATION_DESCRIPTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.REPRESENTATION_DESCRIPTION__NAME, oldName,
					name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDomainType() {
		return domainType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDomainType(String newDomainType) {
		String oldDomainType = domainType;
		domainType = newDomainType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.REPRESENTATION_DESCRIPTION__DOMAIN_TYPE,
					oldDomainType, domainType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTitleExpression() {
		return titleExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTitleExpression(String newTitleExpression) {
		String oldTitleExpression = titleExpression;
		titleExpression = newTitleExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ViewPackage.REPRESENTATION_DESCRIPTION__TITLE_EXPRESSION, oldTitleExpression, titleExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ViewPackage.REPRESENTATION_DESCRIPTION__NAME:
			return getName();
		case ViewPackage.REPRESENTATION_DESCRIPTION__DOMAIN_TYPE:
			return getDomainType();
		case ViewPackage.REPRESENTATION_DESCRIPTION__TITLE_EXPRESSION:
			return getTitleExpression();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case ViewPackage.REPRESENTATION_DESCRIPTION__NAME:
			setName((String) newValue);
			return;
		case ViewPackage.REPRESENTATION_DESCRIPTION__DOMAIN_TYPE:
			setDomainType((String) newValue);
			return;
		case ViewPackage.REPRESENTATION_DESCRIPTION__TITLE_EXPRESSION:
			setTitleExpression((String) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case ViewPackage.REPRESENTATION_DESCRIPTION__NAME:
			setName(NAME_EDEFAULT);
			return;
		case ViewPackage.REPRESENTATION_DESCRIPTION__DOMAIN_TYPE:
			setDomainType(DOMAIN_TYPE_EDEFAULT);
			return;
		case ViewPackage.REPRESENTATION_DESCRIPTION__TITLE_EXPRESSION:
			setTitleExpression(TITLE_EXPRESSION_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case ViewPackage.REPRESENTATION_DESCRIPTION__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case ViewPackage.REPRESENTATION_DESCRIPTION__DOMAIN_TYPE:
			return DOMAIN_TYPE_EDEFAULT == null ? domainType != null : !DOMAIN_TYPE_EDEFAULT.equals(domainType);
		case ViewPackage.REPRESENTATION_DESCRIPTION__TITLE_EXPRESSION:
			return TITLE_EXPRESSION_EDEFAULT == null ? titleExpression != null
					: !TITLE_EXPRESSION_EDEFAULT.equals(titleExpression);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", domainType: "); //$NON-NLS-1$
		result.append(domainType);
		result.append(", titleExpression: "); //$NON-NLS-1$
		result.append(titleExpression);
		result.append(')');
		return result.toString();
	}

} //RepresentationDescriptionImpl
