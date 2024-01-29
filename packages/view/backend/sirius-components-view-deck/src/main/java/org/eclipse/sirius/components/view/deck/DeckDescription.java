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

import org.eclipse.emf.common.util.EList;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.UserColor;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Description</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.deck.DeckDescription#getLaneDescriptions <em>Lane
 * Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.deck.DeckDescription#getBackgroundColor <em>Background Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.deck.DeckDescription#getLaneDropTool <em>Lane Drop Tool</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.deck.DeckPackage#getDeckDescription()
 * @model
 * @generated
 */
public interface DeckDescription extends RepresentationDescription {
    /**
     * Returns the value of the '<em><b>Lane Descriptions</b></em>' containment reference list. The list contents are of
     * type {@link org.eclipse.sirius.components.view.deck.LaneDescription}. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Lane Descriptions</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.deck.DeckPackage#getDeckDescription_LaneDescriptions()
     * @model containment="true"
     * @generated
     */
    EList<LaneDescription> getLaneDescriptions();

    /**
     * Returns the value of the '<em><b>Background Color</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Background Color</em>' containment reference.
     * @see #setBackgroundColor(UserColor)
     * @see org.eclipse.sirius.components.view.deck.DeckPackage#getDeckDescription_BackgroundColor()
     * @model containment="true"
     * @generated
     */
    UserColor getBackgroundColor();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.deck.DeckDescription#getBackgroundColor
     * <em>Background Color</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Background Color</em>' containment reference.
     * @see #getBackgroundColor()
     * @generated
     */
    void setBackgroundColor(UserColor value);

    /**
     * Returns the value of the '<em><b>Lane Drop Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Lane Drop Tool</em>' containment reference.
     * @see #setLaneDropTool(LaneDropTool)
     * @see org.eclipse.sirius.components.view.deck.DeckPackage#getDeckDescription_LaneDropTool()
     * @model containment="true"
     * @generated
     */
    LaneDropTool getLaneDropTool();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.deck.DeckDescription#getLaneDropTool <em>Lane
     * Drop Tool</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Lane Drop Tool</em>' containment reference.
     * @see #getLaneDropTool()
     * @generated
     */
    void setLaneDropTool(LaneDropTool value);

} // DeckDescription
