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
package org.eclipse.sirius.web.representations;

import java.util.UUID;

import org.eclipse.sirius.web.annotations.graphql.GraphQLInterfaceType;

/**
 * The metadata for a representation (without its actual content). This object should be kept lightweight (both to
 * retrieve and to transfer).
 *
 * @author pcdavid
 */
@GraphQLInterfaceType(name = "RepresentationMetadata")
public interface IRepresentationMetadata {
    /**
     * The globally unique identifier of the representation.
     */
    String getId();

    /**
     * The globally unique identifier of the representation's description.
     */
    UUID getDescriptionId();

    /**
     * The user-visible (and editable) label of the representation.
     */
    String getLabel();

    /**
     * Indicates the kind/nature of the representation (e.g. a diagram, a form, etc.)
     */
    String getKind();
}
