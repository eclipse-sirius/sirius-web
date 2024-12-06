/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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

package org.eclipse.sirius.web.table.services;

import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;

import java.util.Optional;
import java.util.UUID;

/**
 * Interface for updating database when forking a studio.
 *
 * @author mcharfadi
 */
public interface IForkedStudioJdbcServices {

    void insertViewDocument(UUID documentId, UUID semanticProjectId, String content, String name);

    void updateRepresentationMetataDataDescriptionId(UUID id, String newRepresentationDescriptionId);

    void updateSemanticDataDomainProjectId(UUID id);

    void updateRepresentationContentDescriptionId(IRepresentationInput representationInput, String oldDescriptionId, String newDescriptionId, String oldSourceId, String newSourceId);

    Optional<Project> createStudioProject(IRepresentationInput representationInput, String newName);

}
