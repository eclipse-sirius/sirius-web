/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.handlers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IInitialDirectEditElementLabelProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InitialDirectEditElementLabelInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InitialDirectEditElementLabelSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handle "initial direct edit element label" events.
 *
 * @author gcoutable
 */
@Service
public class InitialDirectEditElementLabelEventHandler implements IDiagramEventHandler {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IDiagramQueryService diagramQueryService;

    private final List<IInitialDirectEditElementLabelProvider> initialDirectEditElementLabelProviders;

    private final ICollaborativeDiagramMessageService messageService;

    private final Counter counter;

    public InitialDirectEditElementLabelEventHandler(IRepresentationDescriptionSearchService representationDescriptionSearchService, IDiagramQueryService diagramQueryService,
            List<IInitialDirectEditElementLabelProvider> initialDirectEditElementLabelProviders, ICollaborativeDiagramMessageService messageService, MeterRegistry meterRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.initialDirectEditElementLabelProviders = Objects.requireNonNull(initialDirectEditElementLabelProviders);
        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof InitialDirectEditElementLabelInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), InitialDirectEditElementLabelInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), diagramInput);

        if (diagramInput instanceof InitialDirectEditElementLabelInput) {
            InitialDirectEditElementLabelInput input = (InitialDirectEditElementLabelInput) diagramInput;
            Diagram diagram = diagramContext.getDiagram();

            //@formatter:off
            var diagramDescription = this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId())
                    .filter(DiagramDescription.class::isInstance)
                    .map(DiagramDescription.class::cast);
            //@formatter:on

            if (diagramDescription.isPresent()) {
                // @formatter:off
                var optionalInitialDirectEditLabelProviderLabel = this.initialDirectEditElementLabelProviders.stream()
                        .filter(provider -> provider.canHandle(diagramDescription.get()))
                        .findFirst();
                // @formatter:on

                if (optionalInitialDirectEditLabelProviderLabel.isPresent()) {
                    var initialDirectEditElementLabelProvider = optionalInitialDirectEditLabelProviderLabel.get();
                    String labelId = input.labelId();
                    var element = this.findGraphicalElement(diagram, labelId);
                    if (element.isPresent()) {
                        String initialDirectEditElementLabel = initialDirectEditElementLabelProvider.getInitialDirectEditElementLabel(element.get(), labelId, diagram, editingContext);
                        payload = new InitialDirectEditElementLabelSuccessPayload(diagramInput.id(), initialDirectEditElementLabel);
                    }
                }
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private Optional<Object> findGraphicalElement(Diagram diagram, String labelId) {
        Optional<Object> element = Optional.empty();

        var optionalEdge = this.diagramQueryService.findEdgeByLabelId(diagram, labelId);
        if (optionalEdge.isPresent()) {
            element = Optional.of(optionalEdge.get());
        }

        if (element.isEmpty()) {
            var optionalNode = this.diagramQueryService.findNodeByLabelId(diagram, labelId);
            if (optionalNode.isPresent()) {
                element = Optional.of(optionalNode.get());
            }
        }

        return element;
    }

}
