/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.graphql.tests;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks;

/**
 * Used to execute some function using the editing context.
 *
 * @author sbegaudeau
 */
@Service
public class ExecuteEditingContextFunctionEventHandler implements IEditingContextEventHandler {

    private ICollaborativeMessageService messageService;

    public ExecuteEditingContextFunctionEventHandler(ICollaborativeMessageService messageService) {
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof ExecuteEditingContextFunctionInput;
    }

    @Override
    @SuppressWarnings("checkstyle:IllegalCatch")
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), ExecuteEditingContextFunctionInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);

        if (input instanceof ExecuteEditingContextFunctionInput executeEditingContextFunctionInput) {
            changeDescription = executeEditingContextFunctionInput.changeDescription();

            try {
                payload = executeEditingContextFunctionInput.function().apply(editingContext, input);
            } catch (Exception exception) {
                payload = new ErrorPayload(input.id(), exception.getMessage());
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
