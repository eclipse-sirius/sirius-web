/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.api;

import java.util.List;

import org.eclipse.sirius.components.annotations.PublicApi;

/**
 * Interface of the service used to get the path of the parametric svg images accessible from the jars used in the
 * application.</br>
 * The specifier must provide an implementation of this API as a {@link @Service} to provide available images.
 *
 * @author lfasani
 */
@PublicApi
public interface IParametricSVGImageRegistry {
    /**
     * Get the paths of the svg images than can be parameterized.</br>
     * The images are those in the jar.
     * <p>
     * Warning: <b>The path must be unique</b>. Indeed, the images are contained in a jar but the system does not know
     * about the jars. Then if an image is contained in the same folder structure in many jars, then the result to read
     * the image will be unpredictable.
     * </p>
     *
     * The folder must be of the form:
     *
     * <pre>
     * (/ADDITIONAL_FOLDER1/.../ADDITIONAL_FOLDERn)/IMAGE
     * </pre>
     *
     * <p>
     * Path examples:</br>
     * "/myparametricsvgfolder/myImage.svg", "/myParametricImage.svg"
     * </p>
     *
     * @return a list a paths.
     */
    List<ParametricSVGImage> getImages();
}
