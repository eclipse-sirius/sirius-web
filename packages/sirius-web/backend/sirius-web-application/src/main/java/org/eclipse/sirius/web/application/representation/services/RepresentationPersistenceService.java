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

import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.ISemanticRepresentation;
import org.springframework.stereotype.Service;

/**
 * Used to persist representations.
 *
 * @author sbegaudeau
 */
@Service
public class RepresentationPersistenceService implements IRepresentationPersistenceService {
    @Override
    public void save(IEditingContext editingContext, ISemanticRepresentation representation) {
        // Do nothing for now
    }
}
