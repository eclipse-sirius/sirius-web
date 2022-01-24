/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.web.core.api;

import java.util.Optional;

import org.eclipse.sirius.web.core.RepresentationMetadata;
import org.eclipse.sirius.web.representations.IRepresentation;

/**
 * Used to search and retrieve the metadata of a representation.
 *
 * @author sbegaudeau
 */
public interface IRepresentationMetadataSearchService {
    Optional<RepresentationMetadata> findByRepresentation(IRepresentation representation);
}
