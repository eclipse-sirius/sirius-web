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
package org.eclipse.sirius.components.diagrams.layout.configuration;

import java.util.List;

import org.eclipse.sirius.components.diagrams.layout.ILayoutEngineHandlerSwitchProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.IBorderNodeLayoutEngine;
import org.eclipse.sirius.components.diagrams.layout.incremental.LayoutEngineHandlerSwitch;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ICustomNodeLabelPositionProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The configuration used to provide services related to the incremental layout.
 *
 * @author gcoutable
 */
@Configuration
public class LayoutConfiguration {

    @Bean
    ILayoutEngineHandlerSwitchProvider layoutEngineHandlerSwitchProvider(IBorderNodeLayoutEngine borderNodeLayoutEngine, List<ICustomNodeLabelPositionProvider> customLabelPositionProviders) {
        return () -> new LayoutEngineHandlerSwitch(borderNodeLayoutEngine, customLabelPositionProviders);
    }

}
