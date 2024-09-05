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
package org.eclipse.sirius.web.tests.services.representation;

import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Used to build ids of representations.
 *
 * @author mcharfadi
 */
@Service
public class RepresentationIdBuilder {

    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    public String buildExplorerRepresentationId(List<String> expandedObjects, List<String> activatedFilters) {
        List<String> expandedObjectIds = expandedObjects.stream()
                .map(id -> URLEncoder.encode(id, StandardCharsets.UTF_8))
                .toList();

        List<String> activatedFilterIds = activatedFilters.stream()
                .map(id -> URLEncoder.encode(id, StandardCharsets.UTF_8))
                .toList();

        return "explorer://?expandedIds=[" + String.join(",", expandedObjectIds) + "]&activeFilterIds=[" + String.join(",", activatedFilterIds) + "]";
    }

    public String buildSelectionRepresentationId(String treeDescriptionId, String targetObjectId, List<String> expandedObjectIds) {
        return "selection://?treeDescriptionId=" + URLEncoder.encode(treeDescriptionId, StandardCharsets.UTF_8) + "&targetObjectId=" + targetObjectId + "&expandedIds=[" + String.join(",", expandedObjectIds) + "]";
    }

    public String buildDiagramFilterRepresentationId(List<String> objectIds) {
        return "diagramFilter://?objectIds=[" + String.join(",", objectIds) + "]";
    }

    public String buildDetailsRepresentationId(List<String> objectIds) {
        return "details://?objectIds=[" + String.join(",", objectIds) + "]";
    }

    public String buildRepresentationViewRepresentationId(List<String> objectIds) {
        return "representations://?objectIds=[" + String.join(",", objectIds) + "]";
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

        treeId.append("&expandedIds=[");
        treeId.append(String.join(",", expandedObjectIds));
        treeId.append("]");

        return treeId.toString();
    }

    public String buildValidationRepresentationId() {
        return "validation://";
    }

}
