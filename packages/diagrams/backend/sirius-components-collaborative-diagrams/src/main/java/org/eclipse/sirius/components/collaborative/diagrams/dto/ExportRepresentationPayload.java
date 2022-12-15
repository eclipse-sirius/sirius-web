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
package org.eclipse.sirius.components.collaborative.diagrams.dto;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;

/**
 * The payload of the export representation handler.
 *
 * @author rpage
 */
public final class ExportRepresentationPayload implements IPayload {
    private final UUID id;

    private final String name;

    private final String content;

    public ExportRepresentationPayload(UUID id, String name, String svgExport) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.content = Objects.requireNonNull(svgExport);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getContent() {
        return this.content;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, name: {2}, content: {3} '}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.name, this.content);
    }
}
