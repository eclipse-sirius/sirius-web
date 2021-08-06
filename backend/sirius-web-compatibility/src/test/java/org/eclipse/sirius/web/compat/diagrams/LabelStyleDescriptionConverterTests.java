/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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

import java.util.List;
import java.util.function.Function;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sirius.diagram.description.style.ShapeContainerStyleDescription;
import org.eclipse.sirius.diagram.description.style.StyleFactory;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.VariableManager;
import org.junit.jupiter.api.Test;

/**
 * Tests of the transformation of Sirius label style.
 *
 * @author arichard
 */
public class LabelStyleDescriptionConverterTests {

    @Test
    public void testConvertIconPath() {
        String pluginName = "my.sirius.plugin"; //$NON-NLS-1$
        String iconPath = "/my/icon/path/MyIcon.gif"; //$NON-NLS-1$
        String iconFullPath = pluginName + iconPath;

        ShapeContainerStyleDescription styleDescription = StyleFactory.eINSTANCE.createShapeContainerStyleDescription();
        styleDescription.setIconPath(iconFullPath);

        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        VariableManager variableManager = new VariableManager();

        LabelStyleDescriptionConverter labelStyleDescriptionConverter = new LabelStyleDescriptionConverter(interpreter, new IObjectService.NoOp());
        LabelStyleDescription labelStyleDescriptionConverted = labelStyleDescriptionConverter.convert(styleDescription);

        assertThat(labelStyleDescriptionConverted).isNotNull();

        Function<VariableManager, String> iconURLProvider = labelStyleDescriptionConverted.getIconURLProvider();
        assertThat(iconURLProvider.apply(variableManager)).isEqualTo(iconPath);
    }
}
