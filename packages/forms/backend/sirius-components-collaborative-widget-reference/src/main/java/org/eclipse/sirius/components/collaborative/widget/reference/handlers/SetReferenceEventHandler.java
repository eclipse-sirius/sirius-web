/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.components.collaborative.forms.api.IFormInput;
import org.eclipse.sirius.components.collaborative.forms.api.IFormQueryService;
import org.eclipse.sirius.components.collaborative.widget.reference.api.IReferenceWidgetSetElementHandler;
import org.eclipse.sirius.components.collaborative.widget.reference.dto.SetReferenceValueInput;
import org.eclipse.sirius.components.collaborative.widget.reference.messages.IReferenceMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler invoked when the end-user sets a reference value.
 *
 * @author Jerome Gout
 */
@Service
public class SetReferenceEventHandler implements IFormEventHandler {
    private final IFormQueryService formQueryService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final List<IReferenceWidgetSetElementHandler> referenceWidgetSetElementHandlers;

    private final IReferenceMessageService messageService;

    private final Counter counter;

    private final Logger logger = LoggerFactory.getLogger(SetReferenceEventHandler.class);

    public SetReferenceEventHandler(IFormQueryService formQueryService, IReferenceMessageService messageService, IRepresentationDescriptionSearchService representationDescriptionSearchService,
            List<IReferenceWidgetSetElementHandler> referenceWidgetSetElementHandlers, MeterRegistry meterRegistry) {
        this.formQueryService = Objects.requireNonNull(formQueryService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.referenceWidgetSetElementHandlers = Objects.requireNonNull(referenceWidgetSetElementHandlers);
        this.messageService = Objects.requireNonNull(messageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IFormInput formInput) {
        return formInput instanceof SetReferenceValueInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, Form form, IFormInput formInput) {
        this.counter.increment();
        String message = this.messageService.invalidInput(formInput.getClass().getSimpleName(), SetReferenceValueInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(formInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, formInput.representationId(), formInput);

        if (formInput instanceof SetReferenceValueInput input) {
            var optionalWidget = this.formQueryService.findWidget(form, input.referenceWidgetId())
                    .filter(ReferenceWidget.class::isInstance)
                    .map(ReferenceWidget.class::cast);

            var optionalFormDescription = this.representationDescriptionSearchService.findById(editingContext, form.getDescriptionId());

            IStatus status;
            if (optionalWidget.isPresent() && optionalWidget.get().isReadOnly()) {
                status = new Failure(this.messageService.unableToEditReadOnlyWidget());
            } else if (optionalWidget.isPresent() && optionalFormDescription.isPresent() && optionalFormDescription.get() instanceof FormDescription formDescription) {
                status = this.set(editingContext, formDescription, optionalWidget.get(), input.newValueId());
            } else {
                status = new Failure(this.messageService.invalidIds());
            }

            if (status instanceof Success success) {
                this.logger.atInfo()
                        .setMessage("Set reference value action succeed")
                        .addKeyValue("editingContextId", editingContext.getId())
                        .addKeyValue("representationId", input.representationId())
                        .addKeyValue("widgetId", input.referenceWidgetId())
                        .log();

                payload = new SuccessPayload(formInput.id(), success.getMessages());
                changeDescription = new ChangeDescription(success.getChangeKind(), formInput.representationId(), formInput, success.getParameters());
            } else if (status instanceof Failure failure) {
                this.logger.atWarn()
                        .setMessage("Set reference value action failed")
                        .addKeyValue("editingContextId", editingContext.getId())
                        .addKeyValue("representationId", input.representationId())
                        .addKeyValue("widgetId", input.referenceWidgetId())
                        .log();

                payload = new ErrorPayload(formInput.id(), failure.getMessages());
            }
        }

        changeDescriptionSink.tryEmitNext(changeDescription);
        payloadSink.tryEmitValue(payload);
    }

    private IStatus set(IEditingContext editingContext, FormDescription formDescription, ReferenceWidget referenceWidget, String newValueId) {
        if (!referenceWidget.isSelectable()) {
            return new Success();
        }
        return this.referenceWidgetSetElementHandlers.stream()
                .filter(handler -> handler.canHandle(editingContext, formDescription, referenceWidget))
                .findFirst()
                .map(setHandler -> setHandler.set(editingContext, formDescription, referenceWidget, newValueId))
                .orElseGet(() -> new Failure(this.messageService.noHandlerFound()));
    }
}
