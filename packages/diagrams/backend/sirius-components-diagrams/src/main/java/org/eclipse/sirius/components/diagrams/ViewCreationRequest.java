/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.components.NodeContainmentKind;

/**
 * A view creation request.
 *
 * @author hmarchadour
 */
@Immutable
public final class ViewCreationRequest {

    private String parentElementId;

    private UUID descriptionId;

    private String targetObjectId;

    private NodeContainmentKind containmentKind;

    private ViewCreationRequest() {
        // Prevent instantiation
    }

    /**
     * Specify the identifier of the parent view.
     *
     * @return the diagram element identifier
     */
    public String getParentElementId() {
        return this.parentElementId;
    }

    /**
     * Specify the description of the requested view.
     *
     * @return the description identifier
     */
    public UUID getDescriptionId() {
        return this.descriptionId;
    }

    /**
     * Specify the underlying semantic object.
     *
     * @return the target object identifier
     */
    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    /**
     * Specify the containment of the node to create.
     *
     * @return The containment kind to use
     */
    public NodeContainmentKind getContainmentKind() {
        return this.containmentKind;
    }

    public static Builder newViewCreationRequest() {
        return new Builder();
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'parentElementId: {1}, descriptionId: {2}, targetObjectId: {3}, containmentKind: {4}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.parentElementId, this.descriptionId, this.targetObjectId, this.containmentKind);
    }

    /**
     * The builder used to create a new {@link ViewCreationRequest}.
     *
     * @author hmarchadour
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String parentElementId;

        private UUID descriptionId;

        private String targetObjectId;

        private NodeContainmentKind containmentKind;

        private Builder() {
            // Prevent instantiation
        }

        public Builder parentElementId(String parentElementId) {
            this.parentElementId = Objects.requireNonNull(parentElementId);
            return this;
        }

        public Builder descriptionId(UUID descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder targetObjectId(String targetObjectId) {
            this.targetObjectId = Objects.requireNonNull(targetObjectId);
            return this;
        }

        public Builder containmentKind(NodeContainmentKind containmentKind) {
            this.containmentKind = Objects.requireNonNull(containmentKind);
            return this;
        }


        public ViewCreationRequest build() {
            ViewCreationRequest viewCreationRequest = new ViewCreationRequest();
            viewCreationRequest.parentElementId = Objects.requireNonNull(this.parentElementId);
            viewCreationRequest.descriptionId = Objects.requireNonNull(this.descriptionId);
            viewCreationRequest.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            viewCreationRequest.containmentKind = Objects.requireNonNull(this.containmentKind);
            return viewCreationRequest;
        }
    }
}
