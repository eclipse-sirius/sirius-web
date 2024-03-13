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
package org.eclipse.sirius.components.view.builder.generated;

/**
 * Builder for DeckDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class DeckDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.deck.DeckDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.deck.DeckDescription deckDescription = org.eclipse.sirius.components.view.deck.DeckFactory.eINSTANCE.createDeckDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.deck.DeckDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.deck.DeckDescription getDeckDescription() {
        return this.deckDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.deck.DeckDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.deck.DeckDescription build() {
        return this.getDeckDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public DeckDescriptionBuilder name(java.lang.String value) {
        this.getDeckDescription().setName(value);
        return this;
    }
    /**
     * Setter for DomainType.
     *
     * @generated
     */
    public DeckDescriptionBuilder domainType(java.lang.String value) {
        this.getDeckDescription().setDomainType(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public DeckDescriptionBuilder preconditionExpression(java.lang.String value) {
        this.getDeckDescription().setPreconditionExpression(value);
        return this;
    }
    /**
     * Setter for TitleExpression.
     *
     * @generated
     */
    public DeckDescriptionBuilder titleExpression(java.lang.String value) {
        this.getDeckDescription().setTitleExpression(value);
        return this;
    }
    /**
     * Setter for LaneDescriptions.
     *
     * @generated
     */
    public DeckDescriptionBuilder laneDescriptions(org.eclipse.sirius.components.view.deck.LaneDescription ... values) {
        for (org.eclipse.sirius.components.view.deck.LaneDescription value : values) {
            this.getDeckDescription().getLaneDescriptions().add(value);
        }
        return this;
    }

    /**
     * Setter for LaneDropTool.
     *
     * @generated
     */
    public DeckDescriptionBuilder laneDropTool(org.eclipse.sirius.components.view.deck.LaneDropTool value) {
        this.getDeckDescription().setLaneDropTool(value);
        return this;
    }

    /**
     * Setter for Style.
     *
     * @generated
     */
    public DeckDescriptionBuilder style(org.eclipse.sirius.components.view.deck.DeckDescriptionStyle value) {
        this.getDeckDescription().setStyle(value);
        return this;
    }

    /**
     * Setter for ConditionalStyles.
     *
     * @generated
     */
    public DeckDescriptionBuilder conditionalStyles(org.eclipse.sirius.components.view.deck.ConditionalDeckDescriptionStyle ... values) {
        for (org.eclipse.sirius.components.view.deck.ConditionalDeckDescriptionStyle value : values) {
            this.getDeckDescription().getConditionalStyles().add(value);
        }
        return this;
    }

}

