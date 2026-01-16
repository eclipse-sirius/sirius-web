/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import org.eclipse.sirius.components.view.emf.diagram.tools.CollapseElementToolHandler;
import org.eclipse.sirius.components.view.emf.diagram.tools.ExpandElementToolHandler;
import org.eclipse.sirius.components.view.emf.diagram.tools.FadeElementToolHandler;
import org.eclipse.sirius.components.view.emf.diagram.tools.HideElementToolHandler;
import org.eclipse.sirius.components.view.emf.diagram.tools.PinElementToolHandler;
import org.eclipse.sirius.components.view.emf.diagram.tools.UnFadeElementToolHandler;
import org.eclipse.sirius.components.view.emf.diagram.tools.UnPinElementToolHandler;
import org.eclipse.sirius.components.view.emf.messages.IViewEMFMessageService;
import org.springframework.stereotype.Service;

/**
 * An helper to build default tools in the palette.
 *
 * @author fbarbin
 */
@Service
public class PaletteDefaultToolsProvider implements IPaletteToolsProvider {

    private final IViewEMFMessageService messageService;

    public PaletteDefaultToolsProvider(IViewEMFMessageService messageService) {
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public List<ToolSection> createExtraToolSections(DiagramContext diagramContext, Object diagramElementDescription, Object diagramElement) {
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

    @Override
    public List<ITool> createQuickAccessTools(DiagramContext diagramContext, Object diagramElementDescription, Object diagramElement) {
        List<ITool> extraTools = new ArrayList<>();
        List<IDiagramElementDescription> targetDescriptions = new ArrayList<>();
        if (diagramElementDescription instanceof NodeDescription nodeDescription) {
            targetDescriptions.add(nodeDescription);
        } else if (diagramElementDescription instanceof EdgeDescription edgeDescription) {
            targetDescriptions.addAll(edgeDescription.getSourceDescriptions());
        }

        if (this.hasLabelEditTool(diagramElementDescription)) {
            // Edit Tool (the handler is never called)
            var editTool = this.createExtraEditLabelEditTool(targetDescriptions);
            extraTools.add(editTool);
        }

        if (diagramElement instanceof Node node) {
            var pinTool = this.createPinTool(targetDescriptions, node);
            extraTools.add(pinTool);
        }

        if (diagramElement instanceof IDiagramElement element && this.isOutSideLabelManuallyPositioned(diagramContext, element)) {
            extraTools.add(this.getResetOutSideLabelPositionedTool(targetDescriptions));
        }

        if (diagramElement instanceof IDiagramElement element && this.isLabelManuallyResized(diagramContext, element)) {
            extraTools.add(this.getResetLabelResizedTool(targetDescriptions));
        }

        if (diagramElement instanceof Edge edge && this.isEdgeBendingPointsManuallyPositioned(diagramContext, edge)) {
            extraTools.add(this.getResetBendingPointsPositionedTool(targetDescriptions));
        }

        if (diagramElement instanceof Edge edge && this.isEdgeHandlesManuallyPositioned(diagramContext, edge)) {
            extraTools.add(this.getResetHandlesPositionTool(targetDescriptions));
        }

        if (diagramElement instanceof Node) {
            extraTools.add(this.getAdjustSizeTool(targetDescriptions));
        }

        extraTools.add(this.createFadeTool(targetDescriptions, diagramElement));
        extraTools.add(this.createHideTool(targetDescriptions));

        if (this.hasDeleteTool(diagramElementDescription)) {
            // Semantic Delete Tool (the handler is never called)
            var semanticDeleteTool = this.createExtraSemanticDeleteTool(targetDescriptions);
            extraTools.add(semanticDeleteTool);
        }
        return extraTools;
    }

    private List<ITool> createExtraTools(Object diagramElementDescription, Object diagramElement) {
        List<ITool> extraTools = new ArrayList<>();
        List<IDiagramElementDescription> targetDescriptions = new ArrayList<>();
        if (diagramElementDescription instanceof NodeDescription nodeDescription) {
            targetDescriptions.add(nodeDescription);
        } else if (diagramElementDescription instanceof EdgeDescription edgeDescription) {
            targetDescriptions.addAll(edgeDescription.getSourceDescriptions());
        }

        if (this.hasLabelEditTool(diagramElementDescription)) {
            // Edit Tool (the handler is never called)
            var editTool = this.createExtraEditLabelEditTool(targetDescriptions);
            extraTools.add(editTool);
        }

        if (this.isCollapsible(diagramElementDescription, diagramElement)) {
            var expandCollapseTool = this.createExtraExpandCollapseTool(targetDescriptions, diagramElement);
            expandCollapseTool.ifPresent(extraTools::add);
        }

        if (this.hasDeleteTool(diagramElementDescription)) {
            // Semantic Delete Tool (the handler is never called)
            var semanticDeleteTool = this.createExtraSemanticDeleteTool(targetDescriptions);
            extraTools.add(semanticDeleteTool);
        }
        return extraTools;
    }

    private SingleClickOnDiagramElementTool getAdjustSizeTool(List<IDiagramElementDescription> targetDescriptions) {
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("adjust-size")
                .label(this.messageService.defaultQuickToolAdjustSize())
                .iconURL(List.of(DiagramImageConstants.ADJUST_SIZE))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(false)
                .keyBindings(List.of())
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

    private boolean isLabelManuallyResized(DiagramContext diagramContext, IDiagramElement diagramElement) {
        boolean isManualyResized = false;
        if (diagramElement instanceof Node node) {
            var outSideLabels = node.getOutsideLabels();
            var labelLayoutData = diagramContext.diagram().getLayoutData().labelLayoutData();
            isManualyResized = outSideLabels.stream()
                    .anyMatch(label -> labelLayoutData.containsKey(label.id()) &&
                            labelLayoutData.get(label.id()).resizedByUser());
        } else if (diagramElement instanceof Edge edge) {
            var edgeLabels = Stream.of(edge.getBeginLabel(), edge.getCenterLabel(), edge.getEndLabel())
                    .filter(Objects::nonNull)
                    .toList();
            var labelLayoutData = diagramContext.diagram().getLayoutData().labelLayoutData();
            isManualyResized = edgeLabels.stream()
                    .anyMatch(label -> labelLayoutData.containsKey(label.id()) &&
                            labelLayoutData.get(label.id()).resizedByUser());
        }
        return isManualyResized;
    }

    private SingleClickOnDiagramElementTool getResetOutSideLabelPositionedTool(List<IDiagramElementDescription> targetDescriptions) {
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("reset-outside-label-position")
                .label(this.messageService.defaultQuickToolResetOutsideLabelPosition())
                .iconURL(List.of(DiagramImageConstants.RESET_LABEL_POSITION))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(false)
                .keyBindings(List.of())
                .build();
    }

    private SingleClickOnDiagramElementTool getResetLabelResizedTool(List<IDiagramElementDescription> targetDescriptions) {
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("reset-label-resize")
                .label(this.messageService.defaultQuickToolResetLabelSize())
                .iconURL(List.of(DiagramImageConstants.RESET_LABEL_SIZE))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(false)
                .keyBindings(List.of())
                .build();
    }

    private boolean isEdgeBendingPointsManuallyPositioned(DiagramContext diagramContext, Edge edge) {
        var edgeLayoutData = diagramContext.diagram().getLayoutData().edgeLayoutData().get(edge.getId());
        return edgeLayoutData != null && !edgeLayoutData.bendingPoints().isEmpty();
    }

    private SingleClickOnDiagramElementTool getResetBendingPointsPositionedTool(List<IDiagramElementDescription> targetDescriptions) {
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("reset-bending-points")
                .label(this.messageService.defaultQuickToolResetBendingPoints())
                .iconURL(List.of(DiagramImageConstants.RESET_BENDING_POINTS))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(false)
                .keyBindings(List.of())
                .build();
    }

    private boolean isEdgeHandlesManuallyPositioned(DiagramContext diagramContext, Edge edge) {
        var nodeLayoutDatas = Stream.of(diagramContext.diagram().getLayoutData().nodeLayoutData().get(edge.getSourceId()),
                        diagramContext.diagram().getLayoutData().nodeLayoutData().get(edge.getTargetId()))
                .filter(Objects::nonNull)
                .toList();
        return nodeLayoutDatas.stream()
                .flatMap(nodeLayoutData -> nodeLayoutData.handleLayoutData().stream())
                .filter(handleLayoutData -> handleLayoutData.edgeId().equals(edge.getId()))
                .anyMatch(handleLayoutData -> handleLayoutData.position() != null && (handleLayoutData.position().x() != 0 || handleLayoutData.position().y() != 0));
    }

    private SingleClickOnDiagramElementTool getResetHandlesPositionTool(List<IDiagramElementDescription> targetDescriptions) {
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("reset-handles-position")
                .label(this.messageService.defaultQuickToolResetHandlesPosition())
                .iconURL(List.of(DiagramImageConstants.RESET_HANDLES))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(false)
                .keyBindings(List.of())
                .build();
    }

    private Optional<ITool> createExtraExpandCollapseTool(List<IDiagramElementDescription> targetDescriptions, Object diagramElement) {
        if (diagramElement instanceof Node node) {
            var collapsingTool = switch (node.getCollapsingState()) {
                case EXPANDED -> SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(CollapseElementToolHandler.COLLASPSE_ELEMENT_TOOL_ID)
                        .label(this.messageService.defaultQuickToolCollapse())
                        .iconURL(List.of(DiagramImageConstants.COLLAPSE_SVG))
                        .targetDescriptions(targetDescriptions)
                        .appliesToDiagramRoot(false)
                        .withImpactAnalysis(false)
                        .keyBindings(List.of())
                        .build();
                case COLLAPSED -> SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(ExpandElementToolHandler.EXPAND_ELEMENT_TOOL_ID)
                        .label(this.messageService.defaultQuickToolExpand())
                        .iconURL(List.of(DiagramImageConstants.EXPAND_SVG))
                        .targetDescriptions(targetDescriptions)
                        .appliesToDiagramRoot(false)
                        .withImpactAnalysis(false)
                        .keyBindings(List.of())
                        .build();
            };
            return Optional.of(collapsingTool);
        }
        return Optional.empty();
    }

    private ITool createExtraSemanticDeleteTool(List<IDiagramElementDescription> targetDescriptions) {
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("semantic-delete")
                .label(this.messageService.defaultQuickToolDeleteFromModel())
                .iconURL(List.of(DiagramImageConstants.SEMANTIC_DELETE_SVG))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(false)
                .withImpactAnalysis(false)
                .keyBindings(List.of())
                .build();
    }

    private boolean isCollapsible(Object diagramElementDescription, Object diagramElement) {
        if (diagramElementDescription instanceof NodeDescription nodeDescription && diagramElement instanceof Node) {
            return nodeDescription.isCollapsible();
        }
        return false;
    }

    private ITool createFadeTool(List<IDiagramElementDescription> targetDescriptions, Object diagramElement) {
        String label = this.messageService.defaultQuickToolFade();
        String id = FadeElementToolHandler.FADE_ELEMENT_TOOL_ID;
        if (diagramElement instanceof Node node && node.getModifiers().contains(ViewModifier.Faded)
                || diagramElement instanceof Edge edge && edge.getModifiers().contains(ViewModifier.Faded)) {
            label = this.messageService.defaultQuickToolUnFade();
            id = UnFadeElementToolHandler.UNFADE_ELEMENT_TOOL_ID;
        }
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(id)
                .label(label)
                .iconURL(List.of(DiagramImageConstants.FADE_SVG))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(false)
                .keyBindings(List.of())
                .build();
    }

    private ITool createHideTool(List<IDiagramElementDescription> targetDescriptions) {
        String id = HideElementToolHandler.HIDE_ELEMENT_TOOL_ID;

        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(id)
                .label(this.messageService.defaultQuickToolHide())
                .iconURL(List.of(DiagramImageConstants.HIDE_SVG))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(false)
                .keyBindings(List.of())
                .build();
    }

    private ITool createPinTool(List<IDiagramElementDescription> targetDescriptions, Node node) {
        if (node.isPinned()) {
            return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(UnPinElementToolHandler.UNPIN_ELEMENT_TOOL_ID)
                    .label(this.messageService.defaultQuickToolUnPin())
                    .iconURL(List.of(DiagramImageConstants.UNPIN_SVG))
                    .targetDescriptions(targetDescriptions)
                    .appliesToDiagramRoot(false)
                    .keyBindings(List.of())
                    .build();
        } else {
            return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(PinElementToolHandler.PIN_ELEMENT_TOOL_ID)
                    .label(this.messageService.defaultQuickToolPin())
                    .iconURL(List.of(DiagramImageConstants.PIN_SVG))
                    .targetDescriptions(targetDescriptions)
                    .appliesToDiagramRoot(false)
                    .keyBindings(List.of())
                    .build();
        }
    }

    private boolean hasLabelEditTool(Object diagramElementDescription) {
        boolean result = false;
        if (diagramElementDescription instanceof NodeDescription nodeDescription) {
            result = nodeDescription.getLabelEditHandler() != null;
        } else if (diagramElementDescription instanceof EdgeDescription edgeDescription) {
            if (edgeDescription.getLabelEditHandler() instanceof IViewEdgeLabelEditHandler viewEdgeLabelEditHandler) {
                result = viewEdgeLabelEditHandler.hasLabelEditTool(EdgeLabelKind.CENTER_LABEL);
            }
        }
        return result;
    }


    private ITool createExtraEditLabelEditTool(List<IDiagramElementDescription> targetDescriptions) {
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("edit")
                .label(this.messageService.defaultQuickToolEdit())
                .iconURL(List.of(DiagramImageConstants.EDIT_SVG))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(false)
                .withImpactAnalysis(false)
                .keyBindings(List.of())
                .build();

    }

    private boolean hasDeleteTool(Object diagramElementDescription) {
        boolean result = false;
        if (diagramElementDescription instanceof NodeDescription nodeDescription) {
            result = nodeDescription.getDeleteHandler() != null;
        } else if (diagramElementDescription instanceof EdgeDescription edgeDescription) {
            result = edgeDescription.getDeleteHandler() != null;
        }
        return result;
    }
}
