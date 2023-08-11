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
package org.eclipse.sirius.web.services.api.representations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.components.representations.IRepresentation;

/**
 * Interface to manipulate representations.
 *
 * @author gcoutable
 */
public interface IRepresentationService extends IRepresentationMetadataSearchService {

    boolean hasRepresentations(String objectId);

    Optional<RepresentationDescriptor> getRepresentationDescriptorForProjectId(String projectId, String representationId);

    List<RepresentationDescriptor> getRepresentationDescriptorsForProjectId(String projectId);

    Optional<RepresentationDescriptor> getRepresentation(UUID representationId);

    boolean existsById(UUID representationId);

    void delete(UUID representationId);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author frouene
     */
    class NoOp implements IRepresentationService {

        @Override
        public boolean hasRepresentations(String objectId) {
            return false;
        }

        @Override
        public Optional<RepresentationDescriptor> getRepresentationDescriptorForProjectId(String projectId, String representationId) {
            return Optional.empty();
        }

        @Override
        public List<RepresentationDescriptor> getRepresentationDescriptorsForProjectId(String projectId) {
            return null;
        }

        @Override
        public Optional<RepresentationDescriptor> getRepresentation(UUID representationId) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(UUID representationId) {
            return false;
        }

        @Override
        public void delete(UUID representationId) {

        }

        @Override
        public Optional<RepresentationMetadata> findByRepresentationId(String representationId) {
            return Optional.empty();
        }

        @Override
        public Optional<RepresentationMetadata> findByRepresentation(IRepresentation representation) {
            return Optional.empty();
        }

        @Override
        public List<RepresentationMetadata> findAllByTargetObjectId(IEditingContext editingContext, String targetObjectId) {
            return null;
        }
    }

}
