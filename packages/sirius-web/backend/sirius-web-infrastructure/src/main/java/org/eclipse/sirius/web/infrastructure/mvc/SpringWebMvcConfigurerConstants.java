/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.web.infrastructure.mvc;

/**
 * Constants used by the WebMvc configurer.
 *
 * @author sbegaudeau
 */
public final class SpringWebMvcConfigurerConstants {

    /** Base path of the API. */
    public static final String API_BASE_PATH = "/api";

    /** Pattern used to match CSS resources from the front-end. */
    public static final String CSS_PATTERN = "/**/*.css";

    /** Pattern used to match HTML resources from the front-end. */
    public static final String HTML_PATTERN = "/**/*.html";

    /** Pattern used to match JS resources from the front-end. */
    public static final String JS_PATTERN = "/**/*.js";

    /** Pattern used to match JS source maps resources from the front-end. */
    public static final String JS_MAP_PATTERN = "/**/*.js.map";

    /** Pattern used to match JS chunk resources from the front-end. */
    public static final String JS_CHUNK_PATTERN = "/**/*.chunk.js";

    /** Pattern used to match JSON resources from the front-end. */
    public static final String JSON_PATTERN = "/**/*.json";

    /** Pattern used to match ICO resources from the front-end. */
    public static final String ICO_PATTERN = "/**/*.ico";

    /** Pattern used to match TTF resources from the front-end. */
    public static final String TTF_PATTERN = "/**/*.ttf";

    /** Pattern used to match media resources from the front-end. */
    public static final String MEDIA_PATTERN = "/**/media/**";

    /** Pattern used to match jpg images. */
    public static final String JPG_PATTERN = "/**/*.jpg";

    /** Pattern used to match jpeg images. */
    public static final String JPEG_PATTERN = "/**/*.jpeg";

    /** Pattern used to match png images. */
    public static final String PNG_PATTERN = "/**/*.png";

    /** Pattern used to match SVG images. */
    public static final String SVG_PATTERN = "/**/*.svg";

    /** Pattern used to match the raw hostname with any specific path. */
    public static final String EMPTY_PATTERN = "";

    /** Pattern used to match the path of the homepage. */
    public static final String HOMEPAGE_PATTERN = "/";

    /** Pattern used to match any path. */
    public static final String ANY_PATTERN = "/**";

    /** Path of the folder containing the static resources. */
    public static final String STATIC_ASSETS_PATH = "classpath:/static/";

    /** Path of the index.html file. */
    public static final String INDEX_HTML_PATH = "classpath:/static/index.html";

    private SpringWebMvcConfigurerConstants() {
        // Prevent instantiation
    }
}
