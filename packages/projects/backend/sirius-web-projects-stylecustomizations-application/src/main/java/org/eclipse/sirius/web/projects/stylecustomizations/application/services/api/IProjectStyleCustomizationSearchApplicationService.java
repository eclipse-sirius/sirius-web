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
package org.eclipse.sirius.web.projects.stylecustomizations.application.services.api;

import java.util.List;

import org.eclipse.sirius.web.projects.stylecustomizations.application.dto.StyleCustomizationDTO;

/**
 * Application service used to search project style customizations.
 *
 * @author gcoutable
 * @since v2026.11.0
 */
public interface IProjectStyleCustomizationSearchApplicationService {

    List<StyleCustomizationDTO> getStyleCustomizations(String projectId);
}
