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
package org.eclipse.sirius.web.interpreter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Tests of the ExpressionConverter used to validate that it can properly convert non AQL expressions into AQL
 * expressions.
 *
 * @author sbegaudeau
 */
public class ExpressionConverterTestCases {
    @Test
    public void testVarExpressionConvertion() {
        String varExpression = "var:variableName"; //$NON-NLS-1$
        String aqlExpression = new ExpressionConverter().convertExpression(varExpression);
        assertThat(aqlExpression).isEqualTo("aql:variableName"); //$NON-NLS-1$
    }

    @Test
    public void testFeatureExpressionConvertion() {
        String featureExpression = "feature:featureName"; //$NON-NLS-1$
        String aqlExpression = new ExpressionConverter().convertExpression(featureExpression);
        assertThat(aqlExpression).isEqualTo("aql:self.featureName"); //$NON-NLS-1$
    }

    @Test
    public void testEContainerFeatureExpressionConvertion() {
        this.testSpecialFeatureExpressionConvertion("eContainer"); //$NON-NLS-1$
    }

    @Test
    public void testEContentsFeatureExpressionConvertion() {
        this.testSpecialFeatureExpressionConvertion("eContents"); //$NON-NLS-1$
    }

    @Test
    public void testEAllContentsFeatureExpressionConvertion() {
        this.testSpecialFeatureExpressionConvertion("eAllContents"); //$NON-NLS-1$
    }

    @Test
    public void testEClassFeatureExpressionConvertion() {
        this.testSpecialFeatureExpressionConvertion("eClass"); //$NON-NLS-1$
    }

    @Test
    public void testECrossReferencesFeatureExpressionConvertion() {
        this.testSpecialFeatureExpressionConvertion("eCrossReferences"); //$NON-NLS-1$
    }

    private void testSpecialFeatureExpressionConvertion(String featureName) {
        String featureExpression = "feature:" + featureName; //$NON-NLS-1$
        String aqlExpression = new ExpressionConverter().convertExpression(featureExpression);
        assertThat(aqlExpression).isEqualTo("aql:self." + featureName + "()"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Test
    public void testStringExpressionConvertion() {
        String stringExpression = "true"; //$NON-NLS-1$
        String aqlExpression = new ExpressionConverter().convertExpression(stringExpression);
        assertThat(aqlExpression).isEqualTo("aql:'true'"); //$NON-NLS-1$
    }

    @Test
    public void testStringExpressionWithQuotesConvertion() {
        String stringExpression = "Let's get started, let's go!"; //$NON-NLS-1$
        String aqlExpression = new ExpressionConverter().convertExpression(stringExpression);
        assertThat(aqlExpression).isEqualTo("aql:'Let\\'s get started, let\\'s go!'"); //$NON-NLS-1$
    }

    @Test
    public void testVarExpressionForDirectEditConvertion() {
        String varExpression = "var:0"; //$NON-NLS-1$
        String aqlExpression = new ExpressionConverter().convertExpression(varExpression);
        assertThat(aqlExpression).isEqualTo("aql:arg0"); //$NON-NLS-1$
    }
}
