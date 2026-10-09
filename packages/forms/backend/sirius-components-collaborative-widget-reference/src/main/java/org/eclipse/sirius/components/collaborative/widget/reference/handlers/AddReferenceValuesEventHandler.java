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
import org.eclipse.sirius.components.collaborative.widget.reference.api.IReferenceWidgetAddElementHandler;
import org.eclipse.sirius.components.collaborative.widget.reference.dto.AddReferenceValuesInput;
import org.eclipse.sirius.components.collaborative.widget.reference.messages.IReferenceMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
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
 * Handler invoked when the end-user adds reference values.
 *
 * @author Jerome Gout
 */
@Service
public class AddReferenceValuesEventHandler implements IFormEventHandler {
    private final IFormQueryService formQueryService;

    private final IObjectSearchService objectSearchService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final List<IReferenceWidgetAddElementHandler> referenceWidgetAddElementHandlers;

    private final IReferenceMessageService messageService;

    private final Counter counter;

    private final Logger logger = LoggerFactory.getLogger(AddReferenceValuesEventHandler.class);

    public AddReferenceValuesEventHandler(IFormQueryService formQueryService, IReferenceMessageService messageService, IObjectSearchService objectSearchService,
            IRepresentationDescriptionSearchService representationDescriptionSearchService,
            List<IReferenceWidgetAddElementHandler> referenceWidgetAddElementHandlers, MeterRegistry meterRegistry) {
        this.formQueryService = Objects.requireNonNull(formQueryService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.referenceWidgetAddElementHandlers = Objects.requireNonNull(referenceWidgetAddElementHandlers);
        this.messageService = Objects.requireNonNull(messageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IFormInput formInput) {
        return formInput instanceof AddReferenceValuesInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, Form form, IFormInput formInput) {
        this.counter.increment();
        String message = this.messageService.invalidInput(formInput.getClass().getSimpleName(), AddReferenceValuesInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(formInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, formInput.representationId(), formInput);

        if (formInput instanceof AddReferenceValuesInput input) {
            var optionalWidget = this.formQueryService.findWidget(form, input.referenceWidgetId())
                    .filter(ReferenceWidget.class::isInstance)
                    .map(ReferenceWidget.class::cast);

            var optionalFormDescription = this.representationDescriptionSearchService.findById(editingContext, form.getDescriptionId());

            IStatus status;
            if (optionalWidget.isPresent() && optionalWidget.get().isReadOnly()) {
                status = new Failure(this.messageService.unableToEditReadOnlyWidget());
            } else if (optionalWidget.isPresent() && optionalFormDescription.isPresent() && optionalFormDescription.get() instanceof FormDescription formDescription) {
                status = this.add(editingContext, formDescription, optionalWidget.get(), input.newValueIds());
            } else {
                status = new Failure(this.messageService.invalidIds());
            }

            if (status instanceof Success success) {
                this.logger.atInfo()
                        .setMessage("Add reference value action succeed")
                        .addKeyValue("editingContextId", editingContext.getId())
                        .addKeyValue("representationId", input.representationId())
                        .addKeyValue("widgetId", input.referenceWidgetId())
                        .log();

                payload = new SuccessPayload(formInput.id(), success.getMessages());
                changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, formInput.representationId(), formInput, success.getParameters());
            } else if (status instanceof Failure failure) {
                this.logger.atWarn()
                        .setMessage("Add reference value action failed")
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

    private IStatus add(IEditingContext editingContext, FormDescription formDescription, ReferenceWidget referenceWidget, List<String> newValueIds) {
        return this.referenceWidgetAddElementHandlers.stream()
                .filter(handler -> handler.canHandle(formDescription))
                .findFirst()
                .map(clearHandler -> clearHandler.add(editingContext, formDescription, referenceWidget, newValueIds))
                .orElseGet(() -> new Failure(this.messageService.noHandlerFound()));
    }
}
