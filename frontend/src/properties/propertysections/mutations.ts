/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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

export const editTextfieldMutation = gql`
  mutation editTextfield($input: EditTextfieldInput!) {
    editTextfield(input: $input) {
      __typename
      ... on EditTextfieldSuccessPayload {
        status
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`.loc.source.body;

export const updateWidgetFocusMutation = gql`
  mutation updateWidgetFocus($input: UpdateWidgetFocusInput!) {
    updateWidgetFocus(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`.loc.source.body;

export const editCheckboxMutation = gql`
  mutation editCheckbox($input: EditCheckboxInput!) {
    editCheckbox(input: $input) {
      __typename
      ... on EditCheckboxSuccessPayload {
        status
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`.loc.source.body;

export const editRadioMutation = gql`
  mutation editRadio($input: EditRadioInput!) {
    editRadio(input: $input) {
      __typename
      ... on EditRadioSuccessPayload {
        status
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`.loc.source.body;

export const editSelectMutation = gql`
  mutation editSelect($input: EditSelectInput!) {
    editSelect(input: $input) {
      __typename
      ... on EditSelectSuccessPayload {
        status
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`.loc.source.body;
