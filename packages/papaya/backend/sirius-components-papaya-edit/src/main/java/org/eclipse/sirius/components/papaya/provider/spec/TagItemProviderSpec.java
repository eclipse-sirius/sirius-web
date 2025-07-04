/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import org.eclipse.emf.edit.provider.StyledString;
import org.eclipse.sirius.components.papaya.Tag;
import org.eclipse.sirius.components.papaya.provider.TagItemProvider;

/**
 * Customization of the item provider implementation generated by EMF.
 *
 * @author sbegaudeau
 */
public class TagItemProviderSpec extends TagItemProvider {
    public TagItemProviderSpec(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("papaya/full/obj16/Tag.svg"));
    }

    @Override
    public Object getStyledText(Object object) {
        if (object instanceof Tag tag) {
            StyledString styledLabel = new StyledString();
            styledLabel.append("[");
            styledLabel.append(String.valueOf(tag.getKey()));
            styledLabel.append("=");
            styledLabel.append(String.valueOf(tag.getValue()));
            styledLabel.append("]");
            return styledLabel;
        }
        return super.getStyledText(object);
    }
}
