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
package org.eclipse.sirius.components.view.emf.api;

import java.util.Objects;
import java.util.UUID;

/**
 * Lightweight DTO representing a custom image metadata.
 *
 * @author pcdavid
 */
public record CustomImageMetadata(UUID id, String label, String contentType) {
    public CustomImageMetadata {
        Objects.requireNonNull(id);
        Objects.requireNonNull(label);
        Objects.requireNonNull(contentType);
    }
}
