/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.projects.stylecustomizations.domain;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

/**
 * The aggregate root of the project style customization bounded context.
 *
 * @author gcoutable
 */
public class ProjectStyleCustomization {

    private UUID id;

    private AggregateReference<Project, String> project;

    private String styleCustomizationDescriptionId;

    private Instant createdOn;

    public UUID getId() {
        return this.id;
    }

    public AggregateReference<Project, String> getProject() {
        return this.project;
    }

    public String getStyleCustomizationDescriptionId() {
        return this.styleCustomizationDescriptionId;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public static Builder newProjectStyleCustomization() {
        return new Builder();
    }

    /**
     * Used to create new project style customizations.
     *
     * @author gcoutable
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private AggregateReference<Project, String> project;

        private String styleCustomizationDescriptionId;

        public Builder project(AggregateReference<Project, String> project) {
            this.project = Objects.requireNonNull(project);
            return this;
        }

        public Builder styleCustomizationDescriptionId(String styleCustomizationDescriptionId) {
            this.styleCustomizationDescriptionId = Objects.requireNonNull(styleCustomizationDescriptionId);
            return this;
        }

        public ProjectStyleCustomization build(ICause cause) {
            var projectStyleCustomization = new ProjectStyleCustomization();

            projectStyleCustomization.id = UUID.randomUUID();
            projectStyleCustomization.project = Objects.requireNonNull(this.project);
            projectStyleCustomization.styleCustomizationDescriptionId = Objects.requireNonNull(this.styleCustomizationDescriptionId);
            projectStyleCustomization.createdOn = Instant.now();

            return projectStyleCustomization;
        }
    }
}
