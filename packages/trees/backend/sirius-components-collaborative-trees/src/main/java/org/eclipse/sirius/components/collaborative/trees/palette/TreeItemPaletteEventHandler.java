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
package org.eclipse.sirius.components.collaborative.trees.palette;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeEventHandler;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeInput;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeItemPaletteProvider;
import org.eclipse.sirius.components.collaborative.trees.dto.palette.TreeItemPaletteInput;
import org.eclipse.sirius.components.collaborative.trees.palette.api.ITreeItemPaletteCustomizer;
import org.eclipse.sirius.components.collaborative.trees.services.api.ICollaborativeTreeMessageService;
import org.eclipse.sirius.components.collaborative.trees.services.api.ITreeQueryService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.palette.dto.GetPaletteSuccessPayload;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
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

    private final List<ITreeItemPaletteCustomizer> treeItemPaletteCustomizers;

    public TreeItemPaletteEventHandler(
            ICollaborativeTreeMessageService messageService,
            ITreeQueryService treeQueryService,
            List<ITreeItemPaletteProvider> treeItemPaletteProviders,
            List<ITreeItemPaletteCustomizer> treeItemPaletteCustomizers,
            MeterRegistry meterRegistry) {
        this.messageService = Objects.requireNonNull(messageService);
        this.treeQueryService = Objects.requireNonNull(treeQueryService);
        this.treeItemPaletteProviders = Objects.requireNonNull(treeItemPaletteProviders);
        this.treeItemPaletteCustomizers = Objects.requireNonNull(treeItemPaletteCustomizers);

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
                    var palette = optionalPalette.get();
                    var customizers = this.treeItemPaletteCustomizers.stream()
                            .filter(customizer -> customizer.canHandle(editingContext, treeDescription, tree, treeItem))
                            .toList();
                    for (var customizer: customizers) {
                        palette = customizer.customize(editingContext, treeDescription, tree, treeItem, palette);
                    }

                    payload = new GetPaletteSuccessPayload(treeInput.id(), palette);
                }
            }
        }

        changeDescriptionSink.tryEmitNext(changeDescription);
        payloadSink.tryEmitValue(payload);
    }
}
