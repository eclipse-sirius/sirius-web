/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.sirius.components.view.TextStyleDescription;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Text Style Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.impl.TextStyleDescriptionImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.impl.TextStyleDescriptionImpl#getForegroundColorExpression <em>Foreground Color Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.impl.TextStyleDescriptionImpl#getBackgroundColorExpression <em>Background Color Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.impl.TextStyleDescriptionImpl#getIsBoldExpression <em>Is Bold Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.impl.TextStyleDescriptionImpl#getIsItalicExpression <em>Is Italic Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.impl.TextStyleDescriptionImpl#getIsUnderlineExpression <em>Is Underline Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TextStyleDescriptionImpl extends MinimalEObjectImpl.Container implements TextStyleDescription {
    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
	 * The default value of the '{@link #getForegroundColorExpression() <em>Foreground Color Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getForegroundColorExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String FOREGROUND_COLOR_EXPRESSION_EDEFAULT = "";

    /**
	 * The cached value of the '{@link #getForegroundColorExpression() <em>Foreground Color Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getForegroundColorExpression()
	 * @generated
	 * @ordered
	 */
    protected String foregroundColorExpression = FOREGROUND_COLOR_EXPRESSION_EDEFAULT;

    /**
	 * The default value of the '{@link #getBackgroundColorExpression() <em>Background Color Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getBackgroundColorExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String BACKGROUND_COLOR_EXPRESSION_EDEFAULT = "";

    /**
	 * The cached value of the '{@link #getBackgroundColorExpression() <em>Background Color Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getBackgroundColorExpression()
	 * @generated
	 * @ordered
	 */
    protected String backgroundColorExpression = BACKGROUND_COLOR_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getIsBoldExpression() <em>Is Bold Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIsBoldExpression()
     * @generated
     * @ordered
     */
    protected static final String IS_BOLD_EXPRESSION_EDEFAULT = "aql:false";

    /**
     * The cached value of the '{@link #getIsBoldExpression() <em>Is Bold Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIsBoldExpression()
     * @generated
     * @ordered
     */
    protected String isBoldExpression = IS_BOLD_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getIsItalicExpression() <em>Is Italic Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIsItalicExpression()
     * @generated
     * @ordered
     */
    protected static final String IS_ITALIC_EXPRESSION_EDEFAULT = "aql:false";

    /**
     * The cached value of the '{@link #getIsItalicExpression() <em>Is Italic Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIsItalicExpression()
     * @generated
     * @ordered
     */
    protected String isItalicExpression = IS_ITALIC_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getIsUnderlineExpression() <em>Is Underline Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIsUnderlineExpression()
     * @generated
     * @ordered
     */
    protected static final String IS_UNDERLINE_EXPRESSION_EDEFAULT = "aql:false";

    /**
     * The cached value of the '{@link #getIsUnderlineExpression() <em>Is Underline Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIsUnderlineExpression()
     * @generated
     * @ordered
     */
    protected String isUnderlineExpression = IS_UNDERLINE_EXPRESSION_EDEFAULT;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected TextStyleDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return ViewPackage.Literals.TEXT_STYLE_DESCRIPTION;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getName() {
		return name;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.TEXT_STYLE_DESCRIPTION__NAME, oldName, name));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getForegroundColorExpression() {
		return foregroundColorExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setForegroundColorExpression(String newForegroundColorExpression) {
		String oldForegroundColorExpression = foregroundColorExpression;
		foregroundColorExpression = newForegroundColorExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.TEXT_STYLE_DESCRIPTION__FOREGROUND_COLOR_EXPRESSION, oldForegroundColorExpression, foregroundColorExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getBackgroundColorExpression() {
		return backgroundColorExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setBackgroundColorExpression(String newBackgroundColorExpression) {
		String oldBackgroundColorExpression = backgroundColorExpression;
		backgroundColorExpression = newBackgroundColorExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.TEXT_STYLE_DESCRIPTION__BACKGROUND_COLOR_EXPRESSION, oldBackgroundColorExpression, backgroundColorExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getIsBoldExpression() {
		return isBoldExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setIsBoldExpression(String newIsBoldExpression) {
		String oldIsBoldExpression = isBoldExpression;
		isBoldExpression = newIsBoldExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.TEXT_STYLE_DESCRIPTION__IS_BOLD_EXPRESSION, oldIsBoldExpression, isBoldExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getIsItalicExpression() {
		return isItalicExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setIsItalicExpression(String newIsItalicExpression) {
		String oldIsItalicExpression = isItalicExpression;
		isItalicExpression = newIsItalicExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.TEXT_STYLE_DESCRIPTION__IS_ITALIC_EXPRESSION, oldIsItalicExpression, isItalicExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getIsUnderlineExpression() {
		return isUnderlineExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setIsUnderlineExpression(String newIsUnderlineExpression) {
		String oldIsUnderlineExpression = isUnderlineExpression;
		isUnderlineExpression = newIsUnderlineExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.TEXT_STYLE_DESCRIPTION__IS_UNDERLINE_EXPRESSION, oldIsUnderlineExpression, isUnderlineExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case ViewPackage.TEXT_STYLE_DESCRIPTION__NAME:
				return getName();
			case ViewPackage.TEXT_STYLE_DESCRIPTION__FOREGROUND_COLOR_EXPRESSION:
				return getForegroundColorExpression();
			case ViewPackage.TEXT_STYLE_DESCRIPTION__BACKGROUND_COLOR_EXPRESSION:
				return getBackgroundColorExpression();
			case ViewPackage.TEXT_STYLE_DESCRIPTION__IS_BOLD_EXPRESSION:
				return getIsBoldExpression();
			case ViewPackage.TEXT_STYLE_DESCRIPTION__IS_ITALIC_EXPRESSION:
				return getIsItalicExpression();
			case ViewPackage.TEXT_STYLE_DESCRIPTION__IS_UNDERLINE_EXPRESSION:
				return getIsUnderlineExpression();
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
			case ViewPackage.TEXT_STYLE_DESCRIPTION__NAME:
				setName((String)newValue);
				return;
			case ViewPackage.TEXT_STYLE_DESCRIPTION__FOREGROUND_COLOR_EXPRESSION:
				setForegroundColorExpression((String)newValue);
				return;
			case ViewPackage.TEXT_STYLE_DESCRIPTION__BACKGROUND_COLOR_EXPRESSION:
				setBackgroundColorExpression((String)newValue);
				return;
			case ViewPackage.TEXT_STYLE_DESCRIPTION__IS_BOLD_EXPRESSION:
				setIsBoldExpression((String)newValue);
				return;
			case ViewPackage.TEXT_STYLE_DESCRIPTION__IS_ITALIC_EXPRESSION:
				setIsItalicExpression((String)newValue);
				return;
			case ViewPackage.TEXT_STYLE_DESCRIPTION__IS_UNDERLINE_EXPRESSION:
				setIsUnderlineExpression((String)newValue);
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
			case ViewPackage.TEXT_STYLE_DESCRIPTION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ViewPackage.TEXT_STYLE_DESCRIPTION__FOREGROUND_COLOR_EXPRESSION:
				setForegroundColorExpression(FOREGROUND_COLOR_EXPRESSION_EDEFAULT);
				return;
			case ViewPackage.TEXT_STYLE_DESCRIPTION__BACKGROUND_COLOR_EXPRESSION:
				setBackgroundColorExpression(BACKGROUND_COLOR_EXPRESSION_EDEFAULT);
				return;
			case ViewPackage.TEXT_STYLE_DESCRIPTION__IS_BOLD_EXPRESSION:
				setIsBoldExpression(IS_BOLD_EXPRESSION_EDEFAULT);
				return;
			case ViewPackage.TEXT_STYLE_DESCRIPTION__IS_ITALIC_EXPRESSION:
				setIsItalicExpression(IS_ITALIC_EXPRESSION_EDEFAULT);
				return;
			case ViewPackage.TEXT_STYLE_DESCRIPTION__IS_UNDERLINE_EXPRESSION:
				setIsUnderlineExpression(IS_UNDERLINE_EXPRESSION_EDEFAULT);
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
			case ViewPackage.TEXT_STYLE_DESCRIPTION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ViewPackage.TEXT_STYLE_DESCRIPTION__FOREGROUND_COLOR_EXPRESSION:
				return FOREGROUND_COLOR_EXPRESSION_EDEFAULT == null ? foregroundColorExpression != null : !FOREGROUND_COLOR_EXPRESSION_EDEFAULT.equals(foregroundColorExpression);
			case ViewPackage.TEXT_STYLE_DESCRIPTION__BACKGROUND_COLOR_EXPRESSION:
				return BACKGROUND_COLOR_EXPRESSION_EDEFAULT == null ? backgroundColorExpression != null : !BACKGROUND_COLOR_EXPRESSION_EDEFAULT.equals(backgroundColorExpression);
			case ViewPackage.TEXT_STYLE_DESCRIPTION__IS_BOLD_EXPRESSION:
				return IS_BOLD_EXPRESSION_EDEFAULT == null ? isBoldExpression != null : !IS_BOLD_EXPRESSION_EDEFAULT.equals(isBoldExpression);
			case ViewPackage.TEXT_STYLE_DESCRIPTION__IS_ITALIC_EXPRESSION:
				return IS_ITALIC_EXPRESSION_EDEFAULT == null ? isItalicExpression != null : !IS_ITALIC_EXPRESSION_EDEFAULT.equals(isItalicExpression);
			case ViewPackage.TEXT_STYLE_DESCRIPTION__IS_UNDERLINE_EXPRESSION:
				return IS_UNDERLINE_EXPRESSION_EDEFAULT == null ? isUnderlineExpression != null : !IS_UNDERLINE_EXPRESSION_EDEFAULT.equals(isUnderlineExpression);
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
		result.append(" (name: ");
		result.append(name);
		result.append(", foregroundColorExpression: ");
		result.append(foregroundColorExpression);
		result.append(", backgroundColorExpression: ");
		result.append(backgroundColorExpression);
		result.append(", isBoldExpression: ");
		result.append(isBoldExpression);
		result.append(", isItalicExpression: ");
		result.append(isItalicExpression);
		result.append(", isUnderlineExpression: ");
		result.append(isUnderlineExpression);
		result.append(')');
		return result.toString();
	}

} // TextStyleDescriptionImpl
