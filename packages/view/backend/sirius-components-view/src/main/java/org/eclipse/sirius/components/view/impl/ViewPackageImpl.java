/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.sirius.components.view.*;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class ViewPackageImpl extends EPackageImpl implements ViewPackage {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass viewEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass colorPaletteEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass fixedColorEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass userColorEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass representationDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass diagramDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass diagramElementDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass nodeDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass edgeDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass labelStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass borderStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass styleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass nodeStyleDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass rectangularNodeStyleDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass imageNodeStyleDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass iconLabelNodeStyleDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass layoutStrategyDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass freeFormLayoutStrategyDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass listLayoutStrategyDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass edgeStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass toolEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass labelEditToolEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass deleteToolEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass nodeToolEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass edgeToolEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass edgeReconnectionToolEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass sourceEdgeEndReconnectionToolEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass targetEdgeEndReconnectionToolEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass dropToolEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass operationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass changeContextEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass createInstanceEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass setValueEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass unsetValueEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass deleteElementEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass createViewEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass deleteViewEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalNodeStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalEdgeStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass formDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass pageDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass groupDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass widgetDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass textfieldDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass checkboxDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass selectDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass multiSelectDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass textAreaDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass richTextDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass radioDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass barChartDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass pieChartDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass flexboxContainerDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass buttonDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass imageDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass widgetDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass textfieldDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalTextfieldDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass checkboxDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalCheckboxDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass selectDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalSelectDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass multiSelectDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalMultiSelectDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass textareaDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalTextareaDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass radioDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalRadioDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass buttonDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalButtonDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass barChartDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalBarChartDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass pieChartDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalPieChartDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass labelDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass labelDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalLabelDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass linkDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass linkDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalLinkDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass listDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass listDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass conditionalListDescriptionStyleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass diagramPaletteEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass nodePaletteEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass edgePaletteEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass selectionDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass dynamicDialogFolderEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass dynamicDialogDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass dWidgetDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass dSelectWidgetDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass dTextFieldWidgetDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass dValidationMessageDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass dWidgetOutputDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass dWidgetObjectOutputDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass dWidgetStringOutputDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EEnum layoutDirectionEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EEnum arrowStyleEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EEnum lineStyleEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EEnum nodeContainmentKindEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EEnum synchronizationPolicyEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EEnum groupDisplayModeEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EEnum flexDirectionEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EEnum dValidationMessageSeverityEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EDataType identifierEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EDataType interpretedExpressionEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EDataType domainTypeEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EDataType colorEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EDataType lengthEDataType = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
     * EPackage.Registry} by the package package URI value.
     * <p>
     * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
     * performs initialization of the package, or returns the registered package, if one already exists. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see org.eclipse.sirius.components.view.ViewPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private ViewPackageImpl() {
        super(eNS_URI, ViewFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     *
     * <p>
     * This method is used to initialize {@link ViewPackage#eINSTANCE} when that field is accessed. Clients should not
     * invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static ViewPackage init() {
        if (isInited)
            return (ViewPackage) EPackage.Registry.INSTANCE.getEPackage(ViewPackage.eNS_URI);

        // Obtain or create and register package
        Object registeredViewPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
        ViewPackageImpl theViewPackage = registeredViewPackage instanceof ViewPackageImpl ? (ViewPackageImpl) registeredViewPackage : new ViewPackageImpl();

        isInited = true;

        // Create package meta-data objects
        theViewPackage.createPackageContents();

        // Initialize created meta-data
        theViewPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theViewPackage.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(ViewPackage.eNS_URI, theViewPackage);
        return theViewPackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getView() {
        return this.viewEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getView_Descriptions() {
        return (EReference) this.viewEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getView_ColorPalettes() {
        return (EReference) this.viewEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getView_DynamicDialogFolder() {
        return (EReference) this.viewEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getColorPalette() {
        return this.colorPaletteEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getColorPalette_Name() {
        return (EAttribute) this.colorPaletteEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getColorPalette_Colors() {
        return (EReference) this.colorPaletteEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getFixedColor() {
        return this.fixedColorEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getFixedColor_Value() {
        return (EAttribute) this.fixedColorEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getUserColor() {
        return this.userColorEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getUserColor_Name() {
        return (EAttribute) this.userColorEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getRepresentationDescription() {
        return this.representationDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getRepresentationDescription_Name() {
        return (EAttribute) this.representationDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getRepresentationDescription_DomainType() {
        return (EAttribute) this.representationDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getRepresentationDescription_PreconditionExpression() {
        return (EAttribute) this.representationDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getRepresentationDescription_TitleExpression() {
        return (EAttribute) this.representationDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDiagramDescription() {
        return this.diagramDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDiagramDescription_AutoLayout() {
        return (EAttribute) this.diagramDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDiagramDescription_Palette() {
        return (EReference) this.diagramDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDiagramDescription_NodeDescriptions() {
        return (EReference) this.diagramDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDiagramDescription_EdgeDescriptions() {
        return (EReference) this.diagramDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDiagramElementDescription() {
        return this.diagramElementDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDiagramElementDescription_Name() {
        return (EAttribute) this.diagramElementDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDiagramElementDescription_DomainType() {
        return (EAttribute) this.diagramElementDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDiagramElementDescription_SemanticCandidatesExpression() {
        return (EAttribute) this.diagramElementDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDiagramElementDescription_PreconditionExpression() {
        return (EAttribute) this.diagramElementDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDiagramElementDescription_SynchronizationPolicy() {
        return (EAttribute) this.diagramElementDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDiagramElementDescription_LabelExpression() {
        return (EAttribute) this.diagramElementDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getNodeDescription() {
        return this.nodeDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getNodeDescription_Collapsible() {
        return (EAttribute) this.nodeDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeDescription_Palette() {
        return (EReference) this.nodeDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeDescription_ChildrenLayoutStrategy() {
        return (EReference) this.nodeDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeDescription_Style() {
        return (EReference) this.nodeDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeDescription_ConditionalStyles() {
        return (EReference) this.nodeDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeDescription_ChildrenDescriptions() {
        return (EReference) this.nodeDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeDescription_BorderNodesDescriptions() {
        return (EReference) this.nodeDescriptionEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeDescription_ReusedChildNodeDescriptions() {
        return (EReference) this.nodeDescriptionEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeDescription_ReusedBorderNodeDescriptions() {
        return (EReference) this.nodeDescriptionEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getNodeDescription_UserResizable() {
        return (EAttribute) this.nodeDescriptionEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getEdgeDescription() {
        return this.edgeDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeDescription_BeginLabelExpression() {
        return (EAttribute) this.edgeDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeDescription_EndLabelExpression() {
        return (EAttribute) this.edgeDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeDescription_IsDomainBasedEdge() {
        return (EAttribute) this.edgeDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgeDescription_Palette() {
        return (EReference) this.edgeDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgeDescription_SourceNodeDescriptions() {
        return (EReference) this.edgeDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgeDescription_TargetNodeDescriptions() {
        return (EReference) this.edgeDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeDescription_SourceNodesExpression() {
        return (EAttribute) this.edgeDescriptionEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeDescription_TargetNodesExpression() {
        return (EAttribute) this.edgeDescriptionEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgeDescription_Style() {
        return (EReference) this.edgeDescriptionEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgeDescription_ConditionalStyles() {
        return (EReference) this.edgeDescriptionEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getLabelStyle() {
        return this.labelStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getLabelStyle_FontSize() {
        return (EAttribute) this.labelStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getLabelStyle_Italic() {
        return (EAttribute) this.labelStyleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getLabelStyle_Bold() {
        return (EAttribute) this.labelStyleEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getLabelStyle_Underline() {
        return (EAttribute) this.labelStyleEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getLabelStyle_StrikeThrough() {
        return (EAttribute) this.labelStyleEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getBorderStyle() {
        return this.borderStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getBorderStyle_BorderColor() {
        return (EReference) this.borderStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getBorderStyle_BorderRadius() {
        return (EAttribute) this.borderStyleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getBorderStyle_BorderSize() {
        return (EAttribute) this.borderStyleEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getBorderStyle_BorderLineStyle() {
        return (EAttribute) this.borderStyleEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getStyle() {
        return this.styleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getStyle_Color() {
        return (EReference) this.styleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getNodeStyleDescription() {
        return this.nodeStyleDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeStyleDescription_LabelColor() {
        return (EReference) this.nodeStyleDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getNodeStyleDescription_WidthComputationExpression() {
        return (EAttribute) this.nodeStyleDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getNodeStyleDescription_HeightComputationExpression() {
        return (EAttribute) this.nodeStyleDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getNodeStyleDescription_ShowIcon() {
        return (EAttribute) this.nodeStyleDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getRectangularNodeStyleDescription() {
        return this.rectangularNodeStyleDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getRectangularNodeStyleDescription_WithHeader() {
        return (EAttribute) this.rectangularNodeStyleDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getImageNodeStyleDescription() {
        return this.imageNodeStyleDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getImageNodeStyleDescription_Shape() {
        return (EAttribute) this.imageNodeStyleDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getIconLabelNodeStyleDescription() {
        return this.iconLabelNodeStyleDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getLayoutStrategyDescription() {
        return this.layoutStrategyDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getFreeFormLayoutStrategyDescription() {
        return this.freeFormLayoutStrategyDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getListLayoutStrategyDescription() {
        return this.listLayoutStrategyDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getEdgeStyle() {
        return this.edgeStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeStyle_LineStyle() {
        return (EAttribute) this.edgeStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeStyle_SourceArrowStyle() {
        return (EAttribute) this.edgeStyleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeStyle_TargetArrowStyle() {
        return (EAttribute) this.edgeStyleEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeStyle_EdgeWidth() {
        return (EAttribute) this.edgeStyleEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getEdgeStyle_ShowIcon() {
        return (EAttribute) this.edgeStyleEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getTool() {
        return this.toolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTool_Name() {
        return (EAttribute) this.toolEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTool_Body() {
        return (EReference) this.toolEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getLabelEditTool() {
        return this.labelEditToolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getLabelEditTool_InitialDirectEditLabelExpression() {
        return (EAttribute) this.labelEditToolEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDeleteTool() {
        return this.deleteToolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getNodeTool() {
        return this.nodeToolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodeTool_SelectionDescription() {
        return (EReference) this.nodeToolEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getEdgeTool() {
        return this.edgeToolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgeTool_TargetElementDescriptions() {
        return (EReference) this.edgeToolEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getEdgeReconnectionTool() {
        return this.edgeReconnectionToolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getSourceEdgeEndReconnectionTool() {
        return this.sourceEdgeEndReconnectionToolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getTargetEdgeEndReconnectionTool() {
        return this.targetEdgeEndReconnectionToolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDropTool() {
        return this.dropToolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getOperation() {
        return this.operationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getOperation_Children() {
        return (EReference) this.operationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getChangeContext() {
        return this.changeContextEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getChangeContext_Expression() {
        return (EAttribute) this.changeContextEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getCreateInstance() {
        return this.createInstanceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getCreateInstance_TypeName() {
        return (EAttribute) this.createInstanceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getCreateInstance_ReferenceName() {
        return (EAttribute) this.createInstanceEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getCreateInstance_VariableName() {
        return (EAttribute) this.createInstanceEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getSetValue() {
        return this.setValueEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getSetValue_FeatureName() {
        return (EAttribute) this.setValueEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getSetValue_ValueExpression() {
        return (EAttribute) this.setValueEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getUnsetValue() {
        return this.unsetValueEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getUnsetValue_FeatureName() {
        return (EAttribute) this.unsetValueEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getUnsetValue_ElementExpression() {
        return (EAttribute) this.unsetValueEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDeleteElement() {
        return this.deleteElementEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getCreateView() {
        return this.createViewEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getCreateView_ParentViewExpression() {
        return (EAttribute) this.createViewEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getCreateView_ElementDescription() {
        return (EReference) this.createViewEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getCreateView_SemanticElementExpression() {
        return (EAttribute) this.createViewEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getCreateView_VariableName() {
        return (EAttribute) this.createViewEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getCreateView_ContainmentKind() {
        return (EAttribute) this.createViewEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDeleteView() {
        return this.deleteViewEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDeleteView_ViewExpression() {
        return (EAttribute) this.deleteViewEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditional() {
        return this.conditionalEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getConditional_Condition() {
        return (EAttribute) this.conditionalEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalNodeStyle() {
        return this.conditionalNodeStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getConditionalNodeStyle_Style() {
        return (EReference) this.conditionalNodeStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalEdgeStyle() {
        return this.conditionalEdgeStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getFormDescription() {
        return this.formDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getFormDescription_Pages() {
        return (EReference) this.formDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getPageDescription() {
        return this.pageDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPageDescription_Name() {
        return (EAttribute) this.pageDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPageDescription_LabelExpression() {
        return (EAttribute) this.pageDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPageDescription_SemanticCandidatesExpression() {
        return (EAttribute) this.pageDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPageDescription_PreconditionExpression() {
        return (EAttribute) this.pageDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPageDescription_Groups() {
        return (EReference) this.pageDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPageDescription_ToolbarActions() {
        return (EReference) this.pageDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getGroupDescription() {
        return this.groupDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getGroupDescription_Name() {
        return (EAttribute) this.groupDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getGroupDescription_LabelExpression() {
        return (EAttribute) this.groupDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getGroupDescription_DisplayMode() {
        return (EAttribute) this.groupDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getGroupDescription_SemanticCandidatesExpression() {
        return (EAttribute) this.groupDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getGroupDescription_ToolbarActions() {
        return (EReference) this.groupDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getGroupDescription_Widgets() {
        return (EReference) this.groupDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getWidgetDescription() {
        return this.widgetDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getWidgetDescription_Name() {
        return (EAttribute) this.widgetDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getWidgetDescription_LabelExpression() {
        return (EAttribute) this.widgetDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getTextfieldDescription() {
        return this.textfieldDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTextfieldDescription_ValueExpression() {
        return (EAttribute) this.textfieldDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTextfieldDescription_Body() {
        return (EReference) this.textfieldDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTextfieldDescription_Style() {
        return (EReference) this.textfieldDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTextfieldDescription_ConditionalStyles() {
        return (EReference) this.textfieldDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getCheckboxDescription() {
        return this.checkboxDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getCheckboxDescription_ValueExpression() {
        return (EAttribute) this.checkboxDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getCheckboxDescription_Body() {
        return (EReference) this.checkboxDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getCheckboxDescription_Style() {
        return (EReference) this.checkboxDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getCheckboxDescription_ConditionalStyles() {
        return (EReference) this.checkboxDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getSelectDescription() {
        return this.selectDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getSelectDescription_ValueExpression() {
        return (EAttribute) this.selectDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getSelectDescription_CandidatesExpression() {
        return (EAttribute) this.selectDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getSelectDescription_CandidateLabelExpression() {
        return (EAttribute) this.selectDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getSelectDescription_Body() {
        return (EReference) this.selectDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getSelectDescription_Style() {
        return (EReference) this.selectDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getSelectDescription_ConditionalStyles() {
        return (EReference) this.selectDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getMultiSelectDescription() {
        return this.multiSelectDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getMultiSelectDescription_ValueExpression() {
        return (EAttribute) this.multiSelectDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getMultiSelectDescription_CandidatesExpression() {
        return (EAttribute) this.multiSelectDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getMultiSelectDescription_CandidateLabelExpression() {
        return (EAttribute) this.multiSelectDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMultiSelectDescription_Body() {
        return (EReference) this.multiSelectDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMultiSelectDescription_Style() {
        return (EReference) this.multiSelectDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMultiSelectDescription_ConditionalStyles() {
        return (EReference) this.multiSelectDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getTextAreaDescription() {
        return this.textAreaDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTextAreaDescription_ValueExpression() {
        return (EAttribute) this.textAreaDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTextAreaDescription_Body() {
        return (EReference) this.textAreaDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTextAreaDescription_Style() {
        return (EReference) this.textAreaDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTextAreaDescription_ConditionalStyles() {
        return (EReference) this.textAreaDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getRichTextDescription() {
        return this.richTextDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getRichTextDescription_ValueExpression() {
        return (EAttribute) this.richTextDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRichTextDescription_Body() {
        return (EReference) this.richTextDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getRadioDescription() {
        return this.radioDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getRadioDescription_ValueExpression() {
        return (EAttribute) this.radioDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getRadioDescription_CandidatesExpression() {
        return (EAttribute) this.radioDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getRadioDescription_CandidateLabelExpression() {
        return (EAttribute) this.radioDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRadioDescription_Body() {
        return (EReference) this.radioDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRadioDescription_Style() {
        return (EReference) this.radioDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRadioDescription_ConditionalStyles() {
        return (EReference) this.radioDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getBarChartDescription() {
        return this.barChartDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getBarChartDescription_ValuesExpression() {
        return (EAttribute) this.barChartDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getBarChartDescription_KeysExpression() {
        return (EAttribute) this.barChartDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getBarChartDescription_YAxisLabelExpression() {
        return (EAttribute) this.barChartDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getBarChartDescription_Style() {
        return (EReference) this.barChartDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getBarChartDescription_ConditionalStyles() {
        return (EReference) this.barChartDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getBarChartDescription_Width() {
        return (EAttribute) this.barChartDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getBarChartDescription_Height() {
        return (EAttribute) this.barChartDescriptionEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getPieChartDescription() {
        return this.pieChartDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPieChartDescription_ValuesExpression() {
        return (EAttribute) this.pieChartDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPieChartDescription_KeysExpression() {
        return (EAttribute) this.pieChartDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPieChartDescription_Style() {
        return (EReference) this.pieChartDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPieChartDescription_ConditionalStyles() {
        return (EReference) this.pieChartDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getFlexboxContainerDescription() {
        return this.flexboxContainerDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getFlexboxContainerDescription_Children() {
        return (EReference) this.flexboxContainerDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getFlexboxContainerDescription_FlexDirection() {
        return (EAttribute) this.flexboxContainerDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getButtonDescription() {
        return this.buttonDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getButtonDescription_ButtonLabelExpression() {
        return (EAttribute) this.buttonDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getButtonDescription_Body() {
        return (EReference) this.buttonDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getButtonDescription_ImageExpression() {
        return (EAttribute) this.buttonDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getButtonDescription_Style() {
        return (EReference) this.buttonDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getButtonDescription_ConditionalStyles() {
        return (EReference) this.buttonDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getImageDescription() {
        return this.imageDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getImageDescription_UrlExpression() {
        return (EAttribute) this.imageDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getImageDescription_MaxWidthExpression() {
        return (EAttribute) this.imageDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getWidgetDescriptionStyle() {
        return this.widgetDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getTextfieldDescriptionStyle() {
        return this.textfieldDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTextfieldDescriptionStyle_BackgroundColor() {
        return (EReference) this.textfieldDescriptionStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTextfieldDescriptionStyle_ForegroundColor() {
        return (EReference) this.textfieldDescriptionStyleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalTextfieldDescriptionStyle() {
        return this.conditionalTextfieldDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getCheckboxDescriptionStyle() {
        return this.checkboxDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getCheckboxDescriptionStyle_Color() {
        return (EReference) this.checkboxDescriptionStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalCheckboxDescriptionStyle() {
        return this.conditionalCheckboxDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getSelectDescriptionStyle() {
        return this.selectDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getSelectDescriptionStyle_BackgroundColor() {
        return (EReference) this.selectDescriptionStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getSelectDescriptionStyle_ForegroundColor() {
        return (EReference) this.selectDescriptionStyleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalSelectDescriptionStyle() {
        return this.conditionalSelectDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getMultiSelectDescriptionStyle() {
        return this.multiSelectDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMultiSelectDescriptionStyle_BackgroundColor() {
        return (EReference) this.multiSelectDescriptionStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMultiSelectDescriptionStyle_ForegroundColor() {
        return (EReference) this.multiSelectDescriptionStyleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalMultiSelectDescriptionStyle() {
        return this.conditionalMultiSelectDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getTextareaDescriptionStyle() {
        return this.textareaDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTextareaDescriptionStyle_BackgroundColor() {
        return (EReference) this.textareaDescriptionStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTextareaDescriptionStyle_ForegroundColor() {
        return (EReference) this.textareaDescriptionStyleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalTextareaDescriptionStyle() {
        return this.conditionalTextareaDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getRadioDescriptionStyle() {
        return this.radioDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRadioDescriptionStyle_Color() {
        return (EReference) this.radioDescriptionStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalRadioDescriptionStyle() {
        return this.conditionalRadioDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getButtonDescriptionStyle() {
        return this.buttonDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getButtonDescriptionStyle_BackgroundColor() {
        return (EReference) this.buttonDescriptionStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getButtonDescriptionStyle_ForegroundColor() {
        return (EReference) this.buttonDescriptionStyleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalButtonDescriptionStyle() {
        return this.conditionalButtonDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getBarChartDescriptionStyle() {
        return this.barChartDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getBarChartDescriptionStyle_BarsColor() {
        return (EAttribute) this.barChartDescriptionStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalBarChartDescriptionStyle() {
        return this.conditionalBarChartDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getPieChartDescriptionStyle() {
        return this.pieChartDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPieChartDescriptionStyle_Colors() {
        return (EAttribute) this.pieChartDescriptionStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPieChartDescriptionStyle_StrokeWidth() {
        return (EAttribute) this.pieChartDescriptionStyleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPieChartDescriptionStyle_StrokeColor() {
        return (EReference) this.pieChartDescriptionStyleEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalPieChartDescriptionStyle() {
        return this.conditionalPieChartDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getLabelDescription() {
        return this.labelDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getLabelDescription_ValueExpression() {
        return (EAttribute) this.labelDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getLabelDescription_Style() {
        return (EReference) this.labelDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getLabelDescription_ConditionalStyles() {
        return (EReference) this.labelDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getLabelDescriptionStyle() {
        return this.labelDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getLabelDescriptionStyle_Color() {
        return (EReference) this.labelDescriptionStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalLabelDescriptionStyle() {
        return this.conditionalLabelDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getLinkDescription() {
        return this.linkDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getLinkDescription_ValueExpression() {
        return (EAttribute) this.linkDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getLinkDescription_Style() {
        return (EReference) this.linkDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getLinkDescription_ConditionalStyles() {
        return (EReference) this.linkDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getLinkDescriptionStyle() {
        return this.linkDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getLinkDescriptionStyle_Color() {
        return (EReference) this.linkDescriptionStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalLinkDescriptionStyle() {
        return this.conditionalLinkDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getListDescription() {
        return this.listDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getListDescription_ValueExpression() {
        return (EAttribute) this.listDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getListDescription_DisplayExpression() {
        return (EAttribute) this.listDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getListDescription_IsDeletableExpression() {
        return (EAttribute) this.listDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getListDescription_Body() {
        return (EReference) this.listDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getListDescription_Style() {
        return (EReference) this.listDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getListDescription_ConditionalStyles() {
        return (EReference) this.listDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getListDescriptionStyle() {
        return this.listDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getListDescriptionStyle_Color() {
        return (EReference) this.listDescriptionStyleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConditionalListDescriptionStyle() {
        return this.conditionalListDescriptionStyleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDiagramPalette() {
        return this.diagramPaletteEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDiagramPalette_DropTool() {
        return (EReference) this.diagramPaletteEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDiagramPalette_NodeTools() {
        return (EReference) this.diagramPaletteEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getNodePalette() {
        return this.nodePaletteEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodePalette_DeleteTool() {
        return (EReference) this.nodePaletteEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodePalette_LabelEditTool() {
        return (EReference) this.nodePaletteEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodePalette_NodeTools() {
        return (EReference) this.nodePaletteEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getNodePalette_EdgeTools() {
        return (EReference) this.nodePaletteEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getEdgePalette() {
        return this.edgePaletteEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgePalette_DeleteTool() {
        return (EReference) this.edgePaletteEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgePalette_CenterLabelEditTool() {
        return (EReference) this.edgePaletteEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgePalette_BeginLabelEditTool() {
        return (EReference) this.edgePaletteEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgePalette_EndLabelEditTool() {
        return (EReference) this.edgePaletteEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgePalette_NodeTools() {
        return (EReference) this.edgePaletteEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEdgePalette_EdgeReconnectionTools() {
        return (EReference) this.edgePaletteEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getSelectionDescription() {
        return this.selectionDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getSelectionDescription_SelectionCandidatesExpression() {
        return (EAttribute) this.selectionDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getSelectionDescription_SelectionMessage() {
        return (EAttribute) this.selectionDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDynamicDialogFolder() {
        return this.dynamicDialogFolderEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDynamicDialogFolder_SubFolders() {
        return (EReference) this.dynamicDialogFolderEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDynamicDialogFolder_Name() {
        return (EAttribute) this.dynamicDialogFolderEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDynamicDialogFolder_DynamicDialogs() {
        return (EReference) this.dynamicDialogFolderEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDynamicDialogDescription() {
        return this.dynamicDialogDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDynamicDialogDescription_Id() {
        return (EAttribute) this.dynamicDialogDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDynamicDialogDescription_TitleExpression() {
        return (EAttribute) this.dynamicDialogDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDynamicDialogDescription_DescriptionExpression() {
        return (EAttribute) this.dynamicDialogDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDynamicDialogDescription_WidgetDescriptions() {
        return (EReference) this.dynamicDialogDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDynamicDialogDescription_IsValidExpression() {
        return (EAttribute) this.dynamicDialogDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDynamicDialogDescription_Body() {
        return (EReference) this.dynamicDialogDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDynamicDialogDescription_ValidationMessages() {
        return (EReference) this.dynamicDialogDescriptionEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDWidgetDescription() {
        return this.dWidgetDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDWidgetDescription_Id() {
        return (EAttribute) this.dWidgetDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDWidgetDescription_LabelExpression() {
        return (EAttribute) this.dWidgetDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDWidgetDescription_InitialValueExpression() {
        return (EAttribute) this.dWidgetDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDWidgetDescription_Output() {
        return (EReference) this.dWidgetDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDWidgetDescription_Inputs() {
        return (EReference) this.dWidgetDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDSelectWidgetDescription() {
        return this.dSelectWidgetDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDSelectWidgetDescription_OptionsExpression() {
        return (EAttribute) this.dSelectWidgetDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDSelectWidgetDescription_OptionLabelExpression() {
        return (EAttribute) this.dSelectWidgetDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDSelectWidgetDescription_Style() {
        return (EReference) this.dSelectWidgetDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDTextFieldWidgetDescription() {
        return this.dTextFieldWidgetDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDTextFieldWidgetDescription_Style() {
        return (EReference) this.dTextFieldWidgetDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDValidationMessageDescription() {
        return this.dValidationMessageDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDValidationMessageDescription_PreCondition() {
        return (EAttribute) this.dValidationMessageDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDValidationMessageDescription_MessageExpression() {
        return (EAttribute) this.dValidationMessageDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDValidationMessageDescription_Severity() {
        return (EAttribute) this.dValidationMessageDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDValidationMessageDescription_BlocksApplyDialog() {
        return (EAttribute) this.dValidationMessageDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDWidgetOutputDescription() {
        return this.dWidgetOutputDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDWidgetOutputDescription_Name() {
        return (EAttribute) this.dWidgetOutputDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDWidgetObjectOutputDescription() {
        return this.dWidgetObjectOutputDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDWidgetStringOutputDescription() {
        return this.dWidgetStringOutputDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EEnum getLayoutDirection() {
        return this.layoutDirectionEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EEnum getArrowStyle() {
        return this.arrowStyleEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EEnum getLineStyle() {
        return this.lineStyleEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EEnum getNodeContainmentKind() {
        return this.nodeContainmentKindEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EEnum getSynchronizationPolicy() {
        return this.synchronizationPolicyEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EEnum getGroupDisplayMode() {
        return this.groupDisplayModeEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EEnum getFlexDirection() {
        return this.flexDirectionEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EEnum getDValidationMessageSeverity() {
        return this.dValidationMessageSeverityEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EDataType getIdentifier() {
        return this.identifierEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EDataType getInterpretedExpression() {
        return this.interpretedExpressionEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EDataType getDomainType() {
        return this.domainTypeEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EDataType getColor() {
        return this.colorEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EDataType getLength() {
        return this.lengthEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ViewFactory getViewFactory() {
        return (ViewFactory) this.getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package. This method is guarded to have no affect on any invocation but
     * its first. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public void createPackageContents() {
        if (this.isCreated)
            return;
        this.isCreated = true;

        // Create classes and their features
        this.viewEClass = this.createEClass(VIEW);
        this.createEReference(this.viewEClass, VIEW__DESCRIPTIONS);
        this.createEReference(this.viewEClass, VIEW__COLOR_PALETTES);
        this.createEReference(this.viewEClass, VIEW__DYNAMIC_DIALOG_FOLDER);

        this.colorPaletteEClass = this.createEClass(COLOR_PALETTE);
        this.createEAttribute(this.colorPaletteEClass, COLOR_PALETTE__NAME);
        this.createEReference(this.colorPaletteEClass, COLOR_PALETTE__COLORS);

        this.fixedColorEClass = this.createEClass(FIXED_COLOR);
        this.createEAttribute(this.fixedColorEClass, FIXED_COLOR__VALUE);

        this.userColorEClass = this.createEClass(USER_COLOR);
        this.createEAttribute(this.userColorEClass, USER_COLOR__NAME);

        this.representationDescriptionEClass = this.createEClass(REPRESENTATION_DESCRIPTION);
        this.createEAttribute(this.representationDescriptionEClass, REPRESENTATION_DESCRIPTION__NAME);
        this.createEAttribute(this.representationDescriptionEClass, REPRESENTATION_DESCRIPTION__DOMAIN_TYPE);
        this.createEAttribute(this.representationDescriptionEClass, REPRESENTATION_DESCRIPTION__PRECONDITION_EXPRESSION);
        this.createEAttribute(this.representationDescriptionEClass, REPRESENTATION_DESCRIPTION__TITLE_EXPRESSION);

        this.diagramDescriptionEClass = this.createEClass(DIAGRAM_DESCRIPTION);
        this.createEAttribute(this.diagramDescriptionEClass, DIAGRAM_DESCRIPTION__AUTO_LAYOUT);
        this.createEReference(this.diagramDescriptionEClass, DIAGRAM_DESCRIPTION__PALETTE);
        this.createEReference(this.diagramDescriptionEClass, DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS);
        this.createEReference(this.diagramDescriptionEClass, DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS);

        this.diagramElementDescriptionEClass = this.createEClass(DIAGRAM_ELEMENT_DESCRIPTION);
        this.createEAttribute(this.diagramElementDescriptionEClass, DIAGRAM_ELEMENT_DESCRIPTION__NAME);
        this.createEAttribute(this.diagramElementDescriptionEClass, DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE);
        this.createEAttribute(this.diagramElementDescriptionEClass, DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION);
        this.createEAttribute(this.diagramElementDescriptionEClass, DIAGRAM_ELEMENT_DESCRIPTION__PRECONDITION_EXPRESSION);
        this.createEAttribute(this.diagramElementDescriptionEClass, DIAGRAM_ELEMENT_DESCRIPTION__SYNCHRONIZATION_POLICY);
        this.createEAttribute(this.diagramElementDescriptionEClass, DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION);

        this.nodeDescriptionEClass = this.createEClass(NODE_DESCRIPTION);
        this.createEAttribute(this.nodeDescriptionEClass, NODE_DESCRIPTION__COLLAPSIBLE);
        this.createEReference(this.nodeDescriptionEClass, NODE_DESCRIPTION__PALETTE);
        this.createEReference(this.nodeDescriptionEClass, NODE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY);
        this.createEReference(this.nodeDescriptionEClass, NODE_DESCRIPTION__STYLE);
        this.createEReference(this.nodeDescriptionEClass, NODE_DESCRIPTION__CONDITIONAL_STYLES);
        this.createEReference(this.nodeDescriptionEClass, NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS);
        this.createEReference(this.nodeDescriptionEClass, NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS);
        this.createEReference(this.nodeDescriptionEClass, NODE_DESCRIPTION__REUSED_CHILD_NODE_DESCRIPTIONS);
        this.createEReference(this.nodeDescriptionEClass, NODE_DESCRIPTION__REUSED_BORDER_NODE_DESCRIPTIONS);
        this.createEAttribute(this.nodeDescriptionEClass, NODE_DESCRIPTION__USER_RESIZABLE);

        this.edgeDescriptionEClass = this.createEClass(EDGE_DESCRIPTION);
        this.createEAttribute(this.edgeDescriptionEClass, EDGE_DESCRIPTION__BEGIN_LABEL_EXPRESSION);
        this.createEAttribute(this.edgeDescriptionEClass, EDGE_DESCRIPTION__END_LABEL_EXPRESSION);
        this.createEAttribute(this.edgeDescriptionEClass, EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE);
        this.createEReference(this.edgeDescriptionEClass, EDGE_DESCRIPTION__PALETTE);
        this.createEReference(this.edgeDescriptionEClass, EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTIONS);
        this.createEReference(this.edgeDescriptionEClass, EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTIONS);
        this.createEAttribute(this.edgeDescriptionEClass, EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION);
        this.createEAttribute(this.edgeDescriptionEClass, EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION);
        this.createEReference(this.edgeDescriptionEClass, EDGE_DESCRIPTION__STYLE);
        this.createEReference(this.edgeDescriptionEClass, EDGE_DESCRIPTION__CONDITIONAL_STYLES);

        this.labelStyleEClass = this.createEClass(LABEL_STYLE);
        this.createEAttribute(this.labelStyleEClass, LABEL_STYLE__FONT_SIZE);
        this.createEAttribute(this.labelStyleEClass, LABEL_STYLE__ITALIC);
        this.createEAttribute(this.labelStyleEClass, LABEL_STYLE__BOLD);
        this.createEAttribute(this.labelStyleEClass, LABEL_STYLE__UNDERLINE);
        this.createEAttribute(this.labelStyleEClass, LABEL_STYLE__STRIKE_THROUGH);

        this.borderStyleEClass = this.createEClass(BORDER_STYLE);
        this.createEReference(this.borderStyleEClass, BORDER_STYLE__BORDER_COLOR);
        this.createEAttribute(this.borderStyleEClass, BORDER_STYLE__BORDER_RADIUS);
        this.createEAttribute(this.borderStyleEClass, BORDER_STYLE__BORDER_SIZE);
        this.createEAttribute(this.borderStyleEClass, BORDER_STYLE__BORDER_LINE_STYLE);

        this.styleEClass = this.createEClass(STYLE);
        this.createEReference(this.styleEClass, STYLE__COLOR);

        this.nodeStyleDescriptionEClass = this.createEClass(NODE_STYLE_DESCRIPTION);
        this.createEReference(this.nodeStyleDescriptionEClass, NODE_STYLE_DESCRIPTION__LABEL_COLOR);
        this.createEAttribute(this.nodeStyleDescriptionEClass, NODE_STYLE_DESCRIPTION__WIDTH_COMPUTATION_EXPRESSION);
        this.createEAttribute(this.nodeStyleDescriptionEClass, NODE_STYLE_DESCRIPTION__HEIGHT_COMPUTATION_EXPRESSION);
        this.createEAttribute(this.nodeStyleDescriptionEClass, NODE_STYLE_DESCRIPTION__SHOW_ICON);

        this.rectangularNodeStyleDescriptionEClass = this.createEClass(RECTANGULAR_NODE_STYLE_DESCRIPTION);
        this.createEAttribute(this.rectangularNodeStyleDescriptionEClass, RECTANGULAR_NODE_STYLE_DESCRIPTION__WITH_HEADER);

        this.imageNodeStyleDescriptionEClass = this.createEClass(IMAGE_NODE_STYLE_DESCRIPTION);
        this.createEAttribute(this.imageNodeStyleDescriptionEClass, IMAGE_NODE_STYLE_DESCRIPTION__SHAPE);

        this.iconLabelNodeStyleDescriptionEClass = this.createEClass(ICON_LABEL_NODE_STYLE_DESCRIPTION);

        this.layoutStrategyDescriptionEClass = this.createEClass(LAYOUT_STRATEGY_DESCRIPTION);

        this.freeFormLayoutStrategyDescriptionEClass = this.createEClass(FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION);

        this.listLayoutStrategyDescriptionEClass = this.createEClass(LIST_LAYOUT_STRATEGY_DESCRIPTION);

        this.edgeStyleEClass = this.createEClass(EDGE_STYLE);
        this.createEAttribute(this.edgeStyleEClass, EDGE_STYLE__LINE_STYLE);
        this.createEAttribute(this.edgeStyleEClass, EDGE_STYLE__SOURCE_ARROW_STYLE);
        this.createEAttribute(this.edgeStyleEClass, EDGE_STYLE__TARGET_ARROW_STYLE);
        this.createEAttribute(this.edgeStyleEClass, EDGE_STYLE__EDGE_WIDTH);
        this.createEAttribute(this.edgeStyleEClass, EDGE_STYLE__SHOW_ICON);

        this.toolEClass = this.createEClass(TOOL);
        this.createEAttribute(this.toolEClass, TOOL__NAME);
        this.createEReference(this.toolEClass, TOOL__BODY);

        this.labelEditToolEClass = this.createEClass(LABEL_EDIT_TOOL);
        this.createEAttribute(this.labelEditToolEClass, LABEL_EDIT_TOOL__INITIAL_DIRECT_EDIT_LABEL_EXPRESSION);

        this.deleteToolEClass = this.createEClass(DELETE_TOOL);

        this.nodeToolEClass = this.createEClass(NODE_TOOL);
        this.createEReference(this.nodeToolEClass, NODE_TOOL__SELECTION_DESCRIPTION);

        this.edgeToolEClass = this.createEClass(EDGE_TOOL);
        this.createEReference(this.edgeToolEClass, EDGE_TOOL__TARGET_ELEMENT_DESCRIPTIONS);

        this.edgeReconnectionToolEClass = this.createEClass(EDGE_RECONNECTION_TOOL);

        this.sourceEdgeEndReconnectionToolEClass = this.createEClass(SOURCE_EDGE_END_RECONNECTION_TOOL);

        this.targetEdgeEndReconnectionToolEClass = this.createEClass(TARGET_EDGE_END_RECONNECTION_TOOL);

        this.dropToolEClass = this.createEClass(DROP_TOOL);

        this.operationEClass = this.createEClass(OPERATION);
        this.createEReference(this.operationEClass, OPERATION__CHILDREN);

        this.changeContextEClass = this.createEClass(CHANGE_CONTEXT);
        this.createEAttribute(this.changeContextEClass, CHANGE_CONTEXT__EXPRESSION);

        this.createInstanceEClass = this.createEClass(CREATE_INSTANCE);
        this.createEAttribute(this.createInstanceEClass, CREATE_INSTANCE__TYPE_NAME);
        this.createEAttribute(this.createInstanceEClass, CREATE_INSTANCE__REFERENCE_NAME);
        this.createEAttribute(this.createInstanceEClass, CREATE_INSTANCE__VARIABLE_NAME);

        this.setValueEClass = this.createEClass(SET_VALUE);
        this.createEAttribute(this.setValueEClass, SET_VALUE__FEATURE_NAME);
        this.createEAttribute(this.setValueEClass, SET_VALUE__VALUE_EXPRESSION);

        this.unsetValueEClass = this.createEClass(UNSET_VALUE);
        this.createEAttribute(this.unsetValueEClass, UNSET_VALUE__FEATURE_NAME);
        this.createEAttribute(this.unsetValueEClass, UNSET_VALUE__ELEMENT_EXPRESSION);

        this.deleteElementEClass = this.createEClass(DELETE_ELEMENT);

        this.createViewEClass = this.createEClass(CREATE_VIEW);
        this.createEAttribute(this.createViewEClass, CREATE_VIEW__PARENT_VIEW_EXPRESSION);
        this.createEReference(this.createViewEClass, CREATE_VIEW__ELEMENT_DESCRIPTION);
        this.createEAttribute(this.createViewEClass, CREATE_VIEW__SEMANTIC_ELEMENT_EXPRESSION);
        this.createEAttribute(this.createViewEClass, CREATE_VIEW__VARIABLE_NAME);
        this.createEAttribute(this.createViewEClass, CREATE_VIEW__CONTAINMENT_KIND);

        this.deleteViewEClass = this.createEClass(DELETE_VIEW);
        this.createEAttribute(this.deleteViewEClass, DELETE_VIEW__VIEW_EXPRESSION);

        this.conditionalEClass = this.createEClass(CONDITIONAL);
        this.createEAttribute(this.conditionalEClass, CONDITIONAL__CONDITION);

        this.conditionalNodeStyleEClass = this.createEClass(CONDITIONAL_NODE_STYLE);
        this.createEReference(this.conditionalNodeStyleEClass, CONDITIONAL_NODE_STYLE__STYLE);

        this.conditionalEdgeStyleEClass = this.createEClass(CONDITIONAL_EDGE_STYLE);

        this.formDescriptionEClass = this.createEClass(FORM_DESCRIPTION);
        this.createEReference(this.formDescriptionEClass, FORM_DESCRIPTION__PAGES);

        this.pageDescriptionEClass = this.createEClass(PAGE_DESCRIPTION);
        this.createEAttribute(this.pageDescriptionEClass, PAGE_DESCRIPTION__NAME);
        this.createEAttribute(this.pageDescriptionEClass, PAGE_DESCRIPTION__LABEL_EXPRESSION);
        this.createEAttribute(this.pageDescriptionEClass, PAGE_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION);
        this.createEAttribute(this.pageDescriptionEClass, PAGE_DESCRIPTION__PRECONDITION_EXPRESSION);
        this.createEReference(this.pageDescriptionEClass, PAGE_DESCRIPTION__GROUPS);
        this.createEReference(this.pageDescriptionEClass, PAGE_DESCRIPTION__TOOLBAR_ACTIONS);

        this.groupDescriptionEClass = this.createEClass(GROUP_DESCRIPTION);
        this.createEAttribute(this.groupDescriptionEClass, GROUP_DESCRIPTION__NAME);
        this.createEAttribute(this.groupDescriptionEClass, GROUP_DESCRIPTION__LABEL_EXPRESSION);
        this.createEAttribute(this.groupDescriptionEClass, GROUP_DESCRIPTION__DISPLAY_MODE);
        this.createEAttribute(this.groupDescriptionEClass, GROUP_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION);
        this.createEReference(this.groupDescriptionEClass, GROUP_DESCRIPTION__TOOLBAR_ACTIONS);
        this.createEReference(this.groupDescriptionEClass, GROUP_DESCRIPTION__WIDGETS);

        this.widgetDescriptionEClass = this.createEClass(WIDGET_DESCRIPTION);
        this.createEAttribute(this.widgetDescriptionEClass, WIDGET_DESCRIPTION__NAME);
        this.createEAttribute(this.widgetDescriptionEClass, WIDGET_DESCRIPTION__LABEL_EXPRESSION);

        this.textfieldDescriptionEClass = this.createEClass(TEXTFIELD_DESCRIPTION);
        this.createEAttribute(this.textfieldDescriptionEClass, TEXTFIELD_DESCRIPTION__VALUE_EXPRESSION);
        this.createEReference(this.textfieldDescriptionEClass, TEXTFIELD_DESCRIPTION__BODY);
        this.createEReference(this.textfieldDescriptionEClass, TEXTFIELD_DESCRIPTION__STYLE);
        this.createEReference(this.textfieldDescriptionEClass, TEXTFIELD_DESCRIPTION__CONDITIONAL_STYLES);

        this.checkboxDescriptionEClass = this.createEClass(CHECKBOX_DESCRIPTION);
        this.createEAttribute(this.checkboxDescriptionEClass, CHECKBOX_DESCRIPTION__VALUE_EXPRESSION);
        this.createEReference(this.checkboxDescriptionEClass, CHECKBOX_DESCRIPTION__BODY);
        this.createEReference(this.checkboxDescriptionEClass, CHECKBOX_DESCRIPTION__STYLE);
        this.createEReference(this.checkboxDescriptionEClass, CHECKBOX_DESCRIPTION__CONDITIONAL_STYLES);

        this.selectDescriptionEClass = this.createEClass(SELECT_DESCRIPTION);
        this.createEAttribute(this.selectDescriptionEClass, SELECT_DESCRIPTION__VALUE_EXPRESSION);
        this.createEAttribute(this.selectDescriptionEClass, SELECT_DESCRIPTION__CANDIDATES_EXPRESSION);
        this.createEAttribute(this.selectDescriptionEClass, SELECT_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION);
        this.createEReference(this.selectDescriptionEClass, SELECT_DESCRIPTION__BODY);
        this.createEReference(this.selectDescriptionEClass, SELECT_DESCRIPTION__STYLE);
        this.createEReference(this.selectDescriptionEClass, SELECT_DESCRIPTION__CONDITIONAL_STYLES);

        this.multiSelectDescriptionEClass = this.createEClass(MULTI_SELECT_DESCRIPTION);
        this.createEAttribute(this.multiSelectDescriptionEClass, MULTI_SELECT_DESCRIPTION__VALUE_EXPRESSION);
        this.createEAttribute(this.multiSelectDescriptionEClass, MULTI_SELECT_DESCRIPTION__CANDIDATES_EXPRESSION);
        this.createEAttribute(this.multiSelectDescriptionEClass, MULTI_SELECT_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION);
        this.createEReference(this.multiSelectDescriptionEClass, MULTI_SELECT_DESCRIPTION__BODY);
        this.createEReference(this.multiSelectDescriptionEClass, MULTI_SELECT_DESCRIPTION__STYLE);
        this.createEReference(this.multiSelectDescriptionEClass, MULTI_SELECT_DESCRIPTION__CONDITIONAL_STYLES);

        this.textAreaDescriptionEClass = this.createEClass(TEXT_AREA_DESCRIPTION);
        this.createEAttribute(this.textAreaDescriptionEClass, TEXT_AREA_DESCRIPTION__VALUE_EXPRESSION);
        this.createEReference(this.textAreaDescriptionEClass, TEXT_AREA_DESCRIPTION__BODY);
        this.createEReference(this.textAreaDescriptionEClass, TEXT_AREA_DESCRIPTION__STYLE);
        this.createEReference(this.textAreaDescriptionEClass, TEXT_AREA_DESCRIPTION__CONDITIONAL_STYLES);

        this.richTextDescriptionEClass = this.createEClass(RICH_TEXT_DESCRIPTION);
        this.createEAttribute(this.richTextDescriptionEClass, RICH_TEXT_DESCRIPTION__VALUE_EXPRESSION);
        this.createEReference(this.richTextDescriptionEClass, RICH_TEXT_DESCRIPTION__BODY);

        this.radioDescriptionEClass = this.createEClass(RADIO_DESCRIPTION);
        this.createEAttribute(this.radioDescriptionEClass, RADIO_DESCRIPTION__VALUE_EXPRESSION);
        this.createEAttribute(this.radioDescriptionEClass, RADIO_DESCRIPTION__CANDIDATES_EXPRESSION);
        this.createEAttribute(this.radioDescriptionEClass, RADIO_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION);
        this.createEReference(this.radioDescriptionEClass, RADIO_DESCRIPTION__BODY);
        this.createEReference(this.radioDescriptionEClass, RADIO_DESCRIPTION__STYLE);
        this.createEReference(this.radioDescriptionEClass, RADIO_DESCRIPTION__CONDITIONAL_STYLES);

        this.barChartDescriptionEClass = this.createEClass(BAR_CHART_DESCRIPTION);
        this.createEAttribute(this.barChartDescriptionEClass, BAR_CHART_DESCRIPTION__VALUES_EXPRESSION);
        this.createEAttribute(this.barChartDescriptionEClass, BAR_CHART_DESCRIPTION__KEYS_EXPRESSION);
        this.createEAttribute(this.barChartDescriptionEClass, BAR_CHART_DESCRIPTION__YAXIS_LABEL_EXPRESSION);
        this.createEReference(this.barChartDescriptionEClass, BAR_CHART_DESCRIPTION__STYLE);
        this.createEReference(this.barChartDescriptionEClass, BAR_CHART_DESCRIPTION__CONDITIONAL_STYLES);
        this.createEAttribute(this.barChartDescriptionEClass, BAR_CHART_DESCRIPTION__WIDTH);
        this.createEAttribute(this.barChartDescriptionEClass, BAR_CHART_DESCRIPTION__HEIGHT);

        this.pieChartDescriptionEClass = this.createEClass(PIE_CHART_DESCRIPTION);
        this.createEAttribute(this.pieChartDescriptionEClass, PIE_CHART_DESCRIPTION__VALUES_EXPRESSION);
        this.createEAttribute(this.pieChartDescriptionEClass, PIE_CHART_DESCRIPTION__KEYS_EXPRESSION);
        this.createEReference(this.pieChartDescriptionEClass, PIE_CHART_DESCRIPTION__STYLE);
        this.createEReference(this.pieChartDescriptionEClass, PIE_CHART_DESCRIPTION__CONDITIONAL_STYLES);

        this.flexboxContainerDescriptionEClass = this.createEClass(FLEXBOX_CONTAINER_DESCRIPTION);
        this.createEReference(this.flexboxContainerDescriptionEClass, FLEXBOX_CONTAINER_DESCRIPTION__CHILDREN);
        this.createEAttribute(this.flexboxContainerDescriptionEClass, FLEXBOX_CONTAINER_DESCRIPTION__FLEX_DIRECTION);

        this.buttonDescriptionEClass = this.createEClass(BUTTON_DESCRIPTION);
        this.createEAttribute(this.buttonDescriptionEClass, BUTTON_DESCRIPTION__BUTTON_LABEL_EXPRESSION);
        this.createEReference(this.buttonDescriptionEClass, BUTTON_DESCRIPTION__BODY);
        this.createEAttribute(this.buttonDescriptionEClass, BUTTON_DESCRIPTION__IMAGE_EXPRESSION);
        this.createEReference(this.buttonDescriptionEClass, BUTTON_DESCRIPTION__STYLE);
        this.createEReference(this.buttonDescriptionEClass, BUTTON_DESCRIPTION__CONDITIONAL_STYLES);

        this.imageDescriptionEClass = this.createEClass(IMAGE_DESCRIPTION);
        this.createEAttribute(this.imageDescriptionEClass, IMAGE_DESCRIPTION__URL_EXPRESSION);
        this.createEAttribute(this.imageDescriptionEClass, IMAGE_DESCRIPTION__MAX_WIDTH_EXPRESSION);

        this.widgetDescriptionStyleEClass = this.createEClass(WIDGET_DESCRIPTION_STYLE);

        this.textfieldDescriptionStyleEClass = this.createEClass(TEXTFIELD_DESCRIPTION_STYLE);
        this.createEReference(this.textfieldDescriptionStyleEClass, TEXTFIELD_DESCRIPTION_STYLE__BACKGROUND_COLOR);
        this.createEReference(this.textfieldDescriptionStyleEClass, TEXTFIELD_DESCRIPTION_STYLE__FOREGROUND_COLOR);

        this.conditionalTextfieldDescriptionStyleEClass = this.createEClass(CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE);

        this.checkboxDescriptionStyleEClass = this.createEClass(CHECKBOX_DESCRIPTION_STYLE);
        this.createEReference(this.checkboxDescriptionStyleEClass, CHECKBOX_DESCRIPTION_STYLE__COLOR);

        this.conditionalCheckboxDescriptionStyleEClass = this.createEClass(CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE);

        this.selectDescriptionStyleEClass = this.createEClass(SELECT_DESCRIPTION_STYLE);
        this.createEReference(this.selectDescriptionStyleEClass, SELECT_DESCRIPTION_STYLE__BACKGROUND_COLOR);
        this.createEReference(this.selectDescriptionStyleEClass, SELECT_DESCRIPTION_STYLE__FOREGROUND_COLOR);

        this.conditionalSelectDescriptionStyleEClass = this.createEClass(CONDITIONAL_SELECT_DESCRIPTION_STYLE);

        this.multiSelectDescriptionStyleEClass = this.createEClass(MULTI_SELECT_DESCRIPTION_STYLE);
        this.createEReference(this.multiSelectDescriptionStyleEClass, MULTI_SELECT_DESCRIPTION_STYLE__BACKGROUND_COLOR);
        this.createEReference(this.multiSelectDescriptionStyleEClass, MULTI_SELECT_DESCRIPTION_STYLE__FOREGROUND_COLOR);

        this.conditionalMultiSelectDescriptionStyleEClass = this.createEClass(CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE);

        this.textareaDescriptionStyleEClass = this.createEClass(TEXTAREA_DESCRIPTION_STYLE);
        this.createEReference(this.textareaDescriptionStyleEClass, TEXTAREA_DESCRIPTION_STYLE__BACKGROUND_COLOR);
        this.createEReference(this.textareaDescriptionStyleEClass, TEXTAREA_DESCRIPTION_STYLE__FOREGROUND_COLOR);

        this.conditionalTextareaDescriptionStyleEClass = this.createEClass(CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE);

        this.radioDescriptionStyleEClass = this.createEClass(RADIO_DESCRIPTION_STYLE);
        this.createEReference(this.radioDescriptionStyleEClass, RADIO_DESCRIPTION_STYLE__COLOR);

        this.conditionalRadioDescriptionStyleEClass = this.createEClass(CONDITIONAL_RADIO_DESCRIPTION_STYLE);

        this.buttonDescriptionStyleEClass = this.createEClass(BUTTON_DESCRIPTION_STYLE);
        this.createEReference(this.buttonDescriptionStyleEClass, BUTTON_DESCRIPTION_STYLE__BACKGROUND_COLOR);
        this.createEReference(this.buttonDescriptionStyleEClass, BUTTON_DESCRIPTION_STYLE__FOREGROUND_COLOR);

        this.conditionalButtonDescriptionStyleEClass = this.createEClass(CONDITIONAL_BUTTON_DESCRIPTION_STYLE);

        this.barChartDescriptionStyleEClass = this.createEClass(BAR_CHART_DESCRIPTION_STYLE);
        this.createEAttribute(this.barChartDescriptionStyleEClass, BAR_CHART_DESCRIPTION_STYLE__BARS_COLOR);

        this.conditionalBarChartDescriptionStyleEClass = this.createEClass(CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE);

        this.pieChartDescriptionStyleEClass = this.createEClass(PIE_CHART_DESCRIPTION_STYLE);
        this.createEAttribute(this.pieChartDescriptionStyleEClass, PIE_CHART_DESCRIPTION_STYLE__COLORS);
        this.createEAttribute(this.pieChartDescriptionStyleEClass, PIE_CHART_DESCRIPTION_STYLE__STROKE_WIDTH);
        this.createEReference(this.pieChartDescriptionStyleEClass, PIE_CHART_DESCRIPTION_STYLE__STROKE_COLOR);

        this.conditionalPieChartDescriptionStyleEClass = this.createEClass(CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE);

        this.labelDescriptionEClass = this.createEClass(LABEL_DESCRIPTION);
        this.createEAttribute(this.labelDescriptionEClass, LABEL_DESCRIPTION__VALUE_EXPRESSION);
        this.createEReference(this.labelDescriptionEClass, LABEL_DESCRIPTION__STYLE);
        this.createEReference(this.labelDescriptionEClass, LABEL_DESCRIPTION__CONDITIONAL_STYLES);

        this.labelDescriptionStyleEClass = this.createEClass(LABEL_DESCRIPTION_STYLE);
        this.createEReference(this.labelDescriptionStyleEClass, LABEL_DESCRIPTION_STYLE__COLOR);

        this.conditionalLabelDescriptionStyleEClass = this.createEClass(CONDITIONAL_LABEL_DESCRIPTION_STYLE);

        this.linkDescriptionEClass = this.createEClass(LINK_DESCRIPTION);
        this.createEAttribute(this.linkDescriptionEClass, LINK_DESCRIPTION__VALUE_EXPRESSION);
        this.createEReference(this.linkDescriptionEClass, LINK_DESCRIPTION__STYLE);
        this.createEReference(this.linkDescriptionEClass, LINK_DESCRIPTION__CONDITIONAL_STYLES);

        this.linkDescriptionStyleEClass = this.createEClass(LINK_DESCRIPTION_STYLE);
        this.createEReference(this.linkDescriptionStyleEClass, LINK_DESCRIPTION_STYLE__COLOR);

        this.conditionalLinkDescriptionStyleEClass = this.createEClass(CONDITIONAL_LINK_DESCRIPTION_STYLE);

        this.listDescriptionEClass = this.createEClass(LIST_DESCRIPTION);
        this.createEAttribute(this.listDescriptionEClass, LIST_DESCRIPTION__VALUE_EXPRESSION);
        this.createEAttribute(this.listDescriptionEClass, LIST_DESCRIPTION__DISPLAY_EXPRESSION);
        this.createEAttribute(this.listDescriptionEClass, LIST_DESCRIPTION__IS_DELETABLE_EXPRESSION);
        this.createEReference(this.listDescriptionEClass, LIST_DESCRIPTION__BODY);
        this.createEReference(this.listDescriptionEClass, LIST_DESCRIPTION__STYLE);
        this.createEReference(this.listDescriptionEClass, LIST_DESCRIPTION__CONDITIONAL_STYLES);

        this.listDescriptionStyleEClass = this.createEClass(LIST_DESCRIPTION_STYLE);
        this.createEReference(this.listDescriptionStyleEClass, LIST_DESCRIPTION_STYLE__COLOR);

        this.conditionalListDescriptionStyleEClass = this.createEClass(CONDITIONAL_LIST_DESCRIPTION_STYLE);

        this.diagramPaletteEClass = this.createEClass(DIAGRAM_PALETTE);
        this.createEReference(this.diagramPaletteEClass, DIAGRAM_PALETTE__DROP_TOOL);
        this.createEReference(this.diagramPaletteEClass, DIAGRAM_PALETTE__NODE_TOOLS);

        this.nodePaletteEClass = this.createEClass(NODE_PALETTE);
        this.createEReference(this.nodePaletteEClass, NODE_PALETTE__DELETE_TOOL);
        this.createEReference(this.nodePaletteEClass, NODE_PALETTE__LABEL_EDIT_TOOL);
        this.createEReference(this.nodePaletteEClass, NODE_PALETTE__NODE_TOOLS);
        this.createEReference(this.nodePaletteEClass, NODE_PALETTE__EDGE_TOOLS);

        this.edgePaletteEClass = this.createEClass(EDGE_PALETTE);
        this.createEReference(this.edgePaletteEClass, EDGE_PALETTE__DELETE_TOOL);
        this.createEReference(this.edgePaletteEClass, EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL);
        this.createEReference(this.edgePaletteEClass, EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL);
        this.createEReference(this.edgePaletteEClass, EDGE_PALETTE__END_LABEL_EDIT_TOOL);
        this.createEReference(this.edgePaletteEClass, EDGE_PALETTE__NODE_TOOLS);
        this.createEReference(this.edgePaletteEClass, EDGE_PALETTE__EDGE_RECONNECTION_TOOLS);

        this.selectionDescriptionEClass = this.createEClass(SELECTION_DESCRIPTION);
        this.createEAttribute(this.selectionDescriptionEClass, SELECTION_DESCRIPTION__SELECTION_CANDIDATES_EXPRESSION);
        this.createEAttribute(this.selectionDescriptionEClass, SELECTION_DESCRIPTION__SELECTION_MESSAGE);

        this.dynamicDialogFolderEClass = this.createEClass(DYNAMIC_DIALOG_FOLDER);
        this.createEReference(this.dynamicDialogFolderEClass, DYNAMIC_DIALOG_FOLDER__SUB_FOLDERS);
        this.createEAttribute(this.dynamicDialogFolderEClass, DYNAMIC_DIALOG_FOLDER__NAME);
        this.createEReference(this.dynamicDialogFolderEClass, DYNAMIC_DIALOG_FOLDER__DYNAMIC_DIALOGS);

        this.dynamicDialogDescriptionEClass = this.createEClass(DYNAMIC_DIALOG_DESCRIPTION);
        this.createEAttribute(this.dynamicDialogDescriptionEClass, DYNAMIC_DIALOG_DESCRIPTION__ID);
        this.createEAttribute(this.dynamicDialogDescriptionEClass, DYNAMIC_DIALOG_DESCRIPTION__TITLE_EXPRESSION);
        this.createEAttribute(this.dynamicDialogDescriptionEClass, DYNAMIC_DIALOG_DESCRIPTION__DESCRIPTION_EXPRESSION);
        this.createEReference(this.dynamicDialogDescriptionEClass, DYNAMIC_DIALOG_DESCRIPTION__WIDGET_DESCRIPTIONS);
        this.createEAttribute(this.dynamicDialogDescriptionEClass, DYNAMIC_DIALOG_DESCRIPTION__IS_VALID_EXPRESSION);
        this.createEReference(this.dynamicDialogDescriptionEClass, DYNAMIC_DIALOG_DESCRIPTION__BODY);
        this.createEReference(this.dynamicDialogDescriptionEClass, DYNAMIC_DIALOG_DESCRIPTION__VALIDATION_MESSAGES);

        this.dWidgetDescriptionEClass = this.createEClass(DWIDGET_DESCRIPTION);
        this.createEAttribute(this.dWidgetDescriptionEClass, DWIDGET_DESCRIPTION__ID);
        this.createEAttribute(this.dWidgetDescriptionEClass, DWIDGET_DESCRIPTION__LABEL_EXPRESSION);
        this.createEAttribute(this.dWidgetDescriptionEClass, DWIDGET_DESCRIPTION__INITIAL_VALUE_EXPRESSION);
        this.createEReference(this.dWidgetDescriptionEClass, DWIDGET_DESCRIPTION__OUTPUT);
        this.createEReference(this.dWidgetDescriptionEClass, DWIDGET_DESCRIPTION__INPUTS);

        this.dWidgetOutputDescriptionEClass = this.createEClass(DWIDGET_OUTPUT_DESCRIPTION);
        this.createEAttribute(this.dWidgetOutputDescriptionEClass, DWIDGET_OUTPUT_DESCRIPTION__NAME);

        this.dWidgetObjectOutputDescriptionEClass = this.createEClass(DWIDGET_OBJECT_OUTPUT_DESCRIPTION);

        this.dWidgetStringOutputDescriptionEClass = this.createEClass(DWIDGET_STRING_OUTPUT_DESCRIPTION);

        this.dSelectWidgetDescriptionEClass = this.createEClass(DSELECT_WIDGET_DESCRIPTION);
        this.createEAttribute(this.dSelectWidgetDescriptionEClass, DSELECT_WIDGET_DESCRIPTION__OPTIONS_EXPRESSION);
        this.createEAttribute(this.dSelectWidgetDescriptionEClass, DSELECT_WIDGET_DESCRIPTION__OPTION_LABEL_EXPRESSION);
        this.createEReference(this.dSelectWidgetDescriptionEClass, DSELECT_WIDGET_DESCRIPTION__STYLE);

        this.dTextFieldWidgetDescriptionEClass = this.createEClass(DTEXT_FIELD_WIDGET_DESCRIPTION);
        this.createEReference(this.dTextFieldWidgetDescriptionEClass, DTEXT_FIELD_WIDGET_DESCRIPTION__STYLE);

        this.dValidationMessageDescriptionEClass = this.createEClass(DVALIDATION_MESSAGE_DESCRIPTION);
        this.createEAttribute(this.dValidationMessageDescriptionEClass, DVALIDATION_MESSAGE_DESCRIPTION__PRE_CONDITION);
        this.createEAttribute(this.dValidationMessageDescriptionEClass, DVALIDATION_MESSAGE_DESCRIPTION__MESSAGE_EXPRESSION);
        this.createEAttribute(this.dValidationMessageDescriptionEClass, DVALIDATION_MESSAGE_DESCRIPTION__SEVERITY);
        this.createEAttribute(this.dValidationMessageDescriptionEClass, DVALIDATION_MESSAGE_DESCRIPTION__BLOCKS_APPLY_DIALOG);

        // Create enums
        this.layoutDirectionEEnum = this.createEEnum(LAYOUT_DIRECTION);
        this.arrowStyleEEnum = this.createEEnum(ARROW_STYLE);
        this.lineStyleEEnum = this.createEEnum(LINE_STYLE);
        this.nodeContainmentKindEEnum = this.createEEnum(NODE_CONTAINMENT_KIND);
        this.synchronizationPolicyEEnum = this.createEEnum(SYNCHRONIZATION_POLICY);
        this.groupDisplayModeEEnum = this.createEEnum(GROUP_DISPLAY_MODE);
        this.flexDirectionEEnum = this.createEEnum(FLEX_DIRECTION);
        this.dValidationMessageSeverityEEnum = this.createEEnum(DVALIDATION_MESSAGE_SEVERITY);

        // Create data types
        this.identifierEDataType = this.createEDataType(IDENTIFIER);
        this.interpretedExpressionEDataType = this.createEDataType(INTERPRETED_EXPRESSION);
        this.domainTypeEDataType = this.createEDataType(DOMAIN_TYPE);
        this.colorEDataType = this.createEDataType(COLOR);
        this.lengthEDataType = this.createEDataType(LENGTH);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model. This method is guarded to have no affect on any
     * invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public void initializePackageContents() {
        if (this.isInitialized)
            return;
        this.isInitialized = true;

        // Initialize package
        this.setName(eNAME);
        this.setNsPrefix(eNS_PREFIX);
        this.setNsURI(eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        this.fixedColorEClass.getESuperTypes().add(this.getUserColor());
        this.diagramDescriptionEClass.getESuperTypes().add(this.getRepresentationDescription());
        this.nodeDescriptionEClass.getESuperTypes().add(this.getDiagramElementDescription());
        this.edgeDescriptionEClass.getESuperTypes().add(this.getDiagramElementDescription());
        this.nodeStyleDescriptionEClass.getESuperTypes().add(this.getStyle());
        this.nodeStyleDescriptionEClass.getESuperTypes().add(this.getLabelStyle());
        this.nodeStyleDescriptionEClass.getESuperTypes().add(this.getBorderStyle());
        this.rectangularNodeStyleDescriptionEClass.getESuperTypes().add(this.getNodeStyleDescription());
        this.imageNodeStyleDescriptionEClass.getESuperTypes().add(this.getNodeStyleDescription());
        this.iconLabelNodeStyleDescriptionEClass.getESuperTypes().add(this.getNodeStyleDescription());
        this.freeFormLayoutStrategyDescriptionEClass.getESuperTypes().add(this.getLayoutStrategyDescription());
        this.listLayoutStrategyDescriptionEClass.getESuperTypes().add(this.getLayoutStrategyDescription());
        this.edgeStyleEClass.getESuperTypes().add(this.getStyle());
        this.edgeStyleEClass.getESuperTypes().add(this.getLabelStyle());
        this.labelEditToolEClass.getESuperTypes().add(this.getTool());
        this.deleteToolEClass.getESuperTypes().add(this.getTool());
        this.nodeToolEClass.getESuperTypes().add(this.getTool());
        this.edgeToolEClass.getESuperTypes().add(this.getTool());
        this.edgeReconnectionToolEClass.getESuperTypes().add(this.getTool());
        this.sourceEdgeEndReconnectionToolEClass.getESuperTypes().add(this.getEdgeReconnectionTool());
        this.targetEdgeEndReconnectionToolEClass.getESuperTypes().add(this.getEdgeReconnectionTool());
        this.dropToolEClass.getESuperTypes().add(this.getTool());
        this.changeContextEClass.getESuperTypes().add(this.getOperation());
        this.createInstanceEClass.getESuperTypes().add(this.getOperation());
        this.setValueEClass.getESuperTypes().add(this.getOperation());
        this.unsetValueEClass.getESuperTypes().add(this.getOperation());
        this.deleteElementEClass.getESuperTypes().add(this.getOperation());
        this.createViewEClass.getESuperTypes().add(this.getOperation());
        this.deleteViewEClass.getESuperTypes().add(this.getOperation());
        this.conditionalNodeStyleEClass.getESuperTypes().add(this.getConditional());
        this.conditionalEdgeStyleEClass.getESuperTypes().add(this.getConditional());
        this.conditionalEdgeStyleEClass.getESuperTypes().add(this.getEdgeStyle());
        this.formDescriptionEClass.getESuperTypes().add(this.getRepresentationDescription());
        this.textfieldDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.checkboxDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.selectDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.multiSelectDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.textAreaDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.richTextDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.radioDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.barChartDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.pieChartDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.flexboxContainerDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.buttonDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.imageDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.textfieldDescriptionStyleEClass.getESuperTypes().add(this.getWidgetDescriptionStyle());
        this.textfieldDescriptionStyleEClass.getESuperTypes().add(this.getLabelStyle());
        this.conditionalTextfieldDescriptionStyleEClass.getESuperTypes().add(this.getConditional());
        this.conditionalTextfieldDescriptionStyleEClass.getESuperTypes().add(this.getTextfieldDescriptionStyle());
        this.checkboxDescriptionStyleEClass.getESuperTypes().add(this.getWidgetDescriptionStyle());
        this.conditionalCheckboxDescriptionStyleEClass.getESuperTypes().add(this.getConditional());
        this.conditionalCheckboxDescriptionStyleEClass.getESuperTypes().add(this.getCheckboxDescriptionStyle());
        this.selectDescriptionStyleEClass.getESuperTypes().add(this.getWidgetDescriptionStyle());
        this.selectDescriptionStyleEClass.getESuperTypes().add(this.getLabelStyle());
        this.conditionalSelectDescriptionStyleEClass.getESuperTypes().add(this.getConditional());
        this.conditionalSelectDescriptionStyleEClass.getESuperTypes().add(this.getSelectDescriptionStyle());
        this.multiSelectDescriptionStyleEClass.getESuperTypes().add(this.getWidgetDescriptionStyle());
        this.multiSelectDescriptionStyleEClass.getESuperTypes().add(this.getLabelStyle());
        this.conditionalMultiSelectDescriptionStyleEClass.getESuperTypes().add(this.getConditional());
        this.conditionalMultiSelectDescriptionStyleEClass.getESuperTypes().add(this.getMultiSelectDescriptionStyle());
        this.textareaDescriptionStyleEClass.getESuperTypes().add(this.getWidgetDescriptionStyle());
        this.textareaDescriptionStyleEClass.getESuperTypes().add(this.getLabelStyle());
        this.conditionalTextareaDescriptionStyleEClass.getESuperTypes().add(this.getConditional());
        this.conditionalTextareaDescriptionStyleEClass.getESuperTypes().add(this.getTextareaDescriptionStyle());
        this.radioDescriptionStyleEClass.getESuperTypes().add(this.getWidgetDescriptionStyle());
        this.radioDescriptionStyleEClass.getESuperTypes().add(this.getLabelStyle());
        this.conditionalRadioDescriptionStyleEClass.getESuperTypes().add(this.getConditional());
        this.conditionalRadioDescriptionStyleEClass.getESuperTypes().add(this.getRadioDescriptionStyle());
        this.buttonDescriptionStyleEClass.getESuperTypes().add(this.getWidgetDescriptionStyle());
        this.buttonDescriptionStyleEClass.getESuperTypes().add(this.getLabelStyle());
        this.conditionalButtonDescriptionStyleEClass.getESuperTypes().add(this.getConditional());
        this.conditionalButtonDescriptionStyleEClass.getESuperTypes().add(this.getButtonDescriptionStyle());
        this.barChartDescriptionStyleEClass.getESuperTypes().add(this.getWidgetDescriptionStyle());
        this.barChartDescriptionStyleEClass.getESuperTypes().add(this.getLabelStyle());
        this.conditionalBarChartDescriptionStyleEClass.getESuperTypes().add(this.getConditional());
        this.conditionalBarChartDescriptionStyleEClass.getESuperTypes().add(this.getBarChartDescriptionStyle());
        this.pieChartDescriptionStyleEClass.getESuperTypes().add(this.getWidgetDescriptionStyle());
        this.pieChartDescriptionStyleEClass.getESuperTypes().add(this.getLabelStyle());
        this.conditionalPieChartDescriptionStyleEClass.getESuperTypes().add(this.getConditional());
        this.conditionalPieChartDescriptionStyleEClass.getESuperTypes().add(this.getPieChartDescriptionStyle());
        this.labelDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.labelDescriptionStyleEClass.getESuperTypes().add(this.getWidgetDescriptionStyle());
        this.labelDescriptionStyleEClass.getESuperTypes().add(this.getLabelStyle());
        this.conditionalLabelDescriptionStyleEClass.getESuperTypes().add(this.getConditional());
        this.conditionalLabelDescriptionStyleEClass.getESuperTypes().add(this.getLabelDescriptionStyle());
        this.linkDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.linkDescriptionStyleEClass.getESuperTypes().add(this.getWidgetDescriptionStyle());
        this.linkDescriptionStyleEClass.getESuperTypes().add(this.getLabelStyle());
        this.conditionalLinkDescriptionStyleEClass.getESuperTypes().add(this.getConditional());
        this.conditionalLinkDescriptionStyleEClass.getESuperTypes().add(this.getLinkDescriptionStyle());
        this.listDescriptionEClass.getESuperTypes().add(this.getWidgetDescription());
        this.listDescriptionStyleEClass.getESuperTypes().add(this.getWidgetDescriptionStyle());
        this.listDescriptionStyleEClass.getESuperTypes().add(this.getLabelStyle());
        this.conditionalListDescriptionStyleEClass.getESuperTypes().add(this.getConditional());
        this.conditionalListDescriptionStyleEClass.getESuperTypes().add(this.getListDescriptionStyle());
        this.dWidgetObjectOutputDescriptionEClass.getESuperTypes().add(this.getDWidgetOutputDescription());
        this.dWidgetStringOutputDescriptionEClass.getESuperTypes().add(this.getDWidgetOutputDescription());
        this.dSelectWidgetDescriptionEClass.getESuperTypes().add(this.getDWidgetDescription());
        this.dTextFieldWidgetDescriptionEClass.getESuperTypes().add(this.getDWidgetDescription());

        // Initialize classes, features, and operations; add parameters
        this.initEClass(this.viewEClass, View.class, "View", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getView_Descriptions(), this.getRepresentationDescription(), null, "descriptions", null, 0, -1, View.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getView_ColorPalettes(), this.getColorPalette(), null, "colorPalettes", null, 0, -1, View.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getView_DynamicDialogFolder(), this.getDynamicDialogFolder(), null, "dynamicDialogFolder", null, 0, 1, View.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.colorPaletteEClass, ColorPalette.class, "ColorPalette", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getColorPalette_Name(), this.getIdentifier(), "name", null, 1, 1, ColorPalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getColorPalette_Colors(), this.getUserColor(), null, "colors", null, 0, -1, ColorPalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.fixedColorEClass, FixedColor.class, "FixedColor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getFixedColor_Value(), this.getColor(), "value", null, 1, 1, FixedColor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.userColorEClass, UserColor.class, "UserColor", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getUserColor_Name(), this.getIdentifier(), "name", null, 0, 1, UserColor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.representationDescriptionEClass, RepresentationDescription.class, "RepresentationDescription", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getRepresentationDescription_Name(), this.getIdentifier(), "name", "NewRepresentationDescription", 0, 1, RepresentationDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getRepresentationDescription_DomainType(), this.getDomainType(), "domainType", "", 0, 1, RepresentationDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getRepresentationDescription_PreconditionExpression(), this.getInterpretedExpression(), "preconditionExpression", "", 0, 1, RepresentationDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getRepresentationDescription_TitleExpression(), this.getInterpretedExpression(), "titleExpression", "aql:\'New Representation\'", 0, 1,
                RepresentationDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.diagramDescriptionEClass, DiagramDescription.class, "DiagramDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getDiagramDescription_AutoLayout(), this.ecorePackage.getEBoolean(), "autoLayout", null, 1, 1, DiagramDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getDiagramDescription_Palette(), this.getDiagramPalette(), null, "palette", null, 0, 1, DiagramDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getDiagramDescription_NodeDescriptions(), this.getNodeDescription(), null, "nodeDescriptions", null, 0, -1, DiagramDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getDiagramDescription_EdgeDescriptions(), this.getEdgeDescription(), null, "edgeDescriptions", null, 0, -1, DiagramDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.diagramElementDescriptionEClass, DiagramElementDescription.class, "DiagramElementDescription", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getDiagramElementDescription_Name(), this.getIdentifier(), "name", "NewRepresentationDescription", 0, 1, DiagramElementDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getDiagramElementDescription_DomainType(), this.getDomainType(), "domainType", null, 0, 1, DiagramElementDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getDiagramElementDescription_SemanticCandidatesExpression(), this.getInterpretedExpression(), "semanticCandidatesExpression", "aql:self.eContents()", 0, 1,
                DiagramElementDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getDiagramElementDescription_PreconditionExpression(), this.getInterpretedExpression(), "preconditionExpression", null, 0, 1, DiagramElementDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getDiagramElementDescription_SynchronizationPolicy(), this.getSynchronizationPolicy(), "synchronizationPolicy", null, 0, 1, DiagramElementDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getDiagramElementDescription_LabelExpression(), this.getInterpretedExpression(), "labelExpression", "aql:self.name", 0, 1, DiagramElementDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.nodeDescriptionEClass, NodeDescription.class, "NodeDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getNodeDescription_Collapsible(), this.ecorePackage.getEBoolean(), "collapsible", null, 0, 1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getNodeDescription_Palette(), this.getNodePalette(), null, "palette", null, 0, 1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getNodeDescription_ChildrenLayoutStrategy(), this.getLayoutStrategyDescription(), null, "childrenLayoutStrategy", null, 0, 1, NodeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getNodeDescription_Style(), this.getNodeStyleDescription(), null, "style", null, 0, 1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getNodeDescription_ConditionalStyles(), this.getConditionalNodeStyle(), null, "conditionalStyles", null, 0, -1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getNodeDescription_ChildrenDescriptions(), this.getNodeDescription(), null, "childrenDescriptions", null, 0, -1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getNodeDescription_BorderNodesDescriptions(), this.getNodeDescription(), null, "borderNodesDescriptions", null, 0, -1, NodeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getNodeDescription_ReusedChildNodeDescriptions(), this.getNodeDescription(), null, "reusedChildNodeDescriptions", null, 0, -1, NodeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getNodeDescription_ReusedBorderNodeDescriptions(), this.getNodeDescription(), null, "reusedBorderNodeDescriptions", null, 0, -1, NodeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getNodeDescription_UserResizable(), this.ecorePackage.getEBoolean(), "userResizable", "true", 1, 1, NodeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.edgeDescriptionEClass, EdgeDescription.class, "EdgeDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getEdgeDescription_BeginLabelExpression(), this.getInterpretedExpression(), "beginLabelExpression", "", 0, 1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getEdgeDescription_EndLabelExpression(), this.getInterpretedExpression(), "endLabelExpression", "", 0, 1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getEdgeDescription_IsDomainBasedEdge(), this.ecorePackage.getEBoolean(), "isDomainBasedEdge", null, 0, 1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getEdgeDescription_Palette(), this.getEdgePalette(), null, "palette", null, 0, 1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getEdgeDescription_SourceNodeDescriptions(), this.getNodeDescription(), null, "sourceNodeDescriptions", null, 1, -1, EdgeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getEdgeDescription_TargetNodeDescriptions(), this.getNodeDescription(), null, "targetNodeDescriptions", null, 1, -1, EdgeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getEdgeDescription_SourceNodesExpression(), this.getInterpretedExpression(), "sourceNodesExpression", null, 0, 1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getEdgeDescription_TargetNodesExpression(), this.getInterpretedExpression(), "targetNodesExpression", "aql:self.eCrossReferences()", 0, 1, EdgeDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getEdgeDescription_Style(), this.getEdgeStyle(), null, "style", null, 0, 1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getEdgeDescription_ConditionalStyles(), this.getConditionalEdgeStyle(), null, "conditionalStyles", null, 0, -1, EdgeDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.labelStyleEClass, LabelStyle.class, "LabelStyle", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getLabelStyle_FontSize(), this.getLength(), "fontSize", "14", 1, 1, LabelStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getLabelStyle_Italic(), this.ecorePackage.getEBoolean(), "italic", "false", 1, 1, LabelStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getLabelStyle_Bold(), this.ecorePackage.getEBoolean(), "bold", "false", 1, 1, LabelStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getLabelStyle_Underline(), this.ecorePackage.getEBoolean(), "underline", "false", 1, 1, LabelStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getLabelStyle_StrikeThrough(), this.ecorePackage.getEBoolean(), "strikeThrough", "false", 1, 1, LabelStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.borderStyleEClass, BorderStyle.class, "BorderStyle", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getBorderStyle_BorderColor(), this.getUserColor(), null, "borderColor", null, 1, 1, BorderStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getBorderStyle_BorderRadius(), this.getLength(), "borderRadius", "3", 1, 1, BorderStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getBorderStyle_BorderSize(), this.getLength(), "borderSize", "1", 1, 1, BorderStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getBorderStyle_BorderLineStyle(), this.getLineStyle(), "borderLineStyle", null, 0, 1, BorderStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.styleEClass, Style.class, "Style", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getStyle_Color(), this.getUserColor(), null, "color", null, 0, 1, Style.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.nodeStyleDescriptionEClass, NodeStyleDescription.class, "NodeStyleDescription", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getNodeStyleDescription_LabelColor(), this.getUserColor(), null, "labelColor", null, 0, 1, NodeStyleDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getNodeStyleDescription_WidthComputationExpression(), this.getInterpretedExpression(), "widthComputationExpression", "1", 0, 1, NodeStyleDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getNodeStyleDescription_HeightComputationExpression(), this.getInterpretedExpression(), "heightComputationExpression", "1", 0, 1, NodeStyleDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getNodeStyleDescription_ShowIcon(), this.ecorePackage.getEBoolean(), "showIcon", null, 0, 1, NodeStyleDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.rectangularNodeStyleDescriptionEClass, RectangularNodeStyleDescription.class, "RectangularNodeStyleDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getRectangularNodeStyleDescription_WithHeader(), this.ecorePackage.getEBoolean(), "withHeader", null, 0, 1, RectangularNodeStyleDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.imageNodeStyleDescriptionEClass, ImageNodeStyleDescription.class, "ImageNodeStyleDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getImageNodeStyleDescription_Shape(), this.ecorePackage.getEString(), "shape", null, 0, 1, ImageNodeStyleDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.iconLabelNodeStyleDescriptionEClass, IconLabelNodeStyleDescription.class, "IconLabelNodeStyleDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.layoutStrategyDescriptionEClass, LayoutStrategyDescription.class, "LayoutStrategyDescription", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.freeFormLayoutStrategyDescriptionEClass, FreeFormLayoutStrategyDescription.class, "FreeFormLayoutStrategyDescription", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.listLayoutStrategyDescriptionEClass, ListLayoutStrategyDescription.class, "ListLayoutStrategyDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.edgeStyleEClass, EdgeStyle.class, "EdgeStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getEdgeStyle_LineStyle(), this.getLineStyle(), "lineStyle", "Solid", 1, 1, EdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getEdgeStyle_SourceArrowStyle(), this.getArrowStyle(), "sourceArrowStyle", "None", 1, 1, EdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getEdgeStyle_TargetArrowStyle(), this.getArrowStyle(), "targetArrowStyle", "InputArrow", 1, 1, EdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getEdgeStyle_EdgeWidth(), this.getLength(), "edgeWidth", "1", 1, 1, EdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getEdgeStyle_ShowIcon(), this.ecorePackage.getEBoolean(), "showIcon", "false", 0, 1, EdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.toolEClass, Tool.class, "Tool", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getTool_Name(), this.getIdentifier(), "name", "Tool", 1, 1, Tool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
                IS_ORDERED);
        this.initEReference(this.getTool_Body(), this.getOperation(), null, "body", null, 0, -1, Tool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.labelEditToolEClass, LabelEditTool.class, "LabelEditTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getLabelEditTool_InitialDirectEditLabelExpression(), this.getInterpretedExpression(), "initialDirectEditLabelExpression", null, 0, 1, LabelEditTool.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.deleteToolEClass, DeleteTool.class, "DeleteTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.nodeToolEClass, NodeTool.class, "NodeTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getNodeTool_SelectionDescription(), this.getSelectionDescription(), null, "selectionDescription", null, 0, 1, NodeTool.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.edgeToolEClass, EdgeTool.class, "EdgeTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getEdgeTool_TargetElementDescriptions(), this.getDiagramElementDescription(), null, "targetElementDescriptions", null, 0, -1, EdgeTool.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.edgeReconnectionToolEClass, EdgeReconnectionTool.class, "EdgeReconnectionTool", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.sourceEdgeEndReconnectionToolEClass, SourceEdgeEndReconnectionTool.class, "SourceEdgeEndReconnectionTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.targetEdgeEndReconnectionToolEClass, TargetEdgeEndReconnectionTool.class, "TargetEdgeEndReconnectionTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.dropToolEClass, DropTool.class, "DropTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.operationEClass, Operation.class, "Operation", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getOperation_Children(), this.getOperation(), null, "children", null, 0, -1, Operation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.changeContextEClass, ChangeContext.class, "ChangeContext", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getChangeContext_Expression(), this.getInterpretedExpression(), "expression", "aql:self", 1, 1, ChangeContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.createInstanceEClass, CreateInstance.class, "CreateInstance", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getCreateInstance_TypeName(), this.getDomainType(), "typeName", null, 1, 1, CreateInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getCreateInstance_ReferenceName(), this.ecorePackage.getEString(), "referenceName", null, 1, 1, CreateInstance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getCreateInstance_VariableName(), this.ecorePackage.getEString(), "variableName", "newInstance", 1, 1, CreateInstance.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.setValueEClass, SetValue.class, "SetValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getSetValue_FeatureName(), this.ecorePackage.getEString(), "featureName", null, 1, 1, SetValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getSetValue_ValueExpression(), this.getInterpretedExpression(), "valueExpression", null, 1, 1, SetValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.unsetValueEClass, UnsetValue.class, "UnsetValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getUnsetValue_FeatureName(), this.ecorePackage.getEString(), "featureName", null, 1, 1, UnsetValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getUnsetValue_ElementExpression(), this.getInterpretedExpression(), "elementExpression", null, 1, 1, UnsetValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.deleteElementEClass, DeleteElement.class, "DeleteElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.createViewEClass, CreateView.class, "CreateView", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getCreateView_ParentViewExpression(), this.getInterpretedExpression(), "parentViewExpression", "aql:selectedNode", 1, 1, CreateView.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getCreateView_ElementDescription(), this.getDiagramElementDescription(), null, "elementDescription", null, 0, 1, CreateView.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getCreateView_SemanticElementExpression(), this.getInterpretedExpression(), "semanticElementExpression", "aql:self", 1, 1, CreateView.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getCreateView_VariableName(), this.ecorePackage.getEString(), "variableName", null, 0, 1, CreateView.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getCreateView_ContainmentKind(), this.getNodeContainmentKind(), "containmentKind", "CHILD_NODE", 1, 1, CreateView.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.deleteViewEClass, DeleteView.class, "DeleteView", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getDeleteView_ViewExpression(), this.getInterpretedExpression(), "viewExpression", "aql:selectedNode", 1, 1, DeleteView.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalEClass, Conditional.class, "Conditional", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getConditional_Condition(), this.getInterpretedExpression(), "condition", "aql:false", 1, 1, Conditional.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalNodeStyleEClass, ConditionalNodeStyle.class, "ConditionalNodeStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getConditionalNodeStyle_Style(), this.getNodeStyleDescription(), null, "style", null, 0, 1, ConditionalNodeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalEdgeStyleEClass, ConditionalEdgeStyle.class, "ConditionalEdgeStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.formDescriptionEClass, FormDescription.class, "FormDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getFormDescription_Pages(), this.getPageDescription(), null, "pages", null, 0, -1, FormDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.pageDescriptionEClass, PageDescription.class, "PageDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getPageDescription_Name(), this.getIdentifier(), "name", null, 0, 1, PageDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getPageDescription_LabelExpression(), this.getInterpretedExpression(), "labelExpression", null, 0, 1, PageDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getPageDescription_SemanticCandidatesExpression(), this.getInterpretedExpression(), "semanticCandidatesExpression", "aql:self", 0, 1, PageDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getPageDescription_PreconditionExpression(), this.getInterpretedExpression(), "preconditionExpression", "", 0, 1, PageDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getPageDescription_Groups(), this.getGroupDescription(), null, "groups", null, 0, -1, PageDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getPageDescription_ToolbarActions(), this.getButtonDescription(), null, "toolbarActions", null, 0, -1, PageDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.groupDescriptionEClass, GroupDescription.class, "GroupDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getGroupDescription_Name(), this.getIdentifier(), "name", null, 0, 1, GroupDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getGroupDescription_LabelExpression(), this.getInterpretedExpression(), "labelExpression", null, 0, 1, GroupDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getGroupDescription_DisplayMode(), this.getGroupDisplayMode(), "displayMode", "LIST", 1, 1, GroupDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getGroupDescription_SemanticCandidatesExpression(), this.getInterpretedExpression(), "semanticCandidatesExpression", "aql:self", 0, 1, GroupDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getGroupDescription_ToolbarActions(), this.getButtonDescription(), null, "toolbarActions", null, 0, -1, GroupDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getGroupDescription_Widgets(), this.getWidgetDescription(), null, "widgets", null, 0, -1, GroupDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.widgetDescriptionEClass, WidgetDescription.class, "WidgetDescription", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getWidgetDescription_Name(), this.getIdentifier(), "name", null, 0, 1, WidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getWidgetDescription_LabelExpression(), this.getInterpretedExpression(), "labelExpression", null, 0, 1, WidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.textfieldDescriptionEClass, TextfieldDescription.class, "TextfieldDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getTextfieldDescription_ValueExpression(), this.getInterpretedExpression(), "valueExpression", null, 0, 1, TextfieldDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTextfieldDescription_Body(), this.getOperation(), null, "body", null, 0, -1, TextfieldDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTextfieldDescription_Style(), this.getTextfieldDescriptionStyle(), null, "style", null, 0, 1, TextfieldDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTextfieldDescription_ConditionalStyles(), this.getConditionalTextfieldDescriptionStyle(), null, "conditionalStyles", null, 0, -1, TextfieldDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.checkboxDescriptionEClass, CheckboxDescription.class, "CheckboxDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getCheckboxDescription_ValueExpression(), this.getInterpretedExpression(), "valueExpression", null, 0, 1, CheckboxDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getCheckboxDescription_Body(), this.getOperation(), null, "body", null, 0, -1, CheckboxDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getCheckboxDescription_Style(), this.getCheckboxDescriptionStyle(), null, "style", null, 0, 1, CheckboxDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getCheckboxDescription_ConditionalStyles(), this.getConditionalCheckboxDescriptionStyle(), null, "conditionalStyles", null, 0, -1, CheckboxDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.selectDescriptionEClass, SelectDescription.class, "SelectDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getSelectDescription_ValueExpression(), this.getInterpretedExpression(), "valueExpression", null, 0, 1, SelectDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getSelectDescription_CandidatesExpression(), this.getInterpretedExpression(), "candidatesExpression", null, 0, 1, SelectDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getSelectDescription_CandidateLabelExpression(), this.getInterpretedExpression(), "candidateLabelExpression", null, 0, 1, SelectDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getSelectDescription_Body(), this.getOperation(), null, "body", null, 0, -1, SelectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getSelectDescription_Style(), this.getSelectDescriptionStyle(), null, "style", null, 0, 1, SelectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getSelectDescription_ConditionalStyles(), this.getConditionalSelectDescriptionStyle(), null, "conditionalStyles", null, 0, -1, SelectDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.multiSelectDescriptionEClass, MultiSelectDescription.class, "MultiSelectDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getMultiSelectDescription_ValueExpression(), this.getInterpretedExpression(), "valueExpression", null, 0, 1, MultiSelectDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getMultiSelectDescription_CandidatesExpression(), this.getInterpretedExpression(), "candidatesExpression", null, 0, 1, MultiSelectDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getMultiSelectDescription_CandidateLabelExpression(), this.getInterpretedExpression(), "candidateLabelExpression", null, 0, 1, MultiSelectDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getMultiSelectDescription_Body(), this.getOperation(), null, "body", null, 0, -1, MultiSelectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getMultiSelectDescription_Style(), this.getMultiSelectDescriptionStyle(), null, "style", null, 0, 1, MultiSelectDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getMultiSelectDescription_ConditionalStyles(), this.getConditionalMultiSelectDescriptionStyle(), null, "conditionalStyles", null, 0, -1, MultiSelectDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.textAreaDescriptionEClass, TextAreaDescription.class, "TextAreaDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getTextAreaDescription_ValueExpression(), this.getInterpretedExpression(), "valueExpression", null, 0, 1, TextAreaDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTextAreaDescription_Body(), this.getOperation(), null, "body", null, 0, -1, TextAreaDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTextAreaDescription_Style(), this.getTextareaDescriptionStyle(), null, "style", null, 0, 1, TextAreaDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTextAreaDescription_ConditionalStyles(), this.getConditionalTextareaDescriptionStyle(), null, "conditionalStyles", null, 0, -1, TextAreaDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.richTextDescriptionEClass, RichTextDescription.class, "RichTextDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getRichTextDescription_ValueExpression(), this.getInterpretedExpression(), "valueExpression", null, 0, 1, RichTextDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getRichTextDescription_Body(), this.getOperation(), null, "body", null, 0, -1, RichTextDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.radioDescriptionEClass, RadioDescription.class, "RadioDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getRadioDescription_ValueExpression(), this.getInterpretedExpression(), "valueExpression", null, 0, 1, RadioDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getRadioDescription_CandidatesExpression(), this.getInterpretedExpression(), "candidatesExpression", null, 0, 1, RadioDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getRadioDescription_CandidateLabelExpression(), this.getInterpretedExpression(), "candidateLabelExpression", null, 0, 1, RadioDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getRadioDescription_Body(), this.getOperation(), null, "body", null, 0, -1, RadioDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getRadioDescription_Style(), this.getRadioDescriptionStyle(), null, "style", null, 0, 1, RadioDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getRadioDescription_ConditionalStyles(), this.getConditionalRadioDescriptionStyle(), null, "conditionalStyles", null, 0, -1, RadioDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.barChartDescriptionEClass, BarChartDescription.class, "BarChartDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getBarChartDescription_ValuesExpression(), this.getInterpretedExpression(), "valuesExpression", null, 0, 1, BarChartDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getBarChartDescription_KeysExpression(), this.getInterpretedExpression(), "keysExpression", null, 0, 1, BarChartDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getBarChartDescription_YAxisLabelExpression(), this.getInterpretedExpression(), "yAxisLabelExpression", null, 0, 1, BarChartDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getBarChartDescription_Style(), this.getBarChartDescriptionStyle(), null, "style", null, 0, 1, BarChartDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getBarChartDescription_ConditionalStyles(), this.getConditionalBarChartDescriptionStyle(), null, "conditionalStyles", null, 0, -1, BarChartDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getBarChartDescription_Width(), this.getLength(), "width", "500", 1, 1, BarChartDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getBarChartDescription_Height(), this.getLength(), "height", "250", 1, 1, BarChartDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.pieChartDescriptionEClass, PieChartDescription.class, "PieChartDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getPieChartDescription_ValuesExpression(), this.getInterpretedExpression(), "valuesExpression", null, 0, 1, PieChartDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getPieChartDescription_KeysExpression(), this.getInterpretedExpression(), "keysExpression", null, 0, 1, PieChartDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getPieChartDescription_Style(), this.getPieChartDescriptionStyle(), null, "style", null, 0, 1, PieChartDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getPieChartDescription_ConditionalStyles(), this.getConditionalPieChartDescriptionStyle(), null, "conditionalStyles", null, 0, -1, PieChartDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.flexboxContainerDescriptionEClass, FlexboxContainerDescription.class, "FlexboxContainerDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getFlexboxContainerDescription_Children(), this.getWidgetDescription(), null, "children", null, 0, -1, FlexboxContainerDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getFlexboxContainerDescription_FlexDirection(), this.getFlexDirection(), "flexDirection", "row", 1, 1, FlexboxContainerDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.buttonDescriptionEClass, ButtonDescription.class, "ButtonDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getButtonDescription_ButtonLabelExpression(), this.getInterpretedExpression(), "buttonLabelExpression", null, 0, 1, ButtonDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getButtonDescription_Body(), this.getOperation(), null, "body", null, 0, -1, ButtonDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getButtonDescription_ImageExpression(), this.getInterpretedExpression(), "imageExpression", null, 0, 1, ButtonDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getButtonDescription_Style(), this.getButtonDescriptionStyle(), null, "style", null, 0, 1, ButtonDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getButtonDescription_ConditionalStyles(), this.getConditionalButtonDescriptionStyle(), null, "conditionalStyles", null, 0, -1, ButtonDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.imageDescriptionEClass, ImageDescription.class, "ImageDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getImageDescription_UrlExpression(), this.getInterpretedExpression(), "urlExpression", null, 0, 1, ImageDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getImageDescription_MaxWidthExpression(), this.getInterpretedExpression(), "maxWidthExpression", null, 0, 1, ImageDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.widgetDescriptionStyleEClass, WidgetDescriptionStyle.class, "WidgetDescriptionStyle", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.textfieldDescriptionStyleEClass, TextfieldDescriptionStyle.class, "TextfieldDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getTextfieldDescriptionStyle_BackgroundColor(), this.getUserColor(), null, "backgroundColor", null, 0, 1, TextfieldDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTextfieldDescriptionStyle_ForegroundColor(), this.getUserColor(), null, "foregroundColor", null, 0, 1, TextfieldDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalTextfieldDescriptionStyleEClass, ConditionalTextfieldDescriptionStyle.class, "ConditionalTextfieldDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.checkboxDescriptionStyleEClass, CheckboxDescriptionStyle.class, "CheckboxDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getCheckboxDescriptionStyle_Color(), this.getUserColor(), null, "color", null, 0, 1, CheckboxDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalCheckboxDescriptionStyleEClass, ConditionalCheckboxDescriptionStyle.class, "ConditionalCheckboxDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.selectDescriptionStyleEClass, SelectDescriptionStyle.class, "SelectDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getSelectDescriptionStyle_BackgroundColor(), this.getUserColor(), null, "backgroundColor", null, 0, 1, SelectDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getSelectDescriptionStyle_ForegroundColor(), this.getUserColor(), null, "foregroundColor", null, 0, 1, SelectDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalSelectDescriptionStyleEClass, ConditionalSelectDescriptionStyle.class, "ConditionalSelectDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.multiSelectDescriptionStyleEClass, MultiSelectDescriptionStyle.class, "MultiSelectDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getMultiSelectDescriptionStyle_BackgroundColor(), this.getUserColor(), null, "backgroundColor", null, 0, 1, MultiSelectDescriptionStyle.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getMultiSelectDescriptionStyle_ForegroundColor(), this.getUserColor(), null, "foregroundColor", null, 0, 1, MultiSelectDescriptionStyle.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalMultiSelectDescriptionStyleEClass, ConditionalMultiSelectDescriptionStyle.class, "ConditionalMultiSelectDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.textareaDescriptionStyleEClass, TextareaDescriptionStyle.class, "TextareaDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getTextareaDescriptionStyle_BackgroundColor(), this.getUserColor(), null, "backgroundColor", null, 0, 1, TextareaDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTextareaDescriptionStyle_ForegroundColor(), this.getUserColor(), null, "foregroundColor", null, 0, 1, TextareaDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalTextareaDescriptionStyleEClass, ConditionalTextareaDescriptionStyle.class, "ConditionalTextareaDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.radioDescriptionStyleEClass, RadioDescriptionStyle.class, "RadioDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getRadioDescriptionStyle_Color(), this.getUserColor(), null, "color", null, 0, 1, RadioDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalRadioDescriptionStyleEClass, ConditionalRadioDescriptionStyle.class, "ConditionalRadioDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.buttonDescriptionStyleEClass, ButtonDescriptionStyle.class, "ButtonDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getButtonDescriptionStyle_BackgroundColor(), this.getUserColor(), null, "backgroundColor", null, 0, 1, ButtonDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getButtonDescriptionStyle_ForegroundColor(), this.getUserColor(), null, "foregroundColor", null, 0, 1, ButtonDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalButtonDescriptionStyleEClass, ConditionalButtonDescriptionStyle.class, "ConditionalButtonDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.barChartDescriptionStyleEClass, BarChartDescriptionStyle.class, "BarChartDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getBarChartDescriptionStyle_BarsColor(), this.ecorePackage.getEString(), "barsColor", null, 0, 1, BarChartDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalBarChartDescriptionStyleEClass, ConditionalBarChartDescriptionStyle.class, "ConditionalBarChartDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.pieChartDescriptionStyleEClass, PieChartDescriptionStyle.class, "PieChartDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getPieChartDescriptionStyle_Colors(), this.ecorePackage.getEString(), "colors", null, 0, 1, PieChartDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getPieChartDescriptionStyle_StrokeWidth(), this.getLength(), "strokeWidth", null, 0, 1, PieChartDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getPieChartDescriptionStyle_StrokeColor(), this.getUserColor(), null, "strokeColor", null, 0, 1, PieChartDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalPieChartDescriptionStyleEClass, ConditionalPieChartDescriptionStyle.class, "ConditionalPieChartDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.labelDescriptionEClass, LabelDescription.class, "LabelDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getLabelDescription_ValueExpression(), this.getInterpretedExpression(), "valueExpression", null, 0, 1, LabelDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getLabelDescription_Style(), this.getLabelDescriptionStyle(), null, "style", null, 0, 1, LabelDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getLabelDescription_ConditionalStyles(), this.getConditionalLabelDescriptionStyle(), null, "conditionalStyles", null, 0, -1, LabelDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.labelDescriptionStyleEClass, LabelDescriptionStyle.class, "LabelDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getLabelDescriptionStyle_Color(), this.getUserColor(), null, "color", null, 0, 1, LabelDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalLabelDescriptionStyleEClass, ConditionalLabelDescriptionStyle.class, "ConditionalLabelDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.linkDescriptionEClass, LinkDescription.class, "LinkDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getLinkDescription_ValueExpression(), this.getInterpretedExpression(), "valueExpression", null, 0, 1, LinkDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getLinkDescription_Style(), this.getLinkDescriptionStyle(), null, "style", null, 0, 1, LinkDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getLinkDescription_ConditionalStyles(), this.getConditionalLinkDescriptionStyle(), null, "conditionalStyles", null, 0, -1, LinkDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.linkDescriptionStyleEClass, LinkDescriptionStyle.class, "LinkDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getLinkDescriptionStyle_Color(), this.getUserColor(), null, "color", null, 0, 1, LinkDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalLinkDescriptionStyleEClass, ConditionalLinkDescriptionStyle.class, "ConditionalLinkDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.listDescriptionEClass, ListDescription.class, "ListDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getListDescription_ValueExpression(), this.getInterpretedExpression(), "valueExpression", null, 0, 1, ListDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getListDescription_DisplayExpression(), this.getInterpretedExpression(), "displayExpression", null, 0, 1, ListDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getListDescription_IsDeletableExpression(), this.getInterpretedExpression(), "isDeletableExpression", null, 0, 1, ListDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getListDescription_Body(), this.getOperation(), null, "body", null, 0, -1, ListDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getListDescription_Style(), this.getListDescriptionStyle(), null, "style", null, 0, 1, ListDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getListDescription_ConditionalStyles(), this.getConditionalListDescriptionStyle(), null, "conditionalStyles", null, 0, -1, ListDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.listDescriptionStyleEClass, ListDescriptionStyle.class, "ListDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getListDescriptionStyle_Color(), this.getUserColor(), null, "color", null, 0, 1, ListDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.conditionalListDescriptionStyleEClass, ConditionalListDescriptionStyle.class, "ConditionalListDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.diagramPaletteEClass, DiagramPalette.class, "DiagramPalette", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getDiagramPalette_DropTool(), this.getDropTool(), null, "dropTool", null, 0, 1, DiagramPalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getDiagramPalette_NodeTools(), this.getNodeTool(), null, "nodeTools", null, 0, -1, DiagramPalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.nodePaletteEClass, NodePalette.class, "NodePalette", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getNodePalette_DeleteTool(), this.getDeleteTool(), null, "deleteTool", null, 0, 1, NodePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getNodePalette_LabelEditTool(), this.getLabelEditTool(), null, "labelEditTool", null, 0, 1, NodePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getNodePalette_NodeTools(), this.getNodeTool(), null, "nodeTools", null, 0, -1, NodePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getNodePalette_EdgeTools(), this.getEdgeTool(), null, "edgeTools", null, 0, -1, NodePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.edgePaletteEClass, EdgePalette.class, "EdgePalette", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getEdgePalette_DeleteTool(), this.getDeleteTool(), null, "deleteTool", null, 0, 1, EdgePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getEdgePalette_CenterLabelEditTool(), this.getLabelEditTool(), null, "centerLabelEditTool", null, 0, 1, EdgePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getEdgePalette_BeginLabelEditTool(), this.getLabelEditTool(), null, "beginLabelEditTool", null, 0, 1, EdgePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getEdgePalette_EndLabelEditTool(), this.getLabelEditTool(), null, "endLabelEditTool", null, 0, 1, EdgePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getEdgePalette_NodeTools(), this.getNodeTool(), null, "nodeTools", null, 0, -1, EdgePalette.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getEdgePalette_EdgeReconnectionTools(), this.getEdgeReconnectionTool(), null, "edgeReconnectionTools", null, 0, -1, EdgePalette.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.selectionDescriptionEClass, SelectionDescription.class, "SelectionDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getSelectionDescription_SelectionCandidatesExpression(), this.getInterpretedExpression(), "selectionCandidatesExpression", "aql:self", 0, 1,
                SelectionDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getSelectionDescription_SelectionMessage(), this.ecorePackage.getEString(), "selectionMessage", null, 0, 1, SelectionDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.dynamicDialogFolderEClass, DynamicDialogFolder.class, "DynamicDialogFolder", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getDynamicDialogFolder_SubFolders(), this.getDynamicDialogFolder(), null, "subFolders", null, 0, -1, DynamicDialogFolder.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getDynamicDialogFolder_Name(), this.ecorePackage.getEString(), "name", null, 0, 1, DynamicDialogFolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getDynamicDialogFolder_DynamicDialogs(), this.getDynamicDialogDescription(), null, "dynamicDialogs", null, 0, -1, DynamicDialogFolder.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.dynamicDialogDescriptionEClass, DynamicDialogDescription.class, "DynamicDialogDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getDynamicDialogDescription_Id(), this.getIdentifier(), "id", null, 0, 1, DynamicDialogDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getDynamicDialogDescription_TitleExpression(), this.getInterpretedExpression(), "titleExpression", null, 0, 1, DynamicDialogDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getDynamicDialogDescription_DescriptionExpression(), this.getInterpretedExpression(), "descriptionExpression", null, 0, 1, DynamicDialogDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getDynamicDialogDescription_WidgetDescriptions(), this.getDWidgetDescription(), null, "widgetDescriptions", null, 0, -1, DynamicDialogDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getDynamicDialogDescription_IsValidExpression(), this.getInterpretedExpression(), "isValidExpression", null, 0, 1, DynamicDialogDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getDynamicDialogDescription_Body(), this.getOperation(), null, "body", null, 0, -1, DynamicDialogDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getDynamicDialogDescription_ValidationMessages(), this.getDValidationMessageDescription(), null, "validationMessages", null, 0, -1, DynamicDialogDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.dWidgetDescriptionEClass, DWidgetDescription.class, "DWidgetDescription", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getDWidgetDescription_Id(), this.getIdentifier(), "id", null, 0, 1, DWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getDWidgetDescription_LabelExpression(), this.getInterpretedExpression(), "labelExpression", null, 0, 1, DWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getDWidgetDescription_InitialValueExpression(), this.getInterpretedExpression(), "initialValueExpression", null, 0, 1, DWidgetDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getDWidgetDescription_Output(), this.getDWidgetOutputDescription(), null, "output", null, 0, 1, DWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getDWidgetDescription_Inputs(), this.getDWidgetOutputDescription(), null, "inputs", null, 0, -1, DWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.dWidgetOutputDescriptionEClass, DWidgetOutputDescription.class, "DWidgetOutputDescription", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getDWidgetOutputDescription_Name(), this.ecorePackage.getEString(), "name", null, 0, 1, DWidgetOutputDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.dWidgetObjectOutputDescriptionEClass, DWidgetObjectOutputDescription.class, "DWidgetObjectOutputDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.dWidgetStringOutputDescriptionEClass, DWidgetStringOutputDescription.class, "DWidgetStringOutputDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.dSelectWidgetDescriptionEClass, DSelectWidgetDescription.class, "DSelectWidgetDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getDSelectWidgetDescription_OptionsExpression(), this.getInterpretedExpression(), "optionsExpression", null, 0, 1, DSelectWidgetDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getDSelectWidgetDescription_OptionLabelExpression(), this.getInterpretedExpression(), "optionLabelExpression", null, 0, 1, DSelectWidgetDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getDSelectWidgetDescription_Style(), this.getSelectDescriptionStyle(), null, "style", null, 0, 1, DSelectWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.dTextFieldWidgetDescriptionEClass, DTextFieldWidgetDescription.class, "DTextFieldWidgetDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getDTextFieldWidgetDescription_Style(), this.getSelectDescriptionStyle(), null, "style", null, 0, 1, DTextFieldWidgetDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.dValidationMessageDescriptionEClass, DValidationMessageDescription.class, "DValidationMessageDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getDValidationMessageDescription_PreCondition(), this.getInterpretedExpression(), "preCondition", null, 0, 1, DValidationMessageDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getDValidationMessageDescription_MessageExpression(), this.getInterpretedExpression(), "messageExpression", null, 0, 1, DValidationMessageDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getDValidationMessageDescription_Severity(), this.getDValidationMessageSeverity(), "severity", null, 0, 1, DValidationMessageDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getDValidationMessageDescription_BlocksApplyDialog(), this.ecorePackage.getEBoolean(), "blocksApplyDialog", null, 0, 1, DValidationMessageDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Initialize enums and add enum literals
        this.initEEnum(this.layoutDirectionEEnum, LayoutDirection.class, "LayoutDirection");
        this.addEEnumLiteral(this.layoutDirectionEEnum, LayoutDirection.COLUMN);

        this.initEEnum(this.arrowStyleEEnum, ArrowStyle.class, "ArrowStyle");
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.NONE);
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.OUTPUT_ARROW);
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.INPUT_ARROW);
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.OUTPUT_CLOSED_ARROW);
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.INPUT_CLOSED_ARROW);
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.OUTPUT_FILL_CLOSED_ARROW);
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.INPUT_FILL_CLOSED_ARROW);
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.DIAMOND);
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.FILL_DIAMOND);
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.INPUT_ARROW_WITH_DIAMOND);
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.INPUT_ARROW_WITH_FILL_DIAMOND);
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.CIRCLE);
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.FILL_CIRCLE);
        this.addEEnumLiteral(this.arrowStyleEEnum, ArrowStyle.CROSSED_CIRCLE);

        this.initEEnum(this.lineStyleEEnum, LineStyle.class, "LineStyle");
        this.addEEnumLiteral(this.lineStyleEEnum, LineStyle.SOLID);
        this.addEEnumLiteral(this.lineStyleEEnum, LineStyle.DASH);
        this.addEEnumLiteral(this.lineStyleEEnum, LineStyle.DOT);
        this.addEEnumLiteral(this.lineStyleEEnum, LineStyle.DASH_DOT);

        this.initEEnum(this.nodeContainmentKindEEnum, NodeContainmentKind.class, "NodeContainmentKind");
        this.addEEnumLiteral(this.nodeContainmentKindEEnum, NodeContainmentKind.CHILD_NODE);
        this.addEEnumLiteral(this.nodeContainmentKindEEnum, NodeContainmentKind.BORDER_NODE);

        this.initEEnum(this.synchronizationPolicyEEnum, SynchronizationPolicy.class, "SynchronizationPolicy");
        this.addEEnumLiteral(this.synchronizationPolicyEEnum, SynchronizationPolicy.SYNCHRONIZED);
        this.addEEnumLiteral(this.synchronizationPolicyEEnum, SynchronizationPolicy.UNSYNCHRONIZED);

        this.initEEnum(this.groupDisplayModeEEnum, GroupDisplayMode.class, "GroupDisplayMode");
        this.addEEnumLiteral(this.groupDisplayModeEEnum, GroupDisplayMode.LIST);
        this.addEEnumLiteral(this.groupDisplayModeEEnum, GroupDisplayMode.TOGGLEABLE_AREAS);

        this.initEEnum(this.flexDirectionEEnum, FlexDirection.class, "FlexDirection");
        this.addEEnumLiteral(this.flexDirectionEEnum, FlexDirection.ROW);
        this.addEEnumLiteral(this.flexDirectionEEnum, FlexDirection.ROW_REVERSE);
        this.addEEnumLiteral(this.flexDirectionEEnum, FlexDirection.COLUMN);
        this.addEEnumLiteral(this.flexDirectionEEnum, FlexDirection.COLUMN_REVERSE);

        this.initEEnum(this.dValidationMessageSeverityEEnum, DValidationMessageSeverity.class, "DValidationMessageSeverity");
        this.addEEnumLiteral(this.dValidationMessageSeverityEEnum, DValidationMessageSeverity.INFO);
        this.addEEnumLiteral(this.dValidationMessageSeverityEEnum, DValidationMessageSeverity.WARNING);
        this.addEEnumLiteral(this.dValidationMessageSeverityEEnum, DValidationMessageSeverity.ERROR);

        // Initialize data types
        this.initEDataType(this.identifierEDataType, String.class, "Identifier", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        this.initEDataType(this.interpretedExpressionEDataType, String.class, "InterpretedExpression", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        this.initEDataType(this.domainTypeEDataType, String.class, "DomainType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        this.initEDataType(this.colorEDataType, String.class, "Color", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        this.initEDataType(this.lengthEDataType, int.class, "Length", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        this.createResource(eNS_URI);
    }

} // ViewPackageImpl
