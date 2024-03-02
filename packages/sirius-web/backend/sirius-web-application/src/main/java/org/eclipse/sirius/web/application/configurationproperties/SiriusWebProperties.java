/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.application.configurationproperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * The entry point of all the Sirius Web configuration properties.
 *
 * @author sbegaudeau
 */
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "sirius.web")
public record SiriusWebProperties(
        String enabled,
        String disabled
) {
    public static final String EVERYTHING = "*";
    public static final String STUDIO = "studio";

    public static final String PORTAL = "portal";
}
