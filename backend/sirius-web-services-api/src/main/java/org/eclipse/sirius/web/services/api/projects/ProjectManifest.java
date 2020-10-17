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

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.web.annotations.Immutable;

/**
 * Represents the manifest of the project.
 *
 * @author gcoutable
 */
@Immutable
public final class ProjectManifest {
    private String manifestVersion;

    private String siriusWebVersion;

    private List<String> metamodels;

    private Map<String, String> documentIdsToName;

    private Map<String, RepresentationManifest> representations;

    private ProjectManifest() {
        // Prevent instantiation
    }

    public String getManifestVersion() {
        return this.manifestVersion;
    }

    public String getSiriusWebVersion() {
        return this.siriusWebVersion;
    }

    public List<String> getMetamodels() {
        return this.metamodels;
    }

    public Map<String, String> getDocumentIdsToName() {
        return this.documentIdsToName;
    }

    public Map<String, RepresentationManifest> getRepresentations() {
        return this.representations;
    }

    public static Builder newProjectManifest(String manifestVersion, String siriusWebVersion) {
        return new Builder(manifestVersion, siriusWebVersion);
    }

    /**
     * The builder for the {@link ProjectManifest} class.
     *
     * @author gcoutable
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String manifestVersion;

        private String siriusWebVersion;

        private List<String> metamodels;

        private Map<String, String> documentIdsToName;

        private Map<String, RepresentationManifest> representations;

        private Builder(String manifestVersion, String siriusWebVersion) {
            this.manifestVersion = Objects.requireNonNull(manifestVersion);
            this.siriusWebVersion = Objects.requireNonNull(siriusWebVersion);
        }

        public Builder metamodels(List<String> metamodels) {
            this.metamodels = Objects.requireNonNull(metamodels);
            return this;
        }

        public Builder documentIdsToName(Map<String, String> documentIdsToName) {
            this.documentIdsToName = Objects.requireNonNull(documentIdsToName);
            return this;
        }

        public Builder representations(Map<String, RepresentationManifest> representations) {
            this.representations = Objects.requireNonNull(representations);
            return this;
        }

        public ProjectManifest build() {
            ProjectManifest projectManifest = new ProjectManifest();
            projectManifest.manifestVersion = Objects.requireNonNull(this.manifestVersion);
            projectManifest.siriusWebVersion = Objects.requireNonNull(this.siriusWebVersion);
            projectManifest.metamodels = Objects.requireNonNull(this.metamodels);
            projectManifest.documentIdsToName = Objects.requireNonNull(this.documentIdsToName);
            projectManifest.representations = Objects.requireNonNull(this.representations);
            return projectManifest;
        }
    }

}
