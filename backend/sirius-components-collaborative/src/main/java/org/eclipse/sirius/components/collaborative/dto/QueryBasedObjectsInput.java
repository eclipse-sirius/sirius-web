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
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IInput;

import graphql.schema.DataFetchingEnvironment;

/**
 * The input object of the queryBasedObjectsEventHandler.
 *
 * @author fbarbin
 */
public final class QueryBasedObjectsInput implements IInput {

    private UUID id;

    private DataFetchingEnvironment environment;

    public QueryBasedObjectsInput(UUID id, DataFetchingEnvironment environment) {
        this.id = Objects.requireNonNull(id);
        this.environment = Objects.requireNonNull(environment);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public DataFetchingEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }
}
