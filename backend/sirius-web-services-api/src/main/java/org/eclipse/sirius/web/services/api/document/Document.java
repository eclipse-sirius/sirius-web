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
package org.eclipse.sirius.web.services.api.document;

import java.text.MessageFormat;
import java.util.UUID;

import org.eclipse.sirius.web.services.api.projects.Project;

/**
 * Interface used by all documents.
 *
 * @author sbegaudeau
 */
public class Document {

    private UUID id;

    private Project project;

    private String name;

    private String content;

    public Document(UUID id, Project project, String name, String content) {
        this.id = id;
        this.project = project;
        this.name = name;
        this.content = content;
    }

    public UUID getId() {
        return this.id;
    }

    public Project getProject() {
        return this.project;
    }

    public String getName() {
        return this.name;
    }

    public String getContent() {
        return this.content;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, projectId: {2}, name: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.project.getId(), this.name);
    }
}
