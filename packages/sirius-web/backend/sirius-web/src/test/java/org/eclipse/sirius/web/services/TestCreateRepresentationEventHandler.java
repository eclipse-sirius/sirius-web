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
package org.eclipse.sirius.web.services;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationSuccessPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks;

/**
 * Used to create test representations during integration tests.
 *
 * @author sbegaudeau
 */
@Service
public class TestCreateRepresentationEventHandler implements IEditingContextEventHandler {

    private final IRepresentationPersistenceService representationPersistenceService;

    public TestCreateRepresentationEventHandler(IRepresentationPersistenceService representationPersistenceService) {
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof CreateRepresentationInput && ((CreateRepresentationInput) input).representationDescriptionId().equals(new TestRepresentationDescription().getId());
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        var test = new TestRepresentation();

        this.representationPersistenceService.save(input, editingContext, test);

        payloadSink.tryEmitValue(new CreateRepresentationSuccessPayload(input.id(), test));
        changeDescriptionSink.tryEmitNext(new ChangeDescription(ChangeKind.REPRESENTATION_CREATION, editingContext.getId(), input));
    }
}
