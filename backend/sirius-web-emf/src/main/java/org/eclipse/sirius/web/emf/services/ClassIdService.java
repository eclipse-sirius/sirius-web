/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.emf.services;

import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

/**
 * Utility class used to parse and create the identifier of a class.
 *
 * @author sbegaudeau
 */
public class ClassIdService {
    private static final String SEPARATOR = "::"; //$NON-NLS-1$

    public String getClassId(EClass eClass) {
        return eClass.getEPackage().getName() + SEPARATOR + eClass.getName();
    }

    public String getEPackageName(String classId) {
        int index = classId.indexOf(SEPARATOR);
        if (index != -1) {
            return classId.substring(0, index);
        }
        return ""; //$NON-NLS-1$
    }

    public String getEClassName(String classId) {
        int index = classId.indexOf(SEPARATOR);
        if (index != -1) {
            return classId.substring(index + SEPARATOR.length());
        }
        return ""; //$NON-NLS-1$
    }

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
