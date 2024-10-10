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
package org.eclipse.sirius.web.application.object.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.web.application.object.dto.Direction;
import org.eclipse.sirius.web.application.object.services.api.IDefaultObjectRestService;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IDefaultObjectRestService}.
 *
 * @author arichard
 */
@Service
public class DefaultObjectRestService implements IDefaultObjectRestService {

    private final IObjectService objectService;

    public DefaultObjectRestService(IObjectService objectService) {
        this.objectService = Objects.requireNonNull(objectService);
    }

    @Override
    public List<Object> getElements(IEditingContext editingContext) {
        var elements = new ArrayList<>();
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            var resourceSet = emfEditingContext.getDomain().getResourceSet();
            var resources = resourceSet.getResources();
            for (Resource resource : resources) {
                var iterator = resource.getAllContents();
                var stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
                elements.addAll(stream.toList());
            }
        }
        return elements;
    }

    @Override
    public Optional<Object> getElementById(IEditingContext editingContext, String elementId) {
        return this.objectService.getObject(editingContext, elementId);
    }

    @Override
    public List<Object> getRelationshipsByRelatedElement(IEditingContext editingContext, String elementId, Direction direction) {
        return new ArrayList<>();
    }

    @Override
    public List<Object> getRootElements(IEditingContext editingContext) {
        var rootElements = new ArrayList<>();
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            var resourceSet = emfEditingContext.getDomain().getResourceSet();
            var resources = resourceSet.getResources();
            for (Resource resource : resources) {
                var contents = resource.getContents();
                rootElements.addAll(contents);
            }
        }
        return rootElements;
    }
}
