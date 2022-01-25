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
package org.eclipse.sirius.components.graphql.utils.schema;

import graphql.schema.GraphQLType;

/**
 * Used to modify a GraphQL type before its integration into the schema.
 *
 * @author sbegaudeau
 */
public interface ITypeCustomizer {
    GraphQLType customize(GraphQLType graphQLType);
}
