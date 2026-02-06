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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IToolbarProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramToolbar;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetToolbarInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetToolbarSuccessPayload;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Tests of the get toolbar event handler.
 *
 * @author tgiraudet
 */
public class GetToolbarEventHandlerTests {

    @Test
    public void testGetToolbar() {
        String diagramDescriptionId = UUID.randomUUID().toString();
        var diagramDescription = new TestDiagramDescriptionBuilder().getDiagramDescription(diagramDescriptionId, List.of(), List.of());

        var representationDescriptionSearchService = new IRepresentationDescriptionSearchService.NoOp() {
            @Override
            public Optional<IRepresentationDescription> findById(IEditingContext editingContext, String representationDescriptionId) {
                if (representationDescriptionId.equals(diagramDescriptionId)) {
                    return Optional.of(diagramDescription);
                }
                return Optional.empty();
            }
        };

        IToolbarProvider toolbarProvider = (editingContext, diagramContext, description) -> Optional.of(new DiagramToolbar());

        var handler = new GetToolbarEventHandler(representationDescriptionSearchService, toolbarProvider, new ICollaborativeMessageService.NoOp(), new SimpleMeterRegistry());
        var input = new GetToolbarInput(UUID.randomUUID(), "editingContextId", "representationId");

        assertThat(handler.canHandle(null, input)).isTrue();

        One<IPayload> payloadSink = Sinks.one();
        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();

        Diagram diagram = Diagram.newDiagram(UUID.randomUUID().toString())
                .descriptionId(diagramDescriptionId)
                .targetObjectId("diagramTargetObjectId")
                .nodes(List.of())
                .edges(List.of())
                .build();
        DiagramContext diagramContext = new DiagramContext(diagram);

        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), diagramContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.NOTHING);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(GetToolbarSuccessPayload.class);
    }


    @Test
    public void testGetToolbarWithMissingDiagramDescription() {
        var handler = new GetToolbarEventHandler(new IRepresentationDescriptionSearchService.NoOp(),
            (editingContext, diagramContext, description) -> Optional.empty(),
            new ICollaborativeMessageService.NoOp(),
            new SimpleMeterRegistry());
        var input = new GetToolbarInput(UUID.randomUUID(), "editingContextId", "representationId");

        One<IPayload> payloadSink = Sinks.one();
        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        DiagramContext diagramContext = new DiagramContext(new TestDiagramBuilder().getDiagram(UUID.randomUUID().toString()));

        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), diagramContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.NOTHING);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(ErrorPayload.class);
    }
}
