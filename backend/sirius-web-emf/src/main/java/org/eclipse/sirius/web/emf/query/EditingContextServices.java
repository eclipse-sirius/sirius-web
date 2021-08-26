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
package org.eclipse.sirius.web.emf.query;

import java.util.Collection;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.emf.services.EditingContext;

/**
 * An utility class providing various query services.
 *
 * @author fbarbin
 */
public final class EditingContextServices {

    public Collection<EObject> eAllContents(IEditingContext editingContext) {
        //@formatter:off
        Optional<ResourceSet> optionalResourceSet = Optional.of(editingContext)
                .filter(EditingContext.class::isInstance)
                .map(EditingContext.class::cast)
                .map(EditingContext::getDomain)
                .map(EditingDomain::getResourceSet);

        return optionalResourceSet.stream()
         .map(ResourceSet::getResources)
         .flatMap(EList::stream)
         .flatMap(this::collectAllContent)
         .collect(Collectors.toList());
        //@formatter:on
    }

    private Stream<EObject> collectAllContent(Resource resource) {
        Spliterator<EObject> spliterator = Spliterators.spliteratorUnknownSize(resource.getAllContents(), Spliterator.ORDERED);
        return StreamSupport.stream(spliterator, false);
    }
}
