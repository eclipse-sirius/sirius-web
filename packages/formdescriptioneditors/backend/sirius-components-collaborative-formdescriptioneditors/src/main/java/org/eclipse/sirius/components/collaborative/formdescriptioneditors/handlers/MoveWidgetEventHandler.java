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
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.MoveWidgetInput;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.messages.ICollaborativeFormDescriptionEditorMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.view.form.FlexboxContainerDescription;
import org.eclipse.sirius.components.view.form.FormElementDescription;
import org.eclipse.sirius.components.view.form.FormElementFor;
import org.eclipse.sirius.components.view.form.FormElementIf;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handle the move widget event on Form Description Editor.
 *
 * @author arichard
 */
@Service
public class MoveWidgetEventHandler implements IFormDescriptionEditorEventHandler {

    private final Logger logger = LoggerFactory.getLogger(MoveWidgetEventHandler.class);

    private final IObjectSearchService objectSearchService;

    private final ICollaborativeFormDescriptionEditorMessageService messageService;

    private final Counter counter;

    public MoveWidgetEventHandler(IObjectSearchService objectSearchService, ICollaborativeFormDescriptionEditorMessageService messageService, MeterRegistry meterRegistry) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IFormDescriptionEditorInput formDescriptionEditorInput) {
        return formDescriptionEditorInput instanceof MoveWidgetInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IFormDescriptionEditorContext formDescriptionEditorContext,
            IFormDescriptionEditorInput formDescriptionEditorInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(formDescriptionEditorInput.getClass().getSimpleName(), MoveWidgetInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(formDescriptionEditorInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, formDescriptionEditorInput.representationId(), formDescriptionEditorInput);

        if (formDescriptionEditorInput instanceof MoveWidgetInput) {
            String containerId = ((MoveWidgetInput) formDescriptionEditorInput).containerId();
            String widgetId = ((MoveWidgetInput) formDescriptionEditorInput).widgetId();
            int index = ((MoveWidgetInput) formDescriptionEditorInput).index();
            boolean moveWidget = this.moveWidget(editingContext, containerId, widgetId, index);
            if (moveWidget) {
                payload = new SuccessPayload(formDescriptionEditorInput.id());
                changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, formDescriptionEditorInput.representationId(), formDescriptionEditorInput);
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private boolean moveWidget(IEditingContext editingContext, String containerId, String widgetId, int index) {
        boolean success = false;
        var optionalSelf = this.objectSearchService.getObject(editingContext, containerId);
        if (optionalSelf.isPresent()) {
            Object container = optionalSelf.get();
            var objectToMove = this.objectSearchService.getObject(editingContext, widgetId);
            if (objectToMove.filter(FormElementDescription.class::isInstance).isPresent()) {
                FormElementDescription elementToMove = (FormElementDescription) objectToMove.get();
                try {
                    if (container instanceof GroupDescription groupDescription) {
                        if (groupDescription.equals(elementToMove.eContainer())) {
                            groupDescription.getChildren().move(index, elementToMove);
                        } else {
                            groupDescription.getChildren().add(index, elementToMove);
                        }
                        success = true;
                    } else if (container instanceof FlexboxContainerDescription flexboxContainerDescription) {
                        if (flexboxContainerDescription.equals(elementToMove.eContainer())) {
                            flexboxContainerDescription.getChildren().move(index, elementToMove);
                        } else {
                            flexboxContainerDescription.getChildren().add(index, elementToMove);
                        }
                        success = true;
                    } else if (container instanceof FormElementFor formElementFor) {
                        if (formElementFor.equals(elementToMove.eContainer())) {
                            formElementFor.getChildren().move(index, elementToMove);
                        } else {
                            formElementFor.getChildren().add(index, elementToMove);
                        }
                        success = true;
                    } else if (container instanceof FormElementIf formElementIf) {
                        if (formElementIf.equals(elementToMove.eContainer())) {
                            formElementIf.getChildren().move(index, elementToMove);
                        } else {
                            formElementIf.getChildren().add(index, elementToMove);
                        }
                        success = true;
                    }
                } catch (IndexOutOfBoundsException exception) {
                    this.logger.warn(exception.getMessage(), exception);
                }
            }
        }
        return success;
    }
}
