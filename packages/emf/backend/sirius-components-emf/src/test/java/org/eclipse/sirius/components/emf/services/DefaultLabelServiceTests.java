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
package org.eclipse.sirius.components.emf.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.util.EcoreAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemStyledLabelProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.StyledString;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataProvider;
import org.eclipse.sirius.components.core.api.labels.BorderStyle;
import org.eclipse.sirius.components.core.api.labels.UnderLineStyle;
import org.junit.jupiter.api.Test;

/**
 * Tests for the EMF-base {@link org.eclipse.sirius.components.core.api.IDefaultLabelService} implementation.
 *
 * @author pcdavid
 */
public class DefaultLabelServiceTests {
    @Test
    public void testFindImagePathOnCompositeImage() {
        ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(List.of(new EcoreItemProviderAdapterFactory()));
        composedAdapterFactory.addAdapterFactory(new EcoreAdapterFactory());
        composedAdapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
        DefaultLabelService labelService = new DefaultLabelService(List.of(new IRepresentationMetadataProvider.NoOp()), new LabelFeatureProviderRegistry(), composedAdapterFactory, List.of());
        EAttribute attr = EcoreFactory.eINSTANCE.createEAttribute();
        List<String> imagePath = labelService.getImagePath(attr);
        assertThat(imagePath).hasSize(1);
        assertThat(imagePath.get(0))
                .endsWith("/icons/full/obj16/EAttribute.gif");
    }

    @Test
    public void testStyledStringConverter() {
        ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(List.of(new EcoreItemProviderAdapterFactory()));
        composedAdapterFactory.addAdapterFactory(new EcoreAdapterFactory());
        composedAdapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
        var styledLabelProvider = new ItemStyledLabelProvider(composedAdapterFactory);

        StyledStringConverter styledStringConverter = new StyledStringConverter();

        EAttribute attr = EcoreFactory.eINSTANCE.createEAttribute();
        attr.setName("attribute");

        StyledString emfStyledString = (StyledString) styledLabelProvider.getStyledText(attr);
        org.eclipse.sirius.components.core.api.labels.StyledString styledString = styledStringConverter.convert(emfStyledString);

        assertThat(styledString.toString()).hasToString(emfStyledString.getString());
        assertThat(styledString.styledStringFragments()).hasSize(1);
        assertThat(styledString.styledStringFragments().get(0).styledStringFragmentStyle().getBackgroundColor()).isEqualTo("rgb(255,255,255)");
        assertThat(styledString.styledStringFragments().get(0).styledStringFragmentStyle().getUnderlineStyle()).isEqualTo(UnderLineStyle.DASHED);
        assertThat(styledString.styledStringFragments().get(0).styledStringFragmentStyle().getBorderStyle()).isEqualTo(BorderStyle.DASHED);
    }

    /**
     * Implementation of IItemStyledLabelProvider.
     *
     * @author mcharfadi
     */
    public class ItemStyledLabelProvider extends ItemProviderAdapter implements IItemStyledLabelProvider, IItemLabelProvider {
        /**
         * An instance is created from an adapter factory.
         * The factory is used as a key so that we always know which factory created this adapter.
         *
         * @param adapterFactory
         */
        public ItemStyledLabelProvider(AdapterFactory adapterFactory) {
            super(adapterFactory);
        }

        @Override
        public Object getStyledText(Object object) {
            if (object instanceof EObject eObject) {
                String label = (String) eObject.eGet(eObject.eClass().getEStructuralFeature("name"));

                var style = StyledString.Style.newBuilder()
                        .setBorderStyle(StyledString.Style.BorderStyle.DASH)
                        .setBackgroundColor(URI.createURI("color://rgb/255/255/255"))
                        .setUnderlineStyle(StyledString.Style.UnderLineStyle.LINK)
                        .toStyle();

                var styledString = new StyledString();
                styledString.append(label, style);
                return styledString;
            }
            return new StyledString();
        }

        @Override
        public String getText(Object object) {
            return getStyledText(object).toString();
        }
    }
}
