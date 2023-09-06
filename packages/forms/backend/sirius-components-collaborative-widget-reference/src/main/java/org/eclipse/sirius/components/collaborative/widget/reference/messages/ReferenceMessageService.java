/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.widget.reference.messages;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the Reference widget message service.
 *
 * @author Jerome Gout
 */
@Service
public class ReferenceMessageService implements IReferenceMessageService {
    private final MessageSourceAccessor messageSourceAccessor;

    public ReferenceMessageService(@Qualifier("collaborativeReferenceMessageSourceAccessor") MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = Objects.requireNonNull(messageSourceAccessor);
    }

    @Override
    public String invalidInput(String expectedInputTypeName, String receivedInputTypeName) {
        return this.messageSourceAccessor.getMessage(MessageConstants.INVALID_INPUT, new Object[] { expectedInputTypeName, receivedInputTypeName });
    }

    @Override
    public String invalidIds() {
        return this.messageSourceAccessor.getMessage(MessageConstants.INVALID_IDS);
    }

    @Override
    public String unableToEditReadOnlyWidget() {
        return this.messageSourceAccessor.getMessage(MessageConstants.UNABLE_TO_EDIT_READONLY_WIDGET);
    }
}
