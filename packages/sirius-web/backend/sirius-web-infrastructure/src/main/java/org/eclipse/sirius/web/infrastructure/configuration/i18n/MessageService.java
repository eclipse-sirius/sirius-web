/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.infrastructure.configuration.i18n;

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
    public String invalidInput(String expectedInputTypeName, String receivedInputTypeName) {
        return this.messageSourceAccessor.getMessage("INVALID_INPUT", new Object[] {expectedInputTypeName, receivedInputTypeName});
    }

    @Override
    public String upperBoundaryReached(String newInstanceClass, String feature) {
        return this.messageSourceAccessor.getMessage("UPPER_BOUNDARY_REACHED", new Object[] {newInstanceClass, feature});
    }

    @Override
    public String revealSelectedFadedElements() {
        return this.messageSourceAccessor.getMessage("REVEAL_SELECTED_FADED_ELEMENTS");
    }

    @Override
    public String collapseSelectedElements() {
        return this.messageSourceAccessor.getMessage("COLLAPSE_SELECTED_ELEMENTS");
    }

    @Override
    public String expandSelectedElements() {
        return this.messageSourceAccessor.getMessage("EXPAND_SELECTED_ELEMENTS");
    }

    @Override
    public String fadeSelectedElements() {
        return this.messageSourceAccessor.getMessage("FADE_SELECTED_ELEMENTS");
    }

    @Override
    public String hideSelectedElements() {
        return this.messageSourceAccessor.getMessage("HIDE_SELECTED_ELEMENTS");
    }

    @Override
    public String invalidName() {
        return this.messageSourceAccessor.getMessage("INVALID_NAME");
    }

    @Override
    public String notFound() {
        return this.messageSourceAccessor.getMessage("NOT_FOUND");
    }

    @Override
    public String pinSelectedElements() {
        return this.messageSourceAccessor.getMessage("PIN_SELECTED_ELEMENTS");
    }

    @Override
    public String showSelectedElements() {
        return this.messageSourceAccessor.getMessage("SHOW_SELECTED_ELEMENTS");
    }

    @Override
    public String unexpectedError() {
        return this.messageSourceAccessor.getMessage("UNEXPECTED_ERROR");
    }

    @Override
    public String unpinSelectedElements() {
        return this.messageSourceAccessor.getMessage("UNPIN_SELECTED_ELEMENTS");
    }

    @Override
    public String unavailableFeature() {
        return this.messageSourceAccessor.getMessage("UNAVAILABLE_FEATURE");
    }

    @Override
    public String alreadySetFeature() {
        return this.messageSourceAccessor.getMessage("ALREADY_SET_FEATURE");
    }

    @Override
    public String invalidDroppedObject() {
        return this.messageSourceAccessor.getMessage("INVALID_DROPPED_OBJECT");
    }

    @Override
    public String objectDuplicationFailed() {
        return this.messageSourceAccessor.getMessage("OBJECT_DUPLICATION_FAILED");
    }

    @Override
    public String objectDoesNotExist(String objectId) {
        return this.messageSourceAccessor.getMessage("OBJECT_DOES_NOT_EXIST", new Object[] {objectId});
    }
}
