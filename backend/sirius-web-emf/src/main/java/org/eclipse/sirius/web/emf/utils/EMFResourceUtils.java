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
package org.eclipse.sirius.web.emf.utils;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.xmi.XMLParserPool;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLParserPoolImpl;
import org.eclipse.sirius.emfjson.resource.JsonResource;

/**
 * Utility methods to deal with EMF resources.
 *
 * @author pcdavid
 */
public class EMFResourceUtils {

    public Map<String, Object> getFastXMILoadOptions() {
        return this.getFastXMILoadOptions(new XMLParserPoolImpl());
    }

    public Map<String, Object> getFastXMILoadOptions(XMLParserPool parserPool) {
        Map<String, Object> options = new HashMap<>();
        options.put(XMLResource.OPTION_DEFER_ATTACHMENT, Boolean.TRUE);
        options.put(XMLResource.OPTION_DEFER_IDREF_RESOLUTION, Boolean.TRUE);
        options.put(XMLResource.OPTION_USE_DEPRECATED_METHODS, Boolean.TRUE);
        options.put(XMLResource.OPTION_USE_PARSER_POOL, parserPool);
        options.put(XMLResource.OPTION_USE_XML_NAME_TO_FEATURE_MAP, new HashMap<>());
        return options;
    }

    public Map<String, Object> getFastJSONSaveOptions() {
        Map<String, Object> options = new HashMap<>();
        options.put(JsonResource.OPTION_SAVE_DERIVED_FEATURES, Boolean.FALSE);
        options.put(JsonResource.OPTION_SAVE_TRANSIENT_FEATURES, Boolean.FALSE);
        options.put(JsonResource.OPTION_SAVE_UNSETTED_FEATURES, Boolean.FALSE);
        return options;
    }
}
