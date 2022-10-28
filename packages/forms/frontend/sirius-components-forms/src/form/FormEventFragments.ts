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

export const subscribersUpdatedEventPayloadFragment = `
  fragment subscribersUpdatedEventPayloadFragment on SubscribersUpdatedEventPayload {
    id
    subscribers {
      username
    }
  }
`;

export const widgetSubscriptionsUpdatedEventPayloadFragment = `
  fragment widgetSubscriptionsUpdatedEventPayloadFragment on WidgetSubscriptionsUpdatedEventPayload {
    id
    widgetSubscriptions {
      widgetId
      subscribers {
        username
      }
    }
  }
`;

export const formRefreshedEventPayloadFragment = `
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
          buttons {
            ...widgetFields
          }
          widgets {
            ...widgetFields
            ... on FlexboxContainer {
              ...flexboxContainerFields
            }
          }
        }
      }
    }
  }

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
    ... on LabelWidget {
      ...labelFields
    }
    ... on ChartWidget {
      ...chartWidgetFields
    }
    ... on TreeWidget {
      ...treeWidgetFields
    }
  }

  fragment commonFields on Widget {
    id
    __typename
    diagnostics {
      id
      kind
      message
    }
  }

  fragment textfieldFields on Textfield {
    label
    iconURL
    stringValue: value
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
    }
  }

  fragment selectFields on Select {
    label
    iconURL
    value
    options {
      id
      label
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
      imageURL
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
      }
      ... on PieChart {
        metadata {
          label
          kind
        }
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
  
  fragment labelFields on LabelWidget {
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
          }
        }
      }
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
      imageURL
      selectable
    }
  }
`;
