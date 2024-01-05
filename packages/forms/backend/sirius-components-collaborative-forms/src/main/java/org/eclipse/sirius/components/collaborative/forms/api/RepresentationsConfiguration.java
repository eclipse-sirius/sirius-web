/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.IRepresentationConfiguration;

/**
 * The configuration used to create the representations event processor.
 *
 * @author gcoutable
 */
public class RepresentationsConfiguration implements IRepresentationConfiguration {

    private static final String REPRESENTATIONS_PREFIX = "representations:";

    private final String formId;

    private final List<String> objectIds;

    public RepresentationsConfiguration(List<String> objectIds) {
        this.objectIds = Objects.requireNonNull(objectIds);
        this.formId = UUID.nameUUIDFromBytes((REPRESENTATIONS_PREFIX + objectIds).getBytes()).toString();
    }

    @Override
    public String getId() {
        return this.formId;
    }

    public List<String> getObjectIds() {
        return this.objectIds;
    }
}
