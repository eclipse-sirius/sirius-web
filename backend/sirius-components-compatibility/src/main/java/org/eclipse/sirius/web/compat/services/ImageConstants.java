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
package org.eclipse.sirius.web.compat.services;

/**
 * Utility class containing constants for the images.
 *
 * @author lfasani
 */
public final class ImageConstants {
    public static final String IMAGES_ROOT_FOLDER = "/icons/svg"; //$NON-NLS-1$

    public static final String RESOURCE_SVG = IMAGES_ROOT_FOLDER + "/Resource.svg"; //$NON-NLS-1$

    public static final String DEFAULT_SVG = IMAGES_ROOT_FOLDER + "/Default.svg"; //$NON-NLS-1$

    private ImageConstants() {
        // Prevent instantiation
    }

}
