/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.papaya.spec.derived;

import java.util.function.Predicate;

import org.eclipse.sirius.components.papaya.Package;

/**
 * Used to indicate if the qualified name is set.
 *
 * @author sbegaudeau
 */
public class PackageIsSetQualifiedNamePredicate implements Predicate<Package> {
    @Override
    public boolean test(Package aPackage) {
        boolean isSetQualifiedName = aPackage.getName() != null && !aPackage.getName().isBlank();

        var eContainer = aPackage.eContainer();
        while (eContainer instanceof Package eContainingPackage) {
            isSetQualifiedName = isSetQualifiedName && eContainingPackage.getName() != null && !eContainingPackage.getName().isBlank();

            eContainer = eContainer.eContainer();
        }

        return isSetQualifiedName;
    }
}
