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

    /**
     * Disable potentially insecure XML features.
     *
     * @see https://cheatsheetseries.owasp.org/cheatsheets/XML_External_Entity_Prevention_Cheat_Sheet.html
     * @see https://xerces.apache.org/xerces2-j/features.html
     */
    // @formatter:off
    private static final Map<String, Boolean> SAFE_SAX_PARSER_FEATURES = Map.of(
            "http://xml.org/sax/features/external-general-entities", false, //$NON-NLS-1$
            "http://xml.org/sax/features/external-parameter-entities", false, //$NON-NLS-1$
            "http://apache.org/xml/features/nonvalidating/load-external-dtd", false, //$NON-NLS-1$
            "http://apache.org/xml/features/disallow-doctype-decl", true); //$NON-NLS-1$
    // @formatter:on

    public Map<String, Object> getXMILoadOptions() {
        return this.getXMILoadOptions(new XMLParserPoolImpl());
    }

    public Map<String, Object> getXMILoadOptions(XMLParserPool parserPool) {
        Map<String, Object> options = new HashMap<>();
        options.put(XMLResource.OPTION_DEFER_ATTACHMENT, Boolean.TRUE);
        options.put(XMLResource.OPTION_DEFER_IDREF_RESOLUTION, Boolean.TRUE);
        options.put(XMLResource.OPTION_USE_DEPRECATED_METHODS, Boolean.TRUE);
        options.put(XMLResource.OPTION_USE_PARSER_POOL, parserPool);
        options.put(XMLResource.OPTION_USE_XML_NAME_TO_FEATURE_MAP, new HashMap<>());
        options.put(XMLResource.OPTION_USE_PACKAGE_NS_URI_AS_LOCATION, Boolean.FALSE);
        options.put(XMLResource.OPTION_PARSER_FEATURES, SAFE_SAX_PARSER_FEATURES);
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
