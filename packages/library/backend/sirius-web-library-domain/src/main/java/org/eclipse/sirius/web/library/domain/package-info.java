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
    name = "Library",
    allowedDependencies = { "Semantic Data" },
    exposedPackages = {
        "org.eclipse.sirius.web.library.domain",
        "org.eclipse.sirius.web.library.domain.services.api",
        "org.eclipse.sirius.web.library.domain.events"
    }
)
package org.eclipse.sirius.web.library.domain;

import org.eclipse.sirius.web.core.domain.annotations.Module;
