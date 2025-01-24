/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.view.fork.services.api;

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

/**
 * Used initialize the semantic data of the forked project.
 *
 * @author sbegaudeau
 */
public interface IForkedViewSemanticDataInitializer {

    void initialize(ICause cause, AggregateReference<Project, String> project, RepresentationDescription representationDescription, String representationDescriptionId, String sourceId, String sourceElementId, String newSourceElementId);
}
