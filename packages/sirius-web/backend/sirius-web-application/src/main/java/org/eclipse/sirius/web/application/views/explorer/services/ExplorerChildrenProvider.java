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
package org.eclipse.sirius.web.application.views.explorer.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.renderer.TreeRenderer;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerChildrenProvider;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerTreeItemAlteredContentProvider;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationDataSearchService;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IExplorerChildrenProvider} for the Sirius Web Explorer view.
 *
 * @author arichard
 */
@Service
public class ExplorerChildrenProvider implements IExplorerChildrenProvider {

    private final IObjectService objectService;

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final IRepresentationDataSearchService representationDataSearchService;

    private final List<IExplorerTreeItemAlteredContentProvider> alteredContentProviders;

    public ExplorerChildrenProvider(IObjectService objectService, IRepresentationMetadataSearchService representationMetadataSearchService, IRepresentationDataSearchService representationDataSearchService, List<IExplorerTreeItemAlteredContentProvider> alteredContentProviders) {
        this.objectService = Objects.requireNonNull(objectService);
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
        this.representationDataSearchService = Objects.requireNonNull(representationDataSearchService);
        this.alteredContentProviders = Objects.requireNonNull(alteredContentProviders);
    }

    @Override
    public boolean hasChildren(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);

        boolean hasChildren = false;
        if (self instanceof Resource resource) {
            hasChildren = !resource.getContents().isEmpty();
        } else if (self instanceof EObject eObject) {
            hasChildren = !eObject.eContents().isEmpty();

            if (!hasChildren) {
                String id = this.objectService.getId(eObject);
                hasChildren = this.representationDataSearchService.existAnyRepresentationForTargetObjectId(id);
            }
        }
        return hasChildren;
    }

    @Override
    public List<Object> getChildren(VariableManager variableManager) {
        List<Object> children = new ArrayList<>();
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        if (self != null) {
            children = this.getDefaultChildren(variableManager);
            List<String> activeFilterIds = this.getActiveFilterIds(variableManager);

            var providers = this.alteredContentProviders.stream()
                    .filter(provider -> provider.canHandle(self, activeFilterIds))
                    .toList();
            for (IExplorerTreeItemAlteredContentProvider provider : providers) {
                children = provider.apply(children, variableManager);
            }
        }
        return children;
    }

    private List<Object> getDefaultChildren(VariableManager variableManager) {
        List<Object> result = new ArrayList<>();

        List<String> expandedIds = new ArrayList<>();
        Object objects = variableManager.getVariables().get(TreeRenderer.EXPANDED);
        if (objects instanceof List<?> list) {
            expandedIds = list.stream()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .toList();
        }

        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);

        if (optionalEditingContext.isPresent()) {
            IEditingContext editingContext = optionalEditingContext.get();

            String id = this.getTreeItemId(variableManager);
            if (expandedIds.contains(id)) {
                Object self = variableManager.getVariables().get(VariableManager.SELF);

                if (self instanceof Resource resource) {
                    result.addAll(resource.getContents());
                } else if (self instanceof EObject) {
                    var representationMetadata = new ArrayList<>(this.representationMetadataSearchService.findAllByTargetObjectId(editingContext, id));
                    representationMetadata.sort(Comparator.comparing(RepresentationMetadata::getLabel));
                    result.addAll(representationMetadata);
                    List<Object> contents = this.objectService.getContents(self);
                    result.addAll(contents);
                }
            }
        }
        return result;
    }

    private List<String> getActiveFilterIds(VariableManager variableManager) {
        List<String> activeFilterIds;
        Object objects = variableManager.getVariables().get(TreeRenderer.ACTIVE_FILTER_IDS);
        if (objects instanceof List<?> list) {
            activeFilterIds = list.stream().filter(String.class::isInstance).map(String.class::cast).toList();
        } else {
            activeFilterIds = new ArrayList<>();
        }
        return activeFilterIds;
    }

    private String getTreeItemId(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);

        String id = null;
        if (self instanceof RepresentationMetadata representationMetadata) {
            id = representationMetadata.getId();
        } else if (self instanceof Resource resource) {
            id = resource.getURI().path().substring(1);
        } else if (self instanceof EObject) {
            id = this.objectService.getId(self);
        }
        return id;
    }
}
