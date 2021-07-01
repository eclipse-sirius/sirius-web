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

import org.eclipse.sirius.web.collaborative.api.dto.RenameObjectInput;
import org.eclipse.sirius.web.core.api.IEditService;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * Unit tests of the rename object event handler.
 *
 * @author arichard
 */
public class RenameObjectEventHandlerTests {
    @Test
    public void testRenameObject() {
        IObjectService objectService = new NoOpObjectService() {
            @Override
            public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
                return Optional.of(new Object());
            }

            @Override
            public Optional<String> getLabelField(Object object) {
                return Optional.of("label"); //$NON-NLS-1$
            }
        };

        AtomicBoolean hasBeenCalled = new AtomicBoolean();
        IEditService editService = new NoOpEditService() {
            @Override
            public void editLabel(Object object, String labelField, String newValue) {
                hasBeenCalled.set(true);
            }
        };

        RenameObjectEventHandler handler = new RenameObjectEventHandler(new NoOpCollaborativeMessageService(), objectService, editService, new SimpleMeterRegistry());
        var input = new RenameObjectInput(UUID.randomUUID(), UUID.randomUUID(), "objectId", "newName"); //$NON-NLS-1$ //$NON-NLS-2$

        assertThat(handler.canHandle(input)).isTrue();

        IEditingContext editingContext = () -> UUID.randomUUID();
        handler.handle(editingContext, input);
        assertThat(hasBeenCalled.get()).isTrue();
    }
}
