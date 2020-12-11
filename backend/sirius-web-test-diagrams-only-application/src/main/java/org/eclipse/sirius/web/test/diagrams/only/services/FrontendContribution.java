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
package org.eclipse.sirius.web.test.sample.services;

import java.util.List;

import org.eclipse.sirius.web.api.configuration.IFrontendContribution;
import org.springframework.stereotype.Component;

/**
 * Defines the routes handled by the default Sirius Web front-end.
 *
 * @author pcdavid
 */
@Component
public class FrontendContribution implements IFrontendContribution {
    /** Pattern used to match the raw hostname with any specific path. */
    private static final String EMPTY_PATTERN = ""; //$NON-NLS-1$

    /** Pattern used to match the path of the homepage. */
    private static final String HOMEPAGE_PATTERN = "/"; //$NON-NLS-1$

    @Override
    public String getStaticAssetsPath() {
        return "classpath:/static/"; //$NON-NLS-1$
    }

    @Override
    public boolean isMain() {
        return true;
    }

    @Override
    public List<String> getPathPatterns() {
        // @formatter:off
        return List.of(EMPTY_PATTERN,
                       HOMEPAGE_PATTERN,
                       "/new/project", //$NON-NLS-1$
                       "/upload/project", //$NON-NLS-1$
                       "/projects", //$NON-NLS-1$
                       "/projects/*/edit", //$NON-NLS-1$
                       "/projects/*/edit/*"); //$NON-NLS-1$
        // @formatter:on
    }

}
