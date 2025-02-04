/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import org.assertj.core.api.Assertions;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;

/**
 * Entry point of the AssertJ assertions with the reference widget specific ones.
 *
 * @author frouene
 */
public class ReferenceWidgetAssertions extends Assertions {

    public static ReferenceWidgetAssert assertThat(ReferenceWidget referenceWidget) {
        return new ReferenceWidgetAssert(referenceWidget);
    }
}
