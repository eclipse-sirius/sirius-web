/*******************************************************************************
 * Copyright (c) 2022, 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.dto;

import java.util.Objects;

/**
 * An object containing representation description data.
 *
 * @author arichard
 */
public record RepresentationDescriptionMetadataDTO(String id, String label, String defaultName, String endUserDocumentation) {

    public RepresentationDescriptionMetadataDTO {
        Objects.requireNonNull(id);
        Objects.requireNonNull(label);
        Objects.requireNonNull(defaultName);
        Objects.requireNonNull(endUserDocumentation);
    }
}
