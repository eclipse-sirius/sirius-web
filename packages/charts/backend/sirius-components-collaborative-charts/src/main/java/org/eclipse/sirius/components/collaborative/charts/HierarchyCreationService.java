/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.eclipse.sirius.components.charts.hierarchy.Hierarchy;
import org.eclipse.sirius.components.charts.hierarchy.components.HierarchyComponent;
import org.eclipse.sirius.components.charts.hierarchy.components.HierarchyComponentProps;
import org.eclipse.sirius.components.charts.hierarchy.descriptions.HierarchyDescription;
import org.eclipse.sirius.components.charts.hierarchy.renderer.HierarchyRenderer;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
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
public class HierarchyCreationService {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final IObjectService objectService;

    private final Timer timer;

    public HierarchyCreationService(IRepresentationDescriptionSearchService representationDescriptionSearchService, IRepresentationPersistenceService representationPersistenceService,
            IObjectService objectService, MeterRegistry meterRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.objectService = Objects.requireNonNull(objectService);
        // @formatter:off
        this.timer = Timer.builder(Monitoring.REPRESENTATION_EVENT_PROCESSOR_REFRESH)
                .tag(Monitoring.NAME, "hierarchy")
                .register(meterRegistry);
        // @formatter:on
    }

    public Hierarchy create(String label, Object targetObject, HierarchyDescription hierarchyDescription, IEditingContext editingContext) {
        Hierarchy newHierarchy = this.doRender(label, targetObject, editingContext, hierarchyDescription, Optional.empty());
        return newHierarchy;
    }

    public Optional<Hierarchy> refresh(IEditingContext editingContext, HierarchyContext hierarchyContext) {
        Hierarchy previousHierarchy = hierarchyContext.getHierarchy();
        var optionalObject = this.objectService.getObject(editingContext, previousHierarchy.getTargetObjectId());
        // @formatter:off
        var optionalHierarchyDescription = this.representationDescriptionSearchService.findById(editingContext, previousHierarchy.getDescriptionId())
                .filter(HierarchyDescription.class::isInstance)
                .map(HierarchyDescription.class::cast);
        // @formatter:on

        if (optionalObject.isPresent() && optionalHierarchyDescription.isPresent()) {
            Object object = optionalObject.get();
            HierarchyDescription hierarchyDescription = optionalHierarchyDescription.get();
            Hierarchy hierarchy = this.doRender(previousHierarchy.getLabel(), object, editingContext, hierarchyDescription, Optional.of(hierarchyContext));
            return Optional.of(hierarchy);
        }
        return Optional.empty();
    }

    private Hierarchy doRender(String label, Object targetObject, IEditingContext editingContext, HierarchyDescription hierarchyDescription, Optional<HierarchyContext> optionalHierarchyContext) {
        long start = System.currentTimeMillis();

        VariableManager variableManager = new VariableManager();
        variableManager.put(HierarchyDescription.LABEL, label);
        variableManager.put(VariableManager.SELF, targetObject);
        variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);

        Optional<Hierarchy> optionalPreviousHierarchy = optionalHierarchyContext.map(HierarchyContext::getHierarchy);

        HierarchyComponentProps props = new HierarchyComponentProps(variableManager, hierarchyDescription, optionalPreviousHierarchy);
        Element element = new Element(HierarchyComponent.class, props);

        Hierarchy newHierarchy = new HierarchyRenderer().render(element);

        this.representationPersistenceService.save(null, editingContext, newHierarchy);

        long end = System.currentTimeMillis();
        this.timer.record(end - start, TimeUnit.MILLISECONDS);
        return newHierarchy;
    }

}
