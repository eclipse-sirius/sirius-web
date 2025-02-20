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

import org.eclipse.sirius.components.collaborative.omnibox.api.IOmniboxCommandProvider;
import org.eclipse.sirius.components.collaborative.omnibox.dto.OmniboxCommand;
import org.eclipse.sirius.web.application.studio.services.api.IStudioCapableEditingContextPredicate;
import org.springframework.stereotype.Service;

/**
 * Provides the publish studio command in the omnibox.
 *
 * @author gdaniel
 */
@Service
public class StudioPublicationCommandProvider implements IOmniboxCommandProvider {

    public static final String PUBLISH_STUDIO_COMMAND_ID = "publishStudio";

    private final IStudioCapableEditingContextPredicate studioCapableEditingContextPredicate;

    public StudioPublicationCommandProvider(IStudioCapableEditingContextPredicate studioCapableEditingContextPredicate) {
        this.studioCapableEditingContextPredicate = Objects.requireNonNull(studioCapableEditingContextPredicate);
    }

    @Override
    public List<OmniboxCommand> getCommands(String editingContextId, List<String> selectedObjectIds, String query) {
        List<OmniboxCommand> result = new ArrayList<>();
        if (this.studioCapableEditingContextPredicate.test(editingContextId)) {
            result.add(new OmniboxCommand(PUBLISH_STUDIO_COMMAND_ID, "Publish Studio", List.of("/omnibox/publish.svg"), "Publish all the domains and representation descriptions as individual libraries"));
        }
        return result;
    }

}
