/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.widget.reference.api;

import java.util.List;

import org.eclipse.sirius.components.core.api.IEditingContext;

/**
 * Provides the list of root elements used to select or add a reference value.
 *
 * @author Arthur Daussy
 */
public interface IReferenceWidgetRootCandidateSearchProvider {

    /**
     * Returns whether this provider can handle the given representation description.
     * @param descriptionId representation description id
     * @return {@code true} if instance can handle the given representation description or {@code false} otherwise.
     */
    boolean canHandle(String descriptionId);

    /**
     * Returns the list of tree root elements used to set or add a reference value in the reference widget.
     * @param targetElement
     * @param descriptionId
     * @param editingContext
     * @return the list of tree root elements used to set or add a reference value in the reference widget.
     */
    List<? extends Object> getRootElementsForReference(Object targetElement, String descriptionId, IEditingContext editingContext);
}
