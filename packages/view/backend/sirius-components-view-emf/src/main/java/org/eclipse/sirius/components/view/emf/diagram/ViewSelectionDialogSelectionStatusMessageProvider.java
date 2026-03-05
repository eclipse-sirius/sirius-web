/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.selection.services.api.ISelectionDialogSelectionStatusMessageProvider;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.selection.description.SelectionDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.diagram.SelectionDialogDescription;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewDiagramDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.messages.IViewEMFMessageService;
import org.springframework.stereotype.Service;

/**
 * Used to compute the selection status message.
 *
 * @author sbegaudeau
 */
@Service
public class ViewSelectionDialogSelectionStatusMessageProvider implements ISelectionDialogSelectionStatusMessageProvider {

    private final IObjectSearchService objectSearchService;

    private final ILabelService labelService;

    private final IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService;

    private final IViewAQLInterpreterFactory viewAQLInterpreterFactory;

    private final IViewEMFMessageService messageService;

    public ViewSelectionDialogSelectionStatusMessageProvider(IObjectSearchService objectSearchService, ILabelService labelService, IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService,
            IViewAQLInterpreterFactory viewAQLInterpreterFactory, IViewEMFMessageService messageService) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.labelService = Objects.requireNonNull(labelService);
        this.viewDiagramDescriptionSearchService = Objects.requireNonNull(viewDiagramDescriptionSearchService);
        this.viewAQLInterpreterFactory = Objects.requireNonNull(viewAQLInterpreterFactory);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, SelectionDescription selectionDescription) {
        return this.viewDiagramDescriptionSearchService.findViewSelectionDialogDescription(editingContext, selectionDescription.getId())
                .map(EcoreUtil::getRootContainer)
                .filter(View.class::isInstance)
                .isPresent();
    }

    @Override
    public String handle(IEditingContext editingContext, SelectionDescription selectionDescription, List<String> selectedObjectIds) {
        var selectedObjects = selectedObjectIds.stream()
                .map(selectedObjectId -> this.objectSearchService.getObject(editingContext, selectedObjectId))
                .flatMap(Optional::stream)
                .toList();
        String defaultStatusMessage = this.getDefaultSelectionRequiredWithSelectionStatusMessage(selectedObjects);
        SelectionDialogDescription viewSelectionDescription = this.viewDiagramDescriptionSearchService.findViewSelectionDialogDescription(editingContext, selectionDescription.getId()).orElse(null);
        return Optional.ofNullable(EcoreUtil.getRootContainer(viewSelectionDescription))
                .filter(View.class::isInstance)
                .map(View.class::cast)
                .map(view -> this.viewAQLInterpreterFactory.createInterpreter(editingContext, view))
                .map(aqlInterpreter -> this.getSelectionDialogStatusMessage(viewSelectionDescription, aqlInterpreter, selectedObjects, defaultStatusMessage))
                .orElse("");
    }

    private String getSelectionDialogStatusMessage(SelectionDialogDescription selectionDescription, AQLInterpreter interpreter, List<Object> selectedObjects, String defaultStatusMessage) {
        String statusMessage = defaultStatusMessage;
        var selectionRequiredWithSelectionStatusMessageExpression = selectionDescription.getSelectionRequiredWithSelectionStatusMessageExpression();
        var safeSelectionRequiredWithSelectionStatusMessageExpression = Optional.ofNullable(selectionRequiredWithSelectionStatusMessageExpression).orElse("");
        if (!safeSelectionRequiredWithSelectionStatusMessageExpression.isBlank()) {
            var variableManager = new VariableManager();
            variableManager.put("selectedObjects", selectedObjects);
            statusMessage = interpreter.evaluateExpression(variableManager.getVariables(), safeSelectionRequiredWithSelectionStatusMessageExpression)
                    .asString()
                    .orElse(defaultStatusMessage);
        }
        return statusMessage;
    }

    private String getDefaultSelectionRequiredWithSelectionStatusMessage(List<Object> selectedObjects) {
        String statusMessage = "";
        var selectionCount = selectedObjects.size();
        if (selectionCount == 1) {
            var elementLabel = this.labelService.getStyledLabel(selectedObjects.get(0)).toString();
            statusMessage = this.messageService.defaultSelectionDialogSelectionRequiredWithOneSelectedElementStatusMessage(elementLabel);
        } else if (selectionCount > 1) {
            statusMessage = this.messageService.defaultSelectionDialogSelectionRequiredWithManySelectedElementsStatusMessage(selectionCount);
        }
        return statusMessage;
    }
}
