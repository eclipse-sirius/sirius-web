/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IToolService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.tests.TestDiagramBuilder;
import org.eclipse.sirius.components.diagrams.tools.ITool;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.representations.Success;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Used to test the execution of a single click on diagram element tool.
 *
 * @author sbegaudeau
 */
public class InvokeSingleClickOnDiagramElementToolEventHandlerTests {
    @Test
    public void testInvokeTool() {
        var objectService = new IObjectService.NoOp() {
            @Override
            public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
                return Optional.of(new Object());
            }
        };

        var diagramQueryService = new IDiagramQueryService.NoOp();

        // @formatter:off
        var tool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("toolId")
                .label("label")
                .imageURL("imageURL")
                .targetDescriptions(List.of())
                .selectionDescriptionId(null)
                .handler(variableManager -> new Success(ChangeKind.SEMANTIC_CHANGE, Map.of()))
                .appliesToDiagramRoot(true)
                .build();
        // @formatter:on

        var toolService = new IToolService.NoOp() {
            @Override
            public Optional<ITool> findToolById(IEditingContext editingContext, Diagram diagram, String toolId) {
                return Optional.of(tool);
            }
        };
        var representationDescriptionSearchService = new IRepresentationDescriptionSearchService.NoOp();

        var handler = new InvokeSingleClickOnDiagramElementToolEventHandler(objectService, diagramQueryService, toolService, new ICollaborativeDiagramMessageService.NoOp(), new SimpleMeterRegistry(),
                representationDescriptionSearchService);

        var input = new InvokeSingleClickOnDiagramElementToolInput(UUID.randomUUID(), "editingContextId", "representationId", "diagramId", "toolId", 5.0, 8.0, "selectedObjectId");

        IEditingContext editingContext = () -> "editingContextId";

        assertThat(handler.canHandle(input)).isTrue();

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        IDiagramContext diagramContext = new DiagramContext(new TestDiagramBuilder().getDiagram("diagramId"));
        handler.handle(payloadSink, changeDescriptionSink, editingContext, diagramContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(InvokeSingleClickOnDiagramElementToolSuccessPayload.class);
    }
}
