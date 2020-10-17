/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.emf.services.messages;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the EMF message service.
 *
 * @author sbegaudeau
 */
@Service
public class EMFMessageService implements IEMFMessageService {

    private final MessageSourceAccessor messageSourceAccessor;

    public EMFMessageService(@Qualifier("emfMessageSourceAccessor") MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = Objects.requireNonNull(messageSourceAccessor);
    }

    @Override
    public String unexpectedError() {
        return this.messageSourceAccessor.getMessage("UNEXPECTED_ERROR"); //$NON-NLS-1$
    }

    @Override
    public String invalidDocumentName(String name) {
        return this.messageSourceAccessor.getMessage("INVALID_DOCUMENT_NAME", new Object[] { name }); //$NON-NLS-1$
    }

    @Override
    public String stereotypeDescriptionNotFound(String stereotypeDescriptionId) {
        return this.messageSourceAccessor.getMessage("STEREOTYPE_DESCRIPTION_NOT_FOUND", new Object[] { stereotypeDescriptionId }); //$NON-NLS-1$
    }

    @Override
    public String invalidInput(String expectedInputTypeName, String receivedInputTypeName) {
        return this.messageSourceAccessor.getMessage("INVALID_INPUT", new Object[] { expectedInputTypeName, receivedInputTypeName }); //$NON-NLS-1$
    }

}
