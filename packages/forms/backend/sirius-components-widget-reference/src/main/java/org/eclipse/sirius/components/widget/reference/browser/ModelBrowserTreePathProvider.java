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
package org.eclipse.sirius.components.widget.reference.browser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.collaborative.trees.api.ITreePathProvider;
import org.eclipse.sirius.components.collaborative.trees.dto.TreePath;
import org.eclipse.sirius.components.collaborative.trees.dto.TreePathInput;
import org.eclipse.sirius.components.collaborative.trees.dto.TreePathSuccessPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.trees.Tree;
import org.springframework.stereotype.Service;

/**
 * ITreePathProvider implementation for the ModelBrowser tree.
 *
 * @author pcdavid
 */
@Service
public class ModelBrowserTreePathProvider implements ITreePathProvider {

    private final IObjectService objectService;

    public ModelBrowserTreePathProvider(IObjectService objectService) {
        this.objectService = Objects.requireNonNull(objectService);
    }

    @Override
    public boolean canHandle(Tree tree) {
        return tree != null && List.of(ModelBrowsersDescriptionProvider.CONTAINER_DESCRIPTION_ID, ModelBrowsersDescriptionProvider.REFERENCE_DESCRIPTION_ID)
                .contains(tree.getDescriptionId());
    }

    @Override
    public IPayload handle(IEditingContext editingContext, Tree tree, TreePathInput input) {
        int maxDepth = 0;
        Set<String> allAncestors = new HashSet<>();
        for (String selectionEntryId : input.selectionEntryIds()) {
            List<String> itemAncestors = this.getAncestors(editingContext, selectionEntryId);
            allAncestors.addAll(itemAncestors);
            maxDepth = Math.max(maxDepth, itemAncestors.size());
        }
        return new TreePathSuccessPayload(input.id(), new TreePath(allAncestors.stream().toList(), maxDepth));
    }

    public List<String> getAncestors(IEditingContext editingContext, String selectionEntryId) {
        List<String> ancestorsIds = new ArrayList<>();

        var optionalSemanticObject = this.objectService.getObject(editingContext, selectionEntryId);

        Optional<Object> optionalObject = Optional.empty();
        if (optionalSemanticObject.isPresent()) {
            // The first parent of a semantic object item is the item for its actual container
            optionalObject = optionalSemanticObject.filter(EObject.class::isInstance).map(EObject.class::cast)
                    .map(eObject -> Optional.<Object>ofNullable(eObject.eContainer()).orElse(eObject.eResource()));
        }

        while (optionalObject.isPresent()) {
            ancestorsIds.add(this.getItemId(optionalObject.get()));
            optionalObject = optionalObject.filter(EObject.class::isInstance).map(EObject.class::cast)
                    .map(eObject -> Optional.<Object>ofNullable(eObject.eContainer()).orElse(eObject.eResource()));
        }
        return ancestorsIds;
    }

    private String getItemId(Object object) {
        String result = null;
        if (object instanceof Resource resource) {
            result = resource.getURI().path().substring(1);
        } else if (object instanceof EObject) {
            result = this.objectService.getId(object);
        }
        return result;
    }
}
