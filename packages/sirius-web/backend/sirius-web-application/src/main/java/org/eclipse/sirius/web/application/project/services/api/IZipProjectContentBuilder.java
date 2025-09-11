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

import java.io.InputStream;
import java.util.Optional;

import org.eclipse.sirius.web.application.project.dto.ProjectZipContent;

/**
 * Builder of {@link ProjectZipContent}.
 *
 * @author Arthur Daussy
 */
public interface IZipProjectContentBuilder {

    /**
     * Builds the {@link ProjectZipContent} from a zip {@link InputStream}.
     *
     * @param zipData
     *         the data
     * @return a ProjectZipContent or {@link Optional#empty()} if a problems occurs during reading
     */
    Optional<ProjectZipContent> buildFromZip(InputStream zipData);

}
