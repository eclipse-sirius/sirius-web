/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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

import org.eclipse.sirius.components.view.UserColor;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Checkbox Description Style</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.CheckboxDescriptionStyle#getColor <em>Color</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.form.FormPackage#getCheckboxDescriptionStyle()
 * @model
 * @generated
 */
public interface CheckboxDescriptionStyle extends WidgetDescriptionStyle, WidgetGridLayout {

    /**
     * Returns the value of the '<em><b>Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Color</em>' reference.
     * @see #setColor(UserColor)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getCheckboxDescriptionStyle_Color()
     * @model
     * @generated
     */
    UserColor getColor();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.CheckboxDescriptionStyle#getColor
     * <em>Color</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Color</em>' reference.
     * @see #getColor()
     * @generated
     */
    void setColor(UserColor value);

} // CheckboxDescriptionStyle
