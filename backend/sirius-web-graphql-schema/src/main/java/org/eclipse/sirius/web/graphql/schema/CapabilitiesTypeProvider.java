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
package org.eclipse.sirius.web.graphql.schema;

import java.util.Set;

import org.eclipse.sirius.web.graphql.utils.providers.GraphQLObjectTypeProvider;
import org.eclipse.sirius.web.graphql.utils.schema.ITypeProvider;
import org.eclipse.sirius.web.services.api.accounts.Capabilities;
import org.springframework.stereotype.Service;

import graphql.schema.GraphQLType;

/**
 * This class is used to create the definition of the Capabilities type.
 * <p>
 * The types created will match the following GraphQL textual definition:
 * </p>
 *
 * <pre>
 * type Capabilities {
 *   pathOverrides: [String!]!
 * }
 * </pre>
 *
 *
 * @author pcdavid
 */
@Service
public class CapabilitiesTypeProvider implements ITypeProvider {
    public static final String TYPE = "Capabilities"; //$NON-NLS-1$

    private final GraphQLObjectTypeProvider graphQLObjectTypeProvider = new GraphQLObjectTypeProvider();

    @Override
    public Set<GraphQLType> getTypes() {
        return Set.of(this.graphQLObjectTypeProvider.getType(Capabilities.class));
    }
}
