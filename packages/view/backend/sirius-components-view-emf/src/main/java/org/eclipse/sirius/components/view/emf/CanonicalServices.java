/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.view.emf;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IObjectService;

/**
 * Implementation of the default/canonical behaviors suitable to be invoked as services from a plain AQL expression.
 *
 * @author pcdavid
 */
public class CanonicalServices {
    private final IObjectService objectService;

    private final IEditService editService;

    public CanonicalServices(IObjectService objectService, IEditService editService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
    }

    /**
     * Invoke as aql:self.defaultCreateNode(nodeDescription).
     */
    public EObject defaultCreateNode(EObject self, org.eclipse.sirius.components.view.NodeDescription nodeDescription) {
        String domainType = nodeDescription.getDomainType();
        var optionalSemanticElement = this.createSemanticInstance(self, domainType);
        optionalSemanticElement.ifPresent(instance -> this.addInParent(self, instance));
        return optionalSemanticElement.orElse(self);
    }

    /**
     * Invoke as aql:semanticEdgeSource.defaultCreateEdge(edgeDescription, semanticEdgeTarget).
     */
    public EObject defaultCreateEdge(EObject semanticEdgeSource, org.eclipse.sirius.components.view.EdgeDescription edgeDescription, EObject semanticEdgeTarget) {
        if (edgeDescription.isIsDomainBasedEdge()) {
            this.createSemanticInstance(semanticEdgeSource, edgeDescription.getDomainType()).ifPresent(instance -> {
                this.addInParent(semanticEdgeSource.eContainer(), instance);
                this.addReferenceTo(instance, semanticEdgeSource);
                this.addReferenceTo(instance, semanticEdgeTarget);
            });
        } else {
            this.addReferenceTo(semanticEdgeSource, semanticEdgeTarget);
        }
        return semanticEdgeSource;
    }

    /**
     * Invoke as aql:self.defaultEditLabel(newLabel).
     */
    public EObject defaultEditLabel(EObject self, String newLabel) {
        Optional<String> optionalLabelField = this.objectService.getLabelField(self);
        if (optionalLabelField.isPresent()) {
            this.editService.editLabel(self, optionalLabelField.get(), newLabel);
        }
        return self;
    }

    /**
     * Invoke as aql:self.defaultDelete().
     */
    public EObject defaultDelete(EObject self) {
        this.editService.delete(self);
        return self;
    }

    private Optional<EObject> createSemanticInstance(EObject self, String domainType) {
        EPackage ePackage = self.eClass().getEPackage();
        final String shortTypeName;
        String[] segments = domainType.split("::");
        if (segments.length == 2) {
            shortTypeName = segments[1];
        } else {
            shortTypeName = domainType;
        }
        // @formatter:off
        var optionalKlass = ePackage
                          .getEClassifiers().stream()
                          .filter(classifier -> classifier instanceof EClass && Objects.equals(shortTypeName, classifier.getName()))
                          .map(EClass.class::cast)
                          .findFirst();
        // @formatter:on
        if (optionalKlass.isPresent()) {
            EClass klass = optionalKlass.get();
            EObject instance = ePackage.getEFactoryInstance().create(klass);
            // @formatter:off
            // Assume the first stringly-typed attribute represents the object's name/label
            klass.getEAllAttributes().stream().filter(attr -> Objects.equals(String.class, attr.getEType().getInstanceClass())).findFirst().ifPresent(labelAttribute -> {
                instance.eSet(labelAttribute, "New " + klass.getName());
            });
            // @formatter:on
            return Optional.of(instance);
        } else {
            return Optional.empty();
        }
    }

    private void addInParent(EObject parent, EObject instance) {
        parent.eClass().getEAllContainments().stream().filter(ref -> ref.getEType().isInstance(instance)).findFirst().ifPresent(ref -> {
            if (ref.isMany()) {
                ((EList<EObject>) parent.eGet(ref)).add(instance);
            } else {
                parent.eSet(ref, instance);
            }
        });
    }

    private void addReferenceTo(EObject source, EObject target) {
        source.eClass().getEAllReferences().stream().filter(ref -> ref.getEType().isInstance(target)).findFirst().ifPresent(ref -> {
            if (ref.isMany()) {
                ((EList<EObject>) source.eGet(ref)).add(target);
            } else {
                source.eSet(ref, target);
            }
        });
    }

}
