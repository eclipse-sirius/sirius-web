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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ITool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.collaborative.dto.KeyBinding;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.components.view.emf.diagram.ViewToolImageProvider;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.IKeyBindingConverter;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.INodeToolConverter;
import org.eclipse.sirius.components.view.emf.form.converters.MultiValueProvider;
import org.springframework.stereotype.Service;

/**
 * Used to create node tools.
 *
 * @author sbegaudeau
 */
@Service
public class NodeToolConverter implements INodeToolConverter {

    private final IDiagramIdProvider diagramIdProvider;

    private final IKeyBindingConverter keyBindingkeyBindingConverter;

    public NodeToolConverter(IDiagramIdProvider diagramIdProvider, IKeyBindingConverter keyBindingkeyBindingConverter) {
        this.diagramIdProvider = Objects.requireNonNull(diagramIdProvider);
        this.keyBindingkeyBindingConverter = Objects.requireNonNull(keyBindingkeyBindingConverter);
    }

    @Override
    public ITool createNodeTool(AQLInterpreter interpreter, NodeTool viewNodeTool, boolean appliesToDiagramRoot, VariableManager variableManager) {
        String toolId = UUID.nameUUIDFromBytes(EcoreUtil.getURI(viewNodeTool).toString().getBytes()).toString();
        List<String> iconURLs = this.getIconURLs(viewNodeTool, interpreter, variableManager);
        String dialogDescriptionId = "";
        if (viewNodeTool.getDialogDescription() != null) {
            dialogDescriptionId = this.diagramIdProvider.getId(viewNodeTool.getDialogDescription());
        }

        List<KeyBinding> keyBindings = viewNodeTool.getKeyBindings().stream()
                .map(this.keyBindingkeyBindingConverter::createKeyBinding)
                .flatMap(Optional::stream)
                .toList();

        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(toolId)
                .label(viewNodeTool.getName())
                .iconURL(iconURLs)
                .dialogDescriptionId(dialogDescriptionId)
                .targetDescriptions(List.of())
                .appliesToDiagramRoot(appliesToDiagramRoot)
                .withImpactAnalysis(viewNodeTool.isWithImpactAnalysis())
                .keyBindings(keyBindings)
                .build();
    }

    private List<String> getIconURLs(NodeTool nodeTool, AQLInterpreter interpreter, VariableManager variableManager) {
        List<String> iconURL = null;
        String iconURLsExpression = nodeTool.getIconURLsExpression();
        if (iconURLsExpression == null || iconURLsExpression.isBlank()) {
            iconURL = List.of(ViewToolImageProvider.NODE_CREATION_TOOL_ICON);
        } else {
            iconURL = new MultiValueProvider<>(interpreter, iconURLsExpression, String.class).apply(variableManager);
        }
        return iconURL;
    }
}
