/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
import org.eclipse.sirius.components.view.ArrowStyle;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.CheckboxDescription;
import org.eclipse.sirius.components.view.CheckboxDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalEdgeStyle;
import org.eclipse.sirius.components.view.ConditionalNodeStyle;
import org.eclipse.sirius.components.view.CreateInstance;
import org.eclipse.sirius.components.view.CreateView;
import org.eclipse.sirius.components.view.DeleteElement;
import org.eclipse.sirius.components.view.DeleteTool;
import org.eclipse.sirius.components.view.DeleteView;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.DropTool;
import org.eclipse.sirius.components.view.EdgeDescription;
import org.eclipse.sirius.components.view.EdgeStyle;
import org.eclipse.sirius.components.view.EdgeTool;
import org.eclipse.sirius.components.view.FormDescription;
import org.eclipse.sirius.components.view.LabelEditTool;
import org.eclipse.sirius.components.view.LineStyle;
import org.eclipse.sirius.components.view.MultiSelectDescription;
import org.eclipse.sirius.components.view.MultiSelectDescriptionStyle;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.NodeStyle;
import org.eclipse.sirius.components.view.NodeTool;
import org.eclipse.sirius.components.view.RadioDescription;
import org.eclipse.sirius.components.view.RadioDescriptionStyle;
import org.eclipse.sirius.components.view.SelectDescription;
import org.eclipse.sirius.components.view.SelectDescriptionStyle;
import org.eclipse.sirius.components.view.SetValue;
import org.eclipse.sirius.components.view.SynchronizationPolicy;
import org.eclipse.sirius.components.view.TextAreaDescription;
import org.eclipse.sirius.components.view.TextareaDescriptionStyle;
import org.eclipse.sirius.components.view.TextfieldDescription;
import org.eclipse.sirius.components.view.TextfieldDescriptionStyle;
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
        case ViewPackage.DIAGRAM_DESCRIPTION:
            return this.createDiagramDescription();
        case ViewPackage.NODE_DESCRIPTION:
            return this.createNodeDescription();
        case ViewPackage.EDGE_DESCRIPTION:
            return this.createEdgeDescription();
        case ViewPackage.NODE_STYLE:
            return this.createNodeStyle();
        case ViewPackage.EDGE_STYLE:
            return this.createEdgeStyle();
        case ViewPackage.LABEL_EDIT_TOOL:
            return this.createLabelEditTool();
        case ViewPackage.DELETE_TOOL:
            return this.createDeleteTool();
        case ViewPackage.NODE_TOOL:
            return this.createNodeTool();
        case ViewPackage.EDGE_TOOL:
            return this.createEdgeTool();
        case ViewPackage.DROP_TOOL:
            return this.createDropTool();
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
        case ViewPackage.CREATE_VIEW:
            return this.createCreateView();
        case ViewPackage.DELETE_VIEW:
            return this.createDeleteView();
        case ViewPackage.CONDITIONAL_NODE_STYLE:
            return this.createConditionalNodeStyle();
        case ViewPackage.CONDITIONAL_EDGE_STYLE:
            return this.createConditionalEdgeStyle();
        case ViewPackage.FORM_DESCRIPTION:
            return this.createFormDescription();
        case ViewPackage.TEXTFIELD_DESCRIPTION:
            return this.createTextfieldDescription();
        case ViewPackage.CHECKBOX_DESCRIPTION:
            return this.createCheckboxDescription();
        case ViewPackage.SELECT_DESCRIPTION:
            return this.createSelectDescription();
        case ViewPackage.MULTI_SELECT_DESCRIPTION:
            return this.createMultiSelectDescription();
        case ViewPackage.TEXT_AREA_DESCRIPTION:
            return this.createTextAreaDescription();
        case ViewPackage.RADIO_DESCRIPTION:
            return this.createRadioDescription();
        case ViewPackage.TEXTFIELD_DESCRIPTION_STYLE:
            return this.createTextfieldDescriptionStyle();
        case ViewPackage.CHECKBOX_DESCRIPTION_STYLE:
            return this.createCheckboxDescriptionStyle();
        case ViewPackage.SELECT_DESCRIPTION_STYLE:
            return this.createSelectDescriptionStyle();
        case ViewPackage.MULTI_SELECT_DESCRIPTION_STYLE:
            return this.createMultiSelectDescriptionStyle();
        case ViewPackage.TEXTAREA_DESCRIPTION_STYLE:
            return this.createTextareaDescriptionStyle();
        case ViewPackage.RADIO_DESCRIPTION_STYLE:
            return this.createRadioDescriptionStyle();
        default:
            throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
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
        case ViewPackage.ARROW_STYLE:
            return this.createArrowStyleFromString(eDataType, initialValue);
        case ViewPackage.LINE_STYLE:
            return this.createLineStyleFromString(eDataType, initialValue);
        case ViewPackage.SYNCHRONIZATION_POLICY:
            return this.createSynchronizationPolicyFromString(eDataType, initialValue);
        default:
            throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
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
        case ViewPackage.ARROW_STYLE:
            return this.convertArrowStyleToString(eDataType, instanceValue);
        case ViewPackage.LINE_STYLE:
            return this.convertLineStyleToString(eDataType, instanceValue);
        case ViewPackage.SYNCHRONIZATION_POLICY:
            return this.convertSynchronizationPolicyToString(eDataType, instanceValue);
        default:
            throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
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
    public DiagramDescription createDiagramDescription() {
        DiagramDescriptionImpl diagramDescription = new DiagramDescriptionImpl();
        return diagramDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NodeDescription createNodeDescription() {
        NodeDescriptionImpl nodeDescription = new NodeDescriptionImpl();
        return nodeDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EdgeDescription createEdgeDescription() {
        EdgeDescriptionImpl edgeDescription = new EdgeDescriptionImpl();
        return edgeDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NodeStyle createNodeStyle() {
        NodeStyleImpl nodeStyle = new NodeStyleImpl();
        return nodeStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EdgeStyle createEdgeStyle() {
        EdgeStyleImpl edgeStyle = new EdgeStyleImpl();
        return edgeStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LabelEditTool createLabelEditTool() {
        LabelEditToolImpl labelEditTool = new LabelEditToolImpl();
        return labelEditTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DeleteTool createDeleteTool() {
        DeleteToolImpl deleteTool = new DeleteToolImpl();
        return deleteTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NodeTool createNodeTool() {
        NodeToolImpl nodeTool = new NodeToolImpl();
        return nodeTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EdgeTool createEdgeTool() {
        EdgeToolImpl edgeTool = new EdgeToolImpl();
        return edgeTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DropTool createDropTool() {
        DropToolImpl dropTool = new DropToolImpl();
        return dropTool;
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
    public CreateView createCreateView() {
        CreateViewImpl createView = new CreateViewImpl();
        return createView;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DeleteView createDeleteView() {
        DeleteViewImpl deleteView = new DeleteViewImpl();
        return deleteView;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ConditionalNodeStyle createConditionalNodeStyle() {
        ConditionalNodeStyleImpl conditionalNodeStyle = new ConditionalNodeStyleImpl();
        return conditionalNodeStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ConditionalEdgeStyle createConditionalEdgeStyle() {
        ConditionalEdgeStyleImpl conditionalEdgeStyle = new ConditionalEdgeStyleImpl();
        return conditionalEdgeStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public FormDescription createFormDescription() {
        FormDescriptionImpl formDescription = new FormDescriptionImpl();
        return formDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public TextfieldDescription createTextfieldDescription() {
        TextfieldDescriptionImpl textfieldDescription = new TextfieldDescriptionImpl();
        return textfieldDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CheckboxDescription createCheckboxDescription() {
        CheckboxDescriptionImpl checkboxDescription = new CheckboxDescriptionImpl();
        return checkboxDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public SelectDescription createSelectDescription() {
        SelectDescriptionImpl selectDescription = new SelectDescriptionImpl();
        return selectDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public MultiSelectDescription createMultiSelectDescription() {
        MultiSelectDescriptionImpl multiSelectDescription = new MultiSelectDescriptionImpl();
        return multiSelectDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public TextAreaDescription createTextAreaDescription() {
        TextAreaDescriptionImpl textAreaDescription = new TextAreaDescriptionImpl();
        return textAreaDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public RadioDescription createRadioDescription() {
        RadioDescriptionImpl radioDescription = new RadioDescriptionImpl();
        return radioDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public TextfieldDescriptionStyle createTextfieldDescriptionStyle() {
        TextfieldDescriptionStyleImpl textfieldDescriptionStyle = new TextfieldDescriptionStyleImpl();
        return textfieldDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CheckboxDescriptionStyle createCheckboxDescriptionStyle() {
        CheckboxDescriptionStyleImpl checkboxDescriptionStyle = new CheckboxDescriptionStyleImpl();
        return checkboxDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public SelectDescriptionStyle createSelectDescriptionStyle() {
        SelectDescriptionStyleImpl selectDescriptionStyle = new SelectDescriptionStyleImpl();
        return selectDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public MultiSelectDescriptionStyle createMultiSelectDescriptionStyle() {
        MultiSelectDescriptionStyleImpl multiSelectDescriptionStyle = new MultiSelectDescriptionStyleImpl();
        return multiSelectDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public TextareaDescriptionStyle createTextareaDescriptionStyle() {
        TextareaDescriptionStyleImpl textareaDescriptionStyle = new TextareaDescriptionStyleImpl();
        return textareaDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public RadioDescriptionStyle createRadioDescriptionStyle() {
        RadioDescriptionStyleImpl radioDescriptionStyle = new RadioDescriptionStyleImpl();
        return radioDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ArrowStyle createArrowStyleFromString(EDataType eDataType, String initialValue) {
        ArrowStyle result = ArrowStyle.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String convertArrowStyleToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public LineStyle createLineStyleFromString(EDataType eDataType, String initialValue) {
        LineStyle result = LineStyle.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String convertLineStyleToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public SynchronizationPolicy createSynchronizationPolicyFromString(EDataType eDataType, String initialValue) {
        SynchronizationPolicy result = SynchronizationPolicy.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String convertSynchronizationPolicyToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
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
