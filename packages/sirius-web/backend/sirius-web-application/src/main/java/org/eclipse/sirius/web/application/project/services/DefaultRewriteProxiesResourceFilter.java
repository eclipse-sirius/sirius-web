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
package org.eclipse.sirius.web.application.project.services;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.web.application.project.services.api.IRewriteProxiesResourceFilter;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link IRewriteProxiesResourceFilter}.
 *
 * @author arichard
 */
@Service
public class DefaultRewriteProxiesResourceFilter implements IRewriteProxiesResourceFilter {

    @Override
    public boolean shouldRewriteProxies(Resource resource) {
        return true;
    }
}
