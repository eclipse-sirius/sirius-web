/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.diagrams;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.Immutable;

/**
 * A view deletion request.
 *
 * @author hmarchadour
 */
@Immutable
public final class ViewDeletionRequest {

    private UUID elementId;

    private ViewDeletionRequest() {
        // Prevent instantiation
    }

    public UUID getElementId() {
        return this.elementId;
    }

    public static Builder newViewDeletionRequest() {
        return new Builder();
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'elementId: {1}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.elementId);
    }

    /**
     * The builder used to create a new {@link ViewDeletionRequest}.
     *
     * @author hmarchadour
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private UUID elementId;

        private Builder() {
            // Prevent instantiation
        }

        public Builder elementId(UUID elementId) {
            this.elementId = Objects.requireNonNull(elementId);
            return this;
        }

        public ViewDeletionRequest build() {
            ViewDeletionRequest viewDeletionRequest = new ViewDeletionRequest();
            viewDeletionRequest.elementId = Objects.requireNonNull(this.elementId);
            return viewDeletionRequest;
        }
    }
}
