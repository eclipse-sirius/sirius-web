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
import org.eclipse.sirius.components.collaborative.omnibox.api.IOmniboxCommandProvider;
import org.eclipse.sirius.components.collaborative.omnibox.api.IOmniboxCommandSeachService;
import org.eclipse.sirius.components.collaborative.omnibox.dto.OmniboxCommand;
import org.springframework.stereotype.Service;

/**
 * Used to find omnibox commands.
 *
 * @author sbegaudeau
 */
@Service
public class OmniboxCommandSearchService implements IOmniboxCommandSeachService {

    private final List<IOmniboxCommandProvider> omniboxCommandProviders;

    private final List<IOmniboxCommandOrderer> omniboxCommandOrderers;

    public OmniboxCommandSearchService(List<IOmniboxCommandProvider> omniboxCommandProviders, List<IOmniboxCommandOrderer> omniboxCommandOrderers) {
        this.omniboxCommandProviders = Objects.requireNonNull(omniboxCommandProviders);
        this.omniboxCommandOrderers = Objects.requireNonNull(omniboxCommandOrderers);
    }

    @Override
    public List<OmniboxCommand> findAll(String editingContextId, String query) {
        List<OmniboxCommand> omniboxCommands = this.omniboxCommandProviders.stream()
                .flatMap(provider -> provider.getCommands(editingContextId, query).stream())
                .filter(command -> command.label().toLowerCase().contains(query.toLowerCase()))
                .toList();

        for (var orderer: this.omniboxCommandOrderers) {
            omniboxCommands = orderer.order(omniboxCommands);
        }

        return omniboxCommands;
    }
}
