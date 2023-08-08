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
package org.eclipse.sirius.web.sample.task.configuration;

import java.util.UUID;

import org.eclipse.sirius.components.core.configuration.IStereotypeDescriptionRegistry;
import org.eclipse.sirius.components.core.configuration.IStereotypeDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.core.configuration.StereotypeDescription;
import org.eclipse.sirius.web.sample.configuration.StereotypeBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * Configuration used to register stereotype related to Task MM.
 *
 * @author lfasani
 */
@Configuration
public class TaskStereotypeDescriptionRegistryConfigurer implements IStereotypeDescriptionRegistryConfigurer {

    private static final UUID TASK_EXAMPLE_ID = UUID.nameUUIDFromBytes("task_example".getBytes());

    private static final String TASK_EXAMPLE_LABEL = "Task model example";

    private static final String TIMER_NAME = "siriusweb_stereotype_load";

    private final StereotypeBuilder stereotypeBuilder;

    public TaskStereotypeDescriptionRegistryConfigurer(MeterRegistry meterRegistry) {
        this.stereotypeBuilder = new StereotypeBuilder(TIMER_NAME, meterRegistry);
    }

    @Override
    public void addStereotypeDescriptions(IStereotypeDescriptionRegistry registry) {
        registry.add(new StereotypeDescription(TASK_EXAMPLE_ID, TASK_EXAMPLE_LABEL, this::getTaskExampleContent));
    }

    private String getTaskExampleContent() {
        String stereotypeBody = this.stereotypeBuilder.getStereotypeBody(new ClassPathResource("model/task_example.task"));
        return stereotypeBody;
    }
}
