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
import org.eclipse.sirius.components.view.form.ConditionalDateTimeDescriptionStyle;
import org.eclipse.sirius.components.view.form.DateTimeDescription;
import org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle;
import org.eclipse.sirius.components.view.form.DateTimeType;
import org.eclipse.sirius.components.view.form.FormPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Date Time Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.DateTimeDescriptionImpl#getStringValueExpression <em>String
 * Value Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.DateTimeDescriptionImpl#getBody <em>Body</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.DateTimeDescriptionImpl#getIsEnabledExpression <em>Is Enabled
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.DateTimeDescriptionImpl#getStyle <em>Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.DateTimeDescriptionImpl#getConditionalStyles <em>Conditional
 * Styles</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.DateTimeDescriptionImpl#getType <em>Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DateTimeDescriptionImpl extends WidgetDescriptionImpl implements DateTimeDescription {
    /**
     * The default value of the '{@link #getStringValueExpression() <em>String Value Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getStringValueExpression()
     * @generated
     * @ordered
     */
    protected static final String STRING_VALUE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getStringValueExpression() <em>String Value Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getStringValueExpression()
     * @generated
     * @ordered
     */
    protected String stringValueExpression = STRING_VALUE_EXPRESSION_EDEFAULT;

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
     * The default value of the '{@link #getIsEnabledExpression() <em>Is Enabled Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIsEnabledExpression()
     * @generated
     * @ordered
     */
    protected static final String IS_ENABLED_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIsEnabledExpression() <em>Is Enabled Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIsEnabledExpression()
     * @generated
     * @ordered
     */
    protected String isEnabledExpression = IS_ENABLED_EXPRESSION_EDEFAULT;

    /**
     * The cached value of the '{@link #getStyle() <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getStyle()
     * @generated
     * @ordered
     */
    protected DateTimeDescriptionStyle style;

    /**
     * The cached value of the '{@link #getConditionalStyles() <em>Conditional Styles</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getConditionalStyles()
     * @generated
     * @ordered
     */
    protected EList<ConditionalDateTimeDescriptionStyle> conditionalStyles;

    /**
     * The default value of the '{@link #getType() <em>Type</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getType()
     * @generated
     * @ordered
     */
    protected static final DateTimeType TYPE_EDEFAULT = DateTimeType.DATE_TIME;

    /**
     * The cached value of the '{@link #getType() <em>Type</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getType()
     * @generated
     * @ordered
     */
    protected DateTimeType type = TYPE_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DateTimeDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return FormPackage.Literals.DATE_TIME_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getStringValueExpression() {
        return this.stringValueExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setStringValueExpression(String newStringValueExpression) {
        String oldStringValueExpression = this.stringValueExpression;
        this.stringValueExpression = newStringValueExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.DATE_TIME_DESCRIPTION__STRING_VALUE_EXPRESSION, oldStringValueExpression, this.stringValueExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Operation> getBody() {
        if (this.body == null) {
            this.body = new EObjectContainmentEList<>(Operation.class, this, FormPackage.DATE_TIME_DESCRIPTION__BODY);
        }
        return this.body;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getIsEnabledExpression() {
        return this.isEnabledExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setIsEnabledExpression(String newIsEnabledExpression) {
        String oldIsEnabledExpression = this.isEnabledExpression;
        this.isEnabledExpression = newIsEnabledExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.DATE_TIME_DESCRIPTION__IS_ENABLED_EXPRESSION, oldIsEnabledExpression, this.isEnabledExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DateTimeDescriptionStyle getStyle() {
        return this.style;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetStyle(DateTimeDescriptionStyle newStyle, NotificationChain msgs) {
        DateTimeDescriptionStyle oldStyle = this.style;
        this.style = newStyle;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FormPackage.DATE_TIME_DESCRIPTION__STYLE, oldStyle, newStyle);
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
    public void setStyle(DateTimeDescriptionStyle newStyle) {
        if (newStyle != this.style) {
            NotificationChain msgs = null;
            if (this.style != null)
                msgs = ((InternalEObject) this.style).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FormPackage.DATE_TIME_DESCRIPTION__STYLE, null, msgs);
            if (newStyle != null)
                msgs = ((InternalEObject) newStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - FormPackage.DATE_TIME_DESCRIPTION__STYLE, null, msgs);
            msgs = this.basicSetStyle(newStyle, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.DATE_TIME_DESCRIPTION__STYLE, newStyle, newStyle));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<ConditionalDateTimeDescriptionStyle> getConditionalStyles() {
        if (this.conditionalStyles == null) {
            this.conditionalStyles = new EObjectContainmentEList<>(ConditionalDateTimeDescriptionStyle.class, this,
                    FormPackage.DATE_TIME_DESCRIPTION__CONDITIONAL_STYLES);
        }
        return this.conditionalStyles;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DateTimeType getType() {
        return this.type;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setType(DateTimeType newType) {
        DateTimeType oldType = this.type;
        this.type = newType == null ? TYPE_EDEFAULT : newType;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.DATE_TIME_DESCRIPTION__TYPE, oldType, this.type));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case FormPackage.DATE_TIME_DESCRIPTION__BODY:
                return ((InternalEList<?>) this.getBody()).basicRemove(otherEnd, msgs);
            case FormPackage.DATE_TIME_DESCRIPTION__STYLE:
                return this.basicSetStyle(null, msgs);
            case FormPackage.DATE_TIME_DESCRIPTION__CONDITIONAL_STYLES:
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
            case FormPackage.DATE_TIME_DESCRIPTION__STRING_VALUE_EXPRESSION:
                return this.getStringValueExpression();
            case FormPackage.DATE_TIME_DESCRIPTION__BODY:
                return this.getBody();
            case FormPackage.DATE_TIME_DESCRIPTION__IS_ENABLED_EXPRESSION:
                return this.getIsEnabledExpression();
            case FormPackage.DATE_TIME_DESCRIPTION__STYLE:
                return this.getStyle();
            case FormPackage.DATE_TIME_DESCRIPTION__CONDITIONAL_STYLES:
                return this.getConditionalStyles();
            case FormPackage.DATE_TIME_DESCRIPTION__TYPE:
                return this.getType();
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
            case FormPackage.DATE_TIME_DESCRIPTION__STRING_VALUE_EXPRESSION:
                this.setStringValueExpression((String) newValue);
                return;
            case FormPackage.DATE_TIME_DESCRIPTION__BODY:
                this.getBody().clear();
                this.getBody().addAll((Collection<? extends Operation>) newValue);
                return;
            case FormPackage.DATE_TIME_DESCRIPTION__IS_ENABLED_EXPRESSION:
                this.setIsEnabledExpression((String) newValue);
                return;
            case FormPackage.DATE_TIME_DESCRIPTION__STYLE:
                this.setStyle((DateTimeDescriptionStyle) newValue);
                return;
            case FormPackage.DATE_TIME_DESCRIPTION__CONDITIONAL_STYLES:
                this.getConditionalStyles().clear();
                this.getConditionalStyles().addAll((Collection<? extends ConditionalDateTimeDescriptionStyle>) newValue);
                return;
            case FormPackage.DATE_TIME_DESCRIPTION__TYPE:
                this.setType((DateTimeType) newValue);
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
            case FormPackage.DATE_TIME_DESCRIPTION__STRING_VALUE_EXPRESSION:
                this.setStringValueExpression(STRING_VALUE_EXPRESSION_EDEFAULT);
                return;
            case FormPackage.DATE_TIME_DESCRIPTION__BODY:
                this.getBody().clear();
                return;
            case FormPackage.DATE_TIME_DESCRIPTION__IS_ENABLED_EXPRESSION:
                this.setIsEnabledExpression(IS_ENABLED_EXPRESSION_EDEFAULT);
                return;
            case FormPackage.DATE_TIME_DESCRIPTION__STYLE:
                this.setStyle((DateTimeDescriptionStyle) null);
                return;
            case FormPackage.DATE_TIME_DESCRIPTION__CONDITIONAL_STYLES:
                this.getConditionalStyles().clear();
                return;
            case FormPackage.DATE_TIME_DESCRIPTION__TYPE:
                this.setType(TYPE_EDEFAULT);
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
            case FormPackage.DATE_TIME_DESCRIPTION__STRING_VALUE_EXPRESSION:
                return STRING_VALUE_EXPRESSION_EDEFAULT == null ? this.stringValueExpression != null : !STRING_VALUE_EXPRESSION_EDEFAULT.equals(this.stringValueExpression);
            case FormPackage.DATE_TIME_DESCRIPTION__BODY:
                return this.body != null && !this.body.isEmpty();
            case FormPackage.DATE_TIME_DESCRIPTION__IS_ENABLED_EXPRESSION:
                return IS_ENABLED_EXPRESSION_EDEFAULT == null ? this.isEnabledExpression != null : !IS_ENABLED_EXPRESSION_EDEFAULT.equals(this.isEnabledExpression);
            case FormPackage.DATE_TIME_DESCRIPTION__STYLE:
                return this.style != null;
            case FormPackage.DATE_TIME_DESCRIPTION__CONDITIONAL_STYLES:
                return this.conditionalStyles != null && !this.conditionalStyles.isEmpty();
            case FormPackage.DATE_TIME_DESCRIPTION__TYPE:
                return this.type != TYPE_EDEFAULT;
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
        result.append(" (stringValueExpression: ");
        result.append(this.stringValueExpression);
        result.append(", isEnabledExpression: ");
        result.append(this.isEnabledExpression);
        result.append(", type: ");
        result.append(this.type);
        result.append(')');
        return result.toString();
    }

} // DateTimeDescriptionImpl
