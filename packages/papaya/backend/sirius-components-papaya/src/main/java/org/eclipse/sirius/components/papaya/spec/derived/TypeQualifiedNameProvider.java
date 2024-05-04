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
import org.eclipse.sirius.components.papaya.Type;

/**
 * Used to compute the derived field Type#qualifiedName.
 *
 * @author sbegaudeau
 */
public class TypeQualifiedNameProvider implements Function<Type, String> {
    @Override
    public String apply(Type type) {
        String qualifiedName = type.getName();

        var eContainer = type.eContainer();
        while (eContainer instanceof Package || eContainer instanceof Type) {
            if (eContainer instanceof Package eContainingPackage) {
                qualifiedName = eContainingPackage.getName() + "." + qualifiedName;
            } else if (eContainer instanceof Type eContainingType) {
                qualifiedName = eContainingType.getName() + "$" + qualifiedName;
            }

            eContainer = eContainer.eContainer();
        }

        return qualifiedName;
    }
}
