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
 * Builder for LaneDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class LaneDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.deck.LaneDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.deck.LaneDescription laneDescription = org.eclipse.sirius.components.view.deck.DeckFactory.eINSTANCE.createLaneDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.deck.LaneDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.deck.LaneDescription getLaneDescription() {
        return this.laneDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.deck.LaneDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.deck.LaneDescription build() {
        return this.getLaneDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public LaneDescriptionBuilder name(java.lang.String value) {
        this.getLaneDescription().setName(value);
        return this;
    }

    /**
     * Setter for SemanticCandidatesExpression.
     *
     * @generated
     */
    public LaneDescriptionBuilder semanticCandidatesExpression(java.lang.String value) {
        this.getLaneDescription().setSemanticCandidatesExpression(value);
        return this;
    }
    /**
     * Setter for TitleExpression.
     *
     * @generated
     */
    public LaneDescriptionBuilder titleExpression(java.lang.String value) {
        this.getLaneDescription().setTitleExpression(value);
        return this;
    }
    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public LaneDescriptionBuilder labelExpression(java.lang.String value) {
        this.getLaneDescription().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for Style.
     *
     * @generated
     */
    public LaneDescriptionBuilder style(org.eclipse.sirius.components.view.deck.DeckElementDescriptionStyle value) {
        this.getLaneDescription().setStyle(value);
        return this;
    }

    /**
     * Setter for ConditionalStyles.
     *
     * @generated
     */
    public LaneDescriptionBuilder conditionalStyles(org.eclipse.sirius.components.view.deck.ConditionalDeckElementDescriptionStyle ... values) {
        for (org.eclipse.sirius.components.view.deck.ConditionalDeckElementDescriptionStyle value : values) {
            this.getLaneDescription().getConditionalStyles().add(value);
        }
        return this;
    }

    /**
     * Setter for OwnedCardDescriptions.
     *
     * @generated
     */
    public LaneDescriptionBuilder ownedCardDescriptions(org.eclipse.sirius.components.view.deck.CardDescription ... values) {
        for (org.eclipse.sirius.components.view.deck.CardDescription value : values) {
            this.getLaneDescription().getOwnedCardDescriptions().add(value);
        }
        return this;
    }

    /**
     * Setter for EditTool.
     *
     * @generated
     */
    public LaneDescriptionBuilder editTool(org.eclipse.sirius.components.view.deck.EditLaneTool value) {
        this.getLaneDescription().setEditTool(value);
        return this;
    }

    /**
     * Setter for CreateTool.
     *
     * @generated
     */
    public LaneDescriptionBuilder createTool(org.eclipse.sirius.components.view.deck.CreateCardTool value) {
        this.getLaneDescription().setCreateTool(value);
        return this;
    }

    /**
     * Setter for CardDropTool.
     *
     * @generated
     */
    public LaneDescriptionBuilder cardDropTool(org.eclipse.sirius.components.view.deck.CardDropTool value) {
        this.getLaneDescription().setCardDropTool(value);
        return this;
    }

    /**
     * Setter for IsCollapsibleExpression.
     *
     * @generated
     */
    public LaneDescriptionBuilder isCollapsibleExpression(java.lang.String value) {
        this.getLaneDescription().setIsCollapsibleExpression(value);
        return this;
    }

}

