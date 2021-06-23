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
package org.eclipse.sirius.web.spring.controllers;

/**
 * Utility class used to store some constants.
 *
 * @author sbegaudeau
 */
public final class URLConstants {
    public static final String API_BASE_PATH = "/api"; //$NON-NLS-1$

    public static final String GRAPHQL_BASE_PATH = API_BASE_PATH + "/graphql"; //$NON-NLS-1$

    public static final String GRAPHQL_UPLOAD_PATH = API_BASE_PATH + "/graphql/upload"; //$NON-NLS-1$

    public static final String GRAPHQL_SUBSCRIPTION_PATH = "/subscriptions"; //$NON-NLS-1$

    public static final String IMAGE_BASE_PATH = API_BASE_PATH + "/images"; //$NON-NLS-1$

    public static final String DOCUMENT_BASE_PATH = API_BASE_PATH + "/editingcontexts/{editingContextId}/documents"; //$NON-NLS-1$

    public static final String PROJECT_BASE_PATH = API_BASE_PATH + "/projects"; //$NON-NLS-1$

    public static final String ANY_PATTERN = "/**"; //$NON-NLS-1$

    public static final String LOGIN_PAGE = "/login"; //$NON-NLS-1$

    public static final String AUTHENTICATION_ENDPOINT = API_BASE_PATH + "/authenticate"; //$NON-NLS-1$

    public static final String SIGNOFF_ENDPOINT = API_BASE_PATH + "/signoff"; //$NON-NLS-1$

    private URLConstants() {
        // Prevent instantiation
    }
}
