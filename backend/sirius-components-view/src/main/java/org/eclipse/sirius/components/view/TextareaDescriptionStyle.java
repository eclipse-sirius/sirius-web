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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Textarea Description Style</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.TextareaDescriptionStyle#getBackgroundColor <em>Background
 * Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.TextareaDescriptionStyle#getForegroundColor <em>Foreground
 * Color</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getTextareaDescriptionStyle()
 * @model
 * @generated
 */
public interface TextareaDescriptionStyle extends WidgetDescriptionStyle, LabelStyle {
    /**
     * Returns the value of the '<em><b>Background Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Background Color</em>' attribute.
     * @see #setBackgroundColor(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getTextareaDescriptionStyle_BackgroundColor()
     * @model
     * @generated
     */
    String getBackgroundColor();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.TextareaDescriptionStyle#getBackgroundColor
     * <em>Background Color</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Background Color</em>' attribute.
     * @see #getBackgroundColor()
     * @generated
     */
    void setBackgroundColor(String value);

    /**
     * Returns the value of the '<em><b>Foreground Color</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Foreground Color</em>' attribute.
     * @see #setForegroundColor(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getTextareaDescriptionStyle_ForegroundColor()
     * @model
     * @generated
     */
    String getForegroundColor();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.TextareaDescriptionStyle#getForegroundColor
     * <em>Foreground Color</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Foreground Color</em>' attribute.
     * @see #getForegroundColor()
     * @generated
     */
    void setForegroundColor(String value);

} // TextareaDescriptionStyle
