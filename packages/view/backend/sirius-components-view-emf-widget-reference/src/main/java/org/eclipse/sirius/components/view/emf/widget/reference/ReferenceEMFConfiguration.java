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
package org.eclipse.sirius.components.view.emf.widget.reference;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.emf.configuration.ChildExtenderProvider;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.widgets.reference.ReferencePackage;
import org.eclipse.sirius.components.widgets.reference.provider.ReferenceItemProviderAdapterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Registers the View DSL extension for the Reference widget.
 *
 * @author pcdavid
 */
@Configuration
public class ReferenceEMFConfiguration {

    @Bean
    public EPackage referenceWidgetEPackage() {
        return ReferencePackage.eINSTANCE;
    }

    @Bean
    public AdapterFactory referenceWidgetAdapterFactory() {
        return new ReferenceItemProviderAdapterFactory();
    }

    @Bean
    public ChildExtenderProvider referenceWidgetChildExtenderProvider() {
        return new ChildExtenderProvider(FormPackage.eNS_URI, ReferenceItemProviderAdapterFactory.FormChildCreationExtender::new);
    }
}
