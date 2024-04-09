/*******************************************************************************
 * Copyright (c) 2024, 2024 Obeo.
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
package org.eclipse.sirius.web.domain.boundedcontexts.representationdata.projections;

import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

/**
 * Projection used to retrieve only the representation metadata of the representation data.
 *
 * @author sbegaudeau
 */
public record RepresentationDataMetadataOnly(
        UUID id,
        String label,
        String kind,
        String targetObjectId,
        String descriptionId,
        AggregateReference<Project, UUID> project) {
}
