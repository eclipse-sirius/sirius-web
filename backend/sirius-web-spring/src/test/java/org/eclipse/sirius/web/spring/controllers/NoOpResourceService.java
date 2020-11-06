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

import org.eclipse.sirius.web.spring.services.IResourceService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Implementation of the IResourceService service which does nothing at all.
 *
 * @author hmarchadour
 */
public class NoOpResourceService implements IResourceService {

    @Override
    public Resource getResource(String path) {
        return new ClassPathResource(path);
    }
}
