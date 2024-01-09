/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.components.emf.query;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;

/**
 * An utility class providing various query services.
 *
 * @author fbarbin
 */
public final class EditingContextServices {

    public Collection<EObject> allContents(IEditingContext editingContext) {
        return this.getResourceset(editingContext)
                .stream()
               .flatMap(this::collectAllContent)
               .toList();
    }

    public Collection<EObject> contents(IEditingContext editingContext) {
        return this.getResourceset(editingContext)
                .stream()
                .map(ResourceSet::getResources)
                .flatMap(EList::stream)
                .map(Resource::getContents)
                .flatMap(EList::stream)
                .toList();
    }

    public EObject getObjectById(IEditingContext editingContext, String id) {
        return this.getResourceset(editingContext)
                .stream()
                .map(ResourceSet::getResources)
                .flatMap(EList::stream)
                .map(resource -> resource.getEObject(id))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    private Optional<ResourceSet> getResourceset(IEditingContext editingContext) {
        return Optional.of(editingContext)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain)
                .map(EditingDomain::getResourceSet);
    }

    private Stream<EObject> collectAllContent(ResourceSet resourceSet) {
        Spliterator<Notifier> spliterator = Spliterators.spliteratorUnknownSize(resourceSet.getAllContents(), Spliterator.ORDERED);
        return StreamSupport.stream(spliterator, false)
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast);
    }
}
