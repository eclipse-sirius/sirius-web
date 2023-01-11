/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.web.services.api.projects;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IPayload;

/**
 * The payload of the create project from template mutation.
 *
 * @author pcdavid
 */
public final class CreateProjectFromTemplateSuccessPayload implements IPayload {

    private final UUID id;

    private final Project project;

    private final RepresentationMetadata representationToOpen;

    public CreateProjectFromTemplateSuccessPayload(UUID id, Project project, RepresentationMetadata representationToOpen) {
        this.id = Objects.requireNonNull(id);
        this.project = Objects.requireNonNull(project);
        this.representationToOpen = representationToOpen;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public Project getProject() {
        return this.project;
    }

    public RepresentationMetadata getRepresentationToOpen() {
        return this.representationToOpen;
    }

    @Override
    public String toString() {
        if (this.representationToOpen != null) {
            String pattern = "{0} '{'id: {1}, project: '{'id: {2}, name: {3} '}', representationToOpen: '{' id: {4}, label: {5} '}' '}'";
            return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.project.getId(), this.project.getName(), this.representationToOpen.getId(),
                    this.representationToOpen.getLabel());
        } else {
            String pattern = "{0} '{'id: {1}, project: '{'id: {2}, name: {3} '}', representationToOpen: null '}'";
            return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.project.getId(), this.project.getName());
        }
    }
}
