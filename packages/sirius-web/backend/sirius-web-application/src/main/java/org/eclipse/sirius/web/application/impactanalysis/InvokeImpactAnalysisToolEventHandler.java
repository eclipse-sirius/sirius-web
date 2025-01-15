/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.impactanalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.IToolRepresentationExecutor;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.dto.ImpactAnalysisReport;
import org.eclipse.sirius.components.collaborative.dto.InvokeImpactAnalysisToolInput;
import org.eclipse.sirius.components.collaborative.dto.InvokeImpactAnalysisToolSuccessPayload;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceLoader;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceToDocumentService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handle "impact analysis tool" events.
 *
 * @author frouene
 */
@Service
public class InvokeImpactAnalysisToolEventHandler implements IEditingContextEventHandler {

    private final IResourceLoader resourceLoader;

    private final IResourceToDocumentService resourceToDocumentService;

    private final ICollaborativeMessageService messageService;

    private final IRepresentationSearchService representationSearchService;

    private final List<IToolRepresentationExecutor> toolExecutors;

    private final Counter counter;

    public InvokeImpactAnalysisToolEventHandler(IResourceLoader resourceLoader, IResourceToDocumentService resourceToDocumentService,
            ICollaborativeMessageService messageService, IRepresentationSearchService representationSearchService, List<IToolRepresentationExecutor> toolExecutors, MeterRegistry meterRegistry) {
        this.resourceLoader = Objects.requireNonNull(resourceLoader);
        this.resourceToDocumentService = Objects.requireNonNull(resourceToDocumentService);
        this.messageService = Objects.requireNonNull(messageService);
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.toolExecutors = Objects.requireNonNull(toolExecutors);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof InvokeImpactAnalysisToolInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), InvokeImpactAnalysisToolInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);

        if (input instanceof InvokeImpactAnalysisToolInput invokeImpactAnalysisToolInput && editingContext instanceof EditingContext siriusEditingContext) {
            ResourceSet resourceSet = siriusEditingContext.getDomain().getResourceSet();
            var snapshotBeforeToolExecution = resourceSet.getResources().stream().map(resource -> this.resourceToDocumentService.toDocument(resource, false))
                    .filter(Optional::isPresent)
                    .map(Optional::get).toList();

            ChangeRecorder changeRecorder = siriusEditingContext.getChangeRecorder();

            var representationOptional = this.representationSearchService.findById(editingContext, invokeImpactAnalysisToolInput.representationId());
            if (representationOptional.isPresent()) {
                changeRecorder.beginRecording(siriusEditingContext.getDomain().getResourceSet().getResources());

                IStatus toolExecutionResult = this.toolExecutors.stream().filter(executor -> executor.canExecute(editingContext, representationOptional.get())).findFirst()
                        .map(executor -> executor.execute(editingContext, representationOptional.get(), invokeImpactAnalysisToolInput.toolId(), invokeImpactAnalysisToolInput.targetObjectId(),
                                invokeImpactAnalysisToolInput.variables()))
                        .orElse(new Failure(""));

                var diff = siriusEditingContext.getChangeRecorder().summarize();
                siriusEditingContext.getChangeRecorder().endRecording();
                resourceSet.getResources().clear();
                for (var documentSnapshot : snapshotBeforeToolExecution) {
                    this.resourceLoader.toResource(resourceSet, documentSnapshot.document().getId().toString(), documentSnapshot.document().getName(), documentSnapshot.document().getContent(), false);
                }
                if (toolExecutionResult instanceof Success success) {
                    List<String> additionalReports = new ArrayList<>();
                    if (success.getParameters().get("viewCreationRequests") instanceof List<?> viewCreationRequestsList && !viewCreationRequestsList.isEmpty()) {
                        additionalReports.add("Number of view added: " + viewCreationRequestsList.size());
                    }
                    if (success.getParameters()
                            .get("viewDeletionRequests") instanceof List<?> viewDeletionRequestsList && !viewDeletionRequestsList.isEmpty()) {
                        additionalReports.add("Number of view deleted: " + viewDeletionRequestsList.size());
                    }
                    payload = new InvokeImpactAnalysisToolSuccessPayload(invokeImpactAnalysisToolInput.id(),
                            new ImpactAnalysisReport(diff.getObjectsToAttach().size(), diff.getObjectChanges().size(), diff.getObjectsToDetach().size(), additionalReports),
                            success.getMessages());
                    changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);
                } else if (toolExecutionResult instanceof Failure failure) {
                    payload = new ErrorPayload(input.id(), failure.getMessages());
                }
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

}
