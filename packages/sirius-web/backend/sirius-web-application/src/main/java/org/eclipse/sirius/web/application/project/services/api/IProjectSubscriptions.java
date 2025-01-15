/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.application.project.services.api;

import java.util.Optional;
import org.eclipse.sirius.components.core.api.IPayload;

import reactor.core.publisher.Flux;

/**
 * Used to publish or subscribe to project events.
 *
 * @author sbegaudeau
 */
public interface IProjectSubscriptions {
    Optional<Flux<IPayload>> findProjectSubscriptionById(String projectId);

    void emit(String projectId, IPayload payload);

    void dispose(String projectId);
}
