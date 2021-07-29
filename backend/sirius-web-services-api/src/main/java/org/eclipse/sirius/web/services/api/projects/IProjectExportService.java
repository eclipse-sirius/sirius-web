/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.services.api.projects;

import java.util.UUID;

/**
 * Service used to export a project.
 *
 * @author gcoutable
 */
public interface IProjectExportService {

    byte[] exportProjectAsZip(UUID projectId);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IProjectExportService {

        @Override
        public byte[] exportProjectAsZip(UUID projectId) {
            return new byte[0];
        }

    }

}
