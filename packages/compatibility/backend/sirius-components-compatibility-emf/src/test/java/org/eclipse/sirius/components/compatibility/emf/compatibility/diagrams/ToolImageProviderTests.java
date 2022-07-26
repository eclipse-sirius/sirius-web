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
package org.eclipse.sirius.components.compatibility.emf.compatibility.diagrams;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.compatibility.emf.diagrams.ToolImageProvider;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.viewpoint.description.tool.OperationAction;
import org.eclipse.sirius.viewpoint.description.tool.ToolDescription;
import org.eclipse.sirius.viewpoint.description.tool.ToolFactory;
import org.junit.jupiter.api.Test;

/**
 * Unit tests of the ToolImageProvider.
 *
 * @author arichard
 */
public class ToolImageProviderTests {

    private static final String ICON_PATH = "/org.eclipse.sirius.components.diagrams/path"; //$NON-NLS-1$

    @Test
    public void testIconPathNormalization() {
        ToolDescription toolDescription = ToolFactory.eINSTANCE.createToolDescription();
        toolDescription.setIconPath(ICON_PATH);

        IObjectService objectService = new IObjectService.NoOp();
        EPackage.Registry ePackageRegistry = EPackage.Registry.INSTANCE;
        ToolImageProvider toolImageProvider = new ToolImageProvider(objectService, ePackageRegistry, toolDescription);

        String convertedIconPath = toolImageProvider.get();
        assertThat(convertedIconPath.equals(ICON_PATH.substring(ICON_PATH.indexOf('/', 1)))).isTrue();
    }

    @Test
    public void testIconNormalization() {
        OperationAction operationAction = ToolFactory.eINSTANCE.createOperationAction();
        operationAction.setIcon(ICON_PATH);

        IObjectService objectService = new IObjectService.NoOp();
        EPackage.Registry ePackageRegistry = EPackage.Registry.INSTANCE;
        ToolImageProvider toolImageProvider = new ToolImageProvider(objectService, ePackageRegistry, operationAction);

        String convertedIconPath = toolImageProvider.get();
        assertThat(convertedIconPath.equals(ICON_PATH.substring(ICON_PATH.indexOf('/', 1)))).isTrue();
    }
}
