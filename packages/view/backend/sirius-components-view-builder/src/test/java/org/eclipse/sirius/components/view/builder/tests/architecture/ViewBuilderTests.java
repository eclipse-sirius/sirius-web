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
package org.eclipse.sirius.components.view.builder.tests.architecture;

import org.eclipse.sirius.components.view.builder.generated.ReferenceWidgetDescriptionBuilder;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

/**
 * Tests for instancing widget with generated builder.
 *
 * @author mcharfadi
 */
public class ViewBuilderTests {
    @Test
    void testBuildWidgetDescription() {
        var widgetReference = new ReferenceWidgetDescriptionBuilder()
                .name("name")
                .referenceNameExpression("referenceNameExpression")
                .referenceOwnerExpression("referenceOwnerExpression")
                .helpExpression("helpExpression")
                .isEnabledExpression("isEnabledExpression")
                .build();

        assertThat(widgetReference).isInstanceOf(ReferenceWidgetDescription.class);
        assertThat(widgetReference.getReferenceNameExpression()).isEqualTo("referenceNameExpression");
        assertThat(widgetReference.getName()).isEqualTo("name");
        assertThat(widgetReference.getReferenceOwnerExpression()).isEqualTo("referenceOwnerExpression");
        assertThat(widgetReference.getHelpExpression()).isEqualTo("helpExpression");
        assertThat(widgetReference.getIsEnabledExpression()).isEqualTo("isEnabledExpression");
    }
}
