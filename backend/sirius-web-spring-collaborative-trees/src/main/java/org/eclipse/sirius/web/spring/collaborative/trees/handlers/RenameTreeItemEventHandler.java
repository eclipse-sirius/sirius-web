/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.trees.handlers;

import java.util.Objects;

import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.representations.Success;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeKind;
import org.eclipse.sirius.web.spring.collaborative.api.Monitoring;
import org.eclipse.sirius.web.spring.collaborative.trees.api.IExplorerDescriptionProvider;
import org.eclipse.sirius.web.spring.collaborative.trees.api.ITreeEventHandler;
import org.eclipse.sirius.web.spring.collaborative.trees.api.ITreeInput;
import org.eclipse.sirius.web.spring.collaborative.trees.dto.DeleteTreeItemInput;
import org.eclipse.sirius.web.spring.collaborative.trees.dto.RenameTreeItemInput;
import org.eclipse.sirius.web.spring.collaborative.trees.dto.RenameTreeItemSuccessPayload;
import org.eclipse.sirius.web.spring.collaborative.trees.services.api.ICollaborativeTreeMessageService;
import org.eclipse.sirius.web.spring.collaborative.trees.services.api.ITreeQueryService;
import org.eclipse.sirius.web.trees.Tree;
import org.eclipse.sirius.web.trees.TreeItem;
import org.eclipse.sirius.web.trees.description.TreeDescription;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Used to rename tree items.
 *
 * @author sbegaudeau
 */
@Service
public class RenameTreeItemEventHandler implements ITreeEventHandler {

    private final ICollaborativeTreeMessageService messageService;

    private final IExplorerDescriptionProvider explorerDescriptionProvider;

    private final ITreeQueryService treeQueryService;

    private final Counter counter;

    public RenameTreeItemEventHandler(ICollaborativeTreeMessageService messageService, IExplorerDescriptionProvider explorerDescriptionProvider, ITreeQueryService treeQueryService,
            MeterRegistry meterRegistry) {
        this.messageService = Objects.requireNonNull(messageService);
        this.explorerDescriptionProvider = Objects.requireNonNull(explorerDescriptionProvider);
        this.treeQueryService = Objects.requireNonNull(treeQueryService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(ITreeInput treeInput) {
        return treeInput instanceof RenameTreeItemInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, Tree tree, ITreeInput treeInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(treeInput.getClass().getSimpleName(), DeleteTreeItemInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(treeInput.getId(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, treeInput.getRepresentationId(), treeInput);

        if (treeInput instanceof RenameTreeItemInput) {
            RenameTreeItemInput input = (RenameTreeItemInput) treeInput;

            var optionalTreeItem = this.treeQueryService.findTreeItem(tree, input.getTreeItemId());

            if (optionalTreeItem.isPresent()) {
                TreeDescription treeDescription = this.explorerDescriptionProvider.getDescription();
                TreeItem treeItem = optionalTreeItem.get();

                VariableManager variableManager = new VariableManager();
                variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
                variableManager.put(TreeItem.SELECTED_TREE_ITEM, treeItem);

                var status = treeDescription.getRenameHandler().apply(variableManager, input.getNewLabel());
                if (status instanceof Success) {
                    Success success = (Success) status;
                    changeDescription = new ChangeDescription(success.getChangeKind(), treeInput.getRepresentationId(), treeInput, success.getParameters());
                    payload = new RenameTreeItemSuccessPayload(treeInput.getId());
                }
            }
        }

        changeDescriptionSink.tryEmitNext(changeDescription);
        payloadSink.tryEmitValue(payload);
    }

}
