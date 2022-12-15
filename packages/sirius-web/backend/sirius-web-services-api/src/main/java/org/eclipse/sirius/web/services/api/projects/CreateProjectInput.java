/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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

import org.eclipse.sirius.components.core.api.IInput;

/**
 * The input object of the create project mutation.
 *
 * @author wpiers
 */
public final class CreateProjectInput implements IInput {

    private UUID id;

    private String name;

    private Visibility visibility;

    public CreateProjectInput() {
        // Used by Jackson
    }

    public CreateProjectInput(UUID id, String name, Visibility visibility) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.visibility = visibility;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Visibility getVisibility() {
        return this.visibility;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, name: {2}, visibility: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.name, this.visibility);
    }

}
