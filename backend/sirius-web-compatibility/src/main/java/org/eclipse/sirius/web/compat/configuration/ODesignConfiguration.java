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
package org.eclipse.sirius.web.compat.configuration;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.web.compat.services.ODesignRegistry;
import org.eclipse.sirius.web.compat.services.api.IODesignRegistry;
import org.eclipse.sirius.web.compat.services.api.ISiriusConfiguration;
import org.eclipse.sirius.web.compat.services.representations.ODesignReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Used to initialize the odesign registry.
 *
 * @author sbegaudeau
 */
@Configuration
public class ODesignConfiguration {
    @Bean
    public IODesignRegistry oDesignRegistry(ODesignReader oDesignReader, List<ISiriusConfiguration> siriusConfigurations) {
        ODesignRegistry oDesignRegistry = new ODesignRegistry();

        // @formatter:off
        siriusConfigurations.stream()
            .map(ISiriusConfiguration::getODesignPaths)
            .flatMap(Collection::stream)
            .map(ClassPathResource::new)
            .map(oDesignReader::read)
            .flatMap(Optional::stream)
            .forEach(oDesignRegistry::add);
        // @formatter:on

        return oDesignRegistry;
    }
}
