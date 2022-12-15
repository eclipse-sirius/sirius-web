/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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

import org.junit.jupiter.api.Test;

/**
 * Tests of the ExpressionConverter used to validate that it can properly convert non AQL expressions into AQL
 * expressions.
 *
 * @author sbegaudeau
 */
public class ExpressionConverterTests {
    @Test
    public void testVarExpressionConvertion() {
        String varExpression = "var:variableName";
        String aqlExpression = new ExpressionConverter().convertExpression(varExpression);
        assertThat(aqlExpression).isEqualTo("aql:variableName");
    }

    @Test
    public void testFeatureExpressionConvertion() {
        String featureExpression = "feature:featureName";
        String aqlExpression = new ExpressionConverter().convertExpression(featureExpression);
        assertThat(aqlExpression).isEqualTo("aql:self.featureName");
    }

    @Test
    public void testEContainerFeatureExpressionConvertion() {
        this.testSpecialFeatureExpressionConvertion("eContainer");
    }

    @Test
    public void testEContentsFeatureExpressionConvertion() {
        this.testSpecialFeatureExpressionConvertion("eContents");
    }

    @Test
    public void testEAllContentsFeatureExpressionConvertion() {
        this.testSpecialFeatureExpressionConvertion("eAllContents");
    }

    @Test
    public void testEClassFeatureExpressionConvertion() {
        this.testSpecialFeatureExpressionConvertion("eClass");
    }

    @Test
    public void testECrossReferencesFeatureExpressionConvertion() {
        this.testSpecialFeatureExpressionConvertion("eCrossReferences");
    }

    private void testSpecialFeatureExpressionConvertion(String featureName) {
        String featureExpression = "feature:" + featureName;
        String aqlExpression = new ExpressionConverter().convertExpression(featureExpression);
        assertThat(aqlExpression).isEqualTo("aql:self." + featureName + "()");
    }

    @Test
    public void testStringExpressionConvertion() {
        String stringExpression = "true";
        String aqlExpression = new ExpressionConverter().convertExpression(stringExpression);
        assertThat(aqlExpression).isEqualTo("aql:'true'");
    }

    @Test
    public void testStringExpressionWithQuotesConvertion() {
        String stringExpression = "Let's get started, let's go!";
        String aqlExpression = new ExpressionConverter().convertExpression(stringExpression);
        assertThat(aqlExpression).isEqualTo("aql:'Let\\'s get started, let\\'s go!'");
    }

    @Test
    public void testVarExpressionForDirectEditConvertion() {
        String varExpression = "var:0";
        String aqlExpression = new ExpressionConverter().convertExpression(varExpression);
        assertThat(aqlExpression).isEqualTo("aql:arg0");
    }

    @Test
    public void testNullExpressionConvertedToEmptyStringExpression() {
        String expression = null;
        String aqlExpression = new ExpressionConverter().convertExpression(expression);
        assertThat(aqlExpression).isEqualTo("aql:''");
    }
}
