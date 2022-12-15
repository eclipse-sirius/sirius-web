/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.sirius.components.collaborative.dto;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IInput;

/**
 * The input object of the queryBasedIntEventHandler.
 *
 * @author fbarbin
 */
public final class QueryBasedIntInput implements IInput {

    private UUID id;

    private String query;

    private Map<String, Object> variables;

    public QueryBasedIntInput(UUID id, String query, Map<String, Object> variables) {
        this.id = Objects.requireNonNull(id);
        this.query = Objects.requireNonNull(query);
        this.variables = new HashMap<>(Objects.requireNonNull(variables));
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public String getQuery() {
        return this.query;
    }

    public Map<String, Object> getVariables() {
        return this.variables;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, query: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.query);
    }

}
