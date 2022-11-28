/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.formdescriptioneditors.components;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.eclipse.sirius.components.view.ImageDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * The test class for {@link ViewFormDescriptionEditorConverterSwitch}.
 *
 * @author arichard
 */
public class ViewFormDescriptionEditorConverterSwitchTests {

    private static final String NAME = "Name"; //$NON-NLS-1$

    private static final String LABEL_EXPRESSION = "Label"; //$NON-NLS-1$

    private static final String AQL_EXPRESSION = "aql:"; //$NON-NLS-1$

    private static final String BLANK_STRING = "  "; //$NON-NLS-1$

    private static final String DEFAULT_LABEL = "defaultLabel"; //$NON-NLS-1$

    private static Stream<Arguments> notValidValues() {
        // @formatter:off
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(null, ""), //$NON-NLS-1$
                Arguments.of("", null), //$NON-NLS-1$
                Arguments.of(null, BLANK_STRING),
                Arguments.of(BLANK_STRING, null),
                Arguments.of("", ""), //$NON-NLS-1$ //$NON-NLS-2$
                Arguments.of(BLANK_STRING, BLANK_STRING),
                Arguments.of(null, AQL_EXPRESSION),
                Arguments.of("", AQL_EXPRESSION), //$NON-NLS-1$
                Arguments.of(BLANK_STRING, AQL_EXPRESSION));
        // @formatter:on
    }

    private static Stream<Arguments> validLabelExpressionValues() {
        // @formatter:off
        return Stream.of(
                Arguments.of(null, LABEL_EXPRESSION),
                Arguments.of("", LABEL_EXPRESSION), //$NON-NLS-1$
                Arguments.of(BLANK_STRING, LABEL_EXPRESSION),
                Arguments.of(NAME, LABEL_EXPRESSION));
        // @formatter:on
    }

    private static Stream<Arguments> notValidLabelExpressionValues() {
        // @formatter:off
        return Stream.of(
                Arguments.of(NAME, null),
                Arguments.of(NAME, ""), //$NON-NLS-1$
                Arguments.of(NAME, BLANK_STRING),
                Arguments.of(NAME, AQL_EXPRESSION));
        // @formatter:on
    }

    @ParameterizedTest
    @MethodSource("notValidValues")
    public void testWidgetLabelWithNotValidValues(String name, String labelExpression) {
        assertThat(this.callGetWidgetLabel(name, labelExpression)).isEqualTo(DEFAULT_LABEL);
    }

    @ParameterizedTest
    @MethodSource("validLabelExpressionValues")
    public void testWidgetLabelWithValidLabelExpressionValues(String name, String labelExpression) {
        assertThat(this.callGetWidgetLabel(name, labelExpression)).isEqualTo(LABEL_EXPRESSION);
    }

    @ParameterizedTest
    @MethodSource("notValidLabelExpressionValues")
    public void testWidgetLabelWithNotValidLabelExpressionValues(String name, String labelExpression) {
        assertThat(this.callGetWidgetLabel(name, labelExpression)).isEqualTo(NAME);
    }

    private String callGetWidgetLabel(String name, String labelExpression) {
        ImageDescription viewWidgetDescription = ViewFactory.eINSTANCE.createImageDescription();
        viewWidgetDescription.setName(name);
        viewWidgetDescription.setLabelExpression(labelExpression);
        ViewFormDescriptionEditorConverterSwitch converter = new ViewFormDescriptionEditorConverterSwitch(null, null);
        return converter.getWidgetLabel(viewWidgetDescription, DEFAULT_LABEL);
    }
}
