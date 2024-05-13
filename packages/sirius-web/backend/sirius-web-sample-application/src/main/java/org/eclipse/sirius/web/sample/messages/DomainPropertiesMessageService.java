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
package org.eclipse.sirius.web.sample.messages;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

/**
 * The message service for a domain property form.
 *
 * @author aresekb
 */
@Service
public class DomainPropertiesMessageService {

    private static final String MESSAGES_PATH = "messages/sirius-web-sample-application-domain";

    private final MessageSourceAccessor messageSourceAccessor;

    public DomainPropertiesMessageService() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.addBasenames(MESSAGES_PATH);
        this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }

    public String getMessage(String code) {
        return this.messageSourceAccessor.getMessage(code);
    }

}
