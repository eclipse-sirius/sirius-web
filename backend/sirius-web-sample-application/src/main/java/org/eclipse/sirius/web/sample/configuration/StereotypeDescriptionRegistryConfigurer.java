/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.sample.configuration;

import fr.obeo.dsl.designer.sample.flow.FlowFactory;

import java.util.UUID;

import org.eclipse.sirius.web.api.configuration.IStereotypeDescriptionRegistry;
import org.eclipse.sirius.web.api.configuration.IStereotypeDescriptionRegistryConfigurer;
import org.eclipse.sirius.web.api.configuration.StereotypeDescription;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * Configuration used to register new stereotype descriptions.
 *
 * @author sbegaudeau
 */
@Configuration
public class StereotypeDescriptionRegistryConfigurer implements IStereotypeDescriptionRegistryConfigurer {

    public static final UUID EMPTY_FLOW_ID = UUID.nameUUIDFromBytes("empty_flow".getBytes()); //$NON-NLS-1$

    public static final String EMPTY_FLOW_LABEL = "Empty Flow model"; //$NON-NLS-1$

    public static final UUID ROBOT_FLOW_ID = UUID.nameUUIDFromBytes("robot_flow".getBytes()); //$NON-NLS-1$

    public static final String ROBOT_FLOW_LABEL = "Robot Flow model"; //$NON-NLS-1$

    public static final UUID BIG_GUY_FLOW_ID = UUID.nameUUIDFromBytes("big_guy_flow".getBytes()); //$NON-NLS-1$

    public static final String BIG_GUY_FLOW_LABEL = "Big Guy Flow (17k elements)"; //$NON-NLS-1$

    private static final String TIMER_NAME = "siriusweb_stereotype_load"; //$NON-NLS-1$

    private final StereotypeBuilder stereotypeBuilder;

    public StereotypeDescriptionRegistryConfigurer(MeterRegistry meterRegistry) {
        this.stereotypeBuilder = new StereotypeBuilder(TIMER_NAME, meterRegistry);
    }

    @Override
    public void addStereotypeDescriptions(IStereotypeDescriptionRegistry registry) {
        registry.add(new StereotypeDescription(EMPTY_FLOW_ID, EMPTY_FLOW_LABEL, this::getEmptyFlowContent));
        registry.add(new StereotypeDescription(ROBOT_FLOW_ID, ROBOT_FLOW_LABEL, this::getRobotFlowContent));
        registry.add(new StereotypeDescription(BIG_GUY_FLOW_ID, BIG_GUY_FLOW_LABEL, this::getBigGuyFlowContent));
    }

    private String getEmptyFlowContent() {
        return this.stereotypeBuilder.getStereotypeBody(FlowFactory.eINSTANCE.createSystem());
    }

    private String getRobotFlowContent() {
        return this.stereotypeBuilder.getStereotypeBody(new ClassPathResource("robot.flow")); //$NON-NLS-1$
    }

    private String getBigGuyFlowContent() {
        return this.stereotypeBuilder.getStereotypeBody(new ClassPathResource("Big_Guy.flow")); //$NON-NLS-1$
    }
}
