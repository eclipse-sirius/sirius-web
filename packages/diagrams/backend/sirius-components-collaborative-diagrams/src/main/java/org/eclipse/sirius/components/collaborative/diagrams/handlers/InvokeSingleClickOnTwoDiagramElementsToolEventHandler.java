/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnTwoDiagramElementsToolInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.collaborative.diagrams.services.ISingleClickOnTwoDiagramElementHandler;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.WorkbenchSelection;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handle "Invoke single click on two diagram elements tool" events.
 *
 * @author pcdavid
 * @author hmarchadour
 */
@Service
public class InvokeSingleClickOnTwoDiagramElementsToolEventHandler implements IDiagramEventHandler {

    private final IDiagramQueryService diagramQueryService;

    private final ICollaborativeDiagramMessageService messageService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final List<ISingleClickOnTwoDiagramElementHandler> singleClickOnTwoDiagramElementHandlers;

    private final Counter counter;

    public InvokeSingleClickOnTwoDiagramElementsToolEventHandler(IDiagramQueryService diagramQueryService, ICollaborativeDiagramMessageService messageService, IRepresentationDescriptionSearchService representationDescriptionSearchService,
                                                                 List<ISingleClickOnTwoDiagramElementHandler> singleClickOnTwoDiagramElementHandlers, MeterRegistry meterRegistry) {
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.messageService = Objects.requireNonNull(messageService);
        this.singleClickOnTwoDiagramElementHandlers = Objects.requireNonNull(singleClickOnTwoDiagramElementHandlers);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof InvokeSingleClickOnTwoDiagramElementsToolInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, DiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), InvokeSingleClickOnTwoDiagramElementsToolInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.representationId(), diagramInput);

        if (diagramInput instanceof InvokeSingleClickOnTwoDiagramElementsToolInput input) {
            Diagram diagram = diagramContext.diagram();

            var sourceDiagramElement = this.diagramQueryService.findDiagramElementById(diagram, input.diagramSourceElementId());
            var targetDiagramElement = this.diagramQueryService.findDiagramElementById(diagram, input.diagramTargetElementId());

            var diagramDescription = this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId())
                    .filter(DiagramDescription.class::isInstance)
                    .map(DiagramDescription.class::cast);

            if (diagramDescription.isPresent() && sourceDiagramElement.isPresent() && targetDiagramElement.isPresent()) {
                IStatus status = this.singleClickOnTwoDiagramElementHandlers.stream()
                        .filter(handler -> handler.canHandle(editingContext, diagram, input.toolId(), input.diagramSourceElementId(), input.diagramTargetElementId()))
                        .findFirst()
                        .map(handler -> handler.execute(editingContext, diagramContext.diagram(), input.toolId(), input.diagramSourceElementId(), input.diagramTargetElementId(), input.variables()))
                        .orElse(new Failure(this.messageService.handlerNotFound()));

                if (status instanceof Success success && success.getParameters().get(Success.NEW_SELECTION) instanceof WorkbenchSelection newSelection) {
                    payload = new InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload(diagramInput.id(), newSelection, success.getMessages());
                    changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, diagramInput.representationId(), diagramInput);
                } else if (status instanceof Failure failure) {
                    payload = new ErrorPayload(diagramInput.id(), failure.getMessages());
                }
            }

        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

}
