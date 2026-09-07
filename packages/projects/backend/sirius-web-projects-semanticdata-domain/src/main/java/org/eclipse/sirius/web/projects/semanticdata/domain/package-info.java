/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
    name = "ProjectSemanticData",
    allowedDependencies = { "Project", "Semantic Data" },
    exposedPackages = {
        "org.eclipse.sirius.web.projects.semanticdata.domain",
        "org.eclipse.sirius.web.projects.semanticdata.domain.services.api",
        "org.eclipse.sirius.web.projects.semanticdata.domain.events"
    }
)
package org.eclipse.sirius.web.projects.semanticdata.domain;

import org.eclipse.sirius.web.core.domain.annotations.Module;
