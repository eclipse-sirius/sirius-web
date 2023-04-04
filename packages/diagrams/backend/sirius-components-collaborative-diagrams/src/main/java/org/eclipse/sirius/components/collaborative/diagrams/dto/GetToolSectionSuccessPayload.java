/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;


/**
 * The payload of the "Get Tool Sections" query returned on success.
 *
 * @author arichard
 */
public final class GetToolSectionSuccessPayload implements IPayload {
    private final UUID id;

    private final List<ToolSection> toolSections;

    public GetToolSectionSuccessPayload(UUID id, List<ToolSection> toolSections) {
        this.id = Objects.requireNonNull(id);
        this.toolSections = Objects.requireNonNull(toolSections);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public List<ToolSection> getToolSections() {
        return this.toolSections;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName());
    }
}
