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
package org.eclipse.sirius.web.application.project.services.api;

import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.application.project.dto.CreateProjectFromTemplateInput;

/**
 * Used to initialize project from templates.
 *
 * @author sbegaudeau
 */
public interface ITemplateBasedProjectInitializer {
    IPayload initializeProjectFromTemplate(CreateProjectFromTemplateInput input, UUID projectId, String templateId);
}
