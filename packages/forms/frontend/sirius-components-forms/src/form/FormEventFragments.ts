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

import { WidgetContribution } from './Form.types';

export const commonFields = `
  fragment commonFields on Widget {
    id
    __typename
    diagnostics {
      id
      kind
      message
    }
    hasHelpText
    readOnly
  }
`;

export const widgetFields = (contributions: Array<WidgetContribution>) => `
  ${commonFields}
  
  fragment textfieldFields on Textfield {
    label
    iconURL
    stringValue: value
    supportsCompletion
    style {
      backgroundColor
      foregroundColor
      fontSize
      italic
      bold
      underline
      strikeThrough
    }
  }

  fragment textareaFields on Textarea {
    label
    iconURL
    stringValue: value
    supportsCompletion
    style {
      backgroundColor
      foregroundColor
      fontSize
      italic
      bold
      underline
      strikeThrough
    }
  }

  fragment checkboxFields on Checkbox {
    label
    iconURL
    booleanValue: value
    style {
      color
      labelPlacement
    }
  }

  fragment selectFields on Select {
    label
    iconURL
    value
    options {
      id
      label
      iconURL
    }
    style {
      backgroundColor
      foregroundColor
      fontSize
      italic
      bold
      underline
      strikeThrough
    }
  }

  fragment multiSelectFields on MultiSelect {
    label
    iconURL
    values
    options {
      id
      label
      iconURL
    }
    style {
      backgroundColor
      foregroundColor
      fontSize
      italic
      bold
      underline
      strikeThrough
    }
  }

  fragment radioFields on Radio {
    label
    iconURL
    options {
      id
      label
      selected
    }
    style {
      color
      fontSize
      italic
      bold
      underline
      strikeThrough
    }
  }

  fragment listFields on List {
    label
    iconURL
    items {
      id
      label
      kind
      iconURL
      deletable
    }
    style {
      color
      fontSize
      italic
      bold
      underline
      strikeThrough
    }
  }

  fragment linkFields on Link {
    label
    iconURL
    url
    style {
      color
      fontSize
      italic
      bold
      underline
      strikeThrough
    }
  }

  fragment buttonFields on Button {
    label
    buttonLabel
    imageURL
    style {
      backgroundColor
      foregroundColor
      fontSize
      italic
      bold
      underline
      strikeThrough
    }
  }

  fragment splitButtonFields on SplitButton {
    label
    actions {
      ...commonFields
      ...buttonFields
    }
  }

  fragment toolbarActionFields on ToolbarAction {
    label
    buttonLabel
    imageURL
    style {
      backgroundColor
      foregroundColor
      fontSize
      italic
      bold
      underline
      strikeThrough
    }
  }

  fragment chartWidgetFields on ChartWidget {
    label
    chart {
      ... on BarChart {
        metadata {
          label
          kind
        }
        label
        entries {
          key
          value
        }
        style {
          barsColor
          fontSize
          italic
          bold
          underline
          strikeThrough
        }
        width
        height
      }
      ... on PieChart {
        metadata {
          label
          kind
        }
        label
        entries {
          key
          value
        }
        style {
          colors
          strokeColor
          strokeWidth
          fontSize
          italic
          bold
          underline
          strikeThrough
        }
      }
    }
  } 

  fragment labelWidgetFields on LabelWidget {
    label
    stringValue: value
    style {
      color
      fontSize
      italic
      bold
      underline
      strikeThrough
    }
  }

  fragment treeWidgetFields on TreeWidget {
    label
    iconURL
    expandedNodesIds
    nodes {
      id
      parentId
      label
      kind
      iconURL
      endIconsURL
      selectable
      checkable
      value
    }
  }

  fragment imageFields on Image {
    label
    iconURL
    url
    maxWidth
  }

  fragment richTextFields on RichText {
    label
    iconURL
    stringValue: value
  }

  fragment sliderFields on Slider {
    label
    iconURL
    minValue
    maxValue
    currentValue
  }
  
  fragment dateTimeFields on DateTime {
    label
    iconURL
    stringValue
    type
    style {
      backgroundColor
      foregroundColor
      italic
      bold
    }
  }

  ${contributions.map(
    (widget) =>
      `
    fragment ${widget.name.toLowerCase()}Fields on ${widget.name} {
      ${widget.fields}
    }
    `
  )}

  fragment widgetFields on Widget {
    ...commonFields
    ... on Textfield {
      ...textfieldFields
    }
    ... on Textarea {
      ...textareaFields
    }
    ... on Checkbox {
      ...checkboxFields
    }
    ... on Select {
      ...selectFields
    }
    ... on MultiSelect {
      ...multiSelectFields
    }
    ... on Radio {
      ...radioFields
    }
    ... on List {
      ...listFields
    }
    ... on Link {
      ...linkFields
    }
    ... on Button {
      ...buttonFields
    }
    ... on SplitButton {
      ...splitButtonFields
    }
    ... on ToolbarAction {
      ...toolbarActionFields
    }
    ... on LabelWidget {
      ...labelWidgetFields
    }
    ... on ChartWidget {
      ...chartWidgetFields
    }
    ... on TreeWidget {
      ...treeWidgetFields
    }
    ... on Image {
      ...imageFields
    }
    ... on RichText {
      ...richTextFields
    }
    ... on Slider {
      ...sliderFields
    }
    ... on DateTime {
      ...dateTimeFields
    }

    ${contributions.map(
      (widget) =>
        `
      ... on ${widget.name} {
        ...${widget.name.toLowerCase()}Fields
      }
      `
    )}
  }
`;

export const flexboxContainerFields = (contributions: Array<WidgetContribution>) => `
  ${commonFields}
  ${widgetFields(contributions)}
  fragment flexboxContainerFields on FlexboxContainer {
    ...commonFields
    label
    flexDirection
    flexWrap
    flexGrow
    children {
      ...widgetFields
      ... on FlexboxContainer {
        ...commonFields
        label
        flexDirection
        flexWrap
        flexGrow
        children {
          ...widgetFields
          ... on FlexboxContainer {
            ...commonFields
            label
            flexDirection
            flexWrap
            flexGrow
            children {
              ...widgetFields
            }
            borderStyle {
              color
              lineStyle
              size
              radius
            }
          }
        }
        borderStyle {
          color
          lineStyle
          size
          radius
        }
      }
    }
    borderStyle {
      color
      lineStyle
      size
      radius
    }
  }
`;

export const formRefreshedEventPayloadFragment = (contributions: Array<WidgetContribution>) => `
  ${widgetFields(contributions)}
  ${flexboxContainerFields(contributions)}
  fragment formRefreshedEventPayloadFragment on FormRefreshedEventPayload {
    id
    form {
      id
      metadata {
        label
      }
      pages {
        id
        label
        groups {
          id
          label
          displayMode
          toolbarActions {
            ...commonFields
            ...toolbarActionFields
          }
          borderStyle {
            color
            lineStyle
            size
            radius
          }
          widgets {
            ...widgetFields
            ... on FlexboxContainer {
              ...flexboxContainerFields
            }
          }
        }
        toolbarActions {
            ...commonFields
            ...toolbarActionFields
        }
      }
    }
  }
`;
