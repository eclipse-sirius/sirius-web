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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.collaborative.api.dto.RenameRepresentationSuccessPayload;
import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.api.services.IRepresentationPersistenceService;
import org.eclipse.sirius.web.collaborative.api.services.IRepresentationSearchService;
import org.eclipse.sirius.web.collaborative.diagrams.api.dto.RenameDiagramInput;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.tests.TestDiagramDescriptionBuilder;
import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.representations.ISemanticRepresentation;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

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
        UUID projectId = UUID.randomUUID();
        UUID representationId = UUID.randomUUID();
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
            public <T extends IRepresentation> Optional<T> findById(IEditingContext editingContext, UUID representationId, Class<T> representationClass) {
                return Optional.of(diagram).map(representationClass::cast);
            }
        };

        IRepresentationPersistenceService representationPersistenceService = new IRepresentationPersistenceService() {

            @Override
            public void save(UUID editingContextId, ISemanticRepresentation representation) {
            }
        };

        RenameDiagramEventHandler handler = new RenameDiagramEventHandler(representationSearchService, representationPersistenceService, new NoOpCollaborativeDiagramMessageService(),
                new SimpleMeterRegistry());

        var input = new RenameDiagramInput(UUID.randomUUID(), projectId, representationId, NEW_LABEL);

        assertThat(handler.canHandle(input)).isTrue();

        EventHandlerResponse handlerResponse = handler.handle(new NoOpEditingContext(), new NoOpDiagramContext(), input);
        IPayload payload = handlerResponse.getPayload();
        assertThat(payload).isInstanceOf(RenameRepresentationSuccessPayload.class);
    }
}
