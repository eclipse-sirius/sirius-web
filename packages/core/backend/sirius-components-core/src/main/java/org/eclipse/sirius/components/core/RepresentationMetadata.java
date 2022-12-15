/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.core;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * The metadata of a representation.
 *
 * @author sbegaudeau
 */
public class RepresentationMetadata {
    private final String id;

    private final String kind;

    private final String label;

    private final String descriptionId;

    private final String targetObjectId;

    public RepresentationMetadata(String id, String kind, String label, String descriptionId, String targetObjectId) {
        this.id = Objects.requireNonNull(id);
        this.kind = Objects.requireNonNull(kind);
        this.label = Objects.requireNonNull(label);
        this.descriptionId = Objects.requireNonNull(descriptionId);
        this.targetObjectId = targetObjectId;
    }

    public String getId() {
        return this.id;
    }

    public String getKind() {
        return this.kind;
    }

    public String getLabel() {
        return this.label;
    }

    public String getDescriptionId() {
        return this.descriptionId;
    }

    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, kind: {2}, label: {3}, descriptionId: {4}, targetObjectId: {5}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.kind, this.label, this.descriptionId, this.targetObjectId);
    }
}
