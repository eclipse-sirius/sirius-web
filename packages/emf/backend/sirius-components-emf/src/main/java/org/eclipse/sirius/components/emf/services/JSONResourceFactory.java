/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.emfjson.resource.JsonResourceImpl;

/**
 * Special factory for {@link JsonResource}.</br>
 * This factory ensures that objects in that resource are identified by their uuid.
 *
 * @author lfasani
 */
public class JSONResourceFactory extends ResourceFactoryImpl {

    @Override
    public JsonResource createResource(URI uri) {
        Optional.ofNullable(uri)
            .map(URI::scheme)
            .filter(Objects::nonNull)
            .filter(Predicate.not(String::isEmpty))
            .orElseThrow(() -> new IllegalArgumentException(String.format("Missing scheme for URI %s", uri)));

        Map<String, Object> options = new HashMap<>();

        options.put(JsonResource.OPTION_ID_MANAGER, new EObjectIDManager());
        options.put(JsonResource.OPTION_DISPLAY_DYNAMIC_INSTANCES, true);

        var resource = new JsonResourceImpl(uri, options);
        resource.setIntrinsicIDToEObjectMap(new HashMap<>());
        return resource;
    }

    /**
     * Create a resource which URI is made of a correct scheme and the given id as path.
     *
     * @param path
     *            the path of the resource
     */
    public JsonResource createResourceFromPath(String path) {
        URI uri = this.createResourceURI(path);

        return this.createResource(uri);
    }

    /**
     * Create an URI with a correct scheme and the given id as path.
     *
     * @param resourceId
     *            the id of the resource
     */
    public URI createResourceURI(String resourceId) {
        // There are three slashes because the URI authority is empty
        return URI.createURI(IEMFEditingContext.RESOURCE_SCHEME + ":///" + resourceId);
    }
}
