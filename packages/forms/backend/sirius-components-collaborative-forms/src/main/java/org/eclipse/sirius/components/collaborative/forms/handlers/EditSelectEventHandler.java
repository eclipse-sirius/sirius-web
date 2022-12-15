/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.components.collaborative.forms.api.IFormInput;
import org.eclipse.sirius.components.collaborative.forms.api.IFormQueryService;
import org.eclipse.sirius.components.collaborative.forms.dto.EditSelectInput;
import org.eclipse.sirius.components.collaborative.forms.dto.EditSelectSuccessPayload;
import org.eclipse.sirius.components.collaborative.forms.messages.ICollaborativeFormMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.Select;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * The handler of the edit select event.
 *
 * @author lfasani
 */
@Service
public class EditSelectEventHandler implements IFormEventHandler {

    private final IFormQueryService formQueryService;

    private final ICollaborativeFormMessageService messageService;

    private final Counter counter;

    public EditSelectEventHandler(IFormQueryService formQueryService, ICollaborativeFormMessageService messageService, MeterRegistry meterRegistry) {
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
        return formInput instanceof EditSelectInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, Form form, IFormInput formInput) {
        this.counter.increment();
        String message = this.messageService.invalidInput(formInput.getClass().getSimpleName(), EditSelectInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(formInput.getId(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, formInput.getRepresentationId(), formInput);

        if (formInput instanceof EditSelectInput) {
            EditSelectInput input = (EditSelectInput) formInput;

            // @formatter:off
            Optional<Select> optionalSelect = this.formQueryService.findWidget(form, input.getSelectId())
                    .filter(Select.class::isInstance)
                    .map(Select.class::cast);

            IStatus status = optionalSelect.map(Select::getNewValueHandler)
                    .map(handler -> handler.apply(input.getNewValue()))
                    .orElse(new Failure(""));
            // @formatter:on

            if (status instanceof Success) {
                payload = new EditSelectSuccessPayload(formInput.getId());
                changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, formInput.getRepresentationId(), formInput);
            } else if (status instanceof Failure) {
                payload = new ErrorPayload(formInput.getId(), ((Failure) status).getMessage());
            }
        }

        changeDescriptionSink.tryEmitNext(changeDescription);
        payloadSink.tryEmitValue(payload);
    }
}
