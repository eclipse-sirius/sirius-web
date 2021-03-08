/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.emf.services;

import java.util.UUID;

import org.eclipse.sirius.web.emf.services.messages.IEMFMessageService;

/**
 * Implementation of the message service which does nothing.
 *
 * @author sbegaudeau
 */
public class NoOpEMFMessageService implements IEMFMessageService {

    @Override
    public String invalidDocumentName(String name) {
        return ""; //$NON-NLS-1$
    }

    @Override
    public String stereotypeDescriptionNotFound(UUID stereotypeDescriptionId) {
        return ""; //$NON-NLS-1$
    }

    @Override
    public String unexpectedError() {
        return ""; //$NON-NLS-1$
    }

    @Override
    public String invalidInput(String expectedInputTypeName, String receivedInputTypeName) {
        return ""; //$NON-NLS-1$
    }

}
