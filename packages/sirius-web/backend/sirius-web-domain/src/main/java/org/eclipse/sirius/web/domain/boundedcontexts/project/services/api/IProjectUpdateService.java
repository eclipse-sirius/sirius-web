/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.services.IResult;

/**
 * Used to update projects.
 *
 * @author sbegaudeau
 */
public interface IProjectUpdateService {
    IResult<Void> renameProject(ICause cause, String projectId, String newName);

    IResult<Void> addNature(ICause cause, String projectId, String natureName);

    IResult<Void> removeNature(ICause cause, String projectId, String natureName);
}
