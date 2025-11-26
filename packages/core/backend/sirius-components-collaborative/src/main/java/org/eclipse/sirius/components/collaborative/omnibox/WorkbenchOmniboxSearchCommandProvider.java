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

import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.collaborative.omnibox.api.IWorkbenchOmniboxCommandProvider;
import org.eclipse.sirius.components.collaborative.omnibox.dto.OmniboxCommand;
import org.springframework.stereotype.Service;

/**
 * Provides the workbench search command in the omnibox.
 *
 * @author gdaniel
 */
@Service
public class WorkbenchOmniboxSearchCommandProvider implements IWorkbenchOmniboxCommandProvider {

    public static final String SEARCH_COMMAND_ID = "search";

    private final ICollaborativeMessageService collaborativeMessageService;

    public WorkbenchOmniboxSearchCommandProvider(ICollaborativeMessageService collaborativeMessageService) {
        this.collaborativeMessageService = Objects.requireNonNull(collaborativeMessageService);
    }

    @Override
    public List<OmniboxCommand> getCommands(String editingContextId, List<String> selectedObjectIds, String query) {
        return List.of(new OmniboxCommand(SEARCH_COMMAND_ID, this.collaborativeMessageService.searchCommandName(), List.of("/omnibox/search.svg"), this.collaborativeMessageService.searchCommandDescription()));
    }

}
