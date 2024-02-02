/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.infrastructure.i18n;

import java.util.Objects;

import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

/**
 * Used to provide internationalized messages.
 *
 * @author sbegaudeau
 */
@Service
public class MessageService implements IMessageService {

    private final MessageSourceAccessor messageSourceAccessor;

    public MessageService(@Qualifier("messageSourceAccessor") MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = Objects.requireNonNull(messageSourceAccessor);
    }

    @Override
    public String invalidName() {
        return this.messageSourceAccessor.getMessage("INVALID_NAME");
    }
}
