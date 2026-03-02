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

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.selection.services.api.ISelectionDialogProvider;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.selection.description.SelectionDescription;
import org.eclipse.sirius.components.selection.description.SelectionDialog;
import org.eclipse.sirius.components.selection.description.SelectionDialogAction;
import org.eclipse.sirius.components.selection.description.SelectionDialogConfirmButtonLabels;
import org.eclipse.sirius.components.selection.description.SelectionDialogStatusMessages;
import org.eclipse.sirius.components.selection.description.SelectionDialogTitles;
import org.eclipse.sirius.components.view.diagram.SelectionDialogDescription;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewDiagramDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.messages.IViewEMFMessageService;
import org.springframework.stereotype.Service;

/**
 * Used to compute the {@link SelectionDialog}.
 *
 * @author sbegaudeau
 */
@Service
public class ViewSelectionDialogProvider implements ISelectionDialogProvider {

    private final IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService;

    private final IViewEMFMessageService messageService;

    public ViewSelectionDialogProvider(IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService, IViewEMFMessageService messageService) {
        this.viewDiagramDescriptionSearchService = Objects.requireNonNull(viewDiagramDescriptionSearchService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, SelectionDescription selectionDescription) {
        return this.viewDiagramDescriptionSearchService.findViewSelectionDialogDescription(editingContext, selectionDescription.getId()).isPresent();
    }

    @Override
    public SelectionDialog handle(IEditingContext editingContext, SelectionDescription selectionDescription, VariableManager variableManager) {
        SelectionDialogDescription viewSelectionDescription = this.viewDiagramDescriptionSearchService.findViewSelectionDialogDescription(editingContext, selectionDescription.getId()).orElse(null);
        return new SelectionDialog(
                new SelectionDialogTitles(this.messageService.defaultSelectionDialogTitle(), this.messageService.defaultSelectionDialogTitle(), this.messageService.defaultSelectionDialogTitle()),
                this.getSelectionDialogDescription(viewSelectionDescription),
                new SelectionDialogAction(this.getNoSelectionLabel(viewSelectionDescription), this.messageService.defaultSelectionDialogNoSelectionActionDescription()),
                new SelectionDialogAction(this.messageService.defaultSelectionDialogWithSelectionActionLabel(), this.messageService.defaultSelectionDialogWithSelectionActionDescription()),
                new SelectionDialogStatusMessages(this.messageService.defaultSelectionDialogNoSelectionActionStatusMessage(), this.messageService.defaultSelectionDialogSelectionRequiredWithoutSelectionStatusMessage()),
                new SelectionDialogConfirmButtonLabels(this.messageService.defaultSelectionDialogConfirmButtonLabel(), this.messageService.defaultSelectionDialogSelectionRequiredWithoutSelectionConfirmButtonLabel(), this.messageService.defaultSelectionDialogConfirmButtonLabel())
        );
    }

    private String getSelectionDialogDescription(SelectionDialogDescription viewSelectionDescription) {
        String message = Optional.ofNullable(viewSelectionDescription).map(SelectionDialogDescription::getSelectionMessage).orElse("");
        if (message.isBlank()) {
            if (Optional.ofNullable(viewSelectionDescription).map(SelectionDialogDescription::isOptional).orElse(false)) {
                message = this.messageService.defaultSelectionDialogWithOptionalSelectionDescription();
            } else {
                message = this.messageService.defaultSelectionDialogWithMandatorySelectionDescription();
            }
        }
        return message;
    }

    private String getNoSelectionLabel(SelectionDialogDescription viewSelectionDescription) {
        String noSelectionLabel = Optional.ofNullable(viewSelectionDescription).map(SelectionDialogDescription::getNoSelectionLabel).orElse("");
        if (noSelectionLabel.isBlank()) {
            noSelectionLabel = this.messageService.defaultSelectionDialogNoSelectionActionLabel();
        }
        return noSelectionLabel;
    }
}
