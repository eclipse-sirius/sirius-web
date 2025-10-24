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
package org.eclipse.sirius.web.application.views.explorer.services.api;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;

/**
 * Services used to perform operations in the explorer.
 *
 * @author gdaniel
 */
public interface IExplorerServices {

    String getTreeItemId(Object self);

    String getKind(Object self);

    boolean isDeletable(Object self);

    boolean isSelectable(Object self);

    Object getTreeItemObject(String treeItemId, IEditingContext editingContext);

    Object getParent(Object self, String treeItemId, IEditingContext editingContext);

    boolean hasChildren(Object self, IEditingContext editingContext, List<RepresentationMetadata> existingRepresentations);

    /**
     * Returns the un-filtered list of children for {@code self}.
     *
     * @param self
     *            the object to retrieve the children from
     * @param editingContext
     *            the editing context
     * @param expandedIds
     *            the list of expanded tree items
     * @param existingRepresentations
     *            the list of all currently existing representation in the editing context
     * @return the list of children
     */
    List<Object> getDefaultChildren(Object self, IEditingContext editingContext, List<String> expandedIds, List<RepresentationMetadata> existingRepresentations);

    /**
     * Returns the un-filtered list of root elements.
     *
     * @param editingContext
     *            the editing context
     * @return the list of root elements
     */
    List<Resource> getDefaultElements(IEditingContext editingContext);
}
