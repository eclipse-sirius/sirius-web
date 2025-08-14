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
package org.eclipse.sirius.web.services.diagrams;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiFunction;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.springframework.stereotype.Service;

/**
 * Used to create a relation based edge instance model.
 *
 * @author arichard
 */
@Service
public class RelationBasedEdgeInstanceProvider implements BiFunction<IEditingContext, IInput, IPayload> {

    private final IIdentityService identityService;

    public RelationBasedEdgeInstanceProvider(IIdentityService identityService) {
        this.identityService = Objects.requireNonNull(identityService);
    }

    @Override
    public IPayload apply(IEditingContext editingContext, IInput input) {
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            ResourceSet resourceSet = emfEditingContext.getDomain().getResourceSet();
            EPackage ePackage = resourceSet.getPackageRegistry().getEPackage("domain://rbeDomain");
            EFactory eFactory = ePackage.getEFactoryInstance();

            EClassifier rootEClassifier = ePackage.getEClassifier("Root");
            assertThat(rootEClassifier).isInstanceOf(EClass.class);
            EClass rootEClass = (EClass) rootEClassifier;

            EClassifier entityEClassifier = ePackage.getEClassifier("Entity");
            assertThat(entityEClassifier).isInstanceOf(EClass.class);
            EClass entityEClass = (EClass) entityEClassifier;

            var entitiesEStructuralFeature = rootEClass.getEStructuralFeature("entities");
            var subEntitiesEStructuralFeature = entityEClass.getEStructuralFeature("subEntities");
            var nameEStructuralFeature = entityEClass.getEStructuralFeature("name");

            EObject entity3Object = eFactory.create(entityEClass);
            entity3Object.eSet(nameEStructuralFeature, "Entity3");

            EObject entity2Object = eFactory.create(entityEClass);
            entity2Object.eSet(nameEStructuralFeature, "Entity2");
            ((List<EObject>) entity2Object.eGet(subEntitiesEStructuralFeature)).add(entity3Object);

            EObject entity1Object = eFactory.create(entityEClass);
            entity1Object.eSet(nameEStructuralFeature, "Entity1");
            ((List<EObject>) entity1Object.eGet(subEntitiesEStructuralFeature)).add(entity2Object);

            EObject rootObject = eFactory.create(rootEClass);
            ((List<EObject>) rootObject.eGet(entitiesEStructuralFeature)).add(entity1Object);

            UUID documentId = UUID.randomUUID();
            var resource = new JSONResourceFactory().createResourceFromPath(documentId.toString());
            var resourceMetadataAdapter = new ResourceMetadataAdapter("RBE_Document");
            resource.eAdapters().add(resourceMetadataAdapter);
            emfEditingContext.getDomain().getResourceSet().getResources().add(resource);
            resource.getContents().add(rootObject);

            var rootObjectId = this.identityService.getId(rootObject);

            return new RelationBasedEdgeInstanceCreatedPayload(input.id(), rootObjectId);
        }
        return new ErrorPayload(input.id(), "Invalid editing context");
    }
}
