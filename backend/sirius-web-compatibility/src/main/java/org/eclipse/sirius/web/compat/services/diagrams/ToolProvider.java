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
package org.eclipse.sirius.web.compat.services.diagrams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.sirius.business.api.query.IdentifiedElementQuery;
import org.eclipse.sirius.diagram.business.internal.metamodel.description.spec.LayerSpec;
import org.eclipse.sirius.diagram.description.AbstractNodeMapping;
import org.eclipse.sirius.diagram.description.DiagramDescription;
import org.eclipse.sirius.diagram.description.DiagramElementMapping;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.eclipse.sirius.diagram.description.Layer;
import org.eclipse.sirius.diagram.description.tool.ContainerCreationDescription;
import org.eclipse.sirius.diagram.description.tool.EdgeCreationDescription;
import org.eclipse.sirius.diagram.description.tool.NodeCreationDescription;
import org.eclipse.sirius.diagram.description.tool.ToolGroup;
import org.eclipse.sirius.viewpoint.description.tool.AbstractToolDescription;
import org.eclipse.sirius.viewpoint.description.tool.InitEdgeCreationOperation;
import org.eclipse.sirius.viewpoint.description.tool.InitialNodeCreationOperation;
import org.eclipse.sirius.viewpoint.description.tool.InitialOperation;
import org.eclipse.sirius.viewpoint.description.tool.ToolDescription;
import org.eclipse.sirius.web.compat.operations.ChildModelOperationHandler;
import org.eclipse.sirius.web.compat.services.diagrams.tools.ToolImageProvider;
import org.eclipse.sirius.web.compat.services.representations.AQLInterpreterFactory;
import org.eclipse.sirius.web.compat.services.representations.IdentifierProvider;
import org.eclipse.sirius.web.diagrams.description.EdgeDescription;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.diagrams.tools.CreateEdgeTool;
import org.eclipse.sirius.web.diagrams.tools.CreateNodeTool;
import org.eclipse.sirius.web.diagrams.tools.EdgeCandidate;
import org.eclipse.sirius.web.diagrams.tools.ITool;
import org.eclipse.sirius.web.diagrams.tools.ToolSection;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.Status;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.services.api.objects.IObjectService;
import org.springframework.stereotype.Service;

/**
 * This class is used to compute the relevant list of tools for a diagram.
 *
 * @author sbegaudeau
 */
@Service
public class ToolProvider implements IToolProvider {

    private final AQLInterpreterFactory interpreterFactory;

    private final IObjectService objectService;

    private final Registry ePackageRegistry;

    private final IdentifierProvider identifierProvider;

    private final ChildModelOperationHandler childModelOperationHandler;

    public ToolProvider(IObjectService objectService, Registry ePackageRegistry, IdentifierProvider identifierProvider, AQLInterpreterFactory interpreterFactory) {

        this.interpreterFactory = Objects.requireNonNull(interpreterFactory);
        this.objectService = Objects.requireNonNull(objectService);
        this.ePackageRegistry = Objects.requireNonNull(ePackageRegistry);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.childModelOperationHandler = new ChildModelOperationHandler();
    }

    @Override
    public List<ToolSection> getToolSections(Map<UUID, NodeDescription> id2NodeDescriptions, List<EdgeDescription> edgeDescriptions, DiagramDescription siriusDiagramDescription, List<Layer> layers) {

        List<ToolSection> result = new ArrayList<>();
        // @formatter:off
        var siriusToolSections = layers
            .stream()
            .flatMap(layer -> layer.getToolSections().stream())
            .filter(org.eclipse.sirius.diagram.description.tool.ToolSection.class::isInstance)
            .map(org.eclipse.sirius.diagram.description.tool.ToolSection.class::cast)
            .collect(Collectors.toList());
        // @formatter:on
        AQLInterpreter interpreter = this.interpreterFactory.create(siriusDiagramDescription);
        for (var siriusToolSection : siriusToolSections) {
            // @formatter:off
            List<ITool> tools = this.getToolDescriptions(siriusToolSection).stream()
                    .filter(this::isSupported)
                    .map(toolDescription -> this.convertTool(id2NodeDescriptions, siriusDiagramDescription, toolDescription, interpreter))
                    .flatMap(Optional::stream)
                    .collect(Collectors.toList());
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
        return isSupported;
    }

    private ToolSection convertToolSection(org.eclipse.sirius.diagram.description.tool.ToolSection siriusToolSection, List<ITool> tools) {
        String toolSectionLabel = Optional.ofNullable(siriusToolSection.getLabel()).orElse(siriusToolSection.getName());
        // @formatter:off
        return ToolSection.newToolSection(toolSectionLabel)
                .tools(tools)
                .label(toolSectionLabel)
                .imageURL(this.getImagePathFromIconPath(siriusToolSection).orElse("")) //$NON-NLS-1$
                .build();
        // @formatter:on
    }

    private List<AbstractToolDescription> getToolDescriptions(org.eclipse.sirius.diagram.description.tool.ToolSection toolSection) {
        //@formatter:off
        return Stream.concat(
                    toolSection.getOwnedTools().stream(),
                    toolSection.getReusedTools().stream()
                )
                .flatMap(toolEntry -> {
                    if (toolEntry instanceof ToolGroup) {
                        return ((ToolGroup) toolEntry).getTools().stream();
                    }
                    return Stream.of(toolEntry);
                })
                .filter(AbstractToolDescription.class::isInstance)
                .map(AbstractToolDescription.class::cast)
                .collect(Collectors.toList());

        //@formatter:on
    }

    private List<NodeDescription> getParentNodeDescriptions(List<? extends AbstractNodeMapping> nodeMappings, Map<UUID, NodeDescription> id2NodeDescriptions) {
        //@formatter:off
        return nodeMappings.stream()
                .map(AbstractNodeMapping::eContainer)
                .filter(AbstractNodeMapping.class::isInstance)
                .map(AbstractNodeMapping.class::cast)
                .map(this.identifierProvider::getIdentifier)
                .map(UUID::fromString)
                .map(id2NodeDescriptions::get)
                .collect(Collectors.toList());
        //@formatter:on
    }

    private boolean atLeastOneRootMapping(List<? extends AbstractNodeMapping> nodeMappings) {
        //@formatter:off
        return nodeMappings.stream()
                .map(AbstractNodeMapping::eContainer)
                .anyMatch(LayerSpec.class::isInstance);
        //@formatter:on
    }

    private Optional<ITool> convertTool(Map<UUID, NodeDescription> id2NodeDescriptions, org.eclipse.sirius.diagram.description.DiagramDescription siriusDiagramDescription,
            AbstractToolDescription siriusTool, AQLInterpreter interpreter) {
        Optional<ITool> result = Optional.empty();
        if (siriusTool instanceof NodeCreationDescription) {
            NodeCreationDescription nodeCreationTool = (NodeCreationDescription) siriusTool;
            result = Optional.of(this.convertNodeCreationDescription(id2NodeDescriptions, interpreter, nodeCreationTool));
        } else if (siriusTool instanceof ContainerCreationDescription) {
            ContainerCreationDescription containerCreationDescription = (ContainerCreationDescription) siriusTool;
            result = Optional.of(this.convertContainerCreationDescription(id2NodeDescriptions, interpreter, containerCreationDescription));
        } else if (siriusTool instanceof org.eclipse.sirius.viewpoint.description.tool.ToolDescription) {
            org.eclipse.sirius.viewpoint.description.tool.ToolDescription toolDescription = (org.eclipse.sirius.viewpoint.description.tool.ToolDescription) siriusTool;
            result = Optional.of(this.convertToolDescription(id2NodeDescriptions, interpreter, siriusDiagramDescription, toolDescription));
        } else if (siriusTool instanceof EdgeCreationDescription) {
            EdgeCreationDescription edgeCreationDescription = (EdgeCreationDescription) siriusTool;
            result = Optional.of(this.convertEdgeCreationDescription(id2NodeDescriptions, interpreter, edgeCreationDescription));
        }

        return result;
    }

    private CreateNodeTool convertNodeCreationDescription(Map<UUID, NodeDescription> id2NodeDescriptions, AQLInterpreter interpreter, NodeCreationDescription nodeCreationTool) {
        String id = this.identifierProvider.getIdentifier(nodeCreationTool);
        String label = new IdentifiedElementQuery(nodeCreationTool).getLabel();
        String imagePath = new ToolImageProvider(this.objectService, this.ePackageRegistry, nodeCreationTool).get();
        List<NodeDescription> targetDescriptions = this.getParentNodeDescriptions(nodeCreationTool.getNodeMappings(), id2NodeDescriptions);
        // @formatter:off
        return CreateNodeTool.newCreateNodeTool(id)
                .label(label)
                .imageURL(imagePath)
                .handler(this.createNodeCreationHandler(interpreter, nodeCreationTool))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(this.atLeastOneRootMapping(nodeCreationTool.getNodeMappings()))
                .build();
        // @formatter:on
    }

    private CreateNodeTool convertContainerCreationDescription(Map<UUID, NodeDescription> id2NodeDescriptions, AQLInterpreter interpreter, ContainerCreationDescription containerCreationDescription) {
        String id = this.identifierProvider.getIdentifier(containerCreationDescription);
        String label = new IdentifiedElementQuery(containerCreationDescription).getLabel();
        String imagePath = new ToolImageProvider(this.objectService, this.ePackageRegistry, containerCreationDescription).get();
        List<NodeDescription> targetDescriptions = this.getParentNodeDescriptions(containerCreationDescription.getContainerMappings(), id2NodeDescriptions);
        // @formatter:off
        return CreateNodeTool.newCreateNodeTool(id)
                .label(label)
                .imageURL(imagePath)
                .handler(this.createContainerCreationHandler(interpreter, containerCreationDescription))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(this.atLeastOneRootMapping(containerCreationDescription.getContainerMappings()))
                .build();
        // @formatter:on
    }

    private CreateNodeTool convertToolDescription(Map<UUID, NodeDescription> id2NodeDescriptions, AQLInterpreter interpreter, DiagramDescription siriusDiagramDescription,
            ToolDescription toolDescription) {
        String id = this.identifierProvider.getIdentifier(toolDescription);
        String label = new IdentifiedElementQuery(toolDescription).getLabel();
        String imagePath = new ToolImageProvider(this.objectService, this.ePackageRegistry, toolDescription).get();
        // @formatter:off
        siriusDiagramDescription.getNodeMappings();
        List<DiagramElementMapping> mappings = new ArrayList<>(siriusDiagramDescription.getNodeMappings());
        mappings.addAll(siriusDiagramDescription.getContainerMappings());
        List<String> targetDescriptionIds = mappings.stream()
                .map(this.identifierProvider::getIdentifier)
                .collect(Collectors.toList());
        List<NodeDescription> targetDescriptions = targetDescriptionIds.stream()
                .map(UUID::fromString)
                .map(id2NodeDescriptions::get)
                .collect(Collectors.toList());
        return CreateNodeTool.newCreateNodeTool(id)
                .label(label)
                .imageURL(imagePath)
                .handler(this.createGenericToolHandler(interpreter, toolDescription))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(true)
                .build();
        // @formatter:on
    }

    private CreateEdgeTool convertEdgeCreationDescription(Map<UUID, NodeDescription> id2NodeDescriptions, AQLInterpreter interpreter, EdgeCreationDescription edgeCreationDescription) {
        String id = this.identifierProvider.getIdentifier(edgeCreationDescription);
        String label = new IdentifiedElementQuery(edgeCreationDescription).getLabel();
        String imagePath = new ToolImageProvider(this.objectService, this.ePackageRegistry, edgeCreationDescription).get();
        // @formatter:off
        List<EdgeCandidate> edgeCandidates = new ArrayList<>();
        for (EdgeMapping edgeMapping : edgeCreationDescription.getEdgeMappings()) {
            List<NodeDescription> sources = edgeMapping.getSourceMapping().stream()
                    .filter(AbstractNodeMapping.class::isInstance)
                    .map(this.identifierProvider::getIdentifier)
                    .map(UUID::fromString)
                    .map(id2NodeDescriptions::get)
                    .collect(Collectors.toList());
            List<NodeDescription> targets = edgeMapping.getTargetMapping().stream()
                    .filter(AbstractNodeMapping.class::isInstance)
                    .map(this.identifierProvider::getIdentifier)
                    .map(UUID::fromString)
                    .map(id2NodeDescriptions::get)
                    .collect(Collectors.toList());
            EdgeCandidate edgeCandidate = EdgeCandidate.newEdgeCandidate()
                .sources(sources)
                .targets(targets)
                .build();
            edgeCandidates.add(edgeCandidate);
        }
        return CreateEdgeTool.newCreateEdgeTool(id)
                .label(label)
                .imageURL(imagePath)
                .handler(this.createEdgeCreationDescription(interpreter, edgeCreationDescription))
                .edgeCandidates(edgeCandidates)
                .build();
        // @formatter:on
    }

    private Function<VariableManager, Status> createContainerCreationHandler(AQLInterpreter interpreter, ContainerCreationDescription toolDescription) {
        if (toolDescription != null) {
            InitialNodeCreationOperation initialOperation = toolDescription.getInitialOperation();
            return variableManager -> {
                Map<String, Object> variables = variableManager.getVariables();
                return this.childModelOperationHandler.handle(interpreter, variables, List.of(initialOperation.getFirstModelOperations()));
            };
        } else {
            return variableManager -> Status.OK;
        }
    }

    private Function<VariableManager, Status> createNodeCreationHandler(AQLInterpreter interpreter, NodeCreationDescription toolDescription) {
        if (toolDescription != null) {
            InitialNodeCreationOperation initialOperation = toolDescription.getInitialOperation();
            return variableManager -> {
                Map<String, Object> variables = variableManager.getVariables();
                return this.childModelOperationHandler.handle(interpreter, variables, List.of(initialOperation.getFirstModelOperations()));
            };
        } else {
            return variableManager -> Status.OK;
        }
    }

    private Function<VariableManager, Status> createGenericToolHandler(AQLInterpreter interpreter, org.eclipse.sirius.viewpoint.description.tool.ToolDescription toolDescription) {
        if (toolDescription != null) {
            InitialOperation initialOperation = toolDescription.getInitialOperation();
            return variableManager -> {
                Map<String, Object> variables = variableManager.getVariables();
                return this.childModelOperationHandler.handle(interpreter, variables, List.of(initialOperation.getFirstModelOperations()));
            };
        } else {
            return variableManager -> Status.OK;
        }
    }

    private Function<VariableManager, Status> createEdgeCreationDescription(AQLInterpreter interpreter, EdgeCreationDescription edgeCreationDescription) {
        if (edgeCreationDescription != null) {
            InitEdgeCreationOperation initialOperation = edgeCreationDescription.getInitialOperation();
            return variableManager -> {
                Map<String, Object> variables = variableManager.getVariables();
                return this.childModelOperationHandler.handle(interpreter, variables, List.of(initialOperation.getFirstModelOperations()));
            };
        } else {
            return variableManager -> Status.OK;
        }
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
        if (!iconPath.startsWith("/")) { //$NON-NLS-1$
            path = "/" + iconPath; //$NON-NLS-1$
        }

        int index = path.indexOf('/', 1);
        if (index != -1) {
            path = path.substring(index);
        }
        return path;
    }
}
