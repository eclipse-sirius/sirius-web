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
package org.eclipse.sirius.web.emf.compatibility.diagrams;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sirius.web.emf.compatibility.SemanticCandidatesProvider;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.VariableManager;
import org.junit.Test;

/**
 * Test of the computation of the semantic candidates.
 *
 * @author sbegaudeau
 */
public class SemanticCandidatesProviderTestCases {

    private AQLInterpreter interpreter;

    public SemanticCandidatesProviderTestCases() {
        List<Class<?>> classes = new ArrayList<>();
        ArrayList<EPackage> ePackages = new ArrayList<>();
        this.interpreter = new AQLInterpreter(classes, ePackages);
    }

    /**
     * Test the computation of the semantic candidates with the Ecore package as the self variable and with the
     * following properties.
     * <ul>
     * <li>domain class: "ecore::EClass"</li>
     * <li>semantic candidates expression: &aql:self.eClassifiers"</li>
     * <li>precondition expression: ""</li>
     * </ul>
     */
    @Test
    public void testDomainClassAndSemanticCandidatesExpression() {
        String domainClass = "ecore::EClass"; //$NON-NLS-1$
        String semanticCandidatesExpression = "aql:self.eClassifiers"; //$NON-NLS-1$
        String preconditionExpression = ""; //$NON-NLS-1$

        SemanticCandidatesProvider semanticCandidatesProvider = new SemanticCandidatesProvider(this.interpreter, domainClass, semanticCandidatesExpression, preconditionExpression);

        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, EcorePackage.eINSTANCE);

        List<Object> semanticCandidates = semanticCandidatesProvider.apply(variableManager);
        assertThat(semanticCandidates).allMatch(EClass.class::isInstance);
        assertThat(semanticCandidates).hasSize(20);
    }

    /**
     * Test the computation of the semantic candidates with the Ecore package as the self variable and with the
     * following properties.
     * <ul>
     * <li>domain class: "ecore::EClass"</li>
     * <li>semantic candidates expression: &aql:self.eClassifiers"</li>
     * <li>precondition expression: "aql:self.name.startsWith('EEnum')"</li>
     * </ul>
     */
    @Test
    public void testDomainClassSemanticCandidatesExpressionAndPreconditionExpression() {
        String domainClass = "ecore::EClass"; //$NON-NLS-1$
        String semanticCandidatesExpression = "aql:self.eClassifiers"; //$NON-NLS-1$
        String preconditionExpression = "aql:self.name.startsWith('EEnum')"; //$NON-NLS-1$

        SemanticCandidatesProvider semanticCandidatesProvider = new SemanticCandidatesProvider(this.interpreter, domainClass, semanticCandidatesExpression, preconditionExpression);

        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, EcorePackage.eINSTANCE);

        List<Object> semanticCandidates = semanticCandidatesProvider.apply(variableManager);
        assertThat(semanticCandidates).allMatch(EClass.class::isInstance);

        // @formatter:off
        List<EClass> eClasses = semanticCandidates.stream()
                .filter(EClass.class::isInstance)
                .map(EClass.class::cast)
                .collect(Collectors.toList());
        // @formatter:on

        assertThat(semanticCandidates).hasSize(2);
        assertThat(eClasses).extracting(ENamedElement::getName).containsExactly("EEnum", "EEnumLiteral"); //$NON-NLS-1$//$NON-NLS-2$
    }
}
