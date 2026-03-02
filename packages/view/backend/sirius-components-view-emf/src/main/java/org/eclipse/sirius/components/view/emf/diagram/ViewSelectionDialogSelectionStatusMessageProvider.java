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

import org.eclipse.sirius.components.collaborative.selection.services.api.ISelectionDialogSelectionStatusMessageProvider;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.selection.description.SelectionDescription;
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

    private final IViewEMFMessageService messageService;

    public ViewSelectionDialogSelectionStatusMessageProvider(IObjectSearchService objectSearchService, ILabelService labelService, IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService, IViewEMFMessageService messageService) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.labelService = Objects.requireNonNull(labelService);
        this.viewDiagramDescriptionSearchService = Objects.requireNonNull(viewDiagramDescriptionSearchService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, SelectionDescription selectionDescription) {
        return this.viewDiagramDescriptionSearchService.findViewSelectionDialogDescription(editingContext, selectionDescription.getId()).isPresent();
    }

    @Override
    public String handle(IEditingContext editingContext, SelectionDescription selectionDescription, List<String> selectedObjectIds) {
        String statusMessage = "";
        var selectionCount = selectedObjectIds.size();
        if (selectionCount == 1) {
            var elementLabel = this.objectSearchService.getObject(editingContext, selectedObjectIds.get(0))
                    .map(this.labelService::getStyledLabel)
                    .map(StyledString::toString)
                    .orElse("");
            statusMessage = this.messageService.defaultSelectionDialogSelectionRequiredWithOneSelectedElementStatusMessage(elementLabel);
        } else if (selectionCount > 1) {
            statusMessage = this.messageService.defaultSelectionDialogSelectionRequiredWithManySelectedElementsStatusMessage(selectionCount);
        }
        return statusMessage;
    }
}
