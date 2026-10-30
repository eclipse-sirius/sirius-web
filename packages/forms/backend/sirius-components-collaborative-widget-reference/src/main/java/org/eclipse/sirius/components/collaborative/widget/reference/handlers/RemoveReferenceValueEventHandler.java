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
import org.eclipse.sirius.components.collaborative.widget.reference.api.IReferenceWidgetRemoveHandler;
import org.eclipse.sirius.components.collaborative.widget.reference.dto.RemoveReferenceValueInput;
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
 * The handler of the remove reference value event.
 *
 * @author Jerome Gout
 */
@Service
public class RemoveReferenceValueEventHandler implements IFormEventHandler {

    private final IFormQueryService formQueryService;

    private final List<IReferenceWidgetRemoveHandler> referenceWidgetRemoveHandler;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IReferenceMessageService messageService;

    private final Counter counter;

    private final Logger logger = LoggerFactory.getLogger(RemoveReferenceValueEventHandler.class);

    public RemoveReferenceValueEventHandler(IFormQueryService formQueryService, List<IReferenceWidgetRemoveHandler> referenceWidgetRemoveHandler,
            IRepresentationDescriptionSearchService representationDescriptionSearchService, IReferenceMessageService messageService,
            MeterRegistry meterRegistry) {
        this.formQueryService = Objects.requireNonNull(formQueryService);
        this.referenceWidgetRemoveHandler = Objects.requireNonNull(referenceWidgetRemoveHandler);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.messageService = Objects.requireNonNull(messageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IFormInput formInput) {
        return formInput instanceof RemoveReferenceValueInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, Form form, IFormInput formInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(formInput.getClass().getSimpleName(), RemoveReferenceValueInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(formInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, formInput.representationId(), formInput);

        if (formInput instanceof RemoveReferenceValueInput input) {
            var optionalReferenceWidget = this.formQueryService.findWidget(form, input.referenceWidgetId())
                    .filter(ReferenceWidget.class::isInstance)
                    .map(ReferenceWidget.class::cast);

            var optionalFormDescription = this.representationDescriptionSearchService.findById(editingContext, form.getDescriptionId());

            IStatus status;
            if (optionalReferenceWidget.map(ReferenceWidget::isReadOnly).filter(Boolean::booleanValue).isPresent()) {
                status = new Failure(this.messageService.unableToEditReadOnlyWidget());
            } else if (optionalFormDescription.isPresent() && optionalFormDescription.get() instanceof FormDescription formDescription) {
                status = optionalReferenceWidget
                        .filter(referenceWidget -> referenceWidget.getReferenceValues().stream().anyMatch(item -> item.getId().equals(input.referenceValueId())))
                        .map(referenceWidget -> this.remove(editingContext, formDescription, referenceWidget, input.referenceValueId()))
                        .orElseGet(() -> new Failure(this.messageService.invalidIds()));
            } else {
                status = new Failure(this.messageService.invalidIds());
            }

            if (status instanceof Success success) {
                this.logger.atInfo()
                        .setMessage("Value removed from the reference widget")
                        .addKeyValue("editingContextId", editingContext.getId())
                        .addKeyValue("representationId", input.representationId())
                        .addKeyValue("widgetId", input.referenceWidgetId())
                        .log();

                changeDescription = new ChangeDescription(success.getChangeKind(), formInput.representationId(), formInput, success.getParameters());
                payload = new SuccessPayload(formInput.id(), success.getMessages());
            } else if (status instanceof Failure failure) {
                this.logger.atWarn()
                        .setMessage("Removal of the value in the reference widget failed")
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

    private IStatus remove(IEditingContext editingContext, FormDescription formDescription, ReferenceWidget referenceWidget, String referenceValueId) {
        return this.referenceWidgetRemoveHandler.stream()
                .filter(handler -> handler.canHandle(formDescription, referenceWidget))
                .findFirst()
                .map(handler -> handler.remove(editingContext, formDescription, referenceWidget, referenceValueId))
                .orElseGet(() -> new Failure(this.messageService.noHandlerFound()));
    }

}
