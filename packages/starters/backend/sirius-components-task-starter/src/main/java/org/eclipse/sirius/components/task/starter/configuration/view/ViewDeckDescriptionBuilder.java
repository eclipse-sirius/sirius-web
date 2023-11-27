/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.task.starter.configuration.view;

import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.builder.generated.DeckBuilders;
import org.eclipse.sirius.components.view.deck.CardDescription;
import org.eclipse.sirius.components.view.deck.DeckDescription;
import org.eclipse.sirius.components.view.deck.LaneDescription;

/**
 * Builder of the "Deck" view description.
 *
 * @author fbarbin
 */
public class ViewDeckDescriptionBuilder {

    public static final String DECK_REP_DESC_NAME = "Deck Daily Representation";

    private DeckBuilders deckBuilders;

    public ViewDeckDescriptionBuilder() {
        this.deckBuilders = new DeckBuilders();
    }

    public void addRepresentationDescription(View view) {
        DeckDescription deckDescription = this.createDeckDescription();
        LaneDescription laneDescription = this.createLaneDescription();
        CardDescription cardDescription = this.createCardDescription();
        deckDescription.getLaneDescriptions().add(laneDescription);
        laneDescription.getOwnedCardDescriptions().add(cardDescription);
        view.getDescriptions().add(deckDescription);
    }

    private CardDescription createCardDescription() {
        return this.deckBuilders.newCardDescription()
                .semanticCandidatesExpression("aql:self.getTasksWithTag()")
                .titleExpression("aql:self.name")
                .labelExpression("'30 min'")
                .descriptionExpression("aql:self.description")
                .build();
    }

    private LaneDescription createLaneDescription() {
        return this.deckBuilders.newLaneDescription()
                .semanticCandidatesExpression("aql:self.ownedTags->select(tag | tag.prefix == 'daily')")
                .labelExpression("aql:self.getTasksWithTag()->size() + ' / ' + self.eContainer().oclAsType(task::Project).ownedTasks->select(task | task.tags->exists(tag | tag.prefix == 'daily'))->size()")
                .titleExpression("aql:self.suffix")
                .build();
    }

    private DeckDescription createDeckDescription() {
        return this.deckBuilders.newDeckDescription()
                .name(DECK_REP_DESC_NAME)
                .domainType("task::Project")
                .titleExpression("New Daily Representation")
                .build();
    }

}
