/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.dto.EditingContextChildObjectCreationDescriptionsPayload;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.components.collaborative.forms.api.IFormInput;
import org.eclipse.sirius.components.collaborative.widget.reference.api.IReferenceWidgetCreateElementHandler;
import org.eclipse.sirius.components.collaborative.widget.reference.dto.ReferenceWidgetChildCreationDescriptionsInput;
import org.eclipse.sirius.components.core.api.ChildCreationDescription;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Handler used to retrieve the child creation descriptions from a reference widget.
 *
 * @author frouene
 */
@Service
public class ReferenceWidgetChildCreationDescriptionsEventHandler implements IFormEventHandler {

    private final List<IReferenceWidgetCreateElementHandler> referenceWidgetCreateElementHandlers;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final Counter counter;

    public ReferenceWidgetChildCreationDescriptionsEventHandler(List<IReferenceWidgetCreateElementHandler> referenceWidgetCreateElementHandlers,
            IRepresentationDescriptionSearchService representationDescriptionSearchService, MeterRegistry meterRegistry) {
        this.referenceWidgetCreateElementHandlers = Objects.requireNonNull(referenceWidgetCreateElementHandlers);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER).tag(Monitoring.NAME, this.getClass().getSimpleName()).register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IFormInput input) {
        return input instanceof ReferenceWidgetChildCreationDescriptionsInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, Form form, IFormInput input) {
        this.counter.increment();

        List<ChildCreationDescription> childCreationDescriptions = List.of();
        if (input instanceof ReferenceWidgetChildCreationDescriptionsInput castInput) {
            var optionalFormDescription = this.representationDescriptionSearchService.findById(editingContext, form.getDescriptionId());
            if (optionalFormDescription.isPresent() && optionalFormDescription.get() instanceof FormDescription formDescription) {
                childCreationDescriptions = this.referenceWidgetCreateElementHandlers.stream()
                        .filter(provider -> provider.canHandle(formDescription))
                        .findFirst()
                        .map(handler -> handler.getChildCreationDescriptions(editingContext, castInput.containerId(), castInput.referenceKind(), castInput.descriptionId()))
                        .orElse(List.of());
            }
        }
        payloadSink.tryEmitValue(new EditingContextChildObjectCreationDescriptionsPayload(input.id(), childCreationDescriptions));
    }

}
