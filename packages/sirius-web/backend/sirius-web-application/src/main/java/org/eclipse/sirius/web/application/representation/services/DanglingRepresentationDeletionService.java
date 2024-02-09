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

import org.eclipse.sirius.components.collaborative.api.IDanglingRepresentationDeletionService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.springframework.stereotype.Service;

/**
 * Used to delete dangling representations.
 *
 * @author sbegaudeau
 */
@Service
public class DanglingRepresentationDeletionService implements IDanglingRepresentationDeletionService {
    @Override
    public boolean isDangling(IEditingContext editingContext, IRepresentation representation) {
        return false;
    }

    @Override
    public void deleteDanglingRepresentations(String editingContextId) {
        // Do nothing for now
    }
}
