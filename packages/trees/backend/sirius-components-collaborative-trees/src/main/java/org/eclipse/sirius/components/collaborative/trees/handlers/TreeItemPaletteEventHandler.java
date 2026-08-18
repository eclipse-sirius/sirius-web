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
import org.eclipse.sirius.components.collaborative.dto.KeyBinding;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeEventHandler;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeInput;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeItemContextMenuEntryProvider;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeItemPaletteProvider;
import org.eclipse.sirius.components.collaborative.trees.dto.FetchTreeItemContextMenuEntry;
import org.eclipse.sirius.components.collaborative.trees.dto.ITreeItemContextMenuEntry;
import org.eclipse.sirius.components.collaborative.trees.dto.SingleClickTreeItemContextMenuEntry;
import org.eclipse.sirius.components.collaborative.trees.dto.palette.FetchTreeItemTool;
import org.eclipse.sirius.components.collaborative.trees.dto.palette.SingleClickTreeItemTool;
import org.eclipse.sirius.components.collaborative.trees.dto.palette.TreeItemPaletteInput;
import org.eclipse.sirius.components.collaborative.trees.services.api.ICollaborativeTreeMessageService;
import org.eclipse.sirius.components.collaborative.trees.services.api.ITreeQueryService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.palette.dto.GetPaletteSuccessPayload;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.components.palette.dto.IPaletteEntry;
import org.eclipse.sirius.components.palette.dto.Palette;

import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Used to retrieve a palette for a given tree item.
 *
 * @author mcharfadi
 */
@Service
public class TreeItemPaletteEventHandler implements ITreeEventHandler {

    private final ICollaborativeTreeMessageService messageService;

    private final ITreeQueryService treeQueryService;

    private final Counter counter;

    private final List<ITreeItemPaletteProvider> treeItemPaletteProviders;

    private final List<ITreeItemContextMenuEntryProvider> contextMenuEntryProviders;

    public TreeItemPaletteEventHandler(
            ICollaborativeTreeMessageService messageService,
            ITreeQueryService treeQueryService,
            List<ITreeItemPaletteProvider> treeItemPaletteProviders,
            List<ITreeItemContextMenuEntryProvider> contextMenuEntryProviders,
            MeterRegistry meterRegistry) {
        this.messageService = Objects.requireNonNull(messageService);
        this.treeQueryService = Objects.requireNonNull(treeQueryService);
        this.treeItemPaletteProviders = Objects.requireNonNull(treeItemPaletteProviders);
        this.contextMenuEntryProviders = Objects.requireNonNull(contextMenuEntryProviders);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);

    }

    @Override
    public boolean canHandle(IEditingContext editingContext, ITreeInput treeInput) {
        return treeInput instanceof TreeItemPaletteInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, TreeDescription treeDescription, Tree tree, ITreeInput treeInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(treeInput.getClass().getSimpleName(), TreeItemPaletteInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(treeInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, treeInput.representationId(), treeInput);

        if (treeInput instanceof TreeItemPaletteInput input) {
            var optionalTreeItem = this.treeQueryService.findTreeItem(tree, input.treeItemId());
            if (optionalTreeItem.isPresent()) {
                TreeItem treeItem = optionalTreeItem.get();

                var optionalPalette = this.treeItemPaletteProviders.stream()
                        .filter(paletteProvider -> paletteProvider.canHandle(editingContext, treeDescription, tree, treeItem))
                        .findFirst()
                        .map(paletteProvider -> paletteProvider.getPalette(editingContext, treeDescription, tree, treeItem));

                if (optionalPalette.isPresent()) {
                    payload = new GetPaletteSuccessPayload(treeInput.id(), optionalPalette.get());
                } else {
                    var entries = this.contextMenuEntryProviders.stream()
                            .filter(provider -> provider.canHandle(editingContext, treeDescription, tree, treeItem))
                            .flatMap(provider -> provider.getTreeItemContextMenuEntries(editingContext, treeDescription, tree, treeItem).stream())
                            .map(this::convertContextMenuEntryToPaletteEntry)
                            .flatMap(Optional::stream)
                            .toList();
                    var palette = new Palette("", List.of(), entries);
                    payload = new GetPaletteSuccessPayload(treeInput.id(), palette);
                }
            }
        }

        changeDescriptionSink.tryEmitNext(changeDescription);
        payloadSink.tryEmitValue(payload);
    }

    private Optional<IPaletteEntry> convertContextMenuEntryToPaletteEntry(ITreeItemContextMenuEntry treeItemContextMenuEntry) {
        Optional<IPaletteEntry> optionalEntry = Optional.empty();
        if (treeItemContextMenuEntry instanceof SingleClickTreeItemContextMenuEntry(String id, String label, List<String> iconURL, boolean withImpactAnalysis, List<KeyBinding> keyBindings)) {
            optionalEntry = Optional.of(new SingleClickTreeItemTool(id, label, iconURL, withImpactAnalysis, keyBindings));
        } else if (treeItemContextMenuEntry instanceof FetchTreeItemContextMenuEntry(String id, String label, List<String> iconURL, List<KeyBinding> keyBindings)) {
            optionalEntry = Optional.of(new FetchTreeItemTool(id, label, iconURL, keyBindings));
        }
        return optionalEntry;
    }
}
