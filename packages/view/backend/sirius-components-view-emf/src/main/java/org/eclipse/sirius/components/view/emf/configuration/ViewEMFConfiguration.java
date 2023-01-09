/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo and others.
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
package org.eclipse.sirius.components.view.emf.configuration;

import java.util.Objects;

import jakarta.annotation.PostConstruct;

import org.eclipse.emf.ecore.EValidator.Registry;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.emf.diagram.DiagramDescriptionValidator;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of the EMF beans.
 *
 * @author sbegaudeau
 */
@Configuration
public class ViewEMFConfiguration {

    private final Registry eValidatorRegistry;

    public ViewEMFConfiguration(Registry eValidatorRegistry) {
        this.eValidatorRegistry = Objects.requireNonNull(eValidatorRegistry);
    }

    @PostConstruct
    public void registerViewValidator() {
        this.eValidatorRegistry.put(ViewPackage.eINSTANCE, new DiagramDescriptionValidator());
    }
}
