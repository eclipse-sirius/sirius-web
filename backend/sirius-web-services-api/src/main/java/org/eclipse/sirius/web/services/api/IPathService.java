/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.services.api;

/**
 * Interface to manipulate paths and hide them behind a hash.
 *
 * @author hmarchadour
 */
public interface IPathService {

    /**
     * Resolve the given path if it contains a hashed part.
     */
    String resolvePath(String path);

    /**
     * Detect an obfuscate path.
     */
    boolean isObfuscated(String path);

    /**
     * Obfuscate the disk location of the given path.
     */
    String obfuscatePath(String path);
}
