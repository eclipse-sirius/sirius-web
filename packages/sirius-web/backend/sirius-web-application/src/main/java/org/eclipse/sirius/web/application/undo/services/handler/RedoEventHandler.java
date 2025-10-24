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
package org.eclipse.sirius.web.application.undo.services.handler;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.undo.dto.RedoInput;
import org.eclipse.sirius.web.application.undo.services.api.IRepresentationChangeHandler;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to redo mutations.
 *
 * @author mcharfadi
 */
@Service
public class RedoEventHandler implements IEditingContextEventHandler {

    private final ICollaborativeDiagramMessageService messageService;

    private final List<IRepresentationChangeHandler> representationEventProcessorChangeHandlers;

    public RedoEventHandler(ICollaborativeDiagramMessageService messageService, List<IRepresentationChangeHandler> representationEventProcessorChangeHandlers) {
        this.messageService = Objects.requireNonNull(messageService);
        this.representationEventProcessorChangeHandlers = Objects.requireNonNull(representationEventProcessorChangeHandlers);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof RedoInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), RedoInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.id(), message);

        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);
        if (editingContext instanceof EditingContext siriusEditingContext && input instanceof RedoInput redoInput) {
            var emfChangeDescription = siriusEditingContext.getInputId2change().get(redoInput.inputId());
            if (emfChangeDescription != null) {
                emfChangeDescription.applyAndReverse();
            }
            representationEventProcessorChangeHandlers.stream()
                    .filter(changeHandler -> changeHandler.canHandle(redoInput.inputId(), siriusEditingContext))
                    .forEach(changeHandler -> changeHandler.redo(redoInput.inputId(), siriusEditingContext));
            changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), input);
            payload = new SuccessPayload(input.id());
        }
        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

}
