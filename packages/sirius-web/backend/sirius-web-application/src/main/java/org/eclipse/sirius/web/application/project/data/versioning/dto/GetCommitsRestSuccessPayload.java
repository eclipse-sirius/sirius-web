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
package org.eclipse.sirius.web.application.project.data.versioning.dto;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;

/**
 * The payload for the "getCommits" REST API on success.
 *
 * @author arichard
 */
public record GetCommitsRestSuccessPayload(UUID id, List<RestCommit> commits) implements IPayload {
    public GetCommitsRestSuccessPayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(commits);
    }
}
