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
import org.eclipse.sirius.components.view.BarChartDescription;
import org.eclipse.sirius.components.view.BarChartDescriptionStyle;
import org.eclipse.sirius.components.view.ButtonDescription;
import org.eclipse.sirius.components.view.ButtonDescriptionStyle;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.CheckboxDescription;
import org.eclipse.sirius.components.view.CheckboxDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalBarChartDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalButtonDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalCheckboxDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalEdgeStyle;
import org.eclipse.sirius.components.view.ConditionalLabelDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalLinkDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalListDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalMultiSelectDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalNodeStyle;
import org.eclipse.sirius.components.view.ConditionalPieChartDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalRadioDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalSelectDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalTextareaDescriptionStyle;
import org.eclipse.sirius.components.view.ConditionalTextfieldDescriptionStyle;
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
import org.eclipse.sirius.components.view.FlexDirection;
import org.eclipse.sirius.components.view.FlexboxContainerDescription;
import org.eclipse.sirius.components.view.FormDescription;
import org.eclipse.sirius.components.view.FreeFormLayoutStrategyDescription;
import org.eclipse.sirius.components.view.IconLabelNodeStyleDescription;
import org.eclipse.sirius.components.view.ImageDescription;
import org.eclipse.sirius.components.view.ImageNodeStyleDescription;
import org.eclipse.sirius.components.view.LabelDescription;
import org.eclipse.sirius.components.view.LabelDescriptionStyle;
import org.eclipse.sirius.components.view.LabelEditTool;
import org.eclipse.sirius.components.view.LayoutDirection;
import org.eclipse.sirius.components.view.LineStyle;
import org.eclipse.sirius.components.view.LinkDescription;
import org.eclipse.sirius.components.view.LinkDescriptionStyle;
import org.eclipse.sirius.components.view.ListDescription;
import org.eclipse.sirius.components.view.ListDescriptionStyle;
import org.eclipse.sirius.components.view.ListLayoutStrategyDescription;
import org.eclipse.sirius.components.view.MultiSelectDescription;
import org.eclipse.sirius.components.view.MultiSelectDescriptionStyle;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.NodeTool;
import org.eclipse.sirius.components.view.PieChartDescription;
import org.eclipse.sirius.components.view.PieChartDescriptionStyle;
import org.eclipse.sirius.components.view.RadioDescription;
import org.eclipse.sirius.components.view.RadioDescriptionStyle;
import org.eclipse.sirius.components.view.RectangularNodeStyleDescription;
import org.eclipse.sirius.components.view.RichTextDescription;
import org.eclipse.sirius.components.view.SelectDescription;
import org.eclipse.sirius.components.view.SelectDescriptionStyle;
import org.eclipse.sirius.components.view.SetValue;
import org.eclipse.sirius.components.view.SourceEdgeEndReconnectionTool;
import org.eclipse.sirius.components.view.SynchronizationPolicy;
import org.eclipse.sirius.components.view.TargetEdgeEndReconnectionTool;
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
        case ViewPackage.RECTANGULAR_NODE_STYLE_DESCRIPTION:
            return this.createRectangularNodeStyleDescription();
        case ViewPackage.IMAGE_NODE_STYLE_DESCRIPTION:
            return this.createImageNodeStyleDescription();
        case ViewPackage.ICON_LABEL_NODE_STYLE_DESCRIPTION:
            return this.createIconLabelNodeStyleDescription();
        case ViewPackage.FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION:
            return this.createFreeFormLayoutStrategyDescription();
        case ViewPackage.LIST_LAYOUT_STRATEGY_DESCRIPTION:
            return this.createListLayoutStrategyDescription();
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
        case ViewPackage.SOURCE_EDGE_END_RECONNECTION_TOOL:
            return this.createSourceEdgeEndReconnectionTool();
        case ViewPackage.TARGET_EDGE_END_RECONNECTION_TOOL:
            return this.createTargetEdgeEndReconnectionTool();
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
        case ViewPackage.RICH_TEXT_DESCRIPTION:
            return this.createRichTextDescription();
        case ViewPackage.RADIO_DESCRIPTION:
            return this.createRadioDescription();
        case ViewPackage.BAR_CHART_DESCRIPTION:
            return this.createBarChartDescription();
        case ViewPackage.PIE_CHART_DESCRIPTION:
            return this.createPieChartDescription();
        case ViewPackage.FLEXBOX_CONTAINER_DESCRIPTION:
            return this.createFlexboxContainerDescription();
        case ViewPackage.BUTTON_DESCRIPTION:
            return this.createButtonDescription();
        case ViewPackage.IMAGE_DESCRIPTION:
            return this.createImageDescription();
        case ViewPackage.TEXTFIELD_DESCRIPTION_STYLE:
            return this.createTextfieldDescriptionStyle();
        case ViewPackage.CONDITIONAL_TEXTFIELD_DESCRIPTION_STYLE:
            return this.createConditionalTextfieldDescriptionStyle();
        case ViewPackage.CHECKBOX_DESCRIPTION_STYLE:
            return this.createCheckboxDescriptionStyle();
        case ViewPackage.CONDITIONAL_CHECKBOX_DESCRIPTION_STYLE:
            return this.createConditionalCheckboxDescriptionStyle();
        case ViewPackage.SELECT_DESCRIPTION_STYLE:
            return this.createSelectDescriptionStyle();
        case ViewPackage.CONDITIONAL_SELECT_DESCRIPTION_STYLE:
            return this.createConditionalSelectDescriptionStyle();
        case ViewPackage.MULTI_SELECT_DESCRIPTION_STYLE:
            return this.createMultiSelectDescriptionStyle();
        case ViewPackage.CONDITIONAL_MULTI_SELECT_DESCRIPTION_STYLE:
            return this.createConditionalMultiSelectDescriptionStyle();
        case ViewPackage.TEXTAREA_DESCRIPTION_STYLE:
            return this.createTextareaDescriptionStyle();
        case ViewPackage.CONDITIONAL_TEXTAREA_DESCRIPTION_STYLE:
            return this.createConditionalTextareaDescriptionStyle();
        case ViewPackage.RADIO_DESCRIPTION_STYLE:
            return this.createRadioDescriptionStyle();
        case ViewPackage.CONDITIONAL_RADIO_DESCRIPTION_STYLE:
            return this.createConditionalRadioDescriptionStyle();
        case ViewPackage.BUTTON_DESCRIPTION_STYLE:
            return this.createButtonDescriptionStyle();
        case ViewPackage.CONDITIONAL_BUTTON_DESCRIPTION_STYLE:
            return this.createConditionalButtonDescriptionStyle();
        case ViewPackage.BAR_CHART_DESCRIPTION_STYLE:
            return this.createBarChartDescriptionStyle();
        case ViewPackage.CONDITIONAL_BAR_CHART_DESCRIPTION_STYLE:
            return this.createConditionalBarChartDescriptionStyle();
        case ViewPackage.PIE_CHART_DESCRIPTION_STYLE:
            return this.createPieChartDescriptionStyle();
        case ViewPackage.CONDITIONAL_PIE_CHART_DESCRIPTION_STYLE:
            return this.createConditionalPieChartDescriptionStyle();
        case ViewPackage.LABEL_DESCRIPTION:
            return this.createLabelDescription();
        case ViewPackage.LABEL_DESCRIPTION_STYLE:
            return this.createLabelDescriptionStyle();
        case ViewPackage.CONDITIONAL_LABEL_DESCRIPTION_STYLE:
            return this.createConditionalLabelDescriptionStyle();
        case ViewPackage.LINK_DESCRIPTION:
            return this.createLinkDescription();
        case ViewPackage.LINK_DESCRIPTION_STYLE:
            return this.createLinkDescriptionStyle();
        case ViewPackage.CONDITIONAL_LINK_DESCRIPTION_STYLE:
            return this.createConditionalLinkDescriptionStyle();
        case ViewPackage.LIST_DESCRIPTION:
            return this.createListDescription();
        case ViewPackage.LIST_DESCRIPTION_STYLE:
            return this.createListDescriptionStyle();
        case ViewPackage.CONDITIONAL_LIST_DESCRIPTION_STYLE:
            return this.createConditionalListDescriptionStyle();
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
        case ViewPackage.LAYOUT_DIRECTION:
            return this.createLayoutDirectionFromString(eDataType, initialValue);
        case ViewPackage.ARROW_STYLE:
            return this.createArrowStyleFromString(eDataType, initialValue);
        case ViewPackage.LINE_STYLE:
            return this.createLineStyleFromString(eDataType, initialValue);
        case ViewPackage.SYNCHRONIZATION_POLICY:
            return this.createSynchronizationPolicyFromString(eDataType, initialValue);
        case ViewPackage.FLEX_DIRECTION:
            return this.createFlexDirectionFromString(eDataType, initialValue);
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
        case ViewPackage.LAYOUT_DIRECTION:
            return this.convertLayoutDirectionToString(eDataType, instanceValue);
        case ViewPackage.ARROW_STYLE:
            return this.convertArrowStyleToString(eDataType, instanceValue);
        case ViewPackage.LINE_STYLE:
            return this.convertLineStyleToString(eDataType, instanceValue);
        case ViewPackage.SYNCHRONIZATION_POLICY:
            return this.convertSynchronizationPolicyToString(eDataType, instanceValue);
        case ViewPackage.FLEX_DIRECTION:
            return this.convertFlexDirectionToString(eDataType, instanceValue);
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
    public RectangularNodeStyleDescription createRectangularNodeStyleDescription() {
        RectangularNodeStyleDescriptionImpl rectangularNodeStyleDescription = new RectangularNodeStyleDescriptionImpl();
        return rectangularNodeStyleDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ImageNodeStyleDescription createImageNodeStyleDescription() {
        ImageNodeStyleDescriptionImpl imageNodeStyleDescription = new ImageNodeStyleDescriptionImpl();
        return imageNodeStyleDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public IconLabelNodeStyleDescription createIconLabelNodeStyleDescription() {
        IconLabelNodeStyleDescriptionImpl iconLabelNodeStyleDescription = new IconLabelNodeStyleDescriptionImpl();
        return iconLabelNodeStyleDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public FreeFormLayoutStrategyDescription createFreeFormLayoutStrategyDescription() {
        FreeFormLayoutStrategyDescriptionImpl freeFormLayoutStrategyDescription = new FreeFormLayoutStrategyDescriptionImpl();
        return freeFormLayoutStrategyDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ListLayoutStrategyDescription createListLayoutStrategyDescription() {
        ListLayoutStrategyDescriptionImpl listLayoutStrategyDescription = new ListLayoutStrategyDescriptionImpl();
        return listLayoutStrategyDescription;
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
    public SourceEdgeEndReconnectionTool createSourceEdgeEndReconnectionTool() {
        SourceEdgeEndReconnectionToolImpl sourceEdgeEndReconnectionTool = new SourceEdgeEndReconnectionToolImpl();
        return sourceEdgeEndReconnectionTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public TargetEdgeEndReconnectionTool createTargetEdgeEndReconnectionTool() {
        TargetEdgeEndReconnectionToolImpl targetEdgeEndReconnectionTool = new TargetEdgeEndReconnectionToolImpl();
        return targetEdgeEndReconnectionTool;
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
    public RichTextDescription createRichTextDescription() {
        RichTextDescriptionImpl richTextDescription = new RichTextDescriptionImpl();
        return richTextDescription;
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
    public ConditionalTextfieldDescriptionStyle createConditionalTextfieldDescriptionStyle() {
        ConditionalTextfieldDescriptionStyleImpl conditionalTextfieldDescriptionStyle = new ConditionalTextfieldDescriptionStyleImpl();
        return conditionalTextfieldDescriptionStyle;
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
    public ConditionalCheckboxDescriptionStyle createConditionalCheckboxDescriptionStyle() {
        ConditionalCheckboxDescriptionStyleImpl conditionalCheckboxDescriptionStyle = new ConditionalCheckboxDescriptionStyleImpl();
        return conditionalCheckboxDescriptionStyle;
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
    public ConditionalSelectDescriptionStyle createConditionalSelectDescriptionStyle() {
        ConditionalSelectDescriptionStyleImpl conditionalSelectDescriptionStyle = new ConditionalSelectDescriptionStyleImpl();
        return conditionalSelectDescriptionStyle;
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
    public ConditionalMultiSelectDescriptionStyle createConditionalMultiSelectDescriptionStyle() {
        ConditionalMultiSelectDescriptionStyleImpl conditionalMultiSelectDescriptionStyle = new ConditionalMultiSelectDescriptionStyleImpl();
        return conditionalMultiSelectDescriptionStyle;
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
    public ConditionalTextareaDescriptionStyle createConditionalTextareaDescriptionStyle() {
        ConditionalTextareaDescriptionStyleImpl conditionalTextareaDescriptionStyle = new ConditionalTextareaDescriptionStyleImpl();
        return conditionalTextareaDescriptionStyle;
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
    @Override
    public ConditionalRadioDescriptionStyle createConditionalRadioDescriptionStyle() {
        ConditionalRadioDescriptionStyleImpl conditionalRadioDescriptionStyle = new ConditionalRadioDescriptionStyleImpl();
        return conditionalRadioDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ButtonDescriptionStyle createButtonDescriptionStyle() {
        ButtonDescriptionStyleImpl buttonDescriptionStyle = new ButtonDescriptionStyleImpl();
        return buttonDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ConditionalButtonDescriptionStyle createConditionalButtonDescriptionStyle() {
        ConditionalButtonDescriptionStyleImpl conditionalButtonDescriptionStyle = new ConditionalButtonDescriptionStyleImpl();
        return conditionalButtonDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public BarChartDescriptionStyle createBarChartDescriptionStyle() {
        BarChartDescriptionStyleImpl barChartDescriptionStyle = new BarChartDescriptionStyleImpl();
        return barChartDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ConditionalBarChartDescriptionStyle createConditionalBarChartDescriptionStyle() {
        ConditionalBarChartDescriptionStyleImpl conditionalBarChartDescriptionStyle = new ConditionalBarChartDescriptionStyleImpl();
        return conditionalBarChartDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public PieChartDescriptionStyle createPieChartDescriptionStyle() {
        PieChartDescriptionStyleImpl pieChartDescriptionStyle = new PieChartDescriptionStyleImpl();
        return pieChartDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ConditionalPieChartDescriptionStyle createConditionalPieChartDescriptionStyle() {
        ConditionalPieChartDescriptionStyleImpl conditionalPieChartDescriptionStyle = new ConditionalPieChartDescriptionStyleImpl();
        return conditionalPieChartDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LabelDescription createLabelDescription() {
        LabelDescriptionImpl labelDescription = new LabelDescriptionImpl();
        return labelDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LabelDescriptionStyle createLabelDescriptionStyle() {
        LabelDescriptionStyleImpl labelDescriptionStyle = new LabelDescriptionStyleImpl();
        return labelDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ConditionalLabelDescriptionStyle createConditionalLabelDescriptionStyle() {
        ConditionalLabelDescriptionStyleImpl conditionalLabelDescriptionStyle = new ConditionalLabelDescriptionStyleImpl();
        return conditionalLabelDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LinkDescription createLinkDescription() {
        LinkDescriptionImpl linkDescription = new LinkDescriptionImpl();
        return linkDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LinkDescriptionStyle createLinkDescriptionStyle() {
        LinkDescriptionStyleImpl linkDescriptionStyle = new LinkDescriptionStyleImpl();
        return linkDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ConditionalLinkDescriptionStyle createConditionalLinkDescriptionStyle() {
        ConditionalLinkDescriptionStyleImpl conditionalLinkDescriptionStyle = new ConditionalLinkDescriptionStyleImpl();
        return conditionalLinkDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ListDescription createListDescription() {
        ListDescriptionImpl listDescription = new ListDescriptionImpl();
        return listDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ListDescriptionStyle createListDescriptionStyle() {
        ListDescriptionStyleImpl listDescriptionStyle = new ListDescriptionStyleImpl();
        return listDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ConditionalListDescriptionStyle createConditionalListDescriptionStyle() {
        ConditionalListDescriptionStyleImpl conditionalListDescriptionStyle = new ConditionalListDescriptionStyleImpl();
        return conditionalListDescriptionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public LayoutDirection createLayoutDirectionFromString(EDataType eDataType, String initialValue) {
        LayoutDirection result = LayoutDirection.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String convertLayoutDirectionToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public BarChartDescription createBarChartDescription() {
        BarChartDescriptionImpl barChartDescription = new BarChartDescriptionImpl();
        return barChartDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public PieChartDescription createPieChartDescription() {
        PieChartDescriptionImpl pieChartDescription = new PieChartDescriptionImpl();
        return pieChartDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public FlexboxContainerDescription createFlexboxContainerDescription() {
        FlexboxContainerDescriptionImpl flexboxContainerDescription = new FlexboxContainerDescriptionImpl();
        return flexboxContainerDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ButtonDescription createButtonDescription() {
        ButtonDescriptionImpl buttonDescription = new ButtonDescriptionImpl();
        return buttonDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ImageDescription createImageDescription() {
        ImageDescriptionImpl imageDescription = new ImageDescriptionImpl();
        return imageDescription;
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
    public FlexDirection createFlexDirectionFromString(EDataType eDataType, String initialValue) {
        FlexDirection result = FlexDirection.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String convertFlexDirectionToString(EDataType eDataType, Object instanceValue) {
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
