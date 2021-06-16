/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
import org.eclipse.sirius.web.collaborative.api.services.IRepresentationDeletionService;
import org.eclipse.sirius.web.collaborative.api.services.IRepresentationSearchService;
import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.representations.IRepresentation;
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

        IRepresentationSearchService representationSearchService = new IRepresentationSearchService() {

            @Override
            public <T extends IRepresentation> Optional<T> findById(UUID representationId, Class<T> representationClass) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(UUID representationId) {
                return true;
            }
        };

        IRepresentationDeletionService representationDeletionService = new IRepresentationDeletionService() {

            @Override
            public void deleteDanglingRepresentations(UUID editingContextId) {
            }

            @Override
            public void delete(UUID representationId) {
                hasBeenCalled.set(true);
            }
        };

        EventHandlerResponse response = this.handleEvent(representationSearchService, representationDeletionService);

        assertThat(hasBeenCalled.get()).isTrue();
        assertThat(response.getPayload()).isInstanceOf(DeleteRepresentationSuccessPayload.class);
    }

    @Test
    public void testDeleteRepresentationFailureCases() {
        AtomicBoolean hasBeenCalled = new AtomicBoolean();

        IRepresentationSearchService representationSearchService = new IRepresentationSearchService() {

            @Override
            public <T extends IRepresentation> Optional<T> findById(UUID representationId, Class<T> representationClass) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(UUID representationId) {
                return false;
            }
        };

        IRepresentationDeletionService representationDeletionService = new IRepresentationDeletionService() {

            @Override
            public void deleteDanglingRepresentations(UUID editingContextId) {
            }

            @Override
            public void delete(UUID representationId) {
                hasBeenCalled.set(true);
            }
        };

        EventHandlerResponse response = this.handleEvent(representationSearchService, representationDeletionService);

        assertThat(hasBeenCalled.get()).isFalse();
        assertThat(response.getPayload()).isInstanceOf(ErrorPayload.class);
    }

    private EventHandlerResponse handleEvent(IRepresentationSearchService representationSearchService, IRepresentationDeletionService representationDeletionService) {
        IInput input = new DeleteRepresentationInput(UUID.randomUUID(), UUID.randomUUID());
        DeleteRepresentationEventHandler handler = new DeleteRepresentationEventHandler(representationSearchService, representationDeletionService, new NoOpCollaborativeMessageService(),
                new SimpleMeterRegistry());
        assertThat(handler.canHandle(input)).isTrue();

        IEditingContext editingContext = new NoOpEditingContext();
        return handler.handle(editingContext, input);
    }

}
