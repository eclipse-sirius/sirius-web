/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.forms.api;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.IRepresentationConfiguration;

/**
 * The configuration used to create a related elements event processor.
 *
 * @author pcdavid
 */
public class RelatedElementsConfiguration implements IRepresentationConfiguration {

    public static final String RELATED_PREFIX = "relatedElements://";

    private final String formId;

    private final List<String> objectIds;

    public RelatedElementsConfiguration(List<String> objectIds) {
        this.objectIds = Objects.requireNonNull(objectIds);
        var encodedIds = objectIds.stream().map(id -> URLEncoder.encode(id, StandardCharsets.UTF_8)).toList();
        this.formId = RELATED_PREFIX + "?objectIds=[" + String.join(",", encodedIds) + "]";
    }

    @Override
    public String getId() {
        return this.formId;
    }

    public List<String> getObjectIds() {
        return this.objectIds;
    }
}
