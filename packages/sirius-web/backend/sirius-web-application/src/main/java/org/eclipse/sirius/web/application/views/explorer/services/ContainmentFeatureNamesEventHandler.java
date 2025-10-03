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
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.web.application.views.explorer.dto.ContainmentFeature;
import org.eclipse.sirius.web.application.views.explorer.dto.EditingContextContainmentFeatureNamesInput;
import org.eclipse.sirius.web.application.views.explorer.dto.EditingContextContainmentFeatureNamesPayload;
import org.eclipse.sirius.web.application.views.explorer.services.api.IContainmentFeatureProvider;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to get the possible containment feature names from a container and a candidate contained object.
 *
 * @author lfasani
 */
@Service
public class ContainmentFeatureNamesEventHandler implements IEditingContextEventHandler {

    private final IObjectSearchService objectSearchService;

    private final IContainmentFeatureProvider containmentFeatureNameProvider;

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    public ContainmentFeatureNamesEventHandler(IObjectSearchService objectSearchService, IContainmentFeatureProvider containmentFeatureNameProvider, ICollaborativeMessageService messageService, MeterRegistry meterRegistry) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.containmentFeatureNameProvider = Objects.requireNonNull(containmentFeatureNameProvider);
        this.messageService = Objects.requireNonNull(messageService);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);

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
            Optional<Object> optionalContainer = this.objectSearchService.getObject(editingContext, editingContextContainmentFeatureNamesInput.containerId());
            Optional<Object> optionalChild = this.objectSearchService.getObject(editingContext, editingContextContainmentFeatureNamesInput.containedObjectId());
            if (optionalContainer.isPresent() && optionalChild.isPresent()) {
                var container = optionalContainer.get();
                var child = optionalChild.get();

                List<ContainmentFeature> containmentFeatureNames = this.containmentFeatureNameProvider.getContainmentFeatures(container, child);

                payload = new EditingContextContainmentFeatureNamesPayload(input.id(), containmentFeatureNames);
                changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);
            } else {
                payload = new ErrorPayload(input.id(), List.of(new Message(this.messageService.notFound(), MessageLevel.ERROR)));
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

}
