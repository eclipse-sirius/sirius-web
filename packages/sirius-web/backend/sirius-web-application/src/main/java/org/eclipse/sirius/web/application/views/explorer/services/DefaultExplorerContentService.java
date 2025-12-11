/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.application.views.explorer.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.web.application.views.explorer.services.api.IDefaultExplorerContentService;
import org.springframework.stereotype.Service;

/**
 * The default implementation used to compute the content of the explorer.
 *
 * @author sbegaudeau
 */
@Service
public class DefaultExplorerContentService implements IDefaultExplorerContentService {

    private final ILabelService labelService;

    public DefaultExplorerContentService(ILabelService labelService) {
        this.labelService = Objects.requireNonNull(labelService);
    }

    @Override
    public List<Object> getContents(IEditingContext editingContext) {
        var optionalResourceSet = Optional.ofNullable(editingContext)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain)
                .map(EditingDomain::getResourceSet);

        return optionalResourceSet
                .map(resourceSet -> resourceSet.getResources().stream()
                        .sorted(Comparator.nullsLast(Comparator.comparing(resource -> this.labelService.getStyledLabel(resource).toString(), String.CASE_INSENSITIVE_ORDER)))
                        .map(Object.class::cast)
                        .toList())
                .orElseGet(ArrayList::new);
    }
}
