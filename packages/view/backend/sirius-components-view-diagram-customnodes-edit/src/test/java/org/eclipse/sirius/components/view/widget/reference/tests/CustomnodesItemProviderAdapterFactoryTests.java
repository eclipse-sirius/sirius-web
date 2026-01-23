/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.view.widget.reference.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.sirius.components.view.diagram.customnodes.CustomnodesFactory;
import org.eclipse.sirius.components.view.diagram.customnodes.EllipseNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.customnodes.provider.CustomnodesItemProviderAdapterFactory;
import org.eclipse.sirius.components.view.diagram.customnodes.provider.CustomnodesItemProviderAdapterFactory.DiagramChildCreationExtender;
import org.junit.jupiter.api.Test;

/**
 * Basic tests
 *
 * @author cbrun
 */
public class CustomnodesItemProviderAdapterFactoryTests {

    @Test
    public void createAllEcoreTypes() {
        CustomnodesItemProviderAdapterFactory factory = new CustomnodesItemProviderAdapterFactory();
        EllipseNodeStyleDescription ellipse = CustomnodesFactory.eINSTANCE.createEllipseNodeStyleDescription();
        IItemLabelProvider labelProv = (IItemLabelProvider) factory.adapt(ellipse, IItemLabelProvider.class);
        assertNotNull(labelProv, "The factory is supposed to know how to transform the Object to a Label provider");
        assertNotNull(labelProv.getText(ellipse));
        ITreeItemContentProvider childHelper = (ITreeItemContentProvider) factory.adapt(ellipse, ITreeItemContentProvider.class);
        assertNotNull(childHelper);
        assertEquals(0, childHelper.getChildren(ellipse).size());
        assertFalse(childHelper.hasChildren(ellipse));

        // completely synthetic to up the coverage
        new DiagramChildCreationExtender().getNewChildDescriptors(ellipse, null);
        ellipse.eAdapters().add((Adapter) labelProv);
        ellipse.setBorderSize(10);
        ellipse.setChildrenLayoutStrategy(null);

        IItemPropertySource props = (IItemPropertySource) factory.adapt(ellipse, IItemPropertySource.class);
        assertNotNull(props);

        factory.createBorderStyleAdapter();
        factory.createEllipseNodeStyleDescriptionAdapter();
        factory.createNodeStyleDescriptionAdapter();
        factory.createEObjectAdapter();
        factory.isFactoryForType(Object.class);
        factory.adapt(ellipse, IItemLabelProvider.class);



    }

}
