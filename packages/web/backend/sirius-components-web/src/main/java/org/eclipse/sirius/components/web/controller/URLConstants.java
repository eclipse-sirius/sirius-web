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
package org.eclipse.sirius.components.web.controller;

/**
 * Utility class used to store some constants.
 *
 * @author lfasani
 */
public final class URLConstants {
    public static final String API_BASE_PATH = "/api"; //$NON-NLS-1$

    public static final String PARAMETRIC_IMAGE_BASE_PATH = API_BASE_PATH + "/parametricsvgs"; //$NON-NLS-1$

    private URLConstants() {
        // Prevent instantiation
    }
}
