/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.ConditionalMultiSelectDescriptionStyle;
import org.eclipse.sirius.components.view.MultiSelectDescription;
import org.eclipse.sirius.components.view.MultiSelectDescriptionStyle;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Multi Select Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.impl.MultiSelectDescriptionImpl#getValueExpression <em>Value
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.MultiSelectDescriptionImpl#getCandidatesExpression <em>Candidates
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.MultiSelectDescriptionImpl#getCandidateLabelExpression
 * <em>Candidate Label Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.MultiSelectDescriptionImpl#getBody <em>Body</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.MultiSelectDescriptionImpl#getStyle <em>Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.MultiSelectDescriptionImpl#getConditionalStyles <em>Conditional
 * Styles</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MultiSelectDescriptionImpl extends WidgetDescriptionImpl implements MultiSelectDescription {
    /**
     * The default value of the '{@link #getValueExpression() <em>Value Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getValueExpression()
     * @generated
     * @ordered
     */
    protected static final String VALUE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getValueExpression() <em>Value Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
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
     *
     * @see #getCandidateLabelExpression()
     * @generated
     * @ordered
     */
    protected static final String CANDIDATE_LABEL_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getCandidateLabelExpression() <em>Candidate Label Expression</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getCandidateLabelExpression()
     * @generated
     * @ordered
     */
    protected String candidateLabelExpression = CANDIDATE_LABEL_EXPRESSION_EDEFAULT;

    /**
     * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getBody()
     * @generated
     * @ordered
     */
    protected EList<Operation> body;

    /**
     * The cached value of the '{@link #getStyle() <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getStyle()
     * @generated
     * @ordered
     */
    protected MultiSelectDescriptionStyle style;

    /**
     * The cached value of the '{@link #getConditionalStyles() <em>Conditional Styles</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getConditionalStyles()
     * @generated
     * @ordered
     */
    protected EList<ConditionalMultiSelectDescriptionStyle> conditionalStyles;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected MultiSelectDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.MULTI_SELECT_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getValueExpression() {
        return this.valueExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setValueExpression(String newValueExpression) {
        String oldValueExpression = this.valueExpression;
        this.valueExpression = newValueExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.MULTI_SELECT_DESCRIPTION__VALUE_EXPRESSION, oldValueExpression, this.valueExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getCandidatesExpression() {
        return this.candidatesExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setCandidatesExpression(String newCandidatesExpression) {
        String oldCandidatesExpression = this.candidatesExpression;
        this.candidatesExpression = newCandidatesExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.MULTI_SELECT_DESCRIPTION__CANDIDATES_EXPRESSION, oldCandidatesExpression, this.candidatesExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getCandidateLabelExpression() {
        return this.candidateLabelExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setCandidateLabelExpression(String newCandidateLabelExpression) {
        String oldCandidateLabelExpression = this.candidateLabelExpression;
        this.candidateLabelExpression = newCandidateLabelExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.MULTI_SELECT_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION, oldCandidateLabelExpression, this.candidateLabelExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Operation> getBody() {
        if (this.body == null) {
            this.body = new EObjectContainmentEList<>(Operation.class, this, ViewPackage.MULTI_SELECT_DESCRIPTION__BODY);
        }
        return this.body;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public MultiSelectDescriptionStyle getStyle() {
        return this.style;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetStyle(MultiSelectDescriptionStyle newStyle, NotificationChain msgs) {
        MultiSelectDescriptionStyle oldStyle = this.style;
        this.style = newStyle;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ViewPackage.MULTI_SELECT_DESCRIPTION__STYLE, oldStyle, newStyle);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setStyle(MultiSelectDescriptionStyle newStyle) {
        if (newStyle != this.style) {
            NotificationChain msgs = null;
            if (this.style != null)
                msgs = ((InternalEObject) this.style).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ViewPackage.MULTI_SELECT_DESCRIPTION__STYLE, null, msgs);
            if (newStyle != null)
                msgs = ((InternalEObject) newStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ViewPackage.MULTI_SELECT_DESCRIPTION__STYLE, null, msgs);
            msgs = this.basicSetStyle(newStyle, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.MULTI_SELECT_DESCRIPTION__STYLE, newStyle, newStyle));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<ConditionalMultiSelectDescriptionStyle> getConditionalStyles() {
        if (this.conditionalStyles == null) {
            this.conditionalStyles = new EObjectContainmentEList<>(ConditionalMultiSelectDescriptionStyle.class, this,
                    ViewPackage.MULTI_SELECT_DESCRIPTION__CONDITIONAL_STYLES);
        }
        return this.conditionalStyles;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ViewPackage.MULTI_SELECT_DESCRIPTION__BODY:
                return ((InternalEList<?>) this.getBody()).basicRemove(otherEnd, msgs);
            case ViewPackage.MULTI_SELECT_DESCRIPTION__STYLE:
                return this.basicSetStyle(null, msgs);
            case ViewPackage.MULTI_SELECT_DESCRIPTION__CONDITIONAL_STYLES:
                return ((InternalEList<?>) this.getConditionalStyles()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ViewPackage.MULTI_SELECT_DESCRIPTION__VALUE_EXPRESSION:
                return this.getValueExpression();
            case ViewPackage.MULTI_SELECT_DESCRIPTION__CANDIDATES_EXPRESSION:
                return this.getCandidatesExpression();
            case ViewPackage.MULTI_SELECT_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION:
                return this.getCandidateLabelExpression();
            case ViewPackage.MULTI_SELECT_DESCRIPTION__BODY:
                return this.getBody();
            case ViewPackage.MULTI_SELECT_DESCRIPTION__STYLE:
                return this.getStyle();
            case ViewPackage.MULTI_SELECT_DESCRIPTION__CONDITIONAL_STYLES:
                return this.getConditionalStyles();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case ViewPackage.MULTI_SELECT_DESCRIPTION__VALUE_EXPRESSION:
                this.setValueExpression((String) newValue);
                return;
            case ViewPackage.MULTI_SELECT_DESCRIPTION__CANDIDATES_EXPRESSION:
                this.setCandidatesExpression((String) newValue);
                return;
            case ViewPackage.MULTI_SELECT_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION:
                this.setCandidateLabelExpression((String) newValue);
                return;
            case ViewPackage.MULTI_SELECT_DESCRIPTION__BODY:
                this.getBody().clear();
                this.getBody().addAll((Collection<? extends Operation>) newValue);
                return;
            case ViewPackage.MULTI_SELECT_DESCRIPTION__STYLE:
                this.setStyle((MultiSelectDescriptionStyle) newValue);
                return;
            case ViewPackage.MULTI_SELECT_DESCRIPTION__CONDITIONAL_STYLES:
                this.getConditionalStyles().clear();
                this.getConditionalStyles().addAll((Collection<? extends ConditionalMultiSelectDescriptionStyle>) newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case ViewPackage.MULTI_SELECT_DESCRIPTION__VALUE_EXPRESSION:
                this.setValueExpression(VALUE_EXPRESSION_EDEFAULT);
                return;
            case ViewPackage.MULTI_SELECT_DESCRIPTION__CANDIDATES_EXPRESSION:
                this.setCandidatesExpression(CANDIDATES_EXPRESSION_EDEFAULT);
                return;
            case ViewPackage.MULTI_SELECT_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION:
                this.setCandidateLabelExpression(CANDIDATE_LABEL_EXPRESSION_EDEFAULT);
                return;
            case ViewPackage.MULTI_SELECT_DESCRIPTION__BODY:
                this.getBody().clear();
                return;
            case ViewPackage.MULTI_SELECT_DESCRIPTION__STYLE:
                this.setStyle((MultiSelectDescriptionStyle) null);
                return;
            case ViewPackage.MULTI_SELECT_DESCRIPTION__CONDITIONAL_STYLES:
                this.getConditionalStyles().clear();
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case ViewPackage.MULTI_SELECT_DESCRIPTION__VALUE_EXPRESSION:
                return VALUE_EXPRESSION_EDEFAULT == null ? this.valueExpression != null : !VALUE_EXPRESSION_EDEFAULT.equals(this.valueExpression);
            case ViewPackage.MULTI_SELECT_DESCRIPTION__CANDIDATES_EXPRESSION:
                return CANDIDATES_EXPRESSION_EDEFAULT == null ? this.candidatesExpression != null : !CANDIDATES_EXPRESSION_EDEFAULT.equals(this.candidatesExpression);
            case ViewPackage.MULTI_SELECT_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION:
                return CANDIDATE_LABEL_EXPRESSION_EDEFAULT == null ? this.candidateLabelExpression != null : !CANDIDATE_LABEL_EXPRESSION_EDEFAULT.equals(this.candidateLabelExpression);
            case ViewPackage.MULTI_SELECT_DESCRIPTION__BODY:
                return this.body != null && !this.body.isEmpty();
            case ViewPackage.MULTI_SELECT_DESCRIPTION__STYLE:
                return this.style != null;
            case ViewPackage.MULTI_SELECT_DESCRIPTION__CONDITIONAL_STYLES:
                return this.conditionalStyles != null && !this.conditionalStyles.isEmpty();
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String toString() {
        if (this.eIsProxy())
            return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (valueExpression: ");
        result.append(this.valueExpression);
        result.append(", candidatesExpression: ");
        result.append(this.candidatesExpression);
        result.append(", candidateLabelExpression: ");
        result.append(this.candidateLabelExpression);
        result.append(')');
        return result.toString();
    }

} // MultiSelectDescriptionImpl
