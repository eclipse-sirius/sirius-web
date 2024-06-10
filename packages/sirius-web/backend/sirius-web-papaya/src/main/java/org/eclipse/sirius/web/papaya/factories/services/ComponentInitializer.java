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
package org.eclipse.sirius.web.papaya.factories.services;

import java.util.function.Predicate;

import org.eclipse.sirius.components.papaya.Component;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.web.papaya.factories.services.api.IComponentInitializer;

/**
 * Used to initialize quickly a component.
 *
 * @author sbegaudeau
 */
public class ComponentInitializer implements IComponentInitializer {
    @Override
    public Component initialize(String name, String rootPackageName, Predicate<String> packageNameFilter) {
        var rootPackage = PapayaFactory.eINSTANCE.createPackage();
        rootPackage.setName(rootPackageName);

        var component = PapayaFactory.eINSTANCE.createComponent();
        component.setName(name);
        component.getPackages().add(rootPackage);

        new ReflectiveObjectFactory().createTypes(rootPackage, packageNameFilter);

        return component;
    }
}
