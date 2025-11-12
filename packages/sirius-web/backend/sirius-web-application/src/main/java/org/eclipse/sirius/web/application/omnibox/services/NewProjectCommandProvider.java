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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sirius.components.collaborative.omnibox.dto.OmniboxCommand;
import org.eclipse.sirius.web.application.omnibox.services.api.IProjectsOmniboxCommandProvider;
import org.springframework.stereotype.Service;

/**
 * Provides the new project command in the omnibox.
 *
 * @author gdaniel
 */
@Service
public class NewProjectCommandProvider implements IProjectsOmniboxCommandProvider {

    public static final String NEW_PROJECT_COMMAND_ID = "newProject";

    @Override
    public List<OmniboxCommand> getCommands(String query) {
        List<OmniboxCommand> result = new ArrayList<>();
        result.add(new OmniboxCommand(NEW_PROJECT_COMMAND_ID, "Blank project", List.of("/omnibox/new-project.svg"), "Create a blank project"));
        return result;
    }

}
