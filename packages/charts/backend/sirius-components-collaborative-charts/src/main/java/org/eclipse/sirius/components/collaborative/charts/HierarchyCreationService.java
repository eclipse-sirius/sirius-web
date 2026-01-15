/*******************************************************************************
 * Copyright (c) 2022, 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.charts;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.eclipse.sirius.components.charts.hierarchy.Hierarchy;
import org.eclipse.sirius.components.charts.hierarchy.components.HierarchyComponent;
import org.eclipse.sirius.components.charts.hierarchy.components.HierarchyComponentProps;
import org.eclipse.sirius.components.charts.hierarchy.descriptions.HierarchyDescription;
import org.eclipse.sirius.components.charts.hierarchy.renderer.HierarchyRenderer;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.charts.api.IHierarchyCreationService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

/**
 * Service used to create hierarchy representations.
 *
 * @author sbegaudeau
 */
@Service
public class HierarchyCreationService implements IHierarchyCreationService {

    private final Timer timer;

    public HierarchyCreationService(MeterRegistry meterRegistry) {
        this.timer = Timer.builder(Monitoring.REPRESENTATION_EVENT_PROCESSOR_REFRESH)
                .tag(Monitoring.NAME, "hierarchy")
                .register(meterRegistry);
    }

    @Override
    public Hierarchy create(IEditingContext editingContext, HierarchyDescription hierarchyDescription, Object targetObject, HierarchyContext hierarchyContext) {
        long start = System.currentTimeMillis();

        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, targetObject);
        variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);

        Optional<Hierarchy> optionalPreviousHierarchy = Optional.ofNullable(hierarchyContext).map(HierarchyContext::hierarchy);

        HierarchyComponentProps props = new HierarchyComponentProps(variableManager, hierarchyDescription, optionalPreviousHierarchy);
        Element element = new Element(HierarchyComponent.class, props);

        Hierarchy newHierarchy = new HierarchyRenderer().render(element);

        long end = System.currentTimeMillis();
        this.timer.record(end - start, TimeUnit.MILLISECONDS);
        return newHierarchy;
    }

}
