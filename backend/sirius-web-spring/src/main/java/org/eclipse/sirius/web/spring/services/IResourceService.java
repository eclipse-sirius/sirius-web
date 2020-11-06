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
package org.eclipse.sirius.web.spring.services;

import org.eclipse.sirius.web.services.api.IPathService;
import org.springframework.core.io.Resource;

/**
 * Spring resource service.
 *
 * @author hmarchadour
 */
public interface IResourceService {

    /**
     * Return the spring {@link Resource}.
     *
     * @param path
     *            the spring location or an obfuscate path thanks to {@link IPathService}.
     * @return the spring {@link Resource}
     */
    Resource getResource(String path);
}
