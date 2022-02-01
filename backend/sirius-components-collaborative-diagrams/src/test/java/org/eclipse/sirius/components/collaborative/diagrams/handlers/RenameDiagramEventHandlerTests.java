/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.dto.RenameDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.collaborative.dto.RenameRepresentationSuccessPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.tests.TestDiagramDescriptionBuilder;
import org.eclipse.sirius.components.representations.IRepresentation;
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
        String representationId = UUID.randomUUID().toString();
        UUID targetObjectId = UUID.randomUUID();

        DiagramDescription diagramDescription = new TestDiagramDescriptionBuilder().getDiagramDescription(UUID.randomUUID(), List.of(), List.of(), List.of());

        // @formatter:off
        Diagram diagram = Diagram.newDiagram(representationId)
                .label(OLD_LABEL)
                .descriptionId(diagramDescription.getId())
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

        RenameDiagramEventHandler handler = new RenameDiagramEventHandler(representationSearchService, new IRepresentationPersistenceService.NoOp(), new ICollaborativeDiagramMessageService.NoOp(),
                new SimpleMeterRegistry());

        var input = new RenameDiagramInput(UUID.randomUUID(), projectId, representationId, NEW_LABEL);
        assertThat(handler.canHandle(input)).isTrue();

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), new IDiagramContext.NoOp(), input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.REPRESENTATION_RENAMING);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(RenameRepresentationSuccessPayload.class);
        assertThat(((RenameRepresentationSuccessPayload) payload).getRepresentation().getLabel()).isEqualTo(NEW_LABEL);
    }
}
