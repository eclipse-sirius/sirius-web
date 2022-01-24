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
package org.eclipse.sirius.web.core.api;

import java.util.List;

import org.eclipse.sirius.web.annotations.PublicApi;

/**
 * Interface of the service used to get the path of the images accessible from the jars used in the application.</br>
 * As a typical use case, the specifier can provide an implementation of this API as a {@link @Service} so that the
 * images defined in the Viewpoint Specification Project could be accessible.
 *
 * @author lfasani
 */
@PublicApi
public interface IImagePathService {
    /**
     * Get the paths of the folders containing the accessible images.</br>
     * The folders are those in the jar.
     * <p>
     * Warning: <b>The path must be unique</b>. Indeed, the folders are contained in a jar but the system does not know
     * about the jars. Then if an image is contained in the same given folder structure in many jars, then the result to
     * read the image will be unpredictable.
     * </p>
     *
     * The folder must be of the form:
     *
     * <pre>
     * /(FOLDER1)(/ADDITIONAL_FOLDER1/.../ADDITIONAL_FOLDERn)
     * </pre>
     *
     * <p>
     * Path examples:</br>
     * "/images", "/icons/full/obj16"
     * </p>
     *
     * @return a list a paths.
     */
    List<String> getPaths();
}
