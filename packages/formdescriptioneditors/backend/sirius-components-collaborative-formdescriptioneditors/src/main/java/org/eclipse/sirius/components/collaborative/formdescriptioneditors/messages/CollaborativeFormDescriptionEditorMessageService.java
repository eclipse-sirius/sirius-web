/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.collaborative.formdescriptioneditors.messages;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the collaborative form description editor message service.
 *
 * @author arichard
 */
@Service
public class CollaborativeFormDescriptionEditorMessageService implements ICollaborativeFormDescriptionEditorMessageService {
    private final MessageSourceAccessor messageSourceAccessor;

    public CollaborativeFormDescriptionEditorMessageService(@Qualifier("collaborativeFormDescriptionEditorMessageSourceAccessor") MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = Objects.requireNonNull(messageSourceAccessor);
    }

    @Override
    public String invalidInput(String expectedInputTypeName, String receivedInputTypeName) {
        return this.messageSourceAccessor.getMessage("INVALID_INPUT", new Object[] { expectedInputTypeName, receivedInputTypeName });
    }
}
