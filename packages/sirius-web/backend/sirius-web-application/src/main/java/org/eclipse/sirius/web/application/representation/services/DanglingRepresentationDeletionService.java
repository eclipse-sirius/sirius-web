/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.application.representation.services;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IDanglingRepresentationDeletionService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.springframework.stereotype.Service;

/**
 * Used to delete dangling representations.
 *
 * @author sbegaudeau
 */
@Service
public class DanglingRepresentationDeletionService implements IDanglingRepresentationDeletionService {

    private final IObjectSearchService objectSearchService;

    public DanglingRepresentationDeletionService(IObjectSearchService objectSearchService) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
    }

    @Override
    public boolean isDangling(IEditingContext editingContext, IRepresentation representation) {
        String targetObjectId = representation.getTargetObjectId();
        Optional<Object> optionalObject = this.objectSearchService.getObject(editingContext, targetObjectId);
        return optionalObject.isEmpty();
    }

    @Override
    public void deleteDanglingRepresentations(String editingContextId) {
        // Do nothing for now
    }
}
