/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
package org.eclipse.sirius.components.collaborative.formdescriptioneditors.handlers;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorContext;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorEventHandler;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorInput;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.DeleteWidgetInput;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.messages.ICollaborativeFormDescriptionEditorMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handle the delete widget event on Form Description Editor.
 *
 * @author arichard
 */
@Service
public class DeleteWidgetEventHandler implements IFormDescriptionEditorEventHandler {

    private final IObjectSearchService objectSearchService;

    private final IEditService editService;

    private final ICollaborativeFormDescriptionEditorMessageService messageService;

    private final Counter counter;

    public DeleteWidgetEventHandler(IObjectSearchService objectSearchService, IEditService editService, ICollaborativeFormDescriptionEditorMessageService messageService, MeterRegistry meterRegistry) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.editService = Objects.requireNonNull(editService);
        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IFormDescriptionEditorInput formDescriptionEditorInput) {
        return formDescriptionEditorInput instanceof DeleteWidgetInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IFormDescriptionEditorContext formDescriptionEditorContext,
            IFormDescriptionEditorInput formDescriptionEditorInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(formDescriptionEditorInput.getClass().getSimpleName(), DeleteWidgetInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(formDescriptionEditorInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, formDescriptionEditorInput.representationId(), formDescriptionEditorInput);

        if (formDescriptionEditorInput instanceof DeleteWidgetInput) {
            String widgetId = ((DeleteWidgetInput) formDescriptionEditorInput).widgetId();
            boolean deleteWidget = this.deleteWidget(editingContext, widgetId);
            if (deleteWidget) {
                payload = new SuccessPayload(formDescriptionEditorInput.id());
                changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, formDescriptionEditorInput.representationId(), formDescriptionEditorInput);
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    protected boolean deleteWidget(IEditingContext editingContext, String widgetId) {
        var optionalSelf = this.objectSearchService.getObject(editingContext, widgetId);
        if (optionalSelf.isPresent()) {
            this.editService.delete(optionalSelf.get());
            return true;
        }
        return false;
    }
}
