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
package org.eclipse.sirius.web.application.studio.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.omnibox.api.IWorkbenchOmniboxCommandProvider;
import org.eclipse.sirius.components.collaborative.omnibox.dto.OmniboxCommand;
import org.eclipse.sirius.web.application.studio.services.api.IStudioCapableEditingContextPredicate;
import org.springframework.stereotype.Service;

/**
 * Provides the import studio command in the omnibox.
 *
 * @author gdaniel
 */
@Service
public class StudioImportLibraryCommandProvider implements IWorkbenchOmniboxCommandProvider {

    public static final String IMPORT_LIBRARY_COMMAND_ID = "importLibrary";

    private final IStudioCapableEditingContextPredicate studioCapableEditingContextPredicate;

    public StudioImportLibraryCommandProvider(IStudioCapableEditingContextPredicate studioCapableEditingContextPredicate) {
        this.studioCapableEditingContextPredicate = Objects.requireNonNull(studioCapableEditingContextPredicate);
    }

    @Override
    public List<OmniboxCommand> getCommands(String editingContextId, List<String> selectedObjectIds, String query) {
        List<OmniboxCommand> result = new ArrayList<>();
        if (this.studioCapableEditingContextPredicate.test(editingContextId)) {
            result.add(new OmniboxCommand(IMPORT_LIBRARY_COMMAND_ID, "Import studio libraries", List.of("/omnibox/import.svg"), "Import studio libraries in the project"));
        }
        return result;
    }

}
