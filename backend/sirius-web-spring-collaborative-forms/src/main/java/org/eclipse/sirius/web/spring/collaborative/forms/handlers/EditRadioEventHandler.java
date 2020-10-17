/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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

import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.web.collaborative.forms.api.IFormInput;
import org.eclipse.sirius.web.collaborative.forms.api.IFormService;
import org.eclipse.sirius.web.collaborative.forms.api.dto.EditRadioInput;
import org.eclipse.sirius.web.collaborative.forms.api.dto.EditRadioSuccessPayload;
import org.eclipse.sirius.web.forms.Form;
import org.eclipse.sirius.web.forms.Radio;
import org.eclipse.sirius.web.representations.Status;
import org.eclipse.sirius.web.services.api.dto.ErrorPayload;
import org.eclipse.sirius.web.spring.collaborative.forms.messages.ICollaborativeFormMessageService;
import org.springframework.stereotype.Service;

/**
 * The handler of the edit radio event.
 *
 * @author sbegaudeau
 */
@Service
public class EditRadioEventHandler implements IFormEventHandler {

    private final IFormService formService;

    private final ICollaborativeFormMessageService messageService;

    public EditRadioEventHandler(IFormService formService, ICollaborativeFormMessageService messageService) {
        this.formService = Objects.requireNonNull(formService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean canHandle(IFormInput formInput) {
        return formInput instanceof EditRadioInput;
    }

    @Override
    public EventHandlerResponse handle(Form form, IFormInput formInput) {
        if (formInput instanceof EditRadioInput) {
            EditRadioInput input = (EditRadioInput) formInput;

            // @formatter:off
            var optionalRadio = this.formService.findWidget(form, input.getRadioId())
                    .filter(Radio.class::isInstance)
                    .map(Radio.class::cast);

            var status = optionalRadio.map(Radio::getNewValueHandler)
                    .map(handler -> handler.apply(input.getNewValue()))
                    .orElse(Status.ERROR);
            // @formatter:on

            return new EventHandlerResponse(true, representation -> true, new EditRadioSuccessPayload(status.toString()));
        }

        String message = this.messageService.invalidInput(formInput.getClass().getSimpleName(), EditRadioInput.class.getSimpleName());
        return new EventHandlerResponse(false, representation -> false, new ErrorPayload(message));
    }
}
