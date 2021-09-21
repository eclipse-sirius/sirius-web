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
package org.eclipse.sirius.web.emf.view;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.web.core.api.IEditService;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.representations.IStatus;
import org.eclipse.sirius.web.representations.Success;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.view.EdgeDescription;

/**
 * Canonical implementations of the basic behaviors/tools expected on a diagram. The implementation makes some
 * assumptions on both the structure of the domain and that of the view definition, and may not work (or not as
 * expected) on quite normal but non trivial cases.
 *
 * @author pcdavid
 */
public class CanonicalBehaviors {
    private final IObjectService objectService;

    private final IEditService editService;

    public CanonicalBehaviors(IObjectService objectService, IEditService editService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
    }

    public IStatus createNewNode(org.eclipse.sirius.web.view.NodeDescription nodeDescription, VariableManager variableManager) {
        EObject self = variableManager.get(VariableManager.SELF, EObject.class).orElse(null);
        String domainType = nodeDescription.getDomainType();
        this.createSemanticInstance(self, domainType).ifPresent(instance -> this.addInParent(self, instance));
        return new Success();
    }

    public IStatus createNewEdge(VariableManager variableManager, EdgeDescription edgeDescription) {
        EObject semanticSource = variableManager.get(org.eclipse.sirius.web.diagrams.description.EdgeDescription.SEMANTIC_EDGE_SOURCE, EObject.class).get();
        EObject semanticTarget = variableManager.get(org.eclipse.sirius.web.diagrams.description.EdgeDescription.SEMANTIC_EDGE_TARGET, EObject.class).get();
        if (edgeDescription.isIsDomainBasedEdge()) {
            this.createSemanticInstance(semanticSource, edgeDescription.getDomainType()).ifPresent(instance -> {
                this.addInParent(semanticSource, instance);
                this.addReferenceTo(instance, semanticSource);
                this.addReferenceTo(instance, semanticTarget);
            });
        } else {
            this.addReferenceTo(semanticSource, semanticTarget);
        }
        return new Success();
    }

    public IStatus editLabel(VariableManager variableManager, String newLabel) {
        this.self(variableManager).ifPresent(self -> {
            Optional<String> optionalLabelField = this.objectService.getLabelField(self);
            if (optionalLabelField.isPresent()) {
                this.editService.editLabel(self, optionalLabelField.get(), newLabel);
            }
        });
        return new Success();
    }

    public IStatus deleteElement(VariableManager variableManager) {
        this.self(variableManager).ifPresent(this.editService::delete);
        return new Success();
    }

    private Optional<Object> self(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class);
    }

    private Optional<EObject> createSemanticInstance(EObject self, String domainType) {
        EPackage ePackage = self.eClass().getEPackage();
        final String shortTypeName;
        String[] segments = domainType.split("::"); //$NON-NLS-1$
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
                instance.eSet(labelAttribute, "New " + klass.getName()); //$NON-NLS-1$
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
