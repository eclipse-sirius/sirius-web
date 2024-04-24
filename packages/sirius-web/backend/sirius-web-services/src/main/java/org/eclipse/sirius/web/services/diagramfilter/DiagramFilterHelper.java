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
package org.eclipse.sirius.web.services.diagramfilter;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.web.services.diagramfilter.api.IDiagramFilterHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Utility methods for the "Diagram Filter" view.
 *
 * @author gdaniel
 */
@Service
public class DiagramFilterHelper implements IDiagramFilterHelper {

    private final Logger logger = LoggerFactory.getLogger(DiagramFilterHelper.class);

    @Override
    public Set<String> getSelectedElementIds(VariableManager variableManager) {
        return variableManager.get(DiagramFilterDescriptionProvider.SELECTED_TREE_NODES, Map.class)
                .map(checkMap -> {
                    Stream<?> entryStream = checkMap.entrySet().stream();
                    return entryStream.filter(Map.Entry.class::isInstance)
                            .map(Map.Entry.class::cast)
                            .filter(entry -> entry.getValue().equals(Boolean.TRUE))
                            .map(Map.Entry::getKey)
                            .map(Object::toString)
                            .collect(Collectors.toSet());
                })
                .orElse(Set.of());
    }

    @Override
    public IStatus sendDiagramEvent(VariableManager variableManager, IDiagramInput diagramInput) {
        var optionalRepresentationEventProcessor = variableManager.get(DiagramFilterDescriptionProvider.DIAGRAM_EVENT_PROCESSOR, IRepresentationEventProcessor.class);
        var optionalEditingContextEventProcessor = variableManager.get(DiagramFilterDescriptionProvider.EDITING_CONTEXT_EVENT_PROCESSOR, IEditingContextEventProcessor.class);

        if (optionalRepresentationEventProcessor.isPresent() && optionalEditingContextEventProcessor.isPresent()) {
            var editingContextEventProcessor = optionalEditingContextEventProcessor.get();
            var representationEventProcessor = optionalRepresentationEventProcessor.get();

            One<IPayload> payloadSink = Sinks.one();
            Many<ChangeDescription> changeDescriptions = Sinks.many().unicast().onBackpressureBuffer();
            Consumer<Throwable> errorConsumer = throwable -> this.logger.warn(throwable.getMessage(), throwable);
            changeDescriptions.asFlux().subscribe(changeDescription -> editingContextEventProcessor.getRepresentationEventProcessors()
                    .forEach(eventProcessor -> eventProcessor.refresh(changeDescription)), errorConsumer);

            representationEventProcessor.handle(payloadSink, changeDescriptions, diagramInput);
            IPayload handlerResult = payloadSink.asMono().block();

            IStatus result = null;

            EmitResult changeDescriptionEmitResult = changeDescriptions.tryEmitComplete();
            if (changeDescriptionEmitResult.isFailure()) {
                String errorMessage = MessageFormat.format("An error has occurred while marking the publisher as complete: {0}", changeDescriptionEmitResult);
                this.logger.warn(errorMessage);
                result = new Failure(errorMessage);
            } else {
                if (handlerResult instanceof SuccessPayload) {
                    result = new Success();
                } else if (handlerResult instanceof ErrorPayload errorPayload) {
                    result = new Failure(errorPayload.message());
                } else {
                    result = new Failure("Unknown error");
                }
            }
            return result;
        }

        String errorMessage = "Cannot find the diagramEventProcessor or the editingContextEventProcessor";
        this.logger.warn(errorMessage);
        return new Failure(errorMessage);
    }

}
