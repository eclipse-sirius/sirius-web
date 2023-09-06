/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.widget.reference.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Configuration used to retrieve the message source accessor for the project.
 *
 * @author Jerome Gout
 */
@Configuration
public class ReferenceMessageServiceConfiguration {
    private static final String PATH = "messages/sirius-web-spring-collaborative-widget-reference";

    @Bean
    public MessageSourceAccessor collaborativeReferenceMessageSourceAccessor() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.addBasenames(PATH);
        return new MessageSourceAccessor(messageSource);
    }
}
