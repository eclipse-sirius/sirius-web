/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.studio.services.library;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.web.application.studio.services.library.api.DependencyGraph;
import org.eclipse.sirius.web.application.studio.services.library.api.IStudioLibraryDependencyCollector;
import org.springframework.stereotype.Service;

/**
 * Collects the dependencies between studio libraries.
 *
 * @author gdaniel
 */
@Service
public class StudioLibraryDependencyCollector implements IStudioLibraryDependencyCollector {

    @Override
    public DependencyGraph<EObject> collectDependencies(ResourceSet resourceSet) {
        DependencyGraph<EObject> dependencyGraph = new DependencyGraph<>();
        List<Domain> domains = new ArrayList<>();
        List<EObject> eObjectsWithDomainType = new ArrayList<>();
        resourceSet.getAllContents()
            .forEachRemaining(notifier -> {
                if (notifier instanceof EObject eObject) {
                    if (eObject instanceof Domain domain) {
                        // Cache the domains, we need them to resolve domainTypes.
                        domains.add(domain);
                        dependencyGraph.addNode(domain);
                    } else if (eObject instanceof RepresentationDescription representationDescription) {
                        dependencyGraph.addNode(representationDescription);
                    } else if (this.getDomainTypeAttribute(eObject).isPresent()) {
                        eObjectsWithDomainType.add(eObject);
                    }
                    EObject eObjectLibrary = this.getContainingLibraryCandidate(eObject).orElse(eObject);
                    ECrossReferenceAdapter.getCrossReferenceAdapter(eObject)
                        .getInverseReferences(eObject)
                        .forEach(setting -> {
                            if (setting.getEStructuralFeature() instanceof EReference eReference
                                    && !eReference.isContainment()) {
                                EObject dependentLibrary = this.getContainingLibraryCandidate(setting.getEObject()).orElse(setting.getEObject());
                                if (dependentLibrary != eObjectLibrary) {
                                    dependencyGraph.addEdge(dependentLibrary, eObjectLibrary);
                                }
                            }
                        });
                }
            });

        // Add domainType-based dependencies.
        for (EObject eObjectWithDomainType : eObjectsWithDomainType) {
            this.getDomainTypeAttribute(eObjectWithDomainType)
                .ifPresent(domainTypeAttribute -> {
                    String domainType = (String) eObjectWithDomainType.eGet(domainTypeAttribute);
                    if (domainType != null) {
                        String[] splittedDomainType = domainType.split("::");
                        if (splittedDomainType.length > 0) {
                            String domainName = splittedDomainType[0];
                            domains.stream()
                                .filter(domain -> Objects.equals(domain.getName(), domainName))
                                .findFirst()
                                .ifPresent(domain -> {
                                    EObject eObjectLibrary = this.getContainingLibraryCandidate(eObjectWithDomainType).orElse(eObjectWithDomainType);
                                    dependencyGraph.addEdge(eObjectLibrary, domain);
                                });
                        }
                    }
                });
        }

        // Remove non-library elements that aren't connected to a library
        for (List<EObject> component : dependencyGraph.getComponents()) {
            if (component.stream().noneMatch(element -> element instanceof Domain || element instanceof RepresentationDescription)) {
                component.forEach(dependencyGraph::removeNode);
            }
        }

        return dependencyGraph;
    }

    private Optional<EAttribute> getDomainTypeAttribute(EObject eObject) {
        return eObject.eClass().getEAllAttributes().stream()
                .filter(eAttribute -> Objects.equals(eAttribute.getName(), "domainType"))
                .findFirst();
    }

    private Optional<EObject> getContainingLibraryCandidate(EObject eObject) {
        final Optional<EObject> result;
        if (eObject instanceof RepresentationDescription || eObject instanceof Domain) {
            result = Optional.of(eObject);
        } else if (eObject == null) {
            result = Optional.empty();
        } else {
            result = this.getContainingLibraryCandidate(eObject.eContainer());
        }
        return result;
    }

}
