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
package org.eclipse.sirius.components.papaya.provider.spec;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.sirius.components.papaya.provider.TypeParameterItemProvider;

/**
 * Customization of the item provider implementation generated by EMF.
 *
 * @author sbegaudeau
 */
public class TypeParameterItemProviderSpec extends TypeParameterItemProvider {
    public TypeParameterItemProviderSpec(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("papaya/full/obj16/TypeParameter.svg"));
    }
}
