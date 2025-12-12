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

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.DiagramImageConstants;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ITool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolSection;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;
import org.eclipse.sirius.components.view.emf.diagram.api.IGroupPaletteToolsProvider;
import org.eclipse.sirius.components.view.emf.diagram.tools.FadeElementToolHandler;
import org.eclipse.sirius.components.view.emf.diagram.tools.HideElementToolHandler;
import org.eclipse.sirius.components.view.emf.diagram.tools.UnFadeElementToolHandler;
import org.eclipse.sirius.components.view.emf.messages.IViewEMFMessageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * An helper to build default tools in the group palette.
 *
 * @author mcharfadi
 */
@Service
public class GroupPaletteDefaultToolsProvider implements IGroupPaletteToolsProvider {

    private final IViewEMFMessageService messageService;

    public GroupPaletteDefaultToolsProvider(IViewEMFMessageService messageService) {
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public List<ToolSection> createExtraToolSections(DiagramContext diagramContext, List<IDiagramElementDescription> targetDescriptions, List<Object> diagramElements) {
        return List.of();
    }

    @Override
    public List<ITool> createQuickAccessTools(DiagramContext diagramContext, List<IDiagramElementDescription> targetDescriptions, List<Object> diagramElements) {
        List<ITool> extraTools = new ArrayList<>();

        if (containsFadedElement(diagramElements)) {
            var unFadeTool = this.createUnFadeTool(targetDescriptions);
            extraTools.add(unFadeTool);
        } else {
            var fadeTool = this.createFadeTool(targetDescriptions);
            extraTools.add(fadeTool);
        }

        if (diagramElements.stream().anyMatch(Node.class::isInstance)) {
            extraTools.add(this.getAdjustSizeTool(targetDescriptions));
        }

        var hideTool = this.createHideTool(targetDescriptions);
        extraTools.add(hideTool);
        return  extraTools;
    }

    private boolean containsFadedElement(List<Object> diagramElements) {
        return diagramElements.stream().anyMatch(diagramElement -> {
            if (diagramElement instanceof Node node && node.getModifiers().contains(ViewModifier.Faded)) {
                return true;
            } else {
                return diagramElement instanceof Edge edge && edge.getModifiers().contains(ViewModifier.Faded);
            }
        });
    }

    private ITool createFadeTool(List<IDiagramElementDescription> targetDescriptions) {
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(FadeElementToolHandler.FADE_ELEMENT_TOOL_ID)
                .label(this.messageService.defaultQuickToolFade())
                .iconURL(List.of(DiagramImageConstants.FADE_SVG))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(false)
                .build();
    }

    private ITool createUnFadeTool(List<IDiagramElementDescription> targetDescriptions) {
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(UnFadeElementToolHandler.UNFADE_ELEMENT_TOOL_ID)
                .label(this.messageService.defaultQuickToolUnFade())
                .iconURL(List.of(DiagramImageConstants.FADE_SVG))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(false)
                .build();
    }

    private ITool createHideTool(List<IDiagramElementDescription> targetDescriptions) {
        String id = HideElementToolHandler.HIDE_ELEMENT_TOOL_ID;

        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(id)
                .label(this.messageService.defaultQuickToolHide())
                .iconURL(List.of(DiagramImageConstants.HIDE_SVG))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(false)
                .build();
    }

    private ITool getAdjustSizeTool(List<IDiagramElementDescription> targetDescriptions) {
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("adjust-size")
                .label(this.messageService.defaultQuickToolAdjustSize())
                .iconURL(List.of(DiagramImageConstants.ADJUST_SIZE))
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(false)
                .build();
    }

}
