/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.emf.view.diagram;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.collaborative.diagrams.api.DiagramImageConstants;
import org.eclipse.sirius.components.collaborative.diagrams.api.IToolSectionsProvider;
import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.description.SynchronizationPolicy;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnTwoDiagramElementsTool;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.diagrams.tools.ITool;
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
        if (diagramElement instanceof Node && diagramElementDescription instanceof NodeDescription) {
            NodeDescription nodeDescription = (NodeDescription) diagramElementDescription;
            toolSections.addAll(this.getNodeToolSections(diagramDescription, nodeDescription));
        }

        toolSections.addAll(this.createExtraToolSections(diagramElementDescription));
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
            List<ITool> tools = toolSection.getTools().stream().filter(tool -> this.isValidTool(tool, nodeDescription)).collect(Collectors.toList());

            if (!tools.isEmpty()) {
                ToolSection filteredToolSection = ToolSection.newToolSection(toolSection).tools(tools).build();
                toolSections.add(filteredToolSection);
            }
        }
        return toolSections;
    }

    private boolean isValidTool(ITool tool, NodeDescription nodeDescription) {
        boolean isValidTool = tool instanceof SingleClickOnDiagramElementTool && ((SingleClickOnDiagramElementTool) tool).getTargetDescriptions().contains(nodeDescription);
        isValidTool = isValidTool
                || tool instanceof SingleClickOnTwoDiagramElementsTool && ((SingleClickOnTwoDiagramElementsTool) tool).getCandidates().stream().anyMatch(edgeCandidate -> edgeCandidate.getSources().contains(nodeDescription));
        return isValidTool;
    }

    private List<ToolSection> createExtraToolSections(Object diagramElementDescription) {
        List<ToolSection> extraToolSections = new ArrayList<>();

        List<NodeDescription> targetDescriptions = new ArrayList<>();
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

        Function<VariableManager, IStatus> fakeHandler = variableManager -> new Success();

        if (diagramElementDescription instanceof NodeDescription) {
            // Edit Tool (the handler is never called)
            SingleClickOnDiagramElementTool editTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("edit") //$NON-NLS-1$
                    .label("Edit") //$NON-NLS-1$
                    .imageURL(DiagramImageConstants.EDIT_SVG)
                    .targetDescriptions(targetDescriptions)
                    .handler(fakeHandler)
                    .appliesToDiagramRoot(false)
                    .build();
            var editToolSection = ToolSection.newToolSection("edit-section") //$NON-NLS-1$
                    .label("") //$NON-NLS-1$
                    .imageURL("") //$NON-NLS-1$
                    .tools(List.of(editTool))
                    .build();
            extraToolSections.add(editToolSection);
        }

        // Graphical Delete Tool for unsynchronized mapping only (the handler is never called)
        if (diagramElementDescription instanceof NodeDescription || diagramElementDescription instanceof EdgeDescription) {
            if (unsynchronizedMapping) {
                SingleClickOnDiagramElementTool graphicalDeleteTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("graphical-delete") //$NON-NLS-1$
                        .label("Delete from diagram") //$NON-NLS-1$
                        .imageURL(DiagramImageConstants.GRAPHICAL_DELETE_SVG)
                        .targetDescriptions(targetDescriptions)
                        .handler(fakeHandler)
                        .appliesToDiagramRoot(false)
                        .build();
                var graphicalDeleteToolSection = ToolSection.newToolSection("graphical-delete-section") //$NON-NLS-1$
                        .label("") //$NON-NLS-1$
                        .imageURL("") //$NON-NLS-1$
                        .tools(List.of(graphicalDeleteTool))
                        .build();
                extraToolSections.add(graphicalDeleteToolSection);
            }

            // Semantic Delete Tool (the handler is never called)
            SingleClickOnDiagramElementTool semanticDeleteTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("semantic-delete") //$NON-NLS-1$
                    .label("Delete from model") //$NON-NLS-1$
                    .imageURL(DiagramImageConstants.SEMANTIC_DELETE_SVG)
                    .targetDescriptions(targetDescriptions)
                    .handler(fakeHandler)
                    .appliesToDiagramRoot(false)
                    .build();
            var semanticDeleteToolSection = ToolSection.newToolSection("semantic-delete-section") //$NON-NLS-1$
                    .label("") //$NON-NLS-1$
                    .imageURL("") //$NON-NLS-1$
                    .tools(List.of(semanticDeleteTool))
                    .build();
            extraToolSections.add(semanticDeleteToolSection);
        }
        return extraToolSections;
        //@formatter:on
    }

}
