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

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.emf.ecore.EcorePackage;
import org.junit.jupiter.api.Test;

/**
 * Test of the domain class predicate used to check if an EObject matches a domain class declaration.
 *
 * @author sbegaudeau
 */
public class DomainClassPredicateTests {
    @Test
    public void testValidDomainClass() {
        DomainClassPredicate domainClassPredicate = new DomainClassPredicate("ecore::EClass"); //$NON-NLS-1$
        assertThat(domainClassPredicate.test(EcorePackage.Literals.ECLASS)).isTrue();
    }

    @Test
    public void testInvalidDomainClass() {
        DomainClassPredicate domainClassPredicate = new DomainClassPredicate("foo::Bar"); //$NON-NLS-1$
        assertThat(domainClassPredicate.test(EcorePackage.Literals.ECLASS)).isFalse();
    }

    @Test
    public void testSuperClass() {
        DomainClassPredicate domainClassPredicate = new DomainClassPredicate("ecore::EClassifier"); //$NON-NLS-1$
        assertThat(domainClassPredicate.test(EcorePackage.Literals.ECLASS)).isTrue();
    }

    @Test
    public void testSuperSuperClass() {
        DomainClassPredicate domainClassPredicate = new DomainClassPredicate("ecore::ENamedElement"); //$NON-NLS-1$
        assertThat(domainClassPredicate.test(EcorePackage.Literals.ECLASS)).isTrue();
    }

    @Test
    public void testBlankDomainClass() {
        DomainClassPredicate domainClassPredicate = new DomainClassPredicate(""); //$NON-NLS-1$
        assertThat(domainClassPredicate.test(EcorePackage.Literals.ECLASS)).isTrue();
    }
}
