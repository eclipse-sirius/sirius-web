/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
package org.eclipse.sirius.web.tests.services.representation;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.tables.ColumnSort;
import org.springframework.stereotype.Service;

/**
 * Used to build ids of representations.
 *
 * @author mcharfadi
 */
@Service
public class RepresentationIdBuilder {

    private static final String EXPANDED_IDS = "&expandedIds=[";

    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    public String buildExplorerRepresentationId(String treeDescriptionId, List<String> expandedObjects, List<String> activatedFilters) {
        List<String> expandedObjectIds = expandedObjects.stream()
                .map(id -> URLEncoder.encode(id, StandardCharsets.UTF_8))
                .toList();

        List<String> activatedFilterIds = activatedFilters.stream()
                .map(id -> URLEncoder.encode(id, StandardCharsets.UTF_8))
                .toList();

        return "explorer://?treeDescriptionId=" + URLEncoder.encode(treeDescriptionId, StandardCharsets.UTF_8) + EXPANDED_IDS + String.join(",", expandedObjectIds) + "]&activeFilterIds=[" +
                String.join(",", activatedFilterIds) + "]";
    }

    public String buildSelectionRepresentationId(String treeDescriptionId, String targetObjectId, List<String> expandedObjectIds) {
        return "selection://?treeDescriptionId=" + URLEncoder.encode(treeDescriptionId, StandardCharsets.UTF_8) + "&targetObjectId=" + targetObjectId + EXPANDED_IDS + String.join(",", expandedObjectIds) + "]";
    }

    public String buildDiagramFilterRepresentationId(List<String> objectIds) {
        return "diagramFilter://?objectIds=[" + String.join(",", objectIds) + "]";
    }

    public String buildDetailsRepresentationId(List<String> objectIds) {
        return "details://?objectIds=[" + String.join(",", objectIds) + "]";
    }

    public String buildRepresentationViewRepresentationId(List<String> objectIds) {
        return "relatedviews://?objectIds=[" + String.join(",", objectIds) + "]";
    }

    public String buildViewsExplorerViewRepresentationId(List<String> expandedObjectIds) {
        List<String> encodedExpandedObjectIds = expandedObjectIds.stream()
                .map(id -> URLEncoder.encode(id, StandardCharsets.UTF_8))
                .toList();

        return "viewsexplorer://?" + EXPANDED_IDS + String.join(",", encodedExpandedObjectIds) + "]";
    }

    public String buildRelatedElementsRepresentationId(List<String> objectIds) {
        return "relatedElements://?objectIds=[" + String.join(",", objectIds) + "]";
    }

    public String buildModelBrowserRepresentationId(String type, String ownerKind, String targetType, String ownerId, String descriptionId, boolean isContainment, List<String> expandedObjects) {
        StringBuilder treeId = new StringBuilder("modelBrowser://");
        treeId.append(type);

        treeId.append("?ownerKind=");
        treeId.append(URLEncoder.encode(ownerKind, StandardCharsets.UTF_8));

        treeId.append("&targetType=");
        treeId.append(URLEncoder.encode(targetType, StandardCharsets.UTF_8));

        treeId.append("&ownerId=");
        treeId.append(ownerId);

        treeId.append("&descriptionId=");
        treeId.append(descriptionId);

        treeId.append("&isContainment=");
        treeId.append(isContainment);

        List<String> expandedObjectIds = expandedObjects.stream()
                .map(id -> URLEncoder.encode(id, StandardCharsets.UTF_8))
                .toList();

        treeId.append(EXPANDED_IDS);
        treeId.append(String.join(",", expandedObjectIds));
        treeId.append("]");

        return treeId.toString();
    }

    public String buildValidationRepresentationId() {
        return "validation://";
    }

    public String buildTreeRepresentationId(String treeId, List<String> expandedObjects) {
        List<String> expandedObjectIds = expandedObjects.stream()
                .map(id -> URLEncoder.encode(id, StandardCharsets.UTF_8))
                .toList();

        return treeId + "?expandedIds=[" + String.join(",", expandedObjectIds) + "]";
    }

    public String buildTableRepresentationId(String tableId, String cursor, String direction, int size, List<String> expanded, List<String> activatedFilters, List<ColumnSort> columnSort) {
        var expandedIds = expanded.stream()
                .map(id -> URLEncoder.encode(id, StandardCharsets.UTF_8))
                .toList();
        List<String> activatedFilterIds = activatedFilters.stream()
                .map(id -> URLEncoder.encode(id, StandardCharsets.UTF_8))
                .toList();

        return tableId + "?cursor=" +
                cursor +
                "&direction=" +
                direction +
                "&size=" +
                size +
                "&activeRowFilterIds=[" +
                String.join(",", activatedFilterIds) +
                "]" +
                EXPANDED_IDS +
                String.join(",", expandedIds) +
                "]" +
                "&columnSort=[" +
                columnSort.stream().map(sort -> sort.id() + ":" + sort.desc()).collect(Collectors.joining(",")) +
                "]";
    }
}
