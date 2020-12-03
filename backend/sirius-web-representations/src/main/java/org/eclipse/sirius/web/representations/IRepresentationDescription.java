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
package org.eclipse.sirius.web.representations;

import java.util.UUID;
import java.util.function.Predicate;

import org.eclipse.sirius.web.annotations.PublicApi;
import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLInterfaceType;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;

/**
 * Common interface for all the representation descriptions.
 *
 * @author sbegaudeau
 */
@PublicApi
@GraphQLInterfaceType(name = "RepresentationDescription")
public interface IRepresentationDescription {

    String CLASS = "class"; //$NON-NLS-1$

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    UUID getId();

    @GraphQLField
    @GraphQLNonNull
    String getLabel();

    Predicate<VariableManager> getCanCreatePredicate();
}
