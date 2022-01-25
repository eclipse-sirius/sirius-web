/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.collaborative.dto;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.IRepresentationDescription;

/**
 * The payload object for this query.
 *
 * @author pcdavid
 */
public final class EditingContextRepresentationDescriptionsPayload implements IPayload {
    private final UUID id;

    private final List<IRepresentationDescription> representationDescriptions;

    public EditingContextRepresentationDescriptionsPayload(UUID id, List<IRepresentationDescription> representationDescriptions) {
        this.id = Objects.requireNonNull(id);
        this.representationDescriptions = Objects.requireNonNull(representationDescriptions);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public List<IRepresentationDescription> getRepresentationDescriptions() {
        return this.representationDescriptions;
    }

}
