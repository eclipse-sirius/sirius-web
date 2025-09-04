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

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.web.application.views.explorer.dto.EditingContextContainmentFeatureNamesInput;
import org.eclipse.sirius.web.application.views.explorer.dto.EditingContextContainmentFeatureNamesPayload;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to get the possible containment feature names from a container and a candidate contained object.
 *
 * @author lfasani
 */
@Service
public class ContainmentFeatureNamesEventHandler implements IEditingContextEventHandler {

    private final ICollaborativeMessageService messageService;

    private final IObjectService objectService;

    private final Counter counter;

    public ContainmentFeatureNamesEventHandler(ICollaborativeMessageService messageService, IObjectService objectService,
            MeterRegistry meterRegistry) {
        this.messageService = Objects.requireNonNull(messageService);
        this.objectService = Objects.requireNonNull(objectService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER).tag(Monitoring.NAME, this.getClass().getSimpleName()).register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof EditingContextContainmentFeatureNamesInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        List<Message> messages = List.of(new Message(this.messageService.invalidInput(input.getClass()
                .getSimpleName(), EditingContextContainmentFeatureNamesInput.class.getSimpleName()), MessageLevel.ERROR));
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);
        IPayload payload = new ErrorPayload(input.id(), messages);

        if (input instanceof EditingContextContainmentFeatureNamesInput editingContextContainmentFeatureNamesInput) {
            Optional<EObject> containerOpt = this.objectService.getObject(editingContext, editingContextContainmentFeatureNamesInput.containerId()).filter(EObject.class::isInstance)
                    .map(EObject.class::cast);
            Optional<EObject> containedObjectOpt = this.objectService.getObject(editingContext, editingContextContainmentFeatureNamesInput.containedObjectId()).filter(EObject.class::isInstance)
                    .map(EObject.class::cast);

            if (containedObjectOpt.isPresent()) {
                List<String> containmentFeatureNames = new java.util.ArrayList<>();

                containerOpt.ifPresent(eObject -> containmentFeatureNames.addAll(this.getContainmentFeatureNames(eObject, containedObjectOpt.get())));

                payload = new EditingContextContainmentFeatureNamesPayload(input.id(), containmentFeatureNames);
                changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);
            } else {
                payload = new ErrorPayload(input.id(), List.of(new Message("Retrieving the candidate containment references failed", MessageLevel.ERROR)));
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private List<String> getContainmentFeatureNames(EObject container, EObject containedObject) {
        return container.eClass().getEAllContainments().stream()
                .filter(eReference -> eReference.getEReferenceType().isInstance(containedObject))
                .map(ENamedElement::getName)
                .toList();
    }
}
