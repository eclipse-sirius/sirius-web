/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.forms.handlers;

import java.util.Collection;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.components.collaborative.forms.api.IFormInput;
import org.eclipse.sirius.components.collaborative.forms.api.IFormQueryService;
import org.eclipse.sirius.components.collaborative.forms.dto.DeleteListItemInput;
import org.eclipse.sirius.components.collaborative.forms.dto.DeleteListItemSuccessPayload;
import org.eclipse.sirius.components.collaborative.forms.messages.ICollaborativeFormMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.List;
import org.eclipse.sirius.components.forms.ListItem;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.Success;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * The handler of the delete list item event.
 *
 * @author gcoutable
 */
@Service
public class DeleteListItemEventHandler implements IFormEventHandler {

    private final ICollaborativeFormMessageService messageService;

    private final Counter counter;

    private final IFormQueryService formQueryService;

    public DeleteListItemEventHandler(IFormQueryService formQueryService, ICollaborativeFormMessageService messageService, MeterRegistry meterRegistry) {
        this.formQueryService = Objects.requireNonNull(formQueryService);
        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IFormInput formInput) {
        return formInput instanceof DeleteListItemInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, Form form, IFormInput formInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(formInput.getClass().getSimpleName(), DeleteListItemInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(formInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, formInput.representationId(), formInput);

        if (formInput instanceof DeleteListItemInput) {
            DeleteListItemInput input = (DeleteListItemInput) formInput;

            // @formatter:off
            var optionalListItem = this.formQueryService.findWidget(form, input.listId())
                    .filter(List.class::isInstance)
                    .map(List.class::cast)
                    .stream()
                    .map(List::getItems)
                    .flatMap(Collection::stream)
                    .filter(item -> item.getId().toString().equals(input.listItemId()))
                    .findFirst();

            var status = optionalListItem.map(ListItem::getDeleteHandler)
                    .map(handler -> handler.get())
                    .orElse(new Failure(""));
            // @formatter:on

            if (status instanceof Success) {
                Success success = (Success) status;
                changeDescription = new ChangeDescription(success.getChangeKind(), formInput.representationId(), formInput, success.getParameters());
                payload = new DeleteListItemSuccessPayload(formInput.id());
            } else if (status instanceof Failure) {
                payload = new ErrorPayload(formInput.id(), ((Failure) status).getMessage());
            }
        }

        changeDescriptionSink.tryEmitNext(changeDescription);
        payloadSink.tryEmitValue(payload);
    }

}
