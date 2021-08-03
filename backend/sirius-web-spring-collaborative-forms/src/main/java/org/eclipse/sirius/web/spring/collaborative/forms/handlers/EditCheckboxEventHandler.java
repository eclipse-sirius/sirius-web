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

import org.eclipse.sirius.web.collaborative.api.services.ChangeDescription;
import org.eclipse.sirius.web.collaborative.api.services.ChangeKind;
import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.api.services.Monitoring;
import org.eclipse.sirius.web.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.web.collaborative.forms.api.IFormInput;
import org.eclipse.sirius.web.collaborative.forms.api.IFormQueryService;
import org.eclipse.sirius.web.collaborative.forms.api.dto.EditCheckboxInput;
import org.eclipse.sirius.web.collaborative.forms.api.dto.EditCheckboxSuccessPayload;
import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.forms.Checkbox;
import org.eclipse.sirius.web.forms.Form;
import org.eclipse.sirius.web.representations.Status;
import org.eclipse.sirius.web.spring.collaborative.forms.messages.ICollaborativeFormMessageService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * The handler of the edit checkbox event.
 *
 * @author sbegaudeau
 */
@Service
public class EditCheckboxEventHandler implements IFormEventHandler {

    private final IFormQueryService formQueryService;

    private final ICollaborativeFormMessageService messageService;

    private final Counter counter;

    public EditCheckboxEventHandler(IFormQueryService formQueryService, ICollaborativeFormMessageService messageService, MeterRegistry meterRegistry) {
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
        return formInput instanceof EditCheckboxInput;
    }

    @Override
    public EventHandlerResponse handle(Form form, IFormInput formInput) {
        this.counter.increment();

        if (formInput instanceof EditCheckboxInput) {
            EditCheckboxInput input = (EditCheckboxInput) formInput;

            // @formatter:off
            var optionalCheckbox = this.formQueryService.findWidget(form, input.getCheckboxId())
                    .filter(Checkbox.class::isInstance)
                    .map(Checkbox.class::cast);

            var status = optionalCheckbox.map(Checkbox::getNewValueHandler)
                    .map(handler -> handler.apply(input.getNewValue()))
                    .orElse(Status.ERROR);
            // @formatter:on

            if (Status.OK.equals(status)) {
                return new EventHandlerResponse(new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, formInput.getRepresentationId()), new EditCheckboxSuccessPayload(formInput.getId()));
            }
        }

        String message = this.messageService.invalidInput(formInput.getClass().getSimpleName(), EditCheckboxInput.class.getSimpleName());
        return new EventHandlerResponse(new ChangeDescription(ChangeKind.NOTHING, formInput.getRepresentationId()), new ErrorPayload(formInput.getId(), message));
    }
}
