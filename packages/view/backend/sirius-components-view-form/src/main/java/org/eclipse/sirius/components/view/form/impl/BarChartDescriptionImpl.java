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
import org.eclipse.sirius.components.view.form.BarChartDescription;
import org.eclipse.sirius.components.view.form.BarChartDescriptionStyle;
import org.eclipse.sirius.components.view.form.ConditionalBarChartDescriptionStyle;
import org.eclipse.sirius.components.view.form.FormPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Bar Chart Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.BarChartDescriptionImpl#getValuesExpression <em>Values Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.BarChartDescriptionImpl#getKeysExpression <em>Keys Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.BarChartDescriptionImpl#getYAxisLabelExpression <em>YAxis Label Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.BarChartDescriptionImpl#getStyle <em>Style</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.BarChartDescriptionImpl#getConditionalStyles <em>Conditional Styles</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.BarChartDescriptionImpl#getWidth <em>Width</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.form.impl.BarChartDescriptionImpl#getHeight <em>Height</em>}</li>
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
	 * The default value of the '{@link #getKeysExpression() <em>Keys Expression</em>}' attribute.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getKeysExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String KEYS_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getKeysExpression() <em>Keys Expression</em>}' attribute.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
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
	 * The cached value of the '{@link #getStyle() <em>Style</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getStyle()
	 * @generated
	 * @ordered
	 */
    protected BarChartDescriptionStyle style;

    /**
	 * The cached value of the '{@link #getConditionalStyles() <em>Conditional Styles</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getConditionalStyles()
	 * @generated
	 * @ordered
	 */
    protected EList<ConditionalBarChartDescriptionStyle> conditionalStyles;

    /**
	 * The default value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
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
	 * The default value of the '{@link #getHeight() <em>Height</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getHeight()
	 * @generated
	 * @ordered
	 */
    protected static final int HEIGHT_EDEFAULT = 250;

    /**
	 * The cached value of the '{@link #getHeight() <em>Height</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getHeight()
	 * @generated
	 * @ordered
	 */
    protected int height = HEIGHT_EDEFAULT;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected BarChartDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return FormPackage.Literals.BAR_CHART_DESCRIPTION;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getValuesExpression() {
		return valuesExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setValuesExpression(String newValuesExpression) {
		String oldValuesExpression = valuesExpression;
		valuesExpression = newValuesExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.BAR_CHART_DESCRIPTION__VALUES_EXPRESSION, oldValuesExpression, valuesExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getKeysExpression() {
		return keysExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setKeysExpression(String newKeysExpression) {
		String oldKeysExpression = keysExpression;
		keysExpression = newKeysExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.BAR_CHART_DESCRIPTION__KEYS_EXPRESSION, oldKeysExpression, keysExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getYAxisLabelExpression() {
		return yAxisLabelExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setYAxisLabelExpression(String newYAxisLabelExpression) {
		String oldYAxisLabelExpression = yAxisLabelExpression;
		yAxisLabelExpression = newYAxisLabelExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.BAR_CHART_DESCRIPTION__YAXIS_LABEL_EXPRESSION, oldYAxisLabelExpression, yAxisLabelExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public BarChartDescriptionStyle getStyle() {
		return style;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetStyle(BarChartDescriptionStyle newStyle, NotificationChain msgs) {
		BarChartDescriptionStyle oldStyle = style;
		style = newStyle;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FormPackage.BAR_CHART_DESCRIPTION__STYLE, oldStyle, newStyle);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setStyle(BarChartDescriptionStyle newStyle) {
		if (newStyle != style)
		{
			NotificationChain msgs = null;
			if (style != null)
				msgs = ((InternalEObject)style).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FormPackage.BAR_CHART_DESCRIPTION__STYLE, null, msgs);
			if (newStyle != null)
				msgs = ((InternalEObject)newStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - FormPackage.BAR_CHART_DESCRIPTION__STYLE, null, msgs);
			msgs = basicSetStyle(newStyle, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.BAR_CHART_DESCRIPTION__STYLE, newStyle, newStyle));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<ConditionalBarChartDescriptionStyle> getConditionalStyles() {
		if (conditionalStyles == null)
		{
			conditionalStyles = new EObjectContainmentEList<ConditionalBarChartDescriptionStyle>(ConditionalBarChartDescriptionStyle.class, this, FormPackage.BAR_CHART_DESCRIPTION__CONDITIONAL_STYLES);
		}
		return conditionalStyles;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public int getWidth() {
		return width;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setWidth(int newWidth) {
		int oldWidth = width;
		width = newWidth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.BAR_CHART_DESCRIPTION__WIDTH, oldWidth, width));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public int getHeight() {
		return height;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setHeight(int newHeight) {
		int oldHeight = height;
		height = newHeight;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.BAR_CHART_DESCRIPTION__HEIGHT, oldHeight, height));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case FormPackage.BAR_CHART_DESCRIPTION__STYLE:
				return basicSetStyle(null, msgs);
			case FormPackage.BAR_CHART_DESCRIPTION__CONDITIONAL_STYLES:
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
			case FormPackage.BAR_CHART_DESCRIPTION__VALUES_EXPRESSION:
				return getValuesExpression();
			case FormPackage.BAR_CHART_DESCRIPTION__KEYS_EXPRESSION:
				return getKeysExpression();
			case FormPackage.BAR_CHART_DESCRIPTION__YAXIS_LABEL_EXPRESSION:
				return getYAxisLabelExpression();
			case FormPackage.BAR_CHART_DESCRIPTION__STYLE:
				return getStyle();
			case FormPackage.BAR_CHART_DESCRIPTION__CONDITIONAL_STYLES:
				return getConditionalStyles();
			case FormPackage.BAR_CHART_DESCRIPTION__WIDTH:
				return getWidth();
			case FormPackage.BAR_CHART_DESCRIPTION__HEIGHT:
				return getHeight();
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
			case FormPackage.BAR_CHART_DESCRIPTION__VALUES_EXPRESSION:
				setValuesExpression((String)newValue);
				return;
			case FormPackage.BAR_CHART_DESCRIPTION__KEYS_EXPRESSION:
				setKeysExpression((String)newValue);
				return;
			case FormPackage.BAR_CHART_DESCRIPTION__YAXIS_LABEL_EXPRESSION:
				setYAxisLabelExpression((String)newValue);
				return;
			case FormPackage.BAR_CHART_DESCRIPTION__STYLE:
				setStyle((BarChartDescriptionStyle)newValue);
				return;
			case FormPackage.BAR_CHART_DESCRIPTION__CONDITIONAL_STYLES:
				getConditionalStyles().clear();
				getConditionalStyles().addAll((Collection<? extends ConditionalBarChartDescriptionStyle>)newValue);
				return;
			case FormPackage.BAR_CHART_DESCRIPTION__WIDTH:
				setWidth((Integer)newValue);
				return;
			case FormPackage.BAR_CHART_DESCRIPTION__HEIGHT:
				setHeight((Integer)newValue);
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
			case FormPackage.BAR_CHART_DESCRIPTION__VALUES_EXPRESSION:
				setValuesExpression(VALUES_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.BAR_CHART_DESCRIPTION__KEYS_EXPRESSION:
				setKeysExpression(KEYS_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.BAR_CHART_DESCRIPTION__YAXIS_LABEL_EXPRESSION:
				setYAxisLabelExpression(YAXIS_LABEL_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.BAR_CHART_DESCRIPTION__STYLE:
				setStyle((BarChartDescriptionStyle)null);
				return;
			case FormPackage.BAR_CHART_DESCRIPTION__CONDITIONAL_STYLES:
				getConditionalStyles().clear();
				return;
			case FormPackage.BAR_CHART_DESCRIPTION__WIDTH:
				setWidth(WIDTH_EDEFAULT);
				return;
			case FormPackage.BAR_CHART_DESCRIPTION__HEIGHT:
				setHeight(HEIGHT_EDEFAULT);
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
			case FormPackage.BAR_CHART_DESCRIPTION__VALUES_EXPRESSION:
				return VALUES_EXPRESSION_EDEFAULT == null ? valuesExpression != null : !VALUES_EXPRESSION_EDEFAULT.equals(valuesExpression);
			case FormPackage.BAR_CHART_DESCRIPTION__KEYS_EXPRESSION:
				return KEYS_EXPRESSION_EDEFAULT == null ? keysExpression != null : !KEYS_EXPRESSION_EDEFAULT.equals(keysExpression);
			case FormPackage.BAR_CHART_DESCRIPTION__YAXIS_LABEL_EXPRESSION:
				return YAXIS_LABEL_EXPRESSION_EDEFAULT == null ? yAxisLabelExpression != null : !YAXIS_LABEL_EXPRESSION_EDEFAULT.equals(yAxisLabelExpression);
			case FormPackage.BAR_CHART_DESCRIPTION__STYLE:
				return style != null;
			case FormPackage.BAR_CHART_DESCRIPTION__CONDITIONAL_STYLES:
				return conditionalStyles != null && !conditionalStyles.isEmpty();
			case FormPackage.BAR_CHART_DESCRIPTION__WIDTH:
				return width != WIDTH_EDEFAULT;
			case FormPackage.BAR_CHART_DESCRIPTION__HEIGHT:
				return height != HEIGHT_EDEFAULT;
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
		result.append(" (valuesExpression: ");
		result.append(valuesExpression);
		result.append(", keysExpression: ");
		result.append(keysExpression);
		result.append(", yAxisLabelExpression: ");
		result.append(yAxisLabelExpression);
		result.append(", width: ");
		result.append(width);
		result.append(", height: ");
		result.append(height);
		result.append(')');
		return result.toString();
	}

} // BarChartDescriptionImpl
