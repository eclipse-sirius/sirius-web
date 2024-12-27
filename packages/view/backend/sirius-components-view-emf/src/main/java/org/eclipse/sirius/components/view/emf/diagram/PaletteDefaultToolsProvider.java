/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import java.util.stream.Stream;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.DiagramImageConstants;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ITool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolSection;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeLabelKind;
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.view.emf.diagram.api.IPaletteToolsProvider;
import org.springframework.stereotype.Service;

/**
 * An helper to build default tools in the palette.
 *
 * @author fbarbin
 */
@Service
public class PaletteDefaultToolsProvider implements IPaletteToolsProvider {

    @Override
    public List<ToolSection> createExtraToolSections(DiagramContext diagramContext, Object diagramElementDescription, Object diagramElement) {
        List<ToolSection> extraToolSections = new ArrayList<>();

        if (diagramElementDescription instanceof NodeDescription || diagramElementDescription instanceof EdgeDescription) {

            List<ITool> extraTools = this.createExtraTools(diagramContext, diagramElementDescription, diagramElement);
            var editToolSection = ToolSection.newToolSection("edit-section")
                    .label("Edit")
                    .iconURL(List.of())
                    .tools(extraTools)
                    .build();
            extraToolSections.add(editToolSection);

        }
        return extraToolSections;
    }

    @Override
    public List<ITool> createQuickAccessTools(DiagramContext diagramContext, Object diagramElementDescription, Object diagramElement) {
        return this.createExtraTools(diagramContext, diagramElementDescription, diagramElement);
    }

    private List<ITool> createExtraTools(DiagramContext diagramContext, Object diagramElementDescription, Object diagramElement) {
        List<IDiagramElementDescription> targetDescriptions = new ArrayList<>();
        if (diagramElementDescription instanceof NodeDescription nodeDescription) {
            targetDescriptions.add(nodeDescription);
        } else if (diagramElementDescription instanceof EdgeDescription edgeDescription) {
            targetDescriptions.addAll(edgeDescription.getSourceDescriptions());
        }

        List<ITool> extraTools = new ArrayList<>();
        if (this.hasLabelEditTool(diagramElementDescription)) {
            // Edit Tool (the handler is never called)
            var editTool = this.createExtraEditLabelEditTool(targetDescriptions);
            extraTools.add(editTool);
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
        var fadeTool = this.createFadeTool(targetDescriptions, diagramElement);
        extraTools.add(fadeTool);

        if (diagramElement instanceof Node node) {
            var pinTool = this.createPinTool(targetDescriptions, node);
            extraTools.add(pinTool);
        }

        if (diagramElement instanceof IDiagramElement element && isOutSideLabelManuallyPositioned(diagramContext, element)) {
            extraTools.add(getResetOutSideLabelPositionedTool(targetDescriptions));
        }

        if (diagramElement instanceof Edge edge && isEdgeBendingPointsManuallyPositioned(diagramContext, edge)) {
            extraTools.add(getResetBendingPointsPositionedTool(targetDescriptions));
        }

        if (diagramElement instanceof Edge edge && isEdgeHandlesManuallyPositioned(diagramContext, edge)) {
            extraTools.add(getResetHandlesPositionTool(targetDescriptions));
        }

        if (diagramElement instanceof Node) {
            extraTools.add(getAdjustSizeTool(targetDescriptions));
        }

        return extraTools;
    }

    private SingleClickOnDiagramElementTool getAdjustSizeTool(List<IDiagramElementDescription> targetDescriptions) {
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("adjust-size")
                .label("Adjust size")
                .iconURL(List.of(DiagramImageConstants.ADJUST_SVG))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(false)
                .build();
    }

    private boolean isOutSideLabelManuallyPositioned(DiagramContext diagramContext, IDiagramElement diagramElement) {
        boolean isManualyPositionned = false;
        if (diagramElement instanceof Node node) {
            var outSideLabels = node.getOutsideLabels();
            var labelLayoutData = diagramContext.diagram().getLayoutData().labelLayoutData();
            isManualyPositionned = outSideLabels.stream()
                    .anyMatch(label -> labelLayoutData.containsKey(label.id()) &&
                            labelLayoutData.get(label.id()).position().x() != 0 &&
                            labelLayoutData.get(label.id()).position().y() != 0);
        } else if (diagramElement instanceof Edge edge) {
            var edgeLabels = Stream.of(edge.getBeginLabel(), edge.getCenterLabel(), edge.getEndLabel())
                    .filter(Objects::nonNull)
                    .toList();
            var labelLayoutData = diagramContext.diagram().getLayoutData().labelLayoutData();
            isManualyPositionned = edgeLabels.stream()
                    .anyMatch(label -> labelLayoutData.containsKey(label.id()) &&
                            labelLayoutData.get(label.id()).position().x() != 0 &&
                            labelLayoutData.get(label.id()).position().y() != 0);
        }
        return isManualyPositionned;
    }

    private SingleClickOnDiagramElementTool getResetOutSideLabelPositionedTool(List<IDiagramElementDescription> targetDescriptions) {
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("reset-outside-label-position")
                .label("Reset-outside-label-position")
                .iconURL(List.of(DiagramImageConstants.LABEL_OFF))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(false)
                .build();
    }

    private boolean isEdgeBendingPointsManuallyPositioned(DiagramContext diagramContext, Edge edge) {
        var edgeLayoutData = diagramContext.diagram().getLayoutData().edgeLayoutData().get(edge.getId());
        return edgeLayoutData != null && !edgeLayoutData.bendingPoints().isEmpty();
    }

    private SingleClickOnDiagramElementTool getResetBendingPointsPositionedTool(List<IDiagramElementDescription> targetDescriptions) {
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("reset-bending-points")
                .label("Reset bending points")
                .iconURL(List.of(DiagramImageConstants.DIRECTIONS_OFF))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(false)
                .build();
    }

    private boolean isEdgeHandlesManuallyPositioned(DiagramContext diagramContext, Edge edge) {
        var nodeLayoutDatas = Stream.of(diagramContext.diagram().getLayoutData().nodeLayoutData().get(edge.getSourceId()),
                diagramContext.diagram().getLayoutData().nodeLayoutData().get(edge.getTargetId()))
                .filter(Objects::nonNull)
                .toList();
        return nodeLayoutDatas.stream()
                .flatMap(nodeLayoutData -> nodeLayoutData.handleLayoutData().stream())
                .anyMatch(handleLayoutData -> handleLayoutData.position() != null && (handleLayoutData.position().x() != 0 || handleLayoutData.position().y() != 0));
    }

    private SingleClickOnDiagramElementTool getResetHandlesPositionTool(List<IDiagramElementDescription> targetDescriptions) {
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("reset-handles-position")
                .label("Reset Handles Position")
                .iconURL(List.of(DiagramImageConstants.SWIPE_RIGHT_ALT))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(false)
                .build();
    }

    private Optional<ITool> createExtraExpandCollapseTool(List<IDiagramElementDescription> targetDescriptions, Object diagramElement) {
        if (diagramElement instanceof Node node) {
            var collapsingTool = switch (node.getCollapsingState()) {
                case EXPANDED -> SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("collapse")
                        .label("Collapse")
                        .iconURL(List.of(DiagramImageConstants.COLLAPSE_SVG))
                        .targetDescriptions(targetDescriptions)
                        .appliesToDiagramRoot(false)
                        .withImpactAnalysis(false)
                        .build();
                case COLLAPSED -> SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("expand")
                        .label("Expand")
                        .iconURL(List.of(DiagramImageConstants.EXPAND_SVG))
                        .targetDescriptions(targetDescriptions)
                        .appliesToDiagramRoot(false)
                        .withImpactAnalysis(false)
                        .build();
            };
            return Optional.of(collapsingTool);
        }
        return Optional.empty();
    }

    private ITool createExtraSemanticDeleteTool(List<IDiagramElementDescription> targetDescriptions) {
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("semantic-delete")
                .label("Delete from model")
                .iconURL(List.of(DiagramImageConstants.SEMANTIC_DELETE_SVG))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(false)
                .withImpactAnalysis(false)
                .build();
    }

    private boolean isCollapsible(Object diagramElementDescription, Object diagramElement) {
        if (diagramElementDescription instanceof NodeDescription nodeDescription && diagramElement instanceof Node) {
            return nodeDescription.isCollapsible();
        }
        return false;
    }

    private ITool createFadeTool(List<IDiagramElementDescription> targetDescriptions, Object diagramElement) {
        String label = "Fade element";
        String id = "fade";
        if (diagramElement instanceof Node node && node.getModifiers().contains(ViewModifier.Faded)
                || diagramElement instanceof Edge edge && edge.getModifiers().contains(ViewModifier.Faded)) {
            label = "Unfade element";
            id = "unfade";
        }
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(id)
                .label(label)
                .iconURL(List.of(DiagramImageConstants.FADE_SVG))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(false)
                .build();
    }

    private ITool createPinTool(List<IDiagramElementDescription> targetDescriptions, Node node) {
        if (node.isPinned()) {
            return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("unpin")
                    .label("Unpin")
                    .iconURL(List.of(DiagramImageConstants.UNPIN_SVG))
                    .targetDescriptions(targetDescriptions)
                    .appliesToDiagramRoot(false)
                    .build();
        } else {
            return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("pin")
                    .label("Pin")
                    .iconURL(List.of(DiagramImageConstants.PIN_SVG))
                    .targetDescriptions(targetDescriptions)
                    .appliesToDiagramRoot(false)
                    .build();
        }
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
                .withImpactAnalysis(false)
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
