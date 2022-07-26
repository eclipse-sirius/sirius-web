/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.components.collaborative.api;

import java.util.Optional;

/**
 * Interface used to provide an image for a representation when it is displayed in the user interface of the frontend.
 * For example, this method should return the URL of the image displayed for a representation in the explorer.
 *
 * @author sbegaudeau
 */
public interface IRepresentationImageProvider {
    /**
     * Returns the path of an image inside the classpath.
     *
     * @param kind
     *            The kind
     * @return An optional containing a path for the given representation or an empty optional if not supported.
     */
    Optional<String> getImageURL(String kind);
}
