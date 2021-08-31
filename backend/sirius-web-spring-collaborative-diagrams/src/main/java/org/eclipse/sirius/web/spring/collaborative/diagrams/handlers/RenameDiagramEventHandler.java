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
package org.eclipse.sirius.web.spring.collaborative.diagrams.handlers;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.representations.ISemanticRepresentationMetadata;
import org.eclipse.sirius.web.representations.SemanticRepresentationMetadata;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeKind;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.web.spring.collaborative.api.Monitoring;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.web.spring.collaborative.diagrams.dto.RenameDiagramInput;
import org.eclipse.sirius.web.spring.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.web.spring.collaborative.dto.RenameRepresentationSuccessPayload;
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
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext,
            ISemanticRepresentationMetadata diagramMetadata, IDiagramInput diagramInput) {
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

                // @formatter:off
                ISemanticRepresentationMetadata renamedMetadata = SemanticRepresentationMetadata.newRepresentationMetadata(diagramMetadata.getId())
                        .descriptionId(diagramMetadata.getDescriptionId())
                        .label(newLabel)
                        .kind(diagramMetadata.getKind())
                        .targetObjectId(diagramMetadata.getTargetObjectId())
                        .build();
                // @formatter:on
                this.representationPersistenceService.save(editingContext, renamedMetadata, diagram);

                payload = new RenameRepresentationSuccessPayload(diagramInput.getId(), renamedMetadata);
                changeDescription = new ChangeDescription(ChangeKind.REPRESENTATION_RENAMING, renameRepresentationInput.getRepresentationId(), diagramInput);
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
