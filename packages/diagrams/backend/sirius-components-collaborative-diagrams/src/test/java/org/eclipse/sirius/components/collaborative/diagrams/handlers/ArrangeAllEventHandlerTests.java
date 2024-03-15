/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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

import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramChangeKind;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ArrangeAllInput;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.diagrams.events.ArrangeAllEvent;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Tests of the arrange all event handler.
 *
 * @author sbegaudeau
 */
public class ArrangeAllEventHandlerTests {
    @Test
    public void testArrangeAll() {
        var handler = new ArrangeAllEventHandler(new ICollaborativeDiagramMessageService.NoOp(), new SimpleMeterRegistry());
        var input = new ArrangeAllInput(UUID.randomUUID(), "editingContextId", "representationId");

        assertThat(handler.canHandle(input)).isTrue();

        One<IPayload> payloadSink = Sinks.one();
        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        IDiagramContext diagramContext = new DiagramContext(new TestDiagramBuilder().getDiagram(UUID.randomUUID().toString()));

        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), diagramContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(DiagramChangeKind.DIAGRAM_LAYOUT_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(SuccessPayload.class);

        assertThat(diagramContext.getDiagramEvents()).hasSize(1);
        assertThat(diagramContext.getDiagramEvents().get(0)).isInstanceOf(ArrangeAllEvent.class);
    }
}
