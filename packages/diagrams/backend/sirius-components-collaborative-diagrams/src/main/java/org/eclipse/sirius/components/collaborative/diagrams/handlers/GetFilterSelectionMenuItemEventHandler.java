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
package org.eclipse.sirius.components.collaborative.diagrams.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramToolbarFilterSelectionProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.toolbar.tools.FilterSelectionMenuItem;
import org.eclipse.sirius.components.collaborative.diagrams.dto.toolbar.tools.GetFilterSelectionMenuItemsInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.toolbar.tools.GetFilterSelectionMenuItemsSuccessPayload;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Handler used to get the FilterSelectionMenuItem of the diagram toolbar.
 *
 * @author mcharfadi
 */
@Service
public class GetFilterSelectionMenuItemEventHandler implements IDiagramEventHandler {

    private final List<IDiagramToolbarFilterSelectionProvider> toolbarFilterSelectionMenuItemProviders;

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    public GetFilterSelectionMenuItemEventHandler(List<IDiagramToolbarFilterSelectionProvider> toolbarFilterSelectionMenuItemProviders, ICollaborativeMessageService messageService, MeterRegistry meterRegistry) {
        this.toolbarFilterSelectionMenuItemProviders = Objects.requireNonNull(toolbarFilterSelectionMenuItemProviders);
        this.messageService = Objects.requireNonNull(messageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IDiagramInput diagramInput) {
        return diagramInput instanceof GetFilterSelectionMenuItemsInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, DiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), GetFilterSelectionMenuItemsInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.id(), message);

        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), diagramInput);

        if (diagramInput instanceof GetFilterSelectionMenuItemsInput filterSelectionMenuItemsInput) {
            List<FilterSelectionMenuItem> filterSelectionMenuItems = new ArrayList<>();
            toolbarFilterSelectionMenuItemProviders.stream()
                .filter(provider -> provider.canHandle(editingContext, diagramContext, filterSelectionMenuItemsInput.diagramElementIds()))
                .map(provider -> provider.getDiagramToolbarFilterSelectionMenuItem(editingContext, diagramContext, filterSelectionMenuItemsInput.diagramElementIds()))
                .forEach(filterSelectionMenuItems::add);
            payload = new GetFilterSelectionMenuItemsSuccessPayload(diagramInput.id(), filterSelectionMenuItems);
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
