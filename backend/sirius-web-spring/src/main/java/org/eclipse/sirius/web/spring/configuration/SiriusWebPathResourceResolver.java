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

import java.io.IOException;
import java.util.Objects;

import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.PathResourceResolver;

/**
 * The path resource resolver used to exclude API calls from the resolution of static resource.
 * <p>
 * We want to ensure that users can only request real static resources and prevent any attempt at retrieving static
 * resources if the requested resource starts with the path of our API.
 * </p>
 * <p>
 * We could attempt to do this using a whitelist approach by saying that only some specific paths can be retrieved but
 * maintaing such a whitelist would be cumbersome over time since we won't know all the paths used in the front-end of
 * the application.
 * </p>
 *
 * @author sbegaudeau
 */
public class SiriusWebPathResourceResolver extends PathResourceResolver {

    /**
     * The path of the URL used as a prefix for our API.
     */
    private String apiBasePath;

    public SiriusWebPathResourceResolver(String apiBasePath) {
        this.apiBasePath = Objects.requireNonNull(apiBasePath);
    }

    @Override
    protected Resource getResource(String resourcePath, Resource location) throws IOException {
        boolean isValid = !resourcePath.startsWith(this.apiBasePath);
        isValid = isValid && !resourcePath.startsWith(this.apiBasePath.substring(1));
        isValid = isValid && location.exists();
        isValid = isValid && location.isReadable();

        if (isValid) {
            return location;
        }
        return null;
    }
}
