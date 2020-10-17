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
import java.util.Optional;

import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.web.collaborative.forms.api.IFormInput;
import org.eclipse.sirius.web.collaborative.forms.api.IFormService;
import org.eclipse.sirius.web.collaborative.forms.api.dto.EditSelectInput;
import org.eclipse.sirius.web.collaborative.forms.api.dto.EditSelectSuccessPayload;
import org.eclipse.sirius.web.forms.Form;
import org.eclipse.sirius.web.forms.Select;
import org.eclipse.sirius.web.representations.Status;
import org.eclipse.sirius.web.services.api.dto.ErrorPayload;
import org.eclipse.sirius.web.spring.collaborative.forms.messages.ICollaborativeFormMessageService;
import org.springframework.stereotype.Service;

/**
 * The handler of the edit select event.
 *
 * @author lfasani
 */
@Service
public class EditSelectEventHandler implements IFormEventHandler {

    private final IFormService formService;

    private final ICollaborativeFormMessageService messageService;

    public EditSelectEventHandler(IFormService formService, ICollaborativeFormMessageService messageService) {
        this.formService = Objects.requireNonNull(formService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean canHandle(IFormInput formInput) {
        return formInput instanceof EditSelectInput;
    }

    @Override
    public EventHandlerResponse handle(Form form, IFormInput formInput) {
        if (formInput instanceof EditSelectInput) {
            EditSelectInput input = (EditSelectInput) formInput;

            // @formatter:off
            Optional<Select> optionalSelect = this.formService.findWidget(form, input.getSelectId())
                    .filter(Select.class::isInstance)
                    .map(Select.class::cast);

            Status status = optionalSelect.map(Select::getNewValueHandler)
                    .map(handler -> handler.apply(input.getNewValue()))
                    .orElse(Status.ERROR);
            // @formatter:on
            return new EventHandlerResponse(true, representation -> true, new EditSelectSuccessPayload(status.toString()));
        }
        String message = this.messageService.invalidInput(formInput.getClass().getSimpleName(), EditSelectInput.class.getSimpleName());
        return new EventHandlerResponse(false, representation -> false, new ErrorPayload(message));
    }
}
