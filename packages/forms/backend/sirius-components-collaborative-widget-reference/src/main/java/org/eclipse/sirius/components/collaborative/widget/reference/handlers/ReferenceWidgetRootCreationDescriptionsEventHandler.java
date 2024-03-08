/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.dto.EditingContextRootObjectCreationDescriptionsPayload;
import org.eclipse.sirius.components.collaborative.widget.reference.ReferenceWidgetDefaultCreateElementHandler;
import org.eclipse.sirius.components.collaborative.widget.reference.dto.ReferenceWidgetRootCreationDescriptionsInput;
import org.eclipse.sirius.components.core.api.ChildCreationDescription;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.collaborative.widget.reference.api.IReferenceWidgetCreateElementHandler;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Handler used to retrieve the root creation descriptions from a reference widget.
 *
 * @author frouene
 */
@Service
public class ReferenceWidgetRootCreationDescriptionsEventHandler implements IEditingContextEventHandler {

    private final List<IReferenceWidgetCreateElementHandler> referenceWidgetCreateElementHandlers;

    private final ReferenceWidgetDefaultCreateElementHandler defaultReferenceWidgetCreateElementHandler;

    private final Counter counter;

    public ReferenceWidgetRootCreationDescriptionsEventHandler(List<IReferenceWidgetCreateElementHandler> referenceWidgetCreateElementHandlers, IEditService editService, MeterRegistry meterRegistry) {
        this.referenceWidgetCreateElementHandlers = Objects.requireNonNull(referenceWidgetCreateElementHandlers);
        this.defaultReferenceWidgetCreateElementHandler = new ReferenceWidgetDefaultCreateElementHandler(Objects.requireNonNull(editService));
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER).tag(Monitoring.NAME, this.getClass().getSimpleName()).register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof ReferenceWidgetRootCreationDescriptionsInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        List<ChildCreationDescription> childCreationDescriptions = List.of();
        if (input instanceof ReferenceWidgetRootCreationDescriptionsInput castInput) {

            childCreationDescriptions = this.referenceWidgetCreateElementHandlers.stream()
                    .filter(provider -> provider.canHandle(castInput.descriptionId()))
                    .findFirst()
                    .orElse(this.defaultReferenceWidgetCreateElementHandler)
                    .getRootCreationDescriptions(editingContext, castInput.domainId(), castInput.referenceKind(), castInput.descriptionId());
        }
        payloadSink.tryEmitValue(new EditingContextRootObjectCreationDescriptionsPayload(input.id(), childCreationDescriptions));
    }

}
