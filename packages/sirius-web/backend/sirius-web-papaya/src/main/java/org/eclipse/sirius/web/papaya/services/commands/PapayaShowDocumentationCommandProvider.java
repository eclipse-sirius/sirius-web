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
 * Provides the show documentation command for Papaya projects.
 *
 * @author gdaniel
 */
@Service
public class PapayaShowDocumentationCommandProvider implements IOmniboxCommandProvider {

    private final IPapayaCapableEditingContextPredicate papayaCapableEditingContextPredicate;

    public PapayaShowDocumentationCommandProvider(IPapayaCapableEditingContextPredicate papayaCapableEditingContextPredicate) {
        this.papayaCapableEditingContextPredicate = Objects.requireNonNull(papayaCapableEditingContextPredicate);
    }

    @Override
    public List<OmniboxCommand> getCommands(String editingContextId, List<OmniboxContextEntry> contextEntries, String query) {
        List<OmniboxCommand> result = List.of();
        if (this.papayaCapableEditingContextPredicate.test(editingContextId)) {
            result = List.of(new OmniboxCommand("showDocumentation", "Show documentation", List.of("/omnibox/show-documentation.svg"), "Navigate to Sirius Web's documentation"));
        }
        return result;
    }

}
