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
package org.eclipse.sirius.web.papaya.omnibox;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.omnibox.api.IOmniboxCommandHandler;
import org.eclipse.sirius.components.collaborative.omnibox.dto.ExecuteOmniboxCommandInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.springframework.stereotype.Service;

/**
 * Handles the command to create a sample Papaya project.
 *
 * @author gdaniel
 */
@Service
public class PapayaCreateSampleProjectCommandHandler implements IOmniboxCommandHandler {

    private final IEditingContextDispatcher editingContextDispatcher;

    public PapayaCreateSampleProjectCommandHandler(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public boolean canHandle(ExecuteOmniboxCommandInput input) {
        return Objects.equals(input.commandId(), PapayaCreateSampleProjectCommandProvider.CREATE_SAMPLE_PROJECT_COMMAND_ID);
    }

    @Override
    public IPayload handle(ExecuteOmniboxCommandInput input) {
        var editingContextDispatcherInput = new PapayaCreateSampleProjectInput(input.id());
        return this.editingContextDispatcher.dispatchMutation(input.editingContextId(), editingContextDispatcherInput).block();
    }

}
