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
package org.eclipse.sirius.web.diagrams.layout;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.PreDestroy;

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
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Edge;
import org.eclipse.sirius.web.diagrams.ImageNodeStyle;
import org.eclipse.sirius.web.diagrams.Label;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.TextBounds;
import org.eclipse.sirius.web.diagrams.layout.incremental.provider.ImageNodeStyleSizeProvider;
import org.eclipse.sirius.web.diagrams.layout.incremental.provider.ImageSizeProvider;
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
public class ELKDiagramConverter {

    public static final IProperty<String> PROPERTY_TYPE = new Property<>("org.eclipse.sirius.web.layout.type"); //$NON-NLS-1$

    public static final String DEFAULT_DIAGRAM_TYPE = "graph"; //$NON-NLS-1$

    public static final String DEFAULT_IMAGE_TYPE = "image:inside-center"; //$NON-NLS-1$

    private final TextBoundsService textBoundsService;

    private final ImageSizeProvider imageSizeProvider;

    private final ImageNodeStyleSizeProvider imageNodeStyleSizeProvider;

    private final Logger logger = LoggerFactory.getLogger(ELKDiagramConverter.class);

    public ELKDiagramConverter(TextBoundsService textBoundsService) {
        this.textBoundsService = Objects.requireNonNull(textBoundsService);
        this.imageSizeProvider = new ImageSizeProvider();
        this.imageNodeStyleSizeProvider = new ImageNodeStyleSizeProvider(this.imageSizeProvider);
    }

    public ELKConvertedDiagram convert(Diagram diagram) {
        ElkNode elkDiagram = this.convertDiagram(diagram);

        Map<String, ElkGraphElement> id2ElkGraphElements = new HashMap<>();
        Map<String, ElkConnectableShape> connectableShapeIndex = new LinkedHashMap<>();
        diagram.getNodes().stream().forEach(node -> this.convertNode(node, elkDiagram, connectableShapeIndex, id2ElkGraphElements));
        diagram.getEdges().stream().forEach(edge -> this.convertEdge(edge, elkDiagram, connectableShapeIndex, id2ElkGraphElements));

        return new ELKConvertedDiagram(elkDiagram, id2ElkGraphElements);
    }

    private ElkNode convertDiagram(Diagram diagram) {
        ElkNode elkDiagram = ElkGraphFactory.eINSTANCE.createElkNode();
        elkDiagram.setIdentifier(diagram.getId().toString());
        elkDiagram.setProperty(PROPERTY_TYPE, DEFAULT_DIAGRAM_TYPE);
        return elkDiagram;
    }

    private void convertNode(Node node, ElkNode parent, Map<String, ElkConnectableShape> connectableShapeIndex, Map<String, ElkGraphElement> id2ElkGraphElements) {
        ElkNode elkNode = ElkGraphFactory.eINSTANCE.createElkNode();
        elkNode.setIdentifier(node.getId().toString());
        elkNode.setProperty(PROPERTY_TYPE, node.getType());

        TextBounds textBounds = this.textBoundsService.getBounds(node.getLabel());
        double width = Math.max(textBounds.getSize().getWidth(), node.getSize().getWidth());
        double height = Math.max(textBounds.getSize().getHeight(), node.getSize().getHeight());

        elkNode.setDimensions(width, height);
        elkNode.setParent(parent);

        connectableShapeIndex.put(elkNode.getIdentifier(), elkNode);

        if (node.getStyle() instanceof ImageNodeStyle) {
            ImageNodeStyle imageNodeStyle = (ImageNodeStyle) node.getStyle();

            ElkNode elkImage = ElkGraphFactory.eINSTANCE.createElkNode();
            elkImage.setIdentifier(node.getId() + "_image"); //$NON-NLS-1$
            elkImage.setProperty(PROPERTY_TYPE, DEFAULT_IMAGE_TYPE);

            Size imageSize = this.imageNodeStyleSizeProvider.getSize(imageNodeStyle);
            elkImage.setDimensions(imageSize.getWidth(), imageSize.getHeight());

            elkImage.setParent(elkNode);
        }

        node.getBorderNodes().stream().forEach(borderNode -> this.convertBorderNode(borderNode, elkNode, connectableShapeIndex, id2ElkGraphElements));
        node.getChildNodes().stream().forEach(childNode -> this.convertNode(childNode, elkNode, connectableShapeIndex, id2ElkGraphElements));

        this.convertLabel(node.getLabel(), textBounds, elkNode, id2ElkGraphElements, null);

        id2ElkGraphElements.put(node.getId().toString(), elkNode);
    }

    private void convertBorderNode(Node borderNode, ElkNode elkNode, Map<String, ElkConnectableShape> connectableShapeIndex, Map<String, ElkGraphElement> id2ElkGraphElements) {
        ElkPort elkPort = ElkGraphFactory.eINSTANCE.createElkPort();
        elkPort.setIdentifier(borderNode.getId().toString());
        elkPort.setProperty(PROPERTY_TYPE, borderNode.getType());

        TextBounds textBounds = this.textBoundsService.getBounds(borderNode.getLabel());
        double width = Math.max(textBounds.getSize().getWidth(), borderNode.getSize().getWidth());
        double height = Math.max(textBounds.getSize().getHeight(), borderNode.getSize().getHeight());

        elkNode.setDimensions(width, height);
        elkPort.setParent(elkNode);

        connectableShapeIndex.put(elkPort.getIdentifier(), elkPort);

        if (borderNode.getStyle() instanceof ImageNodeStyle) {
            Size imageSize = this.imageNodeStyleSizeProvider.getSize((ImageNodeStyle) borderNode.getStyle());
            elkPort.setDimensions(imageSize.getWidth(), imageSize.getHeight());
        }

        this.convertLabel(borderNode.getLabel(), textBounds, elkPort, id2ElkGraphElements, null);

        id2ElkGraphElements.put(borderNode.getId().toString(), elkPort);
    }

    private void convertLabel(Label label, TextBounds textBounds, ElkGraphElement elkGraphElement, Map<String, ElkGraphElement> id2ElkGraphElements, EdgeLabelPlacement placement) {
        ElkLabel elkLabel = ElkGraphFactory.eINSTANCE.createElkLabel();
        elkLabel.setIdentifier(label.getId().toString());
        elkLabel.setProperty(PROPERTY_TYPE, label.getType());
        elkLabel.setDimensions(textBounds.getSize().getWidth(), textBounds.getSize().getHeight());

        if (label.getText().isEmpty() && !label.getStyle().getIconURL().isEmpty()) {
            elkLabel.setText(" "); //$NON-NLS-1$
        } else {
            elkLabel.setText(label.getText());
        }

        elkLabel.setParent(elkGraphElement);

        if (placement != null) {
            elkLabel.setProperty(CoreOptions.EDGE_LABELS_PLACEMENT, placement);
        }

        elkLabel.eAdapters().add(new AlignmentHolder(textBounds.getAlignment()));

        id2ElkGraphElements.put(label.getId().toString(), elkLabel);
    }

    private void convertEdge(Edge edge, ElkNode elkDiagram, Map<String, ElkConnectableShape> connectableShapeIndex, Map<String, ElkGraphElement> id2ElkGraphElements) {
        ElkEdge elkEdge = ElkGraphFactory.eINSTANCE.createElkEdge();
        elkEdge.setIdentifier(edge.getId().toString());

        ElkConnectableShape source = connectableShapeIndex.get(edge.getSourceId().toString());
        ElkConnectableShape target = connectableShapeIndex.get(edge.getTargetId().toString());

        if (source != null) {
            elkEdge.getSources().add(source);
        } else {
            String pattern = "The source with the id {} has not been found"; //$NON-NLS-1$
            this.logger.warn(pattern, edge.getSourceId());
        }

        if (target != null) {
            elkEdge.getTargets().add(target);
        } else {
            String pattern = "The target with the id {} has not been found"; //$NON-NLS-1$
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

        Optional.ofNullable(edge.getBeginLabel()).ifPresent(label -> this.convertLabel(label, this.textBoundsService.getBounds(label), elkEdge, id2ElkGraphElements, EdgeLabelPlacement.HEAD));
        Optional.ofNullable(edge.getCenterLabel()).ifPresent(label -> this.convertLabel(label, this.textBoundsService.getBounds(label), elkEdge, id2ElkGraphElements, EdgeLabelPlacement.CENTER));
        Optional.ofNullable(edge.getEndLabel()).ifPresent(label -> this.convertLabel(label, this.textBoundsService.getBounds(label), elkEdge, id2ElkGraphElements, EdgeLabelPlacement.TAIL));

        id2ElkGraphElements.put(edge.getId().toString(), elkEdge);
    }

    @PreDestroy
    private void dispose() {
        this.imageSizeProvider.dispose();
    }
}
