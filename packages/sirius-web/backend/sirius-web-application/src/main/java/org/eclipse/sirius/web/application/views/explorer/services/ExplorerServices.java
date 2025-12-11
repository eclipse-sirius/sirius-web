/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
package org.eclipse.sirius.web.application.views.explorer.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.core.api.IContentService;
import org.eclipse.sirius.components.core.api.IDefaultObjectSearchService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IReadOnlyObjectPredicate;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerServices;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Services used to perform operations in the explorer.
 *
 * @author gdaniel
 */
@Service
public class ExplorerServices implements IExplorerServices {

    private final IIdentityService identityService;

    private final IObjectSearchService objectSearchService;

    private final IContentService contentService;

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final IReadOnlyObjectPredicate readOnlyObjectPredicate;

    private final IDefaultObjectSearchService defaultObjectSearchService;

    public ExplorerServices(IIdentityService identityService, IObjectSearchService objectSearchService, IContentService contentService, IRepresentationMetadataSearchService representationMetadataSearchService, IReadOnlyObjectPredicate readOnlyObjectPredicate, IDefaultObjectSearchService defaultObjectSearchService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.contentService = Objects.requireNonNull(contentService);
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
        this.readOnlyObjectPredicate = Objects.requireNonNull(readOnlyObjectPredicate);
        this.defaultObjectSearchService = Objects.requireNonNull(defaultObjectSearchService);
    }

    @Override
    public String getTreeItemId(Object self) {
        String id = null;
        if (self instanceof RepresentationMetadata representationMetadata) {
            id = representationMetadata.getRepresentationMetadataId().toString();
        } else if (self instanceof Resource || self instanceof EObject) {
            id = this.identityService.getId(self);
        }
        return id;
    }

    @Override
    public String getKind(Object self) {
        String kind = "";
        if (self instanceof RepresentationMetadata representationMetadata) {
            kind = representationMetadata.getKind();
        } else if (self instanceof Resource) {
            kind = ExplorerDescriptionProvider.DOCUMENT_KIND;
        } else {
            kind = this.identityService.getKind(self);
        }
        return kind;
    }

    @Override
    public boolean isDeletable(Object self) {
        return !this.readOnlyObjectPredicate.test(self);
    }

    @Override
    public boolean isSelectable(Object self) {
        return true;
    }

    @Override
    public Object getTreeItemObject(String treeItemId, IEditingContext editingContext) {
        Object result = null;
        if (editingContext != null && treeItemId != null) {
            // Fast path to avoid potentially costly IObjectSearchServiceDelegates
            var optionalObject = this.defaultObjectSearchService.getObject(editingContext, treeItemId);
            if (optionalObject.isEmpty()) {
                // Slow path: fallback to the full algorithm
                optionalObject = this.objectSearchService.getObject(editingContext, treeItemId);
            }

            if (optionalObject.isPresent()) {
                result = optionalObject.get();
            } else {
                var optionalEditingDomain = Optional.of(editingContext)
                        .filter(IEMFEditingContext.class::isInstance)
                        .map(IEMFEditingContext.class::cast)
                        .map(IEMFEditingContext::getDomain);

                if (optionalEditingDomain.isPresent()) {
                    var editingDomain = optionalEditingDomain.get();
                    ResourceSet resourceSet = editingDomain.getResourceSet();
                    URI uri = new JSONResourceFactory().createResourceURI(treeItemId);

                    result = resourceSet.getResources().stream()
                            .filter(resource -> resource.getURI().equals(uri))
                            .findFirst()
                            .orElse(null);
                }
            }
        }
        return result;
    }

    @Override
    public Object getParent(Object self, String treeItemId, IEditingContext editingContext) {
        Object result = null;

        if (self instanceof RepresentationMetadata && treeItemId != null && editingContext != null) {
            var optionalSemanticDataId = new UUIDParser().parse(editingContext.getId());
            var optionalRepresentationMetadataId = new UUIDParser().parse(treeItemId);
            if (optionalSemanticDataId.isPresent() && optionalRepresentationMetadataId.isPresent()) {
                var semanticDataId = optionalSemanticDataId.get();
                var representationMetadataId = optionalRepresentationMetadataId.get();
                result = this.representationMetadataSearchService.findMetadataById(AggregateReference.to(semanticDataId), representationMetadataId)
                        .map(RepresentationMetadata::getTargetObjectId)
                        .flatMap(targetObjectId -> this.objectSearchService.getObject(editingContext, targetObjectId))
                        .orElse(null);
            }
        } else if (self instanceof EObject eObject) {
            Object semanticContainer = eObject.eContainer();
            if (semanticContainer == null) {
                semanticContainer = eObject.eResource();
            }
            result = semanticContainer;
        }
        return result;
    }

    @Override
    public boolean hasChildren(Object self, IEditingContext editingContext, List<RepresentationMetadata> existingRepresentations) {
        boolean hasChildren = false;
        if (self instanceof Resource resource) {
            hasChildren = !resource.getContents().isEmpty();
        } else if (self instanceof EObject eObject) {
            hasChildren = !eObject.eContents().isEmpty();

            var optionalSemanticDataId = new UUIDParser().parse(editingContext.getId());
            if (!hasChildren && optionalSemanticDataId.isPresent()) {
                String id = this.identityService.getId(eObject);
                hasChildren = this.findRepresentationsForTargetObjectId(existingRepresentations, id).findAny().isPresent();
            }
        }
        return hasChildren;
    }

    @Override
    public List<Object> getDefaultChildren(Object self, IEditingContext editingContext, List<String> expandedIds, List<RepresentationMetadata> existingRepresentations) {
        List<Object> result = new ArrayList<>();
        if (editingContext != null) {
            String id = this.getTreeItemId(self);
            if (expandedIds.contains(id)) {
                if (self instanceof Resource resource) {
                    result.addAll(resource.getContents());
                } else if (self instanceof EObject) {
                    var optionalSemanticDataId = new UUIDParser().parse(editingContext.getId());

                    if (optionalSemanticDataId.isPresent()) {
                        this.findRepresentationsForTargetObjectId(existingRepresentations, id)
                            .sorted(Comparator.comparing(RepresentationMetadata::getLabel))
                            .forEachOrdered(result::add);
                    }

                    List<Object> contents = this.contentService.getContents(self);
                    result.addAll(contents);
                }
            }
        }
        return result;
    }

    private Stream<RepresentationMetadata> findRepresentationsForTargetObjectId(List<RepresentationMetadata> existingRepresentations, String targetObjectd) {
        return existingRepresentations.stream().filter(representationMetadata -> representationMetadata.getTargetObjectId().equals(targetObjectd));
    }
}
