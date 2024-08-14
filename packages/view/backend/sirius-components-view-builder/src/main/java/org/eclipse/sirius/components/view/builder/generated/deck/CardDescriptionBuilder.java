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
package org.eclipse.sirius.components.view.builder.generated.deck;

/**
 * Builder for CardDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class CardDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.deck.CardDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.deck.CardDescription cardDescription = org.eclipse.sirius.components.view.deck.DeckFactory.eINSTANCE.createCardDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.deck.CardDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.deck.CardDescription getCardDescription() {
        return this.cardDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.deck.CardDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.deck.CardDescription build() {
        return this.getCardDescription();
    }

    /**
     * Setter for SemanticCandidatesExpression.
     *
     * @generated
     */
    public CardDescriptionBuilder semanticCandidatesExpression(java.lang.String value) {
        this.getCardDescription().setSemanticCandidatesExpression(value);
        return this;
    }
    /**
     * Setter for TitleExpression.
     *
     * @generated
     */
    public CardDescriptionBuilder titleExpression(java.lang.String value) {
        this.getCardDescription().setTitleExpression(value);
        return this;
    }
    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public CardDescriptionBuilder labelExpression(java.lang.String value) {
        this.getCardDescription().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for Style.
     *
     * @generated
     */
    public CardDescriptionBuilder style(org.eclipse.sirius.components.view.deck.DeckElementDescriptionStyle value) {
        this.getCardDescription().setStyle(value);
        return this;
    }
    /**
     * Setter for ConditionalStyles.
     *
     * @generated
     */
    public CardDescriptionBuilder conditionalStyles(org.eclipse.sirius.components.view.deck.ConditionalDeckElementDescriptionStyle ... values) {
        for (org.eclipse.sirius.components.view.deck.ConditionalDeckElementDescriptionStyle value : values) {
            this.getCardDescription().getConditionalStyles().add(value);
        }
        return this;
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public CardDescriptionBuilder name(java.lang.String value) {
        this.getCardDescription().setName(value);
        return this;
    }
    /**
     * Setter for DomainType.
     *
     * @generated
     */
    public CardDescriptionBuilder domainType(java.lang.String value) {
        this.getCardDescription().setDomainType(value);
        return this;
    }
    /**
     * Setter for DescriptionExpression.
     *
     * @generated
     */
    public CardDescriptionBuilder descriptionExpression(java.lang.String value) {
        this.getCardDescription().setDescriptionExpression(value);
        return this;
    }
    /**
     * Setter for EditTool.
     *
     * @generated
     */
    public CardDescriptionBuilder editTool(org.eclipse.sirius.components.view.deck.EditCardTool value) {
        this.getCardDescription().setEditTool(value);
        return this;
    }
    /**
     * Setter for DeleteTool.
     *
     * @generated
     */
    public CardDescriptionBuilder deleteTool(org.eclipse.sirius.components.view.deck.DeleteCardTool value) {
        this.getCardDescription().setDeleteTool(value);
        return this;
    }

}

