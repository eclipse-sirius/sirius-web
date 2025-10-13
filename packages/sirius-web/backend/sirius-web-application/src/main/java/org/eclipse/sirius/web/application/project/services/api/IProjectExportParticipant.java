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
package org.eclipse.sirius.web.application.project.services.api;

import java.util.Map;
import java.util.zip.ZipOutputStream;

import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;

/**
 * Used to participate in the export of a project.
 *
 * @author sbegaudeau
 */
public interface IProjectExportParticipant {

    /**
     * Exports some data of a {@link Project}.
     *
     * @param project
     *         the targeted project
     * @param editingContextId
     *         the editing context id to export
     * @param outputStream
     *         an output stream to export the data
     * @return a map of properties of the project to export
     */
    Map<String, Object> exportData(Project project, String editingContextId, ZipOutputStream outputStream);
}
