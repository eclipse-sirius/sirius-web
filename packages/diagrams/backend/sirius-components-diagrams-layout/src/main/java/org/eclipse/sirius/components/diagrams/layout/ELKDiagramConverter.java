/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.elk.alg.common.nodespacing.NodeLabelAndSizeCalculator;
import org.eclipse.elk.core.options.Alignment;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.EdgeLabelPlacement;
import org.eclipse.elk.graph.ElkConnectableShape;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.ElkGraphFactory;
import org.eclipse.elk.graph.ElkLabel;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.eclipse.elk.graph.properties.IProperty;
import org.eclipse.elk.graph.properties.Property;
import org.eclipse.elk.graph.util.ElkGraphUtil;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.ILayoutStrategy;
import org.eclipse.sirius.components.diagrams.ImageNodeStyle;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.ListLayoutStrategy;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.ParametricSVGNodeStyle;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.TextBounds;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ImageNodeStyleSizeProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ImageSizeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Used to convert the diagram into a graph of ELK objects. During the transformation, it will also pre-compute some
 * default sizes to help ELK perform the layout afterward.
 *
 * @author hmarchadour
 * @author sbegaudeau
 */
@Service
public class ELKDiagramConverter implements IELKDiagramConverter {

    public static final IProperty<String> PROPERTY_TYPE = new Property<>("org.eclipse.sirius.components.layout.type");

    public static final IProperty<Class<? extends ILayoutStrategy>> PROPERTY_CHILDREN_LAYOUT_STRATEGY = new Property<>("org.eclipse.sirius.components.layout.children.layout.strategy");

    /**
     * Indicates if the node has the CustomizableProperties.Size set, in which case ELK should consider the current size
     * to be fixed.
     */
    public static final IProperty<Boolean> PROPERTY_CUSTOM_SIZE = new Property<>("org.eclipse.sirius.components.layout.customSize");

    public static final String DEFAULT_DIAGRAM_TYPE = "graph";

    public static final String DEFAULT_IMAGE_TYPE = "image:inside-center";

    private final TextBoundsService textBoundsService;

    private final ImageSizeProvider imageSizeProvider;

    private final ImageNodeStyleSizeProvider imageNodeStyleSizeProvider;

    private final ELKPropertiesService elkPropertiesService;

    private final Logger logger = LoggerFactory.getLogger(ELKDiagramConverter.class);

    public ELKDiagramConverter(TextBoundsService textBoundsService, ImageSizeProvider imageSizeProvider, ELKPropertiesService elkPropertiesService) {
        this.textBoundsService = Objects.requireNonNull(textBoundsService);
        this.imageSizeProvider = Objects.requireNonNull(imageSizeProvider);
        this.imageNodeStyleSizeProvider = new ImageNodeStyleSizeProvider(this.imageSizeProvider);
        this.elkPropertiesService = Objects.requireNonNull(elkPropertiesService);
    }

    @Override
    public ELKConvertedDiagram convert(Diagram diagram, ISiriusWebLayoutConfigurator layoutConfigurator) {
        ElkNode elkDiagram = this.convertDiagram(diagram);

        Map<String, ElkGraphElement> id2ElkGraphElements = new HashMap<>();
        Map<String, ElkConnectableShape> connectableShapeIndex = new LinkedHashMap<>();
        diagram.getNodes().stream().forEach(node -> this.convertNode(node, elkDiagram, connectableShapeIndex, id2ElkGraphElements, layoutConfigurator));
        diagram.getEdges().stream().forEach(edge -> this.convertEdge(edge, elkDiagram, connectableShapeIndex, id2ElkGraphElements));

        return new ELKConvertedDiagram(elkDiagram, id2ElkGraphElements);
    }

    private ElkNode convertDiagram(Diagram diagram) {
        ElkNode elkDiagram = ElkGraphFactory.eINSTANCE.createElkNode();
        elkDiagram.setIdentifier(diagram.getId());
        elkDiagram.setProperty(PROPERTY_TYPE, DEFAULT_DIAGRAM_TYPE);
        return elkDiagram;
    }

    private void convertNode(Node node, ElkNode parent, Map<String, ElkConnectableShape> connectableShapeIndex, Map<String, ElkGraphElement> id2ElkGraphElements,
            ISiriusWebLayoutConfigurator layoutConfigurator) {
        ElkNode elkNode = ElkGraphFactory.eINSTANCE.createElkNode();
        elkNode.setIdentifier(node.getId());
        elkNode.setProperty(PROPERTY_TYPE, node.getType());
        if (node.getChildrenLayoutStrategy() != null) {
            elkNode.setProperty(PROPERTY_CHILDREN_LAYOUT_STRATEGY, node.getChildrenLayoutStrategy().getClass());
        }

        TextBounds textBounds = null;
        Label label = node.getLabel();
        String labelType;
        if (node.isBorderNode()) {
            labelType = this.elkPropertiesService.getBorderNodeLabelType(node, layoutConfigurator);
        } else {
            labelType = this.elkPropertiesService.getNodeLabelType(node, layoutConfigurator);
        }
        if (labelType.startsWith("label:inside-v")) {
            double maxPadding = this.elkPropertiesService.getMaxPadding(node, layoutConfigurator);
            textBounds = this.textBoundsService.getAutoWrapBounds(label, node.getSize().getWidth() - maxPadding * 2);
            elkNode.setDimensions(node.getSize().getWidth(), node.getSize().getHeight());
        } else {
            textBounds = this.textBoundsService.getBounds(label);
            double width = textBounds.getSize().getWidth();
            double height = textBounds.getSize().getHeight();
            elkNode.setDimensions(width, height);
        }

        if (node.getStyle() instanceof RectangularNodeStyle || node.getStyle() instanceof ParametricSVGNodeStyle || node.getStyle() instanceof ImageNodeStyle) {
            elkNode.setProperty(PROPERTY_CUSTOM_SIZE, true);
            Size currentSize = node.getSize();
            double width = Math.max(elkNode.getWidth(), currentSize.getWidth());
            double height = Math.max(elkNode.getHeight(), currentSize.getHeight());
            elkNode.setDimensions(width, height);
        }

        if (ListLayoutStrategy.class.equals(parent.getProperty(PROPERTY_CHILDREN_LAYOUT_STRATEGY))) {
            elkNode.setProperty(CoreOptions.ALIGNMENT, Alignment.LEFT);
        }

        elkNode.setParent(parent);
        connectableShapeIndex.put(elkNode.getIdentifier(), elkNode);
        if (node.getState() == ViewModifier.Hidden) {
            elkNode.setProperty(CoreOptions.NO_LAYOUT, true);
        }

        if (node.getStyle() instanceof ImageNodeStyle) {
            ElkNode elkImage = ElkGraphFactory.eINSTANCE.createElkNode();
            elkImage.setIdentifier(node.getId() + "_image");
            elkImage.setProperty(PROPERTY_TYPE, DEFAULT_IMAGE_TYPE);
            elkImage.setDimensions(elkNode.getWidth(), elkNode.getHeight());
            elkImage.setParent(elkNode);
        }

        node.getBorderNodes().stream().forEach(borderNode -> this.convertBorderNode(borderNode, elkNode, connectableShapeIndex, id2ElkGraphElements));
        node.getChildNodes().stream().forEach(childNode -> this.convertNode(childNode, elkNode, connectableShapeIndex, id2ElkGraphElements, layoutConfigurator));

        boolean hasHeader = this.elkPropertiesService.hasHeader(node);
        this.convertLabel(label, textBounds, elkNode, id2ElkGraphElements, hasHeader, null);

        id2ElkGraphElements.put(node.getId(), elkNode);
    }

    private void convertBorderNode(Node borderNode, ElkNode elkNode, Map<String, ElkConnectableShape> connectableShapeIndex, Map<String, ElkGraphElement> id2ElkGraphElements) {
        ElkPort elkPort = ElkGraphFactory.eINSTANCE.createElkPort();
        elkPort.setIdentifier(borderNode.getId());
        elkPort.setProperty(PROPERTY_TYPE, borderNode.getType());
        if (borderNode.getChildrenLayoutStrategy() != null) {
            elkPort.setProperty(PROPERTY_CHILDREN_LAYOUT_STRATEGY, borderNode.getChildrenLayoutStrategy().getClass());
        }

        TextBounds textBounds = this.textBoundsService.getBounds(borderNode.getLabel());
        double width = borderNode.getSize().getWidth();
        double height = borderNode.getSize().getHeight();
        elkPort.setDimensions(width, height);

        if (borderNode.getStyle() instanceof RectangularNodeStyle || borderNode.getStyle() instanceof ParametricSVGNodeStyle || borderNode.getStyle() instanceof ImageNodeStyle) {
            elkNode.setProperty(PROPERTY_CUSTOM_SIZE, true);
            Size currentSize = borderNode.getSize();
            double portWidth = Math.max(elkPort.getWidth(), currentSize.getWidth());
            double portHeight = Math.max(elkPort.getHeight(), currentSize.getHeight());
            elkPort.setDimensions(portWidth, portHeight);
        }

        elkPort.setParent(elkNode);
        if (borderNode.getState() == ViewModifier.Hidden) {
            elkNode.setProperty(CoreOptions.NO_LAYOUT, true);
        }

        connectableShapeIndex.put(elkPort.getIdentifier(), elkPort);

        boolean hasHeader = this.elkPropertiesService.hasHeader(borderNode);
        this.convertLabel(borderNode.getLabel(), textBounds, elkPort, id2ElkGraphElements, hasHeader, null);

        id2ElkGraphElements.put(borderNode.getId(), elkPort);
    }

    private void convertLabel(Label label, TextBounds textBounds, ElkGraphElement elkGraphElement, Map<String, ElkGraphElement> id2ElkGraphElements, boolean isInsideHeader,
            EdgeLabelPlacement placement) {
        ElkLabel elkLabel = ElkGraphFactory.eINSTANCE.createElkLabel();
        elkLabel.setIdentifier(label.getId());
        elkLabel.setProperty(PROPERTY_TYPE, label.getType());
        elkLabel.setDimensions(textBounds.getSize().getWidth(), textBounds.getSize().getHeight());

        this.handleElkLabel(label, elkLabel, isInsideHeader);

        elkLabel.setParent(elkGraphElement);

        if (placement != null) {
            elkLabel.setProperty(CoreOptions.EDGE_LABELS_PLACEMENT, placement);
        }

        elkLabel.eAdapters().add(new AlignmentHolder(textBounds.getAlignment()));

        id2ElkGraphElements.put(label.getId(), elkLabel);
    }

    /**
     * Decides if the label will be displayed or not, taken into account or not.
     *
     * We have three cases to handle:
     * <ul>
     * <li>The sirius-components label is empty but it has an icon or is inside a header: some space will be reserved
     * for the icon or the header.</li>
     * <li>The sirius-components label is empty and does not have icon nor is inside a header: the label will not be
     * displayed.</li>
     * <li>The sirius-components label has some text: the label will be displayed.</li>
     * </ul>
     *
     * <p>
     * Note: For the first two points, Elk will reserve some space for any Elk label that exists (even if the text is
     * empty). The Elk label height or width (of a node) will be used as padding to position its node children (see
     * {@link NodeLabelAndSizeCalculator#computeInsideNodeLabelPadding()} called by ElkGraphImporter#createLGraph()).
     * That reminds me this method will not work the day we will support label on right or left position. Elk will not
     * display the label if the text is empty (ElkGraphImporter#transformNode() around L.855). So, to handle the first
     * point we add a whitespace to the Elk label to force Elk to "display" the label. For the second point because we
     * don't want the label to be taken into account at all, we force the Elk label dimension to (0,0).
     * </p>
     *
     * @param label
     *            The sirius-components label used to create the Elk label
     * @param elkLabel
     *            The Elk label created from the sirius-components label
     * @param isInsideHeader
     *            Whether the label is inside a header or not.
     */
    private void handleElkLabel(Label label, ElkLabel elkLabel, boolean isInsideHeader) {
        if (label.getText().isEmpty() && (!label.getStyle().getIconURL().isEmpty() || isInsideHeader)) {
            elkLabel.setText(" ");
        } else if (label.getText().isEmpty()) {
            // workaround to prevent an empty label to be considered by Elk.
            elkLabel.setDimensions(0, 0);
        } else {
            elkLabel.setText(label.getText());
        }
    }

    private void convertEdge(Edge edge, ElkNode elkDiagram, Map<String, ElkConnectableShape> connectableShapeIndex, Map<String, ElkGraphElement> id2ElkGraphElements) {
        ElkEdge elkEdge = ElkGraphFactory.eINSTANCE.createElkEdge();
        elkEdge.setIdentifier(edge.getId());

        ElkConnectableShape source = connectableShapeIndex.get(edge.getSourceId());
        ElkConnectableShape target = connectableShapeIndex.get(edge.getTargetId());

        if (edge.getState() == ViewModifier.Hidden) {
            elkEdge.setProperty(CoreOptions.NO_LAYOUT, true);
        }

        if (source != null) {
            elkEdge.getSources().add(source);
        } else {
            String pattern = "The source with the id {} has not been found";
            this.logger.warn(pattern, edge.getSourceId());
        }

        if (target != null) {
            elkEdge.getTargets().add(target);
        } else {
            String pattern = "The target with the id {} has not been found";
            this.logger.warn(pattern, edge.getTargetId());
        }

        if (source != null && target != null) {
            ElkNode container = ElkGraphUtil.findBestEdgeContainment(elkEdge);
            if (container != null) {
                elkEdge.setContainingNode(container);
            } else {
                elkEdge.setContainingNode(elkDiagram);
            }
        }

        Optional.ofNullable(edge.getBeginLabel()).ifPresent(label -> this.convertLabel(label, this.textBoundsService.getBounds(label), elkEdge, id2ElkGraphElements, false, EdgeLabelPlacement.TAIL));
        Optional.ofNullable(edge.getCenterLabel())
                .ifPresent(label -> this.convertLabel(label, this.textBoundsService.getBounds(label), elkEdge, id2ElkGraphElements, false, EdgeLabelPlacement.CENTER));
        Optional.ofNullable(edge.getEndLabel()).ifPresent(label -> this.convertLabel(label, this.textBoundsService.getBounds(label), elkEdge, id2ElkGraphElements, false, EdgeLabelPlacement.HEAD));

        id2ElkGraphElements.put(edge.getId(), elkEdge);
    }
}
