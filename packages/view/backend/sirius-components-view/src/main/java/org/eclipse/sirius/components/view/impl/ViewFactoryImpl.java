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
package org.eclipse.sirius.components.view.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.CreateInstance;
import org.eclipse.sirius.components.view.DeleteElement;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.view.For;
import org.eclipse.sirius.components.view.If;
import org.eclipse.sirius.components.view.Let;
import org.eclipse.sirius.components.view.SetValue;
import org.eclipse.sirius.components.view.UnsetValue;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class ViewFactoryImpl extends EFactoryImpl implements ViewFactory {
    /**
     * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static ViewFactory init() {
        try {
            ViewFactory theViewFactory = (ViewFactory) EPackage.Registry.INSTANCE.getEFactory(ViewPackage.eNS_URI);
            if (theViewFactory != null) {
                return theViewFactory;
            }
        } catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new ViewFactoryImpl();
    }

    /**
     * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ViewFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case ViewPackage.VIEW:
                return this.createView();
            case ViewPackage.COLOR_PALETTE:
                return this.createColorPalette();
            case ViewPackage.FIXED_COLOR:
                return this.createFixedColor();
            case ViewPackage.CHANGE_CONTEXT:
                return this.createChangeContext();
            case ViewPackage.CREATE_INSTANCE:
                return this.createCreateInstance();
            case ViewPackage.SET_VALUE:
                return this.createSetValue();
            case ViewPackage.UNSET_VALUE:
                return this.createUnsetValue();
            case ViewPackage.DELETE_ELEMENT:
                return this.createDeleteElement();
            case ViewPackage.LET:
                return this.createLet();
            case ViewPackage.IF:
                return this.createIf();
            case ViewPackage.FOR:
                return this.createFor();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object createFromString(EDataType eDataType, String initialValue) {
        switch (eDataType.getClassifierID()) {
            case ViewPackage.IDENTIFIER:
                return this.createIdentifierFromString(eDataType, initialValue);
            case ViewPackage.INTERPRETED_EXPRESSION:
                return this.createInterpretedExpressionFromString(eDataType, initialValue);
            case ViewPackage.DOMAIN_TYPE:
                return this.createDomainTypeFromString(eDataType, initialValue);
            case ViewPackage.COLOR:
                return this.createColorFromString(eDataType, initialValue);
            case ViewPackage.LENGTH:
                return this.createLengthFromString(eDataType, initialValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue) {
        switch (eDataType.getClassifierID()) {
            case ViewPackage.IDENTIFIER:
                return this.convertIdentifierToString(eDataType, instanceValue);
            case ViewPackage.INTERPRETED_EXPRESSION:
                return this.convertInterpretedExpressionToString(eDataType, instanceValue);
            case ViewPackage.DOMAIN_TYPE:
                return this.convertDomainTypeToString(eDataType, instanceValue);
            case ViewPackage.COLOR:
                return this.convertColorToString(eDataType, instanceValue);
            case ViewPackage.LENGTH:
                return this.convertLengthToString(eDataType, instanceValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public View createView() {
        ViewImpl view = new ViewImpl();
        return view;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ColorPalette createColorPalette() {
        ColorPaletteImpl colorPalette = new ColorPaletteImpl();
        return colorPalette;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public FixedColor createFixedColor() {
        FixedColorImpl fixedColor = new FixedColorImpl();
        return fixedColor;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ChangeContext createChangeContext() {
        ChangeContextImpl changeContext = new ChangeContextImpl();
        return changeContext;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CreateInstance createCreateInstance() {
        CreateInstanceImpl createInstance = new CreateInstanceImpl();
        return createInstance;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public SetValue createSetValue() {
        SetValueImpl setValue = new SetValueImpl();
        return setValue;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public UnsetValue createUnsetValue() {
        UnsetValueImpl unsetValue = new UnsetValueImpl();
        return unsetValue;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DeleteElement createDeleteElement() {
        DeleteElementImpl deleteElement = new DeleteElementImpl();
        return deleteElement;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Let createLet() {
        LetImpl let = new LetImpl();
        return let;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public If createIf() {
        IfImpl if_ = new IfImpl();
        return if_;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public For createFor() {
        ForImpl for_ = new ForImpl();
        return for_;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String createIdentifierFromString(EDataType eDataType, String initialValue) {
        return (String) super.createFromString(eDataType, initialValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String convertIdentifierToString(EDataType eDataType, Object instanceValue) {
        return super.convertToString(eDataType, instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String createInterpretedExpressionFromString(EDataType eDataType, String initialValue) {
        return (String) super.createFromString(eDataType, initialValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String convertInterpretedExpressionToString(EDataType eDataType, Object instanceValue) {
        return super.convertToString(eDataType, instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String createDomainTypeFromString(EDataType eDataType, String initialValue) {
        return (String) super.createFromString(eDataType, initialValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String convertDomainTypeToString(EDataType eDataType, Object instanceValue) {
        return super.convertToString(eDataType, instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String createColorFromString(EDataType eDataType, String initialValue) {
        return (String) super.createFromString(eDataType, initialValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String convertColorToString(EDataType eDataType, Object instanceValue) {
        return super.convertToString(eDataType, instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public Integer createLengthFromString(EDataType eDataType, String initialValue) {
        return (Integer) super.createFromString(eDataType, initialValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String convertLengthToString(EDataType eDataType, Object instanceValue) {
        return super.convertToString(eDataType, instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ViewPackage getViewPackage() {
        return (ViewPackage) this.getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @deprecated
     * @generated
     */
    @Deprecated
    public static ViewPackage getPackage() {
        return ViewPackage.eINSTANCE;
    }

} // ViewFactoryImpl
