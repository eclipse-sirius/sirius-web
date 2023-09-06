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
import org.eclipse.sirius.components.collaborative.widget.reference.dto.CreateElementInReferenceSuccessPayload;
import org.eclipse.sirius.components.collaborative.widget.reference.dto.CreateElementInput;
import org.eclipse.sirius.components.collaborative.widget.reference.messages.IReferenceMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.eclipse.sirius.components.widget.reference.dto.CreateElementInReferenceHandlerParameters;
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

    private final Counter counter;

    public CreateElementEventHandler(IFormQueryService formQueryService, IReferenceMessageService messageService, IObjectService objectService, MeterRegistry meterRegistry) {
        this.formQueryService = Objects.requireNonNull(formQueryService);
        this.messageService = Objects.requireNonNull(messageService);
        this.objectService = Objects.requireNonNull(objectService);

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
                var handlerInput = this.getHandlerInput(editingContext, input);
                Optional<Object> optionalObject = optionalWidget.map(ReferenceWidget::getCreateElementHandler).map(handler -> handler.apply(handlerInput));

                if (optionalObject.isPresent()) {
                    payload = new CreateElementInReferenceSuccessPayload(formInput.id(), optionalObject.get());
                    changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, formInput.representationId(), formInput);
                }
            }
        }

        changeDescriptionSink.tryEmitNext(changeDescription);
        payloadSink.tryEmitValue(payload);
    }

    private CreateElementInReferenceHandlerParameters getHandlerInput(IEditingContext editingContext, CreateElementInput input) {
        if (input.domainId() != null) {
            return new CreateElementInReferenceHandlerParameters(UUID.fromString(input.containerId()), input.domainId(), null, input.creationDescriptionId());
        } else {
            // this is child creation => containerId = parent object id
            EObject parent = this.objectService.getObject(editingContext, input.containerId())
                    .filter(EObject.class::isInstance)
                    .map(EObject.class::cast)
                    .orElse(null);
            return new CreateElementInReferenceHandlerParameters(null, null, parent, input.creationDescriptionId());
        }
    }
}
