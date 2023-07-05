/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.trees.api;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.description.TreeDescription;

import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Interface of all the tree event handlers.
 *
 * @author sbegaudeau
 */
public interface ITreeEventHandler {
    boolean canHandle(ITreeInput treeInput);

    void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, TreeDescription treeDescription, Tree tree, ITreeInput treeInput);
}
