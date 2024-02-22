/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.application.project.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.application.project.services.api.IProjectSubscriptions;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * Used to publish and subscribe to project events.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectSubscriptions implements IProjectSubscriptions {

    private final IProjectSearchService projectSearchService;

    private final Map<UUID, Sinks.Many<IPayload>> projectIdsToSink = new HashMap<>();

    public ProjectSubscriptions(IProjectSearchService projectSearchService) {
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Flux<IPayload>> findProjectSubscriptionById(UUID projectId) {
        if (this.projectSearchService.existsById(projectId)) {
            var many = this.projectIdsToSink.computeIfAbsent(projectId, id -> Sinks.many().multicast().directBestEffort());
            return Optional.of(many.asFlux());
        }
        return Optional.empty();
    }

    @Override
    public void emit(UUID projectId, IPayload payload) {
        Optional.ofNullable(this.projectIdsToSink.get(projectId)).ifPresent(many -> many.tryEmitNext(payload));
    }

    @Override
    public void dispose(UUID projectId) {
        Optional.ofNullable(this.projectIdsToSink.get(projectId)).ifPresent(Sinks.Many::tryEmitComplete);
        this.projectIdsToSink.remove(projectId);
    }
}
