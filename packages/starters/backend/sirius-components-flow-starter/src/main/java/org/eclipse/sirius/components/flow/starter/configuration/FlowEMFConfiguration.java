/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.components.flow.starter.configuration;

import fr.obeo.dsl.designer.sample.flow.provider.FlowItemProviderAdapterFactory;

import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of the EMF support for Flow MM.
 *
 * @author frouene
 */
@Configuration
public class FlowEMFConfiguration {

    @Bean
    public ComposedAdapterFactory.Descriptor flowAdapterFactoryDescriptor() {
        return FlowItemProviderAdapterFactory::new;
    }
}
