/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.tests.TestDiagramDescriptionBuilder;
import org.eclipse.sirius.web.services.api.projects.IProjectInput;
import org.eclipse.sirius.web.services.api.representations.RenameRepresentationInput;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;
import org.junit.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * Unit tests of the rename representation event handler.
 *
 * @author arichard
 */
public class RenameDiagramEventHandlerTestCases {
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
                .size(Size.newSize().height(10).width(10).build())
                .position(Position.newPosition().x(0).y(0).build())
                .nodes(Collections.emptyList())
                .edges(Collections.emptyList())
                .build();

        RepresentationDescriptor representationDescriptor = RepresentationDescriptor.newRepresentationDescriptor(representationId)
                .label(OLD_LABEL)
                .projectId(projectId)
                .descriptionId(diagram.getDescriptionId())
                .representation(diagram)
                .targetObjectId(targetObjectId.toString())
                .build();
        // @formatter:on
        NoOpRepresentationService noOpRepresentationService = new NoOpRepresentationService() {
            @Override
            public Optional<RepresentationDescriptor> getRepresentation(UUID representationId) {
                return Optional.of(representationDescriptor);
            }
        };

        RenameDiagramEventHandler handler = new RenameDiagramEventHandler(noOpRepresentationService, new NoOpCollaborativeDiagramMessageService(), new SimpleMeterRegistry());

        IProjectInput input = new RenameRepresentationInput(projectId, representationId, NEW_LABEL);

        assertThat(handler.canHandle(input)).isTrue();

        EventHandlerResponse handlerResponse = handler.handle(new NoOpEditingContext(), input);
        IPayload payload = handlerResponse.getPayload();
        assertThat(payload).isInstanceOf(RenameRepresentationSuccessPayload.class);
    }
}
