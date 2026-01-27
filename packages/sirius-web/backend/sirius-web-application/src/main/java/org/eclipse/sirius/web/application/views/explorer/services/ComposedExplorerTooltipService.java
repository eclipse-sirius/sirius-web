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

import org.eclipse.sirius.web.application.views.explorer.services.api.IDefaultExplorerTooltipService;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerTooltipService;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerTooltipServiceDelegate;
import org.springframework.stereotype.Service;

/**
 * Used to manipulate tooltips in the explorer.
 *
 * @author sbegaudeau
 */
@Service
public class ComposedExplorerTooltipService implements IExplorerTooltipService {

    private final IDefaultExplorerTooltipService defaultExplorerTooltipService;

    private final List<IExplorerTooltipServiceDelegate> explorerTooltipServiceDelegates;

    public ComposedExplorerTooltipService(IDefaultExplorerTooltipService defaultExplorerTooltipService, List<IExplorerTooltipServiceDelegate> explorerTooltipServiceDelegates) {
        this.defaultExplorerTooltipService = Objects.requireNonNull(defaultExplorerTooltipService);
        this.explorerTooltipServiceDelegates = Objects.requireNonNull(explorerTooltipServiceDelegates);
    }

    @Override
    public String getTooltip(Object object) {
        return this.explorerTooltipServiceDelegates.stream()
                .filter(delegate -> delegate.canHandle(object))
                .findFirst()
                .map(delegate -> delegate.getTooltip(object))
                .orElseGet(() -> this.defaultExplorerTooltipService.getTooltip(object));
    }
}
