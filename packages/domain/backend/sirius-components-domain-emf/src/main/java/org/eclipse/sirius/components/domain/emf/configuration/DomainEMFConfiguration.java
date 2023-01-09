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
package org.eclipse.sirius.components.domain.emf.configuration;

import java.util.Objects;

import jakarta.annotation.PostConstruct;

import org.eclipse.emf.ecore.EValidator.Registry;
import org.eclipse.sirius.components.domain.DomainPackage;
import org.eclipse.sirius.components.domain.emf.DomainValidator;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of the EMF beans.
 *
 * @author sbegaudeau
 */
@Configuration
public class DomainEMFConfiguration {

    private final Registry eValidatorRegistry;

    public DomainEMFConfiguration(Registry eValidatorRegistry) {
        this.eValidatorRegistry = Objects.requireNonNull(eValidatorRegistry);
    }

    @PostConstruct
    public void registerDomainValidator() {
        this.eValidatorRegistry.put(DomainPackage.eINSTANCE, new DomainValidator());
    }
}
