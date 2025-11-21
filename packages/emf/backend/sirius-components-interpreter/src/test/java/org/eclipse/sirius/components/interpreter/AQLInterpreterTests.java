/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.jupiter.api.Test;

/**
 * Test that the AQLInterpreter can properly evaluate AQL expressions.
 *
 * @author hmarchadour
 */
public class AQLInterpreterTests {

    private static final String SELF = "self";

    @Test
    public void testNameFeatureExpression() {
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        Result result = interpreter.evaluateExpression(Map.of(SELF, EcorePackage.eINSTANCE.getEModelElement()), "feature:name");
        Optional<String> asString = result.asString();
        assertThat(asString).isPresent().hasValue(EcorePackage.eINSTANCE.getEModelElement().getName());
    }

    @Test
    public void testEContentsFeatureExpression() {
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        Result result = interpreter.evaluateExpression(Map.of(SELF, EcorePackage.eINSTANCE.getEModelElement()), "feature:eContents");
        Optional<List<Object>> asObjects = result.asObjects();
        assertThat(asObjects).isPresent();
        assertThat(asObjects.get()).contains(EcorePackage.Literals.EMODEL_ELEMENT__EANNOTATIONS, EcorePackage.Literals.EMODEL_ELEMENT___GET_EANNOTATION__STRING);
    }

    @Test
    public void testEAllContentsFeatureExpression() {
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        Result result = interpreter.evaluateExpression(Map.of(SELF, EcorePackage.eINSTANCE.getEModelElement()), "feature:eAllContents");
        Optional<List<Object>> asObjects = result.asObjects();
        assertThat(asObjects).isPresent();
        assertThat(asObjects.get()).contains(EcorePackage.Literals.EMODEL_ELEMENT__EANNOTATIONS, EcorePackage.Literals.EMODEL_ELEMENT___GET_EANNOTATION__STRING);
    }

    @Test
    public void testEContainerFeatureExpression() {
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        Result result = interpreter.evaluateExpression(Map.of(SELF, EcorePackage.eINSTANCE.getEModelElement()), "feature:eContainer");
        Optional<List<Object>> asObjects = result.asObjects();
        assertThat(asObjects).isPresent();
        assertThat(asObjects.get()).contains(EcorePackage.eINSTANCE);
    }

    @Test
    public void testEClassFeatureExpression() {
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        Result result = interpreter.evaluateExpression(Map.of(SELF, EcorePackage.eINSTANCE.getEModelElement()), "feature:eClass");
        Optional<List<Object>> asObjects = result.asObjects();
        assertThat(asObjects).isPresent();
        assertThat(asObjects.get()).contains(EcorePackage.Literals.ECLASS);
    }

    @Test
    public void testECrossReferencesFeatureExpression() {
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        Result result = interpreter.evaluateExpression(Map.of(SELF, EcorePackage.eINSTANCE.getEEnumLiteral()), "feature:eCrossReferences");
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
        Result result = interpreter.evaluateExpression(Map.of(), "");
        assertThat(result).isNotNull();
        assertThat(result.asBoolean()).contains(Boolean.TRUE);
    }

    @Test
    public void testInvokeServiceFromClass() {
        AQLInterpreter interpreter = new AQLInterpreter(List.of(TestServices.class), List.of(EcorePackage.eINSTANCE));
        Result result = interpreter.evaluateExpression(Map.of(SELF, EcorePackage.eINSTANCE), "aql:self.getCreationMessage()");
        assertThat(result).isNotNull();
        assertThat(result.asString()).contains("none");
    }

    @Test
    public void testInvokeServiceFromInstance() {
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(new TestServices("instance")), List.of(EcorePackage.eINSTANCE));
        Result result = interpreter.evaluateExpression(Map.of(SELF, EcorePackage.eINSTANCE), "aql:self.getCreationMessage()");
        assertThat(result).isNotNull();
        assertThat(result.asString()).contains("instance");
    }

    @Test
    public void testEvaluateInvalidExpression() {
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        Result result = interpreter.evaluateExpression(Map.of(SELF, EcorePackage.eINSTANCE), "aql:self.(");
        assertThat(result).isNotNull();
        assertThat(result.asBoolean()).isEmpty();
        assertThat(result.asInt()).isEmpty();
        assertThat(result.asObject()).isEmpty();
        assertThat(result.asObjects()).isEmpty();
        assertThat(result.asString()).isEmpty();
        assertThat(result.getStatus()).isEqualTo(Status.ERROR);
    }

    @Test
    public void testEInverseService() {
        // Work on a copy of EcorePackage to avoid polluting the global instance with an unexpected ECrossReferenceAdapter.
        EPackage ecoreCopy = EcoreUtil.copyAll(List.of(EcorePackage.eINSTANCE)).iterator().next();
        ecoreCopy.eAdapters().add(new ECrossReferenceAdapter());

        var eClassCopy = ecoreCopy.getEClassifier("EClass");
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(ecoreCopy));
        Result result = interpreter.evaluateExpression(Map.of(SELF, eClassCopy), "aql:self.eInverse()");

        assertThat(result).isNotNull();
        assertThat(result.asBoolean()).contains(Boolean.TRUE);
        assertThat(result.asObjects()).isNotEmpty();

        // All objects returned should have a reference to 'self', either through a normal cross-reference or through containment.
        assertThat(result.asObjects().get()).allMatch(o -> {
            return o instanceof EObject eObject &&
                    (eObject.eCrossReferences().contains(eClassCopy) ||
                     eClassCopy.eContents().contains(eObject) ||
                     eObject.eContents().contains(eClassCopy));
        });
    }

}
