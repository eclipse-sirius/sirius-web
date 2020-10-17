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

import java.util.Objects;

import org.eclipse.sirius.web.annotations.Immutable;

/**
 * Class used to hold meta data for representation for project export.
 *
 * @author gcoutable
 */
@Immutable
public final class RepresentationManifest {

    private String type;

    private String descriptionURI;

    private String targetObjectURI;

    private RepresentationManifest() {
        // Prevent instantiation
    }

    public static Builder newRepresentationManifest() {
        return new Builder();
    }

    public String getType() {
        return this.type;
    }

    public String getDescriptionURI() {
        return this.descriptionURI;
    }

    public String getTargetObjectURI() {
        return this.targetObjectURI;
    }

    /**
     * The builder for the {@link RepresentationManifest} class.
     *
     * @author gcoutable
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String type;

        private String descriptionURI;

        private String targetObjectURI;

        private Builder() {
            // Prevent instantiation
        }

        public Builder type(String type) {
            this.type = Objects.requireNonNull(type);
            return this;
        }

        public Builder descriptionURI(String descriptionURI) {
            this.descriptionURI = Objects.requireNonNull(descriptionURI);
            return this;
        }

        public Builder targetObjectURI(String targetObjectURI) {
            this.targetObjectURI = Objects.requireNonNull(targetObjectURI);
            return this;
        }

        public RepresentationManifest build() {
            RepresentationManifest representationManifest = new RepresentationManifest();
            representationManifest.type = Objects.requireNonNull(this.type);
            representationManifest.descriptionURI = Objects.requireNonNull(this.descriptionURI);
            representationManifest.targetObjectURI = Objects.requireNonNull(this.targetObjectURI);
            return representationManifest;
        }
    }
}
