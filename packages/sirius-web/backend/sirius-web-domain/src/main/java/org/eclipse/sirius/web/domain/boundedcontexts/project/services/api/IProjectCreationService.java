/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.domain.boundedcontexts.project.services.api;

import java.util.List;

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;

/**
 * Usd to create projects.
 *
 * @author sbegaudeau
 */
public interface IProjectCreationService {
    IResult<Project> createProject(ICause cause, String name, List<String> natures);
}
