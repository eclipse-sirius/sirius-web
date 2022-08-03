/*******************************************************************************
 * Copyright (c) 2019, 2020, 2022 Obeo.
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
package org.eclipse.sirius.components.interpreter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.jupiter.api.Test;

/**
 * Test that the AQLInterpreter can properly evaluate AQL expressions.
 *
 * @author hmarchadour
 */
public class AQLInterpreterTests {

    private static final String SELF = "self"; //$NON-NLS-1$

    @Test
    public void testNameFeatureExpression() {
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        Result result = interpreter.evaluateExpression(Map.of(SELF, EcorePackage.eINSTANCE.getEModelElement()), "feature:name"); //$NON-NLS-1$
        Optional<String> asString = result.asString();
        assertThat(asString).isPresent().hasValue(EcorePackage.eINSTANCE.getEModelElement().getName());
    }

    @Test
    public void testEContentsFeatureExpression() {
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        Result result = interpreter.evaluateExpression(Map.of(SELF, EcorePackage.eINSTANCE.getEModelElement()), "feature:eContents"); //$NON-NLS-1$
        Optional<List<Object>> asObjects = result.asObjects();
        assertThat(asObjects).isPresent();
        assertThat(asObjects.get()).contains(EcorePackage.Literals.EMODEL_ELEMENT__EANNOTATIONS, EcorePackage.Literals.EMODEL_ELEMENT___GET_EANNOTATION__STRING);
    }

    @Test
    public void testEAllContentsFeatureExpression() {
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        Result result = interpreter.evaluateExpression(Map.of(SELF, EcorePackage.eINSTANCE.getEModelElement()), "feature:eAllContents"); //$NON-NLS-1$
        Optional<List<Object>> asObjects = result.asObjects();
        assertThat(asObjects).isPresent();
        assertThat(asObjects.get()).contains(EcorePackage.Literals.EMODEL_ELEMENT__EANNOTATIONS, EcorePackage.Literals.EMODEL_ELEMENT___GET_EANNOTATION__STRING);
    }

    @Test
    public void testEContainerFeatureExpression() {
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        Result result = interpreter.evaluateExpression(Map.of(SELF, EcorePackage.eINSTANCE.getEModelElement()), "feature:eContainer"); //$NON-NLS-1$
        Optional<List<Object>> asObjects = result.asObjects();
        assertThat(asObjects).isPresent();
        assertThat(asObjects.get()).contains(EcorePackage.eINSTANCE);
    }

    @Test
    public void testEClassFeatureExpression() {
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        Result result = interpreter.evaluateExpression(Map.of(SELF, EcorePackage.eINSTANCE.getEModelElement()), "feature:eClass"); //$NON-NLS-1$
        Optional<List<Object>> asObjects = result.asObjects();
        assertThat(asObjects).isPresent();
        assertThat(asObjects.get()).contains(EcorePackage.Literals.ECLASS);
    }

    @Test
    public void testECrossReferencesFeatureExpression() {
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        Result result = interpreter.evaluateExpression(Map.of(SELF, EcorePackage.eINSTANCE.getEEnumLiteral()), "feature:eCrossReferences"); //$NON-NLS-1$
        Optional<List<Object>> asObjects = result.asObjects();
        assertThat(asObjects).isPresent();
        assertThat(asObjects.get()).contains(EcorePackage.Literals.EENUM_LITERAL__EENUM);
    }

    @Test
    public void testBrokenEPackage() {
        EPackage broken = EcoreFactory.eINSTANCE.createEPackage();
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(broken));
        assertThat(interpreter).isNotNull();
    }

    @Test
    public void testNullExpressionEvaluatesToTrue() {
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        Result result = interpreter.evaluateExpression(Map.of(), null);
        assertThat(result).isNotNull();
        assertThat(result.asBoolean()).contains(Boolean.TRUE);
    }

    @Test
    public void testEmptyExpressionEvaluatesToTrue() {
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        Result result = interpreter.evaluateExpression(Map.of(), ""); //$NON-NLS-1$
        assertThat(result).isNotNull();
        assertThat(result.asBoolean()).contains(Boolean.TRUE);
    }

    @Test
    public void testInvokeServiceFromClass() {
        AQLInterpreter interpreter = new AQLInterpreter(List.of(TestServices.class), List.of(EcorePackage.eINSTANCE));
        Result result = interpreter.evaluateExpression(Map.of(SELF, EcorePackage.eINSTANCE), "aql:self.getCreationMessage()"); //$NON-NLS-1$
        assertThat(result).isNotNull();
        assertThat(result.asString()).contains("none"); //$NON-NLS-1$
    }

    @Test
    public void testInvokeServiceFromInstance() {
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(new TestServices("instance")), List.of(EcorePackage.eINSTANCE)); //$NON-NLS-1$
        Result result = interpreter.evaluateExpression(Map.of(SELF, EcorePackage.eINSTANCE), "aql:self.getCreationMessage()"); //$NON-NLS-1$
        assertThat(result).isNotNull();
        assertThat(result.asString()).contains("instance"); //$NON-NLS-1$
    }

}
