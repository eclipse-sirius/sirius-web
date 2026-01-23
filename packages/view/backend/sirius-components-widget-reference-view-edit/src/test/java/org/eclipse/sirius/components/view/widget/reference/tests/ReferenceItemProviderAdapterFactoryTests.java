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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.sirius.components.view.widget.reference.ReferenceFactory;
import org.eclipse.sirius.components.view.widget.reference.provider.ReferenceItemProviderAdapterFactory;
import org.junit.jupiter.api.Test;

/**
 * Basic tests
 *
 * @author cbrun
 */
public class ReferenceItemProviderAdapterFactoryTests {

	@Test
	public void createAllEcoreTypes() {
		ReferenceItemProviderAdapterFactory factory = new ReferenceItemProviderAdapterFactory();
		assertNotNull(
				factory.adapt(ReferenceFactory.eINSTANCE.createReferenceWidgetDescription(), IItemLabelProvider.class),
				"The factory is supposed to know how to transform the Object to a Label provider");
		assertNotNull(
				factory.adapt(ReferenceFactory.eINSTANCE.createConditionalReferenceWidgetDescriptionStyle(),
						IItemLabelProvider.class),
				"The factory is supposed to know how to transform the Object to a Label provider");
		assertNotNull(
				factory.adapt(ReferenceFactory.eINSTANCE.createConditionalReferenceWidgetDescriptionStyle(),
						IItemLabelProvider.class),
				"The factory is supposed to know how to transform the Object to a Label provider");
	}

}
