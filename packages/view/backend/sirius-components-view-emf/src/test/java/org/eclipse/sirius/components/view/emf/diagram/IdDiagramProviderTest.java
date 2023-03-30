/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.view.emf.diagram;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.LabelFeatureProviderRegistry;
import org.eclipse.sirius.components.emf.services.ObjectService;
import org.eclipse.sirius.components.emf.services.api.IEMFKindService;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.junit.jupiter.api.Test;

/**
 * Tests for generating ids for diagramDescription edgeDescription & nodeDescription.
 *
 * @author mcharfadi
 */
public class IdDiagramProviderTest {

    private static final String SIRIUS_PROTOCOL = "siriusComponents://";

    private static final String SOURCE_KIND_VIEW = "?sourceKind=view";

    private static final String DIAGRAM_PATH_VIEW = SIRIUS_PROTOCOL + "diagramDescription" + SOURCE_KIND_VIEW;

    private static final String SOURCE_ID = "sourceId=";

    private static final String SOURCE_ELEMENT_ID = "sourceElementId=";

    private static final String AMPERSAND = "&";

    private static final UUID SOURCE_ID_VALUE = UUID.randomUUID();

    private static final UUID SOURCE_ELEMENT_ID_VALUE = UUID.randomUUID();

    private static final URI RESOURCE_URI = URI.createURI(EditingContext.RESOURCE_SCHEME + ":///" + SOURCE_ID_VALUE);

    private static final String DIAGRAM_DESCRIPTION_ID = DIAGRAM_PATH_VIEW + AMPERSAND + SOURCE_ID + SOURCE_ID_VALUE + AMPERSAND + SOURCE_ELEMENT_ID + SOURCE_ELEMENT_ID_VALUE;

    @Test
    void testSetDiagramID() throws Exception {
        DiagramDescription diagramDescription = ViewFactory.eINSTANCE.createDiagramDescription();

        IDAdapter idAdapter = new IDAdapter(SOURCE_ELEMENT_ID_VALUE);
        diagramDescription.eAdapters().add(idAdapter);

        Resource resource = new XMIResourceImpl(RESOURCE_URI);
        resource.getContents().add(diagramDescription);

        ObjectService objectService = new ObjectService(new IEMFKindService.NoOp(), new ComposedAdapterFactory(), new LabelFeatureProviderRegistry());
        IdDiagramProvider idDigramProvider = new IdDiagramProvider(objectService);

        String generatedDiagramDescriptionId = idDigramProvider.getIdDiagramDescription(diagramDescription);
        assertThat(generatedDiagramDescriptionId.equals(DIAGRAM_DESCRIPTION_ID));
    }
}
