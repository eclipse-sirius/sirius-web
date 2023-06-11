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

import java.util.Optional;

import org.eclipse.sirius.components.core.api.IDynamicDialogDescription;
import org.eclipse.sirius.components.core.api.IDynamicDialogDescriptionSearchService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.services.EditingContextWithDynamicDialogDescription;
import org.springframework.stereotype.Service;

/**
 * Service used to search the representation descriptions available.
 *
 * @author sbegaudeau
 * @author hmarchadour
 */
@Service
public class DynamicDialogDescriptionSearchService implements IDynamicDialogDescriptionSearchService {

    @Override
    public Optional<IDynamicDialogDescription> findById(IEditingContext editingContext, String dynamicDialogDescriptionId) {
        // @formatter:off
        return Optional.of(editingContext)
                .filter(EditingContextWithDynamicDialogDescription.class::isInstance)
                .map(EditingContextWithDynamicDialogDescription.class::cast)
                .map(EditingContextWithDynamicDialogDescription::getDynamicDialogDescriptions)
                .map(dynamicDialogDescriptions -> dynamicDialogDescriptions.get(dynamicDialogDescriptionId));
        // @formatter:on
    }
}
