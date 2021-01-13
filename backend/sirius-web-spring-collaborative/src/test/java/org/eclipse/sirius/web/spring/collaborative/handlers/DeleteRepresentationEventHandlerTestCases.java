/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.handlers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.sirius.web.collaborative.api.dto.DeleteRepresentationInput;
import org.eclipse.sirius.web.collaborative.api.dto.DeleteRepresentationSuccessPayload;
import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.services.api.representations.IRepresentationService;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;
import org.junit.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * Unit tests of the delete representation event handler.
 *
 * @author lfasani
 */
public class DeleteRepresentationEventHandlerTestCases {
    @Test
    public void testDeleteRepresentation() {
        AtomicBoolean hasBeenCalled = new AtomicBoolean();

        IRepresentationService representationService = new NoOpRepresentationService() {
            @Override
            public Optional<RepresentationDescriptor> getRepresentation(UUID representationId) {
                IRepresentation representation = new IRepresentation() {

                    @Override
                    public String getLabel() {
                        return null;
                    }

                    @Override
                    public String getKind() {
                        return null;
                    }

                    @Override
                    public UUID getId() {
                        return null;
                    }

                    @Override
                    public UUID getDescriptionId() {
                        return null;
                    }
                };
                RepresentationDescriptor representationDescriptor = RepresentationDescriptor.newRepresentationDescriptor(UUID.randomUUID()).projectId(UUID.randomUUID())
                        .descriptionId(UUID.randomUUID()).targetObjectId(UUID.randomUUID().toString()).label("") //$NON-NLS-1$
                        .representation(representation).build();
                return Optional.of(representationDescriptor);
            }

            @Override
            public void delete(UUID representationId) {
                hasBeenCalled.set(true);
            }
        };

        EventHandlerResponse response = this.handleEvent(representationService);

        assertThat(hasBeenCalled.get()).isTrue();
        assertThat(response.getPayload()).isInstanceOf(DeleteRepresentationSuccessPayload.class);
    }

    @Test
    public void testDeleteRepresentationFailureCases() {
        AtomicBoolean hasBeenCalled = new AtomicBoolean();

        IRepresentationService representationService = new NoOpRepresentationService() {
            @Override
            public Optional<RepresentationDescriptor> getRepresentation(UUID representationId) {
                return Optional.empty();
            }

            @Override
            public void delete(UUID representationId) {
                hasBeenCalled.set(true);
            }
        };

        EventHandlerResponse response = this.handleEvent(representationService);

        assertThat(hasBeenCalled.get()).isFalse();
        assertThat(response.getPayload()).isInstanceOf(ErrorPayload.class);
    }

    private EventHandlerResponse handleEvent(IRepresentationService representationService) {
        IInput input = new DeleteRepresentationInput(UUID.randomUUID());
        DeleteRepresentationEventHandler handler = new DeleteRepresentationEventHandler(representationService, new NoOpCollaborativeMessageService(), new SimpleMeterRegistry());
        assertThat(handler.canHandle(input)).isTrue();

        IEditingContext editingContext = new NoOpEditingContext();
        return handler.handle(editingContext, input);
    }

}
