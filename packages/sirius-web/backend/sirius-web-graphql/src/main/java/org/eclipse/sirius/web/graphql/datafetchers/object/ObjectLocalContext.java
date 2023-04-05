/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.web.graphql.datafetchers.object;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import graphql.schema.DataFetchingEnvironment;

/**
 * A LocalContext for Object sub-levels fields (see {@link DataFetchingEnvironment#getLocalContext()}).
 *
 * @author fbarbin
 */
public class ObjectLocalContext {

    private final String editingContextId;

    private final Map<String, Object> variables;

    public ObjectLocalContext(String editingContextId, Map<String, Object> variables) {
        this.editingContextId = Objects.requireNonNull(editingContextId);
        this.variables = new HashMap<>(Objects.requireNonNull(variables));
    }

    public String getEditingContextId() {
        return this.editingContextId;
    }

    public Map<String, Object> getVariables() {
        return this.variables;
    }
}
