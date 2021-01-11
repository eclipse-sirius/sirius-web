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
package org.eclipse.sirius.web.diagrams.renderer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.diagrams.LineStyle;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.web.diagrams.ViewCreationRequest;
import org.eclipse.sirius.web.diagrams.components.DiagramComponent;
import org.eclipse.sirius.web.diagrams.components.DiagramComponentProps;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.description.LabelDescription;
import org.eclipse.sirius.web.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.diagrams.description.SynchronizationPolicy;
import org.eclipse.sirius.web.representations.Status;
import org.eclipse.sirius.web.representations.VariableManager;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test cases for the rendering of unsynchronized diagrams.
 *
 * @author sbegaudeau
 */
public class UnsynchronizedDiagramTestCases {

    private static final String NODE_TYPE = "node:rectangular"; //$NON-NLS-1$

    private static final String TARGET_OBJECT_ID = "targetObjectId"; //$NON-NLS-1$

    private final Logger logger = LoggerFactory.getLogger(UnsynchronizedDiagramTestCases.class);

    /**
     * This very simple test will validate that we can render synchronized and unsynchronized diagram elements. We will
     * use a diagram description containing one synchronized node description and one unsynchronized node description.
     * We will test the following sequence of events:
     *
     * <ul>
     * <li>render the diagram from scratch: only the synchronized element should appear</li>
     * <li>refresh the diagram: nothing should change</li>
     * <li>create a ViewCreationRequest on a node and refresh this new diagram: two nodes should be visible, the
     * synchronized one and the unsynchronized one</li>
     * </ul>
     */
    @Test
    public void testUnsynchronizedRenderingOnRoot() {
        DiagramDescription diagramDescription = this.getDiagramDescription();

        Diagram initialDiagram = this.render(diagramDescription, List.of(), Optional.empty());
        assertThat(initialDiagram.getNodes()).hasSize(1);

        Diagram refreshedDiagram = this.render(diagramDescription, List.of(), Optional.of(initialDiagram));
        assertThat(refreshedDiagram.getNodes()).hasSize(1);

        // @formatter:off
        ViewCreationRequest viewCreationRequest = ViewCreationRequest.newViewCreationRequest()
            .descriptionId(diagramDescription.getNodeDescriptions().get(0).getId())
            .parentElementId(refreshedDiagram.getId())
            .targetObjectId(TARGET_OBJECT_ID)
            .build();
        // @formatter:on
        Diagram refreshedDiagramAfterNodeCreation = this.render(diagramDescription, List.of(viewCreationRequest), Optional.of(refreshedDiagram));
        assertThat(refreshedDiagramAfterNodeCreation.getNodes()).hasSize(2);
    }

    /**
     * This very simple test will validate that we can add an unsynchronized node thanks to a ViewCreationRequest on a
     * node container. We will test the following sequence of events:
     *
     * <ul>
     * <li>render the diagram with two nodes (the first is unsynchronized, the second is synchronized)</li>
     * <li>create a new node thanks to a ViewCreationRequest during a refresh diagram: a new child nodes should be
     * visible on the first unsynchronized node children</li>
     * </ul>
     */
    @Test
    public void testUnsynchronizedRenderingOnNodeContainer() {
        DiagramDescription diagramDescription = this.getDiagramDescription();

        Diagram initialDiagram = this.render(diagramDescription, List.of(), Optional.empty());
        // @formatter:off
        ViewCreationRequest viewCreationRequest = ViewCreationRequest.newViewCreationRequest()
            .descriptionId(diagramDescription.getNodeDescriptions().get(0).getId())
            .parentElementId(initialDiagram.getId())
            .targetObjectId(TARGET_OBJECT_ID)
            .build();
        // @formatter:on
        Diagram diagramAfterFirstNodeCreation = this.render(diagramDescription, List.of(viewCreationRequest), Optional.of(initialDiagram));
        UUID descriptionId = diagramDescription.getNodeDescriptions().get(0).getChildNodeDescriptions().get(0).getId();
        UUID parentNodeId = diagramAfterFirstNodeCreation.getNodes().get(0).getId();
        // @formatter:off
        ViewCreationRequest childViewCreationRequest = ViewCreationRequest.newViewCreationRequest()
            .descriptionId(descriptionId)
            .parentElementId(parentNodeId)
            .targetObjectId(TARGET_OBJECT_ID)
            .build();
        // @formatter:on
        Diagram diagramAfterSecondNodeCreation = this.render(diagramDescription, List.of(childViewCreationRequest), Optional.of(diagramAfterFirstNodeCreation));

        assertThat(diagramAfterSecondNodeCreation.getNodes()).hasSize(2);
        Node firstRootNode = diagramAfterSecondNodeCreation.getNodes().get(0);
        assertThat(firstRootNode.getBorderNodes()).isEmpty();
        assertThat(firstRootNode.getChildNodes()).hasSize(1);

        Node secondRootNode = diagramAfterSecondNodeCreation.getNodes().get(1);
        assertThat(secondRootNode.getBorderNodes()).isEmpty();
        assertThat(secondRootNode.getChildNodes()).isEmpty();
    }

    private Diagram render(DiagramDescription diagramDescription, List<ViewCreationRequest> viewCreationRequests, Optional<Diagram> optionalPreviousDiagram) {
        VariableManager variableManager = new VariableManager();
        DiagramComponentProps props = new DiagramComponentProps(variableManager, diagramDescription, viewCreationRequests, optionalPreviousDiagram, Map.of(), Set.of(), Optional.empty());
        Element element = new Element(DiagramComponent.class, props);
        Diagram diagram = new DiagramRenderer(this.logger).render(element);
        return diagram;
    }

    private DiagramDescription getDiagramDescription() {
        // @formatter:off
        LabelStyleDescription labelStyleDescription = LabelStyleDescription.newLabelStyleDescription()
                .italicProvider(VariableManager -> true)
                .boldProvider(VariableManager -> true)
                .underlineProvider(VariableManager -> true)
                .strikeThroughProvider(VariableManager -> true)
                .colorProvider(VariableManager -> "#FFFFFF") //$NON-NLS-1$
                .fontSizeProvider(variableManager -> 10)
                .iconURLProvider(VariableManager -> "") //$NON-NLS-1$
                .build();

        LabelDescription labelDescription = LabelDescription.newLabelDescription("labelDescriptionId") //$NON-NLS-1$
                .idProvider(variableManager -> "labelid") //$NON-NLS-1$
                .textProvider(variableManager -> "label") //$NON-NLS-1$
                .styleDescription(labelStyleDescription)
                .build();

        Function<VariableManager, INodeStyle> styleProvider = variableManager -> {
            return RectangularNodeStyle.newRectangularNodeStyle()
                    .color("") //$NON-NLS-1$
                    .borderColor("") //$NON-NLS-1$
                    .borderSize(0)
                    .borderStyle(LineStyle.Solid)
                    .build();
        };

        NodeDescription subUnsynchronizedNodeDescription = NodeDescription.newNodeDescription(UUID.nameUUIDFromBytes("subUnsynchronized".getBytes())) //$NON-NLS-1$
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)
                .typeProvider(variableManager -> NODE_TYPE)
                .semanticElementsProvider(variableManager -> List.of(new Object()))
                .targetObjectIdProvider(variableManager -> TARGET_OBJECT_ID)
                .targetObjectKindProvider(variableManager -> "") //$NON-NLS-1$
                .targetObjectLabelProvider(variableManager -> "")//$NON-NLS-1$
                .labelDescription(labelDescription)
                .styleProvider(styleProvider)
                .borderNodeDescriptions(new ArrayList<>())
                .childNodeDescriptions(new ArrayList<>())
                .labelEditHandler((variableManager, newLabel) -> Status.OK)
                .deleteHandler(variableManager -> Status.OK)
                .build();
        NodeDescription unsynchronizedNodeDescription = NodeDescription.newNodeDescription(UUID.nameUUIDFromBytes("unsynchronized".getBytes())) //$NON-NLS-1$
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)
                .typeProvider(variableManager -> NODE_TYPE)
                .semanticElementsProvider(variableManager -> List.of(new Object()))
                .targetObjectIdProvider(variableManager -> TARGET_OBJECT_ID)
                .targetObjectKindProvider(variableManager -> "") //$NON-NLS-1$
                .targetObjectLabelProvider(variableManager -> "")//$NON-NLS-1$
                .labelDescription(labelDescription)
                .styleProvider(styleProvider)
                .borderNodeDescriptions(new ArrayList<>())
                .childNodeDescriptions(List.of(subUnsynchronizedNodeDescription))
                .labelEditHandler((variableManager, newLabel) -> Status.OK)
                .deleteHandler(variableManager -> Status.OK)
                .build();

        NodeDescription synchronizedNodeDescription = NodeDescription.newNodeDescription(UUID.nameUUIDFromBytes("synchronized".getBytes())) //$NON-NLS-1$
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .typeProvider(variableManager -> NODE_TYPE)
                .semanticElementsProvider(variableManager -> List.of(new Object()))
                .targetObjectIdProvider(variableManager -> TARGET_OBJECT_ID)
                .targetObjectKindProvider(variableManager -> "") //$NON-NLS-1$
                .targetObjectLabelProvider(variableManager -> "")//$NON-NLS-1$
                .labelDescription(labelDescription)
                .styleProvider(styleProvider)
                .borderNodeDescriptions(new ArrayList<>())
                .childNodeDescriptions(new ArrayList<>())
                .labelEditHandler((variableManager, newLabel) -> Status.OK)
                .deleteHandler(variableManager -> Status.OK)
                .build();

        DiagramDescription diagramDescription = DiagramDescription.newDiagramDescription(UUID.nameUUIDFromBytes("diagram".getBytes())) //$NON-NLS-1$
                .label("") //$NON-NLS-1$
                .canCreatePredicate(variableManager -> true)
                .targetObjectIdProvider(variableManager -> "diagramTargetObjectId") //$NON-NLS-1$
                .labelProvider(variableManager -> "label") //$NON-NLS-1$
                .nodeDescriptions(List.of(unsynchronizedNodeDescription, synchronizedNodeDescription))
                .edgeDescriptions(List.of())
                .toolSections(List.of())
                .build();
        // @formatter:on

        return diagramDescription;
    }
}
