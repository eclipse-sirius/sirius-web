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
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.collaborative.omnibox.api.IWorkbenchOmniboxCommandExecutor;
import org.eclipse.sirius.components.collaborative.omnibox.api.IWorkbenchOmniboxCommandHandler;
import org.eclipse.sirius.components.collaborative.omnibox.dto.ExecuteWorkbenchOmniboxCommandInput;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Used to execute workbench omnibox commands.
 *
 * @author sbegaudeau
 */
@Service
public class WorkbenchOmniboxCommandExecutor implements IWorkbenchOmniboxCommandExecutor {

    private final List<IWorkbenchOmniboxCommandHandler> workbenchOmniboxCommandHandlers;

    private final ICollaborativeMessageService collaborativeMessageService;

    private final Logger logger = LoggerFactory.getLogger(WorkbenchOmniboxCommandExecutor.class);

    public WorkbenchOmniboxCommandExecutor(List<IWorkbenchOmniboxCommandHandler> workbenchOmniboxCommandHandlers, ICollaborativeMessageService collaborativeMessageService) {
        this.workbenchOmniboxCommandHandlers = Objects.requireNonNull(workbenchOmniboxCommandHandlers);
        this.collaborativeMessageService = Objects.requireNonNull(collaborativeMessageService);
    }

    @Override
    public IPayload execute(ExecuteWorkbenchOmniboxCommandInput input) {
        Optional<IWorkbenchOmniboxCommandHandler> optionalOmniboxCommandHandler = this.workbenchOmniboxCommandHandlers.stream()
                .filter(handler -> handler.canHandle(input))
                .findFirst();

        IPayload payload = null;
        if (optionalOmniboxCommandHandler.isPresent()) {
            IWorkbenchOmniboxCommandHandler workbenchOmniboxCommandHandler = optionalOmniboxCommandHandler.get();
            payload = workbenchOmniboxCommandHandler.handle(input);
        } else {
            this.logger.warn("No handler found for event: {}", input);
            payload = new ErrorPayload(input.id(), List.of(new Message(this.collaborativeMessageService.notFound(), MessageLevel.ERROR)));
        }
        return payload;
    }
}
