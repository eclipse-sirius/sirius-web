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
import org.eclipse.sirius.components.diagrams.InsideLabelLocation;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.ListLayoutStrategy;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.InsideLabelDescription;
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

    private static final String BLACK_COLOR = "black";

    private static final String NODE_LIST_ITEM_PREFIX = "listitem";

    private static final String NODE_LIST_PREFIX = "list";

    private static final String NODE_IMG_PREFIX = "img";

    private static final String NODE_RECT_PREFIX = "rect";

    private static final String NODE_NAME_SEPARATOR = ":";

    private final Function<VariableManager, Boolean> isHeaderProvider = variableManager -> {
        String prefix = variableManager.get(VariableManager.SELF, Element.class)
                .map(Element::getName)
                .map(name -> name.split(NODE_NAME_SEPARATOR))
                .filter(composedName -> composedName.length >= 2)
                .map(composedName -> composedName[0])
                .orElse("");
        return NODE_LIST_PREFIX.equals(prefix);
    };

    private final Function<VariableManager, INodeStyle> nodeStyleProvider = variableManager -> {
        String prefix = variableManager.get(VariableManager.SELF, Element.class)
                .map(Element::getName)
                .map(name -> name.split(NODE_NAME_SEPARATOR))
                .filter(composedName -> composedName.length >= 2)
                .map(composedName -> composedName[0])
                .orElse("");

        INodeStyle nodeStyle = null;
        switch (prefix) {
            case NODE_RECT_PREFIX:
                nodeStyle = RectangularNodeStyle.newRectangularNodeStyle()
                        .borderSize(1)
                        .borderRadius(3)
                        .borderStyle(LineStyle.Solid)
                        .color("#E5F5F8")
                        .borderColor("#33B0C3")
                        .build();
                break;
            case NODE_IMG_PREFIX:
                nodeStyle = ImageNodeStyle.newImageNodeStyle()
                        .imageURL("")
                        .scalingFactor(1)
                        .build();
                break;
            case NODE_LIST_PREFIX:
                nodeStyle = RectangularNodeStyle.newRectangularNodeStyle()
                        .borderColor(BLACK_COLOR)
                        .borderRadius(0)
                        .borderSize(1)
                        .borderStyle(LineStyle.Solid)
                        .color("white")
                        .build();
                break;
            case NODE_LIST_ITEM_PREFIX:
                nodeStyle = IconLabelNodeStyle.newIconLabelNodeStyle()
                        .backgroundColor("white")
                        .build();
                break;
            default:
                break;
        }

        return nodeStyle;
    };

    private final Function<VariableManager, String> nodeTypeProvider = variableManager -> {
        return variableManager.get(VariableManager.SELF, Element.class)
                .map(Element::getName)
                .map(name -> name.split(NODE_NAME_SEPARATOR))
                .filter(composedName -> composedName.length >= 2)
                .map(composedName -> composedName[0])
                .map(prefix -> {
                    String type = "";
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
                            type = "";
                            break;
                    }
                    return type;
                })
                .orElse("");
    };

    private final Function<VariableManager, ILayoutStrategy> childrenLayoutStrategyProvider = variableManager -> {
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
                            childrenLayoutStrategy = ListLayoutStrategy.newListLayoutStrategy().build();
                            break;
                        case NODE_LIST_ITEM_PREFIX:
                            break;
                        default:
                            break;
                    }
                    return childrenLayoutStrategy;
                })
                .orElse(null);
    };

    private final Function<VariableManager, String> labelProvider = variableManager -> {
        return variableManager.get(VariableManager.SELF, Element.class)
                .map(Element::getName)
                .map(name -> name.split(NODE_NAME_SEPARATOR))
                .filter(composedName -> composedName.length >= 2)
                .map(composedName -> composedName[1])
                .orElse("");
    };

    private final Function<VariableManager, List<?>> nodeSemanticElementProvider = variableManager -> {
        return variableManager.get(VariableManager.SELF, Element.class)
                .map(Element::getChildren)
                .orElse(List.of())
                .stream()
                .filter(element -> !element.getName().startsWith("edge:"))
                .toList();
    };

    private IObjectService objectService;

    private final Function<VariableManager, String> targetObjectIdProvider = variableManager -> {
        // @formatter:off
        return variableManager.get(VariableManager.SELF, Object.class)
                .map(this.objectService::getId)
                .orElse("");
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

            variableManager.get(VariableManager.SELF, Element.class)
                    .map(this.objectService::getId)
                    .ifPresent(id -> targetObjectIdToNodeId.computeIfAbsent(id, k -> UUID.randomUUID().toString()));

            Map<String, Element> idToElement = cache.getObjectToNodes().keySet().stream()
                    .filter(Element.class::isInstance)
                    .map(Element.class::cast)
                    .collect(Collectors.toMap(elem -> {
                        String id = this.objectService.getId(elem);
                        return targetObjectIdToNodeId.computeIfAbsent(id, k -> UUID.randomUUID().toString());
                    }, elem -> elem));

            return variableManager.get(VariableManager.SELF, Element.class)
                    .map(Element::getChildren)
                    .orElseGet(List::of)
                    .stream()
                    .filter(element -> element.getName().startsWith("edge:"))
                    .map(this.objectService::getId)
                    .map(targetObjectIdToNodeId::get)
                    .map(idToElement::get)
                    .map(cache::getElementsRepresenting)
                    .flatMap(Collection::stream)
                    .toList();
        };
    }

    public DiagramDescription getDefaultDiagramDescription(Diagram diagram) {
        LabelDescription labelDescription = this.getDefaultLabelDescription();
        InsideLabelDescription insideLabelDescription = this.getDefaultInsideLabelDescription();

        NodeDescription childDescription = this.getDefaultChildDescription(insideLabelDescription);

        NodeDescription nodeDescription = this.getDefaultNodeDescription(insideLabelDescription, childDescription);

        EdgeDescription edgeDescription = this.getDefaultEdgeDescription(diagram, labelDescription, nodeDescription);

        DiagramDescription diagramDescription = DiagramDescription.newDiagramDescription(TestLayoutDiagramBuilder.DIAGRAM_DESCRIPTION_ID)
                .label("")
                .autoLayout(false)
                .targetObjectIdProvider(this.targetObjectIdProvider)
                .canCreatePredicate(variableManager -> false)
                .labelProvider(variableManager -> variableManager.get(DiagramDescription.LABEL, String.class).orElse(""))
                .palettes(List.of())
                .nodeDescriptions(List.of(nodeDescription))
                .edgeDescriptions(List.of(edgeDescription))
                .dropHandler(variableManager -> new Failure(""))
                .build();

        return diagramDescription;
    }

    private EdgeDescription getDefaultEdgeDescription(Diagram diagram, LabelDescription labelDescription, NodeDescription nodeDescription) {
        List<String> nodeDescriptionIds = List.of(TestLayoutDiagramBuilder.NODE_DESCRIPTION_ID, TestLayoutDiagramBuilder.CHILD_NODE_DESCRIPTION_ID);

        return EdgeDescription.newEdgeDescription(TestLayoutDiagramBuilder.EDGE_DESCRIPTION_ID)
                .targetObjectIdProvider(this.targetObjectIdProvider)
                .targetObjectKindProvider(variableManager -> "")
                .targetObjectLabelProvider(this.labelProvider)
                .semanticElementsProvider(variableManager -> {
                    var optionalCache = variableManager.get(DiagramDescription.CACHE, DiagramRenderingCache.class);
                    if (optionalCache.isEmpty()) {
                        return List.of();
                    }
                    List<Object> objects = new ArrayList<>();
                    DiagramRenderingCache cache = optionalCache.get();
                    for (org.eclipse.sirius.components.representations.Element nodeElement : cache.getNodeToObject().keySet()) {
                        if (nodeElement.getProps() instanceof NodeElementProps props) {
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
                            .color("#002639")
                            .size(1)
                            .build();
                })
                .deleteHandler(variableManager -> new Failure(""))
                .labelEditHandler((variableManager, edgeLabelKind, newLabel) -> new Failure(""))
                .build();

    }

    private NodeDescription getDefaultNodeDescription(InsideLabelDescription insideLabelDescription, NodeDescription childDescription) {
        return NodeDescription.newNodeDescription(TestLayoutDiagramBuilder.NODE_DESCRIPTION_ID)
                .typeProvider(this.nodeTypeProvider)
                .targetObjectIdProvider(this.targetObjectIdProvider)
                .targetObjectKindProvider(variableManager -> "")
                .targetObjectLabelProvider(this.labelProvider)
                .semanticElementsProvider(this.nodeSemanticElementProvider)
                .insideLabelDescription(insideLabelDescription)
                .styleProvider(this.nodeStyleProvider)
                .childrenLayoutStrategyProvider(this.childrenLayoutStrategyProvider)
                .sizeProvider(variableManager -> Size.UNDEFINED)
                .collapsible(false)
                .borderNodeDescriptions(List.of())
                .childNodeDescriptions(List.of(childDescription))
                .deleteHandler(variableManager -> new Failure(""))
                .labelEditHandler((variableManager, newValue) -> new Failure(""))
                .build();
    }

    private NodeDescription getDefaultChildDescription(InsideLabelDescription insideLabelDescription) {
        return NodeDescription.newNodeDescription(TestLayoutDiagramBuilder.CHILD_NODE_DESCRIPTION_ID)
                .typeProvider(this.nodeTypeProvider)
                .targetObjectIdProvider(this.targetObjectIdProvider)
                .targetObjectKindProvider(variableManager -> "")
                .targetObjectLabelProvider(this.labelProvider)
                .semanticElementsProvider(this.nodeSemanticElementProvider)
                .insideLabelDescription(insideLabelDescription)
                .styleProvider(this.nodeStyleProvider)
                .childrenLayoutStrategyProvider(this.childrenLayoutStrategyProvider)
                .sizeProvider(variableManager -> Size.UNDEFINED)
                .borderNodeDescriptions(List.of()).childNodeDescriptions(List.of())
                .deleteHandler(variableManager -> new Failure(""))
                .labelEditHandler((variableManager, newValue) -> new Failure(""))
                .build();
    }

    private LabelDescription getDefaultLabelDescription() {
        return LabelDescription.newLabelDescription(UUID.randomUUID().toString())
                .idProvider(variableManager -> "")
                .textProvider(this.labelProvider)
                .styleDescriptionProvider(variableManager -> {
                    return LabelStyleDescription.newLabelStyleDescription()
                            .boldProvider(vm -> false)
                            .italicProvider(vm -> false)
                            .underlineProvider(vm -> false)
                            .strikeThroughProvider(vm -> false)
                            .colorProvider(vm -> BLACK_COLOR)
                            .fontSizeProvider(vm -> 14)
                            .iconURLProvider(vm -> List.of())
                            .build();
                })
                .build();
    }

    private InsideLabelDescription getDefaultInsideLabelDescription() {
        return InsideLabelDescription.newInsideLabelDescription(UUID.randomUUID().toString())
                .idProvider(variableManager -> "")
                .textProvider(this.labelProvider)
                .styleDescriptionProvider(variableManager -> {
                    return LabelStyleDescription.newLabelStyleDescription()
                            .boldProvider(vm -> false)
                            .italicProvider(vm -> false)
                            .underlineProvider(vm -> false)
                            .strikeThroughProvider(vm -> false)
                            .colorProvider(vm -> BLACK_COLOR)
                            .fontSizeProvider(vm -> 14)
                            .iconURLProvider(vm -> List.of())
                            .build();
                })
                .isHeaderProvider(this.isHeaderProvider)
                .displayHeaderSeparatorProvider(this.isHeaderProvider)
                .insideLabelLocation(InsideLabelLocation.TOP_CENTER)
                .build();
    }

}
