/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import org.eclipse.sirius.components.collaborative.diagrams.dto.IPaletteEntry;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ITool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.Palette;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolSection;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.interpreter.Status;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.diagram.DiagramToolSection;
import org.eclipse.sirius.components.view.diagram.Tool;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionIdProvider;
import org.eclipse.sirius.components.view.emf.diagram.ToolFinder;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.IDiagramPaletteProvider;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.INodeToolFactory;
import org.springframework.stereotype.Service;

/**
 * Used to compute the palette of a diagram.
 *
 * @author sbegaudeau
 */
@Service
public class DiagramPaletteProvider implements IDiagramPaletteProvider {

    private final IURLParser urlParser;

    private final INodeToolFactory nodeToolFactory;

    public DiagramPaletteProvider(IURLParser urlParser, INodeToolFactory nodeToolFactory) {
        this.urlParser = Objects.requireNonNull(urlParser);
        this.nodeToolFactory = Objects.requireNonNull(nodeToolFactory);
    }

    @Override
    public Palette getDiagramPalette(AQLInterpreter interpreter, DiagramDescription diagramDescription, org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription, VariableManager variableManager) {
        Palette diagramPalette = null;
        var toolFinder = new ToolFinder();
        Optional<String> sourceElementId = this.getSourceElementId(diagramDescription.getId());
        if (sourceElementId.isPresent()) {
            String diagramPaletteId = "siriusComponents://diagramPalette?diagramId=" + sourceElementId.get();

            List<IPaletteEntry> paletteEntries = new ArrayList<>();
            toolFinder.findNodeTools(viewDiagramDescription).stream()
                    .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                    .map(tool -> this.nodeToolFactory.createNodeTool(interpreter, tool, true, variableManager))
                    .forEach(paletteEntries::add);

            toolFinder.findToolSections(viewDiagramDescription).stream()
                    .map(toolSection -> this.createToolSection(toolSection, variableManager, interpreter))
                    .forEach(paletteEntries::add);

            List<ITool> quickAccessTools = new ArrayList<>();
            toolFinder.findQuickAccessDiagramTools(viewDiagramDescription).stream()
                    .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                    .map(tool -> this.nodeToolFactory.createNodeTool(interpreter, tool, true, variableManager))
                    .forEach(quickAccessTools::add);

            diagramPalette = Palette.newPalette(diagramPaletteId)
                    .quickAccessTools(quickAccessTools)
                    .paletteEntries(paletteEntries)
                    .build();
        }
        return diagramPalette;
    }

    private Optional<String> getSourceElementId(String descriptionId) {
        var parameters = this.urlParser.getParameterValues(descriptionId);
        return Optional.ofNullable(parameters.get(IRepresentationDescriptionIdProvider.SOURCE_ELEMENT_ID)).orElse(List.of()).stream().findFirst();
    }

    private boolean checkPrecondition(Tool tool, VariableManager variableManager, AQLInterpreter interpreter) {
        String precondition = tool.getPreconditionExpression();
        if (precondition != null && !precondition.isBlank()) {
            Result result = interpreter.evaluateExpression(variableManager.getVariables(), precondition);
            return result.getStatus().compareTo(Status.WARNING) <= 0 && result.asBoolean().orElse(Boolean.FALSE);
        }
        return true;
    }

    private ToolSection createToolSection(DiagramToolSection toolSection, VariableManager variableManager, AQLInterpreter interpreter) {
        String toolSelectionId = UUID.nameUUIDFromBytes(EcoreUtil.getURI(toolSection).toString().getBytes()).toString();

        return ToolSection.newToolSection(toolSelectionId)
                .label(toolSection.getName())
                .iconURL(List.of())
                .tools(toolSection.getNodeTools().stream()
                        .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                        .map(tool -> this.nodeToolFactory.createNodeTool(interpreter, tool, true, variableManager))
                        .toList())
                .build();
    }
}
