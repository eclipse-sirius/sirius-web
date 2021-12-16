/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo and others.
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
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.diagrams.CustomizableProperties;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.diagrams.ImageNodeStyle;
import org.eclipse.sirius.web.diagrams.Label;
import org.eclipse.sirius.web.diagrams.LabelStyle;
import org.eclipse.sirius.web.diagrams.LineStyle;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.components.DiagramComponent;
import org.eclipse.sirius.web.diagrams.components.DiagramComponentProps;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.description.LabelDescription;
import org.eclipse.sirius.web.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.representations.Success;
import org.eclipse.sirius.web.representations.VariableManager;
import org.junit.jupiter.api.Test;

/**
 * Test cases for the rendering of the nodes in diagrams.
 *
 * @author sbegaudeau
 */
public class DiagramRendererNodeTests {

    private static final UUID NODE_DESCRIPTION_ID = UUID.randomUUID();

    private static final String LABEL_TEXT = "Node"; //$NON-NLS-1$

    private static final String LABEL_ID = "labelId"; //$NON-NLS-1$

    private static final int LABEL_FONT_SIZE = 40;

    private static final String LABEL_COLOR = "#AFAFAF"; //$NON-NLS-1$

    private static final String NODE_RECTANGULAR = "node:rectangular"; //$NON-NLS-1$

    private static final String NODE_IMAGE = "node:image"; //$NON-NLS-1$

    private static final String DIAGRAM_LABEL = "Diagram"; //$NON-NLS-1$

    /**
     * Creates a diagram with a single node.
     */
    @Test
    public void testSimpleNodeRendering() {
        Function<VariableManager, INodeStyle> styleProvider = variableManager -> {
            // @formatter:off
            return RectangularNodeStyle.newRectangularNodeStyle()
                    .color("") //$NON-NLS-1$
                    .borderColor("") //$NON-NLS-1$
                    .borderSize(0)
                    .borderStyle(LineStyle.Solid)
                    .build();
            // @formatter:on
        };
        Diagram diagram = this.createDiagram(styleProvider, variableManager -> NODE_RECTANGULAR, VariableManager -> Size.UNDEFINED, Optional.empty());

        assertThat(diagram).isNotNull();
        assertThat(diagram.getId()).asString().isNotBlank();
        assertThat(diagram.getTargetObjectId()).isNotBlank();

        assertThat(diagram.getNodes()).hasSize(1);
        assertThat(diagram.getNodes()).extracting(Node::getTargetObjectId).noneMatch(String::isBlank);
        assertThat(diagram.getNodes()).extracting(Node::getDescriptionId).noneMatch(t -> t.toString().isBlank());
        assertThat(diagram.getNodes()).extracting(Node::getType).noneMatch(String::isBlank);
        assertThat(diagram.getNodes()).extracting(Node::getType).allMatch(t -> NODE_RECTANGULAR.equals(t));
        assertThat(diagram.getNodes()).extracting(Node::getBorderNodes).allMatch(List::isEmpty);
        assertThat(diagram.getNodes()).extracting(Node::getStyle).allMatch(s -> s instanceof RectangularNodeStyle);
        assertThat(diagram.getNodes()).extracting(Node::getSize).allMatch(s -> s.getHeight() == -1 && s.getWidth() == -1);
        assertThat(diagram.getNodes()).extracting(Node::getLabel).extracting(Label::getId).allMatch(id -> UUID.nameUUIDFromBytes(LABEL_ID.getBytes()).toString().equals(id));
        assertThat(diagram.getNodes()).extracting(Node::getLabel).extracting(Label::getText).allMatch(text -> LABEL_TEXT.equals(text));
        assertThat(diagram.getNodes()).extracting(Node::getLabel).extracting(Label::getStyle).extracting(LabelStyle::getColor).allMatch(color -> LABEL_COLOR.equals(color));
        assertThat(diagram.getNodes()).extracting(Node::getLabel).extracting(Label::getStyle).extracting(LabelStyle::getFontSize).allMatch(size -> LABEL_FONT_SIZE == size);
        assertThat(diagram.getNodes()).extracting(Node::getLabel).extracting(Label::getStyle).extracting(LabelStyle::isBold).allMatch(bold -> bold);
        assertThat(diagram.getNodes()).extracting(Node::getLabel).extracting(Label::getStyle).extracting(LabelStyle::isItalic).allMatch(italic -> italic);
        assertThat(diagram.getNodes()).extracting(Node::getLabel).extracting(Label::getStyle).extracting(LabelStyle::isUnderline).allMatch(underline -> underline);
        assertThat(diagram.getNodes()).extracting(Node::getLabel).extracting(Label::getStyle).extracting(LabelStyle::isStrikeThrough).allMatch(strikeThrough -> strikeThrough);
    }

    @Test
    public void testSimpleNodeRenderingWithSizeProvider() {
        Function<VariableManager, INodeStyle> styleProvider = variableManager -> {
            // @formatter:off
            return RectangularNodeStyle.newRectangularNodeStyle()
                    .color("") //$NON-NLS-1$
                    .borderColor("") //$NON-NLS-1$
                    .borderSize(0)
                    .borderStyle(LineStyle.Solid)
                    .build();
            // @formatter:on
        };
        Diagram diagram = this.createDiagram(styleProvider, variableManager -> NODE_RECTANGULAR, VariableManager -> Size.of(10, 200), Optional.empty());

        assertThat(diagram).isNotNull();
        assertThat(diagram.getId()).asString().isNotBlank();
        assertThat(diagram.getTargetObjectId()).isNotBlank();

        assertThat(diagram.getNodes()).hasSize(1);
        assertThat(diagram.getNodes()).extracting(Node::getTargetObjectId).noneMatch(String::isBlank);
        assertThat(diagram.getNodes()).extracting(Node::getDescriptionId).noneMatch(t -> t.toString().isBlank());
        assertThat(diagram.getNodes()).extracting(Node::getType).noneMatch(String::isBlank);
        assertThat(diagram.getNodes()).extracting(Node::getType).allMatch(t -> NODE_RECTANGULAR.equals(t));
        assertThat(diagram.getNodes()).extracting(Node::getBorderNodes).allMatch(List::isEmpty);
        assertThat(diagram.getNodes()).extracting(Node::getStyle).allMatch(s -> s instanceof RectangularNodeStyle);
        assertThat(diagram.getNodes()).extracting(Node::getSize).allMatch(s -> s.getHeight() == 200 && s.getWidth() == 10);
        assertThat(diagram.getNodes()).extracting(Node::getLabel).extracting(Label::getId).allMatch(id -> UUID.nameUUIDFromBytes(LABEL_ID.getBytes()).toString().equals(id));
        assertThat(diagram.getNodes()).extracting(Node::getLabel).extracting(Label::getText).allMatch(text -> LABEL_TEXT.equals(text));
        assertThat(diagram.getNodes()).extracting(Node::getLabel).extracting(Label::getStyle).extracting(LabelStyle::getColor).allMatch(color -> LABEL_COLOR.equals(color));
        assertThat(diagram.getNodes()).extracting(Node::getLabel).extracting(Label::getStyle).extracting(LabelStyle::getFontSize).allMatch(size -> LABEL_FONT_SIZE == size);
        assertThat(diagram.getNodes()).extracting(Node::getLabel).extracting(Label::getStyle).extracting(LabelStyle::isBold).allMatch(bold -> bold);
        assertThat(diagram.getNodes()).extracting(Node::getLabel).extracting(Label::getStyle).extracting(LabelStyle::isItalic).allMatch(italic -> italic);
        assertThat(diagram.getNodes()).extracting(Node::getLabel).extracting(Label::getStyle).extracting(LabelStyle::isUnderline).allMatch(underline -> underline);
        assertThat(diagram.getNodes()).extracting(Node::getLabel).extracting(Label::getStyle).extracting(LabelStyle::isStrikeThrough).allMatch(strikeThrough -> strikeThrough);
    }

    @Test
    public void testSimpleNodeRenderingWithSizeProviderWithPreviousDiagram() {
        Function<VariableManager, INodeStyle> styleProvider = variableManager -> {
            // @formatter:off
            return RectangularNodeStyle.newRectangularNodeStyle()
                    .color("") //$NON-NLS-1$
                    .borderColor("") //$NON-NLS-1$
                    .borderSize(0)
                    .borderStyle(LineStyle.Solid)
                    .build();
            // @formatter:on
        };
        Diagram diagram = this.createDiagram(styleProvider, variableManager -> NODE_RECTANGULAR, VariableManager -> Size.of(10, 200), Optional.empty());

        // We modified the created diagram to change the node previous size.
        Node node = diagram.getNodes().get(0);

        // @formatter:off
        Node nodeWithNewSize = Node.newNode(node)
                .size(Size.of(50, 100))
                .build();
        Diagram newDiagram = Diagram.newDiagram(diagram)
                .nodes(List.of(nodeWithNewSize))
                .build();
        // @formatter:on

        diagram = this.createDiagram(styleProvider, variableManager -> NODE_RECTANGULAR, VariableManager -> Size.of(10, 200), Optional.of(newDiagram));

        // We check that the node size is still the one provided by the VSM
        assertThat(diagram.getNodes()).extracting(Node::getSize).allMatch(s -> s.getHeight() == 200 && s.getWidth() == 10);

        // This time, the size provider return 0,0. The NodeComponent should use the previous size in this case
        diagram = this.createDiagram(styleProvider, variableManager -> NODE_RECTANGULAR, VariableManager -> Size.of(0, 0), Optional.of(newDiagram));
        // We check that the node size is the new one set in new diagram
        assertThat(diagram.getNodes()).extracting(Node::getSize).allMatch(s -> s.getHeight() == 100 && s.getWidth() == 50);

        // @formatter:off
        nodeWithNewSize = Node.newNode(node)
                .size(Size.of(50, 100))
                .customizedProperties(Set.of(CustomizableProperties.Size))
                .build();
        newDiagram = Diagram.newDiagram(diagram)
                .nodes(List.of(nodeWithNewSize))
                .build();
        // @formatter:on
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
            // @formatter:off
            return ImageNodeStyle.newImageNodeStyle()
                    .imageURL("test") //$NON-NLS-1$
                    .scalingFactor(1)
                    .build();
            // @formatter:on
        };
        Diagram diagram = this.createDiagram(styleProvider, variableManager -> NODE_IMAGE, VariableManager -> Size.UNDEFINED, Optional.empty());

        assertThat(diagram).isNotNull();
        assertThat(diagram.getId()).asString().isNotBlank();
        assertThat(diagram.getTargetObjectId()).isNotBlank();

        assertThat(diagram.getNodes()).hasSize(1);
        assertThat(diagram.getNodes()).extracting(Node::getTargetObjectId).noneMatch(String::isBlank);
        assertThat(diagram.getNodes()).extracting(Node::getDescriptionId).noneMatch(t -> t.toString().isBlank());
        assertThat(diagram.getNodes()).extracting(Node::getType).noneMatch(String::isBlank);
        assertThat(diagram.getNodes()).extracting(Node::getType).allMatch(t -> NODE_IMAGE.equals(t));
        assertThat(diagram.getNodes()).extracting(Node::getBorderNodes).allMatch(List::isEmpty);
        assertThat(diagram.getNodes()).extracting(Node::getStyle).allMatch(s -> s instanceof ImageNodeStyle);
        // @formatter:off
        assertThat(diagram.getNodes())
            .extracting(n -> (ImageNodeStyle) n.getStyle())
            .extracting(ImageNodeStyle::getImageURL).noneMatch(String::isBlank);

        assertThat(diagram.getNodes())
            .extracting(n -> (ImageNodeStyle) n.getStyle())
            .extracting(ImageNodeStyle::getScalingFactor).allMatch(scalingFactor -> scalingFactor == 1);
        // @formatter:on
    }

    /**
     * Create a diagram with one element that match with the given styleProvider/typeProvider.
     */
    private Diagram createDiagram(Function<VariableManager, INodeStyle> styleProvider, Function<VariableManager, String> typeProvider, Function<VariableManager, Size> sizeProvider,
            Optional<Diagram> previousDiagram) {
        // @formatter:off
        LabelStyleDescription labelStyleDescription = LabelStyleDescription.newLabelStyleDescription()
                .italicProvider(VariableManager -> true)
                .boldProvider(VariableManager -> true)
                .underlineProvider(VariableManager -> true)
                .strikeThroughProvider(VariableManager -> true)
                .colorProvider(VariableManager -> LABEL_COLOR)
                .fontSizeProvider(variableManager -> LABEL_FONT_SIZE)
                .iconURLProvider(VariableManager -> "") //$NON-NLS-1$
                .build();

        LabelDescription labelDescription = LabelDescription.newLabelDescription("labelDescriptionId") //$NON-NLS-1$
                .idProvider(variableManager -> LABEL_ID)
                .textProvider(variableManager -> LABEL_TEXT)
                .styleDescriptionProvider(variableManager -> labelStyleDescription)
                .build();

        NodeDescription nodeDescription = NodeDescription.newNodeDescription(NODE_DESCRIPTION_ID)
                .typeProvider(typeProvider)
                .semanticElementsProvider(variableManager -> List.of(new Object()))
                .targetObjectIdProvider(variableManager -> "targetObjectId") //$NON-NLS-1$
                .targetObjectKindProvider(variableManager -> "") //$NON-NLS-1$
                .targetObjectLabelProvider(variableManager -> "")//$NON-NLS-1$
                .labelDescription(labelDescription)
                .styleProvider(styleProvider)
                .sizeProvider(sizeProvider)
                .borderNodeDescriptions(new ArrayList<>())
                .childNodeDescriptions(new ArrayList<>())
                .labelEditHandler((variableManager, newLabel) -> new Success())
                .deleteHandler(variableManager -> new Success())
                .build();

        DiagramDescription diagramDescription = DiagramDescription.newDiagramDescription(UUID.randomUUID())
                .label("") //$NON-NLS-1$
                .canCreatePredicate(variableManager -> true)
                .targetObjectIdProvider(variableManager -> "diagramTargetObjectId") //$NON-NLS-1$
                .labelProvider(variableManager -> DIAGRAM_LABEL)
                .nodeDescriptions(List.of(nodeDescription))
                .edgeDescriptions(new ArrayList<>())
                .toolSections(List.of())
                .build();
        // @formatter:on

        VariableManager variableManager = new VariableManager();
        // @formatter:off
        DiagramComponentProps props = DiagramComponentProps.newDiagramComponentProps()
                .variableManager(variableManager)
                .diagramDescription(diagramDescription)
                .viewCreationRequests(List.of())
                .viewDeletionRequests(List.of())
                .previousDiagram(previousDiagram)
                .build();
        // @formatter:on
        Element element = new Element(DiagramComponent.class, props);
        return new DiagramRenderer().render(element);
    }
}
