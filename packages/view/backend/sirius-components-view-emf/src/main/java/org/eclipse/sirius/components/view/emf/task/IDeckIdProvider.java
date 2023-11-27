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
package org.eclipse.sirius.components.view.emf.task;

import org.eclipse.sirius.components.view.deck.CardDescription;
import org.eclipse.sirius.components.view.deck.DeckDescription;
import org.eclipse.sirius.components.view.deck.LaneDescription;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionIdProvider;

/**
 * Interface to provide ids for DeckDescription.
 *
 * @author fbarbin
 */
public interface IDeckIdProvider extends IRepresentationDescriptionIdProvider<DeckDescription> {

    String DECK_DESCRIPTION_KIND = PREFIX + "?kind=deckDescription";
    String LANE_DESCRIPTION_KIND = PREFIX + "?kind=laneDescription";
    String CARD_DESCRIPTION_KIND = PREFIX + "?kind=cardDescription";

    @Override
    String getId(DeckDescription deckDescription);

    String getId(LaneDescription laneDescription);

    String getId(CardDescription cardDescription);


    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author fbarbin
     */
    class NoOp implements IDeckIdProvider {

        @Override
        public String getId(DeckDescription deckDescription) {
            return "";
        }

        @Override
        public String getId(LaneDescription laneDescription) {
            return "";
        }

        @Override
        public String getId(CardDescription cardDescription) {
            return "";
        }

    }
}
