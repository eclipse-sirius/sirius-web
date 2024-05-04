/*******************************************************************************
 * Copyright (c) 2024, 2024 Obeo.
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
package org.eclipse.sirius.web.papaya.configuration;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.sirius.components.papaya.provider.PapayaItemProviderAdapterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Used to register everything to use Papaya models in Sirius Web.
 *
 * @author sbegaudeau
 */
@Configuration
public class PapayaEMFConfiguration {

    @Bean
    public AdapterFactory papayaAdapterFactory() {
        return new PapayaItemProviderAdapterFactory();
    }
}
