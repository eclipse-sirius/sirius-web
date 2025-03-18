/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.view;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.emf.diagram.ViewToolImageProvider;
import org.junit.jupiter.api.Test;

/**
 * Unit tests of the ViewToolImageProvider.
 *
 * @author arichard
 */
public class ViewToolImageProviderTests {

    @Test
    public void testNodeDefaultIcon() {
        var nodeDescription = DiagramFactory.eINSTANCE.createNodeDescription();

        EPackage.Registry ePackageRegistry = EPackage.Registry.INSTANCE;
        ViewToolImageProvider viewToolImageProvider = new ViewToolImageProvider(new ILabelService.NoOp(), ePackageRegistry);

        List<String> iconURL = viewToolImageProvider.getIcon(nodeDescription);
        assertThat(iconURL).hasSize(1);
        assertThat(ViewToolImageProvider.NODE_CREATION_TOOL_ICON.equals(iconURL.get(0))).isTrue();
    }

    @Test
    public void testEdgeDefaultIcon() {
        var edgeDescription = DiagramFactory.eINSTANCE.createEdgeDescription();

        EPackage.Registry ePackageRegistry = EPackage.Registry.INSTANCE;
        ViewToolImageProvider viewToolImageProvider = new ViewToolImageProvider(new ILabelService.NoOp(), ePackageRegistry);

        List<String> iconURL = viewToolImageProvider.getIcon(edgeDescription);
        assertThat(iconURL).hasSize(1);
        assertThat(ViewToolImageProvider.EDGE_CREATION_TOOL_ICON.equals(iconURL.get(0))).isTrue();
    }
}
