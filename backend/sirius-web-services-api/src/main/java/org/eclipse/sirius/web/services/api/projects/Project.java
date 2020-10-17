/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.services.api.accounts.Profile;

/**
 * DTO representing a project.
 *
 * @author sbegaudeau
 */
public class Project {
    private final UUID id;

    private final String name;

    private final Profile owner;

    private final Visibility visibility;

    public Project(UUID id, String name, Profile owner, Visibility visibility) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.owner = Objects.requireNonNull(owner);
        this.visibility = visibility;
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Profile getOwner() {
        return this.owner;
    }

    public Visibility getVisibility() {
        return this.visibility;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, name: {2}, owner: {3}, visibility: {4}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.name, this.owner, this.visibility);
    }
}
