/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.forms.handlers;

import java.util.Objects;

import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.forms.Form;
import org.eclipse.sirius.web.forms.Radio;
import org.eclipse.sirius.web.representations.Status;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeKind;
import org.eclipse.sirius.web.spring.collaborative.api.Monitoring;
import org.eclipse.sirius.web.spring.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.web.spring.collaborative.forms.api.IFormInput;
import org.eclipse.sirius.web.spring.collaborative.forms.api.IFormQueryService;
import org.eclipse.sirius.web.spring.collaborative.forms.dto.EditRadioInput;
import org.eclipse.sirius.web.spring.collaborative.forms.dto.EditRadioSuccessPayload;
import org.eclipse.sirius.web.spring.collaborative.forms.messages.ICollaborativeFormMessageService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * The handler of the edit radio event.
 *
 * @author sbegaudeau
 */
@Service
public class EditRadioEventHandler implements IFormEventHandler {

    private final IFormQueryService formQueryService;

    private final ICollaborativeFormMessageService messageService;

    private final Counter counter;

    public EditRadioEventHandler(IFormQueryService formQueryService, ICollaborativeFormMessageService messageService, MeterRegistry meterRegistry) {
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
        return formInput instanceof EditRadioInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, Form form, IFormInput formInput) {
        this.counter.increment();

        Status status = Status.ERROR;
        if (formInput instanceof EditRadioInput) {
            EditRadioInput input = (EditRadioInput) formInput;

            // @formatter:off
            var optionalRadio = this.formQueryService.findWidget(form, input.getRadioId())
                    .filter(Radio.class::isInstance)
                    .map(Radio.class::cast);

            status = optionalRadio.map(Radio::getNewValueHandler)
                    .map(handler -> handler.apply(input.getNewValue()))
                    .orElse(Status.ERROR);
            // @formatter:on

        }
        if (Status.OK.equals(status)) {
            payloadSink.tryEmitValue(new EditRadioSuccessPayload(formInput.getId()));
            changeDescriptionSink.tryEmitNext(new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, formInput.getRepresentationId(), formInput));
        } else {
            String message = this.messageService.invalidInput(formInput.getClass().getSimpleName(), EditRadioInput.class.getSimpleName());
            payloadSink.tryEmitValue(new ErrorPayload(formInput.getId(), message));
            changeDescriptionSink.tryEmitNext(new ChangeDescription(ChangeKind.NOTHING, formInput.getRepresentationId(), formInput));
        }
    }
}
