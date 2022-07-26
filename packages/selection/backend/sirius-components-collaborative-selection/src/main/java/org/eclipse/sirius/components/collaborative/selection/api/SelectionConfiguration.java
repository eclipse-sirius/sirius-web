/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.sirius.components.collaborative.selection.api;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.IRepresentationConfiguration;

/**
 * The configuration used to create a selection event processor.
 *
 * @author arichard
 */
public class SelectionConfiguration implements IRepresentationConfiguration {

    private final String id;

    private final String selectionId;

    private final String targetObjectId;

    public SelectionConfiguration(String id, String selectionId, String targetObjectId) {
        this.id = Objects.requireNonNull(id);
        this.selectionId = Objects.requireNonNull(selectionId);
        this.targetObjectId = Objects.requireNonNull(targetObjectId);
    }

    @Override
    public String getId() {
        return this.id;
    }

    public String getSelectionId() {
        return this.selectionId;
    }

    public String getTargetObjectId() {
        return this.targetObjectId;
    }

}
