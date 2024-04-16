/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Date Time Description Style</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle#getBackgroundColor <em>Background
 * Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle#getForegroundColor <em>Foreground
 * Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle#isItalic <em>Italic</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle#isBold <em>Bold</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.form.FormPackage#getDateTimeDescriptionStyle()
 * @model
 * @generated
 */
public interface DateTimeDescriptionStyle extends WidgetDescriptionStyle {
    /**
     * Returns the value of the '<em><b>Background Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Background Color</em>' reference.
     * @see #setBackgroundColor(UserColor)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getDateTimeDescriptionStyle_BackgroundColor()
     * @model
     * @generated
     */
    UserColor getBackgroundColor();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle#getBackgroundColor
     * <em>Background Color</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Background Color</em>' reference.
     * @see #getBackgroundColor()
     * @generated
     */
    void setBackgroundColor(UserColor value);

    /**
     * Returns the value of the '<em><b>Foreground Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Foreground Color</em>' reference.
     * @see #setForegroundColor(UserColor)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getDateTimeDescriptionStyle_ForegroundColor()
     * @model
     * @generated
     */
    UserColor getForegroundColor();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle#getForegroundColor
     * <em>Foreground Color</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Foreground Color</em>' reference.
     * @see #getForegroundColor()
     * @generated
     */
    void setForegroundColor(UserColor value);

    /**
     * Returns the value of the '<em><b>Italic</b></em>' attribute. The default value is <code>"false"</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Italic</em>' attribute.
     * @see #setItalic(boolean)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getDateTimeDescriptionStyle_Italic()
     * @model default="false" required="true"
     * @generated
     */
    boolean isItalic();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle#isItalic
     * <em>Italic</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Italic</em>' attribute.
     * @see #isItalic()
     * @generated
     */
    void setItalic(boolean value);

    /**
     * Returns the value of the '<em><b>Bold</b></em>' attribute. The default value is <code>"false"</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Bold</em>' attribute.
     * @see #setBold(boolean)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getDateTimeDescriptionStyle_Bold()
     * @model default="false" required="true"
     * @generated
     */
    boolean isBold();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle#isBold
     * <em>Bold</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Bold</em>' attribute.
     * @see #isBold()
     * @generated
     */
    void setBold(boolean value);

} // DateTimeDescriptionStyle
