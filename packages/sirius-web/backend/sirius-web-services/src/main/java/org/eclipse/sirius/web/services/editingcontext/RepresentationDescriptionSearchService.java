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
package org.eclipse.sirius.web.services.editingcontext;

import java.util.Map;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.springframework.stereotype.Service;

/**
 * Service used to search the representation descriptions available.
 *
 * @author sbegaudeau
 * @author hmarchadour
 */
@Service
public class RepresentationDescriptionSearchService implements IRepresentationDescriptionSearchService {

    @Override
    public Map<String, IRepresentationDescription> findAll(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext emfEditingContext) {
            return emfEditingContext.getRepresentationDescriptions();
        }
        return Map.of();
    }

    @Override
    public Optional<IRepresentationDescription> findById(IEditingContext editingContext, String representationDescriptionId) {
        // @formatter:off
        return Optional.of(editingContext)
                .filter(EditingContext.class::isInstance)
                .map(EditingContext.class::cast)
                .map(EditingContext::getRepresentationDescriptions)
                .map(representationDescriptions -> representationDescriptions.get(representationDescriptionId));
        // @formatter:on
    }

}
