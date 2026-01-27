/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeEventHandler;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeInput;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeItemTooltipProvider;
import org.eclipse.sirius.components.collaborative.trees.dto.TreeItemTooltipInput;
import org.eclipse.sirius.components.collaborative.trees.dto.TreeItemTooltipSuccessPayload;
import org.eclipse.sirius.components.collaborative.trees.services.api.ITreeQueryService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Used to retrieve the tooltip of a tree item.
 *
 * @author gdaniel
 */
@Service
public class TreeItemTooltipEventHandler implements ITreeEventHandler {

    private final List<ITreeItemTooltipProvider> treeItemTooltipProviders;

    private final ITreeQueryService treeQueryService;

    private final Counter counter;

    public TreeItemTooltipEventHandler(List<ITreeItemTooltipProvider> treeItemTooltipProviders, ITreeQueryService treeQueryService, MeterRegistry meterRegistry) {
        this.treeItemTooltipProviders = Objects.requireNonNull(treeItemTooltipProviders);
        this.treeQueryService = Objects.requireNonNull(treeQueryService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, ITreeInput treeInput) {
        return treeInput instanceof TreeItemTooltipInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, TreeDescription treeDescription, Tree tree, ITreeInput treeInput) {
        this.counter.increment();

        IPayload payload = new TreeItemTooltipSuccessPayload(treeInput.id(), "");
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, treeInput.representationId(), treeInput);

        if (treeInput instanceof TreeItemTooltipInput treeItemTooltipInput) {
            Optional<TreeItem> optionalTreeItem = this.treeQueryService.findTreeItem(tree, treeItemTooltipInput.treeItemId());

            if (optionalTreeItem.isPresent()) {
                TreeItem treeItem = optionalTreeItem.get();
                var tooltip = this.treeItemTooltipProviders.stream()
                        .filter(treeItemTooltipProvider -> treeItemTooltipProvider.canHandle(editingContext, treeDescription, tree, treeItem))
                        .findFirst()
                        .map(treeItemTooltipProvider -> treeItemTooltipProvider.handle(editingContext, treeDescription, tree, treeItem))
                        .orElse("");

                payload = new TreeItemTooltipSuccessPayload(treeInput.id(), tooltip);
            }

        }

        changeDescriptionSink.tryEmitNext(changeDescription);
        payloadSink.tryEmitValue(payload);
    }
}
