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
package org.eclipse.sirius.web.infrastructure.elasticsearch.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.infrastructure.elasticsearch.services.api.IDefaultIndexCreationService;
import org.eclipse.sirius.web.infrastructure.elasticsearch.services.api.IIndexCreationService;
import org.eclipse.sirius.web.infrastructure.elasticsearch.services.api.IIndexCreationServiceDelegate;
import org.springframework.stereotype.Service;

/**
 * Creates the index for an editing context.
 *
 * @author gdaniel
 */
@Service
public class ComposedIndexCreationService implements IIndexCreationService {

    private final List<IIndexCreationServiceDelegate> indexCreationServiceDelegates;

    private final IDefaultIndexCreationService defaultIndexCreationService;

    public ComposedIndexCreationService(List<IIndexCreationServiceDelegate> indexCreationServiceDelegates, IDefaultIndexCreationService defaultIndexCreationService) {
        this.indexCreationServiceDelegates = Objects.requireNonNull(indexCreationServiceDelegates);
        this.defaultIndexCreationService = Objects.requireNonNull(defaultIndexCreationService);
    }

    @Override
    public boolean createIndex(String editingContextId) {
        Optional<IIndexCreationServiceDelegate> optionalIndexCreationServiceDelegate = this.indexCreationServiceDelegates.stream()
                .filter(indexCreationServiceDelegate -> indexCreationServiceDelegate.canHandle(editingContextId))
                .findFirst();
        if (optionalIndexCreationServiceDelegate.isPresent()) {
            return optionalIndexCreationServiceDelegate.get().createIndex(editingContextId);
        } else {
            return this.defaultIndexCreationService.createIndex(editingContextId);
        }
    }
}
