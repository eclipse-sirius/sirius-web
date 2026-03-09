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
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.collaborative.selection.dto.GetSelectionDescriptionDialogInput;
import org.eclipse.sirius.components.collaborative.selection.dto.GetSelectionDescriptionDialogPayload;
import org.eclipse.sirius.components.collaborative.selection.dto.SelectionDialogVariable;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks;

/**
 * Handler returning the selection dialog customization from the selection dialog description.
 *
 * @author gcoutable
 */
@Service
public class SelectionDescriptionDialogEventHandler implements IEditingContextEventHandler {

    private static final String TARGET_OBJECT_ID = "targetObjectId";

    private final ICollaborativeMessageService messageService;

    private final IObjectSearchService objectSearchService;

    public SelectionDescriptionDialogEventHandler(ICollaborativeMessageService messageService, IObjectSearchService objectSearchService) {
        this.messageService = Objects.requireNonNull(messageService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof GetSelectionDescriptionDialogInput;
    }


    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), GetSelectionDescriptionDialogInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.id(), message);
        if (input instanceof GetSelectionDescriptionDialogInput getSelectionDescriptionDialogInput) {
            var variableManager = new VariableManager();
            getSelectionDescriptionDialogInput.variables().forEach(variable -> this.addToVariableManager(editingContext, variable, variableManager));
            var selectionDescription = getSelectionDescriptionDialogInput.selectionDescription();
            var dialog = selectionDescription.getDialogProvider().apply(variableManager);
            payload = new GetSelectionDescriptionDialogPayload(input.id(), dialog);
        }
        payloadSink.tryEmitValue(payload);
    }

    private void addToVariableManager(IEditingContext editingContext, SelectionDialogVariable variable, VariableManager variableManager) {
        Optional<Object> optionalObject = this.objectSearchService.getObject(editingContext, variable.value());
        if (optionalObject.isPresent()) {
            String variableName;
            if (TARGET_OBJECT_ID.equals(variable.name())) {
                variableName = VariableManager.SELF;
            } else {
                variableName = variable.name();
            }
            variableManager.put(variableName, optionalObject.get());
        }
    }
}
