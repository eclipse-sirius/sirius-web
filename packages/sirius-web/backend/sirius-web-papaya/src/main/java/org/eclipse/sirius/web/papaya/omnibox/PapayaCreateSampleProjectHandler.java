/*******************************************************************************
 * Copyright (c) 2025, 2025 Obeo.
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
package org.eclipse.sirius.web.papaya.omnibox;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.omnibox.dto.ExecuteWorkbenchOmniboxCommandSuccessPayload;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.eclipse.sirius.web.papaya.factories.SiriusWebProjectCreationLifecycleFactory;
import org.eclipse.sirius.web.papaya.factories.services.EObjectIndexer;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handles the creation of a sample Papaya project.
 *
 * @author gdaniel
 */
@Service
public class PapayaCreateSampleProjectHandler implements IEditingContextEventHandler {

    private final IMessageService messageService;

    private final Counter counter;

    public PapayaCreateSampleProjectHandler(IMessageService messageService, MeterRegistry meterRegistry) {
        this.messageService = Objects.requireNonNull(messageService);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof PapayaCreateSampleProjectInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        IPayload payload = new ErrorPayload(input.id(), this.messageService.unexpectedError());
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            var siriusWebProjectCreationLifecycleFactory = new SiriusWebProjectCreationLifecycleFactory();
            siriusWebProjectCreationLifecycleFactory.create(emfEditingContext);

            var eObjectIndexer = new EObjectIndexer();
            eObjectIndexer.index(emfEditingContext.getDomain().getResourceSet());

            siriusWebProjectCreationLifecycleFactory.link(eObjectIndexer);

            changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), input);
            payload = new ExecuteWorkbenchOmniboxCommandSuccessPayload(input.id(), null, List.of());
        }
        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

}
