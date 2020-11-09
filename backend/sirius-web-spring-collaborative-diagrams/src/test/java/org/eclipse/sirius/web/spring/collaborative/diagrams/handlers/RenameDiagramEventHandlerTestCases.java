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
import org.eclipse.sirius.web.collaborative.diagrams.api.DiagramCreationParameters;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramService;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.tests.TestDiagramDescriptionBuilder;
import org.eclipse.sirius.web.representations.IRepresentationDescription;
import org.eclipse.sirius.web.services.api.Context;
import org.eclipse.sirius.web.services.api.dto.IPayload;
import org.eclipse.sirius.web.services.api.dto.IProjectInput;
import org.eclipse.sirius.web.services.api.objects.IEditingContext;
import org.eclipse.sirius.web.services.api.objects.IObjectService;
import org.eclipse.sirius.web.services.api.representations.IRepresentationDescriptionService;
import org.eclipse.sirius.web.services.api.representations.RenameRepresentationInput;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

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

        IObjectService objectService = new NoOpObjectService() {
            @Override
            public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
                return Optional.of(new Object());
            }
        };

        IRepresentationDescriptionService representationDescriptionService = new NoOpRepresentationDescriptionService() {
            @Override
            public Optional<IRepresentationDescription> findRepresentationDescriptionById(UUID id) {
                return Optional.of(diagramDescription);
            }
        };

        IDiagramService diagramService = new NoOpDiagramService() {
            @Override
            public Diagram create(DiagramCreationParameters parameters) {
                return diagram;
            }
        };

        RenameDiagramEventHandler handler = new RenameDiagramEventHandler(noOpRepresentationService, new NoOpCollaborativeDiagramMessageService(), diagramService, objectService,
                representationDescriptionService, new SimpleMeterRegistry());

        IProjectInput input = new RenameRepresentationInput(projectId, representationId, NEW_LABEL);

        var context = new Context(new UsernamePasswordAuthenticationToken(null, null));
        assertThat(handler.canHandle(input)).isTrue();

        EventHandlerResponse handlerResponse = handler.handle(new NoOpEditingContext(), input, context);
        IPayload payload = handlerResponse.getPayload();
        assertThat(payload).isInstanceOf(RenameRepresentationSuccessPayload.class);
    }
}
