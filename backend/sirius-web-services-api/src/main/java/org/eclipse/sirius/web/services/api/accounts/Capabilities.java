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
package org.eclipse.sirius.web.services.api.accounts;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;

/**
 * DTO representing the optional capabilities available to a viewer.
 *
 * @author pcdavid
 */
@GraphQLObjectType
public class Capabilities {
    private List<String> pathOverrides;

    public Capabilities(List<String> pathOverrides) {
        this.pathOverrides = List.copyOf(pathOverrides);
    }

    @GraphQLField
    @GraphQLNonNull
    public List<String> getPathOverrides() {
        return this.pathOverrides;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{' pathOverrides: {1} '}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.pathOverrides);
    }
}
