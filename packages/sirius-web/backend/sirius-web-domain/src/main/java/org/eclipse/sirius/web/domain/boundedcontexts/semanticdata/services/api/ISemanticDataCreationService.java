/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api;

import java.util.UUID;

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

/**
 * Used to create the semantic data.
 *
 * @author sbegaudeau
 */
public interface ISemanticDataCreationService {
    void initialize(ICause cause, AggregateReference<Project, UUID> project);
}
