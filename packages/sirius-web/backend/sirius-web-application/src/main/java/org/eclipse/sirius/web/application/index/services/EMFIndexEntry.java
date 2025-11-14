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
package org.eclipse.sirius.web.application.index.services;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.List;
import java.util.Map;

import org.eclipse.sirius.web.application.index.services.api.IIndexEntry;

/**
 * A generic index entry for EMF objects.
 *
 * @author gdaniel
 */
public record EMFIndexEntry(
        String editingContextId,
        String id,
        String type,
        String label,
        List<String> iconURLs,
        @JsonAnyGetter
        @JsonAnySetter
        Map<String, Object> settings
) implements IIndexEntry {
}
