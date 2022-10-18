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
package org.eclipse.sirius.web.services.api.images;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

/**
 * Lightweight DTO representing a custom image metadata.
 *
 * @author pcdavid
 */
public class CustomImageMetadata {
    private UUID id;

    private String label;

    private String url;

    private String contentType;

    public CustomImageMetadata(UUID id, String label, String url, String contentType) {
        this.id = Objects.requireNonNull(id);
        this.label = Objects.requireNonNull(label);
        this.url = Objects.requireNonNull(url);
        this.contentType = Objects.requireNonNull(contentType);
    }

    public UUID getId() {
        return this.id;
    }

    public String getURL() {
        return this.url;
    }

    public String getLabel() {
        return this.label;
    }

    public String getContentType() {
        return this.contentType;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{' id: {1}, url: {2}, label: {3}, contentType: {4} '}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.url, this.label, this.contentType);
    }
}
