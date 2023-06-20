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
package org.eclipse.sirius.components.view.form;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Bar Chart Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.BarChartDescription#getValuesExpression <em>Values
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.BarChartDescription#getKeysExpression <em>Keys
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.BarChartDescription#getYAxisLabelExpression <em>YAxis Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.BarChartDescription#getStyle <em>Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.BarChartDescription#getConditionalStyles <em>Conditional
 * Styles</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.BarChartDescription#getWidth <em>Width</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.BarChartDescription#getHeight <em>Height</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.form.FormPackage#getBarChartDescription()
 * @model
 * @generated
 */
public interface BarChartDescription extends WidgetDescription {
    /**
     * Returns the value of the '<em><b>Values Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Values Expression</em>' attribute.
     * @see #setValuesExpression(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getBarChartDescription_ValuesExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getValuesExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.BarChartDescription#getValuesExpression
     * <em>Values Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Values Expression</em>' attribute.
     * @see #getValuesExpression()
     * @generated
     */
    void setValuesExpression(String value);

    /**
     * Returns the value of the '<em><b>Keys Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Keys Expression</em>' attribute.
     * @see #setKeysExpression(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getBarChartDescription_KeysExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getKeysExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.BarChartDescription#getKeysExpression
     * <em>Keys Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Keys Expression</em>' attribute.
     * @see #getKeysExpression()
     * @generated
     */
    void setKeysExpression(String value);

    /**
     * Returns the value of the '<em><b>YAxis Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>YAxis Label Expression</em>' attribute.
     * @see #setYAxisLabelExpression(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getBarChartDescription_YAxisLabelExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getYAxisLabelExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.BarChartDescription#getYAxisLabelExpression
     * <em>YAxis Label Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>YAxis Label Expression</em>' attribute.
     * @see #getYAxisLabelExpression()
     * @generated
     */
    void setYAxisLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Style</em>' containment reference.
     * @see #setStyle(BarChartDescriptionStyle)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getBarChartDescription_Style()
     * @model containment="true"
     * @generated
     */
    BarChartDescriptionStyle getStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.BarChartDescription#getStyle
     * <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Style</em>' containment reference.
     * @see #getStyle()
     * @generated
     */
    void setStyle(BarChartDescriptionStyle value);

    /**
     * Returns the value of the '<em><b>Conditional Styles</b></em>' containment reference list. The list contents are
     * of type {@link org.eclipse.sirius.components.view.form.ConditionalBarChartDescriptionStyle}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Conditional Styles</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.form.FormPackage#getBarChartDescription_ConditionalStyles()
     * @model containment="true"
     * @generated
     */
    EList<ConditionalBarChartDescriptionStyle> getConditionalStyles();

    /**
     * Returns the value of the '<em><b>Width</b></em>' attribute. The default value is <code>"500"</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Width</em>' attribute.
     * @see #setWidth(int)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getBarChartDescription_Width()
     * @model default="500" dataType="org.eclipse.sirius.components.view.Length" required="true"
     * @generated
     */
    int getWidth();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.BarChartDescription#getWidth
     * <em>Width</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Width</em>' attribute.
     * @see #getWidth()
     * @generated
     */
    void setWidth(int value);

    /**
     * Returns the value of the '<em><b>Height</b></em>' attribute. The default value is <code>"250"</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Height</em>' attribute.
     * @see #setHeight(int)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getBarChartDescription_Height()
     * @model default="250" dataType="org.eclipse.sirius.components.view.Length" required="true"
     * @generated
     */
    int getHeight();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.BarChartDescription#getHeight
     * <em>Height</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Height</em>' attribute.
     * @see #getHeight()
     * @generated
     */
    void setHeight(int value);

} // BarChartDescription
