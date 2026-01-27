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
package org.eclipse.sirius.web.application.studio.services.representations;

import java.util.Objects;

import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerTooltipService;
import org.springframework.stereotype.Service;

/**
 * Used to compute the tooltip of the domain explorer.
 *
 * @author sbegaudeau
 */
@Service
public class DomainExplorerTooltipServices {

    private final IExplorerTooltipService explorerTooltipService;

    public DomainExplorerTooltipServices(IExplorerTooltipService explorerTooltipService) {
        this.explorerTooltipService = Objects.requireNonNull(explorerTooltipService);
    }

    public String getTreeItemTooltip(Object self) {
        return this.explorerTooltipService.getTooltip(self);
    }
}
