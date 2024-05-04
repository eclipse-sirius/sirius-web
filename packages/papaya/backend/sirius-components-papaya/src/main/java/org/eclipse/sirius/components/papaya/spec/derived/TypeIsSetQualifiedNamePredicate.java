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
import org.eclipse.sirius.components.papaya.Type;

/**
 * Used to indicate if the qualified name is set.
 *
 * @author sbegaudeau
 */
public class TypeIsSetQualifiedNamePredicate implements Predicate<Type> {
    @Override
    public boolean test(Type type) {
        boolean isSetQualifiedName = type.getName() != null && !type.getName().isBlank();

        var eContainer = type.eContainer();
        while (eContainer instanceof Package || eContainer instanceof Type) {
            if (eContainer instanceof Package eContainingPackage) {
                isSetQualifiedName = isSetQualifiedName && eContainingPackage.getName() != null && !eContainingPackage.getName().isBlank();
            } else if (eContainer instanceof Type eContainingType) {
                isSetQualifiedName = isSetQualifiedName && eContainingType.getName() != null && !eContainingType.getName().isBlank();
            }

            eContainer = eContainer.eContainer();
        }

        return isSetQualifiedName;
    }
}
