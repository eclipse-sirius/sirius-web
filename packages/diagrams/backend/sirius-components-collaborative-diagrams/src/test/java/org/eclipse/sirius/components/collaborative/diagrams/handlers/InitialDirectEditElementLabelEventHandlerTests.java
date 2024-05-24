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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IInitialDirectEditElementLabelProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InitialDirectEditElementLabelInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InitialDirectEditElementLabelSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.LabelOverflowStrategy;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.LabelTextAlign;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.OutsideLabel;
import org.eclipse.sirius.components.diagrams.OutsideLabelLocation;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Used to test the {@link InitialDirectEditElementLabelEventHandler}.
 *
 * @author gcoutable
 */
public class InitialDirectEditElementLabelEventHandlerTests {

    private static final String INIT_LABEL = "initLabel";
    private UUID editingContextId;
    private InitialDirectEditElementLabelEventHandler handler;

    @BeforeEach
    void setup() {
        UUID diagramDescriptionId = UUID.randomUUID();
        this.editingContextId = UUID.randomUUID();

        DiagramDescription diagramDescription = new TestDiagramDescriptionBuilder().getDiagramDescription(diagramDescriptionId.toString(), List.of(), List.of(), List.of());
        IRepresentationDescriptionSearchService representationDescriptionSearchService = new IRepresentationDescriptionSearchService.NoOp() {
            @Override
            public Optional<IRepresentationDescription> findById(IEditingContext editingContext, String representationDescriptionId) {
                return Optional.of(diagramDescription);
            }
        };

        IInitialDirectEditElementLabelProvider initialDirectEditElementLabelProvider = new IInitialDirectEditElementLabelProvider() {
            @Override
            public String getInitialDirectEditElementLabel(Object graphicalElement, String labelId, Diagram diagram, IEditingContext editingContext) {
                return INIT_LABEL;
            }

            @Override
            public boolean canHandle(DiagramDescription diagramDescription) {
                return true;
            }
        };

        this.handler = new InitialDirectEditElementLabelEventHandler(representationDescriptionSearchService, new DiagramQueryService(), List.of(initialDirectEditElementLabelProvider),
                new ICollaborativeDiagramMessageService.NoOp(), new SimpleMeterRegistry());
    }

    @Test
    public void testInitialDirectEditElementLabelEventHandler() {
        Node node = new TestDiagramBuilder().getNode(UUID.randomUUID().toString(), true);
        String labelId = node.getInsideLabel().getId();
        Diagram diagram = Diagram.newDiagram(new TestDiagramBuilder().getDiagram(UUID.randomUUID().toString())).nodes(List.of(node)).build();

        var input = new InitialDirectEditElementLabelInput(UUID.randomUUID(), this.editingContextId.toString(), UUID.randomUUID().toString(), labelId);
        assertThat(this.handler.canHandle(input)).isTrue();

        One<IPayload> payloadSink = Sinks.one();
        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();

        IEditingContext editingContext = this.editingContextId::toString;

        this.handler.handle(payloadSink, changeDescriptionSink, editingContext, new DiagramContext(diagram), input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.NOTHING);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(InitialDirectEditElementLabelSuccessPayload.class);
        InitialDirectEditElementLabelSuccessPayload successPayload = (InitialDirectEditElementLabelSuccessPayload) payload;
        assertThat(successPayload.initialDirectEditElementLabel()).isEqualTo(INIT_LABEL);
    }

    @Test
    public void testInitialDirectEditElementLabelOnNodeWithoutInsideLabelEventHandler() {
        String labelId = UUID.randomUUID().toString();
        LabelStyle labelStyle = LabelStyle.newLabelStyle()
                .color("#000000")
                .fontSize(16)
                .iconURL(List.of())
                .build();
        Node node = new TestDiagramBuilder().getNodeWithOutsideLabels(UUID.randomUUID().toString(), false, List.of(new OutsideLabel(labelId, "text", OutsideLabelLocation.BOTTOM_MIDDLE, labelStyle,
                LabelOverflowStrategy.NONE, LabelTextAlign.LEFT)));
        Diagram diagram = Diagram.newDiagram(new TestDiagramBuilder().getDiagram(UUID.randomUUID().toString())).nodes(List.of(node)).build();

        var input = new InitialDirectEditElementLabelInput(UUID.randomUUID(), this.editingContextId.toString(), UUID.randomUUID().toString(), labelId);
        assertThat(this.handler.canHandle(input)).isTrue();

        One<IPayload> payloadSink = Sinks.one();
        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();

        IEditingContext editingContext = this.editingContextId::toString;

        this.handler.handle(payloadSink, changeDescriptionSink, editingContext, new DiagramContext(diagram), input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.NOTHING);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(InitialDirectEditElementLabelSuccessPayload.class);
        InitialDirectEditElementLabelSuccessPayload successPayload = (InitialDirectEditElementLabelSuccessPayload) payload;
        assertThat(successPayload.initialDirectEditElementLabel()).isEqualTo(INIT_LABEL);
    }


}
