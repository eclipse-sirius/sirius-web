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
package org.eclipse.sirius.components.view;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Pie Chart Description Style</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.PieChartDescriptionStyle#getColors <em>Colors</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.PieChartDescriptionStyle#getStrokeWidth <em>Stroke Width</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.PieChartDescriptionStyle#getStrokeColor <em>Stroke Color</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getPieChartDescriptionStyle()
 * @model
 * @generated
 */
public interface PieChartDescriptionStyle extends WidgetDescriptionStyle, LabelStyle {
    /**
     * Returns the value of the '<em><b>Colors</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Colors</em>' attribute.
     * @see #setColors(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getPieChartDescriptionStyle_Colors()
     * @model
     * @generated
     */
    String getColors();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.PieChartDescriptionStyle#getColors
     * <em>Colors</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Colors</em>' attribute.
     * @see #getColors()
     * @generated
     */
    void setColors(String value);

    /**
     * Returns the value of the '<em><b>Stroke Width</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Stroke Width</em>' attribute.
     * @see #setStrokeWidth(int)
     * @see org.eclipse.sirius.components.view.ViewPackage#getPieChartDescriptionStyle_StrokeWidth()
     * @model dataType="org.eclipse.sirius.components.view.Length"
     * @generated
     */
    int getStrokeWidth();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.PieChartDescriptionStyle#getStrokeWidth
     * <em>Stroke Width</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Stroke Width</em>' attribute.
     * @see #getStrokeWidth()
     * @generated
     */
    void setStrokeWidth(int value);

    /**
     * Returns the value of the '<em><b>Stroke Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Stroke Color</em>' attribute.
     * @see #setStrokeColor(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getPieChartDescriptionStyle_StrokeColor()
     * @model dataType="org.eclipse.sirius.components.view.Color"
     * @generated
     */
    String getStrokeColor();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.PieChartDescriptionStyle#getStrokeColor
     * <em>Stroke Color</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Stroke Color</em>' attribute.
     * @see #getStrokeColor()
     * @generated
     */
    void setStrokeColor(String value);

} // PieChartDescriptionStyle
