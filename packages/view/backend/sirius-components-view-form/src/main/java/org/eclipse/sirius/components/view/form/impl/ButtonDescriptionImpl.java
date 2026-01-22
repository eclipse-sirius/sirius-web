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
import org.eclipse.sirius.components.view.form.ButtonDescription;
import org.eclipse.sirius.components.view.form.ButtonDescriptionStyle;
import org.eclipse.sirius.components.view.form.ConditionalButtonDescriptionStyle;
import org.eclipse.sirius.components.view.form.FormPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Button Description</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.ButtonDescriptionImpl#getButtonLabelExpression <em>Button
 * Label Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.ButtonDescriptionImpl#getBody <em>Body</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.ButtonDescriptionImpl#getImageExpression <em>Image
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.ButtonDescriptionImpl#getStyle <em>Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.ButtonDescriptionImpl#getConditionalStyles <em>Conditional
 * Styles</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ButtonDescriptionImpl extends WidgetDescriptionImpl implements ButtonDescription {
    /**
     * The default value of the '{@link #getButtonLabelExpression() <em>Button Label Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getButtonLabelExpression()
     * @generated
     * @ordered
     */
    protected static final String BUTTON_LABEL_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getButtonLabelExpression() <em>Button Label Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getButtonLabelExpression()
     * @generated
     * @ordered
     */
    protected String buttonLabelExpression = BUTTON_LABEL_EXPRESSION_EDEFAULT;

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
	 * The default value of the '{@link #getImageExpression() <em>Image Expression</em>}' attribute.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getImageExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String IMAGE_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getImageExpression() <em>Image Expression</em>}' attribute.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getImageExpression()
	 * @generated
	 * @ordered
	 */
    protected String imageExpression = IMAGE_EXPRESSION_EDEFAULT;

    /**
	 * The cached value of the '{@link #getStyle() <em>Style</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getStyle()
	 * @generated
	 * @ordered
	 */
    protected ButtonDescriptionStyle style;

    /**
	 * The cached value of the '{@link #getConditionalStyles() <em>Conditional Styles</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getConditionalStyles()
	 * @generated
	 * @ordered
	 */
    protected EList<ConditionalButtonDescriptionStyle> conditionalStyles;

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
    protected ButtonDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return FormPackage.Literals.BUTTON_DESCRIPTION;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getButtonLabelExpression() {
		return buttonLabelExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setButtonLabelExpression(String newButtonLabelExpression) {
		String oldButtonLabelExpression = buttonLabelExpression;
		buttonLabelExpression = newButtonLabelExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.BUTTON_DESCRIPTION__BUTTON_LABEL_EXPRESSION, oldButtonLabelExpression, buttonLabelExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Operation> getBody() {
		if (body == null)
		{
			body = new EObjectContainmentEList<Operation>(Operation.class, this, FormPackage.BUTTON_DESCRIPTION__BODY);
		}
		return body;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getImageExpression() {
		return imageExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setImageExpression(String newImageExpression) {
		String oldImageExpression = imageExpression;
		imageExpression = newImageExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.BUTTON_DESCRIPTION__IMAGE_EXPRESSION, oldImageExpression, imageExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public ButtonDescriptionStyle getStyle() {
		return style;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetStyle(ButtonDescriptionStyle newStyle, NotificationChain msgs) {
		ButtonDescriptionStyle oldStyle = style;
		style = newStyle;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FormPackage.BUTTON_DESCRIPTION__STYLE, oldStyle, newStyle);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setStyle(ButtonDescriptionStyle newStyle) {
		if (newStyle != style)
		{
			NotificationChain msgs = null;
			if (style != null)
				msgs = ((InternalEObject)style).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FormPackage.BUTTON_DESCRIPTION__STYLE, null, msgs);
			if (newStyle != null)
				msgs = ((InternalEObject)newStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - FormPackage.BUTTON_DESCRIPTION__STYLE, null, msgs);
			msgs = basicSetStyle(newStyle, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.BUTTON_DESCRIPTION__STYLE, newStyle, newStyle));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<ConditionalButtonDescriptionStyle> getConditionalStyles() {
		if (conditionalStyles == null)
		{
			conditionalStyles = new EObjectContainmentEList<ConditionalButtonDescriptionStyle>(ConditionalButtonDescriptionStyle.class, this, FormPackage.BUTTON_DESCRIPTION__CONDITIONAL_STYLES);
		}
		return conditionalStyles;
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
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.BUTTON_DESCRIPTION__IS_ENABLED_EXPRESSION, oldIsEnabledExpression, isEnabledExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case FormPackage.BUTTON_DESCRIPTION__BODY:
				return ((InternalEList<?>)getBody()).basicRemove(otherEnd, msgs);
			case FormPackage.BUTTON_DESCRIPTION__STYLE:
				return basicSetStyle(null, msgs);
			case FormPackage.BUTTON_DESCRIPTION__CONDITIONAL_STYLES:
				return ((InternalEList<?>)getConditionalStyles()).basicRemove(otherEnd, msgs);
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
			case FormPackage.BUTTON_DESCRIPTION__BUTTON_LABEL_EXPRESSION:
				return getButtonLabelExpression();
			case FormPackage.BUTTON_DESCRIPTION__BODY:
				return getBody();
			case FormPackage.BUTTON_DESCRIPTION__IMAGE_EXPRESSION:
				return getImageExpression();
			case FormPackage.BUTTON_DESCRIPTION__STYLE:
				return getStyle();
			case FormPackage.BUTTON_DESCRIPTION__CONDITIONAL_STYLES:
				return getConditionalStyles();
			case FormPackage.BUTTON_DESCRIPTION__IS_ENABLED_EXPRESSION:
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
			case FormPackage.BUTTON_DESCRIPTION__BUTTON_LABEL_EXPRESSION:
				setButtonLabelExpression((String)newValue);
				return;
			case FormPackage.BUTTON_DESCRIPTION__BODY:
				getBody().clear();
				getBody().addAll((Collection<? extends Operation>)newValue);
				return;
			case FormPackage.BUTTON_DESCRIPTION__IMAGE_EXPRESSION:
				setImageExpression((String)newValue);
				return;
			case FormPackage.BUTTON_DESCRIPTION__STYLE:
				setStyle((ButtonDescriptionStyle)newValue);
				return;
			case FormPackage.BUTTON_DESCRIPTION__CONDITIONAL_STYLES:
				getConditionalStyles().clear();
				getConditionalStyles().addAll((Collection<? extends ConditionalButtonDescriptionStyle>)newValue);
				return;
			case FormPackage.BUTTON_DESCRIPTION__IS_ENABLED_EXPRESSION:
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
			case FormPackage.BUTTON_DESCRIPTION__BUTTON_LABEL_EXPRESSION:
				setButtonLabelExpression(BUTTON_LABEL_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.BUTTON_DESCRIPTION__BODY:
				getBody().clear();
				return;
			case FormPackage.BUTTON_DESCRIPTION__IMAGE_EXPRESSION:
				setImageExpression(IMAGE_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.BUTTON_DESCRIPTION__STYLE:
				setStyle((ButtonDescriptionStyle)null);
				return;
			case FormPackage.BUTTON_DESCRIPTION__CONDITIONAL_STYLES:
				getConditionalStyles().clear();
				return;
			case FormPackage.BUTTON_DESCRIPTION__IS_ENABLED_EXPRESSION:
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
			case FormPackage.BUTTON_DESCRIPTION__BUTTON_LABEL_EXPRESSION:
				return BUTTON_LABEL_EXPRESSION_EDEFAULT == null ? buttonLabelExpression != null : !BUTTON_LABEL_EXPRESSION_EDEFAULT.equals(buttonLabelExpression);
			case FormPackage.BUTTON_DESCRIPTION__BODY:
				return body != null && !body.isEmpty();
			case FormPackage.BUTTON_DESCRIPTION__IMAGE_EXPRESSION:
				return IMAGE_EXPRESSION_EDEFAULT == null ? imageExpression != null : !IMAGE_EXPRESSION_EDEFAULT.equals(imageExpression);
			case FormPackage.BUTTON_DESCRIPTION__STYLE:
				return style != null;
			case FormPackage.BUTTON_DESCRIPTION__CONDITIONAL_STYLES:
				return conditionalStyles != null && !conditionalStyles.isEmpty();
			case FormPackage.BUTTON_DESCRIPTION__IS_ENABLED_EXPRESSION:
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
		result.append(" (buttonLabelExpression: ");
		result.append(buttonLabelExpression);
		result.append(", imageExpression: ");
		result.append(imageExpression);
		result.append(", IsEnabledExpression: ");
		result.append(isEnabledExpression);
		result.append(')');
		return result.toString();
	}

} // ButtonDescriptionImpl
