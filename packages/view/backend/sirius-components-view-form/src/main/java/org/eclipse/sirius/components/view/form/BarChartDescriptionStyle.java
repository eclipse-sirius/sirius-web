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
package org.eclipse.sirius.components.view.form;

import org.eclipse.sirius.components.view.LabelStyle;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Bar Chart Description Style</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.BarChartDescriptionStyle#getBarsColor <em>Bars Color</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.form.FormPackage#getBarChartDescriptionStyle()
 * @model
 * @generated
 */
public interface BarChartDescriptionStyle extends WidgetDescriptionStyle, LabelStyle {
    /**
     * Returns the value of the '<em><b>Bars Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Bars Color</em>' attribute.
     * @see #setBarsColor(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getBarChartDescriptionStyle_BarsColor()
     * @model
     * @generated
     */
    String getBarsColor();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.BarChartDescriptionStyle#getBarsColor
     * <em>Bars Color</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Bars Color</em>' attribute.
     * @see #getBarsColor()
     * @generated
     */
    void setBarsColor(String value);

} // BarChartDescriptionStyle
