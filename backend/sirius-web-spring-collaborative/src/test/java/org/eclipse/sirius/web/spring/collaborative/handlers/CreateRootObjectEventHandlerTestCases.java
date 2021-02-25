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

import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.services.api.document.CreateRootObjectInput;
import org.eclipse.sirius.web.services.api.document.CreateRootObjectSuccessPayload;
import org.eclipse.sirius.web.services.api.objects.IEditService;
import org.junit.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * Tests of the create root object event handler.
 *
 * @author lfasani
 */
public class CreateRootObjectEventHandlerTestCases {
    @Test
    public void testCreateChild() {
        Object object = new Object();
        IEditService editService = new NoOpEditService() {
            @Override
            public Optional<Object> createRootObject(IEditingContext editingContext, UUID documentId, String namespaceId, String rootObjectCreationDescriptionId) {
                return Optional.of(object);
            }
        };

        CreateRootObjectEventHandler handler = new CreateRootObjectEventHandler(editService, new NoOpCollaborativeMessageService(), new SimpleMeterRegistry());
        var input = new CreateRootObjectInput(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "namespaceId", "rootObjectCreationDescriptionId"); //$NON-NLS-1$//$NON-NLS-2$
        assertThat(handler.canHandle(input)).isTrue();

        EventHandlerResponse handle = handler.handle(null, input);
        assertThat(handle.getPayload()).isInstanceOf(CreateRootObjectSuccessPayload.class);
        assertThat(((CreateRootObjectSuccessPayload) handle.getPayload()).getObject()).isEqualTo(object);
    }
}
