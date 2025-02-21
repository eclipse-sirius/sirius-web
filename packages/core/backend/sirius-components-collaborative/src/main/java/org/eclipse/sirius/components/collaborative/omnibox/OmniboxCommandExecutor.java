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

import org.eclipse.sirius.components.collaborative.omnibox.dto.ExecuteOmniboxCommandInput;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.collaborative.omnibox.api.IOmniboxCommandExecutor;
import org.eclipse.sirius.components.collaborative.omnibox.api.IOmniboxCommandHandler;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Used to execute omnibox commands.
 *
 * @author sbegaudeau
 */
@Service
public class OmniboxCommandExecutor implements IOmniboxCommandExecutor {

    private final List<IOmniboxCommandHandler> omniboxCommandHandlers;

    private final ICollaborativeMessageService collaborativeMessageService;

    private final Logger logger = LoggerFactory.getLogger(OmniboxCommandExecutor.class);

    public OmniboxCommandExecutor(List<IOmniboxCommandHandler> omniboxCommandHandlers, ICollaborativeMessageService collaborativeMessageService) {
        this.omniboxCommandHandlers = Objects.requireNonNull(omniboxCommandHandlers);
        this.collaborativeMessageService = Objects.requireNonNull(collaborativeMessageService);
    }

    @Override
    public IPayload execute(ExecuteOmniboxCommandInput input) {
        Optional<IOmniboxCommandHandler> optionalOmniboxCommandHandler = this.omniboxCommandHandlers.stream()
                .filter(handler -> handler.canHandle(input))
                .findFirst();

        IPayload payload = null;
        if (optionalOmniboxCommandHandler.isPresent()) {
            IOmniboxCommandHandler omniboxCommandHandler = optionalOmniboxCommandHandler.get();
            payload = omniboxCommandHandler.handle(input);
        } else {
            this.logger.warn("No handler found for event: {}", input);
            payload = new ErrorPayload(input.id(), List.of(new Message(this.collaborativeMessageService.notFound(), MessageLevel.ERROR)));
        }
        return payload;
    }
}
