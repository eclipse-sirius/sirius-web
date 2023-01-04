/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.components.emf.services;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.core.api.IKindParser;
import org.eclipse.sirius.components.core.api.SemanticKindConstants;
import org.eclipse.sirius.components.emf.services.api.IEMFKindService;
import org.springframework.stereotype.Service;

/**
 * Utility class used to parse and create the kind of a semantic object.
 *
 * @author sbegaudeau
 */
@Service
public class EMFKindService implements IEMFKindService {

    private final IKindParser kindParser;

    public EMFKindService(IKindParser kindParser) {
        this.kindParser = Objects.requireNonNull(kindParser);
    }

    @Override
    public String getKind(EClass eClass) {
        return URI.create(SemanticKindConstants.PREFIX + "?" + SemanticKindConstants.DOMAIN_ARGUMENT + "=" + eClass.getEPackage().getName() + "&" + SemanticKindConstants.ENTITY_ARGUMENT + "="
                + eClass.getName()).toString();
    }

    @Override
    public String getEPackageName(String kind) {
        return this.kindParser.getParameterValues(kind).get(SemanticKindConstants.DOMAIN_ARGUMENT).get(0);
    }

    @Override
    public String getEClassName(String kind) {
        return this.kindParser.getParameterValues(kind).get(SemanticKindConstants.ENTITY_ARGUMENT).get(0);
    }

    @Override
    public Optional<EPackage> findEPackage(EPackage.Registry ePackageRegistry, String ePackageName) {
        // @formatter:off
        return ePackageRegistry.values().stream()
                .map(object -> {
                    if (object instanceof EPackage.Descriptor) {
                        return ((EPackage.Descriptor) object).getEPackage();
                    }
                    return object;
                })
                .filter(EPackage.class::isInstance)
                .map(EPackage.class::cast)
                .filter(ePackage -> ePackage.getName().equals(ePackageName))
                .findFirst();
        // @formatter:on
    }
}
