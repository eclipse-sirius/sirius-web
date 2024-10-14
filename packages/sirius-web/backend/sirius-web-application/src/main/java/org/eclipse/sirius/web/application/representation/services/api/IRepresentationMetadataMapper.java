/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

package org.eclipse.sirius.web.application.representation.services.api;

import org.eclipse.sirius.web.application.representation.dto.RepresentationMetadataDTO;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;

/**
 * Used to convert a representation metadata to a DTO.
 *
 * @author gcoutable
 */
public interface IRepresentationMetadataMapper {
    RepresentationMetadataDTO toDTO(RepresentationMetadata representationMetadata);
}
