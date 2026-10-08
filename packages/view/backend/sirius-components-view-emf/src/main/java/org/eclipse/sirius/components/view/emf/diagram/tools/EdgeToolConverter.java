/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
package org.eclipse.sirius.components.view.emf.diagram.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.palette.dto.ITool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnTwoDiagramElementsCandidate;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnTwoDiagramElementsTool;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.components.view.emf.diagram.ViewToolImageProvider;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.IEdgeToolConverter;
import org.eclipse.sirius.components.view.emf.form.converters.MultiValueProvider;
import org.springframework.stereotype.Service;

/**
 * Used to create edge tools.
 *
 * @author sbegaudeau
 */
@Service
public class EdgeToolConverter implements IEdgeToolConverter {

    private final IDiagramIdProvider diagramIdProvider;

    private final IDiagramDescriptionService diagramDescriptionService;

    public EdgeToolConverter(IDiagramIdProvider diagramIdProvider, IDiagramDescriptionService diagramDescriptionService) {
        this.diagramIdProvider = Objects.requireNonNull(diagramIdProvider);
        this.diagramDescriptionService = Objects.requireNonNull(diagramDescriptionService);
    }

    @Override
    public ITool createEdgeTool(AQLInterpreter interpreter, EdgeTool viewEdgeTool, DiagramDescription diagramDescription, IDiagramElementDescription diagramElementDescription, VariableManager variableManager) {
        String toolId = UUID.nameUUIDFromBytes(EcoreUtil.getURI(viewEdgeTool).toString().getBytes()).toString();
        List<String> iconURLs = this.getIconURLs(viewEdgeTool.getIconURLsExpression(), interpreter, variableManager);
        String dialogDescriptionId = "";

        var dialogDescription = viewEdgeTool.getDialogDescription();
        if (dialogDescription != null) {
            dialogDescriptionId = this.diagramIdProvider.getId(dialogDescription);
        }

        return SingleClickOnTwoDiagramElementsTool.newSingleClickOnTwoDiagramElementsTool(toolId)
                .label(viewEdgeTool.getName())
                .iconURL(iconURLs)
                .candidates(this.getConnectorToolsCandidates(viewEdgeTool, diagramDescription, diagramElementDescription))
                .dialogDescriptionId(dialogDescriptionId)
                .build();
    }

    @Override
    public ITool createEdgeToolFromNodeTool(NodeTool viewNodeTool, EdgeTool viewEdgeTool, DiagramDescription diagramDescription, IDiagramElementDescription diagramElementDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String toolId = UUID.nameUUIDFromBytes(EcoreUtil.getURI(viewNodeTool).toString().getBytes()).toString();

        List<String> iconURLProvider = this.getIconURLs(viewNodeTool.getIconURLsExpression(), interpreter, variableManager);
        String dialogDescriptionId = "";
        if (viewNodeTool.getDialogDescription() != null) {
            dialogDescriptionId = this.diagramIdProvider.getId(viewNodeTool.getDialogDescription());
        }

        List<SingleClickOnTwoDiagramElementsCandidate> candidates = List.of(SingleClickOnTwoDiagramElementsCandidate.newSingleClickOnTwoDiagramElementsCandidate()
                .sources(List.of(diagramElementDescription))
                .targets(viewEdgeTool.getTargetElementDescriptions().stream()
                        .map(viewDiagramElementDescription -> this.diagramDescriptionService.findDiagramElementDescriptionById(diagramDescription, this.diagramIdProvider.getId(viewDiagramElementDescription)))
                        .flatMap(Optional::stream)
                        .toList())
                .build());

        return SingleClickOnTwoDiagramElementsTool.newSingleClickOnTwoDiagramElementsTool(toolId)
                .label(viewNodeTool.getName())
                .iconURL(iconURLProvider)
                .candidates(candidates)
                .dialogDescriptionId(dialogDescriptionId)
                .build();
    }

    @Override
    public List<SingleClickOnTwoDiagramElementsCandidate> getConnectorToolsCandidates(EdgeTool viewEdgeTool, DiagramDescription diagramDescription, IDiagramElementDescription diagramElementDescription) {
        return List.of(SingleClickOnTwoDiagramElementsCandidate.newSingleClickOnTwoDiagramElementsCandidate()
                .sources(List.of(diagramElementDescription))
                .targets(viewEdgeTool.getTargetElementDescriptions().stream()
                        .map(viewDiagramElementDescription -> this.diagramDescriptionService.findDiagramElementDescriptionById(diagramDescription, this.diagramIdProvider.getId(viewDiagramElementDescription)))
                        .flatMap(Optional::stream)
                        .toList())
                .build());
    }

    private List<String> getIconURLs(String iconURLsExpression, AQLInterpreter interpreter, VariableManager variableManager) {
        List<String> iconURL = new ArrayList<>();
        if (iconURLsExpression == null || iconURLsExpression.isBlank()) {
            iconURL = List.of(ViewToolImageProvider.EDGE_CREATION_TOOL_ICON);
        } else {
            iconURL = new MultiValueProvider<>(interpreter, iconURLsExpression, String.class).apply(variableManager);
        }
        return iconURL;
    }

}
