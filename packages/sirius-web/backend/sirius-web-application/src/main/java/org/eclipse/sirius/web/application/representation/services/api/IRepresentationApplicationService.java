/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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

import org.eclipse.sirius.components.core.graphql.dto.RepresentationMetadataDTO;
import org.eclipse.sirius.web.domain.pagination.Window;
import org.springframework.data.domain.KeysetScrollPosition;

/**
 * Used to interact with representations.
 *
 * @author sbegaudeau
 */
public interface IRepresentationApplicationService {

    Window<RepresentationMetadataDTO> findAllByEditingContextId(String editingContextId, KeysetScrollPosition position, int limit);

    Optional<RepresentationMetadataDTO> findRepresentationMetadataById(String editingContextId, String representationMetadataId);
}
