/**
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.sirius.components.view;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Bar Chart Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.BarChartDescription#getValuesExpression <em>Values
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.BarChartDescription#getKeysExpression <em>Keys Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.BarChartDescription#getYAxisLabelExpression <em>YAxis Label
 * Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getBarChartDescription()
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
     * @see org.eclipse.sirius.components.view.ViewPackage#getBarChartDescription_ValuesExpression()
     * @model
     * @generated
     */
    String getValuesExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.BarChartDescription#getValuesExpression
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
     * @see org.eclipse.sirius.components.view.ViewPackage#getBarChartDescription_KeysExpression()
     * @model
     * @generated
     */
    String getKeysExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.BarChartDescription#getKeysExpression <em>Keys
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
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
     * @see org.eclipse.sirius.components.view.ViewPackage#getBarChartDescription_YAxisLabelExpression()
     * @model
     * @generated
     */
    String getYAxisLabelExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.BarChartDescription#getYAxisLabelExpression
     * <em>YAxis Label Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>YAxis Label Expression</em>' attribute.
     * @see #getYAxisLabelExpression()
     * @generated
     */
    void setYAxisLabelExpression(String value);

} // BarChartDescription
