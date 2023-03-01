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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Checkbox Description Style</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.CheckboxDescriptionStyle#getColor <em>Color</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getCheckboxDescriptionStyle()
 * @model
 * @generated
 */
public interface CheckboxDescriptionStyle extends WidgetDescriptionStyle {
    /**
     * Returns the value of the '<em><b>Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Color</em>' attribute.
     * @see #setColor(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getCheckboxDescriptionStyle_Color()
     * @model dataType="org.eclipse.sirius.components.view.Color"
     * @generated
     */
    String getColor();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.CheckboxDescriptionStyle#getColor
     * <em>Color</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Color</em>' attribute.
     * @see #getColor()
     * @generated
     */
    void setColor(String value);

} // CheckboxDescriptionStyle
