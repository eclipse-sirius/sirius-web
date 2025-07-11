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

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.dto.ImpactAnalysisReport;
import org.eclipse.sirius.components.collaborative.dto.InvokeImpactAnalysisSuccessPayload;
import org.eclipse.sirius.components.collaborative.trees.api.ISingleClickTreeItemContextMenuEntryExecutor;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeEventHandler;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeInput;
import org.eclipse.sirius.components.collaborative.trees.dto.InvokeTreeImpactAnalysisInput;
import org.eclipse.sirius.components.collaborative.trees.services.api.ITreeQueryService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextSnapshotService;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handle tree "impact analysis tool" events.
 *
 * @author gdaniel
 */
@Service
public class InvokeTreeImpactAnalysisToolEventHandler implements ITreeEventHandler {

    private final IEditingContextSnapshotService editingContextSnapshotService;

    private final ITreeQueryService treeQueryService;

    private final List<ISingleClickTreeItemContextMenuEntryExecutor> singleClickTreeItemContextMenuEntryExecutors;

    private final IMessageService messageService;

    private final Counter counter;

    public InvokeTreeImpactAnalysisToolEventHandler(IEditingContextSnapshotService editingContextSnapshotService, ITreeQueryService treeQueryService, List<ISingleClickTreeItemContextMenuEntryExecutor> singleClickTreeItemContextMenuEntryExecutors, IMessageService messageService, MeterRegistry meterRegistry) {
        this.editingContextSnapshotService = Objects.requireNonNull(editingContextSnapshotService);
        this.treeQueryService = Objects.requireNonNull(treeQueryService);
        this.singleClickTreeItemContextMenuEntryExecutors = Objects.requireNonNull(singleClickTreeItemContextMenuEntryExecutors);
        this.messageService = Objects.requireNonNull(messageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(ITreeInput treeInput) {
        return treeInput instanceof InvokeTreeImpactAnalysisInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, TreeDescription treeDescription, Tree tree, ITreeInput treeInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(treeInput.getClass().getSimpleName(), InvokeTreeImpactAnalysisInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(treeInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), treeInput);

        if (treeInput instanceof InvokeTreeImpactAnalysisInput invokeTreeImpactAnalysisInput && editingContext instanceof EditingContext siriusWebEditingContext) {
            var editingContextSnapshot = this.editingContextSnapshotService.createSnapshot(siriusWebEditingContext);
            if (editingContextSnapshot.isPresent()) {
                ChangeRecorder changeRecorder = siriusWebEditingContext.getChangeRecorder();

                changeRecorder.beginRecording(siriusWebEditingContext.getDomain().getResourceSet().getResources());

                IStatus entryExecutionResult = this.treeQueryService.findTreeItem(tree, invokeTreeImpactAnalysisInput.treeItemId())
                        .flatMap(treeItem -> this.singleClickTreeItemContextMenuEntryExecutors.stream()
                                .filter(executor -> executor.canExecute(treeDescription))
                                .findFirst()
                                .map(executor -> executor.execute(editingContext, treeDescription, tree, treeItem, invokeTreeImpactAnalysisInput.menuEntryId())))
                        .orElseGet(() -> new Failure(this.messageService.notFound()));

                var diff = changeRecorder.summarize();
                changeRecorder.endRecording();

                this.editingContextSnapshotService.restoreSnapshot(siriusWebEditingContext, editingContextSnapshot.get(), invokeTreeImpactAnalysisInput);

                if (entryExecutionResult instanceof Success success) {
                    payload = new InvokeImpactAnalysisSuccessPayload(invokeTreeImpactAnalysisInput.id(), new ImpactAnalysisReport(diff.getObjectsToAttach().size(), diff.getObjectChanges().size(), diff.getObjectsToDetach().size(), List.of()), success.getMessages());
                } else if (entryExecutionResult instanceof Failure failure) {
                    payload = new ErrorPayload(invokeTreeImpactAnalysisInput.id(), failure.getMessages());
                }
            } else {
                payload = new ErrorPayload(invokeTreeImpactAnalysisInput.id(), this.messageService.unexpectedError());
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
