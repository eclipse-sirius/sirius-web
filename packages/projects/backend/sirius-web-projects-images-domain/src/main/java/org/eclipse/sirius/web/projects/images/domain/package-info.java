/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
@Module(
    name = "ProjectImage",
    allowedDependencies = { "Project" },
    exposedPackages = {
        "org.eclipse.sirius.web.projects.images.domain",
        "org.eclipse.sirius.web.projects.images.domain.services.api",
        "org.eclipse.sirius.web.projects.images.domain.event"
    }
)
package org.eclipse.sirius.web.projects.images.domain;

import org.eclipse.sirius.web.core.domain.annotations.Module;
