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
package org.eclipse.sirius.web.spring.collaborative.configuration;

import java.util.List;

import org.eclipse.sirius.web.api.configuration.IStereotypeDescriptionRegistryConfigurer;
import org.eclipse.sirius.web.spring.collaborative.stereotypes.StereotypeDescriptionRegistry;
import org.eclipse.sirius.web.spring.collaborative.stereotypes.StereotypeDescriptionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The configuration used to provide the services related to the stereotype descriptions.
 *
 * @author sbegaudeau
 */
@Configuration
public class StereotypeDescriptionConfiguration {
    @Bean
    public StereotypeDescriptionService stereotypeDescriptionService(List<IStereotypeDescriptionRegistryConfigurer> configurers) {
        StereotypeDescriptionRegistry registry = new StereotypeDescriptionRegistry();
        configurers.forEach(configurer -> configurer.addStereotypeDescriptions(registry));
        return new StereotypeDescriptionService(registry);
    }
}
