/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
import org.eclipse.sirius.components.collaborative.forms.dto.ClickListItemInput;
import org.eclipse.sirius.components.collaborative.forms.dto.ClickListItemSuccessPayload;
import org.eclipse.sirius.components.collaborative.forms.messages.ICollaborativeFormMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
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
 * The handler of the click list item event.
 *
 * @author fbarbin
 */
@Service
public class ClickListItemEventHandler implements IFormEventHandler {

    private final ICollaborativeFormMessageService messageService;

    private final Counter counter;

    private final IFormQueryService formQueryService;

    public ClickListItemEventHandler(IFormQueryService formQueryService, ICollaborativeFormMessageService messageService, MeterRegistry meterRegistry) {
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
        return formInput instanceof ClickListItemInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, Form form, IFormInput formInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(formInput.getClass().getSimpleName(), ClickListItemInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(formInput.getId(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, formInput.getRepresentationId(), formInput);

        if (formInput instanceof ClickListItemInput) {
            ClickListItemInput input = (ClickListItemInput) formInput;

            // @formatter:off
            var optionalListItem = this.formQueryService.findWidget(form, input.getListId())
                    .filter(List.class::isInstance)
                    .map(List.class::cast)
                    .stream()
                    .map(List::getItems)
                    .flatMap(Collection::stream)
                    .filter(item -> item.getId().toString().equals(input.getListItemId()))
                    .findFirst();

            var status = optionalListItem.map(ListItem::getClickHandler)
                    .map(handler -> handler.apply(input.getClickEventKind()))
                    .orElse(new Failure("")); //$NON-NLS-1$
            // @formatter:on

            if (status instanceof Success) {
                Success success = (Success) status;
                changeDescription = new ChangeDescription(success.getChangeKind(), formInput.getRepresentationId(), formInput, success.getParameters());
                payload = new ClickListItemSuccessPayload(formInput.getId());
            } else if (status instanceof Failure) {
                payload = new ErrorPayload(formInput.getId(), ((Failure) status).getMessage());
            }
        }

        changeDescriptionSink.tryEmitNext(changeDescription);
        payloadSink.tryEmitValue(payload);
    }

}
