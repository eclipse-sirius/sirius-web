/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramChangeKind;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.EditRectangularNodeAppearanceInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LabelAppearanceInput;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.appearancedata.LabelAppearanceData;
import org.eclipse.sirius.components.diagrams.appearancedata.LabelCustomizedStyle;
import org.eclipse.sirius.components.diagrams.appearancedata.NodeAppearanceData;
import org.eclipse.sirius.components.diagrams.appearancedata.RectangularNodeCustomizedStyle;
import org.eclipse.sirius.components.diagrams.events.EditNodeAppearanceEvent;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Handles diagram events related to editing a rectangular node's appearance.
 *
 * @author nvannier
 */
@Service
public class EditRectangularNodeAppearanceEventHandler implements IDiagramEventHandler {

    private final ICollaborativeDiagramMessageService messageService;

    private final IDiagramQueryService diagramQueryService;

    private final Counter counter;

    public EditRectangularNodeAppearanceEventHandler(ICollaborativeDiagramMessageService messageService, IDiagramQueryService diagramQueryService, MeterRegistry meterRegistry) {
        this.messageService = Objects.requireNonNull(messageService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof EditRectangularNodeAppearanceInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), EditRectangularNodeAppearanceInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.representationId(), diagramInput);

        if (diagramInput instanceof EditRectangularNodeAppearanceInput editAppearanceInput) {
            String nodeId = editAppearanceInput.nodeId();
            Optional<Node> optionalNode = diagramQueryService.findNodeById(diagramContext.getDiagram(), nodeId);
            if (optionalNode.isPresent()) {
                Node node = optionalNode.get();
                NodeAppearanceData currentNodeAppearanceData = Optional.ofNullable(node.getAppearanceData())
                        .orElse(new NodeAppearanceData(nodeId, RectangularNodeCustomizedStyle.newRectangularNodeCustomizedStyle().build(), new ArrayList<>()));
                List<LabelAppearanceData> newLabelsAppearanceData = new ArrayList<>();

                RectangularNodeCustomizedStyle newNodeCustomizedStyle = buildNewRectangularNodeCustomizedStyle(editAppearanceInput, currentNodeAppearanceData);

                if (Objects.nonNull(node.getInsideLabel())) {
                    LabelAppearanceData newInsideLabelAppearanceData = buildNewInsideLabelAppearanceData(editAppearanceInput, node, currentNodeAppearanceData);
                    newLabelsAppearanceData.add(newInsideLabelAppearanceData);
                }

                NodeAppearanceData newNodeAppearanceData = new NodeAppearanceData(nodeId, newNodeCustomizedStyle, newLabelsAppearanceData);
                diagramContext.getDiagramEvents().add(new EditNodeAppearanceEvent(nodeId, newNodeAppearanceData));
                payload = new SuccessPayload(diagramInput.id());
                changeDescription = new ChangeDescription(DiagramChangeKind.DIAGRAM_APPEARANCE_CHANGE, diagramInput.representationId(), diagramInput);
            } else {
                String nodeNotFoundMessage = this.messageService.nodeNotFound(nodeId);
                payload = new ErrorPayload(diagramInput.id(), nodeNotFoundMessage);
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private LabelAppearanceData buildNewInsideLabelAppearanceData(EditRectangularNodeAppearanceInput editAppearanceInput, Node node, NodeAppearanceData currentNodeAppearanceData) {
        String insideLabelId = node.getInsideLabel().getId();
        LabelCustomizedStyle.Builder insideLabelCustomizedStyleBuilder = currentNodeAppearanceData
                .labelsAppearanceData()
                .stream()
                .filter(labelAppearanceData -> Objects.equals(insideLabelId, labelAppearanceData.id()))
                .findFirst()
                .map(labelAppearanceData -> LabelCustomizedStyle.newLabelCustomizedStyle(labelAppearanceData.customizedLabelStyle()))
                .orElse(LabelCustomizedStyle.newLabelCustomizedStyle());
        if (Objects.nonNull(editAppearanceInput.appearance().insideLabel()) && Objects.equals(insideLabelId, editAppearanceInput.appearance().insideLabel().labelId())) {
            LabelAppearanceInput insideLabelAppearanceInput = editAppearanceInput.appearance().insideLabel();

            if (Objects.nonNull(insideLabelAppearanceInput.bold())) {
                var insideLabelBold = insideLabelAppearanceInput.bold();
                insideLabelCustomizedStyleBuilder.bold(insideLabelBold);
            }
        }
        return new LabelAppearanceData(insideLabelId, insideLabelCustomizedStyleBuilder.build());
    }

    private RectangularNodeCustomizedStyle buildNewRectangularNodeCustomizedStyle(EditRectangularNodeAppearanceInput editAppearanceInput, NodeAppearanceData currentNodeAppearanceData) {
        RectangularNodeCustomizedStyle.Builder newNodeCustomizedStyleBuilder;
        if (currentNodeAppearanceData.customizedNodeStyle() instanceof RectangularNodeCustomizedStyle currentCustomizedStyle) {
            newNodeCustomizedStyleBuilder = RectangularNodeCustomizedStyle.newRectangularNodeCustomizedStyle(currentCustomizedStyle);
        } else {
            newNodeCustomizedStyleBuilder = RectangularNodeCustomizedStyle.newRectangularNodeCustomizedStyle();
        }

        if (Objects.nonNull(editAppearanceInput.appearance().background())) {
            String nodeBackground = editAppearanceInput.appearance().background();
            newNodeCustomizedStyleBuilder.background(nodeBackground);
        }

        return newNodeCustomizedStyleBuilder.build();
    }
}
