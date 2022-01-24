/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.diagram.business.internal.metamodel.helper.LayerHelper;
import org.eclipse.sirius.diagram.description.AdditionalLayer;
import org.eclipse.sirius.diagram.description.ContainerMappingImport;
import org.eclipse.sirius.diagram.description.DiagramDescription;
import org.eclipse.sirius.diagram.description.Layer;
import org.eclipse.sirius.diagram.description.tool.ContainerDropDescription;
import org.eclipse.sirius.web.compat.api.IModelOperationHandlerSwitchProvider;
import org.eclipse.sirius.web.compat.diagrams.AbstractNodeMappingConverter;
import org.eclipse.sirius.web.compat.diagrams.EdgeMappingConverter;
import org.eclipse.sirius.web.compat.diagrams.ToolConverter;
import org.eclipse.sirius.web.core.api.IEditService;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription.Builder;
import org.eclipse.sirius.web.diagrams.description.EdgeDescription;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.Failure;
import org.eclipse.sirius.web.representations.IStatus;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramContext;
import org.springframework.stereotype.Service;

/**
 * Used to populate both the nodeDescriptions, the edgeDescriptions and the tool sections of the diagram description
 * builder.
 *
 * @author sbegaudeau
 */
@Service
public class DiagramDescriptionNodeAndEdgeDescriptionsPopulator implements IDiagramDescriptionPopulator {

    private static final String NEW_VIEW_CONTAINER = "newViewContainer"; //$NON-NLS-1$

    private static final String NEW_CONTAINER = "newContainer"; //$NON-NLS-1$

    private final IToolProvider toolProvider;

    private final AbstractNodeMappingConverter abstractNodeMappingConverter;

    private final EdgeMappingConverter edgeMappingConverter;

    private final IEditService editService;

    private final IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider;

    private final IObjectService objectService;

    public DiagramDescriptionNodeAndEdgeDescriptionsPopulator(IToolProvider toolProvider, AbstractNodeMappingConverter abstractNodeMappingConverter, EdgeMappingConverter edgeMappingConverter,
            IEditService editService, IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider, IObjectService objectService) {
        this.toolProvider = Objects.requireNonNull(toolProvider);
        this.abstractNodeMappingConverter = Objects.requireNonNull(abstractNodeMappingConverter);
        this.edgeMappingConverter = Objects.requireNonNull(edgeMappingConverter);
        this.editService = Objects.requireNonNull(editService);
        this.modelOperationHandlerSwitchProvider = Objects.requireNonNull(modelOperationHandlerSwitchProvider);
        this.objectService = Objects.requireNonNull(objectService);
    }

    @Override
    public Builder populate(Builder builder, DiagramDescription siriusDiagramDescription, AQLInterpreter interpreter) {
        Map<UUID, NodeDescription> id2NodeDescriptions = new HashMap<>();

        // @formatter:off
        List<Layer> layers = LayerHelper.getAllLayers(siriusDiagramDescription).stream()
                .filter(this::isEnabledByDefault)
                .collect(Collectors.toList());

        List<NodeDescription> containerDescriptions = layers.stream()
                .flatMap(layer -> layer.getContainerMappings().stream().filter(containerMapping -> !(containerMapping instanceof ContainerMappingImport)))
                .map(containerMapping -> this.abstractNodeMappingConverter.convert(containerMapping, interpreter, id2NodeDescriptions))
                .collect(Collectors.toList());
        // @formatter:on

        // @formatter:off
        List<NodeDescription> nodeDescriptions = layers.stream()
                .flatMap(layer -> layer.getNodeMappings().stream())
                .map(nodeMapping -> this.abstractNodeMappingConverter.convert(nodeMapping, interpreter, id2NodeDescriptions))
                .collect(Collectors.toList());
        // @formatter:on
        nodeDescriptions.addAll(containerDescriptions);

        // @formatter:off
        List<EdgeDescription> edgeDescriptions = layers.stream()
                .flatMap(layer -> layer.getEdgeMappings().stream())
                .map(edgeMapping -> this.edgeMappingConverter.convert(edgeMapping, interpreter, id2NodeDescriptions))
                .collect(Collectors.toList());
        // @formatter:on

        Function<VariableManager, IStatus> dropHandler = this.getDropHandler(siriusDiagramDescription, interpreter);

        // @formatter:off
        return builder.nodeDescriptions(nodeDescriptions)
                .edgeDescriptions(edgeDescriptions)
                .dropHandler(dropHandler)
                .toolSections(this.toolProvider.getToolSections(id2NodeDescriptions, edgeDescriptions, siriusDiagramDescription, layers));
        // @formatter:on
    }

    private boolean isEnabledByDefault(Layer layer) {
        if (layer instanceof AdditionalLayer) {
            AdditionalLayer additionalLayer = (AdditionalLayer) layer;
            return !additionalLayer.isOptional() || additionalLayer.isActiveByDefault();
        }
        return true;
    }

    private Function<VariableManager, IStatus> getDropHandler(DiagramDescription siriusDiagramDescription, AQLInterpreter interpreter) {
        // @formatter:off
        List<ContainerDropDescription> diagramDropTools = siriusDiagramDescription.getAllTools().stream()
                .filter(ContainerDropDescription.class::isInstance)
                .map(ContainerDropDescription.class::cast)
                .collect(Collectors.toList());
        // @formatter:on

        ToolConverter toolConverter = new ToolConverter(interpreter, this.editService, this.modelOperationHandlerSwitchProvider);
        return variableManager -> {
            Optional<EObject> optionalSelf = variableManager.get(VariableManager.SELF, EObject.class);
            Optional<IEditingContext> optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
            Optional<Diagram> optionalDiagram = variableManager.get(IDiagramContext.DIAGRAM_CONTEXT, IDiagramContext.class).map(IDiagramContext::getDiagram);
            Optional<Node> optionalSelectedNode = variableManager.get(Node.SELECTED_NODE, Node.class);
            if (optionalSelf.isPresent() && optionalEditingContext.isPresent()) {
                EObject self = optionalSelf.get();
                IEditingContext editingContext = optionalEditingContext.get();
                VariableManager childVariableManager = variableManager.createChild();

                Optional<Object> newViewContainer = optionalSelectedNode.map(Object.class::cast).or(() -> optionalDiagram);
                // @formatter:off
                Optional<Object> newContainer = optionalSelectedNode.map(Node::getTargetObjectId)
                        .or(() -> optionalDiagram.map(Diagram::getTargetObjectId))
                        .flatMap(targetObjectId -> this.objectService.getObject(editingContext, targetObjectId));
                // @formatter:on

                if (newContainer.isPresent() && newViewContainer.isPresent()) {
                    childVariableManager.put(NEW_CONTAINER, newContainer.get());
                    childVariableManager.put(NEW_VIEW_CONTAINER, newViewContainer.get());
                    childVariableManager.put(ToolProvider.ELEMENT, self);

                    Collection<ContainerDropDescription> candidates = new ArrayList<>();
                    for (ContainerDropDescription dropTool : diagramDropTools) {
                        String precondition = dropTool.getPrecondition();
                        if (precondition != null && !precondition.trim().isBlank()) {
                            boolean result = interpreter.evaluateExpression(childVariableManager.getVariables(), precondition).asBoolean().orElse(false);
                            if (result) {
                                candidates.add(dropTool);
                            }
                        } else {
                            candidates.add(dropTool);
                        }
                    }

                    if (!candidates.isEmpty()) {
                        ContainerDropDescription dropToolDescription = candidates.iterator().next();
                        return toolConverter.createDropToolHandler(dropToolDescription).apply(childVariableManager);
                    }
                }
            }
            return new Failure(""); //$NON-NLS-1$
        };
    }

}
