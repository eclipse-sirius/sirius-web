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

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

/**
 * Content of a project stored in a binary format.
 *
 * @author Arthur Daussy
 */
public interface IProjectBinaryContent {

    /**
     * Gets the name of the project.
     *
     * @return the name of the project
     */
    String getName();

    /**
     * Gets a map that link a relative path in the binary file to a file content.
     *
     * @return the map linking path to content.
     */
    Map<String, ByteArrayOutputStream> getFileContent();

    /**
     * Gets a map that represents the structure of the project. The key of the maps are the properties and the value are either simple attribute or structured object.
     *
     * @return a map
     */
    Map<String, Object> getManifest();

    /**
     * Gets the nature applied on the project.
     *
     * @return a list of Nature.
     */
    List<String> getNatures();


}
