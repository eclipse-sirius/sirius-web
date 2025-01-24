/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST and others.
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

package org.eclipse.sirius.web.view.fork.services;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.view.fork.dto.CreateForkedStudioInput;
import org.eclipse.sirius.web.view.fork.services.api.IForkedStudioCreationService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

/**
 * Handler of createForkedStudio mutation.
 *
 * @author mcharfadi
 */
@Service
public class CreateForkedStudioHandler implements IEditingContextEventHandler {

    private final IForkedStudioCreationService forkedStudioCreationService;

    public CreateForkedStudioHandler(IForkedStudioCreationService forkedStudioCreationService) {
        this.forkedStudioCreationService = Objects.requireNonNull(forkedStudioCreationService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof CreateForkedStudioInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        var payload = this.forkedStudioCreationService.create(input, editingContext);

        payloadSink.tryEmitValue(payload);

        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

}
