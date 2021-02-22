/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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

import java.util.Optional;
import java.util.UUID;

/**
 * Determines what kinds of operations a user can perform on a project and its content.
 *
 * @author pcdavid
 */
public interface IProjectAccessPolicy {
    Optional<AccessLevel> getAccessLevel(String username, UUID projectId);

    boolean canEdit(String username, UUID projectId);

    boolean canAdmin(String username, UUID projectId);
}
