/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.springframework.stereotype.Service;

/**
 * Add support for the Flow domain to the Sirius Web server.
 *
 * @author sbegaudeau
 * @author hmarchadour
 */
@Service
public class ObjectService implements IObjectService {

    private static final String DEFAULT_LABEL_FEATURE = "name"; //$NON-NLS-1$

    private static final String ID_SEPARATOR = "#"; //$NON-NLS-1$

    private final ComposedAdapterFactory composedAdapterFactory;

    private final LabelFeatureProviderRegistry labelFeatureProviderRegistry;

    public ObjectService(ComposedAdapterFactory composedAdapterFactory, LabelFeatureProviderRegistry labelFeatureProviderRegistry) {
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
        this.labelFeatureProviderRegistry = Objects.requireNonNull(labelFeatureProviderRegistry);
    }

    @Override
    public String getId(Object object) {
        String id = null;
        if (object instanceof EObject) {
            EObject eObject = (EObject) object;

            id = this.getIdFromIDAdapter(eObject);
            if (id == null) {
                id = this.getIdFromURIFragment(eObject);
            }
            if (id == null && eObject.eIsProxy()) {
                id = ((InternalEObject) eObject).eProxyURI().toString();
            }
        }
        return id;

    }

    @Override
    public String getKind(Object object) {
        if (object instanceof EObject) {
            EObject eObject = (EObject) object;
            return new ClassIdService().getClassId(eObject.eClass());
        }
        return ""; //$NON-NLS-1$
    }

    private String getIdFromIDAdapter(EObject eObject) {
        // @formatter:off
        return eObject.eAdapters().stream()
                .filter(IDAdapter.class::isInstance)
                .map(IDAdapter.class::cast)
                .findFirst()
                .map(IDAdapter::getId)
                .map(Object::toString)
                .orElse(null);
        // @formatter:on
    }

    private String getIdFromURIFragment(EObject eObject) {
        Resource resource = eObject.eResource();
        String id = null;
        if (resource != null && resource.getURI() != null) {
            ResourceSet resourceSet = resource.getResourceSet();
            if (resourceSet != null && resourceSet.getResources().contains(resource)) {
                id = resource.getURI().lastSegment() + ID_SEPARATOR + resource.getURIFragment(eObject);
            } else {
                // In order to getObject method can retrieve the object from the id, we need to return the full URI for
                // resources that are in the PackageRegistry
                id = resource.getURI() + ID_SEPARATOR + resource.getURIFragment(eObject);
            }
        }
        return id;
    }

    @Override
    public String getLabel(Object object) {
        // @formatter:off
        return Optional.of(object).filter(EObject.class::isInstance)
                .map(EObject.class::cast)
                .flatMap(eObject -> this.getLabelEAttribute(eObject).map(eAttribute -> eObject.eGet(eAttribute)))
                .map(Object::toString)
                .orElse(""); //$NON-NLS-1$
        // @formatter:on
    }

    @Override
    public String getFullLabel(Object object) {
        String fullLabel;
        if (object instanceof EObject) {
            fullLabel = ((EObject) object).eClass().getName();
            String label = this.getLabel(object);
            if (label != null && !label.isEmpty()) {
                fullLabel += " " + label; //$NON-NLS-1$
            }
        } else {
            fullLabel = this.getLabel(object);
        }
        return fullLabel;
    }

    @Override
    public String getImagePath(Object object) {
        if (object instanceof EObject && !(object instanceof DynamicEObjectImpl)) {
            EObject eObject = (EObject) object;

            Adapter adapter = this.composedAdapterFactory.adapt(eObject, IItemLabelProvider.class);
            if (adapter instanceof IItemLabelProvider) {
                IItemLabelProvider labelProvider = (IItemLabelProvider) adapter;
                Object image = labelProvider.getImage(eObject);
                String imagePath = this.findImagePath(image);
                if (imagePath != null) {
                    String[] uriSplit = imagePath.split("!"); //$NON-NLS-1$
                    if (uriSplit.length > 1) {
                        String insideJarPath = uriSplit[uriSplit.length - 1];
                        return insideJarPath;
                    }
                }
            }
        }
        return null;
    }

    private String findImagePath(Object image) {
        String imagePath = null;
        if (image instanceof URI) {
            URI uri = (URI) image;
            imagePath = uri.toString();
        } else if (image instanceof URL) {
            URL url = (URL) image;
            imagePath = url.toString();
        } else if (image instanceof ComposedImage) {
            ComposedImage composite = (ComposedImage) image;
            // @formatter:off
            imagePath = composite.getImages().stream()
                                 .map(this::findImagePath)
                                 .filter(Objects::nonNull)
                                 .findFirst()
                                 .orElse(null);
            // @formatter:on
        }
        return imagePath;
    }

    @Override
    public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
        // @formatter:off
        return Optional.of(editingContext)
                .filter(EditingContext.class::isInstance)
                .map(EditingContext.class::cast)
                .map(EditingContext::getDomain)
                .map(EditingDomain::getResourceSet)
                .flatMap(resourceSet -> {
                    Optional<EObject> optionalEObject = Optional.empty();

                    int index = objectId.indexOf(ID_SEPARATOR);
                    if (index != -1) {
                        String resourceLastSegment = objectId.substring(0, index);
                        String eObjectURIFragment = objectId.substring(index + ID_SEPARATOR.length());
                        optionalEObject = resourceSet.getResources().stream()
                                .filter(resource -> resourceLastSegment.equals(resource.getURI().lastSegment())).findFirst()
                                .map(resource -> resource.getEObject(eObjectURIFragment));
                    } else {
                        optionalEObject = resourceSet.getResources().stream()
                                .flatMap(resource -> Optional.ofNullable(resource.getEObject(objectId)).stream())
                                .findFirst();
                    }

                    // If not found in the resources of the ResourceSet, we search in the PackageRegistry resources
                    if (!optionalEObject.isPresent()) {
                        URI uri = URI.createURI(objectId);
                        EObject eObject = resourceSet.getEObject(uri, false);
                        optionalEObject = Optional.ofNullable(eObject);
                    }
                    return optionalEObject;
                });
        // @formatter:on
    }

    @Override
    public List<Object> getContents(IEditingContext editingContext, String objectId) {
        List<Object> contents = new ArrayList<>();

        // @formatter:off
        this.getObject(editingContext, objectId)
        .filter(EObject.class::isInstance)
                .map(EObject.class::cast)
                .ifPresent(eObject -> contents.addAll(eObject.eContents()));
        // @formatter:on

        return contents;
    }

    @Override
    public Optional<String> getLabelField(Object object) {
     // @formatter:off
        return Optional.of(object)
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast)
                .flatMap(this::getLabelEAttribute)
                .map(EAttribute::getName);
        // @formatter:on
    }

    private Optional<EAttribute> getLabelEAttribute(EObject eObject) {
        Optional<EAttribute> optionalLabelEAttribute = Optional.empty();

        String nsUri = eObject.eClass().getEPackage().getNsURI();
        optionalLabelEAttribute = this.labelFeatureProviderRegistry.getLabelFeatureProvider(nsUri).flatMap(provider -> provider.getLabelEAttribute(eObject));
        if (optionalLabelEAttribute.isEmpty()) {
            optionalLabelEAttribute = this.getDefaultLabelEAttribute(eObject);
        }

        return optionalLabelEAttribute;
    }

    private Optional<EAttribute> getDefaultLabelEAttribute(EObject eObject) {
        Optional<EAttribute> optionalLabelEAttribute = Optional.empty();
        EList<EAttribute> allAttributes = eObject.eClass().getEAllAttributes();
        for (EAttribute eAttribute : allAttributes) {
            if (!eAttribute.isMany() && eAttribute.getEAttributeType().getInstanceClass() == String.class) {
                if (DEFAULT_LABEL_FEATURE.equals(eAttribute.getName())) {
                    optionalLabelEAttribute = Optional.of(eAttribute);
                    break;
                } else if (optionalLabelEAttribute.isEmpty()) {
                    optionalLabelEAttribute = Optional.of(eAttribute);
                }
            }
        }
        return optionalLabelEAttribute;
    }

    @Override
    public boolean isLabelEditable(Object object) {
        boolean isEditable = false;
        if (object instanceof EObject) {
            EObject eObject = (EObject) object;
            String nsUri = eObject.eClass().getEPackage().getNsURI();

            Optional<ILabelFeatureProvider> labelFeatureProvider = this.labelFeatureProviderRegistry.getLabelFeatureProvider(nsUri);
            if (labelFeatureProvider.isPresent()) {
                isEditable = labelFeatureProvider.get().isLabelEditable(eObject);
            } else {
                EClass eClass = eObject.eClass();
                Optional<EAttribute> labelAttribute = this.getDefaultLabelEAttribute(eClass);
                if (labelAttribute.isPresent()) {
                    isEditable = true;
                }
            }
        }
        return isEditable;
    }
}
