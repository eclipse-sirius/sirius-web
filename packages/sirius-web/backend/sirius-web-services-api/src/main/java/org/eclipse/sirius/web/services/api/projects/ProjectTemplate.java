/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * DTO representing a project template.
 *
 * @author pcdavid
 */
@Immutable
public final class ProjectTemplate {
    private String id;

    private String label;

    private String imageURL;

    private List<Nature> natures;

    private ProjectTemplate() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public List<Nature> getNatures() {
        return this.natures;
    }

    public static Builder newProjectTemplate(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, imageURL: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.imageURL);

    }

    /**
     * The builder used to create a ProjectTemplate.
     *
     * @author pcdavod
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private String imageURL;

        private List<Nature> natures;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder imageURL(String imageURL) {
            this.imageURL = Objects.requireNonNull(imageURL);
            return this;
        }

        public Builder natures(List<Nature> natures) {
            this.natures = Objects.requireNonNull(natures);
            return this;
        }

        public ProjectTemplate build() {
            ProjectTemplate projectTemplate = new ProjectTemplate();
            projectTemplate.id = Objects.requireNonNull(this.id);
            projectTemplate.label = Objects.requireNonNull(this.label);
            projectTemplate.natures = Objects.requireNonNull(this.natures);
            projectTemplate.imageURL = this.imageURL;
            return projectTemplate;
        }
    }
}
