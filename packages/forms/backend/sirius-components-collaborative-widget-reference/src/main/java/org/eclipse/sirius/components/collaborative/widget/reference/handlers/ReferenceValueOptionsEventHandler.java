/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.widget.reference.handlers;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.components.collaborative.forms.api.IFormInput;
import org.eclipse.sirius.components.collaborative.forms.api.IFormQueryService;
import org.eclipse.sirius.components.collaborative.forms.messages.ICollaborativeFormMessageService;
import org.eclipse.sirius.components.collaborative.widget.reference.dto.ClickReferenceValueInput;
import org.eclipse.sirius.components.collaborative.widget.reference.dto.ReferenceValueOptionsQueryInput;
import org.eclipse.sirius.components.collaborative.widget.reference.dto.ReferenceValueOptionsQueryPayload;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Handler invoked when the end-user trigger reference widget options retrieval.
 *
 * @author frouene
 */
@Service
public class ReferenceValueOptionsEventHandler implements IFormEventHandler {

    private final ICollaborativeFormMessageService messageService;

    private final Counter counter;

    private final IFormQueryService formQueryService;

    public ReferenceValueOptionsEventHandler(IFormQueryService formQueryService, ICollaborativeFormMessageService messageService, MeterRegistry meterRegistry) {
        this.formQueryService = Objects.requireNonNull(formQueryService);
        this.messageService = Objects.requireNonNull(messageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IFormInput formInput) {
        return formInput instanceof ReferenceValueOptionsQueryInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, Form form, IFormInput formInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(formInput.getClass().getSimpleName(), ClickReferenceValueInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(formInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, formInput.representationId(), formInput);

        if (formInput instanceof ReferenceValueOptionsQueryInput input) {
            var optionalReferenceWidget = this.formQueryService.findWidget(form, input.referenceWidgetId())
                    .filter(ReferenceWidget.class::isInstance)
                    .map(ReferenceWidget.class::cast);

            var options = optionalReferenceWidget
                    .map(ReferenceWidget::getReferenceOptionsProvider)
                    .map(Supplier::get)
                    .orElse(List.of());

            payload = new ReferenceValueOptionsQueryPayload(formInput.id(), options);
        }

        changeDescriptionSink.tryEmitNext(changeDescription);
        payloadSink.tryEmitValue(payload);
    }
}
