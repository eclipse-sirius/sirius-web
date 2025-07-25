/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.tree;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.view.tree.TreeDescription;
import org.springframework.stereotype.Service;

/**
 * descriptionId for TreeDescription.
 *
 * @author Jerome Gout
 */
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class TreeIdProvider implements ITreeIdProvider {

    private final IIdentityService identityService;

    public TreeIdProvider(IIdentityService identityService) {
        this.identityService = Objects.requireNonNull(identityService);
    }

    @Override
    public String getId(TreeDescription treeDescription) {
        String sourceId = this.getSourceIdFromElementDescription(treeDescription);
        String sourceElementId = this.identityService.getId(treeDescription);
        return TREE_DESCRIPTION_KIND + "&" + SOURCE_KIND + "=" + VIEW_SOURCE_KIND + "&" + SOURCE_ID + "=" + sourceId + "&" + SOURCE_ELEMENT_ID + "=" + sourceElementId;
    }

    private String getSourceIdFromElementDescription(EObject elementDescription) {
        return elementDescription.eResource().getURI().toString().split("///")[1];
    }
}
