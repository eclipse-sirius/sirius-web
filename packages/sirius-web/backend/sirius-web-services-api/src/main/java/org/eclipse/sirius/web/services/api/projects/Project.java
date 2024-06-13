/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.web.services.api.projects;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * DTO representing a project.
 *
 * @author sbegaudeau
 */
public class Project {
    private final UUID id;

    private final String name;

    private final List<Nature> natures;

    public Project(UUID id, String name, List<Nature> natures) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.natures = Objects.requireNonNull(natures);
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public List<Nature> getNatures() {
        return this.natures;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, name: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.name);
    }
}
