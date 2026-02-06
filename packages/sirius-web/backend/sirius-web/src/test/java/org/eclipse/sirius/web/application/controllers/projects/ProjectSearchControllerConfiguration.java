/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
package org.eclipse.sirius.web.application.controllers.projects;

import org.eclipse.sirius.web.domain.boundedcontexts.project.repositories.api.IProjectSearchRepositoryDelegate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.simple.JdbcClient;

/**
 * Configuration used to change the project search strategy.
 *
 * @author sbegaudeau
 */
public class ProjectSearchControllerConfiguration {

    @Bean
    @Primary
    public IProjectSearchRepositoryDelegate testProjectSearchRepositoryDelegate(JdbcClient jdbcClient) {
        return new CustomProjectSearchRepositoryDelegate(jdbcClient);
    }
}
