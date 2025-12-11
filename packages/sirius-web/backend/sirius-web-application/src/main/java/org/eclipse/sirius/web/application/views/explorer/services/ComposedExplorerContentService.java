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

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.web.application.views.explorer.services.api.IDefaultExplorerContentService;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerContentService;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerContentServiceDelegate;
import org.springframework.stereotype.Service;

/**
 * Used to compute the content of the explorer.
 *
 * @author sbegaudeau
 */
@Service
public class ComposedExplorerContentService implements IExplorerContentService {

    private final IDefaultExplorerContentService defaultExplorerContentService;

    private final List<IExplorerContentServiceDelegate> explorerContentServiceDelegates;

    public ComposedExplorerContentService(IDefaultExplorerContentService defaultExplorerContentService, List<IExplorerContentServiceDelegate> explorerContentServiceDelegates) {
        this.defaultExplorerContentService = Objects.requireNonNull(defaultExplorerContentService);
        this.explorerContentServiceDelegates = Objects.requireNonNull(explorerContentServiceDelegates);
    }

    @Override
    public List<Object> getContents(IEditingContext editingContext) {
        return this.explorerContentServiceDelegates.stream()
                .filter(delegate -> delegate.canHandle(editingContext))
                .findFirst()
                .map(delegate -> delegate.getContents(editingContext))
                .orElseGet(() -> this.defaultExplorerContentService.getContents(editingContext));
    }
}
