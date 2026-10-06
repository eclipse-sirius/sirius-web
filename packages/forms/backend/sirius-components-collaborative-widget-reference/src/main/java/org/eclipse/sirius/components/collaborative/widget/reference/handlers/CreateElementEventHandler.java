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
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.components.collaborative.forms.api.IFormInput;
import org.eclipse.sirius.components.collaborative.forms.api.IFormQueryService;
import org.eclipse.sirius.components.collaborative.widget.reference.api.IReferenceWidgetCreateElementHandler;
import org.eclipse.sirius.components.collaborative.widget.reference.dto.CreateElementInReferenceSuccessPayload;
import org.eclipse.sirius.components.collaborative.widget.reference.dto.CreateElementInput;
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
public class CreateElementEventHandler implements IFormEventHandler {

    private final IFormQueryService formQueryService;

    private final IReferenceMessageService messageService;

    private final IObjectSearchService objectSearchService;

    private final List<IReferenceWidgetCreateElementHandler> referenceWidgetCreateElementHandlers;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final Counter counter;

    public CreateElementEventHandler(IFormQueryService formQueryService, IReferenceMessageService messageService, IObjectSearchService objectSearchService,
            List<IReferenceWidgetCreateElementHandler> referenceWidgetCreateElementHandlers, IRepresentationDescriptionSearchService representationDescriptionSearchService,
            MeterRegistry meterRegistry) {
        this.formQueryService = Objects.requireNonNull(formQueryService);
        this.messageService = Objects.requireNonNull(messageService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.referenceWidgetCreateElementHandlers = Objects.requireNonNull(referenceWidgetCreateElementHandlers);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IFormInput formInput) {
        return formInput instanceof CreateElementInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, Form form, IFormInput formInput) {
        this.counter.increment();
        String message = this.messageService.invalidInput(formInput.getClass().getSimpleName(), CreateElementInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(formInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, formInput.representationId(), formInput);

        if (formInput instanceof CreateElementInput input) {

            var optionalWidget = this.formQueryService.findWidget(form, input.referenceWidgetId()).filter(ReferenceWidget.class::isInstance).map(ReferenceWidget.class::cast);
            var optionalFormDescription = this.representationDescriptionSearchService.findById(editingContext, form.getDescriptionId());

            if (optionalWidget.isPresent() && optionalWidget.get().isReadOnly()) {
                payload = new ErrorPayload(input.id(), this.messageService.unableToEditReadOnlyWidget());
            } else if (optionalWidget.isPresent() && optionalFormDescription.isPresent() && optionalFormDescription.get() instanceof FormDescription formDescription) {
                Optional<IReferenceWidgetCreateElementHandler> optionalHandler = this.referenceWidgetCreateElementHandlers.stream()
                        .filter(provider -> provider.canHandle(formDescription))
                        .findFirst();

                if (optionalHandler.isPresent()) {
                    var handler = optionalHandler.get();

                    var status = this.createElement(editingContext, handler, input, optionalWidget.get());

                    if (status instanceof Success success) {
                        payload = this.createSuccessPayload(formInput.id(), success);
                        changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, formInput.representationId(), formInput);
                    } else if (status instanceof Failure failure) {
                        payload = new ErrorPayload(input.id(), failure.getMessages());
                    }
                } else {
                    payload = new ErrorPayload(input.id(), this.messageService.noHandlerFound());
                }

            } else {
                payload = new ErrorPayload(input.id(), this.messageService.invalidIds());
            }
        }

        changeDescriptionSink.tryEmitNext(changeDescription);
        payloadSink.tryEmitValue(payload);
    }

    private IPayload createSuccessPayload(UUID id, Success success) {
        var object = success.getParameters().get("object");
        if (object != null) {
            return new CreateElementInReferenceSuccessPayload(id, object, success.getMessages());
        }
        return new SuccessPayload(id, success.getMessages());
    }

    private IStatus createElement(IEditingContext editingContext, IReferenceWidgetCreateElementHandler handler, CreateElementInput input, ReferenceWidget referenceWidget) {
        if (input.domainId() == null) {
            EObject parent = this.objectSearchService.getObject(editingContext, input.containerId())
                    .filter(EObject.class::isInstance)
                    .map(EObject.class::cast)
                    .orElse(null);
            return handler.createChild(editingContext, parent, input.creationDescriptionId(), referenceWidget);
        } else {
            return handler.createRootObject(editingContext, UUID.fromString(input.containerId()), input.domainId(), input.creationDescriptionId(), referenceWidget);
        }
    }

}
