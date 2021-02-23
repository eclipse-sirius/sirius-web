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
package org.eclipse.sirius.web.collaborative.api.services;

import java.util.Objects;

import org.eclipse.sirius.web.core.api.IPayload;

/**
 * Response of the event handlers used to indicate how the execution of the event handler should impact the project.
 *
 * @author sbegaudeau
 */
public class EventHandlerResponse {
    private final String changeKind;

    private final IPayload payload;

    public EventHandlerResponse(String changeKind, IPayload payload) {
        this.changeKind = Objects.requireNonNull(changeKind);
        this.payload = Objects.requireNonNull(payload);
    }

    public String getChangeKind() {
        return this.changeKind;
    }

    public IPayload getPayload() {
        return this.payload;
    }
}
