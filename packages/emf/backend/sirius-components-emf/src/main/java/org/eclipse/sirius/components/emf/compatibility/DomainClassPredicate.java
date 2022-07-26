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
package org.eclipse.sirius.components.emf.compatibility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EClass;

/**
 * Evaluate if a given EClass matches a domain class descriptor (as a string).
 *
 * @author sbegaudeau
 */
public class DomainClassPredicate implements Predicate<EClass> {

    /**
     * The pattern used to match the separator used by both Sirius and AQL.
     */
    private static final Pattern SEPARATOR = Pattern.compile("(::?|\\.)"); //$NON-NLS-1$

    private String domainClass;

    public DomainClassPredicate(String domainClass) {
        this.domainClass = Objects.requireNonNull(domainClass);
    }

    @Override
    public boolean test(EClass actualType) {
        String packageName = null;
        String className = null;

        if (!this.domainClass.isBlank()) {
            Matcher matcher = SEPARATOR.matcher(this.domainClass);
            if (matcher.find()) {
                packageName = this.domainClass.substring(0, matcher.start());
                className = this.domainClass.substring(matcher.end());
            } else {
                className = this.domainClass;
            }

            if (!("EObject".equals(className) && packageName == null) && !("EObject".equals(className) && "ecore".equals(packageName))) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                boolean result = false;

                List<EClass> eAllTypes = new ArrayList<>();
                eAllTypes.add(actualType);
                eAllTypes.addAll(actualType.getEAllSuperTypes());

                Iterator<EClass> iterator = eAllTypes.iterator();
                while (iterator.hasNext() && !result) {
                    EClass eClass = iterator.next();
                    if (packageName == null && className != null) {
                        // Only consider the class name
                        result = className.equals(eClass.getName());
                    } else if (packageName != null && className != null) {
                        result = packageName.equals(eClass.getEPackage().getName()) && className.equals(eClass.getName());
                    }
                }
                return result;
            }
        }

        return true;
    }

}
