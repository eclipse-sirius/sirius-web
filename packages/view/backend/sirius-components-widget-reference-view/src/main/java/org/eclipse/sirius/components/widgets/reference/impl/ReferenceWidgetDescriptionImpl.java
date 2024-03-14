/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.widgets.reference.impl;

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
import org.eclipse.sirius.components.widgets.reference.ConditionalReferenceWidgetDescriptionStyle;
import org.eclipse.sirius.components.widgets.reference.ReferencePackage;
import org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription;
import org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescriptionStyle;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Widget Description</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.widgets.reference.impl.ReferenceWidgetDescriptionImpl#getReferenceOwnerExpression
 * <em>Reference Owner Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.widgets.reference.impl.ReferenceWidgetDescriptionImpl#getReferenceNameExpression
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
     *
     * @see #getReferenceOwnerExpression()
     * @generated
     * @ordered
     */
    protected static final String REFERENCE_OWNER_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getReferenceOwnerExpression() <em>Reference Owner Expression</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getReferenceOwnerExpression()
     * @generated
     * @ordered
     */
    protected String referenceOwnerExpression = REFERENCE_OWNER_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getReferenceNameExpression() <em>Reference Name Expression</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getReferenceNameExpression()
     * @generated
     * @ordered
     */
    protected static final String REFERENCE_NAME_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getReferenceNameExpression() <em>Reference Name Expression</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getReferenceNameExpression()
     * @generated
     * @ordered
     */
    protected String referenceNameExpression = REFERENCE_NAME_EXPRESSION_EDEFAULT;

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
    protected ReferenceWidgetDescriptionStyle style;

    /**
     * The cached value of the '{@link #getConditionalStyles() <em>Conditional Styles</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getConditionalStyles()
     * @generated
     * @ordered
     */
    protected EList<ConditionalReferenceWidgetDescriptionStyle> conditionalStyles;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ReferenceWidgetDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ReferencePackage.Literals.REFERENCE_WIDGET_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getReferenceOwnerExpression() {
        return this.referenceOwnerExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setReferenceOwnerExpression(String newReferenceOwnerExpression) {
        String oldReferenceOwnerExpression = this.referenceOwnerExpression;
        this.referenceOwnerExpression = newReferenceOwnerExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION, oldReferenceOwnerExpression,
                    this.referenceOwnerExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getReferenceNameExpression() {
        return this.referenceNameExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setReferenceNameExpression(String newReferenceNameExpression) {
        String oldReferenceNameExpression = this.referenceNameExpression;
        this.referenceNameExpression = newReferenceNameExpression;
        if (this.eNotificationRequired())
            this.eNotify(
                    new ENotificationImpl(this, Notification.SET, ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION, oldReferenceNameExpression, this.referenceNameExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<Operation> getBody() {
        if (this.body == null) {
            this.body = new EObjectContainmentEList<>(Operation.class, this, ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__BODY);
        }
        return this.body;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ReferenceWidgetDescriptionStyle getStyle() {
        return this.style;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setStyle(ReferenceWidgetDescriptionStyle newStyle) {
        if (newStyle != this.style) {
            NotificationChain msgs = null;
            if (this.style != null)
                msgs = ((InternalEObject) this.style).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__STYLE, null, msgs);
            if (newStyle != null)
                msgs = ((InternalEObject) newStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__STYLE, null, msgs);
            msgs = this.basicSetStyle(newStyle, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__STYLE, newStyle, newStyle));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetStyle(ReferenceWidgetDescriptionStyle newStyle, NotificationChain msgs) {
        ReferenceWidgetDescriptionStyle oldStyle = this.style;
        this.style = newStyle;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__STYLE, oldStyle, newStyle);
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
    public EList<ConditionalReferenceWidgetDescriptionStyle> getConditionalStyles() {
        if (this.conditionalStyles == null) {
            this.conditionalStyles = new EObjectContainmentEList<>(ConditionalReferenceWidgetDescriptionStyle.class, this, ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES);
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
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__BODY:
                return ((InternalEList<?>) this.getBody()).basicRemove(otherEnd, msgs);
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__STYLE:
                return this.basicSetStyle(null, msgs);
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
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
            this.eNotify(new ENotificationImpl(this, Notification.SET, ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION, oldIsEnabledExpression, this.isEnabledExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
                return this.getIsEnabledExpression();
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION:
                return this.getReferenceOwnerExpression();
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION:
                return this.getReferenceNameExpression();
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__BODY:
                return this.getBody();
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__STYLE:
                return this.getStyle();
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
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
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
                this.setIsEnabledExpression((String) newValue);
                return;
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION:
                this.setReferenceOwnerExpression((String) newValue);
                return;
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION:
                this.setReferenceNameExpression((String) newValue);
                return;
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__BODY:
                this.getBody().clear();
                this.getBody().addAll((Collection<? extends Operation>) newValue);
                return;
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__STYLE:
                this.setStyle((ReferenceWidgetDescriptionStyle) newValue);
                return;
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
                this.getConditionalStyles().clear();
                this.getConditionalStyles().addAll((Collection<? extends ConditionalReferenceWidgetDescriptionStyle>) newValue);
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
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
                this.setIsEnabledExpression(IS_ENABLED_EXPRESSION_EDEFAULT);
                return;
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION:
                this.setReferenceOwnerExpression(REFERENCE_OWNER_EXPRESSION_EDEFAULT);
                return;
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION:
                this.setReferenceNameExpression(REFERENCE_NAME_EXPRESSION_EDEFAULT);
                return;
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__BODY:
                this.getBody().clear();
                return;
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__STYLE:
                this.setStyle((ReferenceWidgetDescriptionStyle) null);
                return;
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
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
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION:
                return IS_ENABLED_EXPRESSION_EDEFAULT == null ? this.isEnabledExpression != null : !IS_ENABLED_EXPRESSION_EDEFAULT.equals(this.isEnabledExpression);
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION:
                return REFERENCE_OWNER_EXPRESSION_EDEFAULT == null ? this.referenceOwnerExpression != null : !REFERENCE_OWNER_EXPRESSION_EDEFAULT.equals(this.referenceOwnerExpression);
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION:
                return REFERENCE_NAME_EXPRESSION_EDEFAULT == null ? this.referenceNameExpression != null : !REFERENCE_NAME_EXPRESSION_EDEFAULT.equals(this.referenceNameExpression);
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__BODY:
                return this.body != null && !this.body.isEmpty();
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__STYLE:
                return this.style != null;
            case ReferencePackage.REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES:
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
        result.append(" (isEnabledExpression: ");
        result.append(this.isEnabledExpression);
        result.append(", referenceOwnerExpression: ");
        result.append(this.referenceOwnerExpression);
        result.append(", referenceNameExpression: ");
        result.append(this.referenceNameExpression);
        result.append(')');
        return result.toString();
    }

} // ReferenceWidgetDescriptionImpl
