/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
 * Builder for org.eclipse.sirius.components.view.deck.DeckElementDescription.
 *
 * @author BuilderGenerator
 * @generated
 */
public abstract class DeckElementDescriptionBuilder {

    /**
     * Builder for org.eclipse.sirius.components.view.deck.DeckElementDescription.
     * @generated
     */
    protected abstract org.eclipse.sirius.components.view.deck.DeckElementDescription getDeckElementDescription();

    /**
     * Setter for SemanticCandidatesExpression.
     *
     * @generated
     */
    public DeckElementDescriptionBuilder semanticCandidatesExpression(java.lang.String value) {
        this.getDeckElementDescription().setSemanticCandidatesExpression(value);
        return this;
    }
    /**
     * Setter for TitleExpression.
     *
     * @generated
     */
    public DeckElementDescriptionBuilder titleExpression(java.lang.String value) {
        this.getDeckElementDescription().setTitleExpression(value);
        return this;
    }
    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public DeckElementDescriptionBuilder labelExpression(java.lang.String value) {
        this.getDeckElementDescription().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for Style.
     *
     * @generated
     */
    public DeckElementDescriptionBuilder style(org.eclipse.sirius.components.view.deck.DeckElementDescriptionStyle value) {
        this.getDeckElementDescription().setStyle(value);
        return this;
    }
    /**
     * Setter for ConditionalStyles.
     *
     * @generated
     */
    public DeckElementDescriptionBuilder conditionalStyles(org.eclipse.sirius.components.view.deck.ConditionalDeckElementDescriptionStyle ... values) {
        for (org.eclipse.sirius.components.view.deck.ConditionalDeckElementDescriptionStyle value : values) {
            this.getDeckElementDescription().getConditionalStyles().add(value);
        }
        return this;
    }


}

