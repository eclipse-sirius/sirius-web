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
package org.eclipse.sirius.web.compat.diagrams;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sirius.viewpoint.description.DescriptionFactory;
import org.eclipse.sirius.viewpoint.description.FixedColor;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.junit.Test;

/**
 * Test of the transformation of Sirius colors.
 *
 * @author sbegaudeau
 */
public class ColorDescriptionConverterTestCases {

    @Test
    public void testConvertFixedColor() {
        FixedColor fixedColor = DescriptionFactory.eINSTANCE.createFixedColor();
        fixedColor.setRed(15);
        fixedColor.setGreen(1);
        fixedColor.setBlue(16);

        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        ColorDescriptionConverter colorProvider = new ColorDescriptionConverter(interpreter, Collections.emptyMap());
        String color = colorProvider.convert(fixedColor);
        assertThat(color).isEqualTo("#0f0110"); //$NON-NLS-1$
    }
}
