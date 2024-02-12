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
package org.eclipse.sirius.components.core.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IContentService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IObjectService}.
 *
 * @author mcharfadi
 */
@Service
public class ObjectService implements IObjectService {

    private final ILabelService labelService;

    private final IContentService contentService;

    private final IIdentityService identityService;

    private final IObjectSearchService objectSearchService;

    public ObjectService(ILabelService labelService, IContentService contentService, IIdentityService identityService, IObjectSearchService objectSearchService) {
        this.labelService = Objects.requireNonNull(labelService);
        this.contentService = Objects.requireNonNull(contentService);
        this.identityService = Objects.requireNonNull(identityService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
    }

    @Override
    public List<Object> getContents(Object object) {
        return this.contentService.getContents(object);
    }

    @Override
    public String getId(Object object) {
        return this.identityService.getId(object);
    }

    @Override
    public String getKind(Object object) {
        return this.identityService.getKind(object);
    }

    @Override
    public String getLabel(Object object) {
        return this.labelService.getLabel(object);
    }

    @Override
    public String getFullLabel(Object object) {
        return this.labelService.getFullLabel(object);
    }

    @Override
    public Optional<String> getLabelField(Object object) {
        return this.labelService.getLabelField(object);
    }

    @Override
    public boolean isLabelEditable(Object object) {
        return this.labelService.isLabelEditable(object);
    }

    @Override
    public List<String> getImagePath(Object object) {
        return this.labelService.getImagePath(object);
    }

    @Override
    public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
        return this.objectSearchService.getObject(editingContext, objectId);
    }

}
