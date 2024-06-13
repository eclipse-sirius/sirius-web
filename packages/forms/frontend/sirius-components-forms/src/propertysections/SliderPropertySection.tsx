/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import { useMutation } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import Slider from '@material-ui/core/Slider';
import gql from 'graphql-tag';
import { useEffect, useState } from 'react';
import { PropertySectionComponent, PropertySectionComponentProps } from '../form/Form.types';
import { GQLSlider } from '../form/FormEventFragments.types';
import { PropertySectionLabel } from './PropertySectionLabel';
import {
  GQLEditSliderInput,
  GQLEditSliderMutationData,
  GQLEditSliderMutationVariables,
  GQLEditSliderPayload,
  GQLErrorPayload,
  GQLSuccessPayload,
  GQLUpdateWidgetFocusInput,
  GQLUpdateWidgetFocusMutationData,
  GQLUpdateWidgetFocusMutationVariables,
  GQLUpdateWidgetFocusPayload,
} from './SliderPropertySection.types';

export const editSliderMutation = gql`
  mutation editSlider($input: EditSliderInput!) {
    editSlider(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export const updateWidgetFocusMutation = gql`
  mutation updateWidgetFocus($input: UpdateWidgetFocusInput!) {
    updateWidgetFocus(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const isErrorPayload = (payload: GQLEditSliderPayload | GQLUpdateWidgetFocusPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload: GQLEditSliderPayload | GQLUpdateWidgetFocusPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

export const SliderPropertySection: PropertySectionComponent<GQLSlider> = ({
  editingContextId,
  formId,
  widget,
  readOnly,
}: PropertySectionComponentProps<GQLSlider>) => {
  const [editSlider, { loading, data, error }] = useMutation<GQLEditSliderMutationData, GQLEditSliderMutationVariables>(
    editSliderMutation
  );

  const { addErrorMessage, addMessages } = useMultiToast();

  useEffect(() => {
    if (!loading) {
      if (error) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (data) {
        const { editSlider } = data;
        if (isErrorPayload(editSlider) || isSuccessPayload(editSlider)) {
          addMessages(editSlider.messages);
        }
      }
    }
  }, [loading, error, data]);
  const [
    updateWidgetFocus,
    { loading: updateWidgetFocusLoading, data: updateWidgetFocusData, error: updateWidgetFocusError },
  ] = useMutation<GQLUpdateWidgetFocusMutationData, GQLUpdateWidgetFocusMutationVariables>(updateWidgetFocusMutation);

  const sendUpdateWidgetFocus = (selected: boolean) => {
    const input: GQLUpdateWidgetFocusInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: formId,
      widgetId: widget.id,
      selected,
    };
    const variables: GQLUpdateWidgetFocusMutationVariables = {
      input,
    };
    updateWidgetFocus({ variables });
  };

  useEffect(() => {
    if (!updateWidgetFocusLoading) {
      if (updateWidgetFocusError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (updateWidgetFocusData) {
        const { updateWidgetFocus } = updateWidgetFocusData;
        if (isErrorPayload(updateWidgetFocus)) {
          addMessages(updateWidgetFocus.messages);
        }
      }
    }
  }, [updateWidgetFocusLoading, updateWidgetFocusData, updateWidgetFocusError]);

  const onFocus = () => sendUpdateWidgetFocus(true);
  const onBlur = () => {
    sendUpdateWidgetFocus(false);
  };

  const onValueChanged = (_, value) => {
    const input: GQLEditSliderInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: formId,
      sliderId: widget.id,
      newValue: value,
    };
    const variables: GQLEditSliderMutationVariables = { input };
    editSlider({ variables });
  };

  const [displayedValue, setDisplayedValue] = useState<number>(widget.currentValue);
  useEffect(() => {
    setDisplayedValue(widget.currentValue);
  }, [widget.currentValue]);

  return (
    <div>
      <PropertySectionLabel
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        data-testid={widget.label}
      />
      <Slider
        data-testid={widget.label}
        disabled={readOnly || widget.readOnly}
        onFocus={onFocus}
        onBlur={onBlur}
        min={widget.minValue}
        max={widget.maxValue}
        value={displayedValue}
        step={1}
        valueLabelDisplay="on"
        onChange={(_, value) => setDisplayedValue(value as number)}
        onChangeCommitted={onValueChanged}></Slider>
    </div>
  );
};
