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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.representations.ISemanticRepresentationMetadata;
import org.eclipse.sirius.web.representations.SemanticRepresentationMetadata;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeKind;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.web.spring.collaborative.diagrams.dto.RenameDiagramInput;
import org.eclipse.sirius.web.spring.collaborative.dto.RenameRepresentationSuccessPayload;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Unit tests of the rename representation event handler.
 *
 * @author arichard
 */
public class RenameDiagramEventHandlerTests {
    private static final String OLD_LABEL = "oldLabel"; //$NON-NLS-1$

    private static final String NEW_LABEL = "newLabel"; //$NON-NLS-1$

    @Test
    public void testRenameRepresentation() {
        String projectId = UUID.randomUUID().toString();
        UUID representationId = UUID.randomUUID();
        UUID targetObjectId = UUID.randomUUID();

        // @formatter:off
        Diagram diagram = Diagram.newDiagram(representationId.toString())
                .targetObjectId(targetObjectId.toString())
                .size(Size.of(10, 10))
                .position(Position.at(0, 0))
                .nodes(Collections.emptyList())
                .edges(Collections.emptyList())
                .build();
        // @formatter:on
        IRepresentationSearchService representationSearchService = new IRepresentationSearchService() {

            @Override
            public <T extends IRepresentation> Optional<T> findById(IEditingContext editingContext, String representationId, Class<T> representationClass) {
                return Optional.of(diagram).map(representationClass::cast);
            }
        };

        IRepresentationPersistenceService representationPersistenceService = new IRepresentationPersistenceService.NoOp();

        RenameDiagramEventHandler handler = new RenameDiagramEventHandler(representationSearchService, representationPersistenceService, new NoOpCollaborativeDiagramMessageService(),
                new SimpleMeterRegistry());

        var input = new RenameDiagramInput(UUID.randomUUID(), projectId, representationId.toString(), NEW_LABEL);
        assertThat(handler.canHandle(input)).isTrue();

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        // @formatter:off
        ISemanticRepresentationMetadata diagramMetadata = SemanticRepresentationMetadata.newRepresentationMetadata(representationId.toString())
                .label(OLD_LABEL)
                .kind(Diagram.KIND)
                .descriptionId(UUID.randomUUID())
                .targetObjectId(targetObjectId.toString())
                .build();
        // @formatter:on

        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), new IDiagramContext.NoOp(), diagramMetadata, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.REPRESENTATION_RENAMING);

        IPayload payload = payloadSink.asMono().block();

        assertThat(payload).isInstanceOf(RenameRepresentationSuccessPayload.class);
        assertThat(((RenameRepresentationSuccessPayload) payload).getRepresentation().getLabel()).isEqualTo(NEW_LABEL);
    }
}
