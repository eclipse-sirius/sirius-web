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
package org.eclipse.sirius.web.application.representation.dto;

import java.util.List;

/**
 * The DTO for representation metadata.
 *
 * @author gcoutable
 */
public record RepresentationMetadataDTO(
        String id,
        String label,
        String kind,
        String descriptionId,
        List<String> iconURLs) {
}
