/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.diagrams.api.DiagramImageConstants;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ITool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolSection;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeLabelKind;
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.description.SynchronizationPolicy;

/**
 * An helper to build default tools in the palette.
 * @author fbarbin
 */
public class PaletteDefaultToolsProvider {


    public List<ToolSection> createExtraToolSections(Object diagramElementDescription, Object diagramElement) {
        List<ToolSection> extraToolSections = new ArrayList<>();

        if (diagramElementDescription instanceof NodeDescription || diagramElementDescription instanceof EdgeDescription) {

            List<ITool> extraTools = this.createExtraTools(diagramElementDescription, diagramElement);
            var editToolSection = ToolSection.newToolSection("edit-section")
                    .label("Edit")
                    .iconURL(List.of())
                    .tools(extraTools)
                    .build();
            extraToolSections.add(editToolSection);

        }
        return extraToolSections;
    }

    public List<ITool> createExtraTools(Object diagramElementDescription, Object diagramElement) {
        List<IDiagramElementDescription> targetDescriptions = new ArrayList<>();
        boolean unsynchronizedMapping = false;
        if (diagramElementDescription instanceof NodeDescription nodeDescription) {
            targetDescriptions.add(nodeDescription);
            unsynchronizedMapping = SynchronizationPolicy.UNSYNCHRONIZED.equals(nodeDescription.getSynchronizationPolicy());
        } else if (diagramElementDescription instanceof EdgeDescription edgeDescription) {
            targetDescriptions.addAll(edgeDescription.getSourceNodeDescriptions());
            unsynchronizedMapping = SynchronizationPolicy.UNSYNCHRONIZED.equals(((EdgeDescription) diagramElementDescription).getSynchronizationPolicy());
        }

        List<ITool> extraTools = new ArrayList<>();
        if (this.hasLabelEditTool(diagramElementDescription)) {
            // Edit Tool (the handler is never called)
            var editTool = this.createExtraEditLabelEditTool(targetDescriptions);
            extraTools.add(editTool);
        }
        if (unsynchronizedMapping) {
            // Graphical Delete Tool (the handler is never called)
            var graphicalDeleteTool = this.createExtraGraphicalDeleteTool(targetDescriptions);
            extraTools.add(graphicalDeleteTool);
        }
        if (this.hasDeleteTool(diagramElementDescription)) {
            // Semantic Delete Tool (the handler is never called)
            var semanticDeleteTool = this.createExtraSemanticDeleteTool(targetDescriptions);
            extraTools.add(semanticDeleteTool);
        }
        if (this.isCollapsible(diagramElementDescription, diagramElement)) {
            // Collapse or expand Tool (the handler is never called)
            var expandCollapseTool = this.createExtraExpandCollapseTool(targetDescriptions, diagramElement);
            expandCollapseTool.ifPresent(extraTools::add);
        }
        return extraTools;
    }

    private Optional<ITool> createExtraExpandCollapseTool(List<IDiagramElementDescription> targetDescriptions, Object diagramElement) {

        if (diagramElement instanceof Node node) {
            ITool collapsingTool = null;
            SingleClickOnDiagramElementTool collapseTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("collapse")
                    .label("Collapse")
                    .iconURL(List.of(DiagramImageConstants.COLLAPSE_SVG))
                    .targetDescriptions(targetDescriptions)
                    .appliesToDiagramRoot(false)
                    .build();

            SingleClickOnDiagramElementTool expandTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("expand")
                    .label("Expand")
                    .iconURL(List.of(DiagramImageConstants.EXPAND_SVG))
                    .targetDescriptions(targetDescriptions)
                    .appliesToDiagramRoot(false)
                    .build();

            collapsingTool = switch (node.getCollapsingState()) {
                case EXPANDED -> collapseTool;
                case COLLAPSED -> expandTool;
                default -> null;
            };
            return Optional.ofNullable(collapsingTool);
        }
        return Optional.empty();
    }

    private ITool createExtraSemanticDeleteTool(List<IDiagramElementDescription> targetDescriptions) {
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("semantic-delete")
                .label("Delete from model")
                .iconURL(List.of(DiagramImageConstants.SEMANTIC_DELETE_SVG))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(false)
                .build();
    }

    private ITool createExtraGraphicalDeleteTool(List<IDiagramElementDescription> targetDescriptions) {
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("graphical-delete")
                .label("Delete from diagram")
                .iconURL(List.of(DiagramImageConstants.GRAPHICAL_DELETE_SVG))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(false)
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
            result = nodeDescription.getLabelEditHandler() != null;
        } else if (diagramElementDescription instanceof EdgeDescription edgeDescription) {
            if (edgeDescription.getLabelEditHandler() instanceof IViewEdgeLabelEditHandler viewEdgeLabelEditHandler) {
                result = viewEdgeLabelEditHandler.hasLabelEditTool(EdgeLabelKind.CENTER_LABEL);
            } else {
                result = false;
            }
        }
        return result;
    }


    private ITool createExtraEditLabelEditTool(List<IDiagramElementDescription> targetDescriptions) {
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("edit")
                .label("Edit")
                .iconURL(List.of(DiagramImageConstants.EDIT_SVG))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(false)
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
}
