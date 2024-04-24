/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.web.services.messages;

import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the services messages.
 *
 * @author sbegaudeau
 */
@Service
public class ServicesMessageService implements IServicesMessageService {

    private final MessageSourceAccessor messageSourceAccessor;

    public ServicesMessageService(@Qualifier("servicesMessageSourceAccessor") MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = Objects.requireNonNull(messageSourceAccessor);
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
    public String invalidInput(String expectedInputTypeName, String receivedInputTypeName) {
        return this.messageSourceAccessor.getMessage("INVALID_INPUT", new Object[] { expectedInputTypeName, receivedInputTypeName });
    }

    @Override
    public String invalidProjectName() {
        return this.messageSourceAccessor.getMessage("INVALID_PROJECT_NAME");
    }

    @Override
    public String pinSelectedElements() {
        return this.messageSourceAccessor.getMessage("PIN_SELECTED_ELEMENTS");
    }

    @Override
    public String projectNotFound() {
        return this.messageSourceAccessor.getMessage("PROJECT_NOT_FOUND");
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
    public String invalidDocumentName(String name) {
        return this.messageSourceAccessor.getMessage("INVALID_DOCUMENT_NAME", new Object[] { name });
    }

    @Override
    public String stereotypeNotFound(UUID stereotypeId) {
        return this.messageSourceAccessor.getMessage("STEREOTYPE_NOT_FOUND", new Object[] { stereotypeId });
    }

    @Override
    public String unpinSelectedElements() {
        return this.messageSourceAccessor.getMessage("UNPIN_SELECTED_ELEMENTS");
    }
}
