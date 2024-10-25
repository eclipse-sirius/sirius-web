/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.forms.handlers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationMetadataPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationSuccessPayload;
import org.eclipse.sirius.components.collaborative.forms.messages.ICollaborativeFormMessageService;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Used to test the creation of a form.
 *
 * @author sbegaudeau
 */
public class CreateFormEventHandlerTests {

    @Test
    public void testFormCreation() {
        var formDescription = FormDescription.newFormDescription("representationDescriptionId")
                .label("label")
                .canCreatePredicate(variableManager -> true)
                .pageDescriptions(List.of())
                .idProvider(variableManager -> "id")
                .labelProvider(variableManager -> "label")
                .targetObjectIdProvider(variableManager -> "targetObjectId")
                .build();

        IRepresentationDescriptionSearchService representationDescriptionSearchService = new IRepresentationDescriptionSearchService.NoOp() {
            @Override
            public Optional<IRepresentationDescription> findById(IEditingContext editingContext, String representationDescriptionId) {
                return Optional.of(formDescription);
            }
        };

        AtomicBoolean representationHasBeenPersisted = new AtomicBoolean();
        IRepresentationPersistenceService representationPersistenceService = new IRepresentationPersistenceService.NoOp() {
            @Override
            public void save(ICause cause, IEditingContext editingContext, IRepresentation representation) {
                representationHasBeenPersisted.set(true);
            }
        };

        AtomicBoolean representationMetadataHasBeenPersisted = new AtomicBoolean();
        IRepresentationMetadataPersistenceService representationMetadataPersistenceService = new IRepresentationMetadataPersistenceService.NoOp() {
            @Override
            public void save(ICause cause, IEditingContext editingContext, RepresentationMetadata representationMetadata, String targetObjectId) {
                representationMetadataHasBeenPersisted.set(true);
            }
        };

        IObjectService objectService = new IObjectService.NoOp() {
            @Override
            public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
                return Optional.of(new Object());
            }
        };

        var handler = new CreateFormEventHandler(representationDescriptionSearchService, representationMetadataPersistenceService, representationPersistenceService, objectService, new ICollaborativeFormMessageService.NoOp(),
                new SimpleMeterRegistry());
        var input = new CreateRepresentationInput(UUID.randomUUID(), "editingContextId", "representationDescriptionId", "objectId", "representationName");
        IEditingContext editingContext = () -> "editingContextId";

        assertThat(handler.canHandle(editingContext, input)).isTrue();

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        handler.handle(payloadSink, changeDescriptionSink, editingContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.REPRESENTATION_CREATION);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(CreateRepresentationSuccessPayload.class);

        assertThat(representationHasBeenPersisted.get()).isTrue();
        assertThat(representationMetadataHasBeenPersisted.get()).isTrue();
    }
}
