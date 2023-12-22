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

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import org.eclipse.sirius.components.collaborative.deck.api.IDeckCardService;
import org.eclipse.sirius.components.collaborative.deck.dto.input.CreateDeckCardInput;
import org.eclipse.sirius.components.collaborative.deck.dto.input.DeleteDeckCardInput;
import org.eclipse.sirius.components.collaborative.deck.dto.input.EditDeckCardInput;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.deck.Card;
import org.eclipse.sirius.components.deck.Deck;
import org.eclipse.sirius.components.deck.Lane;
import org.eclipse.sirius.components.deck.description.CardDescription;
import org.eclipse.sirius.components.deck.description.DeckDescription;
import org.eclipse.sirius.components.deck.description.LaneDescription;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

/**
 * Service used to manage cards.
 *
 * @author fbarbin
 */
@Service
public class DeckCardService implements IDeckCardService {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IFeedbackMessageService feedbackMessageService;

    private final IObjectService objectService;

    public DeckCardService(IRepresentationDescriptionSearchService representationDescriptionSearchService, IFeedbackMessageService feedbackMessageService, IObjectService objectService) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.objectService = Objects.requireNonNull(objectService);
    }

    @Override
    public IPayload createCard(CreateDeckCardInput createDeckCardInput, IEditingContext editingContext, Deck deck) {
        IPayload payload = new ErrorPayload(createDeckCardInput.id(), "Create card failed");

        String currentLaneId = createDeckCardInput.currentLaneId();
        Optional<Lane> optionalParentLane = this.findLane(lane -> Objects.equals(lane.id(), currentLaneId), deck);
        Optional<LaneDescription> optionalLaneDescription = optionalParentLane.flatMap(lane -> this.findLaneDescription(lane.descriptionId(), deck, editingContext));

        if (optionalLaneDescription.isPresent()) {
            VariableManager variableManager = new VariableManager();
            Optional<Object> optionalTargetObject;
            if (currentLaneId != null) {
                optionalTargetObject = optionalParentLane
                        .map(card -> this.objectService.getObject(editingContext, card.targetObjectId()))
                        .map(Optional::get);
                if (optionalTargetObject.isEmpty()) {
                    this.feedbackMessageService.addFeedbackMessage(new Message(MessageFormat.format("The current lane of id ''{0}'' is not found", currentLaneId), MessageLevel.ERROR));
                }
            } else {
                optionalTargetObject = this.objectService.getObject(editingContext, deck.targetObjectId());
            }
            if (optionalTargetObject.isPresent()) {
                variableManager.put(VariableManager.SELF, optionalTargetObject.get());
                variableManager.put(DeckDescription.TITLE, createDeckCardInput.title());
                variableManager.put(DeckDescription.DESCRIPTION, createDeckCardInput.description());
                variableManager.put(DeckDescription.LABEL, createDeckCardInput.label());
                variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
                optionalLaneDescription.get().createCardProvider().accept(variableManager);
            }

            payload = this.getPayload(createDeckCardInput.id());
        }

        return payload;
    }

    @Override
    public IPayload deleteCard(DeleteDeckCardInput deleteDeckCardInput, IEditingContext editingContext, Deck deck) {
        IPayload payload = new ErrorPayload(deleteDeckCardInput.id(), "Delete card failed");

        Optional<Card> optionalCard = this.findCard(card -> Objects.equals(card.id(), deleteDeckCardInput.cardId()), deck);
        Optional<CardDescription> optionalCardDescription = optionalCard.flatMap(card -> this.findCardDescription(card.descriptionId(), deck, editingContext));

        if (optionalCard.isPresent() && optionalCardDescription.isPresent()) {
            Optional<Object> optionalTargetObject = this.objectService.getObject(editingContext, optionalCard.get().targetObjectId());
            if (optionalTargetObject.isPresent()) {
                VariableManager variableManager = new VariableManager();
                variableManager.put(VariableManager.SELF, optionalTargetObject.get());
                optionalCardDescription.get().deleteCardProvider().accept(variableManager);

                payload = this.getPayload(deleteDeckCardInput.id());
            }
        }
        return payload;
    }

    @Override
    public IPayload editCard(EditDeckCardInput editDeckCardInput, IEditingContext editingContext, Deck deck) {
        IPayload payload = new ErrorPayload(editDeckCardInput.id(), "Edit card failed");

        Optional<Card> optionalCard = this.findCard(card -> Objects.equals(card.id(), editDeckCardInput.cardId()), deck);
        Optional<CardDescription> optionalCardDescription = optionalCard.flatMap(card -> this.findCardDescription(card.descriptionId(), deck, editingContext));

        if (optionalCard.isPresent() && optionalCardDescription.isPresent()) {
            Optional<Object> optionalTargetObject = this.objectService.getObject(editingContext, optionalCard.get().targetObjectId());
            if (optionalTargetObject.isPresent()) {
                VariableManager variableManager = new VariableManager();
                variableManager.put(VariableManager.SELF, optionalTargetObject.get());
                variableManager.put(DeckDescription.NEW_TITLE, editDeckCardInput.newTitle());
                variableManager.put(DeckDescription.NEW_DESCRIPTION, editDeckCardInput.newDescription());
                variableManager.put(DeckDescription.NEW_LABEL, editDeckCardInput.newLabel());
                optionalCardDescription.get().editCardProvider().accept(variableManager);

                payload = this.getPayload(editDeckCardInput.id());
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

    private Optional<Card> findCard(Predicate<Card> condition, Deck deck) {
        return deck.lanes().stream()
                .flatMap(lane -> lane.cards().stream())
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

    private Optional<CardDescription> findCardDescription(String descriptionId, Deck deck, IEditingContext editingContext) {
        List<LaneDescription> laneDescriptions = this.findDeckDescription(deck.descriptionId(), editingContext)
                .map(DeckDescription::laneDescriptions)
                .orElse(List.of());

        return laneDescriptions.stream()
               .map(LaneDescription::cardDescriptions)
               .flatMap(Collection::stream)
               .filter(cardDesc -> cardDesc.id().equals(descriptionId))
               .findFirst();
    }

}
