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
package org.eclipse.sirius.web.application.omnibox.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.omnibox.dto.OmniboxCommand;
import org.eclipse.sirius.web.application.index.services.api.IIndexQueryService;
import org.eclipse.sirius.web.application.omnibox.services.api.IProjectsOmniboxCommandProvider;
import org.springframework.stereotype.Service;

/**
 * Provides the cross project search command in the omnibox.
 *
 * @author gdaniel
 */
@Service
public class ProjectsOmniboxSearchCommandProvider implements IProjectsOmniboxCommandProvider {

    public static final String SEARCH_COMMAND_ID = "search";

    private final IIndexQueryService indexQueryService;

    public ProjectsOmniboxSearchCommandProvider(IIndexQueryService indexQueryService) {
        this.indexQueryService = Objects.requireNonNull(indexQueryService);
    }

    @Override
    public List<OmniboxCommand> getCommands(String query) {
        List<OmniboxCommand> result = List.of();
        if (this.indexQueryService.isAvailable()) {
            result = List.of(new OmniboxCommand(SEARCH_COMMAND_ID, "Search across projects", List.of("/omnibox/search.svg"), "Search an element in a project"));
        }
        return result;
    }

}
