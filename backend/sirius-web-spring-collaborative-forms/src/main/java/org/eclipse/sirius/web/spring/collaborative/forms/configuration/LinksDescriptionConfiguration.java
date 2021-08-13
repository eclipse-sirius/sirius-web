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
package org.eclipse.sirius.web.spring.collaborative.forms.configuration;

import java.util.List;

import org.eclipse.sirius.web.api.configuration.ILinksDescriptionRegistryConfigurer;
import org.eclipse.sirius.web.spring.collaborative.forms.services.LinksDescriptionRegistry;
import org.eclipse.sirius.web.spring.collaborative.forms.services.LinksDescriptionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The configuration used to provide the services related to the links descriptions.
 *
 * @author ldelaigue
 */
@Configuration
public class LinksDescriptionConfiguration {

    @Bean
    public LinksDescriptionService linksDescriptionService(List<ILinksDescriptionRegistryConfigurer> linksConfigurers) {
        LinksDescriptionRegistry registry = new LinksDescriptionRegistry();
        linksConfigurers.forEach(configurer -> configurer.addLinksDescriptions(registry));
        return new LinksDescriptionService(registry);
    }
}
