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
package org.eclipse.sirius.web.application.editingcontext.services;

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.components.core.api.Domain;
import org.eclipse.sirius.components.core.api.IDomainSearchService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.springframework.stereotype.Service;

/**
 * Service used to search the domains available.
 *
 * @author frouene
 */
@Service
public class DomainSearchService implements IDomainSearchService {
    @Override
    public List<Domain> findAllByEditingContext(IEditingContext editingContext) {
        return Optional.of(editingContext)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain)
                .map(EditingDomain::getResourceSet)
                .map(ResourceSet::getPackageRegistry)
                .map(EPackage.Registry::values)
                .orElse(List.of())
                .stream()
                .filter(EPackage.class::isInstance)
                .map(EPackage.class::cast)
                .map(ePackage -> new Domain(ePackage.getNsURI(), ePackage.getNsURI()))
                .sorted()
                .toList();
    }

    @Override
    public List<Domain> findRootDomainsByEditingContext(IEditingContext editingContext) {
        return Optional.of(editingContext)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain)
                .map(EditingDomain::getResourceSet)
                .map(ResourceSet::getPackageRegistry)
                .map(EPackage.Registry::values)
                .orElse(List.of())
                .stream()
                .filter(EPackage.class::isInstance)
                .map(EPackage.class::cast)
                .map(ePackage -> new Domain(ePackage.getNsURI(), ePackage.getNsURI()))
                .sorted()
                .toList();
    }
}
