/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.controllers.capabilities;

import java.util.List;

import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateProvider;
import org.springframework.context.annotation.Bean;

/**
 * A test configuration to override the list of project template providers to an empty list.
 *
 * @author gcoutable
 */
public class ProjectTemplateCapabilitiesControllerConfiguration {

    @Bean
    public List<IProjectTemplateProvider> projectTemplateProviders() {
        return List.of();
    }

}
