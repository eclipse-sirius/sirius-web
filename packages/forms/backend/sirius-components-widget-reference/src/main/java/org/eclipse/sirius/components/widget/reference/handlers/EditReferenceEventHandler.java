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
package org.eclipse.sirius.components.widget.reference.handlers;

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.components.collaborative.forms.api.IFormInput;
import org.eclipse.sirius.components.collaborative.forms.api.IFormQueryService;
import org.eclipse.sirius.components.collaborative.forms.messages.ICollaborativeFormMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.eclipse.sirius.components.widget.reference.dto.EditReferenceInput;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler invoked when the end-user requests changes to a reference.
 *
 * @author pcdavid
 */
@Service
public class EditReferenceEventHandler implements IFormEventHandler {
    private final IFormQueryService formQueryService;

    private final ICollaborativeFormMessageService messageService;

    private final IObjectService objectService;

    private final Counter counter;

    public EditReferenceEventHandler(IFormQueryService formQueryService, ICollaborativeFormMessageService messageService, IObjectService objectService, MeterRegistry meterRegistry) {
        this.formQueryService = Objects.requireNonNull(formQueryService);
        this.messageService = Objects.requireNonNull(messageService);
        this.objectService = Objects.requireNonNull(objectService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER).tag(Monitoring.NAME, this.getClass().getSimpleName()).register(meterRegistry);
    }

    @Override
    public boolean canHandle(IFormInput formInput) {
        return formInput instanceof EditReferenceInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, Form form, IFormInput formInput) {
        this.counter.increment();
        String message = this.messageService.invalidInput(formInput.getClass().getSimpleName(), EditReferenceInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(formInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, formInput.representationId(), formInput);

        if (formInput instanceof EditReferenceInput input) {

            var optionalWidget = this.formQueryService.findWidget(form, input.referenceWidgetId()).filter(ReferenceWidget.class::isInstance).map(ReferenceWidget.class::cast);

            IStatus status;
            if (optionalWidget.isPresent() && optionalWidget.get().isReadOnly()) {
                status = new Failure("Read-only widget can not be edited");
            } else {
                status = optionalWidget.map(ReferenceWidget::getSetting)
                        .map(setting -> this.editSetting(editingContext, setting, input.newValueIds()))
                        .orElse(new Failure(""));
            }

            if (status instanceof Success success) {
                payload = new SuccessPayload(formInput.id(), success.getMessages());
                changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, formInput.representationId(), formInput);
            } else if (status instanceof Failure failure) {
                payload = new ErrorPayload(formInput.id(), failure.getMessages());
            }
        }

        changeDescriptionSink.tryEmitNext(changeDescription);
        payloadSink.tryEmitValue(payload);
    }

    private IStatus editSetting(IEditingContext editingContext, Setting setting, List<String> newValueIds) {
        IStatus result = new Success();

        List<Object> values = this.resolve(editingContext, newValueIds);
        if (values.size() != newValueIds.size()) {
            result = new Failure("Invalid id(s).");
        } else if (!values.stream().allMatch(value -> this.isCompatible(setting, value))) {
            result = new Failure("Incompatible values.");
        } else if (setting.getEStructuralFeature().isMany()) {
            setting.set(values);
        } else if (newValueIds.isEmpty()) {
            setting.unset();
        } else if (values.size() == 1) {
            setting.set(values.get(0));
        } else {
            result = new Failure("Single-valued reference can only accept a single value");
        }
        return result;
    }

    private List<Object> resolve(IEditingContext editingContext, List<String> ids) {
        return ids.stream().flatMap(id -> this.objectService.getObject(editingContext, id).stream()).toList();
    }

    private boolean isCompatible(Setting setting, Object value) {
        return setting.getEStructuralFeature().getEType().isInstance(value);
    }

}
