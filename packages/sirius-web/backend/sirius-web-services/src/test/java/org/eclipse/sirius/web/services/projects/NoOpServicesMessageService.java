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
package org.eclipse.sirius.web.services.projects;

import java.util.UUID;

import org.eclipse.sirius.web.services.messages.IServicesMessageService;

/**
 * Implementation of the services message service which does nothing.
 *
 * @author sbegaudeau
 */
public class NoOpServicesMessageService implements IServicesMessageService {

    @Override
    public String revealSelectedFadedElements() {
        return "";
    }

    @Override
    public String collapseSelectedElements() {
        return "";
    }

    @Override
    public String expandSelectedElements() {
        return "";
    }

    @Override
    public String fadeSelectedElements() {
        return "";
    }

    @Override
    public String hideSelectedElements() {
        return "";
    }

    @Override
    public String invalidInput(String expectedInputTypeName, String receivedInputTypeName) {
        return "";
    }

    @Override
    public String invalidProjectName() {
        return "";
    }

    @Override
    public String pinSelectedElements() {
        return "";
    }

    @Override
    public String projectNotFound() {
        return "";
    }

    @Override
    public String showSelectedElements() {
        return "";
    }

    @Override
    public String unexpectedError() {
        return "";
    }

    @Override
    public String invalidDocumentName(String name) {
        return "";
    }

    @Override
    public String stereotypeNotFound(UUID stereotypeId) {
        return "";
    }

    @Override
    public String unpinSelectedElements() {
        return "";
    }

}
