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
package org.eclipse.sirius.web.collaborative.api.dto;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.persistence.entities.DocumentEntity;

/**
 * An internal event indicating that a new version of a set of documents has been persisted.
 *
 * @author pcdavid
 */
public class DocumentsModifiedEvent {
    private final UUID projectId;

    private final List<DocumentEntity> documents;

    public DocumentsModifiedEvent(UUID projectId, List<DocumentEntity> documents) {
        this.projectId = Objects.requireNonNull(projectId);
        this.documents = List.copyOf(Objects.requireNonNull(documents));
    }

    public UUID getProjectId() {
        return this.projectId;
    }

    public Collection<DocumentEntity> getDocuments() {
        return this.documents;
    }

}
