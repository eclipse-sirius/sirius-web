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
package org.eclipse.sirius.components.compatibility.messages;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the compatibility message service.
 *
 * @author gcoutable
 */
@Service
public class CompatibilityMessageService implements ICompatibilityMessageService {

    private final MessageSourceAccessor messageSourceAccessor;

    public CompatibilityMessageService(@Qualifier("compatibilityMessageAccessor") MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = Objects.requireNonNull(messageSourceAccessor);
    }

    @Override
    public String noReconnectionToolDefined() {
        return this.messageSourceAccessor.getMessage(MessageConstants.NO_RECONNECTION_TOOL_DEFINED);
    }

    @Override
    public String toolExecutionError() {
        return this.messageSourceAccessor.getMessage(MessageConstants.TOOL_EXECUTION_ERROR);
    }

    @Override
    public String reconnectionToolCannotBeHandled() {
        return this.messageSourceAccessor.getMessage(MessageConstants.TOOL_CANNOT_BE_HANDLED);
    }

}
