/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.RenameDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.collaborative.dto.RenameRepresentationSuccessPayload;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to rename a diagram.
 *
 * @author arichard
 */
@Service
public class RenameDiagramEventHandler implements IDiagramEventHandler {

    private final IRepresentationSearchService representationSearchService;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final ICollaborativeDiagramMessageService messageService;

    private final Counter counter;

    public RenameDiagramEventHandler(IRepresentationSearchService representationSearchService, IRepresentationPersistenceService representationPersistenceService,
            ICollaborativeDiagramMessageService messageService, MeterRegistry meterRegistry) {
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof RenameDiagramInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), RenameDiagramInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.getId(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.getRepresentationId(), diagramInput);

        if (diagramInput instanceof RenameDiagramInput) {
            RenameDiagramInput renameRepresentationInput = (RenameDiagramInput) diagramInput;
            String representationId = renameRepresentationInput.getRepresentationId();
            String newLabel = renameRepresentationInput.getNewLabel();
            Optional<Diagram> optionalDiagram = this.representationSearchService.findById(editingContext, representationId, Diagram.class);
            if (optionalDiagram.isPresent()) {
                Diagram diagram = optionalDiagram.get();

                Diagram renamedDiagram = Diagram.newDiagram(diagram).label(newLabel).build();
                this.representationPersistenceService.save(editingContext, renamedDiagram);
                diagramContext.update(renamedDiagram);

                payload = new RenameRepresentationSuccessPayload(diagramInput.getId(), renamedDiagram);
                changeDescription = new ChangeDescription(ChangeKind.REPRESENTATION_RENAMING, renameRepresentationInput.getRepresentationId(), diagramInput);
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
