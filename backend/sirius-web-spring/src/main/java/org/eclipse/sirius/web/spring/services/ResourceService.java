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

import java.util.Objects;

import org.eclipse.sirius.web.services.api.IPathService;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * Resource Service.
 *
 * @author hmarchadour
 */
@Service
public class ResourceService implements IResourceService {

    protected final IPathService pathService;

    protected final ApplicationContext context;

    public ResourceService(IPathService pathService, ApplicationContext context) {
        this.pathService = Objects.requireNonNull(pathService);
        this.context = Objects.requireNonNull(context);
    }

    @Override
    public Resource getResource(String path) {
        Resource resource;
        if (this.pathService.isObfuscated(path)) {
            resource = this.context.getResource(this.pathService.resolvePath(path));
        } else {
            resource = new ClassPathResource(path);
        }
        return resource;
    }
}
