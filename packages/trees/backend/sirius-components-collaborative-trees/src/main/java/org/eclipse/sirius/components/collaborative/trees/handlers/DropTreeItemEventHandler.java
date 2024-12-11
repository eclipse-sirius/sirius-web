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
import org.eclipse.sirius.components.collaborative.trees.api.IDropTreeItemHandler;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeEventHandler;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeInput;
import org.eclipse.sirius.components.collaborative.trees.dto.DropTreeItemInput;
import org.eclipse.sirius.components.collaborative.trees.services.api.ICollaborativeTreeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Used to drop tree items.
 *
 * @author frouene
 */
@Service
public class DropTreeItemEventHandler implements ITreeEventHandler {

    private final ICollaborativeTreeMessageService messageService;

    private final List<IDropTreeItemHandler> dropTreeItemHandlers;

    private final Counter counter;

    public DropTreeItemEventHandler(ICollaborativeTreeMessageService messageService, List<IDropTreeItemHandler> dropTreeItemHandlers, MeterRegistry meterRegistry) {
        this.messageService = Objects.requireNonNull(messageService);
        this.dropTreeItemHandlers = Objects.requireNonNull(dropTreeItemHandlers);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(ITreeInput treeInput) {
        return treeInput instanceof DropTreeItemInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, TreeDescription treeDescription, Tree tree, ITreeInput treeInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(treeInput.getClass().getSimpleName(), DropTreeItemInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(treeInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, treeInput.representationId(), treeInput);

        if (treeInput instanceof DropTreeItemInput input) {
            var optionalDropHandler = this.dropTreeItemHandlers.stream().filter(provider -> provider.canHandle(editingContext, tree)).findFirst();

            var status = optionalDropHandler
                    .map(provider -> provider.handle(editingContext, tree, input))
                    .orElseGet(() -> new Failure(this.messageService.noDropHandler()));
            if (status instanceof Success success) {
                changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, treeInput.representationId(), treeInput, success.getParameters());
                payload = new SuccessPayload(treeInput.id(), success.getMessages());
            } else if (status instanceof Failure failure) {
                payload = new ErrorPayload(treeInput.id(), failure.getMessages());
            }
        }

        changeDescriptionSink.tryEmitNext(changeDescription);
        payloadSink.tryEmitValue(payload);
    }

}
