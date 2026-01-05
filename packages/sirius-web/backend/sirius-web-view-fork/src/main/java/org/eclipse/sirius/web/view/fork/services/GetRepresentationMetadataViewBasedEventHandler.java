/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
package org.eclipse.sirius.web.view.fork.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionIdProvider;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.web.view.fork.dto.GetRepresentationMetadataViewBasedInput;
import org.eclipse.sirius.web.view.fork.dto.GetRepresentationMetadataViewBasedPayload;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Event handler for RepresentationMetadata#isViewBased query.
 *
 * @author frouene
 */
@Service
public class GetRepresentationMetadataViewBasedEventHandler implements IEditingContextEventHandler {

    private final IIdentityService identityService;

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final IURLParser urlParser;

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    public GetRepresentationMetadataViewBasedEventHandler(IIdentityService identityService, IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService,
                                                          IRepresentationMetadataSearchService representationMetadataSearchService, IURLParser urlParser, ICollaborativeMessageService messageService, MeterRegistry meterRegistry) {
        this.identityService = Objects.requireNonNull(identityService);
        this.viewRepresentationDescriptionSearchService = Objects.requireNonNull(viewRepresentationDescriptionSearchService);
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
        this.urlParser = Objects.requireNonNull(urlParser);
        this.messageService = Objects.requireNonNull(messageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof GetRepresentationMetadataViewBasedInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);
        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), GetRepresentationMetadataViewBasedInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.id(), message);

        var optionalSemanticDataId = new UUIDParser().parse(editingContext.getId());
        if (input instanceof GetRepresentationMetadataViewBasedInput getRepresentationMetadataViewBasedInput && optionalSemanticDataId.isPresent()) {
            var semanticDataId = optionalSemanticDataId.get();
            boolean isViewBased = this.representationMetadataSearchService.findMetadataById(AggregateReference.to(semanticDataId), UUID.fromString(getRepresentationMetadataViewBasedInput.representationId()))
                    .map(representationMetadata -> {
                        var sourceId = this.getSourceId(representationMetadata.getDescriptionId());
                        var sourceElementId = this.getSourceElementId(representationMetadata.getDescriptionId());

                        if (sourceId.isPresent() && sourceElementId.isPresent()) {
                            return this.viewRepresentationDescriptionSearchService.findViewsBySourceId(editingContext, sourceId.get()).stream()
                                    .flatMap(view -> view.getDescriptions().stream())
                                    .anyMatch(description -> this.identityService.getId(description).equals(sourceElementId.get()));
                        }
                        return false;
                    })
                    .orElse(false);
            payload = new GetRepresentationMetadataViewBasedPayload(input.id(), isViewBased);
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private Optional<String> getSourceId(String descriptionId) {
        var parameters = this.urlParser.getParameterValues(descriptionId);
        return Optional.ofNullable(parameters.get(IRepresentationDescriptionIdProvider.SOURCE_ID)).orElse(List.of()).stream().findFirst();
    }

    private Optional<String> getSourceElementId(String descriptionId) {
        var parameters = this.urlParser.getParameterValues(descriptionId);
        return Optional.ofNullable(parameters.get(IRepresentationDescriptionIdProvider.SOURCE_ELEMENT_ID)).orElse(List.of()).stream().findFirst();
    }
}
