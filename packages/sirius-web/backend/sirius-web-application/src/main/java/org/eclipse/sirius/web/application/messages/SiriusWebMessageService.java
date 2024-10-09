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
package org.eclipse.sirius.web.application.messages;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the sirius web message service.
 *
 * @author frouene
 */
@Service
public class SiriusWebMessageService implements ISiriusWebMessageService {

    private final MessageSourceAccessor messageSourceAccessor;

    public SiriusWebMessageService(@Qualifier("siriusWebApplicationMessageSourceAccessor") MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = Objects.requireNonNull(messageSourceAccessor);
    }

    @Override
    public String unavailableFeature() {
        return this.messageSourceAccessor.getMessage(MessageConstants.UNAVAILABLE_FEATURE);
    }

    @Override
    public String alreadySetFeature() {
        return this.messageSourceAccessor.getMessage(MessageConstants.ALREADY_SET_FEATURE);
    }

    @Override
    public String invalidDroppedObject() {
        return this.messageSourceAccessor.getMessage(MessageConstants.INVALID_DROPPED_OBJECT);
    }
}
