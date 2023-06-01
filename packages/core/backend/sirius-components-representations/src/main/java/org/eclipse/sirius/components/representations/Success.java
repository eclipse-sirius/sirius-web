/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
package org.eclipse.sirius.components.representations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The status to return when the representation description handler success.
 *
 * @author gcoutable
 */
public class Success implements IStatus {

    public static final String NEW_SELECTION = "newSelection";

    private final Map<String, Object> parameters;

    private final String changeKind;

    private final List<Message> messages;

    public Success() {
        this("", new HashMap<>());
    }

    public Success(List<Message> messages) {
        this("", new HashMap<>(), messages);
    }

    public Success(String changeKind, Map<String, Object> parameters) {
        this(Objects.requireNonNull(changeKind), Objects.requireNonNull(parameters), List.of());
    }

    public Success(String changeKind, Map<String, Object> parameters, List<Message> messages) {
        this.parameters = Objects.requireNonNull(parameters);
        this.changeKind = Objects.requireNonNull(changeKind);
        this.messages = Objects.requireNonNull(messages);
    }

    public String getChangeKind() {
        return this.changeKind;
    }

    public Map<String, Object> getParameters() {
        return this.parameters;
    }

    public List<Message> getMessages() {
        return this.messages;
    }
}
