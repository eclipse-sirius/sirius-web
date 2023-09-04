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
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.diagrams.api.DiagramImageConstants;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IPaletteProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ITool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.Palette;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnTwoDiagramElementsCandidate;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnTwoDiagramElementsTool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolSection;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeLabelKind;
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.description.SynchronizationPolicy;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.interpreter.Status;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.diagram.DiagramToolSection;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.EdgeToolSection;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.NodeToolSection;
import org.eclipse.sirius.components.view.diagram.Tool;
import org.eclipse.sirius.components.view.emf.IJavaServiceProvider;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.configuration.ViewPaletteToolsConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Used to provide the tools of the palette for diagram created from a view description.
 * <p>
 * "Node tools" (SingleClickOnDiagramElementTool) and "Edge tools" (SingleClickOnTwoDiagramElementsTool) are obtained
 * from the ToolSection produced by ToolConverter for the target element (diagram, node or edge).
 * <p>
 * For nodes and edges (but not diagrams), the "extra tools" which can be exposed in the palette but are not implemented
 * as ITools but as plain Java handlers (label edit and delete) are added as pseudo-entries if the target element has
 * specified an explicit behavior for them. At runtime their invocation goes through distinct GraphQL mutation
 * operations than plain ITool invocation, so the body we provide here for them is never actually invoked.
 * <p>
 * Drop tools and edge reconnection tools are handled in a separate way, as they do not ever appear in the palette of
 * any element since they can only be triggered by direct gestures/interactions.
 *
 * @author sbegaudeau
 */
@Service
public class ViewPaletteProvider implements IPaletteProvider {

    private final Logger logger = LoggerFactory.getLogger(ViewPaletteProvider.class);

    private final IURLParser urlParser;

    private final IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    private final IDiagramDescriptionService diagramDescriptionService;

    private final IDiagramIdProvider diagramIdProvider;

    private final IObjectService objectService;

    private final List<IJavaServiceProvider> javaServiceProviders;

    private final ApplicationContext applicationContext;

    private final Function<EObject, UUID> idProvider = (eObject) -> UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes());

    public ViewPaletteProvider(ViewPaletteToolsConfiguration parameters, IDiagramDescriptionService diagramDescriptionService, IDiagramIdProvider diagramIdProvider, List<IJavaServiceProvider> javaServiceProviders, ApplicationContext applicationContext) {
        this.urlParser = Objects.requireNonNull(parameters.getUrlParser());
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(parameters.getViewRepresentationDescriptionPredicate());
        this.viewRepresentationDescriptionSearchService = Objects.requireNonNull(parameters.getViewRepresentationDescriptionSearchService());
        this.diagramDescriptionService = Objects.requireNonNull(diagramDescriptionService);
        this.diagramIdProvider = Objects.requireNonNull(diagramIdProvider);
        this.objectService = Objects.requireNonNull(parameters.getObjectService());
        this.javaServiceProviders = Objects.requireNonNull(javaServiceProviders);
        this.applicationContext = Objects.requireNonNull(applicationContext);
    }

    @Override
    public boolean canHandle(DiagramDescription diagramDescription) {
        return this.viewRepresentationDescriptionPredicate.test(diagramDescription);
    }

    @Override
    public Palette handle(Object targetElement, Object diagramElement, Object diagramElementDescription, DiagramDescription diagramDescription, IEditingContext editingContext) {
        Palette palette = null;
        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, targetElement);
        var optionalDiagramDescription = this.viewRepresentationDescriptionSearchService.findById(diagramDescription.getId())
                .filter(org.eclipse.sirius.components.view.diagram.DiagramDescription.class::isInstance)
                .map(org.eclipse.sirius.components.view.diagram.DiagramDescription.class::cast);
        if (optionalDiagramDescription.isPresent()) {
            org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription = optionalDiagramDescription.get();
            var interpreter = this.createInterpreter((View) viewDiagramDescription.eContainer(), editingContext);
            if (diagramElement instanceof Diagram) {
                palette = this.getDiagramPalette(diagramDescription, viewDiagramDescription, variableManager, interpreter);
            } else if (diagramElement instanceof Node && diagramElementDescription instanceof NodeDescription nodeDescription) {
                variableManager.put(Node.SELECTED_NODE, diagramElement);
                palette = this.getNodePalette(diagramDescription, nodeDescription, this.createExtraToolSections(diagramElementDescription, diagramElement), variableManager, interpreter);
            } else if (diagramElement instanceof Edge && diagramElementDescription instanceof EdgeDescription edgeDescription) {
                variableManager.put(Edge.SELECTED_EDGE, diagramElement);
                palette = this.getEdgePalette(edgeDescription, this.createExtraToolSections(diagramElementDescription, diagramElement), variableManager, interpreter);
            }
        }
        return palette;
    }

    protected Palette getDiagramPalette(DiagramDescription diagramDescription, org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        Palette diagramPalette = null;
        var toolFinder = new ToolFinder();
        Optional<String> sourceElementId = this.getSourceElementId(diagramDescription.getId());
        if (sourceElementId.isPresent()) {
            String diagramPaletteId = "siriusComponents://diagramPalette?diagramId=" + sourceElementId.get();

            diagramPalette = Palette.newPalette(diagramPaletteId)
                    .tools(toolFinder.findNodeTools(viewDiagramDescription).stream()
                            .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                            .map(this::createDiagramRootNodeTool)
                            .toList())
                    .toolSections(toolFinder.findToolSections(viewDiagramDescription).stream()
                            .map(toolSection -> this.createToolSection(toolSection, variableManager, interpreter))
                            .toList())
                    .build();
        }
        return diagramPalette;
    }

    private ToolSection createToolSection(DiagramToolSection toolSection, VariableManager variableManager, AQLInterpreter interpreter) {
        String toolSelectionId = this.idProvider.apply(toolSection).toString();

        return ToolSection.newToolSection(toolSelectionId)
                .label(toolSection.getName())
                .imageURL("")
                .tools(toolSection.getNodeTools().stream().map(this::createDiagramRootNodeTool).toList())
                .build();
    }

    private ITool createDiagramRootNodeTool(NodeTool viewNodeTool) {
        return this.createNodeTool(viewNodeTool, true);
    }

    private ITool createNodeTool(NodeTool viewNodeTool) {
        return this.createNodeTool(viewNodeTool, false);
    }

    private ITool createNodeTool(NodeTool viewNodeTool, boolean appliesToDiagramRoot) {
        String toolId = this.idProvider.apply(viewNodeTool).toString();
        String selectionDescriptionId = "";
        if (viewNodeTool.getSelectionDescription() != null) {
            selectionDescriptionId = this.objectService.getId(viewNodeTool.getSelectionDescription());
        }
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(toolId)
                .label(viewNodeTool.getName())
                .imageURL(ViewToolImageProvider.NODE_CREATION_TOOL_ICON)
                .selectionDescriptionId(selectionDescriptionId)
                .targetDescriptions(List.of())
                .appliesToDiagramRoot(appliesToDiagramRoot)
                .build();
    }

    protected Palette getNodePalette(DiagramDescription diagramDescription, NodeDescription nodeDescription, List<ToolSection> extraToolSections, VariableManager variableManager, AQLInterpreter interpreter) {
        Optional<String> sourceElementId = this.getSourceElementId(nodeDescription.getId());
        Palette nodePalette = null;
        var toolFinder = new ToolFinder();
        if (sourceElementId.isPresent()) {
            String nodePaletteId = "siriusComponents://nodePalette?nodeId=" + sourceElementId.get();
            var optionalNodeDescription = this.viewRepresentationDescriptionSearchService.findViewNodeDescriptionById(nodeDescription.getId());
            if (optionalNodeDescription.isPresent()) {
                org.eclipse.sirius.components.view.diagram.NodeDescription viewNodeDescription = optionalNodeDescription.get();
                var tools = new ArrayList<ITool>();
                tools.addAll(toolFinder.findNodeTools(viewNodeDescription).stream()
                        .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                        .map(this::createNodeTool)
                        .toList());
                tools.addAll(toolFinder.findEdgeTools(viewNodeDescription).stream()
                        .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                        .map(viewEdgeTools -> this.createEdgeTool(viewEdgeTools, diagramDescription, nodeDescription))
                        .toList());
                var toolSections = new ArrayList<ToolSection>();
                toolSections.addAll(toolFinder.findToolSections(viewNodeDescription).stream()
                        .map(nodeToolSection -> this.createToolSection(nodeToolSection, diagramDescription, nodeDescription, variableManager, interpreter))
                        .toList());
                toolSections.addAll(extraToolSections);
                nodePalette = Palette.newPalette(nodePaletteId)
                        .tools(tools)
                        .toolSections(toolSections)
                        .build();
            }
        }
        return nodePalette;
    }

    private ToolSection createToolSection(NodeToolSection toolSection, DiagramDescription diagramDescription, NodeDescription nodeDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String toolSelectionId = this.idProvider.apply(toolSection).toString();

        var tools = new ArrayList<ITool>();
        tools.addAll(toolSection.getNodeTools().stream()
                .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                .map(this::createNodeTool)
                .toList());
        tools.addAll(toolSection.getEdgeTools().stream()
                .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                .map(viewEdgeTools -> this.createEdgeTool(viewEdgeTools, diagramDescription, nodeDescription))
                .toList());

        return ToolSection.newToolSection(toolSelectionId)
                .label(toolSection.getName())
                .imageURL("")
                .tools(tools)
                .build();
    }

    private ITool createEdgeTool(EdgeTool viewEdgeTool, DiagramDescription diagramDescription, NodeDescription nodeDescription) {
        String toolId = this.idProvider.apply(viewEdgeTool).toString();
        return SingleClickOnTwoDiagramElementsTool.newSingleClickOnTwoDiagramElementsTool(toolId)
                .label(viewEdgeTool.getName())
                .imageURL(ViewToolImageProvider.EDGE_CREATION_TOOL_ICON)
                .candidates(List.of(SingleClickOnTwoDiagramElementsCandidate.newSingleClickOnTwoDiagramElementsCandidate()
                        .sources(List.of(nodeDescription))
                        .targets(viewEdgeTool.getTargetElementDescriptions().stream().filter(org.eclipse.sirius.components.view.diagram.NodeDescription.class::isInstance)
                                .map(viewDiagramElementDescription -> this.diagramDescriptionService.findNodeDescriptionById(diagramDescription, this.diagramIdProvider.getId(viewDiagramElementDescription)))
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .toList())
                        .build()))
                .build();
    }

    protected Palette getEdgePalette(EdgeDescription edgeDescription, List<ToolSection> extraToolSections, VariableManager variableManager, AQLInterpreter interpreter) {
        Palette edgePalette = null;
        var toolFinder = new ToolFinder();
        Optional<String> optionalSourceElementId = this.getSourceElementId(edgeDescription.getId());
        if (optionalSourceElementId.isPresent()) {
            var sourceElementId = optionalSourceElementId.get();

            var optionalEdgeDescription = this.viewRepresentationDescriptionSearchService.findViewEdgeDescriptionById(edgeDescription.getId());
            if (optionalEdgeDescription.isPresent()) {
                org.eclipse.sirius.components.view.diagram.EdgeDescription viewEdgeDescription = optionalEdgeDescription.get();
                String edgePaletteId = "siriusComponents://edgePalette?edgeId=" + sourceElementId;
                var toolSections = new ArrayList<ToolSection>();
                toolSections.addAll(toolFinder.findToolSections(viewEdgeDescription).stream()
                        .map(edgeToolSection -> this.createToolSection(edgeToolSection, variableManager, interpreter))
                        .toList());
                toolSections.addAll(extraToolSections);
                edgePalette = Palette.newPalette(edgePaletteId)
                        .tools(toolFinder.findNodeTools(viewEdgeDescription).stream().map(this::createNodeTool).toList())
                        .toolSections(toolSections)
                        .build();

            }
        }
        return edgePalette;
    }

    private ToolSection createToolSection(EdgeToolSection toolSection, VariableManager variableManager, AQLInterpreter interpreter) {
        String toolSelectionId = this.idProvider.apply(toolSection).toString();

        return ToolSection.newToolSection(toolSelectionId)
                .label(toolSection.getName())
                .imageURL("")
                .tools(toolSection.getNodeTools().stream()
                        .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                        .map(this::createNodeTool)
                        .toList())
                .build();
    }

    private Optional<String> getSourceElementId(String descriptionId) {
        var parameters = this.urlParser.getParameterValues(descriptionId);
        return Optional.ofNullable(parameters.get(IDiagramIdProvider.SOURCE_ELEMENT_ID)).orElse(List.of()).stream().findFirst();
    }

    private List<ToolSection> createExtraToolSections(Object diagramElementDescription, Object diagramElement) {
        List<ToolSection> extraToolSections = new ArrayList<>();

        List<IDiagramElementDescription> targetDescriptions = new ArrayList<>();
        boolean unsynchronizedMapping = false;
        if (diagramElementDescription instanceof NodeDescription nodeDescription) {
            targetDescriptions.add(nodeDescription);
            unsynchronizedMapping = SynchronizationPolicy.UNSYNCHRONIZED.equals(nodeDescription.getSynchronizationPolicy());
        } else if (diagramElementDescription instanceof EdgeDescription edgeDescription) {
            targetDescriptions.addAll(edgeDescription.getSourceNodeDescriptions());
            unsynchronizedMapping = SynchronizationPolicy.UNSYNCHRONIZED.equals(((EdgeDescription) diagramElementDescription).getSynchronizationPolicy());
        }

        // Graphical Delete Tool for unsynchronized mapping only (the handler is never called)
        if (diagramElementDescription instanceof NodeDescription || diagramElementDescription instanceof EdgeDescription) {
            if (this.hasLabelEditTool(diagramElementDescription)) {
                // Edit Tool (the handler is never called)
                var editToolSection = this.createExtraEditLabelEditTool(targetDescriptions);
                extraToolSections.add(editToolSection);
            }
            if (unsynchronizedMapping) {
                // Graphical Delete Tool (the handler is never called)
                var graphicalDeleteToolSection = this.createExtraGraphicalDeleteTool(targetDescriptions);
                extraToolSections.add(graphicalDeleteToolSection);
            }
            if (this.hasDeleteTool(diagramElementDescription)) {
                // Semantic Delete Tool (the handler is never called)
                var semanticDeleteToolSection = this.createExtraSemanticDeleteTool(targetDescriptions);
                extraToolSections.add(semanticDeleteToolSection);
            }
            if (this.isCollapsible(diagramElementDescription, diagramElement)) {
                // Collapse or expand Tool (the handler is never called)
                var expandCollapseToolSection = this.createExtraExpandCollapseTool(targetDescriptions, diagramElement);
                extraToolSections.add(expandCollapseToolSection);
            }
        }
        return extraToolSections;
    }

    private ToolSection createExtraExpandCollapseTool(List<IDiagramElementDescription> targetDescriptions, Object diagramElement) {
        var expandCollapseToolSectionBuilder = ToolSection.newToolSection("expand-collapse-section")
                .label("")
                .imageURL("")
                .tools(List.of());

        if (diagramElement instanceof Node node) {
            List<ITool> collapsingTools = new ArrayList<>();
            SingleClickOnDiagramElementTool collapseTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("collapse")
                    .label("Collapse")
                    .imageURL(DiagramImageConstants.COLLAPSE_SVG)
                    .targetDescriptions(targetDescriptions)
                    .appliesToDiagramRoot(false)
                    .build();

            SingleClickOnDiagramElementTool expandTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("expand")
                    .label("Expand")
                    .imageURL(DiagramImageConstants.EXPAND_SVG)
                    .targetDescriptions(targetDescriptions)
                    .appliesToDiagramRoot(false)
                    .build();

            switch (node.getCollapsingState()) {
                case EXPANDED -> collapsingTools.add(collapseTool);
                case COLLAPSED -> collapsingTools.add(expandTool);
                default -> {
                    // Nothing on purpose
                }
            }
            expandCollapseToolSectionBuilder.tools(collapsingTools);
        }
        return expandCollapseToolSectionBuilder.build();
    }

    private ToolSection createExtraSemanticDeleteTool(List<IDiagramElementDescription> targetDescriptions) {
        SingleClickOnDiagramElementTool semanticDeleteTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("semantic-delete")
                .label("Delete from model")
                .imageURL(DiagramImageConstants.SEMANTIC_DELETE_SVG)
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(false)
                .build();

        return ToolSection.newToolSection("semantic-delete-section")
                .label("")
                .imageURL("")
                .tools(List.of(semanticDeleteTool))
                .build();
    }

    private ToolSection createExtraGraphicalDeleteTool(List<IDiagramElementDescription> targetDescriptions) {
        SingleClickOnDiagramElementTool graphicalDeleteTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("graphical-delete")
                .label("Delete from diagram")
                .imageURL(DiagramImageConstants.GRAPHICAL_DELETE_SVG)
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(false)
                .build();

        return ToolSection.newToolSection("graphical-delete-section")
                .label("")
                .imageURL("")
                .tools(List.of(graphicalDeleteTool))
                .build();
    }

    private boolean isCollapsible(Object diagramElementDescription, Object diagramElement) {
        if (diagramElementDescription instanceof NodeDescription nodeDescription && diagramElement instanceof Node) {
            return nodeDescription.isCollapsible();
        }
        return false;
    }

    private boolean hasLabelEditTool(Object diagramElementDescription) {
        boolean result = true;
        if (diagramElementDescription instanceof NodeDescription nodeDescription) {
            if (nodeDescription.getLabelEditHandler() instanceof IViewNodeLabelEditHandler viewNodeLabelEditHandler) {
                result = viewNodeLabelEditHandler.hasLabelEditTool();
            }
        } else if (diagramElementDescription instanceof EdgeDescription edgeDescription) {
            if (edgeDescription.getLabelEditHandler() instanceof IViewEdgeLabelEditHandler viewEdgeLabelEditHandler) {
                result = viewEdgeLabelEditHandler.hasLabelEditTool(EdgeLabelKind.CENTER_LABEL);
            }
        }
        return result;
    }

    private ToolSection createExtraEditLabelEditTool(List<IDiagramElementDescription> targetDescriptions) {
        SingleClickOnDiagramElementTool editTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("edit")
                .label("Edit")
                .imageURL(DiagramImageConstants.EDIT_SVG)
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(false)
                .build();

        return ToolSection.newToolSection("edit-section")
                .label("")
                .imageURL("")
                .tools(List.of(editTool))
                .build();
    }

    private boolean hasDeleteTool(Object diagramElementDescription) {
        boolean result = true;
        if (diagramElementDescription instanceof NodeDescription nodeDescription) {
            if (nodeDescription.getDeleteHandler() instanceof IViewNodeDeleteHandler viewNodeDeleteHandler) {
                result = viewNodeDeleteHandler.hasSemanticDeleteTool();
            }
        } else if (diagramElementDescription instanceof EdgeDescription edgeDescription) {
            if (edgeDescription.getDeleteHandler() instanceof IViewNodeDeleteHandler viewElementDeleteHandler) {
                result = viewElementDeleteHandler.hasSemanticDeleteTool();
            }
        }
        return result;
    }

    private AQLInterpreter createInterpreter(View view, IEditingContext editingContext) {
        List<EPackage> visibleEPackages = this.getAccessibleEPackages(editingContext);
        AutowireCapableBeanFactory beanFactory = this.applicationContext.getAutowireCapableBeanFactory();
        List<Object> serviceInstances = this.javaServiceProviders.stream()
                .flatMap(provider -> provider.getServiceClasses(view).stream())
                .map(serviceClass -> {
                    try {
                        return beanFactory.createBean(serviceClass);
                    } catch (BeansException beansException) {
                        this.logger.warn("Error while trying to instantiate Java service class " + serviceClass.getName(), beansException);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .map(Object.class::cast)
                .toList();
        return new AQLInterpreter(List.of(), serviceInstances, visibleEPackages);
    }

    private List<EPackage> getAccessibleEPackages(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext) {
            EPackage.Registry packageRegistry = ((EditingContext) editingContext).getDomain().getResourceSet().getPackageRegistry();
            return packageRegistry.values().stream()
                    .filter(EPackage.class::isInstance)
                    .map(EPackage.class::cast)
                    .toList();
        } else {
            return List.of();
        }
    }

    private boolean checkPrecondition(Tool tool, VariableManager variableManager, AQLInterpreter interpreter) {
        String precondition = tool.getPreconditionExpression();
        if (precondition != null && !precondition.isBlank()) {
            Result result = interpreter.evaluateExpression(variableManager.getVariables(), precondition);
            return result.getStatus().compareTo(Status.WARNING) <= 0 && result.asBoolean().orElse(Boolean.FALSE);
        }
        return true;
    }
}
