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

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.web.infrastructure.elasticsearch.services.api.IDefaultIndexUpdateService;
import org.eclipse.sirius.web.infrastructure.elasticsearch.services.api.IIndexUpdateService;
import org.eclipse.sirius.web.infrastructure.elasticsearch.services.api.IIndexUpdateServiceDelegate;
import org.springframework.stereotype.Service;

/**
 * Indexes the content of an editing context.
 *
 * @author gdaniel
 */
@Service
public class ComposedIndexUpdateService implements IIndexUpdateService {

    private final List<IIndexUpdateServiceDelegate> indexUpdateServiceDelegates;

    private final IDefaultIndexUpdateService defaultIndexUpdateService;

    public ComposedIndexUpdateService(List<IIndexUpdateServiceDelegate> indexUpdateServiceDelegates, IDefaultIndexUpdateService defaultIndexUpdateService) {
        this.indexUpdateServiceDelegates = Objects.requireNonNull(indexUpdateServiceDelegates);
        this.defaultIndexUpdateService = Objects.requireNonNull(defaultIndexUpdateService);
    }

    @Override
    public void updateIndex(IEditingContext editingContext) {
        Optional<IIndexUpdateServiceDelegate> optionalIndexUpdateServiceDelegate = this.indexUpdateServiceDelegates.stream()
                .filter(indexUpdateServiceDelegate -> indexUpdateServiceDelegate.canHandle(editingContext))
                .findFirst();
        if (optionalIndexUpdateServiceDelegate.isPresent()) {
            optionalIndexUpdateServiceDelegate.get().updateIndex(editingContext);
        } else {
            this.defaultIndexUpdateService.updateIndex(editingContext);
        }
    }
}
