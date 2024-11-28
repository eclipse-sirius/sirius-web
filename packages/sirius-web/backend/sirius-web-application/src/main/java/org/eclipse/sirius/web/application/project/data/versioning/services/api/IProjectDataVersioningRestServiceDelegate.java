/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.application.project.data.versioning.services.api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.web.application.project.data.versioning.dto.ChangeType;
import org.eclipse.sirius.web.application.project.data.versioning.dto.RestBranch;
import org.eclipse.sirius.web.application.project.data.versioning.dto.RestCommit;
import org.eclipse.sirius.web.application.project.data.versioning.dto.RestDataVersion;

/**
 * Interface of the delegate service used by project-data-versioning-related REST APIs.
 *
 * @author arichard
 */
public interface IProjectDataVersioningRestServiceDelegate {

    boolean canHandle(IEditingContext editingContext);

    List<RestCommit> getCommits(IEditingContext editingContext);

    RestCommit createCommit(IEditingContext editingContext, Optional<UUID> branchId);

    RestCommit getCommitById(IEditingContext editingContext, UUID commitId);

    List<RestDataVersion> getCommitChange(IEditingContext editingContext, UUID commitId, List<ChangeType> changeTypes);

    RestDataVersion getCommitChangeById(IEditingContext editingContext, UUID commitId, UUID changeId);

    List<RestBranch> getBranches(IEditingContext editingContext);

    RestBranch createBranch(IEditingContext editingContext, String branchName, UUID commitId);

    RestBranch getBranchById(IEditingContext editingContext, UUID branchId);

    RestBranch deleteBranch(IEditingContext editingContext, UUID branchId);
}
