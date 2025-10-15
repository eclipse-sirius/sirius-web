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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.omnibox.api.IOmniboxCommandOrderer;
import org.eclipse.sirius.components.collaborative.omnibox.dto.OmniboxCommand;
import org.springframework.stereotype.Service;

/**
 * Sorts the omnibox commands in Sirius Web.
 *
 * @author gdaniel
 */
@Service
public class OmniboxCommandOrderer implements IOmniboxCommandOrderer {

    @Override
    public List<OmniboxCommand> order(List<OmniboxCommand> omniboxCommands) {
        Optional<OmniboxCommand> optionalSearchCommand = omniboxCommands.stream()
                .filter(command -> Objects.equals(command.id(), WorkbenchOmniboxSearchCommandProvider.SEARCH_COMMAND_ID))
                .findFirst();

        if (optionalSearchCommand.isPresent()) {
            List<OmniboxCommand> commands = new ArrayList<>();

            var otherCommands = omniboxCommands.stream()
                    .filter(command -> !Objects.equals(command.id(), WorkbenchOmniboxSearchCommandProvider.SEARCH_COMMAND_ID))
                    .toList();
            commands.add(optionalSearchCommand.get());
            commands.addAll(otherCommands);

            return commands;
        }
        return omniboxCommands;
    }

}
