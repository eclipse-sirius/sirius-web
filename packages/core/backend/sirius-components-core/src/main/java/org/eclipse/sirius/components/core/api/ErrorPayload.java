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
package org.eclipse.sirius.components.core.api;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;

/**
 * General purpose error payload.
 * During an intermediate phase, to avoid breaking all frontend component interfaces,
 * we keep the two ways of broadcasting error messages, in a single String and in a more complex {@link Message} list.
 *
 * @author sbegaudeau
 */
public record ErrorPayload(UUID id, String message, List<Message> messages) implements IPayload {

    public ErrorPayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(message);
        Objects.requireNonNull(messages);
    }

    public ErrorPayload(UUID id, String message) {
        this(id, message, List.of(new Message(message, MessageLevel.ERROR)));
    }

    public ErrorPayload(UUID id, List<Message> messages) {
        this(id, messages.stream().findFirst().map(Message::body).orElse(""), messages);
    }
}
