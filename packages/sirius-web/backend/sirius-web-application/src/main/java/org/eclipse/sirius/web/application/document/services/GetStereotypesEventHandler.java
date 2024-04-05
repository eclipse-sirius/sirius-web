/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.application.document.services;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.application.document.services.api.IStereotypeSearchService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks;

/**
 * Used to compute the list of stereotypes.
 *
 * @author sbegaudeau
 */
@Service
public class GetStereotypesEventHandler implements IEditingContextEventHandler {

    private final IStereotypeSearchService stereotypeSearchService;

    public GetStereotypesEventHandler(IStereotypeSearchService stereotypeSearchService) {
        this.stereotypeSearchService = Objects.requireNonNull(stereotypeSearchService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof GetStereotypesInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        var stereotypePage = this.stereotypeSearchService.findAll(editingContext, PageRequest.of(0, 100));
        payloadSink.tryEmitValue(new GetStereotypesPayload(input.id(), stereotypePage));
    }
}
