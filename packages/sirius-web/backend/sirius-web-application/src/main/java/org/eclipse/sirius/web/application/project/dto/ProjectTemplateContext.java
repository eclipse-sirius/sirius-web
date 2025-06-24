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
package org.eclipse.sirius.web.application.project.dto;

/**
 * Used to describe in which context the query Viewer.projectTemplates is requested.
 *
 * @author gcoutable
 */
public final class ProjectTemplateContext {
    /**
     * The query is executed from the ProjectBrowser.
     * <p>
     *     The returned page will contain in last position one to three hard-coded project templates depending on the results of {@link org.eclipse.sirius.web.application.capability.services.api.ICapabilityVoter#vote(String, String, String)}.
     *     The returned page can contain the following hard-coded project templates: create-project, upload-project, and/or browse-all-project-templates
     * </p>
     */
    public static final String PROJECT_BROWSER = "PROJECT_BROWSER";

    /**
     * The query is executing in any other context.
     * <p>
     *     The result contains only the registered project templates without any hard-coded project templates.
     * </p>
     */
    public static final String PROJECT_TEMPLATE_MODAL = "PROJECT_TEMPLATE_MODAL";

    private ProjectTemplateContext() {
        // Prevent instantiation
    }
}
