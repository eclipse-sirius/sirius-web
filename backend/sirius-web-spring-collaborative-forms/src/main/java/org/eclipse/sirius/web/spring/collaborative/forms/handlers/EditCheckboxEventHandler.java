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
import org.eclipse.sirius.web.collaborative.forms.api.dto.EditCheckboxInput;
import org.eclipse.sirius.web.collaborative.forms.api.dto.EditCheckboxSuccessPayload;
import org.eclipse.sirius.web.forms.Checkbox;
import org.eclipse.sirius.web.forms.Form;
import org.eclipse.sirius.web.representations.Status;
import org.eclipse.sirius.web.services.api.dto.ErrorPayload;
import org.eclipse.sirius.web.spring.collaborative.forms.messages.ICollaborativeFormMessageService;
import org.springframework.stereotype.Service;

/**
 * The handler of the edit checkbox event.
 *
 * @author sbegaudeau
 */
@Service
public class EditCheckboxEventHandler implements IFormEventHandler {

    private final IFormService formService;

    private final ICollaborativeFormMessageService messageService;

    public EditCheckboxEventHandler(IFormService formService, ICollaborativeFormMessageService messageService) {
        this.formService = Objects.requireNonNull(formService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean canHandle(IFormInput formInput) {
        return formInput instanceof EditCheckboxInput;
    }

    @Override
    public EventHandlerResponse handle(Form form, IFormInput formInput) {
        if (formInput instanceof EditCheckboxInput) {
            EditCheckboxInput input = (EditCheckboxInput) formInput;

            // @formatter:off
            var optionalCheckbox = this.formService.findWidget(form, input.getCheckboxId())
                    .filter(Checkbox.class::isInstance)
                    .map(Checkbox.class::cast);

            var status = optionalCheckbox.map(Checkbox::getNewValueHandler)
                    .map(handler -> handler.apply(input.getNewValue()))
                    .orElse(Status.ERROR);
            // @formatter:on

            return new EventHandlerResponse(true, representation -> true, new EditCheckboxSuccessPayload(status.toString()));
        }

        String message = this.messageService.invalidInput(formInput.getClass().getSimpleName(), EditCheckboxInput.class.getSimpleName());
        return new EventHandlerResponse(false, representation -> false, new ErrorPayload(message));
    }
}
