/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import org.eclipse.sirius.components.collaborative.selection.dto.GetSelectionDescriptionNoSelectionLabelInput;
import org.eclipse.sirius.components.collaborative.selection.dto.GetSelectionDescriptionNoSelectionLabelPayload;
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
 * Handler for the {@link org.eclipse.sirius.components.collaborative.selection.dto.GetSelectionDescriptionNoSelectionLabelInput}.
 *
 * @author gcoutable
 */
@Service
public class SelectionDescriptionNoSelectionLabelEventHandler implements IEditingContextEventHandler {

    private static final String TARGET_OBJECT_ID = "targetObjectId";

    private final ICollaborativeMessageService messageService;

    private final IObjectSearchService objectSearchService;

    public SelectionDescriptionNoSelectionLabelEventHandler(ICollaborativeMessageService messageService, IObjectSearchService objectSearchService) {
        this.messageService = Objects.requireNonNull(messageService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof GetSelectionDescriptionNoSelectionLabelInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), GetSelectionDescriptionNoSelectionLabelInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.id(), message);
        if (input instanceof GetSelectionDescriptionNoSelectionLabelInput getSelectionDescriptionNoSelectionLabelInput) {
            VariableManager variableManager = new VariableManager();
            getSelectionDescriptionNoSelectionLabelInput.variables().forEach(variable -> this.addToVariableManager(editingContext, variable, variableManager));
            SelectionDescription selectionDescription = getSelectionDescriptionNoSelectionLabelInput.selectionDescription();
            String noSelectionLabel = selectionDescription.getNoSelectionLabelProvider().apply(variableManager);
            payload = new GetSelectionDescriptionNoSelectionLabelPayload(input.id(), noSelectionLabel);
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
