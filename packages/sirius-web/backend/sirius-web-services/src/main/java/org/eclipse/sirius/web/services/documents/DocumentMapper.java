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
package org.eclipse.sirius.web.services.documents;

import java.util.List;

import org.eclipse.sirius.web.persistence.entities.DocumentEntity;
import org.eclipse.sirius.web.persistence.entities.ProjectEntity;
import org.eclipse.sirius.web.services.api.document.Document;
import org.eclipse.sirius.web.services.api.projects.Project;

/**
 * Class used to convert document entities to document data transfer objects and vice versa.
 *
 * @author sbegaudeau
 */
public class DocumentMapper {
    public Document toDTO(DocumentEntity documentEntity) {
        ProjectEntity projectEntity = documentEntity.getProject();

        Project project = new Project(projectEntity.getId(), projectEntity.getName(), List.of());
        return new Document(documentEntity.getId(), project, documentEntity.getName(), documentEntity.getContent());
    }
}
