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
import gql from 'graphql-tag';

export const subscribersUpdatedEventPayloadFragment = gql`
  fragment subscribersUpdatedEventPayloadFragment on SubscribersUpdatedEventPayload {
    id
    subscribers {
      username
    }
  }
`;

export const widgetSubscriptionsUpdatedEventPayloadFragment = gql`
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

export const formRefreshedEventPayloadFragment = gql`
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
    ... on ChartWidget {
      ...chartWidgetFields
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
    booleanValue: value
    style {
      color
    }
  }

  fragment selectFields on Select {
    label
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
    items {
      id
      label
      kind
      imageURL
      deletable
    }
  }

  fragment linkFields on Link {
    label
    url
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
      }
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
`;
