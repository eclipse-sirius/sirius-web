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
package org.eclipse.sirius.components.view.emf;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Lightweight DTO representing a custom image metadata.
 *
 * @author pcdavid
 */
public final class CustomImageMetadata {
    private UUID id;

    private Optional<String> projectId;

    private String label;

    private String contentType;

    public CustomImageMetadata(UUID id, Optional<String> projectId, String label, String contentType) {
        this.id = Objects.requireNonNull(id);
        this.projectId = Objects.requireNonNull(projectId);
        this.label = Objects.requireNonNull(label);
        this.contentType = Objects.requireNonNull(contentType);
    }

    public UUID getId() {
        return this.id;
    }

    public Optional<String> getProjectId() {
        return this.projectId;
    }

    public String getLabel() {
        return this.label;
    }

    public String getContentType() {
        return this.contentType;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{' id: {1}, projectId: {2}, label: {3}, contentType: {4} '}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.projectId.orElse("none"), this.label, this.contentType); //$NON-NLS-1$
    }
}
