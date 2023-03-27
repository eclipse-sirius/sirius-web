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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.collaborative.diagrams.api.DiagramImageConstants;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IToolSectionsProvider;
import org.eclipse.sirius.components.compatibility.api.IAQLInterpreterFactory;
import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.compatibility.services.api.IODesignRegistry;
import org.eclipse.sirius.components.core.api.Environment;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IKindParser;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.description.SynchronizationPolicy;
import org.eclipse.sirius.components.diagrams.tools.ITool;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.diagrams.tools.ToolSection;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.interpreter.Status;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.diagram.description.ContainerMapping;
import org.eclipse.sirius.diagram.description.DiagramElementMapping;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.eclipse.sirius.diagram.description.Layer;
import org.eclipse.sirius.diagram.description.NodeMapping;
import org.eclipse.sirius.diagram.description.tool.ContainerCreationDescription;
import org.eclipse.sirius.diagram.description.tool.DeleteElementDescription;
import org.eclipse.sirius.diagram.description.tool.DirectEditLabel;
import org.eclipse.sirius.diagram.description.tool.EdgeCreationDescription;
import org.eclipse.sirius.diagram.description.tool.NodeCreationDescription;
import org.eclipse.sirius.viewpoint.description.tool.AbstractToolDescription;
import org.eclipse.sirius.viewpoint.description.tool.MappingBasedToolDescription;
import org.springframework.stereotype.Service;

/**
 * The tool sections provider for the compatibility layer.
 *
 * @author arichard
 */
@Service
public class CompatibilityToolSectionsProvider implements IToolSectionsProvider {

    private static final String SIRIUS_PROTOCOL = "siriusComponents://";

    private static final String SOURCE_ID = "sourceId";

    private static final String SOURCE_ELEMENT_ID = "sourceElementId";

    private final IIdentifierProvider identifierProvider;

    private final IODesignRegistry odesignRegistry;

    private final IAQLInterpreterFactory interpreterFactory;

    private final IKindParser urlIdPaser;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IDiagramDescriptionService diagramDescriptionService;

    public CompatibilityToolSectionsProvider(IDiagramDescriptionService diagramDescriptionService, IRepresentationDescriptionSearchService representationDescriptionSearchService, IKindParser urlIdPaser, IObjectService objectService, IIdentifierProvider identifierProvider, IODesignRegistry odesignRegistry, IAQLInterpreterFactory interpreterFactory) {
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.odesignRegistry = Objects.requireNonNull(odesignRegistry);
        this.interpreterFactory = Objects.requireNonNull(interpreterFactory);
        this.urlIdPaser = Objects.requireNonNull(urlIdPaser);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.diagramDescriptionService = Objects.requireNonNull(diagramDescriptionService);
    }

    @Override
    public boolean canHandle(DiagramDescription diagramDescription) {
        Map<String, List<String>> urlAttributes = this.urlIdPaser.getParameterValues(diagramDescription.getId());
        Optional<String> sourceId = urlAttributes.get(SOURCE_ID).stream().findAny();
        if (sourceId.isPresent()) {
            return this.identifierProvider.findVsmElementId(sourceId.get()).isPresent();
        }
        return false;
    }

    @Override
    public List<ToolSection> handle(Object targetElement, IEditingContext editingContext, Object diagramElementOrDiagram) {
        String diagramElementOrDiagramId = "";
        if (diagramElementOrDiagram instanceof Diagram diagram) {
            diagramElementOrDiagramId = diagram.getDescriptionId();
        }
        else if (diagramElementOrDiagram instanceof IDiagramElement diagramElement) {
            diagramElementOrDiagramId = diagramElement.getDescriptionId();
        }

        Optional<DiagramDescription> diagramDescription = Optional.empty();

        Map<String, List<String>> urlAttributes = this.urlIdPaser.getParameterValues(diagramElementOrDiagramId);
        Optional<String> sourceId = urlAttributes.get(SOURCE_ID).stream().findAny();
        List<ToolSection> toolSections = new ArrayList<>();
        if (sourceId.isPresent()) {
            diagramDescription = this.getDiagramDescriptionFromSourceElementId(editingContext, sourceId.get());
            Optional<String> optionalVsmElementId = this.identifierProvider.findVsmElementId(sourceId.get());

            if (diagramElementOrDiagram instanceof Diagram diagram && diagramDescription.isPresent()) {
                Optional<Object> diagramElementDescription = Optional.of(diagramDescription.get());
                var optionalSiriusDiagramDescription = this.odesignRegistry.getODesigns().stream()
                        .map(EObject::eResource)
                        .map(resource -> resource.getResourceSet().getEObject(URI.createURI(optionalVsmElementId.get()), false))
                        .filter(Objects::nonNull)
                        .filter(org.eclipse.sirius.diagram.description.DiagramDescription.class::isInstance)
                        .map(org.eclipse.sirius.diagram.description.DiagramDescription.class::cast)
                        .findFirst();

                List<ToolSection> filteredToolSections = ((DiagramDescription) diagramElementDescription.get()).getToolSections().stream()
                        .map(toolSection -> this.filteredTools(targetElement, diagramElementOrDiagram, toolSection, optionalSiriusDiagramDescription.get(), diagramElementDescription.get()))
                        .filter(toolSection -> !toolSection.getTools().isEmpty())
                        .toList();

                toolSections.addAll(filteredToolSections);
                toolSections.addAll(this.createExtraToolSections(diagramElementDescription));


            } else if (diagramElementOrDiagram instanceof Node node && diagramDescription.isPresent()) {
                if (diagramDescription.isPresent()) {
                    Optional<Object> diagramElementDescription = Optional.of(this.diagramDescriptionService.findNodeDescriptionById(diagramDescription.get(), node.getDescriptionId()).get());
                    var optionalSiriusDiagramDescription = this.odesignRegistry.getODesigns().stream()
                            .map(EObject::eResource)
                            .map(resource -> resource.getResourceSet().getEObject(URI.createURI(optionalVsmElementId.get()), false))
                            .filter(Objects::nonNull)
                            .filter(org.eclipse.sirius.diagram.description.DiagramDescription.class::isInstance)
                            .map(org.eclipse.sirius.diagram.description.DiagramDescription.class::cast)
                            .findFirst();

                    List<ToolSection> filteredToolSections = diagramDescription.get().getToolSections().stream()
                            .map(toolSection -> this.filteredTools(targetElement, diagramElementOrDiagram, toolSection, optionalSiriusDiagramDescription.get(), diagramElementDescription.get()))
                            .filter(toolSection -> !toolSection.getTools().isEmpty())
                            .toList();

                    toolSections.addAll(filteredToolSections);
                    toolSections.addAll(this.createExtraToolSections(diagramElementDescription));

                }
            } else if (diagramElementOrDiagram instanceof Edge edge && diagramDescription.isPresent()) {
                if (diagramDescription.isPresent()) {
                    Optional<Object>  diagramElementDescription = Optional.of(this.diagramDescriptionService.findEdgeDescriptionById(diagramDescription.get(), edge.getDescriptionId()).get());
                    var optionalSiriusDiagramDescription = this.odesignRegistry.getODesigns().stream()
                            .map(EObject::eResource)
                            .map(resource -> resource.getResourceSet().getEObject(URI.createURI(optionalVsmElementId.get()), false))
                            .filter(Objects::nonNull)
                            .filter(org.eclipse.sirius.diagram.description.DiagramDescription.class::isInstance)
                            .map(org.eclipse.sirius.diagram.description.DiagramDescription.class::cast)
                            .findFirst();

                    List<ToolSection> filteredToolSections = diagramDescription.get().getToolSections().stream()
                            .map(toolSection -> this.filteredTools(targetElement, diagramElementOrDiagram, toolSection, optionalSiriusDiagramDescription.get(), diagramElementDescription.get()))
                            .filter(toolSection -> !toolSection.getTools().isEmpty())
                            .toList();

                    toolSections.addAll(filteredToolSections);
                    toolSections.addAll(this.createExtraToolSections(diagramElementDescription));
                }
            }


        }
        return toolSections;
    }


    private boolean isDiagramDescriptionEqualSourceElementId(DiagramDescription diagramDescription, String diagramElementOrDiagramSourceId) {
        if (!diagramDescription.getId().startsWith(SIRIUS_PROTOCOL)) {
            return false;
        }
        Optional<String> diagramDescriptionSourceId = this.urlIdPaser.getParameterValues(diagramDescription.getId()).get(SOURCE_ID).stream().findAny();
        return diagramDescriptionSourceId.isPresent() && diagramDescriptionSourceId.get().equals(diagramElementOrDiagramSourceId);
    }

    private Optional<DiagramDescription> getDiagramDescriptionFromSourceElementId(IEditingContext editingContext, String sourceElementId) {
        return this.representationDescriptionSearchService.findAll(editingContext).values().stream().filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast).filter(diagramDescription -> this.isDiagramDescriptionEqualSourceElementId(diagramDescription, sourceElementId)).findAny();
    }

    private ToolSection filteredTools(Object targetElement, Object diagramElement, ToolSection toolSection, org.eclipse.sirius.diagram.description.DiagramDescription siriusDiagramDescription,
            Object diagramElementDescription) {
        // @formatter:off
        List<ITool> tools = toolSection.getTools().stream()
                .filter(tool -> {
                    boolean keepTool = false;
                    var optionalVsmElementId = this.identifierProvider.findVsmElementId(tool.getId());
                    if (optionalVsmElementId.isPresent()) {
                        var optionalSiriusTool = this.odesignRegistry.getODesigns().stream()
                                .map(EObject::eResource)
                                .map(resource -> resource.getResourceSet().getEObject(URI.createURI(optionalVsmElementId.get()), false))
                                .filter(Objects::nonNull)
                                .filter(AbstractToolDescription.class::isInstance)
                                .map(AbstractToolDescription.class::cast)
                                .findFirst();
                        if (optionalSiriusTool.isPresent()) {
                            AbstractToolDescription siriusTool = optionalSiriusTool.get();
                            if (siriusTool instanceof MappingBasedToolDescription) {
                                keepTool = this.matchMapping(diagramElementDescription, siriusTool, siriusDiagramDescription);
                            } else {
                                keepTool = true;
                            }
                            if (keepTool) {
                                keepTool = this.checkPrecondition(targetElement, diagramElement, siriusTool, siriusDiagramDescription);
                            }
                        }
                    }
                    return keepTool;
                })
                .toList();
        // @formatter:on

        return ToolSection.newToolSection(toolSection).tools(tools).build();
    }

    private boolean matchMapping(Object diagramElementDescription, AbstractToolDescription siriusTool, org.eclipse.sirius.diagram.description.DiagramDescription siriusDiagramDescription) {
        boolean matchMapping = false;
        var optionalDiagramElementMapping = this.getDiagramElementMapping(diagramElementDescription);
        if (optionalDiagramElementMapping.isPresent()) {
            List<DiagramElementMapping> mappings = this.getSiriusToolMappings(siriusTool);
            if (siriusTool instanceof EdgeCreationDescription) {
                matchMapping = mappings.stream().anyMatch(mapping -> mapping.equals(optionalDiagramElementMapping.get()));
            } else if (diagramElementDescription instanceof DiagramDescription) {
                matchMapping = this.atLeastOneRootMapping(mappings);
            } else {
                matchMapping = this.getParentMappings(mappings).stream().anyMatch(mapping -> mapping.equals(optionalDiagramElementMapping.get()));
            }
        }
        return matchMapping;
    }

    private Optional<EObject> getDiagramElementMapping(Object diagramElementDescription) {
        String descriptionId = this.getDescriptionId(diagramElementDescription);
        Map<String, List<String>> urlAttributes = this.urlIdPaser.getParameterValues(descriptionId);
        Optional<String> diagramElementDescriptionId = Optional.empty();
        if (diagramElementDescription instanceof DiagramDescription) {
            diagramElementDescriptionId = urlAttributes.get(SOURCE_ID).stream().findAny();
        }
        else {
            diagramElementDescriptionId = urlAttributes.get(SOURCE_ELEMENT_ID).stream().findAny();
        }
        if (diagramElementDescriptionId.isPresent()) {
            var optionalVsmElementId = this.identifierProvider.findVsmElementId(diagramElementDescriptionId.get());
            if (optionalVsmElementId.isPresent()) {
                // @formatter:off
                return this.odesignRegistry.getODesigns().stream()
                        .map(EObject::eResource).map(r -> r.getResourceSet().getEObject(URI.createURI(optionalVsmElementId.get()), false))
                        .filter(Objects::nonNull)
                        .findFirst();
                // @formatter:on
            }
        }
        return Optional.empty();
    }

    private List<DiagramElementMapping> getSiriusToolMappings(AbstractToolDescription siriusTool) {
        List<DiagramElementMapping> mappings = new ArrayList<>();
        if (siriusTool instanceof ContainerCreationDescription) {
            EList<ContainerMapping> containerMappings = ((ContainerCreationDescription) siriusTool).getContainerMappings();
            mappings.addAll(containerMappings);
        } else if (siriusTool instanceof NodeCreationDescription) {
            EList<NodeMapping> nodeMappings = ((NodeCreationDescription) siriusTool).getNodeMappings();
            mappings.addAll(nodeMappings);
        } else if (siriusTool instanceof EdgeCreationDescription) {
            EList<EdgeMapping> edgeMappings = ((EdgeCreationDescription) siriusTool).getEdgeMappings();
            mappings.addAll(edgeMappings.stream().map(EdgeMapping::getSourceMapping).flatMap(Collection::stream).toList());
        } else if (siriusTool instanceof DirectEditLabel) {
            EList<DiagramElementMapping> eltMappings = ((DirectEditLabel) siriusTool).getMapping();
            mappings.addAll(eltMappings);
        } else if (siriusTool instanceof DeleteElementDescription) {
            EList<DiagramElementMapping> eltMappings = ((DeleteElementDescription) siriusTool).getMappings();
            mappings.addAll(eltMappings);
        }
        return mappings;
    }

    private List<DiagramElementMapping> getParentMappings(List<DiagramElementMapping> mappings) {
        //@formatter:off
        return mappings.stream()
                .map(DiagramElementMapping::eContainer)
                .filter(DiagramElementMapping.class::isInstance)
                .map(DiagramElementMapping.class::cast)
                .toList();
        //@formatter:on
    }

    private boolean atLeastOneRootMapping(List<DiagramElementMapping> mappings) {
        //@formatter:off
        return mappings.stream()
                .map(DiagramElementMapping::eContainer)
                .anyMatch(Layer.class::isInstance);
        //@formatter:on
    }

    private String getDescriptionId(Object diagramElementDescription) {
        String descriptionId = null;
        if (diagramElementDescription instanceof DiagramDescription) {
            descriptionId = ((DiagramDescription) diagramElementDescription).getId();
        } else if (diagramElementDescription instanceof NodeDescription) {
            descriptionId = ((NodeDescription) diagramElementDescription).getId().toString();
        } else if (diagramElementDescription instanceof EdgeDescription) {
            descriptionId = ((EdgeDescription) diagramElementDescription).getId().toString();
        }
        return descriptionId;
    }

    private boolean checkPrecondition(Object targetElement, Object diagramElement, AbstractToolDescription siriusTool,
            org.eclipse.sirius.diagram.description.DiagramDescription siriusDiagramDescription) {
        boolean checkPrecondition = false;
        if (!(siriusTool instanceof EdgeCreationDescription)) {
            AQLInterpreter interpreter = this.interpreterFactory.create(siriusDiagramDescription);
            String precondition = siriusTool.getPrecondition();
            if (precondition != null && !precondition.isBlank()) {
                VariableManager variableManager = new VariableManager();
                variableManager.put(VariableManager.SELF, targetElement);
                variableManager.put(Environment.ENVIRONMENT, Environment.SIRIUS_COMPONENTS);
                if (diagramElement instanceof Node) {
                    variableManager.put(Node.SELECTED_NODE, diagramElement);
                } else if (diagramElement instanceof Edge) {
                    variableManager.put(Edge.SELECTED_EDGE, diagramElement);
                }
                Result result = interpreter.evaluateExpression(variableManager.getVariables(), precondition);
                checkPrecondition = result.getStatus().compareTo(Status.WARNING) <= 0 && result.asBoolean().orElse(Boolean.FALSE);
            } else {
                checkPrecondition = true;
            }
        } else {
            checkPrecondition = true;
        }
        return checkPrecondition;
    }

    private List<ToolSection> createExtraToolSections(Object diagramElementDescription) {
        List<ToolSection> extraToolSections = new ArrayList<>();

        List<IDiagramElementDescription> targetDescriptions = new ArrayList<>();
        boolean unsynchronizedMapping = false;
        //@formatter:off
        if (diagramElementDescription instanceof NodeDescription nodeDescription) {
            targetDescriptions.add(nodeDescription);
            unsynchronizedMapping = SynchronizationPolicy.UNSYNCHRONIZED.equals(nodeDescription.getSynchronizationPolicy());
        } else if (diagramElementDescription instanceof EdgeDescription edgeDescription) {
            var optionalDiagramElementMapping = this.getDiagramElementMapping(edgeDescription);
            var domainEdgeMapping = optionalDiagramElementMapping
                    .filter(EdgeMapping.class::isInstance)
                    .map(EdgeMapping.class::cast)
                    .filter(edgeMapping -> edgeMapping.isUseDomainElement());
            if (domainEdgeMapping.isPresent()) {
                targetDescriptions.add(edgeDescription);
            } else {
                targetDescriptions.addAll(edgeDescription.getSourceNodeDescriptions());
            }
            unsynchronizedMapping = SynchronizationPolicy.UNSYNCHRONIZED.equals(((EdgeDescription) diagramElementDescription).getSynchronizationPolicy());
        }

        Function<VariableManager, IStatus> fakeHandler = variableManager -> new Success();

        // Graphical Delete Tool for unsynchronized mapping only (the handler is never called)
        if (diagramElementDescription instanceof NodeDescription || diagramElementDescription instanceof EdgeDescription) {
            // Edit Tool (the handler is never called)
            SingleClickOnDiagramElementTool editTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("edit")
                    .label("Edit")
                    .imageURL(DiagramImageConstants.EDIT_SVG)
                    .targetDescriptions(targetDescriptions)
                    .handler(fakeHandler)
                    .appliesToDiagramRoot(false)
                    .build();
            var editToolSection = ToolSection.newToolSection("edit-section")
                    .label("")
                    .imageURL("")
                    .tools(List.of(editTool))
                    .build();
            extraToolSections.add(editToolSection);

            if (unsynchronizedMapping) {
                SingleClickOnDiagramElementTool graphicalDeleteTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("graphical-delete")
                        .label("Delete from diagram")
                        .imageURL(DiagramImageConstants.GRAPHICAL_DELETE_SVG)
                        .targetDescriptions(targetDescriptions)
                        .handler(fakeHandler)
                        .appliesToDiagramRoot(false)
                        .build();
                var graphicalDeleteToolSection = ToolSection.newToolSection("graphical-delete-section")
                        .label("")
                        .imageURL("")
                        .tools(List.of(graphicalDeleteTool))
                        .build();
                extraToolSections.add(graphicalDeleteToolSection);
            }

            // Semantic Delete Tool (the handler is never called)
            SingleClickOnDiagramElementTool semanticDeleteTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("semantic-delete")
                    .label("Delete from model")
                    .imageURL(DiagramImageConstants.SEMANTIC_DELETE_SVG)
                    .targetDescriptions(targetDescriptions)
                    .handler(fakeHandler)
                    .appliesToDiagramRoot(false)
                    .build();
            var semanticDeleteToolSection = ToolSection.newToolSection("semantic-delete-section")
                    .label("")
                    .imageURL("")
                    .tools(List.of(semanticDeleteTool))
                    .build();
            extraToolSections.add(semanticDeleteToolSection);
        }
        return extraToolSections;
        //@formatter:on
    }
}
