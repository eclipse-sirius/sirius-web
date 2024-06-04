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
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.sirius.components.diagrams.CustomizableProperties;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.ImageNodeStyle;
import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.InsideLabelLocation;
import org.eclipse.sirius.components.diagrams.LabelOverflowStrategy;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.LabelTextAlign;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.components.DiagramComponent;
import org.eclipse.sirius.components.diagrams.components.DiagramComponentProps;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.InsideLabelDescription;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IOperationValidator;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.junit.jupiter.api.Test;

/**
 * Test cases for the rendering of the nodes in diagrams.
 *
 * @author sbegaudeau
 */
public class DiagramRendererNodeTests {

    private static final String NODE_DESCRIPTION_ID = UUID.randomUUID().toString();

    private static final String LABEL_TEXT = "Node";

    private static final String INSIDE_LABEL_ID = "insideLabelId";

    private static final int LABEL_FONT_SIZE = 40;

    private static final String LABEL_COLOR = "#AFAFAF";

    private static final String NODE_RECTANGULAR = "node:rectangular";

    private static final String NODE_IMAGE = "node:image";

    private static final String DIAGRAM_LABEL = "Diagram";

    /**
     * Creates a diagram with a single node.
     */
    @Test
    public void testSimpleNodeRendering() {
        Function<VariableManager, INodeStyle> styleProvider = variableManager -> RectangularNodeStyle.newRectangularNodeStyle()
                .background("")
                .borderColor("")
                .borderSize(0)
                .borderStyle(LineStyle.Solid)
                .build();
        Diagram diagram = this.createDiagram(styleProvider, variableManager -> NODE_RECTANGULAR, VariableManager -> Size.UNDEFINED, Optional.empty());

        assertThat(diagram).isNotNull();
        assertThat(diagram.getId()).asString().isNotBlank();
        assertThat(diagram.getLabel()).isEqualTo(DIAGRAM_LABEL);
        assertThat(diagram.getTargetObjectId()).isNotBlank();

        assertThat(diagram.getNodes()).hasSize(1);
        assertThat(diagram.getNodes()).extracting(Node::getTargetObjectId).noneMatch(String::isBlank);
        assertThat(diagram.getNodes()).extracting(Node::getDescriptionId).noneMatch(String::isBlank);
        assertThat(diagram.getNodes()).extracting(Node::getType).noneMatch(String::isBlank);
        assertThat(diagram.getNodes()).extracting(Node::getType).allMatch(NODE_RECTANGULAR::equals);
        assertThat(diagram.getNodes()).extracting(Node::getBorderNodes).allMatch(List::isEmpty);
        assertThat(diagram.getNodes()).extracting(Node::getStyle).allMatch(s -> s instanceof RectangularNodeStyle);
        assertThat(diagram.getNodes()).extracting(Node::getSize).allMatch(s -> s.getHeight() == -1 && s.getWidth() == -1);
        assertThat(diagram.getNodes()).extracting(Node::getInsideLabel).extracting(InsideLabel::getId).allMatch(id -> UUID.nameUUIDFromBytes(INSIDE_LABEL_ID.getBytes()).toString().equals(id));
        assertThat(diagram.getNodes()).extracting(Node::getInsideLabel).extracting(InsideLabel::getText).allMatch(LABEL_TEXT::equals);
        assertThat(diagram.getNodes()).extracting(Node::getInsideLabel).extracting(InsideLabel::getStyle).extracting(LabelStyle::getColor).allMatch(LABEL_COLOR::equals);
        assertThat(diagram.getNodes()).extracting(Node::getInsideLabel).extracting(InsideLabel::getStyle).extracting(LabelStyle::getFontSize).allMatch(size -> LABEL_FONT_SIZE == size);
        assertThat(diagram.getNodes()).extracting(Node::getInsideLabel).extracting(InsideLabel::getStyle).extracting(LabelStyle::isBold).allMatch(bold -> bold);
        assertThat(diagram.getNodes()).extracting(Node::getInsideLabel).extracting(InsideLabel::getStyle).extracting(LabelStyle::isItalic).allMatch(italic -> italic);
        assertThat(diagram.getNodes()).extracting(Node::getInsideLabel).extracting(InsideLabel::getStyle).extracting(LabelStyle::isUnderline).allMatch(underline -> underline);
        assertThat(diagram.getNodes()).extracting(Node::getInsideLabel).extracting(InsideLabel::getStyle).extracting(LabelStyle::isStrikeThrough).allMatch(strikeThrough -> strikeThrough);
    }

    @Test
    public void testSimpleNodeRenderingWithSizeProvider() {
        Function<VariableManager, INodeStyle> styleProvider = variableManager -> RectangularNodeStyle.newRectangularNodeStyle()
                .background("")
                .borderColor("")
                .borderSize(0)
                .borderStyle(LineStyle.Solid)
                .build();
        Diagram diagram = this.createDiagram(styleProvider, variableManager -> NODE_RECTANGULAR, VariableManager -> Size.of(10, 200), Optional.empty());

        assertThat(diagram).isNotNull();
        assertThat(diagram.getId()).asString().isNotBlank();
        assertThat(diagram.getLabel()).isEqualTo(DIAGRAM_LABEL);
        assertThat(diagram.getTargetObjectId()).isNotBlank();

        assertThat(diagram.getNodes()).hasSize(1);
        assertThat(diagram.getNodes()).extracting(Node::getTargetObjectId).noneMatch(String::isBlank);
        assertThat(diagram.getNodes()).extracting(Node::getDescriptionId).noneMatch(String::isBlank);
        assertThat(diagram.getNodes()).extracting(Node::getType).noneMatch(String::isBlank);
        assertThat(diagram.getNodes()).extracting(Node::getType).allMatch(NODE_RECTANGULAR::equals);
        assertThat(diagram.getNodes()).extracting(Node::getBorderNodes).allMatch(List::isEmpty);
        assertThat(diagram.getNodes()).extracting(Node::getStyle).allMatch(s -> s instanceof RectangularNodeStyle);
        assertThat(diagram.getNodes()).extracting(Node::getSize).allMatch(s -> s.getHeight() == 200 && s.getWidth() == 10);
        assertThat(diagram.getNodes()).extracting(Node::getInsideLabel).extracting(InsideLabel::getId).allMatch(id -> UUID.nameUUIDFromBytes(INSIDE_LABEL_ID.getBytes()).toString().equals(id));
        assertThat(diagram.getNodes()).extracting(Node::getInsideLabel).extracting(InsideLabel::getText).allMatch(LABEL_TEXT::equals);
        assertThat(diagram.getNodes()).extracting(Node::getInsideLabel).extracting(InsideLabel::getStyle).extracting(LabelStyle::getColor).allMatch(LABEL_COLOR::equals);
        assertThat(diagram.getNodes()).extracting(Node::getInsideLabel).extracting(InsideLabel::getStyle).extracting(LabelStyle::getFontSize).allMatch(size -> LABEL_FONT_SIZE == size);
        assertThat(diagram.getNodes()).extracting(Node::getInsideLabel).extracting(InsideLabel::getStyle).extracting(LabelStyle::isBold).allMatch(bold -> bold);
        assertThat(diagram.getNodes()).extracting(Node::getInsideLabel).extracting(InsideLabel::getStyle).extracting(LabelStyle::isItalic).allMatch(italic -> italic);
        assertThat(diagram.getNodes()).extracting(Node::getInsideLabel).extracting(InsideLabel::getStyle).extracting(LabelStyle::isUnderline).allMatch(underline -> underline);
        assertThat(diagram.getNodes()).extracting(Node::getInsideLabel).extracting(InsideLabel::getStyle).extracting(LabelStyle::isStrikeThrough).allMatch(strikeThrough -> strikeThrough);
    }

    @Test
    public void testSimpleNodeRenderingWithSizeProviderWithPreviousDiagram() {
        Function<VariableManager, INodeStyle> styleProvider = variableManager -> RectangularNodeStyle.newRectangularNodeStyle()
                .background("")
                .borderColor("")
                .borderSize(0)
                .borderStyle(LineStyle.Solid)
                .build();
        Diagram diagram = this.createDiagram(styleProvider, variableManager -> NODE_RECTANGULAR, VariableManager -> Size.of(10, 200), Optional.empty());

        // We modified the created diagram to change the node previous size.
        Node node = diagram.getNodes().get(0);

        Node nodeWithNewSize = Node.newNode(node)
                .size(Size.of(50, 100))
                .build();
        Diagram newDiagram = Diagram.newDiagram(diagram)
                .nodes(List.of(nodeWithNewSize))
                .build();

        diagram = this.createDiagram(styleProvider, variableManager -> NODE_RECTANGULAR, VariableManager -> Size.of(10, 200), Optional.of(newDiagram));

        // We check that the node size is still the one provided by the VSM
        assertThat(diagram.getNodes()).extracting(Node::getSize).allMatch(s -> s.getHeight() == 200 && s.getWidth() == 10);

        // This time, the size provider return 0,0. The NodeComponent should use the previous size in this case
        diagram = this.createDiagram(styleProvider, variableManager -> NODE_RECTANGULAR, VariableManager -> Size.of(0, 0), Optional.of(newDiagram));
        // We check that the node size is the new one set in new diagram
        assertThat(diagram.getNodes()).extracting(Node::getSize).allMatch(s -> s.getHeight() == 100 && s.getWidth() == 50);

        nodeWithNewSize = Node.newNode(node)
                .size(Size.of(50, 100))
                .customizedProperties(Set.of(CustomizableProperties.Size))
                .build();
        newDiagram = Diagram.newDiagram(diagram)
                .nodes(List.of(nodeWithNewSize))
                .build();
        diagram = this.createDiagram(styleProvider, variableManager -> NODE_RECTANGULAR, VariableManager -> Size.of(10, 200), Optional.of(newDiagram));
        // We now check that we use the custom user size in priority over the VSM default one.
        assertThat(diagram.getNodes()).extracting(Node::getSize).allMatch(s -> s.getHeight() == 100 && s.getWidth() == 50);
    }

    /**
     * Creates a diagram with an image node.
     */
    @Test
    public void testImageNodeRendering() {
        Function<VariableManager, INodeStyle> styleProvider = variableManager -> {
            return ImageNodeStyle.newImageNodeStyle()
                    .imageURL("test")
                    .scalingFactor(1)
                    .build();
        };
        Diagram diagram = this.createDiagram(styleProvider, variableManager -> NODE_IMAGE, VariableManager -> Size.UNDEFINED, Optional.empty());

        assertThat(diagram).isNotNull();
        assertThat(diagram.getId()).asString().isNotBlank();
        assertThat(diagram.getLabel()).isEqualTo(DIAGRAM_LABEL);
        assertThat(diagram.getTargetObjectId()).isNotBlank();

        assertThat(diagram.getNodes()).hasSize(1);
        assertThat(diagram.getNodes()).extracting(Node::getTargetObjectId).noneMatch(String::isBlank);
        assertThat(diagram.getNodes()).extracting(Node::getDescriptionId).noneMatch(String::isBlank);
        assertThat(diagram.getNodes()).extracting(Node::getType).noneMatch(String::isBlank);
        assertThat(diagram.getNodes()).extracting(Node::getType).allMatch(NODE_IMAGE::equals);
        assertThat(diagram.getNodes()).extracting(Node::getBorderNodes).allMatch(List::isEmpty);
        assertThat(diagram.getNodes()).extracting(Node::getStyle).allMatch(s -> s instanceof ImageNodeStyle);

        assertThat(diagram.getNodes())
                .extracting(n -> (ImageNodeStyle) n.getStyle())
                .extracting(ImageNodeStyle::getImageURL).noneMatch(String::isBlank);

        assertThat(diagram.getNodes())
                .extracting(n -> (ImageNodeStyle) n.getStyle())
                .extracting(ImageNodeStyle::getScalingFactor).allMatch(scalingFactor -> scalingFactor == 1);
    }

    @Test
    public void testPinEventRendering() {
        Function<VariableManager, INodeStyle> styleProvider = variableManager -> RectangularNodeStyle.newRectangularNodeStyle()
                .background("")
                .borderColor("")
                .borderSize(0)
                .borderStyle(LineStyle.Solid)
                .build();

        Diagram diagram = this.createDiagram(styleProvider, variableManager -> NODE_RECTANGULAR, VariableManager -> Size.UNDEFINED, Optional.empty());
        assertThat(diagram).isNotNull();
        assertThat(diagram.getNodes().get(0).isPinned()).isEqualTo(false);

        Node nodePinned = Node.newNode(diagram.getNodes().get(0))
                .pinned(true)
                .build();
        Diagram newDiagram = Diagram.newDiagram(diagram)
                .nodes(List.of(nodePinned))
                .build();
        diagram = this.createDiagram(styleProvider, variableManager -> NODE_RECTANGULAR, VariableManager -> Size.UNDEFINED, Optional.of(newDiagram));
        assertThat(diagram.getNodes().get(0).isPinned()).isEqualTo(true);
    }

    /**
     * Create a diagram with one element that match with the given styleProvider/typeProvider.
     */
    private Diagram createDiagram(Function<VariableManager, INodeStyle> styleProvider, Function<VariableManager, String> typeProvider, Function<VariableManager, Size> sizeProvider,
            Optional<Diagram> previousDiagram) {
        LabelStyleDescription labelStyleDescription = LabelStyleDescription.newLabelStyleDescription()
                .italicProvider(VariableManager -> true)
                .boldProvider(VariableManager -> true)
                .underlineProvider(VariableManager -> true)
                .strikeThroughProvider(VariableManager -> true)
                .colorProvider(VariableManager -> LABEL_COLOR)
                .fontSizeProvider(variableManager -> LABEL_FONT_SIZE)
                .iconURLProvider(VariableManager -> List.of())
                .backgroundProvider(variableManager -> "transparent")
                .borderColorProvider(variableManager -> "black")
                .borderRadiusProvider(variableManager -> 0)
                .borderSizeProvider(variableManager -> 0)
                .borderStyleProvider(variableManager -> LineStyle.Solid)
                .build();

        InsideLabelDescription insideLbelDescription = InsideLabelDescription.newInsideLabelDescription("insideLabelDescriptionId")
                .idProvider(variableManager -> INSIDE_LABEL_ID)
                .textProvider(variableManager -> LABEL_TEXT)
                .styleDescriptionProvider(variableManager -> labelStyleDescription)
                .isHeaderProvider(vm -> false)
                .displayHeaderSeparatorProvider(vm -> false)
                .insideLabelLocation(InsideLabelLocation.TOP_CENTER)
                .overflowStrategy(LabelOverflowStrategy.NONE)
                .textAlign(LabelTextAlign.CENTER)
                .build();

        NodeDescription nodeDescription = NodeDescription.newNodeDescription(NODE_DESCRIPTION_ID)
                .typeProvider(typeProvider)
                .semanticElementsProvider(variableManager -> List.of(new Object()))
                .targetObjectIdProvider(variableManager -> "targetObjectId")
                .targetObjectKindProvider(variableManager -> "")
                .targetObjectLabelProvider(variableManager -> "")
                .insideLabelDescription(insideLbelDescription)
                .styleProvider(styleProvider)
                .childrenLayoutStrategyProvider(variableManager -> new FreeFormLayoutStrategy())
                .sizeProvider(sizeProvider)
                .borderNodeDescriptions(new ArrayList<>())
                .childNodeDescriptions(new ArrayList<>())
                .labelEditHandler((variableManager, newLabel) -> new Success())
                .deleteHandler(variableManager -> new Success())
                .build();

        DiagramDescription diagramDescription = DiagramDescription.newDiagramDescription(UUID.randomUUID().toString())
                .label("")
                .canCreatePredicate(variableManager -> true)
                .targetObjectIdProvider(variableManager -> "diagramTargetObjectId")
                .labelProvider(variableManager -> DIAGRAM_LABEL)
                .nodeDescriptions(List.of(nodeDescription))
                .edgeDescriptions(new ArrayList<>())
                .palettes(List.of())
                .dropHandler(variableManager -> new Failure(""))
                .build();

        VariableManager variableManager = new VariableManager();
        DiagramComponentProps props = DiagramComponentProps.newDiagramComponentProps()
                .variableManager(variableManager)
                .diagramDescription(diagramDescription)
                .allDiagramDescriptions(List.of(diagramDescription))
                .viewCreationRequests(List.of())
                .viewDeletionRequests(List.of())
                .previousDiagram(previousDiagram)
                .operationValidator(new IOperationValidator.NoOp())
                .diagramEvents(List.of())
                .build();
        Element element = new Element(DiagramComponent.class, props);
        return new DiagramRenderer().render(element);
    }
}
