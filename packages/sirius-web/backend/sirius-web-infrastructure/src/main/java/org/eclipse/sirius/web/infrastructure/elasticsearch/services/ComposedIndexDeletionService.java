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

import org.eclipse.sirius.web.infrastructure.elasticsearch.services.api.IDefaultIndexDeletionService;
import org.eclipse.sirius.web.infrastructure.elasticsearch.services.api.IIndexDeletionService;
import org.eclipse.sirius.web.infrastructure.elasticsearch.services.api.IIndexDeletionServiceDelegate;
import org.springframework.stereotype.Service;

/**
 * Deletes the index for an editing context.
 *
 * @author gdaniel
 */
@Service
public class ComposedIndexDeletionService implements IIndexDeletionService {

    private final List<IIndexDeletionServiceDelegate> indexDeletionServiceDelegates;

    private final IDefaultIndexDeletionService defaultIndexDeletionService;

    public ComposedIndexDeletionService(List<IIndexDeletionServiceDelegate> indexDeletionServiceDelegates, IDefaultIndexDeletionService defaultIndexDeletionService) {
        this.indexDeletionServiceDelegates = Objects.requireNonNull(indexDeletionServiceDelegates);
        this.defaultIndexDeletionService = Objects.requireNonNull(defaultIndexDeletionService);
    }

    @Override
    public boolean deleteIndex(String editingContextId) {
        Optional<IIndexDeletionServiceDelegate> optionalIndexDeletionServiceDelegate = this.indexDeletionServiceDelegates.stream()
                .filter(indexDeletionServiceDelegate -> indexDeletionServiceDelegate.canHandle(editingContextId))
                .findFirst();
        if (optionalIndexDeletionServiceDelegate.isPresent()) {
            return optionalIndexDeletionServiceDelegate.get().deleteIndex(editingContextId);
        } else {
            return this.defaultIndexDeletionService.deleteIndex(editingContextId);
        }
    }
}
