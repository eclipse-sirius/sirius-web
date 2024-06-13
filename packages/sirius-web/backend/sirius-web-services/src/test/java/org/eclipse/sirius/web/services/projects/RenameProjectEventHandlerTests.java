/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.web.services.projects;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.services.api.projects.IProjectService;
import org.eclipse.sirius.web.services.api.projects.Project;
import org.eclipse.sirius.web.services.api.projects.RenameProjectInput;
import org.eclipse.sirius.web.services.api.projects.RenameProjectSuccessPayload;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Unit tests of the rename project event handler.
 *
 * @author fbarbin
 */
public class RenameProjectEventHandlerTests {
    @Test
    public void testRenameProject() {
        Project project = new Project(UUID.randomUUID(), "newName", List.of());

        AtomicBoolean hasBeenCalled = new AtomicBoolean();
        IProjectService projectService = new IProjectService.NoOp() {
            @Override
            public Optional<Project> renameProject(UUID projectId, String newName) {
                hasBeenCalled.set(true);
                return Optional.of(project);
            }
        };

        RenameProjectEventHandler handler = new RenameProjectEventHandler(new NoOpServicesMessageService(), projectService);
        var input = new RenameProjectInput(UUID.randomUUID(), UUID.randomUUID(), "newName");
        IEditingContext editingContext = () -> UUID.randomUUID().toString();

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        assertThat(handler.canHandle(editingContext, input)).isTrue();
        handler.handle(payloadSink, changeDescriptionSink, editingContext, input);
        assertThat(hasBeenCalled.get()).isTrue();

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(RenameProjectSuccessPayload.class);
    }
}
