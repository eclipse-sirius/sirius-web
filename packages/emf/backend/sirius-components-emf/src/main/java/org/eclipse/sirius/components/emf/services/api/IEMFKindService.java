/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.sirius.components.emf.services.api;

import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;

/**
 * Used to create an retrieve information from kind for EMF objects.
 *
 * @author sbegaudeau
 */
public interface IEMFKindService {
    String getKind(EClass eClass);

    String getEPackageName(String kind);

    String getEClassName(String kind);

    Optional<EPackage> findEPackage(EPackage.Registry ePackageRegistry, String ePackageName);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IEMFKindService {

        @Override
        public String getKind(EClass eClass) {
            return "";
        }

        @Override
        public String getEPackageName(String kind) {
            return "";
        }

        @Override
        public String getEClassName(String kind) {
            return "";
        }

        @Override
        public Optional<EPackage> findEPackage(Registry ePackageRegistry, String ePackageName) {
            return Optional.empty();
        }

    }
}
