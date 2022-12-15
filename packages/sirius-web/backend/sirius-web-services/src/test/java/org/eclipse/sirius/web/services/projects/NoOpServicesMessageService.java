/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
    public String invalidInput(String expectedInputTypeName, String receivedInputTypeName) {
        return "";
    }

    @Override
    public String invalidProjectName() {
        return "";
    }

    @Override
    public String projectNotFound() {
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
    public String stereotypeDescriptionNotFound(UUID stereotypeDescriptionId) {
        return "";
    }

}
