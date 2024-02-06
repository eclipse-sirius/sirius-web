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
package org.eclipse.sirius.components.emf.services.api;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.core.api.IEditingContext;

/**
 * Used to identify editing context using EMF.
 *
 * @author sbegaudeau
 */
public interface IEMFEditingContext extends IEditingContext {
    /**
     * This scheme should be used to create an URI of a resource that corresponds to a document added in the
     * EditingContext ResourceSet.
     */
    String RESOURCE_SCHEME = "sirius";

    AdapterFactoryEditingDomain getDomain();

    @Override
    default void dispose() {
        this.getDomain().getResourceSet().getResources().forEach(Resource::unload);
    }
}
