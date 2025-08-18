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

import java.util.Collection;
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
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.collaborative.diagrams.services.IToolDiagramExecutor;
import org.eclipse.sirius.components.collaborative.diagrams.services.ToolDiagramExecutor;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.WorkbenchSelection;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handle "Invoke single click on diagram element tool" events.
 *
 * @author pcdavid
 */
@Service
public class InvokeSingleClickOnDiagramElementToolEventHandler implements IDiagramEventHandler {

    private final ICollaborativeDiagramMessageService messageService;

    private final IToolDiagramExecutor toolDiagramExecutor;

    private final Counter counter;

    public InvokeSingleClickOnDiagramElementToolEventHandler(ICollaborativeDiagramMessageService messageService, IToolDiagramExecutor toolDiagramExecutor, MeterRegistry meterRegistry) {
        this.messageService = Objects.requireNonNull(messageService);
        this.toolDiagramExecutor = Objects.requireNonNull(toolDiagramExecutor);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof InvokeSingleClickOnDiagramElementToolInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, DiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), InvokeSingleClickOnDiagramElementToolInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.representationId(), diagramInput);

        if (diagramInput instanceof InvokeSingleClickOnDiagramElementToolInput input) {
            IStatus status = this.toolDiagramExecutor.execute(editingContext, diagramContext.diagram(), input.toolId(), input.diagramElementId(), input.variables());
            if (status instanceof Success success) {
                WorkbenchSelection newSelection = null;
                Object newSelectionParameter = success.getParameters().get(Success.NEW_SELECTION);
                if (newSelectionParameter instanceof WorkbenchSelection workbenchSelection) {
                    newSelection = workbenchSelection;
                }
                Object viewCreationRequestsParameter = success.getParameters().get(ToolDiagramExecutor.VIEW_CREATION_REQUESTS);
                if (viewCreationRequestsParameter instanceof List<?> viewCreationRequests && viewCreationRequests.stream().allMatch(ViewCreationRequest.class::isInstance)) {
                    diagramContext.viewCreationRequests().addAll((Collection<? extends ViewCreationRequest>) viewCreationRequests);
                }
                Object viewDeletionRequestsParameter = success.getParameters().get(ToolDiagramExecutor.VIEW_DELETION_REQUESTS);
                if (viewDeletionRequestsParameter instanceof List<?> viewDeletionRequests && viewDeletionRequests.stream().allMatch(ViewDeletionRequest.class::isInstance)) {
                    diagramContext.viewDeletionRequests().addAll((Collection<? extends ViewDeletionRequest>) viewDeletionRequests);
                }
                Object diagramEventsParameter = success.getParameters().get(ToolDiagramExecutor.DIAGRAM_EVENTS);
                if (diagramEventsParameter instanceof List<?> diagramEvents && diagramEvents.stream().allMatch(IDiagramEvent.class::isInstance)) {
                    diagramContext.diagramEvents().addAll((Collection<? extends IDiagramEvent>) diagramEvents);
                }
                payload = new InvokeSingleClickOnDiagramElementToolSuccessPayload(diagramInput.id(), newSelection, success.getMessages());
                changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, diagramInput.representationId(), diagramInput);
            } else if (status instanceof Failure failure) {
                payload = new ErrorPayload(diagramInput.id(), failure.getMessages());
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

}
