/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.trees.api;

import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.trees.Tree;

import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Interface of all the tree event handlers.
 *
 * @author sbegaudeau
 */
public interface ITreeEventHandler {
    boolean canHandle(ITreeInput treeInput);

    void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, Tree tree, ITreeInput treeInput);
}
