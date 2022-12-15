/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.web.sample.configuration;

import java.util.List;

import org.eclipse.sirius.components.compatibility.services.api.ISiriusConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of the Sirius compatibility layer.
 *
 * @author sbegaudeau
 */
@Configuration
public class SampleSiriusConfiguration implements ISiriusConfiguration {

    @Override
    public List<String> getODesignPaths() {
        return List.of("description/flow.odesign");
    }

    @Bean
    @ConditionalOnProperty(prefix = "org.eclipse.sirius.web.features", name = "studioDefinition")
    public ISiriusConfiguration domainModelerDefinition() {
        return () -> List.of("description/domain.odesign");
    }
}
