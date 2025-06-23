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
package org.eclipse.sirius.web.application.capability;

/**
 * The list of all constants for Sirius Web capabilities.
 *
 * @author sbegaudeau
 * @since v2025.8.0
 */
public final class SiriusWebCapabilities {

    public static final String PROJECT = "Project";

    /**
     * The list of project-specific capabilities.
     *
     * @author sbegaudeau
     * @since v2025.8.0
     */
    public static final class Project {

        /**
         * Used to check if projects can be created.
         *
         * @since v2025.8.0
         */
        public static final String CREATE = "create";


        /**
         * Used to check if projects can be uploaded.
         *
         * @since v2025.8.0
         */
        public static final String UPLOAD = "upload";

        /**
         * Used to check if a project can be downloaded.
         *
         * @since v2025.8.0
         */
        public static final String DOWNLOAD = "download";
    }
}
