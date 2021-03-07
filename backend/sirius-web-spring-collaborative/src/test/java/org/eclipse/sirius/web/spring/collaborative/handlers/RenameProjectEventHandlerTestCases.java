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

import org.eclipse.sirius.web.services.api.Context;
import org.eclipse.sirius.web.services.api.projects.IProjectService;
import org.eclipse.sirius.web.services.api.projects.Project;
import org.eclipse.sirius.web.services.api.projects.RenameProjectInput;
import org.eclipse.sirius.web.spring.collaborative.projects.RenameProjectEventHandler;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * Unit tests of the rename project event handler.
 *
 * @author fbarbin
 */
public class RenameProjectEventHandlerTestCases {
    @Test
    public void testRenameObject() {
        AtomicBoolean hasBeenCalled = new AtomicBoolean();
        IProjectService projectService = new NoOpProjectService() {
            @Override
            public Optional<Project> renameProject(UUID projectId, String newName) {
                hasBeenCalled.set(true);
                return Optional.empty();
            }
        };

        RenameProjectEventHandler handler = new RenameProjectEventHandler(new NoOpCollaborativeMessageService(), projectService);
        var input = new RenameProjectInput(UUID.randomUUID(), "newName"); //$NON-NLS-1$
        var context = new Context(new UsernamePasswordAuthenticationToken(null, null));

        assertThat(handler.canHandle(input)).isTrue();

        handler.handle(null, input, context);
        assertThat(hasBeenCalled.get()).isTrue();
    }
}
