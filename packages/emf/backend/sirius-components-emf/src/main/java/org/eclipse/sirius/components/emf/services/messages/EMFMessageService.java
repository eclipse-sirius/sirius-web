/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.components.emf.services.messages;

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
        return this.messageSourceAccessor.getMessage("UNEXPECTED_ERROR");
    }

    @Override
    public String invalidInput(String expectedInputTypeName, String receivedInputTypeName) {
        return this.messageSourceAccessor.getMessage("INVALID_INPUT", new Object[] { expectedInputTypeName, receivedInputTypeName });
    }

    @Override
    public String invalidNumber(String newValue) {
        return this.messageSourceAccessor.getMessage("INVALID_NUMBER", new Object[] { newValue });
    }

    @Override
    public String upperBoundaryReached(String newInstanceClass, String feature) {
        return this.messageSourceAccessor.getMessage("UPPER_BOUNDARY_REACHED", new Object[] { newInstanceClass, feature });
    }

}
