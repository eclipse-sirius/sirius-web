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
@SuppressWarnings("checkstyle:MultipleStringLiterals")
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

        /**
         * Used to check if a project can be renamed.
         *
         * @since v2025.8.0
         */
        public static final String RENAME = "rename";

        /**
         * Used to check if a project can be deleted.
         *
         * @since v2025.8.0
         */
        public static final String DELETE = "delete";

        /**
         * Used to check is a project can be edited.
         *
         * <p>
         *     It involves only the content of a project.
         *     Renaming or deleting a project are handled by their own capabilities.
         * </p>
         *
         * @since v2025.8.0
         */
        public static final String EDIT = "edit";
    }

    public static final String LIBRARY = "Library";

    /**
     * The list of library-specific capabilities.
     *
     * @author gcoutable
     * @since v2025.8.0
     */
    public static final class Library {

        /**
         * Used to check if the library can be viewed.
         *
         * @since v2025.8.0
         */
        public static final String VIEW = "view";
    }

    public static final String PROJECT_SETTINGS = "ProjectSettings";

    /**
     * The list of project-settings-specific capabilities.
     *
     * @author gcoutable
     * @since v2025.8.0
     */
    public static final class ProjectSettings {

        /**
         * Used to check if the project settings view can be viewed.
         *
         * @since v2025.8.0
         */
        public static final String VIEW = "view";
    }

    /**
     * The list of project-settings-tab capabilities.
     *
     * @author gcoutable
     * @since v2025.8.0
     */
    public static final class ProjectSettingsTab {

        /**
         * Used to check if the project settings tab view can be viewed.
         *
         * @since v2025.8.0
         */
        public static final String VIEW = "view";
    }

}
