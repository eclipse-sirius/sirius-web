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

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DeletionPolicy;
import org.eclipse.sirius.components.collaborative.diagrams.handlers.DeleteFromDiagramEventHandler;
import org.eclipse.sirius.components.compatibility.emf.DomainClassPredicate;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.ILayoutStrategy;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.ListLayoutStrategy;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeLabelKind;
import org.eclipse.sirius.components.diagrams.description.IEdgeEditLabelHandler;
import org.eclipse.sirius.components.diagrams.description.LabelDescription;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.description.SynchronizationPolicy;
import org.eclipse.sirius.components.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.components.diagrams.events.ReconnectEdgeKind;
import org.eclipse.sirius.components.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.components.diagrams.tools.EdgeReconnectionTool;
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
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.ConditionalNodeStyle;
import org.eclipse.sirius.components.view.DeleteTool;
import org.eclipse.sirius.components.view.DiagramElementDescription;
import org.eclipse.sirius.components.view.EdgeTool;
import org.eclipse.sirius.components.view.FreeFormLayoutStrategyDescription;
import org.eclipse.sirius.components.view.LabelEditTool;
import org.eclipse.sirius.components.view.LayoutStrategyDescription;
import org.eclipse.sirius.components.view.ListLayoutStrategyDescription;
import org.eclipse.sirius.components.view.NodeStyleDescription;
import org.eclipse.sirius.components.view.NodeTool;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.SourceEdgeEndReconnectionTool;
import org.eclipse.sirius.components.view.TargetEdgeEndReconnectionTool;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionConverter;
import org.eclipse.sirius.components.view.emf.diagram.providers.api.IViewToolImageProvider;
import org.springframework.stereotype.Service;

/**
 * Converts a View-based diagram description into an equivalent {@link DiagramDescription}.
 *
 * @author pcdavid
 */
@Service
public class ViewDiagramDescriptionConverter implements IRepresentationDescriptionConverter {

    private static final String CONVERTED_NODES_VARIABLE = "convertedNodes";

    private static final String DEFAULT_DIAGRAM_LABEL = "Diagram";

    private static final String NODE_CREATION_TOOL_SECTION = "Node Creation";

    private static final String EDGE_CREATION_TOOL_SECTION = "Edge Creation";

    private final IObjectService objectService;

    private final IEditService editService;

    private final StylesFactory stylesFactory;

    private final Function<VariableManager, String> semanticTargetIdProvider;

    private final Function<VariableManager, String> semanticTargetKindProvider;

    private final Function<VariableManager, String> semanticTargetLabelProvider;

    private final Function<DiagramElementDescription, UUID> idProvider = (diagramElementDescription) -> {
        // DiagramElementDescription should have a proper id.
        return UUID.nameUUIDFromBytes(EcoreUtil.getURI(diagramElementDescription).toString().getBytes());
    };

    private final IViewToolImageProvider viewToolImageProvider;

    public ViewDiagramDescriptionConverter(IObjectService objectService, IEditService editService, List<INodeStyleProvider> iNodeStyleProviders, IViewToolImageProvider viewToolImageProvider) {
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
        this.stylesFactory = new StylesFactory(Objects.requireNonNull(iNodeStyleProviders), this.objectService);
        this.semanticTargetIdProvider = variableManager -> this.self(variableManager).map(this.objectService::getId).orElse(null);
        this.semanticTargetKindProvider = variableManager -> this.self(variableManager).map(this.objectService::getKind).orElse(null);
        this.semanticTargetLabelProvider = variableManager -> this.self(variableManager).map(this.objectService::getLabel).orElse(null);
        this.viewToolImageProvider = Objects.requireNonNull(viewToolImageProvider);
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
        List<NodeDescription> nodeDescriptions = viewDiagramDescription.getNodeDescriptions().stream().map(node -> this.convert(node, converterContext)).toList();
        List<EdgeDescription> edgeDescriptions = viewDiagramDescription.getEdgeDescriptions().stream().map(edge -> this.convert(edge, converterContext)).toList();
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
                .tools(this.createTools(converterContext))
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
                return new Failure("No drop handler configured");
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
                .toList();

        var borderNodeDescriptions = viewNodeDescription.getBorderNodesDescriptions().stream()
                .map(borderNodeDescription -> this.convert(borderNodeDescription, converterContext))
                .toList();
        // @formatter:on
        SynchronizationPolicy synchronizationPolicy = SynchronizationPolicy.valueOf(viewNodeDescription.getSynchronizationPolicy().getName());

        AQLInterpreter interpreter = converterContext.getInterpreter();
        Function<VariableManager, String> typeProvider = variableManager -> {
            var effectiveStyle = this.findEffectiveStyle(viewNodeDescription, interpreter, variableManager);
            return this.stylesFactory.getNodeType(effectiveStyle);
        };

        Function<VariableManager, INodeStyle> styleProvider = variableManager -> {
            var effectiveStyle = this.findEffectiveStyle(viewNodeDescription, interpreter, variableManager);
            Optional<String> optionalEditingContextId = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class).map(IEditingContext::getId);
            return this.stylesFactory.createNodeStyle(effectiveStyle, optionalEditingContextId);
        };

        Function<VariableManager, ILayoutStrategy> childrenLayoutStrategyProvider = variableManager -> {
            ILayoutStrategy childrenLayoutStrategy = null;

            LayoutStrategyDescription childrenLayoutStrategyFromViewModel = viewNodeDescription.getChildrenLayoutStrategy();
            if (childrenLayoutStrategyFromViewModel instanceof ListLayoutStrategyDescription) {
                childrenLayoutStrategy = new ListLayoutStrategy();
            } else if (childrenLayoutStrategyFromViewModel instanceof FreeFormLayoutStrategyDescription) {
                childrenLayoutStrategy = new FreeFormLayoutStrategy();
            }
            return childrenLayoutStrategy;
        };

        Function<VariableManager, Size> sizeProvider = variableManager -> this.computeSize(viewNodeDescription, interpreter, variableManager);

        // @formatter:off
        List<UUID> reusedChildNodeDescriptionIds = viewNodeDescription.getReusedChildNodeDescriptions().stream()
                .map(this.idProvider)
                .toList();
        List<UUID> reusedBorderNodeDescriptionIds = viewNodeDescription.getReusedBorderNodeDescriptions().stream()
                .map(this.idProvider)
                .toList();

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
                .collapsible(viewNodeDescription.isCollapsible())
                .reusedChildNodeDescriptionIds(reusedChildNodeDescriptionIds)
                .reusedBorderNodeDescriptionIds(reusedBorderNodeDescriptionIds)
                .sizeProvider(sizeProvider)
                .labelEditHandler(this.createLabelEditHandler(viewNodeDescription, converterContext))
                .deleteHandler(this.createDeleteHandler(viewNodeDescription, converterContext))
                .build();
        // @formatter:on
        converterContext.getConvertedNodes().put(viewNodeDescription, result);
        return result;
    }

    private NodeStyleDescription findEffectiveStyle(org.eclipse.sirius.components.view.NodeDescription viewNodeDescription, AQLInterpreter interpreter, VariableManager variableManager) {
        return viewNodeDescription.getConditionalStyles().stream().filter(style -> this.matches(interpreter, style.getCondition(), variableManager)).map(ConditionalNodeStyle::getStyle).findFirst()
                .orElseGet(viewNodeDescription::getStyle);
    }

    private Size computeSize(org.eclipse.sirius.components.view.NodeDescription viewNodeDescription, AQLInterpreter interpreter, VariableManager variableManager) {
        var effectiveStyle = this.findEffectiveStyle(viewNodeDescription, interpreter, variableManager);
        double computedWidth = Size.UNDEFINED.getWidth();
        if (effectiveStyle.eIsSet(ViewPackage.Literals.NODE_STYLE_DESCRIPTION__WIDTH_COMPUTATION_EXPRESSION) && !effectiveStyle.getWidthComputationExpression().isBlank()) {
            Result result = interpreter.evaluateExpression(variableManager.getVariables(), effectiveStyle.getWidthComputationExpression());
            if (result.getStatus().compareTo(Status.WARNING) <= 0 && result.asInt().isPresent()) {
                computedWidth = result.asInt().getAsInt();
            }
        }
        double computedHeight = Size.UNDEFINED.getHeight();
        if (effectiveStyle.eIsSet(ViewPackage.Literals.NODE_STYLE_DESCRIPTION__HEIGHT_COMPUTATION_EXPRESSION) && !effectiveStyle.getHeightComputationExpression().isBlank()) {
            Result result = interpreter.evaluateExpression(variableManager.getVariables(), effectiveStyle.getHeightComputationExpression());
            if (result.getStatus().compareTo(Status.WARNING) <= 0 && result.asInt().isPresent()) {
                computedHeight = result.asInt().getAsInt();
            }
        }
        return Size.of(computedWidth, computedHeight);
    }

    private boolean matches(AQLInterpreter interpreter, String condition, VariableManager variableManager) {
        return interpreter.evaluateExpression(variableManager.getVariables(), condition).asBoolean().orElse(Boolean.FALSE);
    }

    private List<ToolSection> createToolSections(ViewDiagramDescriptionConverterContext converterContext) {
        var capturedConvertedNodes = Map.copyOf(converterContext.getConvertedNodes());
        var nodeCreationTools = this.getNodeTools(converterContext, capturedConvertedNodes);
        var edgeTools = this.getEdgeTools(converterContext, capturedConvertedNodes);

        // @formatter:off
        var nodeCreationToolSection = ToolSection.newToolSection(UUID.randomUUID().toString())
                .label(NODE_CREATION_TOOL_SECTION)
                .tools(nodeCreationTools)
                .imageURL("")
                .build();
        var edgeCreationToolSection = ToolSection.newToolSection(UUID.randomUUID().toString())
                .label(EDGE_CREATION_TOOL_SECTION)
                .tools(edgeTools)
                .imageURL("")
                .build();
        return List.of(nodeCreationToolSection, edgeCreationToolSection);
        // @formatter:on
    }

    private List<ITool> getNodeTools(ViewDiagramDescriptionConverterContext converterContext, Map<org.eclipse.sirius.components.view.NodeDescription, NodeDescription> capturedConvertedNodes) {
        List<ITool> nodeCreationTools = new ArrayList<>();
        for (var nodeDescription : converterContext.getConvertedNodes().keySet()) {
            List<org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription> allTargetDescriptions = this.getAllTargetDescriptions(nodeDescription, converterContext);
            String imageURL = this.viewToolImageProvider.getImage(nodeDescription);

            // Add custom tools
            int i = 0;
            for (NodeTool nodeTool : nodeDescription.getNodeTools()) {
                // @formatter:off
                SingleClickOnDiagramElementTool customTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(this.getToolId(nodeDescription, i++))
                        .label(nodeTool.getName())
                        .imageURL(imageURL)
                        .handler(variableManager -> {
                            VariableManager child = variableManager.createChild();
                            child.put(CONVERTED_NODES_VARIABLE, capturedConvertedNodes);
                            child.put("nodeDescription", nodeDescription);
                            return new DiagramOperationInterpreter(converterContext.getInterpreter(), this.objectService, this.editService, this.getDiagramContext(child), capturedConvertedNodes).executeTool(nodeTool, child);
                        })
                        .targetDescriptions(allTargetDescriptions)
                        .appliesToDiagramRoot(nodeDescription.eContainer() instanceof org.eclipse.sirius.components.view.DiagramDescription)
                        .build();
                // @formatter:on
                nodeCreationTools.add(customTool);
            }
        }
        return nodeCreationTools;
    }

    private List<ITool> getEdgeTools(ViewDiagramDescriptionConverterContext converterContext, Map<org.eclipse.sirius.components.view.NodeDescription, NodeDescription> capturedConvertedNodes) {
        List<ITool> edgeTools = new ArrayList<>();
        for (var edgeDescription : converterContext.getConvertedEdges().keySet()) {
            String imageURL = this.viewToolImageProvider.getImage(edgeDescription);

            // Add custom tools
            int i = 0;
            for (EdgeTool edgeTool : edgeDescription.getEdgeTools()) {
                // @formatter:off
                SingleClickOnTwoDiagramElementsTool customTool = SingleClickOnTwoDiagramElementsTool.newSingleClickOnTwoDiagramElementsTool(this.getToolId(edgeDescription, i++))
                        .label(edgeTool.getName())
                        .imageURL(imageURL)
                        .candidates(List.of(SingleClickOnTwoDiagramElementsCandidate.newSingleClickOnTwoDiagramElementsCandidate()
                                .sources(edgeDescription.getSourceNodeDescriptions().stream().map(converterContext.getConvertedNodes()::get).toList())
                                .targets(edgeDescription.getTargetNodeDescriptions().stream().map(converterContext.getConvertedNodes()::get).toList())
                                .build()))
                        .handler(variableManager -> {
                            VariableManager child = variableManager.createChild();
                            child.put(CONVERTED_NODES_VARIABLE, capturedConvertedNodes);
                            child.put("edgeDescription", edgeDescription);
                            return new DiagramOperationInterpreter(converterContext.getInterpreter(), this.objectService, this.editService, this.getDiagramContext(variableManager), capturedConvertedNodes).executeTool(edgeTool, child);
                        })
                        .build();
                // @formatter:on
                edgeTools.add(customTool);
            }
        }
        return edgeTools;
    }

    private List<org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription> getAllTargetDescriptions(org.eclipse.sirius.components.view.NodeDescription nodeDescription,
            ViewDiagramDescriptionConverterContext converterContext) {
        List<org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription> allTargetDescriptions = new ArrayList<>();
        var targetDescriptions = Optional.ofNullable(nodeDescription.eContainer()).map(converterContext.getConvertedNodes()::get).stream().toList();
        allTargetDescriptions.addAll(targetDescriptions);

        // @formatter:off
        var crossReferencesAdapter = Optional.ofNullable(nodeDescription.eResource())
                .map(Resource::getResourceSet)
                .map(ResourceSet::eAdapters)
                .orElseGet(BasicEList::new)
                .stream()
                .filter(ECrossReferenceAdapter.class::isInstance)
                .map(ECrossReferenceAdapter.class::cast)
                .findFirst()
                .orElse(new ECrossReferenceAdapter());
        // @formatter:on
        var crossReferences = crossReferencesAdapter.getInverseReferences(nodeDescription);
        for (Setting setting : crossReferences) {
            if (setting.getEObject() instanceof org.eclipse.sirius.components.view.NodeDescription) {
                var nodeDescriptionCrossReference = (org.eclipse.sirius.components.view.NodeDescription) setting.getEObject();
                if (nodeDescriptionCrossReference.getReusedChildNodeDescriptions().contains(nodeDescription)
                        || nodeDescriptionCrossReference.getReusedBorderNodeDescriptions().contains(nodeDescription)) {
                    allTargetDescriptions.add(converterContext.getConvertedNodes().get(nodeDescriptionCrossReference));
                }
            }
        }
        return allTargetDescriptions;
    }

    private List<ITool> createTools(ViewDiagramDescriptionConverterContext converterContext) {
        var capturedConvertedNodes = Map.copyOf(converterContext.getConvertedNodes());
        List<ITool> tools = new ArrayList<>();
        int i = 0;
        for (var edgeDescription : converterContext.getConvertedEdges().keySet()) {
            for (org.eclipse.sirius.components.view.EdgeReconnectionTool edgeReconnectionTool : edgeDescription.getReconnectEdgeTools()) {
                // @formatter:off
                EdgeReconnectionTool.Builder reconnectionToolBuilder = EdgeReconnectionTool.newEdgeReconnectionTool(this.getToolId(edgeDescription, i++))
                        .label(edgeReconnectionTool.getName())
                        .handler(variableManager -> new DiagramOperationInterpreter(converterContext.getInterpreter(), this.objectService, this.editService, this.getDiagramContext(variableManager), capturedConvertedNodes).executeTool(edgeReconnectionTool, variableManager));
                // @formatter:on

                if (edgeReconnectionTool instanceof SourceEdgeEndReconnectionTool) {
                    reconnectionToolBuilder.kind(ReconnectEdgeKind.SOURCE);
                } else if (edgeReconnectionTool instanceof TargetEdgeEndReconnectionTool) {
                    reconnectionToolBuilder.kind(ReconnectEdgeKind.TARGET);
                }

                tools.add(reconnectionToolBuilder.build());
            }
        }

        return tools;
    }

    private LabelDescription getLabelDescription(org.eclipse.sirius.components.view.NodeDescription viewNodeDescription, AQLInterpreter interpreter) {
        Function<VariableManager, String> labelIdProvider = variableManager -> {
            Object parentId = variableManager.get(LabelDescription.OWNER_ID, Object.class).orElse(null);
            return String.valueOf(parentId) + LabelDescription.LABEL_SUFFIX;
        };

        Function<VariableManager, LabelStyleDescription> styleDescriptionProvider = variableManager -> {
            var effectiveStyle = this.findEffectiveStyle(viewNodeDescription, interpreter, variableManager);
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
                    .filter(candidate -> new DomainClassPredicate(Optional.ofNullable(elementDescription.getDomainType()).orElse("")).test(candidate.eClass()))
                    .toList();
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
            semanticElementsProvider = new RelationBasedSemanticElementsProvider(sourceNodeDescriptions.map(NodeDescription::getId).toList());
        }

        Predicate<VariableManager> shouldRenderPredicate = variableManager -> {
            Result result = interpreter.evaluateExpression(variableManager.getVariables(), viewEdgeDescription.getPreconditionExpression());
            return result.asBoolean().orElse(true);
        };

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
                        .toList();
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
                        .toList();
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
                .sourceNodeDescriptions(viewEdgeDescription.getSourceNodeDescriptions().stream().map(converterContext.getConvertedNodes()::get).toList())
                .targetNodeDescriptions(viewEdgeDescription.getTargetNodeDescriptions().stream().map(converterContext.getConvertedNodes()::get).toList())
                .semanticElementsProvider(semanticElementsProvider)
                .shouldRenderPredicate(shouldRenderPredicate)
                .synchronizationPolicy(synchronizationPolicy)
                .sourceNodesProvider(sourceNodesProvider)
                .targetNodesProvider(targetNodesProvider)
                .styleProvider(styleProvider)
                .deleteHandler(this.createDeleteHandler(viewEdgeDescription, converterContext))
                .labelEditHandler(this.createEdgeLabelEditHandler(viewEdgeDescription, converterContext));

        this.getSpecificEdgeLabelDescription(viewEdgeDescription, viewEdgeDescription.getBeginLabelExpression(), "_beginlabel", interpreter).ifPresent(builder::beginLabelDescription);
        this.getSpecificEdgeLabelDescription(viewEdgeDescription, viewEdgeDescription.getLabelExpression(), "_centerlabel", interpreter).ifPresent(builder::centerLabelDescription);
        this.getSpecificEdgeLabelDescription(viewEdgeDescription, viewEdgeDescription.getEndLabelExpression(), "_endlabel", interpreter).ifPresent(builder::endLabelDescription);

        EdgeDescription result = builder.build();
        converterContext.getConvertedEdges().put(viewEdgeDescription, result);
        return result;
        // @formatter:on
    }

    private Function<VariableManager, IStatus> createDeleteHandler(DiagramElementDescription diagramElementDescription, ViewDiagramDescriptionConverterContext converterContext) {
        var capturedConvertedNodes = Map.copyOf(converterContext.getConvertedNodes());
        Function<VariableManager, IStatus> handler = variableManager -> {
            IStatus result;
            DeletionPolicy deletionPolicy = variableManager.get(DeleteFromDiagramEventHandler.DELETION_POLICY, DeletionPolicy.class).orElse(DeletionPolicy.SEMANTIC);
            if (deletionPolicy == DeletionPolicy.GRAPHICAL) {
                this.deleteFromDiagram(variableManager);
                result = new Success();
            } else {
                VariableManager child = variableManager.createChild();
                child.put(CONVERTED_NODES_VARIABLE, capturedConvertedNodes);

                DeleteTool tool = diagramElementDescription.getDeleteTool();
                if (tool != null) {
                    result = new DiagramOperationInterpreter(converterContext.getInterpreter(), this.objectService, this.editService, this.getDiagramContext(variableManager), capturedConvertedNodes)
                            .executeTool(tool, child);
                } else {
                    result = new Failure("No deletion tool configured");
                }
            }
            return result;
        };

        return new IViewNodeDeleteHandler() {
            @Override
            public boolean hasSemanticDeleteTool() {
                return diagramElementDescription.getDeleteTool() != null;
            }

            @Override
            public IStatus apply(VariableManager variableManager) {
                return handler.apply(variableManager);
            }
        };
    }

    private void deleteFromDiagram(VariableManager variableManager) {
        var optionalDiagramContext = variableManager.get(IDiagramContext.DIAGRAM_CONTEXT, DiagramContext.class);
        if (optionalDiagramContext.isPresent()) {
            String elementId = null;
            if (variableManager.get(Node.SELECTED_NODE, Node.class).isPresent()) {
                elementId = variableManager.get(Node.SELECTED_NODE, Node.class).get().getId();
            } else if (variableManager.get(Edge.SELECTED_EDGE, Edge.class).isPresent()) {
                elementId = variableManager.get(Edge.SELECTED_EDGE, Edge.class).get().getId();
            }
            if (elementId != null) {
                ViewDeletionRequest viewDeletionRequest = ViewDeletionRequest.newViewDeletionRequest().elementId(elementId).build();
                optionalDiagramContext.get().getViewDeletionRequests().add(viewDeletionRequest);
            }
        }
    }

    private BiFunction<VariableManager, String, IStatus> createLabelEditHandler(DiagramElementDescription diagramElementDescription, ViewDiagramDescriptionConverterContext converterContext) {
        var capturedConvertedNodes = Map.copyOf(converterContext.getConvertedNodes());
        BiFunction<VariableManager, String, IStatus> handler = (variableManager, newLabel) -> {
            IStatus result;
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put("arg0", newLabel);
            childVariableManager.put("newLabel", newLabel);
            childVariableManager.put(CONVERTED_NODES_VARIABLE, capturedConvertedNodes);
            LabelEditTool tool = diagramElementDescription.getLabelEditTool();
            if (tool != null) {
                result = new DiagramOperationInterpreter(converterContext.getInterpreter(), this.objectService, this.editService, this.getDiagramContext(variableManager), capturedConvertedNodes)
                        .executeTool(tool, childVariableManager);
            } else {
                result = new Failure("No label edition tool configured");
            }
            return result;
        };
        return new IViewNodeLabelEditHandler() {
            @Override
            public IStatus apply(VariableManager variableManager, String newLabel) {
                return handler.apply(variableManager, newLabel);
            }

            @Override
            public boolean hasLabelEditTool() {
                return diagramElementDescription.getLabelEditTool() != null;
            }
        };
    }

    private IEdgeEditLabelHandler createEdgeLabelEditHandler(org.eclipse.sirius.components.view.EdgeDescription edgeDescription, ViewDiagramDescriptionConverterContext converterContext) {
        IEdgeEditLabelHandler handler = (variableManager, edgeLabelKind, newLabel) -> {
            IStatus result;
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put("arg0", newLabel);
            childVariableManager.put("newLabel", newLabel);
            var capturedConvertedNodes = Map.copyOf(converterContext.getConvertedNodes());
            childVariableManager.put(CONVERTED_NODES_VARIABLE, capturedConvertedNodes);

            LabelEditTool tool = null;
            if (edgeLabelKind == EdgeLabelKind.BEGIN_LABEL) {
                tool = edgeDescription.getBeginLabelEditTool();
            } else if (edgeLabelKind == EdgeLabelKind.CENTER_LABEL) {
                tool = edgeDescription.getLabelEditTool();
            } else if (edgeLabelKind == EdgeLabelKind.END_LABEL) {
                tool = edgeDescription.getEndLabelEditTool();
            }

            if (tool != null) {
                result = new DiagramOperationInterpreter(converterContext.getInterpreter(), this.objectService, this.editService, this.getDiagramContext(variableManager), capturedConvertedNodes)
                        .executeTool(tool, childVariableManager);
            } else {
                result = new Failure("No label edition tool configured");
            }
            return result;
        };
        return new IViewEdgeLabelEditHandler() {
            @Override
            public IStatus editLabel(VariableManager variableManager, EdgeLabelKind edgeLabelKind, String newLabel) {
                return handler.editLabel(variableManager, edgeLabelKind, newLabel);
            }

            @Override
            public boolean hasLabelEditTool(EdgeLabelKind labelKind) {
                return switch (labelKind) {
                    case BEGIN_LABEL -> edgeDescription.getBeginLabelEditTool() != null;
                    case CENTER_LABEL -> edgeDescription.getLabelEditTool() != null;
                    case END_LABEL -> edgeDescription.getEndLabelEditTool() != null;
                };
            }
        };
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
        return interpreter.evaluateExpression(variableManager.getVariables(), expression).asString().orElse("");
    }

    private IDiagramContext getDiagramContext(VariableManager variableManager) {
        return variableManager.get(IDiagramContext.DIAGRAM_CONTEXT, IDiagramContext.class).orElse(null);
    }

    private String getToolId(DiagramElementDescription diagramElementDescription, int count) {
        return this.idProvider.apply(diagramElementDescription) + "_tool" + count;
    }

}
