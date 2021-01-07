/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.messages;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

/**
 * Utility class used to retrieve the messages.
 *
 * @author sbegaudeau
 */
@Service
public class CollaborativeMessageService implements ICollaborativeMessageService {

    private final MessageSourceAccessor messageSourceAccessor;

    public CollaborativeMessageService(@Qualifier("collaborativeMessageSourceAccessor") MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = Objects.requireNonNull(messageSourceAccessor);
    }

    @Override
    public String invalidInput(String expectedInputTypeName, String receivedInputTypeName) {
        return this.messageSourceAccessor.getMessage("INVALID_INPUT", new Object[] { expectedInputTypeName, receivedInputTypeName }); //$NON-NLS-1$
    }

    @Override
    public String objectCreationFailed() {
        return this.messageSourceAccessor.getMessage("OBJECT_CREATION_FAILED"); //$NON-NLS-1$
    }

}
