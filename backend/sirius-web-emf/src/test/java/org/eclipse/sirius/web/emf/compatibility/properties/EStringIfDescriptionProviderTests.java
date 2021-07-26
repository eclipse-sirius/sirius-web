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
package org.eclipse.sirius.web.emf.compatibility.properties;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.sirius.viewpoint.description.DescriptionPackage;
import org.eclipse.sirius.web.collaborative.validation.api.IValidationService;
import org.eclipse.sirius.web.forms.description.IfDescription;
import org.eclipse.sirius.web.representations.VariableManager;
import org.junit.jupiter.api.Test;

/**
 * Test cases for the {@link EStringIfDescriptionProvider}.
 *
 * @author pcdavid
 */
public class EStringIfDescriptionProviderTests {

    /**
     * The predicate of an {@code EStringIfDescriptionProvider} should match an {@code EAttribute} typed
     * {@code EString}.
     */
    @Test
    public void testEStringAttributeProducesTextfieldDescription() {
        AdapterFactory adapterFactory = new EcoreItemProviderAdapterFactory();
        EAttribute attribute = EcorePackage.Literals.ENAMED_ELEMENT__NAME;
        assertTrue(this.checkPredicate(adapterFactory, attribute));
    }

    /**
     * The predicate of an {@code EStringIfDescriptionProvider} should match an {@code EAttribute} typed with a custom
     * {@class EDataType} which wraps a plain {@code java.lang.String}.
     */
    @Test
    public void testStringDataTypeAttributeProducesTextfieldDescription() {
        AdapterFactory adapterFactory = new ReflectiveItemProviderAdapterFactory();
        EAttribute attribute = DescriptionPackage.Literals.CONDITIONAL_STYLE_DESCRIPTION__PREDICATE_EXPRESSION;
        assertTrue(this.checkPredicate(adapterFactory, attribute));
    }

    /**
     * The predicate of an {@code EStringIfDescriptionProvider} should NOT match an {@code EAttribute} typed
     * {@code EBoolean}.
     */
    @Test
    public void testEBooleanAttributeDoesNotProducesTextfieldDescription() {
        AdapterFactory adapterFactory = new EcoreItemProviderAdapterFactory();
        EAttribute attribute = EcorePackage.Literals.ECLASS__ABSTRACT;
        assertFalse(this.checkPredicate(adapterFactory, attribute));
    }

    private Boolean checkPredicate(AdapterFactory adapterFactory, EAttribute attribute) {
        ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(adapterFactory);
        EStringIfDescriptionProvider descriptionProvider = new EStringIfDescriptionProvider(composedAdapterFactory, new IValidationService.NoOp());
        IfDescription ifDescription = descriptionProvider.getIfDescription();

        VariableManager variableManager = new VariableManager();
        variableManager.put(PropertiesDefaultDescriptionProvider.ESTRUCTURAL_FEATURE, attribute);
        Boolean predicateResult = ifDescription.getPredicate().apply(variableManager);
        return predicateResult;
    }

}
