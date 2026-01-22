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
import org.eclipse.sirius.components.view.form.ConditionalMultiSelectDescriptionStyle;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.MultiSelectDescription;
import org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Multi Select Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionImpl#getValueExpression <em>Value Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionImpl#getCandidatesExpression <em>Candidates Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionImpl#getCandidateLabelExpression <em>Candidate Label Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionImpl#getBody <em>Body</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionImpl#getStyle <em>Style</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionImpl#getConditionalStyles <em>Conditional Styles</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.MultiSelectDescriptionImpl#getIsEnabledExpression <em>Is Enabled Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MultiSelectDescriptionImpl extends WidgetDescriptionImpl implements MultiSelectDescription {
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
     * The default value of the '{@link #getCandidatesExpression() <em>Candidates Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getCandidatesExpression()
     * @generated
     * @ordered
     */
    protected static final String CANDIDATES_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getCandidatesExpression() <em>Candidates Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getCandidatesExpression()
     * @generated
     * @ordered
     */
    protected String candidatesExpression = CANDIDATES_EXPRESSION_EDEFAULT;

    /**
	 * The default value of the '{@link #getCandidateLabelExpression() <em>Candidate Label Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getCandidateLabelExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String CANDIDATE_LABEL_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getCandidateLabelExpression() <em>Candidate Label Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getCandidateLabelExpression()
	 * @generated
	 * @ordered
	 */
    protected String candidateLabelExpression = CANDIDATE_LABEL_EXPRESSION_EDEFAULT;

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
    protected MultiSelectDescriptionStyle style;

    /**
	 * The cached value of the '{@link #getConditionalStyles() <em>Conditional Styles</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getConditionalStyles()
	 * @generated
	 * @ordered
	 */
    protected EList<ConditionalMultiSelectDescriptionStyle> conditionalStyles;

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
    protected MultiSelectDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return FormPackage.Literals.MULTI_SELECT_DESCRIPTION;
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
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.MULTI_SELECT_DESCRIPTION__VALUE_EXPRESSION, oldValueExpression, valueExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getCandidatesExpression() {
		return candidatesExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setCandidatesExpression(String newCandidatesExpression) {
		String oldCandidatesExpression = candidatesExpression;
		candidatesExpression = newCandidatesExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.MULTI_SELECT_DESCRIPTION__CANDIDATES_EXPRESSION, oldCandidatesExpression, candidatesExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getCandidateLabelExpression() {
		return candidateLabelExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setCandidateLabelExpression(String newCandidateLabelExpression) {
		String oldCandidateLabelExpression = candidateLabelExpression;
		candidateLabelExpression = newCandidateLabelExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.MULTI_SELECT_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION, oldCandidateLabelExpression, candidateLabelExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<Operation> getBody() {
		if (body == null)
		{
			body = new EObjectContainmentEList<Operation>(Operation.class, this, FormPackage.MULTI_SELECT_DESCRIPTION__BODY);
		}
		return body;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public MultiSelectDescriptionStyle getStyle() {
		return style;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetStyle(MultiSelectDescriptionStyle newStyle, NotificationChain msgs) {
		MultiSelectDescriptionStyle oldStyle = style;
		style = newStyle;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FormPackage.MULTI_SELECT_DESCRIPTION__STYLE, oldStyle, newStyle);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setStyle(MultiSelectDescriptionStyle newStyle) {
		if (newStyle != style)
		{
			NotificationChain msgs = null;
			if (style != null)
				msgs = ((InternalEObject)style).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FormPackage.MULTI_SELECT_DESCRIPTION__STYLE, null, msgs);
			if (newStyle != null)
				msgs = ((InternalEObject)newStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - FormPackage.MULTI_SELECT_DESCRIPTION__STYLE, null, msgs);
			msgs = basicSetStyle(newStyle, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.MULTI_SELECT_DESCRIPTION__STYLE, newStyle, newStyle));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<ConditionalMultiSelectDescriptionStyle> getConditionalStyles() {
		if (conditionalStyles == null)
		{
			conditionalStyles = new EObjectContainmentEList<ConditionalMultiSelectDescriptionStyle>(ConditionalMultiSelectDescriptionStyle.class, this, FormPackage.MULTI_SELECT_DESCRIPTION__CONDITIONAL_STYLES);
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
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.MULTI_SELECT_DESCRIPTION__IS_ENABLED_EXPRESSION, oldIsEnabledExpression, isEnabledExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case FormPackage.MULTI_SELECT_DESCRIPTION__BODY:
				return ((InternalEList<?>)getBody()).basicRemove(otherEnd, msgs);
			case FormPackage.MULTI_SELECT_DESCRIPTION__STYLE:
				return basicSetStyle(null, msgs);
			case FormPackage.MULTI_SELECT_DESCRIPTION__CONDITIONAL_STYLES:
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
			case FormPackage.MULTI_SELECT_DESCRIPTION__VALUE_EXPRESSION:
				return getValueExpression();
			case FormPackage.MULTI_SELECT_DESCRIPTION__CANDIDATES_EXPRESSION:
				return getCandidatesExpression();
			case FormPackage.MULTI_SELECT_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION:
				return getCandidateLabelExpression();
			case FormPackage.MULTI_SELECT_DESCRIPTION__BODY:
				return getBody();
			case FormPackage.MULTI_SELECT_DESCRIPTION__STYLE:
				return getStyle();
			case FormPackage.MULTI_SELECT_DESCRIPTION__CONDITIONAL_STYLES:
				return getConditionalStyles();
			case FormPackage.MULTI_SELECT_DESCRIPTION__IS_ENABLED_EXPRESSION:
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
			case FormPackage.MULTI_SELECT_DESCRIPTION__VALUE_EXPRESSION:
				setValueExpression((String)newValue);
				return;
			case FormPackage.MULTI_SELECT_DESCRIPTION__CANDIDATES_EXPRESSION:
				setCandidatesExpression((String)newValue);
				return;
			case FormPackage.MULTI_SELECT_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION:
				setCandidateLabelExpression((String)newValue);
				return;
			case FormPackage.MULTI_SELECT_DESCRIPTION__BODY:
				getBody().clear();
				getBody().addAll((Collection<? extends Operation>)newValue);
				return;
			case FormPackage.MULTI_SELECT_DESCRIPTION__STYLE:
				setStyle((MultiSelectDescriptionStyle)newValue);
				return;
			case FormPackage.MULTI_SELECT_DESCRIPTION__CONDITIONAL_STYLES:
				getConditionalStyles().clear();
				getConditionalStyles().addAll((Collection<? extends ConditionalMultiSelectDescriptionStyle>)newValue);
				return;
			case FormPackage.MULTI_SELECT_DESCRIPTION__IS_ENABLED_EXPRESSION:
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
			case FormPackage.MULTI_SELECT_DESCRIPTION__VALUE_EXPRESSION:
				setValueExpression(VALUE_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.MULTI_SELECT_DESCRIPTION__CANDIDATES_EXPRESSION:
				setCandidatesExpression(CANDIDATES_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.MULTI_SELECT_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION:
				setCandidateLabelExpression(CANDIDATE_LABEL_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.MULTI_SELECT_DESCRIPTION__BODY:
				getBody().clear();
				return;
			case FormPackage.MULTI_SELECT_DESCRIPTION__STYLE:
				setStyle((MultiSelectDescriptionStyle)null);
				return;
			case FormPackage.MULTI_SELECT_DESCRIPTION__CONDITIONAL_STYLES:
				getConditionalStyles().clear();
				return;
			case FormPackage.MULTI_SELECT_DESCRIPTION__IS_ENABLED_EXPRESSION:
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
			case FormPackage.MULTI_SELECT_DESCRIPTION__VALUE_EXPRESSION:
				return VALUE_EXPRESSION_EDEFAULT == null ? valueExpression != null : !VALUE_EXPRESSION_EDEFAULT.equals(valueExpression);
			case FormPackage.MULTI_SELECT_DESCRIPTION__CANDIDATES_EXPRESSION:
				return CANDIDATES_EXPRESSION_EDEFAULT == null ? candidatesExpression != null : !CANDIDATES_EXPRESSION_EDEFAULT.equals(candidatesExpression);
			case FormPackage.MULTI_SELECT_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION:
				return CANDIDATE_LABEL_EXPRESSION_EDEFAULT == null ? candidateLabelExpression != null : !CANDIDATE_LABEL_EXPRESSION_EDEFAULT.equals(candidateLabelExpression);
			case FormPackage.MULTI_SELECT_DESCRIPTION__BODY:
				return body != null && !body.isEmpty();
			case FormPackage.MULTI_SELECT_DESCRIPTION__STYLE:
				return style != null;
			case FormPackage.MULTI_SELECT_DESCRIPTION__CONDITIONAL_STYLES:
				return conditionalStyles != null && !conditionalStyles.isEmpty();
			case FormPackage.MULTI_SELECT_DESCRIPTION__IS_ENABLED_EXPRESSION:
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
		result.append(", candidatesExpression: ");
		result.append(candidatesExpression);
		result.append(", candidateLabelExpression: ");
		result.append(candidateLabelExpression);
		result.append(", IsEnabledExpression: ");
		result.append(isEnabledExpression);
		result.append(')');
		return result.toString();
	}

} // MultiSelectDescriptionImpl
