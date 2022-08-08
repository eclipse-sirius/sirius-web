/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.ArrowStyle;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.ILayoutStrategy;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.IconLabelNodeStyle;
import org.eclipse.sirius.components.diagrams.ImageNodeStyle;
import org.eclipse.sirius.components.diagrams.LayoutDirection;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.ListLayoutStrategy;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.LabelDescription;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.components.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.components.diagrams.tests.builder.Element;
import org.eclipse.sirius.components.diagrams.tests.builder.TestLayoutDiagramBuilder;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Provide the default diagram description used by test.
 *
 * @author gcoutable
 */
public class DefaultTestDiagramDescriptionProvider {

    private static final String NODE_LIST_ITEM_PREFIX = "listitem"; //$NON-NLS-1$

    private static final String NODE_LIST_PREFIX = "list"; //$NON-NLS-1$

    private static final String NODE_IMG_PREFIX = "img"; //$NON-NLS-1$

    private static final String NODE_RECT_PREFIX = "rect"; //$NON-NLS-1$

    private static final String NODE_NAME_SEPARATOR = ":"; //$NON-NLS-1$

    private IObjectService objectService;

    private Function<VariableManager, String> targetObjectIdProvider = variableManager -> {
        // @formatter:off
        return variableManager.get(VariableManager.SELF, Object.class)
                .map(this.objectService::getId)
                .orElse(""); //$NON-NLS-1$
        // @formatter:on
    };

    private Function<VariableManager, INodeStyle> nodeStyleProvider = variableManager -> {
        // @formatter:off
        String prefix = variableManager.get(VariableManager.SELF, Element.class)
                .map(Element::getName)
                .map(name -> name.split(NODE_NAME_SEPARATOR))
                .filter(composedName -> composedName.length >= 2)
                .map(composedName -> composedName[0])
                .orElse(""); //$NON-NLS-1$
        // @formatter:on

        INodeStyle nodeStyle = null;
        switch (prefix) {
        case NODE_RECT_PREFIX:
            // @formatter:off
            nodeStyle = RectangularNodeStyle.newRectangularNodeStyle()
                    .withHeader(false)
                    .borderSize(1)
                    .borderRadius(3)
                    .borderStyle(LineStyle.Solid)
                    .color("#E5F5F8") //$NON-NLS-1$
                    .borderColor("#33B0C3") //$NON-NLS-1$
                    .build();
            // @formatter:on
            break;
        case NODE_IMG_PREFIX:
            // @formatter:off
            nodeStyle = ImageNodeStyle.newImageNodeStyle()
                    .imageURL("") //$NON-NLS-1$
                    .scalingFactor(1)
                    .build();
            // @formatter:on
            break;
        case NODE_LIST_PREFIX:
            // @formatter:off
            nodeStyle = RectangularNodeStyle.newRectangularNodeStyle()
                    .withHeader(true)
                    .borderColor("black") //$NON-NLS-1$
                    .borderRadius(0)
                    .borderSize(1)
                    .borderStyle(LineStyle.Solid)
                    .color("white") //$NON-NLS-1$
                    .build();
            // @formatter:on
            break;
        case NODE_LIST_ITEM_PREFIX:
            // @formatter:off
            nodeStyle = IconLabelNodeStyle.newIconLabelNodeStyle()
                    .backgroundColor("white") //$NON-NLS-1$
                    .build();
            // @formatter:on
            break;
        default:
            break;
        }

        return nodeStyle;
    };

    private Function<VariableManager, String> nodeTypeProvider = variableManager -> {
        // @formatter:off
        return variableManager.get(VariableManager.SELF, Element.class)
                .map(Element::getName)
                .map(name -> name.split(NODE_NAME_SEPARATOR))
                .filter(composedName -> composedName.length >= 2)
                .map(composedName -> composedName[0])
                .map(prefix -> {
                    String type = ""; //$NON-NLS-1$
                    switch (prefix) {
                    case NODE_RECT_PREFIX:
                        type = NodeType.NODE_RECTANGLE;
                        break;
                    case NODE_IMG_PREFIX:
                        type = NodeType.NODE_IMAGE;
                        break;
                    case NODE_LIST_PREFIX:
                        type = NodeType.NODE_RECTANGLE;
                        break;
                    case NODE_LIST_ITEM_PREFIX:
                        type = NodeType.NODE_ICON_LABEL;
                        break;
                    default:
                        type = ""; //$NON-NLS-1$
                        break;
                    }
                    return type;
                })
                .orElse(""); //$NON-NLS-1$
        // @formatter:on
    };

    private Function<VariableManager, ILayoutStrategy> childrenLayoutStrategyProvider = variableManager -> {
     // @formatter:off
        return variableManager.get(VariableManager.SELF, Element.class)
                .map(Element::getName)
                .map(name -> name.split(NODE_NAME_SEPARATOR))
                .filter(composedName -> composedName.length >= 2)
                .map(composedName -> composedName[0])
                .map(prefix -> {
                    ILayoutStrategy childrenLayoutStrategy = null;
                    switch (prefix) {
                    case NODE_RECT_PREFIX:
                        childrenLayoutStrategy = new FreeFormLayoutStrategy();
                        break;
                    case NODE_IMG_PREFIX:
                        break;
                    case NODE_LIST_PREFIX:
                        childrenLayoutStrategy = ListLayoutStrategy.newListLayoutStrategy().direction(LayoutDirection.COLUMN).build();
                        break;
                    case NODE_LIST_ITEM_PREFIX:
                        break;
                    default:
                        break;
                    }
                    return childrenLayoutStrategy;
                })
                .orElse(null);
        // @formatter:on
    };

    private Function<VariableManager, String> labelProvider = variableManager -> {
        // @formatter:off
        return variableManager.get(VariableManager.SELF, Element.class)
                .map(Element::getName)
                .map(name -> name.split(NODE_NAME_SEPARATOR))
                .filter(composedName -> composedName.length >= 2)
                .map(composedName -> composedName[1])
                .orElse(""); //$NON-NLS-1$
        // @formatter:on
    };

    private Function<VariableManager, List<?>> nodeSemanticElementProvider = variableManager -> {
        // @formatter:off
        List<Object> children = new ArrayList<>();
        variableManager.get(VariableManager.SELF, Element.class)
                .map(Element::getChildren)
                .orElse(List.of())
                .stream()
                .filter(element -> !element.getName().startsWith("edge:")) //$NON-NLS-1$
                .forEach(children::add);
        return children;
        // @formatter:on
    };

    public DefaultTestDiagramDescriptionProvider(IObjectService objectService) {
        this.objectService = Objects.requireNonNull(objectService);
    }

    private Map<String, String> getTargetObjectIdToNodeId(List<Node> nodes) {
        Map<String, String> targetObjectIdToNodeId = new HashMap<>();
        List<Node> deeperNode = new ArrayList<>();

        Iterator<Node> nodeIt = nodes.iterator();
        while (nodeIt.hasNext()) {
            Node node = nodeIt.next();
            targetObjectIdToNodeId.put(node.getTargetObjectId(), node.getId());
            deeperNode.addAll(node.getChildNodes());
            deeperNode.addAll(node.getBorderNodes());
        }

        if (!deeperNode.isEmpty()) {
            targetObjectIdToNodeId.putAll(this.getTargetObjectIdToNodeId(deeperNode));
        }
        return targetObjectIdToNodeId;
    }

    private Function<VariableManager, List<org.eclipse.sirius.components.representations.Element>> getEdgeTargetProvider(Diagram diagram) {
        Map<String, String> targetObjectIdToNodeId = this.getTargetObjectIdToNodeId(diagram.getNodes());

        return variableManager -> {
            var optionalCache = variableManager.get(DiagramDescription.CACHE, DiagramRenderingCache.class);
            if (optionalCache.isEmpty()) {
                return List.of();
            }
            DiagramRenderingCache cache = optionalCache.get();

            // @formatter:off
            variableManager.get(VariableManager.SELF, Element.class)
                    .map(this.objectService::getId)
                    .ifPresent(id -> targetObjectIdToNodeId.computeIfAbsent(id, k -> UUID.randomUUID().toString()));
            // @formatter:on

            // @formatter:off
            Map<String, Element> idToElement = cache.getObjectToNodes().keySet().stream()
                    .filter(Element.class::isInstance)
                    .map(Element.class::cast)
                    .collect(Collectors.toMap(elem -> {
                        String id = this.objectService.getId(elem);
                        return targetObjectIdToNodeId.computeIfAbsent(id, k -> UUID.randomUUID().toString());
                    }, elem -> elem));
            // @formatter:on

            // @formatter:off
            return variableManager.get(VariableManager.SELF, Element.class)
                .map(Element::getChildren)
                .orElseGet(List::of)
                .stream()
                .filter(element -> element.getName().startsWith("edge:")) //$NON-NLS-1$
                .map(this.objectService::getId)
                .map(targetObjectIdToNodeId::get)
                .map(idToElement::get)
                .map(cache::getElementsRepresenting)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
            // @formatter:on
        };
    }

    public DiagramDescription getDefaultDiagramDescription(Diagram diagram) {
        LabelDescription labelDescription = this.getDefaultLabelDescription();

        NodeDescription childDescription = this.getDefaultChildDescription(labelDescription);

        NodeDescription nodeDescription = this.getDefaultNodeDescription(labelDescription, childDescription);

        EdgeDescription edgeDescription = this.getDefaultEdgeDescription(diagram, labelDescription, nodeDescription);

        // @formatter:off
        DiagramDescription diagramDescription = DiagramDescription.newDiagramDescription(TestLayoutDiagramBuilder.DIAGRAM_DESCRIPTION_ID)
                 .label("") //$NON-NLS-1$
                 .autoLayout(false)
                 .targetObjectIdProvider(this.targetObjectIdProvider)
                 .canCreatePredicate(variableManager -> false)
                 .labelProvider(variableManager -> variableManager.get(DiagramDescription.LABEL, String.class).orElse(""))  //$NON-NLS-1$
                 .toolSections(List.of())
                 .nodeDescriptions(List.of(nodeDescription))
                 .edgeDescriptions(List.of(edgeDescription))
                 .dropHandler(variableManager -> new Failure("")) //$NON-NLS-1$
                 .build();
        // @formatter:on

        return diagramDescription;
    }

    private EdgeDescription getDefaultEdgeDescription(Diagram diagram, LabelDescription labelDescription, NodeDescription nodeDescription) {
        List<UUID> nodeDescriptionIds = List.of(TestLayoutDiagramBuilder.NODE_DESCRIPTION_ID, TestLayoutDiagramBuilder.CHILD_NODE_DESCRIPTION_ID);

        // @formatter:off
        return EdgeDescription.newEdgeDescription(TestLayoutDiagramBuilder.EDGE_DESCRIPTION_ID)
                .targetObjectIdProvider(this.targetObjectIdProvider)
                .targetObjectKindProvider(variableManager -> "") //$NON-NLS-1$
                .targetObjectLabelProvider(this.labelProvider)
                .semanticElementsProvider(variableManager -> {
                    var optionalCache = variableManager.get(DiagramDescription.CACHE, DiagramRenderingCache.class);
                    if (optionalCache.isEmpty()) {
                        return List.of();
                    }
                    List<Object> objects = new ArrayList<>();
                    DiagramRenderingCache cache = optionalCache.get();
                    for (org.eclipse.sirius.components.representations.Element nodeElement : cache.getNodeToObject().keySet()) {
                        if (nodeElement.getProps() instanceof NodeElementProps) {
                            NodeElementProps props = (NodeElementProps) nodeElement.getProps();
                            if (nodeDescriptionIds.contains(props.getDescriptionId())) {
                                Object object = cache.getNodeToObject().get(nodeElement);
                                objects.add(object);
                            }
                        }
                    }
                    return objects;
                })
                .beginLabelDescription(labelDescription)
                .centerLabelDescription(labelDescription)
                .endLabelDescription(labelDescription)
                .sourceNodeDescriptions(List.of(nodeDescription))
                .targetNodeDescriptions(List.of(nodeDescription))
                .sourceNodesProvider(variableManager -> {
                    var optionalObject = variableManager.get(VariableManager.SELF, Object.class);
                    var optionalCache = variableManager.get(DiagramDescription.CACHE, DiagramRenderingCache.class);
                    if (optionalObject.isEmpty() || optionalCache.isEmpty()) {
                        return List.of();
                    }

                    DiagramRenderingCache cache = optionalCache.get();
                    Object object = optionalObject.get();
                    return cache.getElementsRepresenting(object);
                })
                .targetNodesProvider(this.getEdgeTargetProvider(diagram))
                .styleProvider(variableManager -> {
                    return EdgeStyle.newEdgeStyle()
                            .lineStyle(LineStyle.Solid)
                            .sourceArrow(ArrowStyle.None)
                            .targetArrow(ArrowStyle.InputArrow)
                            .color("#002639") //$NON-NLS-1$
                            .size(1)
                            .build();
                })
                .deleteHandler(variableManager -> new Failure("")) //$NON-NLS-1$
                .labelEditHandler((variableManager, newValue) -> new Failure("")) //$NON-NLS-1$
                .build();
        // @formatter:on

    }

    private NodeDescription getDefaultNodeDescription(LabelDescription labelDescription, NodeDescription childDescription) {
        // @formatter:off
        return NodeDescription.newNodeDescription(TestLayoutDiagramBuilder.NODE_DESCRIPTION_ID)
                .typeProvider(this.nodeTypeProvider)
                .targetObjectIdProvider(this.targetObjectIdProvider)
                .targetObjectKindProvider(variableManager -> "") //$NON-NLS-1$
                .targetObjectLabelProvider(this.labelProvider)
                .semanticElementsProvider(this.nodeSemanticElementProvider)
                .labelDescription(labelDescription)
                .styleProvider(this.nodeStyleProvider)
                .childrenLayoutStrategyProvider(this.childrenLayoutStrategyProvider)
                .sizeProvider(variableManager -> Size.UNDEFINED)
                .borderNodeDescriptions(List.of())
                .childNodeDescriptions(List.of(childDescription))
                .deleteHandler(variableManager -> new Failure("")) //$NON-NLS-1$
                .labelEditHandler((variableManager, newValue) -> new Failure("")) //$NON-NLS-1$
                .build();
        // @formatter:on
    }

    private NodeDescription getDefaultChildDescription(LabelDescription labelDescription) {
        // @formatter:off
        return NodeDescription.newNodeDescription(TestLayoutDiagramBuilder.CHILD_NODE_DESCRIPTION_ID)
                .typeProvider(this.nodeTypeProvider)
                .targetObjectIdProvider(this.targetObjectIdProvider)
                .targetObjectKindProvider(variableManager -> "") //$NON-NLS-1$
                .targetObjectLabelProvider(this.labelProvider)
                .semanticElementsProvider(this.nodeSemanticElementProvider)
                .labelDescription(labelDescription)
                .styleProvider(this.nodeStyleProvider)
                .childrenLayoutStrategyProvider(this.childrenLayoutStrategyProvider)
                .sizeProvider(variableManager -> Size.UNDEFINED)
                .borderNodeDescriptions(List.of()).childNodeDescriptions(List.of())
                .deleteHandler(variableManager -> new Failure("")) //$NON-NLS-1$
                .labelEditHandler((variableManager, newValue) -> new Failure("")) //$NON-NLS-1$
                .build();
        // @formatter:on
    }

    private LabelDescription getDefaultLabelDescription() {
        // @formatter:off
        return  LabelDescription.newLabelDescription(UUID.randomUUID().toString())
                .idProvider(variableManager -> "") //$NON-NLS-1$
                .textProvider(this.labelProvider)
                .styleDescriptionProvider(variableManager -> {
                    return LabelStyleDescription.newLabelStyleDescription()
                            .boldProvider(vm -> false)
                            .italicProvider(vm -> false)
                            .underlineProvider(vm -> false)
                            .strikeThroughProvider(vm -> false)
                            .colorProvider(vm -> "black") //$NON-NLS-1$
                            .fontSizeProvider(vm -> 14)
                            .iconURLProvider(vm -> "") //$NON-NLS-1$
                            .build();
                })
                .build();
        // @formatter:on
    }

}
