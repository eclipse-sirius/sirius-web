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
package org.eclipse.sirius.components.view.emf.task;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.compatibility.emf.DomainClassPredicate;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.deck.description.CardDescription;
import org.eclipse.sirius.components.deck.description.LaneDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.deck.DeckDescription;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionConverter;
import org.eclipse.sirius.components.view.emf.OperationInterpreter;
import org.springframework.stereotype.Service;

/**
 * Converts a View-based deck description into an equivalent {@link DeckDescription}.
 *
 * @author fbarbin
 */
@Service
public class ViewDeckDescriptionConverter implements IRepresentationDescriptionConverter {

    private static final String DEFAULT_DECK_LABEL = "Deck";

    private static final String DEFAULT_DECK_DESCRIPTION_LABEL = "Deck Description";

    private final IObjectService objectService;

    private final IEditService editService;

    private final Function<VariableManager, String> semanticTargetIdProvider;

    private final Function<VariableManager, String> semanticTargetKindProvider;

    private final Function<VariableManager, String> semanticTargetLabelProvider;

    private final DeckIdProvider deckIdProvider;

    public ViewDeckDescriptionConverter(IObjectService objectService, IEditService editService, DeckIdProvider deckIdProvider) {
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
        this.deckIdProvider = Objects.requireNonNull(deckIdProvider);
        this.semanticTargetIdProvider = variableManager -> this.self(variableManager).map(this.objectService::getId).orElse(null);
        this.semanticTargetKindProvider = variableManager -> this.self(variableManager).map(this.objectService::getKind).orElse(null);
        this.semanticTargetLabelProvider = variableManager -> this.self(variableManager).map(this.objectService::getLabel).orElse(null);
    }

    @Override
    public boolean canConvert(RepresentationDescription representationDescription) {
        return representationDescription instanceof org.eclipse.sirius.components.view.deck.DeckDescription;
    }

    @Override
    public IRepresentationDescription convert(RepresentationDescription viewRepresentationDescription, List<RepresentationDescription> allRepresentationDescriptions, AQLInterpreter interpreter) {
        org.eclipse.sirius.components.view.deck.DeckDescription viewDeckDescription = (org.eclipse.sirius.components.view.deck.DeckDescription) viewRepresentationDescription;

        List<LaneDescription> laneDescriptions = viewDeckDescription.getLaneDescriptions().stream().map(laneDescription -> this.convert(laneDescription, interpreter)).toList();

        var id = this.deckIdProvider.getId(viewDeckDescription);
        var label = Optional.ofNullable(viewDeckDescription.getName()).orElse(DEFAULT_DECK_DESCRIPTION_LABEL);
        var idProvider = new GetOrCreateRandomIdProvider();
        Function<VariableManager, String> labelProvider = variableManager -> this.computeDeckLabel(viewDeckDescription, variableManager, interpreter);
        Predicate<VariableManager> canCreatePredicate = variableManager -> this.canCreate(viewDeckDescription.getDomainType(), viewDeckDescription.getPreconditionExpression(), variableManager,
                interpreter);
        Consumer<VariableManager> dropLaneProvider = Optional.ofNullable(viewDeckDescription.getLaneDropTool()).map(tool -> this.getOperationsHandler(tool.getBody(), interpreter)).orElse(variable -> {
        });

        return new org.eclipse.sirius.components.deck.description.DeckDescription(id, label, idProvider, labelProvider, this::getTargetObjectId, canCreatePredicate, laneDescriptions, dropLaneProvider);
    }

    private Consumer<VariableManager> getOperationsHandler(List<Operation> operations, AQLInterpreter interpreter) {
        return variableManager -> {
            OperationInterpreter operationInterpreter = new OperationInterpreter(interpreter, this.editService);
            operationInterpreter.executeOperations(operations, variableManager);
        };
    }

    private LaneDescription convert(org.eclipse.sirius.components.view.deck.LaneDescription viewLaneDescription, AQLInterpreter interpreter) {

        var id = this.deckIdProvider.getId(viewLaneDescription);
        Function<VariableManager, List<Object>> semanticElementsProvider = variableManager -> this.getSemanticElements(viewLaneDescription, variableManager, interpreter);
        Function<VariableManager, String> titleProvider = variableManager -> this.evaluateString(interpreter, variableManager, viewLaneDescription.getTitleExpression());
        Function<VariableManager, String> labelProvider = variableManager -> this.evaluateString(interpreter, variableManager, viewLaneDescription.getLabelExpression());
        Consumer<VariableManager> editLaneProvider = Optional.ofNullable(viewLaneDescription.getEditTool()).map(tool -> this.getOperationsHandler(tool.getBody(), interpreter)).orElse(variable -> {
        });
        Consumer<VariableManager> createCardProvider = Optional.ofNullable(viewLaneDescription.getCreateTool()).map(tool -> this.getOperationsHandler(tool.getBody(), interpreter)).orElse(variable -> {
        });
        Consumer<VariableManager> dropCardProvider = Optional.ofNullable(viewLaneDescription.getCardDropTool()).map(tool -> this.getOperationsHandler(tool.getBody(), interpreter)).orElse(variable -> {
        });

        List<CardDescription> cardDescriptions = viewLaneDescription.getOwnedCardDescriptions().stream().map(cardDescription -> this.convert(cardDescription, interpreter)).toList();
        return new LaneDescription(id, this.semanticTargetKindProvider, this.semanticTargetLabelProvider, this.semanticTargetIdProvider, semanticElementsProvider, titleProvider, labelProvider,
                cardDescriptions, editLaneProvider, createCardProvider, dropCardProvider);
    }

    private CardDescription convert(org.eclipse.sirius.components.view.deck.CardDescription viewCardDescription, AQLInterpreter interpreter) {

        var id = this.deckIdProvider.getId(viewCardDescription);
        Function<VariableManager, List<Object>> semanticElementsProvider = variableManager -> this.getSemanticElements(viewCardDescription, variableManager, interpreter);
        Function<VariableManager, String> titleProvider = variableManager -> this.evaluateString(interpreter, variableManager, viewCardDescription.getTitleExpression());
        Function<VariableManager, String> labelProvider = variableManager -> this.evaluateString(interpreter, variableManager, viewCardDescription.getLabelExpression());
        Function<VariableManager, String> descriptionProvider = variableManager -> this.evaluateString(interpreter, variableManager, viewCardDescription.getDescriptionExpression());

        Consumer<VariableManager> editCardProvider = Optional.ofNullable(viewCardDescription.getEditTool()).map(tool -> this.getOperationsHandler(tool.getBody(), interpreter)).orElse(variable -> {
        });
        Consumer<VariableManager> deleteCardProvider = Optional.ofNullable(viewCardDescription.getDeleteTool()).map(tool -> this.getOperationsHandler(tool.getBody(), interpreter)).orElse(variable -> {
        });

        return new CardDescription(id, this.semanticTargetKindProvider, this.semanticTargetLabelProvider, this.semanticTargetIdProvider, semanticElementsProvider, titleProvider, labelProvider,
                descriptionProvider, editCardProvider, deleteCardProvider);
    }

    private List<Object> getSemanticElements(org.eclipse.sirius.components.view.deck.LaneDescription viewLaneDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        return interpreter.evaluateExpression(variableManager.getVariables(), viewLaneDescription.getSemanticCandidatesExpression())//
                .asObjects().orElseGet(List::of).stream()//
                .filter(EObject.class::isInstance)//
                .toList();
    }

    private List<Object> getSemanticElements(org.eclipse.sirius.components.view.deck.CardDescription viewCardDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        return interpreter.evaluateExpression(variableManager.getVariables(), viewCardDescription.getSemanticCandidatesExpression())//
                .asObjects().orElseGet(List::of).stream()//
                .filter(EObject.class::isInstance)//
                .toList();
    }

    private String getTargetObjectId(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class)//
                .map(this.objectService::getId)//
                .orElse(null);
    }

    private boolean canCreate(String domainType, String preconditionExpression, VariableManager variableManager, AQLInterpreter interpreter) {
        boolean result = false;
        Optional<EClass> optionalEClass = variableManager.get(VariableManager.SELF, EObject.class).map(EObject::eClass).filter(new DomainClassPredicate(domainType));
        if (optionalEClass.isPresent()) {
            if (preconditionExpression != null && !preconditionExpression.isBlank()) {
                result = interpreter.evaluateExpression(variableManager.getVariables(), preconditionExpression).asBoolean().orElse(false);
            } else {
                result = true;
            }
        }
        return result;
    }

    private String computeDeckLabel(org.eclipse.sirius.components.view.deck.DeckDescription viewDeckDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String title = variableManager.get(org.eclipse.sirius.components.deck.description.DeckDescription.LABEL, String.class)
                .orElseGet(() -> this.evaluateString(interpreter, variableManager, viewDeckDescription.getTitleExpression()));
        if (title == null || title.isBlank()) {
            return DEFAULT_DECK_LABEL;
        } else {
            return title;
        }
    }

    private String evaluateString(AQLInterpreter interpreter, VariableManager variableManager, String expression) {
        return interpreter.evaluateExpression(variableManager.getVariables(), expression).asString().orElse("");
    }

    private Optional<Object> self(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class);
    }
}
