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

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeEventHandler;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeInput;
import org.eclipse.sirius.components.collaborative.trees.dto.TreeItemContextMenuInput;
import org.eclipse.sirius.components.collaborative.trees.dto.TreeItemContextMenuSuccessPayload;
import org.eclipse.sirius.components.collaborative.trees.services.api.ICollaborativeTreeMessageService;
import org.eclipse.sirius.components.collaborative.trees.services.api.ITreeQueryService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.FetchTreeItemContextMenuEntry;
import org.eclipse.sirius.components.trees.ITreeItemContextMenuEntry;
import org.eclipse.sirius.components.trees.SingleClickTreeItemContextMenuEntry;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Used to retrieve all menu actions of a given tree item.
 *
 * @author Jerome Gout
 */
@Service
public class TreeItemContextMenuEventHandler implements ITreeEventHandler {

    private final ICollaborativeTreeMessageService messageService;

    private final ITreeQueryService treeQueryService;

    private final Counter counter;

    public TreeItemContextMenuEventHandler(ICollaborativeTreeMessageService messageService, ITreeQueryService treeQueryService,
            MeterRegistry meterRegistry) {
        this.messageService = Objects.requireNonNull(messageService);
        this.treeQueryService = Objects.requireNonNull(treeQueryService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(ITreeInput treeInput) {
        return treeInput instanceof TreeItemContextMenuInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, TreeDescription treeDescription, Tree tree, ITreeInput treeInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(treeInput.getClass().getSimpleName(), TreeItemContextMenuInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(treeInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, treeInput.representationId(), treeInput);

        if (treeInput instanceof TreeItemContextMenuInput input) {

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

                var candidates = treeDescription.getContextMenuEntries().stream()
                        .filter(action -> action.getPrecondition().apply(variableManager))
                        .map(treeItemContextMenuEntry -> this.convertTreeItemContextAction(treeItemContextMenuEntry, variableManager))
                        .filter(Objects::nonNull)
                        .sorted(Comparator.comparing(org.eclipse.sirius.components.collaborative.trees.dto.ITreeItemContextMenuEntry::label))
                        .toList();

                payload = new TreeItemContextMenuSuccessPayload(treeInput.id(), candidates);
            }
        }

        changeDescriptionSink.tryEmitNext(changeDescription);
        payloadSink.tryEmitValue(payload);
    }

    private org.eclipse.sirius.components.collaborative.trees.dto.ITreeItemContextMenuEntry convertTreeItemContextAction(ITreeItemContextMenuEntry treeItemContextMenuEntry, VariableManager variableManager) {
        org.eclipse.sirius.components.collaborative.trees.dto.ITreeItemContextMenuEntry result = null;
        String label = treeItemContextMenuEntry.getLabel().apply(variableManager);
        List<String> iconURL = treeItemContextMenuEntry.getIconURL().apply(variableManager);
        if (treeItemContextMenuEntry instanceof SingleClickTreeItemContextMenuEntry simpleAction) {
            result = new org.eclipse.sirius.components.collaborative.trees.dto.SingleClickTreeItemContextMenuEntry(simpleAction.getId(), label, iconURL);
        } else if (treeItemContextMenuEntry instanceof FetchTreeItemContextMenuEntry fetchAction) {
            result = new org.eclipse.sirius.components.collaborative.trees.dto.FetchTreeItemContextMenuEntry(fetchAction.getId(), label, iconURL);
        }
        return result;
    }

}
