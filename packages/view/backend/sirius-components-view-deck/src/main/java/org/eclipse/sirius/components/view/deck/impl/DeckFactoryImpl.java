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
package org.eclipse.sirius.components.view.deck.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.sirius.components.view.deck.CardDescription;
import org.eclipse.sirius.components.view.deck.CardDropTool;
import org.eclipse.sirius.components.view.deck.ConditionalDeckDescriptionStyle;
import org.eclipse.sirius.components.view.deck.ConditionalDeckElementDescriptionStyle;
import org.eclipse.sirius.components.view.deck.CreateCardTool;
import org.eclipse.sirius.components.view.deck.DeckDescription;
import org.eclipse.sirius.components.view.deck.DeckDescriptionStyle;
import org.eclipse.sirius.components.view.deck.DeckElementDescriptionStyle;
import org.eclipse.sirius.components.view.deck.DeckFactory;
import org.eclipse.sirius.components.view.deck.DeckPackage;
import org.eclipse.sirius.components.view.deck.DeleteCardTool;
import org.eclipse.sirius.components.view.deck.EditCardTool;
import org.eclipse.sirius.components.view.deck.EditLaneTool;
import org.eclipse.sirius.components.view.deck.LaneDescription;
import org.eclipse.sirius.components.view.deck.LaneDropTool;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class DeckFactoryImpl extends EFactoryImpl implements DeckFactory {
    /**
     * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static DeckFactory init() {
        try {
            DeckFactory theDeckFactory = (DeckFactory) EPackage.Registry.INSTANCE.getEFactory(DeckPackage.eNS_URI);
            if (theDeckFactory != null) {
                return theDeckFactory;
            }
        } catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new DeckFactoryImpl();
    }

    /**
     * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public DeckFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case DeckPackage.DECK_DESCRIPTION:
                return this.createDeckDescription();
            case DeckPackage.LANE_DESCRIPTION:
                return this.createLaneDescription();
            case DeckPackage.CARD_DESCRIPTION:
                return this.createCardDescription();
            case DeckPackage.CREATE_CARD_TOOL:
                return this.createCreateCardTool();
            case DeckPackage.EDIT_CARD_TOOL:
                return this.createEditCardTool();
            case DeckPackage.DELETE_CARD_TOOL:
                return this.createDeleteCardTool();
            case DeckPackage.EDIT_LANE_TOOL:
                return this.createEditLaneTool();
            case DeckPackage.CARD_DROP_TOOL:
                return this.createCardDropTool();
            case DeckPackage.LANE_DROP_TOOL:
                return this.createLaneDropTool();
            case DeckPackage.DECK_DESCRIPTION_STYLE:
                return this.createDeckDescriptionStyle();
            case DeckPackage.CONDITIONAL_DECK_DESCRIPTION_STYLE:
                return this.createConditionalDeckDescriptionStyle();
            case DeckPackage.DECK_ELEMENT_DESCRIPTION_STYLE:
                return this.createDeckElementDescriptionStyle();
            case DeckPackage.CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE:
                return this.createConditionalDeckElementDescriptionStyle();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DeckDescription createDeckDescription() {
        DeckDescriptionImpl deckDescription = new DeckDescriptionImpl();
        return deckDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LaneDescription createLaneDescription() {
        LaneDescriptionImpl laneDescription = new LaneDescriptionImpl();
        return laneDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CardDescription createCardDescription() {
        CardDescriptionImpl cardDescription = new CardDescriptionImpl();
        return cardDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CreateCardTool createCreateCardTool() {
        CreateCardToolImpl createCardTool = new CreateCardToolImpl();
        return createCardTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EditCardTool createEditCardTool() {
        EditCardToolImpl editCardTool = new EditCardToolImpl();
        return editCardTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DeleteCardTool createDeleteCardTool() {
        DeleteCardToolImpl deleteCardTool = new DeleteCardToolImpl();
        return deleteCardTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EditLaneTool createEditLaneTool() {
        EditLaneToolImpl editLaneTool = new EditLaneToolImpl();
        return editLaneTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CardDropTool createCardDropTool() {
        CardDropToolImpl cardDropTool = new CardDropToolImpl();
        return cardDropTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LaneDropTool createLaneDropTool() {
        LaneDropToolImpl laneDropTool = new LaneDropToolImpl();
        return laneDropTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DeckDescriptionStyle createDeckDescriptionStyle() {
        DeckDescriptionStyleImpl deckDescriptionStyle = new DeckDescriptionStyleImpl();
        return deckDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ConditionalDeckDescriptionStyle createConditionalDeckDescriptionStyle() {
        ConditionalDeckDescriptionStyleImpl conditionalDeckDescriptionStyle = new ConditionalDeckDescriptionStyleImpl();
        return conditionalDeckDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DeckElementDescriptionStyle createDeckElementDescriptionStyle() {
        DeckElementDescriptionStyleImpl deckElementDescriptionStyle = new DeckElementDescriptionStyleImpl();
        return deckElementDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ConditionalDeckElementDescriptionStyle createConditionalDeckElementDescriptionStyle() {
        ConditionalDeckElementDescriptionStyleImpl conditionalDeckElementDescriptionStyle = new ConditionalDeckElementDescriptionStyleImpl();
        return conditionalDeckElementDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DeckPackage getDeckPackage() {
        return (DeckPackage) this.getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @deprecated
     * @generated
     */
    @Deprecated
    public static DeckPackage getPackage() {
        return DeckPackage.eINSTANCE;
    }

} // DeckFactoryImpl
