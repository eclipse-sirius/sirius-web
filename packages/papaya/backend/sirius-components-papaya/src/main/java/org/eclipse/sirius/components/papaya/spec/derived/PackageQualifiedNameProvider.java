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

import java.util.function.Function;

import org.eclipse.sirius.components.papaya.Package;

/**
 * Used to compute the derived field Package#qualifiedName.
 *
 * @author sbegaudeau
 */
public class PackageQualifiedNameProvider implements Function<Package, String> {
    @Override
    public String apply(Package aPackage) {
        String qualifiedName = aPackage.getName();

        var eContainer = aPackage.eContainer();
        while (eContainer instanceof Package eContainingPackage) {
            qualifiedName = eContainingPackage.getName() + "." + qualifiedName;

            eContainer = eContainer.eContainer();
        }

        return qualifiedName;
    }
}
