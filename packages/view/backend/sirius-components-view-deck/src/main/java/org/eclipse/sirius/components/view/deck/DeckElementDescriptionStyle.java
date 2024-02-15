/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.view.deck;

import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.UserColor;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Element Description Style</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.deck.DeckElementDescriptionStyle#getBackgroundColor <em>Background
 * Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.deck.DeckElementDescriptionStyle#getColor <em>Color</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.deck.DeckPackage#getDeckElementDescriptionStyle()
 * @model
 * @generated
 */
public interface DeckElementDescriptionStyle extends LabelStyle {
    /**
     * Returns the value of the '<em><b>Background Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Background Color</em>' reference.
     * @see #setBackgroundColor(UserColor)
     * @see org.eclipse.sirius.components.view.deck.DeckPackage#getDeckElementDescriptionStyle_BackgroundColor()
     * @model
     * @generated
     */
    UserColor getBackgroundColor();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.deck.DeckElementDescriptionStyle#getBackgroundColor <em>Background
     * Color</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Background Color</em>' reference.
     * @see #getBackgroundColor()
     * @generated
     */
    void setBackgroundColor(UserColor value);

    /**
     * Returns the value of the '<em><b>Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Color</em>' reference.
     * @see #setColor(UserColor)
     * @see org.eclipse.sirius.components.view.deck.DeckPackage#getDeckElementDescriptionStyle_Color()
     * @model
     * @generated
     */
    UserColor getColor();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.deck.DeckElementDescriptionStyle#getColor
     * <em>Color</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Color</em>' reference.
     * @see #getColor()
     * @generated
     */
    void setColor(UserColor value);

} // DeckElementDescriptionStyle
