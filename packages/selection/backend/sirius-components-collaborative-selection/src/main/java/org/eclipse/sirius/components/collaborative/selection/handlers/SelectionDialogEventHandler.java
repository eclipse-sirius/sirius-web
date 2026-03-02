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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.collaborative.selection.dto.GetSelectionDialogInput;
import org.eclipse.sirius.components.collaborative.selection.dto.GetSelectionDialogPayload;
import org.eclipse.sirius.components.collaborative.selection.dto.SelectionDialogVariable;
import org.eclipse.sirius.components.collaborative.selection.services.api.ISelectionDialogProvider;
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
public class SelectionDialogEventHandler implements IEditingContextEventHandler {

    private static final String TARGET_OBJECT_ID = "targetObjectId";

    private final ICollaborativeMessageService messageService;

    private final IObjectSearchService objectSearchService;

    private final List<ISelectionDialogProvider> selectionDialogProviders;

    public SelectionDialogEventHandler(ICollaborativeMessageService messageService, IObjectSearchService objectSearchService, List<ISelectionDialogProvider> selectionDialogProviders) {
        this.messageService = Objects.requireNonNull(messageService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.selectionDialogProviders = Objects.requireNonNull(selectionDialogProviders);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof GetSelectionDialogInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), GetSelectionDialogInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.id(), message);
        if (input instanceof GetSelectionDialogInput getSelectionDialogInput) {
            var variableManager = new VariableManager();
            getSelectionDialogInput.variables().forEach(variable -> this.addToVariableManager(editingContext, variable, variableManager));
            var selectionDescription = getSelectionDialogInput.selectionDescription();

            var optionalProvider = this.selectionDialogProviders.stream()
                    .filter(provider -> provider.canHandle(editingContext, selectionDescription))
                    .findFirst();
            if (optionalProvider.isPresent()) {
                var provider = optionalProvider.get();
                var dialog = provider.handle(editingContext, selectionDescription, variableManager);
                payload = new GetSelectionDialogPayload(input.id(), dialog);
            }
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
