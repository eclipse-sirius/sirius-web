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
package org.eclipse.sirius.web.services.api.modelers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.services.api.projects.Project;

/**
 * Interface of the service used to manipulate modelers.
 *
 * @author pcdavid
 */
public interface IModelerService {

    IPayload createModeler(CreateModelerInput input);

    IPayload renameModeler(RenameModelerInput input);

    IPayload publishModeler(PublishModelerInput input);

    Optional<Modeler> getModeler(UUID modelerId);

    List<Modeler> getModelers(Project project);
}
