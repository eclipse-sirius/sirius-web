/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.services.modelers;

import org.eclipse.sirius.web.persistence.entities.ModelerEntity;
import org.eclipse.sirius.web.services.api.modelers.Modeler;
import org.eclipse.sirius.web.services.api.modelers.PublicationStatus;
import org.eclipse.sirius.web.services.projects.ProjectMapper;

/**
 * Converts Modeler entities to the corresponding DTO.
 *
 * @author pcdavid
 */
public class ModelerMapper {
    public Modeler toDTO(ModelerEntity modelerEntity) {
        var project = new ProjectMapper().toDTO(modelerEntity.getProject());
        var status = PublicationStatus.valueOf(modelerEntity.getPublicationStatus().name());
        return new Modeler(modelerEntity.getId(), modelerEntity.getName(), project, status);
    }
}
