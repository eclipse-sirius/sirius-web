/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.emf.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.sirius.web.services.api.objects.IObjectService;
import org.junit.Test;

/**
 * Unit tests of the object service.
 *
 * @author hmarchadour
 *
 */
public class ObjectServiceTestCases {

    /**
     * Adapter to mock the expected IItemLabelProvider behavior.
     *
     * @author hmarchadour
     */
    private final class MockAdapter extends AdapterImpl implements IItemLabelProvider {
        private final String text;

        private final Object image;

        private MockAdapter(String text, Object image) {
            this.text = text;
            this.image = image;
        }

        @Override
        public String getText(Object object) {
            return this.text;
        }

        @Override
        public Object getImage(Object object) {
            return this.image;
        }
    }

    private IObjectService createObjectService(String text, Object image) {
        ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory();
        AdapterFactory adapterFactory = new AdapterFactoryImpl() {
            @Override
            public boolean isFactoryForType(Object type) {
                return true;
            }

            @Override
            protected Adapter createAdapter(Notifier target) {
                return new MockAdapter(text, image);
            }
        };
        composedAdapterFactory.addAdapterFactory(adapterFactory);
        LabelFeatureProviderRegistry labelFeatureProviderRegistry = new LabelFeatureProviderRegistry();
        return new ObjectService(new NoOpPathService(), composedAdapterFactory, labelFeatureProviderRegistry);
    }

    private EObject createEObject() {
        EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
        EClass eClass = EcoreFactory.eINSTANCE.createEClass();
        ePackage.getEClassifiers().add(eClass);
        eClass.setName(UUID.randomUUID().toString());
        eClass.setInterface(true);
        EObject obj = EcoreUtil.create(eClass);
        return obj;
    }

    @Test
    public void testURI() {
        String path = "/test"; //$NON-NLS-1$
        IObjectService objectService = this.createObjectService("", URI.createFileURI(path)); //$NON-NLS-1$
        EObject eObject = this.createEObject();
        String imagePath = objectService.getImagePath(eObject);
        assertThat(imagePath).isEqualTo("file:" + path); //$NON-NLS-1$
    }

    @Test
    public void testNull() {
        IObjectService objectService = this.createObjectService(null, null);
        String imagePath = objectService.getImagePath(null);
        assertThat(imagePath).isNull();
    }

    @Test
    public void testURL() throws MalformedURLException {
        String path = "http://siriusweb.com"; //$NON-NLS-1$
        IObjectService objectService = this.createObjectService("", new URL(path)); //$NON-NLS-1$
        EObject eObject = this.createEObject();
        String imagePath = objectService.getImagePath(eObject);
        assertThat(imagePath).isEqualTo(path);
    }

    @Test
    public void testComposedImageWithURI() throws MalformedURLException {
        String path = "/test"; //$NON-NLS-1$
        ComposedImage composedImage = new ComposedImage(List.of(URI.createFileURI(path), URI.createFileURI("path2"))); //$NON-NLS-1$
        IObjectService objectService = this.createObjectService("", composedImage); //$NON-NLS-1$
        EObject eObject = this.createEObject();
        String imagePath = objectService.getImagePath(eObject);
        assertThat(imagePath).isEqualTo("file:" + path); //$NON-NLS-1$
    }
}
