/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeEventHandler;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeInput;
import org.eclipse.sirius.components.collaborative.trees.dto.FetchTreeItemContextMenuEntryData;
import org.eclipse.sirius.components.collaborative.trees.dto.FetchTreeItemContextMenuEntryDataInput;
import org.eclipse.sirius.components.collaborative.trees.dto.FetchTreeItemContextMenuEntryDataSuccessPayload;
import org.eclipse.sirius.components.collaborative.trees.services.api.ICollaborativeTreeMessageService;
import org.eclipse.sirius.components.collaborative.trees.services.api.ITreeQueryService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.FetchTreeItemContextMenuEntry;
import org.eclipse.sirius.components.trees.FetchTreeItemContextMenuEntryKind;
import org.eclipse.sirius.components.trees.ITreeItemContextMenuEntry;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Used to retrieve data associated to a tree item fetch action.
 *
 * @author Jerome Gout
 */
@Service
public class FetchTreeItemContextMenuEntryDataEventHandler implements ITreeEventHandler {

    private final ICollaborativeTreeMessageService messageService;

    private final ITreeQueryService treeQueryService;

    private final Counter counter;

    public FetchTreeItemContextMenuEntryDataEventHandler(ICollaborativeTreeMessageService messageService, ITreeQueryService treeQueryService,
            MeterRegistry meterRegistry) {
        this.messageService = Objects.requireNonNull(messageService);
        this.treeQueryService = Objects.requireNonNull(treeQueryService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(ITreeInput treeInput) {
        return treeInput instanceof FetchTreeItemContextMenuEntryDataInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, TreeDescription treeDescription, Tree tree, ITreeInput treeInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(treeInput.getClass().getSimpleName(), FetchTreeItemContextMenuEntryDataInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(treeInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, treeInput.representationId(), treeInput);

        if (treeInput instanceof FetchTreeItemContextMenuEntryDataInput input) {

            var optionalTreeItem = this.treeQueryService.findTreeItem(tree, input.treeItemId());

            if (optionalTreeItem.isPresent()) {
                TreeItem treeItem = optionalTreeItem.get();

                var variableManager = new VariableManager();
                variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
                variableManager.put(TreeDescription.TREE, tree);
                variableManager.put(TreeItem.SELECTED_TREE_ITEM, treeItem);
                variableManager.put(TreeDescription.ID, input.treeItemId());
                var semanticTreeItemObject = treeDescription.getTreeItemObjectProvider().apply(variableManager);
                variableManager.put(VariableManager.SELF, semanticTreeItemObject);

                Optional<ITreeItemContextMenuEntry> action = treeDescription.getContextMenuEntries().stream()
                        .filter(a -> Objects.equals(a.getId(), input.menuEntryId()))
                        .findFirst();
                Optional<FetchTreeItemContextMenuEntryData> metadata = action
                        .filter(FetchTreeItemContextMenuEntry.class::isInstance)
                        .map(FetchTreeItemContextMenuEntry.class::cast)
                        .map(fetchTreeItemContextMenuEntry -> this.getFetchMetadata(fetchTreeItemContextMenuEntry, variableManager));
                if (metadata.isPresent()) {
                    payload = new FetchTreeItemContextMenuEntryDataSuccessPayload(treeInput.id(), metadata.get());
                }
            }
        }

        changeDescriptionSink.tryEmitNext(changeDescription);
        payloadSink.tryEmitValue(payload);
    }

    private FetchTreeItemContextMenuEntryData getFetchMetadata(FetchTreeItemContextMenuEntry fetchAction, VariableManager variableManager) {
        String urlToFetch = fetchAction.getUrlToFetch().apply(variableManager);
        FetchTreeItemContextMenuEntryKind fetchKind = fetchAction.getFetchKind().apply(variableManager);
        return new FetchTreeItemContextMenuEntryData(urlToFetch, fetchKind);
    }

}
