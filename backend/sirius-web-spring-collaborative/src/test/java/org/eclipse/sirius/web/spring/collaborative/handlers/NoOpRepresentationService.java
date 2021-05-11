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
package org.eclipse.sirius.web.spring.collaborative.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.services.api.representations.IRepresentationService;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;

/**
 * Implementation of the representation service which does nothing.
 *
 * @author sbegaudeau
 */
public class NoOpRepresentationService implements IRepresentationService {

    @Override
    public boolean hasRepresentations(String objectId) {
        return false;
    }

    @Override
    public Optional<RepresentationDescriptor> getRepresentationDescriptorForProjectId(UUID projectId, UUID representationId) {
        return Optional.empty();
    }

    @Override
    public List<RepresentationDescriptor> getRepresentationDescriptorsForProjectId(UUID projectId) {
        return new ArrayList<>();
    }

    @Override
    public List<RepresentationDescriptor> getRepresentationDescriptorsForObjectId(String objectId) {
        return new ArrayList<>();
    }

    @Override
    public void save(RepresentationDescriptor representationDescriptor) {
    }

    @Override
    public Optional<RepresentationDescriptor> getRepresentation(UUID representationId) {
        return null;
    }

    @Override
    public void delete(UUID representationId) {
    }

    @Override
    public void deleteDanglingRepresentations(UUID editingContextId) {
    }
}
