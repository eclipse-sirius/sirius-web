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
package org.eclipse.sirius.web.application.studio.services;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.view.util.services.ColorPaletteService;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextPersistenceFilter;
import org.springframework.stereotype.Service;

/**
 * Used to ensure that the default palette is never persisted.
 *
 * @author sbegaudeau
 */
@Service
public class StudioEditingContextPersistenceFilter implements IEditingContextPersistenceFilter {
    @Override
    public boolean shouldPersist(Resource resource) {
        return !ColorPaletteService.SIRIUS_STUDIO_COLOR_PALETTES_URI.equals(resource.getURI().toString());
    }
}
