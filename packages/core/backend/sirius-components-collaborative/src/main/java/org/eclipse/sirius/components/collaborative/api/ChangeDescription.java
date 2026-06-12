/*******************************************************************************
 * Copyright (c) 2021, 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.api;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.events.ICause;

/**
 * The change description is used to express the changes that have been performed in response to an input in the
 * collaborative layer. It helps the various representations event processors among others determine if they are
 * impacted by a change and thus if they should refresh the representation that they are managing.
 *
 * @author sbegaudeau
 */
public class ChangeDescription {

    private final String kind;

    private final String sourceId;

    private final ICause cause;

    private final Map<String, Object> parameters;

    public ChangeDescription(String kind, String sourceId, IInput input) {
        this(kind, sourceId, input, new HashMap<>());
    }

    public ChangeDescription(String kind, String sourceId, IInput input, Map<String, Object> parameters) {
        this(kind, sourceId, (ICause) input, parameters);
    }

    public ChangeDescription(String kind, String sourceId, ICause cause) {
        this(kind, sourceId, cause, new HashMap<>());
    }

    public ChangeDescription(String kind, String sourceId, ICause cause, Map<String, Object> parameters) {
        this.kind = Objects.requireNonNull(kind);
        this.sourceId = Objects.requireNonNull(sourceId);
        this.cause = Objects.requireNonNull(cause);
        this.parameters = Objects.requireNonNull(parameters);
    }

    public String getKind() {
        return this.kind;
    }

    public String getSourceId() {
        return this.sourceId;
    }

    public IInput getInput() {
        IInput input = null;
        ICause currentCause = this.cause;
        while (input == null && currentCause != null) {
            if (currentCause instanceof IInput currentInput) {
                input = currentInput;
            }
            currentCause = currentCause.causedBy();
        }
        return input;
    }

    public ICause getCause() {
        return this.cause;
    }

    public Map<String, Object> getParameters() {
        return this.parameters;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'kind: {1}, sourceId: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.kind, this.sourceId);
    }
}
