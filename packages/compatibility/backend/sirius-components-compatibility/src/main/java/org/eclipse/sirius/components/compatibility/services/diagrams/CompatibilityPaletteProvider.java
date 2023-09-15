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
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.collaborative.diagrams.api.DiagramImageConstants;
import org.eclipse.sirius.components.collaborative.diagrams.api.IPaletteProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ITool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.Palette;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnTwoDiagramElementsCandidate;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnTwoDiagramElementsTool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolSection;
import org.eclipse.sirius.components.compatibility.api.IAQLInterpreterFactory;
import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.compatibility.services.api.IODesignRegistry;
import org.eclipse.sirius.components.core.api.Environment;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.description.SynchronizationPolicy;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.interpreter.Status;
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
public class CompatibilityPaletteProvider implements IPaletteProvider {

    private final IIdentifierProvider identifierProvider;

    private final IODesignRegistry odesignRegistry;

    private final IAQLInterpreterFactory interpreterFactory;

    public CompatibilityPaletteProvider(IIdentifierProvider identifierProvider, IODesignRegistry odesignRegistry, IAQLInterpreterFactory interpreterFactory) {
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.odesignRegistry = Objects.requireNonNull(odesignRegistry);
        this.interpreterFactory = Objects.requireNonNull(interpreterFactory);
    }

    @Override
    public boolean canHandle(DiagramDescription diagramDescription) {
        return this.identifierProvider.findVsmElementId(diagramDescription.getId()).isPresent();
    }

    @Override
    public Palette handle(Object targetElement, Object diagramElement, Object diagramElementDescription, DiagramDescription diagramDescription, IEditingContext editingContext) {
        var optionalVsmElementId = this.identifierProvider.findVsmElementId(diagramDescription.getId());

        var optionalSiriusDiagramDescription = this.odesignRegistry.getODesigns().stream()
                .map(EObject::eResource)
                .map(resource -> resource.getResourceSet().getEObject(URI.createURI(optionalVsmElementId.get()), false))
                .filter(Objects::nonNull)
                .filter(org.eclipse.sirius.diagram.description.DiagramDescription.class::isInstance)
                .map(org.eclipse.sirius.diagram.description.DiagramDescription.class::cast)
                .findFirst();

        List<ToolSection> toolSections = new ArrayList<>();
        if (optionalSiriusDiagramDescription.isPresent()) {
            org.eclipse.sirius.diagram.description.DiagramDescription siriusDiagramDescription = optionalSiriusDiagramDescription.get();
            List<ToolSection> filteredToolSections = this.getToolSectionFromDiagramDescriptionToolSection(diagramDescription).stream()
                    .map(toolSection -> this.filteredTools(targetElement, diagramElement, toolSection, siriusDiagramDescription, diagramElementDescription))
                    .filter(toolSection -> !toolSection.tools().isEmpty())
                    .toList();

            toolSections.addAll(filteredToolSections);
            toolSections.addAll(this.createExtraToolSections(diagramElementDescription));
        }
        String paletteId = "siriusComponents://palette?diagramId=" + optionalVsmElementId.get();
        return Palette.newPalette(paletteId).tools(List.of()).toolSections(toolSections).build();
    }

    private List<ToolSection> getToolSectionFromDiagramDescriptionToolSection(DiagramDescription diagramDescription) {
        List<ToolSection> toolSections = new ArrayList<>();
        diagramDescription.getPalettes().forEach(palette -> {
            List<ITool> tools = palette.getTools().stream().map(this::convertTool).filter(Objects::nonNull).toList();
            ToolSection convertedToolSection = new ToolSection(palette.getId(), "", List.of(), tools);
            toolSections.add(convertedToolSection);
        });
        diagramDescription.getPalettes().stream()
                .flatMap(palette -> palette.getToolSections().stream())
                .forEach(toolSection -> {
                    List<ITool> tools = toolSection.getTools().stream().map(this::convertTool).filter(Objects::nonNull).toList();
                    ToolSection convertedToolSection = new ToolSection(toolSection.getId(), toolSection.getLabel(), toolSection.getIconURL(), tools);
                    toolSections.add(convertedToolSection);
                });
        return toolSections;
    }

    private ITool convertTool(org.eclipse.sirius.components.diagrams.tools.ITool tool) {
        ITool convertedTool = null;
        if (tool instanceof org.eclipse.sirius.components.diagrams.tools.SingleClickOnDiagramElementTool singleClickOnDiagramElementTool) {
            convertedTool = new SingleClickOnDiagramElementTool(singleClickOnDiagramElementTool.getId(), singleClickOnDiagramElementTool.getLabel(),
                    singleClickOnDiagramElementTool.getIconURL(), singleClickOnDiagramElementTool.getTargetDescriptions(),
                    singleClickOnDiagramElementTool.getSelectionDescriptionId(), singleClickOnDiagramElementTool.isAppliesToDiagramRoot());
        }
        if (tool instanceof org.eclipse.sirius.components.diagrams.tools.SingleClickOnTwoDiagramElementsTool singleClickOnTwoDiagramElementsTool) {
            List<SingleClickOnTwoDiagramElementsCandidate> candidates = new ArrayList<>();
            singleClickOnTwoDiagramElementsTool.getCandidates().forEach(candidate -> {
                SingleClickOnTwoDiagramElementsCandidate convertedCandidate = new SingleClickOnTwoDiagramElementsCandidate(candidate.getSources(), candidate.getTargets());
                candidates.add(convertedCandidate);
            });
            convertedTool = new SingleClickOnTwoDiagramElementsTool(singleClickOnTwoDiagramElementsTool.getId(), singleClickOnTwoDiagramElementsTool.getLabel(),
                    singleClickOnTwoDiagramElementsTool.getIconURL(), candidates);
        }
        return convertedTool;
    }

    private ToolSection filteredTools(Object targetElement, Object diagramElement, ToolSection toolSection, org.eclipse.sirius.diagram.description.DiagramDescription siriusDiagramDescription,
            Object diagramElementDescription) {
        // @formatter:off
        List<ITool> tools = toolSection.tools().stream()
                .filter(tool -> {
                    boolean keepTool = false;
                    var optionalVsmElementId = this.identifierProvider.findVsmElementId(tool.id());
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
        return new ToolSection(toolSection.id(), toolSection.label(), toolSection.iconURL(), tools);
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
        var optionalVsmElementId = this.identifierProvider.findVsmElementId(descriptionId);
        if (optionalVsmElementId.isPresent()) {
            // @formatter:off
            return this.odesignRegistry.getODesigns().stream()
                    .map(EObject::eResource).map(r -> r.getResourceSet().getEObject(URI.createURI(optionalVsmElementId.get()), false))
                    .filter(Objects::nonNull)
                    .findFirst();
            // @formatter:on
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
            descriptionId = ((NodeDescription) diagramElementDescription).getId();
        } else if (diagramElementDescription instanceof EdgeDescription) {
            descriptionId = ((EdgeDescription) diagramElementDescription).getId();
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

        // Graphical Delete Tool for unsynchronized mapping only (the handler is never called)
        if (diagramElementDescription instanceof NodeDescription || diagramElementDescription instanceof EdgeDescription) {
            // Edit Tool (the handler is never called)
            SingleClickOnDiagramElementTool editTool = new SingleClickOnDiagramElementTool("edit", "Edit", List.of(DiagramImageConstants.EDIT_SVG), targetDescriptions, "", false);
            var editToolSection = new ToolSection("edit-section", "", List.of(), List.of(editTool));
            extraToolSections.add(editToolSection);

            if (unsynchronizedMapping) {
                SingleClickOnDiagramElementTool graphicalDeleteTool = new SingleClickOnDiagramElementTool("graphical-delete", "Delete from diagram",
                        List.of(DiagramImageConstants.GRAPHICAL_DELETE_SVG), targetDescriptions, "", false);
                var graphicalDeleteToolSection = new ToolSection("graphical-delete-section", "", List.of(), List.of(graphicalDeleteTool));
                extraToolSections.add(graphicalDeleteToolSection);
            }

            // Semantic Delete Tool (the handler is never called)
            SingleClickOnDiagramElementTool semanticDeleteTool = new SingleClickOnDiagramElementTool("semantic-delete", "Delete from model",
                    List.of(DiagramImageConstants.SEMANTIC_DELETE_SVG), targetDescriptions, "", false);
            var graphicalDeleteToolSection = new ToolSection("semantic-delete-section", "", List.of(), List.of(semanticDeleteTool));
            extraToolSections.add(graphicalDeleteToolSection);
        }
        return extraToolSections;
        //@formatter:on
    }
}
