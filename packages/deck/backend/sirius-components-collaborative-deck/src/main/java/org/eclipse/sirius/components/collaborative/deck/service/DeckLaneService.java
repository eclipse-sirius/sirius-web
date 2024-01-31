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

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import org.eclipse.sirius.components.collaborative.deck.api.IDeckContext;
import org.eclipse.sirius.components.collaborative.deck.api.IDeckLaneService;
import org.eclipse.sirius.components.collaborative.deck.dto.input.ChangeLaneCollapsedStateInput;
import org.eclipse.sirius.components.collaborative.deck.dto.input.DropDeckLaneInput;
import org.eclipse.sirius.components.collaborative.deck.dto.input.EditDeckLaneInput;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.deck.Deck;
import org.eclipse.sirius.components.deck.Lane;
import org.eclipse.sirius.components.deck.description.DeckDescription;
import org.eclipse.sirius.components.deck.description.LaneDescription;
import org.eclipse.sirius.components.deck.renderer.events.ChangeLaneCollapseStateDeckEvent;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

/**
 * Service used to manage lanes.
 *
 * @author fbarbin
 */
@Service
public class DeckLaneService implements IDeckLaneService {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IFeedbackMessageService feedbackMessageService;

    private final IObjectService objectService;

    public DeckLaneService(IRepresentationDescriptionSearchService representationDescriptionSearchService, IFeedbackMessageService feedbackMessageService, IObjectService objectService) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.objectService = Objects.requireNonNull(objectService);
    }



    @Override
    public IPayload editLane(EditDeckLaneInput editDeckLaneInput, IEditingContext editingContext, Deck deck) {
        IPayload payload = new ErrorPayload(editDeckLaneInput.id(), "Edit lane failed");

        Optional<Lane> optionalLane = this.findLane(lane -> Objects.equals(lane.id(), editDeckLaneInput.laneId()), deck);
        Optional<LaneDescription> optionalLaneDescription = optionalLane.flatMap(lane -> this.findLaneDescription(lane.descriptionId(), deck, editingContext));

        if (optionalLane.isPresent() && optionalLaneDescription.isPresent()) {
            Optional<Object> optionalTargetObject = this.objectService.getObject(editingContext, optionalLane.get().targetObjectId());
            if (optionalTargetObject.isPresent()) {
                VariableManager variableManager = new VariableManager();
                variableManager.put(VariableManager.SELF, optionalTargetObject.get());
                variableManager.put(DeckDescription.NEW_TITLE, editDeckLaneInput.newTitle());
                optionalLaneDescription.get().editLaneProvider().accept(variableManager);

                payload = this.getPayload(editDeckLaneInput.id());
            }
        }

        return payload;
    }

    @Override
    public IPayload dropLane(DropDeckLaneInput dropDeckLaneInput, IEditingContext editingContext, Deck deck) {
        IPayload payload = new ErrorPayload(dropDeckLaneInput.id(), "Move lane failed");

        Optional<Lane> optionalLane = this.findLane(lane -> Objects.equals(lane.id(), dropDeckLaneInput.laneId()), deck);
        Optional<DeckDescription> optionalDeckDescription =  this.findDeckDescription(deck.descriptionId(), editingContext);

        if (optionalLane.isPresent() && optionalDeckDescription.isPresent()) {
            Optional<Object> optionalTargetObject = this.objectService.getObject(editingContext, optionalLane.get().targetObjectId());
            if (optionalTargetObject.isPresent()) {
                VariableManager variableManager = new VariableManager();
                variableManager.put(VariableManager.SELF, optionalTargetObject.get());
                variableManager.put(LaneDescription.INDEX, dropDeckLaneInput.newIndex());
                optionalDeckDescription.get().dropLaneProvider().accept(variableManager);

                payload = this.getPayload(dropDeckLaneInput.id());
            }
        }

        return payload;
    }

    private IPayload getPayload(UUID payloadId) {
        IPayload payload = null;
        List<Message> feedbackMessages = this.feedbackMessageService.getFeedbackMessages();
        Optional<Message> optionalErrorMsg = feedbackMessages.stream().filter(msg -> MessageLevel.ERROR.equals(msg.level())).findFirst();
        if (optionalErrorMsg.isPresent()) {
            payload = new ErrorPayload(payloadId, optionalErrorMsg.get().body(), feedbackMessages);
        } else {
            payload = new SuccessPayload(payloadId, feedbackMessages);
        }
        return payload;
    }
    private Optional<Lane> findLane(Predicate<Lane> condition, Deck deck) {
        return deck.lanes().stream()
                .filter(condition)
                .findFirst();
    }

    private Optional<DeckDescription> findDeckDescription(String descriptionId, IEditingContext editingContext) {
        return this.representationDescriptionSearchService.findById(editingContext, descriptionId)
                .filter(DeckDescription.class::isInstance)
                .map(DeckDescription.class::cast);
    }

    private Optional<LaneDescription> findLaneDescription(String descriptionId, Deck deck, IEditingContext editingContext) {
        return this.findDeckDescription(deck.descriptionId(), editingContext)
                .stream()
                .map(DeckDescription::laneDescriptions)
                .flatMap(Collection::stream)
                .filter(laneDesc -> laneDesc.id().equals(descriptionId))
                .findFirst();
    }

    @Override
    public IPayload changeLaneCollapsedState(ChangeLaneCollapsedStateInput changeLaneCollapsedStateInput, IEditingContext editingContext, IDeckContext deckContext) {
        deckContext.setDeckEvent(new ChangeLaneCollapseStateDeckEvent(changeLaneCollapsedStateInput.laneId(), changeLaneCollapsedStateInput.collapsed()));
        return this.getPayload(changeLaneCollapsedStateInput.id());
    }

}
