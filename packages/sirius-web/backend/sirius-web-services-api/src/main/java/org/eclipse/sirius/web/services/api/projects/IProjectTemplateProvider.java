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
 * Used to register project templates in the backend. This only considers templates' metadata, see
 * {@link IProjectTemplateInitializer} for the definition of the actual contents of a project created from a template.
 *
 * @author pcdavid
 */
public interface IProjectTemplateProvider {
    List<ProjectTemplate> getProjectTemplates();
}
