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

import org.eclipse.sirius.web.annotations.graphql.GraphQLInterfaceType;

/**
 * Common interface for all the representations.
 *
 * @author sbegaudeau
 */
@GraphQLInterfaceType(name = "Representation")
public interface IRepresentation {
    String getId();

    UUID getDescriptionId();

    String getLabel();

    String getKind();
}
