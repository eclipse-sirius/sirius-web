/**
 * Copyright (c) 2021, 2022 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.sirius.components.view.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.components.view.BarChartDescription;
import org.eclipse.sirius.components.view.ButtonDescription;
import org.eclipse.sirius.components.view.CheckboxDescription;
import org.eclipse.sirius.components.view.FlexboxContainerDescription;
import org.eclipse.sirius.components.view.FormDescription;
import org.eclipse.sirius.components.view.ImageDescription;
import org.eclipse.sirius.components.view.LabelDescription;
import org.eclipse.sirius.components.view.LinkDescription;
import org.eclipse.sirius.components.view.ListDescription;
import org.eclipse.sirius.components.view.MultiSelectDescription;
import org.eclipse.sirius.components.view.PieChartDescription;
import org.eclipse.sirius.components.view.RadioDescription;
import org.eclipse.sirius.components.view.RichTextDescription;
import org.eclipse.sirius.components.view.SelectDescription;
import org.eclipse.sirius.components.view.TextAreaDescription;
import org.eclipse.sirius.components.view.TextfieldDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.view.FormDescription} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class FormDescriptionItemProvider extends RepresentationDescriptionItemProvider {
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public FormDescriptionItemProvider(AdapterFactory adapterFactory) {
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
            this.childrenFeatures.add(ViewPackage.Literals.FORM_DESCRIPTION__TOOLBAR_ACTIONS);
            this.childrenFeatures.add(ViewPackage.Literals.FORM_DESCRIPTION__WIDGETS);
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
     * This returns FormDescription.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/FormDescription.svg")); //$NON-NLS-1$
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
        String label = ((FormDescription) object).getName();
        return label == null || label.length() == 0 ? this.getString("_UI_FormDescription_type") : //$NON-NLS-1$
                this.getString("_UI_FormDescription_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

        switch (notification.getFeatureID(FormDescription.class)) {
        case ViewPackage.FORM_DESCRIPTION__TOOLBAR_ACTIONS:
        case ViewPackage.FORM_DESCRIPTION__WIDGETS:
            this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
            return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children that can be created
     * under this object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        BarChartDescription barChartDescription = ViewFactory.eINSTANCE.createBarChartDescription();
        barChartDescription.setStyle(ViewFactory.eINSTANCE.createBarChartDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.FORM_DESCRIPTION__WIDGETS, barChartDescription));

        ButtonDescription buttonDescription = ViewFactory.eINSTANCE.createButtonDescription();
        buttonDescription.setStyle(ViewFactory.eINSTANCE.createButtonDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.FORM_DESCRIPTION__WIDGETS, buttonDescription));

        CheckboxDescription checkboxDescription = ViewFactory.eINSTANCE.createCheckboxDescription();
        checkboxDescription.setStyle(ViewFactory.eINSTANCE.createCheckboxDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.FORM_DESCRIPTION__WIDGETS, checkboxDescription));

        FlexboxContainerDescription flexboxContainerDescription = ViewFactory.eINSTANCE.createFlexboxContainerDescription();
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.FORM_DESCRIPTION__WIDGETS, flexboxContainerDescription));

        ImageDescription imageDescription = ViewFactory.eINSTANCE.createImageDescription();
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.FORM_DESCRIPTION__WIDGETS, imageDescription));

        LabelDescription labelDescription = ViewFactory.eINSTANCE.createLabelDescription();
        labelDescription.setStyle(ViewFactory.eINSTANCE.createLabelDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.FORM_DESCRIPTION__WIDGETS, labelDescription));

        LinkDescription linkDescription = ViewFactory.eINSTANCE.createLinkDescription();
        linkDescription.setStyle(ViewFactory.eINSTANCE.createLinkDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.FORM_DESCRIPTION__WIDGETS, linkDescription));

        ListDescription listDescription = ViewFactory.eINSTANCE.createListDescription();
        listDescription.setStyle(ViewFactory.eINSTANCE.createListDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.FORM_DESCRIPTION__WIDGETS, listDescription));

        PieChartDescription pieChartDescription = ViewFactory.eINSTANCE.createPieChartDescription();
        pieChartDescription.setStyle(ViewFactory.eINSTANCE.createPieChartDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.FORM_DESCRIPTION__WIDGETS, pieChartDescription));

        RadioDescription radioDescription = ViewFactory.eINSTANCE.createRadioDescription();
        radioDescription.setStyle(ViewFactory.eINSTANCE.createRadioDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.FORM_DESCRIPTION__WIDGETS, radioDescription));

        RichTextDescription richTextDescription = ViewFactory.eINSTANCE.createRichTextDescription();
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.FORM_DESCRIPTION__WIDGETS, richTextDescription));

        SelectDescription selectDescription = ViewFactory.eINSTANCE.createSelectDescription();
        selectDescription.setStyle(ViewFactory.eINSTANCE.createSelectDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.FORM_DESCRIPTION__WIDGETS, selectDescription));

        MultiSelectDescription multiSelectDescription = ViewFactory.eINSTANCE.createMultiSelectDescription();
        multiSelectDescription.setStyle(ViewFactory.eINSTANCE.createMultiSelectDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.FORM_DESCRIPTION__WIDGETS, multiSelectDescription));

        TextAreaDescription textareaDescription = ViewFactory.eINSTANCE.createTextAreaDescription();
        textareaDescription.setStyle(ViewFactory.eINSTANCE.createTextareaDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.FORM_DESCRIPTION__WIDGETS, textareaDescription));

        TextfieldDescription textfieldDescription = ViewFactory.eINSTANCE.createTextfieldDescription();
        textfieldDescription.setStyle(ViewFactory.eINSTANCE.createTextfieldDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.FORM_DESCRIPTION__WIDGETS, textfieldDescription));

        ButtonDescription toolbarActionDescription = ViewFactory.eINSTANCE.createButtonDescription();
        toolbarActionDescription.setStyle(ViewFactory.eINSTANCE.createButtonDescriptionStyle());
        toolbarActionDescription.setName("ToolbarAction"); //$NON-NLS-1$
        toolbarActionDescription.setStyle(ViewFactory.eINSTANCE.createButtonDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.FORM_DESCRIPTION__TOOLBAR_ACTIONS, toolbarActionDescription));
    }

    /**
     * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
        Object childFeature = feature;
        Object childObject = child;

        boolean qualify = childFeature == ViewPackage.Literals.FORM_DESCRIPTION__TOOLBAR_ACTIONS || childFeature == ViewPackage.Literals.FORM_DESCRIPTION__WIDGETS;

        if (qualify) {
            return this.getString("_UI_CreateChild_text2", //$NON-NLS-1$
                    new Object[] { this.getTypeText(childObject), this.getFeatureText(childFeature), this.getTypeText(owner) });
        }
        return super.getCreateChildText(owner, feature, child, selection);
    }
}
