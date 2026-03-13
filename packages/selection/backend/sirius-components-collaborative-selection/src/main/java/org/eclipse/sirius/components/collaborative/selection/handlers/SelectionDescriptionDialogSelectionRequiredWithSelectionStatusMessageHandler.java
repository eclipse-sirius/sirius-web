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
package org.eclipse.sirius.components.collaborative.selection.handlers;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.collaborative.selection.dto.GetSelectionDescriptionDialogSelectionRequiredWithSelectionStatusMessageInput;
import org.eclipse.sirius.components.collaborative.selection.dto.GetSelectionDescriptionDialogSelectionRequiredWithSelectionStatusMessagePayload;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks;

/**
 * The handler used to compute the status message of the selection dialog when the user has made a selection in the tree representation of the selection dialog.
 *
 * @author gcoutable
 */
@Service
public class SelectionDescriptionDialogSelectionRequiredWithSelectionStatusMessageHandler implements IEditingContextEventHandler {

    private static final String TREE_SELECTION = "treeSelection";

    private final ICollaborativeMessageService messageService;

    public SelectionDescriptionDialogSelectionRequiredWithSelectionStatusMessageHandler(ICollaborativeMessageService messageService) {
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof GetSelectionDescriptionDialogSelectionRequiredWithSelectionStatusMessageInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), GetSelectionDescriptionDialogSelectionRequiredWithSelectionStatusMessageInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.id(), message);
        if (input instanceof GetSelectionDescriptionDialogSelectionRequiredWithSelectionStatusMessageInput getSelectionDescriptionDialogSelectionRequiredWithSelectionStatusMessageInput) {
            var variableManager = new VariableManager();
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
            variableManager.put(TREE_SELECTION, getSelectionDescriptionDialogSelectionRequiredWithSelectionStatusMessageInput.treeSelection());
            var selectionDescription = getSelectionDescriptionDialogSelectionRequiredWithSelectionStatusMessageInput.selectionDescription();
            var statusMessage = selectionDescription.getDialogSelectionRequiredWithSelectionStatusMessageProvider().apply(variableManager);
            payload = new GetSelectionDescriptionDialogSelectionRequiredWithSelectionStatusMessagePayload(input.id(), statusMessage);
        }
        payloadSink.tryEmitValue(payload);
    }
}
