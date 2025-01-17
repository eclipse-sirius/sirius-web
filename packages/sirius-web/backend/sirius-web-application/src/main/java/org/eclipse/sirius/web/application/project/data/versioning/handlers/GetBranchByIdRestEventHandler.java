/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.project.data.versioning.handlers;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.application.project.data.versioning.dto.GetBranchByIdRestInput;
import org.eclipse.sirius.web.application.project.data.versioning.dto.GetBranchByIdRestSuccessPayload;
import org.eclipse.sirius.web.application.project.data.versioning.services.api.IProjectDataVersioningRestService;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to execute the "getBranchById" REST API.
 *
 * @author arichard
 */
@Service
public class GetBranchByIdRestEventHandler implements IEditingContextEventHandler {

    private final IProjectDataVersioningRestService projectDataVersioningRestService;

    private final IMessageService messageService;

    private final Counter counter;

    public GetBranchByIdRestEventHandler(IProjectDataVersioningRestService projectDataVersioningRestService, IMessageService messageService, MeterRegistry meterRegistry) {
        this.projectDataVersioningRestService = Objects.requireNonNull(projectDataVersioningRestService);
        this.messageService = Objects.requireNonNull(messageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof GetBranchByIdRestInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), GetBranchByIdRestInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.id(), message);
        if (input instanceof GetBranchByIdRestInput restInput) {
            var branch = this.projectDataVersioningRestService.getBranchById(editingContext, restInput.branchId());
            if (branch != null) {
                payload = new GetBranchByIdRestSuccessPayload(UUID.randomUUID(), branch);
            } else {
                payload = new ErrorPayload(input.id(), MessageFormat.format("The branch {0} does not exist for the current project.", restInput.branchId()));
            }
        }
        payloadSink.tryEmitValue(payload);

        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
