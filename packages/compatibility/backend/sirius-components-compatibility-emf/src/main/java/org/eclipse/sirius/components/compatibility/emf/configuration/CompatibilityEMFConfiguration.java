/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo and others.
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
package org.eclipse.sirius.components.compatibility.emf.configuration;

import org.eclipse.sirius.common.tools.internal.ecore.EPackageHelper;
import org.eclipse.sirius.components.emf.services.ISuggestedRootObjectTypesProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of the EMF beans.
 *
 * @author sbegaudeau
 */
@Configuration
public class CompatibilityEMFConfiguration {
    @Bean
    public ISuggestedRootObjectTypesProvider suggestedRootObjectTypesProvider() {
        return EPackageHelper::getEClassRootElements;
    }
}
