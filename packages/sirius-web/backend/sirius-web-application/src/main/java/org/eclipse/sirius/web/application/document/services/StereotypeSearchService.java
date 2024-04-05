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
package org.eclipse.sirius.web.application.document.services;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.web.application.document.dto.Stereotype;
import org.eclipse.sirius.web.application.document.services.api.IStereotypeProvider;
import org.eclipse.sirius.web.application.document.services.api.IStereotypeSearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Used to find the stereotypes used to create documents.
 *
 * @author sbegaudeau
 */
@Service
public class StereotypeSearchService implements IStereotypeSearchService {

    private final List<IStereotypeProvider> stereotypeProviders;

    public StereotypeSearchService(List<IStereotypeProvider> stereotypeProviders) {
        this.stereotypeProviders = Objects.requireNonNull(stereotypeProviders);
    }

    @Override
    public Page<Stereotype> findAll(IEditingContext editingContext, Pageable pageable) {
        var stereotypes = this.stereotypeProviders.stream()
                .map(provider -> provider.getStereotypes(editingContext))
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(Stereotype::label))
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), stereotypes.size());

        var stereotypePageContent = stereotypes.subList(start, end);
        return new PageImpl<>(stereotypePageContent, pageable, stereotypes.size());
    }
}
