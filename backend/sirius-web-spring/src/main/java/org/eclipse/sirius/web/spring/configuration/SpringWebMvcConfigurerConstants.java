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
package org.eclipse.sirius.web.spring.configuration;

/**
 * Constants used by the WebMvc configurer.
 *
 * @author sbegaudeau
 */
public final class SpringWebMvcConfigurerConstants {
    /** Pattern used to match CSS resources from the front-end. */
    public static final String CSS_PATTERN = "/**/*.css"; //$NON-NLS-1$

    /** Pattern used to match HTML resources from the front-end. */
    public static final String HTML_PATTERN = "/**/*.html"; //$NON-NLS-1$

    /** Pattern used to match JS resources from the front-end. */
    public static final String JS_PATTERN = "/**/*.js"; //$NON-NLS-1$

    /** Pattern used to match JS source maps resources from the front-end. */
    public static final String JS_MAP_PATTERN = "/**/*.js.map"; //$NON-NLS-1$

    /** Pattern used to match JS chunk resources from the front-end. */
    public static final String JS_CHUNK_PATTERN = "/**/*.chunk.js"; //$NON-NLS-1$

    /** Pattern used to match JSON resources from the front-end. */
    public static final String JSON_PATTERN = "/**/*.json"; //$NON-NLS-1$

    /** Pattern used to match ICO resources from the front-end. */
    public static final String ICO_PATTERN = "/**/*.ico"; //$NON-NLS-1$

    /** Pattern used to match TTF resources from the front-end. */
    public static final String TTF_PATTERN = "/**/*.ttf"; //$NON-NLS-1$

    /** Pattern used to match media resources from the front-end. */
    public static final String MEDIA_PATTERN = "/**/media/**"; //$NON-NLS-1$

    /** Pattern used to match the raw hostname with any specific path. */
    public static final String EMPTY_PATTERN = ""; //$NON-NLS-1$

    /** Pattern used to match the path of the homepage. */
    public static final String HOMEPAGE_PATTERN = "/"; //$NON-NLS-1$

    /** Pattern used to match any path. */
    public static final String ANY_PATTERN = "/**"; //$NON-NLS-1$

    /** Path of the folder containing the static resources. */
    public static final String STATIC_ASSETS_PATH = "classpath:/static/"; //$NON-NLS-1$

    /** Path of the index.html file. */
    public static final String INDEX_HTML_PATH = "classpath:/static/index.html"; //$NON-NLS-1$

    private SpringWebMvcConfigurerConstants() {
        // Prevent instantiation
    }
}
