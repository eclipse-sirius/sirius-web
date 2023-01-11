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
package org.eclipse.sirius.web.services.api.projects;

import java.util.List;

/**
 * Aggregates all the project templates related beans into a single service to reduce the number of dependencies needed.
 *
 * @author sbegaudeau
 */
public interface IProjectTemplateService {
    List<IProjectTemplateProvider> getProjectTemplateProviders();

    List<IProjectTemplateInitializer> getProjectTemplateInitializers();

    /**
     * Empty implementation for testing/mocking.
     *
     * @author pcdavid
     */
    class NoOp implements IProjectTemplateService {

        @Override
        public List<IProjectTemplateProvider> getProjectTemplateProviders() {
            return null;
        }

        @Override
        public List<IProjectTemplateInitializer> getProjectTemplateInitializers() {
            return null;
        }

    }
}
