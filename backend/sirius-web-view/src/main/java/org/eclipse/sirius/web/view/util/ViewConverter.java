/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.view.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.diagrams.ArrowStyle;
import org.eclipse.sirius.web.diagrams.EdgeStyle;
import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.diagrams.LineStyle;
import org.eclipse.sirius.web.diagrams.NodeType;
import org.eclipse.sirius.web.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.description.EdgeDescription;
import org.eclipse.sirius.web.diagrams.description.LabelDescription;
import org.eclipse.sirius.web.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.diagrams.description.SynchronizationPolicy;
import org.eclipse.sirius.web.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.web.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.interpreter.Result;
import org.eclipse.sirius.web.representations.Status;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.view.DiagramElementDescription;
import org.eclipse.sirius.web.view.Mode;
import org.eclipse.sirius.web.view.View;

/**
 * Converts a View into an equivalent list of {@link DiagramDescription}.
 *
 * @author pcdavid
 */
public class ViewConverter {
    private final AQLInterpreter interpreter;

    private final IObjectService objectService;

    // @formatter:off
    private final LabelStyleDescription defaultLabelStyle = LabelStyleDescription.newLabelStyleDescription()
            .colorProvider(variableManager -> "#000000") //$NON-NLS-1$
            .fontSizeProvider(variableManager -> 16)
            .boldProvider(variableManager -> false)
            .italicProvider(variableManager -> false)
            .underlineProvider(variableManager -> false)
            .strikeThroughProvider(variableManager -> false)
            .iconURLProvider(variableManager -> "") //$NON-NLS-1$
            .build();
    // @formatter:on

    private final Function<VariableManager, String> semanticTargetIdProvider;

    private final Function<VariableManager, String> semanticTargetKindProvider;

    private final Function<VariableManager, String> semanticTargetLabelProvider;

    // TODO: DiagramElementDescription should have a proper id.
    private final Function<DiagramElementDescription, UUID> idProvider = (diagramElementDescription) -> {
        return UUID.nameUUIDFromBytes(EcoreUtil.getURI(diagramElementDescription).toString().getBytes());
    };

    public ViewConverter(AQLInterpreter interpreter, IObjectService objectService) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.objectService = Objects.requireNonNull(objectService);
        this.semanticTargetIdProvider = variableManager -> this.self(variableManager).map(this.objectService::getId).orElse(null);
        this.semanticTargetKindProvider = variableManager -> this.self(variableManager).map(this.objectService::getKind).orElse(null);
        this.semanticTargetLabelProvider = variableManager -> this.self(variableManager).map(this.objectService::getLabel).orElse(null);
    }

    public List<DiagramDescription> convert(View view) {
        // @formatter:off
        return view.getDescriptions().stream()
                   .filter(org.eclipse.sirius.web.view.DiagramDescription.class::isInstance)
                   .map(org.eclipse.sirius.web.view.DiagramDescription.class::cast)
                   .map(this::convert)
                   .collect(Collectors.toList());
        // @formatter:on
    }

    public DiagramDescription convert(org.eclipse.sirius.web.view.DiagramDescription viewDiagramDescription) {
        Map<org.eclipse.sirius.web.view.NodeDescription, NodeDescription> convertedNodes = new HashMap<>();
        // @formatter:off
        List<NodeDescription> nodeDescriptions = viewDiagramDescription.getNodeDescriptions().stream()
                                                                       .map(subNodeDescription -> this.convert(subNodeDescription, convertedNodes))
                                                                       .collect(Collectors.toList());
        // @formatter:on
        List<EdgeDescription> edgeDescriptions = viewDiagramDescription.getEdgeDescriptions().stream().map(edgeDescription -> this.convert(edgeDescription, convertedNodes))
                .collect(Collectors.toList());
        // @formatter:off
        return DiagramDescription.newDiagramDescription(UUID.nameUUIDFromBytes(("Domain Diagram " + viewDiagramDescription.getName()).getBytes())) //$NON-NLS-1$
                .label(viewDiagramDescription.getName())
                .labelProvider(variableManager -> this.evaluateString(variableManager, viewDiagramDescription.getTitleExpression()))
                .canCreatePredicate(variableManager -> true)
                .targetObjectIdProvider(variableManager -> this.self(variableManager).map(this.objectService::getId).orElse("")) //$NON-NLS-1$
                .nodeDescriptions(nodeDescriptions)
                .edgeDescriptions(edgeDescriptions)
                .toolSections(List.of())
                .build();
        // @formatter:on
    }

    private Optional<Object> self(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class);
    }

    private NodeDescription convert(org.eclipse.sirius.web.view.NodeDescription viewNodeDescription, Map<org.eclipse.sirius.web.view.NodeDescription, NodeDescription> convertedNodes) {
        // @formatter:off
        var childNodeDescriptions = viewNodeDescription.getChildrenDescriptions().stream()
                                                       .map(subNodeDescription -> this.convert(viewNodeDescription, convertedNodes))
                                                       .collect(Collectors.toList());

        INodeStyle nodeStyle = RectangularNodeStyle.newRectangularNodeStyle()
                .color(viewNodeDescription.getStyle().getColor())
                .borderColor("rgb(0, 0, 0)") //$NON-NLS-1$
                .borderSize(1)
                .borderStyle(LineStyle.Solid)
                .build();

        NodeDescription result = NodeDescription.newNodeDescription(this.idProvider.apply(viewNodeDescription))
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .targetObjectKindProvider(this.semanticTargetKindProvider)
                .targetObjectLabelProvider(this.semanticTargetLabelProvider)
                .semanticElementsProvider(this.getSemanticElementsProvider(viewNodeDescription))
                .synchronizationPolicy(viewNodeDescription.getCreationMode() == Mode.AUTO ? SynchronizationPolicy.SYNCHRONIZED : SynchronizationPolicy.UNSYNCHRONIZED)
                .typeProvider(variableManager -> NodeType.NODE_RECTANGLE)
                .labelDescription(this.getLabelDescription(viewNodeDescription))
                .styleProvider(variableManager -> nodeStyle)
                .childNodeDescriptions(childNodeDescriptions)
                .borderNodeDescriptions(List.of())
                .labelEditHandler((variableManager, newLabel) -> Status.OK)
                .deleteHandler(variableManager -> Status.OK)
                .build();
        // @formatter:on
        convertedNodes.put(viewNodeDescription, result);
        return result;
    }

    private LabelDescription getLabelDescription(org.eclipse.sirius.web.view.NodeDescription viewNodeDescription) {
        Function<VariableManager, String> labelIdProvider = variableManager -> {
            Object parentId = variableManager.get(LabelDescription.OWNER_ID, Objects.class).orElse(null);
            return String.valueOf(parentId) + LabelDescription.LABEL_SUFFIX;
        };

        // @formatter:off
        return LabelDescription.newLabelDescription(EcoreUtil.getURI(viewNodeDescription).toString() + "_label") //$NON-NLS-1$
                .idProvider(labelIdProvider)
                .textProvider(variableManager -> this.evaluateString(variableManager, viewNodeDescription.getLabelExpression()))
                .styleDescriptionProvider(variableManager -> this.defaultLabelStyle)
                .build();
        // @formatter:on
    }

    private Function<VariableManager, List<Object>> getSemanticElementsProvider(org.eclipse.sirius.web.view.DiagramElementDescription elementDescription) {
        return variableManager -> {
            Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), elementDescription.getSemanticCandidatesExpression());
            List<Object> candidates = result.asObjects().orElse(List.of());
            // @formatter:off
            return candidates.stream()
                    .filter(EObject.class::isInstance)
                    .map(EObject.class::cast)
                    .filter(candidate -> candidate != null && candidate.eClass().getName().equals(elementDescription.getDomainType()))
                    .collect(Collectors.toList());
            // @formatter:on
        };
    }

    private EdgeDescription convert(org.eclipse.sirius.web.view.EdgeDescription viewEdgeDescription, Map<org.eclipse.sirius.web.view.NodeDescription, NodeDescription> convertedNodes) {

        Function<VariableManager, List<Object>> semanticElementsProvider;
        if (viewEdgeDescription.isIsDomainBasedEdge()) {
            // Same logic as for nodes.
            semanticElementsProvider = this.getSemanticElementsProvider(viewEdgeDescription);
        } else {
            //
            var sourceNodeDescriptions = viewEdgeDescription.getSourceNodeDescriptions().stream().map(convertedNodes::get);
            semanticElementsProvider = new RelationBasedSemanticElementsProvider(sourceNodeDescriptions.map(NodeDescription::getId).collect(Collectors.toList()));
        }

        Function<VariableManager, List<Element>> sourceNodesProvider = null;
        if (viewEdgeDescription.isIsDomainBasedEdge()) {
            sourceNodesProvider = variableManager -> {
                var optionalCache = variableManager.get(DiagramDescription.CACHE, DiagramRenderingCache.class);
                if (optionalCache.isEmpty()) {
                    return List.of();
                }
                DiagramRenderingCache cache = optionalCache.get();
                String sourceFinderExpression = viewEdgeDescription.getSourceNodesExpression();

                Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), sourceFinderExpression);
                List<Object> semanticCandidates = result.asObjects().orElse(List.of());
                var nodeCandidates = semanticCandidates.stream().flatMap(semanticObject -> cache.getElementsRepresenting(semanticObject).stream());

                // @formatter:off
                return nodeCandidates
                        .filter(nodeElement -> viewEdgeDescription.getSourceNodeDescriptions().stream().anyMatch(srcMapping -> this.isFromDescription(nodeElement, viewEdgeDescription)))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                // @formatter:on
            };
        } else {
            sourceNodesProvider = variableManager -> {
                List<UUID> sourceNodeDescriptionIds = viewEdgeDescription.getSourceNodeDescriptions().stream().map(this.idProvider).collect(Collectors.toList());

                var optionalObject = variableManager.get(VariableManager.SELF, Object.class);
                var optionalCache = variableManager.get(DiagramDescription.CACHE, DiagramRenderingCache.class);
                if (optionalObject.isEmpty() || optionalCache.isEmpty()) {
                    return List.of();
                }

                DiagramRenderingCache cache = optionalCache.get();
                Object object = optionalObject.get();

             // @formatter:off
                return cache.getElementsRepresenting(object).stream()
                        .filter(this.isFromCompatibleSourceMapping(viewEdgeDescription))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                // @formatter:on
            };
        }

        Function<VariableManager, List<Element>> targetNodesProvider = null;

        Function<VariableManager, EdgeStyle> styleProvider = variableManager -> {
            // @formatter:off
            return EdgeStyle.newEdgeStyle()
                    .color(viewEdgeDescription.getStyle().getColor())
                    .lineStyle(LineStyle.Solid)
                    .size(1)
                    .sourceArrow(ArrowStyle.None)
                    .targetArrow(ArrowStyle.OutputArrow)
                    .build();
            // @formatter:on
        };

        // @formatter:off
        return EdgeDescription.newEdgeDescription(this.idProvider.apply(viewEdgeDescription))
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .targetObjectKindProvider(this.semanticTargetKindProvider)
                .targetObjectLabelProvider(this.semanticTargetLabelProvider)
                .sourceNodeDescriptions(viewEdgeDescription.getSourceNodeDescriptions().stream().map(convertedNodes::get).collect(Collectors.toList()))
                .targetNodeDescriptions(viewEdgeDescription.getTargetNodeDescriptions().stream().map(convertedNodes::get).collect(Collectors.toList()))
                .semanticElementsProvider(semanticElementsProvider)
                .sourceNodesProvider(sourceNodesProvider)
                .targetNodesProvider(targetNodesProvider)
                .styleProvider(styleProvider)
                .deleteHandler(variableManager -> Status.OK)
                .build();
                // @formatter:on
    }

    private Predicate<Element> isFromCompatibleSourceMapping(org.eclipse.sirius.web.view.EdgeDescription edgeDescription) {
        return nodeElement -> {
            return edgeDescription.getSourceNodeDescriptions().stream().anyMatch(srcDescription -> this.isFromDescription(nodeElement, srcDescription));
        };
    }

    private String evaluateString(VariableManager variableManager, String expression) {
        return this.interpreter.evaluateExpression(variableManager.getVariables(), expression).asString().orElse(""); //$NON-NLS-1$
    }

    private boolean isFromDescription(Element nodeElement, DiagramElementDescription diagramElementDescription) {
        if (nodeElement.getProps() instanceof NodeElementProps) {
            NodeElementProps props = (NodeElementProps) nodeElement.getProps();
            return Objects.equals(EcoreUtil.getURI(diagramElementDescription), props.getDescriptionId().toString());
        }
        return false;
    }

    // Copied from org.eclipse.sirius.web.compat.diagrams.RelationBasedSemanticElementsProvider
    class RelationBasedSemanticElementsProvider implements Function<VariableManager, List<Object>> {

        private final List<UUID> sourceNodeDescriptionIds;

        public RelationBasedSemanticElementsProvider(List<UUID> sourceNodeDescriptionIds) {
            this.sourceNodeDescriptionIds = Objects.requireNonNull(sourceNodeDescriptionIds);
        }

        @Override
        public List<Object> apply(VariableManager variableManager) {
            List<Object> objects = new ArrayList<>();

            var optionalCache = variableManager.get(DiagramDescription.CACHE, DiagramRenderingCache.class);
            if (optionalCache.isEmpty()) {
                return List.of();
            }

            DiagramRenderingCache cache = optionalCache.get();
            for (Element nodeElement : cache.getNodeToObject().keySet()) {
                if (nodeElement.getProps() instanceof NodeElementProps) {
                    NodeElementProps props = (NodeElementProps) nodeElement.getProps();
                    if (this.sourceNodeDescriptionIds.contains(props.getDescriptionId())) {
                        Object object = cache.getNodeToObject().get(nodeElement);
                        objects.add(object);
                    }
                }
            }

            return objects;
        }

    }

    class TargetNodesProvider implements Function<VariableManager, List<Element>> {

        private final org.eclipse.sirius.web.view.EdgeDescription edgeMapping;

        private final AQLInterpreter interpreter;

        public TargetNodesProvider(org.eclipse.sirius.web.view.EdgeDescription edgeMapping, AQLInterpreter interpreter) {
            this.edgeMapping = Objects.requireNonNull(edgeMapping);
            this.interpreter = Objects.requireNonNull(interpreter);
        }

        @Override
        public List<Element> apply(VariableManager variableManager) {

            var optionalCache = variableManager.get(DiagramDescription.CACHE, DiagramRenderingCache.class);
            if (optionalCache.isEmpty()) {
                return List.of();
            }

            DiagramRenderingCache cache = optionalCache.get();

            // @formatter:off
            Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), this.edgeMapping.getTargetNodesExpression());
            return result.asObjects().orElse(List.of()).stream()
                    .flatMap(semanticObject-> cache.getElementsRepresenting(semanticObject).stream())
                    .filter(this.isFromCompatibleTargetMapping())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            // @formatter:on
        }

        private Predicate<Element> isFromCompatibleTargetMapping() {
            return nodeElement -> {
                return this.edgeMapping.getTargetNodeDescriptions().stream().anyMatch(targetMapping -> this.isFromDescription(nodeElement, targetMapping));
            };
        }

        private boolean isFromDescription(Element nodeElement, DiagramElementDescription description) {
            if (nodeElement.getProps() instanceof NodeElementProps) {
                NodeElementProps props = (NodeElementProps) nodeElement.getProps();
                return Objects.equals(ViewConverter.this.idProvider.apply(description), props.getDescriptionId().toString());
            }
            return false;
        }
    }
}
