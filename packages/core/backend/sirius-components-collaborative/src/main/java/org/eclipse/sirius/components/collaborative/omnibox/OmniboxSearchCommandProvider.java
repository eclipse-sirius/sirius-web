/*******************************************************************************
 * Copyright (c) 2025, 2025 Obeo.
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

import org.eclipse.sirius.components.collaborative.omnibox.api.IOmniboxCommandProvider;
import org.eclipse.sirius.components.collaborative.omnibox.dto.OmniboxCommand;
import org.springframework.stereotype.Service;

/**
 * Provides the search command in the omnibox.
 *
 * @author gdaniel
 */
@Service
public class OmniboxSearchCommandProvider implements IOmniboxCommandProvider {

    public static final String SEARCH_COMMAND_ID = "search";

    @Override
    public List<OmniboxCommand> getCommands(String editingContextId, String query) {
        return List.of(new OmniboxCommand(SEARCH_COMMAND_ID, "Search", List.of("/omnibox/search.svg"), "Search an element in the project"));
    }

}
