/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.library.services;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextPersistenceFilter;
import org.springframework.stereotype.Service;

/**
 * Used to ensure that libraries are not persisted.
 *
 * @author sbegaudeau
 */
@Service
public class LibraryEditingContextPersistenceFilter implements IEditingContextPersistenceFilter {
    @Override
    public boolean shouldPersist(Resource resource) {
        return resource.eAdapters().stream().noneMatch(LibraryMetadataAdapter.class::isInstance);
    }
}
