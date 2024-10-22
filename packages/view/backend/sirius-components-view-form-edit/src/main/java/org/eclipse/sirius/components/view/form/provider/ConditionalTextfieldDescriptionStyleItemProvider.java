/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.components.view.form.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.form.ConditionalTextfieldDescriptionStyle;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.provider.ConditionalItemProvider;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.sirius.components.view.form.ConditionalTextfieldDescriptionStyle} object. <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class ConditionalTextfieldDescriptionStyleItemProvider extends ConditionalItemProvider {

    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ConditionalTextfieldDescriptionStyleItemProvider(AdapterFactory adapterFactory) {
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

            this.addFontSizePropertyDescriptor(object);
            this.addItalicPropertyDescriptor(object);
            this.addBoldPropertyDescriptor(object);
            this.addUnderlinePropertyDescriptor(object);
            this.addStrikeThroughPropertyDescriptor(object);
            this.addGridTemplateColumnsPropertyDescriptor(object);
            this.addGridTemplateRowsPropertyDescriptor(object);
            this.addLabelGridRowPropertyDescriptor(object);
            this.addLabelGridColumnPropertyDescriptor(object);
            this.addWidgetGridRowPropertyDescriptor(object);
            this.addWidgetGridColumnPropertyDescriptor(object);
            this.addGapPropertyDescriptor(object);
            this.addBackgroundColorPropertyDescriptor(object);
            this.addForegroundColorPropertyDescriptor(object);
        }
        return this.itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Font Size feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addFontSizePropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_LabelStyle_fontSize_feature"), this.getString("_UI_PropertyDescriptor_description", "_UI_LabelStyle_fontSize_feature", "_UI_LabelStyle_type"),
                ViewPackage.Literals.LABEL_STYLE__FONT_SIZE, true, false, false, ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Italic feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addItalicPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_LabelStyle_italic_feature"), this.getString("_UI_PropertyDescriptor_description", "_UI_LabelStyle_italic_feature", "_UI_LabelStyle_type"),
                ViewPackage.Literals.LABEL_STYLE__ITALIC, true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Bold feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addBoldPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_LabelStyle_bold_feature"), this.getString("_UI_PropertyDescriptor_description", "_UI_LabelStyle_bold_feature", "_UI_LabelStyle_type"),
                ViewPackage.Literals.LABEL_STYLE__BOLD, true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Underline feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addUnderlinePropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_LabelStyle_underline_feature"), this.getString("_UI_PropertyDescriptor_description", "_UI_LabelStyle_underline_feature", "_UI_LabelStyle_type"),
                ViewPackage.Literals.LABEL_STYLE__UNDERLINE, true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Strike Through feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addStrikeThroughPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_LabelStyle_strikeThrough_feature"), this.getString("_UI_PropertyDescriptor_description", "_UI_LabelStyle_strikeThrough_feature", "_UI_LabelStyle_type"),
                ViewPackage.Literals.LABEL_STYLE__STRIKE_THROUGH, true, false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Grid Template Columns feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addGridTemplateColumnsPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_WidgetGridLayout_gridTemplateColumns_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_WidgetGridLayout_gridTemplateColumns_feature", "_UI_WidgetGridLayout_type"),
                FormPackage.Literals.WIDGET_GRID_LAYOUT__GRID_TEMPLATE_COLUMNS, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Grid Template Rows feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addGridTemplateRowsPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_WidgetGridLayout_gridTemplateRows_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_WidgetGridLayout_gridTemplateRows_feature", "_UI_WidgetGridLayout_type"),
                FormPackage.Literals.WIDGET_GRID_LAYOUT__GRID_TEMPLATE_ROWS, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Label Grid Row feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addLabelGridRowPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_WidgetGridLayout_labelGridRow_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_WidgetGridLayout_labelGridRow_feature", "_UI_WidgetGridLayout_type"), FormPackage.Literals.WIDGET_GRID_LAYOUT__LABEL_GRID_ROW,
                true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Label Grid Column feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addLabelGridColumnPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_WidgetGridLayout_labelGridColumn_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_WidgetGridLayout_labelGridColumn_feature", "_UI_WidgetGridLayout_type"),
                FormPackage.Literals.WIDGET_GRID_LAYOUT__LABEL_GRID_COLUMN, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Widget Grid Row feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addWidgetGridRowPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_WidgetGridLayout_widgetGridRow_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_WidgetGridLayout_widgetGridRow_feature", "_UI_WidgetGridLayout_type"),
                FormPackage.Literals.WIDGET_GRID_LAYOUT__WIDGET_GRID_ROW, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Widget Grid Column feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addWidgetGridColumnPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_WidgetGridLayout_widgetGridColumn_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_WidgetGridLayout_widgetGridColumn_feature", "_UI_WidgetGridLayout_type"),
                FormPackage.Literals.WIDGET_GRID_LAYOUT__WIDGET_GRID_COLUMN, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Gap feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addGapPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_WidgetGridLayout_gap_feature"), this.getString("_UI_PropertyDescriptor_description", "_UI_WidgetGridLayout_gap_feature", "_UI_WidgetGridLayout_type"),
                FormPackage.Literals.WIDGET_GRID_LAYOUT__GAP, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Background Color feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addBackgroundColorPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_TextfieldDescriptionStyle_backgroundColor_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_TextfieldDescriptionStyle_backgroundColor_feature", "_UI_TextfieldDescriptionStyle_type"),
                FormPackage.Literals.TEXTFIELD_DESCRIPTION_STYLE__BACKGROUND_COLOR, true, false, false, null, null, null));
    }

    /**
     * This adds a property descriptor for the Foreground Color feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addForegroundColorPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_TextfieldDescriptionStyle_foregroundColor_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_TextfieldDescriptionStyle_foregroundColor_feature", "_UI_TextfieldDescriptionStyle_type"),
                FormPackage.Literals.TEXTFIELD_DESCRIPTION_STYLE__FOREGROUND_COLOR, true, false, false, null, null, null));
    }

    /**
     * This returns ConditionalTextfieldDescriptionStyle.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/ConditionalStyle.svg"));
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
        String label = ((ConditionalTextfieldDescriptionStyle) object).getCondition();
        return label == null || label.length() == 0 ? this.getString("_UI_ConditionalTextfieldDescriptionStyle_type") : this.getString("_UI_ConditionalTextfieldDescriptionStyle_type") + " " + label;
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

        switch (notification.getFeatureID(ConditionalTextfieldDescriptionStyle.class)) {
            case FormPackage.CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__FONT_SIZE:
            case FormPackage.CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__ITALIC:
            case FormPackage.CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__BOLD:
            case FormPackage.CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__UNDERLINE:
            case FormPackage.CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__STRIKE_THROUGH:
            case FormPackage.CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__GRID_TEMPLATE_COLUMNS:
            case FormPackage.CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__GRID_TEMPLATE_ROWS:
            case FormPackage.CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__LABEL_GRID_ROW:
            case FormPackage.CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__LABEL_GRID_COLUMN:
            case FormPackage.CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__WIDGET_GRID_ROW:
            case FormPackage.CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__WIDGET_GRID_COLUMN:
            case FormPackage.CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__GAP:
            case FormPackage.CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__BACKGROUND_COLOR:
            case FormPackage.CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE__FOREGROUND_COLOR:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
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
    }

}
