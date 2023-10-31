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
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.components.collaborative.forms.api.IFormInput;
import org.eclipse.sirius.components.collaborative.forms.api.IFormQueryService;
import org.eclipse.sirius.components.collaborative.widget.reference.ReferenceWidgetDefaultCreateElementHandler;
import org.eclipse.sirius.components.collaborative.widget.reference.dto.CreateElementInReferenceSuccessPayload;
import org.eclipse.sirius.components.collaborative.widget.reference.dto.CreateElementInput;
import org.eclipse.sirius.components.collaborative.widget.reference.messages.IReferenceMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.widget.reference.IReferenceWidgetCreateElementHandler;
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

    private final IObjectService objectService;

    private final List<IReferenceWidgetCreateElementHandler> referenceWidgetCreateElementHandlers;

    private final ReferenceWidgetDefaultCreateElementHandler defaultReferenceWidgetCreateElementHandler;

    private final Counter counter;

    private final IFeedbackMessageService feedbackMessageService;

    public CreateElementEventHandler(IFormQueryService formQueryService, IReferenceMessageService messageService, IObjectService objectService, List<IReferenceWidgetCreateElementHandler> referenceWidgetCreateElementHandlers, IEditService editService, MeterRegistry meterRegistry, IFeedbackMessageService feedbackMessageService) {
        this.formQueryService = Objects.requireNonNull(formQueryService);
        this.messageService = Objects.requireNonNull(messageService);
        this.objectService = Objects.requireNonNull(objectService);
        this.referenceWidgetCreateElementHandlers = Objects.requireNonNull(referenceWidgetCreateElementHandlers);
        this.defaultReferenceWidgetCreateElementHandler = new ReferenceWidgetDefaultCreateElementHandler(Objects.requireNonNull(editService));
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER).tag(Monitoring.NAME, this.getClass().getSimpleName()).register(meterRegistry);
    }

    @Override
    public boolean canHandle(IFormInput formInput) {
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

            if (optionalWidget.isPresent() && optionalWidget.get().isReadOnly()) {
                payload = new ErrorPayload(input.id(), this.messageService.unableToEditReadOnlyWidget());
            } else {
                IReferenceWidgetCreateElementHandler handler = this.referenceWidgetCreateElementHandlers.stream()
                        .filter(provider -> provider.canHandle(input.descriptionId()))
                        .findFirst()
                        .orElse(this.defaultReferenceWidgetCreateElementHandler);

                var optionalObject = this.createElement(editingContext, handler, input);

                if (optionalObject.isPresent()) {
                    payload = new CreateElementInReferenceSuccessPayload(formInput.id(), optionalObject.get(), this.feedbackMessageService.getFeedbackMessages());
                    changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, formInput.representationId(), formInput);
                } else {
                    payload = new ErrorPayload(input.id(), this.feedbackMessageService.getFeedbackMessages());
                }
            }
        }

        changeDescriptionSink.tryEmitNext(changeDescription);
        payloadSink.tryEmitValue(payload);
    }

    private Optional<Object> createElement(IEditingContext editingContext, IReferenceWidgetCreateElementHandler handler, CreateElementInput input) {
        if (input.domainId() == null) {
            EObject parent = this.objectService.getObject(editingContext, input.containerId())
                    .filter(EObject.class::isInstance)
                    .map(EObject.class::cast)
                    .orElse(null);
            return handler.createChild(editingContext, parent, input.creationDescriptionId(), input.descriptionId());
        } else {
            return handler.createRootObject(editingContext, UUID.fromString(input.containerId()), input.domainId(), input.creationDescriptionId(), input.descriptionId());
        }
    }

}
