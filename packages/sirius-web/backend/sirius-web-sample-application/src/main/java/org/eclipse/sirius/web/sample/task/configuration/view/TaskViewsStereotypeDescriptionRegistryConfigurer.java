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
package org.eclipse.sirius.web.sample.task.configuration.view;

import java.util.List;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.sirius.components.core.configuration.IStereotypeDescriptionRegistry;
import org.eclipse.sirius.components.core.configuration.IStereotypeDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.core.configuration.StereotypeDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.web.sample.configuration.StereotypeBuilder;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * Configuration used to register stereotype of view related to Task MM.
 *
 * @author lfasani
 */
@Configuration
public class TaskViewsStereotypeDescriptionRegistryConfigurer implements IStereotypeDescriptionRegistryConfigurer {

    private static final UUID GANTT_VIEW_ID = UUID.nameUUIDFromBytes("gantt_view".getBytes());

    private static final String GANTT_VIEW_LABEL = "Gantt View";

    private static final String TIMER_NAME = "siriusweb_stereotype_load";

    private final StereotypeBuilder stereotypeBuilder;

    public TaskViewsStereotypeDescriptionRegistryConfigurer(MeterRegistry meterRegistry) {
        this.stereotypeBuilder = new StereotypeBuilder(TIMER_NAME, meterRegistry);
    }

    @Override
    public void addStereotypeDescriptions(IStereotypeDescriptionRegistry registry) {
        registry.add(new StereotypeDescription(GANTT_VIEW_ID, GANTT_VIEW_LABEL, this::getGanttViewContent));
    }

    private String getGanttViewContent() {

        View view = this.createView();
        return this.stereotypeBuilder.getStereotypeBody(List.of(view));
    }

    private View createView() {
        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.eAdapters().add(new ECrossReferenceAdapter());

        View view = ViewFactory.eINSTANCE.createView();
        new ViewGanttDescriptionBuilder().createRepresentationDescription(view);

        return view;
    }
}
