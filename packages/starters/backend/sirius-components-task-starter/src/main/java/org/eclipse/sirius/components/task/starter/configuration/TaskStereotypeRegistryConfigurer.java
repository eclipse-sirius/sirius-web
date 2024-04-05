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
package org.eclipse.sirius.components.task.starter.configuration;

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.web.services.api.document.IStereotypeRegistry;
import org.eclipse.sirius.web.services.api.document.IStereotypeRegistryConfigurer;
import org.eclipse.sirius.web.services.api.document.Stereotype;
import org.eclipse.sirius.components.task.starter.helper.StereotypeBuilder;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * Configuration used to register stereotype related to Task MM.
 *
 * @author lfasani
 */
@Configuration
public class TaskStereotypeRegistryConfigurer implements IStereotypeRegistryConfigurer {

    private static final UUID TASK_EXAMPLE_ID = UUID.nameUUIDFromBytes("task_example".getBytes());

    private static final String TASK_EXAMPLE_LABEL = "Task Model Sample";

    private static final String TIMER_NAME = "siriusweb_stereotype_load";

    private final StereotypeBuilder stereotypeBuilder;

    public TaskStereotypeRegistryConfigurer(MeterRegistry meterRegistry) {
        this.stereotypeBuilder = new StereotypeBuilder(TIMER_NAME, meterRegistry);
    }

    @Override
    public void addStereotypes(IStereotypeRegistry registry) {
        registry.add(new Stereotype(TASK_EXAMPLE_ID, TASK_EXAMPLE_LABEL, this::getTaskExampleContent));
    }

    private String getTaskExampleContent() {
        String stereotypeBody = this.stereotypeBuilder.getStereotypeBody(List.of(new TaskExampleBuilder().getContent()));
        return stereotypeBody;
    }
}
