/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
package org.eclipse.sirius.components.view.widget.reference.impl;

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
import org.eclipse.sirius.components.view.form.impl.WidgetDescriptionImpl;
import org.eclipse.sirius.components.view.widget.reference.ConditionalReferenceWidgetDescriptionStyle;
import org.eclipse.sirius.components.view.widget.reference.ReferencePackage;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescriptionStyle;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Widget Description</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.widget.reference.impl.ReferenceWidgetDescriptionImpl#getReferenceOwnerExpression
 * <em>Reference Owner Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.widget.reference.impl.ReferenceWidgetDescriptionImpl#getReferenceNameExpression
 * <em>Reference Name Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReferenceWidgetDescriptionImpl extends WidgetDescriptionImpl implements ReferenceWidgetDescription {

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
	 * The default value of the '{@link #getReferenceOwnerExpression() <em>Reference Owner Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getReferenceOwnerExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String REFERENCE_OWNER_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getReferenceOwnerExpression() <em>Reference Owner Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getReferenceOwnerExpression()
	 * @generated
	 * @ordered
	 */
    protected String referenceOwnerExpression = REFERENCE_OWNER_EXPRESSION_EDEFAULT;

    /**
	 * The default value of the '{@link #getReferenceNameExpression() <em>Reference Name Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getReferenceNameExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String REFERENCE_NAME_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getReferenceNameExpression() <em>Reference Name Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getReferenceNameExpression()
	 * @generated
	 * @ordered
	 */
    protected String referenceNameExpression = REFERENCE_NAME_EXPRESSION_EDEFAULT;

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
	 * The cached value of the '{@link #getStyle() <em>Style</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getStyle()
	 * @generated
	 * @ordered
	 */
    protected ReferenceWidgetDescriptionStyle style;

    /**
	 * The cached value of the '{@link #getConditionalStyles() <em>Conditional Styles</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getConditionalStyles()
	 * @generated
	 * @ordered
	 */
    protected EList<ConditionalReferenceWidgetDescriptionStyle> conditionalStyles;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected ReferenceWidgetDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return ReferencePackage.Literals.REFERENCE_WIDGET_DESCRIPTION;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getReferenceOwnerExpression() {
		return referenceOwnerExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setReferenceOwnerExpression(String newReferenceOwnerExpression) {
		String oldReferenceOwnerExpression = referenceOwnerExpression;
		referenceOwnerExpression = newReferenceOwnerExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION, oldReferenceOwnerExpression, referenceOwnerExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getReferenceNameExpression() {
		return referenceNameExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setReferenceNameExpression(String newReferenceNameExpression) {
		String oldReferenceNameExpression = referenceNameExpression;
		referenceNameExpression = newReferenceNameExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION, oldReferenceNameExpression, referenceNameExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Operation> getBody() {
		if (body == null)
		{
			body = new EObjectContainmentEList<Operation>(Operation.class, this, ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__BODY);
		}
		return body;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public ReferenceWidgetDescriptionStyle getStyle() {
		return style;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setStyle(ReferenceWidgetDescriptionStyle newStyle) {
		if (newStyle != style)
		{
			NotificationChain msgs = null;
			if (style != null)
				msgs = ((InternalEObject)style).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__STYLE, null, msgs);
			if (newStyle != null)
				msgs = ((InternalEObject)newStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__STYLE, null, msgs);
			msgs = basicSetStyle(newStyle, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__STYLE, newStyle, newStyle));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetStyle(ReferenceWidgetDescriptionStyle newStyle, NotificationChain msgs) {
		ReferenceWidgetDescriptionStyle oldStyle = style;
		style = newStyle;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__STYLE, oldStyle, newStyle);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<ConditionalReferenceWidgetDescriptionStyle> getConditionalStyles() {
		if (conditionalStyles == null)
		{
			conditionalStyles = new EObjectContainmentEList<ConditionalReferenceWidgetDescriptionStyle>(ConditionalReferenceWidgetDescriptionStyle.class, this, ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES);
		}
		return conditionalStyles;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__BODY:
				return ((InternalEList<?>)getBody()).basicRemove(otherEnd, msgs);
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__STYLE:
				return basicSetStyle(null, msgs);
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
				return ((InternalEList<?>)getConditionalStyles()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
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
			eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION, oldIsEnabledExpression, isEnabledExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
				return getIsEnabledExpression();
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION:
				return getReferenceOwnerExpression();
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION:
				return getReferenceNameExpression();
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__BODY:
				return getBody();
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__STYLE:
				return getStyle();
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
				return getConditionalStyles();
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
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
				setIsEnabledExpression((String)newValue);
				return;
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION:
				setReferenceOwnerExpression((String)newValue);
				return;
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION:
				setReferenceNameExpression((String)newValue);
				return;
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__BODY:
				getBody().clear();
				getBody().addAll((Collection<? extends Operation>)newValue);
				return;
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__STYLE:
				setStyle((ReferenceWidgetDescriptionStyle)newValue);
				return;
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
				getConditionalStyles().clear();
				getConditionalStyles().addAll((Collection<? extends ConditionalReferenceWidgetDescriptionStyle>)newValue);
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
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
				setIsEnabledExpression(IS_ENABLED_EXPRESSION_EDEFAULT);
				return;
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION:
				setReferenceOwnerExpression(REFERENCE_OWNER_EXPRESSION_EDEFAULT);
				return;
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION:
				setReferenceNameExpression(REFERENCE_NAME_EXPRESSION_EDEFAULT);
				return;
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__BODY:
				getBody().clear();
				return;
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__STYLE:
				setStyle((ReferenceWidgetDescriptionStyle)null);
				return;
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
				getConditionalStyles().clear();
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
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
				return IS_ENABLED_EXPRESSION_EDEFAULT == null ? isEnabledExpression != null : !IS_ENABLED_EXPRESSION_EDEFAULT.equals(isEnabledExpression);
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION:
				return REFERENCE_OWNER_EXPRESSION_EDEFAULT == null ? referenceOwnerExpression != null : !REFERENCE_OWNER_EXPRESSION_EDEFAULT.equals(referenceOwnerExpression);
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION:
				return REFERENCE_NAME_EXPRESSION_EDEFAULT == null ? referenceNameExpression != null : !REFERENCE_NAME_EXPRESSION_EDEFAULT.equals(referenceNameExpression);
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__BODY:
				return body != null && !body.isEmpty();
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__STYLE:
				return style != null;
			case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
				return conditionalStyles != null && !conditionalStyles.isEmpty();
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
		result.append(" (isEnabledExpression: ");
		result.append(isEnabledExpression);
		result.append(", referenceOwnerExpression: ");
		result.append(referenceOwnerExpression);
		result.append(", referenceNameExpression: ");
		result.append(referenceNameExpression);
		result.append(')');
		return result.toString();
	}

} // ReferenceWidgetDescriptionImpl
