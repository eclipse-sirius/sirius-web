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
package org.eclipse.sirius.web.application.project.services.api;

import org.eclipse.emf.ecore.resource.Resource;

/**
 * Used to register resource filter for RewriteProxiesEventHandler.
 *
 * @author arichard
 */
public interface IRewriteProxiesResourceFilter {

    /**
     * Filters the given resource.
     *
     * @param resource
     *            the resource to filter.
     * @return <code>true</code> to let the resource be processed by the RewriteProxiesEventHandler, <code>false</code>
     *         to exclude it.
     */
    boolean shouldRewriteProxies(Resource resource);
}
