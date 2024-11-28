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
package org.eclipse.sirius.web.application.project.data.versioning.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.web.application.project.data.versioning.dto.ChangeType;
import org.eclipse.sirius.web.application.project.data.versioning.dto.RestBranch;
import org.eclipse.sirius.web.application.project.data.versioning.dto.RestCommit;
import org.eclipse.sirius.web.application.project.data.versioning.dto.RestDataVersion;
import org.eclipse.sirius.web.application.project.data.versioning.services.api.IDefaultProjectDataVersioningRestService;
import org.eclipse.sirius.web.application.project.data.versioning.services.api.IProjectDataVersioningRestService;
import org.eclipse.sirius.web.application.project.data.versioning.services.api.IProjectDataVersioningRestServiceDelegate;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IProjectDataVersioningRestService}.
 *
 * @author arichard
 */
@Service
public class ComposedProjectDataVersioningRestService implements IProjectDataVersioningRestService {

    private final List<IProjectDataVersioningRestServiceDelegate> projectDataVersioningRestServiceDelegate;

    private IDefaultProjectDataVersioningRestService defaultProjectDataVersioningRestService;

    public ComposedProjectDataVersioningRestService(List<IProjectDataVersioningRestServiceDelegate> projectDataVersioningRestServiceDelegate, IDefaultProjectDataVersioningRestService defaultProjectDataVersioningRestService) {
        this.projectDataVersioningRestServiceDelegate = Objects.requireNonNull(projectDataVersioningRestServiceDelegate);
        this.defaultProjectDataVersioningRestService = Objects.requireNonNull(defaultProjectDataVersioningRestService);
    }

    @Override
    public List<RestCommit> getCommits(IEditingContext editingContext) {
        var optionalDelegate = this.projectDataVersioningRestServiceDelegate.stream()
                .filter(delegate -> delegate.canHandle(editingContext))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getCommits(editingContext);
        }
        return this.defaultProjectDataVersioningRestService.getCommits(editingContext);
    }

    @Override
    public RestCommit createCommit(IEditingContext editingContext, Optional<UUID> branchId) {
        var optionalDelegate = this.projectDataVersioningRestServiceDelegate.stream()
                .filter(delegate -> delegate.canHandle(editingContext))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().createCommit(editingContext, branchId);
        }
        return this.defaultProjectDataVersioningRestService.createCommit(editingContext, branchId);
    }

    @Override
    public RestCommit getCommitById(IEditingContext editingContext, UUID commitId) {
        var optionalDelegate = this.projectDataVersioningRestServiceDelegate.stream()
                .filter(delegate -> delegate.canHandle(editingContext))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getCommitById(editingContext, commitId);
        }
        return this.defaultProjectDataVersioningRestService.getCommitById(editingContext, commitId);
    }

    @Override
    public List<RestDataVersion> getCommitChange(IEditingContext editingContext, UUID commitId, List<ChangeType> changeTypes) {
        var optionalDelegate = this.projectDataVersioningRestServiceDelegate.stream()
                .filter(delegate -> delegate.canHandle(editingContext))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getCommitChange(editingContext, commitId, changeTypes);
        }
        return this.defaultProjectDataVersioningRestService.getCommitChange(editingContext, commitId, changeTypes);
    }

    @Override
    public RestDataVersion getCommitChangeById(IEditingContext editingContext, UUID commitId, UUID changeId) {
        var optionalDelegate = this.projectDataVersioningRestServiceDelegate.stream()
                .filter(delegate -> delegate.canHandle(editingContext))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getCommitChangeById(editingContext, commitId, changeId);
        }
        return this.defaultProjectDataVersioningRestService.getCommitChangeById(editingContext, commitId, changeId);
    }

    @Override
    public List<RestBranch> getBranches(IEditingContext editingContext) {
        var optionalDelegate = this.projectDataVersioningRestServiceDelegate.stream()
                .filter(delegate -> delegate.canHandle(editingContext))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getBranches(editingContext);
        }
        return this.defaultProjectDataVersioningRestService.getBranches(editingContext);
    }

    @Override
    public RestBranch createBranch(IEditingContext editingContext, String branchName, UUID commitId) {
        var optionalDelegate = this.projectDataVersioningRestServiceDelegate.stream()
                .filter(delegate -> delegate.canHandle(editingContext))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().createBranch(editingContext, branchName, commitId);
        }
        return this.defaultProjectDataVersioningRestService.createBranch(editingContext, branchName, commitId);
    }

    @Override
    public RestBranch getBranchById(IEditingContext editingContext, UUID branchId) {
        var optionalDelegate = this.projectDataVersioningRestServiceDelegate.stream()
                .filter(delegate -> delegate.canHandle(editingContext))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getBranchById(editingContext, branchId);
        }
        return this.defaultProjectDataVersioningRestService.getBranchById(editingContext, branchId);
    }

    @Override
    public RestBranch deleteBranch(IEditingContext editingContext, UUID branchId) {
        var optionalDelegate = this.projectDataVersioningRestServiceDelegate.stream()
                .filter(delegate -> delegate.canHandle(editingContext))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().deleteBranch(editingContext, branchId);
        }
        return this.defaultProjectDataVersioningRestService.deleteBranch(editingContext, branchId);
    }
}
