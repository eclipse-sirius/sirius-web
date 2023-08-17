/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.web.services.api.projects;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;

import reactor.core.publisher.Flux;

/**
 * Interface of the service used to manipulate projects.
 *
 * @author sbegaudeau
 */
public interface IProjectService {

    Optional<Project> getProject(UUID projectId);

    default List<String> getNatures(UUID projectId) {
        return List.of();
    }

    List<Project> getProjects();

    IPayload createProject(CreateProjectInput input);

    IPayload createProject(CreateProjectFromTemplateInput input);

    void delete(UUID projectId);

    Optional<Project> renameProject(UUID projectId, String newName);

    Flux<IPayload> getOutputEvents(UUID projectId);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IProjectService {

        @Override
        public Optional<Project> getProject(UUID projectId) {
            return Optional.empty();
        }

        @Override
        public List<Project> getProjects() {
            return List.of();
        }

        @Override
        public IPayload createProject(CreateProjectInput input) {
            return null;
        }

        @Override
        public IPayload createProject(CreateProjectFromTemplateInput input) {
            return null;
        }

        @Override
        public void delete(UUID projectId) {
        }

        @Override
        public Optional<Project> renameProject(UUID projectId, String newName) {
            return Optional.empty();
        }

        @Override
        public Flux<IPayload> getOutputEvents(UUID projectId) {
            return Flux.empty();
        }
    }
}
