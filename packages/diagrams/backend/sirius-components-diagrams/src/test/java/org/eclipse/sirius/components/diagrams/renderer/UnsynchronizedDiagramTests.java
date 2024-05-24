/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo and others.
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
package org.eclipse.sirius.components.diagrams.renderer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.ILayoutStrategy;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.InsideLabelLocation;
import org.eclipse.sirius.components.diagrams.LabelOverflowStrategy;
import org.eclipse.sirius.components.diagrams.LabelTextAlign;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.components.diagrams.components.DiagramComponent;
import org.eclipse.sirius.components.diagrams.components.DiagramComponentProps;
import org.eclipse.sirius.components.diagrams.components.NodeContainmentKind;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.InsideLabelDescription;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.description.SynchronizationPolicy;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IOperationValidator;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.junit.jupiter.api.Test;

/**
 * Test cases for the rendering of unsynchronized diagrams.
 *
 * @author sbegaudeau
 */
public class UnsynchronizedDiagramTests {

    private static final String NODE_TYPE = "node:rectangular";

    private static final String TARGET_OBJECT_ID = "targetObjectId";

    /**
     * This very simple test will validate that we can render synchronized diagram elements. We will use a diagram
     * description containing one synchronized node description and one unsynchronized node description. We will test
     * the following sequence of events:
     *
     * <ul>
     * <li>render the diagram from scratch: only the synchronized element should appear</li>
     * <li>refresh the diagram: nothing should change</li>
     * </ul>
     */
    @Test
    public void testRenderingOnRoot() {
        DiagramDescription diagramDescription = this.getDiagramDescription(variableManager -> List.of(new Object()));

        Diagram initialDiagram = this.render(diagramDescription, List.of(), List.of(), Optional.empty());
        assertThat(initialDiagram.getNodes()).hasSize(1);

        Diagram refreshedDiagram = this.render(diagramDescription, List.of(), List.of(), Optional.of(initialDiagram));
        assertThat(refreshedDiagram.getNodes()).hasSize(1);
    }

    /**
     * This very simple test will validate that we can render synchronized and unsynchronized diagram elements. We will
     * use a diagram description containing one synchronized node description and one unsynchronized node description.
     * We will test the following sequence of events:
     *
     * <ul>
     * <li>render the diagram from scratch: only the synchronized element should appear</li>
     * <li>create a ViewCreationRequest on a node and refresh this new diagram: two nodes should be visible, the
     * synchronized one and the unsynchronized one</li>
     * </ul>
     */
    @Test
    public void testUnsynchronizedRenderingOnRoot() {
        DiagramDescription diagramDescription = this.getDiagramDescription(variableManager -> List.of(new Object()));

        Diagram initialDiagram = this.render(diagramDescription, List.of(), List.of(), Optional.empty());

        ViewCreationRequest viewCreationRequest = ViewCreationRequest.newViewCreationRequest()
                .descriptionId(diagramDescription.getNodeDescriptions().get(0).getId())
                .parentElementId(initialDiagram.getId())
                .targetObjectId(TARGET_OBJECT_ID)
                .containmentKind(NodeContainmentKind.CHILD_NODE)
                .build();

        Diagram refreshedDiagramAfterNodeCreation = this.render(diagramDescription, List.of(viewCreationRequest), List.of(), Optional.of(initialDiagram));
        assertThat(refreshedDiagramAfterNodeCreation.getNodes()).hasSize(2);
    }

    /**
     * This very simple test will validate that we can render synchronized and unsynchronized diagram elements. We will
     * use a diagram description containing one synchronized node description and one unsynchronized node description.
     * We will test the following sequence of events:
     *
     * <ul>
     * <li>render the diagram from scratch: only the synchronized element should appear</li>
     * <li>create a ViewCreationRequest on a node and refresh this new diagram: two nodes should be visible, the
     * synchronized one and the unsynchronized one</li>
     * <li>create a ViewDeletionRequest on a node and refresh this new diagram: one node should be visible, the
     * synchronized one</li>
     * </ul>
     */
    @Test
    public void testUnsynchronizedRenderingOnRootAfterDeletion() {
        DiagramDescription diagramDescription = this.getDiagramDescription(variableManager -> List.of(new Object()));

        Diagram initialDiagram = this.render(diagramDescription, List.of(), List.of(), Optional.empty());

        ViewCreationRequest viewCreationRequest = ViewCreationRequest.newViewCreationRequest()
                .descriptionId(diagramDescription.getNodeDescriptions().get(0).getId())
                .parentElementId(initialDiagram.getId())
                .targetObjectId(TARGET_OBJECT_ID)
                .containmentKind(NodeContainmentKind.CHILD_NODE)
                .build();

        Diagram refreshedDiagramAfterNodeCreation = this.render(diagramDescription, List.of(viewCreationRequest), List.of(), Optional.of(initialDiagram));
        assertThat(refreshedDiagramAfterNodeCreation.getNodes()).hasSize(2);

        String nodeIdToDelete = refreshedDiagramAfterNodeCreation.getNodes().get(0).getId();

        ViewDeletionRequest viewDeletionRequest = ViewDeletionRequest.newViewDeletionRequest()
                .elementId(nodeIdToDelete)
                .build();
        Diagram refreshedDiagramAfterNodeDeletion = this.render(diagramDescription, List.of(), List.of(viewDeletionRequest), Optional.of(refreshedDiagramAfterNodeCreation));
        assertThat(refreshedDiagramAfterNodeDeletion.getNodes()).hasSize(1);

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
        Function<VariableManager, List<?>> semanticElementsProvider = new Function<>() {
            @Override
            public List<?> apply(VariableManager variableManager) {
                return List.of(new Object());
            }
        };

        DiagramDescription diagramDescription = this.getDiagramDescription(semanticElementsProvider);

        Diagram initialDiagram = this.render(diagramDescription, List.of(), List.of(), Optional.empty());

        ViewCreationRequest viewCreationRequest = ViewCreationRequest.newViewCreationRequest()
                .descriptionId(diagramDescription.getNodeDescriptions().get(0).getId())
                .parentElementId(initialDiagram.getId())
                .targetObjectId(TARGET_OBJECT_ID)
                .containmentKind(NodeContainmentKind.CHILD_NODE)
                .build();

        Diagram diagramAfterFirstNodeCreation = this.render(diagramDescription, List.of(viewCreationRequest), List.of(), Optional.of(initialDiagram));
        String descriptionId = diagramDescription.getNodeDescriptions().get(0).getChildNodeDescriptions().get(0).getId();
        String parentNodeId = diagramAfterFirstNodeCreation.getNodes().get(0).getId();

        ViewCreationRequest childViewCreationRequest = ViewCreationRequest.newViewCreationRequest()
                .descriptionId(descriptionId)
                .parentElementId(parentNodeId)
                .targetObjectId(TARGET_OBJECT_ID)
                .containmentKind(NodeContainmentKind.CHILD_NODE)
                .build();

        Diagram diagramAfterSecondNodeCreation = this.render(diagramDescription, List.of(childViewCreationRequest), List.of(), Optional.of(diagramAfterFirstNodeCreation));

        assertThat(diagramAfterSecondNodeCreation.getNodes()).hasSize(2);
        Node firstRootNode = diagramAfterSecondNodeCreation.getNodes().get(0);
        assertThat(firstRootNode.getBorderNodes()).isEmpty();
        assertThat(firstRootNode.getChildNodes()).hasSize(1);

        Node secondRootNode = diagramAfterSecondNodeCreation.getNodes().get(1);
        assertThat(secondRootNode.getBorderNodes()).isEmpty();
        assertThat(secondRootNode.getChildNodes()).isEmpty();
    }

    /**
     * This very simple test will validate that we can add and remove an unsynchronized node thanks to a
     * ViewCreationRequest/ViewDeletionRequest on a node container. We will test the following sequence of events:
     *
     * <ul>
     * <li>render the diagram with two nodes (the first is unsynchronized, the second is synchronized)</li>
     * <li>create a new node thanks to a ViewCreationRequest during a refresh diagram</li>
     * <li>delete the node thanks to a ViewDeletionRequest during a refresh diagram: no child nodes should be visible on
     * the first unsynchronized node children</li>
     * </ul>
     */
    @Test
    public void testUnsynchronizedRenderingOnNodeContainerAfterDeletion() {
        DiagramDescription diagramDescription = this.getDiagramDescription(variableManager -> List.of(new Object()));

        Diagram initialDiagram = this.render(diagramDescription, List.of(), List.of(), Optional.empty());

        ViewCreationRequest viewCreationRequest = ViewCreationRequest.newViewCreationRequest()
                .descriptionId(diagramDescription.getNodeDescriptions().get(0).getId())
                .parentElementId(initialDiagram.getId())
                .targetObjectId(TARGET_OBJECT_ID)
                .containmentKind(NodeContainmentKind.CHILD_NODE)
                .build();

        Diagram diagramAfterFirstNodeCreation = this.render(diagramDescription, List.of(viewCreationRequest), List.of(), Optional.of(initialDiagram));
        String descriptionId = diagramDescription.getNodeDescriptions().get(0).getChildNodeDescriptions().get(0).getId();
        String parentNodeId = diagramAfterFirstNodeCreation.getNodes().get(0).getId();

        ViewCreationRequest childViewCreationRequest = ViewCreationRequest.newViewCreationRequest()
                .descriptionId(descriptionId)
                .parentElementId(parentNodeId)
                .targetObjectId(TARGET_OBJECT_ID)
                .containmentKind(NodeContainmentKind.CHILD_NODE)
                .build();

        Diagram diagramAfterSecondNodeCreation = this.render(diagramDescription, List.of(childViewCreationRequest), List.of(), Optional.of(diagramAfterFirstNodeCreation));
        String nodeIdToDelete = diagramAfterSecondNodeCreation.getNodes().get(0).getChildNodes().get(0).getId();
        ViewDeletionRequest viewDeletionRequest = ViewDeletionRequest.newViewDeletionRequest()
                .elementId(nodeIdToDelete)
                .build();
        Diagram diagramAfterSecondNodeDeletion = this.render(diagramDescription, List.of(), List.of(viewDeletionRequest), Optional.of(diagramAfterFirstNodeCreation));

        assertThat(diagramAfterSecondNodeDeletion.getNodes()).hasSize(2);
        Node firstRootNode = diagramAfterSecondNodeDeletion.getNodes().get(0);
        assertThat(firstRootNode.getBorderNodes()).isEmpty();
        assertThat(firstRootNode.getChildNodes()).isEmpty();

        Node secondRootNode = diagramAfterSecondNodeCreation.getNodes().get(1);
        assertThat(secondRootNode.getBorderNodes()).isEmpty();
        assertThat(secondRootNode.getChildNodes()).isEmpty();
    }

    private Diagram render(DiagramDescription diagramDescription, List<ViewCreationRequest> viewCreationRequests, List<ViewDeletionRequest> viewDeletionRequests,
            Optional<Diagram> optionalPreviousDiagram) {
        VariableManager variableManager = new VariableManager();
        DiagramComponentProps props = DiagramComponentProps.newDiagramComponentProps()
                .variableManager(variableManager)
                .diagramDescription(diagramDescription)
                .allDiagramDescriptions(List.of(diagramDescription))
                .viewCreationRequests(viewCreationRequests)
                .viewDeletionRequests(viewDeletionRequests)
                .previousDiagram(optionalPreviousDiagram)
                .operationValidator(new IOperationValidator.NoOp())
                .diagramEvents(List.of())
                .build();
        Element element = new Element(DiagramComponent.class, props);
        Diagram diagram = new DiagramRenderer().render(element);
        return diagram;
    }

    private DiagramDescription getDiagramDescription(Function<VariableManager, List<?>> semanticElementsProvider) {
        LabelStyleDescription labelStyleDescription = LabelStyleDescription.newLabelStyleDescription()
                .italicProvider(VariableManager -> true)
                .boldProvider(VariableManager -> true)
                .underlineProvider(VariableManager -> true)
                .strikeThroughProvider(VariableManager -> true)
                .colorProvider(VariableManager -> "#FFFFFF")
                .fontSizeProvider(variableManager -> 10)
                .iconURLProvider(VariableManager -> List.of())
                .build();

        InsideLabelDescription insideLabelDescription = InsideLabelDescription.newInsideLabelDescription("insideLabelDescriptionId")
                .idProvider(variableManager -> "insideLabelId")
                .textProvider(variableManager -> "label")
                .styleDescriptionProvider(variableManager -> labelStyleDescription)
                .isHeaderProvider(vm -> false)
                .displayHeaderSeparatorProvider(vm -> false)
                .insideLabelLocation(InsideLabelLocation.TOP_CENTER)
                .overflowStrategy(LabelOverflowStrategy.NONE)
                .textAlign(LabelTextAlign.CENTER)
                .build();

        Function<VariableManager, INodeStyle> styleProvider = variableManager -> RectangularNodeStyle.newRectangularNodeStyle()
                .background("")
                .borderColor("")
                .borderSize(0)
                .borderStyle(LineStyle.Solid)
                .build();

        Function<VariableManager, ILayoutStrategy> childrenLayoutStrategyProvider = variableManager -> {
            return new FreeFormLayoutStrategy();
        };

        NodeDescription subUnsynchronizedNodeDescription = NodeDescription.newNodeDescription("subUnsynchronized")
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)
                .typeProvider(variableManager -> NODE_TYPE)
                .semanticElementsProvider(semanticElementsProvider)
                .targetObjectIdProvider(variableManager -> TARGET_OBJECT_ID)
                .targetObjectKindProvider(variableManager -> "")
                .targetObjectLabelProvider(variableManager -> "")
                .insideLabelDescription(insideLabelDescription)
                .styleProvider(styleProvider)
                .childrenLayoutStrategyProvider(childrenLayoutStrategyProvider)
                .sizeProvider(variableManager -> Size.UNDEFINED)
                .borderNodeDescriptions(new ArrayList<>())
                .childNodeDescriptions(new ArrayList<>())
                .labelEditHandler((variableManager, newLabel) -> new Success())
                .deleteHandler(variableManager -> new Success())
                .build();

        NodeDescription unsynchronizedNodeDescription = NodeDescription.newNodeDescription("unsynchronized")
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)
                .typeProvider(variableManager -> NODE_TYPE)
                .semanticElementsProvider(semanticElementsProvider)
                .targetObjectIdProvider(variableManager -> TARGET_OBJECT_ID)
                .targetObjectKindProvider(variableManager -> "")
                .targetObjectLabelProvider(variableManager -> "")
                .insideLabelDescription(insideLabelDescription)
                .styleProvider(styleProvider)
                .childrenLayoutStrategyProvider(childrenLayoutStrategyProvider)
                .sizeProvider(variableManager -> Size.UNDEFINED)
                .borderNodeDescriptions(new ArrayList<>())
                .childNodeDescriptions(List.of(subUnsynchronizedNodeDescription))
                .labelEditHandler((variableManager, newLabel) -> new Success())
                .deleteHandler(variableManager -> new Success())
                .build();

        NodeDescription synchronizedNodeDescription = NodeDescription.newNodeDescription("synchronized")
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .typeProvider(variableManager -> NODE_TYPE)
                .semanticElementsProvider(variableManager -> List.of(new Object()))
                .targetObjectIdProvider(variableManager -> TARGET_OBJECT_ID)
                .targetObjectKindProvider(variableManager -> "")
                .targetObjectLabelProvider(variableManager -> "")
                .insideLabelDescription(insideLabelDescription)
                .styleProvider(styleProvider)
                .childrenLayoutStrategyProvider(childrenLayoutStrategyProvider)
                .sizeProvider(variableManager -> Size.UNDEFINED)
                .borderNodeDescriptions(new ArrayList<>())
                .childNodeDescriptions(new ArrayList<>())
                .labelEditHandler((variableManager, newLabel) -> new Success())
                .deleteHandler(variableManager -> new Success())
                .build();

        DiagramDescription diagramDescription = DiagramDescription.newDiagramDescription("diagram")
                .label("")
                .canCreatePredicate(variableManager -> true)
                .targetObjectIdProvider(variableManager -> "diagramTargetObjectId")
                .labelProvider(variableManager -> "label")
                .nodeDescriptions(List.of(unsynchronizedNodeDescription, synchronizedNodeDescription))
                .edgeDescriptions(List.of())
                .palettes(List.of())
                .dropHandler(variableManager -> new Failure(""))
                .build();

        return diagramDescription;
    }
}
