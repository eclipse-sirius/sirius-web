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
package org.eclipse.sirius.web.papaya.services.commands;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.omnibox.api.IOmniboxCommandProvider;
import org.eclipse.sirius.components.collaborative.omnibox.dto.OmniboxCommand;
import org.eclipse.sirius.components.collaborative.omnibox.dto.OmniboxContextEntry;
import org.eclipse.sirius.web.papaya.services.api.IPapayaCapableEditingContextPredicate;
import org.springframework.stereotype.Service;

/**
 * Provides the sample project creation command for Papaya projects.
 *
 * @author gdaniel
 */
@Service
public class PapayaCreateSampleProjectCommandProvider implements IOmniboxCommandProvider {

    public static final String CREATE_SAMPLE_PROJECT_COMMAND_ID = "create_papaya_sample_project";

    private final IPapayaCapableEditingContextPredicate papayaCapableEditingContextPredicate;

    public PapayaCreateSampleProjectCommandProvider(IPapayaCapableEditingContextPredicate papayaCapableEditingContextPredicate) {
        this.papayaCapableEditingContextPredicate = Objects.requireNonNull(papayaCapableEditingContextPredicate);
    }

    @Override
    public List<OmniboxCommand> getCommands(String editingContextId, List<OmniboxContextEntry> contextEntries, String query) {
        if (this.papayaCapableEditingContextPredicate.test(editingContextId)) {
            return List.of(new OmniboxCommand(CREATE_SAMPLE_PROJECT_COMMAND_ID, "Create Sample Papaya Project", List.of("/omnibox/create-sample-project.svg"), "Create a sample Papaya project"));
        }
        return List.of();
    }
}
