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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.ImageDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Image Description</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.ImageDescriptionImpl#getUrlExpression <em>Url
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.ImageDescriptionImpl#getMaxWidthExpression <em>Max Width
 * Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ImageDescriptionImpl extends WidgetDescriptionImpl implements ImageDescription {
    /**
	 * The default value of the '{@link #getUrlExpression() <em>Url Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getUrlExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String URL_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getUrlExpression() <em>Url Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getUrlExpression()
	 * @generated
	 * @ordered
	 */
    protected String urlExpression = URL_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getMaxWidthExpression() <em>Max Width Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getMaxWidthExpression()
     * @generated
     * @ordered
     */
    protected static final String MAX_WIDTH_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getMaxWidthExpression() <em>Max Width Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getMaxWidthExpression()
     * @generated
     * @ordered
     */
    protected String maxWidthExpression = MAX_WIDTH_EXPRESSION_EDEFAULT;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected ImageDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return FormPackage.Literals.IMAGE_DESCRIPTION;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getUrlExpression() {
		return urlExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setUrlExpression(String newUrlExpression) {
		String oldUrlExpression = urlExpression;
		urlExpression = newUrlExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.IMAGE_DESCRIPTION__URL_EXPRESSION, oldUrlExpression, urlExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getMaxWidthExpression() {
		return maxWidthExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setMaxWidthExpression(String newMaxWidthExpression) {
		String oldMaxWidthExpression = maxWidthExpression;
		maxWidthExpression = newMaxWidthExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.IMAGE_DESCRIPTION__MAX_WIDTH_EXPRESSION, oldMaxWidthExpression, maxWidthExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case FormPackage.IMAGE_DESCRIPTION__URL_EXPRESSION:
				return getUrlExpression();
			case FormPackage.IMAGE_DESCRIPTION__MAX_WIDTH_EXPRESSION:
				return getMaxWidthExpression();
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
			case FormPackage.IMAGE_DESCRIPTION__URL_EXPRESSION:
				setUrlExpression((String)newValue);
				return;
			case FormPackage.IMAGE_DESCRIPTION__MAX_WIDTH_EXPRESSION:
				setMaxWidthExpression((String)newValue);
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
			case FormPackage.IMAGE_DESCRIPTION__URL_EXPRESSION:
				setUrlExpression(URL_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.IMAGE_DESCRIPTION__MAX_WIDTH_EXPRESSION:
				setMaxWidthExpression(MAX_WIDTH_EXPRESSION_EDEFAULT);
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
			case FormPackage.IMAGE_DESCRIPTION__URL_EXPRESSION:
				return URL_EXPRESSION_EDEFAULT == null ? urlExpression != null : !URL_EXPRESSION_EDEFAULT.equals(urlExpression);
			case FormPackage.IMAGE_DESCRIPTION__MAX_WIDTH_EXPRESSION:
				return MAX_WIDTH_EXPRESSION_EDEFAULT == null ? maxWidthExpression != null : !MAX_WIDTH_EXPRESSION_EDEFAULT.equals(maxWidthExpression);
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
		result.append(" (urlExpression: ");
		result.append(urlExpression);
		result.append(", maxWidthExpression: ");
		result.append(maxWidthExpression);
		result.append(')');
		return result.toString();
	}

} // ImageDescriptionImpl
