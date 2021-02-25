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

import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.services.api.objects.DeleteObjectInput;
import org.eclipse.sirius.web.services.api.objects.IEditService;
import org.junit.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * Unit tests of the delete object event handler.
 *
 * @author sbegaudeau
 */
public class DeleteObjectEventHandlerTestCases {
    @Test
    public void testDeleteObject() {
        IObjectService objectService = new NoOpObjectService() {
            @Override
            public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
                return Optional.of(new Object());
            }
        };

        AtomicBoolean hasBeenCalled = new AtomicBoolean();
        IEditService editService = new NoOpEditService() {
            @Override
            public void delete(Object object) {
                hasBeenCalled.set(true);
            }
        };

        DeleteObjectEventHandler handler = new DeleteObjectEventHandler(objectService, editService, new NoOpCollaborativeMessageService(), new SimpleMeterRegistry());
        var input = new DeleteObjectInput(UUID.randomUUID(), UUID.randomUUID(), "objectId"); //$NON-NLS-1$

        assertThat(handler.canHandle(input)).isTrue();

        handler.handle(null, input);
        assertThat(hasBeenCalled.get()).isTrue();
    }
}
