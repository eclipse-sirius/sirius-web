/*******************************************************************************
 * Copyright (c) 2019, 2021 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.web.emf.configuration;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.web.compat.api.ICanCreateDiagramPredicateFactory;
import org.eclipse.sirius.web.compat.api.IModelOperationHandlerSwitchProvider;
import org.eclipse.sirius.web.compat.api.ISemanticCandidatesProviderFactory;
import org.eclipse.sirius.web.compat.api.IToolImageProviderFactory;
import org.eclipse.sirius.web.emf.compatibility.SemanticCandidatesProvider;
import org.eclipse.sirius.web.emf.compatibility.diagrams.CanCreateDiagramPredicate;
import org.eclipse.sirius.web.emf.compatibility.diagrams.ToolImageProvider;
import org.eclipse.sirius.web.emf.compatibility.modeloperations.ModelOperationHandlerSwitch;
import org.eclipse.sirius.web.services.api.objects.IObjectService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration used to provide all the necessary beans for the compatibility layer.
 *
 * @author sbegaudeau
 */
@Configuration
public class CompatibilityConfiguration {
    @Bean
    public ISemanticCandidatesProviderFactory semanticCandidatesProviderFactory() {
        return SemanticCandidatesProvider::new;
    }

    @Bean
    public ICanCreateDiagramPredicateFactory canCreateDiagramPredicateFactory() {
        return CanCreateDiagramPredicate::new;
    }

    @Bean
    public IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider() {
        return ModelOperationHandlerSwitch::new;
    }

    @Bean
    public IToolImageProviderFactory toolImageProviderFactory(IObjectService objectService, EPackage.Registry ePackageRegistry) {
        return abstractToolDescription -> new ToolImageProvider(objectService, ePackageRegistry, abstractToolDescription);
    }
}
