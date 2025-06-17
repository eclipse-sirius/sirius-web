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
package org.eclipse.sirius.components.view.emf.deck;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.view.deck.CardDescription;
import org.eclipse.sirius.components.view.deck.DeckDescription;
import org.eclipse.sirius.components.view.deck.LaneDescription;
import org.springframework.stereotype.Service;

/**
 * descriptionID for DeckDescription.
 *
 * @author lfasani
 */
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class DeckIdProvider implements IDeckIdProvider {

    private final IIdentityService identityService;

    public DeckIdProvider(IIdentityService identityService) {
        this.identityService = Objects.requireNonNull(identityService);
    }

    @Override
    public String getId(DeckDescription deckDescription) {
        String sourceId = this.getSourceIdFromElementDescription(deckDescription);
        String sourceElementId = this.identityService.getId(deckDescription);
        return DECK_DESCRIPTION_KIND + "&" + SOURCE_KIND + "=" + VIEW_SOURCE_KIND + "&" + SOURCE_ID + "=" + sourceId + "&" + SOURCE_ELEMENT_ID + "=" + sourceElementId;
    }

    @Override
    public String getId(LaneDescription laneDescription) {
        String sourceId = this.getSourceIdFromElementDescription(laneDescription);
        String sourceElementId = this.identityService.getId(laneDescription);
        return LANE_DESCRIPTION_KIND + "?" + SOURCE_KIND + "=" + VIEW_SOURCE_KIND + "&" + SOURCE_ID + "=" + sourceId + "&" + SOURCE_ELEMENT_ID + "=" + sourceElementId;
    }

    @Override
    public String getId(CardDescription cardDescription) {
        String sourceId = this.getSourceIdFromElementDescription(cardDescription);
        String sourceElementId = this.identityService.getId(cardDescription);
        return CARD_DESCRIPTION_KIND + "?" + SOURCE_KIND + "=" + VIEW_SOURCE_KIND + "&" + SOURCE_ID + "=" + sourceId + "&" + SOURCE_ELEMENT_ID + "=" + sourceElementId;
    }


    private String getSourceIdFromElementDescription(EObject elementDescription) {
        return elementDescription.eResource().getURI().toString().split("///")[1];
    }
}
