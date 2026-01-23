/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.application.project.services.api;

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.application.project.api.ICreateProjectInput;

/**
 * Application service used to create projects.
 *
 * @author sbegaudeau
 * @since v2026.1.0
 */
public interface IProjectCreationApplicationService {
    IPayload createProject(ICreateProjectInput input);
}
