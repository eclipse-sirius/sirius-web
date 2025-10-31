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
package org.eclipse.sirius.web.application.impactanalysis.services;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeDiagramImpactAnalysisToolInput;
import org.eclipse.sirius.components.collaborative.diagrams.services.IToolDiagramExecutor;
import org.eclipse.sirius.components.collaborative.dto.ImpactAnalysisReport;
import org.eclipse.sirius.components.collaborative.dto.InvokeImpactAnalysisSuccessPayload;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextSnapshotService;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceLoader;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceToDocumentService;
import org.eclipse.sirius.web.application.impactanalysis.services.api.IDiagramImpactAnalysisReportProvider;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handle diagram "impact analysis tool" events.
 *
 * @author frouene
 */
@Service
public class InvokeDiagramImpactAnalysisToolEventHandler implements IDiagramEventHandler {

    private final IEditingContextSnapshotService editingContextSnapshotService;

    private final IMessageService messageService;

    private final IToolDiagramExecutor toolDiagramExecutor;

    private final IDiagramImpactAnalysisReportProvider diagramImpactAnalysisReportProvider;

    private final Counter counter;

    public InvokeDiagramImpactAnalysisToolEventHandler(IResourceLoader resourceLoader, IResourceToDocumentService resourceToDocumentService, IEditingContextSnapshotService editingContextSnapshotService, IMessageService messageService, IToolDiagramExecutor toolDiagramExecutor, IDiagramImpactAnalysisReportProvider diagramImpactAnalysisReportProvider, MeterRegistry meterRegistry) {
        this.editingContextSnapshotService = Objects.requireNonNull(editingContextSnapshotService);
        this.messageService = Objects.requireNonNull(messageService);
        this.toolDiagramExecutor = Objects.requireNonNull(toolDiagramExecutor);
        this.diagramImpactAnalysisReportProvider = Objects.requireNonNull(diagramImpactAnalysisReportProvider);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof InvokeDiagramImpactAnalysisToolInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, DiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), InvokeDiagramImpactAnalysisToolInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), diagramInput);

        if (diagramInput instanceof InvokeDiagramImpactAnalysisToolInput invokeImpactAnalysisToolInput && editingContext instanceof EditingContext siriusEditingContext) {
            var editingContextSnapshot = this.editingContextSnapshotService.createSnapshot(siriusEditingContext);
            if (editingContextSnapshot.isPresent()) {
                ChangeRecorder changeRecorder = siriusEditingContext.getChangeRecorder();

                changeRecorder.beginRecording(siriusEditingContext.getDomain().getResourceSet().getResources());

                IStatus toolExecutionResult = this.toolDiagramExecutor.execute(editingContext, diagramContext.diagram(), invokeImpactAnalysisToolInput.toolId(),
                        invokeImpactAnalysisToolInput.diagramElementId(), invokeImpactAnalysisToolInput.variables());

                var diff = siriusEditingContext.getChangeRecorder().summarize();
                siriusEditingContext.getChangeRecorder().endRecording();

                if (toolExecutionResult instanceof Success success) {
                    Optional<ImpactAnalysisReport> optionalImpactAnalysisReport = this.diagramImpactAnalysisReportProvider.getReport(editingContext, diff, success);
                    if (optionalImpactAnalysisReport.isPresent()) {
                        payload = new InvokeImpactAnalysisSuccessPayload(diagramInput.id(), optionalImpactAnalysisReport.get(), success.getMessages());
                        changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), diagramInput);
                    } else {
                        payload = new ErrorPayload(diagramInput.id(), this.messageService.unexpectedError());
                    }
                } else if (toolExecutionResult instanceof Failure failure) {
                    payload = new ErrorPayload(diagramInput.id(), failure.getMessages());
                }
                this.editingContextSnapshotService.restoreSnapshot(siriusEditingContext, editingContextSnapshot.get());
            } else {
                payload = new ErrorPayload(diagramInput.id(), this.messageService.unexpectedError());
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

}
