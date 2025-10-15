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
package org.eclipse.sirius.components.collaborative.omnibox;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.omnibox.api.IOmniboxCommandOrderer;
import org.eclipse.sirius.components.collaborative.omnibox.api.IWorkbenchOmniboxCommandProvider;
import org.eclipse.sirius.components.collaborative.omnibox.api.IWorkbenchOmniboxCommandSearchService;
import org.eclipse.sirius.components.collaborative.omnibox.dto.OmniboxCommand;
import org.springframework.stereotype.Service;

/**
 * Used to find workbench omnibox commands.
 *
 * @author sbegaudeau
 */
@Service
public class WorkbenchOmniboxCommandSearchService implements IWorkbenchOmniboxCommandSearchService {

    private final List<IWorkbenchOmniboxCommandProvider> workbenchOmniboxCommandProviders;

    private final List<IOmniboxCommandOrderer> omniboxCommandOrderers;

    public WorkbenchOmniboxCommandSearchService(List<IWorkbenchOmniboxCommandProvider> workbenchOmniboxCommandProviders, List<IOmniboxCommandOrderer> omniboxCommandOrderers) {
        this.workbenchOmniboxCommandProviders = Objects.requireNonNull(workbenchOmniboxCommandProviders);
        this.omniboxCommandOrderers = Objects.requireNonNull(omniboxCommandOrderers);
    }

    @Override
    public List<OmniboxCommand> findAll(String editingContextId, List<String> selectedObjectIds, String query) {
        List<OmniboxCommand> omniboxCommands = this.workbenchOmniboxCommandProviders.stream()
                .flatMap(provider -> provider.getCommands(editingContextId, selectedObjectIds, query).stream())
                .filter(command -> command.label().toLowerCase().contains(query.toLowerCase()))
                .toList();

        for (var orderer: this.omniboxCommandOrderers) {
            omniboxCommands = orderer.order(omniboxCommands);
        }

        return omniboxCommands;
    }
}
