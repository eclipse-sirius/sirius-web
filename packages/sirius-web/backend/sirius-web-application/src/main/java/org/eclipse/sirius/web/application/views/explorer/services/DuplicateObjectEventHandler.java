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
package org.eclipse.sirius.web.application.views.explorer.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.web.application.views.explorer.dto.DuplicateObjectInput;
import org.eclipse.sirius.web.application.views.explorer.dto.DuplicateObjectSuccessPayload;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerModificationService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to duplicate an object.
 *
 * @author lfasani
 */
@Service
public class DuplicateObjectEventHandler implements IEditingContextEventHandler {

    private final IObjectService objectService;

    private final IExplorerModificationService explorerModificationService;

    private final ICollaborativeMessageService messageService;

    private final IFeedbackMessageService feedbackMessageService;

    private final Counter counter;

    public DuplicateObjectEventHandler(IObjectService objectService, IExplorerModificationService explorerModificationService, ICollaborativeMessageService messageService, IFeedbackMessageService feedbackMessageService,
            MeterRegistry meterRegistry) {
        this.objectService = Objects.requireNonNull(objectService);
        this.explorerModificationService = Objects.requireNonNull(explorerModificationService);
        this.messageService = Objects.requireNonNull(messageService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER).tag(Monitoring.NAME, this.getClass().getSimpleName()).register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof DuplicateObjectInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        List<Message> messages = List.of(new Message(this.messageService.invalidInput(input.getClass().getSimpleName(), DuplicateObjectInput.class.getSimpleName()), MessageLevel.ERROR));
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);
        IPayload payload = null;

        if (input instanceof DuplicateObjectInput duplicateObjectInput) {
            Optional<Object> duplicatedObjectOptional = this.explorerModificationService.duplicateObject(editingContext, duplicateObjectInput.objectId(), duplicateObjectInput.containerId(),
                    duplicateObjectInput.containmentFeatureName(), duplicateObjectInput.duplicateContent(), duplicateObjectInput.copyOutgoingReferences(),
                    duplicateObjectInput.updateIncomingReferences());

            if (duplicatedObjectOptional.isPresent()) {
                payload = new DuplicateObjectSuccessPayload(input.id(), duplicatedObjectOptional.get(), this.feedbackMessageService.getFeedbackMessages());
                changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), input);
            } else {
                payload = new ErrorPayload(input.id(), Optional.of(this.feedbackMessageService.getFeedbackMessages())
                        .orElseGet(() -> List.of(new Message("The duplication of the object has failed", MessageLevel.ERROR))));
            }
        }

        if (payload == null) {
            payload = new ErrorPayload(input.id(), messages);
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
