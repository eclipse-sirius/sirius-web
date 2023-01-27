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
import java.util.function.Function;

import org.eclipse.sirius.components.collaborative.diagrams.api.DiagramImageConstants;
import org.eclipse.sirius.components.collaborative.diagrams.api.IToolSectionsProvider;
import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeLabelKind;
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.description.SynchronizationPolicy;
import org.eclipse.sirius.components.diagrams.tools.ITool;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnTwoDiagramElementsTool;
import org.eclipse.sirius.components.diagrams.tools.ToolSection;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

/**
 * Used to provide the tools of the palette for diagram created from a view description.
 *
 * @author sbegaudeau
 */
@Service
public class ViewToolSectionsProvider implements IToolSectionsProvider {

    private final IIdentifierProvider identifierProvider;

    public ViewToolSectionsProvider(IIdentifierProvider identifierProvider) {
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
    }

    @Override
    public boolean canHandle(DiagramDescription diagramDescription) {
        return this.identifierProvider.findVsmElementId(diagramDescription.getId()).isEmpty();
    }

    @Override
    public List<ToolSection> handle(Object targetElement, Object diagramElement, Object diagramElementDescription, DiagramDescription diagramDescription) {
        List<ToolSection> toolSections = new ArrayList<>();

        if (diagramElement instanceof Diagram) {
            toolSections.addAll(this.getDiagramToolSections(diagramDescription));
        }
        if (diagramElement instanceof Node && diagramElementDescription instanceof NodeDescription nodeDescription) {
            toolSections.addAll(this.getNodeToolSections(diagramDescription, nodeDescription));
        }
        if (diagramElement instanceof Edge && diagramElementDescription instanceof EdgeDescription edgeDescription) {
            toolSections.addAll(this.getEdgeToolSections(diagramDescription, edgeDescription));
        }

        toolSections.addAll(this.createExtraToolSections(diagramElementDescription, diagramElement));
        return toolSections;
    }

    private List<ToolSection> getDiagramToolSections(DiagramDescription diagramDescription) {
        List<ToolSection> toolSections = new ArrayList<>();
        for (ToolSection toolSection : diagramDescription.getToolSections()) {
            List<ITool> tools = new ArrayList<>();
            for (ITool tool : toolSection.getTools()) {
                if (tool instanceof SingleClickOnDiagramElementTool && ((SingleClickOnDiagramElementTool) tool).isAppliesToDiagramRoot()) {
                    tools.add(tool);
                }
            }

            if (!tools.isEmpty()) {
                ToolSection filteredToolSection = ToolSection.newToolSection(toolSection).tools(tools).build();
                toolSections.add(filteredToolSection);
            }
        }
        return toolSections;
    }

    private List<ToolSection> getNodeToolSections(DiagramDescription diagramDescription, NodeDescription nodeDescription) {
        List<ToolSection> toolSections = new ArrayList<>();
        for (ToolSection toolSection : diagramDescription.getToolSections()) {
            List<ITool> tools = toolSection.getTools().stream().filter(tool -> this.isValidTool(tool, nodeDescription)).toList();

            if (!tools.isEmpty()) {
                ToolSection filteredToolSection = ToolSection.newToolSection(toolSection).tools(tools).build();
                toolSections.add(filteredToolSection);
            }
        }
        return toolSections;
    }

    private List<ToolSection> getEdgeToolSections(DiagramDescription diagramDescription, EdgeDescription edgeDescription) {
        List<ToolSection> toolSections = new ArrayList<>();
        for (ToolSection toolSection : diagramDescription.getToolSections()) {
            List<ITool> tools = toolSection.getTools().stream().filter(tool -> this.isValidTool(tool, edgeDescription)).toList();

            if (!tools.isEmpty()) {
                ToolSection filteredToolSection = ToolSection.newToolSection(toolSection).tools(tools).build();
                toolSections.add(filteredToolSection);
            }
        }
        return toolSections;
    }

    private boolean isValidTool(ITool tool, IDiagramElementDescription diagramElementDescription) {
        boolean isValidTool = tool instanceof SingleClickOnDiagramElementTool && ((SingleClickOnDiagramElementTool) tool).getTargetDescriptions().contains(diagramElementDescription);
        isValidTool = isValidTool || tool instanceof SingleClickOnTwoDiagramElementsTool
                && ((SingleClickOnTwoDiagramElementsTool) tool).getCandidates().stream().anyMatch(edgeCandidate -> edgeCandidate.getSources().contains(diagramElementDescription));
        return isValidTool;
    }

    private List<ToolSection> createExtraToolSections(Object diagramElementDescription, Object diagramElement) {
        List<ToolSection> extraToolSections = new ArrayList<>();

        List<IDiagramElementDescription> targetDescriptions = new ArrayList<>();
        boolean unsynchronizedMapping = false;
        //@formatter:off
        if (diagramElementDescription instanceof NodeDescription) {
            targetDescriptions.add((NodeDescription) diagramElementDescription);
            unsynchronizedMapping = SynchronizationPolicy.UNSYNCHRONIZED.equals(((NodeDescription) diagramElementDescription).getSynchronizationPolicy());
        } else if (diagramElementDescription instanceof EdgeDescription) {
            EdgeDescription edgeDescription = (EdgeDescription) diagramElementDescription;
            targetDescriptions.addAll(edgeDescription.getSourceNodeDescriptions());
            unsynchronizedMapping = SynchronizationPolicy.UNSYNCHRONIZED.equals(((EdgeDescription) diagramElementDescription).getSynchronizationPolicy());
        }

        // Graphical Delete Tool for unsynchronized mapping only (the handler is never called)
        if (diagramElementDescription instanceof NodeDescription nodeDescription || diagramElementDescription instanceof EdgeDescription) {
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
        //@formatter:on
    }

    private ToolSection createExtraExpandCollapseTool(List<IDiagramElementDescription> targetDescriptions, Object diagramElement) {
        Function<VariableManager, IStatus> fakeHandler = variableManager -> new Success();
        // @formatter:off
        var expandCollapseToolSectionBuilder = ToolSection.newToolSection("expand-collapse-section")
                .label("")
                .imageURL("")
                .tools(List.of());
        // @formatter:on

        if (diagramElement instanceof Node node) {
            List<ITool> collapsingTools = new ArrayList<>();
            // @formatter:off
            SingleClickOnDiagramElementTool collapseTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("collapse")
                    .label("Collapse")
                    .imageURL(DiagramImageConstants.COLLAPSE_SVG)
                    .targetDescriptions(targetDescriptions)
                    .handler(fakeHandler)
                    .appliesToDiagramRoot(false)
                    .build();
            SingleClickOnDiagramElementTool expandTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("expand")
                    .label("Expand")
                    .imageURL(DiagramImageConstants.EXPAND_SVG)
                    .targetDescriptions(targetDescriptions)
                    .handler(fakeHandler)
                    .appliesToDiagramRoot(false)
                    .build();
            // @formatter:on
            switch (node.getCollapsingState()) {
                case EXPANDED:
                    collapsingTools.add(collapseTool);
                    break;
                case COLLAPSED:
                    collapsingTools.add(expandTool);
                    break;
                default:
                    break;
            }
            expandCollapseToolSectionBuilder.tools(collapsingTools);
        }
        return expandCollapseToolSectionBuilder.build();
    }

    private ToolSection createExtraSemanticDeleteTool(List<IDiagramElementDescription> targetDescriptions) {
        // @formatter:off
        SingleClickOnDiagramElementTool semanticDeleteTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("semantic-delete")
                .label("Delete from model")
                .imageURL(DiagramImageConstants.SEMANTIC_DELETE_SVG)
                .targetDescriptions(targetDescriptions)
                .handler(variableManager -> new Success())
                .appliesToDiagramRoot(false)
                .build();
        var semanticDeleteToolSection = ToolSection.newToolSection("semantic-delete-section")
                .label("")
                .imageURL("")
                .tools(List.of(semanticDeleteTool))
                .build();
        // @formatter:on
        return semanticDeleteToolSection;
    }

    private ToolSection createExtraGraphicalDeleteTool(List<IDiagramElementDescription> targetDescriptions) {
        // @formatter:off
        SingleClickOnDiagramElementTool graphicalDeleteTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("graphical-delete")
                .label("Delete from diagram")
                .imageURL(DiagramImageConstants.GRAPHICAL_DELETE_SVG)
                .targetDescriptions(targetDescriptions)
                .handler(variableManager -> new Success())
                .appliesToDiagramRoot(false)
                .build();
        var graphicalDeleteToolSection = ToolSection.newToolSection("graphical-delete-section")
                .label("")
                .imageURL("")
                .tools(List.of(graphicalDeleteTool))
                .build();
        // @formatter:on
        return graphicalDeleteToolSection;
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
            var handler = nodeDescription.getLabelEditHandler();
            if (handler instanceof IViewNodeLabelEditHandler viewNodeLabelEditHandler) {
                result = viewNodeLabelEditHandler.hasLabelEditTool();
            }
        } else if (diagramElementDescription instanceof EdgeDescription edgeDescription) {
            var handler = edgeDescription.getLabelEditHandler();
            if (handler instanceof IViewEdgeLabelEditHandler viewEdgeLabelEditHandler) {
                result = viewEdgeLabelEditHandler.hasLabelEditTool(EdgeLabelKind.CENTER_LABEL);
            }
        }
        return result;
    }

    private ToolSection createExtraEditLabelEditTool(List<IDiagramElementDescription> targetDescriptions) {
        // @formatter:off
        SingleClickOnDiagramElementTool editTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("edit")
                .label("Edit")
                .imageURL(DiagramImageConstants.EDIT_SVG)
                .targetDescriptions(targetDescriptions)
                .handler(variableManager -> new Success())
                .appliesToDiagramRoot(false)
                .build();
        var editToolSection = ToolSection.newToolSection("edit-section")
                .label("")
                .imageURL("")
                .tools(List.of(editTool))
                .build();
        // @formatter:on
        return editToolSection;
    }

    private boolean hasDeleteTool(Object diagramElementDescription) {
        boolean result = true;
        if (diagramElementDescription instanceof NodeDescription nodeDescription) {
            var handler = nodeDescription.getDeleteHandler();
            if (handler instanceof IViewNodeDeleteHandler viewNodeDeleteHandler) {
                result = viewNodeDeleteHandler.hasSemanticDeleteTool();
            }
        } else if (diagramElementDescription instanceof EdgeDescription edgeDescription) {
            var handler = edgeDescription.getDeleteHandler();
            if (handler instanceof IViewNodeDeleteHandler viewElementDeleteHandler) {
                result = viewElementDeleteHandler.hasSemanticDeleteTool();
            }
        }
        return result;
    }
}
