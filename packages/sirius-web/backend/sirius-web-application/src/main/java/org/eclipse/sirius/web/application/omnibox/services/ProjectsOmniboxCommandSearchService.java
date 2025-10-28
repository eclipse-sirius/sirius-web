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

import org.eclipse.sirius.components.collaborative.omnibox.api.IOmniboxCommandOrderer;
import org.eclipse.sirius.web.application.omnibox.services.api.IProjectsOmniboxCommandProvider;
import org.eclipse.sirius.web.application.omnibox.services.api.IProjectsOmniboxCommandSearchService;
import org.eclipse.sirius.components.collaborative.omnibox.dto.OmniboxCommand;
import org.springframework.stereotype.Service;

/**
 * Used to find projects omnibox commands.
 *
 * @author gdaniel
 */
@Service
public class ProjectsOmniboxCommandSearchService implements IProjectsOmniboxCommandSearchService {

    private final List<IProjectsOmniboxCommandProvider> projectsOmniboxCommandProviders;

    private final List<IOmniboxCommandOrderer> omniboxCommandOrderers;

    public ProjectsOmniboxCommandSearchService(List<IProjectsOmniboxCommandProvider> projectsOmniboxCommandProviders, List<IOmniboxCommandOrderer> omniboxCommandOrderers) {
        this.projectsOmniboxCommandProviders = Objects.requireNonNull(projectsOmniboxCommandProviders);
        this.omniboxCommandOrderers = Objects.requireNonNull(omniboxCommandOrderers);
    }

    @Override
    public List<OmniboxCommand> findAll(String query) {
        List<OmniboxCommand> omniboxCommands = this.projectsOmniboxCommandProviders.stream()
                .flatMap(provider -> provider.getCommands(query).stream())
                .filter(command -> command.label().toLowerCase().contains(query.toLowerCase()))
                .toList();

        for (var orderer: this.omniboxCommandOrderers) {
            omniboxCommands = orderer.order(omniboxCommands);
        }

        return omniboxCommands;
    }
}
