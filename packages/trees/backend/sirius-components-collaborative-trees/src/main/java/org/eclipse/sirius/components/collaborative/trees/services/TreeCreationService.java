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
package org.eclipse.sirius.components.collaborative.trees.services;

import java.util.concurrent.TimeUnit;

import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.components.trees.renderer.TreeRenderer;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

/**
 * Service used to create tree representations.
 *
 * @author Jerome Gout
 */
@Service
public class TreeCreationService {

    private final Timer timer;

    public TreeCreationService(MeterRegistry meterRegistry) {
        this.timer = Timer.builder(Monitoring.REPRESENTATION_EVENT_PROCESSOR_REFRESH).tag(Monitoring.NAME, "tree").register(meterRegistry);
    }

    public Tree create(Object targetObject, TreeDescription treeDescription, IEditingContext editingContext) {
        return this.doRender(targetObject, editingContext, treeDescription);
    }

    private Tree doRender(Object targetObject, IEditingContext editingContext, TreeDescription treeDescription) {
        long start = System.currentTimeMillis();

        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, targetObject);
        variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);

        Tree newTree = new TreeRenderer(variableManager, treeDescription).render();

        long end = System.currentTimeMillis();
        this.timer.record(end - start, TimeUnit.MILLISECONDS);
        return newTree;
    }

}
