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
package org.eclipse.sirius.components.forms;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * A page of the form representation.
 *
 * @author sbegaudeau
 */
@Immutable
public final class Page {
    private String id;

    private String label;

    private List<Group> groups;

    private Page() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public List<Group> getGroups() {
        return this.groups;
    }

    public static Builder newPage(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, groupCount: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.groups.size());
    }

    /**
     * The builder used to create a new page.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private List<Group> groups;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder groups(List<Group> groups) {
            this.groups = Objects.requireNonNull(groups);
            return this;
        }

        public Page build() {
            Page page = new Page();
            page.id = Objects.requireNonNull(this.id);
            page.label = Objects.requireNonNull(this.label);
            page.groups = Objects.requireNonNull(this.groups);
            return page;
        }
    }
}
