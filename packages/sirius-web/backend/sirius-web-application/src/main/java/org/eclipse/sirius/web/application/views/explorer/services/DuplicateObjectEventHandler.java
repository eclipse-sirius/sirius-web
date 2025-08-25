/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.views.explorer.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.web.application.views.explorer.dto.DuplicateObjectInput;
import org.eclipse.sirius.web.application.views.explorer.dto.DuplicateObjectSuccessPayload;
import org.eclipse.sirius.web.application.views.explorer.services.api.DuplicationSettings;
import org.eclipse.sirius.web.application.views.explorer.services.api.IObjectDuplicator;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to duplicate an object.
 *
 * @author lfasani
 */
@Service
public class DuplicateObjectEventHandler implements IEditingContextEventHandler {

    private static final String NEW_OBJECT = "newObject";

    private final IObjectSearchService objectSearchService;

    private final IObjectDuplicator objectDuplicator;

    private final IMessageService messageService;

    private final Counter counter;

    public DuplicateObjectEventHandler(IObjectSearchService objectSearchService, IObjectDuplicator objectDuplicator, IMessageService messageService, MeterRegistry meterRegistry) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.objectDuplicator = Objects.requireNonNull(objectDuplicator);
        this.messageService = Objects.requireNonNull(messageService);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER).tag(Monitoring.NAME, this.getClass().getSimpleName()).register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof DuplicateObjectInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        List<Message> messages = List.of(new Message(this.messageService.invalidInput(input.getClass().getSimpleName(), DuplicateObjectInput.class.getSimpleName()), MessageLevel.ERROR));
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);
        IPayload payload = new ErrorPayload(input.id(), messages);

        if (input instanceof DuplicateObjectInput duplicateObjectInput) {
            var settings = new DuplicationSettings(duplicateObjectInput.duplicateContent(), duplicateObjectInput.copyOutgoingReferences(), duplicateObjectInput.updateIncomingReferences());
            IStatus duplicationResult = this.duplicateObject(editingContext, duplicateObjectInput.objectId(), duplicateObjectInput.containerId(), duplicateObjectInput.containmentFeatureName(),
                    settings);

            if (duplicationResult instanceof Success success) {
                payload = new DuplicateObjectSuccessPayload(input.id(), success.getParameters().get(NEW_OBJECT), success.getMessages());
                changeDescription = new ChangeDescription(success.getChangeKind(), editingContext.getId(), input);
            } else if (duplicationResult instanceof Failure failure) {
                payload = new ErrorPayload(input.id(), failure.getMessages());
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private IStatus duplicateObject(IEditingContext editingContext, String objectToDuplicateId, String containerId, String containmentFeature, DuplicationSettings settings) {
        IStatus result;

        Optional<EObject> optionalObjectToDuplicate = this.objectSearchService.getObject(editingContext, objectToDuplicateId)
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast);
        Optional<EObject> optionalContainerEObject = this.objectSearchService.getObject(editingContext, containerId)
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast);

        if (optionalObjectToDuplicate.isEmpty()) {
            result = new Failure(this.messageService.objectDoesNotExist(objectToDuplicateId));
        } else if (optionalContainerEObject.isEmpty()) {
            result = new Failure(this.messageService.objectDoesNotExist(containerId));
        } else {
            result = this.objectDuplicator.duplicateObject(editingContext, optionalObjectToDuplicate.get(), optionalContainerEObject.get(), containmentFeature, settings);
        }

        return result;
    }
}
