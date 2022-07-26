/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.services.EditingContext;

/**
 * An utility class providing various query services.
 *
 * @author fbarbin
 */
public final class EditingContextServices {

    public Collection<EObject> allContents(IEditingContext editingContext) {
        //@formatter:off
       return this.getResourceset(editingContext)
               .stream()
               .flatMap(this::collectAllContent)
               .collect(Collectors.toList());
        //@formatter:on
    }

    public Collection<EObject> contents(IEditingContext editingContext) {
        //@formatter:off
        return this.getResourceset(editingContext)
                .stream()
                .map(ResourceSet::getResources)
                .flatMap(EList::stream)
                .map(Resource::getContents)
                .flatMap(EList::stream)
                .collect(Collectors.toList());
        //@formatter:on
    }

    public EObject getObjectById(IEditingContext editingContext, String id) {
        //@formatter:off
       return this.getResourceset(editingContext)
                .stream()
                .map(ResourceSet::getResources)
                .flatMap(EList::stream)
                .map(resource -> resource.getEObject(id))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
        //@formatter:on
    }

    private Optional<ResourceSet> getResourceset(IEditingContext editingContext) {
        //@formatter:off
       return  Optional.of(editingContext)
               .filter(EditingContext.class::isInstance)
               .map(EditingContext.class::cast)
               .map(EditingContext::getDomain)
               .map(EditingDomain::getResourceSet);
       //@formatter:on
    }

    private Stream<EObject> collectAllContent(ResourceSet resourceSet) {
        Spliterator<Notifier> spliterator = Spliterators.spliteratorUnknownSize(resourceSet.getAllContents(), Spliterator.ORDERED);
        //@formatter:off
        return StreamSupport.stream(spliterator, false)
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast);
        //@formatter:on
    }
}
