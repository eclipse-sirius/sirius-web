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
package org.eclipse.sirius.web.application.project.services.api;

import java.util.Optional;

/**
 * Used to convert project ids into editing context id and vice versa.
 *
 * @author sbegaudeau
 */
public interface IProjectEditingContextService {
    Optional<String> getEditingContextId(String projectId);

    Optional<String> getEditingContextId(String projectId, String name);

    Optional<String> getProjectId(String editingContextId);
}
