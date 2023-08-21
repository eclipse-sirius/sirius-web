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
package org.eclipse.sirius.components.compatibility.services.diagrams;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.collaborative.diagrams.api.IConnectorToolsProvider;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.compatibility.api.IAQLInterpreterFactory;
import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.compatibility.services.api.IODesignRegistry;
import org.eclipse.sirius.components.core.api.Environment;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.tools.ITool;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnTwoDiagramElementsTool;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.interpreter.Status;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.diagram.description.tool.EdgeCreationDescription;
import org.eclipse.sirius.viewpoint.description.tool.AbstractToolDescription;
import org.springframework.stereotype.Service;

/**
 * Provides tools for the connector tool.
 *
 * @author nvannier
 */
@Service
public class ConnectorToolsProvider implements IConnectorToolsProvider {

    private final IIdentifierProvider identifierProvider;

    private final IODesignRegistry odesignRegistry;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IObjectService objectService;

    private final IAQLInterpreterFactory interpreterFactory;

    private final IDiagramDescriptionService diagramDescriptionService;

    public ConnectorToolsProvider(IODesignRegistry odesignRegistry, IIdentifierProvider identifierProvider, IRepresentationDescriptionSearchService representationDescriptionSearchService,
            IObjectService objectService, IAQLInterpreterFactory interpreterFactory, IDiagramDescriptionService diagramDescriptionService) {
        this.identifierProvider = identifierProvider;
        this.odesignRegistry = odesignRegistry;
        this.representationDescriptionSearchService = representationDescriptionSearchService;
        this.objectService = objectService;
        this.interpreterFactory = interpreterFactory;
        this.diagramDescriptionService = diagramDescriptionService;
    }

    @Override
    public boolean canHandle(DiagramDescription diagramDescription) {
        return this.identifierProvider.findVsmElementId(diagramDescription.getId()).isPresent();
    }

    @Override
    public List<ITool> getConnectorTools(Object sourceDiagramElement, Object targetDiagramElement, Diagram diagram, IEditingContext editingContext) {
        var optDiagramDescription = this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId());
        var optSourceSemanticElement = Optional.of(sourceDiagramElement)
                .map(this::mapDiagramElementToTargetObjectId)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(targetObjectId -> this.objectService.getObject(editingContext, targetObjectId));
        var optTargetSemanticElement = Optional.of(targetDiagramElement)
                .map(this::mapDiagramElementToTargetObjectId)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(targetObjectId -> this.objectService.getObject(editingContext, targetObjectId));
        var optSourceDiagramElementDescriptionId = this.mapDiagramElementToDescriptionId(sourceDiagramElement);
        var optTargetDiagramElementDescriptionId = this.mapDiagramElementToDescriptionId(targetDiagramElement);

        boolean semanticElementsPresent = optSourceSemanticElement.isPresent() && optTargetSemanticElement.isPresent();
        boolean diagramElementDescriptionsPresent = optDiagramDescription.isPresent() && optSourceDiagramElementDescriptionId.isPresent() && optTargetDiagramElementDescriptionId.isPresent();
        if (semanticElementsPresent && diagramElementDescriptionsPresent && optDiagramDescription.get() instanceof DiagramDescription diagramDescription) {
            var optionalVsmElementId = this.identifierProvider.findVsmElementId(diagramDescription.getId());
            if (optionalVsmElementId.isPresent()) {
                var optionalSiriusDiagramDescription = this.odesignRegistry.getODesigns().stream()
                        .map(EObject::eResource)
                        .map(resource -> resource.getResourceSet().getEObject(URI.createURI(optionalVsmElementId.get()), false))
                        .filter(Objects::nonNull)
                        .filter(org.eclipse.sirius.diagram.description.DiagramDescription.class::isInstance)
                        .map(org.eclipse.sirius.diagram.description.DiagramDescription.class::cast)
                        .findFirst();

                var optSourceDiagramElementDescription = this.mapDescriptionIdToDescription(optSourceDiagramElementDescriptionId.get(), diagramDescription, sourceDiagramElement);
                var optTargetDiagramElementDescription = this.mapDescriptionIdToDescription(optTargetDiagramElementDescriptionId.get(), diagramDescription, targetDiagramElement);
                if (optionalSiriusDiagramDescription.isPresent() && optSourceDiagramElementDescription.isPresent() && optTargetDiagramElementDescription.isPresent()) {
                    org.eclipse.sirius.diagram.description.DiagramDescription siriusDiagramDescription = optionalSiriusDiagramDescription.get();

                    Object sourceDiagramElementDescription = optSourceDiagramElementDescription.get();
                    Object targetDiagramElementDescription = optTargetDiagramElementDescription.get();

                    return diagramDescription.getPalettes().stream()
                            .flatMap(palette -> Stream.concat(
                                    palette.getTools().stream(),
                                    palette.getToolSections().stream()
                                            .flatMap(section -> section.getTools().stream())
                            ))
                            .filter(SingleClickOnTwoDiagramElementsTool.class::isInstance)
                            .map(SingleClickOnTwoDiagramElementsTool.class::cast)
                            .filter(tool -> tool.getCandidates().stream()
                                    .anyMatch(candidate -> candidate.getSources().contains(sourceDiagramElementDescription) && candidate.getTargets()
                                            .contains(targetDiagramElementDescription)))
                            .filter(tool -> this.filterTool(tool, optSourceSemanticElement.get(), optTargetSemanticElement.get(), sourceDiagramElement, targetDiagramElement, siriusDiagramDescription))
                            .map(ITool.class::cast)
                            .toList();

                }
            }
        }

        return Collections.emptyList();
    }

    private Optional<String> mapDiagramElementToTargetObjectId(Object object) {
        Optional<String> targetObjectId = Optional.empty();
        if (object instanceof Node) {
            targetObjectId = Optional.of(((Node) object).getTargetObjectId());
        } else if (object instanceof Edge) {
            targetObjectId = Optional.of(((Edge) object).getTargetObjectId());
        }
        return targetObjectId;
    }

    private Optional<String> mapDiagramElementToDescriptionId(Object object) {
        Optional<String> descriptionId = Optional.empty();
        if (object instanceof Node) {
            descriptionId = Optional.of(((Node) object).getDescriptionId());
        } else if (object instanceof Edge) {
            descriptionId = Optional.of(((Edge) object).getDescriptionId());
        }
        return descriptionId;
    }

    private Optional<Object> mapDescriptionIdToDescription(String descriptionId, DiagramDescription diagramDescription, Object diagramElement) {
        Optional<Object> result = Optional.empty();
        if (diagramElement instanceof Node) {
            var description = this.diagramDescriptionService.findNodeDescriptionById(diagramDescription, descriptionId);
            if (description.isPresent()) {
                result = Optional.of(description.get());
            }
        } else if (diagramElement instanceof Edge) {
            var description = this.diagramDescriptionService.findEdgeDescriptionById(diagramDescription, descriptionId);
            if (description.isPresent()) {
                result = Optional.of(description.get());
            }
        }
        return result;
    }

    private boolean filterTool(ITool tool, Object sourceSemanticElement, Object targetSemanticElement, Object sourceDiagramElement, Object targetDiagramElement,
            org.eclipse.sirius.diagram.description.DiagramDescription siriusDiagramDescription) {
        var optionalVsmElementId = this.identifierProvider.findVsmElementId(tool.getId());
        if (optionalVsmElementId.isPresent()) {
            var optionalSiriusTool = this.odesignRegistry.getODesigns().stream().map(EObject::eResource)
                    .map(resource -> resource.getResourceSet().getEObject(URI.createURI(optionalVsmElementId.get()), false)).filter(Objects::nonNull)
                    .filter(AbstractToolDescription.class::isInstance)
                    .map(AbstractToolDescription.class::cast).findFirst();
            if (optionalSiriusTool.isPresent()) {
                AbstractToolDescription siriusTool = optionalSiriusTool.get();
                return this.checkPrecondition(sourceSemanticElement, targetSemanticElement, sourceDiagramElement, targetDiagramElement, siriusTool, siriusDiagramDescription);
            }
        }
        return false;
    }

    private boolean checkPrecondition(Object edgeSourceElement, Object edgeTargetElement, Object edgeSourceDiagramElement, Object edgeTargetDiagramElement, AbstractToolDescription siriusTool,
            org.eclipse.sirius.diagram.description.DiagramDescription siriusDiagramDescription) {
        boolean checkPrecondition = false;
        if (siriusTool instanceof EdgeCreationDescription) {
            AQLInterpreter interpreter = this.interpreterFactory.create(siriusDiagramDescription);
            String precondition = siriusTool.getPrecondition();
            if (precondition != null && !precondition.isBlank()) {
                VariableManager variableManager = new VariableManager();
                variableManager.put(VariableManager.SELF, edgeSourceElement);
                variableManager.put(Edge.PRE_SOURCE, edgeSourceElement);
                variableManager.put(Edge.PRE_TARGET, edgeTargetElement);
                variableManager.put(Edge.PRE_SOURCE_VIEW, edgeSourceDiagramElement);
                variableManager.put(Edge.PRE_TARGET_VIEW, edgeTargetDiagramElement);
                variableManager.put(Environment.ENVIRONMENT, Environment.SIRIUS_COMPONENTS);

                Result result = interpreter.evaluateExpression(variableManager.getVariables(), precondition);
                checkPrecondition = result.getStatus().compareTo(Status.WARNING) <= 0 && result.asBoolean().orElse(Boolean.FALSE);
            } else {
                checkPrecondition = true;
            }
        }
        return checkPrecondition;
    }

}
