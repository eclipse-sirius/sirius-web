/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.forms.tests.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.components.forms.Link;

/**
 * Custom assertion class used to perform some tests on a link widget.
 *
 * @author sbegaudeau
 */
public class LinkAssert extends AbstractAssert<LinkAssert, Link> {

    public LinkAssert(Link link) {
        super(link, LinkAssert.class);
    }

    public LinkAssert hasLabel(String label) {
        assertThat(this.actual.getLabel()).isEqualTo(label);

        return this;
    }

    public LinkAssert hasUrl(String url) {
        assertThat(this.actual.getUrl()).isEqualTo(url);

        return this;
    }

    public LinkAssert hasHelp(String help) {
        assertThat(this.actual.getHelpTextProvider().get()).isEqualTo(help);

        return this;
    }

    public LinkAssert isReadOnly() {
        assertThat(this.actual.isReadOnly())
                .withFailMessage("Expecting the link to be read only but was not read only instead")
                .isTrue();

        return this;
    }

    public LinkAssert isNotReadOnly() {
        assertThat(this.actual.isReadOnly())
                .withFailMessage("Expecting the link not to be read only but was read only instead")
                .isFalse();

        return this;
    }

    public LinkAssert isBold() {
        assertThat(this.actual.getStyle().isBold())
                .withFailMessage("Expecting the link to be bold but was not bold instead")
                .isTrue();

        return this;
    }

    public LinkAssert isNotBold() {
        assertThat(this.actual.getStyle().isBold())
                .withFailMessage("Expecting the link not to be bold but was bold instead")
                .isFalse();

        return this;
    }

    public LinkAssert isItalic() {
        assertThat(this.actual.getStyle().isItalic())
                .withFailMessage("Expecting the link to be italic but was not italic instead")
                .isTrue();

        return this;
    }

    public LinkAssert isNotItalic() {
        assertThat(this.actual.getStyle().isItalic())
                .withFailMessage("Expecting the link not to be italic but was italic instead")
                .isFalse();

        return this;
    }
}
