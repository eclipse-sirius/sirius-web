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
package org.eclipse.sirius.components.compatibility.services.diagrams;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

import org.eclipse.sirius.business.api.query.IdentifiedElementQuery;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.compatibility.api.IAQLInterpreterFactory;
import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.compatibility.api.IModelOperationHandlerSwitchProvider;
import org.eclipse.sirius.components.compatibility.services.SelectModelElementVariableProvider;
import org.eclipse.sirius.components.compatibility.services.diagrams.api.IToolImageProvider;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.tools.ITool;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnTwoDiagramElementsCandidate;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnTwoDiagramElementsTool;
import org.eclipse.sirius.components.diagrams.tools.ToolSection;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.diagram.description.AbstractNodeMapping;
import org.eclipse.sirius.diagram.description.AdditionalLayer;
import org.eclipse.sirius.diagram.description.ContainerMapping;
import org.eclipse.sirius.diagram.description.DiagramDescription;
import org.eclipse.sirius.diagram.description.DiagramElementMapping;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.eclipse.sirius.diagram.description.Layer;
import org.eclipse.sirius.diagram.description.tool.ContainerCreationDescription;
import org.eclipse.sirius.diagram.description.tool.DeleteElementDescription;
import org.eclipse.sirius.diagram.description.tool.EdgeCreationDescription;
import org.eclipse.sirius.diagram.description.tool.NodeCreationDescription;
import org.eclipse.sirius.diagram.description.tool.ToolGroup;
import org.eclipse.sirius.viewpoint.description.tool.AbstractToolDescription;
import org.eclipse.sirius.viewpoint.description.tool.InitEdgeCreationOperation;
import org.eclipse.sirius.viewpoint.description.tool.InitialNodeCreationOperation;
import org.eclipse.sirius.viewpoint.description.tool.InitialOperation;
import org.eclipse.sirius.viewpoint.description.tool.OperationAction;
import org.eclipse.sirius.viewpoint.description.tool.ToolDescription;
import org.springframework.stereotype.Service;

/**
 * This class is used to compute the relevant list of tools for a diagram.
 *
 * @author sbegaudeau
 */
@Service
public class ToolProvider implements IToolProvider {

    /**
     * The name of the compatibility variable used to store and retrieve the edge source from a variable manager.
     */
    public static final String EDGE_SOURCE = "source";

    /**
     * The name of the compatibility variable used to store and retrieve the edge target from a variable manager.
     */
    public static final String EDGE_TARGET = "target";

    /**
     * The name of the compatibility variable used to store and retrieve the semantic element on which a node or
     * container creation tool has been invoked.
     */
    public static final String CONTAINER = "container";

    /**
     * The name of the compatibility variable used to store and retrieve the graphical element on which a node or
     * container creation tool has been invoked.
     */
    public static final String CONTAINER_VIEW = "containerView";

    /**
     * The name of the compatibility variable used to store and retrieve the graphical element on which a delete element
     * tool has been invoked.
     */
    public static final String ELEMENT_VIEW = "elementView";

    /**
     * The name of the compatibility variable used to store and retrieve the semantic element on which a generic tool
     * has been invoked.
     */
    public static final String ELEMENT = "element";

    private final IAQLInterpreterFactory interpreterFactory;

    private final IIdentifierProvider identifierProvider;

    private final IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider;

    private final IToolImageProvider toolImageProvider;

    public ToolProvider(IIdentifierProvider identifierProvider, IAQLInterpreterFactory interpreterFactory, IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider,
            IToolImageProvider toolImageProvider) {
        this.interpreterFactory = Objects.requireNonNull(interpreterFactory);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.modelOperationHandlerSwitchProvider = Objects.requireNonNull(modelOperationHandlerSwitchProvider);
        this.toolImageProvider = Objects.requireNonNull(toolImageProvider);
    }

    @Override
    public List<ToolSection> getToolSections(Map<String, NodeDescription> id2NodeDescriptions, List<EdgeDescription> edgeDescriptions, DiagramDescription siriusDiagramDescription, List<Layer> layers) {

        List<ToolSection> result = new ArrayList<>();
        var siriusToolSections = layers
                .stream()
                .flatMap(layer -> layer.getToolSections().stream())
                .filter(org.eclipse.sirius.diagram.description.tool.ToolSection.class::isInstance)
                .map(org.eclipse.sirius.diagram.description.tool.ToolSection.class::cast)
                .toList();

        AQLInterpreter interpreter = this.interpreterFactory.create(siriusDiagramDescription);
        for (var siriusToolSection : siriusToolSections) {
            // @formatter:off
            List<ITool> tools = this.getToolDescriptions(siriusToolSection).stream()
                    .filter(this::isSupported)
                    .map(toolDescription -> this.convertTool(id2NodeDescriptions, edgeDescriptions, siriusDiagramDescription, toolDescription, interpreter))
                    .flatMap(Optional::stream)
                    .toList();
            // @formatter:on
            if (!tools.isEmpty()) {
                ToolSection toolSection = this.convertToolSection(siriusToolSection, tools);
                result.add(toolSection);
            }
        }
        return result;
    }

    private boolean isSupported(AbstractToolDescription toolDescription) {
        boolean isSupported = toolDescription instanceof NodeCreationDescription;
        isSupported = isSupported || toolDescription instanceof ContainerCreationDescription;
        isSupported = isSupported || toolDescription instanceof EdgeCreationDescription;
        isSupported = isSupported || toolDescription instanceof ToolDescription;
        isSupported = isSupported || toolDescription instanceof DeleteElementDescription;
        isSupported = isSupported || toolDescription instanceof OperationAction;
        return isSupported;
    }

    private ToolSection convertToolSection(org.eclipse.sirius.diagram.description.tool.ToolSection siriusToolSection, List<ITool> tools) {
        String toolSectionLabel = Optional.ofNullable(siriusToolSection.getLabel()).orElse(siriusToolSection.getName());
        // @formatter:off
        return ToolSection.newToolSection(toolSectionLabel)
                .tools(tools)
                .label(toolSectionLabel)
                .imageURL(this.getImagePathFromIconPath(siriusToolSection).orElse(""))
                .build();
        // @formatter:on
    }

    private List<AbstractToolDescription> getToolDescriptions(org.eclipse.sirius.diagram.description.tool.ToolSection toolSection) {
        return Stream.concat(toolSection.getOwnedTools().stream(), toolSection.getReusedTools().stream())
                .flatMap(toolEntry -> {
                    if (toolEntry instanceof ToolGroup) {
                        return ((ToolGroup) toolEntry).getTools().stream();
                    }
                    return Stream.of(toolEntry);
                })
                .filter(AbstractToolDescription.class::isInstance)
                .map(AbstractToolDescription.class::cast)
                .toList();
    }

    private List<IDiagramElementDescription> getParentNodeDescriptions(List<? extends AbstractNodeMapping> nodeMappings, Map<String, NodeDescription> id2NodeDescriptions) {
        //@formatter:off
        return nodeMappings.stream()
                .map(AbstractNodeMapping::eContainer)
                .filter(AbstractNodeMapping.class::isInstance)
                .map(AbstractNodeMapping.class::cast)
                .map(this.identifierProvider::getIdentifier)
                .map(UUID::fromString)
                .map(UUID::toString)
                .map(id2NodeDescriptions::get)
                .filter(IDiagramElementDescription.class::isInstance)
                .map(IDiagramElementDescription.class::cast)
                .toList();
        //@formatter:on
    }

    private boolean atLeastOneRootMapping(List<? extends AbstractNodeMapping> nodeMappings) {
        //@formatter:off
        return nodeMappings.stream()
                .map(AbstractNodeMapping::eContainer)
                .anyMatch(Layer.class::isInstance);
        //@formatter:on
    }

    private Optional<ITool> convertTool(Map<String, NodeDescription> id2NodeDescriptions, List<EdgeDescription> edgeDescriptions,
            org.eclipse.sirius.diagram.description.DiagramDescription siriusDiagramDescription, AbstractToolDescription siriusTool, AQLInterpreter interpreter) {
        Optional<ITool> result = Optional.empty();
        if (siriusTool instanceof NodeCreationDescription) {
            NodeCreationDescription nodeCreationTool = (NodeCreationDescription) siriusTool;
            result = Optional.of(this.convertNodeCreationDescription(id2NodeDescriptions, interpreter, nodeCreationTool));
        } else if (siriusTool instanceof ContainerCreationDescription) {
            ContainerCreationDescription containerCreationDescription = (ContainerCreationDescription) siriusTool;
            result = Optional.of(this.convertContainerCreationDescription(id2NodeDescriptions, interpreter, containerCreationDescription));
        } else if (siriusTool instanceof org.eclipse.sirius.viewpoint.description.tool.ToolDescription) {
            org.eclipse.sirius.viewpoint.description.tool.ToolDescription toolDescription = (org.eclipse.sirius.viewpoint.description.tool.ToolDescription) siriusTool;
            result = Optional.of(this.convertToolDescription(id2NodeDescriptions, edgeDescriptions, interpreter, siriusDiagramDescription, toolDescription));
        } else if (siriusTool instanceof EdgeCreationDescription) {
            EdgeCreationDescription edgeCreationDescription = (EdgeCreationDescription) siriusTool;
            result = Optional.of(this.convertEdgeCreationDescription(id2NodeDescriptions, interpreter, edgeCreationDescription));
        } else if (siriusTool instanceof DeleteElementDescription) {
            DeleteElementDescription deleteElementDescription = (DeleteElementDescription) siriusTool;
            result = Optional.of(this.convertDeleteElementDescription(id2NodeDescriptions, interpreter, deleteElementDescription));
        } else if (siriusTool instanceof OperationAction) {
            OperationAction operationAction = (OperationAction) siriusTool;
            result = Optional.of(this.convertOperationAction(id2NodeDescriptions, edgeDescriptions, interpreter, siriusDiagramDescription, operationAction));
        }

        return result;
    }

    private SingleClickOnDiagramElementTool convertNodeCreationDescription(Map<String, NodeDescription> id2NodeDescriptions, AQLInterpreter interpreter, NodeCreationDescription nodeCreationTool) {
        String id = this.identifierProvider.getIdentifier(nodeCreationTool);
        String label = new IdentifiedElementQuery(nodeCreationTool).getLabel();
        String imagePath = this.toolImageProvider.getImage(nodeCreationTool);
        List<IDiagramElementDescription> targetDescriptions = this.getParentNodeDescriptions(nodeCreationTool.getNodeMappings(), id2NodeDescriptions);
        var selectModelElementVariableOpt = new SelectModelElementVariableProvider().getSelectModelElementVariable(nodeCreationTool.getVariable());
        String selectionDescriptionId = null;
        if (selectModelElementVariableOpt.isPresent()) {
            selectionDescriptionId = this.identifierProvider.getIdentifier(selectModelElementVariableOpt.get());
        }
        // @formatter:off
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(id)
                .label(label)
                .imageURL(imagePath)
                .handler(this.createNodeCreationHandler(interpreter, nodeCreationTool))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(this.atLeastOneRootMapping(nodeCreationTool.getNodeMappings()))
                .selectionDescriptionId(selectionDescriptionId)
                .build();
        // @formatter:on
    }

    private SingleClickOnDiagramElementTool convertContainerCreationDescription(Map<String, NodeDescription> id2NodeDescriptions, AQLInterpreter interpreter,
            ContainerCreationDescription containerCreationDescription) {
        String id = this.identifierProvider.getIdentifier(containerCreationDescription);
        String label = new IdentifiedElementQuery(containerCreationDescription).getLabel();
        String imagePath = this.toolImageProvider.getImage(containerCreationDescription);
        List<IDiagramElementDescription> targetDescriptions = this.getParentNodeDescriptions(containerCreationDescription.getContainerMappings(), id2NodeDescriptions);
        var selectModelElementVariableOpt = new SelectModelElementVariableProvider().getSelectModelElementVariable(containerCreationDescription.getVariable());
        String selectionDescriptionId = null;
        if (selectModelElementVariableOpt.isPresent()) {
            selectionDescriptionId = this.identifierProvider.getIdentifier(selectModelElementVariableOpt.get());
        }
        // @formatter:off
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(id)
                .label(label)
                .imageURL(imagePath)
                .handler(this.createContainerCreationHandler(interpreter, containerCreationDescription))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(this.atLeastOneRootMapping(containerCreationDescription.getContainerMappings()))
                .selectionDescriptionId(selectionDescriptionId)
                .build();
        // @formatter:on
    }

    private SingleClickOnDiagramElementTool convertToolDescription(Map<String, NodeDescription> id2NodeDescriptions, List<EdgeDescription> edgeDescriptions, AQLInterpreter interpreter,
            DiagramDescription siriusDiagramDescription, ToolDescription toolDescription) {
        String id = this.identifierProvider.getIdentifier(toolDescription);
        String label = new IdentifiedElementQuery(toolDescription).getLabel();
        String imagePath = this.toolImageProvider.getImage(toolDescription);

        List<DiagramElementMapping> mappings = this.getAllDiagramElementMappings(siriusDiagramDescription);

        // @formatter:off
        List<String> targetDescriptionIds = mappings.stream()
                .map(this.identifierProvider::getIdentifier)
                .toList();

        List<IDiagramElementDescription> targetNodeDescriptions = targetDescriptionIds.stream()
                .map(UUID::fromString)
                .map(UUID::toString)
                .map(id2NodeDescriptions::get)
                .filter(Objects::nonNull)
                .filter(IDiagramElementDescription.class::isInstance)
                .map(IDiagramElementDescription.class::cast)
                .toList();

        List<IDiagramElementDescription> targetEdgeDescriptions = targetDescriptionIds.stream()
                .map(UUID::fromString)
                .map(UUID::toString)
                .flatMap(targetDescriptionUUID -> edgeDescriptions.stream().filter(edgeDescription -> edgeDescription.getId().equals(targetDescriptionUUID)))
                .filter(Objects::nonNull)
                .filter(IDiagramElementDescription.class::isInstance)
                .map(IDiagramElementDescription.class::cast)
                .toList();

        List<IDiagramElementDescription> targetDescriptions = Stream.concat(targetNodeDescriptions.stream(), targetEdgeDescriptions.stream()).toList();

        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(id)
                .label(label)
                .imageURL(imagePath)
                .handler(this.createGenericToolHandler(interpreter, toolDescription))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(true)
                .build();
        // @formatter:on
    }

    private SingleClickOnDiagramElementTool convertOperationAction(Map<String, NodeDescription> id2NodeDescriptions, List<EdgeDescription> edgeDescriptions, AQLInterpreter interpreter,
            DiagramDescription siriusDiagramDescription, OperationAction operationAction) {
        String id = this.identifierProvider.getIdentifier(operationAction);
        String label = new IdentifiedElementQuery(operationAction).getLabel();
        String imagePath = this.toolImageProvider.getImage(operationAction);

        List<DiagramElementMapping> mappings = this.getAllDiagramElementMappings(siriusDiagramDescription);

        // @formatter:off
        List<String> targetDescriptionIds = mappings.stream()
                .map(this.identifierProvider::getIdentifier)
                .toList();

        List<IDiagramElementDescription> targetNodeDescriptions = targetDescriptionIds.stream()
                .map(UUID::fromString)
                .map(UUID::toString)
                .map(id2NodeDescriptions::get)
                .filter(IDiagramElementDescription.class::isInstance)
                .map(IDiagramElementDescription.class::cast)
                .toList();

        List<IDiagramElementDescription> targetEdgeDescriptions = targetDescriptionIds.stream()
                .map(UUID::fromString)
                .map(UUID::toString)
                .flatMap(targetDescriptionUUID -> edgeDescriptions.stream().filter(edgeDescription -> edgeDescription.getId().equals(targetDescriptionUUID)))
                .filter(IDiagramElementDescription.class::isInstance)
                .map(IDiagramElementDescription.class::cast)
                .toList();

        List<IDiagramElementDescription> targetDescriptions = Stream.concat(targetNodeDescriptions.stream(), targetEdgeDescriptions.stream()).toList();

        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(id)
                .label(label)
                .imageURL(imagePath)
                .handler(this.createOperationActionHandler(interpreter, operationAction))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(true)
                .build();
        // @formatter:on
    }

    private List<DiagramElementMapping> getAllDiagramElementMappings(DiagramDescription siriusDiagramDescription) {
        List<DiagramElementMapping> mappings = new ArrayList<>(siriusDiagramDescription.getDefaultLayer().getNodeMappings());
        mappings.addAll(siriusDiagramDescription.getDefaultLayer().getEdgeMappings());
        mappings.addAll(siriusDiagramDescription.getDefaultLayer().getContainerMappings());
        mappings.addAll(this.getAllSubMappings(siriusDiagramDescription.getDefaultLayer().getContainerMappings()));
        for (AdditionalLayer additionalLayer : siriusDiagramDescription.getAdditionalLayers()) {
            mappings.addAll(additionalLayer.getNodeMappings());
            mappings.addAll(additionalLayer.getEdgeMappings());
            mappings.addAll(additionalLayer.getContainerMappings());
            mappings.addAll(this.getAllSubMappings(additionalLayer.getContainerMappings()));
        }
        return mappings;
    }

    private Collection<? extends DiagramElementMapping> getAllSubMappings(List<ContainerMapping> containerMappings) {
        List<DiagramElementMapping> result = new ArrayList<>();
        for (ContainerMapping containerMapping : containerMappings) {
            result.addAll(containerMapping.getSubNodeMappings());
            result.addAll(containerMapping.getBorderedNodeMappings());
            result.addAll(containerMapping.getSubContainerMappings());
            result.addAll(this.getAllSubMappings(containerMapping.getSubContainerMappings()));
        }
        return result;
    }

    private SingleClickOnTwoDiagramElementsTool convertEdgeCreationDescription(Map<String, NodeDescription> id2NodeDescriptions, AQLInterpreter interpreter,
            EdgeCreationDescription edgeCreationDescription) {
        String id = this.identifierProvider.getIdentifier(edgeCreationDescription);
        String label = new IdentifiedElementQuery(edgeCreationDescription).getLabel();
        String imagePath = this.toolImageProvider.getImage(edgeCreationDescription);

        List<SingleClickOnTwoDiagramElementsCandidate> edgeCandidates = new ArrayList<>();
        for (EdgeMapping edgeMapping : edgeCreationDescription.getEdgeMappings()) {
            List<NodeDescription> sources = edgeMapping.getSourceMapping().stream()
                    .filter(AbstractNodeMapping.class::isInstance)
                    .map(this.identifierProvider::getIdentifier)
                    .map(UUID::fromString)
                    .map(UUID::toString)
                    .map(id2NodeDescriptions::get)
                    .toList();
            List<NodeDescription> targets = edgeMapping.getTargetMapping().stream()
                    .filter(AbstractNodeMapping.class::isInstance)
                    .map(this.identifierProvider::getIdentifier)
                    .map(UUID::fromString)
                    .map(UUID::toString)
                    .map(id2NodeDescriptions::get)
                    .toList();
            SingleClickOnTwoDiagramElementsCandidate edgeCandidate = SingleClickOnTwoDiagramElementsCandidate.newSingleClickOnTwoDiagramElementsCandidate()
                    .sources(sources)
                    .targets(targets)
                    .build();
            edgeCandidates.add(edgeCandidate);
        }

        return SingleClickOnTwoDiagramElementsTool.newSingleClickOnTwoDiagramElementsTool(id)
                .label(label)
                .imageURL(imagePath)
                .handler(this.createEdgeCreationHandler(interpreter, edgeCreationDescription))
                .candidates(edgeCandidates)
                .build();
    }

    private SingleClickOnDiagramElementTool convertDeleteElementDescription(Map<String, NodeDescription> id2NodeDescriptions, AQLInterpreter interpreter,
            DeleteElementDescription deleteElementDescription) {
        String id = this.identifierProvider.getIdentifier(deleteElementDescription);
        String label = new IdentifiedElementQuery(deleteElementDescription).getLabel();
        String imagePath = this.toolImageProvider.getImage(deleteElementDescription);

        List<DiagramElementMapping> mappings = deleteElementDescription.getMappings();

        // @formatter:off
        List<String> targetDescriptionIds = mappings.stream()
                .map(this.identifierProvider::getIdentifier)
                .toList();
        List<IDiagramElementDescription> targetDescriptions = targetDescriptionIds.stream()
                .map(UUID::fromString)
                .map(UUID::toString)
                .map(id2NodeDescriptions::get)
                .filter(Objects::nonNull)
                .filter(IDiagramElementDescription.class::isInstance)
                .map(IDiagramElementDescription.class::cast)
                .toList();
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(id)
                .label(label)
                .imageURL(imagePath)
                .handler(this.createDeleteToolHandler(interpreter, deleteElementDescription))
                .appliesToDiagramRoot(false)
                .targetDescriptions(targetDescriptions)
                .build();
        // @formatter:on
    }

    private Function<VariableManager, IStatus> createContainerCreationHandler(AQLInterpreter interpreter, ContainerCreationDescription toolDescription) {
        if (toolDescription != null) {
            InitialNodeCreationOperation initialOperation = toolDescription.getInitialOperation();
            return variableManager -> {
                Map<String, Object> variables = variableManager.getVariables();
                // Provide compatibility aliases for this variable
                variables.put(CONTAINER, variables.get(VariableManager.SELF));
                if (variables.get(Node.SELECTED_NODE) != null) {
                    variables.put(CONTAINER_VIEW, variables.get(Node.SELECTED_NODE));
                } else {
                    Optional.ofNullable(variables.get(IDiagramContext.DIAGRAM_CONTEXT))
                            .filter(IDiagramContext.class::isInstance)
                            .map(IDiagramContext.class::cast)
                            .ifPresent(diagramContext ->  variables.put(CONTAINER_VIEW, diagramContext.getDiagram()));
                }
                var selectModelelementVariableOpt = new SelectModelElementVariableProvider().getSelectModelElementVariable(toolDescription.getVariable());
                if (selectModelelementVariableOpt.isPresent()) {
                    variables.put(selectModelelementVariableOpt.get().getName(), variables.get(SingleClickOnDiagramElementTool.SELECTED_OBJECT));
                }
                var modelOperationHandlerSwitch = this.modelOperationHandlerSwitchProvider.getModelOperationHandlerSwitch(interpreter);
                return modelOperationHandlerSwitch.apply(initialOperation.getFirstModelOperations())
                        .map(handler -> handler.handle(variables))
                        .orElse(new Failure(""));
            };
        } else {
            return variableManager -> new Success();
        }
    }

    private Function<VariableManager, IStatus> createNodeCreationHandler(AQLInterpreter interpreter, NodeCreationDescription toolDescription) {
        if (toolDescription != null) {
            InitialNodeCreationOperation initialOperation = toolDescription.getInitialOperation();
            return variableManager -> {
                Map<String, Object> variables = variableManager.getVariables();
                // Provide compatibility aliases for this variable
                variables.put(CONTAINER, variables.get(VariableManager.SELF));
                if (variables.get(Node.SELECTED_NODE) != null) {
                    variables.put(CONTAINER_VIEW, variables.get(Node.SELECTED_NODE));
                } else {
                    Optional.ofNullable(variables.get(IDiagramContext.DIAGRAM_CONTEXT))
                            .filter(IDiagramContext.class::isInstance)
                            .map(IDiagramContext.class::cast)
                            .ifPresent(diagramContext -> variables.put(CONTAINER_VIEW, diagramContext.getDiagram()));
                }

                var selectModelelementVariableOpt = new SelectModelElementVariableProvider().getSelectModelElementVariable(toolDescription.getVariable());
                if (selectModelelementVariableOpt.isPresent()) {
                    variables.put(selectModelelementVariableOpt.get().getName(), variables.get(SingleClickOnDiagramElementTool.SELECTED_OBJECT));
                }
                var modelOperationHandlerSwitch = this.modelOperationHandlerSwitchProvider.getModelOperationHandlerSwitch(interpreter);
                return modelOperationHandlerSwitch.apply(initialOperation.getFirstModelOperations())
                        .map(handler -> handler.handle(variables))
                        .orElse(new Failure(""));
            };
        } else {
            return variableManager -> new Success();
        }
    }

    private Function<VariableManager, IStatus> createGenericToolHandler(AQLInterpreter interpreter, org.eclipse.sirius.viewpoint.description.tool.ToolDescription toolDescription) {
        if (toolDescription != null) {
            InitialOperation initialOperation = toolDescription.getInitialOperation();
            return variableManager -> {
                Map<String, Object> variables = variableManager.getVariables();
                // Provide compatibility aliases for this variable
                variables.put(ELEMENT, variables.get(VariableManager.SELF));
                var modelOperationHandlerSwitch = this.modelOperationHandlerSwitchProvider.getModelOperationHandlerSwitch(interpreter);
                return modelOperationHandlerSwitch.apply(initialOperation.getFirstModelOperations())
                        .map(handler -> handler.handle(variables))
                        .orElse(new Failure(""));
            };
        } else {
            return variableManager -> new Success();
        }
    }

    private Function<VariableManager, IStatus> createOperationActionHandler(AQLInterpreter interpreter, OperationAction operationAction) {
        if (operationAction != null) {
            InitialOperation initialOperation = operationAction.getInitialOperation();
            return variableManager -> {
                Map<String, Object> variables = variableManager.getVariables();
                // Provide compatibility aliases for this variable
                variables.put(ELEMENT, variables.get(VariableManager.SELF));
                var modelOperationHandlerSwitch = this.modelOperationHandlerSwitchProvider.getModelOperationHandlerSwitch(interpreter);
                return modelOperationHandlerSwitch.apply(initialOperation.getFirstModelOperations())
                        .map(handler -> handler.handle(variables))
                        .orElse(new Failure(""));
            };
        } else {
            return variableManager -> new Success();
        }
    }

    private Function<VariableManager, IStatus> createEdgeCreationHandler(AQLInterpreter interpreter, EdgeCreationDescription edgeCreationDescription) {
        if (edgeCreationDescription != null) {
            InitEdgeCreationOperation initialOperation = edgeCreationDescription.getInitialOperation();
            return variableManager -> {
                Map<String, Object> variables = variableManager.getVariables();
                // Provide compatibility aliases for these variables
                variables.put(VariableManager.SELF, variables.get(EdgeDescription.SEMANTIC_EDGE_SOURCE));
                variables.put(EDGE_SOURCE, variables.get(EdgeDescription.SEMANTIC_EDGE_SOURCE));
                variables.put(EDGE_TARGET, variables.get(EdgeDescription.SEMANTIC_EDGE_TARGET));
                var modelOperationHandlerSwitch = this.modelOperationHandlerSwitchProvider.getModelOperationHandlerSwitch(interpreter);
                return modelOperationHandlerSwitch.apply(initialOperation.getFirstModelOperations())
                        .map(handler -> handler.handle(variables))
                        .orElse(new Failure(""));
            };
        } else {
            return variableManager -> new Success();
        }
    }

    private Function<VariableManager, IStatus> createDeleteToolHandler(AQLInterpreter interpreter, DeleteElementDescription deleteElementDescription) {
        if (deleteElementDescription != null) {
            InitialOperation initialOperation = deleteElementDescription.getInitialOperation();
            return variableManager -> {
                Map<String, Object> variables = variableManager.getVariables();
                // Provide compatibility aliases for this variable
                variables.put(ELEMENT, variables.get(VariableManager.SELF));
                Object selectedNode = variables.get(Node.SELECTED_NODE);
                if (selectedNode instanceof Node) {
                    variables.put(ELEMENT_VIEW, selectedNode);

                    Optional.ofNullable(variables.get(IDiagramContext.DIAGRAM_CONTEXT))
                            .filter(IDiagramContext.class::isInstance)
                            .map(IDiagramContext.class::cast)
                            .ifPresent(diagramContext -> variables.put(CONTAINER_VIEW, this.getParentNode((Node) selectedNode, diagramContext.getDiagram())));
                }
                var modelOperationHandlerSwitch = this.modelOperationHandlerSwitchProvider.getModelOperationHandlerSwitch(interpreter);
                return modelOperationHandlerSwitch.apply(initialOperation.getFirstModelOperations()).map(handler -> {
                    return handler.handle(variables);
                }).orElse(new Failure(""));
            };
        } else {
            return variableManager -> new Success();
        }
    }

    private Object getParentNode(Node node, Diagram diagram) {
        Object parentNode = null;
        if (node != null && diagram != null) {
            List<Node> nodes = diagram.getNodes();
            if (nodes.contains(node)) {
                parentNode = diagram;
            } else {
                // @formatter:off
                parentNode = nodes.stream()
                        .map(subNode -> this.getParentNode(node, subNode))
                        .filter(Objects::nonNull)
                        .findFirst()
                        .orElse(null);
                // @formatter:on
            }
        }
        return parentNode;
    }

    private Node getParentNode(Node node, Node nodeContainer) {
        List<Node> nodes = nodeContainer.getChildNodes();
        if (nodes.contains(node)) {
            return nodeContainer;
        }
        // @formatter:off
        return nodes.stream()
                .map(subNode -> this.getParentNode(node, subNode))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
        // @formatter:on
    }

    private Optional<String> getImagePathFromIconPath(org.eclipse.sirius.diagram.description.tool.ToolSection toolSection) {
        var optionalIconPath = Optional.ofNullable(toolSection.getIcon());

        // @formatter:off
        return optionalIconPath
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .filter(iconPath -> !iconPath.isBlank())
                .map(this::normalize);
        // @formatter:on
    }

    private String normalize(String iconPath) {
        String path = iconPath;
        if (!iconPath.startsWith("/")) {
            path = "/" + iconPath;
        }

        int index = path.indexOf('/', 1);
        if (index != -1) {
            path = path.substring(index);
        }
        return path;
    }
}
