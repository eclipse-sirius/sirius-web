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

import java.util.Optional;

import org.eclipse.sirius.web.application.representation.dto.RepresentationMetadataDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Used to interact with representations.
 *
 * @author sbegaudeau
 */
public interface IRepresentationApplicationService {

    Page<RepresentationMetadataDTO> findAllByEditingContextId(String editingContextId, Pageable pageable);

    Optional<String> findEditingContextIdFromRepresentationId(String representationId);

    Optional<RepresentationMetadataDTO> findRepresentationMetadataById(String representationMetadataId);
}
