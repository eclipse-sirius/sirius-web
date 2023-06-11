/**
 * Copyright (c) 2021, 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.sirius.components.view.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.DSelectWidgetDescription;
import org.eclipse.sirius.components.view.SelectDescriptionStyle;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>DSelect Widget Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.impl.DSelectWidgetDescriptionImpl#getOptionsExpression <em>Options
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.DSelectWidgetDescriptionImpl#getOptionLabelExpression <em>Option
 * Label Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.DSelectWidgetDescriptionImpl#getStyle <em>Style</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DSelectWidgetDescriptionImpl extends DWidgetDescriptionImpl implements DSelectWidgetDescription {
    /**
     * The default value of the '{@link #getOptionsExpression() <em>Options Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getOptionsExpression()
     * @generated
     * @ordered
     */
    protected static final String OPTIONS_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getOptionsExpression() <em>Options Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getOptionsExpression()
     * @generated
     * @ordered
     */
    protected String optionsExpression = OPTIONS_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getOptionLabelExpression() <em>Option Label Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getOptionLabelExpression()
     * @generated
     * @ordered
     */
    protected static final String OPTION_LABEL_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getOptionLabelExpression() <em>Option Label Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getOptionLabelExpression()
     * @generated
     * @ordered
     */
    protected String optionLabelExpression = OPTION_LABEL_EXPRESSION_EDEFAULT;

    /**
     * The cached value of the '{@link #getStyle() <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getStyle()
     * @generated
     * @ordered
     */
    protected SelectDescriptionStyle style;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DSelectWidgetDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.DSELECT_WIDGET_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getOptionsExpression() {
        return this.optionsExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setOptionsExpression(String newOptionsExpression) {
        String oldOptionsExpression = this.optionsExpression;
        this.optionsExpression = newOptionsExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.DSELECT_WIDGET_DESCRIPTION__OPTIONS_EXPRESSION, oldOptionsExpression, this.optionsExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getOptionLabelExpression() {
        return this.optionLabelExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setOptionLabelExpression(String newOptionLabelExpression) {
        String oldOptionLabelExpression = this.optionLabelExpression;
        this.optionLabelExpression = newOptionLabelExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.DSELECT_WIDGET_DESCRIPTION__OPTION_LABEL_EXPRESSION, oldOptionLabelExpression, this.optionLabelExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public SelectDescriptionStyle getStyle() {
        return this.style;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetStyle(SelectDescriptionStyle newStyle, NotificationChain msgs) {
        SelectDescriptionStyle oldStyle = this.style;
        this.style = newStyle;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ViewPackage.DSELECT_WIDGET_DESCRIPTION__STYLE, oldStyle, newStyle);
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
    public void setStyle(SelectDescriptionStyle newStyle) {
        if (newStyle != this.style) {
            NotificationChain msgs = null;
            if (this.style != null)
                msgs = ((InternalEObject) this.style).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ViewPackage.DSELECT_WIDGET_DESCRIPTION__STYLE, null, msgs);
            if (newStyle != null)
                msgs = ((InternalEObject) newStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ViewPackage.DSELECT_WIDGET_DESCRIPTION__STYLE, null, msgs);
            msgs = this.basicSetStyle(newStyle, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.DSELECT_WIDGET_DESCRIPTION__STYLE, newStyle, newStyle));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ViewPackage.DSELECT_WIDGET_DESCRIPTION__STYLE:
                return this.basicSetStyle(null, msgs);
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
            case ViewPackage.DSELECT_WIDGET_DESCRIPTION__OPTIONS_EXPRESSION:
                return this.getOptionsExpression();
            case ViewPackage.DSELECT_WIDGET_DESCRIPTION__OPTION_LABEL_EXPRESSION:
                return this.getOptionLabelExpression();
            case ViewPackage.DSELECT_WIDGET_DESCRIPTION__STYLE:
                return this.getStyle();
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
            case ViewPackage.DSELECT_WIDGET_DESCRIPTION__OPTIONS_EXPRESSION:
                this.setOptionsExpression((String) newValue);
                return;
            case ViewPackage.DSELECT_WIDGET_DESCRIPTION__OPTION_LABEL_EXPRESSION:
                this.setOptionLabelExpression((String) newValue);
                return;
            case ViewPackage.DSELECT_WIDGET_DESCRIPTION__STYLE:
                this.setStyle((SelectDescriptionStyle) newValue);
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
            case ViewPackage.DSELECT_WIDGET_DESCRIPTION__OPTIONS_EXPRESSION:
                this.setOptionsExpression(OPTIONS_EXPRESSION_EDEFAULT);
                return;
            case ViewPackage.DSELECT_WIDGET_DESCRIPTION__OPTION_LABEL_EXPRESSION:
                this.setOptionLabelExpression(OPTION_LABEL_EXPRESSION_EDEFAULT);
                return;
            case ViewPackage.DSELECT_WIDGET_DESCRIPTION__STYLE:
                this.setStyle((SelectDescriptionStyle) null);
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
            case ViewPackage.DSELECT_WIDGET_DESCRIPTION__OPTIONS_EXPRESSION:
                return OPTIONS_EXPRESSION_EDEFAULT == null ? this.optionsExpression != null : !OPTIONS_EXPRESSION_EDEFAULT.equals(this.optionsExpression);
            case ViewPackage.DSELECT_WIDGET_DESCRIPTION__OPTION_LABEL_EXPRESSION:
                return OPTION_LABEL_EXPRESSION_EDEFAULT == null ? this.optionLabelExpression != null : !OPTION_LABEL_EXPRESSION_EDEFAULT.equals(this.optionLabelExpression);
            case ViewPackage.DSELECT_WIDGET_DESCRIPTION__STYLE:
                return this.style != null;
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
        result.append(" (optionsExpression: ");
        result.append(this.optionsExpression);
        result.append(", optionLabelExpression: ");
        result.append(this.optionLabelExpression);
        result.append(')');
        return result.toString();
    }

} // DSelectWidgetDescriptionImpl
