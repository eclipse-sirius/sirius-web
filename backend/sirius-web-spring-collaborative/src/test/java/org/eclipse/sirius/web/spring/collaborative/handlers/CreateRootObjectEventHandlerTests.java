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

import org.eclipse.sirius.web.core.api.IEditService;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.spring.collaborative.api.EventHandlerResponse;
import org.eclipse.sirius.web.spring.collaborative.dto.CreateRootObjectInput;
import org.eclipse.sirius.web.spring.collaborative.dto.CreateRootObjectSuccessPayload;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * Tests of the create root object event handler.
 *
 * @author lfasani
 */
public class CreateRootObjectEventHandlerTests {
    @Test
    public void testCreateChild() {
        Object object = new Object();
        IEditService editService = new IEditService.NoOp() {
            @Override
            public Optional<Object> createRootObject(IEditingContext editingContext, UUID documentId, String domainId, String rootObjectCreationDescriptionId) {
                return Optional.of(object);
            }
        };

        CreateRootObjectEventHandler handler = new CreateRootObjectEventHandler(editService, new NoOpCollaborativeMessageService(), new SimpleMeterRegistry());
        var input = new CreateRootObjectInput(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID(), "domainId", "rootObjectCreationDescriptionId"); //$NON-NLS-1$//$NON-NLS-2$
        assertThat(handler.canHandle(input)).isTrue();

        IEditingContext editingContext = () -> UUID.randomUUID().toString();
        EventHandlerResponse handle = handler.handle(editingContext, input);
        assertThat(handle.getPayload()).isInstanceOf(CreateRootObjectSuccessPayload.class);
        assertThat(((CreateRootObjectSuccessPayload) handle.getPayload()).getObject()).isEqualTo(object);
    }
}
