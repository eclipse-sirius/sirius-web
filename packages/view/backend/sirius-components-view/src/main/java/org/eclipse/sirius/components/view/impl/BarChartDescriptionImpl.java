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
import org.eclipse.sirius.components.view.BarChartDescription;
import org.eclipse.sirius.components.view.BarChartDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalBarChartDescriptionStyle;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Bar Chart Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.impl.BarChartDescriptionImpl#getValuesExpression <em>Values
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.BarChartDescriptionImpl#getKeysExpression <em>Keys
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.BarChartDescriptionImpl#getYAxisLabelExpression <em>YAxis Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.BarChartDescriptionImpl#getStyle <em>Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.BarChartDescriptionImpl#getConditionalStyles <em>Conditional
 * Styles</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.BarChartDescriptionImpl#getWidth <em>Width</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.BarChartDescriptionImpl#getHeight <em>Height</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BarChartDescriptionImpl extends WidgetDescriptionImpl implements BarChartDescription {
    /**
     * The default value of the '{@link #getValuesExpression() <em>Values Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getValuesExpression()
     * @generated
     * @ordered
     */
    protected static final String VALUES_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getValuesExpression() <em>Values Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getValuesExpression()
     * @generated
     * @ordered
     */
    protected String valuesExpression = VALUES_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getKeysExpression() <em>Keys Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getKeysExpression()
     * @generated
     * @ordered
     */
    protected static final String KEYS_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getKeysExpression() <em>Keys Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getKeysExpression()
     * @generated
     * @ordered
     */
    protected String keysExpression = KEYS_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getYAxisLabelExpression() <em>YAxis Label Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getYAxisLabelExpression()
     * @generated
     * @ordered
     */
    protected static final String YAXIS_LABEL_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getYAxisLabelExpression() <em>YAxis Label Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getYAxisLabelExpression()
     * @generated
     * @ordered
     */
    protected String yAxisLabelExpression = YAXIS_LABEL_EXPRESSION_EDEFAULT;

    /**
     * The cached value of the '{@link #getStyle() <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getStyle()
     * @generated
     * @ordered
     */
    protected BarChartDescriptionStyle style;

    /**
     * The cached value of the '{@link #getConditionalStyles() <em>Conditional Styles</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getConditionalStyles()
     * @generated
     * @ordered
     */
    protected EList<ConditionalBarChartDescriptionStyle> conditionalStyles;

    /**
     * The default value of the '{@link #getWidth() <em>Width</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getWidth()
     * @generated
     * @ordered
     */
    protected static final int WIDTH_EDEFAULT = 500;

    /**
     * The cached value of the '{@link #getWidth() <em>Width</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getWidth()
     * @generated
     * @ordered
     */
    protected int width = WIDTH_EDEFAULT;

    /**
     * The default value of the '{@link #getHeight() <em>Height</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getHeight()
     * @generated
     * @ordered
     */
    protected static final int HEIGHT_EDEFAULT = 250;

    /**
     * The cached value of the '{@link #getHeight() <em>Height</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getHeight()
     * @generated
     * @ordered
     */
    protected int height = HEIGHT_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected BarChartDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.BAR_CHART_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getValuesExpression() {
        return this.valuesExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setValuesExpression(String newValuesExpression) {
        String oldValuesExpression = this.valuesExpression;
        this.valuesExpression = newValuesExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.BAR_CHART_DESCRIPTION__VALUES_EXPRESSION, oldValuesExpression, this.valuesExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getKeysExpression() {
        return this.keysExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setKeysExpression(String newKeysExpression) {
        String oldKeysExpression = this.keysExpression;
        this.keysExpression = newKeysExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.BAR_CHART_DESCRIPTION__KEYS_EXPRESSION, oldKeysExpression, this.keysExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getYAxisLabelExpression() {
        return this.yAxisLabelExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setYAxisLabelExpression(String newYAxisLabelExpression) {
        String oldYAxisLabelExpression = this.yAxisLabelExpression;
        this.yAxisLabelExpression = newYAxisLabelExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.BAR_CHART_DESCRIPTION__YAXIS_LABEL_EXPRESSION, oldYAxisLabelExpression, this.yAxisLabelExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public BarChartDescriptionStyle getStyle() {
        return this.style;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetStyle(BarChartDescriptionStyle newStyle, NotificationChain msgs) {
        BarChartDescriptionStyle oldStyle = this.style;
        this.style = newStyle;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ViewPackage.BAR_CHART_DESCRIPTION__STYLE, oldStyle, newStyle);
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
    public void setStyle(BarChartDescriptionStyle newStyle) {
        if (newStyle != this.style) {
            NotificationChain msgs = null;
            if (this.style != null)
                msgs = ((InternalEObject) this.style).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ViewPackage.BAR_CHART_DESCRIPTION__STYLE, null, msgs);
            if (newStyle != null)
                msgs = ((InternalEObject) newStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ViewPackage.BAR_CHART_DESCRIPTION__STYLE, null, msgs);
            msgs = this.basicSetStyle(newStyle, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.BAR_CHART_DESCRIPTION__STYLE, newStyle, newStyle));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<ConditionalBarChartDescriptionStyle> getConditionalStyles() {
        if (this.conditionalStyles == null) {
            this.conditionalStyles = new EObjectContainmentEList<>(ConditionalBarChartDescriptionStyle.class, this, ViewPackage.BAR_CHART_DESCRIPTION__CONDITIONAL_STYLES);
        }
        return this.conditionalStyles;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int getWidth() {
        return this.width;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setWidth(int newWidth) {
        int oldWidth = this.width;
        this.width = newWidth;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.BAR_CHART_DESCRIPTION__WIDTH, oldWidth, this.width));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int getHeight() {
        return this.height;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setHeight(int newHeight) {
        int oldHeight = this.height;
        this.height = newHeight;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.BAR_CHART_DESCRIPTION__HEIGHT, oldHeight, this.height));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ViewPackage.BAR_CHART_DESCRIPTION__STYLE:
                return this.basicSetStyle(null, msgs);
            case ViewPackage.BAR_CHART_DESCRIPTION__CONDITIONAL_STYLES:
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
            case ViewPackage.BAR_CHART_DESCRIPTION__VALUES_EXPRESSION:
                return this.getValuesExpression();
            case ViewPackage.BAR_CHART_DESCRIPTION__KEYS_EXPRESSION:
                return this.getKeysExpression();
            case ViewPackage.BAR_CHART_DESCRIPTION__YAXIS_LABEL_EXPRESSION:
                return this.getYAxisLabelExpression();
            case ViewPackage.BAR_CHART_DESCRIPTION__STYLE:
                return this.getStyle();
            case ViewPackage.BAR_CHART_DESCRIPTION__CONDITIONAL_STYLES:
                return this.getConditionalStyles();
            case ViewPackage.BAR_CHART_DESCRIPTION__WIDTH:
                return this.getWidth();
            case ViewPackage.BAR_CHART_DESCRIPTION__HEIGHT:
                return this.getHeight();
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
            case ViewPackage.BAR_CHART_DESCRIPTION__VALUES_EXPRESSION:
                this.setValuesExpression((String) newValue);
                return;
            case ViewPackage.BAR_CHART_DESCRIPTION__KEYS_EXPRESSION:
                this.setKeysExpression((String) newValue);
                return;
            case ViewPackage.BAR_CHART_DESCRIPTION__YAXIS_LABEL_EXPRESSION:
                this.setYAxisLabelExpression((String) newValue);
                return;
            case ViewPackage.BAR_CHART_DESCRIPTION__STYLE:
                this.setStyle((BarChartDescriptionStyle) newValue);
                return;
            case ViewPackage.BAR_CHART_DESCRIPTION__CONDITIONAL_STYLES:
                this.getConditionalStyles().clear();
                this.getConditionalStyles().addAll((Collection<? extends ConditionalBarChartDescriptionStyle>) newValue);
                return;
            case ViewPackage.BAR_CHART_DESCRIPTION__WIDTH:
                this.setWidth((Integer) newValue);
                return;
            case ViewPackage.BAR_CHART_DESCRIPTION__HEIGHT:
                this.setHeight((Integer) newValue);
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
            case ViewPackage.BAR_CHART_DESCRIPTION__VALUES_EXPRESSION:
                this.setValuesExpression(VALUES_EXPRESSION_EDEFAULT);
                return;
            case ViewPackage.BAR_CHART_DESCRIPTION__KEYS_EXPRESSION:
                this.setKeysExpression(KEYS_EXPRESSION_EDEFAULT);
                return;
            case ViewPackage.BAR_CHART_DESCRIPTION__YAXIS_LABEL_EXPRESSION:
                this.setYAxisLabelExpression(YAXIS_LABEL_EXPRESSION_EDEFAULT);
                return;
            case ViewPackage.BAR_CHART_DESCRIPTION__STYLE:
                this.setStyle((BarChartDescriptionStyle) null);
                return;
            case ViewPackage.BAR_CHART_DESCRIPTION__CONDITIONAL_STYLES:
                this.getConditionalStyles().clear();
                return;
            case ViewPackage.BAR_CHART_DESCRIPTION__WIDTH:
                this.setWidth(WIDTH_EDEFAULT);
                return;
            case ViewPackage.BAR_CHART_DESCRIPTION__HEIGHT:
                this.setHeight(HEIGHT_EDEFAULT);
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
            case ViewPackage.BAR_CHART_DESCRIPTION__VALUES_EXPRESSION:
                return VALUES_EXPRESSION_EDEFAULT == null ? this.valuesExpression != null : !VALUES_EXPRESSION_EDEFAULT.equals(this.valuesExpression);
            case ViewPackage.BAR_CHART_DESCRIPTION__KEYS_EXPRESSION:
                return KEYS_EXPRESSION_EDEFAULT == null ? this.keysExpression != null : !KEYS_EXPRESSION_EDEFAULT.equals(this.keysExpression);
            case ViewPackage.BAR_CHART_DESCRIPTION__YAXIS_LABEL_EXPRESSION:
                return YAXIS_LABEL_EXPRESSION_EDEFAULT == null ? this.yAxisLabelExpression != null : !YAXIS_LABEL_EXPRESSION_EDEFAULT.equals(this.yAxisLabelExpression);
            case ViewPackage.BAR_CHART_DESCRIPTION__STYLE:
                return this.style != null;
            case ViewPackage.BAR_CHART_DESCRIPTION__CONDITIONAL_STYLES:
                return this.conditionalStyles != null && !this.conditionalStyles.isEmpty();
            case ViewPackage.BAR_CHART_DESCRIPTION__WIDTH:
                return this.width != WIDTH_EDEFAULT;
            case ViewPackage.BAR_CHART_DESCRIPTION__HEIGHT:
                return this.height != HEIGHT_EDEFAULT;
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
        result.append(" (valuesExpression: ");
        result.append(this.valuesExpression);
        result.append(", keysExpression: ");
        result.append(this.keysExpression);
        result.append(", yAxisLabelExpression: ");
        result.append(this.yAxisLabelExpression);
        result.append(", width: ");
        result.append(this.width);
        result.append(", height: ");
        result.append(this.height);
        result.append(')');
        return result.toString();
    }

} // BarChartDescriptionImpl
