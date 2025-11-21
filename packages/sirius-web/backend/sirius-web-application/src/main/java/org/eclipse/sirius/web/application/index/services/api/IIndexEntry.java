/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.index.services.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

/**
 * An index entry.
 *
 * @author gdaniel
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@indexEntryType")
public interface IIndexEntry {

    String INDEX_ENTRY_TYPE_FIELD = "@indexEntryType";

    String EDITING_CONTEXT_ID_FIELD = "@editingContextId";

    String ID_FIELD = "@id";

    String TYPE_FIELD = "@type";

    String LABEL_FIELD = "@label";

    String ICON_URLS_FIELD = "@iconURLs";

    @JsonProperty(EDITING_CONTEXT_ID_FIELD)
    String editingContextId();

    @JsonProperty(ID_FIELD)
    String id();

    @JsonProperty(TYPE_FIELD)
    String type();

    @JsonProperty(LABEL_FIELD)
    String label();

    @JsonProperty(ICON_URLS_FIELD)
    List<String> iconURLs();
}
