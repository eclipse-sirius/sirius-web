/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import java.util.Objects;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.core.api.IReadOnlyObjectPredicate;
import org.eclipse.sirius.web.application.project.services.api.IRewriteProxiesResourceFilter;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link IRewriteProxiesResourceFilter}.
 *
 * @author arichard
 */
@Service
public class DefaultRewriteProxiesResourceFilter implements IRewriteProxiesResourceFilter {

    private final IReadOnlyObjectPredicate readOnlyObjectPredicate;

    public DefaultRewriteProxiesResourceFilter(IReadOnlyObjectPredicate readOnlyObjectPredicate) {
        this.readOnlyObjectPredicate = Objects.requireNonNull(readOnlyObjectPredicate);
    }

    @Override
    public boolean shouldRewriteProxies(Resource resource) {
        return !this.readOnlyObjectPredicate.test(resource);
    }

}
