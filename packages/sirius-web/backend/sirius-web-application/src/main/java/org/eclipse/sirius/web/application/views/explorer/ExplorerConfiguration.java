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
package org.eclipse.sirius.web.application.views.explorer;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.IRepresentationConfiguration;

/**
 * The configuration of the explorer event processor.
 *
 * @author sbegaudeau
 */
public class ExplorerConfiguration implements IRepresentationConfiguration {

    private final String treeId;

    private final List<String> activeFilterIds;

    private final List<String> expanded;

    public ExplorerConfiguration(String editingContextId, String treeId, List<String> expanded, List<String> activeFilters) {
        this.activeFilterIds = Objects.requireNonNull(activeFilters);
        this.expanded = Objects.requireNonNull(expanded);

        StringBuilder idBuilder = new StringBuilder(treeId);
        if (treeId.endsWith("://")) {
            idBuilder.append("?");
        } else {
            idBuilder.append("&");
        }

        List<String> expandedObjectIds = expanded.stream().map(id -> URLEncoder.encode(id, StandardCharsets.UTF_8)).toList();
        idBuilder.append("expandedIds=[").append(String.join(",", expandedObjectIds)).append("]");

        List<String> activatedFilterIds = activeFilters.stream().map(id -> URLEncoder.encode(id, StandardCharsets.UTF_8)).toList();
        idBuilder.append("&activeFilterIds=[").append(String.join(",", activatedFilterIds)).append("]");

        this.treeId = idBuilder.toString();
    }

    @Override
    public String getId() {
        return this.treeId;
    }

    public List<String> getActiveFilterIds() {
        return this.activeFilterIds;
    }

    public List<String> getExpanded() {
        return this.expanded;
    }

}
