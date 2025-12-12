/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import org.eclipse.sirius.components.collaborative.selection.dto.GetSelectionDescriptionMessageInput;
import org.eclipse.sirius.components.collaborative.selection.dto.GetSelectionDescriptionMessagePayload;
import org.eclipse.sirius.components.collaborative.selection.dto.SelectionDialogVariable;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.selection.description.SelectionDescription;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler for the GetSelectionDescriptionMessageInput.
 *
 * @author fbarbin
 */
@Service
public class SelectionDescriptionMessageEventHandler implements IEditingContextEventHandler {

    private static final String TARGET_OBJECT_ID = "targetObjectId";

    private final ICollaborativeMessageService messageService;

    private final IObjectSearchService objectSearchService;

    public SelectionDescriptionMessageEventHandler(ICollaborativeMessageService messageService, IObjectSearchService objectSearchService) {
        this.messageService = Objects.requireNonNull(messageService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof GetSelectionDescriptionMessageInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        if (input instanceof GetSelectionDescriptionMessageInput getSelectionDescriptionMessageInput) {
            VariableManager variableManager = new VariableManager();
            getSelectionDescriptionMessageInput.variables().forEach(variable -> this.addToVariableManager(editingContext, variable, variableManager));
            SelectionDescription selectionDescription = getSelectionDescriptionMessageInput.selectionDescription();
            String message = selectionDescription.getMessageProvider().apply(variableManager);
            payloadSink.tryEmitValue(new GetSelectionDescriptionMessagePayload(input.id(), message));
        } else {
            String message = this.messageService.invalidInput(input.getClass().getSimpleName(), GetSelectionDescriptionMessageInput.class.getSimpleName());
            payloadSink.tryEmitValue(new ErrorPayload(input.id(), message));
        }
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
