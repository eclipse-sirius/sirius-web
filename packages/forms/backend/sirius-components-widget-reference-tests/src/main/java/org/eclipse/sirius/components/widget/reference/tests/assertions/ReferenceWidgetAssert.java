/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.components.widget.reference.tests.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;

/**
 * Custom assertion class used to perform some tests on a reference widget.
 *
 * @author sbegaudeau
 */
public class ReferenceWidgetAssert extends AbstractAssert<ReferenceWidgetAssert, ReferenceWidget> {

    public ReferenceWidgetAssert(ReferenceWidget referenceWidget) {
        super(referenceWidget, ReferenceWidgetAssert.class);
    }

    public ReferenceWidgetAssert hasLabel(String label) {
        assertThat(this.actual.getLabel()).isEqualTo(label);

        return this;
    }

    public ReferenceWidgetAssert hasValueWithLabel(String valueLabel) {
        assertThat(this.actual.getReferenceValues())
                .isNotEmpty()
                .anySatisfy(referenceValue -> assertThat(referenceValue.getLabel()).isEqualTo(valueLabel));
        return this;
    }

    public ReferenceWidgetAssert hasNoValue() {
        assertThat(this.actual.getReferenceValues())
                .isEmpty();
        return this;
    }

    public ReferenceWidgetAssert hasHelpText(String helpText) {
        assertThat(this.actual.getHelpTextProvider()).isNotNull();
        assertThat(this.actual.getHelpTextProvider().get()).isEqualTo(helpText);
        return this;
    }

    public ReferenceWidgetAssert isBold() {
        assertThat(this.actual.getStyle().isBold())
                .withFailMessage("Expecting the reference widget to be bold but was not bold instead")
                .isTrue();

        return this;
    }

    public ReferenceWidgetAssert isNotBold() {
        assertThat(this.actual.getStyle().isBold())
                .withFailMessage("Expecting the reference widget not to be bold but was bold instead")
                .isFalse();

        return this;
    }

    public ReferenceWidgetAssert isItalic() {
        assertThat(this.actual.getStyle().isItalic())
                .withFailMessage("Expecting the reference widget to be italic but was not italic instead")
                .isTrue();

        return this;
    }

    public ReferenceWidgetAssert isNotItalic() {
        assertThat(this.actual.getStyle().isItalic())
                .withFailMessage("Expecting the reference widget not to be italic but was italic instead")
                .isFalse();

        return this;
    }

    public ReferenceWidgetAssert isStrikeThrough() {
        assertThat(this.actual.getStyle().isStrikeThrough())
                .withFailMessage("Expecting the reference widget to be strikethrough but was not strikethrough instead")
                .isTrue();

        return this;
    }

    public ReferenceWidgetAssert isNotStrikeThrough() {
        assertThat(this.actual.getStyle().isStrikeThrough())
                .withFailMessage("Expecting the reference widget not to be strikethrough but was strikethrough instead")
                .isFalse();

        return this;
    }

    public ReferenceWidgetAssert isUnderline() {
        assertThat(this.actual.getStyle().isUnderline())
                .withFailMessage("Expecting the reference widget to be underlined but was not underlined instead")
                .isTrue();

        return this;
    }

    public ReferenceWidgetAssert isNotUnderline() {
        assertThat(this.actual.getStyle().isUnderline())
                .withFailMessage("Expecting the reference widget not to be underlined but was underlined instead")
                .isFalse();

        return this;
    }
}
