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
package org.eclipse.sirius.web.emf.compatibility;

import java.util.Optional;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Descriptor;
import org.eclipse.emf.ecore.EPackage.Registry;

/**
 * Services related to EPackages.
 *
 * @author sbegaudeau
 */
public class EPackageService {
    public Optional<EPackage> findEPackage(Registry ePackageRegistry, String packageName) {
        return ePackageRegistry.values().stream().map(value -> {
            EPackage ePackage = null;
            if (value instanceof EPackage.Descriptor) {
                EPackage.Descriptor descriptor = (Descriptor) value;
                ePackage = descriptor.getEPackage();
            } else if (value instanceof EPackage) {
                ePackage = (EPackage) value;
            }

            return ePackage;
        }).filter(ePackage -> packageName.equals(ePackage.getName())).findFirst();
    }
}
