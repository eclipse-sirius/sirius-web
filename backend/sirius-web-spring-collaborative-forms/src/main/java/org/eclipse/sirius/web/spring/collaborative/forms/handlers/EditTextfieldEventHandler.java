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
import org.eclipse.sirius.web.forms.Form;
import org.eclipse.sirius.web.forms.Textfield;
import org.eclipse.sirius.web.representations.Status;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeKind;
import org.eclipse.sirius.web.spring.collaborative.api.EventHandlerResponse;
import org.eclipse.sirius.web.spring.collaborative.api.Monitoring;
import org.eclipse.sirius.web.spring.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.web.spring.collaborative.forms.api.IFormInput;
import org.eclipse.sirius.web.spring.collaborative.forms.api.IFormQueryService;
import org.eclipse.sirius.web.spring.collaborative.forms.dto.EditTextfieldInput;
import org.eclipse.sirius.web.spring.collaborative.forms.dto.EditTextfieldSuccessPayload;
import org.eclipse.sirius.web.spring.collaborative.forms.messages.ICollaborativeFormMessageService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * The handler of the edit textfield event.
 *
 * @author sbegaudeau
 */
@Service
public class EditTextfieldEventHandler implements IFormEventHandler {

    private final IFormQueryService formQueryService;

    private final ICollaborativeFormMessageService messageService;

    private final Counter counter;

    public EditTextfieldEventHandler(IFormQueryService formQueryService, ICollaborativeFormMessageService messageService, MeterRegistry meterRegistry) {
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
        return formInput instanceof EditTextfieldInput;
    }

    @Override
    public EventHandlerResponse handle(Form form, IFormInput formInput) {
        this.counter.increment();

        if (formInput instanceof EditTextfieldInput) {
            EditTextfieldInput input = (EditTextfieldInput) formInput;

            // @formatter:off
            var optionalTextfield = this.formQueryService.findWidget(form, input.getTextfieldId())
                    .filter(Textfield.class::isInstance)
                    .map(Textfield.class::cast);

            var status = optionalTextfield.map(Textfield::getNewValueHandler)
                    .map(handler -> handler.apply(input.getNewValue()))
                    .orElse(Status.ERROR);
            // @formatter:on

            if (Status.OK.equals(status)) {
                return new EventHandlerResponse(new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, formInput.getRepresentationId()), new EditTextfieldSuccessPayload(formInput.getId()));
            }
        }

        String message = this.messageService.invalidInput(formInput.getClass().getSimpleName(), EditTextfieldInput.class.getSimpleName());
        return new EventHandlerResponse(new ChangeDescription(ChangeKind.NOTHING, formInput.getRepresentationId()), new ErrorPayload(formInput.getId(), message));
    }
}
