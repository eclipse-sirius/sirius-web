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
package org.eclipse.sirius.components.papaya.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.StyledString;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.components.papaya.ContainingLink;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.PapayaPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.papaya.ContainingLink} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class ContainingLinkItemProvider extends LinkItemProvider {
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ContainingLinkItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        if (this.itemPropertyDescriptors == null) {
            super.getPropertyDescriptors(object);

        }
        return this.itemPropertyDescriptors;
    }

    /**
     * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
     * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
     * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    @Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
        if (this.childrenFeatures == null) {
            super.getChildrenFeatures(object);
            this.childrenFeatures.add(PapayaPackage.Literals.CONTAINING_LINK__TARGET);
        }
        return this.childrenFeatures;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EStructuralFeature getChildFeature(Object object, Object child) {
        // Check the type of the specified child object and return the proper feature to use for
        // adding (see {@link AddCommand}) it as a child.

        return super.getChildFeature(object, child);
    }

    /**
     * This returns ContainingLink.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/ContainingLink"));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected boolean shouldComposeCreationImage() {
        return true;
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getText(Object object) {
        return ((StyledString) this.getStyledText(object)).getString();
    }

    /**
     * This returns the label styled text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object getStyledText(Object object) {
        String label = ((ContainingLink) object).getKind();
        StyledString styledLabel = new StyledString();
        if (label == null || label.length() == 0) {
            styledLabel.append(this.getString("_UI_ContainingLink_type"), StyledString.Style.QUALIFIER_STYLER);
        } else {
            styledLabel.append(this.getString("_UI_ContainingLink_type"), StyledString.Style.QUALIFIER_STYLER).append(" " + label);
        }
        return styledLabel;
    }

    /**
     * This handles model notifications by calling {@link #updateChildren} to update any cached children and by creating
     * a viewer notification, which it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    @Override
    public void notifyChanged(Notification notification) {
        this.updateChildren(notification);

        switch (notification.getFeatureID(ContainingLink.class)) {
            case PapayaPackage.CONTAINING_LINK__TARGET:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
                return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children that can be created
     * under this object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createFolder()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createProject()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createOperationalCapability()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createOperationalEntity()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createOperationalActor()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createOperationalProcess()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createOperationalActivity()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createOperationalInteraction()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createIteration()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createTask()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createContribution()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createComponent()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createComponentPort()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createComponentExchange()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createProvidedService()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createRequiredService()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createPackage()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createGenericType()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createAnnotation()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createAnnotationField()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createTypeParameter()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createInterface()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createClass()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createAttribute()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createOperation()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createParameter()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createRecord()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createRecordComponent()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createDataType()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createEnum()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createEnumLiteral()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createApplicationConcern()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createController()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createDomain()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createService()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createEvent()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createCommand()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createQuery()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createRepository()));

        newChildDescriptors.add(this.createChildParameter(PapayaPackage.Literals.CONTAINING_LINK__TARGET, PapayaFactory.eINSTANCE.createChannel()));
    }

}
