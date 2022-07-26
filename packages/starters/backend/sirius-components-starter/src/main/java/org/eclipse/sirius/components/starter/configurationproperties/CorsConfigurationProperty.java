/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.components.starter.configurationproperties;

/**
 * CORS configuration properties object.
 *
 * @author sbegaudeau
 */
public class CorsConfigurationProperty {
    private final String[] allowedOriginPatterns;

    public CorsConfigurationProperty(String[] allowedOriginPatterns) {
        this.allowedOriginPatterns = allowedOriginPatterns;
    }

    public String[] getAllowedOriginPatterns() {
        return this.allowedOriginPatterns;
    }
}
