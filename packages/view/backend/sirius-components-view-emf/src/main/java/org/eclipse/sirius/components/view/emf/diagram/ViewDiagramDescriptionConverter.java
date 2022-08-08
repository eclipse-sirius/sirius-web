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
package org.eclipse.sirius.components.view.emf.diagram;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.compatibility.emf.DomainClassPredicate;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.ILayoutStrategy;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.ListLayoutStrategy;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.LabelDescription;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.description.SynchronizationPolicy;
import org.eclipse.sirius.components.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.components.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.components.diagrams.tools.ITool;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnTwoDiagramElementsCandidate;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnTwoDiagramElementsTool;
import org.eclipse.sirius.components.diagrams.tools.ToolSection;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.interpreter.Status;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.DeleteTool;
import org.eclipse.sirius.components.view.DiagramElementDescription;
import org.eclipse.sirius.components.view.EdgeTool;
import org.eclipse.sirius.components.view.LabelEditTool;
import org.eclipse.sirius.components.view.NodeStyle;
import org.eclipse.sirius.components.view.NodeTool;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.emf.CanonicalBehaviors;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionConverter;
import org.springframework.stereotype.Service;

/**
 * Converts a View-based diagram description into an equivalent {@link DiagramDescription}.
 *
 * @author pcdavid
 */
@Service
public class ViewDiagramDescriptionConverter implements IRepresentationDescriptionConverter {

    private static final String CONVERTED_NODES_VARIABLE = "convertedNodes"; //$NON-NLS-1$

    private static final String DEFAULT_DIAGRAM_LABEL = "Diagram"; //$NON-NLS-1$

    private static final String NODE_CREATION_TOOL_SECTION = "Node Creation"; //$NON-NLS-1$

    private static final String NODE_CREATION_TOOL_ICON = "/img/Entity.svg"; //$NON-NLS-1$

    private static final String EDGE_CREATION_TOOL_SECTION = "Edge Creation"; //$NON-NLS-1$

    private static final String EDGE_CREATION_TOOL_ICON = "/img/Relation.svg"; //$NON-NLS-1$

    private final IObjectService objectService;

    private final IEditService editService;

    private final StylesFactory stylesFactory;

    private final CanonicalBehaviors canonicalBehaviors;

    private final Function<VariableManager, String> semanticTargetIdProvider;

    private final Function<VariableManager, String> semanticTargetKindProvider;

    private final Function<VariableManager, String> semanticTargetLabelProvider;

    private final Function<DiagramElementDescription, UUID> idProvider = (diagramElementDescription) -> {
        // DiagramElementDescription should have a proper id.
        return UUID.nameUUIDFromBytes(EcoreUtil.getURI(diagramElementDescription).toString().getBytes());
    };

    public ViewDiagramDescriptionConverter(IObjectService objectService, IEditService editService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
        this.stylesFactory = new StylesFactory();
        this.semanticTargetIdProvider = variableManager -> this.self(variableManager).map(this.objectService::getId).orElse(null);
        this.semanticTargetKindProvider = variableManager -> this.self(variableManager).map(this.objectService::getKind).orElse(null);
        this.semanticTargetLabelProvider = variableManager -> this.self(variableManager).map(this.objectService::getLabel).orElse(null);
        this.canonicalBehaviors = new CanonicalBehaviors(objectService, editService);
    }

    @Override
    public boolean canConvert(RepresentationDescription representationDescription) {
        return representationDescription instanceof org.eclipse.sirius.components.view.DiagramDescription;
    }

    @Override
    public IRepresentationDescription convert(RepresentationDescription viewRepresentationDescription, AQLInterpreter interpreter) {
        org.eclipse.sirius.components.view.DiagramDescription viewDiagramDescription = (org.eclipse.sirius.components.view.DiagramDescription) viewRepresentationDescription;
        ViewDiagramDescriptionConverterContext converterContext = new ViewDiagramDescriptionConverterContext(interpreter);
        // Nodes must be fully converted first.
        List<NodeDescription> nodeDescriptions = viewDiagramDescription.getNodeDescriptions().stream().map(node -> this.convert(node, converterContext)).collect(Collectors.toList());
        List<EdgeDescription> edgeDescriptions = viewDiagramDescription.getEdgeDescriptions().stream().map(edge -> this.convert(edge, converterContext)).collect(Collectors.toList());
        // @formatter:off
        String diagramDescriptionURI = EcoreUtil.getURI(viewDiagramDescription).toString();
        return DiagramDescription.newDiagramDescription(UUID.nameUUIDFromBytes(diagramDescriptionURI.getBytes()).toString())
                .label(Optional.ofNullable(viewDiagramDescription.getName()).orElse(DEFAULT_DIAGRAM_LABEL))
                .labelProvider(variableManager -> this.computeDiagramLabel(viewDiagramDescription, variableManager, interpreter))
                .canCreatePredicate(variableManager -> this.canCreateDiagram(viewDiagramDescription, variableManager, interpreter))
                .autoLayout(viewDiagramDescription.isAutoLayout())
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .nodeDescriptions(nodeDescriptions)
                .edgeDescriptions(edgeDescriptions)
                .toolSections(this.createToolSections(converterContext))
                .dropHandler(this.createDiagramDropHandler(viewDiagramDescription, converterContext))
                .build();
        // @formatter:on
    }

    private Function<VariableManager, IStatus> createDiagramDropHandler(org.eclipse.sirius.components.view.DiagramDescription viewDiagramDescription,
            ViewDiagramDescriptionConverterContext converterContext) {
        Map<org.eclipse.sirius.components.view.NodeDescription, NodeDescription> capturedNodeDescriptions = Map.copyOf(converterContext.getConvertedNodes());
        return variableManager -> {
            if (viewDiagramDescription.getOnDrop() != null) {
                var augmentedVariableManager = variableManager.createChild();
                augmentedVariableManager.put(CONVERTED_NODES_VARIABLE, capturedNodeDescriptions);
                return new DiagramOperationInterpreter(converterContext.getInterpreter(), this.objectService, this.editService, this.getDiagramContext(variableManager), capturedNodeDescriptions)
                        .executeTool(viewDiagramDescription.getOnDrop(), augmentedVariableManager);
            } else {
                return new Failure("No drop handler configured"); //$NON-NLS-1$
            }
        };
    }

    private String computeDiagramLabel(org.eclipse.sirius.components.view.DiagramDescription viewDiagramDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String title = variableManager.get(DiagramDescription.LABEL, String.class).orElseGet(() -> this.evaluateString(interpreter, variableManager, viewDiagramDescription.getTitleExpression()));
        if (title == null || title.isBlank()) {
            return DEFAULT_DIAGRAM_LABEL;
        } else {
            return title;
        }
    }

    private boolean canCreateDiagram(org.eclipse.sirius.components.view.DiagramDescription viewDiagramDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        boolean result = false;
        // @formatter:off
        Optional<EClass> optionalEClass = variableManager.get(IRepresentationDescription.CLASS, EClass.class)
                .filter(new DomainClassPredicate(viewDiagramDescription.getDomainType()));
        // @formatter:on
        if (optionalEClass.isPresent()) {
            String preconditionExpression = viewDiagramDescription.getPreconditionExpression();
            if (preconditionExpression != null && !preconditionExpression.isBlank()) {
                result = interpreter.evaluateExpression(variableManager.getVariables(), preconditionExpression).asBoolean().orElse(false);
            } else {
                result = true;
            }
        }
        return result;
    }

    private NodeDescription convert(org.eclipse.sirius.components.view.NodeDescription viewNodeDescription, ViewDiagramDescriptionConverterContext converterContext) {
        // @formatter:off
        // Convert our children first, we need their converted values to build our NodeDescription
        var childNodeDescriptions = viewNodeDescription.getChildrenDescriptions().stream()
                .map(childNodeDescription -> this.convert(childNodeDescription, converterContext))
                .collect(Collectors.toList());

        var borderNodeDescriptions = viewNodeDescription.getBorderNodesDescriptions().stream()
                .map(borderNodeDescription -> this.convert(borderNodeDescription, converterContext))
                .collect(Collectors.toList());
        // @formatter:on
        SynchronizationPolicy synchronizationPolicy = SynchronizationPolicy.valueOf(viewNodeDescription.getSynchronizationPolicy().getName());

        AQLInterpreter interpreter = converterContext.getInterpreter();
        Function<VariableManager, String> typeProvider = variableManager -> {
            // @formatter:off
            var effectiveStyle = viewNodeDescription.getConditionalStyles().stream()
                    .filter(style -> this.matches(interpreter, style.getCondition(), variableManager))
                    .map(NodeStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewNodeDescription::getStyle);
            // @formatter:on
            return this.stylesFactory.getNodeType(effectiveStyle);
        };

        Function<VariableManager, INodeStyle> styleProvider = variableManager -> {
            // @formatter:off
            var effectiveStyle = viewNodeDescription.getConditionalStyles().stream()
                    .filter(style -> this.matches(interpreter, style.getCondition(), variableManager))
                    .map(NodeStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewNodeDescription::getStyle);
            Optional<String> optionalEditingContextId = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class)
                                                                       .map(IEditingContext::getId);
            // @formatter:on
            return this.stylesFactory.createNodeStyle(effectiveStyle, optionalEditingContextId);
        };

        Function<VariableManager, ILayoutStrategy> childrenLayoutStrategyProvider = variableManager -> {
            ILayoutStrategy childrenLayoutStrategy = null;

            // @formatter:off
            var effectiveStyle = viewNodeDescription.getConditionalStyles().stream()
                    .filter(style -> this.matches(interpreter, style.getCondition(), variableManager))
                    .map(NodeStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewNodeDescription::getStyle);
            // @formatter:on

            if (effectiveStyle.isListMode()) {
                childrenLayoutStrategy = new ListLayoutStrategy();
            } else if (viewNodeDescription.getChildrenDescriptions().size() > 0) {
                childrenLayoutStrategy = new FreeFormLayoutStrategy();
            }

            return childrenLayoutStrategy;
        };

        Function<VariableManager, Size> sizeProvider = variableManager -> {
            // @formatter:off
            var effectiveStyle = viewNodeDescription.getConditionalStyles().stream()
                    .filter(style -> this.matches(interpreter, style.getCondition(), variableManager))
                    .map(NodeStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewNodeDescription::getStyle);
            // @formatter:on
            Size size = Size.UNDEFINED;
            if (effectiveStyle.eIsSet(ViewPackage.Literals.NODE_STYLE__SIZE_COMPUTATION_EXPRESSION) && !effectiveStyle.getSizeComputationExpression().isBlank()) {
                Result result = interpreter.evaluateExpression(variableManager.getVariables(), effectiveStyle.getSizeComputationExpression());
                if (result.getStatus().compareTo(Status.WARNING) <= 0 && result.asInt().isPresent()) {
                    int computedSize = result.asInt().getAsInt();
                    size = Size.of(computedSize, computedSize);
                }
            }
            return size;
        };

        // @formatter:off
        NodeDescription result = NodeDescription.newNodeDescription(this.idProvider.apply(viewNodeDescription))
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .targetObjectKindProvider(this.semanticTargetKindProvider)
                .targetObjectLabelProvider(this.semanticTargetLabelProvider)
                .semanticElementsProvider(this.getSemanticElementsProvider(viewNodeDescription, interpreter))
                .synchronizationPolicy(synchronizationPolicy)
                .typeProvider(typeProvider)
                .labelDescription(this.getLabelDescription(viewNodeDescription, interpreter))
                .styleProvider(styleProvider)
                .childrenLayoutStrategyProvider(childrenLayoutStrategyProvider)
                .childNodeDescriptions(childNodeDescriptions)
                .borderNodeDescriptions(borderNodeDescriptions)
                .sizeProvider(sizeProvider)
                .labelEditHandler(this.createLabelEditHandler(viewNodeDescription, converterContext))
                .deleteHandler(this.createDeleteHandler(viewNodeDescription, converterContext))
                .build();
        // @formatter:on
        converterContext.getConvertedNodes().put(viewNodeDescription, result);
        return result;
    }

    private boolean matches(AQLInterpreter interpreter, String condition, VariableManager variableManager) {
        return interpreter.evaluateExpression(variableManager.getVariables(), condition).asBoolean().orElse(Boolean.FALSE);
    }

    private List<ToolSection> createToolSections(ViewDiagramDescriptionConverterContext converterContext) {
        var capturedConvertedNodes = Map.copyOf(converterContext.getConvertedNodes());
        List<ITool> nodeCreationTools = new ArrayList<>();
        for (var nodeDescription : converterContext.getConvertedNodes().keySet()) {
            // Add custom tools
            int i = 0;
            for (NodeTool nodeTool : nodeDescription.getNodeTools()) {
                // @formatter:off
                SingleClickOnDiagramElementTool customTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(this.idProvider.apply(nodeDescription) + "_tool" + i++) //$NON-NLS-1$
                        .label(nodeTool.getName())
                        .imageURL(NODE_CREATION_TOOL_ICON)
                        .handler(variableManager -> {
                            VariableManager child = variableManager.createChild();
                            child.put(CONVERTED_NODES_VARIABLE, capturedConvertedNodes);
                            return new DiagramOperationInterpreter(converterContext.getInterpreter(), this.objectService, this.editService, this.getDiagramContext(child), capturedConvertedNodes).executeTool(nodeTool, child);
                        })
                        .targetDescriptions(Optional.ofNullable(nodeDescription.eContainer()).map(converterContext.getConvertedNodes()::get).stream().collect(Collectors.toList()))
                        .appliesToDiagramRoot(nodeDescription.eContainer() instanceof org.eclipse.sirius.components.view.DiagramDescription)
                        .build();
                // @formatter:on
                nodeCreationTools.add(customTool);
            }
            // If there are no custom tools defined, add a canonical creation tool
            if (i == 0) {
                // @formatter:off
                SingleClickOnDiagramElementTool tool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(this.idProvider.apply(nodeDescription) + "_creationTool") //$NON-NLS-1$
                        .label("New " + this.getSimpleTypeName(nodeDescription.getDomainType())) //$NON-NLS-1$
                        .imageURL(NODE_CREATION_TOOL_ICON)
                        .handler(variableManager ->  {
                            VariableManager child = variableManager.createChild();
                            child.put(CONVERTED_NODES_VARIABLE, capturedConvertedNodes);
                            return this.canonicalBehaviors.createNewNode(nodeDescription, variableManager);
                        })
                        .targetDescriptions(Optional.ofNullable(nodeDescription.eContainer()).map(converterContext.getConvertedNodes()::get).stream().collect(Collectors.toList()))
                        .appliesToDiagramRoot(nodeDescription.eContainer() instanceof org.eclipse.sirius.components.view.DiagramDescription)
                        .build();
                // @formatter:on
                nodeCreationTools.add(tool);
            }
        }

        List<ITool> edgeCreationTools = new ArrayList<>();
        for (var edgeDescription : converterContext.getConvertedEdges().keySet()) {
            // Add custom tools
            int i = 0;
            for (EdgeTool edgeTool : edgeDescription.getEdgeTools()) {
                // @formatter:off
                SingleClickOnTwoDiagramElementsTool customTool = SingleClickOnTwoDiagramElementsTool.newSingleClickOnTwoDiagramElementsTool(this.idProvider.apply(edgeDescription) + "_tool" + i++) //$NON-NLS-1$
                        .label(edgeTool.getName())
                        .imageURL(EDGE_CREATION_TOOL_ICON)
                        .candidates(List.of(SingleClickOnTwoDiagramElementsCandidate.newSingleClickOnTwoDiagramElementsCandidate()
                                .sources(edgeDescription.getSourceNodeDescriptions().stream().map(converterContext.getConvertedNodes()::get).collect(Collectors.toList()))
                                .targets(edgeDescription.getTargetNodeDescriptions().stream().map(converterContext.getConvertedNodes()::get).collect(Collectors.toList()))
                                .build()))
                        .handler(variableManager -> {
                            VariableManager child = variableManager.createChild();
                            child.put(CONVERTED_NODES_VARIABLE, capturedConvertedNodes);
                            return new DiagramOperationInterpreter(converterContext.getInterpreter(), this.objectService, this.editService, this.getDiagramContext(variableManager), capturedConvertedNodes).executeTool(edgeTool, child);
                        })
                        .build();
                // @formatter:on
                edgeCreationTools.add(customTool);
            }
            // If there are no custom tools defined, add a canonical creation tool
            if (i == 0) {
                // @formatter:off
                SingleClickOnTwoDiagramElementsTool tool = SingleClickOnTwoDiagramElementsTool.newSingleClickOnTwoDiagramElementsTool(this.idProvider.apply(edgeDescription) + "_creationTool") //$NON-NLS-1$
                        .label("New " + this.getSimpleTypeName(edgeDescription.getDomainType())) //$NON-NLS-1$
                        .imageURL(EDGE_CREATION_TOOL_ICON)
                        .candidates(List.of(SingleClickOnTwoDiagramElementsCandidate.newSingleClickOnTwoDiagramElementsCandidate()
                                .sources(edgeDescription.getSourceNodeDescriptions().stream().map(converterContext.getConvertedNodes()::get).collect(Collectors.toList()))
                                .targets(edgeDescription.getTargetNodeDescriptions().stream().map(converterContext.getConvertedNodes()::get).collect(Collectors.toList()))
                                .build()))
                        .handler(variableManager -> {
                            VariableManager child = variableManager.createChild();
                            child.put(CONVERTED_NODES_VARIABLE, capturedConvertedNodes);
                            return this.canonicalBehaviors.createNewEdge(child, edgeDescription);
                        })
                        .build();
                // @formatter:on
                edgeCreationTools.add(tool);
            }
        }

        // @formatter:off
        return List.of(ToolSection.newToolSection(UUID.randomUUID().toString()).label(NODE_CREATION_TOOL_SECTION).tools(nodeCreationTools).imageURL("").build(), //$NON-NLS-1$
                       ToolSection.newToolSection(UUID.randomUUID().toString()).label(EDGE_CREATION_TOOL_SECTION).tools(edgeCreationTools).imageURL("").build()); //$NON-NLS-1$
        // @formatter:on
    }

    private String getSimpleTypeName(String domainType) {
        String result = Optional.ofNullable(domainType).orElse(""); //$NON-NLS-1$
        if (result.contains("::")) { //$NON-NLS-1$
            result = domainType.substring(domainType.indexOf("::") + 2); //$NON-NLS-1$
        }
        return result;
    }

    private LabelDescription getLabelDescription(org.eclipse.sirius.components.view.NodeDescription viewNodeDescription, AQLInterpreter interpreter) {
        Function<VariableManager, String> labelIdProvider = variableManager -> {
            Object parentId = variableManager.get(LabelDescription.OWNER_ID, Object.class).orElse(null);
            return String.valueOf(parentId) + LabelDescription.LABEL_SUFFIX;
        };

        Function<VariableManager, LabelStyleDescription> styleDescriptionProvider = variableManager -> {
            // @formatter:off
            var effectiveStyle = viewNodeDescription.getConditionalStyles().stream()
                    .filter(style -> this.matches(interpreter, style.getCondition(), variableManager))
                    .map(NodeStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewNodeDescription::getStyle);
            // @formatter:on

            return this.stylesFactory.createLabelStyleDescription(effectiveStyle);
        };

        // @formatter:off
        return LabelDescription.newLabelDescription(EcoreUtil.getURI(viewNodeDescription).toString() + LabelDescription.LABEL_SUFFIX)
                .idProvider(labelIdProvider)
                .textProvider(variableManager -> this.evaluateString(interpreter, variableManager, viewNodeDescription.getLabelExpression()))
                .styleDescriptionProvider(styleDescriptionProvider)
                .build();
        // @formatter:on
    }

    private Optional<LabelDescription> getSpecificEdgeLabelDescription(org.eclipse.sirius.components.view.EdgeDescription viewEdgeDescription, String labelExpression, String labelSuffix,
            AQLInterpreter interpreter) {
        if (labelExpression == null || labelExpression.isBlank()) {
            return Optional.empty();
        }

        Function<VariableManager, String> labelIdProvider = variableManager -> {
            Object parentId = variableManager.get(LabelDescription.OWNER_ID, Object.class).orElse(null);
            return String.valueOf(parentId) + labelSuffix;
        };

        Function<VariableManager, LabelStyleDescription> styleDescriptionProvider = variableManager -> {
            // @formatter:off
            var effectiveStyle = viewEdgeDescription.getConditionalStyles().stream()
                    .filter(style -> this.matches(interpreter, style.getCondition(), variableManager))
                    .map(org.eclipse.sirius.components.view.EdgeStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewEdgeDescription::getStyle);
            // @formatter:on

            return this.stylesFactory.createEdgeLabelStyleDescription(effectiveStyle);
        };

        // @formatter:off
        return Optional.of(LabelDescription.newLabelDescription(EcoreUtil.getURI(viewEdgeDescription).toString() + labelSuffix)
                             .idProvider(labelIdProvider)
                             .textProvider(variableManager -> this.evaluateString(interpreter, variableManager, labelExpression))
                             .styleDescriptionProvider(styleDescriptionProvider)
                             .build());
        // @formatter:on
    }

    private Function<VariableManager, List<?>> getSemanticElementsProvider(org.eclipse.sirius.components.view.DiagramElementDescription elementDescription, AQLInterpreter interpreter) {
        return variableManager -> {
            Result result = interpreter.evaluateExpression(variableManager.getVariables(), elementDescription.getSemanticCandidatesExpression());
            List<Object> candidates = result.asObjects().orElse(List.of());
            // @formatter:off
            return candidates.stream()
                    .filter(EObject.class::isInstance)
                    .map(EObject.class::cast)
                    .filter(candidate -> new DomainClassPredicate(Optional.ofNullable(elementDescription.getDomainType()).orElse("")).test(candidate.eClass())) //$NON-NLS-1$
                    .collect(Collectors.toList());
            // @formatter:on
        };
    }

    private EdgeDescription convert(org.eclipse.sirius.components.view.EdgeDescription viewEdgeDescription, ViewDiagramDescriptionConverterContext converterContext) {
        Function<VariableManager, List<?>> semanticElementsProvider;
        AQLInterpreter interpreter = converterContext.getInterpreter();
        if (viewEdgeDescription.isIsDomainBasedEdge()) {
            // Same logic as for nodes.
            semanticElementsProvider = this.getSemanticElementsProvider(viewEdgeDescription, interpreter);
        } else {
            //
            var sourceNodeDescriptions = viewEdgeDescription.getSourceNodeDescriptions().stream().map(converterContext.getConvertedNodes()::get);
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

                Result result = interpreter.evaluateExpression(variableManager.getVariables(), sourceFinderExpression);
                List<Object> semanticCandidates = result.asObjects().orElse(List.of());
                var nodeCandidates = semanticCandidates.stream().flatMap(semanticObject -> cache.getElementsRepresenting(semanticObject).stream());

                // @formatter:off
                return nodeCandidates
                        .filter(nodeElement -> viewEdgeDescription.getSourceNodeDescriptions().stream().anyMatch(nodeDescription -> this.isFromDescription(nodeElement, nodeDescription)))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                // @formatter:on
            };
        } else {
            sourceNodesProvider = variableManager -> {
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

        Function<VariableManager, List<Element>> targetNodesProvider = new TargetNodesProvider(this.idProvider, viewEdgeDescription, interpreter);

        Function<VariableManager, EdgeStyle> styleProvider = variableManager -> {
            // @formatter:off
            var effectiveStyle = viewEdgeDescription.getConditionalStyles().stream()
                    .filter(style -> this.matches(interpreter, style.getCondition(), variableManager))
                    .map(org.eclipse.sirius.components.view.EdgeStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewEdgeDescription::getStyle);
            // @formatter:on
            return this.stylesFactory.createEdgeStyle(effectiveStyle);
        };

        SynchronizationPolicy synchronizationPolicy = SynchronizationPolicy.valueOf(viewEdgeDescription.getSynchronizationPolicy().getName());

        // @formatter:off
        var builder = EdgeDescription.newEdgeDescription(this.idProvider.apply(viewEdgeDescription))
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .targetObjectKindProvider(this.semanticTargetKindProvider)
                .targetObjectLabelProvider(this.semanticTargetLabelProvider)
                .sourceNodeDescriptions(viewEdgeDescription.getSourceNodeDescriptions().stream().map(converterContext.getConvertedNodes()::get).collect(Collectors.toList()))
                .targetNodeDescriptions(viewEdgeDescription.getTargetNodeDescriptions().stream().map(converterContext.getConvertedNodes()::get).collect(Collectors.toList()))
                .semanticElementsProvider(semanticElementsProvider)
                .synchronizationPolicy(synchronizationPolicy)
                .sourceNodesProvider(sourceNodesProvider)
                .targetNodesProvider(targetNodesProvider)
                .styleProvider(styleProvider)
                .deleteHandler(this.createDeleteHandler(viewEdgeDescription, converterContext))
                .labelEditHandler(this.createLabelEditHandler(viewEdgeDescription, converterContext));

        this.getSpecificEdgeLabelDescription(viewEdgeDescription, viewEdgeDescription.getBeginLabelExpression(), "_beginlabel", interpreter).ifPresent(builder::beginLabelDescription); //$NON-NLS-1$
        this.getSpecificEdgeLabelDescription(viewEdgeDescription, viewEdgeDescription.getLabelExpression(), "_centerlabel", interpreter).ifPresent(builder::centerLabelDescription); //$NON-NLS-1$
        this.getSpecificEdgeLabelDescription(viewEdgeDescription, viewEdgeDescription.getEndLabelExpression(), "_endlabel", interpreter).ifPresent(builder::endLabelDescription); //$NON-NLS-1$

        EdgeDescription result = builder.build();
        converterContext.getConvertedEdges().put(viewEdgeDescription, result);
        return result;
        // @formatter:on
    }

    private Function<VariableManager, IStatus> createDeleteHandler(DiagramElementDescription diagramElementDescription, ViewDiagramDescriptionConverterContext converterContext) {
        var capturedConvertedNodes = Map.copyOf(converterContext.getConvertedNodes());
        DeleteTool tool = diagramElementDescription.getDeleteTool();
        if (tool != null) {
            return variableManager -> {
                VariableManager child = variableManager.createChild();
                child.put(CONVERTED_NODES_VARIABLE, capturedConvertedNodes);
                return new DiagramOperationInterpreter(converterContext.getInterpreter(), this.objectService, this.editService, this.getDiagramContext(variableManager), capturedConvertedNodes)
                        .executeTool(tool, child);
            };
        } else {
            return this.canonicalBehaviors::deleteElement;
        }
    }

    private BiFunction<VariableManager, String, IStatus> createLabelEditHandler(DiagramElementDescription diagramElementDescription, ViewDiagramDescriptionConverterContext converterContext) {
        var capturedConvertedNodes = Map.copyOf(converterContext.getConvertedNodes());
        LabelEditTool tool = diagramElementDescription.getLabelEditTool();
        if (tool != null) {
            return (variableManager, newLabel) -> {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put("arg0", newLabel); //$NON-NLS-1$
                childVariableManager.put(CONVERTED_NODES_VARIABLE, capturedConvertedNodes);
                return new DiagramOperationInterpreter(converterContext.getInterpreter(), this.objectService, this.editService, this.getDiagramContext(variableManager), capturedConvertedNodes)
                        .executeTool(tool, childVariableManager);
            };
        } else {
            return this.canonicalBehaviors::editLabel;
        }
    }

    private Predicate<Element> isFromCompatibleSourceMapping(org.eclipse.sirius.components.view.EdgeDescription edgeDescription) {
        return nodeElement -> edgeDescription.getSourceNodeDescriptions().stream().anyMatch(nodeDescription -> this.isFromDescription(nodeElement, nodeDescription));
    }

    private boolean isFromDescription(Element nodeElement, DiagramElementDescription diagramElementDescription) {
        if (nodeElement.getProps() instanceof NodeElementProps) {
            NodeElementProps props = (NodeElementProps) nodeElement.getProps();
            return Objects.equals(this.idProvider.apply(diagramElementDescription), props.getDescriptionId());
        }
        return false;
    }

    private Optional<Object> self(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class);
    }

    private String evaluateString(AQLInterpreter interpreter, VariableManager variableManager, String expression) {
        return interpreter.evaluateExpression(variableManager.getVariables(), expression).asString().orElse(""); //$NON-NLS-1$
    }

    private IDiagramContext getDiagramContext(VariableManager variableManager) {
        return variableManager.get(IDiagramContext.DIAGRAM_CONTEXT, IDiagramContext.class).orElse(null);
    }

}
