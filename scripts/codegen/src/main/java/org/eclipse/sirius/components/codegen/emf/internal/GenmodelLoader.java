/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

package org.eclipse.sirius.components.codegen.emf.internal;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

final public class GenmodelLoader {
    private final ProjectLocationResolver projectLocationResolver;
    private final ProjectUriMapper projectUriMapper;

    public GenmodelLoader(ProjectLocationResolver projectLocationResolver, ProjectUriMapper projectUriMapper) {
        this.projectLocationResolver = projectLocationResolver;
        this.projectUriMapper = projectUriMapper;
    }

    public List<LoadedGenmodel> loadAll(ResourceSet resourceSet, Path repositoryRoot, List<Path> genmodels) {
        return genmodels.stream().map(path -> this.load(resourceSet, repositoryRoot, path)).collect(Collectors.toList());
    }

    private LoadedGenmodel load(ResourceSet resourceSet, Path repositoryRoot, Path path) {
        URI fileUri = URI.createFileURI(path.toAbsolutePath().toString());
        ProjectLocation projectLocation = this.projectLocationResolver.resolve(repositoryRoot, path);
        this.projectUriMapper.registerGenmodel(resourceSet, projectLocation, fileUri);
        Resource resource = resourceSet.getResource(fileUri, true);
        return new LoadedGenmodel(resource, projectLocation);
    }
}
