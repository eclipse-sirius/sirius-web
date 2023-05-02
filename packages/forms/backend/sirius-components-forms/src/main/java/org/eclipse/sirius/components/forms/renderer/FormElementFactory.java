/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.components.forms.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.charts.IChart;
import org.eclipse.sirius.components.charts.barchart.BarChart;
import org.eclipse.sirius.components.charts.barchart.BarChart.Builder;
import org.eclipse.sirius.components.charts.barchart.BarChartEntry;
import org.eclipse.sirius.components.charts.barchart.elements.BarChartElementProps;
import org.eclipse.sirius.components.charts.piechart.PieChart;
import org.eclipse.sirius.components.charts.piechart.PieChartEntry;
import org.eclipse.sirius.components.charts.piechart.elements.PieChartElementProps;
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.Button;
import org.eclipse.sirius.components.forms.ChartWidget;
import org.eclipse.sirius.components.forms.Checkbox;
import org.eclipse.sirius.components.forms.FlexboxContainer;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.Group;
import org.eclipse.sirius.components.forms.Image;
import org.eclipse.sirius.components.forms.LabelWidget;
import org.eclipse.sirius.components.forms.Link;
import org.eclipse.sirius.components.forms.MultiSelect;
import org.eclipse.sirius.components.forms.Page;
import org.eclipse.sirius.components.forms.Radio;
import org.eclipse.sirius.components.forms.RichText;
import org.eclipse.sirius.components.forms.Select;
import org.eclipse.sirius.components.forms.Textarea;
import org.eclipse.sirius.components.forms.Textfield;
import org.eclipse.sirius.components.forms.ToolbarAction;
import org.eclipse.sirius.components.forms.TreeWidget;
import org.eclipse.sirius.components.forms.elements.ButtonElementProps;
import org.eclipse.sirius.components.forms.elements.ChartWidgetElementProps;
import org.eclipse.sirius.components.forms.elements.CheckboxElementProps;
import org.eclipse.sirius.components.forms.elements.FlexboxContainerElementProps;
import org.eclipse.sirius.components.forms.elements.FormElementProps;
import org.eclipse.sirius.components.forms.elements.GroupElementProps;
import org.eclipse.sirius.components.forms.elements.ImageElementProps;
import org.eclipse.sirius.components.forms.elements.LabelWidgetElementProps;
import org.eclipse.sirius.components.forms.elements.LinkElementProps;
import org.eclipse.sirius.components.forms.elements.ListElementProps;
import org.eclipse.sirius.components.forms.elements.MultiSelectElementProps;
import org.eclipse.sirius.components.forms.elements.PageElementProps;
import org.eclipse.sirius.components.forms.elements.RadioElementProps;
import org.eclipse.sirius.components.forms.elements.RichTextElementProps;
import org.eclipse.sirius.components.forms.elements.SelectElementProps;
import org.eclipse.sirius.components.forms.elements.TextareaElementProps;
import org.eclipse.sirius.components.forms.elements.TextfieldElementProps;
import org.eclipse.sirius.components.forms.elements.ToolbarActionElementProps;
import org.eclipse.sirius.components.forms.elements.TreeElementProps;
import org.eclipse.sirius.components.forms.validation.Diagnostic;
import org.eclipse.sirius.components.forms.validation.DiagnosticElementProps;
import org.eclipse.sirius.components.representations.IElementFactory;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Used to instantiate the elements of the form.
 *
 * @author sbegaudeau
 */
public class FormElementFactory implements IElementFactory {
    private final List<IWidgetDescriptor> widgetDescriptors;

    public FormElementFactory(List<IWidgetDescriptor> widgetDescriptors) {
        this.widgetDescriptors = Objects.requireNonNull(widgetDescriptors);
    }

    @Override
    @SuppressWarnings("checkstyle:JavaNCSS")
    public Object instantiateElement(String type, IProps props, List<Object> children) {
        Object object = null;
        if (FormElementProps.TYPE.equals(type) && props instanceof FormElementProps) {
            object = this.instantiateForm((FormElementProps) props, children);
        } else if (PageElementProps.TYPE.equals(type) && props instanceof PageElementProps) {
            object = this.instantiatePage((PageElementProps) props, children);
        } else if (GroupElementProps.TYPE.equals(type) && props instanceof GroupElementProps) {
            object = this.instantiateGroup((GroupElementProps) props, children);
        } else if (CheckboxElementProps.TYPE.equals(type) && props instanceof CheckboxElementProps) {
            object = this.instantiateCheckbox((CheckboxElementProps) props, children);
        } else if (ListElementProps.TYPE.equals(type) && props instanceof ListElementProps) {
            object = this.instantiateList((ListElementProps) props, children);
        } else if (RadioElementProps.TYPE.equals(type) && props instanceof RadioElementProps) {
            object = this.instantiateRadio((RadioElementProps) props, children);
        } else if (SelectElementProps.TYPE.equals(type) && props instanceof SelectElementProps) {
            object = this.instantiateSelect((SelectElementProps) props, children);
        } else if (MultiSelectElementProps.TYPE.equals(type) && props instanceof MultiSelectElementProps) {
            object = this.instantiateMultiSelect((MultiSelectElementProps) props, children);
        } else if (TextareaElementProps.TYPE.equals(type) && props instanceof TextareaElementProps) {
            object = this.instantiateTextarea((TextareaElementProps) props, children);
        } else if (TextfieldElementProps.TYPE.equals(type) && props instanceof TextfieldElementProps) {
            object = this.instantiateTextfield((TextfieldElementProps) props, children);
        } else if (DiagnosticElementProps.TYPE.equals(type) && props instanceof DiagnosticElementProps) {
            object = this.instantiateDiagnostic((DiagnosticElementProps) props, children);
        } else if (LinkElementProps.TYPE.equals(type) && props instanceof LinkElementProps) {
            object = this.instantiateLink((LinkElementProps) props, children);
        } else if (ButtonElementProps.TYPE.equals(type) && props instanceof ButtonElementProps) {
            object = this.instantiateButton((ButtonElementProps) props, children);
        } else if (LabelWidgetElementProps.TYPE.equals(type) && props instanceof LabelWidgetElementProps) {
            object = this.instantiateLabel((LabelWidgetElementProps) props, children);
        } else if (ChartWidgetElementProps.TYPE.equals(type) && props instanceof ChartWidgetElementProps) {
            object = this.instantiateChartWidget((ChartWidgetElementProps) props, children);
        } else if (BarChartElementProps.TYPE.equals(type) && props instanceof BarChartElementProps) {
            object = this.instantiateBarChart((BarChartElementProps) props);
        } else if (PieChartElementProps.TYPE.equals(type) && props instanceof PieChartElementProps) {
            object = this.instantiatePieChart((PieChartElementProps) props);
        } else if (FlexboxContainerElementProps.TYPE.equals(type) && props instanceof FlexboxContainerElementProps) {
            object = this.instantiateFlexboxContainer((FlexboxContainerElementProps) props, children);
        } else if (TreeElementProps.TYPE.equals(type) && props instanceof TreeElementProps) {
            object = this.instantiateTree((TreeElementProps) props, children);
        } else if (ImageElementProps.TYPE.equals(type) && props instanceof ImageElementProps) {
            object = this.instantiateImage((ImageElementProps) props, children);
        } else if (RichTextElementProps.TYPE.equals(type) && props instanceof RichTextElementProps) {
            object = this.instantiateRichText((RichTextElementProps) props, children);
        } else if (ToolbarActionElementProps.TYPE.equals(type) && props instanceof ToolbarActionElementProps) {
            object = this.instantiateToolbarAction((ToolbarActionElementProps) props, children);
        } else {
            for (IWidgetDescriptor widgetDescriptor : this.widgetDescriptors) {
                if (widgetDescriptor.getWidgetType().equals(type) && widgetDescriptor.getInstancePropsClass().isInstance(props)) {
                    Optional<Object> optionalInstance = widgetDescriptor.instanciate(props, children);
                    if (optionalInstance.isPresent()) {
                        object = optionalInstance.get();
                        break;
                    }
                }
            }
        }

        return object;
    }

    private Object instantiateBarChart(BarChartElementProps props) {
        List<BarChartEntry> entries = this.getBarChartEntries(props);
        // @formatter:off
        Builder builder = BarChart.newBarChart(props.getId())
                .descriptionId(props.getDescriptionId())
                .label(props.getLabel())
                .width(props.getWidth())
                .height(props.getHeight())
                .entries(entries);
        // @formatter:on

        if (props.getStyle() != null) {
            builder.style(props.getStyle());
        }
        return builder.build();
    }

    private Object instantiatePieChart(PieChartElementProps props) {
        List<PieChartEntry> entries = this.getPieChartEntries(props);
        // @formatter:off
        org.eclipse.sirius.components.charts.piechart.PieChart.Builder builder = PieChart.newPieChart(props.getId())
                .descriptionId(props.getDescriptionId())
                .label(props.getLabel())
                .entries(entries);
        // @formatter:on

        if (props.getStyle() != null) {
            builder.style(props.getStyle());
        }
        return builder.build();
    }

    private List<BarChartEntry> getBarChartEntries(BarChartElementProps props) {
        List<BarChartEntry> entries = new ArrayList<>();
        List<String> keys = props.getKeys();
        List<Number> values = props.getValues();
        if (values.size() == keys.size()) {
            for (int i = 0; i < values.size(); i++) {
                entries.add(new BarChartEntry(keys.get(i), values.get(i)));
            }
        }
        return entries;
    }

    private List<PieChartEntry> getPieChartEntries(PieChartElementProps props) {
        List<PieChartEntry> entries = new ArrayList<>();
        List<String> keys = props.getKeys();
        List<Number> values = props.getValues();
        if (values.size() == keys.size()) {
            for (int i = 0; i < values.size(); i++) {
                entries.add(new PieChartEntry(keys.get(i), values.get(i)));
            }
        }
        return entries;
    }

    private Form instantiateForm(FormElementProps props, List<Object> children) {
        // @formatter:off
        List<Page> pages = children.stream()
                .filter(Page.class::isInstance)
                .map(Page.class::cast)
                .toList();

        return Form.newForm(props.getId())
                .label(props.getLabel())
                .targetObjectId(props.getTargetObjectId())
                .descriptionId(props.getDescriptionId())
                .pages(pages)
                .build();
        // @formatter:on
    }

    private Page instantiatePage(PageElementProps props, List<Object> children) {
        List<ToolbarAction> toolbarActions = children.stream()
                .filter(ToolbarAction.class::isInstance)
                .map(ToolbarAction.class::cast)
                .toList();

        List<Group> groups = children.stream()
                .filter(Group.class::isInstance)
                .map(Group.class::cast)
                .toList();

        return Page.newPage(props.getId())
                .label(props.getLabel())
                .toolbarActions(toolbarActions)
                .groups(groups)
                .build();
    }

    private Group instantiateGroup(GroupElementProps props, List<Object> children) {
        // @formatter:off
        List<ToolbarAction> toolbarActions = children.stream()
                .filter(ToolbarAction.class::isInstance)
                .map(ToolbarAction.class::cast)
                .toList();

        List<AbstractWidget> widgets = children.stream()
                .filter(c -> c instanceof AbstractWidget && !(c instanceof ToolbarAction))
                .map(AbstractWidget.class::cast)
                .toList();


        return Group.newGroup(props.getId())
                .label(props.getLabel())
                .displayMode(props.getDisplayMode())
                .toolbarActions(toolbarActions)
                .widgets(widgets)
                .build();
        // @formatter:on
    }

    private Checkbox instantiateCheckbox(CheckboxElementProps props, List<Object> children) {
        List<Diagnostic> diagnostics = this.getDiagnosticsFromChildren(children);

        // @formatter:off
        Checkbox.Builder checkboxBuilder = Checkbox.newCheckbox(props.getId())
                .label(props.getLabel())
                .value(props.isValue())
                .newValueHandler(props.getNewValueHandler())
                .diagnostics(diagnostics);

        if (props.getIconURL() != null) {
            checkboxBuilder.iconURL(props.getIconURL());
        }
        if (props.getStyle() != null) {
            checkboxBuilder.style(props.getStyle());
        }

        return checkboxBuilder.build();
        // @formatter:on
    }

    private org.eclipse.sirius.components.forms.List instantiateList(ListElementProps props, List<Object> children) {
        List<Diagnostic> diagnostics = this.getDiagnosticsFromChildren(children);

        // @formatter:off
        org.eclipse.sirius.components.forms.List.Builder listBuilder = org.eclipse.sirius.components.forms.List.newList(props.getId())
                .label(props.getLabel())
                .items(props.getItems())
                .diagnostics(diagnostics);
        // @formatter:on
        if (props.getIconURL() != null) {
            listBuilder.iconURL(props.getIconURL());
        }
        if (props.getStyle() != null) {
            listBuilder.style(props.getStyle());
        }
        return listBuilder.build();
    }

    private Radio instantiateRadio(RadioElementProps props, List<Object> children) {
        List<Diagnostic> diagnostics = this.getDiagnosticsFromChildren(children);

        // @formatter:off
        Radio.Builder radioBuilder = Radio.newRadio(props.getId())
                .label(props.getLabel())
                .options(props.getOptions())
                .newValueHandler(props.getNewValueHandler())
                .diagnostics(diagnostics);

        if (props.getIconURL() != null) {
            radioBuilder.iconURL(props.getIconURL());
        }
        if (props.getStyle() != null) {
            radioBuilder.style(props.getStyle());
        }

        return radioBuilder.build();
        // @formatter:on
    }

    private Select instantiateSelect(SelectElementProps props, List<Object> children) {
        List<Diagnostic> diagnostics = this.getDiagnosticsFromChildren(children);

        // @formatter:off
        Select.Builder selectBuilder = Select.newSelect(props.getId())
                .label(props.getLabel())
                .options(props.getOptions())
                .value(props.getValue())
                .newValueHandler(props.getNewValueHandler())
                .diagnostics(diagnostics);

        if (props.getIconURL() != null) {
            selectBuilder.iconURL(props.getIconURL());
        }
        if (props.getStyle() != null) {
            selectBuilder.style(props.getStyle());
        }

        return selectBuilder.build();
        // @formatter:on
    }

    private MultiSelect instantiateMultiSelect(MultiSelectElementProps props, List<Object> children) {
        List<Diagnostic> diagnostics = this.getDiagnosticsFromChildren(children);

        // @formatter:off
        MultiSelect.Builder multiSelectBuilder = MultiSelect.newMultiSelect(props.getId())
                .label(props.getLabel())
                .options(props.getOptions())
                .values(props.getValues())
                .newValuesHandler(props.getNewValuesHandler())
                .diagnostics(diagnostics);

        if (props.getIconURL() != null) {
            multiSelectBuilder.iconURL(props.getIconURL());
        }
        if (props.getStyle() != null) {
            multiSelectBuilder.style(props.getStyle());
        }

        return multiSelectBuilder.build();
        // @formatter:on
    }

    private Textarea instantiateTextarea(TextareaElementProps props, List<Object> children) {
        List<Diagnostic> diagnostics = this.getDiagnosticsFromChildren(children);

        // @formatter:off
        Textarea.Builder textareaBuilder = Textarea.newTextarea(props.getId())
                .label(props.getLabel())
                .value(props.getValue())
                .newValueHandler(props.getNewValueHandler())
                .diagnostics(diagnostics);

        if (props.getCompletionProposalsProvider() != null) {
            textareaBuilder.completionProposalsProvider(props.getCompletionProposalsProvider());
        }
        if (props.getIconURL() != null) {
            textareaBuilder.iconURL(props.getIconURL());
        }
        if (props.getStyle() != null) {
            textareaBuilder.style(props.getStyle());
        }

        return textareaBuilder.build();
        // @formatter:on
    }

    private Textfield instantiateTextfield(TextfieldElementProps props, List<Object> children) {
        List<Diagnostic> diagnostics = this.getDiagnosticsFromChildren(children);

        // @formatter:off
        Textfield.Builder textfieldBuilder = Textfield.newTextfield(props.getId())
                .label(props.getLabel())
                .value(props.getValue())
                .newValueHandler(props.getNewValueHandler())
                .diagnostics(diagnostics);
        // @formatter:on

        if (props.getCompletionProposalsProvider() != null) {
            textfieldBuilder.completionProposalsProvider(props.getCompletionProposalsProvider());
        }
        if (props.getIconURL() != null) {
            textfieldBuilder.iconURL(props.getIconURL());
        }
        if (props.getStyle() != null) {
            textfieldBuilder.style(props.getStyle());
        }

        return textfieldBuilder.build();
    }

    private Object instantiateDiagnostic(DiagnosticElementProps props, List<Object> children) {
        // @formatter:off
        return Diagnostic.newDiagnostic(props.getId())
                .kind(props.getKind())
                .message(props.getMessage())
                .build();
        // @formatter:on
    }

    private Link instantiateLink(LinkElementProps props, List<Object> children) {
        List<Diagnostic> diagnostics = this.getDiagnosticsFromChildren(children);

        // @formatter:off
        Link.Builder linkbuilder = Link.newLink(props.getId())
                .label(props.getLabel())
                .url(props.getUrl())
                .diagnostics(diagnostics);
        // @formatter:on

        if (props.getStyle() != null) {
            linkbuilder.style(props.getStyle());
        }
        if (props.getIconURL() != null) {
            linkbuilder.iconURL(props.getIconURL());
        }
        return linkbuilder.build();
    }

    private Button instantiateButton(ButtonElementProps props, List<Object> children) {
        List<Diagnostic> diagnostics = this.getDiagnosticsFromChildren(children);

        // @formatter:off
        Button.Builder buttonBuilder = Button.newButton(props.getId())
                .label(props.getLabel())
                .pushButtonHandler(props.getPushButtonHandler())
                .diagnostics(diagnostics);
        // @formatter:on
        if (props.getIconURL() != null) {
            buttonBuilder.iconURL(props.getIconURL());
        }
        if (props.getButtonLabel() != null) {
            buttonBuilder.buttonLabel(props.getButtonLabel());
        }
        if (props.getImageURL() != null) {
            buttonBuilder.imageURL(props.getImageURL());
        }
        if (props.getStyle() != null) {
            buttonBuilder.style(props.getStyle());
        }

        return buttonBuilder.build();
    }

    private ToolbarAction instantiateToolbarAction(ToolbarActionElementProps props, List<Object> children) {
        List<Diagnostic> diagnostics = this.getDiagnosticsFromChildren(children);

        // @formatter:off
        ToolbarAction.Builder buttonBuilder = ToolbarAction.newToolbarAction(props.getId())
                .label(props.getLabel())
                .pushButtonHandler(props.getPushButtonHandler())
                .diagnostics(diagnostics);
        // @formatter:on
        if (props.getIconURL() != null) {
            buttonBuilder.iconURL(props.getIconURL());
        }
        if (props.getToolbarActionLabel() != null) {
            buttonBuilder.buttonLabel(props.getToolbarActionLabel());
        }
        if (props.getImageURL() != null) {
            buttonBuilder.imageURL(props.getImageURL());
        }
        if (props.getStyle() != null) {
            buttonBuilder.style(props.getStyle());
        }

        return buttonBuilder.build();
    }

    private LabelWidget instantiateLabel(LabelWidgetElementProps props, List<Object> children) {
        List<Diagnostic> diagnostics = this.getDiagnosticsFromChildren(children);

        // @formatter:off
        LabelWidget.Builder labelBuilder = LabelWidget.newLabelWidget(props.getId())
                .label(props.getLabel())
                .value(props.getValue())
                .diagnostics(diagnostics);

        if (props.getStyle() != null) {
            labelBuilder.style(props.getStyle());
        }

        return labelBuilder.build();
        // @formatter:on
    }

    private Object instantiateChartWidget(ChartWidgetElementProps props, List<Object> children) {
        List<Diagnostic> diagnostics = this.getDiagnosticsFromChildren(children);
        // @formatter:off
        var chart = children.stream()
                .filter(IChart.class::isInstance)
                .map(IChart.class::cast)
                .findFirst()
                .orElse(null);

        ChartWidget.Builder chartBuilder = ChartWidget.newChartWidget(props.getId())
                .label(props.getLabel())
                .chart(chart)
                .diagnostics(diagnostics);
        // @formatter:on
        if (props.getIconURL() != null) {
            chartBuilder.iconURL(props.getIconURL());
        }
        return chartBuilder.build();
    }

    private FlexboxContainer instantiateFlexboxContainer(FlexboxContainerElementProps props, List<Object> children) {
        List<Diagnostic> diagnostics = this.getDiagnosticsFromChildren(children);

        // @formatter:off
        List<AbstractWidget> widgets = children.stream()
                .filter(AbstractWidget.class::isInstance)
                .map(AbstractWidget.class::cast)
                .toList();

        return FlexboxContainer.newFlexboxContainer(props.getId())
                .label(props.getLabel())
                .flexDirection(props.getFlexDirection().toString())
                .flexWrap("wrap")
                .flexGrow(1)
                .children(widgets)
                .diagnostics(diagnostics)
                .build();
        // @formatter:on
    }

    private TreeWidget instantiateTree(TreeElementProps props, List<Object> children) {
        List<Diagnostic> diagnostics = this.getDiagnosticsFromChildren(children);

        // @formatter:off
        return TreeWidget.newTreeWidget(props.getId())
                .label(props.getLabel())
                .iconURL(props.getIconURL())
                .nodes(props.getNodes())
                .expandedNodesIds(props.getExpandedNodesIds())
                .diagnostics(diagnostics)
                .build();
        // @formatter:on
    }

    private Image instantiateImage(ImageElementProps props, List<Object> children) {
        List<Diagnostic> diagnostics = this.getDiagnosticsFromChildren(children);

        // @formatter:off
        Image.Builder imagebuilder = Image.newImage(props.getId())
                .label(props.getLabel())
                .url(props.getUrl())
                .diagnostics(diagnostics);
        // @formatter:on

        if (props.getIconURL() != null) {
            imagebuilder.iconURL(props.getIconURL());
        }
        if (props.getMaxWidth() != null) {
            imagebuilder.maxWidth(props.getMaxWidth());
        }
        return imagebuilder.build();
    }

    private RichText instantiateRichText(RichTextElementProps props, List<Object> children) {
        List<Diagnostic> diagnostics = this.getDiagnosticsFromChildren(children);

        // @formatter:off
        RichText.Builder builder = RichText.newRichText(props.getId())
                .label(props.getLabel())
                .value(props.getValue())
                .newValueHandler(props.getNewValueHandler())
                .diagnostics(diagnostics);
        if (props.getIconURL() != null) {
            builder.iconURL(props.getIconURL());
        }
        return builder.build();
        // @formatter:on
    }

    private List<Diagnostic> getDiagnosticsFromChildren(List<Object> children) {
        // @formatter:off
        return children.stream()
                .filter(Diagnostic.class::isInstance)
                .map(Diagnostic.class::cast)
                .toList();
        // @formatter:on
    }

}
