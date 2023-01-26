/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.view.FlexboxContainerDescription;
import org.eclipse.sirius.components.view.GroupDescription;
import org.eclipse.sirius.components.view.WidgetDescription;
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

    private final IObjectService objectService;

    private final ICollaborativeFormDescriptionEditorMessageService messageService;

    private final Counter counter;

    public MoveWidgetEventHandler(IObjectService objectService, ICollaborativeFormDescriptionEditorMessageService messageService, MeterRegistry meterRegistry) {
        this.objectService = Objects.requireNonNull(objectService);
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
            boolean moveWidget = this.moveWidget(editingContext, formDescriptionEditorContext, containerId, widgetId, index);
            if (moveWidget) {
                payload = new SuccessPayload(formDescriptionEditorInput.id());
                changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, formDescriptionEditorInput.representationId(), formDescriptionEditorInput);
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private boolean moveWidget(IEditingContext editingContext, IFormDescriptionEditorContext formDescriptionEditorContext, String containerId, String widgetId, int index) {
        boolean success = false;
        var optionalSelf = this.objectService.getObject(editingContext, containerId);
        if (optionalSelf.isPresent()) {
            Object container = optionalSelf.get();
            var objectToMove = this.objectService.getObject(editingContext, widgetId);
            if (objectToMove.filter(WidgetDescription.class::isInstance).isPresent()) {
                WidgetDescription widgetToMove = (WidgetDescription) objectToMove.get();
                try {
                    if (container instanceof GroupDescription) {
                        if (container.equals(widgetToMove.eContainer())) {
                            ((GroupDescription) container).getWidgets().move(index, widgetToMove);
                        } else {
                            ((GroupDescription) container).getWidgets().add(index, widgetToMove);
                        }
                        success = true;
                    } else if (container instanceof FlexboxContainerDescription) {
                        if (container.equals(widgetToMove.eContainer())) {
                            ((FlexboxContainerDescription) container).getChildren().move(index, widgetToMove);
                        } else {
                            ((FlexboxContainerDescription) container).getChildren().add(index, widgetToMove);
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
