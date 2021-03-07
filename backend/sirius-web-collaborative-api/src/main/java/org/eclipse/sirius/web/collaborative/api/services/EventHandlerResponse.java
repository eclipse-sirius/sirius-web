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
package org.eclipse.sirius.web.collaborative.api.services;

import java.util.Objects;
import java.util.function.Predicate;

import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.services.api.dto.IPayload;

/**
 * Response of the event handlers used to indicate how the execution of the event handler should impact the project.
 *
 * @author sbegaudeau
 */
public class EventHandlerResponse {
    private final boolean isEditingContextDirty;

    private final Predicate<IRepresentation> shouldRefreshPredicate;

    private final IPayload payload;

    public EventHandlerResponse(boolean isEditingContextDirty, Predicate<IRepresentation> shouldRefreshPredicate, IPayload payload) {
        this.isEditingContextDirty = Objects.requireNonNull(isEditingContextDirty);
        this.shouldRefreshPredicate = Objects.requireNonNull(shouldRefreshPredicate);
        this.payload = Objects.requireNonNull(payload);
    }

    public boolean isEditingContextDirty() {
        return this.isEditingContextDirty;
    }

    public Predicate<IRepresentation> getShouldRefreshPredicate() {
        return this.shouldRefreshPredicate;
    }

    public IPayload getPayload() {
        return this.payload;
    }
}
