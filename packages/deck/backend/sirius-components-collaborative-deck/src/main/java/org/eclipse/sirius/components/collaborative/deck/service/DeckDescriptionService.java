/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.deck.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import org.eclipse.sirius.components.deck.description.DeckDescription;
import org.eclipse.sirius.components.deck.description.LaneDescription;
import org.springframework.stereotype.Service;

/**
 * service used to search in DeckDescription.
 *
 * @author fbarbin
 */
@Service
public class DeckDescriptionService {

    public Optional<LaneDescription> findLaneDescriptionById(DeckDescription deckDescription, String laneDescriptionId) {
        return this.findLaneDescription(laneDesc -> Objects.equals(laneDesc.id(), laneDescriptionId), deckDescription.laneDescriptions());
    }

    private Optional<LaneDescription> findLaneDescription(Predicate<LaneDescription> condition, List<LaneDescription> candidates) {
        return candidates.stream().filter(condition::test).findFirst();
    }
}
