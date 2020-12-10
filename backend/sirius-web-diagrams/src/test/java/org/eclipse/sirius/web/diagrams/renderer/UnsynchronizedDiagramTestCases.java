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
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.diagrams.LineStyle;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.web.diagrams.Size;
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
     * <li>create manually a new node and refresh this new diagram: two nodes should be visible, the synchronized one
     * and the unsynchronized one</li>
     * </ul>
     */
    @Test
    public void testUnsynchronizedRendering() {
        DiagramDescription diagramDescription = this.getDiagramDescription();

        Diagram initialDiagram = this.render(diagramDescription, Optional.empty());
        assertThat(initialDiagram.getNodes()).hasSize(1);

        Diagram refreshedDiagram = this.render(diagramDescription, Optional.of(initialDiagram));
        assertThat(refreshedDiagram.getNodes()).hasSize(1);

        Node previousNode = initialDiagram.getNodes().get(0);
        Node newNode = this.createNode(diagramDescription, previousNode);

        // @formatter:off
        Diagram modifiedDiagram = Diagram.newDiagram(initialDiagram)
                .nodes(List.of(previousNode, newNode))
                .build();
        // @formatter:on

        Diagram refreshedDiagramAfterNodeCreation = this.render(diagramDescription, Optional.of(modifiedDiagram));
        assertThat(refreshedDiagramAfterNodeCreation.getNodes()).hasSize(2);
    }

    private Diagram render(DiagramDescription diagramDescription, Optional<Diagram> optionalPreviousDiagram) {
        VariableManager variableManager = new VariableManager();
        DiagramComponentProps props = new DiagramComponentProps(variableManager, diagramDescription, optionalPreviousDiagram);
        Element element = new Element(DiagramComponent.class, props);
        Diagram diagram = new DiagramRenderer(this.logger).render(element);
        return diagram;
    }

    private Node createNode(DiagramDescription diagramDescription, Node previousNode) {
        // @formatter:off
        return Node.newNode(UUID.randomUUID().toString())
                .type(NODE_TYPE)
                .targetObjectId(TARGET_OBJECT_ID)
                .targetObjectKind("") //$NON-NLS-1$
                .targetObjectLabel("") //$NON-NLS-1$
                .descriptionId(diagramDescription.getNodeDescriptions().get(0).getId())
                .borderNode(false)
                .label(previousNode.getLabel())
                .style(previousNode.getStyle())
                .position(Position.UNDEFINED)
                .size(Size.UNDEFINED)
                .borderNodes(List.of())
                .childNodes(List.of())
                .build();
        // @formatter:on
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

        NodeDescription unsynchronizedNodeDescription = NodeDescription.newNodeDescription(UUID.nameUUIDFromBytes("unsynchronized".getBytes())) //$NON-NLS-1$
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)
                .idProvider(variableManager -> UUID.randomUUID().toString())
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

        NodeDescription synchronizedNodeDescription = NodeDescription.newNodeDescription(UUID.nameUUIDFromBytes("synchronized".getBytes())) //$NON-NLS-1$
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .idProvider(variableManager -> UUID.randomUUID().toString())
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
                .idProvider(variableManager -> UUID.randomUUID())
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
