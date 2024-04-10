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

@Module(
    name = "Representation Data",
    allowedDependencies = { "Project" },
    exposedPackages = {
        "org.eclipse.sirius.web.domain.boundedcontexts.representationdata",
        "org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api",
        "org.eclipse.sirius.web.domain.boundedcontexts.representationdata.events"
    }
)
package org.eclipse.sirius.web.domain.boundedcontexts.representationdata;

import org.eclipse.sirius.web.domain.annotations.Module;