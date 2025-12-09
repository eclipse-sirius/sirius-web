/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.components.collaborative.trees.handlers;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.trees.api.ISingleClickTreeItemContextMenuEntryExecutor;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeEventHandler;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeInput;
import org.eclipse.sirius.components.collaborative.trees.dto.InvokeSingleClickTreeItemContextMenuEntryInput;
import org.eclipse.sirius.components.collaborative.trees.services.api.ICollaborativeTreeMessageService;
import org.eclipse.sirius.components.collaborative.trees.services.api.ITreeQueryService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Used to invoke a single click entry of tree items context menu.
 *
 * @author Jerome Gout
 */
@Service
public class InvokeSingleClickTreeItemContextMenuEntryEventHandler implements ITreeEventHandler {

    private final ICollaborativeTreeMessageService messageService;

    private final ITreeQueryService treeQueryService;

    private final Counter counter;

    private final List<ISingleClickTreeItemContextMenuEntryExecutor> singleClickTreeItemContextMenuEntryExecutors;

    public InvokeSingleClickTreeItemContextMenuEntryEventHandler(ICollaborativeTreeMessageService messageService, ITreeQueryService treeQueryService,
            MeterRegistry meterRegistry, List<ISingleClickTreeItemContextMenuEntryExecutor> singleClickTreeItemContextMenuEntryExecutors) {
        this.messageService = Objects.requireNonNull(messageService);
        this.treeQueryService = Objects.requireNonNull(treeQueryService);
        this.singleClickTreeItemContextMenuEntryExecutors = Objects.requireNonNull(singleClickTreeItemContextMenuEntryExecutors);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, ITreeInput treeInput) {
        return treeInput instanceof InvokeSingleClickTreeItemContextMenuEntryInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, TreeDescription treeDescription, Tree tree, ITreeInput treeInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(treeInput.getClass().getSimpleName(), InvokeSingleClickTreeItemContextMenuEntryInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(treeInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, treeInput.representationId(), treeInput);

        if (treeInput instanceof InvokeSingleClickTreeItemContextMenuEntryInput input) {
            var optionalTreeItem = this.treeQueryService.findTreeItem(tree, input.treeItemId());

            if (optionalTreeItem.isPresent()) {
                TreeItem treeItem = optionalTreeItem.get();

                var status = this.singleClickTreeItemContextMenuEntryExecutors.stream()
                        .filter(executor -> executor.canExecute(treeDescription, treeInput))
                        .findFirst()
                        .map(executor -> executor.execute(editingContext, treeDescription, tree, treeItem, input.menuEntryId(), input))
                        .orElseGet(() -> new Failure(this.messageService.noSingleClickTreeItemExecutor()));

                if (status instanceof Success success) {
                    changeDescription = new ChangeDescription(success.getChangeKind(), treeInput.representationId(), treeInput, success.getParameters());
                    payload = new SuccessPayload(treeInput.id());
                } else if (status instanceof Failure failure) {
                    payload = new ErrorPayload(treeInput.id(), failure.getMessages());
                }
            }
        }

        changeDescriptionSink.tryEmitNext(changeDescription);
        payloadSink.tryEmitValue(payload);
    }

}
