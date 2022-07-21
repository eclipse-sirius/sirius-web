/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.components.emf.services;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.emfjson.resource.JsonResourceImpl;

/**
 * Special factory for {@link JsonResource}.</br>
 * This factory ensures that objects in that resource are identified by their uuid.
 *
 * @author lfasani
 */
public class JSONResourceFactoryImpl extends ResourceFactoryImpl {

    @Override
    public JsonResource createResource(URI uri) {
        Map<String, Object> options = new HashMap<>();

        options.put(JsonResource.OPTION_ID_MANAGER, new EObjectIDManager());
        options.put(JsonResource.OPTION_DISPLAY_DYNAMIC_INSTANCES, true);

        return new JsonResourceImpl(uri, options);
    }

    /**
     * Create a resource which URI is made of a correct scheme and the given id as path.
     *
     * @param resourceId
     *            the id of the resource
     */
    public JsonResource createResource(String resourceId) {
        // There are three slashes because the URI authority is empty
        URI uri = URI.createURI(EditingContext.RESOURCE_SCHEME + ":///" + resourceId); //$NON-NLS-1$

        return this.createResource(uri);
    }
}
