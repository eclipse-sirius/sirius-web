/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
    public String invalidProjectName() {
        return this.messageSourceAccessor.getMessage("INVALID_PROJECT_NAME"); //$NON-NLS-1$
    }

}
