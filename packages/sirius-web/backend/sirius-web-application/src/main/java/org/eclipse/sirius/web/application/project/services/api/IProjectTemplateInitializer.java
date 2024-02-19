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
package org.eclipse.sirius.web.application.project.services.api;

import java.util.Optional;

import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;

/**
 * Initializes the contents of a new project created from a project template. The initializer can add new documents to
 * the project, create initial representations, and optionally indicate such a representation to automatically open when
 * the user is redirected to the newly create project.
 *
 * @author pcdavid
 */
public interface IProjectTemplateInitializer {
    boolean canHandle(String projectTemplateId);

    Optional<RepresentationMetadata> handle(String projectTemplateId, IEditingContext editingContext);
}
