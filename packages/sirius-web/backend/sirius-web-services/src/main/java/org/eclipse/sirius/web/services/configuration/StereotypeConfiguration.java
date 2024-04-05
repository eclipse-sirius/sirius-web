/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.web.services.configuration;

import java.util.List;

import org.eclipse.sirius.web.services.api.document.IStereotypeRegistryConfigurer;
import org.eclipse.sirius.web.services.stereotypes.StereotypeRegistry;
import org.eclipse.sirius.web.services.stereotypes.StereotypeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The configuration used to provide the services related to the stereotypes.
 *
 * @author sbegaudeau
 */
@Configuration
public class StereotypeConfiguration {
    @Bean
    public StereotypeService stereotypeService(List<IStereotypeRegistryConfigurer> configurers) {
        StereotypeRegistry registry = new StereotypeRegistry();
        configurers.forEach(configurer -> configurer.addStereotypes(registry));
        return new StereotypeService(registry);
    }
}
