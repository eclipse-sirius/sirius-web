/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.graphql.utils.providers.GraphQLObjectTypeProvider;
import org.eclipse.sirius.web.graphql.utils.schema.ITypeProvider;
import org.eclipse.sirius.web.validation.Diagnostic;
import org.eclipse.sirius.web.validation.Validation;
import org.springframework.stereotype.Service;

import graphql.schema.GraphQLType;

/**
 * This class is used to create all the definitions of the types related to the validation representation.
 *
 * @author gcoutable
 */
@Service
public class ValidationTypesProvider implements ITypeProvider {
    public static final String VALIDATION_TYPE = "Validation"; //$NON-NLS-1$

    public static final String DIAGNOSTIC_TYPE = "Diagnostic"; //$NON-NLS-1$

    private final GraphQLObjectTypeProvider graphQLObjectTypeProvider = new GraphQLObjectTypeProvider();

    @Override
    public Set<GraphQLType> getTypes() {
        // @formatter:off
        List<Class<?>> objectClasses = List.of(
            Validation.class,
            Diagnostic.class
        );

        return objectClasses.stream()
            .map(this.graphQLObjectTypeProvider::getType)
            .collect(Collectors.toUnmodifiableSet());
        // @formatter:on

    }

}
