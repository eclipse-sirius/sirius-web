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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract class of
 * the model. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.deck.DeckPackage
 * @generated
 */
public interface DeckFactory extends EFactory {
    /**
     * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    DeckFactory eINSTANCE = org.eclipse.sirius.components.view.deck.impl.DeckFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Description</em>'.
     * @generated
     */
    DeckDescription createDeckDescription();

    /**
     * Returns a new object of class '<em>Lane Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Lane Description</em>'.
     * @generated
     */
    LaneDescription createLaneDescription();

    /**
     * Returns a new object of class '<em>Card Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Card Description</em>'.
     * @generated
     */
    CardDescription createCardDescription();

    /**
     * Returns a new object of class '<em>Create Card Tool</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Create Card Tool</em>'.
     * @generated
     */
    CreateCardTool createCreateCardTool();

    /**
     * Returns a new object of class '<em>Edit Card Tool</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Edit Card Tool</em>'.
     * @generated
     */
    EditCardTool createEditCardTool();

    /**
     * Returns a new object of class '<em>Delete Card Tool</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Delete Card Tool</em>'.
     * @generated
     */
    DeleteCardTool createDeleteCardTool();

    /**
     * Returns a new object of class '<em>Edit Lane Tool</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Edit Lane Tool</em>'.
     * @generated
     */
    EditLaneTool createEditLaneTool();

    /**
     * Returns a new object of class '<em>Card Drop Tool</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Card Drop Tool</em>'.
     * @generated
     */
    CardDropTool createCardDropTool();

    /**
     * Returns a new object of class '<em>Lane Drop Tool</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Lane Drop Tool</em>'.
     * @generated
     */
    LaneDropTool createLaneDropTool();

    /**
     * Returns a new object of class '<em>Description Style</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Description Style</em>'.
     * @generated
     */
    DeckDescriptionStyle createDeckDescriptionStyle();

    /**
     * Returns a new object of class '<em>Conditional Deck Description Style</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Conditional Deck Description Style</em>'.
     * @generated
     */
    ConditionalDeckDescriptionStyle createConditionalDeckDescriptionStyle();

    /**
     * Returns a new object of class '<em>Element Description Style</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Element Description Style</em>'.
     * @generated
     */
    DeckElementDescriptionStyle createDeckElementDescriptionStyle();

    /**
     * Returns a new object of class '<em>Conditional Deck Element Description Style</em>'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return a new object of class '<em>Conditional Deck Element Description Style</em>'.
     * @generated
     */
    ConditionalDeckElementDescriptionStyle createConditionalDeckElementDescriptionStyle();

    /**
     * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the package supported by this factory.
     * @generated
     */
    DeckPackage getDeckPackage();

} // DeckFactory
