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
package org.eclipse.sirius.web.emf.services;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.emfjson.resource.JsonResourceImpl;

/**
 * Special factory for {@link JsonResource} for Sirius Web.</br>
 * This factory ensures that objects in that resource are identified by their uuid.
 *
 * @author lfasani
 */
public class SiriusWebJSONResourceFactoryImpl extends ResourceFactoryImpl {

    @Override
    public JsonResource createResource(URI uri) {
        Map<String, Object> options = new HashMap<>();

        options.put(JsonResource.OPTION_ID_MANAGER, new EObjectIDManager());
        return new JsonResourceImpl(uri, options);
    }
}
