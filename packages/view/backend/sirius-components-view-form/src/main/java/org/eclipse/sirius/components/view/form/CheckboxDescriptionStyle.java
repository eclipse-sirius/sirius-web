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
 * <li>{@link org.eclipse.sirius.components.view.form.CheckboxDescriptionStyle#getLabelPlacement <em>Label
 * Placement</em>}</li>
 * </ul>
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.form.FormPackage#getCheckboxDescriptionStyle()
 */
public interface CheckboxDescriptionStyle extends WidgetDescriptionStyle {

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

    /**
     * Returns the value of the '<em><b>Label Placement</b></em>' attribute. The default value is <code>"end"</code>.
     * The literals are from the enumeration {@link org.eclipse.sirius.components.view.form.LabelPlacement}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Label Placement</em>' attribute.
     * @model default="end" required="true"
     * @generated
     * @see org.eclipse.sirius.components.view.form.LabelPlacement
     * @see #setLabelPlacement(LabelPlacement)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getCheckboxDescriptionStyle_LabelPlacement()
     */
    LabelPlacement getLabelPlacement();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.CheckboxDescriptionStyle#getLabelPlacement
     * <em>Label Placement</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Label Placement</em>' attribute.
     * @generated
     * @see org.eclipse.sirius.components.view.form.LabelPlacement
     * @see #getLabelPlacement()
     */
    void setLabelPlacement(LabelPlacement value);

} // CheckboxDescriptionStyle
