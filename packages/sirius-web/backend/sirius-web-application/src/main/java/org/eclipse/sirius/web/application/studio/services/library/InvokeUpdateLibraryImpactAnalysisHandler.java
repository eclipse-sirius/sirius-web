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
package org.eclipse.sirius.web.application.studio.services.library;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.dto.ImpactAnalysisReport;
import org.eclipse.sirius.components.collaborative.dto.InvokeImpactAnalysisSuccessPayload;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.datatree.DataTree;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextSnapshotService;
import org.eclipse.sirius.web.application.impactanalysis.services.api.ITreeImpactAnalysisReportProvider;
import org.eclipse.sirius.web.application.library.dto.InvokeUpdateLibraryImpactAnalysisInput;
import org.eclipse.sirius.web.application.studio.services.library.api.IUpdateLibraryExecutor;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handle update library "impact analysis" events.
 *
 * @author gdaniel
 */
@Service
public class InvokeUpdateLibraryImpactAnalysisHandler implements IEditingContextEventHandler {

    private final IUpdateLibraryExecutor updateLibraryExecutor;

    private final IEditingContextSnapshotService editingContextSnapshotService;

    private final ITreeImpactAnalysisReportProvider treeImpactAnalysisReportProvider;

    private final IMessageService messageService;

    private final Counter counter;

    public InvokeUpdateLibraryImpactAnalysisHandler(IUpdateLibraryExecutor updateLibraryExecutor, IEditingContextSnapshotService editingContextSnapshotService, ITreeImpactAnalysisReportProvider treeImpactAnalysisReportProvider, IMessageService messageService, MeterRegistry meterRegistry) {
        this.updateLibraryExecutor = Objects.requireNonNull(updateLibraryExecutor);
        this.editingContextSnapshotService = Objects.requireNonNull(editingContextSnapshotService);
        this.treeImpactAnalysisReportProvider = Objects.requireNonNull(treeImpactAnalysisReportProvider);
        this.messageService = Objects.requireNonNull(messageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof InvokeUpdateLibraryImpactAnalysisInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), InvokeUpdateLibraryImpactAnalysisInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);

        if (input instanceof InvokeUpdateLibraryImpactAnalysisInput invokeUpdateLibraryImpactAnalysisInput && editingContext instanceof EditingContext siriusWebEditingContext) {
            var editingContextSnapshot = this.editingContextSnapshotService.createSnapshot(siriusWebEditingContext);
            if (editingContextSnapshot.isPresent()) {
                ChangeRecorder changeRecorder = siriusWebEditingContext.getChangeRecorder();
                changeRecorder.beginRecording(siriusWebEditingContext.getDomain().getResourceSet().getResources());

                IStatus updateLibraryResult = this.updateLibraryExecutor.updateLibrary(invokeUpdateLibraryImpactAnalysisInput, siriusWebEditingContext, invokeUpdateLibraryImpactAnalysisInput.libraryId());

                var diff = changeRecorder.summarize();
                changeRecorder.endRecording();

                Optional<ImpactAnalysisReport> optionalImpactAnalysisReport = this.treeImpactAnalysisReportProvider.getReport(editingContext, diff, updateLibraryResult);
                if (optionalImpactAnalysisReport.isPresent()) {
                    List<Message> messages = List.of();
                    if (updateLibraryResult instanceof Success success) {
                        messages = success.getMessages();
                    }
                    payload = new InvokeImpactAnalysisSuccessPayload(input.id(), optionalImpactAnalysisReport.get(), messages);
                } else {
                    payload = new InvokeImpactAnalysisSuccessPayload(input.id(),
                            new ImpactAnalysisReport(0, 0, 0, List.of(this.messageService.unexpectedError()), new DataTree("impact_tree", List.of())), List.of());
                }
                this.editingContextSnapshotService.restoreSnapshot(siriusWebEditingContext, editingContextSnapshot.get());
            } else {
                payload = new InvokeImpactAnalysisSuccessPayload(input.id(),
                        new ImpactAnalysisReport(0, 0, 0, List.of(this.messageService.unexpectedError()), new DataTree("impact_tree", List.of())), List.of());
            }
        }
        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
